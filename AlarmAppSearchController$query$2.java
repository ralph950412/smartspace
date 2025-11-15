package com.google.android.systemui.smartspace;

import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.SearchSpec;
import android.os.Bundle;
import android.util.Log;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.builtintypes.Alarm;
import androidx.appsearch.builtintypes.AlarmInstance;
import androidx.appsearch.platformstorage.GlobalSearchSessionImpl;
import androidx.appsearch.platformstorage.SearchResultsImpl;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter$ApiHelperForB;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter$ApiHelperForU;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter$ApiHelperForV;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
final class AlarmAppSearchController$query$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AlarmAppSearchController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlarmAppSearchController$query$2(AlarmAppSearchController alarmAppSearchController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = alarmAppSearchController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AlarmAppSearchController$query$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlarmAppSearchController$query$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.this$0.searchSession == null) {
            Log.w("AlarmAppSearchCtlr", "Session is not initialized yet!");
            return new AnonymousClass1();
        }
        SearchSpec.Builder builder = new SearchSpec.Builder();
        builder.mSchemas = new ArrayList();
        builder.mNamespaces = new ArrayList();
        builder.mTypePropertyFilters = new Bundle();
        builder.mPackageNames = new ArrayList();
        ArraySet arraySet = new ArraySet(0);
        builder.mProjectionTypePropertyMasks = new Bundle();
        builder.mTypePropertyWeights = new Bundle();
        builder.mEmbeddingParameters = new ArrayList();
        builder.mSearchStringParameters = new ArrayList();
        builder.mFilterDocumentIds = new ArrayList();
        builder.mResultCountPerPage = 10;
        builder.mInformationalRankingExpressions = new ArrayList();
        builder.mBuilt = false;
        builder.resetIfBuilt();
        List asList = Arrays.asList("com.google.android.deskclock");
        asList.getClass();
        builder.resetIfBuilt();
        builder.mPackageNames.addAll(asList);
        builder.addFilterDocumentClasses(Alarm.class);
        builder.addFilterDocumentClasses(AlarmInstance.class);
        Preconditions.checkArgumentInRange("resultCountPerPage", 10, 0, 10000);
        builder.resetIfBuilt();
        builder.mResultCountPerPage = 10;
        if (!builder.mTypePropertyWeights.isEmpty()) {
            throw new IllegalArgumentException("Property weights are only compatible with the RANKING_STRATEGY_RELEVANCE_SCORE and RANKING_STRATEGY_ADVANCED_RANKING_EXPRESSION ranking strategies.");
        }
        builder.mBuilt = true;
        SearchSpec searchSpec = new SearchSpec(builder.mSchemas, builder.mNamespaces, builder.mTypePropertyFilters, builder.mPackageNames, builder.mResultCountPerPage, builder.mProjectionTypePropertyMasks, builder.mTypePropertyWeights, new ArrayList(arraySet), builder.mEmbeddingParameters, builder.mInformationalRankingExpressions, builder.mSearchStringParameters, builder.mFilterDocumentIds);
        GlobalSearchSessionImpl globalSearchSessionImpl = this.this$0.searchSession;
        globalSearchSessionImpl.getClass();
        GlobalSearchSession globalSearchSession = globalSearchSessionImpl.mPlatformSession;
        globalSearchSessionImpl.mContext.getClass();
        SearchSpec.Builder builder2 = new SearchSpec.Builder();
        String str = searchSpec.mAdvancedRankingExpression;
        if (str.isEmpty()) {
            builder2.setRankingStrategy(0);
        } else {
            SearchSpecToPlatformConverter$ApiHelperForU.setRankingStrategy(builder2, str);
        }
        SearchSpec.Builder termMatch = builder2.setTermMatch(2);
        List list = searchSpec.mSchemas;
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        SearchSpec.Builder addFilterSchemas = termMatch.addFilterSchemas(list);
        List list2 = searchSpec.mNamespaces;
        if (list2 == null) {
            list2 = Collections.EMPTY_LIST;
        }
        SearchSpec.Builder addFilterNamespaces = addFilterSchemas.addFilterNamespaces(list2);
        List list3 = searchSpec.mPackageNames;
        if (list3 == null) {
            list3 = Collections.EMPTY_LIST;
        }
        addFilterNamespaces.addFilterPackageNames(list3).setResultCountPerPage(searchSpec.mResultCountPerPage).setOrder(0).setSnippetCount(0).setSnippetCountPerProperty(searchSpec.mSnippetCountPerProperty).setMaxSnippetSize(0);
        Iterator it = ((ArrayMap.EntrySet) searchSpec.getProjections().entrySet()).iterator();
        while (true) {
            ArrayMap.MapIterator mapIterator = (ArrayMap.MapIterator) it;
            if (!mapIterator.hasNext()) {
                break;
            }
            mapIterator.next();
            ArrayMap.MapIterator mapIterator2 = mapIterator;
            builder2.addProjection((String) mapIterator2.getKey(), (Collection) mapIterator2.getValue());
        }
        if (!searchSpec.getPropertyWeights().isEmpty()) {
            SearchSpecToPlatformConverter$ApiHelperForU.setPropertyWeights(builder2, searchSpec.getPropertyWeights());
        }
        if (!searchSpec.mEnabledFeatures.isEmpty()) {
            if (searchSpec.mEnabledFeatures.contains("NUMERIC_SEARCH") || searchSpec.mEnabledFeatures.contains("VERBATIM_SEARCH") || searchSpec.mEnabledFeatures.contains("LIST_FILTER_QUERY_LANGUAGE")) {
                SearchSpecToPlatformConverter$ApiHelperForU.copyEnabledFeatures(builder2, searchSpec);
            }
            if (searchSpec.mEnabledFeatures.contains("LIST_FILTER_HAS_PROPERTY_FUNCTION")) {
                SearchSpecToPlatformConverter$ApiHelperForV.copyEnabledFeatures(builder2, searchSpec);
            }
            if (searchSpec.mEnabledFeatures.contains("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION")) {
                throw new UnsupportedOperationException("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION is not available on this AppSearch implementation.");
            }
        }
        if (!searchSpec.mEmbeddingParameters.isEmpty()) {
            SearchSpecToPlatformConverter$ApiHelperForB.addEmbeddingParameters(builder2, searchSpec.mEmbeddingParameters);
            SearchSpecToPlatformConverter$ApiHelperForB.setDefaultEmbeddingSearchMetricType(builder2, searchSpec.mDefaultEmbeddingSearchMetricType);
        }
        if (!searchSpec.mSearchStringParameters.isEmpty()) {
            throw new UnsupportedOperationException("SEARCH_SPEC_SEARCH_STRING_PARAMETERS is not available on this AppSearch implementation.");
        }
        if (!searchSpec.getFilterProperties().isEmpty()) {
            SearchSpecToPlatformConverter$ApiHelperForV.addFilterProperties(builder2, searchSpec.getFilterProperties());
        }
        if (!searchSpec.mInformationalRankingExpressions.isEmpty()) {
            SearchSpecToPlatformConverter$ApiHelperForB.addInformationalRankingExpressions(builder2, searchSpec.mInformationalRankingExpressions);
        }
        if (!searchSpec.mFilterDocumentIds.isEmpty()) {
            throw new UnsupportedOperationException("SEARCH_SPEC_ADD_FILTER_DOCUMENT_IDS is not available on this AppSearch implementation.");
        }
        if (searchSpec.mEnabledFeatures.contains("SCHEMA_SCORABLE_PROPERTY_CONFIG")) {
            throw new UnsupportedOperationException("SCHEMA_SCORABLE_PROPERTY_CONFIG is not available on this AppSearch implementation.");
        }
        return new SearchResultsImpl(globalSearchSession.search("", builder2.build()), searchSpec, globalSearchSessionImpl.mExecutor, globalSearchSessionImpl.mContext);
    }

    /* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
    /* renamed from: com.google.android.systemui.smartspace.AlarmAppSearchController$query$2$1, reason: invalid class name */
    public final class AnonymousClass1 implements SearchResults {
        @Override // androidx.appsearch.app.SearchResults
        public final ListenableFuture getNextPageAsync() {
            return Futures.immediateFuture(new ArrayList());
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
        }
    }
}
