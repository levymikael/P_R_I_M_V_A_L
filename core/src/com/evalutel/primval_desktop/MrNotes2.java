package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;

public class MrNotes2 implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;

    MyDataBase db;

    public MrNotes2(Stage stage, DatabaseDesktop dataBase, float positionX, float positionY)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        parameter.size = (int) screenWidth / 65;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        db = new MyDataBase(dataBase);

        totalNotes = db.getTotalNotePageForIdProfil(0);

        String newTotalNotes = totalNotes.substring(0, totalNotes.length() - 2) + "/3593";

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.ORANGE;
        Label labelNotes = new Label(newTotalNotes, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table container = new Table();
        container.setSize(screenWidth / 6, screenHeight / 14);
        container.setPosition(positionX, positionY);

        container.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));
        container.add(labelNotes).padLeft(screenWidth / 15).padTop(MyConstants.SCREENHEIGHT / 160).expand().fill();
//        labelNotes.debug();

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
