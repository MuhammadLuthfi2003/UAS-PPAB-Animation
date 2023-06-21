package com.example.animation_uas;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();

    float middleX, middleY, radiusX, radiusY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.my_img_view);

        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);


        middleX = vWidth / 2f;
        middleY = vHeight / 2f;
        radiusX = vWidth / 3f;
        radiusY = vHeight/4f;

        drawHead();
        drawRightEye();
        drawLeftEye();
        drawEyeConnector();

        // set fade animation
        ObjectAnimator fadeAnimation = ObjectAnimator.ofFloat(mImgView, "alpha", 0f, 1f);
        fadeAnimation.setDuration(1000);

        ObjectAnimator fadeAnimation_OUT = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        fadeAnimation_OUT.setDuration(1000);

        // Set up the flip animation
        AnimatorSet flipAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_animation);
        flipAnimator.setTarget(mImgView);

        // Apply the flip animation when the view is clicked or triggered
        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(flipAnimator).before(fadeAnimation_OUT).after(fadeAnimation);
                animatorSet.start();
            }
        });


    }

    private void drawEyeConnector() {
        mCirclePaint.setStyle(Paint.Style.FILL);

        Rect path = new Rect(
                (int) (middleX - 250),
                (int) middleY + 10,
                (int) middleX + 250,
                (int) middleY - 10
        );

        mCanvas.drawRect(path, mCirclePaint);
        mImgView.setImageBitmap(mBitmap);
    }

    private void drawLeftEye() {
        mCirclePaint.setStyle(Paint.Style.FILL);

        mCanvas.drawCircle(
                middleX  - 250,
                middleY,
                radiusX - 290,
                mCirclePaint
        );

        mImgView.setImageBitmap(mBitmap);
    }

    private void drawRightEye() {
        mCirclePaint.setStyle(Paint.Style.FILL);

        mCanvas.drawCircle(
                middleX  + 250,
                middleY,
                radiusX - 290,
                mCirclePaint
        );

        mImgView.setImageBitmap(mBitmap);
    }

    private void drawHead() {
        mHeadPaint.setStyle(Paint.Style.FILL);

        RectF head = new RectF (
                middleX - radiusY - 50,
                middleY - radiusX + 25,
                middleX + radiusY + 50,
                middleY + radiusX - 25
        );

        mCanvas.drawOval(head, mHeadPaint);
        mImgView.setImageBitmap(mBitmap);
    }




}