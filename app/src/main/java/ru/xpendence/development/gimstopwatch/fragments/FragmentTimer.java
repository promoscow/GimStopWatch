package ru.xpendence.development.gimstopwatch.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.BitmapHelper;
import ru.xpendence.development.gimstopwatch.util.MathHelper;

/**
 * Created by promoscow on 14.05.17.
 * Main activity for timer frame.
 */

public class FragmentTimer extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private static int absoluteSize = 0;

    private static int fragmentHeight = 0;
    private static int fragmentWidth = 0;
    private static float angle = 1;

    DrawView drawView;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentTimer fragment = new FragmentTimer();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentTimer.onCreate", String.valueOf(getActivity()));
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        drawView = new DrawView(getActivity());

        drawView.post(new Runnable() {
            @Override
            public void run() {

                fragmentHeight = drawView.getHeight();
                fragmentWidth = drawView.getWidth();
                Log.e("TEST",
                        "Fragment View height = " + drawView.getHeight()
                                + ", Fragment View width = " + drawView.getWidth()
                );
            }
        });

        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("FragmentTimer", "onClick");
                MathHelper.isStoped = !MathHelper.isStoped;
                }
                return false;
            }
        });
        Log.d("onCreateView", "exit");
        return drawView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        fragmentHeight = view.getHeight();
        fragmentWidth = view.getWidth();
        Log.d("fragmentHeight", String.valueOf(fragmentHeight));
        Log.d("fragmentWidth", String.valueOf(fragmentWidth));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated", "okay");
    }

    public class DrawView extends View {

        private static final long FPS = 200;
        private static final long TIME_DELAY = 1000 / FPS;
//        private boolean isSized = false;

        private float[] coordinates = new float[5];

        {
            coordinates = MathHelper.defineDensity(getActivity());
        }

        private float density = coordinates[0];
        private float height = coordinates[2] / coordinates[4] * 400;
        private float width = coordinates[2];
        private float dpHeight = 400;
        private float dpWidth = coordinates[4];

        {
            Log.d("FragmentTimer.density", String.valueOf(density));
            Log.d("FragmentTimer.height", String.valueOf(height));
            Log.d("FragmentTimer.width", String.valueOf(width));
            Log.d("FragmentTimer.dpHeight", String.valueOf(dpHeight));
            Log.d("FragmentTimer.dpWidth", String.valueOf(dpWidth));
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            Log.d("onFinishInflate", "okay");
        }

        private final Runnable mInvalidator = new Runnable() {
            @Override
            public final void run() {
                invalidate();
            }
        };

        Resources resources = this.getResources();
        private final Paint mPaint = new Paint();
        private final RectF rectF = new RectF();
        private final Path glare = new Path();
        private final Bitmap playButton =
                BitmapFactory.decodeResource(resources,
                        R.drawable.play_dark);
        private final Bitmap pauseButton =
                BitmapFactory.decodeResource(resources,
                        R.drawable.pause_dark);
//        private float angle;

        {
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.STROKE);

            rectF.set((width / 2) - (width / 3),
                    (height / 2) - (width / 3),
                    (width / 2) + (width / 3),
                    (height / 2) + (width / 3));

//            angle = 0;
        }

        public DrawView(Context context) {
            super(context);
        }

        public DrawView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public DrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected final void onDraw(final Canvas canvas) {

            height = canvas.getHeight();
            width = canvas.getWidth();

            rectF.set((width / 2) - (absoluteSize / 2.5f),
                    (height / 2) - (absoluteSize / 2.5f),
                    (width / 2) + (absoluteSize / 2.5f),
                    (height / 2) + (absoluteSize / 2.5f));


            absoluteSize = MathHelper.getAbsoluteSize(canvas.getHeight(), canvas.getWidth());

            super.onDraw(canvas);

//            drawBackground(canvas);
            drawBgCircle(canvas);

            mPaint.setColor(Color.WHITE);
//            Log.d("playButton", String.valueOf(playButton));
            canvas.drawBitmap(MathHelper.getBitmap(playButton, pauseButton),
                    canvas.getWidth() / 2 - playButton.getWidth() / 2,
                    canvas.getHeight() / 2 - playButton.getHeight() / 2, mPaint);

            drawCircle(canvas);
            drawMainArc(canvas);

            if (angle % 360 == 0) {
                angle = 0;
                MathHelper.setI();
            }
            postDelayed(mInvalidator, TIME_DELAY);
        }

        private void drawMainArc(Canvas canvas) {
            mPaint.setStrokeWidth(absoluteSize * 0.008f);
            angle = MathHelper.getAngle(angle);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(MathHelper.setTimelineColor(angle));
            canvas.drawArc(rectF, 270, angle, false, mPaint);
        }

        private void drawCircle(Canvas canvas) {
            mPaint.setStrokeWidth(absoluteSize * 0.008f);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(MathHelper.setBgI());
            canvas.drawCircle(width / 2, height / 2, (absoluteSize / 2.5f), mPaint);
        }

        private void drawBgCircle(Canvas canvas) {
            mPaint.setStrokeWidth(absoluteSize * 0.008f);
            mPaint.setColor(MathHelper.bgColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(width / 2, height / 2, absoluteSize / 2.5f, mPaint);
        }

        private void drawBackground(Canvas canvas) {
            glare.reset();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(BitmapHelper.BACKGROUND_COLOR);
            mPaint.setAlpha(255);
            glare.moveTo(fragmentWidth, fragmentHeight * 0.75f);
            glare.lineTo(fragmentWidth, fragmentHeight);
            glare.lineTo(0, fragmentHeight);
            glare.lineTo(0, fragmentHeight * 0.75f);
            glare.close();

            canvas.drawPath(glare, mPaint);
        }
    }
}

