package com.google.android.systemui.smartspace;

import android.graphics.ImageDecoder;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0 implements ImageDecoder.OnHeaderDecodedListener {
    public /* synthetic */ int f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        int i = this.f$0;
        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
        imageDecoder.setAllocator(3);
        imageDecoder.setTargetSize((int) (i * (imageInfo.getSize().getHeight() != 0 ? r2.getWidth() / r2.getHeight() : 0.0f)), i);
    }
}
