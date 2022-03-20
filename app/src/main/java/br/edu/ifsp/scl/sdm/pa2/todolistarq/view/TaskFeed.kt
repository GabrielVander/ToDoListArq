package br.edu.ifsp.scl.sdm.pa2.todolistarq.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.pa2.todolistarq.R
import br.edu.ifsp.scl.sdm.pa2.todolistarq.databinding.FragmentTaskFeedBinding
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Task
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_EXTRA_ACTION
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_REQUEST_KEY
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.VIEW_ONLY
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.adapter.OnTaskClickListener
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.adapter.TasksAdapter

class TaskFeed : BaseFragment(), OnTaskClickListener {
    private lateinit var fragmentTaskFeedBinding: FragmentTaskFeedBinding
    private lateinit var tasks: MutableList<Task>
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tasks = mutableListOf()

        for (i in 0..10) {
            tasks.add(Task(i, "Task $i", i % 2))
        }

        setFragmentResultListener(TASK_REQUEST_KEY) { _, bundle ->
            val taskExtra = bundle.getParcelable<Task>(TASK_EXTRA)
            if (taskExtra != null) {
                var isANewTask = true
                tasks.forEachIndexed { index, task ->
                    if (task.id == taskExtra.id) {
                        tasks[index] = taskExtra
                        isANewTask = false
                    }
                }
                if (isANewTask) {
                    tasks.add(taskExtra)
                }
                tasksAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTaskFeedBinding =
            FragmentTaskFeedBinding.inflate(inflater, container, false)

        tasksAdapter = TasksAdapter(this, tasks)
        val feedLayoutManager = LinearLayoutManager(activity)
        fragmentTaskFeedBinding.tasksRecyclerView.adapter = tasksAdapter
        fragmentTaskFeedBinding.tasksRecyclerView.layoutManager = feedLayoutManager

        return fragmentTaskFeedBinding.root
    }

    override fun onTaskClick(index: Int) {
        val task = tasks[index]
        openTaskDisplayFragment(task, true)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = tasksAdapter.position
        return when (item.itemId) {
            R.id.editarTarefaMi -> {
                openTaskDisplayFragment(tasks[position], false)
                true
            }
            R.id.removerTarefaMi -> {
                tasks.removeAt(position)
                tasksAdapter.notifyDataSetChanged()
                true
            }
            else -> false
        }
    }

    private fun openTaskDisplayFragment(task: Task, isViewOnly: Boolean) {
        val args = Bundle().also { bundle ->
            bundle.putParcelable(TASK_EXTRA, task)
            if (isViewOnly) {
                bundle.putInt(TASK_EXTRA_ACTION, VIEW_ONLY)
            }
        }
        val taskDisplayFragment = TaskDisplayFragment()
        taskDisplayFragment.arguments = args

        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            addToBackStack("TaskDisplayFragment")
            replace(R.id.mainFragmentContainerView, taskDisplayFragment)
        }
    }
}