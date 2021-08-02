package co.id.example.kula

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import kotlinx.android.synthetic.main.activity_reservasi_struk.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException

class ReservasiStrukActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore



    val pdfPath = Environment.getExternalStorageDirectory()


    var pageHeight = 1120
    var pagewidth = 792

    var bmp: Bitmap? = null
    var scaledbmp: Bitmap? = null

    private val PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasi_struk)

        val idPemesanan = intent.getStringExtra("idPemesanan")

        db.collection("pemesanan")
            .document(idPemesanan.toString())
            .addSnapshotListener { value, error ->
                harga_strk.setText(value?.getString("harga").toString())
                daftar_nama.text = value?.getString("namaLengkap")
                daftar_email.text = value?.getString("email")
                daftar_telepon.text = value?.getString("noTlp")
                daftar_alamat.text = value?.getString("alamat")
                daftar_layanan.text = value?.getString("layanan")
                daftar_tglpesan.text = value?.getString("tglpemesanan")
                daftar_tglpelaksanaan.text = value?.getString("tglpelaksanaan")
                daftar_metodepembayaran.text = value?.getString("metodePembayaran")
            }



        try {
            if (checkPermission()) {
                Toast.makeText(this, "Hak akses penyimpanan diizinkan", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun print(view: View) {

            val pathPdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString()
            val file = File(pathPdf, setNamePdf("Event"))
            val outputStream = FileOutputStream(file)

            val writer = PdfWriter(file)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)
            pdfDocument.addEventHandler(
                PdfDocumentEvent.END_PAGE,
                WatermarkingEventHandler(getImageKop(R.drawable.nokop))
            )


            val dataEvent = CategoryEventData.listData
            document.setMargins(0f, 0f, 0f, 0f)

            val setColorBackroundHeaderTable = DeviceRgb(249, 255, 167)
            val table =
                Table(
                    UnitValue.createPercentArray(
                        floatArrayOf(
                            2f,
                            10f,
                            10f,
                            3f,
                            10f
                        )
                    )
                ).useAllAvailableWidth()

            table.setMarginRight(10f)
            table.setMarginLeft(10f)
            table.setMarginTop(180f)

            table.addCell(
                Cell().add(
                    Paragraph("Kategori").setTextAlignment(TextAlignment.CENTER).setBold()
                ).setBackgroundColor(setColorBackroundHeaderTable).setPadding(2f)
            )
            table.addCell(
                Cell().add(
                    Paragraph("Title").setTextAlignment(TextAlignment.CENTER).setBold()
                ).setBackgroundColor(setColorBackroundHeaderTable).setPadding(2f)
            )
            table.addCell(
                Cell().add(
                    Paragraph("Deskripsi").setTextAlignment(TextAlignment.CENTER).setBold()
                ).setBackgroundColor(setColorBackroundHeaderTable).setPadding(2f)
            )
            table.addCell(
                Cell().add(
                    Paragraph("Waktu").setTextAlignment(TextAlignment.CENTER).setBold()
                ).setBackgroundColor(setColorBackroundHeaderTable).setPadding(2f)
            )
            table.addCell(
                Cell().add(
                    Paragraph("Penulis/Pengirim").setTextAlignment(TextAlignment.CENTER).setBold()
                ).setBackgroundColor(setColorBackroundHeaderTable).setPadding(2f)
            )


            for (position in 0 until dataEvent.size - 1) {
                val title = CategoryEventData.listData.get(position).title
                val description = CategoryEventData.listData.get(position).description
                val category = CategoryEventData.listData.get(position).category
                val postTime = CategoryEventData.listData.get(position).postTime
                val postBy = CategoryEventData.listData.get(position).postBy



                table.addCell(
                    Cell().add(Paragraph(category).setTextAlignment(TextAlignment.LEFT)).setPadding(2f)
                )
                table.addCell(
                    Cell().add(Paragraph(title).setTextAlignment(TextAlignment.LEFT)).setPadding(2f)
                )
                table.addCell(
                    Cell().add(
                        Paragraph(
                            description.substring(
                                0,
                                100
                            ) + " ..."
                        ).setTextAlignment(TextAlignment.LEFT)
                    ).setPadding(2f)
                )
                table.addCell(
                    Cell().add(Paragraph(postTime).setTextAlignment(TextAlignment.LEFT)).setPadding(2f)
                )
                table.addCell(
                    Cell().add(Paragraph(postBy).setTextAlignment(TextAlignment.LEFT)).setPadding(2f)
                )
            }

            val getDate = Paragraph(getDate())
            val getHormat = Paragraph("\nHormat kami,").setTextAlignment(TextAlignment.RIGHT)
            val getTtd  = Paragraph("Riyan Agustiar Sutardi \n\n").setTextAlignment(TextAlignment.RIGHT)
            val getChief = Paragraph( "Chief Project Creative").setBold().setTextAlignment(TextAlignment.RIGHT)

            document.add(table)
            document.add(getDate.setTextAlignment(TextAlignment.RIGHT).setMarginRight(50f))
            document.add(getHormat.setMarginRight(70f))
            document.add(getTtd.setMarginRight(60f))
            document.add(getChief.setMarginRight(60f))
            document.close()

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun getImageKop(image: Int): Image {
        val kopImage: Bitmap = BitmapFactory.decodeResource(resources, image)

        val stream = ByteArrayOutputStream()
        kopImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteMapDataKop = stream.toByteArray()
        val imageData: ImageData = ImageDataFactory.create(byteMapDataKop)
        return Image(imageData)
    }

    private class WatermarkingEventHandler(image: Image) : IEventHandler, AppCompatActivity() {

        val imageKop = image


        override fun handleEvent(currentEvent: Event) {
            val docEvent = currentEvent as PdfDocumentEvent
            val pdfDoc = docEvent.document
            val page = docEvent.page
            var font: PdfFont? = null
            try {
                font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
            } catch (e: IOException) {

                // Such an exception isn't expected to occur,
                // because helvetica is one of standard fonts
                System.err.println(e.message)
            }
            val canvas = PdfCanvas(page.newContentStreamBefore(), page.resources, pdfDoc)

            com.itextpdf.layout.Canvas(canvas, page.pageSize)
                .add(imageKop)
                .close()


        }
    }



    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }




}