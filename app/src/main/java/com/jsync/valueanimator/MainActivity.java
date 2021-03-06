package com.jsync.valueanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnExpand;
    private ValueAnimator widthAnimator;
    private ValueAnimator positionAnimator;
    private RelativeLayout parentLayout;
    private boolean isExpanded = false;
    private ViewGroup.LayoutParams params;
    private int btnWidth;
    private int right;
    private int left;
    private AnimatorSet animatorSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExpand = findViewById(R.id.btnExpand);
        btnExpand.setOnClickListener(this);
        params = btnExpand.getLayoutParams();

        widthAnimator = new ValueAnimator();
        positionAnimator = new ValueAnimator();
        animatorSet = new AnimatorSet();

        parentLayout = findViewById(R.id.parentLayout);

        final ViewTreeObserver observer = parentLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                btnWidth = btnExpand.getWidth();
                left = btnExpand.getLeft();
                right = parentLayout.getRight() - btnWidth;

                Log.i("Size","Button width: " + btnWidth);
            }
        });


    }

    @Override
    public void onClick(View v) {

        if (!animatorSet.isRunning()) {

            if (!isExpanded) {
                widthAnimator = new ValueAnimator();
                positionAnimator = new ValueAnimator();

                widthAnimator.setIntValues(v.getWidth(), parentLayout.getWidth());
                widthAnimator.setDuration(400);
                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int wi = (int) animation.getAnimatedValue();
                        btnExpand.setWidth(wi);
                        if (wi >= parentLayout.getWidth()){
                            RelativeLayout.LayoutParams layoutParamsView = new RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);

                            layoutParamsView.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                            btnExpand.setLayoutParams(layoutParamsView);
                        }

                    }
                });

                positionAnimator.setIntValues(parentLayout.getWidth(),btnWidth);
                positionAnimator.setDuration(400);
                positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int wi = (int) animation.getAnimatedValue();
                        btnExpand.setWidth(wi);
                    }
                });


                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isExpanded = true;
                        animatorSet.removeListener(this);
                        widthAnimator.removeAllUpdateListeners();
                        positionAnimator.removeAllUpdateListeners();
                        animatorSet = new AnimatorSet();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                animatorSet.playSequentially(widthAnimator,positionAnimator);
                animatorSet.start();
            }else {

                widthAnimator = new ValueAnimator();
                positionAnimator = new ValueAnimator();
                widthAnimator.setIntValues(btnWidth, parentLayout.getWidth());
                widthAnimator.setDuration(400);
                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int wi = (int) animation.getAnimatedValue();
                        btnExpand.setWidth(wi);
                        if (wi >= parentLayout.getWidth()){
                            RelativeLayout.LayoutParams layoutParamsView = new RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);

                            layoutParamsView.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                            btnExpand.setLayoutParams(layoutParamsView);

                        }
                       Log.i("Size","Size: " + wi);

                    }
                });

                positionAnimator.setIntValues(parentLayout.getWidth(),btnWidth);
                positionAnimator.setDuration(400);
                positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int wi = (int) animation.getAnimatedValue();
                        btnExpand.setWidth(wi);
                    }
                });

                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isExpanded = false;
                        animatorSet.removeListener(this);
                        widthAnimator.removeAllUpdateListeners();
                        positionAnimator.removeAllUpdateListeners();
                        animatorSet = new AnimatorSet();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.playSequentially(widthAnimator,positionAnimator);
                animatorSet.start();
            }

        }

    }
}
