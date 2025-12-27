package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import com.google.android.systemui.smartspace.CardPagerAdapter;
import java.util.List;
import java.util.function.IntPredicate;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class CardPagerAdapter$Companion$useRecycledViewForActionsList$1 implements IntPredicate {
    public /* synthetic */ List $newActionsList;
    public /* synthetic */ List $recycledActionsList;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        return CardPagerAdapter.Companion.useRecycledViewForAction((SmartspaceAction) this.$newActionsList.get(i), (SmartspaceAction) this.$recycledActionsList.get(i));
    }
}
