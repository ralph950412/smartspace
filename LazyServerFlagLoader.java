package com.google.android.systemui.smartspace;

import android.provider.DeviceConfig;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import java.util.Set;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class LazyServerFlagLoader {
    public final String mPropertyKey;
    public Boolean mValue = null;

    public LazyServerFlagLoader(String str) {
        this.mPropertyKey = str;
    }

    public final boolean get() {
        if (this.mValue == null) {
            this.mValue = Boolean.valueOf(DeviceConfig.getBoolean("launcher", this.mPropertyKey, true));
            DeviceConfig.addOnPropertiesChangedListener("launcher", new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.google.android.systemui.smartspace.LazyServerFlagLoader$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    LazyServerFlagLoader lazyServerFlagLoader = LazyServerFlagLoader.this;
                    Set keyset = properties.getKeyset();
                    String str = lazyServerFlagLoader.mPropertyKey;
                    if (keyset.contains(str)) {
                        lazyServerFlagLoader.mValue = Boolean.valueOf(properties.getBoolean(str, true));
                    }
                }
            });
        }
        return this.mValue.booleanValue();
    }
}
