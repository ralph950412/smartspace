package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.util.List;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public class BcSmartspaceCardCombinationAtStore extends BcSmartspaceCardCombination {
    public BcSmartspaceCardCombinationAtStore(Context context) {
        super(context);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardCombination, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction smartspaceAction;
        List actionChips = smartspaceTarget.getActionChips();
        if (actionChips == null || actionChips.isEmpty() || (smartspaceAction = (SmartspaceAction) actionChips.get(0)) == null) {
            return false;
        }
        ConstraintLayout constraintLayout = this.mFirstSubCard;
        boolean z = (constraintLayout instanceof BcSmartspaceCardShoppingList) && ((BcSmartspaceCardShoppingList) constraintLayout).setSmartspaceActions(smartspaceTarget, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo);
        ConstraintLayout constraintLayout2 = this.mSecondSubCard;
        boolean z2 = constraintLayout2 != null && fillSubCard(constraintLayout2, smartspaceTarget, smartspaceAction, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo);
        if (z) {
            this.mFirstSubCard.setBackgroundResource(R.drawable.bg_smartspace_combination_sub_card);
        }
        return z && z2;
    }

    public BcSmartspaceCardCombinationAtStore(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
