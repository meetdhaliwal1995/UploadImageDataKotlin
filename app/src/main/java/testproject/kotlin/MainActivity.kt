package testproject.kotlin

import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import testproject.kotlin.Data.GetDataViewModel

class MainActivity : AppCompatActivity(), AdapterImage.ItemCallBack {

    private lateinit var getDataViewModel: GetDataViewModel
    private lateinit var imageAadpter: AdapterImage
    private val REQUEST_PERMISSION = 1001
    private val OPEN_GALLERY = 1002
    private var selectedCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageAadpter = AdapterImage(this, arrayListOf(), this)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = imageAadpter

        getDataViewModel = ViewModelProvider(this).get(GetDataViewModel::class.java)
        getDataViewModel.fetchAllCategories()
        getDataViewModel.realAllCategories.observe(this, Observer { items ->
            val _list = ArrayList<String>()

            items.forEach {
                _list.add(it.name)
            }

            selectedCategory = _list[0]

            val adapter: ArrayAdapter<String> = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                _list
            )

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCategory = _list[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedCategory = _list[0]
                }
            }
        })

        add_imz.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                val permissions = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OPEN_GALLERY) {
            if (data?.clipData != null) {
                val list = arrayListOf<String>()

                val counts = data.clipData?.itemCount!!

                for (i in 0 until counts) {
                    list.add(data.clipData?.getItemAt(i)?.uri.toString())
                    Log.e("MULTIPLE", data.clipData?.getItemAt(i)?.uri.toString())
                }

                updateAdapter(list)
            } else {
                Log.e("SINGLE", data?.data.toString())
                updateAdapter(mutableListOf(data?.data.toString()) as ArrayList<String>)
            }
        }
    }

    private fun updateAdapter(_list: ArrayList<String>) {
        imageAadpter.updateMultipleData(_list)
    }

    override fun onRemoved(item: String) {

    }
}