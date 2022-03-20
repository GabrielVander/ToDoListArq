package br.edu.ifsp.scl.sdm.pa2.todolistarq.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.dao.TaskDao
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class ToDoListArqDatabase : RoomDatabase() {
    object Constants {
        const val DB_NAME = "to_do_list_arq_database"
    }

    abstract fun getTaskDao(): TaskDao
}