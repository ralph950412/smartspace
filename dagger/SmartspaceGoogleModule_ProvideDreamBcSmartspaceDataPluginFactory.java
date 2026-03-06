package com.google.android.systemui.smartspace.dagger;

import com.google.android.systemui.smartspace.BcSmartspaceDataProvider;
import dagger.internal.Provider;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public abstract class SmartspaceGoogleModule_ProvideDreamBcSmartspaceDataPluginFactory implements Provider {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static BcSmartspaceDataProvider provideDreamBcSmartspaceDataPlugin() {
        return new BcSmartspaceDataProvider();
    }
}
