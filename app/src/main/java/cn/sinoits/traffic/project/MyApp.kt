package cn.sinoits.traffic.project

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import cn.sinoits.traffic.project.data.db.AppDatabase
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils

class MyApp:Application(),ViewModelStoreOwner {

    private var mAppViewModelStore:ViewModelStore? = null
    private var mFactory: ViewModelProvider.Factory? = null

    override fun onCreate() {
        super.onCreate()
        initUtils()
        AppDatabase.getInstance(this)
        mAppViewModelStore = ViewModelStore()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun getAppViewModelStoreProvider(activity:Activity):ViewModelProvider{
        if (mFactory == null){
            mFactory = ViewModelProvider.AndroidViewModelFactory(this)
        }
        return ViewModelProvider(activity.applicationContext as MyApp,mFactory!!)
    }

    fun initUtils(){
        Utils.init(this)
        LogUtils.getConfig().apply {
            globalTag = "mazhenming"
            dir = defaultDir
            isLog2FileSwitch = true
        }

        CrashUtils.OnCrashListener { crashInfo, e ->
            LogUtils.e(crashInfo)
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

}