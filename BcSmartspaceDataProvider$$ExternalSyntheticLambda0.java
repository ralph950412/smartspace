package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.function.Predicate;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceDataProvider$$ExternalSyntheticLambda0 implements Predicate {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((SmartspaceTarget) obj).getFeatureType() != 15;
    }
}
