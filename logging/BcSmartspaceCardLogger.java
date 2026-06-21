package com.google.android.systemui.smartspace.logging;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceCardMetadata;
import com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceSubcards;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.google.android.systemui.smartspace.BcSmartSpaceUtil;
import com.google.android.systemui.smartspace.BcSmartspaceEvent;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public abstract class BcSmartspaceCardLogger {
    static {
        FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
    }

    /* JADX DEBUG: Class process forced to load method for inline: com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceCardMetadata.-$$Nest$msetInstanceId(com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceCardMetadata, int):void */
    /* JADX DEBUG: Class process forced to load method for inline: com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceSubcards.-$$Nest$msetClickedSubcardIndex(com.android.systemui.smartspace.SmartspaceProtoLite$SmartSpaceSubcards, int):void */
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
                SmartspaceProtoLite$SmartSpaceCardMetadata.m1496$$Nest$msetInstanceId((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.instance, i2);
                int i3 = bcSmartspaceCardMetadataLoggingInfo.mCardTypeId;
                newBuilder.copyOnWrite();
                SmartspaceProtoLite$SmartSpaceCardMetadata.m1495$$Nest$msetCardTypeId((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.instance, i3);
                arrayList.add((SmartspaceProtoLite$SmartSpaceCardMetadata) newBuilder.build());
                i++;
            }
            SmartspaceProtoLite$SmartSpaceSubcards.Builder newBuilder2 = SmartspaceProtoLite$SmartSpaceSubcards.newBuilder();
            int i4 = bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex;
            newBuilder2.copyOnWrite();
            SmartspaceProtoLite$SmartSpaceSubcards.m1498$$Nest$msetClickedSubcardIndex((SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.instance, i4);
            newBuilder2.copyOnWrite();
            SmartspaceProtoLite$SmartSpaceSubcards.m1497$$Nest$maddAllSubcards((SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.instance, arrayList);
            SmartspaceProtoLite$SmartSpaceSubcards smartspaceProtoLite$SmartSpaceSubcards = (SmartspaceProtoLite$SmartSpaceSubcards) newBuilder2.build();
            try {
                bArr = new byte[smartspaceProtoLite$SmartSpaceSubcards.getSerializedSize(null)];
                CodedOutputStream.ArrayEncoder newInstance = CodedOutputStream.newInstance(bArr);
                smartspaceProtoLite$SmartSpaceSubcards.writeTo(newInstance);
                if (newInstance.spaceLeft() != 0) {
                    throw new IllegalStateException("Did not write as much data as expected.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Serializing " + SmartspaceProtoLite$SmartSpaceSubcards.class.getName() + " to a byte array threw an IOException (should never happen).", e);
            }
        }
        SmartspaceProto$SmartspaceCardDimensionalInfo smartspaceProto$SmartspaceCardDimensionalInfo = bcSmartspaceCardLoggingInfo.mDimensionalInfo;
        byte[] byteArray = smartspaceProto$SmartspaceCardDimensionalInfo != null ? MessageNano.toByteArray(smartspaceProto$SmartspaceCardDimensionalInfo) : null;
        int id = bcSmartspaceEvent.getId();
        int i5 = bcSmartspaceCardLoggingInfo.mInstanceId;
        int i6 = bcSmartspaceCardLoggingInfo.mDisplaySurface;
        int i7 = bcSmartspaceCardLoggingInfo.mRank;
        int i8 = bcSmartspaceCardLoggingInfo.mCardinality;
        int i9 = bcSmartspaceCardLoggingInfo.mFeatureType;
        int i10 = bcSmartspaceCardLoggingInfo.mUid;
        int i11 = bcSmartspaceCardLoggingInfo.mReceivedLatency;
        StatsEvent.Builder newBuilder3 = StatsEvent.newBuilder();
        newBuilder3.setAtomId(352);
        newBuilder3.writeInt(id);
        newBuilder3.writeInt(i5);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(i6);
        newBuilder3.writeInt(i7);
        newBuilder3.writeInt(i8);
        newBuilder3.writeInt(i9);
        newBuilder3.writeInt(i10);
        newBuilder3.addBooleanAnnotation((byte) 1, true);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(0);
        newBuilder3.writeInt(i11);
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
