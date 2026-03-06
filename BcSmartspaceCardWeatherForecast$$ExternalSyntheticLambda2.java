package com.google.android.systemui.smartspace;

import android.view.View;
import android.widget.TextView;
import com.google.android.systemui.smartspace.BcSmartspaceCardWeatherForecast;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2 implements BcSmartspaceCardWeatherForecast.ItemUpdateFunction {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ int f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardWeatherForecast.ItemUpdateFunction
    public final void update(View view, int i) {
        int i2 = this.$r8$classId;
        int i3 = this.f$0;
        switch (i2) {
            case 0:
                int i4 = BcSmartspaceCardWeatherForecast.$r8$clinit;
                ((TextView) view).setTextColor(i3);
                break;
            default:
                int i5 = BcSmartspaceCardWeatherForecast.$r8$clinit;
                ((TextView) view).setTextColor(i3);
                break;
        }
    }
}
