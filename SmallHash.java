package com.google.android.systemui.smartspace;

import java.util.Objects;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public abstract class SmallHash {
    public static int hash(String str) {
        return Math.abs(Math.floorMod(Objects.hashCode(str), 8192));
    }
}
