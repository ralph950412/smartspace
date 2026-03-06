package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceView f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.systemui.smartspace.BcSmartspaceView.<init>(android.content.Context, android.util.AttributeSet):void, com.google.android.systemui.smartspace.BcSmartspaceView.onDetachedFromWindow():void] */
    public /* synthetic */ BcSmartspaceView$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BcSmartspaceView bcSmartspaceView = this.f$0;
        switch (i) {
            case 0:
                boolean z = BcSmartspaceView.DEBUG;
                ContentResolver contentResolver = bcSmartspaceView.getContext().getContentResolver();
                contentResolver.unregisterContentObserver(bcSmartspaceView.mAodObserver);
                contentResolver.unregisterContentObserver(bcSmartspaceView.mBackgroundToggleObserver);
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
