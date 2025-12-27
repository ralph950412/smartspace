package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartSpaceUtil$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public /* synthetic */ BcSmartspaceCardLoggingInfo f$0;
    public /* synthetic */ int f$1;
    public /* synthetic */ boolean f$2;
    public /* synthetic */ BcSmartspaceDataPlugin.IntentStarter f$3;
    public /* synthetic */ SmartspaceAction f$4;
    public /* synthetic */ boolean f$5;
    public /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier f$7;
    public /* synthetic */ String f$8;
    public /* synthetic */ SmartspaceTarget f$9;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        BcSmartspaceDataPlugin.IntentStarter intentStarter = this.f$3;
        SmartspaceAction smartspaceAction = this.f$4;
        boolean z2 = this.f$5;
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = this.f$7;
        String str = this.f$8;
        SmartspaceTarget smartspaceTarget = this.f$9;
        FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
        if (falsingManager == null || !falsingManager.isFalseTap(1)) {
            if (bcSmartspaceCardLoggingInfo != null) {
                BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = bcSmartspaceCardLoggingInfo.mSubcardInfo;
                if (bcSmartspaceSubcardLoggingInfo != null) {
                    bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex = i;
                }
                BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, bcSmartspaceCardLoggingInfo);
            }
            if (!z) {
                intentStarter.startFromAction(smartspaceAction, view, z2);
            }
            if (smartspaceEventNotifier == null) {
                Log.w(str, "Cannot notify target interaction smartspace event: event notifier null.");
            } else {
                smartspaceEventNotifier.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(smartspaceTarget).setSmartspaceActionId(smartspaceAction.getId()).build());
            }
        }
    }
}
