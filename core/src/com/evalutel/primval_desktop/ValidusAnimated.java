package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class ValidusAnimated extends AnimationImageNew implements MyTouchInterface, MyCorrectionAndPauseInterface
{
    public MyTimer.TaskEtape etapeCorrection;
    public boolean isActive;
    public boolean isSpeaking;

    private MyTimer myTimer;

    protected TextureRegion defaultTextureRegion;
    private TextureRegion textureRegionInactif;

//    static String directory = System.getProperty("user.dir");
//    static FileHandle fh = Gdx.files.absolute(directory + "/Images/Validus");

    Music music;


    public ValidusAnimated(int startPositionX, int startPositionY, float animationWidth, float animationHeight, MyTimer timer)
    {
        super(getAnimationValidus(), startPositionX, startPositionY, animationWidth, animationHeight);

        myTimer = timer;

        System.out.println(animationFrames);

        Texture validusTexture = new Texture(Gdx.files.internal("Images/Validus/vo" + "00000.png"));
        validusTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        defaultTextureRegion = new TextureRegion(validusTexture);

        Texture validusInactifTexture = new Texture(Gdx.files.internal("Images/Validus/validusAlpha.png"));
        validusInactifTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionInactif = new TextureRegion(validusInactifTexture);

        animation = new Animation(1f / 15f, (Object[]) animationFrames);
    }


    public void validusPlaySound(String audioPath)
    {
        validusPlaySound(audioPath, null);
    }


    public void validusPlaySound(String audioPath, final MyTimer.TaskEtape nextEtape)
    {
        isSpeaking = true;
        music = Gdx.audio.newMusic(Gdx.files.internal(audioPath));
        music.play();
        music.setOnCompletionListener(new Music.OnCompletionListener()
        {
            @Override
            public void onCompletion(Music music2)
            {
                music.dispose();
                music2.dispose();
                isSpeaking = false;
                music = null;

                if (nextEtape != null)
                {
                    myTimer.schedule(nextEtape, nextEtape.delayN);
                }
            }
        });
    }

    public void stopMusic()
    {
        music.stop();
        music.dispose();
    }


    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    private static ArrayList<String> getAnimationValidus()
    {
        ArrayList<String> imgValidusPaths = new ArrayList<>();

        String imgAux;

        for (int i = 0; i < 29; i++)
        {
            if (i < 10)
            {
                imgAux = "Images/Validus/vo0000" + i + ".png";
                imgValidusPaths.add(imgAux);
            }
            else if (i >= 10 && i < 100)
            {
                imgAux = "Images/Validus//vo000" + i + ".png";
                imgValidusPaths.add(imgAux);
            }
            else
            {
                imgAux = "/Images/Validus/vo00" + i + ".png";
                imgValidusPaths.add(imgAux);
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
        return isActive;
    }

    @Override
    public void setActive(boolean active)
    {
        isActive = active;
    }

    public void TouchDown()
    {
        System.out.println("touchdown validus");
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    public void touchUp(int firstPositionX, int firstPositionY)
    {
        if (isActive && (!isSpeaking))
        {
            if (etapeCorrection != null)
            {
                etapeCorrection.run();
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
        if (isVisible = true)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            if (isSpeaking)
            {
                TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
                batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
            }
            else
            {
                if (isActive)
                {
                    batch.draw(defaultTextureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
                }
                else
                {
                    batch.draw(textureRegionInactif, currentPositionX, currentPositionY, animationWidth, animationHeight);
                }
            }
        }
    }

    @Override
    public boolean isPause()
    {
        return isPaused;
    }

    @Override
    public void myPause()
    {
        super.myPause();
        if (music != null)
        {
            music.pause();
        }

        if (isSpeaking)
        {
            isSpeaking = !isSpeaking;
        }
    }

    @Override
    public void myResume()
    {
        super.myResume();
        if (music != null)
        {
            music.play();

            isSpeaking = !isSpeaking;
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
}
