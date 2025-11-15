package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ScrollEventAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
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
    public final BcSmartspaceView$$ExternalSyntheticLambda1 mLongPressCallback;
    public PageIndicator mPageIndicator;
    public List mPendingTargets;
    public float mPreviousDozeAmount;
    public int mScrollState;
    public boolean mSplitShadeEnabled;
    public Integer mSwipedCardPosition;
    public ViewPager mViewPager;
    public ViewPager2 mViewPager2;
    public final AnonymousClass2 mViewPager2OnPageChangeCallback;
    public final AnonymousClass3 mViewPagerOnPageChangeListener;

    /* renamed from: -$$Nest$monViewPagerPageSelected, reason: not valid java name */
    public static void m2147$$Nest$monViewPagerPageSelected(BcSmartspaceView bcSmartspaceView, int i) {
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
            bcSmartspaceView.mDataProvider.notifySmartspaceEvent(builder.build());
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
        bcSmartspaceView.mDataProvider.notifySmartspaceEvent(builder2.build());
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.systemui.smartspace.BcSmartspaceView$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.google.android.systemui.smartspace.BcSmartspaceView$2] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.google.android.systemui.smartspace.BcSmartspaceView$3] */
    public BcSmartspaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mConfigProvider = new DefaultBcSmartspaceConfigProvider();
        this.mLastReceivedTargets = new ArraySet();
        this.mIsAodEnabled = false;
        this.mCardPosition = 0;
        this.mPreviousDozeAmount = 0.0f;
        this.mScrollState = 0;
        this.mSplitShadeEnabled = false;
        this.mAodObserver = new ContentObserver(new Handler()) { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                BcSmartspaceView bcSmartspaceView = BcSmartspaceView.this;
                boolean z2 = BcSmartspaceView.DEBUG;
                Context context2 = bcSmartspaceView.getContext();
                bcSmartspaceView.mIsAodEnabled = Settings.Secure.getIntForUser(context2.getContentResolver(), "doze_always_on", 0, context2.getUserId()) == 1;
            }
        };
        this.mViewPager2OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageScrollStateChanged(int i) {
                Integer num;
                SmartspaceCard cardAtPosition;
                BcSmartspaceView bcSmartspaceView = BcSmartspaceView.this;
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

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageScrolled(int i, float f, int i2) {
                PageIndicator pageIndicator = BcSmartspaceView.this.mPageIndicator;
                if (pageIndicator != null) {
                    pageIndicator.setPageOffset(f, i);
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageSelected(int i) {
                BcSmartspaceView.m2147$$Nest$monViewPagerPageSelected(BcSmartspaceView.this, i);
            }
        };
        this.mViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.google.android.systemui.smartspace.BcSmartspaceView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
                Integer num;
                SmartspaceCard cardAtPosition;
                BcSmartspaceView bcSmartspaceView = BcSmartspaceView.this;
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

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(int i, float f, int i2) {
                PageIndicator pageIndicator = BcSmartspaceView.this.mPageIndicator;
                if (pageIndicator != null) {
                    pageIndicator.setPageOffset(f, i);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
                BcSmartspaceView.m2147$$Nest$monViewPagerPageSelected(BcSmartspaceView.this, i);
            }
        };
        this.mLongPressCallback = new BcSmartspaceView$$ExternalSyntheticLambda1(this, 2);
        getContext().getTheme().applyStyle(R.style.DefaultSmartspaceView, false);
    }

    public final void cancelScheduledLongPress() {
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 != null && this.mHasPostedLongPress) {
            this.mHasPostedLongPress = false;
            viewPager2.removeCallbacks(this.mLongPressCallback);
        }
    }

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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final int getSelectedPage() {
        ViewPager viewPager = this.mViewPager;
        int i = viewPager != null ? viewPager.mCurItem : 0;
        ViewPager2 viewPager2 = this.mViewPager2;
        return viewPager2 != null ? viewPager2.mCurrentItem : i;
    }

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
        BcSmartspaceCardLoggingInfo.Builder builder = new BcSmartspaceCardLoggingInfo.Builder();
        builder.mInstanceId = InstanceId.create(smartspaceTarget);
        builder.mFeatureType = smartspaceTarget.getFeatureType();
        builder.mDisplaySurface = BcSmartSpaceUtil.getLoggingDisplaySurface(this.mAdapter.getUiSurface(), this.mAdapter.getDozeAmount());
        builder.mRank = i;
        builder.mCardinality = this.mAdapter.getCount();
        builder.mReceivedLatency = i2;
        getContext().getPackageManager();
        builder.mUid = -1;
        builder.mSubcardInfo = containsValidTemplateType ? BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget.getTemplateData()) : BcSmartspaceCardLoggerUtil.createSubcardLoggingInfo(smartspaceTarget);
        builder.mDimensionalInfo = BcSmartspaceCardLoggerUtil.createDimensionalLoggingInfo(smartspaceTarget.getTemplateData());
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = new BcSmartspaceCardLoggingInfo(builder);
        if (containsValidTemplateType) {
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(bcSmartspaceCardLoggingInfo, smartspaceTarget.getTemplateData());
        } else {
            BcSmartspaceCardLoggerUtil.tryForcePrimaryFeatureTypeAndInjectWeatherSubcard(bcSmartspaceCardLoggingInfo, smartspaceTarget);
        }
        BcSmartspaceCardLogger.log(bcSmartspaceEvent, bcSmartspaceCardLoggingInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onAttachedToWindow() {
        /*
            r5 = this;
            super.onAttachedToWindow()
            androidx.viewpager.widget.ViewPager r0 = r5.mViewPager
            java.lang.String r1 = "BcSmartspaceView"
            if (r0 == 0) goto L29
            com.google.android.systemui.smartspace.CardAdapter r2 = r5.mAdapter
            boolean r3 = r2 instanceof com.google.android.systemui.smartspace.CardPagerAdapter
            if (r3 == 0) goto L29
            com.google.android.systemui.smartspace.CardPagerAdapter r2 = (com.google.android.systemui.smartspace.CardPagerAdapter) r2
            r0.setAdapter(r2)
            androidx.viewpager.widget.ViewPager r0 = r5.mViewPager
            com.google.android.systemui.smartspace.BcSmartspaceView$3 r2 = r5.mViewPagerOnPageChangeListener
            java.util.List r3 = r0.mOnPageChangeListeners
            if (r3 != 0) goto L23
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r0.mOnPageChangeListeners = r3
        L23:
            java.util.List r0 = r0.mOnPageChangeListeners
            r0.add(r2)
            goto L49
        L29:
            androidx.viewpager2.widget.ViewPager2 r0 = r5.mViewPager2
            if (r0 == 0) goto L44
            com.google.android.systemui.smartspace.CardAdapter r2 = r5.mAdapter
            boolean r3 = r2 instanceof com.google.android.systemui.smartspace.CardRecyclerViewAdapter
            if (r3 == 0) goto L44
            com.google.android.systemui.smartspace.CardRecyclerViewAdapter r2 = (com.google.android.systemui.smartspace.CardRecyclerViewAdapter) r2
            r0.setAdapter(r2)
            androidx.viewpager2.widget.ViewPager2 r0 = r5.mViewPager2
            com.google.android.systemui.smartspace.BcSmartspaceView$2 r2 = r5.mViewPager2OnPageChangeCallback
            androidx.viewpager2.widget.CompositeOnPageChangeCallback r0 = r0.mExternalPageChangeCallbacks
            java.util.List r0 = r0.mCallbacks
            r0.add(r2)
            goto L49
        L44:
            java.lang.String r0 = "Unable to attach the view pager adapter"
            android.util.Log.w(r1, r0)
        L49:
            com.google.android.systemui.smartspace.PageIndicator r0 = r5.mPageIndicator
            com.google.android.systemui.smartspace.CardAdapter r2 = r5.mAdapter
            int r2 = r2.getCount()
            boolean r3 = r5.isLayoutRtl()
            r0.setNumPages(r2, r3)
            com.google.android.systemui.smartspace.CardAdapter r0 = r5.mAdapter
            java.lang.String r0 = r0.getUiSurface()
            java.lang.String r2 = "lockscreen"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L9c
            android.os.Handler r0 = r5.mBgHandler     // Catch: java.lang.Exception -> L8d
            if (r0 == 0) goto L8f
            com.google.android.systemui.smartspace.BcSmartspaceView$$ExternalSyntheticLambda1 r2 = new com.google.android.systemui.smartspace.BcSmartspaceView$$ExternalSyntheticLambda1     // Catch: java.lang.Exception -> L8d
            r3 = 1
            r2.<init>(r5, r3)     // Catch: java.lang.Exception -> L8d
            r0.post(r2)     // Catch: java.lang.Exception -> L8d
            android.content.Context r0 = r5.getContext()     // Catch: java.lang.Exception -> L8d
            android.content.ContentResolver r2 = r0.getContentResolver()     // Catch: java.lang.Exception -> L8d
            int r0 = r0.getUserId()     // Catch: java.lang.Exception -> L8d
            java.lang.String r3 = "doze_always_on"
            r4 = 0
            int r0 = android.provider.Settings.Secure.getIntForUser(r2, r3, r4, r0)     // Catch: java.lang.Exception -> L8d
            r2 = 1
            if (r0 != r2) goto L8a
            r4 = r2
        L8a:
            r5.mIsAodEnabled = r4     // Catch: java.lang.Exception -> L8d
            goto L9c
        L8d:
            r0 = move-exception
            goto L97
        L8f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Exception -> L8d
            java.lang.String r2 = "Must set background handler to avoid making binder calls on main thread"
            r0.<init>(r2)     // Catch: java.lang.Exception -> L8d
            throw r0     // Catch: java.lang.Exception -> L8d
        L97:
            java.lang.String r2 = "Unable to register Doze Always on content observer."
            android.util.Log.w(r1, r2, r0)
        L9c:
            com.android.systemui.plugins.BcSmartspaceDataPlugin r0 = r5.mDataProvider
            if (r0 == 0) goto La3
            r5.registerDataProvider(r0)
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.BcSmartspaceView.onAttachedToWindow():void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mBgHandler;
        if (handler == null) {
            throw new IllegalStateException("Must set background handler to avoid making binder calls on main thread");
        }
        handler.post(new BcSmartspaceView$$ExternalSyntheticLambda1(this, 0));
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

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R.id.smartspace_card_pager);
        if (findViewById instanceof ViewPager) {
            this.mViewPager = (ViewPager) findViewById;
            this.mAdapter = new CardPagerAdapter(this, this.mConfigProvider);
        } else {
            if (!(findViewById instanceof ViewPager2)) {
                throw new IllegalStateException("smartspace_card_pager is an invalid view type");
            }
            this.mViewPager2 = (ViewPager2) findViewById;
            this.mAdapter = new CardRecyclerViewAdapter(this, this.mConfigProvider);
        }
        PageIndicator pageIndicator = (PageIndicator) findViewById(R.id.smartspace_page_indicator);
        this.mPageIndicator = pageIndicator;
        pageIndicator.setPaddingRelative(((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.non_remoteviews_card_padding_start), this.mPageIndicator.getPaddingTop(), this.mPageIndicator.getPaddingEnd(), this.mPageIndicator.getPaddingBottom());
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        if (viewPager2 == null) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        handleTouchOverride(motionEvent, new BcSmartspaceView$$ExternalSyntheticLambda0(viewPager2, 1));
        return super.onInterceptTouchEvent(motionEvent) || this.mHasPerformedLongPress;
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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        int i;
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
        if (isLayoutRtl) {
            i = this.mAdapter.getCount() - selectedPage;
            ArrayList arrayList = new ArrayList(list);
            Collections.reverse(arrayList);
            list = arrayList;
        } else {
            i = selectedPage;
        }
        this.mAdapter.getTemplateCardAtPosition(selectedPage);
        this.mAdapter.getLegacyCardAtPosition(selectedPage);
        this.mAdapter.getRemoteViewsCardAtPosition(selectedPage);
        this.mAdapter.setTargets(list);
        int count = this.mAdapter.getCount();
        PageIndicator pageIndicator = this.mPageIndicator;
        if (pageIndicator != null) {
            pageIndicator.setNumPages(count, isLayoutRtl);
        }
        if (isLayoutRtl) {
            int max = Math.max(0, Math.min(count - 1, count - i));
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null) {
                viewPager.setCurrentItem(max, false);
            } else {
                ViewPager2 viewPager2 = this.mViewPager2;
                if (viewPager2 != null) {
                    ScrollEventAdapter scrollEventAdapter = viewPager2.mFakeDragger.mScrollEventAdapter;
                    viewPager2.setCurrentItemInternal(max, false);
                }
            }
            this.mPageIndicator.setPageOffset(0.0f, max);
        }
        for (int i2 = 0; i2 < count; i2++) {
            SmartspaceTarget targetAtPosition = this.mAdapter.getTargetAtPosition(i2);
            if (!this.mLastReceivedTargets.contains(targetAtPosition.getSmartspaceTargetId())) {
                logSmartspaceEvent(targetAtPosition, i2, BcSmartspaceEvent.SMARTSPACE_CARD_RECEIVED);
                SmartspaceTargetEvent.Builder builder = new SmartspaceTargetEvent.Builder(8);
                builder.setSmartspaceTarget(targetAtPosition);
                SmartspaceAction baseAction = targetAtPosition.getBaseAction();
                if (baseAction != null) {
                    builder.setSmartspaceActionId(baseAction.getId());
                }
                this.mDataProvider.notifySmartspaceEvent(builder.build());
            }
        }
        this.mLastReceivedTargets.clear();
        this.mLastReceivedTargets.addAll((Collection) this.mAdapter.getSmartspaceTargets().stream().map(new BcSmartspaceView$$ExternalSyntheticLambda3()).collect(Collectors.toList()));
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ViewPager2 viewPager2 = this.mViewPager2;
        return viewPager2 != null ? handleTouchOverride(motionEvent, new BcSmartspaceView$$ExternalSyntheticLambda0(viewPager2, 0)) : super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.mDataProvider;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(z ? 6 : 7).build());
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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void registerConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        this.mConfigProvider = bcSmartspaceConfigPlugin;
        this.mAdapter.setConfigProvider(bcSmartspaceConfigPlugin);
    }

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
        this.mAdapter.setBgHandler(handler);
    }

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
        PageIndicator pageIndicator = this.mPageIndicator;
        if (pageIndicator != null) {
            pageIndicator.setNumPages(this.mAdapter.getCount(), isLayoutRtl());
            this.mPageIndicator.setAlpha(f2);
            if (this.mPageIndicator.getVisibility() != 8) {
                if (f == 1.0f) {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mPageIndicator, 4);
                } else {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mPageIndicator, 0);
                }
            }
        }
        this.mPreviousDozeAmount = f;
        if (this.mAdapter.getHasDifferentTargets() && this.mAdapter.getSmartspaceTargets() != smartspaceTargets && this.mAdapter.getCount() > 0) {
            int count = isLayoutRtl() ? this.mAdapter.getCount() - 1 : 0;
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null) {
                viewPager.setCurrentItem(count, false);
            } else {
                ViewPager2 viewPager2 = this.mViewPager2;
                if (viewPager2 != null) {
                    ScrollEventAdapter scrollEventAdapter = viewPager2.mFakeDragger.mScrollEventAdapter;
                    viewPager2.setCurrentItemInternal(count, false);
                }
            }
            this.mPageIndicator.setPageOffset(0.0f, count);
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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setDozing(boolean z) {
        if (!z && this.mSplitShadeEnabled && this.mAdapter.getHasAodLockscreenTransition() && this.mAdapter.getLockscreenTargets().isEmpty()) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this, 8);
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setFalsingManager(FalsingManager falsingManager) {
        BcSmartSpaceUtil.sFalsingManager = falsingManager;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setHorizontalPaddings(int i) {
        PageIndicator pageIndicator = this.mPageIndicator;
        if (pageIndicator != null) {
            pageIndicator.setPaddingRelative(i, pageIndicator.getPaddingTop(), i, this.mPageIndicator.getPaddingBottom());
        }
        this.mAdapter.setNonRemoteViewsHorizontalPadding(Integer.valueOf(i));
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setIntentStarter(BcSmartspaceDataPlugin.IntentStarter intentStarter) {
        BcSmartSpaceUtil.sIntentStarter = intentStarter;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setKeyguardBypassEnabled(boolean z) {
        this.mAdapter.setKeyguardBypassEnabled(z);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setMediaTarget(SmartspaceTarget smartspaceTarget) {
        this.mAdapter.setMediaTarget(smartspaceTarget);
    }

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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setPrimaryTextColor(int i) {
        this.mAdapter.setPrimaryTextColor(i);
        PageIndicator pageIndicator = this.mPageIndicator;
        pageIndicator.mPrimaryColor = i;
        for (int i2 = 0; i2 < pageIndicator.getChildCount(); i2++) {
            ((ImageView) pageIndicator.getChildAt(i2)).getDrawable().setTint(pageIndicator.mPrimaryColor);
        }
    }

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

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setSplitShadeEnabled(boolean z) {
        this.mSplitShadeEnabled = z;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceView
    public final void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate) {
        this.mAdapter.setTimeChangedDelegate(timeChangedDelegate);
    }

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
