package com.google.android.systemui.smartspace.uitemplate;

import android.app.smartspace.uitemplatedata.CarouselTemplateData;
import java.util.function.Predicate;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final /* synthetic */ class CarouselTemplateCard$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CarouselTemplateData.CarouselItem carouselItem = (CarouselTemplateData.CarouselItem) obj;
        int i = CarouselTemplateCard.$r8$clinit;
        return (carouselItem.getImage() == null || carouselItem.getLowerText() == null || carouselItem.getUpperText() == null) ? false : true;
    }
}
