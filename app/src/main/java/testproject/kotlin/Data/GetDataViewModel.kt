package testproject.kotlin.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GetDataViewModel : ViewModel() {

    var realAllCategories: MutableLiveData<List<CategoriesItem>>

    private var repo: GetDataRepo = GetDataRepo()

    init {
        realAllCategories = repo.getAllCategories
    }

    fun fetchAllCategories() {
        repo.getCategoriesImage()
    }
}