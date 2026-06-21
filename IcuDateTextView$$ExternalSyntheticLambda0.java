package com.google.android.systemui.smartspace;

import android.provider.Settings;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class IcuDateTextView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ IcuDateTextView f$0;

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
