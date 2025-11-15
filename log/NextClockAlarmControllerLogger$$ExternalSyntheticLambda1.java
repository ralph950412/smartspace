package com.google.android.systemui.smartspace.log;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NextClockAlarmControllerLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Integer f$1;

    public /* synthetic */ NextClockAlarmControllerLogger$$ExternalSyntheticLambda1(int i, Integer num, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = num;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "onUserChanged newUser=" + this.f$0 + ", oldUser=" + this.f$1 + ", userContext=" + logMessage.getStr3();
            default:
                return "onBeforeUserSwitching newUser=" + this.f$0 + ", oldUser=" + this.f$1;
        }
    }
}
