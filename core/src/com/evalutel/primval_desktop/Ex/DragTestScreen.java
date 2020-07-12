//package com.evalutel.primval_desktop.Ex;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.evalutel.primval_desktop.Ex.CalculetteView;
////import com.evalutel.primval_desktop.Ex.EnonceView;
//import com.evalutel.primval_desktop.Ex.UneBilleAnimation;
//import com.evalutel.primval_desktop.Ex.UneBilleAnimation2;
//import com.evalutel.primval_desktop.Ex.UneBilleAnimation3;
//import com.evalutel.primval_desktop.Ex.UnePlanche;
//import com.evalutel.primval_desktop.UnOiseau;
//
//import java.util.ArrayList;
//
//public class DragTestScreen implements Screen
//{
//    UnOiseau actorTest;
//    EnonceView enonceMovable;
//    UnePlanche planche, reserve;
//    SpriteBatch batch;
//
//    private Stage stage;
//    UneBilleAnimation billeAnimation;
//    UneBilleAnimation2 billeAnimation2;
//    UneBilleAnimation3 billeAnimation3;
//    CalculetteView calculetteView;
//
//
//    public DragTestScreen()
//    {
////        actorTest = new UnOiseau();
//
//        batch = new SpriteBatch();
//
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//
////        stage.addActor(actorTest);
////
////        final AnimationImage animationImage = new AnimationImage("Images/Chien/","anim_pause_chien0",1000f,1000f, 500, 500);
////        stage.addActor(animationImage);
//
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();
//        float enonceWidth = (screenWidth / 4) * 3;
////
////        String numExercice = "3.";
////        String consigneExercice = " Le vrai premier Le \nvrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premier Le vrai premierLe vrai premier Le vrai premier sda sd asd asd asd asLe vrai premier";
////
////        enonceMovable = new EnonceView(stage, 50, 2000, enonceWidth, numExercice, consigneExercice);
////
////        stage.addActor(enonceMovable);
////
//        calculetteView = new CalculetteView(150, 200, 300, 350);
//        //stage.addActor(calculetteView);
//
//
//////
//        reserve = new UnePlanche(screenWidth / 2, 0);
//        //stage.addActor(reserve);
//
////
//        planche = new UnePlanche(100, 1000);
//        stage.addActor(planche);
//
//
//        ArrayList<String> imagesArray = new ArrayList<>();
//        imagesArray.add("Images/Badix/Billes/bille1.png");
//        billeAnimation = new UneBilleAnimation(imagesArray, screenWidth - 21, screenHeight - 100, 100, 100);
//        stage.addActor(billeAnimation);
//
//
//        ArrayList<String> imagesArray2 = new ArrayList<>();
//        imagesArray2.add("Images/Badix/Billes/bille5.png");
//        billeAnimation2 = new UneBilleAnimation2(imagesArray2, screenWidth - 100, screenHeight - 244, 100, 100);
//        //stage.addActor(billeAnimation2);
//
//        ArrayList<String> imagesArray3 = new ArrayList<>();
//        imagesArray3.add("Images/Badix/Billes/bille6.png");
//        billeAnimation3 = new UneBilleAnimation3(imagesArray3, screenWidth - 100, screenHeight - 134, 100, 100);
//        //stage.addActor(billeAnimation3);
//
//        UneBilleAnimation billeAnimation4 = new UneBilleAnimation(imagesArray, screenWidth - 465, screenHeight - 100, 100, 100);
//        //stage.addActor(billeAnimation4);
//
//
////        Texture textureFleche = new Texture("Images/oiseau/oiseau1_1_00000" + 4 + ".png");
////
////        TextureRegion region = new TextureRegion(textureFleche);
////        region.setRegionWidth(100);
////        region.setRegionHeight(200);
////
////        //image2.setBackground(new TextureRegionDrawable(region));
////
////        Image imageTest = new Image(new TextureRegionDrawable(region), Scaling.fill, Align.center);
////        imageTest.setWidth(100);
////        imageTest.setHeight(100);
//
////        actorTest.addListener(new ClickListener() {
////            @Override
////            public void clicked(InputEvent event, float x, float y) {
////                super.clicked(event, x, y);
////
////                animationImage.animateImage(1000, true, 1300, 300);
////
////            }
////        });
//
//    }
//
//
//    @Override
//    public void show()
//    {
//
//    }
//
//    @Override
//    public void render(float delta)
//    {
//        stage.act(Gdx.graphics.getDeltaTime());
//        //stage.act();
//        //planche.draw();
//
//        Gdx.gl.glClearColor(1, 1, 1, 1); // center
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.draw();
//
///*
//        batch.begin();
//        planche.draw(batch, 1.0f);
//        reserve.draw(batch, 1.0f);
//        billeAnimation.draw(batch, 1.0f);
//        calculetteView.draw(batch, 1.0f);
//        billeAnimation2.draw(batch, 1.0f);
//        billeAnimation3.draw(batch, 1.0f);
//        batch.end();*/
//    }
//
//    @Override
//    public void resize(int width, int height)
//    {
//
//    }
//
//    @Override
//    public void pause()
//    {
//
//    }
//
//    @Override
//    public void resume()
//    {
//
//    }
//
//    @Override
//    public void hide()
//    {
//
//    }
//
//    @Override
//    public void dispose()
//    {
//
//    }
//}
