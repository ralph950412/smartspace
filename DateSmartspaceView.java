package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ComponentName;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.compose.ui.tooling.ComposableInvoker$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.lang.invoke.VarHandle;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public class DateSmartspaceView extends LinearLayout implements BcSmartspaceDataPlugin.SmartspaceView {
    public static final boolean DEBUG = Log.isLoggable("DateSmartspaceView", 3);
    public final AnonymousClass1 mAodSettingsObserver;
    public Handler mBgHandler;
    public int mCurrentTextColor;
    public BcSmartspaceDataPlugin mDataProvider;
    public final SmartspaceAction mDateAction;
    public final SmartspaceTarget mDateTarget;
    public IcuDateTextView mDateView;
    public final DoubleShadowIconDrawable mDndIconDrawable;
    public ImageView mDndImageView;
    public float mDozeAmount;
    public boolean mIsAodEnabled;
    public BcSmartspaceCardLoggingInfo mLoggingInfo;
    public final BcNextAlarmData mNextAlarmData;
    public final DoubleShadowIconDrawable mNextAlarmIconDrawable;
    public DoubleShadowTextView mNextAlarmTextView;
    public int mPrimaryTextColor;
    public String mUiSurface;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.google.android.systemui.smartspace.DateSmartspaceView$1] */
    public DateSmartspaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mUiSurface = null;
        this.mDozeAmount = 0.0f;
        this.mDateTarget = new SmartspaceTarget.Builder("date_card_794317_92634", new ComponentName(getContext(), getClass()), getContext().getUser()).setFeatureType(1).build();
        this.mDateAction = new SmartspaceAction.Builder("dateId", "Date").setIntent(BcSmartSpaceUtil.getOpenCalendarIntent()).build();
        this.mNextAlarmData = new BcNextAlarmData();
        this.mAodSettingsObserver = new ContentObserver(new Handler()) { // from class: com.google.android.systemui.smartspace.DateSmartspaceView.1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                DateSmartspaceView dateSmartspaceView = DateSmartspaceView.this;
                boolean z2 = DateSmartspaceView.DEBUG;
                Context context2 = dateSmartspaceView.getContext();
                boolean z3 = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
                DateSmartspaceView dateSmartspaceView2 = DateSmartspaceView.this;
                if (dateSmartspaceView2.mIsAodEnabled == z3) {
                    return;
                }
                dateSmartspaceView2.mIsAodEnabled = z3;
            }
        };
        context.getTheme().applyStyle(R.style.Smartspace, false);
        this.mNextAlarmIconDrawable = new DoubleShadowIconDrawable(context);
        this.mDndIconDrawable = new DoubleShadowIconDrawable(context);
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
                Log.w("DateSmartspaceView", "Unable to register DOZE_ALWAYS_ON content observer: ", e);
            }
            if (handler == null) {
                throw new IllegalStateException("Must set background handler to avoid making binder calls on main thread");
            }
            DateSmartspaceView$$ExternalSyntheticLambda0 dateSmartspaceView$$ExternalSyntheticLambda0 = new DateSmartspaceView$$ExternalSyntheticLambda0(0);
            dateSmartspaceView$$ExternalSyntheticLambda0.f$0 = this;
            VarHandle.storeStoreFence();
            handler.post(dateSmartspaceView$$ExternalSyntheticLambda0);
            Context context = getContext();
            this.mIsAodEnabled = Settings.Secure.getIntForUser(context.getContentResolver(), "doze_always_on", 0, context.getUserId()) == 1;
        }
        int create = InstanceId.create(this.mDateTarget);
        int featureType = this.mDateTarget.getFeatureType();
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount);
        getContext().getPackageManager();
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo.mInstanceId = create;
        bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo.mRank = 0;
        bcSmartspaceCardLoggingInfo.mCardinality = 0;
        bcSmartspaceCardLoggingInfo.mFeatureType = featureType;
        bcSmartspaceCardLoggingInfo.mReceivedLatency = 0;
        bcSmartspaceCardLoggingInfo.mUid = -1;
        bcSmartspaceCardLoggingInfo.mSubcardInfo = null;
        bcSmartspaceCardLoggingInfo.mDimensionalInfo = null;
        VarHandle.storeStoreFence();
        this.mLoggingInfo = bcSmartspaceCardLoggingInfo;
        IcuDateTextView icuDateTextView = this.mDateView;
        SmartspaceTarget smartspaceTarget = this.mDateTarget;
        SmartspaceAction smartspaceAction = this.mDateAction;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        BcSmartSpaceUtil.setOnClickListener(icuDateTextView, smartspaceTarget, smartspaceAction, bcSmartspaceDataPlugin != null ? bcSmartspaceDataPlugin.getEventNotifier() : null, "DateSmartspaceView", this.mLoggingInfo, 0);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mBgHandler;
        if (handler == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Must set background handler to avoid making binder calls on main thread");
            return;
        }
        DateSmartspaceView$$ExternalSyntheticLambda0 dateSmartspaceView$$ExternalSyntheticLambda0 = new DateSmartspaceView$$ExternalSyntheticLambda0(1);
        dateSmartspaceView$$ExternalSyntheticLambda0.f$0 = this;
        VarHandle.storeStoreFence();
        handler.post(dateSmartspaceView$$ExternalSyntheticLambda0);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDateView = (IcuDateTextView) findViewById(R.id.date);
        this.mNextAlarmTextView = (DoubleShadowTextView) findViewById(R.id.alarm_text_view);
        this.mDndImageView = (ImageView) findViewById(R.id.dnd_icon);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        this.mDataProvider = bcSmartspaceDataPlugin;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setBgHandler(Handler handler) {
        this.mBgHandler = handler;
        this.mDateView.mBgHandler = handler;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDnd(Drawable drawable, String str) {
        if (drawable == null) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mDndImageView, 8);
        } else {
            this.mDndIconDrawable.setIcon(drawable.mutate());
            this.mDndImageView.setImageDrawable(this.mDndIconDrawable);
            this.mDndImageView.setContentDescription(str);
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mDndImageView, 0);
        }
        updateColorForExtras();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozeAmount(float f) {
        int loggingDisplaySurface;
        this.mDozeAmount = f;
        int blendARGB = ColorUtils.blendARGB(this.mPrimaryTextColor, -1, f);
        this.mCurrentTextColor = blendARGB;
        this.mDateView.setTextColor(blendARGB);
        updateColorForExtras();
        if (this.mLoggingInfo == null || (loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount)) == -1) {
            return;
        }
        if (loggingDisplaySurface != 3 || this.mIsAodEnabled) {
            if (DEBUG) {
                Log.d("DateSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", setDozeAmount: Logging SMARTSPACE_CARD_SEEN, loggingSurface = " + loggingDisplaySurface);
            }
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
            BcSmartspaceEvent bcSmartspaceEvent = BcSmartspaceEvent.SMARTSPACE_CARD_SEEN;
            BcSmartspaceCardLogger.log(bcSmartspaceEvent, bcSmartspaceCardLoggingInfo2);
            if (this.mNextAlarmData.mImage != null) {
                int create = InstanceId.create("upcoming_alarm_card_94510_12684");
                int i4 = this.mLoggingInfo.mUid;
                BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo3 = new BcSmartspaceCardLoggingInfo();
                bcSmartspaceCardLoggingInfo3.mInstanceId = create;
                bcSmartspaceCardLoggingInfo3.mDisplaySurface = loggingDisplaySurface;
                bcSmartspaceCardLoggingInfo3.mRank = 0;
                bcSmartspaceCardLoggingInfo3.mCardinality = 0;
                bcSmartspaceCardLoggingInfo3.mFeatureType = 23;
                bcSmartspaceCardLoggingInfo3.mReceivedLatency = 0;
                bcSmartspaceCardLoggingInfo3.mUid = i4;
                bcSmartspaceCardLoggingInfo3.mSubcardInfo = null;
                bcSmartspaceCardLoggingInfo3.mDimensionalInfo = null;
                VarHandle.storeStoreFence();
                BcSmartspaceCardLogger.log(bcSmartspaceEvent, bcSmartspaceCardLoggingInfo3);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setFalsingManager(FalsingManager falsingManager) {
        BcSmartSpaceUtil.sFalsingManager = falsingManager;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setNextAlarm(Drawable drawable, String str) {
        BcNextAlarmData bcNextAlarmData = this.mNextAlarmData;
        bcNextAlarmData.mImage = drawable;
        if (drawable != null) {
            drawable.mutate();
        }
        bcNextAlarmData.mDescription = str;
        Drawable drawable2 = this.mNextAlarmData.mImage;
        DoubleShadowTextView doubleShadowTextView = this.mNextAlarmTextView;
        if (drawable2 == null) {
            BcSmartspaceTemplateDataUtils.updateVisibility(doubleShadowTextView, 8);
        } else {
            doubleShadowTextView.setContentDescription(getContext().getString(R.string.accessibility_next_alarm, str));
            DoubleShadowTextView doubleShadowTextView2 = this.mNextAlarmTextView;
            BcNextAlarmData bcNextAlarmData2 = this.mNextAlarmData;
            bcNextAlarmData2.getClass();
            boolean isEmpty = TextUtils.isEmpty(null);
            String str2 = bcNextAlarmData2.mDescription;
            if (!isEmpty) {
                str2 = ComposableInvoker$$ExternalSyntheticOutline0.m(str2, " · null");
            }
            doubleShadowTextView2.setText(str2);
            DoubleShadowIconDrawable doubleShadowIconDrawable = this.mNextAlarmIconDrawable;
            Drawable drawable3 = this.mNextAlarmData.mImage;
            Context context = getContext();
            FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_icon_size);
            drawable3.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            doubleShadowIconDrawable.setIcon(drawable3);
            this.mNextAlarmTextView.setCompoundDrawablesRelative(this.mNextAlarmIconDrawable, null, null, null);
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mNextAlarmTextView, 0);
            BcNextAlarmData bcNextAlarmData3 = this.mNextAlarmData;
            DoubleShadowTextView doubleShadowTextView3 = this.mNextAlarmTextView;
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
            BcSmartspaceDataPlugin.SmartspaceEventNotifier eventNotifier = bcSmartspaceDataPlugin == null ? null : bcSmartspaceDataPlugin.getEventNotifier();
            int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mUiSurface, this.mDozeAmount);
            bcNextAlarmData3.getClass();
            int create = InstanceId.create("upcoming_alarm_card_94510_12684");
            BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
            bcSmartspaceCardLoggingInfo.mInstanceId = create;
            bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
            bcSmartspaceCardLoggingInfo.mRank = 0;
            bcSmartspaceCardLoggingInfo.mCardinality = 0;
            bcSmartspaceCardLoggingInfo.mFeatureType = 23;
            bcSmartspaceCardLoggingInfo.mReceivedLatency = 0;
            bcSmartspaceCardLoggingInfo.mUid = 0;
            bcSmartspaceCardLoggingInfo.mSubcardInfo = null;
            bcSmartspaceCardLoggingInfo.mDimensionalInfo = null;
            VarHandle.storeStoreFence();
            BcSmartSpaceUtil.setOnClickListener(doubleShadowTextView3, null, BcNextAlarmData.SHOW_ALARMS_ACTION, eventNotifier, "BcNextAlarmData", bcSmartspaceCardLoggingInfo, 0);
        }
        updateColorForExtras();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.widget.LinearLayout
    public final void setOrientation(int i) {
        super.setOrientation(i);
        WeatherSmartspaceView weatherSmartspaceView = (WeatherSmartspaceView) findViewById(R.id.weather_smartspace_view);
        if (i != 0 && weatherSmartspaceView != null) {
            if (i == 1) {
                this.mDateView.setOnClickListener(null);
                this.mDateView.setClickable(false);
                setClickable(true);
                setFocusable(true);
                setOnClickListener(weatherSmartspaceView.mOnClickListener);
                return;
            }
            return;
        }
        IcuDateTextView icuDateTextView = this.mDateView;
        SmartspaceTarget smartspaceTarget = this.mDateTarget;
        SmartspaceAction smartspaceAction = this.mDateAction;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        BcSmartSpaceUtil.setOnClickListener(icuDateTextView, smartspaceTarget, smartspaceAction, bcSmartspaceDataPlugin == null ? null : bcSmartspaceDataPlugin.getEventNotifier(), "DateSmartspaceView", this.mLoggingInfo, 0);
        setClickable(false);
        setFocusable(false);
        setOnClickListener(null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setPrimaryTextColor(int i) {
        this.mPrimaryTextColor = i;
        int blendARGB = ColorUtils.blendARGB(i, -1, this.mDozeAmount);
        this.mCurrentTextColor = blendARGB;
        this.mDateView.setTextColor(blendARGB);
        updateColorForExtras();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setScreenOn(boolean z) {
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            icuDateTextView.mIsInteractive = Boolean.valueOf(z);
            icuDateTextView.rescheduleTicker();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            if (icuDateTextView.isAttachedToWindow()) {
                Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
            } else {
                icuDateTextView.mTimeChangedDelegate = timeChangedDelegate;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setUiSurface(String str) {
        if (isAttachedToWindow()) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
            return;
        }
        this.mUiSurface = str;
        if (TextUtils.equals(str, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            IcuDateTextView icuDateTextView = this.mDateView;
            if (icuDateTextView.isAttachedToWindow()) {
                Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
            } else {
                icuDateTextView.mUpdatesOnAod = true;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateColorForExtras() {
        DoubleShadowTextView doubleShadowTextView = this.mNextAlarmTextView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(this.mCurrentTextColor);
            this.mNextAlarmIconDrawable.setTint(this.mCurrentTextColor);
        }
        ImageView imageView = this.mDndImageView;
        if (imageView == null || imageView.getDrawable() == null) {
            return;
        }
        imageView.getDrawable().setTint(this.mCurrentTextColor);
        imageView.invalidate();
    }

    public DateSmartspaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DateSmartspaceView(Context context) {
        this(context, null);
    }
}
