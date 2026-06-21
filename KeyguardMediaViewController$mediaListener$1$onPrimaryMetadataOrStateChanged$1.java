package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.media.MediaMetadata;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.wm.shell.R;
import java.util.Iterator;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 implements Runnable {
    public /* synthetic */ MediaMetadata $metadata;
    public /* synthetic */ int $state;
    public /* synthetic */ KeyguardMediaViewController this$0;

    @Override // java.lang.Runnable
    public final void run() {
        CharSequence charSequence;
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        LockscreenSmartspaceController lockscreenSmartspaceController = keyguardMediaViewController.smartspaceController;
        MediaMetadata mediaMetadata = this.$metadata;
        if (!NotificationMediaManager.isPlayingState(this.$state)) {
            keyguardMediaViewController.title = null;
            keyguardMediaViewController.artist = null;
            lockscreenSmartspaceController.mediaTarget = null;
            Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
            while (it.hasNext()) {
                ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setMediaTarget(null);
            }
            return;
        }
        if (mediaMetadata != null) {
            charSequence = mediaMetadata.getText("android.media.metadata.DISPLAY_TITLE");
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = mediaMetadata.getText("android.media.metadata.TITLE");
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = keyguardMediaViewController.context.getResources().getString(R.string.music_controls_no_title);
            }
        } else {
            charSequence = null;
        }
        CharSequence text = mediaMetadata != null ? mediaMetadata.getText("android.media.metadata.ARTIST") : null;
        CharSequence charSequence2 = charSequence;
        if (TextUtils.equals(keyguardMediaViewController.title, charSequence2) && TextUtils.equals(keyguardMediaViewController.artist, text)) {
            return;
        }
        keyguardMediaViewController.title = charSequence2;
        keyguardMediaViewController.artist = text;
        if (charSequence2 != null) {
            SmartspaceTarget build = new SmartspaceTarget.Builder("deviceMedia", keyguardMediaViewController.mediaComponent, UserHandle.of(((UserTrackerImpl) keyguardMediaViewController.userTracker).getUserId())).setFeatureType(41).setHeaderAction(new SmartspaceAction.Builder("deviceMediaTitle", charSequence2.toString()).setSubtitle(keyguardMediaViewController.artist).setIcon(keyguardMediaViewController.mediaManager.getMediaIcon()).build()).build();
            lockscreenSmartspaceController.mediaTarget = build;
            Iterator it2 = lockscreenSmartspaceController.smartspaceViews.iterator();
            while (it2.hasNext()) {
                ((BcSmartspaceDataPlugin.SmartspaceView) it2.next()).setMediaTarget(build);
            }
            return;
        }
        keyguardMediaViewController.title = null;
        keyguardMediaViewController.artist = null;
        lockscreenSmartspaceController.mediaTarget = null;
        Iterator it3 = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it3.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it3.next()).setMediaTarget(null);
        }
    }
}
