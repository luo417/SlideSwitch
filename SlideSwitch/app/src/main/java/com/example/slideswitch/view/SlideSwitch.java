package com.example.slideswitch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hailin on 2016/12/15.
 */

public class SlideSwitch extends View {
    private Bitmap switchBackground;
    private Bitmap switchSliding;
    private SwitchState switchState; //用于记录滑动开关的状态
    private int currentX; //用于记录滑动时的x坐标
    private boolean isSliding = false; //是否正在滑动

    public SlideSwitch(Context context) {
        super(context);
    }

    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置滑动开关的背景图片
     * @param switchBackgroundResource
     */
    public void setSwitchBackgroundResource(int switchBackgroundResource) {
        switchBackground = BitmapFactory.decodeResource(getResources(), switchBackgroundResource);
    }

    /**
     * 设置滑动开关的滑块
     * @param switchSlidingResource
     */
    public void setSwitchSlidingResource(int switchSlidingResource) {
        switchSliding = BitmapFactory.decodeResource(getResources(), switchSlidingResource);
    }

    //用于记录滑动开关状态的枚举常量
    public enum SwitchState {
        Open, Close
    }

    /**
     * 设置滑动开关的状态
     * @param state
     */
    public void setSwitchState(SwitchState state) {
        switchState = state;
    }

    /**
     * 设置当前控件显示在屏幕上的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBackground.getWidth(), switchBackground.getHeight());
    }

    /**
     * 绘制自己显示在屏幕上的样子
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制滑动开关的背景
        canvas.drawBitmap(switchBackground, 0, 0, null);

        //根据当前滑动的X坐标绘制滑动开关的滑块
        if (isSliding) {
            int excursion = currentX - switchSliding.getWidth()/2; //滑块左边的偏移量
            if (excursion < 0) {
                excursion = 0;
            }
            if (excursion > switchBackground.getWidth() - switchSliding.getWidth()) {
                excursion = switchBackground.getWidth() - switchSliding.getWidth();
            }
            canvas.drawBitmap(switchSliding, excursion, 0, null);
        } else {
            //抬起时，根据switchState的值设置滑动块的位置
            if (switchState == SwitchState.Open) {
                canvas.drawBitmap(switchSliding, switchBackground.getWidth() - switchSliding.getWidth(), 0, null);
            } else {
                canvas.drawBitmap(switchSliding, 0, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                int centerX = switchBackground.getWidth() / 2;
                if (currentX < centerX) {
                    if (switchState != SwitchState.Close) {
                        switchState = SwitchState.Close;

                        if (listener != null) {
                            listener.onSwitchStateChange(switchState);
                        }
                    }
                } else {
                    if (switchState != SwitchState.Open) {
                        switchState = SwitchState.Open;

                        if (listener != null) {
                            listener.onSwitchStateChange(switchState);
                        }
                    }
                }

                break;
        }
        invalidate();  //重绘，会调用onDraw方法
        return true;
    }

    private OnSWitchStateChangeListener listener;
    public void setOnSwitchStateChangeListener(OnSWitchStateChangeListener listener){
        this.listener = listener;
    }

    //通过接口将滑动开关的状态暴露给使用者
    public interface OnSWitchStateChangeListener{
        void onSwitchStateChange(SwitchState switchState);
    }
}
