package com.google.android.systemui.smartspace;

import android.util.Log;
import androidx.appsearch.observer.DocumentChangeInfo;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.google.android.systemui.smartspace.log.NextClockAlarmControllerLogger;
import com.google.android.systemui.smartspace.log.NextClockAlarmControllerLogger$$ExternalSyntheticLambda0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class NextClockAlarmController$observerCallback$1 {
    public final /* synthetic */ NextClockAlarmController this$0;

    public NextClockAlarmController$observerCallback$1(NextClockAlarmController nextClockAlarmController) {
        this.this$0 = nextClockAlarmController;
    }

    public final void onDocumentChanged(DocumentChangeInfo documentChangeInfo) {
        Log.d("NextClockAlarmCtlr", "onDocumentChanged changeInfo=" + documentChangeInfo);
        NextClockAlarmController nextClockAlarmController = this.this$0;
        NextClockAlarmControllerLogger nextClockAlarmControllerLogger = nextClockAlarmController.logger;
        String documentChangeInfo2 = documentChangeInfo.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        NextClockAlarmControllerLogger$$ExternalSyntheticLambda0 nextClockAlarmControllerLogger$$ExternalSyntheticLambda0 = new NextClockAlarmControllerLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = nextClockAlarmControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("NextClockAlarmControllerLog", logLevel, nextClockAlarmControllerLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = documentChangeInfo2;
        logBuffer.commit(obtain);
        StandaloneCoroutine standaloneCoroutine = nextClockAlarmController.updateNextAlarmJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        nextClockAlarmController.updateNextAlarmJob = BuildersKt.launch$default(nextClockAlarmController.applicationScope, null, null, new NextClockAlarmController$observerCallback$1$onDocumentChanged$1(nextClockAlarmController, null), 3);
    }
}
