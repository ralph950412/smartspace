package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
final class KeyguardZenAlarmViewController$getNextAlarm$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$getNextAlarm$2(KeyguardZenAlarmViewController keyguardZenAlarmViewController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardZenAlarmViewController;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardZenAlarmViewController$getNextAlarm$2(this.this$0, continuation);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardZenAlarmViewController$getNextAlarm$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
        ResultKt.throwOnFailure(obj);
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.this$0.zenModeController;
        AlarmManager.AlarmClockInfo nextAlarmClock = zenModeControllerImpl.mAlarmManager.getNextAlarmClock(zenModeControllerImpl.mUserId);
        return new Long(nextAlarmClock != null ? nextAlarmClock.getTriggerTime() : 0L);
    }
}
