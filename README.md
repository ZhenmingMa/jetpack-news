# jetpack-news
项目架构图  
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width="720">

## 涉及到的组件
### Navigation 
配合BottomNavigationView底部导航栏实现，在mainActivity里监听当前的Destination来进行隐藏和显示  
### Databinding和LiveData
使用databinding绑定viewmodel，关联viewmodel和view，无需管理生命周期和非空判断，安全快速有效
### Paging
通过官方自带paging分页库组件实现无限上拉的列表，并完整的实现连下拉刷新的逻辑
本项目包含了使用数据库缓存+网络请求 和只使用网络请求两种情况
### Room
使用Room来进行数据缓存，同时实现了有关数据有效期的相关逻辑判断

## 效果图如下  
   <img src="https://raw.githubusercontent.com/mazhenming892/jetpack-news/master/screenshot/Screenshot_1590487164.png" width="320"><img src="https://raw.githubusercontent.com/mazhenming892/jetpack-news/master/screenshot/Screenshot_1590487170.png" width="320"><img src="https://raw.githubusercontent.com/mazhenming892/jetpack-news/master/screenshot/Screenshot_1590487180.png" width="320"><img src="https://raw.githubusercontent.com/mazhenming892/jetpack-news/master/screenshot/Screenshot_1590487189.png" width="320">

