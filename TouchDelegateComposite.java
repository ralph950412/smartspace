package com.google.android.systemui.smartspace;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class TouchDelegateComposite extends TouchDelegate {
    public final ArrayList mDelegates;

    public TouchDelegateComposite(BaseTemplateCard baseTemplateCard) {
        super(new Rect(), baseTemplateCard);
        this.mDelegates = new ArrayList();
    }

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
