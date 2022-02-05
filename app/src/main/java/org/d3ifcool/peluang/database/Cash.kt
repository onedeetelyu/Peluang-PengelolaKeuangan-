package org.d3ifcool.peluang.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cash(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nominal: Double = 0.0,
    var deskripsi: String = ""
    //coming soon: tanggal dan kategori
)