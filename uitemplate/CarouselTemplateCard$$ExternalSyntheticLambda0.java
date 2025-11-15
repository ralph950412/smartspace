package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.uitemplatedata.CarouselTemplateData;
import java.util.function.Predicate;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CarouselTemplateCard$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CarouselTemplateData.CarouselItem carouselItem = (CarouselTemplateData.CarouselItem) obj;
        int i = CarouselTemplateCard.$r8$clinit;
        return (carouselItem.getImage() == null || carouselItem.getLowerText() == null || carouselItem.getUpperText() == null) ? false : true;
    }
}
