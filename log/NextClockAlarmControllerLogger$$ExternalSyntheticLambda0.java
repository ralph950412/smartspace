package com.google.android.systemui.smartspace.log;

import androidx.appsearch.platformstorage.converter.GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NextClockAlarmControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0.m("onDocumentChanged changeInfo=", logMessage.getStr1());
            case 1:
                return GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0.m("Error: ", logMessage.getStr1());
            case 2:
                return GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0.m("Next Alarm is set to ", logMessage.getStr1());
            default:
                return GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0.m("Changed alarm info=", logMessage.getStr1());
        }
    }
}
