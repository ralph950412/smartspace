package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class DateSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public Set mAttachListeners;
    public BcSmartspaceDataPlugin.SmartspaceEventNotifier mEventNotifier;
    public AnonymousClass1 mStateChangeListener;
    public Set mViews;

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        this.mAttachListeners.add(onAttachStateChangeListener);
        Iterator it = ((HashSet) this.mViews).iterator();
        while (it.hasNext()) {
            onAttachStateChangeListener.onViewAttachedToWindow((View) it.next());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getLargeClockView(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_plus_extras_large, viewGroup, false);
        inflate.setId(R.id.date_smartspace_view_large);
        inflate.addOnAttachStateChangeListener(this.mStateChangeListener);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getView(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_plus_extras, viewGroup, false);
        inflate.addOnAttachStateChangeListener(this.mStateChangeListener);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = this.mEventNotifier;
        if (smartspaceEventNotifier != null) {
            smartspaceEventNotifier.notifySmartspaceEvent(smartspaceTargetEvent);
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void registerSmartspaceEventNotifier(BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier) {
        this.mEventNotifier = smartspaceEventNotifier;
    }
}
