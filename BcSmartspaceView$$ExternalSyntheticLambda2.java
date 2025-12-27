package com.google.android.systemui.smartspace;

import android.provider.Settings;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceView f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BcSmartspaceView bcSmartspaceView = this.f$0;
        switch (i) {
            case 0:
                boolean z = BcSmartspaceView.DEBUG;
                bcSmartspaceView.getContext().getContentResolver().unregisterContentObserver(bcSmartspaceView.mAodObserver);
                break;
            case 1:
                boolean z2 = BcSmartspaceView.DEBUG;
                bcSmartspaceView.getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, bcSmartspaceView.mAodObserver, -1);
                break;
            default:
                ViewPager2 viewPager2 = bcSmartspaceView.mViewPager2;
                if (viewPager2 != null) {
                    bcSmartspaceView.mHasPerformedLongPress = true;
                    if (viewPager2.performLongClick()) {
                        bcSmartspaceView.mViewPager2.setPressed(false);
                        bcSmartspaceView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    }
                }
                break;
        }
    }
}
