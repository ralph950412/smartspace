package com.google.android.systemui.smartspace;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.systemui.smartspace.BcSmartspaceCardWeatherForecast;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda0 implements BcSmartspaceCardWeatherForecast.ItemUpdateFunction {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ Object[] f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardWeatherForecast.ItemUpdateFunction
    public final void update(View view, int i) {
        int i2 = this.$r8$classId;
        Object[] objArr = this.f$0;
        switch (i2) {
            case 0:
                int i3 = BcSmartspaceCardWeatherForecast.$r8$clinit;
                ((TextView) view).setText(((String[]) objArr)[i]);
                break;
            case 1:
                int i4 = BcSmartspaceCardWeatherForecast.$r8$clinit;
                ((TextView) view).setText(((String[]) objArr)[i]);
                break;
            default:
                int i5 = BcSmartspaceCardWeatherForecast.$r8$clinit;
                ((ImageView) view).setImageBitmap(((Bitmap[]) objArr)[i]);
                break;
        }
    }
}
