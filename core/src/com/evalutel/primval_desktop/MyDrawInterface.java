package com.evalutel.primval_desktop;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface MyDrawInterface {

    public void myDraw(Batch batch);

    boolean isVisible();
    void setVisible(boolean visible);



}
