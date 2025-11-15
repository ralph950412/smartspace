package com.google.android.systemui.smartspace.log;

import androidx.appsearch.platformstorage.converter.GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NextClockAlarmControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        String str1;
        String str;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                str1 = logMessage.getStr1();
                str = "onDocumentChanged changeInfo=";
                break;
            case 1:
                str1 = logMessage.getStr1();
                str = "Error: ";
                break;
            case 2:
                str1 = logMessage.getStr1();
                str = "Next Alarm is set to ";
                break;
            default:
                str1 = logMessage.getStr1();
                str = "Changed alarm info=";
                break;
        }
        return GenericDocumentToPlatformConverter$$ExternalSyntheticOutline0.m(str, str1);
    }
}
