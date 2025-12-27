package com.google.android.systemui.smartspace.uitemplate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.widget.ImageView;
import java.lang.invoke.VarHandle;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubImageTemplateCard$$ExternalSyntheticLambda0 implements Icon.OnDrawableLoadedListener {
    public /* synthetic */ SubImageTemplateCard f$0;
    public /* synthetic */ String f$1;
    public /* synthetic */ String f$2;
    public /* synthetic */ Map f$3;
    public /* synthetic */ int f$4;
    public /* synthetic */ List f$5;
    public /* synthetic */ int f$6;
    public /* synthetic */ WeakReference f$7;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
    public final void onDrawableLoaded(Drawable drawable) {
        SubImageTemplateCard subImageTemplateCard = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        Map map = this.f$3;
        int i = this.f$4;
        List list = this.f$5;
        int i2 = this.f$6;
        WeakReference weakReference = this.f$7;
        int i3 = SubImageTemplateCard.$r8$clinit;
        if (!str.equals(subImageTemplateCard.mPrevSmartspaceTargetId)) {
            Log.d("SubImageTemplateCard", "SmartspaceTarget has changed. Skip the loaded result...");
            return;
        }
        subImageTemplateCard.mIconDrawableCache.put(str2, drawable);
        map.put(Integer.valueOf(i), drawable);
        if (map.size() == list.size()) {
            AnimationDrawable animationDrawable = new AnimationDrawable();
            List list2 = (List) map.values().stream().filter(new SubImageTemplateCard$$ExternalSyntheticLambda1()).collect(Collectors.toList());
            if (list2.isEmpty()) {
                Log.w("SubImageTemplateCard", "All images are failed to load. Reset imageView");
                ImageView imageView = subImageTemplateCard.mImageView;
                if (imageView == null) {
                    return;
                }
                imageView.getLayoutParams().width = -2;
                subImageTemplateCard.mImageView.setImageDrawable(null);
                subImageTemplateCard.mImageView.setBackgroundTintList(null);
                return;
            }
            SubImageTemplateCard$$ExternalSyntheticLambda2 subImageTemplateCard$$ExternalSyntheticLambda2 = new SubImageTemplateCard$$ExternalSyntheticLambda2();
            subImageTemplateCard$$ExternalSyntheticLambda2.f$0 = animationDrawable;
            subImageTemplateCard$$ExternalSyntheticLambda2.f$1 = i2;
            VarHandle.storeStoreFence();
            list2.forEach(subImageTemplateCard$$ExternalSyntheticLambda2);
            ImageView imageView2 = (ImageView) weakReference.get();
            imageView2.setImageDrawable(animationDrawable);
            int intrinsicWidth = animationDrawable.getIntrinsicWidth();
            if (imageView2.getLayoutParams().width != intrinsicWidth) {
                Log.d("SubImageTemplateCard", "imageView requestLayout");
                imageView2.getLayoutParams().width = intrinsicWidth;
                imageView2.requestLayout();
            }
            animationDrawable.start();
        }
    }
}
