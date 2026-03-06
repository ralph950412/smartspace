package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.provider.Settings;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceView f$0;
    public /* synthetic */ ContentResolver f$1;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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
