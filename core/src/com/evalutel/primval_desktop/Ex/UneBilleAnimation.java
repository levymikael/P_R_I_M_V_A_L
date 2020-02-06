package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.evalutel.primval_desktop.Ex.AnimationImage;
import com.evalutel.primval_desktop.Ex.UnePlanche;

import java.util.ArrayList;

public class UneBilleAnimation extends AnimationImage
{
    public float startPositionDragX;
    public float startPositionDragY;

    public UnePlanche zoneTestDepot;

    public UneBilleAnimation(ArrayList<String> imagesPaths, final float startPositionX, final float startPositionY, float animationHeight, float animationWidth)
    {
        super(imagesPaths, startPositionX, startPositionY, animationHeight, animationWidth);

        zoneTestDepot = new UnePlanche(100,100);


        setTouchable(Touchable.enabled);

        addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);

                startPositionDragX = currentPositionX;
                startPositionDragY = currentPositionY;

            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);

                //moveBy(x - getWidth() / 2, y - getHeight() / 2);
                float posNewX = startPositionDragX + x - getWidth() / 2;
                float posNewY = startPositionDragY + y - getHeight() / 2;
                currentPositionX = posNewX;
                currentPositionY = posNewY;

            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);


                boolean isInRect = (currentPositionX > zoneTestDepot.getPosStartX()) && (currentPositionX < zoneTestDepot.getPosEndX()) && (currentPositionY > zoneTestDepot.getPosStartY()) && (currentPositionY < zoneTestDepot.getPosEndY());

                if (isInRect )
                {
                    setPosition(currentPositionX, currentPositionY);
                }
                else
                    {
                        currentPositionX = Gdx.graphics.getWidth()-100;
                        currentPositionY = Gdx.graphics.getHeight()-100;

                        int ok = 5;
                        ok++;

//                        currentPositionY = 1500;
//                        currentPositionX = 2000;
                        setPosition(currentPositionX, currentPositionY);
                    }
            }
        });

    }

}
