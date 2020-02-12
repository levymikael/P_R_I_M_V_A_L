package com.evalutel.ui_tools;

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

    Stage stage;


    public MyImageButton(Stage stage, String imagePathUp, String imagePathDown, float width, float height)
    {
        super(getDrawable(imagePathUp, width, height), getDrawable(imagePathDown, width, height));

        stage.addActor(this);

    }

    private static SpriteDrawable getDrawable(String imagePath, float width, float height)
    {

        Texture texture = new Texture(Gdx.files.internal(imagePath));
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        return new SpriteDrawable(sprite);
    }


    @Override
    public void myDraw(Batch batch)
    {
        stage.draw();
    }



    public void addListener()
    {

    }
}
