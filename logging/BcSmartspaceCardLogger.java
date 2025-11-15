package com.google.android.systemui.smartspace.logging;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceCardMetadata;
import com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceSubcards;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceEvent;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.List;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public abstract class BcSmartspaceCardLogger {
    static {
        BcSmartspaceDataPlugin.IntentStarter intentStarter = BcSmartSpaceUtil.sIntentStarter;
    }

    public static void log(BcSmartspaceEvent bcSmartspaceEvent, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        byte[] bArr;
        List list;
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = bcSmartspaceCardLoggingInfo.mSubcardInfo;
        if (bcSmartspaceSubcardLoggingInfo == null || (list = bcSmartspaceSubcardLoggingInfo.mSubcards) == null || list.isEmpty()) {
            bArr = null;
        } else {
            ArrayList arrayList = new ArrayList();
            List list2 = bcSmartspaceSubcardLoggingInfo.mSubcards;
            int i = 0;
            while (true) {
                ArrayList arrayList2 = (ArrayList) list2;
                if (i >= arrayList2.size()) {
                    break;
                }
                BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = (BcSmartspaceCardMetadataLoggingInfo) arrayList2.get(i);
                SmartspaceProtoLite$SmartSpaceCardMetadata.Builder newBuilder = SmartspaceProtoLite$SmartSpaceCardMetadata.newBuilder();
                int i2 = bcSmartspaceCardMetadataLoggingInfo.mInstanceId;
                newBuilder.copyOnWrite();
                SmartspaceProtoLite$SmartSpaceCardMetadata.m1093$$Nest$msetInstanceId((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.instance, i2);
                newBuilder.copyOnWrite();
                SmartspaceProtoLite$SmartSpaceCardMetadata.m1092$$Nest$msetCardTypeId((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.instance, bcSmartspaceCardMetadataLoggingInfo.mCardTypeId);
                arrayList.add((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.build());
                i++;
            }
            SmartspaceProtoLite$SmartSpaceSubcards.Builder newBuilder2 = SmartspaceProtoLite$SmartSpaceSubcards.newBuilder();
            int i3 = bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex;
            newBuilder2.copyOnWrite();
            SmartspaceProtoLite$SmartSpaceSubcards.m1095$$Nest$msetClickedSubcardIndex((SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.instance, i3);
            newBuilder2.copyOnWrite();
            SmartspaceProtoLite$SmartSpaceSubcards.m1094$$Nest$maddAllSubcards((SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.instance, arrayList);
            bArr = ((SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.build()).toByteArray();
        }
        SmartspaceProto$SmartspaceCardDimensionalInfo smartspaceProto$SmartspaceCardDimensionalInfo = bcSmartspaceCardLoggingInfo.mDimensionalInfo;
        byte[] byteArray = smartspaceProto$SmartspaceCardDimensionalInfo != null ? MessageNano.toByteArray(smartspaceProto$SmartspaceCardDimensionalInfo) : null;
        int id = bcSmartspaceEvent.getId();
        int i4 = bcSmartspaceCardLoggingInfo.mInstanceId;
        int i5 = bcSmartspaceCardLoggingInfo.mFeatureType;
        StatsEvent.Builder newBuilder3 = StatsEvent.newBuilder();
        newBuilder3.setAtomId(352);
        newBuilder3.writeInt(id);
        newBuilder3.writeInt(i4);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(bcSmartspaceCardLoggingInfo.mDisplaySurface);
        newBuilder3.writeInt(bcSmartspaceCardLoggingInfo.mRank);
        newBuilder3.writeInt(bcSmartspaceCardLoggingInfo.mCardinality);
        newBuilder3.writeInt(i5);
        newBuilder3.writeInt(bcSmartspaceCardLoggingInfo.mUid);
        newBuilder3.addBooleanAnnotation((byte) 1, true);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(bcSmartspaceCardLoggingInfo.mReceivedLatency);
        if (bArr == null) {
            bArr = new byte[0];
        }
        newBuilder3.writeByteArray(bArr);
        if (byteArray == null) {
            byteArray = new byte[0];
        }
        newBuilder3.writeByteArray(byteArray);
        newBuilder3.usePooledBuffer();
        StatsLog.write(newBuilder3.build());
    }
}
