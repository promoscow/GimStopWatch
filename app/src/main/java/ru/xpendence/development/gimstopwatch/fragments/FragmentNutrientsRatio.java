package ru.xpendence.development.gimstopwatch.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import ru.xpendence.development.gimstopwatch.util.MathHelper;
import ru.xpendence.development.gimstopwatch.util.NutrientsHelper;
import ru.xpendence.development.gimstopwatch.util.PersonalData;

/**
 * Created by promoscow on 23.05.17.
 * Nutrients Ratio displayed as a diagram.
 */

public class FragmentNutrientsRatio extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private static int absoluteSize = 0;
    private static int indent = 0;

    private static int fragmentHeight = 0;
    private static int fragmentWidth = 0;

    private static float angleProteins;
    private static float angleFats;
    private static float angleCarboHydrates;

    private float proteinsDayNorm = (float) PersonalData.getDailyProteins();
    private float fatsDayNorm = (float) PersonalData.getDailyFats();
    private float carboHydratesDayNorm = (float) PersonalData.getDailyCarbohydrates();

    NutrientsHelper nutrientsHelper;

    {
        nutrientsHelper = new NutrientsHelper(proteinsDayNorm, fatsDayNorm, carboHydratesDayNorm);

        angleCarboHydrates = nutrientsHelper.getCarboHydratesAngle();
        angleFats = nutrientsHelper.getFatsAngle();
        angleProteins = nutrientsHelper.getProteinsAngle();

        Log.d("carboHydratesAngle", String.valueOf(angleCarboHydrates));
        Log.d("fatsAngle", String.valueOf(angleFats));
        Log.d("proteinsAngle", String.valueOf(angleProteins));
    }

    FragmentNutrientsRatio.DrawView drawView;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FragmentNutrientsRatio fragment = new FragmentNutrientsRatio();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentTimer.onCreate", String.valueOf(getActivity()));
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        drawView = new FragmentNutrientsRatio.DrawView(getActivity());

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

        private static final long FPS = 10;
        private static final long TIME_DELAY = 1000 / FPS;

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
        private final RectF rectBG = new RectF();
        private final Bitmap help =
                BitmapFactory.decodeResource(resources,
                        R.drawable.help);
        private final RectF rectText = new RectF();

        {
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.FILL);

            rectF.set((width / 2) - (absoluteSize / 3),
                    (height / 2) - (absoluteSize / 3),
                    (width / 2) + (absoluteSize / 3),
                    (height / 2) + (absoluteSize / 3));

            rectBG.set((width / 2) - (absoluteSize / 2.5f),
                    (height / 2) - (absoluteSize / 2.5f),
                    (width / 2) + (absoluteSize / 2.5f),
                    (height / 2) + (absoluteSize / 2.5f));

//            rectText.set((width / 2) - 200,
//                    (indent / 2) - indent * 0.25f,
//                    width / 2 + 200,
//                    indent / 2 + indent * 0.25f);
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
                    ((height + indent) / 2) - (absoluteSize / 2.5f),
                    (width / 2) + (absoluteSize / 2.5f),
                    ((height + indent) / 2) + (absoluteSize / 2.5f));

            rectBG.set((width / 2) - (absoluteSize/ 2),
                    ((height + indent) / 2) - (absoluteSize / 2),
                    (width / 2) + (absoluteSize / 2),
                    ((height + indent) / 2) + (absoluteSize / 2));

//            rectText.set((width / 2) - 200,
//                    (indent * 0.75f) - indent * 0.25f,
//                    width / 2 + 200,
//                    indent * 0.75f + indent * 0.25f);

            absoluteSize = MathHelper.getAbsoluteSize(canvas.getHeight(), canvas.getWidth());
//            indent = MathHelper.getIndent(canvas.getHeight());

            super.onDraw(canvas);

            drawIdealNutrients(canvas);
//            drawMainShadow(canvas);
            drawMainNutrients(canvas);

            postDelayed(mInvalidator, TIME_DELAY);
        }

        private void drawMainShadow(Canvas canvas) {
            mPaint.setColor(Color.DKGRAY);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAlpha(100);
            canvas.drawCircle((width / 2) + 2, (height / 2) + 2, absoluteSize / 2.5f, mPaint);
        }

        private void drawIdealNutrients(Canvas canvas) {

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAlpha(255);

            mPaint.setColor(NutrientsHelper.getLightBlue());
            canvas.drawArc(rectBG, 270, 360 * 0.35f, true, mPaint);
            mPaint.setColor(NutrientsHelper.getLightYellow());
            canvas.drawArc(rectBG, 360 * 0.35f - 90, 360 * 0.15f, true, mPaint);
            mPaint.setColor(NutrientsHelper.getLightGreen());
            canvas.drawArc(rectBG, 360 * 0.35f - 90 + 360 * 0.15f, 180, true, mPaint);
        }

        private void drawMainNutrients(Canvas canvas) {

            if (PersonalData.getTotalDailyNutrients() > 0) {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAlpha(255);

                mPaint.setColor(NutrientsHelper.getBLUE());
                canvas.drawArc(rectF, 270, angleProteins, true, mPaint);
                mPaint.setColor(NutrientsHelper.getYELLOW());
                canvas.drawArc(rectF, angleProteins - 90, angleFats, true, mPaint);
                mPaint.setColor(NutrientsHelper.getGREEN());
                canvas.drawArc(rectF, angleProteins - 90 + angleFats, angleCarboHydrates, true, mPaint);
            } else {
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAlpha(255);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle((width / 2), (height / 2), absoluteSize / 2.5f, mPaint);

                canvas.drawBitmap(help,
                        canvas.getWidth() / 2 - help.getWidth() / 2,
                        canvas.getHeight() / 2 - help.getHeight() / 2, mPaint);
            }
        }
    }
}
