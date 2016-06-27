package com.elpassion.loadingerrorsuccessbutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((TextView)v).setText(null);
                v.setBackgroundResource(R.drawable.round_rect);
                final View viewById = findViewById(R.id.frame);
                DisplayMetrics dm = getResources().getDisplayMetrics();
                ResizeAnimation animation = new ResizeAnimation(viewById,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, dm),
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, dm),
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 66, dm),
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 66, dm)
                );
                viewById.startAnimation(animation);
                findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                viewById.animate()
                        .setStartDelay(2000)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                v.setBackgroundResource(R.drawable.round_rect_red);
                                ((TextView) v).setTextColor(Color.BLACK);
                                ((TextView) v).setText("!");
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        })
                        .setInterpolator(new TimeInterpolator() {
                            @Override
                            public float getInterpolation(float input) {
                                input *= 4 * Math.PI;
                                return (float) (Math.sin(input) * 30);
                            }
                        })
                        .translationXBy(1)
                        .start();
            }
        });
    }
}

class ResizeAnimation extends Animation {
    private View mView;
    private float mToHeight;
    private float mFromHeight;

    private float mToWidth;
    private float mFromWidth;

    public ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
        mToHeight = toHeight;
        mToWidth = toWidth;
        mFromHeight = fromHeight;
        mFromWidth = fromWidth;
        mView = v;
        setDuration(300);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float height =
                (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
        float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        ViewGroup.LayoutParams p = mView.getLayoutParams();
        p.height = (int) height;
        p.width = (int) width;
        mView.requestLayout();
    }
}