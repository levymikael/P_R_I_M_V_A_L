package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Dices extends AnimationImageNew implements MyCorrectionAndPauseInterface, MyTouchInterface
{

    static ArrayList arrayListNumber, arrayListAnimal;

    static int lastDiceValue, lastDiceValue2;

    float dice2positionX;
    TextureRegion textureEmptyDice, texturePlusDice;
    boolean isClicked = false;

    public Dices(float dicePositionX, float dicePositionY, float animationWidth, float animationHeight, float dice2positionX)
    {
        super(getAnimationDiceNumber(), getAnimationDiceAnimals(), getAnimationDice2Number(), getAnimationDiceAnimals(), dicePositionX, dicePositionY, animationWidth, animationHeight);

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        this.setPosition(dicePositionX, dicePositionY);

        this.dice2positionX = dice2positionX;


        Texture diceTexture1 = new Texture(Gdx.files.internal("Images/onglet2_3/des_vide.png"));
        diceTexture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureEmptyDice = new TextureRegion(diceTexture1);


        Texture diceTexturePlus = new Texture(Gdx.files.internal("Images/onglet2_3/des_plus.png"));
        diceTexturePlus.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texturePlusDice = new TextureRegion(diceTexturePlus);
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

    private static ArrayList<String> getAnimationDice2Number()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        int[] array = {1, 2, 3, 4, 5, 6};

        array = MyMath.melangeTab(array);


        for (int i = 0; i < array.length; i++)
        {
            String imgaux = "Images/onglet2_3/des_0" + array[i] + ".png";
            imgDicesPaths.add(imgaux);
            lastDiceValue2 = array[i];
        }

        return imgDicesPaths;
    }

    public int getLastDice1value()
    {
        return lastDiceValue;
    }

    public int getLastDice2value()
    {
        return lastDiceValue2;
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
        batch.draw(textureEmptyDice, currentPositionX, currentPositionY, animationWidth, animationHeight);
        batch.draw(texturePlusDice, currentPositionX + animationWidth, currentPositionY, animationWidth / 2, (animationHeight / 2) * (152f / 186f));
        batch.draw(textureEmptyDice, dice2positionX, currentPositionY, animationWidth, animationHeight);

        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion1 = (TextureRegion) animation.getKeyFrame(elapsedTime, animationContinue);
        TextureRegion textureRegion2 = (TextureRegion) animation2.getKeyFrame(elapsedTime, animationContinue);
        TextureRegion textureRegion3 = (TextureRegion) animation3.getKeyFrame(elapsedTime, animationContinue);
        TextureRegion textureRegion4 = (TextureRegion) animation4.getKeyFrame(elapsedTime, animationContinue);


        if (isClicked == true)
        {
            batch.draw(textureRegion1, currentPositionX, currentPositionY, animationWidth, animationHeight);
        }

        if ((animation2 != null) && (isClicked == true))
        {
            batch.draw(textureRegion2, currentPositionX, currentPositionY, animationWidth, animationHeight);
        }

        if ((animation3 != null) && (isClicked == true))
        {
            batch.draw(textureRegion3, dice2positionX, currentPositionY, animationWidth, animationHeight);
        }

        if ((animation4 != null) && (isClicked == true))
        {
            batch.draw(textureRegion4, dice2positionX, currentPositionY, animationWidth, animationHeight);
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

        if (isClicked == false)
        {
            isClicked = true;
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
