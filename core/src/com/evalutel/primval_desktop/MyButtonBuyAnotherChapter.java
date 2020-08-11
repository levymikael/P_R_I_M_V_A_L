package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonBuyAnotherChapter extends MyImageButton implements MyDrawInterface
{
    private boolean isActif;


    public MyButtonBuyAnotherChapter(Stage stage, float width, float height)
    {
        super(stage, "Images/buy_other_chapter.png", width, height);
        setTouchable(Touchable.enabled);


        addListener(new ClickListener()
                    {
                        @Override
                        public void clicked(InputEvent event, float x, float y)
                        {
                            Gdx.app.log("Buy another chapter", "Acheter un autre chapitre clicqué");
                            // achat next chapter to implement
                        }
                    }
        );
    }


    @Override
    public void myDraw(Batch batch)
    {

    }

}
