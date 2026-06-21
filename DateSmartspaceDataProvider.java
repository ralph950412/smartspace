package com.google.android.systemui.smartspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class DateSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public Set mAttachListeners;
    public EventNotifierProxy mEventNotifier;
    public AnonymousClass1 mStateChangeListener;
    public Set mViews;

    /* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
    /* renamed from: com.google.android.systemui.smartspace.DateSmartspaceDataProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnAttachStateChangeListener {
        public /* synthetic */ DateSmartspaceDataProvider this$0;

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            this.this$0.mViews.add(view);
            Iterator it = ((HashSet) this.this$0.mAttachListeners).iterator();
            while (it.hasNext()) {
                ((View.OnAttachStateChangeListener) it.next()).onViewAttachedToWindow(view);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            this.this$0.mViews.remove(view);
            Iterator it = ((HashSet) this.this$0.mAttachListeners).iterator();
            while (it.hasNext()) {
                ((View.OnAttachStateChangeListener) it.next()).onViewDetachedFromWindow(view);
            }
        }
    }

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

    /* JADX DEBUG: Multi-variable search result rejected for r4v2, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getLargeClockView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.date_plus_extras_large, (ViewGroup) null, false);
        inflate.setId(R.id.date_smartspace_view_large);
        inflate.addOnAttachStateChangeListener(this.mStateChangeListener);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v2, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.date_plus_extras, (ViewGroup) null, false);
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
