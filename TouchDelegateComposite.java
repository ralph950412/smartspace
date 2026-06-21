package com.google.android.systemui.smartspace;

import android.view.MotionEvent;
import android.view.TouchDelegate;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class TouchDelegateComposite extends TouchDelegate {
    public ArrayList mDelegates;

    @Override // android.view.TouchDelegate
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Iterator it = this.mDelegates.iterator();
        while (it.hasNext()) {
            TouchDelegate touchDelegate = (TouchDelegate) it.next();
            motionEvent.setLocation(x, y);
            if (touchDelegate.onTouchEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }
}
