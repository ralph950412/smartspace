package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.appwidget.AppWidgetHostView;
import android.content.Context;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.lang.invoke.VarHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class BcSmartspaceRemoteViewsCard extends AppWidgetHostView implements SmartspaceCard {
    public BcSmartspaceCardLoggingInfo _loggingInfo;
    public SmartspaceTarget _target;
    public String _uiSurface;

    public BcSmartspaceRemoteViewsCard(Context context) {
        super(context);
        setOnLongClickListener(null);
        if (Intrinsics.areEqual(this._uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            super.setInteractionHandler(null);
        }
    }

    public static /* synthetic */ void getRemoteViewInteractionHandler$annotations() {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z) {
        updateAppWidget(null);
        this._target = smartspaceTarget;
        this._loggingInfo = bcSmartspaceCardLoggingInfo;
        updateAppWidget(smartspaceTarget.getRemoteViews());
        SmartspaceAction headerAction = smartspaceTarget.getHeaderAction();
        if (headerAction == null) {
            setOnClickListener(null);
            super.setInteractionHandler(null);
            return;
        }
        BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, headerAction, smartspaceEventNotifier, "BcSmartspaceRemoteViewsCard", bcSmartspaceCardLoggingInfo, 0);
        if (Intrinsics.areEqual(this._uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            BcSmartSpaceUtil.AnonymousClass1 anonymousClass1 = new BcSmartSpaceUtil.AnonymousClass1();
            anonymousClass1.val$eventNotifier = smartspaceEventNotifier;
            anonymousClass1.val$loggingInfo = bcSmartspaceCardLoggingInfo;
            anonymousClass1.val$target = smartspaceTarget;
            anonymousClass1.val$action = headerAction;
            VarHandle.storeStoreFence();
            super.setInteractionHandler(anonymousClass1);
        }
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final BcSmartspaceCardLoggingInfo getLoggingInfo() {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this._loggingInfo;
        if (bcSmartspaceCardLoggingInfo != null) {
            return bcSmartspaceCardLoggingInfo;
        }
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this._uiSurface, 0.0f);
        SmartspaceTarget smartspaceTarget = this._target;
        int featureType = smartspaceTarget != null ? smartspaceTarget.getFeatureType() : 0;
        getContext().getPackageManager();
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo2.mInstanceId = 0;
        bcSmartspaceCardLoggingInfo2.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo2.mRank = 0;
        bcSmartspaceCardLoggingInfo2.mCardinality = 0;
        bcSmartspaceCardLoggingInfo2.mFeatureType = featureType;
        bcSmartspaceCardLoggingInfo2.mReceivedLatency = 0;
        bcSmartspaceCardLoggingInfo2.mUid = -1;
        bcSmartspaceCardLoggingInfo2.mSubcardInfo = null;
        bcSmartspaceCardLoggingInfo2.mDimensionalInfo = null;
        VarHandle.storeStoreFence();
        return bcSmartspaceCardLoggingInfo2;
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final View getView() {
        return this;
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setDozeAmount(float f) {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setPrimaryTextColor(int i) {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setScreenOn(boolean z) {
    }
}
