package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import com.android.wm.shell.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
final class KeyguardZenAlarmViewController$updateModeIcon$1 extends SuspendLambda implements Function2 {
    public final /* synthetic */ ZenModeInfo $mainActiveMode;
    public final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $view;
    int label;
    public final /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardZenAlarmViewController$updateModeIcon$1(ZenModeInfo zenModeInfo, KeyguardZenAlarmViewController keyguardZenAlarmViewController, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
        super(2, continuation);
        this.$mainActiveMode = zenModeInfo;
        this.this$0 = keyguardZenAlarmViewController;
        this.$view = smartspaceView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardZenAlarmViewController$updateModeIcon$1(this.$mainActiveMode, this.this$0, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        KeyguardZenAlarmViewController$updateModeIcon$1 keyguardZenAlarmViewController$updateModeIcon$1 = (KeyguardZenAlarmViewController$updateModeIcon$1) create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        keyguardZenAlarmViewController$updateModeIcon$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("call to 'resume' before 'invoke' with coroutine");
            return null;
        }
        ResultKt.throwOnFailure(obj);
        ZenModeInfo zenModeInfo = this.$mainActiveMode;
        if (zenModeInfo != null) {
            this.$view.setDnd(zenModeInfo.icon.drawable, this.this$0.context.getString(R.string.active_mode_content_description, zenModeInfo.name));
        } else {
            this.$view.setDnd(null, null);
        }
        return Unit.INSTANCE;
    }
}
