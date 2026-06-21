package com.google.android.systemui.smartspace.logging;

import androidx.collection.IntIntPair$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Objects;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class BcSmartspaceSubcardLoggingInfo {
    public int mClickedSubcardIndex;
    public List mSubcards;

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

    public final int hashCode() {
        return Objects.hash(this.mSubcards, Integer.valueOf(this.mClickedSubcardIndex));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BcSmartspaceSubcardLoggingInfo{mSubcards=");
        sb.append(this.mSubcards);
        sb.append(", mClickedSubcardIndex=");
        return IntIntPair$$ExternalSyntheticOutline0.m(sb, this.mClickedSubcardIndex, '}');
    }
}
