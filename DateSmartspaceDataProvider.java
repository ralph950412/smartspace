package com.google.android.systemui.smartspace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class DateSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public final Set mViews = new HashSet();
    public final Set mAttachListeners = new HashSet();
    public final EventNotifierProxy mEventNotifier = new EventNotifierProxy();
    public final AnonymousClass1 mStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.google.android.systemui.smartspace.DateSmartspaceDataProvider.1
        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            DateSmartspaceDataProvider.this.mViews.add(view);
            Iterator it = ((HashSet) DateSmartspaceDataProvider.this.mAttachListeners).iterator();
            while (it.hasNext()) {
                ((View.OnAttachStateChangeListener) it.next()).onViewAttachedToWindow(view);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            DateSmartspaceDataProvider.this.mViews.remove(view);
            Iterator it = ((HashSet) DateSmartspaceDataProvider.this.mAttachListeners).iterator();
            while (it.hasNext()) {
                ((View.OnAttachStateChangeListener) it.next()).onViewDetachedFromWindow(view);
            }
        }
    };

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        this.mAttachListeners.add(onAttachStateChangeListener);
        Iterator it = ((HashSet) this.mViews).iterator();
        while (it.hasNext()) {
            onAttachStateChangeListener.onViewAttachedToWindow((View) it.next());
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceEventNotifier getEventNotifier() {
        return this.mEventNotifier;
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
    public final void setEventDispatcher(BcSmartspaceDataPlugin.SmartspaceEventDispatcher smartspaceEventDispatcher) {
        this.mEventNotifier.eventDispatcher = smartspaceEventDispatcher;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void setIntentStarter(BcSmartspaceDataPlugin.IntentStarter intentStarter) {
        this.mEventNotifier.intentStarterRef = intentStarter;
    }
}
