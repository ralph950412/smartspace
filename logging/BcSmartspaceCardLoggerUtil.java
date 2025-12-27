package com.google.android.systemui.smartspace.logging;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.uitemplatedata.BaseTemplateData;
import android.os.Bundle;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceCardDimensionalInfo;
import com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceFeatureDimension;
import com.google.android.systemui.smartspace.InstanceId;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class BcSmartspaceCardLoggerUtil {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static boolean containsValidTemplateType(BaseTemplateData baseTemplateData) {
        return (baseTemplateData == null || baseTemplateData.getTemplateType() == 0 || baseTemplateData.getTemplateType() == 8) ? false : true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static SmartspaceProto$SmartspaceCardDimensionalInfo createDimensionalLoggingInfo(BaseTemplateData baseTemplateData) {
        if (baseTemplateData == null || baseTemplateData.getPrimaryItem() == null || baseTemplateData.getPrimaryItem().getTapAction() == null) {
            return null;
        }
        Bundle extras = baseTemplateData.getPrimaryItem().getTapAction().getExtras();
        ArrayList arrayList = new ArrayList();
        if (extras != null && !extras.isEmpty()) {
            ArrayList<Integer> integerArrayList = extras.getIntegerArrayList("ss_card_dimension_ids");
            ArrayList<Integer> integerArrayList2 = extras.getIntegerArrayList("ss_card_dimension_values");
            if (integerArrayList != null && integerArrayList2 != null && integerArrayList.size() == integerArrayList2.size()) {
                for (int i = 0; i < integerArrayList.size(); i++) {
                    SmartspaceProto$SmartspaceFeatureDimension smartspaceProto$SmartspaceFeatureDimension = new SmartspaceProto$SmartspaceFeatureDimension();
                    smartspaceProto$SmartspaceFeatureDimension.featureDimensionId = integerArrayList.get(i).intValue();
                    smartspaceProto$SmartspaceFeatureDimension.featureDimensionValue = integerArrayList2.get(i).intValue();
                    arrayList.add(smartspaceProto$SmartspaceFeatureDimension);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        SmartspaceProto$SmartspaceCardDimensionalInfo smartspaceProto$SmartspaceCardDimensionalInfo = new SmartspaceProto$SmartspaceCardDimensionalInfo();
        smartspaceProto$SmartspaceCardDimensionalInfo.featureDimensions = (SmartspaceProto$SmartspaceFeatureDimension[]) arrayList.toArray(new SmartspaceProto$SmartspaceFeatureDimension[arrayList.size()]);
        return smartspaceProto$SmartspaceCardDimensionalInfo;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo(SmartspaceTarget smartspaceTarget) {
        if (smartspaceTarget.getBaseAction() == null || smartspaceTarget.getBaseAction().getExtras() == null || smartspaceTarget.getBaseAction().getExtras().isEmpty() || smartspaceTarget.getBaseAction().getExtras().getInt("subcardType", -1) == -1) {
            return null;
        }
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        int create = InstanceId.create(baseAction.getExtras().getString("subcardId"));
        int i = baseAction.getExtras().getInt("subcardType");
        BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = new BcSmartspaceCardMetadataLoggingInfo();
        bcSmartspaceCardMetadataLoggingInfo.mInstanceId = create;
        bcSmartspaceCardMetadataLoggingInfo.mCardTypeId = i;
        VarHandle.storeStoreFence();
        ArrayList arrayList = new ArrayList();
        arrayList.add(bcSmartspaceCardMetadataLoggingInfo);
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = new BcSmartspaceSubcardLoggingInfo();
        bcSmartspaceSubcardLoggingInfo.mSubcards = arrayList;
        bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex = 0;
        return bcSmartspaceSubcardLoggingInfo;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void createSubcardLoggingInfoHelper(List list, BaseTemplateData.SubItemInfo subItemInfo) {
        if (subItemInfo == null || subItemInfo.getLoggingInfo() == null) {
            return;
        }
        BaseTemplateData.SubItemLoggingInfo loggingInfo = subItemInfo.getLoggingInfo();
        int featureType = loggingInfo.getFeatureType();
        int instanceId = loggingInfo.getInstanceId();
        BcSmartspaceCardMetadataLoggingInfo bcSmartspaceCardMetadataLoggingInfo = new BcSmartspaceCardMetadataLoggingInfo();
        bcSmartspaceCardMetadataLoggingInfo.mInstanceId = instanceId;
        bcSmartspaceCardMetadataLoggingInfo.mCardTypeId = featureType;
        VarHandle.storeStoreFence();
        list.add(bcSmartspaceCardMetadataLoggingInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void tryForcePrimaryFeatureTypeOrUpdateLogInfoFromTemplateData(BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, BaseTemplateData baseTemplateData) {
        if (bcSmartspaceCardLoggingInfo.mFeatureType == 1) {
            bcSmartspaceCardLoggingInfo.mFeatureType = 39;
            bcSmartspaceCardLoggingInfo.mInstanceId = InstanceId.create("date_card_794317_92634");
            return;
        }
        if (baseTemplateData == null || baseTemplateData.getPrimaryItem() == null || baseTemplateData.getPrimaryItem().getLoggingInfo() == null) {
            return;
        }
        int featureType = baseTemplateData.getPrimaryItem().getLoggingInfo().getFeatureType();
        if (featureType > 0) {
            bcSmartspaceCardLoggingInfo.mFeatureType = featureType;
        }
        int instanceId = baseTemplateData.getPrimaryItem().getLoggingInfo().getInstanceId();
        if (instanceId > 0) {
            bcSmartspaceCardLoggingInfo.mInstanceId = instanceId;
        }
    }

    public static BcSmartspaceSubcardLoggingInfo createSubcardLoggingInfo(BaseTemplateData baseTemplateData) {
        if (baseTemplateData == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (baseTemplateData.getPrimaryItem() != null && baseTemplateData.getPrimaryItem().getLoggingInfo() != null && baseTemplateData.getPrimaryItem().getLoggingInfo().getFeatureType() == 1) {
            createSubcardLoggingInfoHelper(arrayList, baseTemplateData.getPrimaryItem());
        }
        createSubcardLoggingInfoHelper(arrayList, baseTemplateData.getSubtitleItem());
        createSubcardLoggingInfoHelper(arrayList, baseTemplateData.getSubtitleSupplementalItem());
        createSubcardLoggingInfoHelper(arrayList, baseTemplateData.getSupplementalLineItem());
        if (arrayList.isEmpty()) {
            return null;
        }
        BcSmartspaceSubcardLoggingInfo bcSmartspaceSubcardLoggingInfo = new BcSmartspaceSubcardLoggingInfo();
        bcSmartspaceSubcardLoggingInfo.mSubcards = arrayList;
        bcSmartspaceSubcardLoggingInfo.mClickedSubcardIndex = 0;
        return bcSmartspaceSubcardLoggingInfo;
    }
}
