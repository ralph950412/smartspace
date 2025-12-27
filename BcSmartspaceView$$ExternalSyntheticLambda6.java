package com.google.android.systemui.smartspace;

import androidx.viewpager2.widget.ScrollEventAdapter;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda6 implements Runnable {
    public /* synthetic */ BcSmartspaceView f$0;
    public /* synthetic */ int f$1;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        BcSmartspaceView bcSmartspaceView = this.f$0;
        int i = this.f$1;
        ViewPager2 viewPager2 = bcSmartspaceView.mViewPager2;
        ScrollEventAdapter scrollEventAdapter = viewPager2.mFakeDragger.mScrollEventAdapter;
        viewPager2.setCurrentItemInternal(i, false);
    }
}
