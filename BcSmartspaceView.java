package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.PageTransformerAdapter;
import androidx.viewpager2.widget.ScrollEventAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.systemui.customization.clocks.R$dimen;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.CardPagerAdapter;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import java.lang.invoke.VarHandle;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceView extends FrameLayout implements BcSmartspaceDataPlugin.SmartspaceTargetListener, BcSmartspaceDataPlugin.SmartspaceView {
    public static final boolean DEBUG = Log.isLoggable("BcSmartspaceView", 3);
    public CardAdapter mAdapter;
    public final AnonymousClass1 mAodObserver;
    public Handler mBgHandler;
    public int mCardPosition;
    public BcSmartspaceConfigPlugin mConfigProvider;
    public BcSmartspaceDataPlugin mDataProvider;
    public boolean mHasPerformedLongPress;
    public boolean mHasPostedLongPress;
    public boolean mIsAodEnabled;
    public final ArraySet mLastReceivedTargets;
    public final BcSmartspaceView$$ExternalSyntheticLambda2 mLongPressCallback;
    public PageIndicator mPageIndicator;
    public PagerDots mPagerDots;
    public List mPendingTargets;
    public RecyclerView.ViewHolder mPreInflatedViewHolder;
    public float mPreviousDozeAmount;
    public final RecyclerView.RecycledViewPool mRecycledViewPool;
    public int mScrollState;
    public boolean mSplitShadeEnabled;
    public Integer mSwipedCardPosition;
    public ViewPager mViewPager;
    public ViewPager2 mViewPager2;
    public final AnonymousClass2 mViewPager2OnPageChangeCallback;
    public final AnonymousClass3 mViewPagerOnPageChangeListener;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceView$2, reason: invalid class name */
    public final class AnonymousClass2 extends ViewPager2.OnPageChangeCallback {
        public /* synthetic */ BcSmartspaceView this$0;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrollStateChanged(int i) {
            Integer num;
            SmartspaceCard cardAtPosition;
            BcSmartspaceView bcSmartspaceView = this.this$0;
            bcSmartspaceView.mScrollState = i;
            if (i == 1) {
                bcSmartspaceView.mSwipedCardPosition = Integer.valueOf(bcSmartspaceView.mViewPager2.mCurrentItem);
            }
            if (i == 0) {
                if (bcSmartspaceView.mConfigProvider.isSwipeEventLoggingEnabled() && (num = bcSmartspaceView.mSwipedCardPosition) != null && num.intValue() != bcSmartspaceView.mViewPager2.mCurrentItem && (cardAtPosition = bcSmartspaceView.mAdapter.getCardAtPosition(bcSmartspaceView.mSwipedCardPosition.intValue())) != null) {
                    BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_SWIPE, cardAtPosition.getLoggingInfo());
                }
                bcSmartspaceView.mSwipedCardPosition = null;
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrolled(int i, float f, int i2) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            boolean z = BcSmartspaceView.DEBUG;
            bcSmartspaceView.setSelectedDot(f, i);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageSelected(int i) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            boolean z = BcSmartspaceView.DEBUG;
            bcSmartspaceView.setSelectedDot(0.0f, i);
            BcSmartspaceView.m2501$$Nest$monViewPagerPageSelected(bcSmartspaceView, i);
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceView$3, reason: invalid class name */
    public final class AnonymousClass3 implements ViewPager.OnPageChangeListener {
        public /* synthetic */ BcSmartspaceView this$0;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrollStateChanged(int i) {
            Integer num;
            SmartspaceCard cardAtPosition;
            BcSmartspaceView bcSmartspaceView = this.this$0;
            bcSmartspaceView.mScrollState = i;
            if (i == 1) {
                bcSmartspaceView.mSwipedCardPosition = Integer.valueOf(bcSmartspaceView.mViewPager.mCurItem);
            }
            if (i == 0) {
                if (bcSmartspaceView.mConfigProvider.isSwipeEventLoggingEnabled() && (num = bcSmartspaceView.mSwipedCardPosition) != null && num.intValue() != bcSmartspaceView.mViewPager.mCurItem && (cardAtPosition = bcSmartspaceView.mAdapter.getCardAtPosition(bcSmartspaceView.mSwipedCardPosition.intValue())) != null) {
                    BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_SWIPE, cardAtPosition.getLoggingInfo());
                }
                bcSmartspaceView.mSwipedCardPosition = null;
                List list = bcSmartspaceView.mPendingTargets;
                if (list != null) {
                    bcSmartspaceView.onSmartspaceTargetsUpdated(list);
                }
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(int i, float f, int i2) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            boolean z = BcSmartspaceView.DEBUG;
            bcSmartspaceView.setSelectedDot(f, i);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            boolean z = BcSmartspaceView.DEBUG;
            bcSmartspaceView.setSelectedDot(0.0f, i);
            BcSmartspaceView.m2501$$Nest$monViewPagerPageSelected(bcSmartspaceView, i);
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceView$4, reason: invalid class name */
    public final class AnonymousClass4 {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: -$$Nest$monViewPagerPageSelected, reason: not valid java name */
    public static void m2501$$Nest$monViewPagerPageSelected(BcSmartspaceView bcSmartspaceView, int i) {
        SmartspaceTarget targetAtPosition = bcSmartspaceView.mAdapter.getTargetAtPosition(bcSmartspaceView.mCardPosition);
        bcSmartspaceView.mCardPosition = i;
        SmartspaceTarget targetAtPosition2 = bcSmartspaceView.mAdapter.getTargetAtPosition(i);
        if (targetAtPosition2 != null) {
            bcSmartspaceView.logSmartspaceEvent(targetAtPosition2, bcSmartspaceView.mCardPosition, BcSmartspaceEvent.SMARTSPACE_CARD_SEEN);
        }
        if (bcSmartspaceView.mDataProvider == null) {
            Log.w("BcSmartspaceView", "Cannot notify target hidden/shown smartspace events: data provider null");
            return;
        }
        if (targetAtPosition == null) {
            Log.w("BcSmartspaceView", "Cannot notify target hidden smartspace event: previous target is null.");
        } else {
            SmartspaceTargetEvent.Builder builder = new SmartspaceTargetEvent.Builder(3);
            builder.setSmartspaceTarget(targetAtPosition);
            SmartspaceAction baseAction = targetAtPosition.getBaseAction();
            if (baseAction != null) {
                builder.setSmartspaceActionId(baseAction.getId());
            }
            bcSmartspaceView.mDataProvider.getEventNotifier().notifySmartspaceEvent(builder.build());
        }
        if (targetAtPosition2 == null) {
            Log.w("BcSmartspaceView", "Cannot notify target shown smartspace event: shown card smartspace target null.");
            return;
        }
        SmartspaceTargetEvent.Builder builder2 = new SmartspaceTargetEvent.Builder(2);
        builder2.setSmartspaceTarget(targetAtPosition2);
        SmartspaceAction baseAction2 = targetAtPosition2.getBaseAction();
        if (baseAction2 != null) {
            builder2.setSmartspaceActionId(baseAction2.getId());
        }
        bcSmartspaceView.mDataProvider.getEventNotifier().notifySmartspaceEvent(builder2.build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.systemui.smartspace.BcSmartspaceView$1] */
    public BcSmartspaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mConfigProvider = new DefaultBcSmartspaceConfigProvider();
        this.mRecycledViewPool = new RecyclerView.RecycledViewPool();
        this.mPreInflatedViewHolder = null;
        this.mLastReceivedTargets = new ArraySet();
        this.mIsAodEnabled = false;
        this.mCardPosition = 0;
        this.mPreviousDozeAmount = 0.0f;
        this.mScrollState = 0;
        this.mSplitShadeEnabled = false;
        this.mAodObserver = new ContentObserver(new Handler()) { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                BcSmartspaceView bcSmartspaceView = BcSmartspaceView.this;
                boolean z2 = BcSmartspaceView.DEBUG;
                Context context2 = bcSmartspaceView.getContext();
                bcSmartspaceView.mIsAodEnabled = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
            }
        };
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        anonymousClass2.this$0 = this;
        VarHandle.storeStoreFence();
        this.mViewPager2OnPageChangeCallback = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        anonymousClass3.this$0 = this;
        VarHandle.storeStoreFence();
        this.mViewPagerOnPageChangeListener = anonymousClass3;
        BcSmartspaceView$$ExternalSyntheticLambda2 bcSmartspaceView$$ExternalSyntheticLambda2 = new BcSmartspaceView$$ExternalSyntheticLambda2(2);
        bcSmartspaceView$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        this.mLongPressCallback = bcSmartspaceView$$ExternalSyntheticLambda2;
        getContext().getTheme().applyStyle(R.style.DefaultSmartspaceView, false);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void cancelScheduledLongPress() {
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 != null && this.mHasPostedLongPress) {
            this.mHasPostedLongPress = false;
            viewPager2.removeCallbacks(this.mLongPressCallback);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final int getCurrentCardTopPadding() {
        if (this.mAdapter.getLegacyCardAtPosition(getSelectedPage()) != null) {
            return this.mAdapter.getLegacyCardAtPosition(getSelectedPage()).getPaddingTop();
        }
        if (this.mAdapter.getTemplateCardAtPosition(getSelectedPage()) != null) {
            return this.mAdapter.getTemplateCardAtPosition(getSelectedPage()).getPaddingTop();
        }
        if (this.mAdapter.getRemoteViewsCardAtPosition(getSelectedPage()) != null) {
            return this.mAdapter.getRemoteViewsCardAtPosition(getSelectedPage()).getPaddingTop();
        }
        return 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final int getSelectedPage() {
        ViewPager viewPager = this.mViewPager;
        int i = viewPager != null ? viewPager.mCurItem : 0;
        ViewPager2 viewPager2 = this.mViewPager2;
        return viewPager2 != null ? viewPager2.mCurrentItem : i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean handleTouchOverride(MotionEvent motionEvent, BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0) {
        boolean onTouchEvent;
        if (this.mViewPager2 != null) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mHasPerformedLongPress = false;
                if (this.mViewPager2.isLongClickable()) {
                    cancelScheduledLongPress();
                    this.mHasPostedLongPress = true;
                    this.mViewPager2.postDelayed(this.mLongPressCallback, ViewConfiguration.getLongPressTimeout());
                }
            } else if (action == 1 || action == 3) {
                cancelScheduledLongPress();
            }
            if (this.mHasPerformedLongPress) {
                cancelScheduledLongPress();
                return true;
            }
            int i = bcSmartspaceView$$ExternalSyntheticLambda0.$r8$classId;
            ViewPager2 viewPager2 = bcSmartspaceView$$ExternalSyntheticLambda0.f$0;
            switch (i) {
                case 0:
                    onTouchEvent = viewPager2.onTouchEvent(motionEvent);
                    break;
                default:
                    onTouchEvent = viewPager2.onInterceptTouchEvent(motionEvent);
                    break;
            }
            if (onTouchEvent) {
                cancelScheduledLongPress();
                return true;
            }
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void logSmartspaceEvent(SmartspaceTarget smartspaceTarget, int i, BcSmartspaceEvent bcSmartspaceEvent) {
        int i2;
        if (bcSmartspaceEvent == BcSmartspaceEvent.SMARTSPACE_CARD_RECEIVED) {
            try {
                i2 = (int) Instant.now().minusMillis(smartspaceTarget.getCreationTimeMillis()).toEpochMilli();
            } catch (ArithmeticException | DateTimeException e) {
                Log.e("BcSmartspaceView", "received_latency_millis will be -1 due to exception ", e);
                i2 = -1;
            }
        } else {
            i2 = 0;
        }
        boolean containsValidTemplateType = BcSmartspaceCardLoggerUtil.containsValidTemplateType(smartspaceTarget.getTemplateData());
        int create = InstanceId.create(smartspaceTarget);
        int featureType = smartspaceTarget.getFeatureType();
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mAdapter.getUiSurface(), this.mAdapter.getDozeAmount());
        int count = this.mAdapter.getCount();
        getContext().getPackageManager();
        BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo = containsValidTemplateType ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo.mInstanceId = create;
        bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo.mRank = i;
        bcSmartspaceCardLoggingInfo.mCardinality = count;
        bcSmartspaceCardLoggingInfo.mFeatureType = featureType;
        bcSmartspaceCardLoggingInfo.mReceivedLatency = i2;
        bcSmartspaceCardLoggingInfo.mUid = -1;
        bcSmartspaceCardLoggingInfo.mSubcardInfo = createSubcardLoggingInfo;
        bcSmartspaceCardLoggingInfo.mDimensionalInfo = createDimensionalLoggingInfo;
        VarHandle.storeStoreFence();
        if (containsValidTemplateType) {
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(bcSmartspaceCardLoggingInfo, smartspaceTarget.getTemplateData());
        }
        BcSmartspaceCardLogger.log(bcSmartspaceEvent, bcSmartspaceCardLoggingInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:11:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAttachedToWindow() {
        PagerDots pagerDots;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin;
        super.onAttachedToWindow();
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            CardAdapter cardAdapter = this.mAdapter;
            if (cardAdapter instanceof CardPagerAdapter) {
                viewPager.setAdapter((CardPagerAdapter) cardAdapter);
                ViewPager viewPager2 = this.mViewPager;
                AnonymousClass3 anonymousClass3 = this.mViewPagerOnPageChangeListener;
                if (viewPager2.mOnPageChangeListeners == null) {
                    viewPager2.mOnPageChangeListeners = new ArrayList();
                }
                viewPager2.mOnPageChangeListeners.add(anonymousClass3);
                pagerDots = this.mPagerDots;
                if (pagerDots != null) {
                    pagerDots.setNumPages(this.mAdapter.getCount(), isLayoutRtl());
                }
                if (TextUtils.equals(this.mAdapter.getUiSurface(), BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                    try {
                        Handler handler = this.mBgHandler;
                        if (handler == null) {
                            throw new IllegalStateException("Must set background handler to avoid making binder calls on main thread");
                        }
                        boolean z = true;
                        BcSmartspaceView$$ExternalSyntheticLambda2 bcSmartspaceView$$ExternalSyntheticLambda2 = new BcSmartspaceView$$ExternalSyntheticLambda2(1);
                        bcSmartspaceView$$ExternalSyntheticLambda2.f$0 = this;
                        VarHandle.storeStoreFence();
                        handler.post(bcSmartspaceView$$ExternalSyntheticLambda2);
                        Context context = getContext();
                        if (Settings.Secure.getIntForUser(context.getContentResolver(), "doze_always_on", 0, context.getUserId()) != 1) {
                            z = false;
                        }
                        this.mIsAodEnabled = z;
                    } catch (Exception e) {
                        Log.w("BcSmartspaceView", "Unable to register Doze Always on content observer.", e);
                    }
                }
                bcSmartspaceDataPlugin = this.mDataProvider;
                if (bcSmartspaceDataPlugin != null) {
                    registerDataProvider(bcSmartspaceDataPlugin);
                    return;
                }
                return;
            }
        }
        ViewPager2 viewPager22 = this.mViewPager2;
        if (viewPager22 != null) {
            CardAdapter cardAdapter2 = this.mAdapter;
            if (cardAdapter2 instanceof CardRecyclerViewAdapter) {
                viewPager22.setAdapter((CardRecyclerViewAdapter) cardAdapter2);
                this.mViewPager2.mExternalPageChangeCallbacks.mCallbacks.add(this.mViewPager2OnPageChangeCallback);
                pagerDots = this.mPagerDots;
                if (pagerDots != null) {
                }
                if (TextUtils.equals(this.mAdapter.getUiSurface(), BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                }
                bcSmartspaceDataPlugin = this.mDataProvider;
                if (bcSmartspaceDataPlugin != null) {
                }
            }
        }
        Log.w("BcSmartspaceView", "Unable to attach the view pager adapter");
        pagerDots = this.mPagerDots;
        if (pagerDots != null) {
        }
        if (TextUtils.equals(this.mAdapter.getUiSurface(), BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
        }
        bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
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
        BcSmartspaceView$$ExternalSyntheticLambda2 bcSmartspaceView$$ExternalSyntheticLambda2 = new BcSmartspaceView$$ExternalSyntheticLambda2(0);
        bcSmartspaceView$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        handler.post(bcSmartspaceView$$ExternalSyntheticLambda2);
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            AnonymousClass3 anonymousClass3 = this.mViewPagerOnPageChangeListener;
            List list = viewPager.mOnPageChangeListeners;
            if (list != null) {
                list.remove(anonymousClass3);
            }
        } else {
            ViewPager2 viewPager2 = this.mViewPager2;
            if (viewPager2 != null) {
                viewPager2.mExternalPageChangeCallbacks.mCallbacks.remove(this.mViewPager2OnPageChangeCallback);
            }
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.unregisterListener(this);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R.id.smartspace_card_pager);
        if (findViewById instanceof ViewPager) {
            this.mViewPager = (ViewPager) findViewById;
            BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin = this.mConfigProvider;
            CardPagerAdapter cardPagerAdapter = new CardPagerAdapter();
            cardPagerAdapter.root = this;
            cardPagerAdapter.viewHolders = new SparseArray();
            cardPagerAdapter.enableCardRecycling = new LazyServerFlagLoader("enable_card_recycling");
            cardPagerAdapter.enableReducedCardRecycling = new LazyServerFlagLoader("enable_reduced_card_recycling");
            cardPagerAdapter.recycledCards = new SparseArray();
            cardPagerAdapter.recycledLegacyCards = new SparseArray();
            cardPagerAdapter.recycledRemoteViewsCards = new SparseArray();
            cardPagerAdapter.smartspaceTargets = new ArrayList();
            cardPagerAdapter._aodTargets = new ArrayList();
            cardPagerAdapter._lockscreenTargets = new ArrayList();
            cardPagerAdapter.mediaTargets = new ArrayList();
            cardPagerAdapter.dozeColor = -1;
            int attrColor = GraphicsUtils.getAttrColor(android.R.attr.textColorPrimary, getContext());
            cardPagerAdapter.primaryTextColor = attrColor;
            cardPagerAdapter.currentTextColor = attrColor;
            cardPagerAdapter.configProvider = bcSmartspaceConfigPlugin;
            cardPagerAdapter.transitioningTo = CardPagerAdapter.TransitionType.NOT_IN_TRANSITION;
            VarHandle.storeStoreFence();
            this.mAdapter = cardPagerAdapter;
        } else {
            if (!(findViewById instanceof ViewPager2)) {
                throw new IllegalStateException("smartspace_card_pager is an invalid view type");
            }
            ViewPager2 viewPager2 = (ViewPager2) findViewById;
            this.mViewPager2 = viewPager2;
            AnonymousClass4 anonymousClass4 = new AnonymousClass4();
            if (!viewPager2.mSavedItemAnimatorPresent) {
                DefaultItemAnimator defaultItemAnimator = viewPager2.mRecyclerView.mItemAnimator;
                viewPager2.mSavedItemAnimatorPresent = true;
            }
            viewPager2.mRecyclerView.setItemAnimator(null);
            PageTransformerAdapter pageTransformerAdapter = viewPager2.mPageTransformerAdapter;
            if (anonymousClass4 != pageTransformerAdapter.mPageTransformer) {
                pageTransformerAdapter.mPageTransformer = anonymousClass4;
                ScrollEventAdapter scrollEventAdapter = viewPager2.mScrollEventAdapter;
                scrollEventAdapter.updateScrollEventValues();
                ScrollEventAdapter.ScrollEventValues scrollEventValues = scrollEventAdapter.mScrollValues;
                double d = scrollEventValues.mPosition + scrollEventValues.mOffset;
                int i = (int) d;
                float f = (float) (d - i);
                viewPager2.mPageTransformerAdapter.onPageScrolled(i, f, Math.round(viewPager2.getPageSize() * f));
            }
            this.mAdapter = new CardRecyclerViewAdapter(this, this.mConfigProvider);
            if (this.mViewPager2 != null) {
                CardRecyclerViewAdapter cardRecyclerViewAdapter = new CardRecyclerViewAdapter(this, this.mConfigProvider);
                cardRecyclerViewAdapter.setTargets(Collections.EMPTY_LIST, null);
                if (cardRecyclerViewAdapter.smartspaceTargets.size() > 0) {
                    RecyclerView recyclerView = (RecyclerView) this.mViewPager2.getChildAt(0);
                    RecyclerView.RecycledViewPool recycledViewPool = this.mRecycledViewPool;
                    RecyclerView.Recycler recycler = recyclerView.mRecycler;
                    RecyclerView recyclerView2 = recycler.this$0;
                    recycler.poolingContainerDetach(recyclerView2.mAdapter, false);
                    RecyclerView.RecycledViewPool recycledViewPool2 = recycler.mRecyclerPool;
                    if (recycledViewPool2 != null) {
                        recycledViewPool2.mAttachCountForClearing--;
                    }
                    recycler.mRecyclerPool = recycledViewPool;
                    if (recycledViewPool != null && recyclerView2.mAdapter != null) {
                        recycledViewPool.mAttachCountForClearing++;
                    }
                    recycler.maybeSendPoolingContainerAttach();
                    this.mPreInflatedViewHolder = cardRecyclerViewAdapter.createViewHolder(cardRecyclerViewAdapter.getItemViewType(0), recyclerView);
                }
            }
        }
        View findViewById2 = findViewById(R.id.smartspace_page_indicator);
        if (findViewById2 instanceof PagerDots) {
            this.mPagerDots = (PagerDots) findViewById2;
        }
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setPaddingRelative(((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start), this.mPagerDots.getPaddingTop(), this.mPagerDots.getPaddingEnd(), this.mPagerDots.getPaddingBottom());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 == null) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0 = new BcSmartspaceView$$ExternalSyntheticLambda0(1);
        bcSmartspaceView$$ExternalSyntheticLambda0.f$0 = viewPager2;
        VarHandle.storeStoreFence();
        handleTouchOverride(motionEvent, bcSmartspaceView$$ExternalSyntheticLambda0);
        return super.onInterceptTouchEvent(motionEvent) || this.mHasPerformedLongPress;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        RecyclerView.ViewHolder viewHolder = this.mPreInflatedViewHolder;
        if (viewHolder != null) {
            this.mRecycledViewPool.putRecycledView(viewHolder);
            this.mPreInflatedViewHolder = null;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.enhanced_smartspace_height);
        if (size <= 0 || size >= dimensionPixelSize) {
            super.onMeasure(i, i2);
            setScaleX(1.0f);
            setScaleY(1.0f);
            resetPivot();
            return;
        }
        float f = size;
        float f2 = dimensionPixelSize;
        float f3 = f / f2;
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.round(View.MeasureSpec.getSize(i) / f3), 1073741824), View.MeasureSpec.makeMeasureSpec(dimensionPixelSize, 1073741824));
        setScaleX(f3);
        setScaleY(f3);
        setPivotX(0.0f);
        setPivotY(f2 / 2.0f);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        if (DEBUG) {
            Log.d("BcSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", onTargetsAvailable called. Callers = " + Debug.getCallers(5));
            StringBuilder sb = new StringBuilder("    targets.size() = ");
            sb.append(list.size());
            Log.d("BcSmartspaceView", sb.toString());
            Log.d("BcSmartspaceView", "    targets = " + list.toString());
        }
        if (this.mViewPager != null && this.mScrollState != 0 && this.mAdapter.getCount() > 1 && this.mViewPager != null) {
            this.mPendingTargets = list;
            return;
        }
        this.mPendingTargets = null;
        boolean isLayoutRtl = isLayoutRtl();
        int selectedPage = getSelectedPage();
        if (isLayoutRtl && (this.mAdapter instanceof CardPagerAdapter)) {
            ArrayList arrayList = new ArrayList(list);
            Collections.reverse(arrayList);
            list = arrayList;
        }
        View templateCardAtPosition = this.mAdapter.getTemplateCardAtPosition(selectedPage);
        BcSmartspaceCard legacyCardAtPosition = this.mAdapter.getLegacyCardAtPosition(selectedPage);
        BcSmartspaceRemoteViewsCard remoteViewsCardAtPosition = this.mAdapter.getRemoteViewsCardAtPosition(selectedPage);
        if (templateCardAtPosition == null) {
            templateCardAtPosition = legacyCardAtPosition != null ? legacyCardAtPosition : remoteViewsCardAtPosition;
        }
        int count = this.mAdapter.getCount();
        CardAdapter cardAdapter = this.mAdapter;
        if (!(cardAdapter instanceof CardRecyclerViewAdapter)) {
            cardAdapter.setTargets(list);
            setTargets(isLayoutRtl, selectedPage, templateCardAtPosition, count);
            return;
        }
        BcSmartspaceView$$ExternalSyntheticLambda1 bcSmartspaceView$$ExternalSyntheticLambda1 = new BcSmartspaceView$$ExternalSyntheticLambda1();
        bcSmartspaceView$$ExternalSyntheticLambda1.f$0 = this;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$1 = isLayoutRtl;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$2 = selectedPage;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$3 = templateCardAtPosition;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$4 = count;
        VarHandle.storeStoreFence();
        ((CardRecyclerViewAdapter) cardAdapter).setTargets(list, bcSmartspaceView$$ExternalSyntheticLambda1);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 == null) {
            return super.onTouchEvent(motionEvent);
        }
        Objects.requireNonNull(viewPager2);
        BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0 = new BcSmartspaceView$$ExternalSyntheticLambda0(0);
        bcSmartspaceView$$ExternalSyntheticLambda0.f$0 = viewPager2;
        VarHandle.storeStoreFence();
        return handleTouchOverride(motionEvent, bcSmartspaceView$$ExternalSyntheticLambda0);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.getEventNotifier().notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(z ? 6 : 7).build());
        }
        if (this.mViewPager == null || this.mScrollState == 0) {
            return;
        }
        this.mScrollState = 0;
        List list = this.mPendingTargets;
        if (list != null) {
            onSmartspaceTargetsUpdated(list);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.mConfigProvider = bcSmartspaceConfigPlugin;
        this.mAdapter.setConfigProvider(bcSmartspaceConfigPlugin);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.mDataProvider;
        if (bcSmartspaceDataPlugin2 != null) {
            bcSmartspaceDataPlugin2.unregisterListener(this);
        }
        this.mDataProvider = bcSmartspaceDataPlugin;
        bcSmartspaceDataPlugin.registerListener(this);
        this.mAdapter.setDataProvider(this.mDataProvider);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            cancelScheduledLongPress();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setBgHandler(Handler handler) {
        this.mBgHandler = handler;
        this.mAdapter.setBgHandler(handler);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozeAmount(float f) {
        float f2;
        float f3;
        List smartspaceTargets = this.mAdapter.getSmartspaceTargets();
        this.mAdapter.setDozeAmount(f);
        if (!this.mAdapter.getSmartspaceTargets().isEmpty()) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this, 0);
        }
        if (this.mAdapter.getHasAodLockscreenTransition()) {
            float f4 = this.mPreviousDozeAmount;
            if (f == f4) {
                f2 = getAlpha();
            } else {
                float f5 = f4 > f ? 1.0f - f : f;
                float f6 = 0.36f;
                if (f5 < 0.36f) {
                    f3 = 0.36f - f5;
                } else {
                    f3 = f5 - 0.36f;
                    f6 = 0.64f;
                }
                f2 = f3 / f6;
            }
        } else {
            f2 = 1.0f;
        }
        setAlpha(f2);
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setNumPages(this.mAdapter.getCount(), isLayoutRtl());
            this.mPagerDots.setAlpha(f2);
            if (this.mPagerDots.getVisibility() != 8) {
                if (f == 1.0f) {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mPagerDots, 4);
                } else {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mPagerDots, 0);
                }
            }
        }
        this.mPreviousDozeAmount = f;
        if (this.mAdapter.getHasDifferentTargets() && this.mAdapter.getSmartspaceTargets() != smartspaceTargets && this.mAdapter.getCount() > 0) {
            if (this.mAdapter instanceof CardRecyclerViewAdapter) {
                setSelectedPage(0);
            } else {
                setSelectedPage(isLayoutRtl() ? this.mAdapter.getCount() - 1 : 0);
            }
        }
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mAdapter.getUiSurface(), this.mAdapter.getDozeAmount());
        if (loggingDisplaySurface == -1) {
            return;
        }
        if (loggingDisplaySurface != 3 || this.mIsAodEnabled) {
            if (DEBUG) {
                Log.d("BcSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", setDozeAmount: Logging SMARTSPACE_CARD_SEEN, currentSurface = " + loggingDisplaySurface);
            }
            SmartspaceTarget targetAtPosition = this.mAdapter.getTargetAtPosition(this.mCardPosition);
            if (targetAtPosition == null) {
                Log.w("BcSmartspaceView", "Current card is not present in the Adapter; cannot log.");
            } else {
                logSmartspaceEvent(targetAtPosition, this.mCardPosition, BcSmartspaceEvent.SMARTSPACE_CARD_SEEN);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozing(boolean z) {
        if (!z && this.mSplitShadeEnabled && this.mAdapter.getHasAodLockscreenTransition() && this.mAdapter.getLockscreenTargets().isEmpty()) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this, 8);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setFalsingManager(FalsingManager falsingManager) {
        BcSmartSpaceUtil.sFalsingManager = falsingManager;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setHorizontalPaddings(int i) {
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setPaddingRelative(i, pagerDots.getPaddingTop(), i, this.mPagerDots.getPaddingBottom());
        }
        this.mAdapter.setNonRemoteViewsHorizontalPadding(Integer.valueOf(i));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setKeyguardBypassEnabled(boolean z) {
        this.mAdapter.setKeyguardBypassEnabled(z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        CardAdapter cardAdapter = this.mAdapter;
        if (!(cardAdapter instanceof CardRecyclerViewAdapter)) {
            cardAdapter.setMediaTarget(smartspaceTarget);
            return;
        }
        CardRecyclerViewAdapter cardRecyclerViewAdapter = (CardRecyclerViewAdapter) cardAdapter;
        cardRecyclerViewAdapter.mediaTargets.clear();
        if (smartspaceTarget != null) {
            cardRecyclerViewAdapter.mediaTargets.add(smartspaceTarget);
        }
        cardRecyclerViewAdapter.updateTargetVisibility(null, true);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.setOnLongClickListener(onLongClickListener);
            return;
        }
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 != null) {
            viewPager2.setOnLongClickListener(onLongClickListener);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setPrimaryTextColor(int i) {
        this.mAdapter.setPrimaryTextColor(i);
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.primaryColor = i;
            pagerDots.paint.setColor(i);
            pagerDots.invalidate();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setScreenOn(boolean z) {
        if (this.mViewPager != null && this.mScrollState != 0) {
            this.mScrollState = 0;
            List list = this.mPendingTargets;
            if (list != null) {
                onSmartspaceTargetsUpdated(list);
            }
        }
        this.mAdapter.setScreenOn(z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setSelectedDot(float f, int i) {
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            if (i < 0) {
                pagerDots.getClass();
                return;
            }
            if (i >= pagerDots.numPages) {
                return;
            }
            pagerDots.currentPositionIndex = i;
            pagerDots.currentPositionOffset = f;
            pagerDots.invalidate();
            if (f >= 0.5d) {
                i++;
            }
            pagerDots.updateCurrentPageIndex(i);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setSelectedPage(int i) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.setCurrentItem(i, false);
        } else {
            ViewPager2 viewPager2 = this.mViewPager2;
            if (viewPager2 != null) {
                BcSmartspaceView$$ExternalSyntheticLambda6 bcSmartspaceView$$ExternalSyntheticLambda6 = new BcSmartspaceView$$ExternalSyntheticLambda6();
                bcSmartspaceView$$ExternalSyntheticLambda6.f$0 = this;
                bcSmartspaceView$$ExternalSyntheticLambda6.f$1 = i;
                VarHandle.storeStoreFence();
                viewPager2.post(bcSmartspaceView$$ExternalSyntheticLambda6);
            }
        }
        setSelectedDot(0.0f, i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setSplitShadeEnabled(boolean z) {
        this.mSplitShadeEnabled = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void setTargets(boolean z, int i, View view, int i2) {
        int count = this.mAdapter.getCount();
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setNumPages(count, z);
        }
        if (z && (this.mAdapter instanceof CardPagerAdapter)) {
            setSelectedPage(Math.max(0, Math.min(count - 1, count - (i2 - i))));
        } else if (this.mAdapter instanceof CardRecyclerViewAdapter) {
            setSelectedPage(Math.max(0, Math.min(i, count - 1)));
        }
        for (int i3 = 0; i3 < count; i3++) {
            SmartspaceTarget targetAtPosition = this.mAdapter.getTargetAtPosition(i3);
            if (!this.mLastReceivedTargets.contains(targetAtPosition.getSmartspaceTargetId())) {
                logSmartspaceEvent(targetAtPosition, i3, BcSmartspaceEvent.SMARTSPACE_CARD_RECEIVED);
                SmartspaceTargetEvent.Builder builder = new SmartspaceTargetEvent.Builder(8);
                builder.setSmartspaceTarget(targetAtPosition);
                SmartspaceAction baseAction = targetAtPosition.getBaseAction();
                if (baseAction != null) {
                    builder.setSmartspaceActionId(baseAction.getId());
                }
                this.mDataProvider.getEventNotifier().notifySmartspaceEvent(builder.build());
            }
        }
        this.mLastReceivedTargets.clear();
        this.mLastReceivedTargets.addAll((Collection) this.mAdapter.getSmartspaceTargets().stream().map(new BcSmartspaceView$$ExternalSyntheticLambda7()).collect(Collectors.toList()));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.mAdapter.setTimeChangedDelegate(timeChangedDelegate);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setUiSurface(String str) {
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Must call before attaching view to window.");
        }
        if (str == BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN) {
            getContext().getTheme().applyStyle(R.style.LauncherSmartspaceView, true);
        }
        this.mAdapter.setUiSurface(str);
    }
}
