package com.google.android.systemui.smartspace;

import android.provider.Settings;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BcSmartspaceView f$0;

    public /* synthetic */ BcSmartspaceView$$ExternalSyntheticLambda1(BcSmartspaceView bcSmartspaceView, int i) {
        this.$r8$classId = i;
        this.f$0 = bcSmartspaceView;
    }

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
