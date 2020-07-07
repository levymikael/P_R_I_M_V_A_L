package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_1;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_2;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class MrNotes implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;
    BitmapFont bitmapFont;

    MyDataBase db;

    public MrNotes(Stage stage, DatabaseDesktop dataBase, int chapitre)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        parameter.size = screenHeight / 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        String totalNotes;

        db = new MyDataBase(dataBase);

        totalNotes = db.getTotalNotePageForIdProfil(chapitre);


// Configuration police
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.ORANGE;
        Label labelNotes = new Label(totalNotes, labelStyle);

        Texture textureMrNotes = new Texture(Gdx.files.internal("Images/mr_notes1.png"));
        textureMrNotes.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table container = new Table();
        stage.addActor(container);
        container.setPosition(17 * screenWidth / 18, 21 * screenHeight / 25);

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 4;

        Table mrNotes = new Table();

        mrNotes.setBackground(new SpriteDrawable(new Sprite(new TextureRegion(textureMrNotes))));

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);

        Table notes = new Table();
        notes.add(labelNotes).height(screenHeight / 30).padLeft(screenWidth / 60).padRight(screenWidth / 60);
        notes.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Pixmap orangeBorder = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.ORANGE);

        Table border = new Table();
        border.pad(screenWidth / 1000);
        border.setBackground(new SpriteDrawable(new Sprite(new Texture(orangeBorder))));
        border.add(notes);

        container.add(mrNotes).height(screenHeight / 10).width((MyConstants.SCREENHEIGHT / 10) * (172f / 223f));
        container.row();
        container.add(border);
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
