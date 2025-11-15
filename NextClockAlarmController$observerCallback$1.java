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

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
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
        LogBuffer logBuffer = nextClockAlarmControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("NextClockAlarmControllerLog", LogLevel.DEBUG, new NextClockAlarmControllerLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = documentChangeInfo2;
        logBuffer.commit(obtain);
        StandaloneCoroutine standaloneCoroutine = nextClockAlarmController.updateNextAlarmJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        nextClockAlarmController.updateNextAlarmJob = BuildersKt.launch$default(nextClockAlarmController.applicationScope, null, null, new NextClockAlarmController$observerCallback$1$onDocumentChanged$1(nextClockAlarmController, null), 3);
    }
}
