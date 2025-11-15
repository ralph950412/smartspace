package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.graphics.ColorUtils;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.CardPagerAdapter;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    public static final Companion Companion = new Companion();
    public Handler bgHandler;
    public BcSmartspaceConfigPlugin configProvider;
    public int currentTextColor;
    public BcSmartspaceDataPlugin dataProvider;
    public float dozeAmount;
    public boolean hasAodLockscreenTransition;
    public boolean hasDifferentTargets;
    public boolean keyguardBypassEnabled;
    public Integer nonRemoteViewsHorizontalPadding;
    public float previousDozeAmount;
    public int primaryTextColor;
    public final BcSmartspaceView root;
    public BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate;
    public TransitionType transitioningTo;
    public String uiSurface;
    public final SparseArray viewHolders = new SparseArray();
    public final LazyServerFlagLoader enableCardRecycling = new LazyServerFlagLoader("enable_card_recycling");
    public final LazyServerFlagLoader enableReducedCardRecycling = new LazyServerFlagLoader("enable_reduced_card_recycling");
    public final SparseArray recycledCards = new SparseArray();
    public final SparseArray recycledLegacyCards = new SparseArray();
    public final SparseArray recycledRemoteViewsCards = new SparseArray();
    public List smartspaceTargets = new ArrayList();
    public final List _aodTargets = new ArrayList();
    public final List _lockscreenTargets = new ArrayList();
    public final List mediaTargets = new ArrayList();
    public final int dozeColor = -1;

    /* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
    public final class Companion {
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

        public static boolean useRecycledViewForActionsList(final List list, final List list2) {
            Map map = BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES;
            if (list != null || list2 != null) {
                if ((list == null || list2 == null) ? false : true) {
                    list.getClass();
                    int size = list.size();
                    list2.getClass();
                    if (size != list2.size() || !IntStream.range(0, list.size()).allMatch(new IntPredicate() { // from class: com.google.android.systemui.smartspace.CardPagerAdapter$Companion$useRecycledViewForActionsList$1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i) {
                            return CardPagerAdapter.Companion.useRecycledViewForAction((SmartspaceAction) list.get(i), (SmartspaceAction) list2.get(i));
                        }
                    })) {
                    }
                }
                return false;
            }
            return true;
        }

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
    /* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
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

    /* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
    public final class ViewHolder {
        public final BaseTemplateCard card;
        public final BcSmartspaceCard legacyCard;
        public final int position;
        public final BcSmartspaceRemoteViewsCard remoteViewsCard;
        public SmartspaceTarget target;

        public ViewHolder(int i, BcSmartspaceCard bcSmartspaceCard, SmartspaceTarget smartspaceTarget, BaseTemplateCard baseTemplateCard, BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard) {
            this.position = i;
            this.legacyCard = bcSmartspaceCard;
            this.target = smartspaceTarget;
            this.card = baseTemplateCard;
            this.remoteViewsCard = bcSmartspaceRemoteViewsCard;
        }
    }

    public CardPagerAdapter(BcSmartspaceView bcSmartspaceView, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.root = bcSmartspaceView;
        int attrColor = GraphicsUtils.getAttrColor(android.R.attr.textColorPrimary, bcSmartspaceView.getContext());
        this.primaryTextColor = attrColor;
        this.currentTextColor = attrColor;
        this.configProvider = bcSmartspaceConfigPlugin;
        this.transitioningTo = TransitionType.NOT_IN_TRANSITION;
    }

    public static final int getBaseLegacyCardRes(int i) {
        return Companion.getBaseLegacyCardRes(i);
    }

    public static final boolean useRecycledViewForNewTarget(SmartspaceTarget smartspaceTarget, SmartspaceTarget smartspaceTarget2) {
        return Companion.useRecycledViewForNewTarget(smartspaceTarget, smartspaceTarget2);
    }

    public final void addDefaultDateCardIfEmpty(List list) {
        if (list.isEmpty()) {
            BcSmartspaceView bcSmartspaceView = this.root;
            list.add(new SmartspaceTarget.Builder("date_card_794317_92634", new ComponentName(bcSmartspaceView.getContext(), (Class<?>) CardPagerAdapter.class), bcSmartspaceView.getContext().getUser()).setFeatureType(1).setTemplateData(new BaseTemplateData.Builder(1).build()).build());
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewPager viewPager, int i, Object obj) {
        ViewHolder viewHolder = (ViewHolder) obj;
        BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
        LazyServerFlagLoader lazyServerFlagLoader = this.enableCardRecycling;
        if (bcSmartspaceCard != null) {
            SmartspaceTarget smartspaceTarget = bcSmartspaceCard.mTarget;
            if (smartspaceTarget != null && lazyServerFlagLoader.get()) {
                this.recycledLegacyCards.put(BcSmartSpaceUtil.getFeatureType(smartspaceTarget), bcSmartspaceCard);
            }
            viewPager.removeView(bcSmartspaceCard);
        }
        BaseTemplateCard baseTemplateCard = viewHolder.card;
        if (baseTemplateCard != null) {
            SmartspaceTarget smartspaceTarget2 = baseTemplateCard.mTarget;
            if (smartspaceTarget2 != null && lazyServerFlagLoader.get()) {
                this.recycledCards.put(smartspaceTarget2.getFeatureType(), baseTemplateCard);
            }
            viewPager.removeView(baseTemplateCard);
        }
        BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = viewHolder.remoteViewsCard;
        if (bcSmartspaceRemoteViewsCard != null) {
            if (lazyServerFlagLoader.get()) {
                Log.d("SsCardPagerAdapter", "[rmv] Caching RemoteViews card");
                this.recycledRemoteViewsCards.put(BcSmartSpaceUtil.getFeatureType(viewHolder.target), bcSmartspaceRemoteViewsCard);
            }
            Log.d("SsCardPagerAdapter", "[rmv] Removing RemoteViews card");
            viewPager.removeView(bcSmartspaceRemoteViewsCard);
        }
        if (this.viewHolders.get(i) == viewHolder) {
            this.viewHolders.remove(i);
        }
    }

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

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return this.smartspaceTargets.size();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final float getDozeAmount() {
        return this.dozeAmount;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasAodLockscreenTransition() {
        return this.hasAodLockscreenTransition;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final boolean getHasDifferentTargets() {
        return this.hasDifferentTargets;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getItemPosition(Object obj) {
        ViewHolder viewHolder = (ViewHolder) obj;
        SmartspaceTarget targetAtPosition = getTargetAtPosition(viewHolder.position);
        if (viewHolder.target == targetAtPosition) {
            return -1;
        }
        if (targetAtPosition == null || BcSmartSpaceUtil.getFeatureType(targetAtPosition) != BcSmartSpaceUtil.getFeatureType(viewHolder.target) || !Intrinsics.areEqual(targetAtPosition.getSmartspaceTargetId(), viewHolder.target.getSmartspaceTargetId())) {
            return -2;
        }
        viewHolder.target = targetAtPosition;
        onBindViewHolder(viewHolder);
        return -1;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BcSmartspaceCard getLegacyCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.legacyCard;
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
        if (viewHolder != null) {
            return viewHolder.remoteViewsCard;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final List getSmartspaceTargets() {
        return this.smartspaceTargets;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final SmartspaceTarget getTargetAtPosition(int i) {
        if (this.smartspaceTargets.isEmpty() || i < 0 || i >= this.smartspaceTargets.size()) {
            return null;
        }
        return (SmartspaceTarget) this.smartspaceTargets.get(i);
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final BaseTemplateCard getTemplateCardAtPosition(int i) {
        ViewHolder viewHolder = (ViewHolder) this.viewHolders.get(i);
        if (viewHolder != null) {
            return viewHolder.card;
        }
        return null;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final String getUiSurface() {
        return this.uiSurface;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewPager viewPager, int i) {
        ViewHolder viewHolder;
        int i2;
        BaseTemplateCard baseTemplateCard;
        Integer num;
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.smartspaceTargets.get(i);
        Log.i("SsCardPagerAdapter", "[rmv] Rendering flag - enabled: true " + ("rmv: " + smartspaceTarget.getRemoteViews()));
        RemoteViews remoteViews = smartspaceTarget.getRemoteViews();
        LazyServerFlagLoader lazyServerFlagLoader = this.enableCardRecycling;
        BcSmartspaceCard bcSmartspaceCard = null;
        if (remoteViews != null) {
            Log.i("SsCardPagerAdapter", "[rmv] Use RemoteViews for the feature: " + smartspaceTarget.getFeatureType());
            BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = lazyServerFlagLoader.get() ? (BcSmartspaceRemoteViewsCard) this.recycledRemoteViewsCards.removeReturnOld(BcSmartSpaceUtil.getFeatureType(smartspaceTarget)) : null;
            if (bcSmartspaceRemoteViewsCard == null) {
                bcSmartspaceRemoteViewsCard = new BcSmartspaceRemoteViewsCard(viewPager.getContext());
                bcSmartspaceRemoteViewsCard.mUiSurface = this.uiSurface;
                bcSmartspaceRemoteViewsCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            }
            BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard2 = bcSmartspaceRemoteViewsCard;
            i2 = i;
            viewHolder = new ViewHolder(i2, null, smartspaceTarget, null, bcSmartspaceRemoteViewsCard2);
            viewPager.addView(bcSmartspaceRemoteViewsCard2);
        } else {
            boolean containsValidTemplateType = BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
            Companion companion = Companion;
            LazyServerFlagLoader lazyServerFlagLoader2 = this.enableReducedCardRecycling;
            if (containsValidTemplateType) {
                Log.i("SsCardPagerAdapter", "Use UI template for the feature: " + smartspaceTarget.getFeatureType());
                BaseTemplateCard baseTemplateCard2 = lazyServerFlagLoader.get() ? (BaseTemplateCard) this.recycledCards.removeReturnOld(smartspaceTarget.getFeatureType()) : null;
                if (baseTemplateCard2 == null || (lazyServerFlagLoader2.get() && !companion.useRecycledViewForNewTarget(smartspaceTarget, baseTemplateCard2.mTarget))) {
                    BaseTemplateData templateData = smartspaceTarget.getTemplateData();
                    BaseTemplateData.SubItemInfo primaryItem = templateData != null ? templateData.getPrimaryItem() : null;
                    int i3 = (primaryItem == null || (SmartspaceUtils.isEmpty(primaryItem.getText()) && primaryItem.getIcon() == null)) ? R.layout.smartspace_base_template_card_with_date : R.layout.smartspace_base_template_card;
                    LayoutInflater from = LayoutInflater.from(viewPager.getContext());
                    BaseTemplateCard baseTemplateCard3 = (BaseTemplateCard) from.inflate(i3, (ViewGroup) viewPager, false);
                    String str = this.uiSurface;
                    baseTemplateCard3.mUiSurface = str;
                    if (baseTemplateCard3.mDateView != null && TextUtils.equals(str, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                        IcuDateTextView icuDateTextView = baseTemplateCard3.mDateView;
                        if (icuDateTextView.isAttachedToWindow()) {
                            throw new IllegalStateException("Must call before attaching view to window.");
                        }
                        icuDateTextView.mUpdatesOnAod = true;
                    }
                    Integer num2 = this.nonRemoteViewsHorizontalPadding;
                    if (num2 != null) {
                        int intValue = num2.intValue();
                        baseTemplateCard3.setPaddingRelative(intValue, baseTemplateCard3.getPaddingTop(), intValue, baseTemplateCard3.getPaddingBottom());
                    }
                    Handler handler = this.bgHandler;
                    Handler handler2 = handler != null ? handler : null;
                    baseTemplateCard3.mBgHandler = handler2;
                    IcuDateTextView icuDateTextView2 = baseTemplateCard3.mDateView;
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
                    if (templateData != null && (num = (Integer) BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.get(Integer.valueOf(templateData.getTemplateType()))) != null) {
                        BcSmartspaceCardSecondary bcSmartspaceCardSecondary = (BcSmartspaceCardSecondary) from.inflate(num.intValue(), (ViewGroup) baseTemplateCard3, false);
                        Log.i("SsCardPagerAdapter", "Secondary card is found");
                        baseTemplateCard3.setSecondaryCard(bcSmartspaceCardSecondary);
                    }
                    baseTemplateCard = baseTemplateCard3;
                } else {
                    baseTemplateCard = baseTemplateCard2;
                }
                i2 = i;
                viewHolder = new ViewHolder(i2, null, smartspaceTarget, baseTemplateCard, null);
                viewPager.addView(baseTemplateCard);
            } else {
                BcSmartspaceCard bcSmartspaceCard2 = lazyServerFlagLoader.get() ? (BcSmartspaceCard) this.recycledLegacyCards.removeReturnOld(BcSmartSpaceUtil.getFeatureType(smartspaceTarget)) : null;
                if (bcSmartspaceCard2 == null || (lazyServerFlagLoader2.get() && !companion.useRecycledViewForNewTarget(smartspaceTarget, bcSmartspaceCard2.mTarget))) {
                    int featureType = BcSmartSpaceUtil.getFeatureType(smartspaceTarget);
                    LayoutInflater from2 = LayoutInflater.from(viewPager.getContext());
                    int baseLegacyCardRes = companion.getBaseLegacyCardRes(featureType);
                    if (baseLegacyCardRes == 0) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m("No legacy card can be created for feature type: ", "SsCardPagerAdapter", featureType);
                    } else {
                        bcSmartspaceCard = (BcSmartspaceCard) from2.inflate(baseLegacyCardRes, (ViewGroup) viewPager, false);
                        bcSmartspaceCard.mUiSurface = this.uiSurface;
                        Integer num3 = this.nonRemoteViewsHorizontalPadding;
                        if (num3 != null) {
                            int intValue2 = num3.intValue();
                            bcSmartspaceCard.setPaddingRelative(intValue2, bcSmartspaceCard.getPaddingTop(), intValue2, bcSmartspaceCard.getPaddingBottom());
                        }
                        Integer num4 = (Integer) BcSmartSpaceUtil.FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP.get(Integer.valueOf(featureType));
                        if (num4 != null) {
                            bcSmartspaceCard.setSecondaryCard((BcSmartspaceCardSecondary) from2.inflate(num4.intValue(), (ViewGroup) bcSmartspaceCard, false));
                        }
                    }
                } else {
                    bcSmartspaceCard = bcSmartspaceCard2;
                }
                i2 = i;
                viewHolder = new ViewHolder(i2, bcSmartspaceCard, smartspaceTarget, null, null);
                if (bcSmartspaceCard != null) {
                    viewPager.addView(bcSmartspaceCard);
                }
            }
        }
        onBindViewHolder(viewHolder);
        this.viewHolders.put(i2, viewHolder);
        return viewHolder;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        ViewHolder viewHolder = (ViewHolder) obj;
        return view == viewHolder.legacyCard || view == viewHolder.card || view == viewHolder.remoteViewsCard;
    }

    public final void onBindViewHolder(ViewHolder viewHolder) {
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.smartspaceTargets.get(viewHolder.position);
        boolean containsValidTemplateType = BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mInstanceId = InstanceId.create(smartspaceTarget);
        builder.mFeatureType = smartspaceTarget.getFeatureType();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.uiSurface, this.dozeAmount);
        builder.mRank = viewHolder.position;
        builder.mCardinality = this.smartspaceTargets.size();
        this.root.getContext().getPackageManager();
        builder.mUid = -1;
        builder.mSubcardInfo = containsValidTemplateType ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        builder.mDimensionalInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo(builder);
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = null;
        if (smartspaceTarget.getRemoteViews() != null) {
            BcSmartspaceRemoteViewsCard bcSmartspaceRemoteViewsCard = viewHolder.remoteViewsCard;
            if (bcSmartspaceRemoteViewsCard == null) {
                Log.w("SsCardPagerAdapter", "[rmv] No RemoteViews card view can be binded");
                return;
            }
            Log.d("SsCardPagerAdapter", "[rmv] Refreshing RemoteViews card");
            if (this.dataProvider != null) {
                final int i = 0;
                smartspaceEventNotifier = new BcSmartspaceDataPlugin.SmartspaceEventNotifier(this) { // from class: com.google.android.systemui.smartspace.CardPagerAdapter$onBindViewHolder$1
                    public final /* synthetic */ CardPagerAdapter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                        switch (i) {
                            case 0:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin.getClass();
                                bcSmartspaceDataPlugin.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                            case 1:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin2.getClass();
                                bcSmartspaceDataPlugin2.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                            default:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin3.getClass();
                                bcSmartspaceDataPlugin3.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                        }
                    }
                };
            }
            bcSmartspaceRemoteViewsCard.bindData(smartspaceTarget, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
            return;
        }
        if (!containsValidTemplateType) {
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeAndInjectWeatherSubcard(bcSmartspaceCardLoggingInfo, smartspaceTarget);
            BcSmartspaceCard bcSmartspaceCard = viewHolder.legacyCard;
            if (bcSmartspaceCard == null) {
                Log.w("SsCardPagerAdapter", "No legacy card view can be binded");
                return;
            }
            if (this.dataProvider != null) {
                final int i2 = 2;
                smartspaceEventNotifier = new BcSmartspaceDataPlugin.SmartspaceEventNotifier(this) { // from class: com.google.android.systemui.smartspace.CardPagerAdapter$onBindViewHolder$1
                    public final /* synthetic */ CardPagerAdapter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                        switch (i2) {
                            case 0:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin.getClass();
                                bcSmartspaceDataPlugin.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                            case 1:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin2.getClass();
                                bcSmartspaceDataPlugin2.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                            default:
                                BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.this$0.dataProvider;
                                bcSmartspaceDataPlugin3.getClass();
                                bcSmartspaceDataPlugin3.notifySmartspaceEvent(smartspaceTargetEvent);
                                break;
                        }
                    }
                };
            }
            bcSmartspaceCard.bindData(smartspaceTarget, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
            bcSmartspaceCard.setPrimaryTextColor(this.currentTextColor);
            bcSmartspaceCard.setDozeAmount$1(this.dozeAmount);
            return;
        }
        BaseTemplateData templateData = smartspaceTarget.getTemplateData();
        if (templateData == null) {
            throw new IllegalStateException("Required value was null.");
        }
        BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(bcSmartspaceCardLoggingInfo, templateData);
        BaseTemplateCard baseTemplateCard = viewHolder.card;
        if (baseTemplateCard == null) {
            Log.w("SsCardPagerAdapter", "No ui-template card view can be binded");
            return;
        }
        if (this.dataProvider != null) {
            final int i3 = 1;
            smartspaceEventNotifier = new BcSmartspaceDataPlugin.SmartspaceEventNotifier(this) { // from class: com.google.android.systemui.smartspace.CardPagerAdapter$onBindViewHolder$1
                public final /* synthetic */ CardPagerAdapter this$0;

                {
                    this.this$0 = this;
                }

                @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                    switch (i3) {
                        case 0:
                            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.this$0.dataProvider;
                            bcSmartspaceDataPlugin.getClass();
                            bcSmartspaceDataPlugin.notifySmartspaceEvent(smartspaceTargetEvent);
                            break;
                        case 1:
                            BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.this$0.dataProvider;
                            bcSmartspaceDataPlugin2.getClass();
                            bcSmartspaceDataPlugin2.notifySmartspaceEvent(smartspaceTargetEvent);
                            break;
                        default:
                            BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.this$0.dataProvider;
                            bcSmartspaceDataPlugin3.getClass();
                            bcSmartspaceDataPlugin3.notifySmartspaceEvent(smartspaceTargetEvent);
                            break;
                    }
                }
            };
        }
        baseTemplateCard.bindData(smartspaceTarget, smartspaceEventNotifier, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        baseTemplateCard.setPrimaryTextColor(this.currentTextColor);
        baseTemplateCard.setDozeAmount$1(this.dozeAmount);
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
        this.dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        updateTargetVisibility();
        updateCurrentTextColor();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setKeyguardBypassEnabled(boolean z) {
        this.keyguardBypassEnabled = z;
        updateTargetVisibility();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mediaTargets.clear();
        if (smartspaceTarget != null) {
            this.mediaTargets.add(smartspaceTarget);
        }
        updateTargetVisibility();
        notifyDataSetChanged();
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
        updateCurrentTextColor();
    }

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

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTargets(List list) {
        Bundle extras;
        this._aodTargets.clear();
        this._lockscreenTargets.clear();
        this.hasDifferentTargets = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            smartspaceTarget.getClass();
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

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.timeChangedDelegate = timeChangedDelegate;
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setUiSurface(String str) {
        this.uiSurface = str;
    }

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
                    bcSmartspaceCard.setDozeAmount$1(this.dozeAmount);
                }
                BaseTemplateCard baseTemplateCard = viewHolder.card;
                if (baseTemplateCard != null) {
                    baseTemplateCard.setPrimaryTextColor(this.currentTextColor);
                    baseTemplateCard.setDozeAmount$1(this.dozeAmount);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0053 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void updateTargetVisibility() {
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
            r3 = 0
            r4 = 1052266988(0x3eb851ec, float:0.36)
            r5 = 1065353216(0x3f800000, float:1.0)
            r6 = 1
            if (r2 == r0) goto L38
            float r7 = r9.dozeAmount
            int r8 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r8 != 0) goto L2c
            goto L36
        L2c:
            int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r7 < 0) goto L38
            com.google.android.systemui.smartspace.CardPagerAdapter$TransitionType r7 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardPagerAdapter$TransitionType r8 = com.google.android.systemui.smartspace.CardPagerAdapter.TransitionType.TO_AOD
            if (r7 != r8) goto L38
        L36:
            r7 = r6
            goto L39
        L38:
            r7 = r3
        L39:
            if (r2 == r1) goto L50
            float r2 = r9.dozeAmount
            r8 = 0
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 != 0) goto L43
            goto L4e
        L43:
            float r5 = r5 - r2
            int r2 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r2 < 0) goto L50
            com.google.android.systemui.smartspace.CardPagerAdapter$TransitionType r2 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardPagerAdapter$TransitionType r4 = com.google.android.systemui.smartspace.CardPagerAdapter.TransitionType.TO_LOCKSCREEN
            if (r2 != r4) goto L50
        L4e:
            r2 = r6
            goto L51
        L50:
            r2 = r3
        L51:
            if (r7 != 0) goto L55
            if (r2 == 0) goto L5f
        L55:
            if (r7 == 0) goto L59
            r2 = r0
            goto L5a
        L59:
            r2 = r1
        L5a:
            r9.smartspaceTargets = r2
            r9.notifyDataSetChanged()
        L5f:
            if (r0 == r1) goto L62
            goto L63
        L62:
            r6 = r3
        L63:
            r9.hasAodLockscreenTransition = r6
            com.android.systemui.plugins.BcSmartspaceConfigPlugin r0 = r9.configProvider
            boolean r0 = r0.isDefaultDateWeatherDisabled()
            if (r0 == 0) goto L86
            java.lang.String r0 = r9.uiSurface
            java.lang.String r1 = "home"
            boolean r0 = kotlin.text.StringsKt__StringsJVMKt.equals(r0, r1, r3)
            if (r0 != 0) goto L86
            java.util.List r0 = r9.smartspaceTargets
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L81
            r3 = 8
        L81:
            com.google.android.systemui.smartspace.BcSmartspaceView r9 = r9.root
            com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils.updateVisibility(r9, r3)
        L86:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.CardPagerAdapter.updateTargetVisibility():void");
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
