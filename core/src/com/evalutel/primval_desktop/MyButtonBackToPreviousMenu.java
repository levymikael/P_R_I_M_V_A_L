package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;

import java.util.Date;


public class MyButtonBackToPreviousMenu extends MyImageButton implements MyDrawInterface
{
    private boolean isActif;

    Game game;


    public MyButtonBackToPreviousMenu(final Game game, Stage stage, float width, float height/*, MyDataBase db,  UnResultat resultat*/)
    {
        super(stage, "Images/button_menu.png", width, height);
        setTouchable(Touchable.enabled);

        this.game = game;

        this.setPosition(MyConstants.SCREENWIDTH / 60, 6 * MyConstants.SCREENHEIGHT / 7);
    }


    @Override
    public void myDraw(Batch batch)
    {
    }
}
