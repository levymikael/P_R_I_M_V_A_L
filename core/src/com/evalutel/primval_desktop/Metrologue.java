package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evalutel.primval_desktop.General.MyConstants;

import java.util.ArrayList;


public class Metrologue extends AnimationImageNew implements MyDrawInterface, MyCorrectionAndPauseInterface
{
    public boolean isActif;
    public boolean isSpeaking;
    private MyTimer myTimer;

    protected boolean isPaused = true;
    private TextureRegion defaultTextureRegion;

    Music music;

    static String directory = System.getProperty("user.dir");
    static FileHandle fh = Gdx.files.absolute(directory + "/Images/Metrologue");

    public Metrologue(int startPositionX, int startpositionY, int animationWidth, int animationHeight, MyTimer timer)
    {
        super(getAnimationMetrologue(), startPositionX, startpositionY, animationWidth, animationHeight);

        myTimer = timer;

        Texture metrologueTexture = new Texture(Gdx.files.internal("Images/Metrologue/me" + "00000.png"));
        metrologueTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        defaultTextureRegion = new TextureRegion(metrologueTexture);

        animation = new Animation(1f / 15f, (Object[]) animationFrames);

        this.setPosition(MyConstants.SCREENWIDTH / 60, 2 * MyConstants.SCREENHEIGHT / 5);
    }

    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    public void metrologuePlaySound(String audioPath)
    {
        metrologuePlaySound(audioPath, null);
    }

    public void metrologuePlaySound(String audioPath, final MyTimer.TaskEtape nextEtape)
    {
        isSpeaking = true;
        music = Gdx.audio.newMusic(Gdx.files.internal(audioPath));
        music.play();
        music.setOnCompletionListener(new Music.OnCompletionListener()
        {
            @Override
            public void onCompletion(Music music2)
            {
                try
                {
                    music.dispose();
                    music2.dispose();
                } catch (Exception e)
                {

                }

                music = null;
                isSpeaking = false;

                if (nextEtape != null)
                {
                    myTimer.schedule(nextEtape, nextEtape.delayN);
                }
            }
        });
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


    public void stopMusic()
    {
        music.stop();
        music.dispose();
    }


    @Override
    public void myDraw(Batch batch)
    {
        if (isVisible = true)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();

            if (isSpeaking)
            {
                TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, isSpeaking);
                batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
            }
            else
            {
                batch.draw(defaultTextureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
            }
        }
    }

    private static ArrayList<String> getAnimationMetrologue()
    {
        ArrayList<String> imgMetrologuePaths = new ArrayList<>();

        String imgaux;

        for (int i = 0; i < 29; i++)
        {
            if (i < 10)
            {
                imgaux = "Images/Metrologue/me0000" + i + ".png";
                imgMetrologuePaths.add(imgaux);
            }
            else if (i >= 10 && i < 100)
            {
                imgaux = "Images/Metrologue/me000" + i + ".png";
                imgMetrologuePaths.add(imgaux);
            }
            else
            {
                imgaux = "Images/Metrologue/me00" + i + ".png";
                imgMetrologuePaths.add(imgaux);
            }
        }
        return imgMetrologuePaths;
    }


    @Override
    public void myPause()
    {
        if (music != null)
        {
            music.pause();

            Gdx.app.log("SONG", Float.toString(music.getPosition()));
        }


        if (isSpeaking)
        {
            isSpeaking = !isSpeaking;
        }
    }

    @Override
    public void myResume()
    {
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
