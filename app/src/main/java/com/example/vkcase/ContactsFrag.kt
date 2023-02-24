package com.example.vkcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkcase.databinding.FragContactsBinding
import java.io.Console

class ContactsFrag : Fragment(R.layout.frag_contacts) {

    private lateinit var binding : FragContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragContactsBinding.inflate(inflater, container, false)
        val fragmentLM = LinearLayoutManager(requireContext())
        val persons = requireArguments().getParcelableArrayList<Person>("persons") as ArrayList<Person>

        binding.fragmentRecycler.layoutManager = fragmentLM
        val contactsAdapter = ContactsAdapter()
        binding.fragmentRecycler.adapter = contactsAdapter
        contactsAdapter.setData(persons)


        binding.backBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return binding.root
    }
}