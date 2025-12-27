package com.google.android.systemui.smartspace;

import android.provider.Settings;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class WeatherSmartspaceView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ WeatherSmartspaceView f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WeatherSmartspaceView weatherSmartspaceView = this.f$0;
        switch (i) {
            case 0:
                boolean z = WeatherSmartspaceView.DEBUG;
                weatherSmartspaceView.getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, weatherSmartspaceView.mAodSettingsObserver, -1);
                break;
            default:
                boolean z2 = WeatherSmartspaceView.DEBUG;
                weatherSmartspaceView.getContext().getContentResolver().unregisterContentObserver(weatherSmartspaceView.mAodSettingsObserver);
                break;
        }
    }
}
