package com.google.android.systemui.smartspace;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00ba, code lost:
    
        if (com.google.android.systemui.smartspace.NextClockAlarmController.access$updateNextAlarm(r12, r11) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00bc, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x002f, code lost:
    
        if (r1.createSearchSession(r12, r11) == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r12)
            goto Lbd
        L11:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L19:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L33
        L1d:
            kotlin.ResultKt.throwOnFailure(r12)
            com.google.android.systemui.smartspace.NextClockAlarmController r12 = r11.this$0
            com.google.android.systemui.smartspace.AlarmAppSearchController r1 = r12.alarmAppSearchController
            android.content.Context r12 = r12.context
            r12.getClass()
            r11.label = r3
            java.lang.Object r12 = r1.createSearchSession(r12, r11)
            if (r12 != r0) goto L33
            goto Lbc
        L33:
            com.google.android.systemui.smartspace.NextClockAlarmController r12 = r11.this$0
            com.google.android.systemui.smartspace.AlarmAppSearchController r1 = r12.alarmAppSearchController
            com.google.android.systemui.smartspace.NextClockAlarmController$observerCallback$1 r12 = r12.observerCallback
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl r4 = r1.searchSession
            if (r4 != 0) goto L45
            java.lang.String r12 = "AlarmAppSearchCtlr"
            java.lang.String r1 = "Session is not initialized yet!"
            android.util.Log.w(r12, r1)
            goto Lb2
        L45:
            androidx.appsearch.observer.ObserverSpec$Builder r4 = new androidx.appsearch.observer.ObserverSpec$Builder
            r4.<init>()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r4.mFilterSchemas = r5
            r5 = 0
            r4.mBuilt = r5
            java.lang.Class[] r6 = new java.lang.Class[r3]
            java.lang.Class<androidx.appsearch.builtintypes.Alarm> r7 = androidx.appsearch.builtintypes.Alarm.class
            r6[r5] = r7
            r4.addFilterDocumentClasses(r6)
            java.lang.Class[] r6 = new java.lang.Class[r3]
            java.lang.Class<androidx.appsearch.builtintypes.AlarmInstance> r7 = androidx.appsearch.builtintypes.AlarmInstance.class
            r6[r5] = r7
            r4.addFilterDocumentClasses(r6)
            r4.mBuilt = r3
            java.util.ArrayList r3 = r4.mFilterSchemas
            r3.getClass()
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl r4 = r1.searchSession
            r4.getClass()
            java.lang.String r5 = "com.google.android.deskclock"
            java.util.concurrent.Executor r1 = r1.mainExecutor
            r1.getClass()
            r12.getClass()
            androidx.collection.ArrayMap r6 = r4.mObserverCallbacksLocked
            monitor-enter(r6)
            androidx.collection.ArrayMap r7 = r4.mObserverCallbacksLocked     // Catch: java.lang.Throwable -> L8f
            java.lang.Object r7 = r7.get(r12)     // Catch: java.lang.Throwable -> L8f
            android.app.appsearch.observer.ObserverCallback r7 = (android.app.appsearch.observer.ObserverCallback) r7     // Catch: java.lang.Throwable -> L8f
            if (r7 != 0) goto L91
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl$1 r7 = new androidx.appsearch.platformstorage.GlobalSearchSessionImpl$1     // Catch: java.lang.Throwable -> L8f
            r7.<init>()     // Catch: java.lang.Throwable -> L8f
            goto L91
        L8f:
            r11 = move-exception
            goto Ld3
        L91:
            android.app.appsearch.GlobalSearchSession r8 = r4.mPlatformSession     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            android.app.appsearch.observer.ObserverSpec$Builder r9 = new android.app.appsearch.observer.ObserverSpec$Builder     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            r9.<init>()     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            androidx.collection.ArraySet r10 = new androidx.collection.ArraySet     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            r10.<init>(r3)     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            java.util.Set r3 = java.util.Collections.unmodifiableSet(r10)     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            android.app.appsearch.observer.ObserverSpec$Builder r3 = r9.addFilterSchemas(r3)     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            android.app.appsearch.observer.ObserverSpec r3 = r3.build()     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl.ApiHelperForT.registerObserverCallback(r8, r5, r3, r1, r7)     // Catch: java.lang.Throwable -> L8f android.app.appsearch.exceptions.AppSearchException -> Lc0
            androidx.collection.ArrayMap r1 = r4.mObserverCallbacksLocked     // Catch: java.lang.Throwable -> L8f
            r1.put(r12, r7)     // Catch: java.lang.Throwable -> L8f
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L8f
        Lb2:
            com.google.android.systemui.smartspace.NextClockAlarmController r12 = r11.this$0
            r11.label = r2
            java.lang.Object r11 = com.google.android.systemui.smartspace.NextClockAlarmController.access$updateNextAlarm(r12, r11)
            if (r11 != r0) goto Lbd
        Lbc:
            return r0
        Lbd:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        Lc0:
            r11 = move-exception
            androidx.appsearch.exceptions.AppSearchException r12 = new androidx.appsearch.exceptions.AppSearchException     // Catch: java.lang.Throwable -> L8f
            int r0 = r11.getResultCode()     // Catch: java.lang.Throwable -> L8f
            java.lang.String r1 = r11.getMessage()     // Catch: java.lang.Throwable -> L8f
            java.lang.Throwable r11 = r11.getCause()     // Catch: java.lang.Throwable -> L8f
            r12.<init>(r0, r1, r11)     // Catch: java.lang.Throwable -> L8f
            throw r12     // Catch: java.lang.Throwable -> L8f
        Ld3:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L8f
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.NextClockAlarmController$updateSession$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
