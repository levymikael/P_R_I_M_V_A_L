package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class DragReserve2Container extends Actor {

    Texture container, planche, bille;
    TextureRegion containerRegion, billeRegion;

    TextureRegion[] animationFrames;
    Animation animation;

    public float posX, posY;

    private float firstX, firstY;
    private float currentX, currentY;

    float elapsedTime;
    boolean getBack2StartPosition;


    public DragReserve2Container() {


        container = new Texture(Gdx.files.internal("Images/Badix/boite900_vide.png"));
        containerRegion = new TextureRegion(container);

        planche = new Texture(Gdx.files.internal("Images/Badix/planche_9billes.png"));
        bille = new Texture(Gdx.files.internal("Images/Badix/Billes/bille2.png"));
        billeRegion = new TextureRegion(bille);

        animationFrames = new TextureRegion[17];
        for (int i = 0; i < 17; i++) {
            Texture imgAux = new Texture("Images/Badix/Billes/bille" + i + ".png");
            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;
        }

        animation = new Animation(1f / 17f, (Object[]) animationFrames);

        setColor(Color.GREEN);

        setTouchable(Touchable.enabled);

        setWidth(billeRegion.getRegionWidth());
        setHeight(billeRegion.getRegionHeight());


        addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);

                boolean dragActivated = true;
                firstX = x;
                firstY = y;

                currentX = getX();
                currentY = getY();

                posX = currentX;
                posY = currentY;
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);

                //moveBy(x - getWidth() / 2, y - getHeight() / 2);
                float posNewX = currentX + x - getWidth() / 2;
                float posNewY = currentY + y - getHeight() / 2;
                posX = posNewX;
                posY = posNewY;

            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);

                //moveBy(x-firstX, y - firstY);

                setPosition(posX, posY);

                if ( y > planche.getHeight() || (x <(Gdx.graphics.getWidth() / 2 - (planche.getWidth()/Gdx.graphics.getWidth()) / 2) || x > (Gdx.graphics.getWidth() / 2 + (planche.getWidth()/Gdx.graphics.getWidth()) / 2)))
                    {
//                        posX = 0;
//                        posY = 0;

                    }

                else
                {
                    getBack2StartPosition = true;
                    setX(posX);
                    setY(posY);
                }
//recalculer la position de la bille si dragnon effectif

            }
        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        elapsedTime += Gdx.graphics.getDeltaTime();
        Object object = animation.getKeyFrame(elapsedTime, true);

        TextureRegion textureRegion = (TextureRegion) object;

        float positionXContainer = Gdx.graphics.getWidth() - container.getWidth();
        float positionYContainer = Gdx.graphics.getHeight() - container.getHeight();


        float positionX = elapsedTime * 20.0f;
        float positionY = elapsedTime * 20.0f;


        batch.draw(container, positionXContainer, positionYContainer, 334, 229);
        batch.draw(planche, (Gdx.graphics.getWidth() / 2), 0, 455, 449);

        batch.draw(textureRegion, posX, posY, getWidth(), getHeight());

//        if (getBack2StartPosition)
//        {
//            batch.draw(textureRegion, posX, posY, getWidth(), getHeight());
//        }
//        else{
            //batch.draw(textureRegion, 100, 1000, getWidth(), getHeight());
//        }


    }

}
