package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Ex.Screen_Demos;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonDemos extends MyImageButton implements MyDrawInterface
{

    public MyButtonDemos(Stage stage, float width, float height, final Game game, final DatabaseDesktop dataBase)
    {
        super(stage, "Images/button_demonstration.png", width, height);


        float posY = (5f * MyConstants.SCREENHEIGHT / 6f - this.getHeight() / 2f);
        float posX = (4f * MyConstants.SCREENWIDTH / 25f);
        this.setPosition(posX, posY);

        setTouchable(Touchable.enabled);

        addListener(new ClickListener()
                    {
                        @Override
                        public void clicked(InputEvent event, float x, float y)
                        {
                            Gdx.app.log("My button demos", "clicked !");
                            game.setScreen(new Screen_Demos(game, dataBase));
                        }
                    }
        );
    }

    @Override
    public void myDraw(Batch batch)
    {
    }
}
