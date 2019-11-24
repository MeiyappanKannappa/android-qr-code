package com.esastech.dynamicshuttle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import java.io.File

class MainActivityZxing : AppCompatActivity() {

    private var detector: BarcodeDetector? = null
    private val LOG_TAG = "Barcode Scanner API"
    private val PHOTO_REQUEST = 10
    private var scanResults: TextView? = null
    private var decode: TextView? = null
    private var imageUri: Uri? = null
    private val SAVED_INSTANCE_URI = "uri"
    private val SAVED_INSTANCE_RESULT = "result"
    private var currImagePath: String? = null
    internal var imageFile: File? = null

    val REQUEST_WRITE_PERMISSION: Int = 2;
    val BARCODE_READER_REQUEST_CODE: Int = 3

    internal var qrScanIntegrator: IntentIntegrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var continuebutton = findViewById(R.id.button) as Button
        scanResults = findViewById(R.id.textView) as TextView

        qrScanIntegrator = IntentIntegrator(this)


        continuebutton.setOnClickListener { view ->
            Toast.makeText(this, "Initializing App....", Toast.LENGTH_SHORT).show()
            qrScanIntegrator?.initiateScan()


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "No QR CODE", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {


                    // Show values in UI.
                    scanResults?.text = result.contents


                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}