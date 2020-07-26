package com.evalutel.primval_desktop.Sommaire;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.LigneTableauxResultsChapitre;
import com.evalutel.primval_desktop.General.LigneTableauxResults;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.TableauxTitreChapitre;
import com.evalutel.primval_desktop.MrNotes;
import com.evalutel.primval_desktop.MrTemps;
import com.evalutel.primval_desktop.MyButtonRetour;
import com.evalutel.primval_desktop.MyDrawInterface;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class Screen_All_Results extends Game implements Screen, InputProcessor, ApplicationListener
{
    private DatabaseDesktop dataBase;
    protected Stage stage;

    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    private ScreeenBackgroundImage bandeauHaut;
    private ScreeenBackgroundImage fondSommaire;
    private MrNotes mrNotes;
    private MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    ArrayList<Table> tableArrayList = new ArrayList<>();

    MyButtonRetour myButtonRetour;

    TextureRegionDrawable textureRegionDrawableBg;
    TextureRegionDrawable orangeBg;

    public float screenWidth;

    BitmapFont bitmapFontZAP;

    boolean chap1titleClicked, chap2titleClicked = false;

    private Table chapter1Table, chapter2Table, chapter3Table, chapter4Table, chapter5Table, chapter6Table, chapter7Table, chapter8Table, chapter9Table, chapter10Table, chapter11Table, chapter12Table, chapter13Table, chapter14Table, chapter15Table, chapter16Table, chapter17Table, chapter18Table, chapter19Table, chapter20Table, chapter21Table, chapter22Table, chapter23Table, chapter24Table, chapter25Table;
    private Table tableCollapsible1, tableCollapsible2, tableCollapsible3, tableCollapsible4, tableCollapsible5, tableCollapsible6, tableCollapsible7, tableCollapsible8, tableCollapsible9, tableCollapsible10, tableCollapsible11, tableCollapsible12, tableCollapsible13, tableCollapsible14, tableCollapsible15, tableCollapsible16, tableCollapsible17, tableCollapsible18, tableCollapsible19, tableCollapsible20, tableCollapsible21, tableCollapsible22, tableCollapsible23, tableCollapsible24, tableCollapsible25;


    float lineHeight = MyConstants.SCREENHEIGHT / 20;
    float buttonSize = lineHeight / 10;
    int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
    float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 100;

    Texture textureCours, textureExercices;


    public Screen_All_Results(Game game, DatabaseDesktop dataBase)
    {
        this.game = game;
        this.dataBase = dataBase;

        stage = new Stage();
        batch = new SpriteBatch();
        BitmapFont bitmapFontFRHND;

        screenWidth = MyConstants.SCREENWIDTH;

        FreeTypeFontGenerator FONT_FRHND = new FreeTypeFontGenerator(Gdx.files.internal("font/FRHND521_0.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterFRHND = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterFRHND.size = MyConstants.SCREENWIDTH / 40;
        parameterFRHND.minFilter = Texture.TextureFilter.Linear;
        parameterFRHND.magFilter = Texture.TextureFilter.Linear;
        bitmapFontFRHND = MyConstants.FONT_FRHND.generateFont(parameterFRHND);
        FONT_FRHND.dispose();

        FreeTypeFontGenerator FONT_ZAP = new FreeTypeFontGenerator(Gdx.files.internal("font/Zapf Humanist 601 BT.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterZAP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterZAP.size = MyConstants.SCREENWIDTH / 70;
        parameterZAP.minFilter = Texture.TextureFilter.Linear;
        parameterZAP.magFilter = Texture.TextureFilter.Linear;
        bitmapFontZAP = FONT_ZAP.generateFont(parameterZAP);
        FONT_ZAP.dispose();

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontFRHND;
        labelStyleBlue.fontColor = Color.NAVY;

        allDrawables = new ArrayList<>();

        bandeauHaut = new ScreeenBackgroundImage("Images/Pages Chapitres/Bandeau haut.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Backgrounds/web_hi_res_512.png");

        myButtonRetour = new MyButtonRetour(stage, MyConstants.SCREENWIDTH / 15, MyConstants.SCREENWIDTH / 15, game, dataBase, "sommaire general");
        myButtonRetour.setPosition(MyConstants.SCREENWIDTH / 25, 5 * MyConstants.SCREENHEIGHT / 6 - myButtonRetour.getHeight() / 2);


        Label labelChap1Titre = new Label("Résultats obtenus", labelStyleWhite);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(MyConstants.SCREENWIDTH / 2 - MyConstants.SCREENWIDTH / 12, 9 * MyConstants.SCREENHEIGHT / 10);
        stage.addActor(nomChapitre);

        mrTemps = new MrTemps(stage, dataBase, 0);
        mrNotes = new MrNotes(stage, dataBase, 0);

        textureCours = new Texture(Gdx.files.internal("Images/Pages onglets/Cours - onglets.png"));
        textureCours.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        textureExercices = new Texture(Gdx.files.internal("Images/Pages onglets/Exercice-onglets.png"));
        textureExercices.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        Pixmap bgOrange = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgOrange.setColor(Color.rgb888(241, 160, 57));
        bgOrange.fill();
        orangeBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgOrange)));

        Table container = new Table();
        Table table = new Table();

        float positionButton = myButtonRetour.getY();
        float heightContainer = (positionButton);
        container.setSize(MyConstants.SCREENWIDTH, heightContainer - MyConstants.SCREENHEIGHT / 20);
        container.setPosition(0, 0);


        Table chapter1Table = chapter1Results();
        Table chapter2Table = chapter2Results();
        Table chapter3Table = chapter3Results();
        Table chapter4Table = chapter4Results();
        Table chapter5Table = chapter5Results();
        Table chapter6Table = chapter6Results();
        Table chapter7Table = chapter7Results();
        Table chapter8Table = chapter8Results();
        Table chapter9Table = chapter9Results();
        Table chapter10Table = chapter10Results();
        Table chapter11Table = chapter11Results();
        Table chapter12Table = chapter12Results();
        Table chapter13Table = chapter13Results();
        Table chapter14Table = chapter14Results();
        Table chapter15Table = chapter15Results();
        Table chapter16Table = chapter16Results();
        Table chapter17Table = chapter17Results();
        Table chapter18Table = chapter18Results();
        Table chapter19Table = chapter19Results();
        Table chapter20Table = chapter20Results();
        Table chapter21Table = chapter21Results();
        Table chapter22Table = chapter22Results();
        Table chapter23Table = chapter23Results();
        Table chapter24Table = chapter24Results();
        Table chapter25Table = chapter25Results();


        float lineHeight = MyConstants.SCREENHEIGHT / 17f;

        for (int i = 0; i < 25; i++)
        {
            Table tableAux = tableArrayList.get(i);
            table.add(tableAux).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible1.getHeight());
            table.row();

        }


//        table.debug();
//        table.add(chapter1Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible1.getHeight());
//        table.row();
//        table.add(chapter2Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter3Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter4Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter5Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter6Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter7Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter8Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter9Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter10Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter11Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter12Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter13Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter14Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter15Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter16Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter17Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter18Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter19Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter20Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter21Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter22Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter23Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter24Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();
//        table.add(chapter25Table).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible2.getHeight());
//        table.row();


        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(3 * MyConstants.SCREENHEIGHT / 4f).width(screenWidth);

        container.debug();

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
    }

    public Table chapter1Results()
    {
        chapter1Table = new Table();

        String labelChapterTitle = "Pratique des nombres de 1 à 9";
        String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        String label2 = "Faire correspondre des billes à des oiseaux";
        String label3 = "Écriture des chiffres de 1 à 9";
        String label4 = "Prononciation des chiffres de 1 à 9";
        String label5 = "Compter des oiseaux et taper leur nombre";
        String label6 = "Un gâteau pour plusieurs anniversaires";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap1.png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, 1, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", 1, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 5, dataBase);
        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton, label6, textureExercices, "blue", 1, 6, dataBase);


        tableCollapsible1 = new Table();
        tableCollapsible1.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible1.row();
        tableCollapsible1.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible1.row();
        tableCollapsible1.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible1.row();
        tableCollapsible1.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible1.row();
        tableCollapsible1.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible1.row();
        tableCollapsible1.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter1Table.add(tableChapTitle).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter1Table.row();
        chapter1Table.add(tableCollapsible1).width(screenWidth).height(0);
        chapter1Table.row();

        tableCollapsible1.setVisible(false);


        tableChapTitle.addListener(new ClickListener()
                                   {
                                       @Override
                                       public void clicked(InputEvent event, float x, float y)
                                       {
                                           chap1titleClicked = !chap1titleClicked;
                                           System.out.println("chap1titleClicked" + chap1titleClicked + " " + event);
                                       }
                                   }
        );

        tableArrayList.add(chapter1Table);
        return chapter1Table;
    }

    public Table chapter2Results()
    {
        chapter2Table = new Table();
        tableCollapsible2 = new Table();
        int chapitre = 2;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle2 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible2.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter2Table.add(tableChapTitle2).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter2Table.row();
        chapter2Table.add(tableCollapsible2).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter2Table.row();

        tableCollapsible2.setVisible(false);


        tableChapTitle2.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter2Table);
        return chapter2Table;
    }

    public Table chapter3Results()
    {
        chapter3Table = new Table();
        tableCollapsible3 = new Table();
        int chapitre = 3;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle3 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible3.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible3.row();
        tableCollapsible3.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible3.row();
        tableCollapsible3.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible3.row();
        tableCollapsible3.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible3.row();
        tableCollapsible3.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter3Table.add(tableChapTitle3).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter3Table.row();
        chapter3Table.add(tableCollapsible3).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter3Table.row();

        tableCollapsible3.setVisible(false);


        tableChapTitle3.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter3Table);
        return chapter3Table;
    }

    public Table chapter4Results()
    {
        chapter4Table = new Table();
        tableCollapsible4 = new Table();
        int chapitre = 4;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle4 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible4.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible4.row();
        tableCollapsible4.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible4.row();
        tableCollapsible4.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible4.row();
        tableCollapsible4.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible4.row();
        tableCollapsible4.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter4Table.add(tableChapTitle4).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter4Table.row();
        chapter4Table.add(tableCollapsible4).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter4Table.row();

        tableCollapsible4.setVisible(false);


        tableChapTitle4.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter4Table);
        return chapter4Table;
    }

    public Table chapter5Results()
    {
        chapter5Table = new Table();
        tableCollapsible5 = new Table();
        int chapitre = 5;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle5 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible5.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible5.row();
        tableCollapsible5.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible5.row();
        tableCollapsible5.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible5.row();
        tableCollapsible5.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible5.row();
        tableCollapsible5.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter5Table.add(tableChapTitle5).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter5Table.row();
        chapter5Table.add(tableCollapsible5).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter5Table.row();

        tableCollapsible5.setVisible(false);


        tableChapTitle5.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter5Table);
        return chapter5Table;
    }

    public Table chapter6Results()
    {
        chapter6Table = new Table();
        tableCollapsible6 = new Table();
        int chapitre = 6;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle6 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible6.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible6.row();
        tableCollapsible6.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible6.row();
        tableCollapsible6.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible6.row();
        tableCollapsible6.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible6.row();
        tableCollapsible6.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter6Table.add(tableChapTitle6).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter6Table.row();
        chapter6Table.add(tableCollapsible6).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter6Table.row();

        tableCollapsible6.setVisible(false);


        tableChapTitle6.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );


        tableArrayList.add(chapter6Table);
        return chapter6Table;
    }

    public Table chapter7Results()
    {
        chapter7Table = new Table();
        tableCollapsible7 = new Table();
        int chapitre = 7;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle7 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible7.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible7.row();
        tableCollapsible7.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible7.row();
        tableCollapsible7.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible7.row();
        tableCollapsible7.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible7.row();
        tableCollapsible7.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter7Table.add(tableChapTitle7).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter7Table.row();
        chapter7Table.add(tableCollapsible7).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter7Table.row();

        tableCollapsible7.setVisible(false);


        tableChapTitle7.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter7Table);
        return chapter7Table;
    }

    public Table chapter8Results()
    {
        chapter8Table = new Table();
        tableCollapsible8 = new Table();
        int chapitre = 8;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle8 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible8.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible8.row();
        tableCollapsible8.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible8.row();
        tableCollapsible8.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible8.row();
        tableCollapsible8.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible8.row();
        tableCollapsible8.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter8Table.add(tableChapTitle8).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter8Table.row();
        chapter8Table.add(tableCollapsible8).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter8Table.row();

        tableCollapsible8.setVisible(false);


        tableChapTitle8.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter8Table);
        return chapter8Table;
    }

    public Table chapter9Results()
    {
        chapter9Table = new Table();
        tableCollapsible9 = new Table();
        int chapitre = 9;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle9 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible9.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible9.row();
        tableCollapsible9.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible9.row();
        tableCollapsible9.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible9.row();
        tableCollapsible9.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible9.row();
        tableCollapsible9.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter9Table.add(tableChapTitle9).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter9Table.row();
        chapter9Table.add(tableCollapsible9).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter9Table.row();

        tableCollapsible9.setVisible(false);


        tableChapTitle9.addListener(new ClickListener()
                                    {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y)
                                        {
                                            chap2titleClicked = !chap2titleClicked;
                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                        }
                                    }
        );

        tableArrayList.add(chapter9Table);
        return chapter9Table;
    }

    public Table chapter10Results()
    {
        chapter10Table = new Table();
        tableCollapsible10 = new Table();
        int chapitre = 10;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle10 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible10.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible10.row();
        tableCollapsible10.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible10.row();
        tableCollapsible10.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible10.row();
        tableCollapsible10.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible10.row();
        tableCollapsible10.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter10Table.add(tableChapTitle10).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter10Table.row();
        chapter10Table.add(tableCollapsible10).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter10Table.row();

        tableCollapsible10.setVisible(false);


        tableChapTitle10.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter10Table);
        return chapter10Table;
    }

    public Table chapter11Results()
    {
        chapter11Table = new Table();
        tableCollapsible11 = new Table();
        int chapitre = 11;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle11 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible11.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible11.row();
        tableCollapsible11.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible11.row();
        tableCollapsible11.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible11.row();
        tableCollapsible11.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible11.row();
        tableCollapsible11.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter11Table.add(tableChapTitle11).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter11Table.row();
        chapter11Table.add(tableCollapsible11).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter11Table.row();

        tableCollapsible11.setVisible(false);

        tableChapTitle11.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter11Table);
        return chapter11Table;
    }


    public Table chapter12Results()
    {
        chapter12Table = new Table();
        tableCollapsible12 = new Table();
        int chapitre = 12;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle12 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible12.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible12.row();
        tableCollapsible12.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible12.row();
        tableCollapsible12.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible12.row();
        tableCollapsible12.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible12.row();
        tableCollapsible12.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter12Table.add(tableChapTitle12).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter12Table.row();
        chapter12Table.add(tableCollapsible12).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter12Table.row();

        tableCollapsible12.setVisible(false);


        tableChapTitle12.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter12Table);
        return chapter12Table;
    }

    public Table chapter13Results()
    {
        chapter13Table = new Table();
        tableCollapsible13 = new Table();
        int chapitre = 13;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle13 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible13.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible13.row();
        tableCollapsible13.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible13.row();
        tableCollapsible13.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible13.row();
        tableCollapsible13.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible13.row();
        tableCollapsible13.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter13Table.add(tableChapTitle13).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter13Table.row();
        chapter13Table.add(tableCollapsible13).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter13Table.row();

        tableCollapsible13.setVisible(false);


        tableChapTitle13.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter13Table);
        return chapter13Table;
    }

    public Table chapter14Results()
    {
        chapter14Table = new Table();
        tableCollapsible14 = new Table();
        int chapitre = 14;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle14 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible14.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible14.row();
        tableCollapsible14.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible14.row();
        tableCollapsible14.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible14.row();
        tableCollapsible14.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible14.row();
        tableCollapsible14.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter14Table.add(tableChapTitle14).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter14Table.row();
        chapter14Table.add(tableCollapsible14).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter14Table.row();

        tableCollapsible14.setVisible(false);


        tableChapTitle14.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );
        tableArrayList.add(chapter14Table);
        return chapter14Table;
    }

    public Table chapter15Results()
    {
        chapter15Table = new Table();
        tableCollapsible15 = new Table();
        int chapitre = 15;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle15 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible15.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible15.row();
        tableCollapsible15.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible15.row();
        tableCollapsible15.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible15.row();
        tableCollapsible15.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible15.row();
        tableCollapsible15.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter15Table.add(tableChapTitle15).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter15Table.row();
        chapter15Table.add(tableCollapsible15).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter15Table.row();

        tableCollapsible15.setVisible(false);


        tableChapTitle15.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter15Table);
        return chapter15Table;
    }

    public Table chapter16Results()
    {
        chapter16Table = new Table();
        tableCollapsible16 = new Table();
        int chapitre = 16;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle16 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible16.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible16.row();
        tableCollapsible16.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible16.row();
        tableCollapsible16.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible16.row();
        tableCollapsible16.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible16.row();
        tableCollapsible16.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter16Table.add(tableChapTitle16).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter16Table.row();
        chapter16Table.add(tableCollapsible16).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter16Table.row();

        tableCollapsible16.setVisible(false);


        tableChapTitle16.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter16Table);
        return chapter16Table;
    }

    public Table chapter17Results()
    {
        chapter17Table = new Table();
        tableCollapsible17 = new Table();
        int chapitre = 17;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle17 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible17.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible17.row();
        tableCollapsible17.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible17.row();
        tableCollapsible17.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible17.row();
        tableCollapsible17.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible17.row();
        tableCollapsible17.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter17Table.add(tableChapTitle17).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter17Table.row();
        chapter17Table.add(tableCollapsible17).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter17Table.row();

        tableCollapsible17.setVisible(false);


        tableChapTitle17.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter17Table);
        return chapter17Table;
    }

    public Table chapter18Results()
    {
        chapter18Table = new Table();
        tableCollapsible18 = new Table();
        int chapitre = 18;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle18 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible18.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible18.row();
        tableCollapsible18.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible18.row();
        tableCollapsible18.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible18.row();
        tableCollapsible18.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible18.row();
        tableCollapsible18.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter18Table.add(tableChapTitle18).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter18Table.row();
        chapter18Table.add(tableCollapsible18).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter18Table.row();

        chapter18Table.setVisible(false);


        tableChapTitle18.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter18Table);

        return chapter18Table;
    }

    public Table chapter19Results()
    {
        chapter19Table = new Table();
        tableCollapsible19 = new Table();
        int chapitre = 19;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle19 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible19.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible19.row();
        tableCollapsible19.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible19.row();
        tableCollapsible19.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible19.row();
        tableCollapsible19.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible19.row();
        tableCollapsible19.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter19Table.add(tableChapTitle19).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter19Table.row();
        chapter19Table.add(tableCollapsible19).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter19Table.row();

        tableCollapsible19.setVisible(false);


        tableChapTitle19.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter19Table);

        return chapter19Table;
    }


    public Table chapter20Results()
    {
        chapter20Table = new Table();
        tableCollapsible20 = new Table();
        int chapitre = 20;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle20 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible20.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible20.row();
        tableCollapsible20.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible20.row();
        tableCollapsible20.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible20.row();
        tableCollapsible20.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible20.row();
        tableCollapsible20.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter20Table.add(tableChapTitle20).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter20Table.row();
        chapter20Table.add(tableCollapsible20).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter20Table.row();

        tableCollapsible20.setVisible(false);


        tableChapTitle20.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter20Table);

        return chapter20Table;
    }

    public Table chapter21Results()
    {
        chapter21Table = new Table();
        tableCollapsible21 = new Table();
        int chapitre = 21;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle21 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible21.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible21.row();
        tableCollapsible21.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible21.row();
        tableCollapsible21.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible21.row();
        tableCollapsible21.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible21.row();
        tableCollapsible21.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter21Table.add(tableChapTitle21).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter21Table.row();
        chapter21Table.add(tableCollapsible21).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter21Table.row();

        tableCollapsible21.setVisible(false);


        tableChapTitle21.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter21Table);
        return chapter21Table;
    }


    public Table chapter22Results()
    {
        chapter22Table = new Table();
        tableCollapsible22 = new Table();
        int chapitre = 22;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle22 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible22.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible22.row();
        tableCollapsible22.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible22.row();
        tableCollapsible22.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible22.row();
        tableCollapsible22.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible22.row();
        tableCollapsible22.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter22Table.add(tableChapTitle22).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter22Table.row();
        chapter22Table.add(tableCollapsible2).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter22Table.row();

        tableCollapsible22.setVisible(false);


        tableChapTitle22.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter22Table);
        return chapter22Table;
    }

    public Table chapter23Results()
    {
        chapter23Table = new Table();
        tableCollapsible23 = new Table();
        int chapitre = 23;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle23 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible23.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible23.row();
        tableCollapsible23.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible23.row();
        tableCollapsible23.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible23.row();
        tableCollapsible23.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible23.row();
        tableCollapsible23.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter23Table.add(tableChapTitle23).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter23Table.row();
        chapter23Table.add(tableCollapsible23).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter23Table.row();

        tableCollapsible23.setVisible(false);


        tableChapTitle23.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter23Table);
        return chapter23Table;
    }

    public Table chapter24Results()
    {
        chapter24Table = new Table();
        tableCollapsible24 = new Table();
        int chapitre = 24;


        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle24 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible24.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible24.row();
        tableCollapsible24.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible24.row();
        tableCollapsible24.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible24.row();
        tableCollapsible24.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible24.row();
        tableCollapsible24.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter24Table.add(tableChapTitle24).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter24Table.row();
        chapter24Table.add(tableCollapsible24).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter24Table.row();

        tableCollapsible24.setVisible(false);


        tableChapTitle24.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter24Table);

        return chapter24Table;
    }

    public Table chapter25Results()
    {
        chapter25Table = new Table();
        tableCollapsible25 = new Table();
        int chapitre = 25;


        String labelChapterTitle = "La division, une nouvelle opération. Division en ligne et division posée. Partage du reste.";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";


        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSizeOnglet);


        Table tableChapTitle25 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, dataBase);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton, label1, textureCours, "red", chapitre, 1, dataBase);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton, label2, textureExercices, "blue", chapitre, 2, dataBase);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton, label3, textureCours, "red", chapitre, 3, dataBase);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton, label4, textureExercices, "blue", chapitre, 4, dataBase);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton, label5, textureExercices, "blue", chapitre, 5, dataBase);


        tableCollapsible25.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible25.row();
        tableCollapsible25.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible25.row();
        tableCollapsible25.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible25.row();
        tableCollapsible25.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible25.row();
        tableCollapsible25.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        chapter25Table.add(tableChapTitle25).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);
        chapter25Table.row();
        chapter25Table.add(tableCollapsible25).width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f);
        chapter25Table.row();

        tableCollapsible25.setVisible(false);


        tableChapTitle25.addListener(new ClickListener()
                                     {
                                         @Override
                                         public void clicked(InputEvent event, float x, float y)
                                         {
                                             chap2titleClicked = !chap2titleClicked;
                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
                                         }
                                     }
        );

        tableArrayList.add(chapter25Table);
        return chapter25Table;
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
        batch.begin();
        batch.setTransformMatrix(new Matrix4());

        fondSommaire.myDraw2(batch, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT, 0, 0);
        bandeauHaut.myDraw2(batch, MyConstants.SCREENWIDTH, MyConstants.SCREENHEIGHT / 6f, 0, (MyConstants.SCREENHEIGHT - MyConstants.SCREENHEIGHT / 6f));
        for (int i = 0; i < allDrawables.size(); i++)
        {
            MyDrawInterface newItem = allDrawables.get(i);
            if (newItem.isVisible())
            {
                newItem.myDraw(batch);
            }
        }
        if (chap1titleClicked)
        {
            tableCollapsible1.setVisible(true);
            tableCollapsible1.setHeight(MyConstants.SCREENHEIGHT / 4f);
        }
        else
        {
            tableCollapsible1.setVisible(false);
            tableCollapsible1.setHeight(0);

        }

        if (chap2titleClicked)
        {
            tableCollapsible2.setVisible(true);

        }
        else
        {
            tableCollapsible2.setVisible(false);
        }

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void create()
    {
        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);
    }

    private void setScreen()
    {
    }

    @Override
    public void resize(int width, int height)
    {
//        stage.getViewport().update(width, height, true);
//        width = 2400;
//        height = 1350;


//        viewport.update(width, height);
    }

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
