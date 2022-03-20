package br.edu.ifsp.scl.sdm.pa2.todolistarq.model.dao

import androidx.room.*
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Task

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task): Long

    @Delete
    fun remove(task: Task)

    @Delete
    fun removeMultiple(vararg task: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getSingle(taskId: Int): Task
}