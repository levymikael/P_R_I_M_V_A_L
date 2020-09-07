package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Dices extends AnimationImageNew implements MyCorrectionAndPauseInterface, MyTouchInterface
{
    private static ArrayList arrayListNumber;

    private ArrayList<int[]> randDicesArray = new ArrayList<>();
    private TextureRegion[] allImagesDes = new TextureRegion[6];
    private TextureRegion[] allImagesAnimal = new TextureRegion[7];

    private int currentChiffreDes1, currentChiffreDes2;


    private float dice2positionX;
    private TextureRegion textureEmptyDice;
    private TextureRegion texturePlusDice;
    private boolean isClicked = false;
    private boolean isAnimating = false;
    public int diceRenewal = 0;

    public Dices(float dicePositionX, float dicePositionY, float animationWidth, float animationHeight, float dice2positionX)
    {
        super(getAnimationDiceNumber(), getAnimationDiceAnimals(), getAnimationDice2Number(), getAnimationDiceAnimals(), dicePositionX, dicePositionY, animationWidth, animationHeight);

        animation = new Animation(1f / 6f, (Object[]) animationFrames);

        this.setPosition(dicePositionX, dicePositionY);

        this.dice2positionX = dice2positionX;


        Texture diceTexture1 = new Texture(Gdx.files.internal("Images/onglet2_3/des_vide.png"));
        diceTexture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureEmptyDice = new TextureRegion(diceTexture1);

        String[] animalsArray = {"baleine", "dauphin", "dinosaure", "elephant", "lion", "papillon", "tortue"};


        Texture diceTexturePlus = new Texture(Gdx.files.internal("Images/onglet2_3/des_plus.png"));
        diceTexturePlus.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texturePlusDice = new TextureRegion(diceTexturePlus);

        for (int i = 0; i < 7; i++)
        {
            String imgName = "Images/onglet2_3/des_" + animalsArray[i] + ".png";

            Texture imgAux = new Texture(imgName);
            imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            allImagesAnimal[i] = textureRegionAux;
        }

        for (int i = 0; i < 6; i++)
        {
            String imgName = "Images/onglet2_3/des_0" + (i + 1) + ".png";

            Texture imgAux = new Texture(imgName);
            imgAux.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            TextureRegion textureRegionAux = new TextureRegion(imgAux);
            allImagesDes[i] = textureRegionAux;
        }


        getRandDicesArray();
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
        }

        return imgDicesPaths;
    }

//    public int randDice1()
//    {
////        Random rand = new Random();
//
////        return rand.nextInt(6) + 1;
//        return MathUtils.random(1, 6);
//    }


    public int randDice2(int nbDice1)
    {
//        Random rand = new Random();

//        return nbDice1 + rand.nextInt(7 - nbDice1);
        return /*nbDice1 -*/ MathUtils.random(1, Math.min(6,9-nbDice1));

    }

    public void getRandDicesArray()
    {
        for (int i = 0; i < 6; i++)
        {
            int[] diceNumUpTo6 = {0, 0};
            int randDice1 = MathUtils.random(1, 6);

            diceNumUpTo6[0] = randDice1;
            diceNumUpTo6[1] = randDice2(randDice1);
            randDicesArray.add(diceNumUpTo6);
        }
    }

    private static ArrayList<String> getAnimationDice2Number()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        int[] array = {1, 2, 3, 4, 5, 6};

        array = MyMath.melangeTab(array);


        for (int value : array)
        {
            String imgaux = "Images/onglet2_3/des_0" + value + ".png";
            imgDicesPaths.add(imgaux);
        }

        return imgDicesPaths;
    }

    public int getLastDice1value(/*int questionCourante*/)
    {
        return randDicesArray.get(diceRenewal)[0];
    }

    public int getLastDice2value(/*int questionCourante*/)
    {
        return randDicesArray.get(diceRenewal)[1];
    }

//    public void displayNumberDiceFace()
//    {
//        String dice1NumberFace = "Images/onglet2_3/des_0" + getLastDice1value() + ".png";
//        textureNumberDice1 = new TextureRegion(new Texture(dice1NumberFace));
//
//        String dice2NumberFace = "Images/onglet2_3/des_0" + getLastDice2value() + ".png";
//        textureNumberDice2 = new TextureRegion(new Texture(dice2NumberFace));
//    }


    private static ArrayList<String> getAnimationDiceAnimals()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        ArrayList arrayListAnimal = new ArrayList<String>();
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


        if (isAnimating)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion textureNumberDice1 = (TextureRegion) animation.getKeyFrame(elapsedTime, isAnimating);
            TextureRegion textureNumberDice2 = (TextureRegion) animation3.getKeyFrame(elapsedTime, isAnimating);
            TextureRegion textureNumberDiceAnimal1 = (TextureRegion) animation2.getKeyFrame(elapsedTime, isAnimating);
            TextureRegion textureNumberDiceAnimal2 = (TextureRegion) animation4.getKeyFrame(elapsedTime, isAnimating);

            batch.draw(textureNumberDice1, currentPositionX, currentPositionY, animationWidth, animationHeight);
            batch.draw(textureNumberDice2, dice2positionX, currentPositionY, animationWidth, animationHeight);
            batch.draw(textureNumberDiceAnimal1, currentPositionX, currentPositionY, animationWidth, animationHeight);
            batch.draw(textureNumberDiceAnimal2, dice2positionX, currentPositionY, animationWidth, animationHeight);
        }
        else
        {
            if (currentChiffreDes1 > 0)
            {
                TextureRegion textureRegionDe1 = allImagesDes[currentChiffreDes1 - 1];
                TextureRegion textureRegionDe2 = allImagesDes[currentChiffreDes2 - 1];
                TextureRegion textureRegionDeAnimal1 = allImagesAnimal[currentChiffreDes1 - 1];
                TextureRegion textureRegionDeAnimal2 = allImagesAnimal[currentChiffreDes2 - 1];

                batch.draw(textureRegionDe1, currentPositionX, currentPositionY, animationWidth, animationHeight);
                batch.draw(textureRegionDe2, dice2positionX, currentPositionY, animationWidth, animationHeight);
                batch.draw(textureRegionDeAnimal1, currentPositionX, currentPositionY, animationWidth, animationHeight);
                batch.draw(textureRegionDeAnimal2, dice2positionX, currentPositionY, animationWidth, animationHeight);
            }
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

        if (isAnimating)
        {
            return false;
        }
        else
        {
            isAnimating = true;
            currentChiffreDes1 = randDicesArray.get(diceRenewal)[0];
            currentChiffreDes2 = randDicesArray.get(diceRenewal)[1];

            final Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/son_des.mp3"));
            music.play();

            Timer timerDice = new Timer();

            timerDice.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    isAnimating = false;

                    isClicked = false;

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
