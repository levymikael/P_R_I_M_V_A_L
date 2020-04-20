package com.evalutel.primval_desktop.Ex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationImage extends Actor implements InputProcessor
{

    TextureRegion[] animationFrames;
    //SpriteBatch batch;
    Animation animation;
    private Timer timer = new Timer();

    public float elapsedTime, animationVitesse, currentPositionX, currentPositionY, deplacementEnX, deplacementEnY, animationHeight, animationWidth;

    public boolean animationContinue = true;


    public AnimationImage(ArrayList<String> imagesPaths, float startPositionX, float startPositionY, float animationHeight, float animationWidth)
    {

        this.animationHeight = animationHeight;
        this.animationWidth = animationWidth;
        this.currentPositionX = startPositionX;
        this.currentPositionY = startPositionY;

        //batch = new SpriteBatch();
        int framesToAnimateQuantity = imagesPaths.size();
        animationFrames = new TextureRegion[framesToAnimateQuantity];


        for (int i = 0; i < framesToAnimateQuantity; i++)
        {
            String pathAux = imagesPaths.get(i);
            Texture imgAux = new Texture(pathAux);
            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;

        }

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        setPosition(startPositionX, startPositionY);
        setWidth(animationWidth);
        setHeight(animationHeight);
    }

//Veiller a ce que les images a animer possedent la meme nomenclature

    public AnimationImage(String cheminDossier, String nomImageAAnimer, float startPositionX, float startPositionY, float animationHeight, float animationWidth)
    {

        this.animationHeight = animationHeight;
        this.animationWidth = animationWidth;
        this.currentPositionX = startPositionX;
        this.currentPositionY = startPositionY;

        //batch = new SpriteBatch();
        int framesToAnimateQuantity = new File(cheminDossier).list().length;
        animationFrames = new TextureRegion[framesToAnimateQuantity];


        for (int i = 0; i < framesToAnimateQuantity; i++)
        {
            Texture imgAux = new Texture(cheminDossier + "/" + nomImageAAnimer + i + ".png");
            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;

        }

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        setPosition(startPositionX, startPositionY);
    }

    public void animateImage(long animationDureemillis, boolean animationContinue, float deplacementEnX, float deplacementEnY)
    {

        this.animationVitesse = animationDureemillis;
        this.animationContinue = animationContinue;
        this.deplacementEnX = deplacementEnX;
        this.deplacementEnY = deplacementEnY;


        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        long deltaTime = 10;
        long nbIterations = animationDureemillis / deltaTime;

        float deltaX = (deplacementEnX - currentPositionX) / nbIterations;
        float deltaY = (deplacementEnY - currentPositionY) / nbIterations;

        timer.schedule(new TaskMoveAnimation(deltaX, deltaY, deltaTime), deltaTime);

    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    private class TaskMoveAnimation extends TimerTask
    {
        private float deltaX;
        private float deltaY;
        private long deltaTime;

        private TaskMoveAnimation(float dx, float dy, long dT)
        {
            deltaX = dx;
            deltaY = dy;
            deltaTime = dT;
        }

        @Override
        public void run()
        {

            currentPositionX += deltaX;
            currentPositionY += deltaY;

            double distanceCarre = Math.pow((double) (deplacementEnX - currentPositionX), 2.0) + Math.pow((double) (deplacementEnY - currentPositionY), 2.0);

            double distanceStop = Math.pow((double) (deltaX), 2.0) + Math.pow((double) (deltaY), 2.0);

            if (distanceCarre > distanceStop * 2)
            {
                timer.schedule(new TaskMoveAnimation(deltaX, deltaY, deltaTime), deltaTime);
            }
            else
            {
                currentPositionX = deplacementEnX;
                currentPositionY = deplacementEnY;
            }

        }
    }


    /*

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        //super.draw(batch, parentAlpha);


        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TextureRegion textureRegion = (TextureRegion)animation.getKeyFrame(elapsedTime, animationContinue);

        //batch.begin();
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth,  animationHeight);

        batch.draw(textureRegion, 150, 300, animationWidth,  animationHeight);

        batch.draw(textureRegion, 400, 200, animationWidth,  animationHeight);

        batch.draw(textureRegion, 66, 176, animationWidth,  animationHeight);

        batch.draw(textureRegion, 66, 176, animationWidth,  animationHeight);

        batch.draw(textureRegion, 125, 176, animationWidth,  animationHeight);

        batch.draw(textureRegion, 66, 186, animationWidth,  animationHeight);

        //batch.end();

    }
*/
    @Override
    public void act(float delta)
    {
        super.act(delta);


    }
}
