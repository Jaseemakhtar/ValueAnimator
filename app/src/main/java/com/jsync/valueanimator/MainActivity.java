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
        animatorSet = new AnimatorSet();
        if (!animatorSet.isRunning()) {

            if (!isExpanded) {

                widthAnimator.setIntValues(v.getWidth(), parentLayout.getWidth());
                widthAnimator.setDuration(1000);

                positionAnimator.setIntValues(left,right);
                positionAnimator.setDuration(1000);

                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int width = (int) widthAnimator.getAnimatedValue();
                        btnExpand.setWidth(width);
                    }
                });

                positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer x = (Integer) animation.getAnimatedValue();
                        btnExpand.setX(x);
                    }
                });

                widthAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        btnExpand.setText("CLICK TO SHRINK!");
                        isExpanded = true;
                    }
                });


                animatorSet.playTogether(widthAnimator,positionAnimator);
                animatorSet.start();

            }else {


                widthAnimator.setIntValues(parentLayout.getWidth(),btnWidth);
                widthAnimator.setDuration(1000);

                positionAnimator.setIntValues(right,left);
                positionAnimator.setDuration(1000);

                widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int width = (int) animation.getAnimatedValue();
                        btnExpand.setWidth(width);


                    }
                });

                positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer x = (Integer) animation.getAnimatedValue();
                        btnExpand.setX(x);
                    }
                });

                widthAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isExpanded = false;
                        btnExpand.setText("Click To Expand!");
                    }
                });

                animatorSet.playTogether(widthAnimator,positionAnimator);
                animatorSet.start();
            }

        }

    }
}
