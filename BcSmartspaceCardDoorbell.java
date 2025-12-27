package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.VarHandle;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCardDoorbell extends BcSmartspaceCardGenericImage {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mGifFrameDurationInMs;
    public final LatencyInstrumentContext mLatencyInstrumentContext;
    public ImageView mLoadingIcon;
    public ViewGroup mLoadingScreenView;
    public String mPreviousTargetId;
    public ProgressBar mProgressBar;
    public final Map mUriToDrawable;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class DrawableWithUri extends DrawableWrapper {
        public Path mClipPath;
        public ContentResolver mContentResolver;
        public Drawable mDrawable;
        public int mHeightInPx;
        public WeakReference mImageViewWeakReference;
        public WeakReference mLoadingScreenWeakReference;
        public float mScaledCornerRadius;
        public RectF mTempRect;
        public Uri mUri;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            int save = canvas.save();
            canvas.clipPath(this.mClipPath);
            super.draw(canvas);
            canvas.restoreToCount(save);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public final void onBoundsChange(Rect rect) {
            this.mTempRect.set(getBounds());
            this.mClipPath.reset();
            Path path = this.mClipPath;
            RectF rectF = this.mTempRect;
            float f = this.mScaledCornerRadius;
            path.addRoundRect(rectF, f, f, Path.Direction.CCW);
            super.onBoundsChange(rect);
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class LatencyInstrumentContext {
        public LatencyTracker mLatencyTracker;
        public Set mUriSet;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public final void cancelInstrument() {
            if (this.mUriSet.isEmpty()) {
                return;
            }
            this.mLatencyTracker.onActionCancel(22);
            this.mUriSet.clear();
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class LoadUriTask extends AsyncTask {
        public LatencyInstrumentContext mInstrumentContext;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            DrawableWithUri[] drawableWithUriArr = (DrawableWithUri[]) objArr;
            Drawable drawable = null;
            if (drawableWithUriArr.length <= 0) {
                return null;
            }
            DrawableWithUri drawableWithUri = drawableWithUriArr[0];
            try {
                InputStream openInputStream = drawableWithUri.mContentResolver.openInputStream(drawableWithUri.mUri);
                try {
                    int i = drawableWithUri.mHeightInPx;
                    int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                    try {
                        ImageDecoder.Source createSource = ImageDecoder.createSource((Resources) null, openInputStream);
                        BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0 bcSmartspaceCardDoorbell$$ExternalSyntheticLambda0 = new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0();
                        bcSmartspaceCardDoorbell$$ExternalSyntheticLambda0.f$0 = i;
                        VarHandle.storeStoreFence();
                        drawable = ImageDecoder.decodeDrawable(createSource, bcSmartspaceCardDoorbell$$ExternalSyntheticLambda0);
                    } catch (IOException e) {
                        Log.e("BcSmartspaceCardBell", "Unable to decode stream: " + e);
                    }
                    drawableWithUri.mDrawable = drawable;
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                } finally {
                }
            } catch (Exception e2) {
                Log.w("BcSmartspaceCardBell", "open uri:" + drawableWithUri.mUri + " got exception:" + e2);
            }
            return drawableWithUri;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.os.AsyncTask
        public final void onCancelled() {
            this.mInstrumentContext.cancelInstrument();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            DrawableWithUri drawableWithUri = (DrawableWithUri) obj;
            if (drawableWithUri == null) {
                return;
            }
            Drawable drawable = drawableWithUri.mDrawable;
            if (drawable != null) {
                drawableWithUri.setDrawable(drawable);
                ImageView imageView = (ImageView) drawableWithUri.mImageViewWeakReference.get();
                int intrinsicWidth = drawableWithUri.mDrawable.getIntrinsicWidth();
                if (imageView.getLayoutParams().width != intrinsicWidth) {
                    Log.d("BcSmartspaceCardBell", "imageView requestLayout " + drawableWithUri.mUri);
                    imageView.getLayoutParams().width = intrinsicWidth;
                    imageView.requestLayout();
                }
                LatencyInstrumentContext latencyInstrumentContext = this.mInstrumentContext;
                Uri uri = drawableWithUri.mUri;
                if (!latencyInstrumentContext.mUriSet.isEmpty()) {
                    if (uri == null || !latencyInstrumentContext.mUriSet.remove(uri)) {
                        latencyInstrumentContext.cancelInstrument();
                    } else if (latencyInstrumentContext.mUriSet.isEmpty()) {
                        latencyInstrumentContext.mLatencyTracker.onActionEnd(22);
                    }
                }
            } else {
                BcSmartspaceTemplateDataUtils.updateVisibility((ImageView) drawableWithUri.mImageViewWeakReference.get(), 8);
                this.mInstrumentContext.cancelInstrument();
            }
            BcSmartspaceTemplateDataUtils.updateVisibility((View) drawableWithUri.mLoadingScreenWeakReference.get(), 8);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardDoorbell(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void maybeResetImageView(SmartspaceTarget smartspaceTarget) {
        boolean equals = smartspaceTarget.getSmartspaceTargetId().equals(this.mPreviousTargetId);
        this.mPreviousTargetId = smartspaceTarget.getSmartspaceTargetId();
        if (equals) {
            return;
        }
        this.mImageView.getLayoutParams().width = -2;
        this.mImageView.setImageDrawable(null);
        this.mUriToDrawable.clear();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void maybeUpdateLayoutHeight(Bundle bundle, View view, String str) {
        if (bundle.containsKey(str)) {
            float f = getContext().getResources().getDisplayMetrics().density;
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            int i = bundle.getInt(str);
            FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
            layoutParams.height = (int) (f * i);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void maybeUpdateLayoutWidth(Bundle bundle, View view, String str) {
        if (bundle.containsKey(str)) {
            float f = getContext().getResources().getDisplayMetrics().density;
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            int i = bundle.getInt(str);
            FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
            layoutParams.width = (int) (f * i);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mLoadingScreenView = (ViewGroup) findViewById(R.id.loading_screen);
        this.mProgressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        this.mLoadingIcon = (ImageView) findViewById(R.id.loading_screen_icon);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        super.resetUi();
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoadingScreenView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mProgressBar, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoadingIcon, 8);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0276  */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        if (!getContext().getPackageName().equals("com.android.systemui")) {
            return false;
        }
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        final int i = 1;
        List list = (List) smartspaceTarget.getIconGrid().stream().filter(new Predicate() { // from class: com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell$$ExternalSyntheticLambda3
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                switch (i) {
                    case 0:
                        return Objects.nonNull((BcSmartspaceCardDoorbell.DrawableWithUri) obj);
                    default:
                        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                        return ((SmartspaceAction) obj).getExtras().containsKey("imageUri");
                }
            }
        }).map(new Function() { // from class: com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell$$ExternalSyntheticLambda5
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (r1) {
                    case 0:
                        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                        return ((SmartspaceAction) obj).getExtras().getString("imageUri");
                    default:
                        return Uri.parse((String) obj);
                }
            }
        }).map(new Function() { // from class: com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell$$ExternalSyntheticLambda5
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                        return ((SmartspaceAction) obj).getExtras().getString("imageUri");
                    default:
                        return Uri.parse((String) obj);
                }
            }
        }).collect(Collectors.toList());
        if (!list.isEmpty()) {
            if (extras != null && extras.containsKey("frameDurationMs")) {
                this.mGifFrameDurationInMs = extras.getInt("frameDurationMs");
            }
            LatencyInstrumentContext latencyInstrumentContext = this.mLatencyInstrumentContext;
            Stream stream = list.stream();
            BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1 bcSmartspaceCardDoorbell$$ExternalSyntheticLambda1 = new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1();
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda1.f$0 = this;
            VarHandle.storeStoreFence();
            Collection collection = (Collection) stream.filter(bcSmartspaceCardDoorbell$$ExternalSyntheticLambda1).collect(Collectors.toSet());
            if (collection != null) {
                latencyInstrumentContext.getClass();
                if (!collection.isEmpty()) {
                    latencyInstrumentContext.mUriSet.addAll(collection);
                }
            }
            if (!latencyInstrumentContext.mUriSet.isEmpty()) {
                latencyInstrumentContext.mLatencyTracker.onActionStart(22);
            }
            maybeResetImageView(smartspaceTarget);
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 0);
            ContentResolver contentResolver = getContext().getApplicationContext().getContentResolver();
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.enhanced_smartspace_card_height);
            float dimension = getResources().getDimension(R.dimen.enhanced_smartspace_secondary_card_corner_radius);
            WeakReference weakReference = new WeakReference(this.mImageView);
            WeakReference weakReference2 = new WeakReference(this.mLoadingScreenView);
            Stream stream2 = list.stream();
            BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 = new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(0);
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$0 = this;
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$1 = contentResolver;
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$2 = dimensionPixelSize;
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$3 = dimension;
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$4 = weakReference;
            bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2.f$5 = weakReference2;
            VarHandle.storeStoreFence();
            List list2 = (List) stream2.map(bcSmartspaceCardDoorbell$$ExternalSyntheticLambda2).filter(new Predicate() { // from class: com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell$$ExternalSyntheticLambda3
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    switch (r1) {
                        case 0:
                            return Objects.nonNull((BcSmartspaceCardDoorbell.DrawableWithUri) obj);
                        default:
                            int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
                            return ((SmartspaceAction) obj).getExtras().containsKey("imageUri");
                    }
                }
            }).collect(Collectors.toList());
            AnimationDrawable animationDrawable = new AnimationDrawable();
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                animationDrawable.addFrame((Drawable) it.next(), this.mGifFrameDurationInMs);
            }
            this.mImageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
            Log.d("BcSmartspaceCardBell", "imageUri is set");
            return true;
        }
        if (extras != null && extras.containsKey("imageBitmap")) {
            Bitmap bitmap = (Bitmap) extras.get("imageBitmap");
            maybeResetImageView(smartspaceTarget);
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 0);
            if (bitmap != null) {
                if (bitmap.getHeight() != 0) {
                    int dimension2 = (int) getResources().getDimension(R.dimen.enhanced_smartspace_card_height);
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (dimension2 * (bitmap.getWidth() / bitmap.getHeight())), dimension2, true);
                }
                RoundedBitmapDrawable21 create = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                create.setCornerRadius(getResources().getDimension(R.dimen.enhanced_smartspace_secondary_card_corner_radius));
                this.mImageView.setImageDrawable(create);
                Log.d("BcSmartspaceCardBell", "imageBitmap is set");
            }
            return true;
        }
        if (extras == null || !extras.containsKey("loadingScreenState")) {
            return false;
        }
        int i2 = extras.getInt("loadingScreenState");
        String dimensionRatio = BcSmartSpaceUtil.getDimensionRatio(extras);
        if (dimensionRatio == null) {
            return false;
        }
        maybeResetImageView(smartspaceTarget);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mImageView, 8);
        ((ConstraintLayout.LayoutParams) this.mLoadingScreenView.getLayoutParams()).dimensionRatio = dimensionRatio;
        this.mLoadingScreenView.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.smartspace_button_background)));
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoadingScreenView, 0);
        maybeUpdateLayoutWidth(extras, this.mProgressBar, "progressBarWidth");
        maybeUpdateLayoutHeight(extras, this.mProgressBar, "progressBarHeight");
        this.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(getContext().getColor(R.color.smartspace_button_text)));
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mProgressBar, i2 == 1 ? true : i2 == 4 ? extras.getBoolean("progressBarVisible", true) : false ? 0 : 8);
        if (i2 == 2) {
            this.mLoadingIcon.setImageDrawable(getContext().getDrawable(R.drawable.videocam));
        } else if (i2 == 3) {
            this.mLoadingIcon.setImageDrawable(getContext().getDrawable(R.drawable.videocam_off));
        } else {
            if (i2 != 4 || !extras.containsKey("loadingScreenIcon")) {
                z = false;
                maybeUpdateLayoutWidth(extras, this.mLoadingIcon, "loadingIconWidth");
                maybeUpdateLayoutHeight(extras, this.mLoadingIcon, "loadingIconHeight");
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoadingIcon, z ? 0 : 8);
                return true;
            }
            this.mLoadingIcon.setImageBitmap((Bitmap) extras.get("loadingScreenIcon"));
            if (extras.getBoolean("tintLoadingIcon", false)) {
                this.mLoadingIcon.setColorFilter(getContext().getColor(R.color.smartspace_button_text));
            }
        }
        z = true;
        maybeUpdateLayoutWidth(extras, this.mLoadingIcon, "loadingIconWidth");
        maybeUpdateLayoutHeight(extras, this.mLoadingIcon, "loadingIconHeight");
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mLoadingIcon, z ? 0 : 8);
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BcSmartspaceCardDoorbell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUriToDrawable = new HashMap();
        this.mGifFrameDurationInMs = 200;
        LatencyInstrumentContext latencyInstrumentContext = new LatencyInstrumentContext();
        latencyInstrumentContext.mUriSet = new HashSet();
        latencyInstrumentContext.mLatencyTracker = LatencyTracker.getInstance(context);
        VarHandle.storeStoreFence();
        this.mLatencyInstrumentContext = latencyInstrumentContext;
    }
}
