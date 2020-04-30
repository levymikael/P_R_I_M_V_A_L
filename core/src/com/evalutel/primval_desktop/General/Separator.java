package com.evalutel.primval_desktop.General;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Separator extends Widget
{
    private SeparatorStyle style;

    /** New separator with default style */
    public Separator () {
        style = VisUI.getSkin().get(SeparatorStyle.class);
    }

    public Separator (String styleName) {
        style = VisUI.getSkin().get(styleName, SeparatorStyle.class);
    }

    public Separator (SeparatorStyle style) {
        this.style = style;
    }

    @Override
    public float getPrefHeight () {
        return style.thickness;
    }

    @Override
    public float getPrefWidth () {
        return style.thickness;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
        style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
    }

    public SeparatorStyle getStyle () {
        return style;
    }

    static public class SeparatorStyle {
        public Drawable background;
        public int thickness;

        public SeparatorStyle () {
        }

        public SeparatorStyle (SeparatorStyle style) {
            this.background = style.background;
            this.thickness = style.thickness;
        }

        public SeparatorStyle (Drawable bg, int thickness) {
            this.background = bg;
            this.thickness = thickness;
        }
    }
}
