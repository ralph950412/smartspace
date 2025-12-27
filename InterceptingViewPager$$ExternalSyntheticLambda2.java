package com.google.android.systemui.smartspace;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class InterceptingViewPager$$ExternalSyntheticLambda2 implements Runnable {
    public /* synthetic */ InterceptingViewPager f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        InterceptingViewPager interceptingViewPager = this.f$0;
        interceptingViewPager.mHasPerformedLongPress = true;
        if (interceptingViewPager.performLongClick()) {
            interceptingViewPager.getParent().requestDisallowInterceptTouchEvent(true);
        }
    }
}
