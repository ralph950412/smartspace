package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.Collection;
import java.util.stream.Collectors;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda1 implements Runnable {
    public /* synthetic */ BcSmartspaceView f$0;
    public /* synthetic */ boolean f$1;
    public /* synthetic */ View f$3;
    public /* synthetic */ Runnable f$5;

    @Override // java.lang.Runnable
    public final void run() {
        BcSmartspaceView bcSmartspaceView = this.f$0;
        boolean z = this.f$1;
        Runnable runnable = this.f$5;
        int size = bcSmartspaceView.mAdapter.smartspaceTargets.size();
        PagerDots pagerDots = bcSmartspaceView.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setNumPages(size, z);
        }
        for (int i = 0; i < size; i++) {
            SmartspaceTarget targetAtPosition = bcSmartspaceView.mAdapter.getTargetAtPosition(i);
            if (!bcSmartspaceView.mLastReceivedTargets.contains(targetAtPosition.getSmartspaceTargetId())) {
                bcSmartspaceView.logSmartspaceEvent(targetAtPosition, i, BcSmartspaceEvent.SMARTSPACE_CARD_RECEIVED);
                SmartspaceTargetEvent.Builder builder = new SmartspaceTargetEvent.Builder(8);
                builder.setSmartspaceTarget(targetAtPosition);
                SmartspaceAction baseAction = targetAtPosition.getBaseAction();
                if (baseAction != null) {
                    builder.setSmartspaceActionId(baseAction.getId());
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = bcSmartspaceView.mDataProvider;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.getEventNotifier().notifySmartspaceEvent(builder.build());
                }
            }
        }
        bcSmartspaceView.mLastReceivedTargets.clear();
        bcSmartspaceView.mLastReceivedTargets.addAll((Collection) bcSmartspaceView.mAdapter.smartspaceTargets.stream().map(new BcSmartspaceView$$ExternalSyntheticLambda7()).collect(Collectors.toList()));
        if (runnable != null) {
            runnable.run();
        }
    }
}
