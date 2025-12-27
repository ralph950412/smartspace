package com.google.android.systemui.smartspace;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.app.smartspace.uitemplatedata.TapAction;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.wm.shell.R;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLogger;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.Map;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class BcSmartSpaceUtil {
    public static final Map FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP;
    public static FalsingManager sFalsingManager;

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartSpaceUtil$1, reason: invalid class name */
    public final class AnonymousClass1 implements RemoteViews.InteractionHandler {
        public /* synthetic */ SmartspaceAction val$action;
        public /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier val$eventNotifier;
        public /* synthetic */ BcSmartspaceCardLoggingInfo val$loggingInfo;
        public /* synthetic */ SmartspaceTarget val$target;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            BcSmartspaceDataPlugin.IntentStarter intentStarter = BcSmartSpaceUtil.getIntentStarter(this.val$eventNotifier, "BcSmartspaceRemoteViewsCard");
            if (pendingIntent != null) {
                BcSmartspaceCardLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, this.val$loggingInfo);
                BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = this.val$eventNotifier;
                if (smartspaceEventNotifier != null) {
                    smartspaceEventNotifier.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(this.val$target).setSmartspaceActionId(this.val$action.getId()).build());
                }
                intentStarter.startPendingIntent(view, pendingIntent, false);
            }
            return true;
        }
    }

    /* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
    /* renamed from: com.google.android.systemui.smartspace.BcSmartSpaceUtil$2, reason: invalid class name */
    public final class AnonymousClass2 implements BcSmartspaceDataPlugin.IntentStarter {
        public /* synthetic */ String val$tag;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
        public final void startIntent(View view, Intent intent, boolean z) {
            try {
                view.getContext().startActivity(intent);
            } catch (ActivityNotFoundException | NullPointerException | SecurityException e) {
                Log.e(this.val$tag, "Cannot invoke smartspace intent", e);
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
        public final void startPendingIntent(View view, PendingIntent pendingIntent, boolean z) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.e(this.val$tag, "Cannot invoke canceled smartspace intent", e);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        Integer valueOf = Integer.valueOf(R.layout.smartspace_card_generic_landscape_image);
        Integer valueOf2 = Integer.valueOf(R.layout.smartspace_card_doorbell);
        FEATURE_TYPE_TO_SECONDARY_CARD_RESOURCE_MAP = Map.ofEntries(Map.entry(-1, Integer.valueOf(R.layout.smartspace_card_combination)), Map.entry(-2, Integer.valueOf(R.layout.smartspace_card_combination_at_store)), Map.entry(3, valueOf), Map.entry(18, valueOf), Map.entry(4, Integer.valueOf(R.layout.smartspace_card_flight)), Map.entry(14, Integer.valueOf(R.layout.smartspace_card_loyalty)), Map.entry(13, Integer.valueOf(R.layout.smartspace_card_shopping_list)), Map.entry(9, Integer.valueOf(R.layout.smartspace_card_sports)), Map.entry(10, Integer.valueOf(R.layout.smartspace_card_weather_forecast)), Map.entry(30, valueOf2), Map.entry(20, valueOf2));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static String getDimensionRatio(Bundle bundle) {
        if (!bundle.containsKey("imageRatioWidth") || !bundle.containsKey("imageRatioHeight")) {
            return null;
        }
        int i = bundle.getInt("imageRatioWidth");
        int i2 = bundle.getInt("imageRatioHeight");
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        return i + ":" + i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int getFeatureType(SmartspaceTarget smartspaceTarget) {
        List actionChips = smartspaceTarget.getActionChips();
        int featureType = smartspaceTarget.getFeatureType();
        return (actionChips == null || actionChips.isEmpty()) ? featureType : (featureType == 13 && actionChips.size() == 1) ? -2 : -1;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static Drawable getIconDrawableWithCustomSize(Icon icon, Context context, int i) {
        if (icon == null) {
            return null;
        }
        Drawable bitmapDrawable = (icon.getType() == 1 || icon.getType() == 5) ? new BitmapDrawable(context.getResources(), icon.getBitmap()) : icon.loadDrawable(context);
        if (bitmapDrawable != null) {
            bitmapDrawable.setBounds(0, 0, i, i);
        }
        return bitmapDrawable;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static BcSmartspaceDataPlugin.IntentStarter getIntentStarter(BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str) {
        BcSmartspaceDataPlugin.IntentStarter intentStarter = smartspaceEventNotifier != null ? smartspaceEventNotifier.getIntentStarter() : null;
        if (intentStarter != null) {
            return intentStarter;
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        anonymousClass2.val$tag = str;
        VarHandle.storeStoreFence();
        return anonymousClass2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getLoggingDisplaySurface(String str, float f) {
        boolean z;
        if (str == null) {
            return 0;
        }
        str.hashCode();
        switch (str.hashCode()) {
            case 3208415:
                if (str.equals(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN)) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 95848451:
                if (str.equals(BcSmartspaceDataPlugin.UI_SURFACE_DREAM)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1792850263:
                if (str.equals(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD)) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case true:
                if (f != 1.0f) {
                    if (f == 0.0f) {
                    }
                }
                break;
        }
        return 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static Intent getOpenCalendarIntent() {
        return new Intent("android.intent.action.VIEW").setData(ContentUris.appendId(CalendarContract.CONTENT_URI.buildUpon().appendPath("time"), System.currentTimeMillis()).build()).addFlags(270532608);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setOnClickListener(View view, SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, int i) {
        if (view == null || smartspaceAction == null) {
            Log.e(str, "No tap action can be set up");
            return;
        }
        boolean z = false;
        boolean z2 = smartspaceAction.getExtras() != null && smartspaceAction.getExtras().getBoolean("show_on_lockscreen");
        if (smartspaceAction.getIntent() == null && smartspaceAction.getPendingIntent() == null) {
            z = true;
        }
        BcSmartspaceDataPlugin.IntentStarter intentStarter = getIntentStarter(smartspaceEventNotifier, str);
        BcSmartSpaceUtil$$ExternalSyntheticLambda1 bcSmartSpaceUtil$$ExternalSyntheticLambda1 = new BcSmartSpaceUtil$$ExternalSyntheticLambda1();
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$0 = bcSmartspaceCardLoggingInfo;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$1 = i;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$2 = z;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$3 = intentStarter;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$4 = smartspaceAction;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$5 = z2;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$7 = smartspaceEventNotifier;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$8 = str;
        bcSmartSpaceUtil$$ExternalSyntheticLambda1.f$9 = smartspaceTarget;
        VarHandle.storeStoreFence();
        view.setOnClickListener(bcSmartSpaceUtil$$ExternalSyntheticLambda1);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setOnClickListener$1(View view, SmartspaceTarget smartspaceTarget, TapAction tapAction, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, int i) {
        if (view == null || tapAction == null) {
            Log.e(str, "No tap action can be set up");
            return;
        }
        boolean shouldShowOnLockscreen = tapAction.shouldShowOnLockscreen();
        BcSmartSpaceUtil$$ExternalSyntheticLambda0 bcSmartSpaceUtil$$ExternalSyntheticLambda0 = new BcSmartSpaceUtil$$ExternalSyntheticLambda0();
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$0 = bcSmartspaceCardLoggingInfo;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$1 = i;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$2 = smartspaceEventNotifier;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$3 = str;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$4 = tapAction;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$5 = shouldShowOnLockscreen;
        bcSmartSpaceUtil$$ExternalSyntheticLambda0.f$7 = smartspaceTarget;
        VarHandle.storeStoreFence();
        view.setOnClickListener(bcSmartSpaceUtil$$ExternalSyntheticLambda0);
    }
}
