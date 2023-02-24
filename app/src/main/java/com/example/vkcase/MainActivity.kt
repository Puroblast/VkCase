package com.example.vkcase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val imgAdapter = ImageAdapter()
    private val persons = arrayListOf(
        Person(
            "Venera Phone Long Contact To Test Again Again Again Again Again",
            R.drawable.anime_avatar,
            1
        ) ,
        Person(
            "Anna",
            R.drawable.avatar_image,
            2
        )
    )
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imgAdapter.setData(persons)
        val customLM = object : GridLayoutManager(this, 1) {
            override fun canScrollVertically(): Boolean = false
        }
        var cameraFlag = true
        var microFlag = true


        binding.recyclerView.layoutManager = customLM
        binding.recyclerView.adapter = imgAdapter

        binding.sortIb.setOnClickListener {
            swap()
        }

        binding.cameraIb.setOnClickListener {
            if (cameraFlag) {
                binding.cameraIb.setImageResource(R.drawable.videocam_off)
                binding.cameraIb.setBackgroundResource(R.drawable.circular_background_white)
                cameraFlag = false
            } else {
                binding.cameraIb.setImageResource(R.drawable.videocam)
                binding.cameraIb.setBackgroundResource(R.drawable.circular_background)
                cameraFlag = true
            }
        }

        binding.micIb.setOnClickListener {
            if (microFlag) {
                binding.micIb.setImageResource(R.drawable.mic_off)
                binding.micIb.setBackgroundResource(R.drawable.circular_background_white)
                microFlag = false
            } else {
                binding.micIb.setImageResource(R.drawable.mic)
                binding.micIb.setBackgroundResource(R.drawable.circular_background)
                microFlag = true
            }
        }

        binding.handIb.setOnClickListener {
            val helloDialogFragment = HelloDialogFragment()
            helloDialogFragment.show(supportFragmentManager, "Hello")
        }

        binding.cancelIb.setOnClickListener {
            finish()
        }

        binding.chatIb.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
            startActivity(intent)
        }

        binding.groupIb.setOnClickListener {
            if (savedInstanceState == null) {
                val bundle = Bundle()
                bundle.putParcelableArrayList("persons", persons)
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<ContactsFrag>(binding.container.id, args = bundle)
                }
            }
        }

    }


    private fun swap() {
        val changedPerson = persons[0]
        val newPersons = arrayListOf<Person>()
        persons[0] = persons[1]
        persons[1] = changedPerson
        newPersons.addAll(persons)
        imgAdapter.setData(newPersons)
    }
}