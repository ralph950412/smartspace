package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.uitemplatedata.HeadToHeadTemplateData;
import android.app.smartspace.uitemplatedata.Icon;
import android.app.smartspace.uitemplatedata.Text;
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
public class HeadToHeadTemplateCard extends BcSmartspaceCardSecondary {
    public ImageView mFirstCompetitorIcon;
    public TextView mFirstCompetitorText;
    public TextView mHeadToHeadTitle;
    public ImageView mSecondCompetitorIcon;
    public TextView mSecondCompetitorText;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public HeadToHeadTemplateCard(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mHeadToHeadTitle = (TextView) findViewById(R.id.head_to_head_title);
        this.mFirstCompetitorText = (TextView) findViewById(R.id.first_competitor_text);
        this.mSecondCompetitorText = (TextView) findViewById(R.id.second_competitor_text);
        this.mFirstCompetitorIcon = (ImageView) findViewById(R.id.first_competitor_icon);
        this.mSecondCompetitorIcon = (ImageView) findViewById(R.id.second_competitor_icon);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mHeadToHeadTitle, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mFirstCompetitorText, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondCompetitorText, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mFirstCompetitorIcon, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondCompetitorIcon, 8);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ab, code lost:
    
        if (r1 != false) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009e  */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        HeadToHeadTemplateData templateData = smartspaceTarget.getTemplateData();
        boolean z2 = false;
        if (!BcSmartspaceCardLoggerUtil.containsValidTemplateType(templateData)) {
            Log.w("HeadToHeadTemplateCard", "HeadToHeadTemplateData is null or invalid template type");
            return false;
        }
        if (templateData.getHeadToHeadTitle() != null) {
            Text headToHeadTitle = templateData.getHeadToHeadTitle();
            TextView textView = this.mHeadToHeadTitle;
            if (textView != null) {
                BcSmartspaceTemplateDataUtils.setText(textView, headToHeadTitle);
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mHeadToHeadTitle, 0);
                z = true;
                if (templateData.getHeadToHeadFirstCompetitorText() != null) {
                    Text headToHeadFirstCompetitorText = templateData.getHeadToHeadFirstCompetitorText();
                    TextView textView2 = this.mFirstCompetitorText;
                    if (textView2 == null) {
                        Log.w("HeadToHeadTemplateCard", "No first competitor text view to update");
                        if (!z) {
                            z = false;
                        }
                    } else {
                        BcSmartspaceTemplateDataUtils.setText(textView2, headToHeadFirstCompetitorText);
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.mFirstCompetitorText, 0);
                    }
                    z = true;
                }
                if (templateData.getHeadToHeadSecondCompetitorText() != null) {
                    Text headToHeadSecondCompetitorText = templateData.getHeadToHeadSecondCompetitorText();
                    TextView textView3 = this.mSecondCompetitorText;
                    if (textView3 == null) {
                        Log.w("HeadToHeadTemplateCard", "No second competitor text view to update");
                        if (!z) {
                            z = false;
                        }
                    } else {
                        BcSmartspaceTemplateDataUtils.setText(textView3, headToHeadSecondCompetitorText);
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondCompetitorText, 0);
                    }
                    z = true;
                }
                if (templateData.getHeadToHeadFirstCompetitorIcon() != null) {
                    Icon headToHeadFirstCompetitorIcon = templateData.getHeadToHeadFirstCompetitorIcon();
                    ImageView imageView = this.mFirstCompetitorIcon;
                    if (imageView == null) {
                        Log.w("HeadToHeadTemplateCard", "No first competitor icon view to update");
                        if (!z) {
                            z = false;
                        }
                    } else {
                        BcSmartspaceTemplateDataUtils.setIcon(imageView, headToHeadFirstCompetitorIcon);
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.mFirstCompetitorIcon, 0);
                    }
                    z = true;
                }
                if (templateData.getHeadToHeadSecondCompetitorIcon() != null) {
                    Icon headToHeadSecondCompetitorIcon = templateData.getHeadToHeadSecondCompetitorIcon();
                    ImageView imageView2 = this.mSecondCompetitorIcon;
                    if (imageView2 == null) {
                        Log.w("HeadToHeadTemplateCard", "No second competitor icon view to update");
                    } else {
                        BcSmartspaceTemplateDataUtils.setIcon(imageView2, headToHeadSecondCompetitorIcon);
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondCompetitorIcon, 0);
                    }
                    z2 = true;
                    z = z2;
                }
                if (z && templateData.getHeadToHeadAction() != null) {
                    BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, templateData.getHeadToHeadAction(), smartspaceEventNotifier, "HeadToHeadTemplateCard", bcSmartspaceCardLoggingInfo, 0);
                }
                return z;
            }
            Log.w("HeadToHeadTemplateCard", "No head-to-head title view to update");
        }
        z = false;
        if (templateData.getHeadToHeadFirstCompetitorText() != null) {
        }
        if (templateData.getHeadToHeadSecondCompetitorText() != null) {
        }
        if (templateData.getHeadToHeadFirstCompetitorIcon() != null) {
        }
        if (templateData.getHeadToHeadSecondCompetitorIcon() != null) {
        }
        if (z) {
            BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, templateData.getHeadToHeadAction(), smartspaceEventNotifier, "HeadToHeadTemplateCard", bcSmartspaceCardLoggingInfo, 0);
        }
        return z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        this.mFirstCompetitorText.setTextColor(i);
        this.mSecondCompetitorText.setTextColor(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public HeadToHeadTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
