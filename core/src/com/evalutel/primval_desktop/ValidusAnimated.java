package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.File;
import java.util.ArrayList;

public class ValidusAnimated extends AnimationImageNew /*implements MyDrawInterface */ implements MyTouchInterface, MyPauseInterface
{
    public MyTimer.TaskEtape etapeCorrection;
    public boolean isActif;
    public boolean isSpeaking;
    protected TextureRegion defaultTextureRegion;
    private TextureRegion textureRegionInactif;

    static String directory = System.getProperty("user.dir");
    static FileHandle fh = Gdx.files.absolute(directory + "/Images/Validus");

    Music music;


    public ValidusAnimated(int startPositionX, int startPositionY, float animationWidth, float animationHeight)
    {
        super(getAnimationValidus(), startPositionX, startPositionY, animationWidth, animationHeight);

//        if (animationFrames.length != 0)

        System.out.println(animationFrames);
//        {
            defaultTextureRegion = animationFrames[0];
//        }
//        else
//        {
//            defaultTextureRegion = new TextureRegion(new Texture(fh + "/vo00000.png"));
//        }
        textureRegionInactif = new TextureRegion(new Texture(Gdx.files.internal("Images/Validus/validusAlpha.png")));

        animation = new Animation(1f / 15f, (Object[]) animationFrames);

        int ok = 5;
        ok++;


    }


    public void validusPlaySound(String audioPath)
    {
        isSpeaking = true;

        music = Gdx.audio.newMusic(Gdx.files.internal(audioPath));
//        music.setLooping(false);
        music.play();
//       boolean isLooping = false;
        int ok = 34;
        ok++;

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


        int validusDirectorySize = fh.list().length - 1;

        int ok = 5;
        ok++;

        String imgAux;

        for (int i = 0; i < validusDirectorySize - 1; i++)
        {
            if (i < 10)
            {
                imgAux = fh + "/vo0000" + i + ".png";
                imgValidusPaths.add(imgAux);
            }
            else if (i >= 10 && i < 100)
            {
                imgAux = fh + "/vo000" + i + ".png";
                imgValidusPaths.add(imgAux);
            }
            else
            {
                imgAux = fh + "/vo00" + i + ".png";
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

    @Override
    public boolean isDragable()
    {
        return false;
    }

    public void touchUp(int firstPositionX, int firstPositionY)
    {
        System.out.println("touchUp validus");

        if (isActif && (!isSpeaking))
        {
            if (etapeCorrection != null)
            {
                etapeCorrection.run();
            }
        }
    }


    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (isSpeaking)
        {
            TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
            batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
        }
        else
        {
            if (isActif)
            {
                batch.draw(defaultTextureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
            }
            else
            {
                batch.draw(textureRegionInactif, currentPositionX, currentPositionY, animationWidth, animationHeight);
            }
        }
    }

    @Override
    public void myPause()
    {
        if (music != null)
        {
            music.pause();
        }


    }

    @Override
    public void myResume()
    {
        if (music != null)
        {
            music.play();
        }
    }
}
