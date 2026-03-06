package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import com.google.android.systemui.smartspace.CardPagerAdapter;
import java.util.List;
import java.util.function.IntPredicate;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
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
