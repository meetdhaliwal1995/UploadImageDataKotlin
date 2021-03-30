package testproject.kotlin.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import testproject.kotlin.MyApp
import testproject.kotlin.NetworkInterface
import javax.security.auth.callback.Callback

class GetDataRepo {

    val getAllCategories: MutableLiveData<List<CategoriesItem>> = MutableLiveData()

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
}