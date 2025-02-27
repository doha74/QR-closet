package com.example.qrcloset

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.Manifest
import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.qrcloset.liste.Scanned
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class QrScannerFragment : Fragment() {

    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private var isDialogShown = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        barcodeScannerView = view.findViewById(R.id.barcode_scanner)
        barcodeScannerView.decodeContinuous(callback)
        // Inflate the layout for this fragment
        return view
    }

    private val callback = BarcodeCallback { result ->
        result.text?.let { qrCode ->

            if (!isDialogShown) { // Only show dialog if not already shown
                isDialogShown = true

                Scanned.listeScanned.add(generateQRCode(qrCode, 600))

                // Inflate bottom sheet dialog layout
                val dialog = BottomSheetDialog(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

                // Set QR code text in the TextView
                val idAllDescription = dialogView.findViewById<TextView>(R.id.idAllDescription)
                idAllDescription.text = qrCode

                // Set up close button
                val btnClose = dialogView.findViewById<Button>(R.id.idBtnDismiss)
                btnClose.setOnClickListener {
                    dialog.dismiss()
                }

                // Handle dialog dismissal
                dialog.setOnDismissListener {
                    isDialogShown = false // Reset flag when dialog is dismissed
                }

                dialog.setCancelable(false)
                dialog.setContentView(dialogView)
                dialog.show()
            }

        }

    }
    override fun onResume() {
        super.onResume()
        requestCameraPermission()
        barcodeScannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView.pause()
    }
    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            barcodeScannerView.resume()
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateQRCode(text: String, size:Int): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)

        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }

        return bitmap
    }

}