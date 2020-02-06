package com.evalutel.primval_desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class ButtonTest extends Game /*implements MyDrawInterface */{

    Stage stage;
    TextButton textButtton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    @Override
    public void create() {
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
//        Button button = new Button();
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle(textButtonStyle);
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-textButtton");
        textButtonStyle.down = skin.getDrawable("down-textButtton");
        textButtonStyle.checked = skin.getDrawable("checked-textButtton");
        textButtton = new TextButton("Button1", textButtonStyle);
        textButtton.setPosition(100,200);
//        stage.addActor(textButtton);
    }

    @Override
    public void render() {
        super.render();
        stage.draw();
    }

}