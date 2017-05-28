package ru.xpendence.development.gimstopwatch.fragments;

import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.xpendence.development.gimstopwatch.R;
import ru.xpendence.development.gimstopwatch.util.BitmapHelper;
import ru.xpendence.development.gimstopwatch.util.MathHelper;

/**
 * Created by promoscow on 14.05.17.
 * Main activity for timer frame.
 */

public class FragmentFillDayRate extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private static int fragmentHeight = 0;
    private static int fragmentWidth = 0;

    DrawView drawView;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentFillDayRate fragment = new FragmentFillDayRate();
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
                if (container != null) {
                    fragmentHeight = container.getHeight();
                    fragmentWidth = container.getWidth();
                    Log.e("TEST",
                            "Parent height = " + container.getHeight()
                                    + ", Parent width = " + container.getWidth()
                    );
                }
                fragmentHeight = drawView.getHeight();
                fragmentWidth = drawView.getWidth();
                Log.e("TEST",
                        "Fragment View height = " + drawView.getHeight()
                                + ", Fragment View width = " + drawView.getWidth()
                );
            }
        });
        drawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FragmentTimer", "onClick");
                MathHelper.isStoped = !MathHelper.isStoped;
            }
        });

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

        Resources resources = this.getResources();

        private static final long FPS = 10;
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

        private final Runnable mInvalidator = new Runnable() {
            @Override
            public final void run() {
                invalidate();
            }
        };

        private final Paint mPaint = new Paint();
//        private final Rect mRect = new Rect();
        private final RectF rectF = new RectF();
        private final Bitmap calContainer =
                BitmapFactory.decodeResource(resources,
                        R.drawable.cal_container_200);
        private final Bitmap calContainerFill =
                BitmapFactory.decodeResource(resources,
                        BitmapHelper.getCalContainerFill());
        private final Bitmap plusIcon =
                BitmapFactory.decodeResource(resources,
                        R.drawable.plus_top);

        {
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.STROKE);

            rectF.set(WIDTH - 100, 200, WIDTH + 100, 300);
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

            drawFill(canvas);

            postDelayed(mInvalidator, TIME_DELAY);
        }

        private void drawPlusButton(Canvas canvas) {
            mPaint.setColor(0xFFFFFFFF);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(canvas.getWidth() - 150, 50, canvas.getWidth() + 150, 200, 150, 150, mPaint);
            canvas.drawBitmap(plusIcon, canvas.getWidth() - 135, 63, mPaint);
        }

        private void drawFill(Canvas canvas) {

            mPaint.setColor(0xFFFFFFFF);

            canvas.drawBitmap(calContainerFill,
                    canvas.getWidth() / 2 - calContainer.getWidth() / 2,
                    canvas.getHeight() / 2 - calContainer.getHeight() / 2,
                    mPaint);
        }
    }
}

