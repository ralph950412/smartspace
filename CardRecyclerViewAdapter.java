package com.google.android.systemui.smartspace;

import android.R;
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
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter$1;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.graphics.ColorUtils;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.assist.ui.InvocationLightsView$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class CardRecyclerViewAdapter extends RecyclerView.Adapter implements CardAdapter {
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
    public final AsyncListDiffer mDiffer;
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

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class DiffUtilItemCallback extends DiffUtil.Callback {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public final /* bridge */ /* synthetic */ boolean areContentsTheSame(Object obj, Object obj2) {
            return false;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public final boolean areItemsTheSame(Object obj, Object obj2) {
            return Intrinsics.areEqual(((SmartspaceTarget) obj).getSmartspaceTargetId(), ((SmartspaceTarget) obj2).getSmartspaceTargetId());
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class TransitionType {
        public static final /* synthetic */ TransitionType[] $VALUES;
        public static final TransitionType NOT_IN_TRANSITION;
        public static final TransitionType TO_AOD;
        public static final TransitionType TO_LOCKSCREEN;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public static TransitionType valueOf(String str) {
            return (TransitionType) Enum.valueOf(TransitionType.class, str);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public static TransitionType[] values() {
            return (TransitionType[]) $VALUES.clone();
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public SmartspaceCard card;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public CardRecyclerViewAdapter(BcSmartspaceView bcSmartspaceView, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        DiffUtilItemCallback diffUtilItemCallback = new DiffUtilItemCallback();
        synchronized (AsyncDifferConfig.Builder.sExecutorLock) {
            try {
                if (AsyncDifferConfig.Builder.sDiffExecutor == null) {
                    AsyncDifferConfig.Builder.sDiffExecutor = Executors.newFixedThreadPool(2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Executor executor = AsyncDifferConfig.Builder.sDiffExecutor;
        AsyncDifferConfig asyncDifferConfig = new AsyncDifferConfig();
        asyncDifferConfig.mBackgroundThreadExecutor = executor;
        asyncDifferConfig.mDiffCallback = diffUtilItemCallback;
        VarHandle.storeStoreFence();
        ListAdapter$1 listAdapter$1 = new ListAdapter$1();
        listAdapter$1.this$0 = this;
        VarHandle.storeStoreFence();
        AsyncListDiffer asyncListDiffer = new AsyncListDiffer(new AdapterListUpdateCallback(this), asyncDifferConfig);
        this.mDiffer = asyncListDiffer;
        ((CopyOnWriteArrayList) asyncListDiffer.mListeners).add(listAdapter$1);
        this.root = bcSmartspaceView;
        this.viewHolders = new SparseArray();
        this.smartspaceTargets = new ArrayList();
        this._aodTargets = new ArrayList();
        this._lockscreenTargets = new ArrayList();
        this.mediaTargets = new ArrayList();
        this.dozeColor = -1;
        int attrColor = GraphicsUtils.getAttrColor(R.attr.textColorPrimary, bcSmartspaceView.getContext());
        this.primaryTextColor = attrColor;
        this.currentTextColor = attrColor;
        this.configProvider = bcSmartspaceConfigPlugin;
        this.transitioningTo = TransitionType.NOT_IN_TRANSITION;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static boolean isTemplateCard(SmartspaceTarget smartspaceTarget) {
        return smartspaceTarget.getTemplateData() != null && BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void addDefaultDateCardIfEmpty$1(List list) {
        if (list.isEmpty()) {
            BcSmartspaceView bcSmartspaceView = this.root;
            list.add(new SmartspaceTarget.Builder("date_card_794317_92634", new ComponentName(bcSmartspaceView.getContext(), (Class<?>) CardRecyclerViewAdapter.class), bcSmartspaceView.getContext().getUser()).setFeatureType(1).setTemplateData(new BaseTemplateData.Builder(1).build()).build());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceCard getCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.card;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final int getCount() {
        return this.smartspaceTargets.size();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final float getDozeAmount() {
        return this._dozeAmount;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasAodLockscreenTransition() {
        return this.hasAodLockscreenTransition;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasDifferentTargets() {
        return this.hasDifferentTargets;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mDiffer.mReadOnlyList.size();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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
            return num != null ? num.intValue() : com.android.wm.shell.R.layout.smartspace_card;
        }
        templateData.getClass();
        BaseTemplateData.SubItemInfo primaryItem = templateData.getPrimaryItem();
        if (primaryItem == null) {
            return com.android.wm.shell.R.layout.smartspace_base_template_card_with_date;
        }
        if (SmartspaceUtils.isEmpty(primaryItem.getText()) && primaryItem.getIcon() == null) {
            return com.android.wm.shell.R.layout.smartspace_base_template_card_with_date;
        }
        BaseTemplateData.SubItemLoggingInfo loggingInfo = primaryItem.getLoggingInfo();
        if (loggingInfo != null && loggingInfo.getFeatureType() == 1) {
            return com.android.wm.shell.R.layout.smartspace_base_template_card_with_date;
        }
        Integer num2 = (Integer) BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.get(Integer.valueOf(templateData.getTemplateType()));
        return num2 != null ? num2.intValue() : com.android.wm.shell.R.layout.smartspace_base_template_card;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceCard getLegacyCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BcSmartspaceCard) {
            return (BcSmartspaceCard) smartspaceCard;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final List getLockscreenTargets() {
        return (this.mediaTargets.isEmpty() || !this.keyguardBypassEnabled) ? this._lockscreenTargets : this.mediaTargets;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceRemoteViewsCard getRemoteViewsCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BcSmartspaceRemoteViewsCard) {
            return (BcSmartspaceRemoteViewsCard) smartspaceCard;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final List getSmartspaceTargets() {
        return this.smartspaceTargets;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceTarget getTargetAtPosition(int i) {
        if (i < 0 || i >= getItemCount()) {
            return null;
        }
        return (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BaseTemplateCard getTemplateCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if (smartspaceCard instanceof BaseTemplateCard) {
            return (BaseTemplateCard) smartspaceCard;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final String getUiSurface() {
        return this.uiSurface;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
        smartspaceTarget.getClass();
        boolean isTemplateCard = isTemplateCard(smartspaceTarget);
        int create = InstanceId.create(smartspaceTarget);
        int featureType = smartspaceTarget.getFeatureType();
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.uiSurface, this._dozeAmount);
        int size = this.smartspaceTargets.size();
        this.root.getContext().getPackageManager();
        BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo = isTemplateCard ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo.mInstanceId = create;
        bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo.mRank = i;
        bcSmartspaceCardLoggingInfo.mCardinality = size;
        bcSmartspaceCardLoggingInfo.mFeatureType = featureType;
        bcSmartspaceCardLoggingInfo.mReceivedLatency = 0;
        bcSmartspaceCardLoggingInfo.mUid = -1;
        bcSmartspaceCardLoggingInfo.mSubcardInfo = createSubcardLoggingInfo;
        bcSmartspaceCardLoggingInfo.mDimensionalInfo = createDimensionalLoggingInfo;
        VarHandle.storeStoreFence();
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
            BaseTemplateCard baseTemplateCard = (BaseTemplateCard) smartspaceCard;
            Handler handler = this.bgHandler;
            if (handler == null) {
                handler = null;
            }
            baseTemplateCard.mBgHandler = handler;
            IcuDateTextView icuDateTextView = baseTemplateCard.mDateView;
            if (icuDateTextView != null) {
                icuDateTextView.mBgHandler = handler;
            }
        } else if (!(smartspaceCard instanceof BcSmartspaceCard)) {
            Log.w("SsCardRecyclerViewAdapter", "No legacy card view can be binded");
            return;
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.dataProvider;
        smartspaceCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        smartspaceCard.setPrimaryTextColor(this.currentTextColor);
        smartspaceCard.setDozeAmount$1(this._dozeAmount);
        this.viewHolders.put(i, viewHolder2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r8v10, resolved type: com.google.android.systemui.smartspace.BcSmartspaceRemoteViewsCard */
    /* JADX DEBUG: Multi-variable search result rejected for r8v8, resolved type: com.google.android.systemui.smartspace.BcSmartspaceCard */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        BaseTemplateCard baseTemplateCard;
        Set set = templateSecondaryCardResourceIdSet;
        Integer num = null;
        if (set.contains(Integer.valueOf(i)) || i == com.android.wm.shell.R.layout.smartspace_base_template_card_with_date || i == com.android.wm.shell.R.layout.smartspace_base_template_card) {
            if (set.contains(Integer.valueOf(i))) {
                num = Integer.valueOf(i);
                i = com.android.wm.shell.R.layout.smartspace_base_template_card;
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
            Integer num2 = this.nonRemoteViewsHorizontalPadding;
            if (num2 != null) {
                int intValue = num2.intValue();
                baseTemplateCard2.setPaddingRelative(intValue, baseTemplateCard2.getPaddingTop(), intValue, baseTemplateCard2.getPaddingBottom());
            }
            BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate = this.timeChangedDelegate;
            IcuDateTextView icuDateTextView2 = baseTemplateCard2.mDateView;
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
            if (set2.contains(Integer.valueOf(i)) || i == com.android.wm.shell.R.layout.smartspace_card) {
                if (set2.contains(Integer.valueOf(i))) {
                    num = Integer.valueOf(i);
                    i = com.android.wm.shell.R.layout.smartspace_card;
                }
                LayoutInflater from2 = LayoutInflater.from(viewGroup.getContext());
                BcSmartspaceCard bcSmartspaceCard = (BcSmartspaceCard) from2.inflate(i, viewGroup, false);
                bcSmartspaceCard.mUiSurface = this.uiSurface;
                Integer num3 = this.nonRemoteViewsHorizontalPadding;
                if (num3 != null) {
                    int intValue2 = num3.intValue();
                    bcSmartspaceCard.setPaddingRelative(intValue2, bcSmartspaceCard.getPaddingTop(), intValue2, bcSmartspaceCard.getPaddingBottom());
                }
                bcSmartspaceCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                baseTemplateCard = bcSmartspaceCard;
                if (num != null) {
                    bcSmartspaceCard.setSecondaryCard((BcSmartspaceCardSecondary) from2.inflate(num.intValue(), (ViewGroup) bcSmartspaceCard, false));
                    baseTemplateCard = bcSmartspaceCard;
                }
            } else {
                BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = new BcSmartspaceRemoteViewsCard(viewGroup.getContext());
                bcSmartspaceRemoteViewsCard.mUiSurface = this.uiSurface;
                bcSmartspaceRemoteViewsCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                baseTemplateCard = bcSmartspaceRemoteViewsCard;
            }
        }
        ViewHolder viewHolder = new ViewHolder(baseTemplateCard.getView());
        viewHolder.card = baseTemplateCard;
        VarHandle.storeStoreFence();
        return viewHolder;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setBgHandler(Handler handler) {
        this.bgHandler = handler;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.configProvider = bcSmartspaceConfigPlugin;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        this.dataProvider = bcSmartspaceDataPlugin;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setDozeAmount(float f) {
        this._dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        updateTargetVisibility(null, false);
        updateCurrentTextColor$1();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setKeyguardBypassEnabled(boolean z) {
        this.keyguardBypassEnabled = z;
        updateTargetVisibility(null, false);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mediaTargets.clear();
        if (smartspaceTarget != null) {
            this.mediaTargets.add(smartspaceTarget);
        }
        updateTargetVisibility(null, true);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setPrimaryTextColor(int i) {
        this.primaryTextColor = i;
        updateCurrentTextColor$1();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTargets(List list) {
        setTargets(list, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.timeChangedDelegate = timeChangedDelegate;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setUiSurface(String str) {
        this.uiSurface = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTargetVisibility(Runnable runnable, boolean z) {
        boolean z2;
        boolean z3;
        List lockscreenTargets = !this.mediaTargets.isEmpty() ? this.mediaTargets : this.hasDifferentTargets ? this._aodTargets : getLockscreenTargets();
        List lockscreenTargets2 = getLockscreenTargets();
        List list = this.smartspaceTargets;
        if (list != lockscreenTargets) {
            float f = this._dozeAmount;
            if (f == 1.0f || (f >= 0.36f && this.transitioningTo == TransitionType.TO_AOD)) {
                z2 = true;
                if (list != lockscreenTargets2) {
                    float f2 = this._dozeAmount;
                    if (f2 == 0.0f || (1.0f - f2 >= 0.36f && this.transitioningTo == TransitionType.TO_LOCKSCREEN)) {
                        z3 = true;
                        if (z2) {
                            Log.d("SsCardRecyclerViewAdapter", "Updating Smartspace targets to targets for AOD");
                            this.smartspaceTargets = lockscreenTargets;
                        } else if (z3) {
                            Log.d("SsCardRecyclerViewAdapter", "Updating Smartspace targets to targets for Lockscreen");
                            this.smartspaceTargets = lockscreenTargets2;
                        }
                        if (!z || z2 || z3) {
                            this.viewHolders.clear();
                            this.mDiffer.submitList(CollectionsKt.toList(this.smartspaceTargets), runnable);
                        }
                        this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
                        if (!this.configProvider.isDefaultDateWeatherDisabled() || StringsKt__StringsJVMKt.equals(this.uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, false)) {
                            return;
                        }
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.root, this.smartspaceTargets.isEmpty() ? 8 : 0);
                        return;
                    }
                }
                z3 = false;
                if (z2) {
                }
                if (!z) {
                }
                this.viewHolders.clear();
                this.mDiffer.submitList(CollectionsKt.toList(this.smartspaceTargets), runnable);
                this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
                if (this.configProvider.isDefaultDateWeatherDisabled()) {
                    return;
                } else {
                    return;
                }
            }
        }
        z2 = false;
        if (list != lockscreenTargets2) {
        }
        z3 = false;
        if (z2) {
        }
        if (!z) {
        }
        this.viewHolders.clear();
        this.mDiffer.submitList(CollectionsKt.toList(this.smartspaceTargets), runnable);
        this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
        if (this.configProvider.isDefaultDateWeatherDisabled()) {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public final void setTargets(List list, BcSmartspaceView$$ExternalSyntheticLambda1 bcSmartspaceView$$ExternalSyntheticLambda1) {
        Bundle extras;
        this._aodTargets.clear();
        this._lockscreenTargets.clear();
        this.hasDifferentTargets = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            if (smartspaceTarget.getFeatureType() == 34 || (smartspaceTarget.getRemoteViews() == null && !isTemplateCard(smartspaceTarget) && smartspaceTarget.getFeatureType() == 1)) {
                InvocationLightsView$$ExternalSyntheticOutline0.m(smartspaceTarget.getFeatureType(), "No card can be created for target: ", "SsCardRecyclerViewAdapter");
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
