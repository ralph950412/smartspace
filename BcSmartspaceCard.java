package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Rect;
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
import com.android.app.animation.Interpolators;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardMetadataLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.utils.ContentDescriptionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
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

    public BcSmartspaceCard(Context context) {
        this(context, null);
    }

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

    /* JADX WARN: Removed duplicated region for block: B:82:0x01df A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01e0  */
    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void bindData(android.app.smartspace.SmartspaceTarget r17, com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier r18, com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 519
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.BcSmartspaceCard.bindData(android.app.smartspace.SmartspaceTarget, com.android.systemui.plugins.BcSmartspaceDataPlugin$SmartspaceEventNotifier, com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo, boolean):void");
    }

    @Override // android.view.View
    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        createAccessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", " ");
        return createAccessibilityNodeInfo;
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final BcSmartspaceCardLoggingInfo getLoggingInfo() {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.mLoggingInfo;
        if (bcSmartspaceCardLoggingInfo != null) {
            return bcSmartspaceCardLoggingInfo;
        }
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount);
        SmartspaceTarget smartspaceTarget = this.mTarget;
        builder.mFeatureType = smartspaceTarget == null ? 0 : smartspaceTarget.getFeatureType();
        getContext().getPackageManager();
        builder.mUid = -1;
        return new BcSmartspaceCardLoggingInfo(builder);
    }

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
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mSecondaryCardGroup, (this.mDozeAmount > 1.0f ? 1 : (this.mDozeAmount == 1.0f ? 0 : -1)) == 0 || !this.mValidSecondaryCard ? 8 : 0);
        SmartspaceTarget smartspaceTarget2 = this.mTarget;
        if (smartspaceTarget2 == null || smartspaceTarget2.getFeatureType() != 30) {
            ViewGroup viewGroup = this.mSecondaryCardGroup;
            if (viewGroup == null || viewGroup.getVisibility() == 8) {
                this.mTextGroup.setTranslationX(0.0f);
            } else {
                this.mTextGroup.setTranslationX(((PathInterpolator) Interpolators.EMPHASIZED).getInterpolation(this.mDozeAmount) * this.mSecondaryCardGroup.getWidth() * (isRtl$1() ? 1 : -1));
                this.mSecondaryCardGroup.setAlpha(Math.max(0.0f, Math.min(1.0f, ((1.0f - this.mDozeAmount) * 9.0f) - 6.0f)));
            }
        }
    }

    public final void setPrimaryOnClickListener(SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceTarget smartspaceTarget2;
        SmartspaceAction smartspaceAction2;
        if (smartspaceAction != null) {
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = bcSmartspaceCardLoggingInfo;
            BcSmartSpaceUtil.setOnClickListener(this, smartspaceTarget, smartspaceAction, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo2, 0);
            TextView textView = this.mTitleTextView;
            if (textView != null) {
                BcSmartSpaceUtil.setOnClickListener(textView, smartspaceTarget, smartspaceAction, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo2, 0);
                smartspaceTarget2 = smartspaceTarget;
                smartspaceAction2 = smartspaceAction;
                bcSmartspaceCardLoggingInfo2 = bcSmartspaceCardLoggingInfo2;
            } else {
                smartspaceTarget2 = smartspaceTarget;
                smartspaceAction2 = smartspaceAction;
            }
            TextView textView2 = this.mSubtitleTextView;
            if (textView2 != null) {
                BcSmartSpaceUtil.setOnClickListener(textView2, smartspaceTarget2, smartspaceAction2, this.mEventNotifier, "BcSmartspaceCard", bcSmartspaceCardLoggingInfo2, 0);
            }
        }
    }

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

    public final void updateIconTint() {
        DoubleShadowIconDrawable doubleShadowIconDrawable;
        SmartspaceTarget smartspaceTarget = this.mTarget;
        if (smartspaceTarget == null || (doubleShadowIconDrawable = this.mIconDrawable) == null) {
            return;
        }
        if (smartspaceTarget.getFeatureType() != 1) {
            doubleShadowIconDrawable.setTint(this.mIconTintColor);
        } else {
            doubleShadowIconDrawable.setTintList(null);
        }
        DoubleShadowIconDrawable doubleShadowIconDrawable2 = this.mBaseActionIconDrawable;
        if (doubleShadowIconDrawable2 == null) {
            return;
        }
        SmartspaceAction baseAction = this.mTarget.getBaseAction();
        int i = -1;
        if (baseAction != null && baseAction.getExtras() != null && !baseAction.getExtras().isEmpty()) {
            i = baseAction.getExtras().getInt("subcardType", -1);
        }
        if (i != 1) {
            doubleShadowIconDrawable2.setTint(this.mIconTintColor);
        } else {
            doubleShadowIconDrawable2.setTintList(null);
        }
    }

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
    public final void setScreenOn(boolean z) {
    }

    @Override // com.google.android.systemui.smartspace.SmartspaceCard
    public final View getView() {
        return this;
    }
}
