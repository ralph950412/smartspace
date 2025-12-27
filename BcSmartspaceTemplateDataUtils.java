package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceUtils;
import android.app.smartspace.uitemplatedata.Icon;
import android.app.smartspace.uitemplatedata.Text;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wm.shell.R;
import java.util.Map;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class BcSmartspaceTemplateDataUtils {
    public static final Map TEMPLATE_TYPE_TO_SECONDARY_CARD_RES = Map.ofEntries(Map.entry(2, Integer.valueOf(R.layout.smartspace_sub_image_template_card)), Map.entry(3, Integer.valueOf(R.layout.smartspace_sub_list_template_card)), Map.entry(7, Integer.valueOf(R.layout.smartspace_sub_card_template_card)), Map.entry(5, Integer.valueOf(R.layout.smartspace_head_to_head_template_card)), Map.entry(6, Integer.valueOf(R.layout.smartspace_combined_cards_template_card)), Map.entry(4, Integer.valueOf(R.layout.smartspace_carousel_template_card)));

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void offsetTextViewForIcon(TextView textView, DoubleShadowIconDrawable doubleShadowIconDrawable, boolean z) {
        if (doubleShadowIconDrawable == null) {
            textView.setTranslationX(0.0f);
        } else {
            textView.setTranslationX((z ? 1 : -1) * doubleShadowIconDrawable.mIconInsetSize);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setIcon(ImageView imageView, Icon icon) {
        if (imageView == null) {
            Log.w("BcSmartspaceTemplateDataUtils", "Cannot set. The image view is null");
            return;
        }
        if (icon == null) {
            Log.w("BcSmartspaceTemplateDataUtils", "Cannot set. The given icon is null");
            updateVisibility(imageView, 8);
        }
        imageView.setImageIcon(icon.getIcon());
        if (icon.getContentDescription() != null) {
            imageView.setContentDescription(icon.getContentDescription());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setText(TextView textView, Text text) {
        if (textView == null) {
            Log.w("BcSmartspaceTemplateDataUtils", "Cannot set. The text view is null");
            return;
        }
        if (SmartspaceUtils.isEmpty(text)) {
            Log.w("BcSmartspaceTemplateDataUtils", "Cannot set. The given text is empty");
            updateVisibility(textView, 8);
        } else {
            textView.setText(text.getText());
            textView.setEllipsize(text.getTruncateAtType());
            textView.setMaxLines(text.getMaxLines());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void updateVisibility(View view, int i) {
        if (view == null || view.getVisibility() == i) {
            return;
        }
        view.setVisibility(i);
    }
}
