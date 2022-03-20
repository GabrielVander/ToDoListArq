package br.edu.ifsp.scl.sdm.pa2.todolistarq.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import br.edu.ifsp.scl.sdm.pa2.todolistarq.R
import br.edu.ifsp.scl.sdm.pa2.todolistarq.databinding.FragmentTaskDisplayBinding
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Task
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_EXTRA
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_EXTRA_ACTION
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.TASK_REQUEST_KEY
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constants.VIEW_ONLY
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
                Task(
                    150,
                    fragmentTaskDisplayBinding.taskNameEditText.text.toString(),
                    if (fragmentTaskDisplayBinding.isTaskDoneCheckbox.isChecked) 1 else 0
                )
            )
        }

        val taskExtra = arguments?.getParcelable<Task>(TASK_EXTRA)
        if (taskExtra != null) {
            taskExtraId = taskExtra.id.toLong()
            with(fragmentTaskDisplayBinding) {
                taskNameEditText.setText(taskExtra.name)
                isTaskDoneCheckbox.isChecked =
                    taskExtra.isDone != 0
            }
            val taskExtraAction = arguments?.getInt(TASK_EXTRA_ACTION)
            if (taskExtraAction == VIEW_ONLY) {
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

    private fun returnTask(task: Task) {
        setFragmentResult(TASK_REQUEST_KEY, Bundle().also {
            it.putParcelable(TASK_EXTRA, task)
        })
        activity?.supportFragmentManager?.popBackStack()
    }
}