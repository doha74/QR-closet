package com.example.qrcloset.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcloset.R
import com.example.qrcloset.data.Produit
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BinaryBitmap
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader

class ScannedAdapter (private val mList: MutableList<Bitmap>, val context: Context) : RecyclerView.Adapter<ScannedAdapter.ViewHolder>() {
    private var isDialogShown = false

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageviewBitmap)

    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageBitmap(ItemsViewModel)
        holder.itemView.setOnClickListener {
            if (!isDialogShown) { // Only show dialog if not already shown
                isDialogShown = true

                // Inflate bottom sheet dialog layout
                val dialog =
                    BottomSheetDialog(context)// RequiredContext() is not available in the adapter
                val dialogView =
                    LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null)/* LayoutInflater.inflate() is incorrectly .
                It should be LayoutInflater.from(context) */

                // Set QR code text in the TextView
                val idAllDescription = dialogView.findViewById<TextView>(R.id.idAllDescription)
                idAllDescription.text = decodeQRCodeFromBitmap(ItemsViewModel)

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
    fun decodeQRCodeFromBitmap(bitmap: Bitmap): String? {
        try {
            // Convert the Bitmap to a BinaryBitmap
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

            val source = RGBLuminanceSource(width, height, pixels)
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

            // Decode the QR code
            val reader = QRCodeReader()
            val result = reader.decode(binaryBitmap)
            return result.text
        } catch (e: Exception) {
            Log.e("QRCodeDecoding", "Error decoding QR code: ${e.message}")
        }
        return null
    }
}