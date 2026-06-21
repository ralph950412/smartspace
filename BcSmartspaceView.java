package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
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
import androidx.viewpager2.widget.PageTransformerAdapter;
import androidx.viewpager2.widget.ScrollEventAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.customization.clocks.R$dimen;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.CardRecyclerViewAdapter;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import com.google.android.systemui.smartspace.logging.BcSmartspaceSubcardLoggingInfo;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.lang.invoke.VarHandle;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import okio.Buffer$$ExternalSyntheticBUOutline0;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public class BcSmartspaceView extends FrameLayout implements BcSmartspaceDataPlugin.SmartspaceTargetListener, BcSmartspaceDataPlugin.SmartspaceView {
    public static final boolean DEBUG = Log.isLoggable("BcSmartspaceView", 3);
    public CardRecyclerViewAdapter mAdapter;
    public final AnonymousClass1 mAodObserver;
    public final AnonymousClass1 mBackgroundToggleObserver;
    public Handler mBgHandler;
    public int mCardPosition;
    public BcSmartspaceConfigPlugin mConfigProvider;
    public BcSmartspaceDataPlugin mDataProvider;
    public boolean mHasPerformedLongPress;
    public boolean mHasPostedLongPress;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mIsAodEnabled;
    public boolean mIsBackgroundEnabled;
    public final ArraySet mLastReceivedTargets;
    public final BcSmartspaceView$$ExternalSyntheticLambda2 mLongPressCallback;
    public PageIndicator mPageIndicator;
    public PagerDots mPagerDots;
    public RecyclerView.ViewHolder mPreInflatedViewHolder;
    public float mPreviousDozeAmount;
    public final RecyclerView.RecycledViewPool mRecycledViewPool;
    public int mScrollState;
    public boolean mSplitShadeEnabled;
    public Integer mSwipedCardPosition;
    public final int mTouchSlop;
    public ViewPager2 mViewPager2;
    public final AnonymousClass3 mViewPager2OnPageChangeCallback;

    /* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceView$3, reason: invalid class name */
    public final class AnonymousClass3 extends ViewPager2.OnPageChangeCallback {
        public /* synthetic */ BcSmartspaceView this$0;

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrollStateChanged(int i) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            bcSmartspaceView.mScrollState = i;
            if (i == 1) {
                bcSmartspaceView.mSwipedCardPosition = Integer.valueOf(bcSmartspaceView.mViewPager2.mCurrentItem);
            }
            if (i == 0) {
                Integer num = bcSmartspaceView.mSwipedCardPosition;
                if (num != null && num.intValue() != bcSmartspaceView.mViewPager2.mCurrentItem) {
                    CardRecyclerViewAdapter.ViewHolder viewHolder = (CardRecyclerViewAdapter.ViewHolder) bcSmartspaceView.mAdapter.viewHolders.get(bcSmartspaceView.mSwipedCardPosition.intValue());
                    SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
                    if (smartspaceCard != null) {
                        BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_SWIPE, smartspaceCard.getLoggingInfo());
                    }
                }
                bcSmartspaceView.mSwipedCardPosition = null;
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageScrolled(int i, float f, int i2) {
            BcSmartspaceView.m1610$$Nest$msetSelectedDot(this.this$0, i, f);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public final void onPageSelected(int i) {
            BcSmartspaceView bcSmartspaceView = this.this$0;
            BcSmartspaceView.m1610$$Nest$msetSelectedDot(bcSmartspaceView, i, 0.0f);
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
    }

    /* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartspaceView$4, reason: invalid class name */
    public final class AnonymousClass4 {
    }

    /* renamed from: -$$Nest$msetSelectedDot, reason: not valid java name */
    public static void m1610$$Nest$msetSelectedDot(BcSmartspaceView bcSmartspaceView, int i, float f) {
        PagerDots pagerDots = bcSmartspaceView.mPagerDots;
        if (pagerDots == null || i < 0 || i >= pagerDots.numPages) {
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

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.systemui.smartspace.BcSmartspaceView$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.systemui.smartspace.BcSmartspaceView$1] */
    public BcSmartspaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mConfigProvider = new DefaultBcSmartspaceConfigProvider();
        this.mRecycledViewPool = new RecyclerView.RecycledViewPool();
        this.mPreInflatedViewHolder = null;
        this.mLastReceivedTargets = new ArraySet();
        final int i = 0;
        this.mIsAodEnabled = false;
        this.mIsBackgroundEnabled = false;
        this.mCardPosition = 0;
        this.mPreviousDozeAmount = 0.0f;
        this.mScrollState = 0;
        this.mSplitShadeEnabled = false;
        this.mAodObserver = new ContentObserver(this, new Handler()) { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.1
            public final /* synthetic */ BcSmartspaceView this$0;

            {
                this.this$0 = this;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                switch (i) {
                    case 0:
                        BcSmartspaceView bcSmartspaceView = this.this$0;
                        boolean z2 = BcSmartspaceView.DEBUG;
                        Context context2 = bcSmartspaceView.getContext();
                        bcSmartspaceView.mIsAodEnabled = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
                        break;
                    default:
                        BcSmartspaceView bcSmartspaceView2 = this.this$0;
                        boolean z3 = BcSmartspaceView.DEBUG;
                        bcSmartspaceView2.onBackgroundToggled();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mBackgroundToggleObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.1
            public final /* synthetic */ BcSmartspaceView this$0;

            {
                this.this$0 = this;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                switch (i2) {
                    case 0:
                        BcSmartspaceView bcSmartspaceView = this.this$0;
                        boolean z2 = BcSmartspaceView.DEBUG;
                        Context context2 = bcSmartspaceView.getContext();
                        bcSmartspaceView.mIsAodEnabled = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
                        break;
                    default:
                        BcSmartspaceView bcSmartspaceView2 = this.this$0;
                        boolean z3 = BcSmartspaceView.DEBUG;
                        bcSmartspaceView2.onBackgroundToggled();
                        break;
                }
            }
        };
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        anonymousClass3.this$0 = this;
        VarHandle.storeStoreFence();
        this.mViewPager2OnPageChangeCallback = anonymousClass3;
        BcSmartspaceView$$ExternalSyntheticLambda2 bcSmartspaceView$$ExternalSyntheticLambda2 = new BcSmartspaceView$$ExternalSyntheticLambda2(1);
        bcSmartspaceView$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        this.mLongPressCallback = bcSmartspaceView$$ExternalSyntheticLambda2;
        getContext().getTheme().applyStyle(R.style.DefaultSmartspaceView, false);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final void cancelScheduledLongPress() {
        if (this.mHasPostedLongPress) {
            this.mHasPostedLongPress = false;
            this.mViewPager2.removeCallbacks(this.mLongPressCallback);
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final int getCurrentCardTopPadding() {
        BcSmartspaceCard legacyCardAtPosition = this.mAdapter.getLegacyCardAtPosition(this.mViewPager2.mCurrentItem);
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        if (legacyCardAtPosition != null) {
            return cardRecyclerViewAdapter.getLegacyCardAtPosition(this.mViewPager2.mCurrentItem).getPaddingTop();
        }
        BaseTemplateCard templateCardAtPosition = cardRecyclerViewAdapter.getTemplateCardAtPosition(this.mViewPager2.mCurrentItem);
        CardRecyclerViewAdapter cardRecyclerViewAdapter2 = this.mAdapter;
        if (templateCardAtPosition != null) {
            return cardRecyclerViewAdapter2.getTemplateCardAtPosition(this.mViewPager2.mCurrentItem).getPaddingTop();
        }
        CardRecyclerViewAdapter.ViewHolder viewHolder = (CardRecyclerViewAdapter.ViewHolder) cardRecyclerViewAdapter2.viewHolders.get(this.mViewPager2.mCurrentItem);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        if ((smartspaceCard instanceof BcSmartspaceRemoteViewsCard ? (BcSmartspaceRemoteViewsCard) smartspaceCard : null) == null) {
            return 0;
        }
        CardRecyclerViewAdapter.ViewHolder viewHolder2 = (CardRecyclerViewAdapter.ViewHolder) this.mAdapter.viewHolders.get(this.mViewPager2.mCurrentItem);
        Object obj = viewHolder2 != null ? viewHolder2.card : null;
        return (obj instanceof BcSmartspaceRemoteViewsCard ? (BcSmartspaceRemoteViewsCard) obj : null).getPaddingTop();
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final int getSelectedPage() {
        return this.mViewPager2.mCurrentItem;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000e, code lost:
    
        if (r0 != 3) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleTouchOverride(MotionEvent motionEvent, BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0) {
        boolean onTouchEvent;
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (Math.hypot(motionEvent.getX() - this.mInitialTouchX, motionEvent.getY() - this.mInitialTouchY) > this.mTouchSlop) {
                        cancelScheduledLongPress();
                    }
                }
            }
            cancelScheduledLongPress();
        } else {
            this.mInitialTouchX = motionEvent.getX();
            this.mInitialTouchY = motionEvent.getY();
            this.mHasPerformedLongPress = false;
            if (this.mViewPager2.isLongClickable()) {
                cancelScheduledLongPress();
                this.mHasPostedLongPress = true;
                this.mViewPager2.postDelayed(this.mLongPressCallback, ViewConfiguration.getLongPressTimeout());
            }
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
        if (!onTouchEvent) {
            return false;
        }
        cancelScheduledLongPress();
        return true;
    }

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
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        int loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(cardRecyclerViewAdapter.uiSurface, cardRecyclerViewAdapter._dozeAmount);
        int size = this.mAdapter.smartspaceTargets.size();
        getContext().getPackageManager();
        BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo = containsValidTemplateType ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo();
        bcSmartspaceCardLoggingInfo.mInstanceId = create;
        bcSmartspaceCardLoggingInfo.mDisplaySurface = loggingDisplaySurface;
        bcSmartspaceCardLoggingInfo.mRank = i;
        bcSmartspaceCardLoggingInfo.mCardinality = size;
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

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mViewPager2.setAdapter(this.mAdapter);
        this.mViewPager2.mExternalPageChangeCallbacks.mCallbacks.add(this.mViewPager2OnPageChangeCallback);
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setNumPages(this.mAdapter.smartspaceTargets.size(), isLayoutRtl());
        }
        if (this.mBgHandler == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Must set background handler to avoid making binder calls on main thread");
            return;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        if (TextUtils.equals(this.mAdapter.uiSurface, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
            try {
                Handler handler = this.mBgHandler;
                BcSmartspaceView$$ExternalSyntheticLambda3 bcSmartspaceView$$ExternalSyntheticLambda3 = new BcSmartspaceView$$ExternalSyntheticLambda3(0);
                bcSmartspaceView$$ExternalSyntheticLambda3.f$0 = this;
                bcSmartspaceView$$ExternalSyntheticLambda3.f$1 = contentResolver;
                VarHandle.storeStoreFence();
                handler.post(bcSmartspaceView$$ExternalSyntheticLambda3);
                Context context = getContext();
                this.mIsAodEnabled = Settings.Secure.getIntForUser(context.getContentResolver(), "doze_always_on", 0, context.getUserId()) == 1;
            } catch (Exception e) {
                Log.w("BcSmartspaceView", "Unable to register Doze Always on content observer.", e);
            }
        }
        try {
            Handler handler2 = this.mBgHandler;
            BcSmartspaceView$$ExternalSyntheticLambda3 bcSmartspaceView$$ExternalSyntheticLambda32 = new BcSmartspaceView$$ExternalSyntheticLambda3(1);
            bcSmartspaceView$$ExternalSyntheticLambda32.f$0 = this;
            bcSmartspaceView$$ExternalSyntheticLambda32.f$1 = contentResolver;
            VarHandle.storeStoreFence();
            handler2.post(bcSmartspaceView$$ExternalSyntheticLambda32);
        } catch (Exception e2) {
            Log.w("BcSmartspaceView", "Unable to register Smartspace Background Settings observer.", e2);
        }
        onBackgroundToggled();
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            registerDataProvider(bcSmartspaceDataPlugin);
        }
    }

    public final void onBackgroundToggled() {
        boolean z = Settings.Secure.getIntForUser(getContext().getContentResolver(), "smartspace_settings_background", 0, getContext().getUserId()) == 1;
        if (this.mIsBackgroundEnabled == z) {
            return;
        }
        this.mIsBackgroundEnabled = z;
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        cardRecyclerViewAdapter._isBackgroundEnabled = z;
        cardRecyclerViewAdapter.refreshCardBackground();
        cardRecyclerViewAdapter.refreshCardPaddings();
        cardRecyclerViewAdapter.updateCurrentTextColor();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mBgHandler;
        if (handler == null) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Must set background handler to avoid making binder calls on main thread");
            return;
        }
        BcSmartspaceView$$ExternalSyntheticLambda2 bcSmartspaceView$$ExternalSyntheticLambda2 = new BcSmartspaceView$$ExternalSyntheticLambda2(0);
        bcSmartspaceView$$ExternalSyntheticLambda2.f$0 = this;
        VarHandle.storeStoreFence();
        handler.post(bcSmartspaceView$$ExternalSyntheticLambda2);
        ViewPager2 viewPager2 = this.mViewPager2;
        viewPager2.mExternalPageChangeCallbacks.mCallbacks.remove(this.mViewPager2OnPageChangeCallback);
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.unregisterListener(this);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.smartspace_card_pager);
        this.mViewPager2 = viewPager2;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        if (!viewPager2.mSavedItemAnimatorPresent) {
            viewPager2.mSavedItemAnimator = viewPager2.mRecyclerView.mItemAnimator;
            viewPager2.mSavedItemAnimatorPresent = true;
        }
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
        DefaultItemAnimator defaultItemAnimator = recyclerViewImpl.mItemAnimator;
        if (defaultItemAnimator != null) {
            defaultItemAnimator.endAnimations();
            recyclerViewImpl.mItemAnimator.mListener = null;
        }
        recyclerViewImpl.mItemAnimator = null;
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
        CardRecyclerViewAdapter cardRecyclerViewAdapter = new CardRecyclerViewAdapter(this, this.mConfigProvider);
        cardRecyclerViewAdapter.uiSurface = BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN;
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
        View findViewById = findViewById(R.id.smartspace_page_indicator);
        if (findViewById instanceof PagerDots) {
            this.mPagerDots = (PagerDots) findViewById;
        }
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setPaddingRelative(((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start), this.mPagerDots.getPaddingTop(), this.mPagerDots.getPaddingEnd(), this.mPagerDots.getPaddingBottom());
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        Objects.requireNonNull(viewPager2);
        BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0 = new BcSmartspaceView$$ExternalSyntheticLambda0(1);
        bcSmartspaceView$$ExternalSyntheticLambda0.f$0 = viewPager2;
        VarHandle.storeStoreFence();
        handleTouchOverride(motionEvent, bcSmartspaceView$$ExternalSyntheticLambda0);
        return super.onInterceptTouchEvent(motionEvent) || this.mHasPerformedLongPress;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        RecyclerView.ViewHolder viewHolder = this.mPreInflatedViewHolder;
        if (viewHolder != null) {
            this.mRecycledViewPool.putRecycledView(viewHolder);
            this.mPreInflatedViewHolder = null;
        }
        super.onLayout(z, i, i2, i3, i4);
    }

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

    public void onSmartspaceTargetsUpdated(List list, Runnable runnable) {
        if (DEBUG) {
            Log.d("BcSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", onTargetsAvailable called. Callers = " + Debug.getCallers(5));
            StringBuilder sb = new StringBuilder("    targets.size() = ");
            sb.append(list.size());
            Log.d("BcSmartspaceView", sb.toString());
            Log.d("BcSmartspaceView", "    targets = " + list.toString());
        }
        boolean isLayoutRtl = isLayoutRtl();
        int i = this.mViewPager2.mCurrentItem;
        View templateCardAtPosition = this.mAdapter.getTemplateCardAtPosition(i);
        View legacyCardAtPosition = this.mAdapter.getLegacyCardAtPosition(i);
        CardRecyclerViewAdapter.ViewHolder viewHolder = (CardRecyclerViewAdapter.ViewHolder) this.mAdapter.viewHolders.get(i);
        SmartspaceCard smartspaceCard = viewHolder != null ? viewHolder.card : null;
        View view = smartspaceCard instanceof BcSmartspaceRemoteViewsCard ? (BcSmartspaceRemoteViewsCard) smartspaceCard : null;
        if (templateCardAtPosition == null) {
            templateCardAtPosition = legacyCardAtPosition != null ? legacyCardAtPosition : view;
        }
        this.mAdapter.smartspaceTargets.size();
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        BcSmartspaceView$$ExternalSyntheticLambda1 bcSmartspaceView$$ExternalSyntheticLambda1 = new BcSmartspaceView$$ExternalSyntheticLambda1();
        bcSmartspaceView$$ExternalSyntheticLambda1.f$0 = this;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$1 = isLayoutRtl;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$3 = templateCardAtPosition;
        bcSmartspaceView$$ExternalSyntheticLambda1.f$5 = runnable;
        VarHandle.storeStoreFence();
        cardRecyclerViewAdapter.setTargets(list, bcSmartspaceView$$ExternalSyntheticLambda1);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        Objects.requireNonNull(viewPager2);
        BcSmartspaceView$$ExternalSyntheticLambda0 bcSmartspaceView$$ExternalSyntheticLambda0 = new BcSmartspaceView$$ExternalSyntheticLambda0(0);
        bcSmartspaceView$$ExternalSyntheticLambda0.f$0 = viewPager2;
        VarHandle.storeStoreFence();
        return handleTouchOverride(motionEvent, bcSmartspaceView$$ExternalSyntheticLambda0);
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.getEventNotifier().notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(z ? 6 : 7).build());
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.mConfigProvider = bcSmartspaceConfigPlugin;
        this.mAdapter.configProvider = bcSmartspaceConfigPlugin;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.mDataProvider;
        if (bcSmartspaceDataPlugin2 != null) {
            bcSmartspaceDataPlugin2.unregisterListener(this);
        }
        this.mDataProvider = bcSmartspaceDataPlugin;
        bcSmartspaceDataPlugin.registerListener(this);
        this.mAdapter.dataProvider = this.mDataProvider;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            cancelScheduledLongPress();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setBgHandler(Handler handler) {
        this.mBgHandler = handler;
        this.mAdapter.bgHandler = handler;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0057  */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDozeAmount(float f) {
        boolean z;
        float f2;
        PagerDots pagerDots;
        CardRecyclerViewAdapter cardRecyclerViewAdapter;
        int loggingDisplaySurface;
        List list;
        CardRecyclerViewAdapter cardRecyclerViewAdapter2 = this.mAdapter;
        List list2 = cardRecyclerViewAdapter2.smartspaceTargets;
        cardRecyclerViewAdapter2._dozeAmount = f;
        float f3 = cardRecyclerViewAdapter2.previousDozeAmount;
        cardRecyclerViewAdapter2.transitioningTo = f3 > f ? CardRecyclerViewAdapter.TransitionType.TO_LOCKSCREEN : f3 < f ? CardRecyclerViewAdapter.TransitionType.TO_AOD : CardRecyclerViewAdapter.TransitionType.NOT_IN_TRANSITION;
        cardRecyclerViewAdapter2.previousDozeAmount = f;
        cardRecyclerViewAdapter2.updateTargetVisibility(null, false);
        Drawable drawable = cardRecyclerViewAdapter2.currentBackgroundDrawable;
        if (drawable != cardRecyclerViewAdapter2.backgroundOutlineDrawable) {
            float f4 = cardRecyclerViewAdapter2._dozeAmount;
            if (f4 == 1.0f || (f4 >= 0.36f && cardRecyclerViewAdapter2.transitioningTo == CardRecyclerViewAdapter.TransitionType.TO_AOD)) {
                z = true;
                boolean z2 = drawable == cardRecyclerViewAdapter2.backgroundDrawable && cardRecyclerViewAdapter2.needToSetToLockscreenTargets();
                if (!z) {
                    cardRecyclerViewAdapter2.currentBackgroundDrawable = cardRecyclerViewAdapter2.backgroundOutlineDrawable;
                    cardRecyclerViewAdapter2.refreshCardBackground();
                } else if (z2) {
                    cardRecyclerViewAdapter2.currentBackgroundDrawable = cardRecyclerViewAdapter2.backgroundDrawable;
                    cardRecyclerViewAdapter2.refreshCardBackground();
                }
                cardRecyclerViewAdapter2.updateCurrentTextColor();
                if (!this.mAdapter.smartspaceTargets.isEmpty()) {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this, 0);
                }
                if (this.mAdapter.hasAodLockscreenTransition) {
                    f2 = 1.0f;
                } else {
                    float f5 = this.mPreviousDozeAmount;
                    if (f == f5) {
                        f2 = getAlpha();
                    } else {
                        float f6 = f5 > f ? 1.0f - f : f;
                        f2 = f6 < 0.36f ? (0.36f - f6) / 0.36f : (f6 - 0.36f) / 0.64f;
                    }
                }
                setAlpha(f2);
                pagerDots = this.mPagerDots;
                if (pagerDots != null) {
                    pagerDots.setNumPages(this.mAdapter.smartspaceTargets.size(), isLayoutRtl());
                    this.mPagerDots.setAlpha(f2);
                    if (this.mPagerDots.getVisibility() != 8) {
                        PagerDots pagerDots2 = this.mPagerDots;
                        if (f == 1.0f) {
                            BcSmartspaceTemplateDataUtils.updateVisibility(pagerDots2, 4);
                        } else {
                            BcSmartspaceTemplateDataUtils.updateVisibility(pagerDots2, 0);
                        }
                    }
                }
                this.mPreviousDozeAmount = f;
                cardRecyclerViewAdapter = this.mAdapter;
                if (cardRecyclerViewAdapter.hasDifferentTargets && (list = cardRecyclerViewAdapter.smartspaceTargets) != list2 && list.size() > 0) {
                    this.mViewPager2.setCurrentItem(0, false);
                }
                CardRecyclerViewAdapter cardRecyclerViewAdapter3 = this.mAdapter;
                loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(cardRecyclerViewAdapter3.uiSurface, cardRecyclerViewAdapter3._dozeAmount);
                if (loggingDisplaySurface != -1) {
                    return;
                }
                if (loggingDisplaySurface != 3 || this.mIsAodEnabled) {
                    if (DEBUG) {
                        Log.d("BcSmartspaceView", "@" + Integer.toHexString(hashCode()) + ", setDozeAmount: Logging SMARTSPACE_CARD_SEEN, currentSurface = " + loggingDisplaySurface);
                    }
                    SmartspaceTarget targetAtPosition = this.mAdapter.getTargetAtPosition(this.mCardPosition);
                    if (targetAtPosition == null) {
                        Log.w("BcSmartspaceView", "Current card is not present in the Adapter; cannot log.");
                        return;
                    } else {
                        logSmartspaceEvent(targetAtPosition, this.mCardPosition, BcSmartspaceEvent.SMARTSPACE_CARD_SEEN);
                        return;
                    }
                }
                return;
            }
        }
        z = false;
        if (drawable == cardRecyclerViewAdapter2.backgroundDrawable) {
        }
        if (!z) {
        }
        cardRecyclerViewAdapter2.updateCurrentTextColor();
        if (!this.mAdapter.smartspaceTargets.isEmpty()) {
        }
        if (this.mAdapter.hasAodLockscreenTransition) {
        }
        setAlpha(f2);
        pagerDots = this.mPagerDots;
        if (pagerDots != null) {
        }
        this.mPreviousDozeAmount = f;
        cardRecyclerViewAdapter = this.mAdapter;
        if (cardRecyclerViewAdapter.hasDifferentTargets) {
            this.mViewPager2.setCurrentItem(0, false);
        }
        CardRecyclerViewAdapter cardRecyclerViewAdapter32 = this.mAdapter;
        loggingDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(cardRecyclerViewAdapter32.uiSurface, cardRecyclerViewAdapter32._dozeAmount);
        if (loggingDisplaySurface != -1) {
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozing(boolean z) {
        if (z || !this.mSplitShadeEnabled) {
            return;
        }
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        if (cardRecyclerViewAdapter.hasAodLockscreenTransition) {
            if (((cardRecyclerViewAdapter.mediaTargets.isEmpty() || !cardRecyclerViewAdapter.keyguardBypassEnabled) ? cardRecyclerViewAdapter._lockscreenTargets : cardRecyclerViewAdapter.mediaTargets).isEmpty()) {
                BcSmartspaceTemplateDataUtils.updateVisibility(this, 8);
            }
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setFalsingManager(FalsingManager falsingManager) {
        BcSmartSpaceUtil.sFalsingManager = falsingManager;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setHighContrastBackgroundColor(int i) {
        this.mAdapter.getClass();
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setHorizontalPaddings(int i) {
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.setPaddingRelative(i, pagerDots.getPaddingTop(), i, this.mPagerDots.getPaddingBottom());
        }
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        cardRecyclerViewAdapter.nonRemoteViewsHorizontalPadding = Integer.valueOf(i);
        if (cardRecyclerViewAdapter._isBackgroundEnabled) {
            return;
        }
        cardRecyclerViewAdapter.refreshCardPaddings();
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setKeyguardBypassEnabled(boolean z) {
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        cardRecyclerViewAdapter.keyguardBypassEnabled = z;
        cardRecyclerViewAdapter.updateTargetVisibility(null, false);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        cardRecyclerViewAdapter.mediaTargets.clear();
        if (smartspaceTarget != null) {
            cardRecyclerViewAdapter.mediaTargets.add(smartspaceTarget);
        }
        cardRecyclerViewAdapter.updateTargetVisibility(null, true);
    }

    @Override // android.view.View
    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mViewPager2.setOnLongClickListener(onLongClickListener);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setPrimaryTextColor(int i) {
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        cardRecyclerViewAdapter.primaryTextColor = i;
        cardRecyclerViewAdapter.updateCurrentTextColor();
        PagerDots pagerDots = this.mPagerDots;
        if (pagerDots != null) {
            pagerDots.primaryColor = i;
            pagerDots.paint.setColor(i);
            pagerDots.invalidate();
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setScreenOn(boolean z) {
        CardRecyclerViewAdapter cardRecyclerViewAdapter = this.mAdapter;
        int size = cardRecyclerViewAdapter.viewHolders.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = cardRecyclerViewAdapter.viewHolders;
            CardRecyclerViewAdapter.ViewHolder viewHolder = (CardRecyclerViewAdapter.ViewHolder) sparseArray.get(sparseArray.keyAt(i));
            if (viewHolder != null) {
                viewHolder.card.setScreenOn(z);
            }
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setSplitShadeEnabled(boolean z) {
        this.mSplitShadeEnabled = z;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.mAdapter.timeChangedDelegate = timeChangedDelegate;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setUiSurface(String str) {
        if (isAttachedToWindow()) {
            Buffer$$ExternalSyntheticBUOutline0.m$1("Must call before attaching view to window.");
            return;
        }
        if (str == BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN) {
            getContext().getTheme().applyStyle(R.style.LauncherSmartspaceView, true);
        }
        this.mAdapter.uiSurface = str;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        onSmartspaceTargetsUpdated(list, null);
    }
}
