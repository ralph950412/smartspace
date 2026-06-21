package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
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

    public void resetUi() {
    }

    public abstract boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo);

    public abstract void setTextColor(int i);

    public BcSmartspaceCardSecondary(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPrevSmartspaceTargetId = "";
    }
}
