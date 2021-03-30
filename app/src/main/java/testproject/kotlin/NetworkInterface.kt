package testproject.kotlin

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import testproject.kotlin.Data.GetCategories

interface NetworkInterface {

    @POST("{endpoint}")
    @FormUrlEncoded
    fun getCategories(
        @Path(value = "endpoint", encoded = true) endpoint: String
    ): Call<GetCategories>
}