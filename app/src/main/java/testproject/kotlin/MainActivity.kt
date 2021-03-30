package testproject.kotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import testproject.kotlin.Data.GetDataViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var getDataViewModel: GetDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getDataViewModel = ViewModelProvider(this).get(GetDataViewModel::class.java)
        getDataViewModel.fetchAllCategories()
        getDataViewModel.realAllCategories.observe(this, Observer { items ->
            val _list = ArrayList<String>()

            items.forEach {
                _list.add(it.name)
            }

            val adapter: ArrayAdapter<String> = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                _list
            )

            spinner.adapter = adapter
        })
    }
}