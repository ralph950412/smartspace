package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.CoreStartable;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.NotificationMediaManager$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.InitializationChecker;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class KeyguardSmartspaceStartable implements CoreStartable {
    public final InitializationChecker initializationChecker;
    public final KeyguardMediaViewController mediaController;
    public final KeyguardZenAlarmViewController zenController;

    public KeyguardSmartspaceStartable(KeyguardZenAlarmViewController keyguardZenAlarmViewController, KeyguardMediaViewController keyguardMediaViewController, InitializationChecker initializationChecker) {
        this.zenController = keyguardZenAlarmViewController;
        this.mediaController = keyguardMediaViewController;
        this.initializationChecker = initializationChecker;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.initializationChecker.initializeComponents()) {
            final KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.zenController;
            keyguardZenAlarmViewController.datePlugin.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.systemui.smartspace.KeyguardZenAlarmViewController$init$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = KeyguardZenAlarmViewController.this;
                    BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                    if (!keyguardZenAlarmViewController2.smartspaceViews.contains(smartspaceView)) {
                        keyguardZenAlarmViewController2.smartspaceViews.add(smartspaceView);
                        RepeatWhenAttachedKt.repeatWhenAttached((View) smartspaceView, EmptyCoroutineContext.INSTANCE, new KeyguardZenAlarmViewController$addSmartspaceView$1(keyguardZenAlarmViewController2, smartspaceView, null));
                    }
                    if (KeyguardZenAlarmViewController.this.smartspaceViews.size() == 1) {
                        KeyguardZenAlarmViewController keyguardZenAlarmViewController3 = KeyguardZenAlarmViewController.this;
                        NextClockAlarmController nextClockAlarmController = keyguardZenAlarmViewController3.nextClockAlarmController;
                        KeyguardZenAlarmViewController$nextAlarmCallback$1 keyguardZenAlarmViewController$nextAlarmCallback$1 = keyguardZenAlarmViewController3.nextAlarmCallback;
                        nextClockAlarmController.changeCallbacks.add(keyguardZenAlarmViewController$nextAlarmCallback$1);
                        KeyguardZenAlarmViewController keyguardZenAlarmViewController4 = keyguardZenAlarmViewController$nextAlarmCallback$1.this$0;
                        BuildersKt.launch$default(keyguardZenAlarmViewController4.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController4, null), 3);
                    }
                    KeyguardZenAlarmViewController keyguardZenAlarmViewController5 = KeyguardZenAlarmViewController.this;
                    BuildersKt.launch$default(keyguardZenAlarmViewController5.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController5, null), 3);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    KeyguardZenAlarmViewController.this.smartspaceViews.remove((BcSmartspaceDataPlugin.SmartspaceView) view);
                    if (KeyguardZenAlarmViewController.this.smartspaceViews.isEmpty()) {
                        KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = KeyguardZenAlarmViewController.this;
                        NextClockAlarmController nextClockAlarmController = keyguardZenAlarmViewController2.nextClockAlarmController;
                        nextClockAlarmController.changeCallbacks.remove(keyguardZenAlarmViewController2.nextAlarmCallback);
                    }
                }
            });
            NextClockAlarmController nextClockAlarmController = keyguardZenAlarmViewController.nextClockAlarmController;
            UserTracker userTracker = nextClockAlarmController.userTracker;
            if (nextClockAlarmController.isUserUnlocked$1()) {
                nextClockAlarmController.updateSession(((UserTrackerImpl) userTracker).getUserContext());
            }
            nextClockAlarmController.dumpManager.registerNormalDumpable("NextClockAlarmCtlr", nextClockAlarmController);
            ((UserTrackerImpl) userTracker).addCallback(nextClockAlarmController.userChangedCallback, nextClockAlarmController.mainExecutor);
            BuildersKt.launch$default(keyguardZenAlarmViewController.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController, null), 3);
            final KeyguardMediaViewController keyguardMediaViewController = this.mediaController;
            keyguardMediaViewController.plugin.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.systemui.smartspace.KeyguardMediaViewController$init$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    KeyguardMediaViewController keyguardMediaViewController2 = KeyguardMediaViewController.this;
                    keyguardMediaViewController2.smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                    NotificationMediaManager notificationMediaManager = keyguardMediaViewController2.mediaManager;
                    KeyguardMediaViewController$mediaListener$1 keyguardMediaViewController$mediaListener$1 = keyguardMediaViewController2.mediaListener;
                    notificationMediaManager.mMediaListeners.add(keyguardMediaViewController$mediaListener$1);
                    notificationMediaManager.mBackgroundExecutor.execute(new NotificationMediaManager$$ExternalSyntheticLambda0(0, notificationMediaManager, keyguardMediaViewController$mediaListener$1));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    KeyguardMediaViewController keyguardMediaViewController2 = KeyguardMediaViewController.this;
                    keyguardMediaViewController2.smartspaceView = null;
                    NotificationMediaManager notificationMediaManager = keyguardMediaViewController2.mediaManager;
                    notificationMediaManager.mMediaListeners.remove(keyguardMediaViewController2.mediaListener);
                }
            });
        }
    }
}
