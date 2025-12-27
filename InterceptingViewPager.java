package com.google.android.systemui.smartspace;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.viewpager.widget.ViewPager;
import com.android.wm.shell.R;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class InterceptingViewPager extends ViewPager {
    public boolean mHasPerformedLongPress;
    public boolean mHasPostedLongPress;
    public final Runnable mLongPressCallback;
    public final InterceptingViewPager$$ExternalSyntheticLambda0 mSuperOnIntercept;
    public final InterceptingViewPager$$ExternalSyntheticLambda0 mSuperOnTouch;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public InterceptingViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        InterceptingViewPager$$ExternalSyntheticLambda0 interceptingViewPager$$ExternalSyntheticLambda0 = new InterceptingViewPager$$ExternalSyntheticLambda0(0);
        interceptingViewPager$$ExternalSyntheticLambda0.f$0 = this;
        VarHandle.storeStoreFence();
        this.mSuperOnTouch = interceptingViewPager$$ExternalSyntheticLambda0;
        InterceptingViewPager$$ExternalSyntheticLambda0 interceptingViewPager$$ExternalSyntheticLambda02 = new InterceptingViewPager$$ExternalSyntheticLambda0(1);
        interceptingViewPager$$ExternalSyntheticLambda02.f$0 = this;
        VarHandle.storeStoreFence();
        this.mSuperOnIntercept = interceptingViewPager$$ExternalSyntheticLambda02;
        InterceptingViewPager$$ExternalSyntheticLambda2 interceptingViewPager$$ExternalSyntheticLambda2 = new InterceptingViewPager$$ExternalSyntheticLambda2();
        interceptingViewPager$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        this.mLongPressCallback = interceptingViewPager$$ExternalSyntheticLambda2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void cancelScheduledLongPress() {
        if (this.mHasPostedLongPress) {
            this.mHasPostedLongPress = false;
            removeCallbacks(this.mLongPressCallback);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        AccessibilityNodeInfoCompat.wrap(createAccessibilityNodeInfo).setRoleDescription(getContext().getString(R.string.smartspace_role_desc));
        return createAccessibilityNodeInfo;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean handleTouchOverride(MotionEvent motionEvent, InterceptingViewPager$$ExternalSyntheticLambda0 interceptingViewPager$$ExternalSyntheticLambda0) {
        boolean onTouchEvent;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mHasPerformedLongPress = false;
            if (isLongClickable()) {
                cancelScheduledLongPress();
                this.mHasPostedLongPress = true;
                postDelayed(this.mLongPressCallback, ViewConfiguration.getLongPressTimeout());
            }
        } else if (action == 1 || action == 3) {
            cancelScheduledLongPress();
        }
        if (this.mHasPerformedLongPress) {
            cancelScheduledLongPress();
            return true;
        }
        int i = interceptingViewPager$$ExternalSyntheticLambda0.$r8$classId;
        InterceptingViewPager interceptingViewPager = interceptingViewPager$$ExternalSyntheticLambda0.f$0;
        switch (i) {
            case 0:
                onTouchEvent = super.onTouchEvent(motionEvent);
                break;
            default:
                onTouchEvent = super.onInterceptTouchEvent(motionEvent);
                break;
        }
        if (!onTouchEvent) {
            return false;
        }
        cancelScheduledLongPress();
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return handleTouchOverride(motionEvent, this.mSuperOnIntercept);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return handleTouchOverride(motionEvent, this.mSuperOnTouch);
    }

    public InterceptingViewPager(Context context) {
        super(context);
        this.mItems = new ArrayList();
        this.mTempItem = new ViewPager.ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new ViewPager.AnonymousClass3();
        this.mScrollState = 0;
        initViewPager(context);
        InterceptingViewPager$$ExternalSyntheticLambda0 interceptingViewPager$$ExternalSyntheticLambda0 = new InterceptingViewPager$$ExternalSyntheticLambda0(0);
        interceptingViewPager$$ExternalSyntheticLambda0.f$0 = this;
        VarHandle.storeStoreFence();
        this.mSuperOnTouch = interceptingViewPager$$ExternalSyntheticLambda0;
        InterceptingViewPager$$ExternalSyntheticLambda0 interceptingViewPager$$ExternalSyntheticLambda02 = new InterceptingViewPager$$ExternalSyntheticLambda0(1);
        interceptingViewPager$$ExternalSyntheticLambda02.f$0 = this;
        VarHandle.storeStoreFence();
        this.mSuperOnIntercept = interceptingViewPager$$ExternalSyntheticLambda02;
        InterceptingViewPager$$ExternalSyntheticLambda2 interceptingViewPager$$ExternalSyntheticLambda2 = new InterceptingViewPager$$ExternalSyntheticLambda2();
        interceptingViewPager$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        this.mLongPressCallback = interceptingViewPager$$ExternalSyntheticLambda2;
    }
}
