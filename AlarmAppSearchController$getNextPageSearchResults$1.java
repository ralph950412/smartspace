package com.google.android.systemui.smartspace;

import androidx.constraintlayout.widget.ConstraintSet;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
final class AlarmAppSearchController$getNextPageSearchResults$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AlarmAppSearchController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlarmAppSearchController$getNextPageSearchResults$1(AlarmAppSearchController alarmAppSearchController, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.this$0 = alarmAppSearchController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= ConstraintSet.Layout.UNSET_GONE_MARGIN;
        return this.this$0.getNextPageSearchResults(null, this);
    }
}
