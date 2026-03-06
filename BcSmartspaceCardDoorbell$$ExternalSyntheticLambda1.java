package com.google.android.systemui.smartspace;

import android.net.Uri;
import java.util.function.Predicate;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1 implements Predicate {
    public /* synthetic */ BcSmartspaceCardDoorbell f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !this.f$0.mUriToDrawable.containsKey((Uri) obj);
    }
}
