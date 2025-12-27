package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.util.Locale;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCardShoppingList extends BcSmartspaceCardSecondary {
    public static final int[] LIST_ITEM_TEXT_VIEW_IDS = {R.id.list_item_1, R.id.list_item_2, R.id.list_item_3};
    public ImageView mCardPromptIconView;
    public TextView mCardPromptView;
    public TextView mEmptyListMessageView;
    public ImageView mListIconView;
    public final TextView[] mListItems;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardShoppingList(Context context) {
        super(context);
        this.mListItems = new TextView[3];
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mCardPromptView = (TextView) findViewById(R.id.card_prompt);
        this.mEmptyListMessageView = (TextView) findViewById(R.id.empty_list_message);
        this.mCardPromptIconView = (ImageView) findViewById(R.id.card_prompt_icon);
        this.mListIconView = (ImageView) findViewById(R.id.list_icon);
        for (int i = 0; i < 3; i++) {
            this.mListItems[i] = (TextView) findViewById(LIST_ITEM_TEXT_VIEW_IDS[i]);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void resetUi() {
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mEmptyListMessageView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mListIconView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptIconView, 8);
        BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptView, 8);
        for (int i = 0; i < 3; i++) {
            BcSmartspaceTemplateDataUtils.updateVisibility(this.mListItems[i], 8);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bitmap bitmap = null;
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        if (extras != null) {
            if (extras.containsKey("appIcon")) {
                bitmap = (Bitmap) extras.get("appIcon");
            } else if (extras.containsKey("imageBitmap")) {
                bitmap = (Bitmap) extras.get("imageBitmap");
            }
            this.mCardPromptIconView.setImageBitmap(bitmap);
            this.mListIconView.setImageBitmap(bitmap);
            if (extras.containsKey("cardPrompt")) {
                String string = extras.getString("cardPrompt");
                TextView textView = this.mCardPromptView;
                if (textView == null) {
                    Log.w("BcSmartspaceCardShoppingList", "No card prompt view to update");
                } else {
                    textView.setText(string);
                }
                BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptView, 0);
                if (bitmap != null) {
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mCardPromptIconView, 0);
                    return true;
                }
            } else {
                if (extras.containsKey("emptyListString")) {
                    String string2 = extras.getString("emptyListString");
                    TextView textView2 = this.mEmptyListMessageView;
                    if (textView2 == null) {
                        Log.w("BcSmartspaceCardShoppingList", "No empty list message view to update");
                    } else {
                        textView2.setText(string2);
                    }
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mEmptyListMessageView, 0);
                    BcSmartspaceTemplateDataUtils.updateVisibility(this.mListIconView, 0);
                    return true;
                }
                if (extras.containsKey("listItems")) {
                    String[] stringArray = extras.getStringArray("listItems");
                    if (stringArray.length != 0) {
                        BcSmartspaceTemplateDataUtils.updateVisibility(this.mListIconView, 0);
                        for (int i = 0; i < 3; i++) {
                            TextView textView3 = this.mListItems[i];
                            if (textView3 == null) {
                                Locale locale = Locale.US;
                                RecordingInputConnection$$ExternalSyntheticOutline0.m(i + 1, "Missing list item view to update at row: ", "BcSmartspaceCardShoppingList");
                                return true;
                            }
                            if (i < stringArray.length) {
                                BcSmartspaceTemplateDataUtils.updateVisibility(textView3, 0);
                                textView3.setText(stringArray[i]);
                            } else {
                                BcSmartspaceTemplateDataUtils.updateVisibility(textView3, 8);
                                textView3.setText("");
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        this.mCardPromptView.setTextColor(i);
        this.mEmptyListMessageView.setTextColor(i);
        for (int i2 = 0; i2 < 3; i2++) {
            TextView textView = this.mListItems[i2];
            if (textView == null) {
                Locale locale = Locale.US;
                RecordingInputConnection$$ExternalSyntheticOutline0.m(i2 + 1, "Missing list item view to update at row: ", "BcSmartspaceCardShoppingList");
                return;
            }
            textView.setTextColor(i);
        }
    }

    public BcSmartspaceCardShoppingList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mListItems = new TextView[3];
    }
}
