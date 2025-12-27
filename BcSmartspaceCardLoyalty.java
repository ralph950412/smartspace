package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCardLoyalty extends BcSmartspaceCardGenericImage {
    public TextView mCardPromptView;
    public ImageView mLoyaltyProgramLogoView;
    public TextView mLoyaltyProgramNameView;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardLoyalty(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mLoyaltyProgramLogoView = (ImageView) findViewById(R.id.loyalty_program_logo);
        this.mLoyaltyProgramNameView = (TextView) findViewById(R.id.loyalty_program_name);
        this.mCardPromptView = (TextView) findViewById(R.id.card_prompt);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        super.resetUi();
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoyaltyProgramLogoView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoyaltyProgramNameView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptView, 8);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage
    public final void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.mLoyaltyProgramLogoView.setImageBitmap(bitmap);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        super.setSmartspaceActions(smartspaceTarget, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo);
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        if (extras == null) {
            return false;
        }
        boolean containsKey = extras.containsKey("imageBitmap");
        if (extras.containsKey("cardPrompt")) {
            String string = extras.getString("cardPrompt");
            TextView textView = this.mCardPromptView;
            if (textView == null) {
                Log.w("BcSmartspaceCardLoyalty", "No card prompt view to update");
            } else {
                textView.setText(string);
            }
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptView, 0);
            if (containsKey) {
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 0);
            }
            return true;
        }
        if (!extras.containsKey("loyaltyProgramName")) {
            if (containsKey) {
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoyaltyProgramLogoView, 0);
            }
            return containsKey;
        }
        String string2 = extras.getString("loyaltyProgramName");
        TextView textView2 = this.mLoyaltyProgramNameView;
        if (textView2 == null) {
            Log.w("BcSmartspaceCardLoyalty", "No loyalty program name view to update");
        } else {
            textView2.setText(string2);
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoyaltyProgramNameView, 0);
        if (containsKey) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoyaltyProgramLogoView, 0);
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        this.mLoyaltyProgramNameView.setTextColor(i);
        this.mCardPromptView.setTextColor(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BcSmartspaceCardLoyalty(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
