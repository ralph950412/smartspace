package com.google.android.systemui.smartspace.logging;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Objects;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
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
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.mClickedSubcardIndex, '}');
    }
}
