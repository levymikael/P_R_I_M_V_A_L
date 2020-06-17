package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.UIDesign;
import com.evalutel.primval_desktop.MrNotes2;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Screen_Sommaire_General extends Game implements Screen, InputProcessor, ApplicationListener
{
    private DatabaseDesktop dataBase;
    protected Stage stage;
    int screenWidth;
    int screenHeight;
    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    ScreeenBackgroundImage fondSommairee;
    ScreeenBackgroundImage fondSommaire;
    ScreeenBackgroundImage imgSommaire;

    MrNotes2 mrNotes2;
    MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();

//    Table chapitresOnglet;

    FreeTypeFontGenerator generator;

    Texture logoTitre;

    final String strDate;

    public Screen_Sommaire_General(final Game game, final DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        Gdx.app.log("screenheight, screenWidth", screenHeight + "/" + screenWidth);

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFont;

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = screenHeight / 40;
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFont;
        labelStyleBlue.fontColor = new Color(41.0f / 255.0f, 103.0f / 255.0f, 159.0f / 255.0f, 1);

        Label.LabelStyle labelStyleRed = new Label.LabelStyle();
        labelStyleRed.font = bitmapFont;
        labelStyleRed.fontColor = new Color(235.0f / 50.0f, 44.0f / 255.0f, 35.0f / 255.0f, 1);

        allDrawables = new ArrayList<>();

        fondSommairee = new ScreeenBackgroundImage("Images/Sommaire/fond_sommaire_color.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Sommaire/sommaire_bandeau_gauche.jpg");

        imgSommaire = new ScreeenBackgroundImage("Images/Sommaire/primaire.png");

        logoTitre = new Texture(Gdx.files.internal("Images/Sommaire/titre_sommaire.png"));
        logoTitre.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        mrNotes2 = new MrNotes2(stage, dataBase, screenWidth / 100, screenHeight / 2);

        Label labelChapitres = new Label("Chapitres", labelStyleBlue);
        Label labelResultats = new Label("Résultats", labelStyleBlue);
        Label labelEspaceParents = new Label("Espace Parents", labelStyleBlue);
        Label labelPresentation = new Label("Présentation", labelStyleBlue);

        Table container = new Table();
        container.setPosition(screenWidth / 50, 2 * screenHeight / 9);
        container.setWidth(screenWidth / 7);
        container.setHeight(screenHeight / 6);

        int widthButton = 500;
        int heightButton = widthButton / 4;
        int cornerRadius = heightButton / 2;

        Pixmap whiteRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, Color.WHITE);
        Pixmap blueRoundedBackground = UIDesign.createRoundedRectangle(widthButton, heightButton, cornerRadius, new Color(41.0f / 255.0f, 103.0f / 255.0f, 159.0f / 255.0f, 1));
        Pixmap greyRoundedBackground = UIDesign.createRoundedRectangle((widthButton * 2), widthButton, cornerRadius, Color.GRAY);
        Pixmap whiteRoundedBackground2 = UIDesign.createRoundedRectangle((widthButton * 2), widthButton, cornerRadius, Color.WHITE);

        Table avatarPic = new Table();
        Texture avatarTexture = new Texture(Gdx.files.internal("Images/avatar_1.png"));
        avatarTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        avatarPic.setBackground(new SpriteDrawable(new Sprite(avatarTexture)));
        avatarPic.setWidth(screenWidth / 7);
        avatarPic.setHeight(screenHeight / 5);
        avatarPic.setPosition(screenWidth / 50, 6 * screenHeight / 10);

        int paddingButtonBorders = MyConstants.SCREENWIDTH / 500;

        Table chaptersButton = new Table();
        chaptersButton.add(labelChapitres);
        chaptersButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table blueBorderTableChapters = new Table();
        blueBorderTableChapters.pad(paddingButtonBorders);
        blueBorderTableChapters.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        blueBorderTableChapters.add(chaptersButton);

        Table resultsButton = new Table();
        resultsButton.add(labelResultats);
        resultsButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table blueBorderTableResults = new Table();
        blueBorderTableResults.pad(paddingButtonBorders);
        blueBorderTableResults.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        blueBorderTableResults.add(resultsButton);

        Table espaceParentsButton = new Table();
        espaceParentsButton.add(labelEspaceParents);
        espaceParentsButton.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table blueBorderTableParentSpace = new Table();
        blueBorderTableParentSpace.pad(paddingButtonBorders);
        blueBorderTableParentSpace.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        blueBorderTableParentSpace.add(espaceParentsButton);

        Table presentation = new Table();
        presentation.add(labelPresentation);
        presentation.setBackground(new SpriteDrawable(new Sprite(new Texture(whiteRoundedBackground))));

        Table blueBorderTablePresentation = new Table();
        blueBorderTablePresentation.pad(paddingButtonBorders);
        blueBorderTablePresentation.setBackground(new SpriteDrawable(new Sprite(new Texture(blueRoundedBackground))));
        blueBorderTablePresentation.add(presentation);

        int widthButton2 = screenWidth / 7;
        int heightButton2 = widthButton2 / 4;
        int paddingBetweenButtons = MyConstants.SCREENHEIGHT / 120;

        container.add(blueBorderTableChapters).padBottom(paddingBetweenButtons).align(Align.center).height(heightButton2).width(widthButton2);
//        container.row();
//        container.add(blueBorderTableResults).padTop(paddingBetweenButtons).padBottom(paddingBetweenButtons).align(Align.center).height(heightButton2).width(widthButton2);
//        container.row();
//        container.add(blueBorderTableParentSpace).padTop(paddingBetweenButtons).padBottom(paddingBetweenButtons).align(Align.center).height(heightButton2).width(widthButton2);
//        container.row();
//        container.add(blueBorderTablePresentation).padTop(paddingBetweenButtons).align(Align.center).height(heightButton2).width(widthButton2);

        Table summaryImgTable = new Table();
        summaryImgTable.setTouchable(Touchable.enabled);
        summaryImgTable.setSize(4 * screenWidth / 5, MyConstants.SCREENHEIGHT);
        summaryImgTable.setPosition(screenWidth / 5, 0);
        summaryImgTable.setTouchable(Touchable.enabled);


        stage.addActor(summaryImgTable);
        stage.addActor(avatarPic);
        stage.addActor(container);

        chaptersButton.setTouchable(Touchable.enabled);
        chaptersButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new Screen_All_Chapters(game, dataBase));
                Gdx.app.log("Screen All chapters ", "clicked!");
            }
        });

        summaryImgTable.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new Screen_All_Chapters(game, dataBase));
                Gdx.app.log("Screen All chapters ", "clicked!");
            }
        });

        resultsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new Screen_All_Results(game, dataBase));
                Gdx.app.log("Screen All results ", "clicked!");
            }
        });

//        Label labelEscape = new Label("Quitter", labelStyleRed);

        Texture escapeBtn = new Texture(Gdx.files.internal("Images/Quitter primaire.png"));
        escapeBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table quit = new Table();
        quit.setTouchable(Touchable.enabled);
        quit.setBackground(new SpriteDrawable(new Sprite(escapeBtn)));

        Table quitBorder = new Table();
        quitBorder.pad(screenWidth / 500);

        quit.setSize(screenWidth / 20, screenWidth / 20);
        quit.setPosition(screenWidth - (float) (quit.getWidth() * 1.5), screenHeight - (float) (quit.getWidth() * 1.5));


        stage.addActor(quit);
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        strDate = formatter.format(date);

        quit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

                Gdx.app.exit();
                Gdx.app.log("Escape", "Quit at " + strDate);
            }
        });
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    @Override
    public void show()
    {
    }

    @Override
    public void render(float delta)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
            Gdx.app.log("Escape", "Quit at " + strDate);
        }
        batch.begin();

        fondSommaire.myDraw2(batch, screenWidth / 5, screenHeight, 0, 0);
        fondSommairee.myDraw2(batch, screenWidth, screenHeight, ((screenWidth / 5) - (screenWidth / 70)), 0);

        float x = (int) (3 * screenWidth / 5) * (709.0f / 626.0f);
        float y = (3 * screenWidth / 5);
        imgSommaire.myDraw2(batch, (int) x, (int) y, ((screenWidth / 4)), (int) ((screenHeight / 2) - (y / 2)));
//        batch.draw(imgSommaire, 4 * screenWidth / 15, screenHeight / 5, 2 * screenWidth / 5, (((2 * screenWidth / 5) * 600) / 680));
//        test.draw(batch);


        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }

        float logoHeight = screenHeight / 8;

        batch.draw(logoTitre, screenWidth / 40, screenHeight - logoHeight - screenHeight / 50.0f, screenWidth / 8, logoHeight);

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create()
    {
        //camera = new PerspectiveCamera();
        //viewport = new FitViewport(800, 480, camera);

//        stage = new Stage(new StretchViewport(width, height));

    }

    private void setScreen()
    {
    }


    /*
    @Override
    public void resize(int width, int height)
    {
        //stage.getViewport().update(width, height, true);
//        width = 2400;
//        height = 1350;
//        viewport.update(width, height);


    }
*/
    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
