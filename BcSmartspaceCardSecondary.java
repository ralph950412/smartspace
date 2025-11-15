package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public abstract class BcSmartspaceCardSecondary extends ConstraintLayout {
    public String mPrevSmartspaceTargetId;

    public BcSmartspaceCardSecondary(Context context) {
        super(context);
        this.mPrevSmartspaceTargetId = "";
    }

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
