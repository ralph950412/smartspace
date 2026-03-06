package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.uitemplatedata.CarouselTemplateData;
import java.util.function.Predicate;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class CarouselTemplateCard$$ExternalSyntheticLambda0 implements Predicate {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CarouselTemplateData.CarouselItem carouselItem = (CarouselTemplateData.CarouselItem) obj;
        int i = CarouselTemplateCard.$r8$clinit;
        return (carouselItem.getImage() == null || carouselItem.getLowerText() == null || carouselItem.getUpperText() == null) ? false : true;
    }
}
