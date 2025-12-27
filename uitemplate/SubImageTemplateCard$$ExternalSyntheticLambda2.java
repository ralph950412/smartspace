package com.google.android.systemui.smartspace.uitemplate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import java.util.function.Consumer;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubImageTemplateCard$$ExternalSyntheticLambda2 implements Consumer {
    public /* synthetic */ AnimationDrawable f$0;
    public /* synthetic */ int f$1;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AnimationDrawable animationDrawable = this.f$0;
        int i = this.f$1;
        int i2 = SubImageTemplateCard.$r8$clinit;
        animationDrawable.addFrame((Drawable) obj, i);
    }
}
