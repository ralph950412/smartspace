package com.google.android.systemui.smartspace;

import android.provider.Settings;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class DateSmartspaceView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ DateSmartspaceView f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DateSmartspaceView dateSmartspaceView = this.f$0;
        switch (i) {
            case 0:
                boolean z = DateSmartspaceView.DEBUG;
                dateSmartspaceView.getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, dateSmartspaceView.mAodSettingsObserver, -1);
                break;
            default:
                boolean z2 = DateSmartspaceView.DEBUG;
                dateSmartspaceView.getContext().getContentResolver().unregisterContentObserver(dateSmartspaceView.mAodSettingsObserver);
                break;
        }
    }
}
