package com.google.android.systemui.smartspace;

import java.util.Objects;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public abstract class SmallHash {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int hash(String str) {
        return Math.abs(Math.floorMod(Objects.hashCode(str), 8192));
    }
}
