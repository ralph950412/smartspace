package com.google.android.systemui.smartspace;

import android.content.IntentFilter;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class IcuDateTextView$$ExternalSyntheticLambda4 implements Runnable {
    public /* synthetic */ IcuDateTextView f$0;
    public /* synthetic */ IntentFilter f$1;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        IcuDateTextView icuDateTextView = this.f$0;
        IntentFilter intentFilter = this.f$1;
        int i = IcuDateTextView.$r8$clinit;
        icuDateTextView.getContext().registerReceiver(icuDateTextView.mIntentReceiver, intentFilter);
    }
}
