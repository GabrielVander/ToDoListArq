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
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Tarefa
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.ACAO_TAREFA_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.CONSULTA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.TAREFA_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.TAREFA_REQUEST_KEY
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.adapter.OnTaskClickListener
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.adapter.TarefasAdapter

class TaskFeed : BaseFragment(), OnTaskClickListener {
    private lateinit var fragmentTaskFeedBinding: FragmentTaskFeedBinding
    private lateinit var tasks: MutableList<Tarefa>
    private lateinit var tasksAdapter: TarefasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tasks = mutableListOf()

        for (i in 0..10) {
            tasks.add(Tarefa(i, "Task $i", i % 2))
        }

        setFragmentResultListener(TAREFA_REQUEST_KEY) { _, bundle ->
            val taskExtra = bundle.getParcelable<Tarefa>(TAREFA_EXTRA)
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

        tasksAdapter = TarefasAdapter(this, tasks)
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
        val position = tasksAdapter.posicao
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

    private fun openTaskDisplayFragment(task: Tarefa, isViewOnly: Boolean) {
        val args = Bundle().also { bundle ->
            bundle.putParcelable(TAREFA_EXTRA, task)
            if (isViewOnly) {
                bundle.putInt(ACAO_TAREFA_EXTRA, CONSULTA)
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