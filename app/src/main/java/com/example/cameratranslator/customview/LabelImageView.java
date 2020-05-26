package com.example.cameratranslator.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.callback.ItemClickCallback;
import com.example.cameratranslator.model.BoundingPoly;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duy M. Nguyen on 5/17/2020.
 */
public class LabelImageView extends AppCompatImageView {

    // vẽ line
    private Paint linePaint, selectedPaint, textPaint, selectedTextPaint;
    private int selectedIndex = -1;
    private Bitmap imageBitmap;
    private float ratio = 1f;
    private float bitmapWidth, bitmapHeight;

    private List<LocalizedObjectAnnotation> localizedObjectAnnotations = new ArrayList<>();

    private ItemClickCallback itemClickCallback;

    public LabelImageView(Context context) {
        super(context);
        initPaint();
    }

    public LabelImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        invalidate();
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    private void initPaint() {
        setFocusable(true);
        setFocusableInTouchMode(true);

        // Vẽ line
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(8);

        // Vẽ line của selected object
        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(getResources().getColor(R.color.colorPrimary));
        selectedPaint.setStrokeWidth(12);

        // Vẽ text
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.colorWhite));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(8);
        textPaint.setTextSize(32f);

        // Vẽ text của selected object
        selectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedTextPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        selectedTextPaint.setStyle(Paint.Style.FILL);
        selectedTextPaint.setStrokeWidth(8);
        selectedTextPaint.setTextSize(36f);

    }

    public void draw(List<LocalizedObjectAnnotation> localizedObjectAnnotations) {
        this.localizedObjectAnnotations.clear();
        this.localizedObjectAnnotations = localizedObjectAnnotations;
        invalidate();
    }

    public void clear() {
        this.localizedObjectAnnotations.clear();
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);

        imageBitmap = bm;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (localizedObjectAnnotations != null && localizedObjectAnnotations.size() > 0) {
            for (int i = 0; i < localizedObjectAnnotations.size(); i++) {
                if (i != selectedIndex) {
                    drawLabel(canvas, i, linePaint, textPaint);
                }
            }
            if (selectedIndex >= 0 && selectedIndex < localizedObjectAnnotations.size()) {
                drawLabel(canvas, selectedIndex, selectedPaint, selectedTextPaint);
            }
        }
    }

    private void drawLabel(Canvas canvas, int position, Paint linePaint, Paint textPaint) {
        float offset = 12f;
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        ratio = Math.min((float) getMeasuredWidth() / imageBitmap.getWidth(), (float) getMeasuredHeight() / imageBitmap.getHeight());
        bitmapWidth = imageBitmap.getWidth() * ratio;
        bitmapHeight = imageBitmap.getHeight() * ratio;

        LocalizedObjectAnnotation localizedObjectAnnotation = localizedObjectAnnotations.get(position);
        BoundingPoly boundingPoly = localizedObjectAnnotation.getBoundingPoly();
        List<BoundingPoly.NormalizedVertice> normalizedVertices = boundingPoly.getNormalizedVertices();

        // Caculate actual position to draw on ImageView
        float x1 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(0).getX();
        float y1 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(0).getY();
        float x2 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(1).getX();
        float y2 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(1).getY();
        float x3 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(2).getX();
        float y3 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(2).getY();
        float x4 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(3).getX();
        float y4 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(3).getY();

        // Draw 4 edges of the rectangle
        canvas.drawLine(x1, y1, x2, y2, linePaint);
        canvas.drawLine(x2, y2, x3, y3, linePaint);
        canvas.drawLine(x3, y3, x4, y4, linePaint);
        canvas.drawLine(x4, y4, x1, y1, linePaint);

        // Draw the text
        String label = localizedObjectAnnotation.getTranslation();
        float w = textPaint.measureText(label) / 2f;
        float textsize = textPaint.getTextSize();
        canvas.drawRect(x1, y1 - textsize - offset, x1 + 2 * (w + offset), y1, linePaint);
        canvas.drawText(label, x1 + offset, y1 - offset, textPaint);
    }

    private float triangleArea(float xA, float yA, float xB, float yB, float xC, float yC) {
        float a = (float) Math.sqrt((xB - xA) * (xB - xA) + (yB - yA) * (yB - yA));
        float b = (float) Math.sqrt((xC - xB) * (xC - xB) + (yC - yB) * (yC - yB));
        float c = (float) Math.sqrt((xA - xC) * (xA - xC) + (yA - yC) * (yA - yC));
        float p = (a + b + c) / 2f;
        return (float) Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private float rectangleArea(float xA, float yA, float xB, float yB, float xC, float yC, float xD, float yD) {
        float a = (float) Math.sqrt((xB - xA) * (xB - xA) + (yB - yA) * (yB - yA));
        float b = (float) Math.sqrt((xC - xB) * (xC - xB) + (yC - yB) * (yC - yB));
        float c = (float) Math.sqrt((xD - xC) * (xD - xC) + (yD - yC) * (yD - yC));
        float d = (float) Math.sqrt((xA - xD) * (xA - xD) + (yA - yD) * (yA - yD));
        float p = (a + b + c + d) / 2f;
        return (float) Math.sqrt((p - a) * (p - b) * (p - c) * (p - d));
    }

    private int isInsideAnyRectangle(float x, float y) {
        float eps = 20f;
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        int rectIndex = -1;
        float smallestArea = Float.MAX_VALUE;

        // To check if a point is inside a rectangle, check if the area of 4 triangles is equal to the area of the rectangle
        for (int index = 0; index < localizedObjectAnnotations.size(); index++) {
            LocalizedObjectAnnotation localizedObjectAnnotation = localizedObjectAnnotations.get(index);
            BoundingPoly boundingPoly = localizedObjectAnnotation.getBoundingPoly();
            List<BoundingPoly.NormalizedVertice> normalizedVertices = boundingPoly.getNormalizedVertices();

            float x1 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(0).getX();
            float y1 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(0).getY();
            float x2 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(1).getX();
            float y2 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(1).getY();
            float x3 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(2).getX();
            float y3 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(2).getY();
            float x4 = (width - bitmapWidth) / 2f + bitmapWidth * normalizedVertices.get(3).getX();
            float y4 = (height - bitmapHeight) / 2f + bitmapHeight * normalizedVertices.get(3).getY();

            float s1 = triangleArea(x, y, x1, y1, x2, y2);
            float s2 = triangleArea(x, y, x2, y2, x3, y3);
            float s3 = triangleArea(x, y, x3, y3, x4, y4);
            float s4 = triangleArea(x, y, x4, y4, x1, y1);

            float S = rectangleArea(x1, y1, x2, y2, x3, y3, x4, y4);

            if (Math.abs(s1 + s2 + s3 + s4 - S) <= eps && S < smallestArea) {
                smallestArea = S;
                rectIndex = index;
            }
        }

        return rectIndex;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int index = isInsideAnyRectangle(x, y);
            setSelectedIndex(index);
            if (index != -1 && itemClickCallback != null) {
                itemClickCallback.onItemClick(selectedIndex);
            }
        }

        return true;
    }
}