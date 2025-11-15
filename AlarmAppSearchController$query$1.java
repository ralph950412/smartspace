package com.google.android.systemui.smartspace;

import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
final class AlarmAppSearchController$query$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AlarmAppSearchController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlarmAppSearchController$query$1(AlarmAppSearchController alarmAppSearchController, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.this$0 = alarmAppSearchController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.query(this);
    }
}
