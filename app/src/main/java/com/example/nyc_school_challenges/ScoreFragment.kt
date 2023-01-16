package com.example.nyc_school_challenges

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.nyc_school_challenges.databinding.FragmentScoreBinding
import com.example.nyc_school_challenges.viewmodel.SchoolViewModel

class ScoreFragment : DialogFragment() {

    private lateinit var binding : FragmentScoreBinding
    private lateinit var viewModel : SchoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            //android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
            android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SchoolViewModel::class.java)
        val currentSelection = viewModel.lCurrentSelection.value
        binding.schoolName.text = currentSelection.schoolName
        binding.schoolAddress.text = currentSelection.addressString
        binding.phone.text = currentSelection.phone
        binding.website.text = currentSelection.website
        binding.email.text = currentSelection.email

        binding.mathScore.text = currentSelection.satScores?.satMathAvgScore ?: "N/A"
        binding.criticalReadingScore.text = currentSelection.satScores?.satCriticalReadingAvgScore ?: "N/A"
        binding.writingScore.text = currentSelection.satScores?.satWritingAvgScore ?: "N/A"

        binding.numTaskTaker.text = currentSelection.satScores?.numOfSatTestTakers ?: "N/A"

        binding.schoolAddressLayout.setOnClickListener{
            //will open map
            val lat = currentSelection.school?.latitude ?: "0.0"
            val lng = currentSelection.school?.longitude ?: "0.0"
            val label = currentSelection.schoolName
            val uri = "geo:$lat,$lng?q=$lat,$lng($label)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

        binding.emailLayout.setOnClickListener {
            val email = currentSelection.emailUrl
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = email
            startActivity(intent)
        }
        binding.phoneLayout.setOnClickListener {
            val phone = currentSelection.phoneURL
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = phone
            startActivity(intent)
        }
        binding.websiteLayout.setOnClickListener {
            val website = currentSelection.webSiteUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = website
            startActivity(intent)
        }
        binding.close.setOnClickListener {
            dismiss()
        }


    }



    companion object {

        @JvmStatic
        fun newInstance() =
            ScoreFragment()
    }
}