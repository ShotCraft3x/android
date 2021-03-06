package com.e.union.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.proyectoandroid.safety.R

import kotlinx.android.synthetic.main.fragment_onboarding3.view.*

class DetalleReto : Fragment() {
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var backgroundColor: String
    private var imageResource = 0
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: LottieAnimationView
    private lateinit var layout:  RelativeLayout
    private lateinit var mFakeStatusBar:  View


    //Iniciar una nueva ruta
    private var latruta = 0.0
    private var lngruta = 0.0
    private var nombrepunto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments!!.getString(ARG_PARAM1)!!
            description = arguments!!.getString(ARG_PARAM2)!!
            imageResource = arguments!!.getInt(ARG_PARAM3)
            backgroundColor = arguments!!.getString(ARG_PARAM4)!!
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootLayout: View =
            inflater.inflate(R.layout.fragment_onboarding3, container, false)
        tvTitle = rootLayout.text_onboarding_title
        tvDescription = rootLayout.text_onboarding_description
        image = rootLayout.image_onboarding
        layout = rootLayout.layout_container
        mFakeStatusBar = rootLayout.fake_statusbar_view
        tvTitle.text = title
        tvDescription.text = description
        image.setAnimation(imageResource)
        layout.setBackgroundColor(Color.parseColor(backgroundColor))
        mFakeStatusBar.setBackgroundColor(Color.parseColor(backgroundColor))
        return rootLayout
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            title: String?,
            description: String?,
            imageResource: Int,
            backgroundColor: String
        ): DetalleReto {
            val fragment = DetalleReto()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putString(ARG_PARAM2, description)
            args.putInt(ARG_PARAM3, imageResource)
            args.putString(ARG_PARAM4, backgroundColor)
            fragment.arguments = args
            return fragment
        }
    }
}
