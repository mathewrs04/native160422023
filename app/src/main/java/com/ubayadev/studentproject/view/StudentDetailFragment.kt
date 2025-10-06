package com.ubayadev.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubayadev.studentproject.R
import com.ubayadev.studentproject.databinding.FragmentStudentDetailBinding
import com.ubayadev.studentproject.model.Student
import com.ubayadev.studentproject.viewmodel.DetailViewModel


class StudentDetailFragment : Fragment() {
    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var viewmodel: DetailViewModel
    private lateinit var student:Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewmodel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel() {
        viewmodel.studentLD.observe(viewLifecycleOwner, Observer {
            student = it

            // update UI
            binding.txtId.setText(student.id)
            binding.txtName.setText(student.name)
            binding.txtBod.setText(student.bod)
            binding.txtPhone.setText(student.phone)
        })
    }
}