package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.Sommaire.Screen_All_Chapters;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addListener;

public class Dices extends AnimationImageNew implements MyCorrectionAndPauseInterface, MyTouchInterface
{

    static ArrayList arrayListNumber, arrayListAnimal;

    static int lastDiceValue;

    public Dices(float dicePositionX, float dicePositionY, float animationWidth, float animationHeight)
    {
        super(getAnimationDiceNumber(), getAnimationDiceAnimals(), getAnimationDiceNumber(), getAnimationDiceAnimals(), dicePositionX, dicePositionY, animationWidth, animationHeight);

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        this.setPosition(dicePositionX, dicePositionY);
    }


    private static ArrayList<String> getAnimationDiceNumber()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        int[] array = {1, 2, 3, 4, 5, 6};

        array = MyMath.melangeTab(array);


        for (int i = 0; i < array.length; i++)
        {
            String imgaux = "Images/onglet2_3/des_0" + array[i] + ".png";
            imgDicesPaths.add(imgaux);
            lastDiceValue = array[i];
        }

        return imgDicesPaths;
    }

    public int getLastDicevalue()
    {
        return lastDiceValue;
    }


    private static ArrayList<String> getAnimationDiceAnimals()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        arrayListAnimal = new ArrayList<String>();
        arrayListAnimal.add("baleine");
        arrayListAnimal.add("dauphin");
        arrayListAnimal.add("dinosaure");
        arrayListAnimal.add("elephant");
        arrayListAnimal.add("lion");
        arrayListAnimal.add("papillon");
        arrayListAnimal.add("tortue");

        for (int i = 0; i < arrayListAnimal.size(); i++)
        {
            String imgaux = "Images/onglet2_3/des_" + arrayListAnimal.get(i) + ".png";
            imgDicesPaths.add(imgaux);
        }

        return imgDicesPaths;
    }

    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }

    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion1 = (TextureRegion) animation.getKeyFrame(elapsedTime, animationContinue);
        batch.draw(textureRegion1, currentPositionX, currentPositionY, animationWidth, animationHeight);

        if (animation2 != null)
        {
            TextureRegion textureRegion2 = (TextureRegion) animation2.getKeyFrame(elapsedTime, animationContinue);
            batch.draw(textureRegion2, currentPositionX, currentPositionY, animationWidth, animationHeight);
        }

        if (animation3 != null)
        {
            TextureRegion textureRegion3 = (TextureRegion) animation3.getKeyFrame(elapsedTime, animationContinue);
            batch.draw(textureRegion3, currentPositionX + 100, currentPositionY, animationWidth, animationHeight);
        }

        if (animation4 != null)
        {
            TextureRegion textureRegion4 = (TextureRegion) animation4.getKeyFrame(elapsedTime, animationContinue);
            batch.draw(textureRegion4, currentPositionX + 100, currentPositionY, animationWidth, animationHeight);
        }
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
    public boolean isTouched(float x, float y)
    {
        if (!isVisible)
        {
            isVisible = true;
        }

        final Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/son_des.mp3"));
        music.play();


        Timer timerDice = new Timer();


        timerDice.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                animationContinue = false;
//                dice2.animationContinue = false;
                Gdx.app.log("fin Timer", "");

                music.stop();
                music.setOnCompletionListener(new Music.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(Music music)
                    {
                        music.dispose();
                    }
                });

            }
        }, 3_000);

//        if (animationContinue)
//        {
//            animationContinue = false;
//        }
        System.out.println("dé touche");
        return false;
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }

    @Override
    public void setActive(boolean active)
    {
        isActive = active;

    }


}
