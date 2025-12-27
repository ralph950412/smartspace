package com.google.android.systemui.smartspace;

import android.app.ActivityManager;
import android.text.format.DateFormat;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.Iterator;
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
final class KeyguardZenAlarmViewController$showAlarm$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Long $alarm;
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$showAlarm$1(Long l, KeyguardZenAlarmViewController keyguardZenAlarmViewController, Continuation continuation) {
        super(2, continuation);
        this.$alarm = l;
        this.this$0 = keyguardZenAlarmViewController;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardZenAlarmViewController$showAlarm$1(this.$alarm, this.this$0, continuation);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardZenAlarmViewController$showAlarm$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0099 A[LOOP:1: B:23:0x0093->B:25:0x0099, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0040  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long longValue;
        Iterator it;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Long l = this.$alarm;
            if (l != null) {
                longValue = l.longValue();
                if (longValue > 0) {
                    this.this$0.getClass();
                    if (longValue <= TimeUnit.HOURS.toMillis(12L) + System.currentTimeMillis()) {
                        String obj2 = DateFormat.format(DateFormat.is24HourFormat(this.this$0.context, ActivityManager.getCurrentUser()) ? "HH:mm" : "h:mm", longValue).toString();
                        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
                        Iterator it2 = keyguardZenAlarmViewController.smartspaceViews.iterator();
                        while (it2.hasNext()) {
                            ((BcSmartspaceDataPlugin.SmartspaceView) it2.next()).setNextAlarm(keyguardZenAlarmViewController.alarmImage, obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }
                it = this.this$0.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setNextAlarm(null, null);
                }
                return Unit.INSTANCE;
            }
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
        longValue = ((Number) obj).longValue();
        if (longValue > 0) {
        }
        it = this.this$0.smartspaceViews.iterator();
        while (it.hasNext()) {
        }
        return Unit.INSTANCE;
    }
}
