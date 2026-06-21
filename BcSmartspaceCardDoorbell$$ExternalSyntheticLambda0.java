package com.google.android.systemui.smartspace;

import android.net.Uri;
import java.util.function.Predicate;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0 implements Predicate {
    public /* synthetic */ BcSmartspaceCardDoorbell f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !this.f$0.mUriToDrawable.containsKey((Uri) obj);
    }
}
