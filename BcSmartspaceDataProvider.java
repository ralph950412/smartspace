package com.google.android.systemui.smartspace;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.lang.invoke.VarHandle;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class BcSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public static final boolean DEBUG = Log.isLoggable(BcSmartspaceDataPlugin.TAG, 3);
    public final AnonymousClass1 mStateChangeListener;
    public final Set mSmartspaceTargetListeners = new CopyOnWriteArraySet();
    public List mSmartspaceTargets = Collections.EMPTY_LIST;
    public final Set mViews = new HashSet();
    public final Set mAttachListeners = new HashSet();
    public final EventNotifierProxy mEventNotifier = new EventNotifierProxy();

    /* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceDataProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnAttachStateChangeListener {
        public /* synthetic */ BcSmartspaceDataProvider this$0;

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

    public BcSmartspaceDataProvider() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        anonymousClass1.this$0 = this;
        VarHandle.storeStoreFence();
        this.mStateChangeListener = anonymousClass1;
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
    public final BcSmartspaceDataPlugin.SmartspaceView getView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.smartspace_enhanced2, (ViewGroup) null, false);
        inflate.addOnAttachStateChangeListener(this.mStateChangeListener);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void onTargetsAvailable(List list) {
        if (DEBUG) {
            Log.d(BcSmartspaceDataPlugin.TAG, this + " onTargetsAvailable called. Callers = " + Debug.getCallers(3));
            StringBuilder sb = new StringBuilder("    targets.size() = ");
            sb.append(list.size());
            Log.d(BcSmartspaceDataPlugin.TAG, sb.toString());
            Log.d(BcSmartspaceDataPlugin.TAG, "    targets = " + list.toString());
        }
        this.mSmartspaceTargets = list.stream().filter(new BcSmartspaceDataProvider$$ExternalSyntheticLambda0()).toList();
        Set set = this.mSmartspaceTargetListeners;
        BcSmartspaceDataProvider$$ExternalSyntheticLambda1 bcSmartspaceDataProvider$$ExternalSyntheticLambda1 = new BcSmartspaceDataProvider$$ExternalSyntheticLambda1();
        bcSmartspaceDataProvider$$ExternalSyntheticLambda1.f$0 = this;
        VarHandle.storeStoreFence();
        ((CopyOnWriteArraySet) set).forEach(bcSmartspaceDataProvider$$ExternalSyntheticLambda1);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void registerListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        this.mSmartspaceTargetListeners.add(smartspaceTargetListener);
        smartspaceTargetListener.onSmartspaceTargetsUpdated(this.mSmartspaceTargets);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void setEventDispatcher(BcSmartspaceDataPlugin.SmartspaceEventDispatcher smartspaceEventDispatcher) {
        this.mEventNotifier.eventDispatcher = smartspaceEventDispatcher;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void setIntentStarter(BcSmartspaceDataPlugin.IntentStarter intentStarter) {
        this.mEventNotifier.intentStarterRef = intentStarter;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void unregisterListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        this.mSmartspaceTargetListeners.remove(smartspaceTargetListener);
    }
}
