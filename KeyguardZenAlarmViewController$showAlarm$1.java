package com.google.android.systemui.smartspace;

import android.app.ActivityManager;
import android.text.format.DateFormat;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a4 A[LOOP:1: B:24:0x009e->B:26:0x00a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003f  */
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
                    if (longValue <= System.currentTimeMillis() + 43200000) {
                        String obj2 = DateFormat.format(DateFormat.is24HourFormat(this.this$0.context, ActivityManager.getCurrentUser()) ? "HH:mm" : "h:mm", longValue).toString();
                        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
                        Iterator it2 = keyguardZenAlarmViewController.smartspaceViews.iterator();
                        while (it2.hasNext()) {
                            ((BcSmartspaceDataPlugin.SmartspaceView) it2.next()).setNextAlarm(keyguardZenAlarmViewController.alarmImage, obj2);
                        }
                        StateFlowImpl stateFlowImpl = this.this$0.zenModeInteractor.zenModeRepository.hasNextAlarm;
                        Boolean bool = Boolean.TRUE;
                        stateFlowImpl.getClass();
                        stateFlowImpl.updateState(null, bool);
                        return Unit.INSTANCE;
                    }
                }
                it = this.this$0.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setNextAlarm(null, null);
                }
                StateFlowImpl stateFlowImpl2 = this.this$0.zenModeInteractor.zenModeRepository.hasNextAlarm;
                Boolean bool2 = Boolean.FALSE;
                stateFlowImpl2.getClass();
                stateFlowImpl2.updateState(null, bool2);
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
                Buffer$$ExternalSyntheticBUOutline0.m$1("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
        }
        longValue = ((Number) obj).longValue();
        if (longValue > 0) {
        }
        it = this.this$0.smartspaceViews.iterator();
        while (it.hasNext()) {
        }
        StateFlowImpl stateFlowImpl22 = this.this$0.zenModeInteractor.zenModeRepository.hasNextAlarm;
        Boolean bool22 = Boolean.FALSE;
        stateFlowImpl22.getClass();
        stateFlowImpl22.updateState(null, bool22);
        return Unit.INSTANCE;
    }
}
