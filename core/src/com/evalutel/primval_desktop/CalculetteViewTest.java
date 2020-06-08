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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.ui_tools.MyImageButton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class CalculetteViewTest implements MyDrawInterface, MyTouchInterface, MyCorrectionAndPauseInterface
{
    float width, height;

    //    public float posX;
//    public float posY;
    float positionX;
    float positionY;

    private TextureRegion textureRegionFond;

    public MyTextButton additionBouton, soustractionBouton, multiplicationBouton, egalBouton, effacerBouton, validerBouton, zero_bouton, un_bouton, deux_bouton, trois_bouton, quatre_bouton, cinq_bouton, six_bouton, sept_bouton, huit_bouton, neuf_bouton;

    TextField textFieldTest;
    Skin skin;
    Stage stage;
    TextField.TextFieldStyle textFieldStyleTest;


String calculetteInput;
    ArrayList<MyTextButton> arrayListButtons = new ArrayList<>();
    protected boolean isVisible = true;
    protected boolean isActive = true;

    float buttonSize, paddingAux1, paddingAux2;

    public CalculetteViewTest(Stage stage)
    {
        buttonSize = MyConstants.SCREENWIDTH / 24;
        paddingAux1 = MyConstants.SCREENWIDTH / 100;
        paddingAux2 = MyConstants.SCREENWIDTH / 200;

        this.width = (4 * buttonSize) + (2 * paddingAux1) + (3 * paddingAux2);
        this.height = (5 * buttonSize) + (2.5f * paddingAux1) + (3 * paddingAux2);

        this.positionX = MyConstants.SCREENWIDTH - width - (MyConstants.SCREENWIDTH / 200);
        this.positionY = MyConstants.SCREENWIDTH / 200;

        skin = new Skin();

        this.stage = stage;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        parameter.padLeft = 20;
        parameter.padRight = 50;
        parameter.padBottom = 70;

        BitmapFont bitmapFont = generator.generateFont(parameter);

        generator.dispose();

        Color fontColor = new Color(Color.BLACK);

        textFieldStyleTest = new TextField.TextFieldStyle();
        textFieldStyleTest.fontColor = fontColor;
        textFieldStyleTest.font = bitmapFont;

        float heightTF = height / 5.0f;

        textFieldTest = new TextField("", textFieldStyleTest);
        textFieldTest.setBounds(positionX, positionY + height - heightTF, width * 1.05f, heightTF);
        textFieldTest.setAlignment(Align.right);

        stage.addActor(textFieldTest);

        Texture textureFond = new Texture("Images/calculetteKeys/clavier_fond_bis.png");
        textureFond.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionFond = new TextureRegion(textureFond);

//        Texture textureAddition = new Texture("Images/calculetteKeys/clavier_addition.png");
//        textureAddition.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        additionBouton = new TextureRegion(textureAddition);
//
//        divisionBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_division_inactif.png"));
//        effacerBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_effacer.png"));
//        egalBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_egal.png"));
//        multiplicationBouton = new TextureRegion((new Texture("Images/calculetteKeys/clavier_multiplication.png")));
//        parenthese1Bouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_prenthese_inactive1.png"));
//        parenthese2Bouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_prenthese_inactive2.png"));
//        soustractionBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_soustraction.png"));
//        validerBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_valider.png"));
//        virguleBouton = new TextureRegion(new Texture("Images/calculetteKeys/clavier_virgule.png"));
//        chiffreAux = new TextureRegion(new Texture("Images/calculetteKeys/clavier_virgule.png"));

//        myButtonTest = new MyImageButton(stage, "Images/calculetteKeys/clavier_fond_bis.png", "Images/calculetteKeys/clavier_fond_bis.png", 300, 200);
//
//        myButtonTest.setPosition(200, 100);
//
//        stage.addActor(myButtonTest);
//
//
//        myButtonTest.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                super.clicked(event, x, y);
//
//            }
//        });

        int fontSizeButton = MyConstants.SCREENWIDTH / 60;

        String emptyButtonInactivePath = "Images/calculetteKeys/vide_inactif.png";
        String emptyButtonActivePath = "Images/calculetteKeys/highlight/vide_actif.png";

        additionBouton = new MyTextButton("", "Images/calculetteKeys/clavier_addition.png", "Images/calculetteKeys/highlight/clavier_addition_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        soustractionBouton = new MyTextButton("", "Images/calculetteKeys/clavier_soustraction.png", "Images/calculetteKeys/highlight/clavier_soustraction_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        multiplicationBouton = new MyTextButton("", "Images/calculetteKeys/clavier_multiplication.png", "Images/calculetteKeys/highlight/clavier_multiplication_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        egalBouton = new MyTextButton("", "Images/calculetteKeys/clavier_egal.png", "Images/calculetteKeys/highlight/clavier_egal_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        effacerBouton = new MyTextButton("", "Images/calculetteKeys/clavier_effacer.png", "Images/calculetteKeys/highlight/clavier_effacer_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        validerBouton = new MyTextButton("", "Images/calculetteKeys/clavier_valider.png", "Images/calculetteKeys/highlight/clavier_valider_highlight.png", buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);

        zero_bouton = new MyTextButton("0", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        un_bouton = new MyTextButton("1", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        deux_bouton = new MyTextButton("2", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        trois_bouton = new MyTextButton("3", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        quatre_bouton = new MyTextButton("4", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        cinq_bouton = new MyTextButton("5", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        six_bouton = new MyTextButton("6", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        sept_bouton = new MyTextButton("7", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        huit_bouton = new MyTextButton("8", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);
        neuf_bouton = new MyTextButton("9", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, "font/comic_sans_ms.ttf", fontSizeButton);

        arrayListButtons.add(zero_bouton);
        zero_bouton.addListener(new ButtonOperationsListener("0"));
        arrayListButtons.add(un_bouton);
        un_bouton.addListener(new ButtonOperationsListener("1"));
        arrayListButtons.add(quatre_bouton);
        quatre_bouton.addListener(new ButtonOperationsListener("4"));
        arrayListButtons.add(sept_bouton);
        sept_bouton.addListener(new ButtonOperationsListener("7"));

        arrayListButtons.add(effacerBouton);
        effacerBouton.addListener(new ButtonOperationsListener(("effacer")));
        arrayListButtons.add(deux_bouton);
        deux_bouton.addListener((new ButtonOperationsListener("2")));
        arrayListButtons.add(cinq_bouton);
        cinq_bouton.addListener(new ButtonOperationsListener("5"));
        arrayListButtons.add(huit_bouton);
        huit_bouton.addListener(new ButtonOperationsListener("8"));

        arrayListButtons.add(validerBouton);
        //TODO action a concevoir --> Runnable
        arrayListButtons.add(trois_bouton);
        trois_bouton.addListener(new ButtonOperationsListener("3"));
        arrayListButtons.add(six_bouton);
        six_bouton.addListener(new ButtonOperationsListener("6"));
        arrayListButtons.add(neuf_bouton);
        neuf_bouton.addListener(new ButtonOperationsListener("9"));

        arrayListButtons.add(egalBouton);
        egalBouton.addListener(new ButtonOperationsListener("="));
        arrayListButtons.add(multiplicationBouton);
        multiplicationBouton.addListener(new ButtonOperationsListener("x"));
        arrayListButtons.add(soustractionBouton);
        soustractionBouton.addListener(new ButtonOperationsListener("-"));
        arrayListButtons.add(additionBouton);
        additionBouton.addListener(new ButtonOperationsListener("+"));

        for (MyTextButton textButton : arrayListButtons)
        {
            stage.addActor(textButton);
        }

//        TextButton.TextButtonStyle styleTest = six_bouton.getStyle();
//        ClickListener clickListenerTest = six_bouton.getClickListener();
//        //styleTest.set
//        clickListenerTest.setVisualPressed(true);
    }

    public String getInput()
    {
        return calculetteInput;
    }

    public void screenDeletion ()
    {
       textFieldTest.clear();

    }

    public MyPoint buttonPosition(int buttonNumber)
    {
        MyPoint buttonPosition = new MyPoint(0, 0);
        switch (buttonNumber)
        {
            case 0:
                buttonPosition.x = (int) (zero_bouton.getX() + zero_bouton.getWidth() / 3);
                buttonPosition.y = (int) (zero_bouton.getY() + zero_bouton.getHeight() / 3);
                break;
            case 1:
                buttonPosition.x = (int) (un_bouton.getX() + un_bouton.getWidth() / 3);
                buttonPosition.y = (int) (un_bouton.getY() + un_bouton.getHeight() / 3);
                break;
            case 2:
                buttonPosition.x = (int) (deux_bouton.getX() + deux_bouton.getWidth() / 3);
                buttonPosition.y = (int) (deux_bouton.getY() + deux_bouton.getHeight() / 3);
                break;
            case 3:
                buttonPosition.x = (int) (trois_bouton.getX() + trois_bouton.getWidth() / 3);
                buttonPosition.y = (int) (trois_bouton.getY() + trois_bouton.getHeight() / 3);
                break;
            case 4:
                buttonPosition.x = (int) (quatre_bouton.getX() + quatre_bouton.getWidth() / 3);
                buttonPosition.y = (int) (quatre_bouton.getY() + quatre_bouton.getHeight() / 3);
                break;
            case 5:
                buttonPosition.x = (int) (cinq_bouton.getX() + cinq_bouton.getWidth() / 3);
                buttonPosition.y = (int) (cinq_bouton.getY() + cinq_bouton.getHeight() / 3);
                break;
            case 6:
                buttonPosition.x = (int) (six_bouton.getX() + six_bouton.getWidth() / 3);
                buttonPosition.y = (int) (six_bouton.getY() + six_bouton.getHeight() / 3);
                break;
            case 7:
                buttonPosition.x = (int) (sept_bouton.getX() + sept_bouton.getWidth() / 3);
                buttonPosition.y = (int) (sept_bouton.getY() + sept_bouton.getHeight() / 3);
                break;
            case 8:
                buttonPosition.x = (int) (huit_bouton.getX() + huit_bouton.getWidth() / 3);
                buttonPosition.y = (int) (huit_bouton.getY() + huit_bouton.getHeight() / 3);
                break;
            case 9:
                buttonPosition.x = (int) (neuf_bouton.getX() + neuf_bouton.getWidth() / 3);
                buttonPosition.y = (int) (neuf_bouton.getY() + neuf_bouton.getHeight() / 3);
                break;
        }

        return buttonPosition;
    }

    public void textDisplay(int buttonNumber)
    {
        textFieldTest.setText(String.valueOf(buttonNumber));


    }

    public MyPoint calculetteValidateAndDisplay()
    {
        MyPoint buttonValidatePosition = new MyPoint((int) (validerBouton.getX() + validerBouton.getWidth() / 3), (int) (validerBouton.getY() + validerBouton.getHeight() / 3));

        return buttonValidatePosition;
    }

    public void textRemove()
    {
        textFieldTest.setText("");

    }

    @Override
    public void myCorrectionStart()
    {

    }

    @Override
    public void myCorrectionStop()
    {

    }

    @Override
    public void myPause()
    {

    }

    @Override
    public void myResume()
    {

    }

    @Override
    public boolean isPause()
    {
        return false;
    }

    @Override
    public boolean isTouched(int x, int y)
    {
        return false;
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public void setPosition(int x, int y)
    {

    }

    @Override
    public MyPoint getPosition()
    {
        return null;
    }

    @Override
    public float getWidth()
    {
        return 0;
    }

    @Override
    public float getHeight()
    {
        return 0;
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }

    @Override
    public void setActive(boolean active)
    {
        isActive = active;
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

            if (!isActive)
            {
                return;
            }

            String exText = textFieldTest.getText();

            if (string.equals("effacer"))
            {
                if (exText.length() > 0)
                {
                    String textNew = exText.substring(0, exText.length() - 1);
                    textFieldTest.setText(textNew);

                    calculetteInput = textNew;
                }
            }
            else
            {
                String textNew = exText + string;
                textFieldTest.setText(textNew);


               calculetteInput = textNew;

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
        batch.draw(textureRegionFond, positionX, positionY, width, height);

        int cpt = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                MyTextButton buttonAux = arrayListButtons.get(cpt);
                buttonAux.setSize(buttonSize, buttonSize);
                buttonAux.setPosition(paddingAux1 + positionX + (paddingAux2 + buttonSize) * i, paddingAux1 + positionY + (paddingAux2 + buttonSize) * j);
                buttonAux.draw(batch, 1.0f);
                buttonAux.getStyle().fontColor = Color.BLACK;

                cpt++;
            }
        }
        textFieldTest.draw(batch, 1.0f);
    }
}
