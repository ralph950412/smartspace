package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.UUID;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
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
