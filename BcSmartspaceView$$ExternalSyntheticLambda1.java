package com.google.android.systemui.smartspace;

import android.view.View;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda1 implements Runnable {
    public /* synthetic */ BcSmartspaceView f$0;
    public /* synthetic */ boolean f$1;
    public /* synthetic */ int f$2;
    public /* synthetic */ View f$3;
    public /* synthetic */ int f$4;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        BcSmartspaceView bcSmartspaceView = this.f$0;
        boolean z = this.f$1;
        int i = this.f$2;
        View view = this.f$3;
        int i2 = this.f$4;
        boolean z2 = BcSmartspaceView.DEBUG;
        bcSmartspaceView.setTargets(z, i, view, i2);
    }
}
