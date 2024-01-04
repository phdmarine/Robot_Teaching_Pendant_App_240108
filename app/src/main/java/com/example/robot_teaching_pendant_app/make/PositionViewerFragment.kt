package com.example.robot_teaching_pendant_app.make

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.robot_teaching_pendant_app.R
import com.example.robot_teaching_pendant_app.databinding.PositionViewerFragmentBinding
import com.example.robot_teaching_pendant_app.system.RobotPosition

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PositionViewerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PositionViewerFragment : Fragment() {



    interface FragmentCommunicationInterface{
        fun refreshTextView()
    }


    private var _binding: PositionViewerFragmentBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = PositionViewerFragmentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = ViewModelProviders.of(requireActivity()).get(RobotPositionViewModel::class.java)



        val xTv = binding.xTv
        val yTv = binding.yTv
        val zTv = binding.zTv
        val rxTv = binding.rxTv
        val ryTv = binding.ryTv
        val rzTv = binding.rzTv
        val joint1Tv = binding.joint1Tv
        val joint2Tv = binding.joint2Tv
        val joint3Tv = binding.joint3Tv
        val joint4Tv = binding.joint4Tv


        xTv.setText(" x: %.2f".format(RobotPosition.x))
        yTv.setText(" y: %.2f".format(RobotPosition.y))
        zTv.setText(" z: %.2f".format(RobotPosition.z))
        rxTv.setText(" Rx: %.2f".format(RobotPosition.Rx))
        ryTv.setText(" Ry: %.2f".format(RobotPosition.Ry))
        rzTv.setText(" Rz: %.2f".format(RobotPosition.Rz))
        joint1Tv.setText(" joint1: %.2f".format(RobotPosition.joint1))
        joint2Tv.setText(" joint2: %.2f".format(RobotPosition.joint2))
        joint3Tv.setText(" joint3: %.2f".format(RobotPosition.joint3))
        joint4Tv.setText(" joint4: %.2f".format(RobotPosition.joint4))

    }



    fun setTextView(){
        binding.xTv.setText(" x: %.2f".format(RobotPosition.x))
        binding.yTv.setText(" y: %.2f".format(RobotPosition.y))
        binding.zTv.setText(" z: %.2f".format(RobotPosition.z))
        binding.rxTv.setText(" Rx: %.2f".format(RobotPosition.Rx))
        binding.ryTv.setText(" Ry: %.2f".format(RobotPosition.Ry))
        binding.rzTv.setText(" Rz: %.2f".format(RobotPosition.Rz))
        binding.joint1Tv.setText(" joint1: %.2f".format(RobotPosition.joint1))
        binding.joint2Tv.setText(" joint2: %.2f".format(RobotPosition.joint2))
        binding.joint3Tv.setText(" joint3: %.2f".format(RobotPosition.joint3))
        binding.joint4Tv.setText(" joint4: %.2f".format(RobotPosition.joint4))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PositionViewerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PositionViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}