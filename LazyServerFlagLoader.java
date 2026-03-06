package com.google.android.systemui.smartspace;

import android.provider.DeviceConfig;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import java.lang.invoke.VarHandle;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class LazyServerFlagLoader {
    public final String mPropertyKey;
    public Boolean mValue = null;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public LazyServerFlagLoader(String str) {
        this.mPropertyKey = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean get() {
        if (this.mValue == null) {
            this.mValue = Boolean.valueOf(DeviceConfig.getBoolean("launcher", this.mPropertyKey, true));
            ProfileInstallReceiver$$ExternalSyntheticLambda0 profileInstallReceiver$$ExternalSyntheticLambda0 = new ProfileInstallReceiver$$ExternalSyntheticLambda0();
            LazyServerFlagLoader$$ExternalSyntheticLambda0 lazyServerFlagLoader$$ExternalSyntheticLambda0 = new LazyServerFlagLoader$$ExternalSyntheticLambda0();
            lazyServerFlagLoader$$ExternalSyntheticLambda0.f$0 = this;
            VarHandle.storeStoreFence();
            DeviceConfig.addOnPropertiesChangedListener("launcher", profileInstallReceiver$$ExternalSyntheticLambda0, lazyServerFlagLoader$$ExternalSyntheticLambda0);
        }
        return this.mValue.booleanValue();
    }
}
