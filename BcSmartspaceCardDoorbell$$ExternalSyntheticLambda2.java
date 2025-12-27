package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell;
import java.lang.invoke.VarHandle;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ BcSmartspaceCardDoorbell f$0;
    public /* synthetic */ ContentResolver f$1;
    public /* synthetic */ int f$2;
    public /* synthetic */ float f$3;
    public /* synthetic */ WeakReference f$4;
    public /* synthetic */ WeakReference f$5;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    public /* synthetic */ BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BcSmartspaceCardDoorbell bcSmartspaceCardDoorbell = this.f$0;
                ContentResolver contentResolver = this.f$1;
                int i = this.f$2;
                float f = this.f$3;
                WeakReference weakReference = this.f$4;
                WeakReference weakReference2 = this.f$5;
                Map map = bcSmartspaceCardDoorbell.mUriToDrawable;
                BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 = new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(1);
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$0 = bcSmartspaceCardDoorbell;
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$1 = contentResolver;
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$2 = i;
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$3 = f;
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$4 = weakReference;
                bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$5 = weakReference2;
                VarHandle.storeStoreFence();
                return (BcSmartspaceCardDoorbell.DrawableWithUri) ((HashMap) map).computeIfAbsent((Uri) obj, bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2);
            default:
                BcSmartspaceCardDoorbell bcSmartspaceCardDoorbell2 = this.f$0;
                ContentResolver contentResolver2 = this.f$1;
                int i2 = this.f$2;
                float f2 = this.f$3;
                WeakReference weakReference3 = this.f$4;
                WeakReference weakReference4 = this.f$5;
                int i3 = BcSmartspaceCardDoorbell.$r8$clinit;
                BcSmartspaceCardDoorbell.DrawableWithUri drawableWithUri = new BcSmartspaceCardDoorbell.DrawableWithUri(new ColorDrawable(0));
                drawableWithUri.mTempRect = new RectF();
                drawableWithUri.mClipPath = new Path();
                drawableWithUri.mScaledCornerRadius = f2;
                drawableWithUri.mUri = (Uri) obj;
                drawableWithUri.mHeightInPx = i2;
                drawableWithUri.mContentResolver = contentResolver2;
                drawableWithUri.mImageViewWeakReference = weakReference3;
                drawableWithUri.mLoadingScreenWeakReference = weakReference4;
                VarHandle.storeStoreFence();
                BcSmartspaceCardDoorbell.LatencyInstrumentContext latencyInstrumentContext = bcSmartspaceCardDoorbell2.mLatencyInstrumentContext;
                BcSmartspaceCardDoorbell.LoadUriTask loadUriTask = new BcSmartspaceCardDoorbell.LoadUriTask();
                loadUriTask.mInstrumentContext = latencyInstrumentContext;
                VarHandle.storeStoreFence();
                loadUriTask.execute(drawableWithUri);
                return drawableWithUri;
        }
    }
}
