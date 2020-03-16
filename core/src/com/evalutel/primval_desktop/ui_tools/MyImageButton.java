package com.evalutel.primval_desktop.ui_tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.MyDrawInterface;

public class MyImageButton extends ImageButton implements MyDrawInterface
{
    public int currentPositionX, currentPositionY;

    public float width, height;


    public MyImageButton(Stage stage, String imagePathUp, String imagePathDown, float width, float height)
    {
        super(getDrawable(imagePathUp, width, height), getDrawable(imagePathDown, width, height));


        this.width = width;
        this.height = height;

        stage.addActor(this);

    }

    public MyImageButton(Stage stage, String imagePathUp, float width, float height)
    {
        super(getDrawable(imagePathUp, width, height));


        this.width = width;
        this.height = height;

        stage.addActor(this);

    }

    private static SpriteDrawable getDrawable(String imagePath, float width, float height)
    {

        Texture texture = new Texture(Gdx.files.internal(imagePath));
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        return new SpriteDrawable(sprite);
    }


    public MyPoint getPosition()
    {
        return new MyPoint(currentPositionX, currentPositionY);
    }


    public float getWidth()
    {
        return width;
    }


    public float getHeight()
    {
        return height;
    }

    @Override
    public void myDraw(Batch batch)
    {
    }


    public void addListener()
    {

    }
}
