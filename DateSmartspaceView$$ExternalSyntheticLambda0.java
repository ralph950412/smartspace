package com.google.android.systemui.smartspace;

import android.provider.Settings;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class DateSmartspaceView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ DateSmartspaceView f$0;

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
