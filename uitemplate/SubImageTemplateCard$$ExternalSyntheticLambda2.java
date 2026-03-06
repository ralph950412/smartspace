package com.google.android.systemui.smartspace.uitemplate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import java.util.function.Consumer;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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
