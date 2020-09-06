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
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

public class MrNotes implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;


    public MrNotes(Stage stage, int chapitre)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/segoeUIsemibold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = screenHeight / 40;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;


        totalNotes = db.getTotalNotePageForIdProfil(chapitre);

// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = MyConstants.redresultat;
        Label labelNotes = new Label(totalNotes, labelStyle);

        float itemWidth = MyConstants.SCREENWIDTH / 15f;
        float itemHeight = itemWidth * (223f / 172f);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes1.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table container = new Table();
        Table mrNotes = new Table();
        Table notes = new Table();
        Table border = new Table();


        container.setPosition(MyConstants.SCREENWIDTH - itemWidth /** .7f*/, 21f * screenHeight / 25f);

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;


        mrNotes.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));
        mrNotes.setSize(itemWidth, itemHeight);
        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        notes.add(labelNotes).height(itemHeight / 4f).padLeft(screenWidth / 60f).padRight(screenWidth / 60f);
        notes.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Pixmap orangeBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, MyConstants.redresultat);

        border.pad(screenWidth / 900f);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(orangeBorder))));
        border.add(notes);

        container.add(mrNotes).height(itemHeight).width(itemWidth);
        container.row();
        container.add(border);

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
