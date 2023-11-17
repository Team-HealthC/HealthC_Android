package com.healthc.data.utils;

import android.graphics.Rect;

import com.healthc.data.model.local.detection.ObjectDetectionResult;
import com.healthc.data.model.local.detection.PreprocessedImage;

import java.util.ArrayList;
import java.util.Arrays;

public class PrePostProcessor {
    // for yolov5 model, no need to apply MEAN and STD
    public static float[] NO_MEAN_RGB = new float[] {0.0f, 0.0f, 0.0f};
    public static float[] NO_STD_RGB = new float[] {1.0f, 1.0f, 1.0f};

    private static int mOutputRow = 25200; // as decided by the YOLOv5 model for input image of size 640*640
    private static int mOutputColumn = 58; // left, top, right, bottom, score and 80 class probability
    private static float mThreshold = 0.30f; // score above which a detection is generated
    private static int mNmsLimit = 15;

    // The two methods nonMaxSuppression and IOU below are ported from https://github.com/hollance/YOLO-CoreML-MPSNNGraph/blob/master/Common/Helpers.swift
    /**
     Removes bounding boxes that overlap too much with other boxes that have
     a higher score.
     - Parameters:
     - boxes: an array of bounding boxes and their scores
     - limit: the maximum number of boxes that will be selected
     - threshold: used to decide whether boxes overlap too much
     */
    static ArrayList<ObjectDetectionResult> nonMaxSuppression(
        ArrayList<ObjectDetectionResult> boxes, int limit, float threshold
    ) {
        // Do an argsort on the confidence scores, from high to low.
        // Collections.sort(boxes, (o1, o2) -> o1.compareTo(o2));
        boxes.sort(ObjectDetectionResult::compareTo);

        ArrayList<ObjectDetectionResult> selected = new ArrayList<>();
        boolean[] active = new boolean[boxes.size()];
        Arrays.fill(active, true);
        int numActive = active.length;

        // The algorithm is simple: Start with the box that has the highest score.
        // Remove any remaining boxes that overlap it more than the given threshold
        // amount. If there are any boxes left (i.e. these did not overlap with any
        // previous boxes), then repeat this procedure, until no more boxes remain
        // or the limit has been reached.
        boolean done = false;
        for (int i=0; i<boxes.size() && !done; i++) {
            if (active[i]) {
                ObjectDetectionResult boxA = boxes.get(i);
                selected.add(boxA);
                if (selected.size() >= limit) break;

                for (int j=i+1; j<boxes.size(); j++) {
                    if (active[j]) {
                        ObjectDetectionResult boxB = boxes.get(j);
                        if (IOU(boxA.getRect(), boxB.getRect()) > threshold) {
                            active[j] = false;
                            numActive -= 1;
                            if (numActive <= 0) {
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return selected;
    }

    /**
     Computes intersection-over-union overlap between two bounding boxes.
     */
    static float IOU(Rect a, Rect b) {
        float areaA = (a.right - a.left) * (a.bottom - a.top);
        if (areaA <= 0.0) return 0.0f;

        float areaB = (b.right - b.left) * (b.bottom - b.top);
        if (areaB <= 0.0) return 0.0f;

        float intersectionMinX = Math.max(a.left, b.left);
        float intersectionMinY = Math.max(a.top, b.top);
        float intersectionMaxX = Math.min(a.right, b.right);
        float intersectionMaxY = Math.min(a.bottom, b.bottom);
        float intersectionArea = Math.max(intersectionMaxY - intersectionMinY, 0) *
                Math.max(intersectionMaxX - intersectionMinX, 0);
        return intersectionArea / (areaA + areaB - intersectionArea);
    }

    public static ArrayList<ObjectDetectionResult> outputsToNMSPredictions(
        float[] outputs,
        PreprocessedImage preprocessedImage
    ) {
        ArrayList<ObjectDetectionResult> ObjectDetectionResults = new ArrayList<>();
        for (int i = 0; i < mOutputRow; i++) {
            if (outputs[i * mOutputColumn + 4] > mThreshold) {
                float x = outputs[i* mOutputColumn];
                float y = outputs[i* mOutputColumn +1];
                float w = outputs[i* mOutputColumn +2];
                float h = outputs[i* mOutputColumn +3];

                float left = preprocessedImage.getInputImageScaleX() * (x - w/2);
                float top = preprocessedImage.getInputImageScaleY() * (y - h/2);
                float right = preprocessedImage.getInputImageScaleX() * (x + w/2);
                float bottom = preprocessedImage.getInputImageScaleY() * (y + h/2);

                float max = outputs[i* mOutputColumn +5];
                int classIndex = 0;
                for (int j = 0; j < mOutputColumn - 5; j++) {
                    if (outputs[i* mOutputColumn + 5 + j] > max) {
                        max = outputs[i* mOutputColumn + 5 + j];
                        classIndex = j;
                    }
                }

                Rect rect = new Rect(
                    (int)(preprocessedImage.getImageStartX() + preprocessedImage.getImageViewScaleX() * left),
                    (int)(preprocessedImage.getImageStartY() + preprocessedImage.getImageViewScaleY() * top),
                    (int)(preprocessedImage.getImageStartX() + preprocessedImage.getImageViewScaleX() * right),
                    (int)(preprocessedImage.getImageStartY() + preprocessedImage.getImageViewScaleY() * bottom)
                );

                // 각 인덱스마다, 인식 후 인식률과 함께 삽입
                ObjectDetectionResult ObjectDetectionResult =
                        new ObjectDetectionResult(classIndex, outputs[i * mOutputColumn + 4], rect);

                ObjectDetectionResults.add(ObjectDetectionResult);
            }
        }
        return nonMaxSuppression(ObjectDetectionResults, mNmsLimit, mThreshold);
    }
}
