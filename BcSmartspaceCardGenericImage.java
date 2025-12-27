package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCardGenericImage extends BcSmartspaceCardSecondary {
    public ImageView mImageView;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardGenericImage(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.image_view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public void resetUi() {
        this.mImageView.setImageBitmap(null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setImageBitmap(Bitmap bitmap) {
        this.mImageView.setImageBitmap(bitmap);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        if (extras == null || !extras.containsKey("imageBitmap")) {
            return false;
        }
        if (extras.containsKey("imageScaleType")) {
            String string = extras.getString("imageScaleType");
            try {
                this.mImageView.setScaleType(ImageView.ScaleType.valueOf(string));
            } catch (IllegalArgumentException unused) {
                MotionLayout$$ExternalSyntheticOutline0.m("Invalid imageScaleType value: ", string, "SmartspaceGenericImg");
            }
        }
        String dimensionRatio = BcSmartSpaceUtil.getDimensionRatio(extras);
        if (dimensionRatio != null) {
            ((ConstraintLayout.LayoutParams) this.mImageView.getLayoutParams()).dimensionRatio = dimensionRatio;
        }
        if (extras.containsKey("imageLayoutWidth")) {
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mImageView.getLayoutParams())).width = extras.getInt("imageLayoutWidth");
        }
        if (extras.containsKey("imageLayoutHeight")) {
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mImageView.getLayoutParams())).height = extras.getInt("imageLayoutHeight");
        }
        setImageBitmap((Bitmap) extras.get("imageBitmap"));
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BcSmartspaceCardGenericImage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public void setTextColor(int i) {
    }
}
