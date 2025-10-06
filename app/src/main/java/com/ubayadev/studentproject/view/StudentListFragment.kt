package com.ubayadev.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.studentproject.R
import com.ubayadev.studentproject.databinding.FragmentStudentListBinding
import com.ubayadev.studentproject.viewmodel.ListViewModel


class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    private lateinit var viewmodel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init the vm
        viewmodel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewmodel.refresh()

//        // testing file
//        viewmodel.testSaveFile()

        //setup recycle view
        binding.recViewStudent.layoutManager = LinearLayoutManager(context)
        binding.recViewStudent.adapter = studentListAdapter

        // swipe refresh
        binding.refreshLayout.setOnRefreshListener {
            viewmodel.refresh()
            binding.refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        // obeserve - live data - arraylist student
        viewmodel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        // observe - live data - loadingLD
        viewmodel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                // still loading
                binding.progressLoad.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE

            } else {
                // sudah ga loading
                binding.progressLoad.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE

            }
        })

        viewmodel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                // ada error
                binding.txtError.text = "Something wrong when load student data"
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.INVISIBLE
            }
        })

    }
}