package co.id.example.kula.data

data class Pemesanan(
    val idUser: String,
    val idPemesanan: String,
    val namaLengkap: String,
    val email: String,
    val alamat: String,
    val noTlp: String,
    val layanan: String,
    val tglpemesanan: String,
    val tglpelaksanaan: String,
    val harga: String,
    )