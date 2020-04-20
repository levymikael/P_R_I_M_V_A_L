package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.MyPoint;
import com.evalutel.primval_desktop.ui_tools.PauseSingleton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class AnimationImageNew implements MyDrawInterface
{
    protected TextureRegion[] animationFrames;
    Animation animation;
    MyTimer timer = new MyTimer();


    public float elapsedTime, animationVitesse, animationHeight, animationWidth;

    public int currentPositionX, currentPositionY, deplacementEnX, deplacementEnY;
    public boolean animationContinue = true;
    protected int screenWidth;
    protected int screenHeight;

    protected boolean isVisible = true;


    public AnimationImageNew(ArrayList<String> imagesPaths, int startPositionX, int startPositionY, float animationWidth, float animationHeight)
    {
        this.animationHeight = animationHeight;
        this.animationWidth = animationWidth;
        this.currentPositionX = startPositionX;
        this.currentPositionY = startPositionY;

        int framesToAnimateQuantity ;


        if (imagesPaths.size() == 0)
        {
            framesToAnimateQuantity = 1;
            Gdx.app.log("Methode animation", "imagesPath size =1" + this);

        }
        else
        {
            framesToAnimateQuantity = imagesPaths.size();
            Gdx.app.log("Methode animation", Integer.toString(framesToAnimateQuantity));
        }



        animationFrames = new TextureRegion[framesToAnimateQuantity];


        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        for (int i = 0; i < framesToAnimateQuantity; i++)
        {
            String pathAux = imagesPaths.get(i);
            int ok = 5;
            ok++;

            Texture imgAux = new Texture(pathAux);
            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            animationFrames[i] = textureRegionAux;
        }

        animation = new Animation(1f / 6f, (Object[]) animationFrames);


    }

    public AnimationImageNew(String oneImagePath, int startPositionX, int startPositionY, float animationWidth, float animationHeight)
    {
        this(arrayFromImage(oneImagePath), startPositionX, startPositionY, animationWidth, animationHeight);
    }


    public void setPosition(int x, int y)
    {
        currentPositionX = x;
        currentPositionY = y;
    }

    public void setPositionCenter(int x, int y)
    {
        currentPositionX = x - (int) (animationWidth / 2.0f);
        currentPositionY = y - (int) (animationHeight / 2.0f);
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

    public void animateImage(long animationDureemillis, boolean animationContinue, int deplacementEnX, int deplacementEnY, MyTimer.TaskEtape taskEtape, long delayNext, float vitesse)
    {
        this.animationContinue = animationContinue;
        this.deplacementEnX = deplacementEnX;
        this.deplacementEnY = deplacementEnY;

        animation = new Animation(vitesse, (Object[]) animationFrames);

        long deltaTime = 20;
        long nbIterations = animationDureemillis / deltaTime;

        float deltaX = (float) (deplacementEnX - currentPositionX) / (float) nbIterations;
        float deltaY = (float) (deplacementEnY - currentPositionY) / (float) nbIterations;


        timer.schedule(new TaskMoveAnimation(currentPositionX, currentPositionY, deltaX, deltaY, deltaTime, taskEtape, delayNext), deltaTime);

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
            if (!pauseSingleton.isPause)
            {
                currentPositionFloatX += deltaX;
                currentPositionFloatY += deltaY;

                currentPositionX = (int) currentPositionFloatX;
                currentPositionY = (int) currentPositionFloatY;

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
    }


    private static ArrayList<String> arrayFromImage(String image)
    {
        ArrayList<String> retour = new ArrayList<>();
        retour.add(image);
        return retour;
    }

}
