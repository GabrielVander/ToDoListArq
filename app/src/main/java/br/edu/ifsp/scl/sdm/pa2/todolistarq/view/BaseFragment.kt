package br.edu.ifsp.scl.sdm.pa2.todolistarq.view

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    object Constants {
        const val TASK_REQUEST_KEY = "TASK_REQUEST_KEY"
        const val TASK_EXTRA = "TASK_EXTRA"
        const val TASK_EXTRA_ACTION = "TASK_EXTRA_ACTION"
        const val VIEW_ONLY = 1
    }
}