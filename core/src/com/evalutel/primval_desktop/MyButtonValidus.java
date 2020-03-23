package com.evalutel.primval_desktop;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;

import java.io.File;
import java.util.ArrayList;


public class MyButtonValidus extends MyImageButton implements MyDrawInterface
{
    public com.evalutel.primval_desktop.onglets.chapitre1.ScreenOnglet.TaskEtape etapeCorrection;
    public boolean isActif;


    public MyButtonValidus(Stage stage, float width, float height)
    {
        super(stage, "Images/vo00000.png", width, height);

        setZIndex(0);
        setTouchable(Touchable.enabled);

        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (isActif)
                {
                    if (etapeCorrection != null)
                    {
                        etapeCorrection.run();
                    }
                }
            }
        });
    }

//    public MyButtonValidus(Stage stage, float width, float height)
//    {
//        super(stage, getAnimationValidus(), width, height);
//
//        setZIndex(0);
//        setTouchable(Touchable.enabled);
//
//        addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                if (isActif)
//                {
//                    if (etapeCorrection != null)
//                    {
//                        etapeCorrection.run();
//                    }
//                }
//            }
//        });
//    }

    public void ValidusSound(String audioPath)
    {

        Sound sound = Gdx.audio.newSound(Gdx.files.internal(audioPath));
        sound.play(1.0f);



    }

    /**
     * @param currentPositionX point x coordinate
     * @param currentPositionY point y coordinate
     * @return whether the point is contained in the rectangle
     */
    public boolean contains(float currentPositionX, float currentPositionY)
    {
        return this.currentPositionX <= currentPositionX && this.currentPositionX + this.width >= currentPositionX && this.currentPositionY <= currentPositionY && this.currentPositionY + this.height >= currentPositionY;
    }


    @Override
    public void myDraw(Batch batch)
    {
        /*TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal("Images/vo00000.png")));

        batch.draw(textureRegion2, currentPositionX, currentPositionY, width, height);*/
    }


    private static ArrayList<String> getAnimationValidus()
    {

        //  https://www.reddit.com/r/libgdx/comments/4gwxod/best_way_to_make_an_animated_button_in_scene2d/

        ArrayList<String> imgValidusPaths = new ArrayList<>();

        int validusDirectorySize = new File("/Users/mikaellevy/Documents/Developper/Desktop/Primval-Dekstop/android/assets/Images/Validus").listFiles().length;

        for (int i = 0; i < validusDirectorySize; i++)
        {
            String imgaux = "Images/Validus/vo0000" + i + ".png";
            imgValidusPaths.add(imgaux);
        }

        return imgValidusPaths;

    }

}
