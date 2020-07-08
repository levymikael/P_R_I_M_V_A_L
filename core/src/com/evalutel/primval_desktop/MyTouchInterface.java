package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.ui_tools.MyPoint;

public interface MyTouchInterface
{

    boolean isTouched(float x, float y);
    boolean isDragable();

    void setPosition(float x, float y);

    MyPoint getPosition();

    float getWidth();

    float getHeight();

    boolean isActive();

    void setActive(boolean active);

}
