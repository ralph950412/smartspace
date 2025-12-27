package com.google.android.systemui.smartspace;

import android.provider.DeviceConfig;
import java.util.Set;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
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
