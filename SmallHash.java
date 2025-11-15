package com.google.android.systemui.smartspace;

import java.util.Objects;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public abstract class SmallHash {
    public static int hash(String str) {
        return Math.abs(Math.floorMod(Objects.hashCode(str), 8192));
    }
}
