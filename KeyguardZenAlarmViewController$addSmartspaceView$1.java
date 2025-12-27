package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import java.lang.invoke.VarHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
final class KeyguardZenAlarmViewController$addSmartspaceView$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $v;
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.KeyguardZenAlarmViewController$addSmartspaceView$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $v;
        public /* synthetic */ KeyguardZenAlarmViewController this$0;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // kotlinx.coroutines.flow.FlowCollector
        public final Object emit(Object obj, Continuation continuation) {
            this.this$0.updateModeIcon(this.$v, (ZenModeInfo) obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$addSmartspaceView$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardZenAlarmViewController;
        this.$v = smartspaceView;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new KeyguardZenAlarmViewController$addSmartspaceView$1(this.this$0, this.$v, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
            Flow flow = keyguardZenAlarmViewController.zenModeInteractor.mainActiveMode;
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView = this.$v;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            anonymousClass1.this$0 = keyguardZenAlarmViewController;
            anonymousClass1.$v = smartspaceView;
            VarHandle.storeStoreFence();
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
