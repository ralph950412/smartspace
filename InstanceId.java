package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.UUID;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public abstract class InstanceId {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int create(SmartspaceTarget smartspaceTarget) {
        if (smartspaceTarget == null) {
            return SmallHash.hash(UUID.randomUUID().toString());
        }
        String smartspaceTargetId = smartspaceTarget.getSmartspaceTargetId();
        return (smartspaceTargetId == null || smartspaceTargetId.isEmpty()) ? SmallHash.hash(String.valueOf(smartspaceTarget.getCreationTimeMillis())) : SmallHash.hash(smartspaceTargetId);
    }

    public static int create(String str) {
        if (str != null && !str.isEmpty()) {
            return SmallHash.hash(str);
        }
        return SmallHash.hash(UUID.randomUUID().toString());
    }
}
