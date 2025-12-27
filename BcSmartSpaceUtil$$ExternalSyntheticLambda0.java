package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.app.smartspace.uitemplatedata.TapAction;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartSpaceUtil$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public /* synthetic */ BcSmartspaceCardLoggingInfo f$0;
    public /* synthetic */ int f$1;
    public /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier f$2;
    public /* synthetic */ String f$3;
    public /* synthetic */ TapAction f$4;
    public /* synthetic */ boolean f$5;
    public /* synthetic */ SmartspaceTarget f$7;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.f$0;
        int i = this.f$1;
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = this.f$2;
        String str = this.f$3;
        TapAction tapAction = this.f$4;
        boolean z = this.f$5;
        SmartspaceTarget smartspaceTarget = this.f$7;
        FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
        if (falsingManager == null || !falsingManager.isFalseTap(1)) {
            if (bcSmartspaceCardLoggingInfo != null) {
                BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = bcSmartspaceCardLoggingInfo.mSubcardInfo;
                if (bcSmartspaceSubcardLoggingInfo != null) {
                    bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex = i;
                }
                BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, bcSmartspaceCardLoggingInfo);
            }
            BcSmartspaceDataPlugin.IntentStarter intentStarter = BcSmartSpaceUtil.getIntentStarter(smartspaceEventNotifier, str);
            if (tapAction != null && (tapAction.getIntent() != null || tapAction.getPendingIntent() != null)) {
                intentStarter.startFromAction(tapAction, view, z);
            }
            if (smartspaceEventNotifier == null) {
                Log.w(str, "Cannot notify target interaction smartspace event: event notifier null.");
            } else {
                smartspaceEventNotifier.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(smartspaceTarget).setSmartspaceActionId(tapAction.getId().toString()).build());
            }
        }
    }
}
