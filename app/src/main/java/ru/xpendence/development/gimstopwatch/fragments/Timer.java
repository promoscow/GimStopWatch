package ru.xpendence.development.gimstopwatch.fragments;

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

import java.sql.Time;

import ru.xpendence.development.gimstopwatch.util.MathHelper;

/**
 * Created by promoscow on 14.05.17.
 * Main activity for timer frame.
 */

public class Timer extends Fragment {

    private static int absoluteSize = 0;

    private static int fragmentHeight = 0;
    private static int fragmentWidth = 0;
    private static float angle = 1;

    DrawView drawView;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        Timer fragment = new Timer();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Timer.onCreate", String.valueOf(getActivity()));
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("Timer.onCreateView", String.valueOf(inflater));
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
                Log.d("Timer", "onClick");
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
            Log.d("Timer.density", String.valueOf(density));
            Log.d("Timer.height", String.valueOf(height));
            Log.d("Timer.width", String.valueOf(width));
            Log.d("Timer.dpHeight", String.valueOf(dpHeight));
            Log.d("Timer.dpWidth", String.valueOf(dpWidth));
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

        private final Paint mPaint = new Paint();
        private final RectF rectF = new RectF();
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

            rectF.set((width / 2) - (absoluteSize / 3),
                    (height / 2) - (absoluteSize / 3),
                    (width / 2) + (absoluteSize / 3),
                    (height / 2) + (absoluteSize / 3));


            absoluteSize = MathHelper.getAbsoluteSize(canvas.getHeight(), canvas.getWidth());

            super.onDraw(canvas);
            mPaint.setColor(MathHelper.setBgI());
            canvas.drawCircle(width / 2, height / 2, (absoluteSize / 3), mPaint);

            angle = MathHelper.getAngle(angle);
            mPaint.setColor(MathHelper.setTimelineColor(angle));

            canvas.drawArc(rectF, 270, angle, false, mPaint);

            mPaint.setTextSize(100);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(String.valueOf(MathHelper.getI()), 100, 200, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);

            if (angle % 360 == 0) {
                angle = 0;
                MathHelper.setI();
            }
            postDelayed(mInvalidator, TIME_DELAY);
        }
    }
}

