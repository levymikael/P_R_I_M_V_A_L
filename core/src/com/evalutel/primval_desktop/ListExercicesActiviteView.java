package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.Database.DataBase;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.General.LigneTableaux;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_1;
import com.evalutel.primval_desktop.onglets.chapitre1.ScreenEx1_2;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

public class ListExercicesActiviteView implements MyDrawInterface
{
    public float screenWidth;
    private boolean isVisible = true;

    Game game;

    DatabaseDesktop dataBase;

    public ListExercicesActiviteView(Stage stage, final Game game, Label.LabelStyle labelStyle, final DatabaseDesktop dataBase)
    {
        screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();

        this.game = game;
        this.dataBase = dataBase;

// Configuration police
        Label label1 = new Label("Les nombres de 1 à 9. Badix, Métrologue et Validus", labelStyle);
        Label label2 = new Label("Faire correspondre des billes à des oiseaux", labelStyle);
        Label label3 = new Label("Écriture des chiffres 1 à 9", labelStyle);
        Label label4 = new Label("Prononciation des chiffres 1 à 9", labelStyle);
        Label label5 = new Label("Compter des oiseaux et taper leur nombre", labelStyle);
        Label label6 = new Label("Un gâteau pour plusieurs anniversaires", labelStyle);

        Texture textureCours = new Texture(Gdx.files.internal("Images/icon_cours.png"));
        Texture textureExercices = new Texture(Gdx.files.internal("Images/icon_exercice.png"));

        Table container = new Table();
        stage.addActor(container);
        container.setSize(screenWidth, 3 * screenHeight / 5);
        container.setPosition(0, 3 * screenHeight / 7 - container.getHeight() / 2);

        MyTextButton un_bouton = new MyTextButton("1", "Images/red_circle.png", "Images/red_circle.png", 70, 70, "font/FRHND521_0.TTF");
        MyTextButton deux_bouton = new MyTextButton("2", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton trois_bouton = new MyTextButton("3", "Images/red_circle.png", "Images/red_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton quatre_bouton = new MyTextButton("4", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton cinq_bouton = new MyTextButton("5", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");
        MyTextButton six_bouton = new MyTextButton("6", "Images/blue_circle.png", "Images/blue_circle.png", 50, 50, "font/FRHND521_0.TTF");

        Table table = new Table();
        Table tableEx1 = LigneTableaux.getLigne(un_bouton, label1, textureCours, "red", 1, 1, dataBase);
        Table tableEx2 = LigneTableaux.getLigne(deux_bouton, label2, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx3 = LigneTableaux.getLigne(trois_bouton, label3, textureCours, "red", 1, 2, dataBase);
        Table tableEx4 = LigneTableaux.getLigne(quatre_bouton, label4, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx5 = LigneTableaux.getLigne(cinq_bouton, label5, textureExercices, "blue", 1, 2, dataBase);
        Table tableEx6 = LigneTableaux.getLigne(six_bouton, label6, textureExercices, "blue", 1, 2, dataBase);

        table.add(tableEx1).width(screenWidth).height(screenHeight / 11);
        table.row();
        table.add(tableEx2).width(screenWidth).height(screenHeight / 11);
        table.row();
        table.add(tableEx3).width(screenWidth).height(screenHeight / 11);
        table.row();
        table.add(tableEx4).width(screenWidth).height(screenHeight / 11);
        table.row();
        table.add(tableEx5).width(screenWidth).height(screenHeight / 11);
        table.row();
        table.add(tableEx6).width(screenWidth).height(screenHeight / 11);
        table.row();

        table.setWidth(screenWidth);

        ScrollPane scroll = new ScrollPane(table);
        scroll.layout();

        container.add(scroll).height(2 * screenHeight / 5);
        container.row();

        tableEx1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new ScreenEx1_1(game, dataBase));
                System.out.println("I got clicked!1");
            }
        });
        tableEx2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!2");
                game.setScreen(new ScreenEx1_2(game, dataBase));
            }
        });
        tableEx3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!3");
            }
        });
        tableEx4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!4");
            }
        });
        tableEx5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!5");
            }
        });
        tableEx6.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("I got clicked!6");
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