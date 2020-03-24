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
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;

import java.util.Date;


public class MyButtonBackToPreviousMenu extends MyImageButton implements MyDrawInterface
{
    public com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet.TaskEtape etapeCorrection;
    private boolean isActif;

    Game game;
    final DatabaseDesktop dataBase;

//    MyDataBase db ;



    public MyButtonBackToPreviousMenu(final Game game, Stage stage, float width, float height, final DatabaseDesktop dataBase/*, MyDataBase db,  UnResultat resultat*/)
    {
        super(stage, "Images/button_menu.png", width, height);
        setTouchable(Touchable.enabled);

        this.game = game;
        this.dataBase = dataBase;

//        MyDataBase db = new MyDataBase(dataBase);


//        final MyDataBase finalDb = db;

    }

// implement sql query
 /*   endTime = System.currentTimeMillis();
    seconds = (endTime - startTime) / 1000L;

    MyDataBase db = new MyDataBase(dataBase);

    java.util.Date date = new java.util.Date();


    long dateTest = new Date().getTime() / 1000L;


    int score = ecrinDiamantView.getDiamantCount();

    UnResultat resultatEx1_2 = new UnResultat("Primval", 1, 2, 0, consigneExercice, 9, dateTest, score, 0, 0, 123);

                db.insertResultat(resultatEx1_2);

*/

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
        TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal("Images/vo00000.png")));

        batch.draw(textureRegion2, currentPositionX, currentPositionY, width, height);

    }


}
