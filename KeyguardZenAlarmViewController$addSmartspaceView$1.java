package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
final class KeyguardZenAlarmViewController$addSmartspaceView$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $v;
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$addSmartspaceView$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardZenAlarmViewController;
        this.$v = smartspaceView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new KeyguardZenAlarmViewController$addSmartspaceView$1(this.this$0, this.$v, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
            Flow flow = keyguardZenAlarmViewController.zenModeInteractor.mainActiveMode;
            final BcSmartspaceDataPlugin.SmartspaceView smartspaceView = this.$v;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.google.android.systemui.smartspace.KeyguardZenAlarmViewController$addSmartspaceView$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardZenAlarmViewController.this.updateModeIcon(smartspaceView, (ZenModeInfo) obj2);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
