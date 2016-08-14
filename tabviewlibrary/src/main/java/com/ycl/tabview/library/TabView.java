package com.ycl.tabview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yaochangliang on 2016/8/13 17:36
 * 邮箱：yaochangliang159@sina.com
 */
public class TabView extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int LMP = LinearLayout.LayoutParams.MATCH_PARENT;
    /**
     * the TextView selected color,default is orange
     */
    private int mTextViewSelColor;
    /**
     * the TextView unselected color,default is black
     */
    private int mTextViewUnSelColor;
    /**
     * the TabView background color,default is white
     */
    private int mTabViewBackgroundColor;
    /**
     * the TabView`s height,default is 52dip
     */
    private int mTabViewHeight;
    /**
     * Spacing between the icon and textview,default is 2dip
     */
    private int mImageViewTextViewMargin;
    /**
     * the textview`s size,default is 14sp
     */
    private int mTextViewSize;
    /**
     * the imageview`s width,default width  is 30dip
     */
    private int mImageViewWidth;
    /**
     * the imageview`s height,default height is 30dip
     */
    private int mImageViewHeight;
    /**
     * the child inside tabview
     */
    private List<TabViewChild> mTabViewChildList;
    /**
     * the tabview`s location,default is bottom
     */
    private int mTabViewGravity=Gravity.BOTTOM;
    /**
     * which tabchild to show,default is 0
     */
    private int mTabViewDefaultPosition=0;
    private LinearLayout tabview;
    private List<Integer> unselectedIconList;
    private FrameLayout mFragmentContainer;
    private FragmentManager mSupportFragmentManager;
    private Fragment mFragments[];
    private int index = 0;
    private int currentTabIndex;

    public TabView(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }


    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initView(context);
    }


    private void initDefaultAttrs(Context context){
        mTextViewSelColor= Color.rgb(252,88,17);
        mTextViewUnSelColor= Color.rgb(129,130,149);
        mTabViewBackgroundColor= Color.rgb(255,255,255);
        mTabViewHeight=TabViewUtil.dp2px(context,52);
        mImageViewTextViewMargin=TabViewUtil.dp2px(context,2);
        mTextViewSize=TabViewUtil.sp2px(context,14);
        mImageViewWidth=TabViewUtil.dp2px(context,30);
        mImageViewHeight=TabViewUtil.dp2px(context,30);

    }


    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }
    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.TabView_tab_textViewSelColor) {
            mTextViewSelColor = typedArray.getColor(attr, mTextViewSelColor);
        } else if (attr == R.styleable.TabView_tab_textViewUnSelColor) {
            mTextViewUnSelColor = typedArray.getColor(attr,mTextViewUnSelColor);
        } else if (attr == R.styleable.TabView_tab_tabViewBackgroundColor) {
            mTabViewBackgroundColor = typedArray.getColor(attr, mTabViewBackgroundColor);
        } else if (attr == R.styleable.TabView_tab_tabViewHeight) {
            mTabViewHeight = typedArray.getDimensionPixelSize(attr, mTabViewHeight);
        } else if (attr == R.styleable.TabView_imageViewTextViewMargin) {
            mImageViewTextViewMargin = typedArray.getDimensionPixelSize(attr, mImageViewTextViewMargin);
        } else if (attr == R.styleable.TabView_tab_textViewSize) {
            mTextViewSize = typedArray.getDimensionPixelSize(attr, mTextViewSize);
        } else if (attr == R.styleable.TabView_tab_imageViewWidth) {
            mImageViewWidth = typedArray.getDimensionPixelSize(attr, mImageViewWidth);
        } else if (attr == R.styleable.TabView_tab_imageViewHeight) {
            mImageViewHeight = typedArray.getDimensionPixelSize(attr, mImageViewHeight);
        } else if (attr == R.styleable.TabView_tab_tabViewGravity) {
            mTabViewGravity = typedArray.getInt(attr, mTabViewGravity);
        } else if (attr == R.styleable.TabView_tab_tabViewDefaultPosition) {
            mTabViewDefaultPosition = typedArray.getInteger(attr, mTabViewDefaultPosition);

        }
    }
    private void initView(Context context){
        tabview=new LinearLayout(context);
        tabview.setId(R.id.tabview_id);


        mFragmentContainer=new FrameLayout(context);
        mFragmentContainer.setId(R.id.tabview_fragment_container);
        RelativeLayout.LayoutParams fragmentContainerParams=new RelativeLayout.LayoutParams(RMP,RMP);
        RelativeLayout.LayoutParams tabviewParams=null;
        if(mTabViewGravity==Gravity.BOTTOM){
            tabviewParams=new RelativeLayout.LayoutParams(RMP,mTabViewHeight);
            tabview.setOrientation(LinearLayout.HORIZONTAL);
            tabviewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            fragmentContainerParams.addRule(RelativeLayout.ABOVE,R.id.tabview_id);
        }else if(mTabViewGravity==Gravity.LEFT){
            tabviewParams=new RelativeLayout.LayoutParams(mTabViewHeight,RMP);
            tabview.setOrientation(LinearLayout.VERTICAL);
            tabviewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            fragmentContainerParams.addRule(RelativeLayout.RIGHT_OF,R.id.tabview_id);
        }else if(mTabViewGravity==Gravity.TOP){
            tabviewParams=new RelativeLayout.LayoutParams(RMP,mTabViewHeight);
            tabview.setOrientation(LinearLayout.HORIZONTAL);
            tabviewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            fragmentContainerParams.addRule(RelativeLayout.BELOW,R.id.tabview_id);
        }else if(mTabViewGravity==Gravity.RIGHT){
            tabviewParams=new RelativeLayout.LayoutParams(mTabViewHeight,RMP);
            tabview.setOrientation(LinearLayout.VERTICAL);
            tabviewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            fragmentContainerParams.addRule(RelativeLayout.LEFT_OF,R.id.tabview_id);
        }
        tabview.setLayoutParams(tabviewParams);
        tabview.setBackgroundColor(mTabViewBackgroundColor);

        mFragmentContainer.setLayoutParams(fragmentContainerParams);
        this.addView(tabview);
        this.addView(mFragmentContainer);

    }
    private void initTabChildView(){
        tabview.removeAllViews();
        unselectedIconList=new ArrayList<>();
        mFragments=new Fragment[mTabViewChildList.size()];
        for(int i=0;i<mFragments.length;i++){
            final TabViewChild t=mTabViewChildList.get(i);
            mFragments[i]=t.getmFragment();
        }
        if(mTabViewDefaultPosition>=mTabViewChildList.size()){
            mSupportFragmentManager.beginTransaction().add(R.id.tabview_fragment_container, mFragments[0]).show(mFragments[0]).commit();
        }else{
            mSupportFragmentManager.beginTransaction().add(R.id.tabview_fragment_container, mFragments[mTabViewDefaultPosition]).show(mFragments[mTabViewDefaultPosition]).commit();
        }


        for(int i=0;i<mTabViewChildList.size();i++){
            final TabViewChild t=mTabViewChildList.get(i);
            LinearLayout tabChild=new LinearLayout(getContext());
            tabChild.setGravity(Gravity.CENTER);
            tabChild.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams tabChildParams=null;
            if(mTabViewGravity==Gravity.BOTTOM){
                tabChildParams=new LinearLayout.LayoutParams(0,LMP,1.0f);
                tabChildParams.gravity= Gravity.CENTER_HORIZONTAL;
            }else if(mTabViewGravity==Gravity.LEFT){
                tabChildParams=new LinearLayout.LayoutParams(LMP,0,1.0f);
                tabChildParams.gravity= Gravity.CENTER_VERTICAL;
            }else if(mTabViewGravity==Gravity.TOP){
                tabChildParams=new LinearLayout.LayoutParams(0,LMP,1.0f);
                tabChildParams.gravity= Gravity.CENTER_HORIZONTAL;
            }else if(mTabViewGravity==Gravity.RIGHT){
                tabChildParams=new LinearLayout.LayoutParams(LMP,0,1.0f);
                tabChildParams.gravity= Gravity.CENTER_VERTICAL;
            }
            tabChild.setLayoutParams(tabChildParams);



            final ImageView imageview=new ImageView(getContext());
            LinearLayout.LayoutParams ivParams=new LinearLayout.LayoutParams(
               mImageViewWidth, mImageViewHeight
            );
            ivParams.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;

            imageview.setLayoutParams(ivParams);
            imageview.setImageResource(t.getImageViewUnSelIcon());
            unselectedIconList.add(t.getImageViewUnSelIcon());
            tabChild.addView(imageview);

            final TextView textview=new TextView(getContext());
            textview.setText(t.getTextViewText());
            textview.setTextColor(mTextViewUnSelColor);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextViewSize);

            LinearLayout.LayoutParams textviewParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textviewParams.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
            textviewParams.topMargin=mImageViewTextViewMargin;
            textview.setLayoutParams(textviewParams);
            tabChild.addView(textview);

            tabview.addView(tabChild);


            final int currentPosition=i;
            if(mTabViewDefaultPosition>=mTabViewChildList.size()){
                if(i==0){
                    imageview.setImageResource(t.getImageViewSelIcon());
                    textview.setText(t.getTextViewText());
                    textview.setTextColor(mTextViewSelColor);
                }
            }else{
                if(mTabViewDefaultPosition==i){
                    imageview.setImageResource(t.getImageViewSelIcon());
                    textview.setText(t.getTextViewText());
                    textview.setTextColor(mTextViewSelColor);
                }
            }
            tabChild.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    reSetAll();
                    imageview.setImageResource(t.getImageViewSelIcon());
                    textview.setText(t.getTextViewText());
                    textview.setTextColor(mTextViewSelColor);
                    index = currentPosition;
                    showOrHide();
                    if(listener!=null){
                        listener.onTabChildClick(currentPosition,imageview,textview);
                    }
                }
            });

        }
    }
    public void setTabViewChild(List<TabViewChild> tabViewChildList, FragmentManager supportFragmentManager){
        this.mTabViewChildList=tabViewChildList;
        this.mSupportFragmentManager=supportFragmentManager;
        if(mTabViewDefaultPosition>=tabViewChildList.size()){
            index=0;
            currentTabIndex=0;
            mTabViewDefaultPosition=0;
        }
        initTabChildView();

    }
    public void setTabViewDefaultPosition(int position){
        this.mTabViewDefaultPosition=position;
        this.index=position;
        this.currentTabIndex=position;
    }
    private void reSetAll(){
        for(int i=0;i<tabview.getChildCount();i++){
            LinearLayout tabChild= (LinearLayout) tabview.getChildAt(i);
            for(int j=0;j<tabChild.getChildCount();j++){
                ImageView iv= (ImageView) tabChild.getChildAt(0);
                TextView tv= (TextView) tabChild.getChildAt(1);
                iv.setImageResource(unselectedIconList.get(i));
                tv.setTextColor(mTextViewUnSelColor);
            }
        }
    }
    private OnTabChildClickListener listener=null;
    public void setOnTabChildClickListener(OnTabChildClickListener l){
        listener=l;
    }
    public interface OnTabChildClickListener{
         void onTabChildClick(int position,ImageView imageView,TextView textView);
    }


    private void showOrHide() {
        if (currentTabIndex != index) {
            FragmentTransaction trx = mSupportFragmentManager.beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.tabview_fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;
    }
    public void setTextViewSelectedColor(int color){
        this.mTextViewSelColor=color;
    }
    public void setTextViewUnSelectedColor(int color){
        this.mTextViewUnSelColor=color;
    }
    public void setTabViewBackgroundColor(int color){
        this.mTabViewBackgroundColor=color;
        tabview.setBackgroundColor(color);
    }


    /**
     *
     * @param height px
     */
    public void setTabViewHeight(int height){
        this.mTabViewHeight=height;
    }


    /**
     *
     * @param margin px
     */
    public void setImageViewTextViewMargin(int margin){
        this.mImageViewTextViewMargin=margin;
    }
    public void setTextViewSize(int size){
        this.mTextViewSize= TabViewUtil.sp2px(getContext(),size);
    }
    public void setImageViewWidth(int width){
        this.mImageViewWidth=width;
    }
    public void setImageViewHeight(int height){
        this.mImageViewHeight=height;
    }
    public void setTabViewGravity(int gravity){
        this.mTabViewGravity=gravity;
    }


}
