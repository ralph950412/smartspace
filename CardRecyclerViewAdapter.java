package com.google.android.systemui.smartspace;

import android.R;
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
import android.view.ViewGroup;
import android.widget.RemoteViews;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.ClockEventController$listenForDnd$1$1$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntriesKt;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class CardRecyclerViewAdapter extends RecyclerView.Adapter implements CardAdapter {
    public static final Set legacySecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartSpaceUtil.FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP.values());
    public static final Set templateSecondaryCardResourceIdSet = CollectionsKt.toSet(BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.values());
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
    public List smartspaceTargets = new ArrayList();
    public final List _aodTargets = new ArrayList();
    public final List _lockscreenTargets = new ArrayList();
    public final List mediaTargets = new ArrayList();
    public final int dozeColor = -1;

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
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final SmartspaceCard card;

        public ViewHolder(SmartspaceCard smartspaceCard) {
            super(smartspaceCard.getView());
            this.card = smartspaceCard;
        }
    }

    public CardRecyclerViewAdapter(BcSmartspaceView bcSmartspaceView, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.root = bcSmartspaceView;
        int attrColor = GraphicsUtils.getAttrColor(R.attr.textColorPrimary, bcSmartspaceView.getContext());
        this.primaryTextColor = attrColor;
        this.currentTextColor = attrColor;
        this.configProvider = bcSmartspaceConfigPlugin;
        this.transitioningTo = TransitionType.NOT_IN_TRANSITION;
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.smartspaceTargets.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.smartspaceTargets.get(i);
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
        Integer num2 = (Integer) BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES.get(Integer.valueOf(templateData.getTemplateType()));
        return num2 != null ? num2.intValue() : com.android.wm.shell.R.layout.smartspace_base_template_card;
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
        if (this.smartspaceTargets.isEmpty() || i < 0 || i >= this.smartspaceTargets.size()) {
            return null;
        }
        return (SmartspaceTarget) this.smartspaceTargets.get(i);
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
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) this.smartspaceTargets.get(i);
        boolean isTemplateCard = isTemplateCard(smartspaceTarget);
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mInstanceId = InstanceId.create(smartspaceTarget);
        builder.mFeatureType = smartspaceTarget.getFeatureType();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.uiSurface, this.dozeAmount);
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
        smartspaceCard.bindData(smartspaceTarget, this.dataProvider == null ? null : new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.google.android.systemui.smartspace.CardRecyclerViewAdapter$onBindViewHolder$1
            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
            public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = CardRecyclerViewAdapter.this.dataProvider;
                bcSmartspaceDataPlugin.getClass();
                bcSmartspaceDataPlugin.notifySmartspaceEvent(smartspaceTargetEvent);
            }
        }, bcSmartspaceCardLoggingInfo, this.smartspaceTargets.size() > 1);
        smartspaceCard.setPrimaryTextColor(this.currentTextColor);
        smartspaceCard.setDozeAmount$1(this.dozeAmount);
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
        if (set.contains(Integer.valueOf(i)) || i == com.android.wm.shell.R.layout.smartspace_base_template_card_with_date || i == com.android.wm.shell.R.layout.smartspace_base_template_card) {
            if (set.contains(Integer.valueOf(i))) {
                num = Integer.valueOf(i);
                i = com.android.wm.shell.R.layout.smartspace_base_template_card;
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
            if (!set2.contains(Integer.valueOf(i)) && i != com.android.wm.shell.R.layout.smartspace_card) {
                bcSmartspaceRemoteViewsCard = new BcSmartspaceRemoteViewsCard(viewGroup.getContext());
                bcSmartspaceRemoteViewsCard.mUiSurface = bcSmartspaceRemoteViewsCard.mUiSurface;
                bcSmartspaceRemoteViewsCard.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                return new ViewHolder(bcSmartspaceRemoteViewsCard);
            }
            if (set2.contains(Integer.valueOf(i))) {
                num2 = Integer.valueOf(i);
                i = com.android.wm.shell.R.layout.smartspace_card;
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
        this.dozeAmount = f;
        float f2 = this.previousDozeAmount;
        this.transitioningTo = f2 > f ? TransitionType.TO_LOCKSCREEN : f2 < f ? TransitionType.TO_AOD : TransitionType.NOT_IN_TRANSITION;
        this.previousDozeAmount = f;
        updateTargetVisibility$1();
        updateCurrentTextColor$1();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public final void setKeyguardBypassEnabled(boolean z) {
        this.keyguardBypassEnabled = z;
        updateTargetVisibility$1();
    }

    @Override // com.google.android.systemui.smartspace.CardAdapter
    public void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mediaTargets.clear();
        if (smartspaceTarget != null) {
            this.mediaTargets.add(smartspaceTarget);
        }
        updateTargetVisibility$1();
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
        Bundle extras;
        this._aodTargets.clear();
        this._lockscreenTargets.clear();
        this.hasDifferentTargets = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            if (smartspaceTarget != null) {
                if (smartspaceTarget.getFeatureType() != 34) {
                    if ((smartspaceTarget.getRemoteViews() != null) || isTemplateCard(smartspaceTarget) || smartspaceTarget.getFeatureType() != 1) {
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
                ClockEventController$listenForDnd$1$1$$ExternalSyntheticOutline0.m("No card can be created for target: ", "SsCardRecyclerViewAdapter", smartspaceTarget.getFeatureType());
            }
        }
        if (!this.configProvider.isDefaultDateWeatherDisabled()) {
            addDefaultDateCardIfEmpty$1(this._aodTargets);
            addDefaultDateCardIfEmpty$1(this._lockscreenTargets);
        }
        updateTargetVisibility$1();
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

    public final void updateCurrentTextColor$1() {
        this.currentTextColor = ColorUtils.blendARGB(this.primaryTextColor, this.dozeColor, this.dozeAmount);
        int size = this.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = this.viewHolders;
            ViewHolder viewHolder = (ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null) {
                int i2 = this.currentTextColor;
                SmartspaceCard smartspaceCard = viewHolder.card;
                smartspaceCard.setPrimaryTextColor(i2);
                smartspaceCard.setDozeAmount$1(this.dozeAmount);
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
    public final void updateTargetVisibility$1() {
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
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r7 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r8 = com.google.android.systemui.smartspace.CardRecyclerViewAdapter.TransitionType.TO_AOD
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
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r2 = r9.transitioningTo
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter$TransitionType r4 = com.google.android.systemui.smartspace.CardRecyclerViewAdapter.TransitionType.TO_LOCKSCREEN
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.CardRecyclerViewAdapter.updateTargetVisibility$1():void");
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
