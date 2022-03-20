package br.edu.ifsp.scl.sdm.pa2.todolistarq.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import br.edu.ifsp.scl.sdm.pa2.todolistarq.R
import br.edu.ifsp.scl.sdm.pa2.todolistarq.databinding.FragmentTaskDisplayBinding
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Tarefa
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.ACAO_TAREFA_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.CONSULTA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.TAREFA_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.TAREFA_REQUEST_KEY
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskDisplayFragment : BaseFragment() {
    private lateinit var fragmentTaskDisplayBinding: FragmentTaskDisplayBinding

    private var taskExtraId: Long = NON_EXISTING_ID
    private var fab: FloatingActionButton? = null

    companion object {
        private const val NON_EXISTING_ID = -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab = activity?.findViewById(R.id.newTaskFab)
        fab?.visibility = GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTaskDisplayBinding = FragmentTaskDisplayBinding.inflate(inflater, container, false)

        fragmentTaskDisplayBinding.saveTaskButton.setOnClickListener {
            if (taskExtraId != NON_EXISTING_ID) {
            } else {
            }
            returnTask(
                Tarefa(
                    150,
                    fragmentTaskDisplayBinding.taskNameEditText.text.toString(),
                    if (fragmentTaskDisplayBinding.isTaskDoneCheckbox.isChecked) 1 else 0
                )
            )
        }

        val taskExtra = arguments?.getParcelable<Tarefa>(TAREFA_EXTRA)
        if (taskExtra != null) {
            taskExtraId = taskExtra.id.toLong()
            with(fragmentTaskDisplayBinding) {
                taskNameEditText.setText(taskExtra.nome)
                isTaskDoneCheckbox.isChecked =
                    taskExtra.realizada != 0
            }
            val taskExtraAction = arguments?.getInt(ACAO_TAREFA_EXTRA)
            if (taskExtraAction == CONSULTA) {
                with(fragmentTaskDisplayBinding) {
                    taskNameEditText.isEnabled = false
                    isTaskDoneCheckbox.isEnabled = false
                    saveTaskButton.visibility = GONE
                }
            }
        }

        return fragmentTaskDisplayBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        fab?.visibility = View.VISIBLE
    }

    private fun returnTask(task: Tarefa) {
        setFragmentResult(TAREFA_REQUEST_KEY, Bundle().also {
            it.putParcelable(TAREFA_EXTRA, task)
        })
        activity?.supportFragmentManager?.popBackStack()
    }
}