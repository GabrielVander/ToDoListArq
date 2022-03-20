package br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull
    var name: String,
    @NonNull
    var isDone: Int = 0
) : Parcelable
