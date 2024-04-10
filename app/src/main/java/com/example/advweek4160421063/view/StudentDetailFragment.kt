package com.example.advweek4160421063.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.advweek4160421063.R
import com.example.advweek4160421063.databinding.FragmentStudentDetailBinding
import com.example.advweek4160421063.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            val student = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId

            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.fetch(student)

            observeViewModel()
        }

    }
    fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            var student = it

            binding.btnUpdate?.setOnClickListener {
                Observable.timer(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("Messages", "five seconds")
                        MainActivity.showNotification(student.name.toString(),
                            "A new notification created",
                            R.drawable.ic_launcher_foreground)
                    }
            }



            if (it == null) {
            }
            else {
                binding.txtID.setText(it.id)
                binding.txtName.setText(it.name)
                binding.txtBoD.setText(it.dob)
                binding.txtPhone.setText(it.phone)

                val picasso = this.context?.let { it1 -> Picasso.Builder(it1) }
                picasso?.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                }
                picasso?.build()?.load(viewModel.studentLD.value?.photoUrl)?.into(binding.imageView2)
            }
        })

    }


}