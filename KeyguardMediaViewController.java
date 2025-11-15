package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaMetadata;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.wm.shell.R;
import kotlin.Unit;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewController {
    public CharSequence artist;
    public final Context context;
    public final ComponentName mediaComponent;
    public final KeyguardMediaViewController$mediaListener$1 mediaListener = new NotificationMediaManager.MediaListener() { // from class: com.google.android.systemui.smartspace.KeyguardMediaViewController$mediaListener$1
        @Override // com.android.systemui.media.NotificationMediaManager.MediaListener
        public final void onPrimaryMetadataOrStateChanged(final MediaMetadata mediaMetadata, final int i) {
            final KeyguardMediaViewController keyguardMediaViewController = KeyguardMediaViewController.this;
            ((ExecutorImpl) keyguardMediaViewController.uiExecutor).execute(new Runnable() { // from class: com.google.android.systemui.smartspace.KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    CharSequence charSequence;
                    Unit unit;
                    KeyguardMediaViewController keyguardMediaViewController2 = KeyguardMediaViewController.this;
                    MediaMetadata mediaMetadata2 = mediaMetadata;
                    if (!NotificationMediaManager.isPlayingState(i)) {
                        keyguardMediaViewController2.title = null;
                        keyguardMediaViewController2.artist = null;
                        BcSmartspaceDataPlugin.SmartspaceView smartspaceView = keyguardMediaViewController2.smartspaceView;
                        if (smartspaceView != null) {
                            smartspaceView.setMediaTarget(null);
                            return;
                        }
                        return;
                    }
                    if (mediaMetadata2 != null) {
                        charSequence = mediaMetadata2.getText("android.media.metadata.DISPLAY_TITLE");
                        if (TextUtils.isEmpty(charSequence)) {
                            charSequence = mediaMetadata2.getText("android.media.metadata.TITLE");
                        }
                        if (TextUtils.isEmpty(charSequence)) {
                            charSequence = keyguardMediaViewController2.context.getResources().getString(R.string.music_controls_no_title);
                        }
                    } else {
                        charSequence = null;
                    }
                    CharSequence text = mediaMetadata2 != null ? mediaMetadata2.getText("android.media.metadata.ARTIST") : null;
                    if (TextUtils.equals(keyguardMediaViewController2.title, charSequence) && TextUtils.equals(keyguardMediaViewController2.artist, text)) {
                        return;
                    }
                    keyguardMediaViewController2.title = charSequence;
                    keyguardMediaViewController2.artist = text;
                    if (charSequence != null) {
                        SmartspaceTarget build = new SmartspaceTarget.Builder("deviceMedia", keyguardMediaViewController2.mediaComponent, UserHandle.of(((UserTrackerImpl) keyguardMediaViewController2.userTracker).getUserId())).setFeatureType(41).setHeaderAction(new SmartspaceAction.Builder("deviceMediaTitle", charSequence.toString()).setSubtitle(keyguardMediaViewController2.artist).setIcon(keyguardMediaViewController2.mediaManager.getMediaIcon()).build()).build();
                        BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = keyguardMediaViewController2.smartspaceView;
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
                    keyguardMediaViewController2.title = null;
                    keyguardMediaViewController2.artist = null;
                    BcSmartspaceDataPlugin.SmartspaceView smartspaceView3 = keyguardMediaViewController2.smartspaceView;
                    if (smartspaceView3 != null) {
                        smartspaceView3.setMediaTarget(null);
                    }
                }
            });
        }
    };
    public final NotificationMediaManager mediaManager;
    public final BcSmartspaceDataPlugin plugin;
    public BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    public CharSequence title;
    public final DelayableExecutor uiExecutor;
    public final UserTracker userTracker;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.google.android.systemui.smartspace.KeyguardMediaViewController$mediaListener$1] */
    public KeyguardMediaViewController(Context context, UserTracker userTracker, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, DelayableExecutor delayableExecutor, NotificationMediaManager notificationMediaManager) {
        this.context = context;
        this.userTracker = userTracker;
        this.plugin = bcSmartspaceDataPlugin;
        this.uiExecutor = delayableExecutor;
        this.mediaManager = notificationMediaManager;
        this.mediaComponent = new ComponentName(context, (Class<?>) KeyguardMediaViewController.class);
    }

    public static /* synthetic */ void getSmartspaceView$annotations() {
    }
}
