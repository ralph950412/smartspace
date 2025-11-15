package com.google.android.systemui.smartspace;

import androidx.constraintlayout.widget.ConstraintSet;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
final class NextClockAlarmController$updateNextAlarm$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NextClockAlarmController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NextClockAlarmController$updateNextAlarm$1(NextClockAlarmController nextClockAlarmController, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.this$0 = nextClockAlarmController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= ConstraintSet.Layout.UNSET_GONE_MARGIN;
        return NextClockAlarmController.access$updateNextAlarm(this.this$0, this);
    }
}
