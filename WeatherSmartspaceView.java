package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.app.smartspace.uitemplatedata.Icon;
import android.app.smartspace.uitemplatedata.TapAction;
import android.app.smartspace.uitemplatedata.Text;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.bcsmartspace.R$styleable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.utils.ContentDescriptionUtil;
import java.lang.invoke.VarHandle;
import java.util.List;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class WeatherSmartspaceView extends LinearLayout implements BcSmartspaceDataPlugin.SmartspaceTargetListener, BcSmartspaceDataPlugin.SmartspaceView {
    public static final boolean DEBUG = Log.isLoggable("WeatherSmartspaceView", 3);
    public final AnonymousClass1 mAodSettingsObserver;
    public Handler mBgHandler;
    public BcSmartspaceDataPlugin mDataProvider;
    public float mDozeAmount;
    public final DoubleShadowIconDrawable mIconDrawable;
    public final int mIconSize;
    public boolean mIsAodEnabled;
    public BcSmartspaceCardLoggingInfo mLoggingInfo;
    public int mPrimaryTextColor;
    public final boolean mRemoveTextDescent;
    public final int mTextDescentExtraPadding;
    public String mUiSurface;
    public DoubleShadowTextView mView;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public WeatherSmartspaceView(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        Handler handler;
        super.onAttachedToWindow();
        if (TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            try {
                handler = this.mBgHandler;
            } catch (Exception e) {
                Log.w("WeatherSmartspaceView", "Unable to register DOZE_ALWAYS_ON content observer: ", e);
            }
            if (handler == null) {
                throw new IllegalStateException("Must set background handler to avoid making binder calls on main thread");
            }
            WeatherSmartspaceView$$ExternalSyntheticLambda0 weatherSmartspaceView$$ExternalSyntheticLambda0 = new WeatherSmartspaceView$$ExternalSyntheticLambda0(0);
            weatherSmartspaceView$$ExternalSyntheticLambda0.f$0 = this;
            VarHandle.storeStoreFence();
            handler.post(weatherSmartspaceView$$ExternalSyntheticLambda0);
            Context context = getContext();
            this.mIsAodEnabled = Settings.Secure.getIntForUser(context.getContentResolver(), "doze_always_on", 0, context.getUserId()) == 1;
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.registerListener(this);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mBgHandler;
        if (handler == null) {
            throw new IllegalStateException("Must set background handler to avoid making binder calls on main thread");
        }
        WeatherSmartspaceView$$ExternalSyntheticLambda0 weatherSmartspaceView$$ExternalSyntheticLambda0 = new WeatherSmartspaceView$$ExternalSyntheticLambda0(1);
        weatherSmartspaceView$$ExternalSyntheticLambda0.f$0 = this;
        VarHandle.storeStoreFence();
        handler.post(weatherSmartspaceView$$ExternalSyntheticLambda0);
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.unregisterListener(this);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mView = (DoubleShadowTextView) findViewById(R.id.weather_text_view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        if (list.size() > 1) {
            return;
        }
        if (list.isEmpty() && TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM)) {
            return;
        }
        if (list.isEmpty()) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mView, 8);
            return;
        }
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mView, 0);
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) list.get(0);
        if (smartspaceTarget.getFeatureType() != 1) {
            return;
        }
        boolean containsValidTemplateType = BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
        SmartspaceAction headerAction = smartspaceTarget.getHeaderAction();
        if (containsValidTemplateType || headerAction != null) {
            int create = InstanceId.create(smartspaceTarget);
            int featureType = smartspaceTarget.getFeatureType();
            int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount);
            getContext().getPackageManager();
            SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
            bcSmartspaceCardLoggingInfo.mInstanceId = create;
            bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
            bcSmartspaceCardLoggingInfo.mRank = 0;
            bcSmartspaceCardLoggingInfo.mCardinality = 0;
            bcSmartspaceCardLoggingInfo.mFeatureType = featureType;
            bcSmartspaceCardLoggingInfo.mReceivedLatency = 0;
            bcSmartspaceCardLoggingInfo.mUid = -1;
            bcSmartspaceCardLoggingInfo.mSubcardInfo = null;
            bcSmartspaceCardLoggingInfo.mDimensionalInfo = createDimensionalLoggingInfo;
            VarHandle.storeStoreFence();
            this.mLoggingInfo = bcSmartspaceCardLoggingInfo;
            if (!containsValidTemplateType) {
                SmartspaceAction headerAction2 = smartspaceTarget.getHeaderAction();
                if (headerAction2 == null) {
                    Log.d("WeatherSmartspaceView", "Passed-in header action is null");
                } else {
                    CharSequence title = headerAction2.getTitle();
                    this.mView.setText(title.toString());
                    this.mView.setCompoundDrawablesRelative(null, null, null, null);
                    TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
                    this.mIconDrawable.setIcon(BcSmartSpaceUtil.getIconDrawableWithCustomSize(headerAction2.getIcon(), getContext(), this.mIconSize));
                    this.mView.setCompoundDrawablesRelative(this.mIconDrawable, null, null, null);
                    ContentDescriptionUtil.setFormattedContentDescription("WeatherSmartspaceView", this.mView, title, headerAction2.getContentDescription());
                    if (!TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM)) {
                        DoubleShadowTextView doubleShadowTextView = this.mView;
                        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
                        BcSmartSpaceUtil.setOnClickListener(doubleShadowTextView, smartspaceTarget, headerAction2, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, "WeatherSmartspaceView", this.mLoggingInfo, 0);
                    }
                }
            } else if (smartspaceTarget.getTemplateData() != null) {
                BaseTemplateData.SubItemInfo subtitleItem = smartspaceTarget.getTemplateData().getSubtitleItem();
                if (subtitleItem == null) {
                    Log.d("WeatherSmartspaceView", "Passed-in item info is null");
                } else {
                    Text text = subtitleItem.getText();
                    BcSmartspaceTemplateDataUtils.setText(this.mView, text);
                    this.mView.setCompoundDrawablesRelative(null, null, null, null);
                    Icon icon = subtitleItem.getIcon();
                    TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
                    if (icon != null) {
                        this.mIconDrawable.setIcon(BcSmartSpaceUtil.getIconDrawableWithCustomSize(icon.getIcon(), getContext(), this.mIconSize));
                        this.mView.setCompoundDrawablesRelative(this.mIconDrawable, null, null, null);
                    }
                    ContentDescriptionUtil.setFormattedContentDescription("WeatherSmartspaceView", this.mView, SmartspaceUtils.isEmpty(text) ? "" : text.getText(), icon != null ? icon.getContentDescription() : "");
                    TapAction tapAction = subtitleItem.getTapAction();
                    if (tapAction != null && !TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM)) {
                        DoubleShadowTextView doubleShadowTextView2 = this.mView;
                        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.mDataProvider;
                        BcSmartSpaceUtil.setOnClickListener$1(doubleShadowTextView2, smartspaceTarget, tapAction, bcSmartspaceDataPlugin2 != null ? bcSmartspaceDataPlugin2.getEventNotifier() : null, "WeatherSmartspaceView", this.mLoggingInfo, 0);
                    }
                }
            }
            if (this.mRemoveTextDescent) {
                this.mView.setPaddingRelative(0, 0, 0, this.mTextDescentExtraPadding - ((int) Math.floor(r12.getPaint().getFontMetrics().descent)));
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.mDataProvider;
        if (bcSmartspaceDataPlugin2 != null) {
            bcSmartspaceDataPlugin2.unregisterListener(this);
        }
        this.mDataProvider = bcSmartspaceDataPlugin;
        if (isAttachedToWindow()) {
            this.mDataProvider.registerListener(this);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setBgHandler(Handler handler) {
        this.mBgHandler = handler;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozeAmount(float f) {
        int loggingDisplaySurface;
        this.mDozeAmount = f;
        this.mView.setTextColor(ColorUtils.blendARGB(this.mPrimaryTextColor, -1, f));
        TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
        if (this.mLoggingInfo == null || (loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount)) == -1) {
            return;
        }
        if (loggingDisplaySurface != 3 || this.mIsAodEnabled) {
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.mLoggingInfo;
            int i = bcSmartspaceCardLoggingInfo.mInstanceId;
            int i2 = bcSmartspaceCardLoggingInfo.mFeatureType;
            int i3 = bcSmartspaceCardLoggingInfo.mUid;
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo2 = new BcSmartspaceCardLoggingInfo();
            bcSmartspaceCardLoggingInfo2.mInstanceId = i;
            bcSmartspaceCardLoggingInfo2.mDisplaySurface = loggingDisplaySurface;
            bcSmartspaceCardLoggingInfo2.mRank = 0;
            bcSmartspaceCardLoggingInfo2.mCardinality = 0;
            bcSmartspaceCardLoggingInfo2.mFeatureType = i2;
            bcSmartspaceCardLoggingInfo2.mReceivedLatency = 0;
            bcSmartspaceCardLoggingInfo2.mUid = i3;
            bcSmartspaceCardLoggingInfo2.mSubcardInfo = null;
            bcSmartspaceCardLoggingInfo2.mDimensionalInfo = null;
            VarHandle.storeStoreFence();
            if (DEBUG) {
                Log.d("WeatherSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", setDozeAmount: Logging SMARTSPACE_CARD_SEEN, loggingSurface = " + loggingDisplaySurface);
            }
            BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_SEEN, bcSmartspaceCardLoggingInfo2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setFalsingManager(FalsingManager falsingManager) {
        BcSmartSpaceUtil.sFalsingManager = falsingManager;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setPrimaryTextColor(int i) {
        this.mPrimaryTextColor = i;
        this.mView.setTextColor(ColorUtils.blendARGB(i, -1, this.mDozeAmount));
        TextUtils.equals(this.mUiSurface, BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setUiSurface(String str) {
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Must call before attaching view to window.");
        }
        this.mUiSurface = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public WeatherSmartspaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.android.systemui.smartspace.WeatherSmartspaceView$1] */
    public WeatherSmartspaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mUiSurface = null;
        this.mDozeAmount = 0.0f;
        this.mLoggingInfo = null;
        this.mAodSettingsObserver = new ContentObserver(new Handler()) { // from class: com.google.android.systemui.smartspace.WeatherSmartspaceView.1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                WeatherSmartspaceView weatherSmartspaceView = WeatherSmartspaceView.this;
                boolean z2 = WeatherSmartspaceView.DEBUG;
                Context context2 = weatherSmartspaceView.getContext();
                boolean z3 = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
                WeatherSmartspaceView weatherSmartspaceView2 = WeatherSmartspaceView.this;
                if (weatherSmartspaceView2.mIsAodEnabled == z3) {
                    return;
                }
                weatherSmartspaceView2.mIsAodEnabled = z3;
            }
        };
        context.getTheme().applyStyle(R.style.Smartspace, false);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.WeatherSmartspaceView, 0, 0);
        try {
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, context.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_size));
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(0, context.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_inset));
            this.mRemoveTextDescent = obtainStyledAttributes.getBoolean(2, false);
            this.mTextDescentExtraPadding = obtainStyledAttributes.getDimensionPixelSize(3, 0);
            obtainStyledAttributes.recycle();
            this.mIconSize = dimensionPixelSize;
            this.mIconDrawable = new DoubleShadowIconDrawable(dimensionPixelSize, dimensionPixelSize2, context);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
