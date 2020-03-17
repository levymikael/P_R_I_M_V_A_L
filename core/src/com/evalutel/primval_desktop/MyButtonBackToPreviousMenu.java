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
import com.evalutel.primval_desktop.onglets.chapitre1.Screen_Chapitre1;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonBackToPreviousMenu extends MyImageButton implements MyDrawInterface, MyTouchInterface
{
    public com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet.TaskEtape etapeCorrection;
    private boolean isActif;

    Game game;
    final DatabaseDesktop dataBase;


    public MyButtonBackToPreviousMenu(final Game game, Stage stage, float width, float height, final DatabaseDesktop dataBase)
    {
        super(stage, "Images/button_menu.png", width, height);
        setTouchable(Touchable.enabled);

        this.game = game;
        this.dataBase = dataBase;

        addListener(new ClickListener()
                    {
                        @Override
                        public void clicked(InputEvent event, float x, float y)
                        {
                            System.out.println("click on Back to Menu");
                            game.setScreen(new Screen_Chapitre1(game, dataBase));
                        }
                    }
        );
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
        TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal("Images/vo00000.png")));

        batch.draw(textureRegion2, currentPositionX, currentPositionY, width, height);

    }

    @Override
    public boolean isTouched(int x, int y)
    {
        return false;
    }

    @Override
    public void setPosition(int x, int y)
    {
        super.setPosition(x, y);
    }


    @Override
    public boolean isActive()
    {
        return isActif;
    }

    @Override
    public void setActive(boolean active)
    {
        isActif = active;
    }
}
