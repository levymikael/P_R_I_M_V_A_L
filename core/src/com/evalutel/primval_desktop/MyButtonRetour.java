package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.Sommaire.Screen_All_Chapters;
import com.evalutel.primval_desktop.Sommaire.Screen_Sommaire_General;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonRetour extends MyImageButton implements MyDrawInterface
{
//    private boolean isActif;


    public MyButtonRetour(Stage stage, float width, float height, final Game game, String previousScreen)
    {
        super(stage, "Images/fleche_retour.png", width, height);
        setTouchable(Touchable.enabled);


        switch (previousScreen)
        {
            case "sommaire general":
                addListener(new ClickListener()
                            {
                                @Override
                                public void clicked(InputEvent event, float x, float y)
                                {
                                    Gdx.app.log("My button retour", "Sommaire general");
                                    game.setScreen(new Screen_Sommaire_General(game));
                                }
                            }
                );
                break;
            case "chapitres":
                addListener(new ClickListener()
                            {
                                @Override
                                public void clicked(InputEvent event, float x, float y)
                                {
                                    Gdx.app.log("My button retour", "Sommaire chapitres");
                                    game.setScreen(new Screen_All_Chapters(game));
                                }
                            }
                );
                break;
           /* case "screen all chapters":
                addListener(new ClickListener()
                            {
                                @Override
                                public void clicked(InputEvent event, float x, float y)
                                {
                                    Gdx.app.log("My button retour", "Screen All Chapters");
                                    game.setScreen(new Screen_All_Chapters(game));
                                }
                            }
                );
                break;*/
        }
    }


    @Override
    public void myDraw(Batch batch)
    {
    }
}
