package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.MyPoint;
import com.evalutel.primval_desktop.ui_tools.PauseSingleton;

import java.util.ArrayList;
import java.util.HashMap;


public class AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    protected TextureRegion[] animationFrames,animationFrames2 ;
    protected Animation animation, animation2;
    MyTimer timer = new MyTimer();


    public float elapsedTime, animationVitesse, animationHeight, animationWidth;

    public float currentPositionX, currentPositionY, deplacementEnX, deplacementEnY;
    public boolean animationContinue = true;
    protected int screenWidth;
    protected int screenHeight;

    protected boolean isVisible = true;
    protected boolean isPaused = false;

    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();


    public AnimationImageNew(ArrayList<String> imagesPaths, float startPositionX, float startPositionY, float animationWidth, float animationHeight)
    {
        this.animationHeight = animationHeight;
        this.animationWidth = animationWidth;
        this.currentPositionX = startPositionX;
        this.currentPositionY = startPositionY;

        int framesToAnimateQuantity;

        if (imagesPaths.size() == 0)
        {
            framesToAnimateQuantity = 1;
            Gdx.app.log("Methode animation", "imagesPath size = 0" + this);
        }
        else
        {
            framesToAnimateQuantity = imagesPaths.size();
            Gdx.app.log("Methode animation", Integer.toString(framesToAnimateQuantity) + this);
        }

        animationFrames = new TextureRegion[framesToAnimateQuantity];

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        for (int i = 0; i < framesToAnimateQuantity; i++)
        {
            String pathAux = imagesPaths.get(i);

            Texture imgAux = new Texture(pathAux);
            imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;
        }

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

    }

    public AnimationImageNew(String oneImagePath, float startPositionX, float startPositionY, float animationWidth, float animationHeight)
    {
        this(arrayFromImage(oneImagePath), startPositionX, startPositionY, animationWidth, animationHeight);
    }

    public AnimationImageNew(ArrayList<String> imagesPaths, ArrayList<String> imagesPaths2 , float startPositionX, float startPositionY, float animationWidth, float animationHeight)
    {
        this.animationHeight = animationHeight;
        this.animationWidth = animationWidth;
        this.currentPositionX = startPositionX;
        this.currentPositionY = startPositionY;

        int framesToAnimateQuantity1;
        int framesToAnimateQuantity2;

        if (imagesPaths.size() == 0)
        {
            framesToAnimateQuantity1 = 1;
            Gdx.app.log("Methode animation", "imagesPath size = 0" + this);
        }
        else
        {
            framesToAnimateQuantity1 = imagesPaths.size();
            Gdx.app.log("Methode animation", Integer.toString(framesToAnimateQuantity1) + this);
        }

        if (imagesPaths2.size() == 0)
        {
            framesToAnimateQuantity2 = 1;
            Gdx.app.log("Methode animation", "imagesPath size = 0" + this);
        }
        else
        {
            framesToAnimateQuantity2 = imagesPaths2.size();
            Gdx.app.log("Methode animation", Integer.toString(framesToAnimateQuantity2) + this);
        }

        animationFrames = new TextureRegion[framesToAnimateQuantity1];
        animationFrames2 = new TextureRegion[framesToAnimateQuantity2];

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        for (int i = 0; i < framesToAnimateQuantity1; i++)
        {
            String pathAux = imagesPaths.get(i);

            Texture imgAux = new Texture(pathAux);
            imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;
        }

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        for (int i = 0; i < framesToAnimateQuantity2; i++)
        {
            String pathAux = imagesPaths2.get(i);

            Texture imgAux = new Texture(pathAux);
            imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames2[i] = textureRegionAux;
        }

        animation2 = new Animation(1f / 6f, (Object[]) animationFrames2);
    }

    // refaire une methode avec 2 array
    public void changeImage(TextureRegion textureRegion)
    {
        //animationFrames = new TextureRegion[1];
        animationFrames[0] = textureRegion;
        animation = new Animation(1f / 6f, (Object[]) animationFrames);
    }

    public void setPosition(float x, float y)
    {
        currentPositionX = x;
        currentPositionY = y;
    }

    public void setPositionCenter(float x, float y)
    {
        currentPositionX = x - (animationWidth / 2f);
        currentPositionY = y - (animationHeight / 2f);
    }


    public MyPoint getPosition()
    {
        return new MyPoint(currentPositionX, currentPositionY);
    }


    public float getWidth()
    {
        return animationWidth;
    }


    public float getHeight()
    {
        return animationHeight;
    }

    public void animateImage(long animationDureemillis, boolean animationContinue, float deplacementEnX, float deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext, float vitesse)
    {
        this.animationContinue = animationContinue;
        this.deplacementEnX = deplacementEnX;
        this.deplacementEnY = deplacementEnY;

        animation = new Animation(vitesse, (Object[]) animationFrames);

        long deltaTime = 20;
        long nbIterations = animationDureemillis / deltaTime;

        float deltaX = (deplacementEnX - currentPositionX) / (float) nbIterations;
        float deltaY = (deplacementEnY - currentPositionY) / (float) nbIterations;

        timer.schedule(new TaskMoveAnimation(currentPositionX, currentPositionY, deltaX, deltaY, deltaTime, taskEtape, delayNext), deltaTime);

        int ok = 5;
        ok++;
    }


    @Override
    public void myCorrectionStart()
    {

    }

    @Override
    public void myCorrectionStop()
    {

    }

    @Override
    public void myPause()
    {
        isPaused = true;
    }

    @Override
    public void myResume()
    {
        isPaused = false;
    }

    @Override
    public boolean isPause()
    {
        return isPaused;
    }


    protected class TaskMoveAnimation extends MyTimer.TaskEtape
    {
        private float deltaX;
        private float deltaY;
        private long deltaTime;
        private float currentPositionFloatX;
        private float currentPositionFloatY;
        private MyTimer.TaskEtape nextEtape;
        private long delayNext;

        protected TaskMoveAnimation(float currxF, float curryf, float dx, float dy, long dT, MyTimer.TaskEtape nextEtape, long delayNext)
        {
            deltaX = dx;
            deltaY = dy;
            deltaTime = dT;
            currentPositionFloatX = currxF;
            currentPositionFloatY = curryf;
            this.nextEtape = nextEtape;
            this.delayNext = delayNext;
        }


        @Override
        public void run()
        {
            PauseSingleton pauseSingleton = PauseSingleton.getInstance();
            if (!isPaused)
            {
                currentPositionFloatX += deltaX;
                currentPositionFloatY += deltaY;

                currentPositionX = currentPositionFloatX;
                currentPositionY = currentPositionFloatY;

                double distanceCarre = Math.pow((double) (deplacementEnX - currentPositionX), 2.0) + Math.pow((double) (deplacementEnY - currentPositionY), 2.0);
                double distanceStop = Math.pow((double) (deltaX), 2.0) + Math.pow((double) (deltaY), 2.0);

                if (distanceCarre > distanceStop * 2)
                {
                    timer.schedule(new TaskMoveAnimation(currentPositionFloatX, currentPositionFloatY, deltaX, deltaY, deltaTime, nextEtape, delayNext), deltaTime);
                }
                else
                {
                    currentPositionX = deplacementEnX;
                    currentPositionY = deplacementEnY;
                    if (nextEtape != null)
                    {
                        timer.schedule(nextEtape, delayNext);
                    }
                }
            }
            else
            {
                timer.schedule(new TaskMoveAnimation(currentPositionFloatX, currentPositionFloatY, deltaX, deltaY, deltaTime, nextEtape, delayNext), deltaTime);
            }
        }
    }

    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }


    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, animationContinue);
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);

//        TextureRegion textureRegion2 = (TextureRegion) animation2.getKeyFrame(elapsedTime, animationContinue);
//        batch.draw(textureRegion2, currentPositionX, currentPositionY - 100, animationWidth, animationHeight);
    }


//    private void drawSprite(String name, float x, float y, SpriteBatch batch)
//    {
//        Sprite sprite = sprites.get(name);
//
//        sprite.setPosition(x, y);
//
//        sprite.draw(batch);
//    }


    private static ArrayList<String> arrayFromImage(String image)
    {
        ArrayList<String> retour = new ArrayList<>();
        retour.add(image);
        return retour;
    }
}
