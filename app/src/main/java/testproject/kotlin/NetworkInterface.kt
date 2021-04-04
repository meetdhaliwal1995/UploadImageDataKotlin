package testproject.kotlin

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import testproject.kotlin.Data.GetCategories
import testproject.kotlin.Data.UploadImage

interface NetworkInterface {

    @GET("{endpoint}")
    fun getCategories(@Path(value = "endpoint", encoded = true) endpoint: String): Call<GetCategories>

    @Multipart
    @POST("{endpoint}")
    fun imageUpload(
        @Path(value = "endpoint" , encoded = true) endpoint: String,
        @Part("category_id") categoryId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("desc") description: RequestBody,
        @Part("expiry") expiry: RequestBody,
//        @Part category_id: MultipartBody.Part?,
//        @Part name: MultipartBody.Part?,
//        @Part desc: MultipartBody.Part?,
//        @Part expiry: MultipartBody.Part?,
        @Part product_image: List<MultipartBody.Part?>
    ): Call<UploadImage>
}