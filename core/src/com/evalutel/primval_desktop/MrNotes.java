package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.General.LigneTableaux;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_1;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_2;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class MrNotes implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;

    MyDataBase db;

    public MrNotes(Stage stage, DatabaseDesktop dataBase)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        db = new MyDataBase(dataBase);

        totalNotes = db.getTotalNotePageForIdProfil();


// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.ORANGE;
        Label labelNotes = new Label(totalNotes, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes1.png"));

        Table container = new Table();
        stage.addActor(container);
        container.setSize(screenWidth / 12, screenWidth / 7);
        container.setPosition(7 * screenWidth / 8, 7 * screenHeight / 10);

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
