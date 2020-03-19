package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;
import java.util.ArrayList;


public class Metrologue extends AnimationImageNew implements MyDrawInterface
{
    public int largeurBille;

    public Metrologue(int startPositionX, int startpositionY, int animationWidth, int animationHeight) {

        super("Images/me00000.png",  startPositionX, startpositionY, animationWidth, animationHeight);
    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY) {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.animationWidth >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.animationHeight >= currentPositionY;
    }


    @Override
    public void myDraw(Batch batch) {
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("Images/me00000.png")));

        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);

    }


    private static ArrayList<String> getAnimationMetrologue()
    {
        ArrayList<String> imgMetrologuePaths = new ArrayList<>();

        int validusDirectorySize = new File("/Users/mikaellevy/Documents/Developper/Desktop/Primval-Dekstop/android/assets/Images/Validus").listFiles().length;

        for (int i = 0; i < validusDirectorySize; i++)
        {
            String imgaux = "Images/Metrologue/vo0000" + i + ".png";
            imgMetrologuePaths.add(imgaux);
        }

        return imgMetrologuePaths;

    }


}
