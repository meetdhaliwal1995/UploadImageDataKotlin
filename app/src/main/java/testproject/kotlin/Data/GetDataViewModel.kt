package testproject.kotlin.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class GetDataViewModel : ViewModel() {

    var realAllCategories: MutableLiveData<List<CategoriesItem>>
    var imageUploaded: MutableLiveData<UploadImage>

    private var repo: GetDataRepo = GetDataRepo()

    init {
        realAllCategories = repo.getAllCategories
        imageUploaded = repo.imageUpload
    }

    fun fetchAllCategories() {
        repo.getCategoriesImage()
    }

    fun uploadImage(catid: String, nAme: String, dEsc:String, eXpiry: String, productImage: ArrayList<File>
    ){
        repo.uplaodData(catid,nAme,dEsc,eXpiry,productImage)
    }
}