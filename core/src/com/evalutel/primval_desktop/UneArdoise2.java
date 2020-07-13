package com.evalutel.primval_desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.evalutel.primval_desktop.General.MyConstants;

public class UneArdoise2 extends Table implements MyCorrectionAndPauseInterface, MyDrawInterface
{
    private final FreeTypeFontGenerator generatorComic;
    private final BitmapFont bitmapFont;
    public String number;

    Texture ardoiseBgActive, ardoiseBgInactive;

    boolean isActive = true;

    public float currentPositionX, currentPositionY;

    Label labelEmplacement1, labelEmplacement2, labelEmplacement3;
    Label.LabelStyle labelStyleWhite;

    public UneArdoise2(Stage stage, final String number, float posX, float posY, float ardoiseSize)
    {
        this.number = number;
        this.setPosition(posX, posY);
        this.setSize(ardoiseSize, ardoiseSize * 1.3f);


        ardoiseBgActive = new Texture("Images/Ardoise/ardoise_fond.png");
        ardoiseBgActive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        ardoiseBgInactive = new Texture("Images/Ardoise/ardoise_fond.png");
        ardoiseBgInactive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.setBackground(new TextureRegionDrawable(new TextureRegion(ardoiseBgActive)));

        generatorComic = new FreeTypeFontGenerator(Gdx.files.internal("font/comic_sans_ms.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = MyConstants.SCREENHEIGHT / 40;
        bitmapFont = generatorComic.generateFont(parameter);
        generatorComic.dispose();


        labelStyleWhite = new Label.LabelStyle();
        labelStyleWhite.font = bitmapFont;
        labelStyleWhite.fontColor = Color.WHITE;


//        labelEmplacement2 = new Label("0", labelStyleWhite);
//        labelEmplacement2 = new Label("0", labelStyleWhite);
//        labelEmplacement3 = new Label("0", labelStyleWhite);

        Table emplacement1 = new Table();
        Table emplacementPlus = new Table();
        Table emplacement2 = new Table();
        Table emplacementTiret = new Table();
        Table emplacementEgal = new Table();
        Table emplacement3 = new Table();

        Texture plusTxture = new Texture(Gdx.files.internal("Images/Ardoise/ardoise_plus_new.png"));
        plusTxture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture emplacementTxture = new Texture(Gdx.files.internal("Images/Ardoise/ardoise_emplacement.png"));
        emplacementTxture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture tiretTxture = new Texture(Gdx.files.internal("Images/Ardoise/ardoise_tiret_new.png"));
        tiretTxture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture egalTxture = new Texture(Gdx.files.internal("Images/Ardoise/ardoise_egal_new.png"));
        egalTxture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        float widthEmplacement = this.getWidth() * 0.4f;
        float heightEmplacement = widthEmplacement * 134f / 192f;

        float widthTiret = this.getWidth() * 0.8f;
        float heightTiret = widthTiret * (20f / 564f);

        float widthPlus = widthEmplacement / 2;
        float heightPlus = widthPlus * (120f / 127f);

        float widthEgal = widthEmplacement / 2;
        float heightEgal = widthEgal * (107f / 115f);


        emplacement1.setBackground(new SpriteDrawable(new Sprite(emplacementTxture)));
        emplacement1.add(labelEmplacement1);

        this.add().width(widthPlus);
        this.add(emplacement1).width(widthEmplacement).height(heightEmplacement);
        this.row();
        emplacementPlus.setBackground(new SpriteDrawable(new Sprite(plusTxture)));
        emplacement2.setBackground(new SpriteDrawable(new Sprite(emplacementTxture)));
        emplacement2.add(labelEmplacement2);
        this.add(emplacementPlus).width(widthPlus).height(heightPlus);
        this.add(emplacement2).width(widthEmplacement).height(heightEmplacement);
        this.row();
        emplacementTiret.setBackground(new SpriteDrawable(new Sprite(tiretTxture)));
        this.add(emplacementTiret).width(widthTiret).height(heightTiret);
        this.row();
        emplacementEgal.setBackground(new SpriteDrawable(new Sprite(egalTxture)));
        emplacement3.setBackground(new SpriteDrawable(new Sprite(emplacementTxture)));
        emplacement3.add(labelEmplacement3);
        this.add(emplacementEgal).width(widthEgal).height(heightEgal);
        this.add(emplacement3).width(widthEmplacement).height(heightEmplacement);


        if (isActive)
        {
            setTouchable(Touchable.enabled);
        }

        stage.addActor(this);
    }

    public void fillLabel(int labelNumber, String labelText)
    {
        switch (labelNumber)
        {
            case 1:
                labelEmplacement1 = new Label(labelText, labelStyleWhite);
                break;
            case 2:
                labelEmplacement2 = new Label(labelText, labelStyleWhite);
                break;
            case 3:
                labelEmplacement3 = new Label(labelText, labelStyleWhite);
                break;

            default:
                break;
        }

    }

    public void setActive(boolean active)
    {
        isActive = active;
    }


    public boolean isActive()
    {
        return isActive;
    }

    public int ardoiseClicked(int number)
    {
        return number;
    }

    @Override
    public void myCorrectionStart()
    {
        isActive = false;
    }

    @Override
    public void myCorrectionStop()
    {
        isActive = !isActive;
    }

    @Override
    public void myPause()
    {
        isActive = false;
    }

    @Override
    public void myResume()
    {
        isActive = !isActive;
    }

    @Override
    public boolean isPause()
    {
        return false;
    }

    @Override
    public void myDraw(Batch batch)
    {

    }
}
