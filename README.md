# APP模块
1. **GlobalConfiguration:**  App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中,BaseURL的设定,日志框架的选型和开关,Application/Activity/Fragment的生命周期注入实现类在这里绑定,各种配置信息的设置就在这里
2. **AppLifecyclesImpl:** 用于整个APP的Application的管理,AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑,初始化日志系统/设置ButterKnife的模式/初始化LeakCanary内存泄漏检测,**常用于第三方的初始化** ;
3. **ActivityLifecycleCallbacksImpl:**  用于所有Activity生命周期的管理, ActivityLifecycleCallbacks 的所有方法都会在 Activity (包括三方库) 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
4. **FragmentLifecycleCallbacksImpl:**  Fragment的生命周期的管理,注意此处会先调用arms依赖包下的com.jess.arms.integration.FragmentLifecycle(FragmentLifecycleCallbacks的默认实现类)的对应方法,然后再调用FragmentLifecycleCallbacksImpl的对应方法
5. **GlobalHttpHandlerImpl:** 网络请求的实现类,这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作, 可以先客户端一步拿到每一次http请求的结果
6. **ResponseErrorListenerImpl:** 网络请求响应错误时的触发实现类,可根据不同的错误码进行统一响应,如弹出提示
7. **指定 Application:**  本框架想要正常运行需要使用框架提供的 BaseApplication ,当然您也可以自定义一个 Application 继承于它,也可以不用继承,直接将 BaseApplication 的代码复制到您自定义的 Application 里(里面只有几行代码),但是我并不推荐您使用后面的两种方式,因为本框架已经向开发者提供了 ConfigModule#injectAppLifecycle 方法,可以在运行时动态的向 BaseApplication 中插入任意代码,这样即使您不需要自定义 Application ,也可以做到初始化自己的业务
8. **关于自定义View:** 实现 AndroidAutoLayout 规范的 {@link CardView}可使用 MVP_generator_solution 中的 AutoView 模版生成各种符合 AndroidAutoLayout 规范的 {@link View}
9. **一键生成Fragment/Activity:** new的时候选择MVPArms全家桶,若需修改模板则在目录D:\Program Files\Android\Android Studio\plugins\android\lib\templates\activities\MVPArmsTemplate\root\src\app_package修改对应的文件
6. **Activity/Present的方法执行顺序:**  Activity的onCreate befor -> Presenter的onStart befor -> Presenter的onStart -> Presenter的构造方法(因为在父类的构造方法中调用的onStart) -> Activity的initData ->Activity的onCreate
6. **关于图片的选择:** 在zeplin上选择平台后在右侧选择Density:mdpi ,里面的设计尺寸就能对应上了,图片需要下载svg图,在使用时使用AS处理修改width和viewportHeight相同,命名在前面添加svg_,也可以使用IOS的三倍图放在xxhdpi文件夹下
6. **:**



# arms依赖包
1. **GlobalConfigModule:** 框架独创的建造者模式 ,可向框架中注入外部配置的自定义参数
2. **IView/IPresenter/IModel:** 框架要求框架中的每个 View/Presenter/Model 都需要实现此类, 以满足规范,对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
3. **GlideConfiguration:** 用于配置缓存文件夹,切换图片请求框架等操作,图片缓存文件最大值的定义
3. **GlideArms:** 可以当成Glide来使用
3. **:**


![Logo](image/arms_banner_v1.0.jpg)

<p align="center">
   <a href="https://bintray.com/jessyancoding/maven/MVPArms/2.4.1/link">
    <img src="https://img.shields.io/badge/Jcenter-v2.4.1-brightgreen.svg?style=flat-square" alt="Latest Stable Version" />
  </a>
  <a href="https://travis-ci.org/JessYanCoding/MVPArms">
    <img src="https://travis-ci.org/JessYanCoding/MVPArms.svg?branch=master" alt="Build Status" />
  </a>
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/badge/API-14%2B-blue.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square" alt="License" />
  </a>
  <a href="https://www.jianshu.com/u/1d0c0bc634db">
    <img src="https://img.shields.io/badge/Author-JessYan-orange.svg?style=flat-square" alt="Author" />
  </a>
  <a href="https://shang.qq.com/wpa/qunwpa?idkey=1a5dc5e9b2e40a780522f46877ba243eeb64405d42398643d544d3eec6624917">
    <img src="https://img.shields.io/badge/QQ群-301733278-orange.svg?style=flat-square" alt="QQ Group" />
  </a>
</p>

<p align="center">
  <a href="MVPArms.md">
    <b>中文说明</b>
  </a>
</p> 

## A common Architecture for Android Applications developing based on MVP, integrates many Open Source Projects (like Dagger2、RxJava、Retrofit ...), to make your developing quicker and easier.

## Architectural
<img src="https://github.com/JessYanCoding/MVPArms/raw/master/image/Architecture.png" width="80%" height="80%">

## Usage
> New Project (**The following steps are too cumbersome? Now you can use the [new feature (generate an app Module with one click)](https://github.com/JessYanCoding/MVPArms-Module-Template) in new projects, to avoid the cumbersome configuration of the project, to quickly open the world of MVPArms**)
>> If you are building a new project, directly to the entire project **clone** (or download), as **app** as the main **Module** (It is recommended to remove the **arms Module** and use **Gradle** to [depend](https://github.com/JessYanCoding/MVPArms/wiki#1.1) on this framework remotely for easy updates), then the package name into their own package name , **app Module** contains the package structure can be used directly, a mainstream `MVP` +` Dagger2` + `Retrofit` +` RxJava` framework so easy to build successful, and now you refer **Mvp** Package under the **UserActivity** format,[Use Template to automatically generate MVP, Dagger2 related classes](https://github.com/JessYanCoding/MVPArmsTemplate),With access to [Wiki documents](https://github.com/JessYanCoding/MVPArms/wiki) slowly grasp the framework to see more articles as soon as possible in the project to use it, in practice, learning is the fastest

> Old Project
>> [Old projects would like to introduce this framework, you can refer to the Wiki documentation, written in great detail](https://github.com/JessYanCoding/MVPArms/wiki)

## Wiki
[Detailed usage reference Wiki (**Must see!!!**)](https://github.com/JessYanCoding/MVPArms/wiki)


## Notice

* [MVPArms Learning Project](https://github.com/JessYanCoding/MVPArms/blob/master/CONTRIBUTING_APP.md)

* [Collection Box](https://github.com/JessYanCoding/MVPArms/issues/40)

* [Update Log](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)

* [Common Issues](https://github.com/JessYanCoding/MVPArms/wiki/Issues)

* The use of these technologies for the latter part of the project maintenance and iterative, especially large projects is very helpful, but is to develop a pre-write a page to write a lot of `MVP`,` Dagger2` class and interface, which is indeed a headache for the development of pre- Now the framework has been able to [Template](https://github.com/JessYanCoding/MVPArmsTemplate) automatically generate some `MVP`,` Dagger2` template code, and now we can very easily use the framework.

* Use this frame comes with automatic adaptation function, please refer to [AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout).

* This framework does not provide any third-party libraries associated with the **UI**(except for the [`AndroidAutoLayout`](https://github.com/hongyangAndroid/AndroidAutoLayout) screen adaptation scheme).


## Functionality & Libraries
1. [`Mvp` Google's official` Mvp` architecture project, which contains several different schema branches (this is the Dagger branch).](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)
2. [`Dagger2`](https://github.com/google/dagger)
3. [`RxJava`](https://github.com/ReactiveX/RxJava)
4. [`RxAndroid`](https://github.com/ReactiveX/RxAndroid)
5. [`Rxlifecycle`](https://github.com/trello/RxLifecycle)
6. [`RxCache`](https://github.com/VictorAlbertos/RxCache)
7. [`RxPermissions`](https://github.com/tbruyelle/RxPermissions)
8. [`RxErroHandler`](https://github.com/JessYanCoding/RxErrorHandler)
9. [`Retrofit`](https://github.com/square/retrofit)
10. [`Okhttp`](https://github.com/square/okhttp)
11. [`Autolayout`](https://github.com/hongyangAndroid/AndroidAutoLayout)
12. [`Gson`](https://github.com/google/gson)
13. [`Butterknife`](https://github.com/JakeWharton/butterknife)
14. [`Androideventbus`](https://github.com/hehonghui/AndroidEventBus)
15. [`Timber`](https://github.com/JakeWharton/timber)
16. [`Glide`](https://github.com/bumptech/glide)
17. [`LeakCanary`](https://github.com/square/leakcanary)

## Who is using MVPArms?

**小顶家装 工长端** | **小顶家装 工人端** | **小顶家装 材料端** | **小顶网** | **智播** |
:-------------------------------------------------------------------:|:----------:|:---------------:|:--------:|:--------------:|
[<img src="image/xiaoding_foreman_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/xiaoding_worker_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/xiaoding_material_logo.png" width="80" height="80">](http://www.dggxdjz.com) | [<img src="image/top_net_work_logo.png" width="80" height="80">](http://www.dgg.net/appload.htm) | [<img src="image/zhibo_logo.png" width="80" height="80">](http://www.zhibocloud.cn/)| 
**天天视频** | **天天直播** | **中斗通航** | **中斗祥云** | **麋鹿旅行** |
[<img src="image/tiantian_video_logo.png" width="80" height="80">](http://sj.qq.com/myapp/detail.htm?apkName=com.dzwh.ttys) | [<img src="image/tiantian_live_logo.png" width="80" height="80">](http://www.25pp.com/android/detail_7611392/) | [<img src="image/tong_hang_logo.png" width="80" height="80">](https://fir.im/3176) | <img src="image/xiang_yun_logo.png" width="80" height="80">  | [<img src="image/mi_lu_logo.png" width="80" height="80">](http://android.myapp.com/myapp/detail.htm?apkName=com.elk.tourist) | 
**汇财富** | **觅窝** | **晒墨宝** | **(In Progress App ...)** | **(Your App ...)** |
[<img src="image/hui_cai_fu_logo.png" width="80" height="80">](http://android.myapp.com/myapp/detail.htm?apkName=com.tahone.client) | [<img src="image/mi_wo_logo.png" width="80" height="80">](http://miwo.ai/) | [<img src="image/shaimobao_logo.png" width="80" height="80">](http://sj.qq.com/myapp/search.htm?kw=%E6%99%92%E5%A2%A8%E5%AE%9D)  | <img src="image/android_logo.png" width="80" height="80"> | <img src="image/android_logo.png" width="80" height="80">|

## Update
* Tuesday, 26 September 2017: [**Cache**](https://github.com/JessYanCoding/MVPArms/blob/master/arms/src/main/java/com/jess/arms/integration/cache/)
* Tuesday, 12 September 2017: [**Lifecycleable**](https://github.com/JessYanCoding/MVPArms/blob/master/arms/src/main/java/com/jess/arms/integration/lifecycle)
* Thursday, 20 July 2017: [**RetrofitUrlManager**](https://github.com/JessYanCoding/RetrofitUrlManager)
* Tuesday, 13 June 2017: [**ProgressManager**](https://github.com/JessYanCoding/ProgressManager)
* Wednesday, 31 May 2017: [**Template**](https://github.com/JessYanCoding/MVPArmsTemplate)
* Monday, 24 April 2017: [**AppDelegate**](https://github.com/JessYanCoding/MVPArms/wiki#3.12)
* Thursday, 13 April 2017: [**RepositoryManager**](https://github.com/JessYanCoding/MVPArms/wiki#2.3)
* Thursday, 15 December 2016: [**AppManager**](https://github.com/JessYanCoding/MVPArms/wiki#3.11)
* Sunday, 25 December 2016: [**GlobeConfigModule**](https://github.com/JessYanCoding/MVPArms/wiki#3.1)
* Monday, 26 December 2016: [**Version Update**](https://github.com/JessYanCoding/MVPArms/wiki#1.6)

## Acknowledgements 
Thanks to all the three libraries used in this framework **Author**, and all for the **Open Sourece** selfless contributions **Developer** and **Organizations**, so that we can better work and study, I will also spare time return to the open source community

## Donate
![alipay](image/pay_alipay.jpg) ![](image/pay_wxpay.jpg)

## About Me
* **Email**: <jess.yan.effort@gmail.com>  
* **Home**: <http://jessyan.me>
* **掘金**: <https://gold.xitu.io/user/57a9dbd9165abd0061714613>
* **简书**: <http://www.jianshu.com/u/1d0c0bc634db>

## License
``` 
 Copyright 2016, jessyan       
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
