package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import java.util.UUID;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
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
