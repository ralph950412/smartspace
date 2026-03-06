package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.android.systemui.controls.management.StructureAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
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
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class CardRecyclerViewAdapter extends RecyclerView.Adapter implements CardAdapter {
    public static final Set legacySecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartSpaceUtil.FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP.values());
    public static final Set templateSecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.values());
    public final List _aodTargets;
    public float _dozeAmount;
    public boolean _isBackgroundEnabled;
    public final List _lockscreenTargets;
    public final Drawable backgroundDrawable;
    public final Drawable backgroundOutlineDrawable;
    public Handler bgHandler;
    public final int bgNonRemoteViewsHorizontalPadding;
    public BcSmartspaceConfigPlugin configProvider;
    public Drawable currentBackgroundDrawable;
    public int currentTextColor;
    public BcSmartspaceDataPlugin dataProvider;
    public final int defaultNonRemoteViewsPaddingStart;
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
    public final GradientDrawable solidBackgroundDrawable;
    public BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate;
    public TransitionType transitioningTo;
    public String uiSurface;
    public final SparseArray viewHolders;

    /* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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
    /* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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

    /* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public SmartspaceCard card;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public final void setBackground(Drawable drawable) {
            View view = this.itemView;
            if (view instanceof BcSmartspaceRemoteViewsCard) {
                return;
            }
            ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
            View childAt = viewGroup != null ? viewGroup.getChildAt(0) : null;
            if (childAt != null) {
                childAt.setBackground(drawable);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public CardRecyclerViewAdapter(BcSmartspaceView bcSmartspaceView, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        GradientDrawable gradientDrawable;
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
        this.backgroundOutlineDrawable = bcSmartspaceView.getContext().getDrawable(R.drawable.bg_non_remoteviews_card_outline);
        this.backgroundDrawable = bcSmartspaceView.getContext().getDrawable(R.drawable.bg_non_remoteviews_card);
        try {
            gradientDrawable = getSolidBackgroundDrawable();
        } catch (IllegalStateException e) {
            Log.w("SsCardRecyclerViewAdapter", "Failed to get solid background drawable", e);
            gradientDrawable = null;
        }
        this.solidBackgroundDrawable = gradientDrawable;
        this.smartspaceTargets = new ArrayList();
        this._aodTargets = new ArrayList();
        this._lockscreenTargets = new ArrayList();
        this.mediaTargets = new ArrayList();
        this.dozeColor = -1;
        int attrColor = GraphicsUtils.getAttrColor(android.R.attr.textColorPrimary, this.root.getContext());
        this.primaryTextColor = attrColor;
        this.currentTextColor = attrColor;
        this.configProvider = bcSmartspaceConfigPlugin;
        this.bgNonRemoteViewsHorizontalPadding = this.root.getContext().getResources().getDimensionPixelSize(R.dimen.bg_non_remoteviews_card_padding_horizontal);
        this.defaultNonRemoteViewsPaddingStart = this.root.getContext().getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start);
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
        BaseTemplateData.SubItemLoggingInfo loggingInfo = primaryItem.getLoggingInfo();
        if (loggingInfo != null && loggingInfo.getFeatureType() == 1) {
            return R.layout.smartspace_base_template_card_with_date;
        }
        Integer num2 = (Integer) BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.get(Integer.valueOf(templateData.getTemplateType()));
        return num2 != null ? num2.intValue() : R.layout.smartspace_base_template_card;
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
    public final int getNonRemoteViewsPaddingEnd() {
        if (this._isBackgroundEnabled) {
            return this.bgNonRemoteViewsHorizontalPadding;
        }
        Integer num = this.nonRemoteViewsHorizontalPadding;
        if (num == null) {
            return 0;
        }
        num.getClass();
        return num.intValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int getNonRemoteViewsPaddingStart() {
        if (this._isBackgroundEnabled) {
            return this.bgNonRemoteViewsHorizontalPadding;
        }
        Integer num = this.nonRemoteViewsHorizontalPadding;
        if (num == null) {
            return this.defaultNonRemoteViewsPaddingStart;
        }
        num.getClass();
        return num.intValue();
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
    public final GradientDrawable getSolidBackgroundDrawable() {
        GradientDrawable gradientDrawable = this.solidBackgroundDrawable;
        if (gradientDrawable != null) {
            return gradientDrawable;
        }
        Drawable drawable = this.backgroundDrawable;
        if (drawable == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Background drawable is null");
            return null;
        }
        if (!(drawable instanceof LayerDrawable)) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Background drawable isn't a LayerDrawable");
            return null;
        }
        Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(R.id.solid);
        if (findDrawableByLayerId == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Solid background drawable is null");
            return null;
        }
        if (findDrawableByLayerId instanceof GradientDrawable) {
            return (GradientDrawable) findDrawableByLayerId;
        }
        Buffer$$ExternalSyntheticBUOutline0.m$1("Solid background drawable isn't a LayerDrawable");
        return null;
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
    public final boolean needToSetToLockscreenTargets$1() {
        float f = this._dozeAmount;
        if (f == 0.0f) {
            return true;
        }
        return 1.0f - f >= 0.36f && this.transitioningTo == TransitionType.TO_LOCKSCREEN;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void onBackgroundToggled(boolean z) {
        this._isBackgroundEnabled = z;
        refreshCardBackground();
        refreshCardPaddings();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.mDiffer.mReadOnlyList.get(i);
        smartspaceTarget.getClass();
        boolean z = smartspaceTarget.getRemoteViews() != null;
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
        if (z) {
            if (!(smartspaceCard instanceof BcSmartspaceRemoteViewsCard)) {
                Log.w("SsCardRecyclerViewAdapter", "[rmv] No RemoteViews card view can be binded");
                return;
            }
            Log.d("SsCardRecyclerViewAdapter", "[rmv] Refreshing RemoteViews card");
        } else if (isTemplateCard) {
            BaseTemplateData templateData = smartspaceTarget.getTemplateData();
            if (templateData == null) {
                Buffer$$ExternalSyntheticBUOutline0.m$1("Required value was null.");
                return;
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
            baseTemplateCard.setPaddingRelative(getNonRemoteViewsPaddingStart(), baseTemplateCard.getPaddingTop(), getNonRemoteViewsPaddingEnd(), baseTemplateCard.getPaddingBottom());
        } else if (!(smartspaceCard instanceof BcSmartspaceCard)) {
            Log.w("SsCardRecyclerViewAdapter", "No legacy card view can be binded");
            return;
        } else {
            BcSmartspaceCard bcSmartspaceCard = (BcSmartspaceCard) smartspaceCard;
            bcSmartspaceCard.setPaddingRelative(getNonRemoteViewsPaddingStart(), bcSmartspaceCard.getPaddingTop(), getNonRemoteViewsPaddingEnd(), bcSmartspaceCard.getPaddingBottom());
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.dataProvider;
        smartspaceCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        viewHolder2.setBackground(this._isBackgroundEnabled ? this.currentBackgroundDrawable : null);
        smartspaceCard.setPrimaryTextColor(this.currentTextColor);
        smartspaceCard.setDozeAmount(this._dozeAmount);
        this.viewHolders.put(i, viewHolder2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r9v11, resolved type: com.google.android.systemui.smartspace.BcSmartspaceRemoteViewsCard */
    /* JADX DEBUG: Multi-variable search result rejected for r9v9, resolved type: com.google.android.systemui.smartspace.BcSmartspaceCard */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        Integer num;
        BaseTemplateCard baseTemplateCard;
        FrameLayout frameLayout;
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
                    Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
                    return null;
                }
                icuDateTextView.mUpdatesOnAod = true;
            }
            BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate = this.timeChangedDelegate;
            IcuDateTextView icuDateTextView2 = baseTemplateCard2.mDateView;
            if (icuDateTextView2 != null) {
                if (icuDateTextView2.isAttachedToWindow()) {
                    Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
                    return null;
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
            if (set2.contains(Integer.valueOf(i)) || i == R.layout.smartspace_card) {
                if (set2.contains(Integer.valueOf(i))) {
                    num2 = Integer.valueOf(i);
                    i = R.layout.smartspace_card;
                }
                LayoutInflater from2 = LayoutInflater.from(viewGroup.getContext());
                BcSmartspaceCard bcSmartspaceCard = (BcSmartspaceCard) from2.inflate(i, viewGroup, false);
                bcSmartspaceCard.mUiSurface = this.uiSurface;
                bcSmartspaceCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                baseTemplateCard = bcSmartspaceCard;
                if (num2 != null) {
                    bcSmartspaceCard.setSecondaryCard((BcSmartspaceCardSecondary) from2.inflate(num2.intValue(), (ViewGroup) bcSmartspaceCard, false));
                    baseTemplateCard = bcSmartspaceCard;
                }
            } else {
                BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = new BcSmartspaceRemoteViewsCard(viewGroup.getContext());
                bcSmartspaceRemoteViewsCard.mUiSurface = this.uiSurface;
                bcSmartspaceRemoteViewsCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                baseTemplateCard = bcSmartspaceRemoteViewsCard;
            }
        }
        if (baseTemplateCard instanceof BcSmartspaceRemoteViewsCard) {
            frameLayout = (BcSmartspaceRemoteViewsCard) baseTemplateCard;
        } else {
            FrameLayout frameLayout2 = new FrameLayout(viewGroup.getContext());
            frameLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            View view = new View(viewGroup.getContext());
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -1);
            marginLayoutParams.topMargin = StructureAdapter$$ExternalSyntheticOutline0.m(view, R.dimen.background_top_padding);
            marginLayoutParams.bottomMargin = StructureAdapter$$ExternalSyntheticOutline0.m(view, R.dimen.background_bottom_padding);
            view.setLayoutParams(marginLayoutParams);
            frameLayout2.addView(view);
            frameLayout2.addView(baseTemplateCard.getView());
            frameLayout = frameLayout2;
        }
        ViewHolder viewHolder = new ViewHolder(frameLayout);
        viewHolder.card = baseTemplateCard;
        VarHandle.storeStoreFence();
        return viewHolder;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void refreshCardBackground() {
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(this.viewHolders.keyAt(i));
            if (viewHolder != null) {
                viewHolder.setBackground(this._isBackgroundEnabled ? this.currentBackgroundDrawable : null);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void refreshCardPaddings() {
        int nonRemoteViewsPaddingStart = getNonRemoteViewsPaddingStart();
        int nonRemoteViewsPaddingEnd = getNonRemoteViewsPaddingEnd();
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.viewHolders.keyAt(i);
            BcSmartspaceCard legacyCardAtPosition = getLegacyCardAtPosition(keyAt);
            if (legacyCardAtPosition != null) {
                legacyCardAtPosition.setPaddingRelative(nonRemoteViewsPaddingStart, legacyCardAtPosition.getPaddingTop(), nonRemoteViewsPaddingEnd, legacyCardAtPosition.getPaddingBottom());
            }
            BaseTemplateCard templateCardAtPosition = getTemplateCardAtPosition(keyAt);
            if (templateCardAtPosition != null) {
                templateCardAtPosition.setPaddingRelative(nonRemoteViewsPaddingStart, templateCardAtPosition.getPaddingTop(), nonRemoteViewsPaddingEnd, templateCardAtPosition.getPaddingBottom());
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void resetListIfNeeded() {
        BcSmartspaceView bcSmartspaceView = this.root;
        BcSmartspaceView bcSmartspaceView2 = bcSmartspaceView != null ? bcSmartspaceView : null;
        boolean z = false;
        if ((bcSmartspaceView2 != null ? bcSmartspaceView2.getSelectedPage() : 0) != 0) {
            return;
        }
        if (bcSmartspaceView == null) {
            bcSmartspaceView = null;
        }
        if ((bcSmartspaceView != null ? bcSmartspaceView.mScrollState : 0) != 0) {
            return;
        }
        AsyncListDiffer asyncListDiffer = this.mDiffer;
        if (asyncListDiffer.mReadOnlyList.size() == 1 && this.smartspaceTargets.size() == 1) {
            z = true;
        }
        if (z) {
            return;
        }
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) CollectionsKt.firstOrNull(asyncListDiffer.mReadOnlyList);
        String smartspaceTargetId = smartspaceTarget != null ? smartspaceTarget.getSmartspaceTargetId() : null;
        SmartspaceTarget smartspaceTarget2 = (SmartspaceTarget) CollectionsKt.firstOrNull(this.smartspaceTargets);
        if (Intrinsics.areEqual(smartspaceTargetId, smartspaceTarget2 != null ? smartspaceTarget2.getSmartspaceTargetId() : null)) {
            return;
        }
        asyncListDiffer.submitList(null, null);
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
    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0052  */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDozeAmount(float f) {
        boolean z;
        this._dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        boolean z2 = false;
        updateTargetVisibility(null, false);
        Drawable drawable = this.currentBackgroundDrawable;
        if (drawable != this.backgroundOutlineDrawable) {
            float f3 = this._dozeAmount;
            if (f3 == 1.0f || (f3 >= 0.36f && this.transitioningTo == TransitionType.TO_AOD)) {
                z = true;
                if (drawable != this.backgroundDrawable && needToSetToLockscreenTargets$1()) {
                    z2 = true;
                }
                if (!z) {
                    this.currentBackgroundDrawable = this.backgroundOutlineDrawable;
                    refreshCardBackground();
                } else if (z2) {
                    this.currentBackgroundDrawable = this.backgroundDrawable;
                    refreshCardBackground();
                }
                updateCurrentTextColor$1();
            }
        }
        z = false;
        if (drawable != this.backgroundDrawable) {
            z2 = true;
        }
        if (!z) {
        }
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
        if (this._isBackgroundEnabled) {
            return;
        }
        refreshCardPaddings();
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
                smartspaceCard.setDozeAmount(this._dozeAmount);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTargetVisibility(Runnable runnable, boolean z) {
        boolean z2;
        List lockscreenTargets = !this.mediaTargets.isEmpty() ? this.mediaTargets : this.hasDifferentTargets ? this._aodTargets : getLockscreenTargets();
        List lockscreenTargets2 = getLockscreenTargets();
        List list = this.smartspaceTargets;
        if (list != lockscreenTargets) {
            float f = this._dozeAmount;
            if (f == 1.0f || (f >= 0.36f && this.transitioningTo == TransitionType.TO_AOD)) {
                z2 = true;
                boolean z3 = list == lockscreenTargets2 && needToSetToLockscreenTargets$1();
                if (!z2) {
                    Log.d("SsCardRecyclerViewAdapter", "Updating Smartspace targets to targets for AOD");
                    this.smartspaceTargets = lockscreenTargets;
                } else if (z3) {
                    Log.d("SsCardRecyclerViewAdapter", "Updating Smartspace targets to targets for Lockscreen");
                    this.smartspaceTargets = lockscreenTargets2;
                }
                if (!z || z2 || z3) {
                    this.viewHolders.clear();
                    resetListIfNeeded();
                    this.mDiffer.submitList(CollectionsKt.toList(this.smartspaceTargets), runnable);
                }
                this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
                if (this.configProvider.isDefaultDateWeatherDisabled() || StringsKt__StringsJVMKt.equals(this.uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, false)) {
                }
                BcSmartspaceTemplateDataUtils.updateVisibility(this.root, this.smartspaceTargets.isEmpty() ? 8 : 0);
                return;
            }
        }
        z2 = false;
        if (list == lockscreenTargets2) {
        }
        if (!z2) {
        }
        if (!z) {
        }
        this.viewHolders.clear();
        resetListIfNeeded();
        this.mDiffer.submitList(CollectionsKt.toList(this.smartspaceTargets), runnable);
        this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
        if (this.configProvider.isDefaultDateWeatherDisabled()) {
        }
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

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTargets(List list) {
        setTargets(list, null);
    }
}
