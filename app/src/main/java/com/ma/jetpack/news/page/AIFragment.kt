//package cn.sinoits.traffic.project.page
//
//import android.os.Bundle
//import android.view.View
//import cn.sinoits.traffic.project.R
//import com.ma.ai_module.BaseFragment
//import kotlinx.android.synthetic.main.fragment_ai.*
//
//class AIFragment : BaseFragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun layoutRes(): Int = R.layout.fragment_ai
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        btn_jump_wt.setOnClickListener {
//            nav().navigate(R.id.action_AIFragment_to_plateRecFragment)
//        }
//
//        btn_jump_hyper.setOnClickListener{
//            nav().navigate(R.id.action_AIFragment_to_plateHyperFragment)
//        }
//
//        btn_jump_yolov2.setOnClickListener{
//            nav().navigate(R.id.action_AIFragment_to_checkFragment)
//        }
//
//    }
//}
