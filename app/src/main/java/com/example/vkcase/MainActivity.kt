package com.example.vkcase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.vkcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val myId = 1
    private val imgAdapter = ImageAdapter()
    private var persons = arrayListOf(
        Person(
            "Venera Phone Long Contact To Test Again Again Again Again Again",
            R.drawable.anime_avatar,
            1
        ),
        Person(
            "Anna",
            R.drawable.avatar_image,
            2
        )
    )
    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val customLM = object : GridLayoutManager(this, 1) {
        override fun canScrollVertically(): Boolean = false
    }
    private var cameraFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        imgAdapter.setData(persons)
        with(binding) {
            groupCountTv.text = persons.size.toString()
            recyclerView.layoutManager = customLM
            recyclerView.adapter = imgAdapter
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
            setClicks(savedInstanceState)
        }
    }

    private fun ActivityMainBinding.setClicks(savedInstanceState: Bundle?) {
        sortIb.setOnClickListener { swap() }
        cameraIb.setOnClickListener { changeCameraState() }
        micIb.setOnClickListener { changeMicState() }
        handIb.setOnClickListener { showDialogFragment() }
        cancelIb.setOnClickListener { finish() }
        chatIb.setOnClickListener { goToMessages() }
        groupIb.setOnClickListener { goToGroupPart(savedInstanceState) }
    }

    private fun goToGroupPart(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putParcelableArrayList("persons", persons)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ContactsFrag>(binding.container.id, args = bundle)
            }
        }
    }

    private fun goToMessages() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
        startActivity(intent)
    }

    private fun showDialogFragment() {
        val helloDialogFragment = HelloDialogFragment()
        helloDialogFragment.show(supportFragmentManager, "Hello")
    }

    private fun changeMicState() {
        for (person in persons) {
            val position = persons.indexOf(person)
            if (person.id == myId) {
                if (person.isMuted) {
                    binding.micIb.setImageResource(R.drawable.mic)
                    binding.micIb.setBackgroundResource(R.drawable.circular_background)
                    person.isMuted = false
                } else {
                    binding.micIb.setImageResource(R.drawable.mic_off)
                    binding.micIb.setBackgroundResource(R.drawable.circular_background_white)
                    person.isMuted = true
                }
                imgAdapter.notifyItemChanged(position)
                break
            }
        }
    }

    private fun changeCameraState() {
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

    private fun swap() {
        val newPersons = arrayListOf<Person>()
        newPersons.addAll(persons)
        newPersons[0] = persons[1]
        newPersons[1] = persons[0]
        persons = newPersons
        imgAdapter.setData(persons)
    }
}