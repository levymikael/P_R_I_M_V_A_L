package com.evalutel.primval_desktop.onglets.chapitre2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.Dices;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UneArdoise2;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;


public class ScreenEx2_3 extends ScreenOnglet implements InputProcessor
{
    private UneArdoise2 uneArdoise2;
    private CalculetteView calculetteView;

    TextButton.TextButtonStyle styleTest;
    private Drawable drawableAux;

    private int diceTotal, failedAttempts, diceRenewal;

    private Dices dice1;

    private String nbInput;
    private boolean touchValidate = false;


    ScreenEx2_3(final Game game, String ongletTitre)
    {
        super(game, 2, 3, true, MyConstants.noteMaxChap2[1]);

        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/onglet2_3/des_fond.jpg");
        allDrawables.add(bgScreenEx1_1);

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

        int noteMax = db.getHighestNote(2, 3);

        String noteMaxObtenue = noteMax + "/18";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        resultatExercice = new UnResultat("Primval", 2, 3, 0, ongletTitre, 18, 0, dateTest, 0, 0, 0, 123);


        float widthCalculette = MyConstants.SCREENWIDTH / 6.5f;
        float hauteurCalculette = (widthCalculette * 362f / 355f) * 1.2f;
        float positionCalculetteX = MyConstants.SCREENWIDTH - widthCalculette - (MyConstants.SCREENWIDTH / 200f);
        float positionCalculetteY = MyConstants.SCREENWIDTH / 200f;

        calculetteView = new CalculetteView(stage, validusAnimated, positionCalculetteX, positionCalculetteY, widthCalculette, hauteurCalculette);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        float ardoise2Size = MyConstants.SCREENWIDTH / 6.5f;
        float posYArdoise2 = MyConstants.SCREENHEIGHT / 2f - ((ardoise2Size * 1.2f) / 2);

        uneArdoise2 = new UneArdoise2(stage, "", calculetteView.positionX, posYArdoise2, ardoise2Size);
        allDrawables.add(uneArdoise2);
        uneArdoise2.setActive(false);
        myCorrectionAndPauseGeneral.addElements(uneArdoise2);

        float diceWidth = MyConstants.SCREENWIDTH / 7f;
        float diceHeight = diceWidth * (263f / 314f);


        float dice2positionX = MyConstants.SCREENWIDTH / 4f + (diceWidth * 1.3f);

        dice1 = new Dices(MyConstants.SCREENWIDTH / 4f, MyConstants.SCREENHEIGHT / 2f, diceWidth, diceHeight, dice2positionX);
        dice1.setActive(false);
        allDrawables.add(dice1);

        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre2(game));

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
            MyTimer.TaskEtape nextEtape = new DiceStep(3_000, 1_500);

            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Total dun lancer de 2 des.mp3", nextEtape);
        }
    }

    private class DiceStep extends MyTimer.TaskEtape
    {
        private DiceStep(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (diceRenewal == 0)
            {
                activiteView.addTextActivite("Touche les dés pour les faire tourner");
            }
            else
            {
                activiteView.setTextActivite("Touche les dés pour les faire tourner");
            }

            dice1.setActive(true);
            dice1.diceRenewal = diceRenewal;

            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Touche les des pour les faire tourner.mp3");
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
            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Tape clavier premier de.mp3");

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
            } catch (Exception ignored)
            {

            }
            if ((value == dice1.getLastDice1value()))
            {
                validusAnimated.goodAnswerPlaySound(new NextQuestion(500));

                validusAnimated.isActive = false;
                addDiamonds(1);

                uneArdoise2.fillLabel(1, Integer.toString(value));
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
                }
                else if (value == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
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
            timer.schedule(new MoveMainToCalculette(1_000), 1_000);
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(dice1.getLastDice1value());

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
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
            switch (dice1.getLastDice1value())
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

            MyPoint buttonPosition = calculetteView.buttonPosition(dice1.getLastDice1value());

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textDisplay(dice1.getLastDice1value());
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

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);

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
            uneSouris.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textRemove();

            uneArdoise2.fillLabel(1, Integer.toString(dice1.getLastDice1value()));
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
            uneSouris.setVisible(false);
            nbInput = String.valueOf(dice1.getLastDice1value());
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
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

            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Tape clavier deuxieme de.mp3", nextEtape);
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
            } catch (Exception ignored)
            {

            }
            if ((value == dice1.getLastDice2value()))
            {
                validusAnimated.goodAnswerPlaySound(new NextQuestion2(1_000));

                validusAnimated.isActive = false;
                addDiamonds(1);

                uneArdoise2.fillLabel(2, Integer.toString(value));
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

                }
                else if (value == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
//                else if (value < dice1.getLastDice2value())
//                {
//                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
//                }
//                else if (value > dice1.getLastDice2value())
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

    private class EtapeRectification2 extends MyTimer.TaskEtape
    {
        private EtapeRectification2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            timer.schedule(new MoveMainToCalculette2(1_000), 1_000);
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(dice1.getLastDice2value());

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette2(1_500, 1_000);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
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

            MyPoint buttonPosition = calculetteView.buttonPosition(dice1.getLastDice2value());

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate2(500);

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textDisplay(dice1.getLastDice2value());
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

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1500);

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
            uneSouris.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear2(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);
            uneArdoise2.fillLabel(2, Integer.toString(dice1.getLastDice2value()));

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
            uneSouris.setVisible(false);
            timer.schedule(new NextQuestion2(500), 500);
            styleTest.up = drawableAux;
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


            MyTimer.TaskEtape nextEtape = new InputClavier3(500);


            activiteView.addTextActivite("Tape au clavier le total des points sur les deux dés puis valide.");
            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Tape clavier total deux des.mp3", nextEtape);
            nbInput = null;

            diceTotal = dice1.getLastDice1value() + dice1.getLastDice2value();
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
            } catch (Exception ignored)
            {

            }
            if (value == diceTotal)
            {

                validusAnimated.goodAnswerPlaySound(new ResetScreen(1_000));

                validusAnimated.isActive = false;
                addDiamonds(1);
                uneArdoise2.fillLabel(3, Integer.toString(value));
                questionCourante++;

                calculetteView.setActive(false);
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
//                else if (value == -1)
//                {
//                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
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
            timer.schedule(new MoveMainToCalculette3(1_000), 1_000);
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition((diceTotal));

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette3(1_500, 1_000);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
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
            switch (diceTotal)
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

            MyPoint buttonPosition = calculetteView.buttonPosition(diceTotal);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate3(500);

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textDisplay(diceTotal);
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

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1500);

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
            uneSouris.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear3(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);
            uneArdoise2.fillLabel(3, Integer.toString(diceTotal));

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
            uneSouris.setVisible(false);
            nbInput = String.valueOf(diceTotal);
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
            MyTimer.TaskEtape nextEtape = new Annonce1(1_500);

            solutionView.addTextActivite(dice1.getLastDice1value() + " + " + dice1.getLastDice2value() + " = " + diceTotal);
            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_JAnnonceLAddition.mp3", nextEtape);

            calculetteView.textRemove();

            uneArdoise2.eraseAllLabels();
        }
    }


    private class Annonce1 extends MyTimer.TaskEtape
    {
        private Annonce1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new Annonce2(1_500, 0);
            switch (dice1.getLastDice1value())
            {
                case 1:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/UnPlus.mp3", nextEtape);
                    break;
                case 2:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/DeuxPlus.mp3", nextEtape);
                    break;
                case 3:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/TroisPlus.mp3", nextEtape);
                    break;
                case 4:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/QuatrePlus.mp3", nextEtape);
                    break;
                case 5:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/CinqPlus.mp3", nextEtape);
                    break;
                case 6:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SixPlus.mp3", nextEtape);
                    break;
                case 7:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SeptPlus.mp3", nextEtape);
                    break;
                case 8:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/HuitPlus.mp3", nextEtape);
                    break;
                default:
                    break;
            }
        }
    }


    private class Annonce2 extends MyTimer.TaskEtape
    {
        private Annonce2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new AnnonceTotal(1_500, 0);
            switch (dice1.getLastDice2value())
            {
                case 1:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/UnEgal.mp3", nextEtape);
                    break;
                case 2:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/DeuxEgal.mp3", nextEtape);
                    break;
                case 3:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/TroisEgal.mp3", nextEtape);
                    break;
                case 4:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/QuatreEgal.mp3", nextEtape);
                    break;
                case 5:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/CinqEgal.mp3", nextEtape);
                    break;
                case 6:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SixEgal.mp3", nextEtape);
                    break;
                case 7:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/SeptEgal.mp3", nextEtape);
                    break;
                case 8:
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/Annonce_Resultat_Audio/HuitEgal.mp3", nextEtape);
                    break;
                default:
                    break;
            }
        }
    }

    private class AnnonceTotal extends MyTimer.TaskEtape
    {
        private AnnonceTotal(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new EtapeIntermediaire(1_500, 500);
            switch (dice1.getLastDice1value() + dice1.getLastDice2value())
            {
                case 2:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/deux.mp3", nextEtape);
                    break;
                case 3:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/trois.mp3", nextEtape);
                    break;
                case 4:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/quatre.mp3", nextEtape);
                    break;
                case 5:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/cinq.mp3", nextEtape);
                    break;
                case 6:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/six.mp3", nextEtape);
                    break;
                case 7:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/sept.mp3", nextEtape);
                    break;
                case 8:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/huit.mp3", nextEtape);
                    break;
                case 9:
                    metrologue.metrologuePlaySound("Sounds/Onglet1_4/neuf.mp3", nextEtape);
                    break;
                default:
                    break;
            }
        }
    }


    private class EtapeIntermediaire extends MyTimer.TaskEtape
    {
        private EtapeIntermediaire(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            diceRenewal++;
            if (diceRenewal == 6)
            {
                timer.schedule(new Fin(2_000, 0), 0);
            }
            else
            {
                timer.schedule(new DiceStep(3_500, 1_500), 0);
            }
        }
    }


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


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (dice1.isActive() && (dice1.contains(mousePointerX, mousePointerY)))
        {
            dice1.isTouched(mousePointerX, mousePointerY);

            dice1.setActive(false);

            timer.schedule(new EtapeInstructionArdoise1(4_000, 2_000), 3_000);
        }
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
            if (objectTouched instanceof ValidusAnimated)
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