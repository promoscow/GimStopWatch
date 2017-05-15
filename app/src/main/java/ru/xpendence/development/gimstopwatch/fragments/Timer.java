package ru.xpendence.development.gimstopwatch.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import ru.xpendence.development.gimstopwatch.util.MathHelper;

/**
 * Created by promoscow on 14.05.17.
 * Main activity for timer frame.
 */

public class Timer extends Fragment {

    DrawView drawView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Timer.onCreate", String.valueOf(getActivity()));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Timer.onCreateView", String.valueOf(inflater));
        drawView = new DrawView(getActivity());
        return drawView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated", "okay");
    }

    public class DrawView extends View {

        private static final long FPS = 100;
        private static final long TIME_DELAY = 1000 / FPS;

        private float[] coordinates = new float[5];

        {
            coordinates = MathHelper.defineDensity(getActivity());
        }

        private final float DENSITY = coordinates[0];
        private final float HEIGHT = coordinates[2] / coordinates[4] * 400;
        private final float WIDTH = coordinates[2];
        private final float DP_HEIGHT = 400;
        private final float DP_WIDTH = coordinates[4];

        private Random random;

        {
            Log.d("Timer.density", String.valueOf(DENSITY));
            Log.d("Timer.height", String.valueOf(HEIGHT));
            Log.d("Timer.width", String.valueOf(WIDTH));
            Log.d("Timer.dpHeight", String.valueOf(DP_HEIGHT));
            Log.d("Timer.dpWidth", String.valueOf(DP_WIDTH));
        }

        private final Runnable mInvalidator = new Runnable() {
            @Override
            public final void run() {
                invalidate();
            }
        };

        private final Paint mPaint = new Paint();
        private final RectF rectF = new RectF();
        private float angle;

        {
            mPaint.setStrokeWidth(20);
            mPaint.setStyle(Paint.Style.STROKE);

            rectF.set((WIDTH / 2) - (WIDTH / 3),
                    (HEIGHT / 2) - (WIDTH / 3),
                    (WIDTH / 2) + (WIDTH / 3),
                    (HEIGHT / 2) + (WIDTH / 3));

            angle = 0;
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
            super.onDraw(canvas);
//            random = new Random();
//            float x1 = random.nextInt(1000) + 10;
//            float y1 = random.nextInt(1000) + 10;
//            float x2 = random.nextInt(1000) + 10;
//            float y2 = random.nextInt(1000) + 10;
//            int a = random.nextInt(255);
//            int b = random.nextInt(255);
//            int c = random.nextInt(255);
//
//            mPaint.setARGB(100, a, b, c);
//            canvas.drawLine(x1, y1, x2, y2, mPaint);
            mPaint.setColor(MathHelper.setBgI());
//            mPaint.setARGB(100, MathHelper.setBgRGB(0), MathHelper.setBgRGB(1), MathHelper.setBgRGB(2));
            canvas.drawCircle(WIDTH / 2, HEIGHT / 2, (WIDTH / 3), mPaint);

            angle = MathHelper.getAngle(angle);
            mPaint.setColor(MathHelper.setTimelineColor(angle));
//            mPaint.setARGB(100, MathHelper.setRGB(0), MathHelper.setRGB(1), MathHelper.setRGB(2));

            canvas.drawArc(rectF, 270, angle, false, mPaint);
            if (angle % 360 == 0) {
                angle = 0;
                MathHelper.setI();
//                MathHelper.setY();
            }
            postDelayed(mInvalidator, TIME_DELAY);
        }
    }
}

