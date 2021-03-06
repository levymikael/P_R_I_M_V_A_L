package com.evalutel.primval_desktop.onglets.chapitre1;

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
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_5 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    private CalculetteView calculetteView;

    private UnePlancheNew planche1;

    private int cptOiseau = 0;
    private int randNumOiseau;

    private TextButton.TextButtonStyle styleTest;

    private int[] numOiseauArray;

    private int failedAttempts, currrentBillesNumber = 0;

    private String nbInput;

    private Drawable drawableAux;


    ScreenEx1_5(final Game game, String ongletTitre)
    {
        super(game, 1, 5, true, 9);


        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        sacDeBilles = new SacDeBilles(53f * MyConstants.SCREENWIDTH / 60f, 9 * MyConstants.SCREENHEIGHT / 11f, (largeurBilleUnique * 1.5f), (largeurBilleUnique * 1.5f));
        sacDeBilles.largeurBille = largeurBilleUnique;
        sacDeBilles.setActive(false);
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
//        allCorrigibles.add(sacDeBilles);

        allPlanches = new ArrayList<>();

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2f - largeurPlancheUnique / 2, 0, largeurPlancheUnique, largeurBilleUnique);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
        planche1.setActive(false);
        allPlanches.add(planche1);

        for (int i = 0; i < 9; i++)
        {
            UneBille bille = new UneBille(10_000, 10_000, sacDeBilles.largeurBille);

            sacDeBilles.addBilleToReserve(bille);
            allDrawables.add(bille);
            objectTouchedList.add(bille);
            bille.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bille);
//            allCorrigibles.add(bille);
        }


        billesList = new ArrayList<>();

        numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        activiteView = new ActiviteView(stage, xTableTitre, activiteWidth * 42 / 1626, activiteWidth, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);


        float widthCalculette = MyConstants.SCREENWIDTH / 6.5f;
        float hauteurCalculette = (widthCalculette * 362f / 355f) * 1.2f;
        float positionCalculetteX = MyConstants.SCREENWIDTH - widthCalculette - (MyConstants.SCREENWIDTH / 200f);
        float positionCalculetteY = MyConstants.SCREENWIDTH / 200f;

        calculetteView = new CalculetteView(stage, validusAnimated, positionCalculetteX, positionCalculetteY, widthCalculette, hauteurCalculette);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(1, 5);

        String noteMaxObtenue = noteMax + "/9";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46);


        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        oiseauxList = getNumberOiseauxArList();

        numOiseauArray = MyMath.genereTabAleatoire(9);

        resultatExercice = new UnResultat("Primval", 1, 5, 0, ongletTitre, 9, 0, dateTest, 0, 0, 0, 123);


        uneSouris.setPosition(MyConstants.SCREENWIDTH / 2f, MyConstants.SCREENHEIGHT / 3f);


        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre1(game));

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


        timer.schedule(new PresentationOnglet(3_000), 1_000);
    }

    private class PresentationOnglet extends MyTimer.TaskEtape
    {
        private PresentationOnglet(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new EtapeInstruction(3_000, 2_000);

            metrologue.metrologuePlaySound("Sounds/onglet_1_5/Metrologue - onglet titre 1-5.mp3", nextEtape);
        }
    }

    private class EtapeInstruction extends MyTimer.TaskEtape
    {
        private EtapeInstruction(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private EtapeInstruction(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            randNumOiseau = numOiseauArray[questionCourante];

            MyTimer.TaskEtape nextEtape = new DisplayOiseaux(2000, 0);

            activiteView.setTextActivite("1. Place autant de billes que d'oiseaux que tu vois tape ce nombre au clavier puis valide");

            if (questionCourante == 0)
            {
                metrologue.metrologuePlaySound("Sounds/onglet_1_5/metrologue - Instructions onglet 1_5.mp3", nextEtape);
            }
            else
            {
                timer.schedule(new DisplayOiseaux(2000, 0), 0);
            }
        }
    }


    private class DisplayOiseaux extends MyTimer.TaskEtape
    {
        private DisplayOiseaux(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            DisplayOiseaux nextEtape = new DisplayOiseaux(0, 0);

            if (cptOiseau < randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);

                float posX;
                float posY;
                if (cptOiseau > 5)
                {
                    posY = 5 * MyConstants.SCREENHEIGHT / 11f;
                    posX = (2 * MyConstants.SCREENWIDTH / 9f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 3);
                }
                else
                {
                    posY = 7 * MyConstants.SCREENHEIGHT / 10f;

                    if (cptOiseau < 3)
                    {
                        posX = (MyConstants.SCREENWIDTH / 6f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
                    }
                    else
                    {
                        posX = (2 * MyConstants.SCREENWIDTH / 9f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
                    }
                }
                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau++;
            }
            else if (cptOiseau > randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau - 1);
                int posX = MyConstants.SCREENWIDTH * 2;
                int posY = MyConstants.SCREENHEIGHT * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau--;
            }
            else
            {
                timer.schedule(new InputClavier(1_000), 100);
            }
        }
    }

    private class InputClavier extends MyTimer.TaskEtape
    {
//        private InputClavier(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

        private InputClavier(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteView.setActive(true);
            validusAnimated.setActive(true);
            sacDeBilles.setActive(true);

            planche1.setActive(true);

            validusAnimated.etapeCorrection = new PressValidate(0);
            calculetteView.etapeCorrection = new PressValidate(0);

        }
    }


//    private class EtapeAttendreValidus extends MyTimer.TaskEtape
//    {
//        private EtapeAttendreValidus(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            failedAttempts = 0;
//            validusAnimated.isActive = true;
//            validusAnimated.etapeCorrection = new CheckValidus(0);
//        }
//    }

//    private class CheckValidus extends MyTimer.TaskEtape
//    {
//        private CheckValidus(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            if (planche1.getNumberBilles() == randNumOiseau)
//            {
//                MyTimer.TaskEtape nextEtape = new EtapeNextQuestion(1_000, 500);
//
//                if (questionCourante != 8)
//                {
//
////                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - C'est bien continue.mp3", nextEtape);
//                }
//                else
//                {
////                    activiteView.addTextActivite("Youpi ! Tu as gagné un diamant.");
////                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3", nextEtape);
//                }
////                validusAnimated.isActive = false;
////                addDiamonds(1);
////                planche1.SetAllBillesActive();
//            }
//            else
//            {
//                if (failedAttempts == 1)
//                {
//                    myCorrectionAndPauseGeneral.correctionStart();
//
//                    isInCorrection = !isInCorrection;
//
//                    validusAnimated.isActive = false;
//                    planche1.setAllBillesInactive();
//                    reserveBilles.setActive(false);
//                    failedAttempts = 0;
//
//                    MyTimer.TaskEtape nextEtape = new EtapeRectification(1_000);
//
//                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", nextEtape);
//
//                    addPierres(1);
//
//                }
//                else
//                {
//                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
//                }
//                failedAttempts++;
//            }
//        }
//    }

    private class PressValidate extends MyTimer.TaskEtape
    {
        private PressValidate(long durMillis)
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
            if ((planche1.getNumberBilles() == randNumOiseau) && (value == planche1.getNumberBilles()))
            {
                if (questionCourante != 8)
                {
                    validusAnimated.goodAnswerPlaySound(new EtapeNextQuestion(500, 0));
                    addDiamonds(1);

                }
                else
                {
                    timer.schedule(new Fin(1_000, 0), 500);
                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
                    addDiamonds(1);

                }
                validusAnimated.isActive = false;
//                addDiamonds(1);
                planche1.setAllBillesActive();
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
                    planche1.setAllBillesInactive();
                    planche1.setActive(false);
                    addPierres(1);
                }
                else if (planche1.getNumberBilles() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                else if (planche1.getNumberBilles() < randNumOiseau)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
                }
                else if (planche1.getNumberBilles() > randNumOiseau)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus _ tu tes trompe trop de billes essaie encore.mp3");
                }
                else if ((planche1.getNumberBilles() == randNumOiseau) && (value != planche1.getNumberBilles()))
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/Ong1_5_Validus_Tu n'as pas tape le bon chiffre au clavier.mp3");
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
            timer.schedule(new AddBilles(1_000), 1_000);
        }
    }


    private class AddBilles extends MyTimer.TaskEtape
    {
        private AddBilles(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille billeRectification;
            if (planche1.getNumberBilles() < randNumOiseau)
            {
                billeRectification = sacDeBilles.getBilleAndRemove();
                billeRectification.setVisible(true);
                planche1.addBilleAndOrganize(billeRectification);
                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (planche1.getNumberBilles() > randNumOiseau)
            {
                billeRectification = planche1.getLastBille();
                planche1.removeBille(billeRectification);
                sacDeBilles.addBilleToReserve(billeRectification);

                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (planche1.getNumberBilles() == randNumOiseau)
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(randNumOiseau);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneSouris.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
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
            switch (randNumOiseau)
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

            MyPoint buttonPosition = calculetteView.buttonPosition(randNumOiseau);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneSouris.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteView.textDisplay(randNumOiseau);
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

            uneSouris.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

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

            uneSouris.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteView.textRemove();

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
            nbInput = String.valueOf(randNumOiseau);
//            boolean afterCorrection = true;
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
            planche1.setAllBillesActive();
            planche1.setActive(true);
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

//            boolean isAllActive = true;

            if (questionCourante == 8)
            {
                timer.schedule(new Fin(500, 0), 500);
            }
            else
            {
                timer.schedule(new EtapeInstruction(500), 0);
            }
            questionCourante++;

            nbInput = null;
        }
    }

    private class EtapeNextQuestion extends MyTimer.TaskEtape
    {
        private EtapeNextQuestion(long durMillis)
        {
            super(durMillis);
        }

        private EtapeNextQuestion(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            questionCourante++;
            if (questionCourante == 9)       // fin exercice
            {
                MyTimer.TaskEtape finOnglet = new FinOnglet(1_000, 500);
                finOnglet.run();

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1_000L;

                ecrinDiamantView.getDiamantCount();

                timer.cancel();
            }
            else
            {
                timer.schedule(new EtapeInstruction(1_000, 500), 500);
                calculetteView.textRemove();

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
            MyTimer.TaskEtape finOnglet = new FinOnglet(1_000, 500);
            finOnglet.run();

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1_000L;

            timer.cancel();
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
        {
//            if ((billesList.size() <= 9) || planche1.getNumberBilles() <= 9)
//            {
            UneBille billeAdded = sacDeBilles.getBilleAndRemove();
            if (billeAdded != null)
            {

                objectTouched = billeAdded;
                billeAdded.setActive(true);
                billeAdded.setVisible(true);
            }
//            }
        }
        else if (validusAnimated.contains(mousePointerX, mousePointerY) && validusAnimated.isActive() && (!validusAnimated.isPause()))
        {
            objectTouched = validusAnimated;
        }
        else /*si bille part de la planche*/
        {
            for (int i = 0; i < objectTouchedList.size(); i++)
            {
                MyTouchInterface objetAux = objectTouchedList.get(i);

                if (objetAux.isTouched(screenX, reversedScreenY) && objetAux.isActive())
                {
                    objectTouched = objetAux;
                    firstPositionX = mousePointerX;
                    firstPositionY = mousePointerY;

                    if (objectTouched instanceof UneBille)
                    {
                        UneBille billeAux = (UneBille) objectTouched;

                        billeAux.touchDown();
                        break;
                    }
                }
            }
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

        if (objectTouched != null)
        {
            if ((objectTouched instanceof UneBille) && (planche1.isActive()))
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


    ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;

        for (int i = 0; i < 9; i++)
        {
            float firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (MyConstants.SCREENWIDTH / 12f) * (396f / 500f), (MyConstants.SCREENWIDTH / 12f) * (500f / 396f));

            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
        }
        return oiseauxList;
    }

}