package com.netease.study.exercise.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.netease.study.exercise.R;
import com.netease.study.exercise.utils.DeviceUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by netease on 15/7/24. Beifeng bwifeng@163.com
 */
public class FlashView extends FrameLayout {
    private Context context;
    private ImageHandler mhandler = new ImageHandler(new WeakReference<FlashView>(this));
    private List<String> imageUris;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private FlashViewListener mFlashViewListener;/*向外提供点击接口*/
    private boolean isTwo = false;/*两张图片轮播做特殊处理*/
    private int pos;


    public FlashView(Context context) {
        this(context, null);
    }

    public FlashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取该自定义控件自定义的属性
        this.context = context;
         /*        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.FlashView);
         effect = mTypedArray.getInt(R.styleable.FlashView_effect, 2);*/
        initUI(context);
        if (!(imageUris.size() <= 0)) setImageUris(imageUris);/**/
    }

    /**
     * 设置监听 @param mFlashViewListener
     */
    public void setOnPageClickListener(FlashViewListener mFlashViewListener) {
        this.mFlashViewListener = mFlashViewListener;
    }

    private void initUI(Context context) {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<ImageView>();
        imageUris = new ArrayList<String>(); /*        imageLoaderTools = ImageLoaderTools.getInstance(context.getApplicationContext());*/
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager); /*mFlashViewListener必须实例化*/
    }

    public List<String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(List<String> imageuris) {
        if (imageUris.size() > 0) {
            imageUris.clear();
            imageViewsList.clear();
            dotViewsList.clear();
            mLinearLayout.removeAllViews();
        }
        if (imageuris.size() <= 0) {/* 如果得到的图片张数为0，则增加一张默认的图片*/
            imageUris.add("drawable://" + R.drawable.defaultflashview);
        } else if (imageuris.size() == 2) {
            isTwo = true;
            imageUris.addAll(imageuris);
            imageUris.addAll(imageuris);
        } else {
            isTwo = false;
            imageUris.addAll(imageuris);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 0);
        for (int i = 0; i < imageUris.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Uri uri = Uri.parse(imageUris.get(i));

            Glide.with(context).load(uri + "?thumbnail=600x500")
                    .placeholder(R.drawable.shape_line_bg)
                    .override(DeviceUtils.dp2px(300), DeviceUtils.dp2px(250))
                    .centerCrop()
                    .into(imageView);

            imageViewsList.add(imageView);

            ImageView viewDot = new ImageView(getContext());
            if (i == 0) viewDot.setBackgroundResource(R.drawable.dot_white);
            else viewDot.setBackgroundResource(R.drawable.dot_light);
            viewDot.setLayoutParams(lp);
            if (isTwo)/*为两张图片时加入的判断*/ if (i > 1) {
            } else {
                dotViewsList.add(viewDot);
                mLinearLayout.addView(viewDot);
            }
            else {
                dotViewsList.add(viewDot);
                mLinearLayout.addView(viewDot);
            }
        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        /****/setPageTransformer(true, new ParallaxPageTransformer()); /*设置轮播效果*/

        if (imageUris.size() < 1) {/*图片小于等于1张时，不轮播*/
        }
        else if (imageUris.size() == 1) {/*图片等于1张时，不显示圆点*/
            dotViewsList.get(0).setVisibility(View.GONE);
        } else { /* 利用反射修改自动轮播的动画持续时间*/
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
                field.set(mViewPager, scroller);
                scroller.setmDuration(500);
                mViewPager.setCurrentItem(100 * imageViewsList.size());
                mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 切换轮播小点的显示 @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < dotViewsList.size(); i++)
            if (i == selectItems % dotViewsList.size())
                dotViewsList.get(i).setBackgroundResource(R.drawable.dot_white);
            else dotViewsList.get(i).setBackgroundResource(R.drawable.dot_light);
    }

    /**
     * 数据适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {

        }

        @Override
        public Object instantiateItem(View container, int position) {
            position = position % imageViewsList.size();
            if (position < 0) {
                position = position + imageViewsList.size();

            }

            if (isTwo) {
                pos = position % 2;
            } else {
                pos = position;
            }
            final int posclick = pos;
            View view = imageViewsList.get(position);

            view.setTag(position);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mFlashViewListener != null) {
                        mFlashViewListener.onClick(posclick);
                    } else {

                    }

                }
            });
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewPager pager = (ViewPager) vp;
                pager.removeView(view);
            }
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (imageUris.size() <= 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    /**
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);

                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            mhandler.sendMessage(Message.obtain(mhandler, ImageHandler.MSG_PAGE_CHANGED, pos, 0));
            setImageBackground(pos);
        }
    }

    /**
     * 设置轮播效果
     * @param b
     * @param ParallaxPageTransformer 平行
     */
    private void setPageTransformer(boolean b, PageTransformer ParallaxPageTransformer) {
        mViewPager.setPageTransformer(b, ParallaxPageTransformer);
    }

    /**
     * 设置加速度 ，通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间
     */

    public static class FixedSpeedScroller extends Scroller {
        private int mDuration = 5000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }


    private static class ImageHandler extends Handler {

        protected static final int MSG_UPDATE_IMAGE = 1;

        protected static final int MSG_KEEP_SILENT = 2;

        protected static final int MSG_BREAK_SILENT = 3;

        protected static final int MSG_PAGE_CHANGED = 4;

        protected static final long MSG_DELAY = 8000;

        private WeakReference<FlashView> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<FlashView> wk) {
            weakReference = wk;
//            System.out.println("dsfdsfdsf:::" + currentItem);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FlashView activity = weakReference.get();
            if (activity == null) {
                return;
            }
            if (activity.mhandler.hasMessages(MSG_UPDATE_IMAGE)) {
                if (currentItem > 0)// 这里必须加入currentItem>0的判断，否则不能完美的自动轮播
                {
                    activity.mhandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.mViewPager.setCurrentItem(currentItem);
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    break;
                case MSG_BREAK_SILENT:
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                default:
                    break;
            }
        }
    }
}
