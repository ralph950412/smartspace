package com.google.android.systemui.smartspace.logging;

import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import java.util.Objects;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class BcSmartspaceCardLoggingInfo {
    public int mCardinality;
    public SmartspaceProto$SmartspaceCardDimensionalInfo mDimensionalInfo;
    public int mDisplaySurface;
    public int mFeatureType;
    public int mInstanceId;
    public int mRank;
    public int mReceivedLatency;
    public BcSmartspaceSubcardLoggingInfo mSubcardInfo;
    public int mUid;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BcSmartspaceCardLoggingInfo)) {
            return false;
        }
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = (BcSmartspaceCardLoggingInfo) obj;
        return this.mInstanceId == bcSmartspaceCardLoggingInfo.mInstanceId && this.mDisplaySurface == bcSmartspaceCardLoggingInfo.mDisplaySurface && this.mRank == bcSmartspaceCardLoggingInfo.mRank && this.mCardinality == bcSmartspaceCardLoggingInfo.mCardinality && this.mFeatureType == bcSmartspaceCardLoggingInfo.mFeatureType && this.mReceivedLatency == bcSmartspaceCardLoggingInfo.mReceivedLatency && this.mUid == bcSmartspaceCardLoggingInfo.mUid && Objects.equals(this.mSubcardInfo, bcSmartspaceCardLoggingInfo.mSubcardInfo) && Objects.equals(this.mDimensionalInfo, bcSmartspaceCardLoggingInfo.mDimensionalInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mInstanceId), Integer.valueOf(this.mDisplaySurface), Integer.valueOf(this.mRank), Integer.valueOf(this.mCardinality), Integer.valueOf(this.mFeatureType), Integer.valueOf(this.mReceivedLatency), Integer.valueOf(this.mUid), this.mSubcardInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        return "instance_id = " + this.mInstanceId + ", feature type = " + this.mFeatureType + ", display surface = " + this.mDisplaySurface + ", rank = " + this.mRank + ", cardinality = " + this.mCardinality + ", receivedLatencyMillis = " + this.mReceivedLatency + ", uid = " + this.mUid + ", subcardInfo = " + this.mSubcardInfo + ", dimensionalInfo = " + this.mDimensionalInfo;
    }
}
