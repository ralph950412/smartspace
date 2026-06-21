package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.provider.Settings;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceView f$0;
    public /* synthetic */ ContentResolver f$1;

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BcSmartspaceView bcSmartspaceView = this.f$0;
                ContentResolver contentResolver = this.f$1;
                boolean z = BcSmartspaceView.DEBUG;
                bcSmartspaceView.getClass();
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, bcSmartspaceView.mAodObserver, -1);
                break;
            default:
                BcSmartspaceView bcSmartspaceView2 = this.f$0;
                ContentResolver contentResolver2 = this.f$1;
                boolean z2 = BcSmartspaceView.DEBUG;
                bcSmartspaceView2.getClass();
                contentResolver2.registerContentObserver(Settings.Secure.getUriFor("smartspace_settings_background"), false, bcSmartspaceView2.mBackgroundToggleObserver);
                break;
        }
    }
}
