package com.google.android.systemui.smartspace;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.appwidget.AppWidgetHostView;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class BcSmartspaceRemoteViewsCard extends AppWidgetHostView implements SmartspaceCard {
    public BcSmartspaceDataPlugin.SmartspaceEventNotifier mEventNotifier;
    public BcSmartspaceCardLoggingInfo mLoggingInfo;
    public SmartspaceTarget mTarget;
    public String mUiSurface;

    public BcSmartspaceRemoteViewsCard(Context context) {
        super(context);
        setOnLongClickListener(null);
        if (Intrinsics.areEqual(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            super.setInteractionHandler(null);
        }
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z) {
        this.mTarget = smartspaceTarget;
        this.mLoggingInfo = bcSmartspaceCardLoggingInfo;
        this.mEventNotifier = smartspaceEventNotifier;
        updateAppWidget(smartspaceTarget.getRemoteViews());
        SmartspaceAction headerAction = smartspaceTarget.getHeaderAction();
        if (headerAction == null) {
            setOnClickListener(null);
            super.setInteractionHandler(null);
        } else {
            BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, headerAction, this.mEventNotifier, "BcSmartspaceRemoteViewsCard", bcSmartspaceCardLoggingInfo, 0);
            if (Intrinsics.areEqual(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                super.setInteractionHandler(new RemoteViews.InteractionHandler() { // from class: com.google.android.systemui.smartspace.BcSmartSpaceUtil.1
                    public final /* synthetic */ SmartspaceAction val$action;
                    public final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier val$eventNotifier;
                    public final /* synthetic */ SmartspaceTarget val$target;

                    public AnonymousClass1(BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier2, SmartspaceTarget smartspaceTarget2, SmartspaceAction headerAction2) {
                        r2 = smartspaceEventNotifier2;
                        r3 = smartspaceTarget2;
                        r4 = headerAction2;
                    }

                    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                        BcSmartspaceDataPlugin.IntentStarter intentStarter = BcSmartSpaceUtil.sIntentStarter;
                        if (intentStarter == null) {
                            intentStarter = new AnonymousClass2("BcSmartspaceRemoteViewsCard");
                        }
                        if (pendingIntent != null) {
                            BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, BcSmartspaceCardLoggingInfo.this);
                            BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier2 = r2;
                            if (smartspaceEventNotifier2 != null) {
                                smartspaceEventNotifier2.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(r3).setSmartspaceActionId(r4.getId()).build());
                            }
                            intentStarter.startPendingIntent(view, pendingIntent, false);
                        }
                        return true;
                    }
                });
            }
        }
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final BcSmartspaceCardLoggingInfo getLoggingInfo() {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.mLoggingInfo;
        if (bcSmartspaceCardLoggingInfo != null) {
            return bcSmartspaceCardLoggingInfo;
        }
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, 0.0f);
        SmartspaceTarget smartspaceTarget = this.mTarget;
        builder.mFeatureType = smartspaceTarget != null ? smartspaceTarget.getFeatureType() : 0;
        getContext().getPackageManager();
        builder.mUid = -1;
        return new BcSmartspaceCardLoggingInfo(builder);
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setDozeAmount$1(float f) {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setPrimaryTextColor(int i) {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setScreenOn(boolean z) {
    }

    public static /* synthetic */ void getRemoteViewInteractionHandler$annotations() {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final View getView() {
        return this;
    }
}
