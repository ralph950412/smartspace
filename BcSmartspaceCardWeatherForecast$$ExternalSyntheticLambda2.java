package com.google.android.systemui.smartspace;

import android.view.View;
import android.widget.TextView;
import com.google.android.systemui.smartspace.BcSmartspaceCardWeatherForecast;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BcSmartspaceCardWeatherForecast$$ExternalSyntheticLambda2 implements BcSmartspaceCardWeatherForecast.ItemUpdateFunction {
    public final /* synthetic */ int $r8$classId;
    public /* synthetic */ int f$0;

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
