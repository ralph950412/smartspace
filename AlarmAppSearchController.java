package com.google.android.systemui.smartspace;

import android.util.Log;
import androidx.appsearch.platformstorage.GlobalSearchSessionImpl;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class AlarmAppSearchController {
    public static final boolean DEBUG = Log.isLoggable("AlarmAppSearchCtlr", 3);
    public final CoroutineDispatcher bgDispatcher;
    public final Executor mainExecutor;
    public GlobalSearchSessionImpl searchSession;

    public AlarmAppSearchController(Executor executor, CoroutineDispatcher coroutineDispatcher) {
        this.mainExecutor = executor;
        this.bgDispatcher = coroutineDispatcher;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|7|(1:(2:10|11)(2:19|20))(3:21|22|(1:24)(1:25))|12|(1:14)|16|17))|28|6|7|(0)(0)|12|(0)|16|17) */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0034, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0088, code lost:
    
        android.util.Log.e("AlarmAppSearchCtlr", "Failed to create session", r8);
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0076 A[Catch: all -> 0x0034, TRY_LEAVE, TryCatch #0 {all -> 0x0034, blocks: (B:11:0x0030, B:12:0x006e, B:14:0x0076, B:22:0x0041), top: B:7:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object createSearchSession(android.content.Context r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8 = this;
            java.lang.String r0 = "session created="
            boolean r1 = r10 instanceof com.google.android.systemui.smartspace.AlarmAppSearchController$createSearchSession$1
            if (r1 == 0) goto L16
            r1 = r10
            com.google.android.systemui.smartspace.AlarmAppSearchController$createSearchSession$1 r1 = (com.google.android.systemui.smartspace.AlarmAppSearchController$createSearchSession$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L16
            int r2 = r2 - r3
            r1.label = r2
            goto L1b
        L16:
            com.google.android.systemui.smartspace.AlarmAppSearchController$createSearchSession$1 r1 = new com.google.android.systemui.smartspace.AlarmAppSearchController$createSearchSession$1
            r1.<init>(r8, r10)
        L1b:
            java.lang.Object r10 = r1.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r3 = r1.label
            java.lang.String r4 = "AlarmAppSearchCtlr"
            r5 = 1
            if (r3 == 0) goto L3e
            if (r3 != r5) goto L36
            java.lang.Object r8 = r1.L$1
            com.google.android.systemui.smartspace.AlarmAppSearchController r8 = (com.google.android.systemui.smartspace.AlarmAppSearchController) r8
            java.lang.Object r9 = r1.L$0
            com.google.android.systemui.smartspace.AlarmAppSearchController r9 = (com.google.android.systemui.smartspace.AlarmAppSearchController) r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L34
            goto L6e
        L34:
            r8 = move-exception
            goto L88
        L36:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3e:
            kotlin.ResultKt.throwOnFailure(r10)
            r9.getClass()     // Catch: java.lang.Throwable -> L34
            java.util.concurrent.Executor r10 = androidx.appsearch.platformstorage.PlatformStorage.EXECUTOR     // Catch: java.lang.Throwable -> L34
            androidx.appsearch.platformstorage.PlatformStorage$GlobalSearchContext r3 = new androidx.appsearch.platformstorage.PlatformStorage$GlobalSearchContext     // Catch: java.lang.Throwable -> L34
            r3.<init>(r9, r10)     // Catch: java.lang.Throwable -> L34
            java.lang.Class<android.app.appsearch.AppSearchManager> r6 = android.app.appsearch.AppSearchManager.class
            java.lang.Object r9 = r9.getSystemService(r6)     // Catch: java.lang.Throwable -> L34
            android.app.appsearch.AppSearchManager r9 = (android.app.appsearch.AppSearchManager) r9     // Catch: java.lang.Throwable -> L34
            androidx.concurrent.futures.ResolvableFuture r6 = new androidx.concurrent.futures.ResolvableFuture     // Catch: java.lang.Throwable -> L34
            r6.<init>()     // Catch: java.lang.Throwable -> L34
            androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda0 r7 = new androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L34
            r7.<init>()     // Catch: java.lang.Throwable -> L34
            r9.createGlobalSearchSession(r10, r7)     // Catch: java.lang.Throwable -> L34
            r1.L$0 = r8     // Catch: java.lang.Throwable -> L34
            r1.L$1 = r8     // Catch: java.lang.Throwable -> L34
            r1.label = r5     // Catch: java.lang.Throwable -> L34
            java.lang.Object r10 = androidx.concurrent.futures.ListenableFutureKt.await(r6, r1)     // Catch: java.lang.Throwable -> L34
            if (r10 != r2) goto L6d
            return r2
        L6d:
            r9 = r8
        L6e:
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl r10 = (androidx.appsearch.platformstorage.GlobalSearchSessionImpl) r10     // Catch: java.lang.Throwable -> L34
            r8.searchSession = r10     // Catch: java.lang.Throwable -> L34
            boolean r8 = com.google.android.systemui.smartspace.AlarmAppSearchController.DEBUG     // Catch: java.lang.Throwable -> L34
            if (r8 == 0) goto L8d
            androidx.appsearch.platformstorage.GlobalSearchSessionImpl r8 = r9.searchSession     // Catch: java.lang.Throwable -> L34
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L34
            r9.append(r8)     // Catch: java.lang.Throwable -> L34
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Throwable -> L34
            android.util.Log.d(r4, r8)     // Catch: java.lang.Throwable -> L34
            goto L8d
        L88:
            java.lang.String r9 = "Failed to create session"
            android.util.Log.e(r4, r9, r8)
        L8d:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.AlarmAppSearchController.createSearchSession(android.content.Context, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getNextPageSearchResults(androidx.appsearch.app.SearchResults r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.google.android.systemui.smartspace.AlarmAppSearchController$getNextPageSearchResults$1
            if (r0 == 0) goto L13
            r0 = r6
            com.google.android.systemui.smartspace.AlarmAppSearchController$getNextPageSearchResults$1 r0 = (com.google.android.systemui.smartspace.AlarmAppSearchController$getNextPageSearchResults$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.google.android.systemui.smartspace.AlarmAppSearchController$getNextPageSearchResults$1 r0 = new com.google.android.systemui.smartspace.AlarmAppSearchController$getNextPageSearchResults$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L2f
            if (r1 != r2) goto L27
            kotlin.ResultKt.throwOnFailure(r4)
            return r4
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r4)
            com.google.common.util.concurrent.ListenableFuture r4 = r5.getNextPageAsync()
            r0.label = r2
            java.lang.Object r4 = androidx.concurrent.futures.ListenableFutureKt.await(r4, r0)
            if (r4 != r6) goto L3f
            return r6
        L3f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.AlarmAppSearchController.getNextPageSearchResults(androidx.appsearch.app.SearchResults, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object query(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.google.android.systemui.smartspace.AlarmAppSearchController$query$1
            if (r0 == 0) goto L13
            r0 = r5
            com.google.android.systemui.smartspace.AlarmAppSearchController$query$1 r0 = (com.google.android.systemui.smartspace.AlarmAppSearchController$query$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.google.android.systemui.smartspace.AlarmAppSearchController$query$1 r0 = new com.google.android.systemui.smartspace.AlarmAppSearchController$query$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            return r5
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.google.android.systemui.smartspace.AlarmAppSearchController$query$2 r5 = new com.google.android.systemui.smartspace.AlarmAppSearchController$query$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.bgDispatcher
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.AlarmAppSearchController.query(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final String toString() {
        return "AlarmAppSearchController { searchSession=" + this.searchSession + " }";
    }
}
