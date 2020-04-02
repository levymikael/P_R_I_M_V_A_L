package com.evalutel.primval_desktop.ui_tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.evalutel.primval_desktop.MyDrawInterface;

import java.util.ArrayList;

public class MyImageButton extends ImageButton implements MyDrawInterface
{
    public int currentPositionX, currentPositionY;

    public float width, height, elapsedTime, animationHeight, animationWidth;

    public boolean animationContinue = true;


    protected int screenWidth;
    protected int screenHeight;

    TextureRegion[] animationFrames;

    Animation animation;



    public MyImageButton(Stage stage, String imagePathUp, String imagePathDown, float width, float height)
    {
        super(getDrawable(imagePathUp, width, height), getDrawable(imagePathDown, width, height));


//        this.width = width;
//        this.height = height;

        stage.addActor(this);

    }

    public MyImageButton(Stage stage, String imagePathUp, float width, float height)
    {
        super(getDrawable(imagePathUp, width, height));


        this.width = width;
        this.height = height;

        stage.addActor(this);

    }

//    @Override
//    public Drawable getDrawable(String name) {
//        try {
//            Drawable drawable = super.getDrawable(name);
//            if (drawable != null)
//                return drawable;
//        } catch (GdxRuntimeException gre) {
//            // Ignored
//        }
//
//        // Try and build an animated drawable
//        float frameDuration = 1f/30f;
//        String animKey = name + ".anim-";
//        ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
//        ObjectMap<String, TextureRegion> textures = this.getAll(TextureRegion.class);
//        for (String key:textures.keys()) {
//            if (key.startsWith(animKey)) {
//                String[] parts = key.substring(animKey.length()).split("-");
//                int frame = 0;
//                for (String part:parts) {
//                    if (part.startsWith("fps")) {
//                        frameDuration = 1f / Float.parseFloat(part.substring("fps".length()));
//                    } else if (part.startsWith("f")) {
//                        frame = Integer.parseInt(part.substring(1));
//                    }
//                }
//                while (frames.size() < frame+1)
//                    frames.add(null);
//                frames.set(frame, textures.get(key));
//            }
//        }
//        if (!frames.isEmpty()) {
//            AnimatedDrawable animatedDrawable = new AnimatedDrawable(frames.toArray(new TextureRegion[0]), frameDuration);
//            add(name, animatedDrawable, Drawable.class);
//            return animatedDrawable;
//        }

//        throw new GdxRuntimeException("No drawable registered with name " + name);
//    }



    private static SpriteDrawable getDrawable(String imagePath, float width, float height)
    {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        Sprite sprite = new Sprite(texture);
        sprite.setSize(width, height);
        return new SpriteDrawable(sprite);
    }

    public float getWidth()
    {
        return width;
    }


    public float getHeight()
    {
        return height;
    }


    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void myDraw(Batch batch)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion textureRegion = (TextureRegion) animation.getKeyFrame(elapsedTime, animationContinue);
        batch.draw(textureRegion, currentPositionX, currentPositionY, animationWidth, animationHeight);

    }


    public void addListener()
    {
    }
}
