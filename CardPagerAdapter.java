package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.content.ComponentName;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.lang.invoke.VarHandle;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class CardPagerAdapter implements CardAdapter {
    public static final Companion Companion = new Companion();
    public List _aodTargets;
    public List _lockscreenTargets;
    public Handler bgHandler;
    public BcSmartspaceConfigPlugin configProvider;
    public int currentTextColor;
    public BcSmartspaceDataPlugin dataProvider;
    public float dozeAmount;
    public int dozeColor;
    public LazyServerFlagLoader enableCardRecycling;
    public LazyServerFlagLoader enableReducedCardRecycling;
    public boolean hasAodLockscreenTransition;
    public boolean hasDifferentTargets;
    public boolean keyguardBypassEnabled;
    public DataSetObservable mObservable;
    public DataSetObserver mViewPagerObserver;
    public List mediaTargets;
    public Integer nonRemoteViewsHorizontalPadding;
    public float previousDozeAmount;
    public int primaryTextColor;
    public SparseArray recycledCards;
    public SparseArray recycledLegacyCards;
    public SparseArray recycledRemoteViewsCards;
    public BcSmartspaceView root;
    public List smartspaceTargets;
    public BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate;
    public TransitionType transitioningTo;
    public String uiSurface;
    public SparseArray viewHolders;

    /* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
    public final class Companion {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public static boolean useRecycledViewForAction(SmartspaceAction smartspaceAction, SmartspaceAction smartspaceAction2) {
            Map map = BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES;
            if (smartspaceAction == null && smartspaceAction2 == null) {
                return true;
            }
            if (smartspaceAction == null || smartspaceAction2 == null) {
                return false;
            }
            smartspaceAction.getClass();
            Bundle extras = smartspaceAction.getExtras();
            smartspaceAction2.getClass();
            Bundle extras2 = smartspaceAction2.getExtras();
            if (extras == null && extras2 == null) {
                return true;
            }
            Bundle extras3 = smartspaceAction.getExtras();
            Bundle extras4 = smartspaceAction2.getExtras();
            if (extras3 == null || extras4 == null) {
                return false;
            }
            Bundle extras5 = smartspaceAction.getExtras();
            Set<String> keySet = extras5 != null ? extras5.keySet() : null;
            Bundle extras6 = smartspaceAction2.getExtras();
            return Intrinsics.areEqual(keySet, extras6 != null ? extras6.keySet() : null);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public static boolean useRecycledViewForActionsList(List list, List list2) {
            Map map = BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES;
            if (list == null && list2 == null) {
                return true;
            }
            if (list != null && list2 != null) {
                list.getClass();
                int size = list.size();
                list2.getClass();
                if (size == list2.size()) {
                    IntStream range = IntStream.range(0, list.size());
                    CardPagerAdapter$Companion$useRecycledViewForActionsList$1 cardPagerAdapter$Companion$useRecycledViewForActionsList$1 = new CardPagerAdapter$Companion$useRecycledViewForActionsList$1();
                    cardPagerAdapter$Companion$useRecycledViewForActionsList$1.$newActionsList = list;
                    cardPagerAdapter$Companion$useRecycledViewForActionsList$1.$recycledActionsList = list2;
                    VarHandle.storeStoreFence();
                    if (range.allMatch(cardPagerAdapter$Companion$useRecycledViewForActionsList$1)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public final int getBaseLegacyCardRes(int i) {
            if (i != -2 && i != -1) {
                if (i == 1) {
                    return 0;
                }
                if (i != 2 && i != 3 && i != 4 && i != 6 && i != 18 && i != 20 && i != 30 && i != 9 && i != 10) {
                    switch (i) {
                    }
                    return R.layout.smartspace_card;
                }
            }
            return R.layout.smartspace_card;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public final boolean useRecycledViewForNewTarget(SmartspaceTarget smartspaceTarget, SmartspaceTarget smartspaceTarget2) {
            if (smartspaceTarget2 == null || !Intrinsics.areEqual(smartspaceTarget.getSmartspaceTargetId(), smartspaceTarget2.getSmartspaceTargetId()) || !useRecycledViewForAction(smartspaceTarget.getHeaderAction(), smartspaceTarget2.getHeaderAction()) || !useRecycledViewForAction(smartspaceTarget.getBaseAction(), smartspaceTarget2.getBaseAction()) || !useRecycledViewForActionsList(smartspaceTarget.getActionChips(), smartspaceTarget2.getActionChips()) || !useRecycledViewForActionsList(smartspaceTarget.getIconGrid(), smartspaceTarget2.getIconGrid())) {
                return false;
            }
            BaseTemplateData templateData = smartspaceTarget.getTemplateData();
            BaseTemplateData templateData2 = smartspaceTarget2.getTemplateData();
            if (templateData == null && templateData2 == null) {
                return true;
            }
            return (templateData == null || templateData2 == null || !Intrinsics.areEqual(templateData, templateData2)) ? false : true;
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
    public final class ViewHolder {
        public final BaseTemplateCard card;
        public final BcSmartspaceCard legacyCard;
        public final int position;
        public final BcSmartspaceRemoteViewsCard remoteViewsCard;
        public SmartspaceTarget target;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public ViewHolder(int i, BcSmartspaceCard bcSmartspaceCard, SmartspaceTarget smartspaceTarget, BaseTemplateCard baseTemplateCard, BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard) {
            this.position = i;
            this.legacyCard = bcSmartspaceCard;
            this.target = smartspaceTarget;
            this.card = baseTemplateCard;
            this.remoteViewsCard = bcSmartspaceRemoteViewsCard;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static final int getBaseLegacyCardRes(int i) {
        return Companion.getBaseLegacyCardRes(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static final boolean useRecycledViewForNewTarget(SmartspaceTarget smartspaceTarget, SmartspaceTarget smartspaceTarget2) {
        return Companion.useRecycledViewForNewTarget(smartspaceTarget, smartspaceTarget2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void addDefaultDateCardIfEmpty(List list) {
        BcSmartspaceView bcSmartspaceView = this.root;
        if (list.isEmpty()) {
            list.add(new SmartspaceTarget.Builder("date_card_794317_92634", new ComponentName(bcSmartspaceView.getContext(), (Class<?>) CardPagerAdapter.class), bcSmartspaceView.getContext().getUser()).setFeatureType(1).setTemplateData(new BaseTemplateData.Builder(1).build()).build());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        LazyServerFlagLoader lazyServerFlagLoader = this.enableCardRecycling;
        ViewHolder viewHolder = (ViewHolder) obj;
        BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
        BaseTemplateCard baseTemplateCard = viewHolder.card;
        if (bcSmartspaceCard != null) {
            SmartspaceTarget smartspaceTarget = bcSmartspaceCard.mTarget;
            if (smartspaceTarget != null && lazyServerFlagLoader.get()) {
                this.recycledLegacyCards.put(BcSmartSpaceUtil.getFeatureType(smartspaceTarget), bcSmartspaceCard);
            }
            viewGroup.removeView(bcSmartspaceCard);
        }
        if (baseTemplateCard != null) {
            SmartspaceTarget smartspaceTarget2 = baseTemplateCard.mTarget;
            if (smartspaceTarget2 != null && lazyServerFlagLoader.get()) {
                this.recycledCards.put(smartspaceTarget2.getFeatureType(), baseTemplateCard);
            }
            viewGroup.removeView(baseTemplateCard);
        }
        BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = viewHolder.remoteViewsCard;
        if (bcSmartspaceRemoteViewsCard != null) {
            if (lazyServerFlagLoader.get()) {
                Log.d("SsCardPagerAdapter", "[rmv] Caching RemoteViews card");
                this.recycledRemoteViewsCards.put(BcSmartSpaceUtil.getFeatureType(viewHolder.target), bcSmartspaceRemoteViewsCard);
            }
            Log.d("SsCardPagerAdapter", "[rmv] Removing RemoteViews card");
            viewGroup.removeView(bcSmartspaceRemoteViewsCard);
        }
        if (this.viewHolders.get(i) == viewHolder) {
            this.viewHolders.remove(i);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceCard getCardAtPosition(int i) {
        BaseTemplateCard baseTemplateCard;
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null && (baseTemplateCard = viewHolder.card) != null) {
            return baseTemplateCard;
        }
        BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
        return bcSmartspaceCard != null ? bcSmartspaceCard : viewHolder.remoteViewsCard;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final int getCount() {
        return this.smartspaceTargets.size();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final float getDozeAmount() {
        return this.dozeAmount;
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
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceCard getLegacyCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.legacyCard;
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
        if (viewHolder != null) {
            return viewHolder.remoteViewsCard;
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
        if (this.smartspaceTargets.isEmpty() || i < 0 || i >= this.smartspaceTargets.size()) {
            return null;
        }
        return (SmartspaceTarget) this.smartspaceTargets.get(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BaseTemplateCard getTemplateCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.card;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final String getUiSurface() {
        return this.uiSurface;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void notifyDataSetChanged() {
        synchronized (this) {
            try {
                DataSetObserver dataSetObserver = this.mViewPagerObserver;
                if (dataSetObserver != null) {
                    dataSetObserver.onChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mObservable.notifyChanged();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void onBindViewHolder(ViewHolder viewHolder) {
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.smartspaceTargets.get(viewHolder.position);
        boolean containsValidTemplateType = BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
        int create = InstanceId.create(smartspaceTarget);
        int featureType = smartspaceTarget.getFeatureType();
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.uiSurface, this.dozeAmount);
        int i = viewHolder.position;
        int size = this.smartspaceTargets.size();
        this.root.getContext().getPackageManager();
        BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo = containsValidTemplateType ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
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
        if (smartspaceTarget.getRemoteViews() != null) {
            BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = viewHolder.remoteViewsCard;
            if (bcSmartspaceRemoteViewsCard == null) {
                Log.w("SsCardPagerAdapter", "[rmv] No RemoteViews card view can be binded");
                return;
            }
            Log.d("SsCardPagerAdapter", "[rmv] Refreshing RemoteViews card");
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.dataProvider;
            bcSmartspaceRemoteViewsCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
            return;
        }
        if (!containsValidTemplateType) {
            BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
            if (bcSmartspaceCard == null) {
                Log.w("SsCardPagerAdapter", "No legacy card view can be binded");
                return;
            }
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.dataProvider;
            bcSmartspaceCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin2 != null ? bcSmartspaceDataPlugin2.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
            bcSmartspaceCard.setPrimaryTextColor(this.currentTextColor);
            bcSmartspaceCard.setDozeAmount(this.dozeAmount);
            return;
        }
        BaseTemplateData templateData = smartspaceTarget.getTemplateData();
        if (templateData == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Required value was null.");
            return;
        }
        BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(bcSmartspaceCardLoggingInfo, templateData);
        BaseTemplateCard baseTemplateCard = viewHolder.card;
        if (baseTemplateCard == null) {
            Log.w("SsCardPagerAdapter", "No ui-template card view can be binded");
            return;
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.dataProvider;
        baseTemplateCard.bindData(smartspaceTarget, bcSmartspaceDataPlugin3 != null ? bcSmartspaceDataPlugin3.getEventNotifier() : null, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        baseTemplateCard.setPrimaryTextColor(this.currentTextColor);
        baseTemplateCard.setDozeAmount(this.dozeAmount);
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
        this.dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        updateTargetVisibility();
        updateCurrentTextColor();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setKeyguardBypassEnabled(boolean z) {
        this.keyguardBypassEnabled = z;
        updateTargetVisibility();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mediaTargets.clear();
        if (smartspaceTarget != null) {
            this.mediaTargets.add(smartspaceTarget);
        }
        updateTargetVisibility();
        notifyDataSetChanged();
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
        int size2 = this.recycledCards.size();
        for (int i2 = 0; i2 < size2; i2++) {
            BaseTemplateCard baseTemplateCard = (BaseTemplateCard) this.recycledCards.valueAt(i2);
            baseTemplateCard.setPaddingRelative(num.intValue(), baseTemplateCard.getPaddingTop(), num.intValue(), baseTemplateCard.getPaddingBottom());
        }
        int size3 = this.recycledLegacyCards.size();
        for (int i3 = 0; i3 < size3; i3++) {
            BcSmartspaceCard bcSmartspaceCard = (BcSmartspaceCard) this.recycledLegacyCards.valueAt(i3);
            bcSmartspaceCard.setPaddingRelative(num.intValue(), bcSmartspaceCard.getPaddingTop(), num.intValue(), bcSmartspaceCard.getPaddingBottom());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setPrimaryTextColor(int i) {
        this.primaryTextColor = i;
        updateCurrentTextColor();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setScreenOn(boolean z) {
        BaseTemplateCard baseTemplateCard;
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = this.viewHolders;
            ViewHolder viewHolder = (ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null && (baseTemplateCard = viewHolder.card) != null) {
                baseTemplateCard.setScreenOn(z);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTargets(List list) {
        Bundle extras;
        this._aodTargets.clear();
        this._lockscreenTargets.clear();
        this.hasDifferentTargets = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            if (smartspaceTarget.getFeatureType() != 34) {
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
            addDefaultDateCardIfEmpty(this._aodTargets);
            addDefaultDateCardIfEmpty(this._lockscreenTargets);
        }
        updateTargetVisibility();
        notifyDataSetChanged();
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
    public final void updateCurrentTextColor() {
        this.currentTextColor = ColorUtils.blendARGB(this.primaryTextColor, this.dozeColor, this.dozeAmount);
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = this.viewHolders;
            ViewHolder viewHolder = (ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null) {
                BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
                if (bcSmartspaceCard != null) {
                    bcSmartspaceCard.setPrimaryTextColor(this.currentTextColor);
                    bcSmartspaceCard.setDozeAmount(this.dozeAmount);
                }
                BaseTemplateCard baseTemplateCard = viewHolder.card;
                if (baseTemplateCard != null) {
                    baseTemplateCard.setPrimaryTextColor(this.currentTextColor);
                    baseTemplateCard.setDozeAmount(this.dozeAmount);
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0053 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTargetVisibility() {
        boolean z;
        boolean z2;
        List lockscreenTargets = !this.mediaTargets.isEmpty() ? this.mediaTargets : this.hasDifferentTargets ? this._aodTargets : getLockscreenTargets();
        List lockscreenTargets2 = getLockscreenTargets();
        List list = this.smartspaceTargets;
        if (list != lockscreenTargets) {
            float f = this.dozeAmount;
            if (f == 1.0f || (f >= 0.36f && this.transitioningTo == TransitionType.TO_AOD)) {
                z = true;
                if (list != lockscreenTargets2) {
                    float f2 = this.dozeAmount;
                    if (f2 == 0.0f || (1.0f - f2 >= 0.36f && this.transitioningTo == TransitionType.TO_LOCKSCREEN)) {
                        z2 = true;
                        if (!z || z2) {
                            this.smartspaceTargets = z ? lockscreenTargets : lockscreenTargets2;
                            notifyDataSetChanged();
                        }
                        this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
                        if (!this.configProvider.isDefaultDateWeatherDisabled() || StringsKt__StringsJVMKt.equals(this.uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, false)) {
                            return;
                        }
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.root, this.smartspaceTargets.isEmpty() ? 8 : 0);
                        return;
                    }
                }
                z2 = false;
                if (!z) {
                }
                this.smartspaceTargets = z ? lockscreenTargets : lockscreenTargets2;
                notifyDataSetChanged();
                this.hasAodLockscreenTransition = lockscreenTargets != lockscreenTargets2;
                if (this.configProvider.isDefaultDateWeatherDisabled()) {
                    return;
                } else {
                    return;
                }
            }
        }
        z = false;
        if (list != lockscreenTargets2) {
        }
        z2 = false;
        if (!z) {
        }
        this.smartspaceTargets = z ? lockscreenTargets : lockscreenTargets2;
        notifyDataSetChanged();
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
    public final void onBackgroundToggled(boolean z) {
    }
}
