package com.google.android.systemui.smartspace;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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
