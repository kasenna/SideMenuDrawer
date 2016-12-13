package karpen74us.sidemenudrawer;

/*
RippleImageButton.java
Copyright (C) 2016  Max Karpenkov

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;


public class RippleImageButton extends ImageButton {
    private float duration = 50;
    private float speed = 1;
    private float radius = 0;
    private Paint paint = new Paint();
    private float endRadius = 0;
    private float rippleX = 0;
    private float rippleY = 0;
    private int width = 0;
    private int height = 0;
    private OnClickListener clickListener = null;
    private OnLongClickListener onlongclickListener = null;
    private Handler handler;
    private int touchAction;
    private RippleImageButton thisRippleView = this;
    private boolean long_pressed = false;
    private Runnable mlongPressed = new Runnable() {
        public void run() {
            if (onlongclickListener!=null){
                onlongclickListener.onLongClick(thisRippleView);
                long_pressed = true;
            }
        }
    };
    private boolean ripple_then_click = true;

    public RippleImageButton(Context context) {
        this(context, null, 0);
    }

    public RippleImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode())
            return;
        handler = new Handler();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if(radius > 0 && radius < endRadius) {
            canvas.drawCircle(rippleX, rippleY, radius, paint);
            if(touchAction == MotionEvent.ACTION_UP)
                invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        rippleX = event.getX();
        rippleY = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (isEnabled()&&isClickable()&&isLongClickable()) {
                    handler.removeCallbacks(mlongPressed);
                    getParent().requestDisallowInterceptTouchEvent(false);
                    touchAction = MotionEvent.ACTION_UP;
                    radius = 1;
                    endRadius = Math.max(Math.max(Math.max(width - rippleX, rippleX), rippleY), height - rippleY);
                    speed = endRadius / duration * 10;
                    if (!ripple_then_click) {
                        if (!long_pressed) {
                            if (clickListener != null) {
                                clickListener.onClick(thisRippleView);
                            }
                        }
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (radius < endRadius) {
                                radius += speed;
                                paint.setAlpha(50 * (int) (radius / endRadius) + 50);
                                handler.postDelayed(this, 1);
                            } else if (ripple_then_click) {
                                if (!long_pressed) {
                                    if (clickListener != null) {
                                        clickListener.onClick(thisRippleView);
                                    }
                                }
                            }
                        }
                    }, 1);
                    invalidate();
                    break;
                }
            }
            case MotionEvent.ACTION_CANCEL: {
                if (isEnabled()&&isClickable()&&isLongClickable()) {
                    handler.removeCallbacks(mlongPressed);
                    long_pressed = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    touchAction = MotionEvent.ACTION_CANCEL;
                    radius = 0;
                    invalidate();
                    break;
                }
            }
            case MotionEvent.ACTION_DOWN: {
                if (isEnabled()&&isClickable()&&isLongClickable()) {
                    if (onlongclickListener != null) {
                        handler.postDelayed(mlongPressed, 500);
                    }
                    long_pressed = false;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    touchAction = MotionEvent.ACTION_UP;
                    radius = 0;
                    invalidate();
                    return true;
                }
            }
            case MotionEvent.ACTION_MOVE: {
                if (isEnabled()&&isClickable()&&isLongClickable()) {
                    handler.removeCallbacks(mlongPressed);
                    long_pressed = false;
                    if (rippleX < 0 || rippleX > width || rippleY < 0 || rippleY > height) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        touchAction = MotionEvent.ACTION_CANCEL;
                        radius = 0;
                        invalidate();
                        break;
                    } else {
                        touchAction = MotionEvent.ACTION_MOVE;
                        invalidate();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        clickListener = l;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        onlongclickListener = l;
    }

    public void setRippleColor(int color){
        paint.setColor(color);
    }

    public void setRippleDuration(float dur){
        duration = dur;
    }

    public void setRippleThenClick(boolean state){
        ripple_then_click = state;
    }

}
