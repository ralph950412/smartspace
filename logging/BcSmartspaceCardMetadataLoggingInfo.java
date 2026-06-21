package com.google.android.systemui.smartspace.logging;

import androidx.collection.IntIntPair$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class BcSmartspaceCardMetadataLoggingInfo {
    public int mCardTypeId;
    public int mInstanceId;

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

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mInstanceId), Integer.valueOf(this.mCardTypeId));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BcSmartspaceCardMetadataLoggingInfo{mInstanceId=");
        sb.append(this.mInstanceId);
        sb.append(", mCardTypeId=");
        return IntIntPair$$ExternalSyntheticOutline0.m(sb, this.mCardTypeId, '}');
    }
}
