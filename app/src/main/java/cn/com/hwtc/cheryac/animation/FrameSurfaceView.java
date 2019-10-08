package cn.com.hwtc.cheryac.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 频谱帧动画
 */
public class FrameSurfaceView extends BaseSurfaceView {
    private static final String TAG = FrameSurfaceView.class.getSimpleName();
    private List<Integer> normalBitmaps;
    public static final int INVALID_INDEX = Integer.MAX_VALUE;
    private int bufferSize = 3;
    public static final String DECODE_THREAD_NAME = "DecodingThread";
    public static final int INFINITE = -1;
    private int repeatTimes;
    private int repeatedCount;

    private boolean isInit = false;
    /**
     * the resources of frame animation
     */
    private List<Integer> bitmapIds = new ArrayList<>();

    /**
     * the index of bitmap resource which is decoding
     */
    private int bitmapIdIndex = 400;
    /**
     * the index of frame which is drawing
     */
    private int frameIndex = INVALID_INDEX;
    /**
     * decoded bitmaps stores in this queue
     * consumer is drawing thread, producer is decoding thread.
     */
    private LinkedBlockingQueue decodedBitmaps = new LinkedBlockingQueue(bufferSize);
    /**
     * bitmaps already drawn by canvas stores in this queue
     * consumer is decoding thread, producer is drawing thread.
     */
    private LinkedBlockingQueue drawnBitmaps = new LinkedBlockingQueue(bufferSize);


    private LinkedBlockingQueue originDrawnBitmaps = new LinkedBlockingQueue(bufferSize);
    /**
     * the thread for decoding bitmaps
     */
    private HandlerThread decodeThread;
    /**
     * the Runnable describes how to decode one bitmap
     */
    private DecodeRunnable decodeRunnable;
    /**
     * this handler helps to decode bitmap one after another
     */
    private Handler handler;
    private BitmapFactory.Options options;
    private Paint paint = new Paint();
    private Rect srcRect;
    private Rect dstRect = new Rect();
    private int defaultWidth;
    private int defaultHeight;
    public static final int FRAM_ANIMATION_DURATION = 14000;

    public int index;
    private int originIndex = 0;

    public FrameSurfaceView(Context context) {
        super(context);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    @Override
    protected void init() {
        super.init();
        options = new BitmapFactory.Options();
        options.inMutable = true;
        decodeThread = new HandlerThread(DECODE_THREAD_NAME);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        dstRect.set(0, 0, getWidth(), getHeight());
    }


    @Override
    protected int getDefaultWidth() {
        return defaultWidth;
    }

    @Override
    protected int getDefaultHeight() {
        return defaultHeight;
    }

    @Override
    protected void onFrameDrawFinish() {
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
//        if (visibility == View.VISIBLE) {
//            isVisible = true;
//        } else {
//            isVisible = false;
//        }
        isVisible = visibility == View.VISIBLE;
    }

    public boolean isInit() {
        return isInit;
    }

    /**
     * set the duration of frame animation
     *
     * @param duration time in milliseconds
     */
    public void setDuration(int duration) {
        int frameDuration = duration / bitmapIds.size();
        setFrameDuration(frameDuration);
    }

    /**
     * set the materials of frame animation which is an array of bitmap resource id
     *
     * @param
     */
    public void setBitmapIds(int index) {
        normalBitmaps = VisualizerUtil.getInstance().getBitmapIds();
        if (normalBitmaps == null || normalBitmaps.size() == 0) {
            return;
        }
        originIndex = index;
        bitmapIdIndex = index;
        this.bitmapIds = normalBitmaps;
        getBitmapDimension(bitmapIds.get(bitmapIdIndex));
        preloadFrames();
        decodeRunnable = new DecodeRunnable(bitmapIdIndex, bitmapIds, options);
        isInit = true;
    }

    private void getBitmapDimension(int bitmapId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), bitmapId, options);
        defaultWidth = options.outWidth;
        defaultHeight = options.outHeight;
        srcRect = new Rect(0, 0, defaultWidth, defaultHeight);
        //we have to re-measure to make defaultWidth in use in onMeasure()
        requestLayout();
    }

    /**
     * load the first several frames of animation before it is started
     */
    private void preloadFrames() {
        putDecodedBitmap(bitmapIds.get(bitmapIdIndex++), options, new LinkedBitmap());
//        putDecodedBitmap(bitmapIds.get(bitmapIdIndex++), options, new LinkedBitmap());
    }

    /**
     * recycle the bitmap used by frame animation.
     * Usually it should be invoked when the ui of frame animation is no longer visible
     */
    public void destroy() {
        if (drawnBitmaps != null) {
            drawnBitmaps.clear();
        }
        if (originDrawnBitmaps != null) {
            originDrawnBitmaps.clear();
        }
        if (decodeThread != null) {
            decodeThread.quit();
            decodeThread = null;
        }
        if (handler != null) {
            handler = null;
        }
    }

    @Override
    protected void onRestoreFrameDraw(Canvas canvas) {
        super.onRestoreFrameDraw(canvas);
        drawFrame(canvas, false);
    }

    @Override
    protected void onFrameDraw(Canvas canvas) {
        drawFrame(canvas, true);
    }

    @Override
    protected void setFrameDuration(int frameDuration) {
        super.setFrameDuration(frameDuration);
    }

    private void drawFrame(Canvas canvas, boolean isDrawNew) {
//        clearCanvas(canvas);
        if (!isStart()) {
            return;
        }
        if (!isFinish()) {
            clearCanvas(canvas);

            if (isDrawNew) {
                drawNewFrame(canvas);
            } else {
                /*drawNewFrame(canvas);*/
                drawRestoreFrame(canvas);
            }
        } else {
            onFrameAnimationEnd();
            if (repeatTimes != 0 && repeatTimes == INFINITE) {
                start();
                drawNewFrame(canvas);
            } else if (repeatedCount < repeatTimes) {
                start();
                repeatedCount++;
            } else {
                repeatedCount = 0;
            }
        }
    }

    /**
     * draw a single frame which is a bitmap
     *
     * @param canvas
     */
    private void drawNewFrame(Canvas canvas) {
        LinkedBitmap linkedBitmap = getDecodedBitmap();
        if (linkedBitmap != null) {
            canvas.drawBitmap(linkedBitmap.bitmap, srcRect, dstRect, paint);
            putDrawnBitmap(linkedBitmap);
        }
        frameIndex++;
        if (mOnDrawnFrameChange != null) {
            mOnDrawnFrameChange.onDrawnIndex(frameIndex);
        }
    }

    /**
     * draw a restore frame from the last drawn
     *
     * @param canvas
     */
    private void drawRestoreFrame(Canvas canvas) {
        LinkedBitmap linkedBitmap = getOriginDrawnBitmap();
        if (linkedBitmap != null) {
            canvas.drawBitmap(linkedBitmap.bitmap, srcRect, dstRect, paint);
        }
    }

    /**
     * invoked when frame animation is done
     */
    private void onFrameAnimationEnd() {
        reset();
    }

    /**
     * reset the index of frame, preparing for the next frame animation
     */
    public void reset() {
        frameIndex = INVALID_INDEX;
        if (originIndex != 0) {
            originIndex = 0;
        }
    }

    /**
     * whether frame animation is finished
     *
     * @return true: animation is finished, false: animation is doing
     */
    private boolean isFinish() {
        return frameIndex >= bitmapIds.size() - originIndex;
    }

    /**
     * whether frame animation is started
     *
     * @return true: animation is started, false: animation is not started
     */
    private boolean isStart() {
        return frameIndex != INVALID_INDEX;
    }


    public void setStart(boolean isStart) {
        this.isStart = isStart;
        isStopEnd = false;
    }


    /**
     * start frame animation from the first frame
     */
    public void start() {
        frameIndex = 0;
        if (decodeThread == null) {
            decodeThread = new HandlerThread(DECODE_THREAD_NAME);
        }
        if (!decodeThread.isAlive()) {
            decodeThread.start();
        }
        if (handler == null) {
            handler = new Handler(decodeThread.getLooper());
        }
        if (decodeRunnable != null) {
            decodeRunnable.setIndex(0);
            handler.post(decodeRunnable);
        }
    }

    /**
     * clear out the drawing on canvas,preparing for the next frame
     * * @param canvas
     */
    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    /**
     * decode bitmap by BitmapFactory.decodeStream(), it is about twice faster than BitmapFactory.decodeResource()
     *
     * @param resId   the bitmap resource
     * @param options
     * @return
     */
    private Bitmap decodeBitmap(int resId, BitmapFactory.Options options) {
        options.inScaled = false;
        InputStream inputStream = getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private void putDecodedBitmapByReuse(int resId, BitmapFactory.Options options) {
        LinkedBitmap linkedBitmap = getDrawnBitmap();
        if (linkedBitmap == null) {
            linkedBitmap = new LinkedBitmap();
        }
        options.inBitmap = linkedBitmap.bitmap;
        putDecodedBitmap(resId, options, linkedBitmap);
    }

    private void putDecodedBitmap(int resId, BitmapFactory.Options options, LinkedBitmap linkedBitmap) {
        Bitmap bitmap = decodeBitmap(resId, options);
        linkedBitmap.bitmap = bitmap;
        try {
            decodedBitmaps.put(linkedBitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putDrawnBitmap(LinkedBitmap bitmap) {
        if (bitmap != null) {
            drawnBitmaps.offer(bitmap);
            originDrawnBitmaps.offer(bitmap);
        }
    }

    /**
     * get bitmap which already drawn by canvas,but not reuse
     *
     * @return
     */
    private LinkedBitmap getOriginDrawnBitmap() {
        LinkedBitmap bitmap = null;
        try {
            bitmap = originDrawnBitmaps.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * get bitmap which already drawn by canvas
     *
     * @return
     */
    private LinkedBitmap getDrawnBitmap() {
        LinkedBitmap bitmap = null;
        try {
            bitmap = drawnBitmaps.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * get decoded bitmap in the decoded bitmap queue
     * it might block due to new bitmap is not ready
     *
     * @return
     */
    private LinkedBitmap getDecodedBitmap() {
        LinkedBitmap bitmap = null;
        try {
            bitmap = decodedBitmaps.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public int getIndex() {
        return index;
    }

    private class DecodeRunnable implements Runnable {

        private List<Integer> bitmapIds;
        private BitmapFactory.Options options;

        public DecodeRunnable(int ind, List<Integer> bitmapIds, BitmapFactory.Options options) {
            index = ind;
            this.bitmapIds = bitmapIds;
            this.options = options;
        }

        public void setIndex(int index) {
            index = index;
        }

        @Override
        public void run() {
            putDecodedBitmapByReuse(bitmapIds.get(index), options);
            index++;
            if (index < bitmapIds.size()) {
                handler.post(this);
            } else {
                index = 0;
            }
        }
    }

    public interface OnDrawnFrameChange {
        public void onDrawnIndex(int index);
    }

    private OnDrawnFrameChange mOnDrawnFrameChange;

    public void setOnDrawnFrameChange(OnDrawnFrameChange onDrawnFrameChange) {
        mOnDrawnFrameChange = onDrawnFrameChange;
    }
}
