package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;
import java.util.ArrayList;


public class Metrologue extends AnimationImageNew implements MyDrawInterface
{
    public int largeurBille;

    public Metrologue(int startPositionX, int startpositionY, int animationWidth, int animationHeight)
    {

        super(getAnimationMetrologue(), startPositionX, startpositionY, animationWidth, animationHeight);
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


    public void MetrologueSound(String audioPath)
    {

        Sound sound = Gdx.audio.newSound(Gdx.files.internal(audioPath));
        sound.play(1.0f);



    }


    @Override
    public void myDraw(Batch batch)
    {
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Images/me00000.png")));

        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);

    }


    private static ArrayList<String> getAnimationMetrologue()
    {
        ArrayList<String> imgMetrologuePaths = new ArrayList<>();

        int metrologueDirectorySize = new File("/Users/mikaellevy/Documents/Developper/Desktop/Primval-Dekstop/android/assets/Images/Metrologue").listFiles().length;

        String imgaux = "";

        for (int i = 0; i < metrologueDirectorySize-1; i++)
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


}
