package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_All_ChaptersNew;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Sommaire_General;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonRetour extends MyImageButton implements MyDrawInterface
{
    private boolean isActif;


    public MyButtonRetour(Stage stage, float width, float height, final Game game, final DatabaseDesktop dataBase, String screenPrevious)
    {
        super(stage, "Images/fleche_retour.png", width, height);
        setTouchable(Touchable.enabled);


        if (screenPrevious == "sommaire general")
        {
            addListener(new ClickListener()
                        {
                            @Override
                            public void clicked(InputEvent event, float x, float y)
                            {
                                Gdx.app.log("My button retour", "Sommaire general");
                                game.setScreen(new Screen_Sommaire_General(game, dataBase));
                            }
                        }
            );
        }
        else if (screenPrevious == "chapitres")
        {
            addListener(new ClickListener()
                        {
                            @Override
                            public void clicked(InputEvent event, float x, float y)
                            {
                                Gdx.app.log("My button retour", "Sommaire chapitres");
                                game.setScreen(new Screen_All_ChaptersNew(game, dataBase));
                            }
                        }
            );
        }

    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.width >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.height >= currentPositionY;
    }


    @Override
    public void myDraw(Batch batch)
    {
        /*TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal("Images/fleche_retour.png")));

        batch.draw(textureRegion2, currentPositionX, currentPositionY, width, height);*/

    }

}
