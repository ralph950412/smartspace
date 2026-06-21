package com.google.android.systemui.smartspace;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
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
