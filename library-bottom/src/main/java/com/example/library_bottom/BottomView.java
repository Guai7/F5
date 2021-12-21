package com.example.library_bottom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * +----------------------------------------------------------------------
 * | 项   目: mvp
 * +----------------------------------------------------------------------
 * | 包   名: com.bawei.bootombar
 * +----------------------------------------------------------------------
 * | 类   名: BottomView
 * +----------------------------------------------------------------------
 * | 时　　间: 2021/9/17 9:55
 * +----------------------------------------------------------------------
 * | 代码创建: 王益德
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 **/
public class BottomView extends FrameLayout implements View.OnClickListener{

    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams;

    private List<View> viewList;
    private List<BottomItem> bottomItems;

    private int unSelectedColor = Color.parseColor("#B8B2B1");
    private int selectedColor = Color.WHITE;
    private int backgroundColor = Color.DKGRAY;
    private Typeface textTypeface = Typeface.DEFAULT;
    private int touchBackgroundColor = Color.parseColor("#333333");

    private int selectedTextSize = 16;
    private int unSelectedTextSize = 14;

    private ViewGroup.LayoutParams ImageParam;
    private FrameLayout frameLayout;


    /**
     * 构造方法
     */
    public BottomView(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * 自定义属性构造
     */
    public BottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //自定义属性
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomView);
        int color = array.getColor(R.styleable.BottomView_selectedTextColor, 0);
        if (color!=0){
            selectedColor = color;
        }

        int color1 = array.getColor(R.styleable.BottomView_unSelectedTextColor, 0);
        if (color1!=0){
            unSelectedColor = color1;
        }

        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_bottombar, this, true);

        linearLayout = inflate.findViewById(R.id.ll_container);
        frameLayout = inflate.findViewById(R.id.container);

        frameLayout.setBackgroundColor(backgroundColor);

        layoutParams = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.gravity = Gravity.CENTER;

        ImageParam = new ViewGroup.LayoutParams(50,50);

        viewList = new ArrayList<>();
        bottomItems = new ArrayList<>();
    }

    /**
     * 添加item到bottomItems
     */
    public BottomView addItem(BottomItem item){
        bottomItems.add(item);
        return this;
    }

    /**
     * 设置选中字体颜色
     */
    public BottomView setSelectedTextColor(@ColorInt int color){
        selectedColor = color;
        return this;
    }

    /**
     * 设置未选中字体颜色
     */
    public BottomView setUnSelectedTextColor(@ColorInt int color){
        unSelectedColor = color;
        return this;
    }

    /**
     * 设置背景颜色
     */
    public BottomView setBottomBackgroundColor(@ColorInt int color){
        backgroundColor = color;
        frameLayout.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 设置字体
     */
    public BottomView setTypeface(Typeface textTypeface){
        this.textTypeface = textTypeface;
        return this;
    }

    /**
     * 设置选中字体大小
     */
    public BottomView setSelectedTextSize(int textSize){
        selectedTextSize = textSize;
        return this;
    }

    /**
     * 设置未选中字体大小
     */
    public BottomView setUnSelectedTextSize(int textSize){
        unSelectedTextSize = textSize;
        return this;
    }

    /**
     * 设置触摸时的条目背景颜色
     */
    public BottomView setTouchBackgroundColor(int color){
        touchBackgroundColor = color;
        return this;
    }

    //除了第一次选中 往后的选中的动画效果
    private void select(){

        if (currentPosition == previousPosition){
            return;
        }

        View currentPositionView = viewList.get(currentPosition);
        View previousPositionView = viewList.get(previousPosition);

        if (currentPositionView instanceof TextView){
            @SuppressLint("Recycle") ValueAnimator valueAnimator = ValueAnimator.ofFloat(unSelectedTextSize,selectedTextSize);
            valueAnimator.setDuration(100);
            valueAnimator.addUpdateListener(animation -> {
                Float animatedValue = (Float) animation.getAnimatedValue();
                ((TextView) currentPositionView).setTextSize(animatedValue);
                currentPositionView.requestLayout();
            });
            ((TextView) currentPositionView).setTextColor(selectedColor);
            valueAnimator.start();
        }

        if(previousPositionView instanceof TextView){
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(selectedTextSize, unSelectedTextSize);
            valueAnimator.setDuration(100);
            valueAnimator.addUpdateListener(animation -> {
                Float animatedValue = (Float) animation.getAnimatedValue();
                ((TextView) previousPositionView).setTextSize(animatedValue);
                previousPositionView.requestLayout();
            });
            ((TextView) previousPositionView).setTextColor(unSelectedColor);
            valueAnimator.start();
        }
    }

    /**
     * 默认选中颜色
     */
    @SuppressLint("ResourceAsColor")
    private void defaultSelected(int position){

        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            if (view instanceof TextView){
                if (position == i){
                    ((TextView) view).setTextColor(selectedColor);
                    ((TextView) view).setTextSize(selectedTextSize);
                    ((TextView) view).setTypeface(textTypeface);
                }else {
                    ((TextView) view).setTextColor(unSelectedColor);
                    ((TextView) view).setTextSize(unSelectedTextSize);
                }
            }

            if (view instanceof ImageView){
                if (position == i){
                    ((ImageView) view).setImageTintList(ColorStateList.valueOf(selectedColor));
                    ((ImageView) view).setPadding(48,48,48,48);
                }else {
                    ((ImageView) view).setImageTintList(ColorStateList.valueOf(unSelectedColor));
                    ((ImageView) view).setPadding(56,56,56,56);
                }
            }
        }
    }


    /**
     * 最终建造方法
     */
    @SuppressLint("ClickableViewAccessibility")
    public void build(){
        for (int i = 0; i < bottomItems.size(); i++) {
            String msg = bottomItems.get(i).getMsg();
            Drawable drawable = bottomItems.get(i).getDrawable();
            if (msg != null) {

                TextView textView = new TextView(getContext());
                textView.setOnClickListener(this);
                textView.setText(msg);
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(16);

                //设置触摸监听事件
                textView.setOnTouchListener((v, event) -> {
                    //手指 初次接触到屏幕 时触发
                    if (event.getAction()==MotionEvent.ACTION_DOWN){
                        textView.setBackgroundColor(touchBackgroundColor);
                    }

                    //手指 离开屏幕 时触发
                    if (event.getAction()==MotionEvent.ACTION_UP){
                        textView.setBackgroundColor(backgroundColor);
                    }
                    return false;
                });

                textView.setTag(R.id.itemTag,i);
                textView.setTextColor(unSelectedColor);

                linearLayout.addView(textView);
                viewList.add(textView);
            }

            if (drawable != null){
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(layoutParams);
                imageView.setImageDrawable(drawable);
                imageView.setLayoutParams(layoutParams);
                imageView.setPadding(56,56,56,56);
                imageView.setImageTintList(ColorStateList.valueOf(unSelectedColor));
                imageView.setTag(R.id.itemTag,i);
                imageView.setOnClickListener(this);
                //设置触摸监听事件
                imageView.setOnTouchListener((v, event) -> {
                    //手指 初次接触到屏幕 时触发
                    if (event.getAction()==MotionEvent.ACTION_DOWN){
                        imageView.setBackgroundColor(touchBackgroundColor);
                    }

                    //手指 离开屏幕 时触发
                    if (event.getAction()==MotionEvent.ACTION_UP){
                        imageView.setBackgroundColor(backgroundColor);
                    }
                    return false;
                });
                linearLayout.addView(imageView);
                viewList.add(imageView);
            }
        }
        defaultSelected(0);
    }


    private OnItemClickListener listener;

    private int previousPosition = -1;
    private int currentPosition = 0;

    private static final String TAG = "BottomView";

    @Override
    public void onClick(View v) {



        int tag = (int) v.getTag(R.id.itemTag);

        //记录上一次点击的位置
        previousPosition = currentPosition;

        //更新当前位置
        currentPosition = tag;

        //更改点击事件
        if (listener!=null){
            listener.selected(bottomItems.get(currentPosition),previousPosition,currentPosition);
        }

//        defaultSelected(currentPosition);
        select();
    }

    /**
     * 监听position事件
     */
    public interface OnItemClickListener{
        void selected(BottomItem item,int previousPosition,int currentPosition);
    }

    public BottomView setOnClickItemListener(OnItemClickListener listener){
        this.listener = listener;
        return this;
    }
}
