package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public class BcSmartspaceCardFlight extends BcSmartspaceCardSecondary {
    public ImageView mQrCodeView;

    public BcSmartspaceCardFlight(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQrCodeView = (ImageView) findViewById(R.id.flight_qr_code);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mQrCodeView, 8);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        if (extras == null || !extras.containsKey("qrCodeBitmap")) {
            return false;
        }
        Bitmap bitmap = (Bitmap) extras.get("qrCodeBitmap");
        ImageView imageView = this.mQrCodeView;
        if (imageView == null) {
            Log.w("BcSmartspaceCardFlight", "No flight QR code view to update");
        } else {
            imageView.setImageBitmap(bitmap);
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mQrCodeView, 0);
        return true;
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
    }

    public BcSmartspaceCardFlight(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
