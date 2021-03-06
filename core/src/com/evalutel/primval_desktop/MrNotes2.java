package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
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
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

public class MrNotes2 implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;

    public MrNotes2(Stage stage, float positionX, float positionY, String screen)
    {
        screenWidth = MyConstants.SCREENWIDTH;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = (int) screenWidth / 65;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        AppSingleton appSingleton = AppSingleton.getInstance();

        totalNotes = appSingleton.myDataBase.getTotalNotePageForIdProfil(0);
        String newTotalNotes;

        if (screen.equals("general"))
        {
            newTotalNotes = totalNotes.substring(0, totalNotes.length() - 5) + "/3593";
        }
        else
        {
            newTotalNotes = totalNotes;
        }

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = MyConstants.mrNotesFontColor;
        Label labelNotes = new Label(newTotalNotes, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        float logoWidth = screenWidth / 7f;
        float logoHeight = logoWidth * (120f / 380f);
        float positionXNew = positionX - (logoWidth / 2f);


        Table container = new Table();
        container.setSize(logoWidth, logoHeight);


        container.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));
        if (screen.equals("general"))
        {
            container.add(labelNotes).padLeft(logoWidth/3f).padTop(MyConstants.SCREENHEIGHT / 160f).expand().fill();
            container.setPosition(positionXNew, positionY);
        }
        else
        {
            container.add(labelNotes).padLeft(logoWidth/3f).padTop(MyConstants.SCREENHEIGHT / 160f).expand().fill();
            container.setPosition(MyConstants.SCREENWIDTH*.98f-logoWidth, positionY);

        }

        stage.addActor(container);
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
