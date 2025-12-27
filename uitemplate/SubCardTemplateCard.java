package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.SubCardTemplateData;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceCardSecondary;
import com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class SubCardTemplateCard extends BcSmartspaceCardSecondary {
    public ImageView mImageView;
    public TextView mTextView;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public SubCardTemplateCard(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.image_view);
        this.mTextView = (TextView) findViewById(R.id.card_prompt);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mTextView, 8);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        SubCardTemplateData templateData = smartspaceTarget.getTemplateData();
        if (!BcSmartspaceCardLoggerUtil.containsValidTemplateType(templateData)) {
            Log.w("SubCardTemplateCard", "SubCardTemplateData is null or invalid template type");
            return false;
        }
        boolean z2 = true;
        if (templateData.getSubCardIcon() != null) {
            BcSmartspaceTemplateDataUtils.setIcon(this.mImageView, templateData.getSubCardIcon());
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 0);
            z = true;
        } else {
            z = false;
        }
        if (SmartspaceUtils.isEmpty(templateData.getSubCardText())) {
            z2 = z;
        } else {
            BcSmartspaceTemplateDataUtils.setText(this.mTextView, templateData.getSubCardText());
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mTextView, 0);
        }
        if (z2 && templateData.getSubCardAction() != null) {
            BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, templateData.getSubCardAction(), smartspaceEventNotifier, "SubCardTemplateCard", bcSmartspaceCardLoggingInfo, 0);
        }
        return z2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        this.mTextView.setTextColor(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public SubCardTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
