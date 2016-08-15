# Android-TabView
这是一个封装了android常用的底部导航栏+fragment的库，用这个库，我们可以几行代码就搞定大多数APP的主界面的布局

![image](https://github.com/yaochangliang159/Android-TabView/raw/master/screenshot/image_left.jpg)
![image](https://github.com/yaochangliang159/Android-TabView/raw/master/screenshot/image_top.jpg)
![image](https://github.com/yaochangliang159/Android-TabView/raw/master/screenshot/image_right.jpg)
![image](https://github.com/yaochangliang159/Android-TabView/raw/master/screenshot/image_bottom.jpg)
##How To Use?
在Android Studio项目的build.gradle里,加入以下代码:
```Java
    compile 'com.ycl.tabview.library:tabviewlibrary:1.0'
```
Sync项目以后，引入这个库就成功了~~~
###Step 1:
####在xml中,加入以下代码:
```Java
  <com.ycl.tabview.library.TabView
        android:id="@+id/tabView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
  </com.ycl.tabview.library.TabView>
```
###Step 2:
####为底部导航栏添加数据源:
```Java
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.tab01_sel,R.drawable.tab01_unsel,"首页",  FragmentCommon.newInstance("首页"));
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.tab02_sel,R.drawable.tab02_unsel,"分类",  FragmentCommon.newInstance("分类"));
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.tab03_sel,R.drawable.tab03_unsel,"资讯",  FragmentCommon.newInstance("资讯"));
        TabViewChild tabViewChild04=new TabViewChild(R.drawable.tab04_sel,R.drawable.tab04_unsel,"购物车",FragmentCommon.newInstance("购物车"));
        TabViewChild tabViewChild05=new TabViewChild(R.drawable.tab05_sel,R.drawable.tab05_unsel,"我的",  FragmentCommon.newInstance("我的"));
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
```
是不是看起来感觉好复杂？。。。其实就是new了5个实体类，把这5个实体类添加到了一个集合里，我们最终想要的是tabViewChildList这个集合,每个实体类的构造方法有四个参数:<br>
* 第一个参数：导航栏上面的某一个tab被点击时候，相应的切换的图片
* 第二个参数：导航栏上面的tab没有被被点击时候，相应的切换的图片
* 第三个参数：导航栏上面的某一个tab的文字显示
* 第四个参数：导航栏上面的某一个tab对应的Fragment对象，传进来就可以

###Step 3:
####为TabView设置数据源:
```Java
 tabView.setTabViewChild(tabViewChildList,getSupportFragmentManager());
```
一共需要三步，就OK了，以一种最简单的方式就运行出来效果了，当然，还能自定义很多属性<br>

###在JAVA代码里自定义
```Java
        tabView.setTextViewSelectedColor(Color.BLUE);
        tabView.setTextViewUnSelectedColor(Color.BLACK);
        tabView.setTabViewBackgroundColor(Color.YELLOW);
        tabView.setTabViewHeight(dip2px(52));
        tabView.setImageViewTextViewMargin(2);
        tabView.setTextViewSize(14);
        tabView.setImageViewWidth(dip2px(30));
        tabView.setImageViewHeight(dip2px(30));
        tabView.setTabViewGravity(Gravity.TOP);
        tabView.setTabViewDefaultPosition(2);
```
###在xml中自定义:
```Java
 <com.ycl.tabview.library.TabView
        android:id="@+id/tabView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:tab_textViewSelColor="#ff0000"
        app:tab_textViewUnSelColor="#00ff00"
        app:tab_tabViewBackgroundColor="#0000ff"
        app:imageViewTextViewMargin="2dip"
        app:tab_imageViewHeight="30dip"
        app:tab_imageViewWidth="30dip"
        app:tab_tabViewDefaultPosition="1"
        app:tab_tabViewGravity="right"
        app:tab_tabViewHeight="52dip"
        app:tab_textViewSize="14sp"
        >
    </com.ycl.tabview.library.TabView>
```
详细的使用方法在DEMO里面都演示啦,如果你觉得这个库还不错,请赏我一颗star吧~~~
