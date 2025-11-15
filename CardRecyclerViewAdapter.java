package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class CardRecyclerViewAdapter extends ListAdapter implements CardAdapter {
    public static final Set legacySecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartSpaceUtil.FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP.values());
    public static final Set templateSecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.values());
    public final List _aodTargets;
    public float _dozeAmount;
    public final List _lockscreenTargets;
    public Handler bgHandler;
    public BcSmartspaceConfigPlugin configProvider;
    public int currentTextColor;
    public BcSmartspaceDataPlugin dataProvider;
    public final int dozeColor;
    public boolean hasAodLockscreenTransition;
    public boolean hasDifferentTargets;
    public boolean keyguardBypassEnabled;
    public final List mediaTargets;
    public Integer nonRemoteViewsHorizontalPadding;
    public float previousDozeAmount;
    public int primaryTextColor;
    public final BcSmartspaceView root;
    public List smartspaceTargets;
    public BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate;
    public TransitionType transitioningTo;
    public String uiSurface;
    public final SparseArray viewHolders;

    /* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
    public final class DiffUtilItemCallback extends DiffUtil.Callback {
        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public final /* bridge */ /* synthetic */ boolean areContentsTheSame(Object obj, Object obj2) {
            return false;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public final boolean areItemsTheSame(Object obj, Object obj2) {
            return Intrinsics.areEqual(((SmartspaceTarget) obj).getSmartspaceTargetId(), ((SmartspaceTarget) obj2).getSmartspaceTargetId());
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
    public final class TransitionType {
        public static final /* synthetic */ TransitionType[] $VALUES;
        public static final TransitionType NOT_IN_TRANSITION;
        public static final TransitionType TO_AOD;
        public static final TransitionType TO_LOCKSCREEN;

        static {
            TransitionType transitionType = new TransitionType("NOT_IN_TRANSITION", 0);
            NOT_IN_TRANSITION = transitionType;
            TransitionType transitionType2 = new TransitionType("TO_LOCKSCREEN", 1);
            TO_LOCKSCREEN = transitionType2;
            TransitionType transitionType3 = new TransitionType("TO_AOD", 2);
            TO_AOD = transitionType3;
            TransitionType[] transitionTypeArr = {transitionType, transitionType2, transitionType3};
            $VALUES = transitionTypeArr;
            EnumEntriesKt.enumEntries(transitionTypeArr);
        }

        public static TransitionType valueOf(String str) {
            return (TransitionType) Enum.valueOf(TransitionType.class, str);
        }

        public static TransitionType[] values() {
            return (TransitionType[]) $VALUES.clone();
        }
    }

    /* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final SmartspaceCard card;

        public ViewHolder(SmartspaceCard smartspaceCard) {
            super(smartspaceCard.getView());
            this.card = smartspaceCard;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public CardRecyclerViewAdapter(com.google.android.systemui.smartspace.BcSmartspaceView r4, com.android.systemui.plugins.BcSmartspaceConfigPlugin r5) {
        /*
            r3 = this;
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$DiffUtilItemCallback r0 = new com.google.android.systemui.smartspace.CardRecyclerViewAdapter$DiffUtilItemCallback
            r0.<init>()
            java.lang.Object r1 = androidx.recyclerview.widget.AsyncDifferConfig.Builder.sExecutorLock
            monitor-enter(r1)
            java.util.concurrent.Executor r2 = androidx.recyclerview.widget.AsyncDifferConfig.Builder.sDiffExecutor     // Catch: java.lang.Throwable -> L14
            if (r2 != 0) goto L16
            r2 = 2
            java.util.concurrent.ExecutorService r2 = java.util.concurrent.Executors.newFixedThreadPool(r2)     // Catch: java.lang.Throwable -> L14
            androidx.recyclerview.widget.AsyncDifferConfig.Builder.sDiffExecutor = r2     // Catch: java.lang.Throwable -> L14
            goto L16
        L14:
            r3 = move-exception
            goto L5f
        L16:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L14
            java.util.concurrent.Executor r1 = androidx.recyclerview.widget.AsyncDifferConfig.Builder.sDiffExecutor
            androidx.recyclerview.widget.AsyncDifferConfig r2 = new androidx.recyclerview.widget.AsyncDifferConfig
            r2.<init>(r1, r0)
            r3.<init>(r2)
            r3.root = r4
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r3.viewHolders = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3.smartspaceTargets = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3._aodTargets = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3._lockscreenTargets = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3.mediaTargets = r0
            r0 = -1
            r3.dozeColor = r0
            android.content.Context r4 = r4.getContext()
            r0 = 16842806(0x1010036, float:2.369371E-38)
            int r4 = com.android.launcher3.icons.GraphicsUtils.getAttrColor(r0, r4)
            r3.primaryTextColor = r4
            r3.currentTextColor = r4
            r3.configProvider = r5
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r4 = com.google.android.systemui.smartspace.CardRecyclerViewAdapter.TransitionType.NOT_IN_TRANSITION
            r3.transitioningTo = r4
            return
        L5f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L14
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.CardRecyclerViewAdapter.<init>(com.google.android.systemui.smartspace.BcSmartspaceView, com.android.systemui.plugins.BcSmartspaceConfigPlugin):void");
    }

    public static boolean isTemplateCard(SmartspaceTarget smartspaceTarget) {
        return smartspaceTarget.getTemplateData() != null && BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
    }

    public final void addDefaultDateCardIfEmpty$1(List list) {
        if (list.isEmpty()) {
            BcSmartspaceView bcSmartspaceView = this.root;
            list.add(new SmartspaceTarget.Builder("date_card_794317_92634", new ComponentName(bcSmartspaceView.getContext(), (Class<?>) CardRecyclerViewAdapter.class), bcSmartspaceView.getContext().getUser()).setFeatureType(1).setTemplateData(new BaseTemplateData.Builder(1).build()).build());
        }
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceCard getCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.card;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final int getCount() {
        return this.smartspaceTargets.size();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final float getDozeAmount() {
        return this._dozeAmount;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasAodLockscreenTransition() {
        return this.hasAodLockscreenTransition;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasDifferentTargets() {
        return this.hasDifferentTargets;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
        RemoteViews remoteViews = smartspaceTarget.getRemoteViews();
        BaseTemplateData templateData = smartspaceTarget.getTemplateData();
        if (smartspaceTarget.getRemoteViews() != null) {
            remoteViews.getClass();
            return remoteViews.getLayoutId();
        }
        if (!isTemplateCard(smartspaceTarget)) {
            Integer num = (Integer) BcSmartSpaceUtil.FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP.get(Integer.valueOf(BcSmartSpaceUtil.getFeatureType(smartspaceTarget)));
            return num != null ? num.intValue() : R.layout.smartspace_card;
        }
        templateData.getClass();
        BaseTemplateData.SubItemInfo primaryItem = templateData.getPrimaryItem();
        if (primaryItem == null) {
            return R.layout.smartspace_base_template_card_with_date;
        }
        if (SmartspaceUtils.isEmpty(primaryItem.getText()) && primaryItem.getIcon() == null) {
            return R.layout.smartspace_base_template_card_with_date;
        }
        Integer num2 = (Integer) BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.get(Integer.valueOf(templateData.getTemplateType()));
        return num2 != null ? num2.intValue() : R.layout.smartspace_base_template_card;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceCard getLegacyCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BcSmartspaceCard) {
            return (BcSmartspaceCard) smartspaceCard;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final List getLockscreenTargets() {
        return (this.mediaTargets.isEmpty() || !this.keyguardBypassEnabled) ? this._lockscreenTargets : this.mediaTargets;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceRemoteViewsCard getRemoteViewsCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BcSmartspaceRemoteViewsCard) {
            return (BcSmartspaceRemoteViewsCard) smartspaceCard;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final List getSmartspaceTargets() {
        return this.smartspaceTargets;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceTarget getTargetAtPosition(int i) {
        if (i < 0 || i >= getItemCount()) {
            return null;
        }
        return (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BaseTemplateCard getTemplateCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BaseTemplateCard) {
            return (BaseTemplateCard) smartspaceCard;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final String getUiSurface() {
        return this.uiSurface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
        smartspaceTarget.getClass();
        boolean isTemplateCard = isTemplateCard(smartspaceTarget);
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mInstanceId = InstanceId.create(smartspaceTarget);
        builder.mFeatureType = smartspaceTarget.getFeatureType();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.uiSurface, this._dozeAmount);
        builder.mRank = i;
        builder.mCardinality = this.smartspaceTargets.size();
        this.root.getContext().getPackageManager();
        builder.mUid = -1;
        builder.mSubcardInfo = isTemplateCard ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        builder.mDimensionalInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo(builder);
        SmartspaceCard smartspaceCard = viewHolder2.card;
        if (smartspaceTarget.getRemoteViews() != null) {
            if (!(smartspaceCard instanceof BcSmartspaceRemoteViewsCard)) {
                Log.w("SsCardRecyclerViewAdapter", "[rmv] No RemoteViews card view can be binded");
                return;
            }
            Log.d("SsCardRecyclerViewAdapter", "[rmv] Refreshing RemoteViews card");
        } else if (isTemplateCard) {
            BaseTemplateData templateData = smartspaceTarget.getTemplateData();
            if (templateData == null) {
                throw new IllegalStateException("Required value was null.");
            }
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(bcSmartspaceCardLoggingInfo, templateData);
            if (!(smartspaceCard instanceof BaseTemplateCard)) {
                Log.w("SsCardRecyclerViewAdapter", "No ui-template card view can be binded");
                return;
            }
        } else {
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeAndInjectWeatherSubcard(bcSmartspaceCardLoggingInfo, smartspaceTarget);
            if (!(smartspaceCard instanceof BcSmartspaceCard)) {
                Log.w("SsCardRecyclerViewAdapter", "No legacy card view can be binded");
                return;
            }
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.dataProvider;
        smartspaceCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        smartspaceCard.setPrimaryTextColor(this.currentTextColor);
        smartspaceCard.setDozeAmount$1(this._dozeAmount);
        this.viewHolders.put(i, viewHolder2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        Integer num;
        BaseTemplateCard baseTemplateCard;
        BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard;
        Set set = templateSecondaryCardResourceIdSet;
        Integer num2 = null;
        if (set.contains(Integer.valueOf(i)) || i == R.layout.smartspace_base_template_card_with_date || i == R.layout.smartspace_base_template_card) {
            if (set.contains(Integer.valueOf(i))) {
                num = Integer.valueOf(i);
                i = R.layout.smartspace_base_template_card;
            } else {
                num = null;
            }
            LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
            BaseTemplateCard baseTemplateCard2 = (BaseTemplateCard) from.inflate(i, viewGroup, false);
            String str = this.uiSurface;
            baseTemplateCard2.mUiSurface = str;
            if (baseTemplateCard2.mDateView != null && TextUtils.equals(str, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                IcuDateTextView icuDateTextView = baseTemplateCard2.mDateView;
                if (icuDateTextView.isAttachedToWindow()) {
                    throw new IllegalStateException("Must call before attaching view to window.");
                }
                icuDateTextView.mUpdatesOnAod = true;
            }
            Integer num3 = this.nonRemoteViewsHorizontalPadding;
            if (num3 != null) {
                int intValue = num3.intValue();
                baseTemplateCard2.setPaddingRelative(intValue, baseTemplateCard2.getPaddingTop(), intValue, baseTemplateCard2.getPaddingBottom());
            }
            Handler handler = this.bgHandler;
            Handler handler2 = handler != null ? handler : null;
            baseTemplateCard2.mBgHandler = handler2;
            IcuDateTextView icuDateTextView2 = baseTemplateCard2.mDateView;
            if (icuDateTextView2 != null) {
                icuDateTextView2.mBgHandler = handler2;
            }
            BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate = this.timeChangedDelegate;
            if (icuDateTextView2 != null) {
                if (icuDateTextView2.isAttachedToWindow()) {
                    throw new IllegalStateException("Must call before attaching view to window.");
                }
                icuDateTextView2.mTimeChangedDelegate = timeChangedDelegate;
            }
            baseTemplateCard2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            baseTemplateCard = baseTemplateCard2;
            if (num != null) {
                BcSmartspaceCardSecondary bcSmartspaceCardSecondary = (BcSmartspaceCardSecondary) from.inflate(num.intValue(), (ViewGroup) baseTemplateCard2, false);
                Log.i("SsCardRecyclerViewAdapter", "Secondary card is found");
                baseTemplateCard2.setSecondaryCard(bcSmartspaceCardSecondary);
                baseTemplateCard = baseTemplateCard2;
            }
        } else {
            Set set2 = legacySecondaryCardResourceIdSet;
            if (!set2.contains(Integer.valueOf(i)) && i != R.layout.smartspace_card) {
                bcSmartspaceRemoteViewsCard = new BcSmartspaceRemoteViewsCard(viewGroup.getContext());
                bcSmartspaceRemoteViewsCard.mUiSurface = bcSmartspaceRemoteViewsCard.mUiSurface;
                bcSmartspaceRemoteViewsCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                return new ViewHolder(bcSmartspaceRemoteViewsCard);
            }
            if (set2.contains(Integer.valueOf(i))) {
                num2 = Integer.valueOf(i);
                i = R.layout.smartspace_card;
            }
            LayoutInflater from2 = LayoutInflater.from(viewGroup.getContext());
            BcSmartspaceCard bcSmartspaceCard = (BcSmartspaceCard) from2.inflate(i, viewGroup, false);
            bcSmartspaceCard.mUiSurface = this.uiSurface;
            Integer num4 = this.nonRemoteViewsHorizontalPadding;
            if (num4 != null) {
                int intValue2 = num4.intValue();
                bcSmartspaceCard.setPaddingRelative(intValue2, bcSmartspaceCard.getPaddingTop(), intValue2, bcSmartspaceCard.getPaddingBottom());
            }
            bcSmartspaceCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            baseTemplateCard = bcSmartspaceCard;
            if (num2 != null) {
                bcSmartspaceCard.setSecondaryCard((BcSmartspaceCardSecondary) from2.inflate(num2.intValue(), (ViewGroup) bcSmartspaceCard, false));
                baseTemplateCard = bcSmartspaceCard;
            }
        }
        bcSmartspaceRemoteViewsCard = baseTemplateCard;
        return new ViewHolder(bcSmartspaceRemoteViewsCard);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setBgHandler(Handler handler) {
        this.bgHandler = handler;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.configProvider = bcSmartspaceConfigPlugin;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        this.dataProvider = bcSmartspaceDataPlugin;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setDozeAmount(float f) {
        this._dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        updateTargetVisibility(null, false);
        updateCurrentTextColor$1();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setKeyguardBypassEnabled(boolean z) {
        this.keyguardBypassEnabled = z;
        updateTargetVisibility(null, false);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mediaTargets.clear();
        if (smartspaceTarget != null) {
            this.mediaTargets.add(smartspaceTarget);
        }
        updateTargetVisibility(null, true);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setNonRemoteViewsHorizontalPadding(Integer num) {
        this.nonRemoteViewsHorizontalPadding = num;
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.viewHolders.keyAt(i);
            BcSmartspaceCard legacyCardAtPosition = getLegacyCardAtPosition(keyAt);
            if (legacyCardAtPosition != null) {
                legacyCardAtPosition.setPaddingRelative(num.intValue(), legacyCardAtPosition.getPaddingTop(), num.intValue(), legacyCardAtPosition.getPaddingBottom());
            }
            BaseTemplateCard templateCardAtPosition = getTemplateCardAtPosition(keyAt);
            if (templateCardAtPosition != null) {
                templateCardAtPosition.setPaddingRelative(num.intValue(), templateCardAtPosition.getPaddingTop(), num.intValue(), templateCardAtPosition.getPaddingBottom());
            }
        }
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setPrimaryTextColor(int i) {
        this.primaryTextColor = i;
        updateCurrentTextColor$1();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setScreenOn(boolean z) {
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = this.viewHolders;
            ViewHolder viewHolder = (ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null) {
                viewHolder.card.setScreenOn(z);
            }
        }
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTargets(List list) {
        setTargets(list, null);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.timeChangedDelegate = timeChangedDelegate;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setUiSurface(String str) {
        this.uiSurface = str;
    }

    public final void updateCurrentTextColor$1() {
        this.currentTextColor = ColorUtils.blendARGB(this.primaryTextColor, this.dozeColor, this._dozeAmount);
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = this.viewHolders;
            ViewHolder viewHolder = (ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null) {
                SmartspaceCard smartspaceCard = viewHolder.card;
                smartspaceCard.setPrimaryTextColor(this.currentTextColor);
                smartspaceCard.setDozeAmount$1(this._dozeAmount);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void updateTargetVisibility(java.lang.Runnable r10, boolean r11) {
        /*
            r9 = this;
            java.util.List r0 = r9.mediaTargets
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto Lb
            java.util.List r0 = r9.mediaTargets
            goto L16
        Lb:
            boolean r0 = r9.hasDifferentTargets
            if (r0 == 0) goto L12
            java.util.List r0 = r9._aodTargets
            goto L16
        L12:
            java.util.List r0 = r9.getLockscreenTargets()
        L16:
            java.util.List r1 = r9.getLockscreenTargets()
            java.util.List r2 = r9.smartspaceTargets
            r3 = 1052266988(0x3eb851ec, float:0.36)
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1
            r6 = 0
            if (r2 == r0) goto L38
            float r7 = r9._dozeAmount
            int r8 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r8 != 0) goto L2c
            goto L36
        L2c:
            int r7 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r7 < 0) goto L38
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r7 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r8 = com.google.android.systemui.smartspace.CardRecyclerViewAdapter.TransitionType.TO_AOD
            if (r7 != r8) goto L38
        L36:
            r7 = r5
            goto L39
        L38:
            r7 = r6
        L39:
            if (r2 == r1) goto L50
            float r2 = r9._dozeAmount
            r8 = 0
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 != 0) goto L43
            goto L4e
        L43:
            float r4 = r4 - r2
            int r2 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r2 < 0) goto L50
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r2 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r3 = com.google.android.systemui.smartspace.CardRecyclerViewAdapter.TransitionType.TO_LOCKSCREEN
            if (r2 != r3) goto L50
        L4e:
            r2 = r5
            goto L51
        L50:
            r2 = r6
        L51:
            java.lang.String r3 = "SsCardRecyclerViewAdapter"
            if (r7 == 0) goto L5d
            java.lang.String r4 = "Updating Smartspace targets to targets for AOD"
            android.util.Log.d(r3, r4)
            r9.smartspaceTargets = r0
            goto L66
        L5d:
            if (r2 == 0) goto L66
            java.lang.String r4 = "Updating Smartspace targets to targets for Lockscreen"
            android.util.Log.d(r3, r4)
            r9.smartspaceTargets = r1
        L66:
            if (r11 != 0) goto L6c
            if (r7 != 0) goto L6c
            if (r2 == 0) goto L7c
        L6c:
            android.util.SparseArray r11 = r9.viewHolders
            r11.clear()
            java.util.List r11 = r9.smartspaceTargets
            java.util.List r11 = kotlin.collections.CollectionsKt.toList(r11)
            androidx.recyclerview.widget.AsyncListDiffer r2 = r9.mDiffer
            r2.submitList(r11, r10)
        L7c:
            if (r0 == r1) goto L7f
            goto L80
        L7f:
            r5 = r6
        L80:
            r9.hasAodLockscreenTransition = r5
            com.android.systemui.plugins.BcSmartspaceConfigPlugin r10 = r9.configProvider
            boolean r10 = r10.isDefaultDateWeatherDisabled()
            if (r10 == 0) goto La3
            java.lang.String r10 = r9.uiSurface
            java.lang.String r11 = "home"
            boolean r10 = kotlin.text.StringsKt__StringsJVMKt.equals(r10, r11, r6)
            if (r10 != 0) goto La3
            java.util.List r10 = r9.smartspaceTargets
            boolean r10 = r10.isEmpty()
            if (r10 == 0) goto L9e
            r6 = 8
        L9e:
            com.google.android.systemui.smartspace.BcSmartspaceView r9 = r9.root
            com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils.updateVisibility(r9, r6)
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.CardRecyclerViewAdapter.updateTargetVisibility(java.lang.Runnable, boolean):void");
    }

    public final void setTargets(List list, BcSmartspaceView$$ExternalSyntheticLambda1 bcSmartspaceView$$ExternalSyntheticLambda1) {
        Bundle extras;
        this._aodTargets.clear();
        this._lockscreenTargets.clear();
        this.hasDifferentTargets = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            if (smartspaceTarget != null) {
                if (smartspaceTarget.getFeatureType() == 34 || (smartspaceTarget.getRemoteViews() == null && !isTemplateCard(smartspaceTarget) && smartspaceTarget.getFeatureType() == 1)) {
                    ClockEventController$$ExternalSyntheticOutline0.m(smartspaceTarget.getFeatureType(), "No card can be created for target: ", "SsCardRecyclerViewAdapter");
                } else {
                    SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
                    int i = (baseAction == null || (extras = baseAction.getExtras()) == null) ? 3 : extras.getInt("SCREEN_EXTRA", 3);
                    if ((i & 2) != 0) {
                        this._aodTargets.add(smartspaceTarget);
                    }
                    if ((i & 1) != 0) {
                        this._lockscreenTargets.add(smartspaceTarget);
                    }
                    if (i != 3) {
                        this.hasDifferentTargets = true;
                    }
                }
            }
        }
        if (!this.configProvider.isDefaultDateWeatherDisabled()) {
            addDefaultDateCardIfEmpty$1(this._aodTargets);
            addDefaultDateCardIfEmpty$1(this._lockscreenTargets);
        }
        updateTargetVisibility(bcSmartspaceView$$ExternalSyntheticLambda1, true);
    }

    public static /* synthetic */ void getAodTargets$annotations() {
    }

    public static /* synthetic */ void getConfigProvider$annotations() {
    }

    public static /* synthetic */ void getDataProvider$annotations() {
    }

    public static /* synthetic */ void getDozeAmount$annotations() {
    }

    public static /* synthetic */ void getHasAodLockscreenTransition$annotations() {
    }

    public static /* synthetic */ void getHasDifferentTargets$annotations() {
    }

    public static /* synthetic */ void getKeyguardBypassEnabled$annotations() {
    }

    public static /* synthetic */ void getLockscreenTargets$annotations() {
    }

    public static /* synthetic */ void getPrimaryTextColor$annotations() {
    }

    public static /* synthetic */ void getScreenOn$annotations() {
    }

    public static /* synthetic */ void getUiSurface$annotations() {
    }
}
