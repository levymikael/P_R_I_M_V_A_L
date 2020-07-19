package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.LigneTableaux;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyDrawInterface;

import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class ListExercicesActiviteViewChap1 implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;

    Game game;

    DatabaseDesktop dataBase;

    public ListExercicesActiviteViewChap1(Stage stage, final Game game, final DatabaseDesktop dataBase)
    {
        screenWidth = MyConstants.SCREENWIDTH;
        final int screenHeight = MyConstants.SCREENHEIGHT;

        this.game = game;
        this.dataBase = dataBase;

        final String label1 = "Les nombres de 1 à 9. Badix, Métrologue et Validus";
        final String label2 = "Faire correspondre des billes à des oiseaux";
        final String label3 = "Écriture des chiffres de 1 à 9";
        final String label4 = "Prononciation des chiffres de 1 à 9";
        final String label5 = "Compter des oiseaux et taper leur nombre";
        final String label6 = "Un gâteau pour plusieurs anniversaires";

        Texture textureCours = new Texture(Gdx.files.internal("Images/Pages onglets/Cours - onglets.png"));
        textureCours.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture textureExercices = new Texture(Gdx.files.internal("Images/Pages onglets/Exercice-onglets.png"));
        textureExercices.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table container = new Table();
        stage.addActor(container);
        container.setSize(screenWidth, 7 * screenHeight / 10);
        container.setPosition(0, 5 * screenHeight / 14 - container.getHeight() / 2);

        int lineHeight = screenHeight / 11;
        int buttonSize = lineHeight * 5 / 10;
        int fontSize = screenHeight / 40;
        int paddingInterOnglets = -MyConstants.SCREENHEIGHT / 50;

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", buttonSize, "font/FRHND521_0.TTF", fontSize);

        Table table = new Table();
        Table tableEx1 = LigneTableaux.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableaux.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableaux.getLigne(trois_bouton, label3, textureCours, "red", 1, 3, dataBase);
        Table tableEx4 = LigneTableaux.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 4, dataBase);
        Table tableEx5 = LigneTableaux.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 5, dataBase);
        Table tableEx6 = LigneTableaux.getLigne(six_bouton, label6, textureExercices, "blue", 1, 6, dataBase);

        table.add(tableEx1).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);
        table.row();
        table.add(tableEx2).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);
        table.row();
        table.add(tableEx3).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);
        table.row();
        table.add(tableEx4).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);
        table.row();
        table.add(tableEx5).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);
        table.row();
        table.add(tableEx6).width(screenWidth).height(lineHeight).padBottom(paddingInterOnglets);

        table.align(Align.top);
        table.setHeight(MyConstants.SCREENHEIGHT / 2);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(3 * screenHeight / 5).align(Align.top);

        tableEx1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new ScreenEx1_1(game, dataBase, label1));
                System.out.println("I got clicked!1.1");
            }
        });
        tableEx2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!1.2");
                game.setScreen(new ScreenEx1_2(game, dataBase, label2));
            }
        });
        tableEx3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!1.3");
                game.setScreen(new ScreenEx1_3(game, dataBase, label3));

            }
        });
        tableEx4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new ScreenEx1_4(game, dataBase, label4));
                System.out.println("I got clicked!1.4");
            }
        });
        tableEx5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new ScreenEx1_5(game, dataBase, label5));
                System.out.println("I got clicked!5");
            }
        });
        tableEx6.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!6");
                game.setScreen(new ScreenEx1_6(game, dataBase, label6));
            }
        });
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
