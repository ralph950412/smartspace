package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class WeatherSmartspaceDataProvider implements BcSmartspaceDataPlugin {
    public static final boolean DEBUG = Log.isLoggable("WeatherSSDataProvider", 3);
    public final Set mSmartspaceTargetListeners = new HashSet();
    public final List mSmartspaceTargets = new ArrayList();
    public final EventNotifierProxy mEventNotifier = new EventNotifierProxy();

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceEventNotifier getEventNotifier() {
        return this.mEventNotifier;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getLargeClockView(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_large, viewGroup, false);
        inflate.setId(R.id.weather_smartspace_view_large);
        return (BcSmartspaceDataPlugin.SmartspaceView) inflate;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final BcSmartspaceDataPlugin.SmartspaceView getView(ViewGroup viewGroup) {
        return (BcSmartspaceDataPlugin.SmartspaceView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather, viewGroup, false);
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
        this.mSmartspaceTargetListeners.forEach(new Consumer() { // from class: com.google.android.systemui.smartspace.WeatherSmartspaceDataProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BcSmartspaceDataPlugin.SmartspaceTargetListener) obj).onSmartspaceTargetsUpdated(WeatherSmartspaceDataProvider.this.mSmartspaceTargets);
            }
        });
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
