package com.google.android.systemui.smartspace;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public enum BcSmartspaceEvent {
    /* JADX INFO: Fake field, exist only in values array */
    IGNORE(-1),
    SMARTSPACE_CARD_RECEIVED(759),
    SMARTSPACE_CARD_CLICK(760),
    /* JADX INFO: Fake field, exist only in values array */
    SMARTSPACE_CARD_DISMISS(761),
    SMARTSPACE_CARD_SEEN(800),
    /* JADX INFO: Fake field, exist only in values array */
    ENABLED_SMARTSPACE(822),
    /* JADX INFO: Fake field, exist only in values array */
    DISABLED_SMARTSPACE(823),
    SMARTSPACE_CARD_SWIPE(1960);

    private final int mId;

    BcSmartspaceEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
