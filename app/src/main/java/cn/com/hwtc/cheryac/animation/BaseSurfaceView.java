package cn.com.hwtc.cheryac.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEFAULT_FRAME_DURATION_MILLISECOND = 50;
    public static final int MSG_DRAW_NEW = 0X10;
    public static final int MSG_DRAW_RESTORE = 0X11;

    private HandlerThread handlerThread;
    private SurfaceViewHandler handler;
    protected int frameDuration = DEFAULT_FRAME_DURATION_MILLISECOND;
    private Canvas canvas;
    private boolean isAlive;
    protected  boolean isVisible = true;
    protected boolean isStart = false;
    protected boolean isStopEnd = false;
    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected int getFrameDuration() {
        return frameDuration;
    }

    protected void setFrameDuration(int frameDuration) {
        this.frameDuration = frameDuration;
    }

    protected void init() {
        getHolder().addCallback(this);
        setBackgroundTransparent();
    }

    private void setBackgroundTransparent() {
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        //是否允许其他控件覆盖在SurfaceView之上
//        setZOrderMediaOverlay(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       startDrawThread();
        isAlive = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (this) {
            isAlive = false;
            stopDrawThread();

           /* isLastStop = isStart ;
            isStart = false;
           isStopEnd = false ;*/
          if(isStart){
              isStopEnd = false ;
           }

        }
    }

    private void stopDrawThread() {
        handler.removeCallbacksAndMessages(null);
        handlerThread.quit();
        handler = null;
    }

    private void startDrawThread() {
        handlerThread = new HandlerThread("SurfaceViewThread");
        handlerThread.start();
        handler = new SurfaceViewHandler(handlerThread.getLooper());
        handler.sendEmptyMessage(MSG_DRAW_NEW);
       /* if (isStart) {
            handler.sendEmptyMessage(MSG_DRAW_RESTORE);
        }else {
            handler.sendEmptyMessage(MSG_DRAW_NEW);
        }*/

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int originWidth = getMeasuredWidth();
        int originHeight = getMeasuredHeight();
        int width = widthMode == MeasureSpec.AT_MOST ? getDefaultWidth() : originWidth;
        int height = heightMode == MeasureSpec.AT_MOST ? getDefaultHeight() : originHeight;
        setMeasuredDimension(width, height);
        Log.v("ttaylor", "BaseSurfaceView.onMeasure()" + "  default Width=" + getDefaultWidth() + " default height=" + getDefaultHeight());
    }

    /**
     * the width is used when wrap_content is set to layout_width
     * the child knows how big it should be
     *
     * @return
     */
    protected abstract int getDefaultWidth();

    /**
     * the height is used when wrap_content is set to layout_height
     * the child knows how big it should be
     *
     * @return
     */
    protected abstract int getDefaultHeight();


    private class SurfaceViewHandler extends Handler {

        public SurfaceViewHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DRAW_RESTORE:
                    Canvas canvas = getHolder().lockCanvas();
                    onRestoreFrameDraw(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                    break;
                case MSG_DRAW_NEW:
                    post(new DrawRunnable());
                    break;
                 default:
                     break;

            }

        }
    }

    private class DrawRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
            if (!isAlive) {
                return;
            }
            try {
                if (isStart ) {
                    if (!isStopEnd){
                        canvas = getHolder().lockCanvas();
                        onFrameDraw(canvas);
                    }
                }else{
                    canvas = getHolder().lockCanvas();
                    onFrameDraw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (getHolder() != null) {
                    if (!isAlive) {
                        return;
                    }
                    if (isStart) {
                        if (!isStopEnd){
                            getHolder().unlockCanvasAndPost(canvas);
                            isStopEnd = true ;
                        }
                    }else{
                        if(canvas!=null){
                            getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                    onFrameDrawFinish();
                }
            }
                if (!isAlive) {
                    return;
                }
            handler.postDelayed(this, frameDuration);
        }
        }
    }

    /**
     * it is will be invoked after one frame is drawn
     */
    protected abstract void onFrameDrawFinish();

    /**
     * draw one frame to the surface by canvas
     *
     * @param canvas
     */
    protected abstract void onFrameDraw(Canvas canvas);

    protected void onRestoreFrameDraw(Canvas canvas){}

}
