package com.google.android.systemui.smartspace.logging;

import androidx.collection.IntIntPair$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Objects;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class BcSmartspaceSubcardLoggingInfo {
    public int mClickedSubcardIndex;
    public List mSubcards;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BcSmartspaceSubcardLoggingInfo)) {
            return false;
        }
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = (BcSmartspaceSubcardLoggingInfo) obj;
        return this.mClickedSubcardIndex == bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex && Objects.equals(this.mSubcards, bcSmartspaceSubcardLoggingInfo.mSubcards);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return Objects.hash(this.mSubcards, Integer.valueOf(this.mClickedSubcardIndex));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        StringBuilder sb = new StringBuilder("BcSmartspaceSubcardLoggingInfo{mSubcards=");
        sb.append(this.mSubcards);
        sb.append(", mClickedSubcardIndex=");
        return IntIntPair$$ExternalSyntheticOutline0.m(sb, this.mClickedSubcardIndex, '}');
    }
}
