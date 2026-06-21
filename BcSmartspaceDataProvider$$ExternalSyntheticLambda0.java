package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.function.Predicate;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceDataProvider$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((SmartspaceTarget) obj).getFeatureType() != 15;
    }
}
