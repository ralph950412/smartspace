package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.app.animation.Interpolators;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardMetadataLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.utils.ContentDescriptionUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCard extends ConstraintLayout implements SmartspaceCard {
    public final DoubleShadowIconDrawable mBaseActionIconDrawable;
    public Rect mBaseActionIconSubtitleHitRect;
    public DoubleShadowTextView mBaseActionIconSubtitleView;
    public float mDozeAmount;
    public BcSmartspaceDataPlugin.SmartspaceEventNotifier mEventNotifier;
    public final DoubleShadowIconDrawable mIconDrawable;
    public int mIconTintColor;
    public BcSmartspaceCardLoggingInfo mLoggingInfo;
    public BcSmartspaceCardSecondary mSecondaryCard;
    public ViewGroup mSecondaryCardGroup;
    public TextView mSubtitleTextView;
    public SmartspaceTarget mTarget;
    public ViewGroup mTextGroup;
    public TextView mTitleTextView;
    public boolean mTouchDelegateIsDirty;
    public String mUiSurface;
    public boolean mUsePageIndicatorUi;
    public boolean mValidSecondaryCard;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCard(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int getClickedIndex(BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, int i) {
        List list;
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = bcSmartspaceCardLoggingInfo.mSubcardInfo;
        if (bcSmartspaceSubcardLoggingInfo != null && (list = bcSmartspaceSubcardLoggingInfo.mSubcards) != null) {
            int i2 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) list;
                if (i2 >= arrayList.size()) {
                    break;
                }
                BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = (BcSmartspaceCardMetadataLoggingInfo) arrayList.get(i2);
                if (bcSmartspaceCardMetadataLoggingInfo != null && bcSmartspaceCardMetadataLoggingInfo.mCardTypeId == i) {
                    return i2 + 1;
                }
                i2++;
            }
        }
        return 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z) {
        SmartspaceAction smartspaceAction;
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = bcSmartspaceCardLoggingInfo;
        Drawable drawable = null;
        this.mLoggingInfo = null;
        this.mEventNotifier = null;
        int i = 8;
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondaryCardGroup, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mBaseActionIconSubtitleView, 8);
        this.mIconDrawable.mIconDrawable = null;
        this.mBaseActionIconDrawable.mIconDrawable = null;
        int i2 = 0;
        setTitle(null, null, false);
        setSubtitle(null, null, false);
        setBaseActionIconSubtitle(null, null, null);
        updateIconTint();
        setOnClickListener(null);
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setOnClickListener(null);
            textView.setClickable(false);
        }
        TextView textView2 = this.mSubtitleTextView;
        if (textView2 != null) {
            textView2.setOnClickListener(null);
            textView2.setClickable(false);
        }
        DoubleShadowTextView doubleShadowTextView = this.mBaseActionIconSubtitleView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setOnClickListener(null);
            doubleShadowTextView.setClickable(false);
        }
        this.mTarget = smartspaceTarget;
        this.mEventNotifier = smartspaceEventNotifier;
        SmartspaceAction headerAction = smartspaceTarget.getHeaderAction();
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        this.mLoggingInfo = bcSmartspaceCardLoggingInfo2;
        this.mUsePageIndicatorUi = z;
        this.mValidSecondaryCard = false;
        ViewGroup viewGroup = this.mTextGroup;
        if (viewGroup != null) {
            viewGroup.setTranslationX(0.0f);
        }
        if (headerAction != null) {
            BcSmartspaceCardSecondary bcSmartspaceCardSecondary = this.mSecondaryCard;
            if (bcSmartspaceCardSecondary != null) {
                bcSmartspaceCardSecondary.reset(smartspaceTarget.getSmartspaceTargetId());
                this.mValidSecondaryCard = this.mSecondaryCard.setSmartspaceActions(smartspaceTarget, this.mEventNotifier, bcSmartspaceCardLoggingInfo2);
            }
            ViewGroup viewGroup2 = this.mSecondaryCardGroup;
            if (viewGroup2 != null) {
                viewGroup2.setAlpha(1.0f);
            }
            ViewGroup viewGroup3 = this.mSecondaryCardGroup;
            if (this.mDozeAmount != 1.0f && this.mValidSecondaryCard) {
                i = 0;
            }
            BcSmartspaceTemplateDataUtils.updateVisibility(viewGroup3, i);
            Icon icon = headerAction.getIcon();
            Context context = getContext();
            FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
            Drawable iconDrawableWithCustomSize = BcSmartSpaceUtil.getIconDrawableWithCustomSize(icon, context, context.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_size));
            boolean z2 = iconDrawableWithCustomSize != null;
            this.mIconDrawable.setIcon(iconDrawableWithCustomSize);
            CharSequence title = headerAction.getTitle();
            CharSequence subtitle = headerAction.getSubtitle();
            boolean z3 = smartspaceTarget.getFeatureType() == 1 || !TextUtils.isEmpty(title);
            boolean isEmpty = TextUtils.isEmpty(subtitle);
            boolean z4 = !isEmpty;
            if (!z3) {
                title = subtitle;
            }
            setTitle(title, headerAction.getContentDescription(), z3 != z4 && z2);
            if (!z3 || isEmpty) {
                subtitle = null;
            }
            setSubtitle(subtitle, headerAction.getContentDescription(), z2);
        }
        if (baseAction != null) {
            int i3 = (baseAction.getExtras() == null || baseAction.getExtras().isEmpty()) ? -1 : baseAction.getExtras().getInt("subcardType", -1);
            if (baseAction.getIcon() != null) {
                Icon icon2 = baseAction.getIcon();
                Context context2 = getContext();
                FalsingManager falsingManager2 = BcSmartSpaceUtil.sFalsingManager;
                drawable = BcSmartSpaceUtil.getIconDrawableWithCustomSize(icon2, context2, context2.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_size));
            }
            this.mBaseActionIconDrawable.setIcon(drawable);
            setBaseActionIconSubtitle(baseAction.getSubtitle(), baseAction.getContentDescription(), this.mBaseActionIconDrawable);
            if (i3 != -1) {
                i2 = getClickedIndex(bcSmartspaceCardLoggingInfo2, i3);
            } else {
                Log.d("BcSmartspaceCard", "Subcard expected but missing type. loggingInfo=" + bcSmartspaceCardLoggingInfo2.toString() + ", baseAction=" + baseAction.toString());
            }
            BcSmartSpaceUtil.setOnClickListener(this.mBaseActionIconSubtitleView, smartspaceTarget, baseAction, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo, i2);
            smartspaceAction = baseAction;
            bcSmartspaceCardLoggingInfo2 = bcSmartspaceCardLoggingInfo;
        } else {
            smartspaceAction = baseAction;
        }
        updateIconTint();
        if (headerAction == null || (headerAction.getIntent() == null && headerAction.getPendingIntent() == null)) {
            SmartspaceAction smartspaceAction2 = smartspaceAction;
            if (smartspaceAction2 == null || (smartspaceAction2.getIntent() == null && smartspaceAction2.getPendingIntent() == null)) {
                if (headerAction != null) {
                    BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, headerAction, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo, 0);
                }
            } else if (smartspaceAction2 != null) {
                BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, smartspaceAction2, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo, 0);
            }
        } else {
            if (smartspaceTarget.getFeatureType() == 1 && bcSmartspaceCardLoggingInfo2.mFeatureType == 39) {
                getClickedIndex(bcSmartspaceCardLoggingInfo2, 1);
            }
            if (headerAction != null) {
                BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, headerAction, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo2, 0);
            }
        }
        ViewGroup viewGroup4 = this.mSecondaryCardGroup;
        if (viewGroup4 == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewGroup4.getLayoutParams();
        if (BcSmartSpaceUtil.getFeatureType(smartspaceTarget) == -2) {
            layoutParams.matchConstraintMaxWidth = (getWidth() * 3) / 4;
        } else {
            layoutParams.matchConstraintMaxWidth = getWidth() / 2;
        }
        this.mSecondaryCardGroup.setLayoutParams(layoutParams);
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
        SmartspaceTarget smartspaceTarget = this.mTarget;
        int featureType = smartspaceTarget == null ? 0 : smartspaceTarget.getFeatureType();
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
        this.mTextGroup = (ViewGroup) findViewById(R.id.text_group);
        this.mSecondaryCardGroup = (ViewGroup) findViewById(R.id.secondary_card_group);
        this.mTitleTextView = (TextView) findViewById(R.id.title_text);
        this.mSubtitleTextView = (TextView) findViewById(R.id.subtitle_text);
        DoubleShadowTextView doubleShadowTextView = (DoubleShadowTextView) findViewById(R.id.base_action_icon_subtitle);
        this.mBaseActionIconSubtitleView = doubleShadowTextView;
        if (doubleShadowTextView != null) {
            this.mBaseActionIconSubtitleHitRect = new Rect();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z || this.mTouchDelegateIsDirty) {
            this.mTouchDelegateIsDirty = false;
            setTouchDelegate(null);
            DoubleShadowTextView doubleShadowTextView = this.mBaseActionIconSubtitleView;
            if (doubleShadowTextView == null || doubleShadowTextView.getVisibility() != 0) {
                return;
            }
            int dimensionPixelSize = (getResources().getDimensionPixelSize(R.dimen.subtitle_hit_rect_height) - this.mBaseActionIconSubtitleView.getHeight()) / 2;
            this.mBaseActionIconSubtitleView.getHitRect(this.mBaseActionIconSubtitleHitRect);
            offsetDescendantRectToMyCoords((View) this.mBaseActionIconSubtitleView.getParent(), this.mBaseActionIconSubtitleHitRect);
            if (dimensionPixelSize > 0 || this.mBaseActionIconSubtitleHitRect.bottom != getHeight()) {
                if (dimensionPixelSize > 0) {
                    this.mBaseActionIconSubtitleHitRect.top -= dimensionPixelSize;
                }
                this.mBaseActionIconSubtitleHitRect.bottom = getHeight();
                setTouchDelegate(new TouchDelegate(this.mBaseActionIconSubtitleHitRect, this.mBaseActionIconSubtitleView));
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setBaseActionIconSubtitle(CharSequence charSequence, CharSequence charSequence2, Drawable drawable) {
        if (this.mBaseActionIconSubtitleView == null) {
            Log.w("BcSmartspaceCard", "No base action icon subtitle view to update");
            return;
        }
        if (TextUtils.isEmpty(charSequence)) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mBaseActionIconSubtitleView, 8);
            return;
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mBaseActionIconSubtitleView, 0);
        this.mBaseActionIconSubtitleView.setText(charSequence);
        this.mBaseActionIconSubtitleView.setCompoundDrawablesRelative(drawable, null, null, null);
        ContentDescriptionUtil.setFormattedContentDescription("BcSmartspaceCard", this.mBaseActionIconSubtitleView, charSequence, charSequence2);
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
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondaryCardGroup, (this.mDozeAmount == 1.0f || !this.mValidSecondaryCard) ? 8 : 0);
        SmartspaceTarget smartspaceTarget2 = this.mTarget;
        if (smartspaceTarget2 == null || smartspaceTarget2.getFeatureType() != 30) {
            ViewGroup viewGroup = this.mSecondaryCardGroup;
            if (viewGroup == null || viewGroup.getVisibility() == 8) {
                this.mTextGroup.setTranslationX(0.0f);
                return;
            }
            this.mTextGroup.setTranslationX(((PathInterpolator) Interpolators.EMPHASIZED).getInterpolation(this.mDozeAmount) * this.mSecondaryCardGroup.getWidth() * (isRtl$1() ? 1 : -1));
            this.mSecondaryCardGroup.setAlpha(Math.max(0.0f, Math.min(1.0f, ((1.0f - this.mDozeAmount) * 9.0f) - 6.0f)));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setPrimaryTextColor(int i) {
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextColor(i);
        }
        TextView textView2 = this.mSubtitleTextView;
        if (textView2 != null) {
            textView2.setTextColor(i);
        }
        DoubleShadowTextView doubleShadowTextView = this.mBaseActionIconSubtitleView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(i);
        }
        BcSmartspaceCardSecondary bcSmartspaceCardSecondary = this.mSecondaryCard;
        if (bcSmartspaceCardSecondary != null) {
            bcSmartspaceCardSecondary.setTextColor(i);
        }
        this.mIconTintColor = i;
        updateIconTint();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setSecondaryCard(BcSmartspaceCardSecondary bcSmartspaceCardSecondary) {
        ViewGroup viewGroup = this.mSecondaryCardGroup;
        if (viewGroup == null) {
            return;
        }
        this.mSecondaryCard = bcSmartspaceCardSecondary;
        BcSmartspaceTemplateDataUtils.updateVisibility(viewGroup, 8);
        this.mSecondaryCardGroup.removeAllViews();
        if (bcSmartspaceCardSecondary != null) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-2, getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_card_height));
            layoutParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_secondary_card_start_margin));
            layoutParams.startToStart = 0;
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            this.mSecondaryCardGroup.addView(bcSmartspaceCardSecondary, layoutParams);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setSubtitle(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        TextView textView = this.mSubtitleTextView;
        if (textView == null) {
            Log.w("BcSmartspaceCard", "No subtitle view to update");
            return;
        }
        textView.setText(charSequence);
        this.mSubtitleTextView.setCompoundDrawablesRelative((TextUtils.isEmpty(charSequence) || !z) ? null : this.mIconDrawable, null, null, null);
        TextView textView2 = this.mSubtitleTextView;
        SmartspaceTarget smartspaceTarget = this.mTarget;
        textView2.setMaxLines((smartspaceTarget == null || smartspaceTarget.getFeatureType() != 5 || this.mUsePageIndicatorUi) ? 1 : 2);
        ContentDescriptionUtil.setFormattedContentDescription("BcSmartspaceCard", this.mSubtitleTextView, charSequence, charSequence2);
        BcSmartspaceTemplateDataUtils.offsetTextViewForIcon(this.mSubtitleTextView, z ? this.mIconDrawable : null, isRtl$1());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setTitle(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        boolean z2;
        TextView textView = this.mTitleTextView;
        if (textView == null) {
            Log.w("BcSmartspaceCard", "No title view to update");
            return;
        }
        textView.setText(charSequence);
        SmartspaceTarget smartspaceTarget = this.mTarget;
        SmartspaceAction headerAction = smartspaceTarget == null ? null : smartspaceTarget.getHeaderAction();
        Bundle extras = headerAction == null ? null : headerAction.getExtras();
        if (extras == null || !extras.containsKey("titleEllipsize")) {
            SmartspaceTarget smartspaceTarget2 = this.mTarget;
            if (smartspaceTarget2 != null && smartspaceTarget2.getFeatureType() == 2 && Locale.ENGLISH.getLanguage().equals(((ViewGroup) this).mContext.getResources().getConfiguration().locale.getLanguage())) {
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            } else {
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
            }
        } else {
            String string = extras.getString("titleEllipsize");
            try {
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.valueOf(string));
            } catch (IllegalArgumentException unused) {
                MotionLayout$$ExternalSyntheticOutline0.m("Invalid TruncateAt value: ", string, "BcSmartspaceCard");
            }
        }
        boolean z3 = false;
        if (extras != null) {
            int i = extras.getInt("titleMaxLines");
            if (i != 0) {
                this.mTitleTextView.setMaxLines(i);
            }
            z2 = extras.getBoolean("disableTitleIcon");
        } else {
            z2 = false;
        }
        if (z && !z2) {
            z3 = true;
        }
        if (z3) {
            ContentDescriptionUtil.setFormattedContentDescription("BcSmartspaceCard", this.mTitleTextView, charSequence, charSequence2);
        }
        this.mTitleTextView.setCompoundDrawablesRelative(z3 ? this.mIconDrawable : null, null, null, null);
        BcSmartspaceTemplateDataUtils.offsetTextViewForIcon(this.mTitleTextView, z3 ? this.mIconDrawable : null, isRtl$1());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateIconTint() {
        SmartspaceTarget smartspaceTarget = this.mTarget;
        if (smartspaceTarget == null) {
            return;
        }
        DoubleShadowIconDrawable doubleShadowIconDrawable = this.mIconDrawable;
        if (smartspaceTarget.getFeatureType() == 1) {
            doubleShadowIconDrawable.setTintList(null);
        } else {
            doubleShadowIconDrawable.setTint(this.mIconTintColor);
        }
        DoubleShadowIconDrawable doubleShadowIconDrawable2 = this.mBaseActionIconDrawable;
        SmartspaceAction baseAction = this.mTarget.getBaseAction();
        int i = -1;
        if (baseAction != null && baseAction.getExtras() != null && !baseAction.getExtras().isEmpty()) {
            i = baseAction.getExtras().getInt("subcardType", -1);
        }
        if (i == 1) {
            doubleShadowIconDrawable2.setTintList(null);
        } else {
            doubleShadowIconDrawable2.setTint(this.mIconTintColor);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BcSmartspaceCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSecondaryCard = null;
        this.mIconTintColor = GraphicsUtils.getAttrColor(android.R.attr.textColorPrimary, getContext());
        this.mTextGroup = null;
        this.mSecondaryCardGroup = null;
        this.mTitleTextView = null;
        this.mSubtitleTextView = null;
        this.mBaseActionIconSubtitleView = null;
        this.mBaseActionIconSubtitleHitRect = null;
        this.mUiSurface = null;
        this.mTouchDelegateIsDirty = false;
        context.getTheme().applyStyle(R.style.Smartspace, false);
        this.mIconDrawable = new DoubleShadowIconDrawable(context);
        this.mBaseActionIconDrawable = new DoubleShadowIconDrawable(context);
        setDefaultFocusHighlightEnabled(false);
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final View getView() {
        return this;
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final void setScreenOn(boolean z) {
    }
}
