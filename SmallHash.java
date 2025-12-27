package com.google.android.systemui.smartspace;

import java.util.Objects;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class SmallHash {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int hash(String str) {
        return Math.abs(Math.floorMod(Objects.hashCode(str), 8192));
    }
}
