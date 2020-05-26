package cn.sinoits.traffic.project.page.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cn.sinoits.traffic.project.MyApp
import cn.sinoits.traffic.project.brige.ShareViewModel

abstract class BaseFragment : Fragment() {

    lateinit var mActivity: Activity
    var shareViewModel: ShareViewModel? = null

    abstract fun layoutRes(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return (activity?.applicationContext as MyApp).getAppViewModelStoreProvider(activity!!)
    }

    fun getFragmentViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, defaultViewModelProviderFactory)
    }

    fun nav(): NavController {
        return NavHostFragment.findNavController(this)
    }
}