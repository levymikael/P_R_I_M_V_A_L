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
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
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

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        db = new MyDataBase(dataBase);

        totalNotes = db.getTotalNotePageForIdProfil();

        String newTotalNotes = totalNotes.substring(0, totalNotes.length() - 2) + "/3593";

        int ok = 5;
        ok++;


// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.ORANGE;
        Label labelNotes = new Label(newTotalNotes, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes1.png"));

        Table container = new Table();
        stage.addActor(container);
        container.setSize(screenWidth / 8, screenWidth / 25);
        container.setPosition(positionX, positionY);

        Table mrNotes = new Table();
        mrNotes.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle((int) screenWidth / 10, screenHeight / 18, 25, Color.WHITE);


        Table notes = new Table();
        notes.add(labelNotes);
        notes.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        container.add(mrNotes);

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
