package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class CalculetteViewTest implements MyDrawInterface
{
    float width, height;

    public float posX;
    public float posY;
    float posXinCalc = posX + 160.0f;
    float posYinCalc = posY + 220.0f;

    private TextureRegion textureRegionFond, additionBouton, divisionBouton, effacerBouton, egalBouton, multiplicationBouton, parenthese1Bouton, parenthese2Bouton, soustractionBouton, validerBouton, virguleBouton;

    TextureRegion chiffreAux;

    MyTextButton additionBouton2, soustractionBouton2, multiplicationBouton2, egalBouton2, effacerBouton2, validerBouton2, zero_bouton, un_bouton, deux_bouton, trois_bouton, quatre_bouton, cinq_bouton, six_bouton, sept_bouton, huit_bouton, neuf_bouton;

    InputEvent inputTest;
    TextField textFieldTest;
    Skin skin;
    Stage stage;
    TextField.TextFieldStyle textFieldStyleTest;

    MyImageButton myButtonTest;

    // ArrayList<TextureRegion> arrayListButtons = new ArrayList<>();
    ArrayList<MyTextButton> arrayListButtons2 = new ArrayList<>();
    protected boolean isVisible = true;
    protected boolean isActive = true;

    public CalculetteViewTest(Stage stage, float positionX, float positionY, float width, float height)
    {
        this.width = width;
        this.height = height;

        posX = positionX;
        posY = positionY;
        skin = new Skin();

        this.stage = stage;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;
        parameter.padLeft = 20;
        parameter.padRight = 50;
        parameter.padBottom = 70;

        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        Color fontColor = new Color(Color.BLACK);

        textFieldStyleTest = new TextField.TextFieldStyle();
        textFieldStyleTest.fontColor = fontColor;
        textFieldStyleTest.font = bitmapFont;


        float heightTF = height / 4.0f;

        textFieldTest = new TextField("", textFieldStyleTest);
        //textFieldTest.setColor(Color.GREEN);
        textFieldTest.setBounds(positionX, positionY + height - heightTF, width, heightTF);
        //textFieldTest.setPosition(positionX, positionY+height);
        //textFieldTest.setWidth(width);
        //textFieldTest.setHeight(height/7.0f);
//        TextField.TextFieldStyle tt = textFieldTest.getStyle();
        textFieldTest.setAlignment(Align.right);

        stage.addActor(textFieldTest);

        textFieldTest.addListener(new ClickListener()
        {

        });

        //Gdx.input.setInputProcessor(stage);
        Texture textureFond = new Texture("Images/calculetteKeys/clavier_fond_bis.png");
        textureFond.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionFond = new TextureRegion(textureFond);

        Texture textureAddition = new Texture("Images/calculetteKeys/clavier_addition.png");
        textureAddition.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        additionBouton = new TextureRegion(textureAddition);

        divisionBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_division_inactif.png"));
        effacerBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_effacer.png"));
        egalBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_egal.png"));
        multiplicationBouton = new TextureRegion((new Texture("Images/calculetteKeys/clavier_multiplication.png")));
        parenthese1Bouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_prenthese_inactive1.png"));
        parenthese2Bouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_prenthese_inactive2.png"));
        soustractionBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_soustraction.png"));
        validerBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_valider.png"));
        virguleBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_virgule.png"));
        chiffreAux = new TextureRegion(new Texture("Images/calculetteKeys/clavier_virgule.png"));

        myButtonTest = new MyImageButton(stage, "Images/calculetteKeys/clavier_fond_bis.png", "Images/calculetteKeys/clavier_fond_bis.png", 300, 200);

        myButtonTest.setPosition(200, 100);

        stage.addActor(myButtonTest);


        myButtonTest.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                super.clicked(event, x, y);

            }
        });

        //setTouchable(Touchable.enabled);

        //setWidth(width);
        //setHeight(height);

        inputTest = new InputEvent();

        additionBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_addition.png", "Images/calculetteKeys/highlight/clavier_addition_highlight.png", 50, "font/comic_sans_ms.ttf", 50);
        soustractionBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_soustraction.png", "Images/calculetteKeys/highlight/clavier_soustraction_highlight.png", 50, "font/comic_sans_ms.ttf", 50);
        multiplicationBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_multiplication.png", "Images/calculetteKeys/highlight/clavier_multiplication_highlight.png", 50, "font/comic_sans_ms.ttf", 50);
        egalBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_egal.png", "Images/calculetteKeys/highlight/clavier_egal_highlight.png", 50, "font/comic_sans_ms.ttf", 50);
        effacerBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_effacer.png", "Images/calculetteKeys/highlight/clavier_effacer_highlight.png", 50, "font/comic_sans_ms.ttf", 50);
        validerBouton2 = new MyTextButton("", "Images/calculetteKeys/clavier_valider.png", "Images/calculetteKeys/highlight/clavier_valider_highlight.png", 50, "font/comic_sans_ms.ttf", 50);

        zero_bouton = new MyTextButton("0", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        un_bouton = new MyTextButton("1", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        deux_bouton = new MyTextButton("2", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        trois_bouton = new MyTextButton("3", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        quatre_bouton = new MyTextButton("4", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        cinq_bouton = new MyTextButton("5", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        six_bouton = new MyTextButton("6", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        sept_bouton = new MyTextButton("7", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        huit_bouton = new MyTextButton("8", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);
        neuf_bouton = new MyTextButton("9", "Images/calculetteKeys/vide_inactif.png", "Images/calculetteKeys/highlight/vide_actif.png", 50, "font/comic_sans_ms.ttf", 50);


        arrayListButtons2.add(zero_bouton);
        zero_bouton.addListener(new ButtonOperationsListener("0"));
        arrayListButtons2.add(un_bouton);
        un_bouton.addListener(new ButtonOperationsListener("1"));
        arrayListButtons2.add(quatre_bouton);
        quatre_bouton.addListener(new ButtonOperationsListener("4"));
        arrayListButtons2.add(sept_bouton);
        sept_bouton.addListener(new ButtonOperationsListener("7"));

        arrayListButtons2.add(effacerBouton2);
        effacerBouton2.addListener(new ButtonOperationsListener(("effacer")));
        arrayListButtons2.add(deux_bouton);
        deux_bouton.addListener((new ButtonOperationsListener("2")));
        arrayListButtons2.add(cinq_bouton);
        cinq_bouton.addListener(new ButtonOperationsListener("5"));
        arrayListButtons2.add(huit_bouton);
        huit_bouton.addListener(new ButtonOperationsListener("8"));

        arrayListButtons2.add(validerBouton2);
        //TODO action a concevoir --> Runnable
        arrayListButtons2.add(trois_bouton);
        trois_bouton.addListener(new ButtonOperationsListener("3"));
        arrayListButtons2.add(six_bouton);
        six_bouton.addListener(new ButtonOperationsListener("6"));
        arrayListButtons2.add(neuf_bouton);
        neuf_bouton.addListener(new ButtonOperationsListener("9"));

        arrayListButtons2.add(egalBouton2);
        egalBouton2.addListener(new ButtonOperationsListener("="));
        arrayListButtons2.add(multiplicationBouton2);
        multiplicationBouton2.addListener(new ButtonOperationsListener("x"));
        arrayListButtons2.add(soustractionBouton2);
        soustractionBouton2.addListener(new ButtonOperationsListener("-"));
        arrayListButtons2.add(additionBouton2);
        additionBouton2.addListener(new ButtonOperationsListener("+"));

        for (MyTextButton textButton : arrayListButtons2)
        {
            stage.addActor(textButton);
        }

    }


    private class ButtonOperationsListener extends ClickListener
    {
        private String string;

        private ButtonOperationsListener(String str)
        {
            string = str;
        }

        @Override
        public void clicked(InputEvent event, float x, float y)
        {
            super.clicked(event, x, y);

            String exText = textFieldTest.getText();

            if (string.equals("effacer"))
            {
                if (exText.length() > 0)
                {
                    String textNew = exText.substring(0, exText.length() - 1);
                    textFieldTest.setText(textNew);
                }
            }
            else
            {
                String textNew = exText + string;
                textFieldTest.setText(textNew);
            }
        }
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
        float paddingLeft = width / 20;
        float paddingBottom = height / 20;
        batch.draw(textureRegionFond, posX, posY, width, height);

        int cpt = 0;
        float widthButton = width / 4.5f;
        float heightButton = height / 5.5f;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {

                MyTextButton buttonAux = arrayListButtons2.get(cpt);
                buttonAux.setSize(widthButton, heightButton);
                buttonAux.setPosition(paddingLeft + posX + widthButton * i, paddingBottom + posY + heightButton * j);
                buttonAux.draw(batch, 1.0f);
                buttonAux.getStyle().fontColor = Color.BLACK;

                cpt++;
            }
        }
        textFieldTest.draw(batch, 1.0f);
    }
}
