package com.google.android.systemui.smartspace;

import android.provider.DeviceConfig;
import java.util.Set;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class LazyServerFlagLoader$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public /* synthetic */ LazyServerFlagLoader f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        LazyServerFlagLoader lazyServerFlagLoader = this.f$0;
        Set keyset = properties.getKeyset();
        String str = lazyServerFlagLoader.mPropertyKey;
        if (keyset.contains(str)) {
            lazyServerFlagLoader.mValue = Boolean.valueOf(properties.getBoolean(str, true));
        }
    }
}
