package com.google.android.systemui.smartspace;

import android.graphics.drawable.Drawable;
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

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
final class KeyguardZenAlarmViewController$updateModeIcon$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ZenModeInfo $mainActiveMode;
    final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $view;
    int label;
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

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
        Drawable mutate;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ZenModeInfo zenModeInfo = this.$mainActiveMode;
        if (zenModeInfo != null) {
            Drawable drawable = zenModeInfo.icon.drawable;
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState == null || (mutate = constantState.newDrawable(this.this$0.context.getResources())) == null) {
                mutate = drawable.mutate();
            }
            this.$view.setDnd(mutate, this.this$0.context.getString(R.string.active_mode_content_description, this.$mainActiveMode.name));
        } else {
            this.$view.setDnd(null, null);
        }
        return Unit.INSTANCE;
    }
}
