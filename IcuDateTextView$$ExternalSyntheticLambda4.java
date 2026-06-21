package com.google.android.systemui.smartspace;

import android.content.IntentFilter;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class IcuDateTextView$$ExternalSyntheticLambda4 implements Runnable {
    public /* synthetic */ IcuDateTextView f$0;
    public /* synthetic */ IntentFilter f$1;

    @Override // java.lang.Runnable
    public final void run() {
        IcuDateTextView icuDateTextView = this.f$0;
        IntentFilter intentFilter = this.f$1;
        int i = IcuDateTextView.$r8$clinit;
        icuDateTextView.getContext().registerReceiver(icuDateTextView.mIntentReceiver, intentFilter);
    }
}
