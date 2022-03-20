package br.edu.ifsp.scl.sdm.pa2.todolistarq.view.adapter

import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa2.todolistarq.R
import br.edu.ifsp.scl.sdm.pa2.todolistarq.databinding.ViewTaskBinding
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Task

class TasksAdapter(
    private val onTaskClickListener: OnTaskClickListener,
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    class TaskViewHolder(viewTaskBinding: ViewTaskBinding) :
        RecyclerView.ViewHolder(viewTaskBinding.root), View.OnCreateContextMenuListener {
        val nameTextView: TextView = viewTaskBinding.nameTextView
        val isDoneCheckbox: CheckBox = viewTaskBinding.isDoneCheckbox

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_tarefa, contextMenu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ViewTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.nameTextView.text = task.name
        holder.isDoneCheckbox.isChecked = task.isDone != 0

        holder.itemView.setOnClickListener {
            onTaskClickListener.onTaskClick(position)
        }
        holder.itemView.setOnLongClickListener {
            this.position = holder.adapterPosition
            false
        }
    }

    override fun getItemCount(): Int = tasks.size

    var position = -1
}