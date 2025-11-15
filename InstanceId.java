package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.UUID;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public abstract class InstanceId {
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
