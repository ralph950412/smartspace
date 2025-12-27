package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.lang.invoke.VarHandle;
import java.util.Locale;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public class BcSmartspaceCardWeatherForecast extends BcSmartspaceCardSecondary {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    public interface ItemUpdateFunction {
        void update(View view, int i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BcSmartspaceCardWeatherForecast(Context context) {
        super(context);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ConstraintLayout[] constraintLayoutArr = new ConstraintLayout[4];
        for (int i = 0; i < 4; i++) {
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewGroup.inflate(getContext(), R.layout.smartspace_card_weather_forecast_column, null);
            constraintLayout.setId(View.generateViewId());
            constraintLayoutArr[i] = constraintLayout;
        }
        int i2 = 0;
        while (i2 < 4) {
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, 0);
            ConstraintLayout constraintLayout2 = constraintLayoutArr[i2];
            ConstraintLayout constraintLayout3 = i2 > 0 ? constraintLayoutArr[i2 - 1] : null;
            ConstraintLayout constraintLayout4 = i2 < 3 ? constraintLayoutArr[i2 + 1] : null;
            if (i2 == 0) {
                layoutParams.startToStart = 0;
                layoutParams.horizontalChainStyle = 1;
            } else {
                layoutParams.startToEnd = constraintLayout3.getId();
            }
            if (i2 == 3) {
                layoutParams.endToEnd = 0;
            } else {
                layoutParams.endToStart = constraintLayout4.getId();
            }
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            addView(constraintLayout2, layoutParams);
            i2++;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        boolean z;
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Bundle extras = baseAction == null ? null : baseAction.getExtras();
        if (extras == null) {
            return false;
        }
        if (extras.containsKey("temperatureValues")) {
            String[] stringArray = extras.getStringArray("temperatureValues");
            if (stringArray == null) {
                Log.w("BcSmartspaceCardWeatherForecast", "Temperature values array is null.");
            } else {
                BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0 bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0 = new BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0(1);
                bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0.f$0 = stringArray;
                VarHandle.storeStoreFence();
                updateFields(bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0, stringArray.length, R.id.temperature_value, "temperature value");
            }
            z = true;
        } else {
            z = false;
        }
        if (extras.containsKey("weatherIcons")) {
            Bitmap[] bitmapArr = (Bitmap[]) extras.get("weatherIcons");
            if (bitmapArr == null) {
                Log.w("BcSmartspaceCardWeatherForecast", "Weather icons array is null.");
            } else {
                BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0 bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda02 = new BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0(2);
                bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda02.f$0 = bitmapArr;
                VarHandle.storeStoreFence();
                updateFields(bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda02, bitmapArr.length, R.id.weather_icon, "weather icon");
            }
            z = true;
        }
        if (!extras.containsKey("timestamps")) {
            return z;
        }
        String[] stringArray2 = extras.getStringArray("timestamps");
        if (stringArray2 == null) {
            Log.w("BcSmartspaceCardWeatherForecast", "Timestamps array is null.");
            return true;
        }
        BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0 bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda03 = new BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0(0);
        bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda03.f$0 = stringArray2;
        VarHandle.storeStoreFence();
        updateFields(bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda03, stringArray2.length, R.id.timestamp, "timestamp");
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public final void setTextColor(int i) {
        BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2 bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2 = new BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2(0);
        bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2.f$0 = i;
        VarHandle.storeStoreFence();
        updateFields(bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2, 4, R.id.temperature_value, "temperature value");
        BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2 bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda22 = new BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2(1);
        bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda22.f$0 = i;
        VarHandle.storeStoreFence();
        updateFields(bcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda22, 4, R.id.timestamp, "timestamp");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateFields(ItemUpdateFunction itemUpdateFunction, int i, int i2, String str) {
        if (getChildCount() < 4) {
            Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Missing %d ", str, " view(s) to update."), Integer.valueOf(4 - getChildCount())));
            return;
        }
        if (i < 4) {
            int i3 = 4 - i;
            Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Missing %d ", str, "(s). Hiding incomplete columns."), Integer.valueOf(i3)));
            if (getChildCount() < 4) {
                Log.w("BcSmartspaceCardWeatherForecast", "Missing " + (4 - getChildCount()) + " columns to update.");
            } else {
                int i4 = 3 - i3;
                int i5 = 0;
                while (i5 < 4) {
                    BcSmartspaceTemplateDataUtils.updateVisibility(getChildAt(i5), i5 <= i4 ? 0 : 8);
                    i5++;
                }
                ((ConstraintLayout.LayoutParams) ((ConstraintLayout) getChildAt(0)).getLayoutParams()).horizontalChainStyle = i3 == 0 ? 1 : 0;
            }
        }
        int min = Math.min(4, i);
        for (int i6 = 0; i6 < min; i6++) {
            View findViewById = getChildAt(i6).findViewById(i2);
            if (findViewById == null) {
                Log.w("BcSmartspaceCardWeatherForecast", String.format(Locale.US, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Missing ", str, " view to update at column: %d."), Integer.valueOf(i6 + 1)));
                return;
            }
            itemUpdateFunction.update(findViewById, i6);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public BcSmartspaceCardWeatherForecast(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
