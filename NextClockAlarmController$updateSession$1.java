package com.google.android.systemui.smartspace;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
final class NextClockAlarmController$updateSession$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ NextClockAlarmController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NextClockAlarmController$updateSession$1(NextClockAlarmController nextClockAlarmController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nextClockAlarmController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NextClockAlarmController$updateSession$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NextClockAlarmController$updateSession$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00ce, code lost:
    
        if (com.google.android.systemui.smartspace.NextClockAlarmController.access$updateNextAlarm(r14, r13) == r0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00d0, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x002f, code lost:
    
        if (r1.createSearchSession(r14, r13) == r0) goto L33;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.NextClockAlarmController$updateSession$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
