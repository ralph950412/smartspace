package com.google.android.systemui.smartspace;

import android.content.IntentFilter;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
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
