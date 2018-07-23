package me.jessyan.mvparms.demo.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by ew on 2018/5/18.
 *  Fragment的生命周期的管理,注意此处会先调用arms依赖包下的com.jess.arms.integration.FragmentLifecycle(FragmentLifecycleCallbacks的默认实现类)的对应方法,然后再调用FragmentLifecycleCallbacksImpl的对应方法
 */

public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {
    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
        // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
        // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
        // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        //Leakcanary观察内存泄漏
        ((RefWatcher) ArmsUtils
                .obtainAppComponentFromContext(f.getActivity())
                .extras()
                .get(IntelligentCache.KEY_KEEP + RefWatcher.class.getName()))
                .watch(f);
    }
}
