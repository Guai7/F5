package com.example.library_bottom;

import android.graphics.drawable.Drawable;

/**
 * +----------------------------------------------------------------------
 * | 项   目: mvp
 * +----------------------------------------------------------------------
 * | 包   名: com.bawei.bootombar
 * +----------------------------------------------------------------------
 * | 类   名: BottomItem
 * +----------------------------------------------------------------------
 * | 时　　间: 2021/9/17 10:14
 * +----------------------------------------------------------------------
 * | 代码创建: 王益德
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 **/
public class BottomItem {
    private String msg;
    private Drawable drawable;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public BottomItem(String msg, Drawable drawable) {
        this.msg = msg;
        this.drawable = drawable;
    }
}
