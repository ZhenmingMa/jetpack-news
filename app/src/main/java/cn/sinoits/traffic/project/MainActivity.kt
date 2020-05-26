package cn.sinoits.traffic.project

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cn.sinoits.traffic.project.page.base.BaseActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_container)
//        setupActionBarWithNavController(
//            navController,
//            AppBarConfiguration(
//                setOf(R.id.newsFragment, R.id.jokeFragment, R.id.photoFragment
////                    ,R.id.AIFragment
//                )
//            )
//        )
        bottom_bar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, args ->
            bottom_bar.visibility = when (destination.id) {
                R.id.newsFragment, R.id.jokeFragment, R.id.photoFragment
//                ,R.id.AIFragment
                -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            navController.popBackStack()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (GSYVideoManager.isFullState(this)) {
            GSYVideoManager.backFromWindowFull(this)
            return
        }
        //解决navigationUI默认返回到
        // By default, the back stack will be popped back to the navigation graph's start destination.
        when (navController.currentDestination?.id) {
            R.id.newsFragment, R.id.jokeFragment, R.id.photoFragment
//                ,R.id.AIFragment
            -> finish()
            else -> super.onBackPressed()
        }


    }


}
