package com.google.android.systemui.smartspace;

import android.content.ContentResolver;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceView f$0;

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
                bcSmartspaceView.mHasPerformedLongPress = true;
                if (bcSmartspaceView.mViewPager2.performLongClick()) {
                    bcSmartspaceView.mViewPager2.setPressed(false);
                    bcSmartspaceView.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
    }
}
