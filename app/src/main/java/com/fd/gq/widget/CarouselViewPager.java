package com.fd.gq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.fd.gq.R;
import com.fd.gq.util.LogUtil;
import com.fd.gq.util.UI;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016/12/6.
 */

public class CarouselViewPager extends RelativeLayout {
    /**
     *  加载图片过程中的背景  需要是资源id
     */
    private int laodingBackground;
    /**
     * 指示标志 样式
     * <enum name="number" value="0"/>
     * <enum name="rect_line" value="1"/>
     * <enum name="rect" value="2"/>
     * <enum name="round" value="3"/>
     */
    private int style;
    private ViewPager viewPager;
    /**
     * 底部 指示标志容器
     */
    private LinearLayout indexLayout;
    /**
     * 数据图片Url集合
     */
    private List<String> dates;
    /**
     * 除数字样式外，指示标志，记录。
     */
    private ImageView oldDot;
    /**
     * 数字样式，指示标志，记录。
     */
    private TextView oldTextDot;
    /**
     * 自动轮播时间 单位秒 默认5秒
     */
    private  int time = 5;
    /**
     * 指示标志 的高，除线性样式外，宽高皆为次
     */
    private int dotWidthAndHeight = 15;
    /**
     * 指示标志背景
     */
    private int indexBackgroud = Color.WHITE;
    /**
     * 选中后的指示标志背景
     */
    private int indexCheckBackground = Color.RED;

    private int textColor = Color.BLUE;
    /**
     * 用于存放显示图片集合
     */
    private ArrayList<ImageView>  views = new ArrayList<>();
    /**
     * 布局宽高
     */
    private int mViewWidth;
    private int mViewHeight;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){

                int nextItem = (viewPager.getCurrentItem()+1);
                viewPager.setCurrentItem(nextItem);
            }
        }
    };

    public List<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
        initViewPager();
    }

    public void setDates(String...dates){
         this.dates =  Arrays.asList(dates);
        initViewPager();
    }

    public CarouselViewPager(Context context) {
        this(context,null);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public CarouselViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.carousel_viewpager, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CarouselViewPager, defStyleAttr, 0);
        laodingBackground = a.getResourceId(R.styleable.CarouselViewPager_loading_background,android.R.color.darker_gray);
        style  = a.getInt(R.styleable.CarouselViewPager_bottom_barstyle,3);
        indexBackgroud = a.getColor(R.styleable.CarouselViewPager_index_backgroud,indexBackgroud);
        indexCheckBackground = a.getColor(R.styleable.CarouselViewPager_index_checked_backgroud,indexCheckBackground);
        dotWidthAndHeight = a.getInt(R.styleable.CarouselViewPager_index_height,dotWidthAndHeight);
        time = a.getInt(R.styleable.CarouselViewPager_auto_time,time);
        textColor = a.getColor(R.styleable.CarouselViewPager_intdex_text_color,textColor);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewWidth = UI.getScreenWidth(getContext());
        indexLayout = (LinearLayout) findViewById(R.id.carousel_index);
        viewPager = (ViewPager) findViewById(R.id.carousel_viewpager);
    }


    private void initViewPager(){
        indexLayout.removeAllViews();
        int size = dates.size();
        for(int i = 0;i < size;i++){
            ImageView imgage = new ImageView(getContext());
            imgage.setBackgroundResource(laodingBackground);
            imgage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
            imgage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(imgage);
            if(style==0){//数字
                indexLayout.addView(getDot(dotWidthAndHeight,i+1));
            }else if(style==1){//线行
                RelativeLayout.LayoutParams lp = (LayoutParams) indexLayout.getLayoutParams();
                lp.bottomMargin = 0;//当为线性时底部无边距
                lp.height = dotWidthAndHeight;
                indexLayout.setLayoutParams(lp);
                indexLayout.addView(getDot((mViewWidth/dates.size()),dotWidthAndHeight,R.drawable.rect_dot));
            }else if(style==2){//矩形
                indexLayout.addView(getDot(dotWidthAndHeight,dotWidthAndHeight,R.drawable.rect_dot));
            }else {//默认圆形
                indexLayout.addView(getDot(dotWidthAndHeight,dotWidthAndHeight,R.drawable.round_dot));
            }
        }
        if(style==0){
            oldTextDot = (TextView) indexLayout.getChildAt(0);
            oldTextDot.setTextColor(indexCheckBackground);
        }else{
            oldDot = (ImageView) indexLayout.getChildAt(0);
            oldDot.setColorFilter(indexCheckBackground, PorterDuff.Mode.MULTIPLY);
        }


        viewPager.setAdapter(new CarouselViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(style==0){
                    oldTextDot.setTextColor(textColor);
                    TextView newdot = (TextView) indexLayout.getChildAt(position%dates.size());
                    newdot.setTextColor(indexCheckBackground);
                    oldTextDot = newdot;
                }else {
                    oldDot.setColorFilter(indexBackgroud, PorterDuff.Mode.MULTIPLY);
                    ImageView newDot = (ImageView) indexLayout.getChildAt(position%dates.size());
                    newDot.setColorFilter(indexCheckBackground, PorterDuff.Mode.MULTIPLY);
                    oldDot = newDot;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 子线程 定时 切换轮播
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
            handler.postDelayed(this,time*1000);
        }
    };

    /**
     * 根据宽、高、背景得到一个除数字样式外的 指示标志
     * @param width
     * @param height
     * @param resourceId
     * @return
     */
    private ImageView getDot(int width,int height,int resourceId){
        ImageView dot = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        if(style==1){
            lp.rightMargin = 1;
        }else {
            lp.leftMargin = 3;
            lp.rightMargin = 3;
        }
        dot.setLayoutParams(lp);
        dot.setImageResource(resourceId);
        dot.setScaleType(ImageView.ScaleType.FIT_XY);
        dot.setColorFilter(indexBackgroud, PorterDuff.Mode.MULTIPLY);
        return dot;
    }

    /**
     * 根据字体大小、坐标生成一个数字样式指示标志
     * @param fontSize
     * @param position
     * @return
     */
    private TextView getDot(int fontSize,int position){
        TextView dot = new TextView(getContext());
        dot.setGravity(Gravity.CENTER);
        dot.setTextSize(fontSize);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(fontSize*4,fontSize*4);
        dot.setTextColor(textColor);
        dot.setText(position+"");
        dot.setBackgroundResource(R.drawable.round_dot);
        dot.getBackground().setColorFilter(indexBackgroud,PorterDuff.Mode.MULTIPLY);
        lp.leftMargin = 3;
        lp.rightMargin = 3;
        dot.setLayoutParams(lp);
        return dot;
    }

    /**
     * ViewPager适配器
     */
    class CarouselViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            LogUtil.e("destroyItem__"+position);
            view.removeView((View)object);
        }

        // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            position = position%dates.size();
            LogUtil.e("instantiateItem__"+position);
            ImageView v = views.get(position);
            view.addView(v);
            Glide.with(getContext()).load(dates.get(position)).into(v);
            return v;
        }
    }

    /**
     * 分别对应 Activity 或 Fragment 的生命周期
     * 启动自动轮播
     */
    public void onResume(){
        handler.postDelayed(run,time*1000);
    }
    /**
     * 分别对应 Activity 或 Fragment 的生命周期
     * 关闭自动轮播
     */
    public void onPause(){
        handler.removeCallbacks(run);
    }

}
