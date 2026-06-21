package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class WeatherSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public static final boolean DEBUG = Log.isLoggable("WeatherSSDataProvider", 3);
    public final Set mSmartspaceTargetListeners = new HashSet();
    public final List mSmartspaceTargets = new ArrayList();
    public final EventNotifierProxy mEventNotifier = new EventNotifierProxy();

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceEventNotifier getEventNotifier() {
        return this.mEventNotifier;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getLargeClockView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.weather_large, (ViewGroup) null, false);
        inflate.setId(R.id.weather_smartspace_view_large);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getView(Context context) {
        return (BcSmartspaceDataPlugin.SmartspaceView) LayoutInflater.from(context).inflate(R.layout.weather, (ViewGroup) null, false);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void onTargetsAvailable(List list) {
        if (DEBUG) {
            Log.d("WeatherSSDataProvider", this + " onTargetsAvailable called. Callers = " + Debug.getCallers(3));
            StringBuilder sb = new StringBuilder("    targets.size() = ");
            sb.append(list.size());
            Log.d("WeatherSSDataProvider", sb.toString());
            Log.d("WeatherSSDataProvider", "    targets = " + list.toString());
        }
        this.mSmartspaceTargets.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) it.next();
            if (smartspaceTarget.getFeatureType() == 1) {
                this.mSmartspaceTargets.add(smartspaceTarget);
            }
        }
        Set set = this.mSmartspaceTargetListeners;
        WeatherSmartspaceDataProvider$$ExternalSyntheticLambda0 weatherSmartspaceDataProvider$$ExternalSyntheticLambda0 = new WeatherSmartspaceDataProvider$$ExternalSyntheticLambda0();
        weatherSmartspaceDataProvider$$ExternalSyntheticLambda0.f$0 = this;
        VarHandle.storeStoreFence();
        set.forEach(weatherSmartspaceDataProvider$$ExternalSyntheticLambda0);
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
