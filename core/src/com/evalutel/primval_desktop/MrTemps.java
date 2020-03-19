package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.Ex.User;

public class MrTemps implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;


    public MrTemps(Stage stage, String Notes)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();



        User user = new User();
        //user.setIdProfil(39);
        user.setName("userTest");


        Profil profilTest = new Profil(2,"prenomTest", "nomTest", 6,"CP",1);

        user.setProfil(2);




        String userName = user.getName();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.GREEN;
        Label labelNotes = new Label(userName, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_temps1.png"));

        Table container = new Table();
        stage.addActor(container);
        container.setSize(200, 500);
        container.setPosition(6 * screenWidth / 8, 7 * screenHeight / 10);

        Table mrNotes = new Table();
        mrNotes.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));

        Table notes = new Table();
        notes.add(labelNotes);

        container.add(mrNotes);
        container.row();
        container.add(notes);


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

    }
}
