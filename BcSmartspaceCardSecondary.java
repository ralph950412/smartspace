package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class BcSmartspaceCardSecondary extends ConstraintLayout {
    public String mPrevSmartspaceTargetId;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardSecondary(Context context) {
        super(context);
        this.mPrevSmartspaceTargetId = "";
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void reset(String str) {
        if (this.mPrevSmartspaceTargetId.equals(str)) {
            return;
        }
        this.mPrevSmartspaceTargetId = str;
        resetUi();
    }

    public abstract boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo);

    public abstract void setTextColor(int i);

    public BcSmartspaceCardSecondary(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPrevSmartspaceTargetId = "";
    }

    public void resetUi() {
    }
}
