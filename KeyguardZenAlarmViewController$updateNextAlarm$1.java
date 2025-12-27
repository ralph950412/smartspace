package com.google.android.systemui.smartspace;

import java.util.concurrent.TimeUnit;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
final class KeyguardZenAlarmViewController$updateNextAlarm$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$updateNextAlarm$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardZenAlarmViewController;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardZenAlarmViewController$updateNextAlarm$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardZenAlarmViewController$updateNextAlarm$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
            keyguardZenAlarmViewController.alarmManager.cancel(keyguardZenAlarmViewController.showNextAlarm);
            KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = this.this$0;
            this.label = 1;
            obj = BuildersKt.withContext(keyguardZenAlarmViewController2.bgDispatcher, new KeyguardZenAlarmViewController$getNextAlarm$2(keyguardZenAlarmViewController2, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        long longValue = ((Number) obj).longValue();
        if (longValue > 0) {
            long millis = longValue - TimeUnit.HOURS.toMillis(12L);
            if (millis > 0) {
                KeyguardZenAlarmViewController keyguardZenAlarmViewController3 = this.this$0;
                keyguardZenAlarmViewController3.alarmManager.setExact(1, millis, "lock_screen_next_alarm", keyguardZenAlarmViewController3.showNextAlarm, keyguardZenAlarmViewController3.handler);
            }
        }
        this.this$0.showAlarm(new Long(longValue));
        return Unit.INSTANCE;
    }
}
