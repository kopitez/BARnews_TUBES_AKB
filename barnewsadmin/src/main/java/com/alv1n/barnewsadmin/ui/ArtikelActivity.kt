package com.alv1n.barnewsadmin.ui

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.database.BuildConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.alv1n.barnewsadmin.MainActivity
import com.alv1n.barnewsadmin.R
import com.alv1n.barnewsadmin.data.Artikel
import com.alv1n.barnewsadmin.data.ArtikelDb
import com.alv1n.barnewsadmin.databinding.ActivityArtikelBinding
import com.alv1n.barnewsadmin.utils.Constant
import com.alv1n.barnewsadmin.utils.LoadUtilBitmap
import java.io.FileNotFoundException
import java.util.*

class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding

    private var selectedImageBitmap: Bitmap? = null
    private var uriUpload: Uri? = null
    private var urlDownload: String? = null
    private var imgChange: Boolean = false
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private val viewModel: ArtikelViewModel by lazy {
        val factory = ArtikelViewModelFactory(ArtikelDb.getInstance())
        ViewModelProvider(this, factory).get(ArtikelViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            elevation = 0f
            title = "Tambah Artikel"
            setDisplayHomeAsUpEnabled(true)
        }
        initUI()
    }

    private fun initUI() {
        binding.btnSumbit.setOnClickListener {
            val title = binding.etTitleArticle.text
            val content = binding.etContentArticle.text
            if (title.isEmpty()) {
                showMessage("Judul artikel haru diisi!")
            } else if (content.isEmpty()) {
                showMessage("Isi artikel harus diisi!")
            } else {
                if (imgChange) {
                    uploadImage()
                } else {
                    insertData()
                }
            }
        }
        binding.btnUploadImage.setOnClickListener {
            if (!checkPermissionExternalStorage(this)) {
                showMessage("Tidak mendapat akses ke galeri.")
            } else {
                pickFormGallery()
            }
        }
    }

    private fun pickFormGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFormGallery()
                } else {
                    Toast.makeText(this, "Belum Dapat Akses ke Galeri", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == android.app.Activity.RESULT_OK) when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                val imageSelectedUri: Uri? = data!!.data
                setupImage(imageSelectedUri!!)
                val uriUploadColum = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor? =
                    contentResolver.query(imageSelectedUri, uriUploadColum, null, null, null)

                if (cursor != null) {
                    cursor.moveToFirst()
                    if (Build.VERSION.SDK_INT >= 29) {
                        val imageUri: Uri? = data.data
                        try {
                            if (BuildConfig.DEBUG && imageUri == null) {
                                error("Permintaan Gagal")
                            }
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }

                        setupImage(imageUri!!)

                    } else {
                        cursor.close()
                        setupImage(imageSelectedUri)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val selectImage: Uri? = data.data
                        try {
                            if (BuildConfig.DEBUG && selectImage == null) {
                                error("Permintaan Gagal")
                            }
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        setupImage(selectImage!!)
                    } else {
                        LoadUtilBitmap.loadBitmap(selectedImageBitmap.toString())?.apply {
                            setupImage(imageSelectedUri)
                        }
                    }

                }
            }
        }
    }

    private fun setupImage(uri: Uri) {
        Glide.with(this).load(uri).into(binding.imgArticle)
        uriUpload = uri
        imgChange = true
    }

    //Check Permission External Storage
    private fun checkPermissionExternalStorage(context: Context): Boolean {
        val currentApi = Build.VERSION.SDK_INT
        if (currentApi >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as android.app.Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    showDialog(
                        "External Storage",
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        GALLERY_REQUEST_CODE
                    )
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    private fun showDialog(msg: String, activity: android.app.Activity, permission: String) {
        val alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("$msg permission is necessary")
        alertBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                GALLERY_REQUEST_CODE
            )
        }
        alertBuilder.create().show()
    }

    private fun uploadImage() {
        if (uriUpload != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val ref = storageReference.child("artikel/" + UUID.randomUUID().toString())

            ref.putFile(uriUpload!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        if (uri != null) {
                            urlDownload = uri.toString()
                            insertDataWithImg(urlDownload!!)
                        }
                    }

                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    showMessage(e.message!!)
                }
                .addOnProgressListener {
                    val progress = (100.0 * it.bytesTransferred
                            / it.totalByteCount)
                    progressDialog.setMessage(String.format("Uploaded %.2f", progress) + "%")
                }
        }
    }

    private fun insertData() {
        val key = database.child(Constant.DB_ARTICLE).push().key!!
        val title = binding.etTitleArticle.text
        val content = binding.etContentArticle.text
        var image =
            "https://firebasestorage.googleapis.com/v0/b/peduli-rumah-yatim.appspot.com/o/img%2Factivity%2Fpengajian2.jpg?alt=media&token=f60a0a75-7cc3-42d9-8cb8-7f07dd9565b9"
        val date = "Juli"
        val source = "BARNews"

        val data = Artikel(
            key,
            title.toString(),
            content.toString(),
            image,
            date,
            source
        )

        viewModel.insertData(data)
        showMessage("Tambah Artikel Berhasil!")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun insertDataWithImg(uri: String) {
        val key = database.child(Constant.DB_ARTICLE).push().key!!
        val title = binding.etTitleArticle.text
        val content = binding.etContentArticle.text
        var image = uri
        val date = "Juli"
        val source = "BARNews"

        val data = Artikel(
            key,
            title.toString(),
            content.toString(),
            image.toString(),
            date,
            source
        )

        viewModel.insertData(data)
        showMessage("Tambah Artikel berhasil!")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showMessage(messageResId: String) {
        val toast = Toast.makeText(
            this, messageResId,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }

    companion object {
        const val GALLERY_REQUEST_CODE = 9002
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}