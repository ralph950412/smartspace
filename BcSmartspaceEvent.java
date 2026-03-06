package com.google.android.systemui.smartspace;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    BcSmartspaceEvent(int i) {
        this.mId = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int getId() {
        return this.mId;
    }
}
