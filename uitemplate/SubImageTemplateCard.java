package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.uitemplatedata.Icon;
import android.app.smartspace.uitemplatedata.SubImageTemplateData;
import android.app.smartspace.uitemplatedata.TapAction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceCardSecondary;
import com.google.android.systemui.smartspace.BcSmartspaceTemplateDataUtils;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggerUtil;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.VarHandle;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class SubImageTemplateCard extends BcSmartspaceCardSecondary {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler mHandler;
    public final Map mIconDrawableCache;
    public final int mImageHeight;
    public ImageView mImageView;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class DrawableWrapper {
        public ContentResolver mContentResolver;
        public Drawable mDrawable;
        public int mHeightInPx;
        public SubImageTemplateCard$$ExternalSyntheticLambda0 mListener;
        public Uri mUri;
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public final class LoadUriTask extends AsyncTask {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            DrawableWrapper[] drawableWrapperArr = (DrawableWrapper[]) objArr;
            Drawable drawable = null;
            if (drawableWrapperArr.length <= 0) {
                return null;
            }
            DrawableWrapper drawableWrapper = drawableWrapperArr[0];
            try {
                InputStream openInputStream = drawableWrapper.mContentResolver.openInputStream(drawableWrapper.mUri);
                int i = drawableWrapper.mHeightInPx;
                int i2 = SubImageTemplateCard.$r8$clinit;
                try {
                    ImageDecoder.Source createSource = ImageDecoder.createSource((Resources) null, openInputStream);
                    SubImageTemplateCard$$ExternalSyntheticLambda3 subImageTemplateCard$$ExternalSyntheticLambda3 = new SubImageTemplateCard$$ExternalSyntheticLambda3();
                    subImageTemplateCard$$ExternalSyntheticLambda3.f$0 = i;
                    VarHandle.storeStoreFence();
                    drawable = ImageDecoder.decodeDrawable(createSource, subImageTemplateCard$$ExternalSyntheticLambda3);
                } catch (IOException e) {
                    Log.e("SubImageTemplateCard", "Unable to decode stream: " + e);
                }
                drawableWrapper.mDrawable = drawable;
            } catch (Exception e2) {
                Log.w("SubImageTemplateCard", "open uri:" + drawableWrapper.mUri + " got exception:" + e2);
            }
            return drawableWrapper;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            DrawableWrapper drawableWrapper = (DrawableWrapper) obj;
            drawableWrapper.mListener.onDrawableLoaded(drawableWrapper.mDrawable);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public SubImageTemplateCard(Context context) {
        this(context, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.image_view);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        Map map = this.mIconDrawableCache;
        if (map != null) {
            map.clear();
        }
        ImageView imageView = this.mImageView;
        if (imageView == null) {
            return;
        }
        imageView.getLayoutParams().width = -2;
        this.mImageView.setImageDrawable(null);
        this.mImageView.setBackgroundTintList(null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        String sb;
        SubImageTemplateData templateData = smartspaceTarget.getTemplateData();
        if (!BcSmartspaceCardLoggerUtil.containsValidTemplateType(templateData) || templateData.getSubImages() == null || templateData.getSubImages().isEmpty()) {
            Log.w("SubImageTemplateCard", "SubImageTemplateData is null or has no SubImage or invalid template type");
            return false;
        }
        List subImages = templateData.getSubImages();
        TapAction subImageAction = templateData.getSubImageAction();
        if (this.mImageView == null) {
            Log.w("SubImageTemplateCard", "No image view can be updated. Skipping background update...");
        } else if (subImageAction != null && subImageAction.getExtras() != null) {
            Bundle extras = subImageAction.getExtras();
            String string = extras.getString("imageDimensionRatio", "");
            if (!TextUtils.isEmpty(string)) {
                this.mImageView.getLayoutParams().width = 0;
                ((ConstraintLayout.LayoutParams) this.mImageView.getLayoutParams()).dimensionRatio = string;
            }
            if (extras.getBoolean("shouldShowBackground", false)) {
                this.mImageView.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.smartspace_button_background)));
            }
        }
        int i = 200;
        if (subImageAction != null && subImageAction.getExtras() != null) {
            i = subImageAction.getExtras().getInt("GifFrameDurationMillis", 200);
        }
        ContentResolver contentResolver = getContext().getApplicationContext().getContentResolver();
        TreeMap treeMap = new TreeMap();
        WeakReference weakReference = new WeakReference(this.mImageView);
        String str = this.mPrevSmartspaceTargetId;
        for (int i2 = 0; i2 < subImages.size(); i2++) {
            Icon icon = (Icon) subImages.get(i2);
            if (icon != null && icon.getIcon() != null) {
                android.graphics.drawable.Icon icon2 = icon.getIcon();
                Map map = BcSmartspaceTemplateDataUtils.TEMPLATE_TYPE_TO_SECONDARY_CARD_RES;
                StringBuilder sb2 = new StringBuilder(icon2.getType());
                switch (icon2.getType()) {
                    case 1:
                    case 5:
                        sb2.append(icon2.getBitmap().hashCode());
                        sb = sb2.toString();
                        break;
                    case 2:
                        sb2.append(icon2.getResPackage());
                        sb2.append(String.format("0x%08x", Integer.valueOf(icon2.getResId())));
                        sb = sb2.toString();
                        break;
                    case 3:
                        sb2.append(Arrays.hashCode(icon2.getDataBytes()));
                        sb = sb2.toString();
                        break;
                    case 4:
                    case 6:
                        sb2.append(icon2.getUriString());
                        sb = sb2.toString();
                        break;
                    default:
                        sb = sb2.toString();
                        break;
                }
                SubImageTemplateCard$$ExternalSyntheticLambda0 subImageTemplateCard$$ExternalSyntheticLambda0 = new SubImageTemplateCard$$ExternalSyntheticLambda0();
                subImageTemplateCard$$ExternalSyntheticLambda0.f$0 = this;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$1 = str;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$2 = sb;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$3 = treeMap;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$4 = i2;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$5 = subImages;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$6 = i;
                subImageTemplateCard$$ExternalSyntheticLambda0.f$7 = weakReference;
                VarHandle.storeStoreFence();
                if (this.mIconDrawableCache.containsKey(sb) && this.mIconDrawableCache.get(sb) != null) {
                    subImageTemplateCard$$ExternalSyntheticLambda0.onDrawableLoaded((Drawable) this.mIconDrawableCache.get(sb));
                } else if (icon2.getType() == 4) {
                    Uri uri = icon2.getUri();
                    int i3 = this.mImageHeight;
                    DrawableWrapper drawableWrapper = new DrawableWrapper();
                    drawableWrapper.mUri = uri;
                    drawableWrapper.mHeightInPx = i3;
                    drawableWrapper.mContentResolver = contentResolver;
                    drawableWrapper.mListener = subImageTemplateCard$$ExternalSyntheticLambda0;
                    VarHandle.storeStoreFence();
                    new LoadUriTask().execute(drawableWrapper);
                } else {
                    icon2.loadDrawableAsync(getContext(), subImageTemplateCard$$ExternalSyntheticLambda0, this.mHandler);
                }
            }
        }
        if (subImageAction != null) {
            BcSmartSpaceUtil.setOnClickListener$1(this, smartspaceTarget, subImageAction, smartspaceEventNotifier, "SubImageTemplateCard", bcSmartspaceCardLoggingInfo, 0);
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public SubImageTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIconDrawableCache = new HashMap();
        this.mHandler = new Handler();
        this.mImageHeight = getResources().getDimensionPixelOffset(R.dimen.enhanced_smartspace_card_height);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
    }
}
