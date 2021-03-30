package testproject.kotlin.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class GetDataViewModel : ViewModel() {

    var realAllCategories: MutableLiveData<List<CategoriesItem>>

    private var repo: GetDataRepo = GetDataRepo()

    init {
        realAllCategories = repo.getAllCategories
    }

    fun fetchAllCategories() {
        repo.getCategoriesImage()
    }

    fun uploadImage(cat_id: String , nAme: String , dEsc:String , eXpiry : String ,productImage: File){
        repo.uplaodData(cat_id,nAme,dEsc,eXpiry,productImage)
    }
}