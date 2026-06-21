package com.google.android.systemui.smartspace.uitemplate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import java.util.function.Consumer;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubImageTemplateCard$$ExternalSyntheticLambda2 implements Consumer {
    public /* synthetic */ AnimationDrawable f$0;
    public /* synthetic */ int f$1;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AnimationDrawable animationDrawable = this.f$0;
        int i = this.f$1;
        int i2 = SubImageTemplateCard.$r8$clinit;
        animationDrawable.addFrame((Drawable) obj, i);
    }
}
