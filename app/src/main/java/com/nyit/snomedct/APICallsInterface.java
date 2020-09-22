package com.nyit.snomedct;

import com.nyit.snomedct.AllInformation.AllInfo;
import com.nyit.snomedct.Atoms.Atoms;
import com.nyit.snomedct.ConceptResults.Concepts;
import com.nyit.snomedct.Relationship.Relations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICallsInterface {


    @GET("search/current?sabs=SNOMEDCT_US&returnIdType=code")
    Call<Concepts> getConcepts(@Query("string") String query,
                               @Query("ticket") String ticket);

    @GET("atoms")
    Call<Atoms> getAtoms(@Query("ticket") String ticket);

    @GET("relations?pageSize=100")
    Call<Relations> getRelations(@Query("ticket") String ticket);

    @GET("{id}")
    Call<AllInfo> getAllInfo(@Path("id") String id,
            @Query("ticket") String ticket);


}
