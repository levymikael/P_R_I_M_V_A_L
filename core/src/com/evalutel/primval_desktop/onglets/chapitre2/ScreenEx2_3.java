package com.evalutel.primval_desktop.onglets.chapitre2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.Dices;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneArdoise2;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class ScreenEx2_3 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;

    private UnePlancheNew planche1, planche2, planche3;
    private UneBille billeRectification;

    int[] numOiseauArray;

    private ArrayList<int[]> randomDicesArray = new ArrayList<>();

    private UneArdoise2 uneArdoise2;
    private CalculetteView calculetteView;
    private float posX, posY;

    TextButton.TextButtonStyle styleTest;
    Drawable drawableAux;

    private int cptOiseauTotal, cptOiseau1, cptOiseau2, oiseauxToDisplayBranche1, oiseauxToDisplayBranche2;
    private int /*brancheRenewal,*/ failedAttempts;
    private int diceNumber1;

    Texture emptyDices;

    Label currentLabel;

    Dices dice1;

    String nbInput;
    boolean afterCorrection, isAllActive, touchValidate = false;


    public ScreenEx2_3(final Game game, String ongletTitre)
    {
        super(game, 2, 3, true, 18);

        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/onglet2_3/des_fond.jpg");
        allDrawables.add(bgScreenEx1_1);

//        oiseauxList = getNumberOiseauxArList();

//        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60, 9 * MyConstants.SCREENHEIGHT / 11, (float) (largeurBilleUnique * 1.5), (float) (largeurBilleUnique * 1.5));
//        sacDeBilles.largeurBille = largeurBilleMultiple;
////        sacDeBilles.isActive();
//        sacDeBilles.setActive(false);
//        allDrawables.add(sacDeBilles);
//        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
////        allCorrigibles.add(sacDeBilles);
//
//        planche1 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 1.9f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
//        planche2 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 1.2f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
//        planche3 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 0.5f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
//        allPlanches.add(planche1);
//        allPlanches.add(planche2);
//        allPlanches.add(planche3);

//
//        for (int i = 0; i < allPlanches.size(); i++)
//        {
//            UnePlancheNew unePlanche = allPlanches.get(i);
//            allDrawables.add(unePlanche);
//            unePlanche.shouldReturnToReserve = true;
//            myCorrectionAndPauseGeneral.addElements(unePlanche);
////            allCorrigibles.add(unePlanche);
//        }
//
//        planche2.setVisible(false);
//        planche3.setVisible(false);
//        planche2.setActive(false);
//        planche3.setActive(false);

        numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        float posEnonceX = (MyConstants.SCREENWIDTH - activiteWidth) / 2f;
        float posSolutionX = posEnonceX + activiteWidth / 2f;

        activiteView = new ActiviteView(stage, posEnonceX, activiteWidth * 42 / 1626, activiteWidth / 2f, "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        solutionView = new ActiviteView(stage, posSolutionX, activiteWidth * 42 / 1626, activiteWidth / 2f, "solution");
        allDrawables.add(solutionView);
        myCorrectionAndPauseGeneral.addElements(solutionView);

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(2, 2);

        String noteMaxObtenue = noteMax + "/18";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);


        getRandDesArray();

        resultatExercice = new UnResultat("Primval", 2, 3, 0, ongletTitre, 15, 0, dateTest, 0, 0, 0, 123);

        calculetteView = new CalculetteView(stage, validusAnimated);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        float buttonSize = (4 * MyConstants.SCREENWIDTH / 24f) + (2 * MyConstants.SCREENWIDTH / 100f) + (3 * MyConstants.SCREENWIDTH / 200f);
        float posYArdoise2 = 0.3f * MyConstants.SCREENWIDTH;

        uneArdoise2 = new UneArdoise2(stage, "", 3.95f * MyConstants.SCREENWIDTH / 5, posYArdoise2, buttonSize);
        allDrawables.add(uneArdoise2);
        uneArdoise2.setActive(false);
        myCorrectionAndPauseGeneral.addElements(uneArdoise2);

        float diceWidth = MyConstants.SCREENWIDTH / 7f;
        float diceHeight = diceWidth * (263f / 314f);
        float dicePlusHeight = diceWidth * (152f / 186f);

        Texture diceTexture1 = new Texture(Gdx.files.internal("Images/onglet2_3/des_vide.png"));
        diceTexture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table emptyDice1 = new Table();
        emptyDice1.setBackground(new SpriteDrawable(new Sprite(diceTexture1)));
        emptyDice1.setSize(diceWidth, diceHeight);
        emptyDice1.setPosition(MyConstants.SCREENWIDTH / 4f, 2 * MyConstants.SCREENHEIGHT / 3f);

        Texture diceTexturePlus = new Texture(Gdx.files.internal("Images/onglet2_3/des_plus.png"));
        diceTexturePlus.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table plusDice = new Table();
        plusDice.setBackground(new SpriteDrawable(new Sprite(diceTexturePlus)));
        plusDice.setSize(diceWidth / 2, dicePlusHeight / 2);
        plusDice.setPosition(emptyDice1.getX() + emptyDice1.getWidth() + 10, 2 * MyConstants.SCREENHEIGHT / 3f + 10);

        Texture diceTexture2 = new Texture(Gdx.files.internal("Images/onglet2_3/des_vide.png"));
        diceTexture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table emptyDice2 = new Table();
        emptyDice2.setBackground(new SpriteDrawable(new Sprite(diceTexture2)));
        emptyDice2.setSize(diceWidth, diceHeight);
        emptyDice2.setPosition(plusDice.getX() + (diceWidth / 3), 2 * MyConstants.SCREENHEIGHT / 3f);

        int ok = 5;
        ok++;

        float dice2positionX = emptyDice2.getX();

        dice1 = new Dices(MyConstants.SCREENWIDTH / 2f, MyConstants.SCREENHEIGHT / 2f, emptyDice1.getWidth(), emptyDice1.getHeight(), dice2positionX);

        dice1.setPosition(emptyDice1.getX(), emptyDice1.getY() - 300);
        dice1.setVisible(false);
        dice1.setActive(false);

        allDrawables.add(dice1);

        stage.addActor(emptyDice1);
        stage.addActor(plusDice);
        stage.addActor(emptyDice2);

        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre2(game));

//                game.dispose();
                Gdx.app.log("button click", "click!");

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1_000L;

                resultatExercice.setDuree(seconds);
                resultatExercice.setDate(endTime);

                if ((metrologue.isSpeaking) && (metrologue != null))
                {
                    metrologue.stopMusic();
                }
                else if ((validusAnimated.isSpeaking) && (validusAnimated != null))
                {
                    validusAnimated.stopMusic();
                }

                timer.cancel();
                AppSingleton appSingleton = AppSingleton.getInstance();
                MyDataBase db = appSingleton.myDataBase;

                db.insertResultat(resultatExercice);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                return true;
            }
        });


        timer.schedule(new PresentationMetrologue(3_000), 1_000);
    }


    private class PresentationMetrologue extends MyTimer.TaskEtape
    {
        private PresentationMetrologue(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Touche les dés pour les faire tourner");

            int dicevalue1 = dice1.getLastDicevalue();

            int diceValue2 = dice1.getLastDice2value();

            int ok = 5;
            ok++;

            timer.schedule(new DiceStep(1_500), 0);
        }
    }

    private class DiceStep extends MyTimer.TaskEtape
    {
        private DiceStep(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            dice1.setActive(true);

            int dicevalue1 = dice1.getLastDicevalue();

            int diceValue2 = dice1.getLastDice2value();

            int ok = 5;
            ok++;

            timer.schedule(new EtapeInstructionArdoise1(3_000, 500), 0);
        }
    }

    private class EtapeInstructionArdoise1 extends MyTimer.TaskEtape
    {
        private EtapeInstructionArdoise1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Tape au clavier le nombre de points sur le premier dé puis valide.");
            calculetteView.setActive(true);

            validusAnimated.etapeCorrection = new PressValidate1(0);
            calculetteView.etapeCorrection = new PressValidate1(0);
        }
    }


    private class PressValidate1 extends MyTimer.TaskEtape
    {
        private PressValidate1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String txtTape = calculetteView.getInput();

            int value = -1;

            try
            {
                value = Integer.parseInt(txtTape);
            } catch (Exception e)
            {

            }
            if ((value == dice1.getLastDicevalue())/* && (value == planche1.getNumberBilles())*/)
            {
//                if (questionCourante != 8)
//                {
                validusAnimated.goodAnswerPlaySound(new NextQuestion(500));
//                }
//                else
//                {
//                    timer.schedule(new Fin(1_000, 0), 500);
//                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
//                }
                validusAnimated.isActive = false;
                addDiamonds(1);

                uneArdoise2.fillLabel(1, Integer.toString(value));
                questionCourante++;
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteView.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
                    failedAttempts = 0;

                    addPierres(1);
                    questionCourante++;
                }
                else if (planche1.getNumberBilles() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                else if (planche1.getNumberBilles() < oiseauxToDisplayBranche1)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
                }
                else if (planche1.getNumberBilles() > oiseauxToDisplayBranche1)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus _ tu tes trompe trop de billes essaie encore.mp3");
                }
                else
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }

    private class EtapeRectification extends MyTimer.TaskEtape
    {
        private EtapeRectification(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            timer.schedule(new AddBilles1(1_000), 1_000);
        }
    }


    private class AddBilles1 extends MyTimer.TaskEtape
    {
        private AddBilles1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (planche1.getNumberBilles() < oiseauxToDisplayBranche1)
            {
                billeRectification = sacDeBilles.getBilleAndRemove();
                billeRectification.setVisible(true);
                planche1.addBilleAndOrganize(billeRectification);
                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (planche1.getNumberBilles() > oiseauxToDisplayBranche1)
            {
                billeRectification = planche1.getLastBille();
                planche1.removeBille(billeRectification);
                sacDeBilles.addBilleToReserve(billeRectification);

                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (planche1.getNumberBilles() == oiseauxToDisplayBranche1)
            {
                timer.schedule(new MoveMainToCalculette(1_000), 1_000);
            }
        }
    }


    private class MoveMainToCalculette extends MyTimer.TaskEtape
    {
        private MoveMainToCalculette(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche1);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
        }
    }

    private class ClickMainToCalculette extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            switch (oiseauxToDisplayBranche1)
            {
                case 1:
                    styleTest = calculetteView.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteView.deux_bouton.getStyle();
                    break;
                case 3:
                    styleTest = calculetteView.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteView.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteView.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteView.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteView.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteView.huit_bouton.getStyle();
                    break;
                case 9:
                    styleTest = calculetteView.neuf_bouton.getStyle();
                    break;
                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche1);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textDisplay(oiseauxToDisplayBranche1);
        }
    }

    private class MoveMainToValidate extends MyTimer.TaskEtape
    {
        private MoveMainToValidate(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1_000, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);

            styleTest.up = drawableAux;
        }
    }

    private class ClickOnValidate extends MyTimer.TaskEtape
    {
        private ClickOnValidate(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textRemove();

            uneArdoise2.fillLabel(1, Integer.toString(oiseauxToDisplayBranche1));
        }
    }

    private class MainDisappear extends MyTimer.TaskEtape
    {
        private MainDisappear(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(false);
            nbInput = String.valueOf(diceNumber1);
            afterCorrection = true;
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
//            planche1.setAllBillesActive();
            failedAttempts = 0;
        }
    }

    private class NextQuestion extends MyTimer.TaskEtape
    {

        private NextQuestion(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteView.textRemove();


            timer.schedule(new EtapeInstructionDice2(500, 0), 0);


            nbInput = null;
        }
    }

    private class EtapeInstructionDice2 extends MyTimer.TaskEtape
    {
        private EtapeInstructionDice2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new InputClavier2(2_000);

            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_PlaceSurLaDeuxiemePlancheAutantDebIlles.mp3", nextEtape);
            activiteView.addTextActivite("Tape au clavier le nombre de points sur le deuxième dé puis valide.");
        }
    }


    private class InputClavier2 extends MyTimer.TaskEtape
    {
        private InputClavier2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
//            sacDeBilles.setActive(true);
            calculetteView.setActive(true);
            validusAnimated.setActive(true);

            validusAnimated.etapeCorrection = new PressValidate2(0);
            calculetteView.etapeCorrection = new PressValidate2(0);
        }
    }

    private class PressValidate2 extends MyTimer.TaskEtape
    {
        private PressValidate2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String txtTape = calculetteView.getInput();

            int value = -1;

            try
            {
                value = Integer.parseInt(txtTape);
            } catch (Exception e)
            {

            }
            if ((value == dice1.getLastDice2value()))
            {
                validusAnimated.goodAnswerPlaySound(new NextQuestion2(1_000));

                validusAnimated.isActive = false;
                addDiamonds(1);

                uneArdoise2.fillLabel(2, Integer.toString(value));
                questionCourante++;
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteView.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification2(2_000));
                    failedAttempts = 0;

                    addPierres(1);
                    questionCourante++;

                }
                else if (planche2.getNumberBilles() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                else if (planche2.getNumberBilles() < oiseauxToDisplayBranche2)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
                }
                else if (planche2.getNumberBilles() > oiseauxToDisplayBranche2)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus _ tu tes trompe trop de billes essaie encore.mp3");
                }
                else
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }

    private class EtapeRectification2 extends MyTimer.TaskEtape
    {
        private EtapeRectification2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            timer.schedule(new AddBilles2(1_000), 1_000);
        }
    }


    private class AddBilles2 extends MyTimer.TaskEtape
    {
        private AddBilles2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (planche2.getNumberBilles() < oiseauxToDisplayBranche2)
            {
                billeRectification = sacDeBilles.getBilleAndRemove();
                billeRectification.setVisible(true);
                timer.schedule(new EtapeRectification2(500), 500);
            }
            else if (planche2.getNumberBilles() > oiseauxToDisplayBranche2)
            {
                billeRectification = planche2.getLastBille();
                planche2.removeBille(billeRectification);

                timer.schedule(new EtapeRectification2(500), 500);
            }
            else if (dice1.getLastDice2value() == oiseauxToDisplayBranche2)
            {
                timer.schedule(new MoveMainToCalculette2(1_000), 1_000);
            }
        }
    }


    private class MoveMainToCalculette2 extends MyTimer.TaskEtape
    {
        private MoveMainToCalculette2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche2);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette2(1_500, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
        }
    }

    private class ClickMainToCalculette2 extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            switch (dice1.getLastDice2value())
            {
                case 1:
                    styleTest = calculetteView.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteView.deux_bouton.getStyle();
                    break;
                case 3:
                    styleTest = calculetteView.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteView.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteView.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteView.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteView.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteView.huit_bouton.getStyle();
                    break;
                case 9:
                    styleTest = calculetteView.neuf_bouton.getStyle();
                    break;
                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche2);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate2(500);

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textDisplay(oiseauxToDisplayBranche2);
        }
    }

    private class MoveMainToValidate2 extends MyTimer.TaskEtape
    {
        private MoveMainToValidate2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate2(1_000, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1500);

            styleTest.up = drawableAux;
        }
    }

    private class ClickOnValidate2 extends MyTimer.TaskEtape
    {
        private ClickOnValidate2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear2(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);
            uneArdoise2.fillLabel(2, Integer.toString(oiseauxToDisplayBranche2));

            calculetteView.textRemove();
        }
    }

    private class MainDisappear2 extends MyTimer.TaskEtape
    {
        private MainDisappear2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(false);
//            nbInput = String.valueOf(numOiseauxBranche2);
            afterCorrection = true;
            timer.schedule(new NextQuestion2(500), 500);
            styleTest.up = drawableAux;
            planche2.setAllBillesActive();
            failedAttempts = 0;
        }
    }

    private class NextQuestion2 extends MyTimer.TaskEtape
    {

        private NextQuestion2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteView.textRemove();

            isAllActive = true;
//
//            if (questionCourante == 15)
//            {
//                timer.schedule(new Fin(500, 0), 500);
//            }
//            else
//            {
            timer.schedule(new InputClavier3(500), 0);
//            }

            nbInput = null;

            cptOiseauTotal = cptOiseau1 + cptOiseau2;

            int ok = 5;
            ok++;
        }
    }


    private class InputClavier3 extends MyTimer.TaskEtape
    {

        private InputClavier3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {

            for (int i = 0; i < allPlanches.size(); i++)
            {
                UnePlancheNew planche = allPlanches.get(i);
                planche.setAllBillesActive();
            }
            calculetteView.setActive(true);
            validusAnimated.setActive(true);

            validusAnimated.etapeCorrection = new PressValidate3(0);
            calculetteView.etapeCorrection = new PressValidate3(0);
        }
    }


    private class PressValidate3 extends MyTimer.TaskEtape
    {
        private PressValidate3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String txtTape = calculetteView.getInput();

            int value = -1;

            try
            {
                value = Integer.parseInt(txtTape);
            } catch (Exception e)
            {

            }
            if ((planche3.getNumberBilles() == (cptOiseau1 + cptOiseau2)) && (value == planche3.getNumberBilles()))
            {
//                if (questionCourante != 8)
//                {
                validusAnimated.goodAnswerPlaySound(new ResetScreen(1_000));
//                }
//                else
//                {
//                    timer.schedule(new Fin(1_000, 0), 500);
//                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
//                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                planche2.setAllBillesInactive();
                uneArdoise2.fillLabel(3, Integer.toString(value));
                questionCourante++;


//                solutionView.addTextActivite(cptOiseau1 + " + "+ cptOiseau2 +" = " + (cptOiseau1 + cptOiseau2));

            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteView.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification3(2_000));
                    failedAttempts = 0;

                    addPierres(1);
                    questionCourante++;

                }
                else if (planche3.getNumberBilles() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
//                else if (planche2.getNumberBilles() < oiseauxToDisplayBranche2)
//                {
//                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
//                }
//                else if (planche2.getNumberBilles() > oiseauxToDisplayBranche2)
//                {
//                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus _ tu tes trompe trop de billes essaie encore.mp3");
//                }
                else
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }

    private class EtapeRectification3 extends MyTimer.TaskEtape
    {
        private EtapeRectification3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            timer.schedule(new AddBilles3(1_000), 1_000);
        }
    }


    private class AddBilles3 extends MyTimer.TaskEtape
    {
        private AddBilles3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (planche3.getNumberBilles() < (cptOiseau1 + cptOiseau2))
            {
                if (planche1.getNumberBilles() != 0)
                {
                    billeRectification = planche1.getLastBille();
                }
                else if (planche2.getNumberBilles() != 0)
                {
                    billeRectification = planche2.getLastBille();

                }
                planche3.addBilleAndOrganize(billeRectification);
                timer.schedule(new EtapeRectification3(500), 500);
            }
//            else if (planche2.getNumberBilles() > oiseauxToDisplayBranche2)
//            {
//                billeRectification = planche2.getLastBille();
//                planche2.removeBille(billeRectification);
//                sacDeBilles.addBilleToReserve(billeRectification);
//
//                timer.schedule(new EtapeRectification2(500), 500);
//            }
            else if (planche3.getNumberBilles() == (cptOiseau1 + cptOiseau2))
            {
                timer.schedule(new MoveMainToCalculette3(1_000), 1_000);
            }
        }
    }


    private class MoveMainToCalculette3 extends MyTimer.TaskEtape
    {
        private MoveMainToCalculette3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition((cptOiseau1 + cptOiseau2));

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette3(1_500, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
        }
    }

    private class ClickMainToCalculette3 extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette3(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            switch ((cptOiseau1 + cptOiseau2))
            {
                case 1:
                    styleTest = calculetteView.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteView.deux_bouton.getStyle();
                    break;
                case 3:
                    styleTest = calculetteView.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteView.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteView.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteView.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteView.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteView.huit_bouton.getStyle();
                    break;
                case 9:
                    styleTest = calculetteView.neuf_bouton.getStyle();
                    break;
                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition((cptOiseau1 + cptOiseau2));

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate3(500);

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textDisplay((cptOiseau1 + cptOiseau2));
        }
    }

    private class MoveMainToValidate3 extends MyTimer.TaskEtape
    {
        private MoveMainToValidate3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate3(1_000, 1_000);

            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1500);

            styleTest.up = drawableAux;
        }
    }

    private class ClickOnValidate3 extends MyTimer.TaskEtape
    {
        private ClickOnValidate3(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear3(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);
            uneArdoise2.fillLabel(3, Integer.toString((cptOiseau1 + cptOiseau2)));

            calculetteView.textRemove();
        }
    }

    private class MainDisappear3 extends MyTimer.TaskEtape
    {
        private MainDisappear3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(false);
            nbInput = String.valueOf((cptOiseau1 + cptOiseau2));
            afterCorrection = true;
            timer.schedule(new ResetScreen(1_000), 500);
            styleTest.up = drawableAux;
            failedAttempts = 0;

        }
    }

    private class ResetScreen extends MyTimer.TaskEtape
    {
        private ResetScreen(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {

            solutionView.addTextActivite(cptOiseau1 + " + " + cptOiseau2 + " = " + (cptOiseau1 + cptOiseau2));
            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_JAnnonceLAddition.mp3", null);

            sacDeBilles.setActive(true);
            calculetteView.textRemove();

            uneArdoise2.eraseAllLabels();
        }
    }
//
//    private class Annonce1 extends MyTimer.TaskEtape
//    {
//        private Annonce1(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            MyTimer.TaskEtape nextEtape = new Annonce2(1_500, 500);
//            switch (cptOiseau1)
//            {
//                case 1:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/UnPlus.mp3", nextEtape);
//                    break;
//                case 2:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/DeuxPlus.mp3", nextEtape);
//                    break;
//                case 3:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/TroisPlus.mp3", nextEtape);
//                    break;
//                case 4:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/QuatrePlus.mp3", nextEtape);
//                    break;
//                case 5:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/CinqPlus.mp3", nextEtape);
//                    break;
//                case 6:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SixPlus.mp3", nextEtape);
//                    break;
//                case 7:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SeptPlus.mp3", nextEtape);
//                    break;
//                case 8:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/HuitPlus.mp3", nextEtape);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }


//    private class Annonce2 extends MyTimer.TaskEtape
//    {
//        private Annonce2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            MyTimer.TaskEtape nextEtape = new AnnonceTotal(1_500, 500);
//            switch (cptOiseau2)
//            {
//                case 1:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/UnEgal.mp3", nextEtape);
//                    break;
//                case 2:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/DeuxEgal.mp3", nextEtape);
//                    break;
//                case 3:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/TroisEgal.mp3", nextEtape);
//                    break;
//                case 4:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/QuatreEgal.mp3", nextEtape);
//                    break;
//                case 5:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/CinqEgal.mp3", nextEtape);
//                    break;
//                case 6:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SixEgal.mp3", nextEtape);
//                    break;
//                case 7:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SeptEgal.mp3", nextEtape);
//                    break;
//                case 8:
//                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/HuitEgal.mp3", nextEtape);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

//    private class AnnonceTotal extends MyTimer.TaskEtape
//    {
//        private AnnonceTotal(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (questionCourante != 15)
//            {
//                MyTimer.TaskEtape nextEtape = new EtapeInstructionPlanche1(1_500, 500);
//                switch (cptOiseau1 + cptOiseau2)
//                {
//                    case 2:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/deux.mp3", nextEtape);
//                        break;
//                    case 3:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/trois.mp3", nextEtape);
//                        break;
//                    case 4:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/quatre.mp3", nextEtape);
//                        break;
//                    case 5:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/cinq.mp3", nextEtape);
//                        break;
//                    case 6:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/six.mp3", nextEtape);
//                        break;
//                    case 7:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/sept.mp3", nextEtape);
//                        break;
//                    case 8:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/huit.mp3", nextEtape);
//                        break;
//                    case 9:
//                        metrologue.metrologuePlaySound("Sounds/Onglet1_4/neuf.mp3", nextEtape);
//                        break;
//                    default:
//                        break;
//                }
//
//                cptOiseau1 = 0;
//                cptOiseau2 = 0;
//                cptOiseauTotal = 0;
//            }
//            else if (questionCourante == 15)
//            {
//                timer.schedule(new Fin(2000, 500), 0);
//            }
//        }
//    }


    private class Fin extends MyTimer.TaskEtape
    {
        private Fin(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape finOnglet = new FinOnglet(1000, 500);
            finOnglet.run();

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1000L;

            timer.cancel();
        }
    }

//
//    public ArrayList<UneBille> autoFillPlanche()
//    {
//        float firstPositionBilleX = (sacDeBilles.getPosition().x + sacDeBilles.largeurBille / 4);
//        float firstPositionBilleY = (sacDeBilles.getPosition().y + sacDeBilles.largeurBille);
//
//        billesList = new ArrayList<>();
//
//        for (int i = 0; i < oiseauxList.size(); i++)
//        {
//            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, largeurBilleMultiple);
//            sacDeBilles.addBilleToReserve(billeAdded);
//            allDrawables.add(billeAdded);
//            objectTouchedList.add(billeAdded);
//            billeAdded.setVisible(false);
//            myCorrectionAndPauseGeneral.addElements(billeAdded);
//
//        }
//        return billesList;
//    }

    public int randomDice1()
    {
        Random rand = new Random();
        diceNumber1 = rand.nextInt(5) + 1;
        return diceNumber1;
    }

    public int randomDice2()
    {
        Random rand = new Random();
        int diceNumber2 = rand.nextInt(6 - diceNumber1) + 1;
        return diceNumber2;
    }

    public void getRandDesArray()
    {
        for (int i = 0; i < 6; i++)
        {
            int[] dicesNumUpTo9 = {0, 0};
            dicesNumUpTo9[0] = randomDice1();
            dicesNumUpTo9[1] = randomDice2();
            randomDicesArray.add(dicesNumUpTo9);
        }
    }

//
//    public ArrayList getNumberOiseauxArList()
//    {
//        oiseauxList = new ArrayList<>();
//
//        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
//        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;
//
//        for (int i = 0; i < 9; i++)
//        {
//            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
//            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (MyConstants.SCREENWIDTH / 15) * (396.0f / 500.0f), (float) (MyConstants.SCREENWIDTH / 15) * (500.0f / 396.0f));
//            allDrawables.add(unOiseau);
//            oiseauxList.add(unOiseau);
//            myCorrectionAndPauseGeneral.addElements(unOiseau);
//        }
//        return oiseauxList;
//    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button)
//    {
//        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
//        mousePointerX = screenX;
//        mousePointerY = reversedScreenY;
//
//        if (reserveBilles.contains(screenX, reversedScreenY)) /*si bille part de la reserve*/
//        {
//            System.out.println("clickedOnContainer");
//            UneMain uneMainAdded = new UneMain("Images/EnonceUIElements/doigt_new.png",reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille, reserveBilles.largeurBille);
//            objectTouchedList.add(uneMainAdded);
//            allDrawables.add(uneMainAdded);
//            objectTouched = uneMainAdded;
////            firstPositionX = mousePointerX;
////            firstPositionY = mousePointerY;
//        } else /*si bille part de la planche*/
//        {
//            for (int i = 0; i < objectTouchedList.size(); i++)
//            {
//                MyTouchInterface objetAux = objectTouchedList.get(i);
//
//                if (objetAux.isTouched(screenX, reversedScreenY))
//                {
//                    objectTouched = objetAux;
//                    firstPositionX = objectTouched.getPositionBille().x;
//                    firstPositionY = objectTouched.getPositionBille().y;
//
//                    if (objectTouched instanceof UneMain)
//                    {
//                        UneMain uneMainAux = (UneMain) objectTouched;
//                        uneMainAux.touchDown();
//                        break;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer)
//    {
//        if (objectTouched != null)
//        {
//            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
//        }
//        return true;
//    }
//
//    @Override
//    public boolean TouchUp(int screenX, int screenY, int pointer, int button)
//    {
//        if (objectTouched != null)
//        {
//            if (objectTouched instanceof UneMain)
//            {
//                UneMain mainAux = (UneMain) objectTouched;
//                mainAux.TouchUp(planche1, screenX, MyConstants.SCREENHEIGHT - screenY);
////
////                else /*si bille pas deposee dans planche*/
////                    {
////                    objectTouched.setPosition(firstPositionX, firstPositionY);
////                    if (billeAux.plancheNew != null) {
////                        if (billeAux.plancheNew.shouldReturnToReserve)
////                        {
////                            billeAux.setPosition(100000, 100000);
////                            allDrawables.remove(billeAux);
////                            billeAux.plancheNew = null;
////                        }
////                        else {
////                            planche1.addBilleAndOrganize(billeAux);
////                            planche2.addBilleAndOrganize(billeAux);
////                            planche3.addBilleAndOrganize(billeAux);
////                        }
////                    } else {
////                        allDrawables.remove(billeAux);
////                        billeAux.setPosition(100000, 100000);
////                    }
////                }
//            }
//
//        }
//        objectTouched = null;
//        return false;
//    }
//

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (dice1.isActive())
        {

            dice1.isTouched(mousePointerX, mousePointerY);
        }

//        boolean isReserveActif = sacDeBilles.isActive();
//        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
//        {
//            int ok = 5;
//            ok++;
////            if (billesList.size() <= 9)
//            if ((planche1.getNumberBilles() + planche2.getNumberBilles() + planche3.getNumberBilles() <= 9) || (billesList.size() <= 9))
//            {
//                UneBille billeAdded = sacDeBilles.getBilleAndRemove();
//                if (billeAdded != null)
//                {
//                    billeAdded.setVisible(true);
//                    objectTouched = billeAdded;
//                    billeAdded.setActive(true);
//                }
//            }
//        }
//        else if (validusAnimated.contains(mousePointerX, mousePointerY) && validusAnimated.isActive() && (!validusAnimated.isPause()))
//        {
//            objectTouched = validusAnimated;
//        }
//        else /*si bille part de la planche*/
//        {
//            for (int i = 0; i < objectTouchedList.size(); i++)
//            {
//                MyTouchInterface objetAux = objectTouchedList.get(i);
//
//                if (objetAux.isTouched(screenX, reversedScreenY) && objetAux.isActive())
//                {
//                    objectTouched = objetAux;
//                    firstPositionX = mousePointerX;
//                    firstPositionY = mousePointerY;
//
//                    if (objectTouched instanceof UneBille)
//                    {
//                        UneBille billeAux = (UneBille) objectTouched;
//                        billeAux.touchDown();
//                        break;
//                    }
//                }
//            }
//        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if ((objectTouched != null) && (objectTouched.isDragable()))
        {
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
        }
        return true;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if ((objectTouched != null) && (objectTouched.isActive()))
        {
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;

                billeAux.touchUp(allPlanches);
                billesList.add(billeAux);
            }
            else if (objectTouched instanceof ValidusAnimated)
            {
                if (validusAnimated.contains(mousePointerX, mousePointerY))
                {
                    validusAnimated.touchUp(mousePointerX, mousePointerY);
                }
            }
        }
        objectTouched = null;
        return false;
    }
}