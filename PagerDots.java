package com.google.android.systemui.smartspace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.wm.shell.R;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
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

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.numPages < 2) {
            return;
        }
        float paddingLeft = getPaddingLeft();
        float paddingTop = getPaddingTop();
        int save = canvas.save();
        canvas.translate(paddingLeft, paddingTop);
        try {
            float f = this.activeDotSize;
            float f2 = this.dotSize;
            float f3 = this.currentPositionOffset;
            float f4 = (f - f2) * f3;
            int i = (this.primaryColor >> 24) & 255;
            int i2 = (int) (i * 0.4f);
            int i3 = (int) ((i - i2) * f3);
            RectF rectF = this.tempRectF;
            rectF.top = 0.0f;
            rectF.bottom = f2;
            rectF.left = 0.0f;
            int i4 = this.numPages;
            int i5 = 0;
            while (i5 < i4) {
                int i6 = this.currentPositionIndex;
                float f5 = i5 == i6 ? this.activeDotSize - f4 : i5 == i6 + 1 ? this.dotSize + f4 : this.dotSize;
                int i7 = i5 == i6 ? i - i3 : i5 == i6 + 1 ? i2 + i3 : i2;
                RectF rectF2 = this.tempRectF;
                rectF2.right = rectF2.left + f5;
                this.paint.setAlpha(i7);
                RectF rectF3 = this.tempRectF;
                float f6 = this.dotRadius;
                canvas.drawRoundRect(rectF3, f6, f6, this.paint);
                RectF rectF4 = this.tempRectF;
                rectF4.left = rectF4.right + this.dotMargin;
                i5++;
            }
            canvas.restoreToCount(save);
        } catch (Throwable th) {
            canvas.restoreToCount(save);
            throw th;
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(getPaddingRight() + getPaddingLeft() + (View.MeasureSpec.getMode(i) == 1073741824 ? View.MeasureSpec.getSize(i) : (int) (((this.dotMargin + this.dotSize) * (this.numPages - 1)) + this.activeDotSize)), getPaddingBottom() + getPaddingTop() + (View.MeasureSpec.getMode(i2) == 1073741824 ? View.MeasureSpec.getSize(i2) : (int) this.dotSize));
    }

    public final void setNumPages(int i, boolean z) {
        if (i == this.numPages) {
            return;
        }
        if (i <= 0) {
            Log.w("SsPagerDots", "Total number of pages invalid: " + i + ". Assuming 1 page.");
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

    public final void setPageOffset(float f, int i) {
        if (i < 0 || i >= this.numPages) {
            return;
        }
        this.currentPositionIndex = i;
        this.currentPositionOffset = f;
        invalidate();
        if (f >= 0.5d) {
            i++;
        }
        updateCurrentPageIndex(i);
    }

    public final void updateCurrentPageIndex(int i) {
        if (i == this.currentPageIndex) {
            return;
        }
        this.currentPageIndex = i;
        setContentDescription(getContext().getString(R.string.accessibility_smartspace_page, Integer.valueOf(this.currentPageIndex + 1), Integer.valueOf(this.numPages)));
    }

    public PagerDots(Context context) {
        this(context, null);
    }

    public static /* synthetic */ void getNumPages$annotations() {
    }

    public static /* synthetic */ void getPaint$annotations() {
    }
}
