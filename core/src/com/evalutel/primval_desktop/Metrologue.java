package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;
import java.util.ArrayList;


public class Metrologue extends AnimationImageNew implements MyDrawInterface, MyPauseInterface
{
    public int largeurBille;

    public boolean isActif;
     public boolean isSpeaking;

    protected boolean isPaused = true;
    private TextureRegion defaultTextureRegion;

    Music music;

    public Metrologue(int startPositionX, int startpositionY, int animationWidth, int animationHeight)
    {

        super(getAnimationMetrologue(), startPositionX, startpositionY, animationWidth, animationHeight);


        animation = new Animation(1f / 15f, animationFrames);

    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    public void metrologuePlaySound(String audioPath)
    {
        isSpeaking = true;
        music = Gdx.audio.newMusic(Gdx.files.internal(audioPath));
//        music.setLooping(false);
        music.play();
////       boolean isLooping = false;
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

    public void stopMusic ()
    {
        music.stop();
        music.dispose();
    }


    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();


        TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, isSpeaking);
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);
    }


    private static ArrayList<String> getAnimationMetrologue()
    {
        ArrayList<String> imgMetrologuePaths = new ArrayList<>();

        int metrologueDirectorySize = new File("/Users/mikaellevy/Documents/Developper/Desktop/Primval-Dekstop/android/assets/Images/Metrologue").listFiles().length;

        String imgaux;

        for (int i = 0; i < metrologueDirectorySize - 1; i++)
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

            Gdx.app.log("SONG",Float.toString(music.getPosition()));
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
