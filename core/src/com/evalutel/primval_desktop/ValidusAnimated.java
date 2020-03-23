package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.File;
import java.util.ArrayList;

import static jdk.nashorn.internal.runtime.PropertyListeners.addListener;

public class ValidusAnimated extends AnimationImageNew /*implements MyDrawInterface */ implements MyTouchInterface
{

    public com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet.TaskEtape etapeCorrection;
    private boolean isActif;
    boolean isSpeaking;


    public ValidusAnimated(int startPositionX, int startPositionY, float animationWidth, float animationHeight)
    {
        super(getAnimationValidus(), startPositionX, startPositionY, animationWidth, animationHeight);


    }


    public void ValidusPlaySound(String audioPath)
    {
        isSpeaking = true;

        Music music = Gdx.audio.newMusic(Gdx.files.internal(audioPath));
//        music.setLooping(false);
        music.play();
////       boolean isLooping = false;
//
        music.setOnCompletionListener(new Music.OnCompletionListener()
        {
            @Override
            public void onCompletion(Music music)
            {
                music.dispose();
                isSpeaking = false;

            }
        });
    }


    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    private static ArrayList<String> getAnimationValidus()
    {
        ArrayList<String> imgValidusPaths = new ArrayList<>();

        int validusDirectorySize = new File("Images/Validus").listFiles().length;

        String imgaux = "";


        for (int i = 0; i < validusDirectorySize - 1; i++)
        {
            if (i < 10)
            {
                imgaux = "Images/Validus/vo0000" + i + ".png";
                imgValidusPaths.add(imgaux);
            }
            else if (i >= 10 && i < 100)
            {
                imgaux = "Images/Validus/vo000" + i + ".png";
                imgValidusPaths.add(imgaux);
            }
            else
            {
                imgaux = "Images/Validus/vo00" + i + ".png";
                imgValidusPaths.add(imgaux);
            }
        }


        return imgValidusPaths;
    }

    @Override
    public boolean isTouched(int x, int y)
    {
        Rectangle rectangle = new Rectangle(currentPositionX, currentPositionY, animationWidth, animationHeight);

        return rectangle.contains(x, y);
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

    public void TouchDown()
    {

        System.out.println("touchdown validus");

    }

    public void TouchUp(ArrayList<UnePlancheNew> planches, int firstPositionX, int firstPositionY)
    {
        System.out.println("touchUp validus");

    }


    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, isSpeaking);
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
    }
}
