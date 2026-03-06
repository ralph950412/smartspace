package com.google.android.systemui.smartspace.logging;

import androidx.collection.IntIntPair$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class BcSmartspaceCardMetadataLoggingInfo {
    public int mCardTypeId;
    public int mInstanceId;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BcSmartspaceCardMetadataLoggingInfo)) {
            return false;
        }
        BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = (BcSmartspaceCardMetadataLoggingInfo) obj;
        return this.mInstanceId == bcSmartspaceCardMetadataLoggingInfo.mInstanceId && this.mCardTypeId == bcSmartspaceCardMetadataLoggingInfo.mCardTypeId;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mInstanceId), Integer.valueOf(this.mCardTypeId));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        StringBuilder sb = new StringBuilder("BcSmartspaceCardMetadataLoggingInfo{mInstanceId=");
        sb.append(this.mInstanceId);
        sb.append(", mCardTypeId=");
        return IntIntPair$$ExternalSyntheticOutline0.m(sb, this.mCardTypeId, '}');
    }
}
