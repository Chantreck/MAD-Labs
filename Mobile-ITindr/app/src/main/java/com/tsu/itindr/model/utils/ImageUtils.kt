package com.tsu.itindr.model.utils

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tsu.itindr.BuildConfig
import com.tsu.itindr.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

object ImageUtils {
    fun getImageBody(context: Context, uri: Uri): RequestBody {
        val bitmap = getImageBitmap(context, uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val image = byteArrayOutputStream.toByteArray()
        return image.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun getImageBitmap(context: Context, uri: Uri): Bitmap {
        val stream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(stream)
    }

    fun getTempFileUri(activity: AppCompatActivity): Uri {
        val tempFile = File.createTempFile("tmp_image_file", ".jpg", activity.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(activity.applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tempFile)
    }

    fun getPicture(
        activity: AppCompatActivity,
        checkCameraPermission: ActivityResultLauncher<String>,
        chooseImageContract: ActivityResultLauncher<String>,
    ) {
        val items = with(activity.resources) {
            arrayOf(getString(R.string.camera), getString(R.string.gallery))
        }
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.dialog_title)
            .setItems(items) { dialog, which ->
                dialog.cancel()
                when (which) {
                    0 -> checkCameraPermission.launch(Manifest.permission.CAMERA)
                    1 -> chooseImageContract.launch("image/*")
                }
            }
            .setCancelable(true)
            .show()
    }
}