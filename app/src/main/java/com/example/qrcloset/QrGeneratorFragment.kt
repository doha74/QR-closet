package com.example.qrcloset

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.qrcloset.Functions.Functions
import com.example.qrcloset.data.Produit
import com.example.qrcloset.liste.Generated
import com.google.android.material.textfield.TextInputLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.NotFoundException
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import com.google.zxing.qrcode.encoder.QRCode
import java.io.IOException


class QrGeneratorFragment : Fragment() {
    lateinit var tilName: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    lateinit var tilSize: TextInputLayout
    lateinit var tilColor: TextInputLayout
    lateinit var tilPrice: TextInputLayout
    lateinit var button: Button

    lateinit var inputName: String
    lateinit var inputDescription : String
    lateinit var inputPrice: String
    lateinit var inputSize: String
    lateinit var inputColor: String

    lateinit var qrContent: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_generator, container, false)

        tilName = view.findViewById(R.id.tilName)
        tilDescription = view.findViewById(R.id.tilDescription)
        tilPrice = view.findViewById(R.id.tilPrice)
        tilSize = view.findViewById(R.id.tilSize)
        tilColor = view.findViewById(R.id.tilColor)
        button = view.findViewById(R.id.btnGenerateQr)


        button.setOnClickListener{
            inputName = tilName.editText?.text.toString()
            inputDescription = tilDescription.editText?.text.toString()
            inputPrice = tilPrice.editText?.text.toString()
            inputSize = tilSize.editText?.text.toString()
            inputColor = tilColor.editText?.text.toString()

            if (inputName.isEmpty() || inputDescription.isEmpty() || inputPrice.isEmpty() ||
                inputSize.isEmpty() || inputColor.isEmpty()) {
                tilName.error = if (inputName.isEmpty()) "Name is required" else null
                tilDescription.error = if (inputDescription.isEmpty()) "Description is required" else null
                tilPrice.error = if (inputPrice.isEmpty()) "Price is required" else null
                tilSize.error = if (inputSize.isEmpty()) "Size is required" else null
                tilColor.error = if (inputColor.isEmpty()) "Color is required" else null
                return@setOnClickListener
            }

            qrContent = "Name: $inputName \nDescription: $inputDescription " +
                    "\nSize: $inputSize \nColor: $inputColor \nPrice: $inputPrice"

            Generated.listeGenerated.add(
                Produit(
                    inputName,
                    inputDescription,
                    inputPrice.toDoubleOrNull() ?: 0.0,
                    inputSize,
                    inputColor,
                    Functions.generateQRCode(qrContent, 600)
                )
            )
            alertdialogBuilder(qrContent) }
        // Inflate the layout for this fragment
        return view
    }

    private fun alertdialogBuilder(context: String){

        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.generatedCode)
        imageView.setImageBitmap(Functions.generateQRCode(qrContent, 600))
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView) // Set the custom view for the dialog
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog on button click
            }
            .create()

        alertDialog.show()
    }

}