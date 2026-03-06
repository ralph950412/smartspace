package com.google.android.systemui.smartspace;

import android.provider.Settings;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class IcuDateTextView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ IcuDateTextView f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        IcuDateTextView icuDateTextView = this.f$0;
        switch (i) {
            case 0:
                int i2 = IcuDateTextView.$r8$clinit;
                try {
                    icuDateTextView.getContext().unregisterReceiver(icuDateTextView.mIntentReceiver);
                    break;
                } catch (IllegalArgumentException unused) {
                    return;
                }
            case 1:
                int i3 = IcuDateTextView.$r8$clinit;
                icuDateTextView.getContext().getContentResolver().unregisterContentObserver(icuDateTextView.mAodSettingsObserver);
                break;
            case 2:
                int i4 = IcuDateTextView.$r8$clinit;
                icuDateTextView.onTimeChanged(false);
                break;
            default:
                int i5 = IcuDateTextView.$r8$clinit;
                icuDateTextView.getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, icuDateTextView.mAodSettingsObserver, -1);
                break;
        }
    }
}
