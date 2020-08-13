package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Dices extends AnimationImageNew implements MyCorrectionAndPauseInterface, MyTouchInterface
{

    static ArrayList arrayListNumber, arrayListAnimal;

    static int lastDiceValue;

    public Dices(float dicePositionX, float dicePositionY, float animationWidth, float animationHeight)
    {
        super(getAnimationDiceNumber(), getAnimationDiceAnimals(), dicePositionX, dicePositionY, animationWidth, animationHeight);

        animation = new Animation(1f / 6f, (Object[]) animationFrames);


        this.setPosition(dicePositionX, dicePositionY);


//        setTouchable(Touchable.enabled);
//
//        setWidth(200);
//        setHeight(450);
//
//        addListener(new DragListener()
//        {
//            @Override
//            public void dragStart(InputEvent event, float x, float y, int pointer) {
//                super.dragStart(event, x, y, pointer);
//
//                firstX = x;
//                firstY = y;
//
//                currentX = getX();
//                currentY = getY();
//
//                posX =  currentX;
//                posY =  currentY;
//            }
//
//            @Override
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                super.drag(event, x, y, pointer);
//
//                //moveBy(x-firstX, y - firstY);
//
//                moveBy(x - getWidth() / 2, y - getHeight() / 2);
//
//                //posX =  x;
//                //posY =  y;
//
//                posX = getX();
//                posY = getY();
//
//            }
//
//            @Override
//            public void dragStop(InputEvent event, float x, float y, int pointer) {
//                super.dragStop(event, x, y, pointer);
//
//                //moveBy(x-firstX, y - firstY);
//
//                setX(posX);
//                setY(posY);
//            }
//        });


    }

//    public int randDice1()
//    {
//        Random rand = new Random();
//        diceValue1 = rand.nextInt(6) + 1;
//        return diceValue1;
//    }
//
//
//    public int randDice2()
//    {
//        Random rand = new Random();
//        int diceValue2 = rand.nextInt(6 - diceValue1) + 1;
//
//        return diceValue2;
//    }

    private static ArrayList<String> getAnimationDiceNumber()
    {
        ArrayList<String> imgDicesPaths = new ArrayList<>();

        int[] array = {1, 2, 3, 4, 5, 6};

        array = MyMath.melangeTab(array);


//        Object[] faceAndIndex1 = {"1", 1};
//        Object[] faceAndIndex2 = {"2", 2};
//        Object[] faceAndIndex3 = {"3", 3};
//        Object[] faceAndIndex4 = {"4", 4};
//        Object[] faceAndIndex5 = {"5", 5};
//        Object[] faceAndIndex6 = {"6", 6};
//
//        arrayListNumber = new ArrayList<Object>();
//        arrayListNumber.add(faceAndIndex1);
//        arrayListNumber.add(faceAndIndex2);
//        arrayListNumber.add(faceAndIndex3);
//        arrayListNumber.add(faceAndIndex4);
//        arrayListNumber.add(faceAndIndex5);
//        arrayListNumber.add(faceAndIndex6);

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

        Timer timerDice = new Timer();


        timerDice.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                animationContinue = false;
//                dice2.animationContinue = false;
                Gdx.app.log("fin Timer", "");

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
        return false;
    }

    @Override
    public void setActive(boolean active)
    {

    }

//    @Override
//    public void myPause()
//    {
//
//    }
//
//    @Override
//    public void myResume()
//    {
//
//    }
}
