package com.google.android.systemui.smartspace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.wm.shell.R;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class PagerDots extends View {
    public final float activeDotSize;
    public int currentPageIndex;
    public int currentPositionIndex;
    public float currentPositionOffset;
    public final float dotMargin;
    public final float dotRadius;
    public final float dotSize;
    public int numPages;
    public final Paint paint;
    public int primaryColor;
    public final RectF tempRectF;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public PagerDots(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.numPages = -1;
        this.currentPageIndex = -1;
        this.currentPositionIndex = -1;
        float dimension = getResources().getDimension(R.dimen.page_indicator_dot_size);
        this.dotSize = dimension;
        this.dotMargin = getResources().getDimension(R.dimen.page_indicator_dot_margin);
        float f = 2;
        this.activeDotSize = dimension * f;
        this.dotRadius = dimension / f;
        this.tempRectF = new RectF();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        this.primaryColor = color;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.primaryColor);
        this.paint = paint;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float f;
        if (this.numPages < 2) {
            return;
        }
        int save = canvas.save();
        try {
            canvas.scale(isLayoutRtl() ? -1.0f : 1.0f, 1.0f, canvas.getWidth() * 0.5f, 0.0f);
            canvas.translate(getPaddingStart(), getPaddingTop());
            float f2 = this.activeDotSize;
            float f3 = this.dotSize;
            float f4 = this.currentPositionOffset;
            float f5 = (f2 - f3) * f4;
            int i = (this.primaryColor >> 24) & 255;
            int i2 = (int) (i * 0.4f);
            int i3 = (int) ((i - i2) * f4);
            RectF rectF = this.tempRectF;
            rectF.top = 0.0f;
            rectF.bottom = f3;
            rectF.left = 0.0f;
            int i4 = this.numPages;
            int i5 = 0;
            while (i5 < i4) {
                int i6 = this.currentPositionIndex;
                if (i5 == i6) {
                    f = this.activeDotSize - f5;
                } else {
                    int i7 = i6 + 1;
                    float f6 = this.dotSize;
                    f = i5 == i7 ? f6 + f5 : f6;
                }
                int i8 = i5 == i6 ? i - i3 : i5 == i6 + 1 ? i2 + i3 : i2;
                RectF rectF2 = this.tempRectF;
                rectF2.right = rectF2.left + f;
                this.paint.setAlpha(i8);
                RectF rectF3 = this.tempRectF;
                float f7 = this.dotRadius;
                canvas.drawRoundRect(rectF3, f7, f7, this.paint);
                RectF rectF4 = this.tempRectF;
                rectF4.left = rectF4.right + this.dotMargin;
                i5++;
            }
        } finally {
            canvas.restoreToCount(save);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(getPaddingRight() + getPaddingLeft() + (View.MeasureSpec.getMode(i) == 1073741824 ? View.MeasureSpec.getSize(i) : (int) (((this.dotMargin + this.dotSize) * (this.numPages - 1)) + this.activeDotSize)), getPaddingBottom() + getPaddingTop() + (View.MeasureSpec.getMode(i2) == 1073741824 ? View.MeasureSpec.getSize(i2) : (int) this.dotSize));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setNumPages(int i, boolean z) {
        if (i == this.numPages) {
            return;
        }
        if (i <= 0) {
            this.numPages = 1;
        } else {
            this.numPages = i;
        }
        int i2 = this.currentPageIndex;
        if (i2 < 0) {
            updateCurrentPageIndex(z ? this.numPages - 1 : 0);
            this.currentPositionIndex = this.currentPageIndex;
        } else {
            int i3 = this.numPages;
            if (i2 >= i3) {
                updateCurrentPageIndex(z ? 0 : i3 - 1);
                this.currentPositionIndex = this.currentPageIndex;
            }
        }
        if (this.numPages < 2) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this, 8);
        } else if (getVisibility() != 4) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this, 0);
        }
        requestLayout();
        invalidate();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateCurrentPageIndex(int i) {
        if (i == this.currentPageIndex) {
            return;
        }
        this.currentPageIndex = i;
        setContentDescription(getContext().getString(R.string.accessibility_smartspace_page, Integer.valueOf(this.currentPageIndex + 1), Integer.valueOf(this.numPages)));
    }

    public static /* synthetic */ void getNumPages$annotations() {
    }

    public static /* synthetic */ void getPaint$annotations() {
    }

    public PagerDots(Context context) {
        this(context, null);
    }
}
