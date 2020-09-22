package com.nyit.snomedct;

import com.nyit.snomedct.AllInformation.AllInfo;
import com.nyit.snomedct.Atoms.Atoms;
import com.nyit.snomedct.ConceptResults.Concepts;
import com.nyit.snomedct.Relationship.Relations;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewAPICallsInterface {



    @GET("search/current?sabs=SNOMEDCT_US&returnIdType=code")
    Observable<Concepts> getConcepts(@Query("string") String query,
                                     @Query("ticket") String ticket);

    @GET("atoms")
    Observable<Atoms> getAtoms(@Query("ticket") String ticket);

    @GET("relations?pageSize=100")
    Observable<Relations> getRelations(@Query("ticket") String ticket);

    @GET("{id}")
    Observable<AllInfo> getAllInfo(@Path("id") String id,
                             @Query("ticket") String ticket);


}
