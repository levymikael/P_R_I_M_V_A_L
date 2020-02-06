package com.evalutel.primval_desktop;

import com.evalutel.ui_tools.MyPoint;

public interface MyTouchInterface
{

    public boolean isTouched(int x, int y);

    public void setPosition(int x, int y);

    public MyPoint getPosition();

    public float getWidth();

    public float getHeight();

    boolean isActive();

    void setActive(boolean active);

}
