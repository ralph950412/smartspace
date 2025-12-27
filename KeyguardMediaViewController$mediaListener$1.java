package com.google.android.systemui.smartspace;

import android.media.MediaMetadata;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.lang.invoke.VarHandle;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewController$mediaListener$1 implements NotificationMediaManager.MediaListener {
    public /* synthetic */ KeyguardMediaViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.media.NotificationMediaManager.MediaListener
    public final void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        DelayableExecutor delayableExecutor = keyguardMediaViewController.uiExecutor;
        KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 keyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 = new KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1();
        keyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1.this$0 = keyguardMediaViewController;
        keyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1.$metadata = mediaMetadata;
        keyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1.$state = i;
        VarHandle.storeStoreFence();
        ((ExecutorImpl) delayableExecutor).execute(keyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1);
    }
}
