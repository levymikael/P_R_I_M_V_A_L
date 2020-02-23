package com.evalutel.primval_desktop;

import com.evalutel.primval_desktop.ui_tools.MyPoint;

public interface MyTouchInterface
{

    boolean isTouched(int x, int y);

    void setPosition(int x, int y);

    MyPoint getPosition();

    float getWidth();

    float getHeight();

    boolean isActive();

    void setActive(boolean active);

}
