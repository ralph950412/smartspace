package com.google.android.systemui.smartspace;

import android.graphics.ImageDecoder;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda7 implements ImageDecoder.OnHeaderDecodedListener {
    public /* synthetic */ int f$0;

    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        int i = this.f$0;
        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
        imageDecoder.setAllocator(3);
        imageDecoder.setTargetSize((int) (i * (imageInfo.getSize().getHeight() != 0 ? r2.getWidth() / r2.getHeight() : 0.0f)), i);
    }
}
