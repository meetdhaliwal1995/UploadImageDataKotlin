package testproject.kotlin.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import testproject.kotlin.MyApp
import testproject.kotlin.NetworkInterface
import java.io.File
import javax.security.auth.callback.Callback

class GetDataRepo {

    val getAllCategories: MutableLiveData<List<CategoriesItem>> = MutableLiveData()
    val imageUpload: MutableLiveData<UploadImage> = MutableLiveData()

    fun getCategoriesImage(): MutableLiveData<List<CategoriesItem>> {
        val list = MutableLiveData<List<CategoriesItem>>()

        val networkInterface = MyApp.getRetrofit().create(NetworkInterface::class.java)
        networkInterface.getCategories("xttest/get_categories.php").enqueue(object : Callback,
            retrofit2.Callback<GetCategories> {
            override fun onFailure(call: Call<GetCategories>, t: Throwable) {
                Log.e("Failed", t.localizedMessage);
            }

            override fun onResponse(call: Call<GetCategories>, response: Response<GetCategories>) {
                getAllCategories.value = response.body()?.categories
            }
        })

        return list
    }

    fun uplaodData(
        id: String,
        nAme: String,
        dEsc: String,
        eXpiry: String,
        pRoduct_image: File
    ): MutableLiveData<UploadImage> {
        val upload = MutableLiveData<UploadImage>()

        val requestBodyImage: RequestBody =
            RequestBody.create("*/*".toMediaTypeOrNull(), pRoduct_image)

        val category_id = MultipartBody.Part.createFormData("category_id", id)
        val name = MultipartBody.Part.createFormData("name", nAme)
        val desc = MultipartBody.Part.createFormData("desc", dEsc)
        val expiry = MultipartBody.Part.createFormData("expiry", eXpiry)
        val product_image =
            MultipartBody.Part.createFormData(
                "product_image[0]",
                pRoduct_image.name,
                requestBodyImage
            )

        val networkInterface = MyApp.getRetrofit().create(NetworkInterface::class.java)
        networkInterface.imageUpload(
            "xttest/save_user.php",
            category_id,
            name,
            desc,
            expiry,
            product_image
        ).enqueue(object : retrofit2.Callback<UploadImage> {
            override fun onResponse(call: Call<UploadImage>, response: Response<UploadImage>) {
                Log.e("response", "pic upload")
                imageUpload.value = response.body()
            }

            override fun onFailure(call: Call<UploadImage>, t: Throwable) {
                Log.e("failure", "pic upload")

            }
        })
        return upload
    }
}