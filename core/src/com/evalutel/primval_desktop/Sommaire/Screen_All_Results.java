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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    protected Stage stage;

    private SpriteBatch batch;
    private Game game;

    private Camera camera;

    private Viewport viewport;

    protected long startTime, endTime, seconds, dateTest;


    private ScreeenBackgroundImage bandeauHaut;
    private ScreeenBackgroundImage fondSommaire;
    private MrNotes mrNotes;
    private MrTemps mrTemps;

    protected ArrayList<MyDrawInterface> allDrawables = new ArrayList<>();
    //    ArrayList<Table> tableArrayList = new ArrayList<>();
//    ArrayList<HorizontalGroup> tableArrayList = new ArrayList<>();
//    ArrayList<HorizontalGroup> arrayList2 = new ArrayList<>();
    ArrayList<Table> arrayList2 = new ArrayList<>();

    MyButtonRetour myButtonRetour;

    TextureRegionDrawable textureRegionDrawableBg;
    TextureRegionDrawable orangeBg;

    public float screenWidth;

    BitmapFont bitmapFontZAP, bitmapFontFRHND, bitmapFontArial, bitmapFontArialBold;

    int lastClicked = -1;
    float collapsibleTableHeight = MyConstants.SCREENHEIGHT / 8f;

    private boolean chap1titleClicked, chap2titleClicked, chap3titleClicked, chap4titleClicked, chap5titleClicked, chap6titleClicked, chap7titleClicked, chap8titleClicked, chap9titleClicked, chap10titleClicked, chap11titleClicked, chap12titleClicked, chap13titleClicked, chap14titleClicked, chap15titleClicked, chap16titleClicked, chap17titleClicked, chap18titleClicked, chap19titleClicked, chap20titleClicked, chap21titleClicked, chap22titleClicked, chap23titleClicked, chap24titleClicked, chap25titleClicked = false;

    //    private Table chapter1Table, chapter2Table, chapter3Table, chapter4Table, chapter5Table, chapter6Table, chapter7Table, chapter8Table, chapter9Table, chapter10Table, chapter11Table, chapter12Table, chapter13Table, chapter14Table, chapter15Table, chapter16Table, chapter17Table, chapter18Table, chapter19Table, chapter20Table, chapter21Table, chapter22Table, chapter23Table, chapter24Table, chapter25Table;
//    private HorizontalGroup chapter1Table, chapter2Table, chapter3Table, chapter4Table, chapter5Table, chapter6Table, chapter7Table, chapter8Table, chapter9Table, chapter10Table, chapter11Table, chapter12Table, chapter13Table, chapter14Table, chapter15Table, chapter16Table, chapter17Table, chapter18Table, chapter19Table, chapter20Table, chapter21Table, chapter22Table, chapter23Table, chapter24Table, chapter25Table;
    private Table tableCollapsible1, tableCollapsible2, tableCollapsible3, tableCollapsible4, tableCollapsible5, tableCollapsible6, tableCollapsible7, tableCollapsible8, tableCollapsible9, tableCollapsible10, tableCollapsible11, tableCollapsible12, tableCollapsible13, tableCollapsible14, tableCollapsible15, tableCollapsible16, tableCollapsible17, tableCollapsible18, tableCollapsible19, tableCollapsible20, tableCollapsible21, tableCollapsible22, tableCollapsible23, tableCollapsible24, tableCollapsible25;
//    private VerticalGroup tableCollapsible1, tableCollapsible2, tableCollapsible3, tableCollapsible4, tableCollapsible5, tableCollapsible6, tableCollapsible7, tableCollapsible8, tableCollapsible9, tableCollapsible10, tableCollapsible11, tableCollapsible12, tableCollapsible13, tableCollapsible14, tableCollapsible15, tableCollapsible16, tableCollapsible17, tableCollapsible18, tableCollapsible19, tableCollapsible20, tableCollapsible21, tableCollapsible22, tableCollapsible23, tableCollapsible24, tableCollapsible25;

//    MyTextButton un_bouton_red, deux_bouton_red, trois_bouton_red, quatre_bouton_red, cinq_bouton_red, six_bouton_red, sept_bouton_red, huit_bouton_red;
//    MyTextButton un_bouton_blue, deux_bouton_blue, trois_bouton_blue, quatre_bouton_blue, cinq_bouton_blue, six_bouton_blue, sept_bouton_blue, huit_bouton_blue;


    private float lineHeight = MyConstants.SCREENHEIGHT / 21f;
    private float buttonSize = lineHeight / 11f;
    private int fontSizeButton = MyConstants.SCREENHEIGHT / 70;
    private int fontSizeOnglet = MyConstants.SCREENHEIGHT / 60;
    private float paddingInterOnglets = -MyConstants.SCREENHEIGHT / 200f;

    private Texture textureCours, textureExercices;

    TextButton.TextButtonStyle buttonStyleRed, buttonStyleBlue;


    public Screen_All_Results(Game game)
    {
        this.game = game;

        stage = new Stage();
        batch = new SpriteBatch();

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


        FreeTypeFontGenerator FONT_ArialBold = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterArialBold = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterArialBold.size = MyConstants.SCREENWIDTH / 70;
        parameterArialBold.minFilter = Texture.TextureFilter.Linear;
        parameterArialBold.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArialBold = FONT_ArialBold.generateFont(parameterArialBold);
        FONT_ArialBold.dispose();

        FreeTypeFontGenerator fontArial = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFontArial = fontArial.generateFont(parameter);
        fontArial.dispose();

        Label.LabelStyle labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFontFRHND;
        labelStyleWhite.fontColor = Color.WHITE;

        Label.LabelStyle labelStyleBlue = new Label.LabelStyle();
        labelStyleBlue.font = bitmapFontFRHND;
        labelStyleBlue.fontColor = Color.NAVY;
        /*--------------------------------------buttonStyleRed---------------------------------------*/
        buttonStyleRed = new TextButton.TextButtonStyle();

        FreeTypeFontGenerator generatorRed = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterRed = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterRed.size = fontSizeButton;
        BitmapFont bitmapFontRed = generatorRed.generateFont(parameter); // font size 12 pixels
        generatorRed.dispose();

        buttonStyleRed.font = bitmapFontRed;

        Texture textureUpRed = new Texture(Gdx.files.internal("Images/red_circle.png"));
        textureUpRed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        SpriteDrawable spriteDrawableUp = new SpriteDrawable(new Sprite(textureUpRed));

        buttonStyleRed.up = spriteDrawableUp;

        /*--------------------------------------buttonStyleBlue---------------------------------------*/

        buttonStyleBlue = new TextButton.TextButtonStyle();

        FreeTypeFontGenerator generatorBlue = new FreeTypeFontGenerator(Gdx.files.internal("font/arial-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterBlue = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterBlue.size = fontSizeButton;
        BitmapFont bitmapFontBlue = generatorBlue.generateFont(parameter); // font size 12 pixels
        generatorBlue.dispose();

        buttonStyleBlue.font = bitmapFontBlue;

        Texture textureUpBlue = new Texture(Gdx.files.internal("Images/blue_circle.png"));
        textureUpBlue.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        SpriteDrawable spriteDrawableUpBlue = new SpriteDrawable(new Sprite(textureUpBlue));

        buttonStyleBlue.up = spriteDrawableUpBlue;

        /*-------------------------------------------------------------------------------------------*/

        allDrawables = new ArrayList<>();

        bandeauHaut = new ScreeenBackgroundImage("Images/Pages Chapitres/Bandeau haut.jpg");

        fondSommaire = new ScreeenBackgroundImage("Images/Backgrounds/web_hi_res_512.png");

        myButtonRetour = new MyButtonRetour(stage, MyConstants.SCREENWIDTH / 15f, MyConstants.SCREENWIDTH / 15f, game, "sommaire general");
        myButtonRetour.setPosition(MyConstants.SCREENWIDTH / 25f, (5 * MyConstants.SCREENHEIGHT / 6f) - myButtonRetour.getHeight() / 2);


        Label labelChap1Titre = new Label("Résultats obtenus", labelStyleWhite);

        Table nomChapitre = TableauxTitreChapitre.getLigne(labelChap1Titre, null);
        nomChapitre.setPosition(MyConstants.SCREENWIDTH / 2 - MyConstants.SCREENWIDTH / 12, 9.2f * MyConstants.SCREENHEIGHT / 10);
        stage.addActor(nomChapitre);

        mrTemps = new MrTemps(stage, 0);
        mrNotes = new MrNotes(stage, 0);

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
        container.setSize(MyConstants.SCREENWIDTH, heightContainer - MyConstants.SCREENHEIGHT / 10f);
        container.setPosition(0, MyConstants.SCREENHEIGHT /15f);

        Table chapter1Table = chapter1Results(1, 36);
        Table chapter2Table = chapter2Results(2, 70);


//        HorizontalGroup chapter1Table = chapter1Results(1, 36);
//        HorizontalGroup chapter2Table = chapter2Results(2, 70);
//        HorizontalGroup chapter3Table = chapter3Results(3, 99);
//        HorizontalGroup chapter4Table = chapter4Results(4, 129);
//        HorizontalGroup chapter5Table = chapter5Results(5, 187);
//        HorizontalGroup chapter6Table = chapter6Results(6, 49);
//        HorizontalGroup chapter7Table = chapter7Results(7, 61);
//        HorizontalGroup chapter8Table = chapter8Results(8, 104);
//        HorizontalGroup chapter9Table = chapter9Results(9, 144);
//        HorizontalGroup chapter10Table = chapter10Results(10, 109);
//        HorizontalGroup chapter11Table = chapter11Results(11, 312);
//        HorizontalGroup chapter12Table = chapter12Results(12, 56);
//        HorizontalGroup chapter13Table = chapter13Results(13, 120);
//        HorizontalGroup chapter14Table = chapter14Results(14, 162);
//        HorizontalGroup chapter15Table = chapter15Results(15, 138);
//        HorizontalGroup chapter16Table = chapter16Results(16, 103);
//        HorizontalGroup chapter17Table = chapter17Results(17, 176);
//        HorizontalGroup chapter18Table = chapter18Results(18, 155);
//        HorizontalGroup chapter19Table = chapter19Results(19, 171);
//        HorizontalGroup chapter20Table = chapter20Results(20, 159);
//        HorizontalGroup chapter21Table = chapter21Results(21, 326);
//        HorizontalGroup chapter22Table = chapter22Results(22, 144);
//        HorizontalGroup chapter23Table = chapter23Results(23, 203);
//        HorizontalGroup chapter24Table = chapter24Results(24, 160);
//        HorizontalGroup chapter25Table = chapter25Results(25, 220);


        float lineHeight = MyConstants.SCREENHEIGHT / 17f;
//
//        for (int i = 0; i < tableArrayList.size(); i++)
//        {
////            Table tableAux = tableArrayList.get(i);
//            HorizontalGroup hgAux = tableArrayList.get(i);
//            table.add(hgAux).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(tableCollapsible1.getHeight());
//            table.row();
//
//            arrayList2.add(hgAux);
//
//        }
        for (int i = 0; i < arrayList2.size(); i++)
        {
//            Table tableAux = tableArrayList.get(i);
            Table tableAux = arrayList2.get(i);
            table.add(tableAux).width(MyConstants.SCREENWIDTH).padBottom(lineHeight).height(lineHeight * 4);
            table.row();

//            arrayList2.add(tableAux);

        }


        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(/*3.5f **/ 10000/* / 4f*/).width(screenWidth);

        scroll.setPosition(0, 0);


        container.debug();

        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
    }

    public /*HorizontalGroup*/ Table chapter1Results(int chapitre, int noteMaxPossible)
    {
//        chapter1Table = new HorizontalGroup();
        Table chapter1Table = new Table();
        tableCollapsible1 = new Table();

        String labelChapterTitle = "Pratique des nombres de 1 à 9";
        String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        String label2 = "Faire correspondre des billes à des oiseaux";
        String label3 = "Écriture des chiffres de 1 à 9";
        String label4 = "Prononciation des chiffres de 1 à 9";
        String label5 = "Compter des oiseaux et taper leur nombre";
        String label6 = "Un gâteau pour plusieurs anniversaires";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);

        Table tableChapTitle1 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArialBold);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);

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

        tableCollapsible1.setHeight(collapsibleTableHeight);

////        chapter1Table.addActor(tableChapTitle)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//        chapter1Table.add(tableChapTitle1).width(screenWidth).height(lineHeight);
////        chapter1Table.addActor(tableCollapsible1)/*.width(screenWidth).height(0)*/;
//        chapter1Table.row();
//        chapter1Table.add(tableCollapsible1).width(screenWidth).height(0);
//

//        chapter1Table.addActor(tableChapTitle1);
        chapter1Table.add(tableChapTitle1).width(screenWidth).height(lineHeight /** 0.5f*/)/*.padBottom(-MyConstants.SCREENHEIGHT / 100)*/;
        chapter1Table.row();
        chapter1Table.add(tableCollapsible1).width(screenWidth).height(lineHeight * 6f).padBottom(-2 * paddingInterOnglets);

//        chapter1Table.grow();


//        tableChapTitle1.addListener(new ClickListener()
//                                    {
//                                        @Override
//                                        public void clicked(InputEvent event, float x, float y)
//                                        {
//                                            chap1titleClicked = !chap1titleClicked;
//                                            System.out.println("chap1titleClicked" + chap1titleClicked + " " + event);
//                                        }
//                                    }
//        );

//        tableArrayList.add(chapter1Table);
        arrayList2.add(chapter1Table);
        return chapter1Table;
    }

    public /*HorizontalGroup*/Table chapter2Results(int chapitre, int noteMaxPossible)
    {
        Table chapter2Table = new Table();
//        chapter2Table = new HorizontalGroup();
        Table/**/   tableCollapsible2 = new Table();

        String labelChapterTitle = "Introduction de l'Addition";
        String label1 = "Addition dont le total ne dépasse pas 9";
        String label2 = "Additionner les oiseaux sur les deux branches";
        String label3 = "Total d'un lancer de 2 dés";
        String label4 = "Utiliser la même couleur pour colorier les cases avec le même total";
        String label5 = "Calcul mental";

        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);

        Table tableChapTitle2 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArialBold);
        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);

        tableCollapsible2.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight)/*.padTop(MyConstants.SCREENHEIGHT / 250f)*/;
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
        tableCollapsible2.row();
        tableCollapsible2.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);

        tableCollapsible2.setHeight(collapsibleTableHeight);

//        chapter2Table.addActor(tableChapTitle2);
        chapter2Table.add(tableChapTitle2).width(screenWidth).height(lineHeight);
//        chapter2Table.addActor(tableCollapsible2);
        chapter2Table.row();
        chapter2Table.add(tableCollapsible2).width(screenWidth).height(lineHeight * 5);
//        chapter2Table.wrap();
//        chapter2Table.add(tableChapTitle2).height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight);


//        tableChapTitle2.addListener(new ClickListener()
//                                    {
//                                        @Override
//                                        public void clicked(InputEvent event, float x, float y)
//                                        {
//                                            chap2titleClicked = !chap2titleClicked;
//                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                        }
//                                    }
//        );

//        tableArrayList.add(chapter2Table);
        arrayList2.add(chapter2Table);
        return chapter2Table;
    }

//    public HorizontalGroup chapter3Results(int chapitre, int noteMaxPossible)
//    {
//        chapter3Table = new HorizontalGroup();
//        tableCollapsible3 = new Table();
//
//
//        String labelChapterTitle = "Les nombres de 1 à 69. Introduction du zéro et des dizaines";
//        String label1 = "Introduction des dizaines. Les chiffres de 11 à 19";
//        String label2 = "Combien de billes dans la boîte? Je les range, j'inscris et je dis leur nombre ";
//        String label3 = "Les nombres de 1 à 69. Introduction du zéro et des dizaines";
//
//        String label4 = " Représenter, taper et prononcer les âges des membres de la famille Cochet";
//        String label5 = " Prononciation des chiffres de 10 à 69";
//        String label6 = " Addition de nombres sans retenue avec un total inférieur à 70";
//        String label7 = " Addition de nombres sans retenue avec un total inférieur à 70";
//
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle3 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//
//
//        tableCollapsible3.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible3.row();
//        tableCollapsible3.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        tableCollapsible3.setHeight(collapsibleTableHeight);
//
//        chapter3Table.addActor(tableChapTitle3)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter3Table.row();
//        chapter3Table.addActor(tableCollapsible3)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
////        chapter3Table.row();
//
//        chapter3Table.wrap();
//
//
//        tableChapTitle3.addListener(new ClickListener()
//                                    {
//                                        @Override
//                                        public void clicked(InputEvent event, float x, float y)
//                                        {
//                                            chap3titleClicked = !chap3titleClicked;
//                                            System.out.println("chap3titleClicked" + chap3titleClicked + " " + event);
//                                        }
//                                    }
//        );
//
//        tableArrayList.add(chapter3Table);
//        return chapter3Table;
//    }
//
////    public HorizontalGroup chapter4Results(int chapitre, int noteMaxPossible)
////    {
////        startTime = System.currentTimeMillis();
////
////        chapter4Table = new HorizontalGroup();
////        tableCollapsible4 = new Table();
////
////
////        String labelChapterTitle = "Les nombres de 1 à 99. Additions sans retenue";
////        String label1 = " Introduction des dizaines: 70, 80, 90.";
////        String label2 = " Pratique des nombres de 70 à 79";
////        String label3 = " Pratique des nombres de 80 à 89";
////        String label4 = " Pratique des nombres de 90 à 99";
////        String label5 = "  Addition de nombres sans retenue avec un total inférieur à 100 ";
////        String label6 = "  Compléter un nombre au nombre de dizaines le plus proche ";
////        String label7 = "  Apprendre les nombres de 1 à 99 en jouant au Loto ";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//////        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle4 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        tableCollapsible4.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible4.row();
////        tableCollapsible4.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible4.setHeight(collapsibleTableHeight);
////
////        chapter4Table.addActor(tableChapTitle4)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter4Table.row();
////        chapter4Table.addActor(tableCollapsible4)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
//////        chapter4Table.row();
////
////        chapter4Table.wrap();
////
////        tableChapTitle4.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap4titleClicked = !chap4titleClicked;
////                                            System.out.println("chap4titleClicked" + chap4titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////        tableArrayList.add(chapter4Table);
////
////
////        endTime = System.currentTimeMillis();
////        seconds = (endTime - startTime) /*/ 1_000L*/;
////        System.out.println("chapter4 results" + seconds);
////        return chapter4Table;
////    }
////
////    private HorizontalGroup chapter5Results(int chapitre, int noteMaxPossible)
////    {
////
////        long startTime = System.currentTimeMillis();
////        chapter5Table = new HorizontalGroup();
////        tableCollapsible5 = new Table();
////
////
////        String labelChapterTitle = "Additions avec retenue. Calcul mental";
////        String label1 = "Addition par calcul mental";
////        String label2 = "Introduction de l'addition avec retenue";
////        String label3 = "Additionner deux nombres dont le total est inférieur à 99";
////        String label4 = "Additionner trois nombres de 1 chiffre sur Badix et taper le résultat";
////        String label5 = "Additionner trois nombres de 1 chiffre sur Badix et taper le résultat";
////        String label6 = "Additionner trois nombres sur l’ardoise";
////        String label7 = "Utiliser la même couleur pour colorier les cases avec le même total ";
////
////        long startTimebouton = System.currentTimeMillis();
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleRed);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", /* "Images/blue_circle.png",*/ buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5",  /* "Images/blue_circle.png",*/ buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", /* "Images/blue_circle.png",*/ buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7",  /* "Images/blue_circle.png",*/ buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8",  /* "Images/blue_circle.png",*/ buttonSize, buttonStyleBlue);
////
////
////        long boutonStependTime = System.currentTimeMillis();
////        long boutonStep = (boutonStependTime - startTimebouton) /*/ 1_000L*/;
////        System.out.println("chapter5 results bouton " + boutonStep);
////
////        long startTimelignetable = System.currentTimeMillis();
////
////        Table tableChapTitle5 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        long ligneeTable = System.currentTimeMillis();
////        long tableStep = (ligneeTable - startTimelignetable) /*/ 1_000L*/;
////        System.out.println("chapter5 table " + tableStep);
////
////        long addTable = System.currentTimeMillis();
////
////
////        tableCollapsible5.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible5.row();
////        tableCollapsible5.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////
////        tableCollapsible5.setHeight(collapsibleTableHeight);
////        long endTable = System.currentTimeMillis();
////
////        long addtableStep = (endTable - addTable) /*/ 1_000L*/;
////        System.out.println("chapter5 add table dessin " + addtableStep);
////
////
////        chapter5Table.addActor(tableChapTitle5)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter5Table.addActor(tableCollapsible5)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
////
////        chapter5Table.wrap();
////
////
////        tableChapTitle5.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap5titleClicked = !chap5titleClicked;
////                                            System.out.println("chap5titleClicked" + chap5titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////        tableArrayList.add(chapter5Table);
////
////        long endTime = System.currentTimeMillis();
////        long seconds = (endTime - startTime) /*/ 1_000L*/;
////        System.out.println("chapter5 results all " + seconds);
////        return chapter5Table;
////    }
////
////    public HorizontalGroup chapter6Results(int chapitre, int noteMaxPossible)
////    {
////        chapter6Table = new HorizontalGroup();
////        tableCollapsible6 = new Table();
////
////        String labelChapterTitle = "Outils de la géométrie. Triangles. Points alignés.";
////        String label1 = "Le point, la règle, le crayon, le segment.";
////        String label2 = "Tracer les segments joignant 3 points pour construire un triangle ";
////        String label3 = "Mesurer la longueur d’un segment. ";
////        String label4 = "Mesurer les côtés d’un triangle présenté.";
////        String label5 = "Reproduire un triangle isocèle et  un triangle rectangle.";
////        String label6 = "Points alignés. Trouver et tracer 3 droites portant 3 points ou plus";
////        String label7 = "Activité libre";
////
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
////        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//////        MyTextButton trois_bouton_blue = new MyTextButton("3",  buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//////        MyTextButton sept_bouton_blue = new MyTextButton("7",  buttonSize, buttonStyleBlue);
//////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle6 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_red, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        tableCollapsible6.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible6.row();
////        tableCollapsible6.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible6.setHeight(collapsibleTableHeight);
////
////        chapter6Table.addActor(tableChapTitle6)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter6Table.row();
////        chapter6Table.addActor(tableCollapsible6)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
//////        chapter6Table.row();
////
////        chapter1Table.wrap();
////
////        tableChapTitle6.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap6titleClicked = !chap6titleClicked;
////                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////
////        tableArrayList.add(chapter6Table);
////        return chapter6Table;
////    }
////
////    public HorizontalGroup chapter7Results(int chapitre, int noteMaxPossible)
////    {
////        chapter7Table = new HorizontalGroup();
////        tableCollapsible7 = new Table();
////
////
////        String labelChapterTitle = "L’équerre et l’angle droit. Le rectangle et le carré.";
////        String label1 = "L'équerre, le tracé d'un angle droit";
////        String label2 = "Distance d’un point à une droite.";
////        String label3 = " Tracer et mesurer une hauteur d’un triangle ";
////        String label4 = "Un quadrilatère quelconque. Le rectangle, le carré. ";
////        String label5 = " Tracé d’un rectangle en se guidant du quadrillage. ";
////        String label6 = " Tracé d’un rectangle connaissant les longueurs des côtés ";
////        String label7 = " Tracés de carrés ";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
////        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle7 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        tableCollapsible7.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible7.row();
////        tableCollapsible7.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible7.setHeight(collapsibleTableHeight);
////
////        chapter7Table.addActor(tableChapTitle7)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter7Table.addActor(tableCollapsible7)/*.width(screenWidth).height(0)*/;
////
////        chapter7Table.wrap();
////
////
////        tableChapTitle7.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap7titleClicked = !chap7titleClicked;
////                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////        tableArrayList.add(chapter7Table);
////        return chapter7Table;
////    }
//
////    public HorizontalGroup chapter8Results(int chapitre, int noteMaxPossible)
////    {
////        chapter8Table = new HorizontalGroup();
////        tableCollapsible8 = new Table();
////
////        String labelChapterTitle = "La centaine. La monnaie, acheter avec des youpies";
////        String label1 = "Introduction de la centaine";
////        String label2 = "Représenter sur Badix des nombres contenant des centaines ";
////        String label3 = " Additionner des nombres avec des centaines";
////        String label4 = "Une monnaie pour jouer à faire des achats : les youpies !";
////        String label5 = "Payer des montants ne comprenant que des dizaines ou des centaines";
////        String label6 = "Savoir faire l’appoint avec le moins de billets et de pièces";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//////        MyTextButton quatre_bouton_blue = new MyTextButton("4",  buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//////        MyTextButton sept_bouton_blue = new MyTextButton("7",  buttonSize, buttonStyleBlue);
//////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle8 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_red, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        tableCollapsible8.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible8.row();
////        tableCollapsible8.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible8.row();
////        tableCollapsible8.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible8.row();
////        tableCollapsible8.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible8.row();
////        tableCollapsible8.row();
////        tableCollapsible8.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible8.setHeight(collapsibleTableHeight);
////
////        chapter8Table.addActor(tableChapTitle8)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter8Table.row();
////        chapter8Table.addActor(tableCollapsible8)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
//////        chapter8Table.row();
////
////        chapter8Table.wrap();
////
////
////        tableChapTitle8.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap8titleClicked = !chap8titleClicked;
////                                            System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////        tableArrayList.add(chapter8Table);
////        return chapter8Table;
////    }
////
////    public HorizontalGroup chapter9Results(int chapitre, int noteMaxPossible)
////    {
////        chapter9Table = new HorizontalGroup();
////        tableCollapsible9 = new Table();
////
////        String labelChapterTitle = "La soustraction sous deux formes. Calcul mental";
////        String label1 = "Introduction de la soustraction";
////        String label2 = "Soustractions sans retenues sur Badix et sur l’ardoise. ";
////        String label3 = "Faire une soustraction en rendant la monnaie ";
////        String label4 = "Soustractions en complétant  les unités puis les dizaines. ";
////        String label5 = " Effectuer des soustractions par calcul mental.";
////        String label6 = " La soustraction avec retenue  ";
////        String label7 = "Soustractions avec retenue sur Badix et sur l’ardoise";
////        String label8 = "Soustractions en complétant  les unités, les dizaines et les centaines ";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//////        MyTextButton trois_bouton_blue = new MyTextButton("3",  buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//////        MyTextButton six_bouton_blue = new MyTextButton("6",  buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle9 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_red, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible9.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible9.row();
////        tableCollapsible9.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible9.setHeight(collapsibleTableHeight);
////
////
////        chapter9Table.addActor(tableChapTitle9)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter9Table.row();
////        chapter9Table.addActor(tableCollapsible9)/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
//////        chapter9Table.row();
////
////        chapter9Table.wrap();
////
////
////        tableChapTitle9.addListener(new ClickListener()
////                                    {
////                                        @Override
////                                        public void clicked(InputEvent event, float x, float y)
////                                        {
////                                            chap9titleClicked = !chap9titleClicked;
////                                            System.out.println("chap9titleClicked" + chap2titleClicked + " " + event);
////                                        }
////                                    }
////        );
////
////        tableArrayList.add(chapter9Table);
////        return chapter9Table;
////    }
////
////    public HorizontalGroup chapter10Results(int chapitre, int noteMaxPossible)
////    {
////        chapter10Table = new HorizontalGroup();
////        tableCollapsible10 = new Table();
////
////
////        String labelChapterTitle = "Le compas, le cercle et le disque. Outils de translation";
////        String label1 = "Le compas sert à tracer un cercle et à mesurer des distances9";
////        String label2 = "Triangle rectangle et rectangle inscrits dans un cercle";
////        String label3 = "Tracé de deux cercles qui ne se coupent pas ";
////        String label4 = "Tracé et propriétés de deux cercles qui se coupent en deux points ";
////        String label5 = "Mesure d’une distance à l’aide du compas gradué. Outils de translation.";
////        String label6 = "Tracer un triangle et mesurer ses côtés au compas";
////        String label7 = "Dessiner un collier de perlesl";
////        String label8 = "Construire des parallèles à une droite passant par des points tracés";
////
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
////        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//////        MyTextButton six_bouton_blue = new MyTextButton("6",  buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle10 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_red, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(sept_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible10.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible10.row();
////        tableCollapsible10.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible10.setHeight(collapsibleTableHeight);
////
////        chapter10Table.addActor(tableChapTitle10)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter10Table.row();
////        chapter10Table.addActor(tableCollapsible10)/*/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
//////        chapter10Table.row();
////
//////        tableCollapsible10.setVisible(false);
////
////
////        tableChapTitle10.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap10titleClicked = !chap10titleClicked;
////                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter10Table);
////        return chapter10Table;
////    }
////
////    public HorizontalGroup chapter11Results(int chapitre, int noteMaxPossible)
////    {
////        chapter11Table = new HorizontalGroup();
////        tableCollapsible11 = new Table();
////
////
////        String labelChapterTitle = "Ranger dans l’ordre les entiers avec ou sans des sauts";
////        String label1 = "Comparaison de deux nombres entiers ";
////        String label2 = "Représenter deux nombres sur Badix et les comparer.";
////        String label3 = "Ranger des nombres du plus petit au plus grand";
////        String label4 = "Suite de nombres entiers qui se suivent de 1 en 1";
////        String label5 = "Suite de nombres entiers qui se suivent de 2 en 2";
////        String label6 = "Suite de nombres entiers qui se suivent de 10 en 10";
////        String label7 = "Suite de nombres entiers qui se suivent de 5 en 5";
////        String label8 = "Suite de nombres entiers qui se suivent de 3 en 3";
////
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle11 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible11.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible11.row();
////        tableCollapsible11.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////
////        tableCollapsible11.setHeight(collapsibleTableHeight);
////
////        chapter11Table.addActor(tableChapTitle11)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter11Table.addActor(tableCollapsible11)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter11Table.wrap();
////
////        tableChapTitle11.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap11titleClicked = !chap11titleClicked;
////                                             System.out.println("chap11titleClicked" + chap11titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter11Table);
////        return chapter11Table;
////    }
////
//
////    public HorizontalGroup chapter12Results(int chapitre, int noteMaxPossible)
////    {
////        chapter12Table = new HorizontalGroup();
////        tableCollapsible12 = new Table();
////
////        String labelChapterTitle = "Faire des achats à la boulangerie, payer et rendre la monnaie.";
////        String label1 = "Acheter du pain, un croissant ou un pain au chocolat.";
////        String label2 = "Acheter trois sandwichs pour le repas de midi.";
////        String label3 = "Acheter 2 friandises et rendre la monnaie sur 50 ";
////        String label4 = "Acheter 2 gâteaux et rendre la monnaie sur 500";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
//////       MyTextButton un_bouton_red = new MyTextButton("1",  buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//////        MyTextButton cinq_bouton_blue = new MyTextButton("5",  buttonSize, buttonStyleBlue);
//////        MyTextButton six_bouton_blue = new MyTextButton("6",  buttonSize, buttonStyleBlue);
//////        MyTextButton sept_bouton_blue = new MyTextButton("7",  buttonSize, buttonStyleBlue);
//////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle12 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_blue, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////        tableCollapsible12.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible12.row();
////        tableCollapsible12.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible12.row();
////        tableCollapsible12.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible12.row();
////        tableCollapsible12.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//////        tableCollapsible12.row();
//////        tableCollapsible12.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible12.setHeight(collapsibleTableHeight);
////
////        chapter12Table.addActor(tableChapTitle12)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter12Table.addActor(tableCollapsible12)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter12Table.wrap();
////
////
////        tableChapTitle12.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap12titleClicked = !chap12titleClicked;
////                                             System.out.println("chap12titleClicked" + chap12titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter12Table);
////        return chapter12Table;
////    }
////
////    public HorizontalGroup chapter13Results(int chapitre, int noteMaxPossible)
////    {
////        chapter13Table = new HorizontalGroup();
////        tableCollapsible13 = new Table();
////
////        String labelChapterTitle = "La multiplication par 2. Le boulier. Nombres pairs et impairs.";
////        String label1 = "La multiplication : une addition répétée du même nombre. Le boulier";
////        String label2 = "Pratique de la table de multiplication par 2.";
////        String label3 = "Transformer une addition de nombres 2 en multiplication";
////        String label4 = "Compter les chaussettes dans des paires de chaussettes";
////        String label5 = "Christian veut acheter des macarons  pour lui et pour sa sœur Nathalie";
////        String label6 = "Calcul mental sur la table de multiplication par 2";
////        String label7 = " Sur la plage. Combien de traces de pas après des sauts sur le sable ?";
////        String label8 = "Reconnaître les nombres pairs et impairs entre 1 et 20";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle13 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible13.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible13.row();
////        tableCollapsible13.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible13.setHeight(collapsibleTableHeight);
////
////        chapter13Table.addActor(tableChapTitle13)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter13Table.addActor(tableCollapsible13)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter13Table.wrap();
////
////
////        tableChapTitle13.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap13titleClicked = !chap13titleClicked;
////                                             System.out.println("chap13titleClicked" + chap13titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter13Table);
////        return chapter13Table;
////    }
////
////    public HorizontalGroup chapter14Results(int chapitre, int noteMaxPossible)
////    {
////        chapter14Table = new HorizontalGroup();
////        tableCollapsible14 = new Table();
////
////        String labelChapterTitle = "La table de multiplication par 3. Périmètre du triangle équilatéral.";
////        String label1 = "La multiplication par 3 sur le boulier";
////        String label2 = "Pratique de la table de multiplication par 3.";
////        String label3 = " Transformer une addition de nombres 3 en multiplication.";
////        String label4 = "Construction d’un triangle équilatéral. Calcul de son périmètre.";
////        String label5 = "Triangles équilatéraux emboités";
////        String label6 = "Calcul mental sur la table de multiplication par 3";
////        String label7 = "Combien de bougies dans des paquets de 3";
////        String label8 = "Utiliser la même couleur pour colorier les cases avec la même valeur.";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//////        MyTextButton quatre_bouton_blue = new MyTextButton("4",  buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle14 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_red, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////        tableCollapsible14.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible14.row();
////        tableCollapsible14.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible14.setHeight(collapsibleTableHeight);
////
////        chapter14Table.addActor(tableChapTitle14)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter14Table.addActor(tableCollapsible14)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter14Table.wrap();
////
////
////        tableChapTitle14.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap2titleClicked = !chap2titleClicked;
////                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                         }
////                                     }
////        );
////        tableArrayList.add(chapter14Table);
////        return chapter14Table;
////    }
////
////    public HorizontalGroup chapter15Results(int chapitre, int noteMaxPossible)
////    {
////        chapter15Table = new HorizontalGroup();
////        tableCollapsible15 = new Table();
////
////
////        String labelChapterTitle = "La table de multiplication par 4. Périmètre du carré. Associativité de la multiplication.";
////        String label1 = "La table de multiplication par 4. Périmètre du carré. Associativité de la multiplication.";
////        String label2 = " Pratique de la table de multiplication par 4.";
////        String label3 = "La multiplication est associative. Exemple de la multiplication par 4.";
////        String label4 = " Retrouver la table de multiplication par 4 en utilisant l’associativité.";
////        String label5 = "Construction du carré. Calcul de son périmètre.";
////        String label6 = "Construction et périmètres de carrés emboités";
////        String label7 = "Calcul mental sur la table de multiplication par 4";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
////        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//////        MyTextButton trois_bouton_blue = new MyTextButton("3",  buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//////        MyTextButton cinq_bouton_blue = new MyTextButton("5",  buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle15 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_red, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////
////
////        tableCollapsible15.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible15.row();
////        tableCollapsible15.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////
////        tableCollapsible15.setHeight(collapsibleTableHeight);
////
////        chapter15Table.addActor(tableChapTitle15)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter15Table.addActor(tableCollapsible15)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter15Table.wrap();
////
////        tableChapTitle15.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap2titleClicked = !chap2titleClicked;
////                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter15Table);
////        return chapter15Table;
////    }
////
////    public HorizontalGroup chapter16Results(int chapitre, int noteMaxPossible)
////    {
////        chapter16Table = new HorizontalGroup();
////        tableCollapsible16 = new Table();
////
////
////        String labelChapterTitle = "La multiplication par 5. Commutativité de la multiplication. ";
////        String label1 = "La multiplication par 5 sur le boulier";
////        String label2 = "Pratique de la table de multiplication par 5.";
////        String label3 = "La multiplication est commutative. Exemple de la multiplication par 5";
////        String label4 = "Vérifier la commutativité sur 2 multiplications déjà rencontrés";
////        String label5 = "La commutativité permet de découvrir le résultat de nouvelles multiplications";
////        String label6 = " Calcul mental sur la table de multiplication par 5";
////        String label7 = "Christian et Nathalie collectionnent des étoiles de mer";
////        String label8 = "Acheter des gâteaux pour les invités\"";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle16 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible16.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible16.row();
////        tableCollapsible16.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible16.setHeight(collapsibleTableHeight);
////
////        chapter16Table.addActor(tableChapTitle16)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter16Table.addActor(tableCollapsible16)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter16Table.wrap();
////
////
////        tableChapTitle16.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap16titleClicked = !chap16titleClicked;
////                                             System.out.println("chap16titleClicked" + chap16titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter16Table);
////        return chapter16Table;
////    }
//
////    public HorizontalGroup chapter17Results(int chapitre, int noteMaxPossible)
////    {
////        chapter17Table = new HorizontalGroup();
////        tableCollapsible17 = new Table();
////
////        String labelChapterTitle = "Le nombre 1000 et les multiples de 10. Les multiplications par 10, 100, 1000…";
////        String label1 = "Introduction du nombre 1000 et de ses multiples par 10";
////        String label2 = "Analyser le contenu en décades d’un nombre donné";
////        String label3 = "Addition de nombres faisant intervenir des unités de mille.";
////        String label4 = "Multiplication d’un nombre  par 10 et par un multiple de 10";
////        String label5 = "Multiplier  un nombre à 1 chiffre par un multiple de 10.";
////        String label6 = "Multiplication d’un nombre  par 100 et par un multiple de 100";
////        String label7 = "Savoir multiplier un nombre par des multiples de 100";
////        String label8 = "Recette d’une caissière d’un magasin de sport";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
//////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
//////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
////
//////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
////        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//////        MyTextButton quatre_bouton_blue = new MyTextButton("4",  buttonSize, buttonStyleBlue);
////        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//////        MyTextButton six_bouton_blue = new MyTextButton("6",  buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
////
////
////        Table tableChapTitle17 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
////        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
////        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
////        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "blue", chapitre, 3, bitmapFontArial);
////        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_red, label4, textureExercices, "red", chapitre, 4, bitmapFontArial);
////        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
////        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_red, label6, textureExercices, "red", chapitre, 6, bitmapFontArial);
////        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
////        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
////
////
////        tableCollapsible17.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////        tableCollapsible17.row();
////        tableCollapsible17.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
////
////        tableCollapsible17.setHeight(collapsibleTableHeight);
////
////        chapter17Table.addActor(tableChapTitle17)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
//////        chapter1Table.row();
////        chapter17Table.addActor(tableCollapsible17)/*.width(screenWidth).height(0)*/;
//////        chapter1Table./*.width(screenWidth).height(0)*/;
//////        chapter1Table.row();
//////        tableCollapsible1.setVisible(false);
////        chapter17Table.wrap();
////
////
////        tableChapTitle17.addListener(new ClickListener()
////                                     {
////                                         @Override
////                                         public void clicked(InputEvent event, float x, float y)
////                                         {
////                                             chap17titleClicked = !chap17titleClicked;
////                                             System.out.println("chap17titleClicked" + chap17titleClicked + " " + event);
////                                         }
////                                     }
////        );
////
////        tableArrayList.add(chapter17Table);
////        return chapter17Table;
////    }
////
////    public HorizontalGroup chapter18Results(int chapitre, int noteMaxPossible)
////    {
////        chapter18Table = new HorizontalGroup();
////        tableCollapsible18 = new Table();
////
////        String labelChapterTitle = "La multiplication par 6. Distributivité de la multiplication.";
////        String label1 = "La multiplication par 6 sur le boulier";
////        String label2 = "Pratique de la table de multiplication par 6.";
////        String label3 = "La multiplication est  distributive sur l’addition. Applications.";
////        String label4 = "Effectuer une multiplication en utilisant la distributivité ";
////        String label5 = "Calcul mental sur la table de multiplication par 6";
////        String label6 = "Pentagone régulier inscrit dans un cercle.";
////        String label7 = "Construire un pentagone régulier. Périmètre du pentagone. ";
////        String label8 = "Utiliser la même couleur pour colorier les cases avec la même valeur.";
////
////        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
////
////        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
//////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3",  buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
////        MyTextButton six_bouton_blue = new MyTextButton("6",  buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle18 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_red, label6, textureExercices, "red", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
//
//        tableCollapsible18.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible18.row();
//        tableCollapsible18.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        tableCollapsible18.setHeight(collapsibleTableHeight);
//
//        chapter18Table.addActor(tableChapTitle18)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter1Table.row();
//        chapter18Table.addActor(tableCollapsible18)/*.width(screenWidth).height(0)*/;
////        chapter1Table./*.width(screenWidth).height(0)*/;
////        chapter1Table.row();
////        tableCollapsible1.setVisible(false);
//        chapter18Table.wrap();
//
//        tableChapTitle18.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap2titleClicked = !chap2titleClicked;
//                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter18Table);
//
//        return chapter18Table;
//    }
//
//    public HorizontalGroup chapter19Results(int chapitre, int noteMaxPossible)
//    {
//        chapter19Table = new HorizontalGroup();
//        tableCollapsible19 = new Table();
//
//        String labelChapterTitle = "La table de multiplication par 7, 8 et 9.";
//        String label1 = " La multiplication par 7 sur le boulier";
//        String label2 = "Pratique de la table de multiplication par 7.";
//        String label3 = "Effectuer une multiplication par 7 en utilisant la distributivité ";
//        String label4 = " La multiplication par 8 sur le boulier";
//        String label5 = "Multiplication par 8 en utilisant l’associativité";
//        String label6 = "Calcul mental sur la table de multiplication par 8";
//        String label7 = "La multiplication par 9 sur le boulier";
//        String label8 = "Calcul mental sur la table de multiplication par 9";
//        String label9 = "Utiliser la même couleur pour colorier les cases avec la même valeur.";
//
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
////        MyTextButton quatre_bouton_blue = new MyTextButton("4",  buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
////        MyTextButton sept_bouton_blue = new MyTextButton("7",  buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//        MyTextButton neuf_bouton_blue = new MyTextButton("9", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle19 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureExercices, "blue", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_red, label4, textureCours, "red", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_red, label7, textureCours, "red", chapitre, 7, bitmapFontArial);
//        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
//        Table tableEx9 = LigneTableauxResults.getLigne(neuf_bouton_blue, label9, textureExercices, "blue", chapitre, 9, bitmapFontArial);
//
//
//        tableCollapsible19.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible19.row();
//        tableCollapsible19.add(tableEx9).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        tableCollapsible19.setHeight(collapsibleTableHeight);
//
//        chapter19Table.addActor(tableChapTitle19)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter1Table.row();
//        chapter19Table.addActor(tableCollapsible19)/*.width(screenWidth).height(0)*/;
////        chapter1Table./*.width(screenWidth).height(0)*/;
////        chapter1Table.row();
////        tableCollapsible1.setVisible(false);
//        chapter19Table.wrap();
//
//        tableChapTitle19.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap19titleClicked = !chap19titleClicked;
//                                             System.out.println("chap19titleClicked" + chap19titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter19Table);
//
//        return chapter19Table;
//    }
//
//
//    public HorizontalGroup chapter20Results(int chapitre, int noteMaxPossible)
//    {
//        chapter20Table = new HorizontalGroup();
//        tableCollapsible20 = new Table();
//
//
//        String labelChapterTitle = "Pratique de la multiplication en ligne par un nombre à 1 chiffre. Applications.";
//        String label1 = "Leçon sur la multiplication en  ligne par un nombre à 1 chiffre";
//        String label2 = "Multiplications en ligne par 2 et par 3.";
//        String label3 = "Multiplications en ligne par 4 et par 5";
//        String label4 = "Multiplications en ligne par 6 et par 7";
//        String label5 = "Multiplications en ligne par 8 et par 9";
//        String label6 = "Longueur d’un trajet aller-retour.";
//        String label7 = " Acheter des croissants et des pains au chocolat avec des youpies.";
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
////        MyTextButton deux_bouton_red = new MyTextButton("2",  buttonSize, buttonStyleRed);
////        MyTextButton trois_bouton_red = new MyTextButton("3",  buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
////        MyTextButton un_bouton_blue = new MyTextButton("1",  buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle20 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureExercices, "blue", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//
//
//        tableCollapsible20.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible20.row();
//        tableCollapsible20.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        tableCollapsible20.setHeight(collapsibleTableHeight);
//
//        chapter20Table.addActor(tableChapTitle20)/*/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter1Table.row();
//        chapter20Table.addActor(tableCollapsible20)/*.width(screenWidth).height(0)*/;
////        chapter1Table./*.width(screenWidth).height(0)*/;
////        chapter1Table.row();
////        tableCollapsible1.setVisible(false);
//        chapter20Table.wrap();
//
//
//        tableChapTitle20.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap20titleClicked = !chap20titleClicked;
//                                             System.out.println("chap20titleClicked" + chap20titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter20Table);
//
//        return chapter20Table;
//    }

//    public HorizontalGroup chapter21Results(int chapitre, int noteMaxPossible)
//    {
//        chapter21Table = new HorizontalGroup();
//        tableCollapsible21 = new Table();
//
//        String labelChapterTitle = "Table de multiplication. Symétrie. Révisions";
//        String label1 = "La table de multiplication, on en a besoin dans toutes les multiplications.";
//        String label2 = "Compléter la table pour les multiplications par 1 à 5";
//        String label3 = "Compléter la table pour les multiplications par 6 et 7";
//        String label4 = "Compléter la table pour les multiplications par 8 et 9";
//        String label5 = "Compléter la table pour les multiplications par 10";
//        String label6 = "Calcul mental. Produit de 2 facteurs compris entre 1 et 16";
//        String label7 = "Calcul mental. Produit de 2 facteurs compris entre 18 et 36";
//        String label8 = "Calcul mental. Produit de 2 facteurs compris entre 40 et 81";
//        String label9 = "La table de multiplication complète pour consultation";
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle21 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_blue, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//        Table tableEx8 = LigneTableauxResults.getLigne(sept_bouton_blue, label8, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//        Table tableEx9 = LigneTableauxResults.getLigne(sept_bouton_blue, label9, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//
//
//        tableCollapsible21.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible21.row();
//        tableCollapsible21.add(tableEx9).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        chapter21Table.addActor(tableChapTitle21)/*.height(lineHeight).padBottom(-1 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter21Table.row();
//        chapter21Table.addActor(tableCollapsible21)/*.width(screenWidth).height(MyConstants.SCREENHEIGHT / 4f)*/;
////        chapter21Table.row();
//
//        tableCollapsible21.setVisible(false);
//
//
//        tableChapTitle21.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap2titleClicked = !chap2titleClicked;
//                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter21Table);
//        return chapter21Table;
//    }
//
//
//    public HorizontalGroup chapter22Results(int chapitre, int noteMaxPossible)
//    {
//        chapter22Table = new HorizontalGroup();
//        tableCollapsible22 = new Table();
//
//        String labelChapterTitle = "Les nombres décimaux, écriture et addition";
//        String label1 = "Introduction des nombres décimaux : les dixièmes";
//        String label2 = "Représenter des nombres décimaux inférieurs à 100";
//        String label3 = "Addition de 2 nombres décimaux avec ou sans retenue";
//        String label4 = "Additionner 2 nombres contenant une  décimale, sur Badix et sur l’ardoise.";
//        String label5 = "Soustraction de deux nombres décimaux sur Badix et sur l’ardoise";
//        String label6 = "Effectuer des soustractions sur Badix et sur l’ardoise";
//        String label7 = "Soustractions en complétant les dixièmes, les unités, les dizaines et les centaines";
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle22 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_red, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_red, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//
//        tableCollapsible22.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible22.row();
//        tableCollapsible22.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//        tableCollapsible22.setHeight(collapsibleTableHeight);
//
//        chapter22Table.addActor(tableChapTitle22)/*/*.height(lineHeight).padBottom(-22 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter22Table.row();
//        chapter22Table.addActor(tableCollapsible22)/*.width(screenWidth).height(0)*/;
////        chapter22Table./*.width(screenWidth).height(0)*/;
////        chapter22Table.row();
////        tableCollapsible22.setVisible(false);
//        chapter22Table.wrap();
//
//        tableChapTitle22.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap22titleClicked = !chap22titleClicked;
//                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter22Table);
//        return chapter22Table;
//    }
//
//    public HorizontalGroup chapter23Results(int chapitre, int noteMaxPossible)
//    {
//        chapter23Table = new HorizontalGroup();
//        tableCollapsible23 = new Table();
//
//
//        String labelChapterTitle = "Le mètre, ses multiples et sous-multiples. Pratique des nombres décimaux.";
//        String label1 = "Le mètre, le décimètre, le centimètre et le millimètre";
//        String label2 = "Convertir des longueurs dans des unités plus petites";
//        String label3 = "Convertir des longueurs dans des unités plus grandes";
//        String label4 = "Les multiples du mètre : le décamètre, l’hectomètre et le kilomètre";
//        String label5 = "Convertir des mètres, décamètres et hectomètres en kilomètres";
//        String label6 = "Convertir des décamètres, hectomètres et kilomètres en mètres";
//        String label7 = "Mesure des côtés d’un triangle. Calcul de son périmètre";
//        String label8 = "Distances à vol d’oiseau entre des villes françaises sur une carte et conversion en kilomètres";
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
//        MyTextButton huit_bouton_blue = new MyTextButton("8", buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle23 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//        Table tableEx8 = LigneTableauxResults.getLigne(huit_bouton_blue, label8, textureExercices, "blue", chapitre, 8, bitmapFontArial);
//
//
//        tableCollapsible23.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible23.row();
//        tableCollapsible23.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//
//        tableCollapsible23.setHeight(collapsibleTableHeight);
//
//        chapter23Table.addActor(tableChapTitle23)/*/*.height(lineHeight).padBottom(-23 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter23Table.row();
//        chapter23Table.addActor(tableCollapsible23)/*.width(screenWidth).height(0)*/;
////        chapter23Table./*.width(screenWidth).height(0)*/;
////        chapter23Table.row();
////        tableCollapsible23.setVisible(false);
//        chapter23Table.wrap();
//
//
//        tableChapTitle23.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap23titleClicked = !chap23titleClicked;
//                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter23Table);
//        return chapter23Table;
//    }
//
//    public HorizontalGroup chapter24Results(int chapitre, int noteMaxPossible)
//    {
//        chapter24Table = new HorizontalGroup();
//        tableCollapsible24 = new Table();
//
//        String labelChapterTitle = "Le kilogramme et sous-multiples. Convertir des unités de masse. Pesées";
//        String label1 = "Le kilogramme, l’hectogramme, le décagramme, le gramme";
//        String label2 = "Les sous-multiples du gramme : le décigramme, le centigramme, le milligramme.";
//        String label3 = "Convertir des masses dans des unités plus petites";
//        String label4 = "Convertir des masses dans des unités plus grandes";
//        String label5 = "Déterminer les masses d’objets inférieures à 500g";
//        String label6 = "Peser des objets dont la masse est supérieure à 500 grammes";
//        String label7 = "Léo et Julie jouent à la marchande de fruits et légumes";
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
////        MyTextButton quatre_bouton_red = new MyTextButton("4",  buttonSize, buttonStyleRed);
////       MyTextButton cinq_bouton_red = new MyTextButton("5",  buttonSize, buttonStyleRed);
////        MyTextButton six_bouton_red = new MyTextButton("6",  buttonSize, buttonStyleRed);
////        MyTextButton sept_bouton_red = new MyTextButton("7",  buttonSize, buttonStyleRed);
////        MyTextButton huit_bouton_red = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
//        MyTextButton trois_bouton_blue = new MyTextButton("3", buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
//        Table tableChapTitle24 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 7, bitmapFontArial);
//
//
//        tableCollapsible24.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible24.row();
//        tableCollapsible24.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//
//        tableCollapsible24.setHeight(collapsibleTableHeight);
//
//        chapter24Table.addActor(tableChapTitle24)/*/*.height(lineHeight).padBottom(-24 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter24Table.row();
//        chapter24Table.addActor(tableCollapsible24)/*.width(screenWidth).height(0)*/;
////        chapter24Table./*.width(screenWidth).height(0)*/;
////        chapter24Table.row();
////        tableCollapsible24.setVisible(false);
//        chapter24Table.wrap();
//
//
//        tableChapTitle24.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap24titleClicked = !chap24titleClicked;
//                                             System.out.println("chap24titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter24Table);
//
//        return chapter24Table;
//    }
//
//    public HorizontalGroup chapter25Results(int chapitre, int noteMaxPossible)
//    {
//        chapter25Table = new HorizontalGroup();
//        tableCollapsible25 = new Table();
//
//        String labelChapterTitle = "La division, une nouvelle opération. Division en ligne et division posée. Partage du reste.";
//        String label1 = "Introduction de la division. Division par un entier à 1 chiffre";
//        String label2 = "Divisions en ligne de nombres entiers inférieurs à 100 par 2";
//        String label3 = "Divisions en ligne de nombres entiers inférieurs à 100 par 3 et 4";
//        String label4 = "Divisions en ligne de nombres entiers inférieurs à 100 par 5, 6 et 7";
//        String label5 = "Divisions en ligne de nombres entiers inférieurs à 100 par 8 et 9";
//        String label6 = "La division posée. L’art de partager le reste";
//        String label7 = "Pratique de la division avec des entiers ou avec 1 décimale";
//        String label8 = "Prix d’un macaron dans des boites de 4, 6, 8 et 10.";
//
//
//        MyTextButton chapter_bouton = new MyTextButton("", "Images/IndicesChapitres/chap" + chapitre + ".png", lineHeight * 1.2f, "font/FRHND521_0.TTF", fontSizeOnglet);
//
//        MyTextButton un_bouton_red = new MyTextButton("1", buttonSize, buttonStyleRed);
//        MyTextButton deux_bouton_red = new MyTextButton("2", buttonSize, buttonStyleRed);
//        MyTextButton trois_bouton_red = new MyTextButton("3", buttonSize, buttonStyleRed);
//        MyTextButton quatre_bouton_red = new MyTextButton("4", buttonSize, buttonStyleRed);
//        MyTextButton cinq_bouton_red = new MyTextButton("5", buttonSize, buttonStyleRed);
//        MyTextButton six_bouton_red = new MyTextButton("6", buttonSize, buttonStyleRed);
//        MyTextButton sept_bouton_red = new MyTextButton("7", buttonSize, buttonStyleRed);
//        MyTextButton huit_bouton_red = new MyTextButton("8", buttonSize, buttonStyleRed);
//
//        MyTextButton un_bouton_blue = new MyTextButton("1", buttonSize, buttonStyleBlue);
//        MyTextButton deux_bouton_blue = new MyTextButton("2", buttonSize, buttonStyleBlue);
////        MyTextButton trois_bouton_blue = new MyTextButton("3",  buttonSize, buttonStyleBlue);
//        MyTextButton quatre_bouton_blue = new MyTextButton("4", buttonSize, buttonStyleBlue);
//        MyTextButton cinq_bouton_blue = new MyTextButton("5", buttonSize, buttonStyleBlue);
//        MyTextButton six_bouton_blue = new MyTextButton("6", buttonSize, buttonStyleBlue);
//        MyTextButton sept_bouton_blue = new MyTextButton("7", buttonSize, buttonStyleBlue);
////        MyTextButton huit_bouton_blue = new MyTextButton("8",  buttonSize, buttonStyleBlue);
//
//
//        Table tableChapTitle25 = LigneTableauxResultsChapitre.getLigne(chapter_bouton, labelChapterTitle, chapitre, noteMaxPossible, bitmapFontArial);
//        Table tableEx1 = LigneTableauxResults.getLigne(un_bouton_red, label1, textureCours, "red", chapitre, 1, bitmapFontArial);
//        Table tableEx2 = LigneTableauxResults.getLigne(deux_bouton_blue, label2, textureExercices, "blue", chapitre, 2, bitmapFontArial);
//        Table tableEx3 = LigneTableauxResults.getLigne(trois_bouton_red, label3, textureCours, "red", chapitre, 3, bitmapFontArial);
//        Table tableEx4 = LigneTableauxResults.getLigne(quatre_bouton_blue, label4, textureExercices, "blue", chapitre, 4, bitmapFontArial);
//        Table tableEx5 = LigneTableauxResults.getLigne(cinq_bouton_blue, label5, textureExercices, "blue", chapitre, 5, bitmapFontArial);
//        Table tableEx6 = LigneTableauxResults.getLigne(six_bouton_blue, label6, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx7 = LigneTableauxResults.getLigne(sept_bouton_blue, label7, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//        Table tableEx8 = LigneTableauxResults.getLigne(sept_bouton_blue, label8, textureExercices, "blue", chapitre, 6, bitmapFontArial);
//
//        tableCollapsible25.add(tableEx1).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx2).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx3).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx4).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx5).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx6).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx7).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//        tableCollapsible25.row();
//        tableCollapsible25.add(tableEx8).height(lineHeight).padBottom(paddingInterOnglets).width(screenWidth).height(lineHeight);
//
//
//        tableCollapsible25.setHeight(collapsibleTableHeight);
//
//        chapter25Table.addActor(tableChapTitle25)/*/*.height(lineHeight).padBottom(-25 * paddingInterOnglets).width(screenWidth).height(lineHeight)*/;
////        chapter25Table.row();
//        chapter25Table.addActor(tableCollapsible25)/*.width(screenWidth).height(0)*/;
////        chapter25Table./*.width(screenWidth).height(0)*/;
////        chapter25Table.row();
////        tableCollapsible25.setVisible(false);
//        chapter25Table.wrap();
//
//
//        tableChapTitle25.addListener(new ClickListener()
//                                     {
//                                         @Override
//                                         public void clicked(InputEvent event, float x, float y)
//                                         {
//                                             chap25titleClicked = !chap25titleClicked;
//                                             System.out.println("chap1titleClicked" + chap2titleClicked + " " + event);
//                                         }
//                                     }
//        );
//
//        tableArrayList.add(chapter25Table);
//        return chapter25Table;
//    }

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


//        if (chap1titleClicked)
//        {
//            chapter1Table.addActor(tableCollapsible1);
//            chapter1Table.grow();
//        }
//        else if (!chap1titleClicked)
//        {
//            chapter1Table.removeActor(tableCollapsible1);
//            chapter1Table.wrap();
//        }
//
//        if (chap2titleClicked)
//        {
//            chapter2Table.addActor(tableCollapsible2);
//            chapter2Table.grow();
//        }
//        else if (!chap2titleClicked)
//        {
//            chapter2Table.removeActor(tableCollapsible2);
//            chapter2Table.wrap();
//        }
//
//        if (chap3titleClicked)
//        {
//            chapter3Table.addActor(tableCollapsible3);
//            chapter3Table.grow();
//        }
//        else if (!chap3titleClicked)
//        {
//            chapter3Table.removeActor(tableCollapsible3);
//            chapter3Table.wrap();
//        }
//
//        if (chap4titleClicked)
//        {
//            chapter4Table.addActor(tableCollapsible4);
//            chapter4Table.grow();
//        }
//        else if (!chap4titleClicked)
//        {
//            chapter4Table.removeActor(tableCollapsible4);
//            chapter4Table.wrap();
//        }
//
//        if (chap5titleClicked)
//        {
//            chapter5Table.addActor(tableCollapsible5);
//            chapter5Table.grow();
//        }
//        else if (!chap5titleClicked)
//        {
//            chapter5Table.removeActor(tableCollapsible5);
//            chapter5Table.wrap();
//        }
//
//        if (chap6titleClicked)
//        {
//            chapter6Table.addActor(tableCollapsible6);
//            chapter6Table.grow();
//        }
//        else if (!chap6titleClicked)
//        {
//            chapter6Table.removeActor(tableCollapsible6);
//            chapter6Table.wrap();
//        }
//
//        if (chap7titleClicked)
//        {
//            chapter7Table.addActor(tableCollapsible7);
//            chapter7Table.grow();
//        }
//        else if (!chap7titleClicked)
//        {
//            chapter7Table.removeActor(tableCollapsible7);
//            chapter7Table.wrap();
//        }
//
//        if (chap8titleClicked)
//        {
//            chapter8Table.addActor(tableCollapsible8);
//            chapter8Table.grow();
//        }
//        else if (!chap8titleClicked)
//        {
//            chapter8Table.removeActor(tableCollapsible8);
//            chapter8Table.wrap();
//        }
//
//        if (chap9titleClicked)
//        {
//            chapter9Table.addActor(tableCollapsible9);
//            chapter9Table.grow();
//        }
//        else if (!chap9titleClicked)
//        {
//            chapter9Table.removeActor(tableCollapsible9);
//            chapter9Table.wrap();
//        }
//
//        if (chap10titleClicked)
//        {
//            chapter10Table.addActor(tableCollapsible10);
//            chapter10Table.grow();
//        }
//        else if (!chap10titleClicked)
//        {
//            chapter10Table.removeActor(tableCollapsible10);
//            chapter10Table.wrap();
//        }
//
//        if (chap11titleClicked)
//        {
//            chapter11Table.addActor(tableCollapsible11);
//            chapter11Table.grow();
//        }
//        else if (!chap11titleClicked)
//        {
//            chapter11Table.removeActor(tableCollapsible11);
//            chapter11Table.wrap();
//        }
//
//        if (chap12titleClicked)
//        {
//            chapter12Table.addActor(tableCollapsible12);
//            chapter12Table.grow();
//        }
//        else if (!chap12titleClicked)
//        {
//            chapter12Table.removeActor(tableCollapsible12);
//            chapter12Table.wrap();
//        }
//        if (chap13titleClicked)
//        {
//            chapter13Table.addActor(tableCollapsible13);
//            chapter13Table.grow();
//        }
//        else if (!chap13titleClicked)
//        {
//            chapter13Table.removeActor(tableCollapsible13);
//            chapter13Table.wrap();
//        }
//
//        if (chap14titleClicked)
//        {
//            chapter14Table.addActor(tableCollapsible14);
//            chapter14Table.grow();
//        }
//        else if (!chap14titleClicked)
//        {
//            chapter14Table.removeActor(tableCollapsible14);
//            chapter14Table.wrap();
//        }
//
//        if (chap15titleClicked)
//        {
//            chapter15Table.addActor(tableCollapsible15);
//            chapter15Table.grow();
//        }
//        else if (!chap15titleClicked)
//        {
//            chapter15Table.removeActor(tableCollapsible15);
//            chapter15Table.wrap();
//        }
//
//        if (chap16titleClicked)
//        {
//            chapter16Table.addActor(tableCollapsible16);
//            chapter16Table.grow();
//        }
//        else if (!chap16titleClicked)
//        {
//            chapter16Table.removeActor(tableCollapsible16);
//            chapter16Table.wrap();
//        }
//
//        if (chap17titleClicked)
//        {
//            chapter17Table.addActor(tableCollapsible17);
//            chapter17Table.grow();
//        }
//        else if (!chap17titleClicked)
//        {
//            chapter17Table.removeActor(tableCollapsible17);
//            chapter17Table.wrap();
//        }
//
//        if (chap18titleClicked)
//        {
//            chapter18Table.addActor(tableCollapsible18);
//            chapter18Table.grow();
//        }
//        else if (!chap18titleClicked)
//        {
//            chapter18Table.removeActor(tableCollapsible18);
//            chapter18Table.wrap();
//        }
//
//        if (chap19titleClicked)
//        {
//            chapter19Table.addActor(tableCollapsible19);
//            chapter19Table.grow();
//        }
//        else
//        {
//            chapter19Table.removeActor(tableCollapsible19);
//            chapter19Table.wrap();
//        }
//
//        if (chap20titleClicked)
//        {
//            chapter20Table.addActor(tableCollapsible20);
//            chapter20Table.grow();
//        }
//        else if (!chap20titleClicked)
//        {
//            chapter20Table.removeActor(tableCollapsible20);
//            chapter20Table.wrap();
//        }
//
//        if (chap21titleClicked)
//        {
//            chapter21Table.addActor(tableCollapsible21);
//            chapter21Table.grow();
//        }
//        else if (!chap21titleClicked)
//        {
//            chapter21Table.removeActor(tableCollapsible21);
//            chapter21Table.wrap();
//        }
//
//        if (chap22titleClicked)
//        {
//            chapter22Table.addActor(tableCollapsible22);
//            chapter22Table.grow();
//        }
//        else if (!chap22titleClicked)
//        {
//            chapter22Table.removeActor(tableCollapsible22);
//            chapter22Table.wrap();
//        }
//
//        if (chap23titleClicked)
//        {
//            chapter23Table.addActor(tableCollapsible23);
//            chapter23Table.grow();
//        }
//        else if (!chap23titleClicked)
//        {
//            chapter23Table.removeActor(tableCollapsible23);
//            chapter23Table.wrap();
//        }
//
//        if (chap24titleClicked)
//        {
//            chapter24Table.addActor(tableCollapsible24);
//            chapter24Table.grow();
//        }
//        else if (!chap24titleClicked)
//        {
//            chapter24Table.removeActor(tableCollapsible24);
//            chapter24Table.wrap();
//        }
//
//        if (chap25titleClicked)
//        {
//            chapter25Table.addActor(tableCollapsible25);
//            chapter25Table.grow();
//        }
//        else if (!chap25titleClicked)
//        {
//            chapter25Table.removeActor(tableCollapsible25);
//            chapter25Table.wrap();
//        }

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
