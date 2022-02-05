package org.d3ifcool.peluang

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.d3ifcool.peluang.database.Cash
import org.d3ifcool.peluang.database.CashDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class MainActivityTest {
    companion object {
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
        //    a. Klik pada button “cash in ”
        onView(withId(R.id.btnCashIn)).perform(click())
        Thread.sleep(500)
        //    b. Ketikkan nominal yang sudah ditentukan pada EditText untuk nominal
        onView(withId(R.id.etNominal)).perform(ViewActions.typeText(CASHIN_DUMMY_1.nominal.toString()))
        Thread.sleep(500)
        //    c. Klik tombol yang bertuliskan “ Simpan ”
        onView(withText("Simpan")).perform(click())
        Thread.sleep(500)

        //	3. Ulangi langkah ke 2 untuk memastikan saldonya bertambah
        onView(withId(R.id.btnCashIn)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.etNominal)).perform(ViewActions.typeText(CASHIN_DUMMY_2.nominal.toString()))
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
        onView(withId(R.id.etNominal)).perform(ViewActions.typeText(CASHOUT_DUMMY_1.nominal.toString()))
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
        onView(withId(R.id.etNominal)).perform(ViewActions.typeText(CASHOUT_DUMMY_2.nominal.toString()))
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
        onView(withId(R.id.reset)).perform(click())
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
    }

    private fun ViewInteraction.atItem(pos: Int, action: ViewAction) {
        perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(pos, action))
    }
}