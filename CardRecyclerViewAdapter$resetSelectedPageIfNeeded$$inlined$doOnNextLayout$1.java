package com.google.android.systemui.smartspace;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class CardRecyclerViewAdapter$resetSelectedPageIfNeeded$$inlined$doOnNextLayout$1 implements View.OnLayoutChangeListener {
    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        view.removeOnLayoutChangeListener(this);
        ((ViewPager2) view).setCurrentItem(0, false);
    }
}
