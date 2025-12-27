package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.app.smartspace.uitemplatedata.Icon;
import android.app.smartspace.uitemplatedata.TapAction;
import android.app.smartspace.uitemplatedata.Text;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.app.animation.Interpolators;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceCardSecondary;
import com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils;
import com.google.android.systemui.smartspace.DoubleShadowIconDrawable;
import com.google.android.systemui.smartspace.DoubleShadowTextView;
import com.google.android.systemui.smartspace.IcuDateTextView;
import com.google.android.systemui.smartspace.SmartspaceCard;
import com.google.android.systemui.smartspace.TouchDelegateComposite;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardMetadataLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.utils.ContentDescriptionUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BaseTemplateCard extends ConstraintLayout implements SmartspaceCard {
    public Handler mBgHandler;
    public IcuDateTextView mDateView;
    public float mDozeAmount;
    public ViewGroup mExtrasGroup;
    public int mFeatureType;
    public int mIconTintColor;
    public BcSmartspaceCardLoggingInfo mLoggingInfo;
    public BcSmartspaceCardSecondary mSecondaryCard;
    public ViewGroup mSecondaryCardPane;
    public boolean mShouldShowPageIndicator;
    public ViewGroup mSubtitleGroup;
    public Rect mSubtitleHitRect;
    public Rect mSubtitleSupplementalHitRect;
    public DoubleShadowTextView mSubtitleSupplementalView;
    public DoubleShadowTextView mSubtitleTextView;
    public DoubleShadowTextView mSupplementalLineTextView;
    public SmartspaceTarget mTarget;
    public BaseTemplateData mTemplateData;
    public ViewGroup mTextGroup;
    public DoubleShadowTextView mTitleTextView;
    public final TouchDelegateComposite mTouchDelegateComposite;
    public boolean mTouchDelegateIsDirty;
    public String mUiSurface;
    public boolean mValidSecondaryCard;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BaseTemplateCard(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static boolean shouldTint(BaseTemplateData.SubItemInfo subItemInfo) {
        if (subItemInfo == null || subItemInfo.getIcon() == null) {
            return false;
        }
        return subItemInfo.getIcon().shouldTint();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z) {
        DoubleShadowTextView doubleShadowTextView;
        DoubleShadowTextView doubleShadowTextView2;
        DoubleShadowTextView doubleShadowTextView3;
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier2 = smartspaceEventNotifier;
        this.mTarget = null;
        this.mTemplateData = null;
        this.mFeatureType = 0;
        this.mLoggingInfo = null;
        setOnClickListener(null);
        setClickable(false);
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            icuDateTextView.setOnClickListener(null);
            icuDateTextView.setClickable(false);
        }
        resetTextView(this.mTitleTextView);
        resetTextView(this.mSubtitleTextView);
        resetTextView(this.mSubtitleSupplementalView);
        resetTextView(this.mSupplementalLineTextView);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mTitleTextView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSubtitleGroup, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSubtitleTextView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSubtitleSupplementalView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondaryCardPane, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mExtrasGroup, 8);
        this.mTarget = smartspaceTarget;
        this.mTemplateData = smartspaceTarget.getTemplateData();
        this.mFeatureType = smartspaceTarget.getFeatureType();
        this.mLoggingInfo = bcSmartspaceCardLoggingInfo;
        this.mShouldShowPageIndicator = z;
        this.mValidSecondaryCard = false;
        ViewGroup viewGroup = this.mTextGroup;
        if (viewGroup != null) {
            viewGroup.setTranslationX(0.0f);
        }
        if (this.mTemplateData == null) {
            return;
        }
        this.mLoggingInfo = getLoggingInfo();
        if (this.mSecondaryCard != null) {
            Log.i("SsBaseTemplateCard", "Secondary card is not null");
            this.mSecondaryCard.reset(smartspaceTarget.getSmartspaceTargetId());
            this.mValidSecondaryCard = this.mSecondaryCard.setSmartspaceActions(smartspaceTarget, smartspaceEventNotifier, this.mLoggingInfo);
        }
        ViewGroup viewGroup2 = this.mSecondaryCardPane;
        if (viewGroup2 != null) {
            BcSmartspaceTemplateDataUtils.updateVisibility(viewGroup2, (this.mDozeAmount == 1.0f || !this.mValidSecondaryCard) ? 8 : 0);
        }
        BaseTemplateData.SubItemInfo primaryItem = this.mTemplateData.getPrimaryItem();
        IcuDateTextView icuDateTextView2 = this.mDateView;
        if (icuDateTextView2 == null) {
            Log.d("SsBaseTemplateCard", "No date view can be set up");
        } else {
            if (TextUtils.isEmpty(icuDateTextView2.getText())) {
                Log.d("SsBaseTemplateCard", "Date view text is empty");
            }
            TapAction build = new TapAction.Builder((primaryItem == null || primaryItem.getTapAction() == null) ? UUID.randomUUID().toString() : primaryItem.getTapAction().getId().toString()).setIntent(BcSmartSpaceUtil.getOpenCalendarIntent()).build();
            BcSmartSpaceUtil.setOnClickListener$1(this, this.mTarget, build, smartspaceEventNotifier2, "SsBaseTemplateCard", bcSmartspaceCardLoggingInfo, 0);
            smartspaceEventNotifier2 = smartspaceEventNotifier;
            BcSmartSpaceUtil.setOnClickListener$1(this.mDateView, this.mTarget, build, smartspaceEventNotifier2, "SsBaseTemplateCard", bcSmartspaceCardLoggingInfo, 0);
        }
        setUpTextView(this.mTitleTextView, this.mTemplateData.getPrimaryItem(), smartspaceEventNotifier, this.mDateView == null);
        setUpTextView(this.mSubtitleTextView, this.mTemplateData.getSubtitleItem(), smartspaceEventNotifier, true);
        setUpTextView(this.mSubtitleSupplementalView, this.mTemplateData.getSubtitleSupplementalItem(), smartspaceEventNotifier, true);
        setUpTextView(this.mSupplementalLineTextView, this.mTemplateData.getSupplementalLineItem(), smartspaceEventNotifier, true);
        if (this.mExtrasGroup != null) {
            DoubleShadowTextView doubleShadowTextView4 = this.mSupplementalLineTextView;
            if (doubleShadowTextView4 == null || doubleShadowTextView4.getVisibility() != 0 || (this.mShouldShowPageIndicator && this.mDateView == null)) {
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mExtrasGroup, 8);
            } else {
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mExtrasGroup, 0);
                updateZenColors();
            }
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSubtitleGroup, this.mSubtitleTextView.getVisibility() != 8 || this.mSubtitleSupplementalView.getVisibility() != 8 ? 0 : 8);
        if (smartspaceTarget.getFeatureType() == 1 && (doubleShadowTextView3 = this.mSubtitleSupplementalView) != null && doubleShadowTextView3.getVisibility() == 0) {
            this.mSubtitleTextView.setEllipsize(null);
        }
        if (this.mDateView == null && this.mTemplateData.getPrimaryItem() != null && this.mTemplateData.getPrimaryItem().getTapAction() != null) {
            BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, this.mTemplateData.getPrimaryItem().getTapAction(), smartspaceEventNotifier2, "SsBaseTemplateCard", this.mLoggingInfo, 0);
            if (this.mDateView == null && (doubleShadowTextView = this.mTitleTextView) != null && doubleShadowTextView.getVisibility() == 0 && (doubleShadowTextView2 = this.mSubtitleTextView) != null && doubleShadowTextView2.getVisibility() == 0 && this.mTemplateData.getPrimaryItem() != null && this.mTemplateData.getPrimaryItem().getTapAction() != null && this.mTemplateData.getSubtitleItem() != null && this.mTemplateData.getSubtitleItem().getTapAction() != null) {
                TapAction tapAction = this.mTemplateData.getPrimaryItem().getTapAction();
                TapAction tapAction2 = this.mTemplateData.getSubtitleItem().getTapAction();
                if (tapAction.getIntent() != null && !tapAction.getIntent().filterEquals(tapAction2.getIntent())) {
                    Log.d("SsBaseTemplateCard", "Primary item tapAction intent = " + tapAction.getIntent());
                    Log.d("SsBaseTemplateCard", "Subtitle item tapAction intent = " + tapAction2.getIntent());
                } else if (tapAction.getPendingIntent() == null || tapAction.getPendingIntent().equals(tapAction2.getPendingIntent())) {
                    DoubleShadowTextView doubleShadowTextView5 = this.mTitleTextView;
                    doubleShadowTextView5.setOnClickListener(null);
                    doubleShadowTextView5.setClickable(false);
                    DoubleShadowTextView doubleShadowTextView6 = this.mSubtitleTextView;
                    doubleShadowTextView6.setOnClickListener(null);
                    doubleShadowTextView6.setClickable(false);
                } else {
                    Log.d("SsBaseTemplateCard", "Primary item tapAction pendingIntent = " + tapAction.getPendingIntent());
                    Log.d("SsBaseTemplateCard", "Subtitle item tapAction pendingIntent = " + tapAction2.getPendingIntent());
                }
            }
        }
        ViewGroup viewGroup3 = this.mSecondaryCardPane;
        if (viewGroup3 == null) {
            Log.i("SsBaseTemplateCard", "Secondary card pane is null");
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewGroup3.getLayoutParams();
        layoutParams.matchConstraintMaxWidth = getWidth() / 2;
        this.mSecondaryCardPane.setLayoutParams(layoutParams);
        this.mTouchDelegateIsDirty = true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        AccessibilityNodeInfoCompat.wrap(createAccessibilityNodeInfo).setRoleDescription(" ");
        return createAccessibilityNodeInfo;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final BcSmartspaceCardLoggingInfo getLoggingInfo() {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.mLoggingInfo;
        if (bcSmartspaceCardLoggingInfo != null) {
            return bcSmartspaceCardLoggingInfo;
        }
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount);
        int i = this.mFeatureType;
        getContext().getPackageManager();
        SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(this.mTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo2.mInstanceId = 0;
        bcSmartspaceCardLoggingInfo2.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo2.mRank = 0;
        bcSmartspaceCardLoggingInfo2.mCardinality = 0;
        bcSmartspaceCardLoggingInfo2.mFeatureType = i;
        bcSmartspaceCardLoggingInfo2.mReceivedLatency = 0;
        bcSmartspaceCardLoggingInfo2.mUid = -1;
        bcSmartspaceCardLoggingInfo2.mSubcardInfo = null;
        bcSmartspaceCardLoggingInfo2.mDimensionalInfo = createDimensionalLoggingInfo;
        VarHandle.storeStoreFence();
        return bcSmartspaceCardLoggingInfo2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
        this.mTextGroup = (ViewGroup) findViewById(R.id.text_group);
        this.mSecondaryCardPane = (ViewGroup) findViewById(R.id.secondary_card_group);
        this.mDateView = (IcuDateTextView) findViewById(R.id.date);
        this.mTitleTextView = (DoubleShadowTextView) findViewById(R.id.title_text);
        this.mSubtitleGroup = (ViewGroup) findViewById(R.id.smartspace_subtitle_group);
        this.mSubtitleTextView = (DoubleShadowTextView) findViewById(R.id.subtitle_text);
        this.mSubtitleSupplementalView = (DoubleShadowTextView) findViewById(R.id.base_action_icon_subtitle);
        this.mExtrasGroup = (ViewGroup) findViewById(R.id.smartspace_extras_group);
        if (this.mSubtitleTextView != null) {
            this.mSubtitleHitRect = new Rect();
        }
        if (this.mSubtitleSupplementalView != null) {
            this.mSubtitleSupplementalHitRect = new Rect();
        }
        if (this.mSubtitleTextView != null || this.mSubtitleSupplementalView != null) {
            setTouchDelegate(this.mTouchDelegateComposite);
        }
        ViewGroup viewGroup = this.mExtrasGroup;
        if (viewGroup != null) {
            this.mSupplementalLineTextView = (DoubleShadowTextView) viewGroup.findViewById(R.id.supplemental_line_text);
        }
        Handler handler = this.mBgHandler;
        if (handler != null) {
            this.mDateView.mBgHandler = handler;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z || this.mTouchDelegateIsDirty) {
            boolean z2 = false;
            this.mTouchDelegateIsDirty = false;
            if (getTouchDelegate() != null && getTouchDelegate().getClass() == TouchDelegateComposite.class) {
                TouchDelegateComposite touchDelegateComposite = (TouchDelegateComposite) getTouchDelegate();
                touchDelegateComposite.mDelegates.clear();
                ViewGroup viewGroup = this.mSubtitleGroup;
                if (viewGroup == null || viewGroup.getVisibility() != 0) {
                    return;
                }
                DoubleShadowTextView doubleShadowTextView = this.mSubtitleTextView;
                boolean z3 = doubleShadowTextView != null && doubleShadowTextView.getVisibility() == 0;
                DoubleShadowTextView doubleShadowTextView2 = this.mSubtitleSupplementalView;
                boolean z4 = doubleShadowTextView2 != null && doubleShadowTextView2.getVisibility() == 0;
                if (z3 || z4) {
                    DoubleShadowTextView doubleShadowTextView3 = this.mSubtitleTextView;
                    boolean z5 = doubleShadowTextView3 != null && doubleShadowTextView3.hasOnClickListeners();
                    DoubleShadowTextView doubleShadowTextView4 = this.mSubtitleSupplementalView;
                    if (doubleShadowTextView4 != null && doubleShadowTextView4.hasOnClickListeners()) {
                        z2 = true;
                    }
                    if (z5 || z2) {
                        int dimensionPixelSize = (getResources().getDimensionPixelSize(R.dimen.subtitle_hit_rect_height) - this.mSubtitleGroup.getHeight()) / 2;
                        if (dimensionPixelSize > 0 || this.mSubtitleGroup.getBottom() != getHeight()) {
                            if (z3 && z5) {
                                this.mSubtitleTextView.getHitRect(this.mSubtitleHitRect);
                                offsetDescendantRectToMyCoords(this.mSubtitleGroup, this.mSubtitleHitRect);
                                if (dimensionPixelSize > 0) {
                                    this.mSubtitleHitRect.top -= dimensionPixelSize;
                                }
                                this.mSubtitleHitRect.bottom = getBottom();
                                touchDelegateComposite.mDelegates.add(new TouchDelegate(this.mSubtitleHitRect, this.mSubtitleTextView));
                            }
                            if (z4 && z2) {
                                this.mSubtitleSupplementalView.getHitRect(this.mSubtitleSupplementalHitRect);
                                offsetDescendantRectToMyCoords(this.mSubtitleGroup, this.mSubtitleSupplementalHitRect);
                                if (dimensionPixelSize > 0) {
                                    this.mSubtitleSupplementalHitRect.top -= dimensionPixelSize;
                                }
                                this.mSubtitleSupplementalHitRect.bottom = getBottom();
                                touchDelegateComposite.mDelegates.add(new TouchDelegate(this.mSubtitleSupplementalHitRect, this.mSubtitleSupplementalView));
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void resetTextView(DoubleShadowTextView doubleShadowTextView) {
        if (doubleShadowTextView == null) {
            return;
        }
        doubleShadowTextView.setCompoundDrawablesRelative(null, null, null, null);
        doubleShadowTextView.setOnClickListener(null);
        doubleShadowTextView.setClickable(false);
        doubleShadowTextView.setContentDescription(null);
        doubleShadowTextView.setText((CharSequence) null);
        isRtl$1();
        Map map = BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES;
        doubleShadowTextView.setTranslationX(0.0f);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setDozeAmount$1(float f) {
        this.mDozeAmount = f;
        SmartspaceTarget smartspaceTarget = this.mTarget;
        if (smartspaceTarget != null && smartspaceTarget.getBaseAction() != null && this.mTarget.getBaseAction().getExtras() != null) {
            Bundle extras = this.mTarget.getBaseAction().getExtras();
            if (this.mTitleTextView != null && extras.getBoolean("hide_title_on_aod")) {
                this.mTitleTextView.setAlpha(1.0f - f);
            }
            if (this.mSubtitleTextView != null && extras.getBoolean("hide_subtitle_on_aod")) {
                this.mSubtitleTextView.setAlpha(1.0f - f);
            }
        }
        if (this.mTextGroup == null) {
            return;
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondaryCardPane, (this.mDozeAmount == 1.0f || !this.mValidSecondaryCard) ? 8 : 0);
        ViewGroup viewGroup = this.mSecondaryCardPane;
        if (viewGroup == null || viewGroup.getVisibility() == 8) {
            this.mTextGroup.setTranslationX(0.0f);
            return;
        }
        this.mTextGroup.setTranslationX(((PathInterpolator) Interpolators.EMPHASIZED).getInterpolation(this.mDozeAmount) * this.mSecondaryCardPane.getWidth() * (isRtl$1() ? 1 : -1));
        this.mSecondaryCardPane.setAlpha(Math.max(0.0f, Math.min(1.0f, ((1.0f - this.mDozeAmount) * 9.0f) - 6.0f)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setPrimaryTextColor(int i) {
        this.mIconTintColor = i;
        DoubleShadowTextView doubleShadowTextView = this.mTitleTextView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(i);
            BaseTemplateData baseTemplateData = this.mTemplateData;
            if (baseTemplateData != null) {
                updateTextViewIconTint(this.mTitleTextView, shouldTint(baseTemplateData.getPrimaryItem()));
            }
        }
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            icuDateTextView.setTextColor(i);
        }
        DoubleShadowTextView doubleShadowTextView2 = this.mSubtitleTextView;
        if (doubleShadowTextView2 != null) {
            doubleShadowTextView2.setTextColor(i);
            BaseTemplateData baseTemplateData2 = this.mTemplateData;
            if (baseTemplateData2 != null) {
                updateTextViewIconTint(this.mSubtitleTextView, shouldTint(baseTemplateData2.getSubtitleItem()));
            }
        }
        DoubleShadowTextView doubleShadowTextView3 = this.mSubtitleSupplementalView;
        if (doubleShadowTextView3 != null) {
            doubleShadowTextView3.setTextColor(i);
            BaseTemplateData baseTemplateData3 = this.mTemplateData;
            if (baseTemplateData3 != null) {
                updateTextViewIconTint(this.mSubtitleSupplementalView, shouldTint(baseTemplateData3.getSubtitleSupplementalItem()));
            }
        }
        updateZenColors();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setScreenOn(boolean z) {
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            icuDateTextView.mIsInteractive = Boolean.valueOf(z);
            icuDateTextView.rescheduleTicker();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setSecondaryCard(BcSmartspaceCardSecondary bcSmartspaceCardSecondary) {
        ViewGroup viewGroup = this.mSecondaryCardPane;
        if (viewGroup == null) {
            return;
        }
        this.mSecondaryCard = bcSmartspaceCardSecondary;
        BcSmartspaceTemplateDataUtils.updateVisibility(viewGroup, 8);
        this.mSecondaryCardPane.removeAllViews();
        if (bcSmartspaceCardSecondary != null) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-2, getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_card_height));
            layoutParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_secondary_card_start_margin));
            layoutParams.startToStart = 0;
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            this.mSecondaryCardPane.addView(bcSmartspaceCardSecondary, layoutParams);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setUpTextView(DoubleShadowTextView doubleShadowTextView, BaseTemplateData.SubItemInfo subItemInfo, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, boolean z) {
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo;
        List list;
        if (doubleShadowTextView == null) {
            Log.d("SsBaseTemplateCard", "No text view can be set up");
            return;
        }
        resetTextView(doubleShadowTextView);
        if (subItemInfo == null) {
            Log.d("SsBaseTemplateCard", "Passed-in item info is null");
            BcSmartspaceTemplateDataUtils.updateVisibility(doubleShadowTextView, 8);
            return;
        }
        Text text = subItemInfo.getText();
        BcSmartspaceTemplateDataUtils.setText(doubleShadowTextView, subItemInfo.getText());
        if (!SmartspaceUtils.isEmpty(text)) {
            doubleShadowTextView.setTextColor(this.mIconTintColor);
        }
        Icon icon = subItemInfo.getIcon();
        if (icon != null) {
            DoubleShadowIconDrawable doubleShadowIconDrawable = new DoubleShadowIconDrawable(getContext());
            android.graphics.drawable.Icon icon2 = icon.getIcon();
            Context context = getContext();
            FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
            doubleShadowIconDrawable.setIcon(BcSmartSpaceUtil.getIconDrawableWithCustomSize(icon2, context, context.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_size)));
            doubleShadowTextView.setCompoundDrawablesRelative(doubleShadowIconDrawable, null, null, null);
            ContentDescriptionUtil.setFormattedContentDescription("SsBaseTemplateCard", doubleShadowTextView, SmartspaceUtils.isEmpty(text) ? "" : text.getText(), icon.getContentDescription());
            updateTextViewIconTint(doubleShadowTextView, icon.shouldTint());
            if (z) {
                BcSmartspaceTemplateDataUtils.offsetTextViewForIcon(doubleShadowTextView, doubleShadowIconDrawable, isRtl$1());
            }
        }
        int i = 0;
        BcSmartspaceTemplateDataUtils.updateVisibility(doubleShadowTextView, 0);
        SmartspaceTarget smartspaceTarget = this.mTarget;
        TapAction tapAction = subItemInfo.getTapAction();
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.mLoggingInfo;
        if (bcSmartspaceCardLoggingInfo != null && (bcSmartspaceSubcardLoggingInfo = bcSmartspaceCardLoggingInfo.mSubcardInfo) != null && (list = bcSmartspaceSubcardLoggingInfo.mSubcards) != null && !list.isEmpty() && subItemInfo.getLoggingInfo() != null) {
            int featureType = subItemInfo.getLoggingInfo().getFeatureType();
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = this.mLoggingInfo;
            if (featureType != bcSmartspaceCardLoggingInfo2.mFeatureType) {
                List list2 = bcSmartspaceCardLoggingInfo2.mSubcardInfo.mSubcards;
                BaseTemplateData.SubItemLoggingInfo loggingInfo = subItemInfo.getLoggingInfo();
                int i2 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) list2;
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = (BcSmartspaceCardMetadataLoggingInfo) arrayList.get(i2);
                    if (bcSmartspaceCardMetadataLoggingInfo.mInstanceId == loggingInfo.getInstanceId() && bcSmartspaceCardMetadataLoggingInfo.mCardTypeId == loggingInfo.getFeatureType()) {
                        i = i2 + 1;
                        break;
                    }
                    i2++;
                }
            }
        }
        BcSmartSpaceUtil.setOnClickListener$1(doubleShadowTextView, smartspaceTarget, tapAction, smartspaceEventNotifier, "SsBaseTemplateCard", bcSmartspaceCardLoggingInfo, i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateTextViewIconTint(DoubleShadowTextView doubleShadowTextView, boolean z) {
        for (Drawable drawable : doubleShadowTextView.getCompoundDrawablesRelative()) {
            if (drawable != null) {
                if (z) {
                    drawable.setTint(this.mIconTintColor);
                } else {
                    drawable.setTintList(null);
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateZenColors() {
        DoubleShadowTextView doubleShadowTextView = this.mSupplementalLineTextView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(this.mIconTintColor);
            if (BcSmartspaceCardLoggerUtil.containsValidTemplateType(this.mTemplateData)) {
                updateTextViewIconTint(this.mSupplementalLineTextView, shouldTint(this.mTemplateData.getSupplementalLineItem()));
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BaseTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSecondaryCard = null;
        this.mFeatureType = 0;
        this.mLoggingInfo = null;
        this.mIconTintColor = GraphicsUtils.getAttrColor(android.R.attr.textColorPrimary, getContext());
        this.mTextGroup = null;
        this.mSecondaryCardPane = null;
        this.mDateView = null;
        this.mTitleTextView = null;
        this.mSubtitleGroup = null;
        this.mSubtitleTextView = null;
        this.mSubtitleSupplementalView = null;
        this.mSubtitleHitRect = null;
        this.mSubtitleSupplementalHitRect = null;
        this.mExtrasGroup = null;
        this.mSupplementalLineTextView = null;
        TouchDelegateComposite touchDelegateComposite = new TouchDelegateComposite(new Rect(), this);
        touchDelegateComposite.mDelegates = new ArrayList();
        VarHandle.storeStoreFence();
        this.mTouchDelegateComposite = touchDelegateComposite;
        this.mTouchDelegateIsDirty = false;
        context.getTheme().applyStyle(R.style.Smartspace, false);
        setDefaultFocusHighlightEnabled(false);
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final View getView() {
        return this;
    }
}
