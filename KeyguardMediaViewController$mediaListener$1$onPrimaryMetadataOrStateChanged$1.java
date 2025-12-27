package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.media.MediaMetadata;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.wm.shell.R;
import kotlin.Unit;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 implements Runnable {
    public /* synthetic */ MediaMetadata $metadata;
    public /* synthetic */ int $state;
    public /* synthetic */ KeyguardMediaViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        CharSequence charSequence;
        Unit unit;
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        MediaMetadata mediaMetadata = this.$metadata;
        if (!NotificationMediaManager.isPlayingState(this.$state)) {
            keyguardMediaViewController.title = null;
            keyguardMediaViewController.artist = null;
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView = keyguardMediaViewController.smartspaceView;
            if (smartspaceView != null) {
                smartspaceView.setMediaTarget(null);
                return;
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
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = keyguardMediaViewController.smartspaceView;
            if (smartspaceView2 != null) {
                smartspaceView2.setMediaTarget(build);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit != null) {
                return;
            }
        }
        keyguardMediaViewController.title = null;
        keyguardMediaViewController.artist = null;
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView3 = keyguardMediaViewController.smartspaceView;
        if (smartspaceView3 != null) {
            smartspaceView3.setMediaTarget(null);
        }
    }
}
