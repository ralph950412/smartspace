package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.uitemplatedata.CarouselTemplateData;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceCardSecondary;
import com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.util.List;
import java.util.Locale;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class CarouselTemplateCard extends BcSmartspaceCardSecondary {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public CarouselTemplateCard(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ConstraintLayout[] constraintLayoutArr = new ConstraintLayout[4];
        for (int i = 0; i < 4; i++) {
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewGroup.inflate(getContext(), R.layout.smartspace_carousel_column_template_card, null);
            constraintLayout.setId(View.generateViewId());
            constraintLayoutArr[i] = constraintLayout;
        }
        int i2 = 0;
        while (i2 < 4) {
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, 0);
            ConstraintLayout constraintLayout2 = constraintLayoutArr[i2];
            ConstraintLayout constraintLayout3 = i2 > 0 ? constraintLayoutArr[i2 - 1] : null;
            ConstraintLayout constraintLayout4 = i2 < 3 ? constraintLayoutArr[i2 + 1] : null;
            if (i2 == 0) {
                layoutParams.startToStart = 0;
                layoutParams.horizontalChainStyle = 1;
            } else {
                layoutParams.startToEnd = constraintLayout3.getId();
            }
            if (i2 == 3) {
                layoutParams.endToEnd = 0;
            } else {
                layoutParams.endToStart = constraintLayout4.getId();
            }
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            addView(constraintLayout2, layoutParams);
            i2++;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            BcSmartspaceTemplateDataUtils.updateVisibility(childAt.findViewById(R.id.upper_text), 8);
            BcSmartspaceTemplateDataUtils.updateVisibility(childAt.findViewById(R.id.icon), 8);
            BcSmartspaceTemplateDataUtils.updateVisibility(childAt.findViewById(R.id.lower_text), 8);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        CarouselTemplateData templateData = smartspaceTarget.getTemplateData();
        if (!BcSmartspaceCardLoggerUtil.containsValidTemplateType(templateData) || templateData.getCarouselItems() == null) {
            Log.w("CarouselTemplateCard", "CarouselTemplateData is null or has no CarouselItem or invalid template type");
            return false;
        }
        List carouselItems = templateData.getCarouselItems();
        int intExact = Math.toIntExact(carouselItems.stream().filter(new CarouselTemplateCard$$ExternalSyntheticLambda0()).count());
        if (intExact < 4) {
            Locale locale = Locale.US;
            int i = 4 - intExact;
            Log.w("CarouselTemplateCard", "Hiding " + i + " incomplete column(s).");
            int i2 = 3 - i;
            int i3 = 0;
            while (i3 < 4) {
                BcSmartspaceTemplateDataUtils.updateVisibility(getChildAt(i3), i3 <= i2 ? 0 : 8);
                i3++;
            }
            ((ConstraintLayout.LayoutParams) ((ConstraintLayout) getChildAt(0)).getLayoutParams()).horizontalChainStyle = i == 0 ? 1 : 0;
        }
        for (int i4 = 0; i4 < intExact; i4++) {
            TextView textView = (TextView) getChildAt(i4).findViewById(R.id.upper_text);
            ImageView imageView = (ImageView) getChildAt(i4).findViewById(R.id.icon);
            TextView textView2 = (TextView) getChildAt(i4).findViewById(R.id.lower_text);
            BcSmartspaceTemplateDataUtils.setText(textView, ((CarouselTemplateData.CarouselItem) carouselItems.get(i4)).getUpperText());
            BcSmartspaceTemplateDataUtils.updateVisibility(textView, 0);
            BcSmartspaceTemplateDataUtils.setIcon(imageView, ((CarouselTemplateData.CarouselItem) carouselItems.get(i4)).getImage());
            BcSmartspaceTemplateDataUtils.updateVisibility(imageView, 0);
            BcSmartspaceTemplateDataUtils.setText(textView2, ((CarouselTemplateData.CarouselItem) carouselItems.get(i4)).getLowerText());
            BcSmartspaceTemplateDataUtils.updateVisibility(textView2, 0);
        }
        if (templateData.getCarouselAction() != null) {
            BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, templateData.getCarouselAction(), smartspaceEventNotifier, "CarouselTemplateCard", bcSmartspaceCardLoggingInfo, 0);
        }
        for (CarouselTemplateData.CarouselItem carouselItem : templateData.getCarouselItems()) {
            if (carouselItem.getTapAction() != null) {
                BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, carouselItem.getTapAction(), smartspaceEventNotifier, "CarouselTemplateCard", bcSmartspaceCardLoggingInfo, 0);
            }
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ((TextView) getChildAt(i2).findViewById(R.id.upper_text)).setTextColor(i);
            ((TextView) getChildAt(i2).findViewById(R.id.lower_text)).setTextColor(i);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public CarouselTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
