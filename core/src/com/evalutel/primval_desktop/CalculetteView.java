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
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.Interfaces.MyCorrectionAndPauseInterface;
import com.evalutel.primval_desktop.Interfaces.MyDrawInterface;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.ui_tools.MyPoint;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class CalculetteView implements MyDrawInterface, MyTouchInterface, MyCorrectionAndPauseInterface
{
    private float width, height;

    public MyTimer.TaskEtape etapeCorrection;
    //    public float posX;
//    public float posY;
    public float positionX, positionY;

    private TextureRegion textureRegionFond;

    public MyTextButton additionBouton, soustractionBouton, multiplicationBouton, egalBouton, effacerBouton, validerBouton, zero_bouton, un_bouton, deux_bouton, trois_bouton, quatre_bouton, cinq_bouton, six_bouton, sept_bouton, huit_bouton, neuf_bouton;

    private TextField textFieldTest;
    private Skin skin;
    private Stage stage;
    private TextField.TextFieldStyle textFieldStyleTest;


    private String calculetteInput;
    private ArrayList<MyTextButton> arrayListButtons = new ArrayList<>();
    protected boolean isVisible = true;
    protected boolean isActive = true;
    private ValidusAnimated validusAnimated;

    private float buttonSize, paddingAux1, paddingAux2;

    public CalculetteView(Stage stage, final ValidusAnimated validusAnimated)
    {
        this.validusAnimated = validusAnimated;
        buttonSize = MyConstants.SCREENWIDTH / 25f;
        paddingAux1 = MyConstants.SCREENWIDTH / 80f;
        paddingAux2 = MyConstants.SCREENWIDTH / 130f;
        int fontSizeButton = (int) (buttonSize / 1.7f);


        this.width = (4 * buttonSize) + (1.9f * paddingAux1) + (3 * paddingAux2) + MyConstants.SCREENWIDTH / 80f;
        this.height = (5 * buttonSize) + (2.5f * paddingAux1) + (2.5f * paddingAux2);

        this.positionX = MyConstants.SCREENWIDTH - width - (MyConstants.SCREENWIDTH / 200f);
        this.positionY = MyConstants.SCREENWIDTH / 200f;


        this.stage = stage;

        String emptyButtonInactivePath = "Images/calculetteKeys/vide_inactif.png";
        String emptyButtonActivePath = "Images/calculetteKeys/highlight/vide_actif.png";
        String comicSansMsPath = "font/comic_sans_ms.ttf";


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(comicSansMsPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENWIDTH / 60;
        parameter.padLeft = 20;
        parameter.padRight = MyConstants.SCREENWIDTH / 80;
        parameter.padBottom = 70;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();


        textFieldStyleTest = new TextField.TextFieldStyle();
        textFieldStyleTest.fontColor = new Color(Color.BLACK);
        textFieldStyleTest.font = bitmapFont;

        float heightTF = height / 5f;

        textFieldTest = new TextField("", textFieldStyleTest);
        textFieldTest.setBounds(positionX, positionY + height - heightTF, width * 1f, heightTF);
        textFieldTest.setAlignment(Align.right);

        stage.addActor(textFieldTest);

        Texture textureFond = new Texture("Images/calculetteKeys/clavier_fond_bis.png");
        textureFond.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegionFond = new TextureRegion(textureFond);


        additionBouton = new MyTextButton("", "Images/calculetteKeys/clavier_addition.png", "Images/calculetteKeys/highlight/clavier_addition_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);
        soustractionBouton = new MyTextButton("", "Images/calculetteKeys/clavier_soustraction.png", "Images/calculetteKeys/highlight/clavier_soustraction_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);
        multiplicationBouton = new MyTextButton("", "Images/calculetteKeys/clavier_multiplication.png", "Images/calculetteKeys/highlight/clavier_multiplication_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);
        egalBouton = new MyTextButton("", "Images/calculetteKeys/clavier_egal.png", "Images/calculetteKeys/highlight/clavier_egal_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);
        effacerBouton = new MyTextButton("", "Images/calculetteKeys/clavier_effacer.png", "Images/calculetteKeys/highlight/clavier_effacer_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);
        validerBouton = new MyTextButton("", "Images/calculetteKeys/clavier_valider.png", "Images/calculetteKeys/highlight/clavier_valider_highlight.png", buttonSize, comicSansMsPath, fontSizeButton);

        zero_bouton = new MyTextButton("0", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        un_bouton = new MyTextButton("1", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        deux_bouton = new MyTextButton("2", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        trois_bouton = new MyTextButton("3", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        quatre_bouton = new MyTextButton("4", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        cinq_bouton = new MyTextButton("5", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        six_bouton = new MyTextButton("6", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        sept_bouton = new MyTextButton("7", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        huit_bouton = new MyTextButton("8", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);
        neuf_bouton = new MyTextButton("9", emptyButtonInactivePath, emptyButtonActivePath, buttonSize, comicSansMsPath, fontSizeButton);

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


        validerBouton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (isActive && (!validusAnimated.isSpeaking))
                {
                    if (etapeCorrection != null)
                    {
                        etapeCorrection.run();
                    }
                }
            }
        });
    }

    public float getCalculetteTopY()
    {
        return textureRegionFond.getRegionHeight() + this.positionY;
    }

    public String getInput()
    {
        return textFieldTest.getText();
    }

    public void screenDeletion()
    {
        textFieldTest.clear();

    }

    public MyPoint buttonPosition(int buttonNumber)
    {
        MyPoint buttonPosition = new MyPoint(0, 0);
        switch (buttonNumber)
        {
            case 0:
                buttonPosition.x = (zero_bouton.getX() + zero_bouton.getWidth() / 2);
                buttonPosition.y =  (zero_bouton.getY() + zero_bouton.getHeight() / 2);
                break;
            case 1:
                buttonPosition.x =  (un_bouton.getX() + un_bouton.getWidth() / 2);
                buttonPosition.y =  (un_bouton.getY() + un_bouton.getHeight() / 2);
                break;
            case 2:
                buttonPosition.x =  (deux_bouton.getX() + deux_bouton.getWidth() / 2);
                buttonPosition.y =  (deux_bouton.getY() + deux_bouton.getHeight() / 2);
                break;
            case 3:
                buttonPosition.x =  (trois_bouton.getX() + trois_bouton.getWidth() / 2);
                buttonPosition.y =  (trois_bouton.getY() + trois_bouton.getHeight() / 2);
                break;
            case 4:
                buttonPosition.x =  (quatre_bouton.getX() + quatre_bouton.getWidth() / 2);
                buttonPosition.y =  (quatre_bouton.getY() + quatre_bouton.getHeight() / 2);
                break;
            case 5:
                buttonPosition.x =  (cinq_bouton.getX() + cinq_bouton.getWidth() / 2);
                buttonPosition.y =  (cinq_bouton.getY() + cinq_bouton.getHeight() / 2);
                break;
            case 6:
                buttonPosition.x =  (six_bouton.getX() + six_bouton.getWidth() / 2);
                buttonPosition.y =  (six_bouton.getY() + six_bouton.getHeight() / 2);
                break;
            case 7:
                buttonPosition.x =  (sept_bouton.getX() + sept_bouton.getWidth() / 2);
                buttonPosition.y =  (sept_bouton.getY() + sept_bouton.getHeight() / 2);
                break;
            case 8:
                buttonPosition.x =  (huit_bouton.getX() + huit_bouton.getWidth() / 2);
                buttonPosition.y =  (huit_bouton.getY() + huit_bouton.getHeight() / 2);
                break;
            case 9:
                buttonPosition.x =  (neuf_bouton.getX() + neuf_bouton.getWidth() / 2);
                buttonPosition.y =  (neuf_bouton.getY() + neuf_bouton.getHeight() / 2);
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
        return new MyPoint((validerBouton.getX() + validerBouton.getWidth() / 2), (validerBouton.getY() + validerBouton.getHeight() / 2));
    }

    public void textRemove()
    {
        textFieldTest.setText("");
    }

    @Override
    public void myCorrectionStart()
    {
        isActive = false;
    }

    @Override
    public void myCorrectionStop()
    {
        isActive = false;
    }

    @Override
    public void myPause()
    {
        isActive = false;
    }

    @Override
    public void myResume()
    {
        isActive = true;
    }

    @Override
    public boolean isPause()
    {
        return false;
    }

    @Override
    public boolean isTouched(float x, float y)
    {
        return false;
    }

    @Override
    public boolean isDragable()
    {
        return false;
    }

    @Override
    public void setPosition(float x, float y)
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
                buttonAux.setPosition(paddingAux1 + positionX + (paddingAux2 + buttonSize) * i, positionY + paddingAux1 + (paddingAux2 + buttonSize) * j);
                buttonAux.draw(batch, 1.0f);
                buttonAux.getStyle().fontColor = Color.BLACK;
                cpt++;
            }
        }
        textFieldTest.draw(batch, 1.0f);
    }
}
