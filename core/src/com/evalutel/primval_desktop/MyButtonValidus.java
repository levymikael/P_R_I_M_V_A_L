package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;


public class MyButtonValidus extends MyImageButton implements MyDrawInterface, MyTouchInterface
{
    public com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet.TaskEtape etapeCorrection;
    private boolean isActif;


    public MyButtonValidus(Stage stage, float width, float height)
    {
        super(stage, "Images/vo00000.png", width, height);
        setTouchable(Touchable.enabled);


        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(isActif)
                {
                    if(etapeCorrection != null)
                    {
                        etapeCorrection.run();
                    }

                }
            }
        });
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
        super.setPosition(x,y);
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