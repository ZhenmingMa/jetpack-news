package cn.sinoits.traffic.project.page.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cn.sinoits.traffic.project.MyApp
import cn.sinoits.traffic.project.brige.ShareViewModel

abstract class BaseActivity:AppCompatActivity(){
     var shareViewModel: ShareViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        shareViewModel = getAppViewModelProvider().get(ShareViewModel::class.java)
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return ( application as MyApp).getAppViewModelStoreProvider(this)
    }

}