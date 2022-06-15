package com.example.permissionstest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private var runAfterRequestWriteExternalStoragePermissions: (() -> Unit)? = null
    private val writeExternalStoragePermissionsRequired = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val requestWriteExternalStoragePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val runAfter = runAfterRequestWriteExternalStoragePermissions
        runAfterRequestWriteExternalStoragePermissions = null
        if (permissions.containsValue(false)) return@registerForActivityResult
        runAfter?.invoke()
    }

    private fun requestWriteExternalStoragePermissions(runAfter: () -> Unit) {
        Log.i(TAG, "+requestWriteExternalStoragePermissions(...)")
        writeExternalStoragePermissionsRequired.forEach {
            val permission = ContextCompat.checkSelfPermission(this, it)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                runAfterRequestWriteExternalStoragePermissions = runAfter
                if (shouldShowRequestPermissionRationale(it)) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Permission(s) Required")
                        setMessage("Please allow the required permission(s)")
                        setPositiveButton("OK") { _, _ ->
                            requestWriteExternalStoragePermissions.launch(
                                writeExternalStoragePermissionsRequired
                            )
                        }
                        setNegativeButton("Cancel", null)
                    }.show()
                } else {
                    Log.i(TAG, "requestWriteExternalStoragePermissions: requestWriteExternalStoragePermissions.launch($writeExternalStoragePermissionsRequired)")
                    requestWriteExternalStoragePermissions.launch(
                        writeExternalStoragePermissionsRequired
                    )
                }
                Log.i(TAG, "-requestWriteExternalStoragePermissions(...)")
                return
            }
        }
        runAfter()
        Log.i(TAG, "-requestWriteExternalStoragePermissions(...)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "+onCreate(...)")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestWriteExternalStoragePermissions {
            Log.i(TAG, "Huzzah!")
        }
        Log.v(TAG, "-onCreate(...)")
    }

    override fun onStart() {
        Log.v(TAG, "+onStart()")
        super.onStart()
        Log.v(TAG, "-onStart()")
    }

    override fun onResume() {
        Log.v(TAG, "+onResume()")
        super.onResume()
        Log.v(TAG, "-onResume()")
    }

    override fun onPause() {
        Log.v(TAG, "+onPause()")
        super.onPause()
        Log.v(TAG, "-onPause()")
    }

    override fun onStop() {
        Log.v(TAG, "+onStop()")
        super.onStop()
        Log.v(TAG, "-onStop()")
    }

    override fun onDestroy() {
        Log.v(TAG, "+onDestroy()")
        super.onDestroy()
        Log.v(TAG, "-onDestroy()")
    }
}