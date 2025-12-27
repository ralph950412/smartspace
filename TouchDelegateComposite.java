package com.google.android.systemui.smartspace;

import android.view.MotionEvent;
import android.view.TouchDelegate;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class TouchDelegateComposite extends TouchDelegate {
    public ArrayList mDelegates;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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
