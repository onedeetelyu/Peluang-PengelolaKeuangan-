# Peluang (Pengelola Keuangan)

<strong>Dibuat oleh:</strong>
* 6706180024 - Wandi Yusuf Kurniawan - @onedeetelyu
* 6706184047 - Farhan Shofwani - @farhanshofwani1159
* 6706184090 - Putra Budiansyah - @PutraBudiansyah
   
<strong>Deskripsi:</strong><br>
Aplikasi untuk memudahkan user dalam mengatur keuangannya. Dengan aplikasi ini, user dapat memasukkan jumlah pemasukan, pengeluaran, riwayat pengeluaranya, serta menampilkan grafik pengeluarannya yang mengefisiensikan user dalam menabung/mengelola keuangannya.<br><br>

<strong>Fitur:</strong>
* Tambah Cash-In & Cash-Out
* Reset Cash-In & Cash-Out
* Riwayat Cash-Out
* Update Cash-Out => Coming soon.
* Delete beberapa Cash-Out 
* Grafik Cash-Out => Coming soon.

<strong>Link video dan aplikasi Assessment 1:</strong> https://drive.google.com/drive/folders/1X9TT30QFsxhYMXrLF8WbmVdgaI5y01hA <br>
<strong>Link video dan aplikasi Assessment 2:</strong> https://drive.google.com/file/d/1rhqgzl-zbdljpq7baBwjHngAiSc3i9bd/view?usp=sharing

<strong>Skenario Testing</strong><br>
A. Pemasukan data:
1. Jalankan MainActivity
2. Lakukan aksi menambah data pemasukan baru<br>
   a. Klik pada button “cash in”<br>
   b. Ketikkan nominal yang sudah ditentukan pada EditText untuk nominal<br>
   c. Klik tombol yang bertuliskan “Simpan”<br>
3. Ulangi langkah ke 2 untuk memastikan saldonya bertambah
4. Cek apakah saldonya bertambah sesuai dengan 2 kali pemasukan
5. Lakukan aksi menambah data pengeluaran baru
6. Ulangi langkah ke 5 untuk memastikan saldonya berkurang
7. Cek apakah hasilnya sesuai yang diharapkan<br>
   a. Saldonya berhasil berkurang setelah ditambahkannya data pengeluaran sebanyak 2 kali<br>
   b. Nominal dan deskripsinya berhasil ditampilkan<br>
8. Tutup activity

B. Pengeluaran data:
1. Masukkan beberapa data pemasukan dan pengeluaran sebagai data awal
2. Jalankan MainActivity
3. Lakukan penghapusan data pengeluaran<br>
   a. Lakukan long click pada data ke-2 pengeluaran di RecyclerView, lalu cek apakah action mode nya muncul / menu delete tampil<br>
   b. Lakukan klik terhadap data pertama dan ke-4 pengeluaran pada RecyclerView, lalu cek apakah action mode menampilkan jumlah data terpilih<br>
   c. Lakukan klik terhadap menu hapus, lalu konfirmasi hapus<br>
   d. Cek apakah data pertama, ke-2, dan ke-4 pengeluarannya sudah tidak ada lagi<br>
   e. Cek apakah saldonya bertambah sesuai dengan dihapusnya 2 data pengeluaran<br>
4. Lakukan penghapusan pada semua data
5. Cek apakah semua data sudah benar-benar terhapus.
6. Tutup activity

<strong>Kodingan untuk Testing</strong>

    `companion object {
        private val CASHIN_DUMMY_1 = Cash(0, 50000.0, "")
        private val CASHIN_DUMMY_2 = Cash(1, 75000.0, "")
        private val CASHOUT_DUMMY_1 = Cash(2, 25000.0, "Membeli tahu bulat dan odading")
        private val CASHOUT_DUMMY_2 = Cash(3, 100000.0, "Membeli pulsa untuk kuota internet")
        private val CASHIN_DUMMY_3 = Cash(104, 200000.0, "")
        private val CASHOUT_DUMMY_3 = Cash(105, -20000.0, "Membeli jajanan")
        private val CASHOUT_DUMMY_4 = Cash(106, -120000.0, "Mentraktir teman makan")
        private val CASHOUT_DUMMY_5 = Cash(107, -60000.0, "Membeli suvenir untuk teman-teman")
    }

    @Before
    fun setUp() {
        // Lakukan penghapusan database setiap kali test akan dijalankan.
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(CashDatabase.DATABASE_NAME)
    }

    //Pemasukan data
    @Test
    fun testNumber1Insert() {
        //  1. Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(500)

        //	2. Lakukan aksi menambah data pemasukan baru
        //    a. Klik pada button “cash in”
        onView(withId(R.id.btnCashIn)).perform(click())
        Thread.sleep(500)
        //    b. Ketikkan nominal yang sudah ditentukan pada EditText untuk nominal
        onView(withId(R.id.etPemasukan)).perform(ViewActions.typeText(CASHIN_DUMMY_1.nominal.toString()))
        Thread.sleep(500)
        //    c. Klik tombol yang bertuliskan “Simpan”
        onView(withText("Simpan")).perform(click())
        Thread.sleep(500)

        //	3. Ulangi langkah ke 2 untuk memastikan saldonya bertambah
        onView(withId(R.id.btnCashIn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.etPemasukan)).perform(ViewActions.typeText(CASHIN_DUMMY_2.nominal.toString()))
        Thread.sleep(500)
        onView(withText("Simpan")).perform(click())
        Thread.sleep(500)

        //	4. Cek apakah saldonya bertambah sesuai dengan 2 kali pemasukan
        onView(withText("Rp125000.0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)

        //	5. Lakukan aksi menambah data pengeluaran baru
        //	  a. Klik pada button “cash out”
        onView(withId(R.id.btnCashOut)).perform(click())
        Thread.sleep(500)
        //	  b. Ketikkan nominal yang sudah ditentukan pada EditText untuk nominal
        onView(withId(R.id.etPengeluaran)).perform(ViewActions.typeText(CASHOUT_DUMMY_1.nominal.toString()))
        Thread.sleep(500)
        //	  c. Ketikkan deskripsi yang sudah ditentukan pada EditText untuk deskripsi
        onView(withId(R.id.etDeskripsi)).perform(ViewActions.typeText(CASHOUT_DUMMY_1.deskripsi))
        Thread.sleep(500)
        //	  d. Klik tombol yang bertuliskan “Simpan”
        onView(withText("Simpan")).perform(click())
        Thread.sleep(500)

        //	6. Ulangi langkah ke 5 untuk memastikan saldonya berkurang
        onView(withId(R.id.btnCashOut)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.etPengeluaran)).perform(ViewActions.typeText(CASHOUT_DUMMY_2.nominal.toString()))
        Thread.sleep(500)
        onView(withId(R.id.etDeskripsi)).perform(ViewActions.typeText(CASHOUT_DUMMY_2.deskripsi))
        Thread.sleep(500)
        onView(withText("Simpan")).perform(click())
        Thread.sleep(500)

        //	7. Cek apakah hasilnya sesuai yang diharapkan
        //	  a. Saldonya berhasil berkurang setelah ditambahkannya data pengeluaran sebanyak 2 kali
        onView(withText("Rp0.0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //	  b. Nominal dan deskripsinya berhasil ditampilkan
        onView(withText("Rp" + CASHOUT_DUMMY_1.nominal.toString())).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        onView(withText(CASHOUT_DUMMY_1.deskripsi)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withText("Rp" + CASHOUT_DUMMY_2.nominal.toString())).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        onView(withText(CASHOUT_DUMMY_2.deskripsi)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)

        //	8. Tutup activity
        activityScenario.close()
    }

    //Penghapusan data
    @Test
    fun testNumber2Delete() {
        //  1. Masukkan beberapa data pemasukan dan pengeluaran sebagai data awal
        runBlocking(Dispatchers.IO) {
            val dao = CashDatabase.getInstance(ApplicationProvider.getApplicationContext()).dao
            dao.insertData(CASHIN_DUMMY_3)
            dao.insertData(CASHOUT_DUMMY_3)
            dao.insertData(CASHOUT_DUMMY_4)
            dao.insertData(CASHOUT_DUMMY_5)
        }

        //  2. Jalankan MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(500)

        //  3. Lakukan penghapusan data pengeluaran
        //	  a. Lakukan long click pada data ke-2 pengeluaran di RecyclerView, lalu cek apakah action mode nya muncul / menu delete tampil
        onView(withId(R.id.rv_cash)).atItem(1, ViewActions.longClick())
        onView(withId(R.id.delete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)
        //	  b. Lakukan klik terhadap data pertama dan ke-4 pengeluaran pada RecyclerView, lalu cek apakah action mode menampilkan jumlah data terpilih
        onView(withId(R.id.rv_cash)).atItem(0, click())
        onView(withId(R.id.rv_cash)).atItem(3, click())
        onView(withText("3")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)
        //	  c. Lakukan klik terhadap menu hapus, lalu konfirmasi hapus
        onView(withId(R.id.delete)).perform(click())
        Thread.sleep(500)
        onView(withText("Hapus")).perform(click())
        Thread.sleep(500)
        //	  d. Cek apakah data pertama, ke-2, dan ke-4 pengeluarannya sudah tidak ada lagi
        onView(withText("Rp" + CASHOUT_DUMMY_1.nominal.toString())).check(ViewAssertions.doesNotExist())
        onView(withText(CASHOUT_DUMMY_1.deskripsi)).check(ViewAssertions.doesNotExist())
        onView(withText("Rp" + CASHOUT_DUMMY_2.nominal.toString())).check(ViewAssertions.doesNotExist())
        onView(withText(CASHOUT_DUMMY_2.deskripsi)).check(ViewAssertions.doesNotExist())
        onView(withText("Rp" + CASHOUT_DUMMY_4.nominal.toString())).check(ViewAssertions.doesNotExist())
        onView(withText(CASHOUT_DUMMY_4.deskripsi)).check(ViewAssertions.doesNotExist())
        //    e. Cek apakah saldonya bertambah sesuai dengan dihapusnya 2 data pengeluaran
        onView(withText("Rp245000.0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)

        //  4. Lakukan penghapusan pada semua data
        onView(withId(R.id.btnReset)).perform(click())
        Thread.sleep(500)
        onView(withText("Hapus")).perform(click())

        // 5. Cek apakah semua data sudah benar-benar terhapus.
        onView(withText("Rp" + CASHOUT_DUMMY_3.nominal.toString())).check(ViewAssertions.doesNotExist())
        onView(withText(CASHOUT_DUMMY_3.deskripsi)).check(ViewAssertions.doesNotExist())
        onView(withText("Rp" + CASHOUT_DUMMY_5.nominal.toString())).check(ViewAssertions.doesNotExist())
        onView(withText(CASHOUT_DUMMY_5.deskripsi)).check(ViewAssertions.doesNotExist())
        onView(withText("Rp0.0")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(500)

        //	6. Tutup activity
        activityScenario.close()
    }`

<strong>Hasil Testing:</strong>
![Screenshot (76)](https://user-images.githubusercontent.com/42926210/99254481-0106a800-2845-11eb-8e22-5eb29fb9b970.png)
