// Copyright (c) 2020 Facebook, Inc. and its affiliates.
// All rights reserved.
//
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree.

package com.healthc.app.presentation.detection.object_detection;

import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.healthc.data.model.local.detection.ObjectDetectionResult;

import java.util.List;

public class ObjectDetectionResultView extends View {

    private final static int TEXT_X = 40;
    private final static int TEXT_Y = 35;
    private final static int TEXT_WIDTH = 260;
    private final static int TEXT_HEIGHT = 50;

    private Paint mPaintRectangle;
    private Paint mPaintText;
    private List<ObjectDetectionResult> objectDetectionResultList;
    private List<String> classList;

    public ObjectDetectionResultView(Context context) {
        super(context);
    }

    public ObjectDetectionResultView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaintRectangle = new Paint();
        mPaintRectangle.setColor(Color.BLUE);
        mPaintText = new Paint();
    }
    @SuppressLint({"DrawAllocation", "DefaultLocale"})
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (objectDetectionResultList.isEmpty()) return;

        for (ObjectDetectionResult objectDetectionResult : objectDetectionResultList) {
            mPaintRectangle.setStrokeWidth(5);
            mPaintRectangle.setStyle(Paint.Style.STROKE);
            canvas.drawRect(objectDetectionResult.getRect(), mPaintRectangle);

            Path mPath = new Path();
            RectF mRectF = new RectF(
                    objectDetectionResult.getRect().left,
                    objectDetectionResult.getRect().top,
                    objectDetectionResult.getRect().left + TEXT_WIDTH,
                    objectDetectionResult.getRect().top + TEXT_HEIGHT
            );

            mPath.addRect(mRectF, Path.Direction.CW);
            mPaintText.setColor(Color.MAGENTA);
            canvas.drawPath(mPath, mPaintText);

            mPaintText.setColor(Color.WHITE);
            mPaintText.setStrokeWidth(0);
            mPaintText.setStyle(Paint.Style.FILL);
            mPaintText.setTextSize(32);
            canvas.drawText(
                    String.format(
                            "%s %.2f",
                            classList.get(objectDetectionResult.getClassIndex()),
                            objectDetectionResult.getScore()
                    ),
                    objectDetectionResult.getRect().left + TEXT_X,
                    objectDetectionResult.getRect().top + TEXT_Y, mPaintText
            );
        }
    }

    public void setObjectDetectionResult(
        List<ObjectDetectionResult> objectDetectionResultList,
        List<String> classList
    ) {
        this.objectDetectionResultList = objectDetectionResultList;
        this.classList = classList;
    }
}
