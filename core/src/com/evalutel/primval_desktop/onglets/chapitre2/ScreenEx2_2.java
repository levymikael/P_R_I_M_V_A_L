package com.evalutel.primval_desktop.onglets.chapitre2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
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
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.SacDeBilles;
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


public class ScreenEx2_2 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;

    private UnePlancheNew planche1, planche2, planche3;
    private UneBille billeRectification;

    int[] numOiseauArray;

    private ArrayList<int[]> randOiseauxArray = new ArrayList<>();

    private UneArdoise2 uneArdoise2;
    private CalculetteView calculetteView;
    private float posX, posY;

    TextButton.TextButtonStyle styleTest;
    Drawable drawableAux;

    private int cptOiseauTotal, cptOiseau1, cptOiseau2, /*cptBille,*/
            oiseauxToDisplayBranche1, oiseauxToDisplayBranche2;
    private int brancheRenewal, failedAttempts;


    Label currentLabel;

    String nbInput;
    boolean afterCorrection, isAllActive, touchValidate = false;


    ScreenEx2_2(final Game game, String ongletTitre)
    {
        super(game, 2, 2, true, 15);

        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60, 9 * MyConstants.SCREENHEIGHT / 11, (largeurBilleUnique * 1.5f), (largeurBilleUnique * 1.5f));
        sacDeBilles.largeurBille = largeurBilleMultiple;
        sacDeBilles.setActive(false);
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
//        allCorrigibles.add(sacDeBilles);

        float allPlanchesStartPositionX = 1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2;

        planche1 = new UnePlancheNew(allPlanchesStartPositionX, 1.9f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche2 = new UnePlancheNew(allPlanchesStartPositionX, 1.2f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche3 = new UnePlancheNew(allPlanchesStartPositionX, 0.5f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        allPlanches.add(planche1);
        allPlanches.add(planche2);
        allPlanches.add(planche3);


        for (int i = 0; i < allPlanches.size(); i++)
        {
            UnePlancheNew unePlanche = allPlanches.get(i);
            allDrawables.add(unePlanche);
            unePlanche.shouldReturnToReserve = true;
            myCorrectionAndPauseGeneral.addElements(unePlanche);
//            allCorrigibles.add(unePlanche);
        }

        planche2.setVisible(false);
        planche3.setVisible(false);
        planche2.setActive(false);
        planche3.setActive(false);

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

        String noteMaxObtenue = noteMax + "/15";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        billesList = autoFillPlanche();

        oiseauxList = getNumberOiseauxArList();

        getRandOiseauxArray();

        resultatExercice = new UnResultat("Primval", 2, 2, 0, ongletTitre, 15, 0, dateTest, 0, 0, 0, 123);


        float widthCalculette = MyConstants.SCREENWIDTH / 6.5f;
        float hauteurCalculette = (widthCalculette * 362f / 355f) * 1.2f;
        float positionCalculetteX = MyConstants.SCREENWIDTH - widthCalculette - (MyConstants.SCREENWIDTH / 200f);
        float positionCalculetteY = MyConstants.SCREENWIDTH / 200f;

        calculetteView = new CalculetteView(stage, validusAnimated, positionCalculetteX, positionCalculetteY, widthCalculette, hauteurCalculette);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        float ardoise2Size = MyConstants.SCREENWIDTH / 6.5f;
        float posYArdoise2 = calculetteView.getCalculetteTopY() + MyConstants.SCREENHEIGHT / 15f;

        uneArdoise2 = new UneArdoise2(stage, "", calculetteView.positionX, posYArdoise2, ardoise2Size);
        allDrawables.add(uneArdoise2);
        uneArdoise2.setActive(false);
        myCorrectionAndPauseGeneral.addElements(uneArdoise2);


        metrologue.setVisible(false);

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

//        cptOiseau1 = 5;
//        cptOiseau2 = 2;
//        metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_JAnnonceLAddition.mp3", new Annonce1(1_000));
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
            MyTimer.TaskEtape nextEtape = new EtapeInstructionPlanche1(3_000, 0);

            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_titreOnglet.mp3", nextEtape);
        }
    }

    private class EtapeInstructionPlanche1 extends MyTimer.TaskEtape
    {
        private EtapeInstructionPlanche1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new DisplayOiseauxBranche1(2_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_desoiseauxSePosentSurLaBranche.mp3", nextEtape);
        }
    }


    private class DisplayOiseauxBranche1 extends MyTimer.TaskEtape
    {
        private DisplayOiseauxBranche1(long durMillis, long delay)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new DisplayOiseauxBranche1(0, 0);

            oiseauxToDisplayBranche1 = randOiseauxArray.get(brancheRenewal)[0];
            sacDeBilles.setActive(true);
            activiteView.setTextActivite("Place sur la première planche autant de billes que d'oiseaux que tu vois sur la première branche, tape leur nombre au clavier puis valide");

            if (cptOiseau1 < oiseauxToDisplayBranche1)
            {
                if ((cptOiseau1 == 1) && (brancheRenewal == 0))
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_PlaceSurLaPremiereBranche.mp3");
                }
                UnOiseau oiseau = oiseauxList.get(cptOiseau1);

                posY = 7 * MyConstants.SCREENHEIGHT / 10f;
                posX = (MyConstants.SCREENWIDTH / 7f) + ((oiseau.animationWidth + oiseau.animationWidth / 15) * (cptOiseau1));


                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau1++;
            }
            else
            {
                timer.schedule(new InputClavier1(500), 0);
            }
        }
    }

    private class InputClavier1 extends MyTimer.TaskEtape
    {

        private InputClavier1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            sacDeBilles.setActive(true);
            calculetteView.setActive(true);
            validusAnimated.setActive(true);

            planche1.shouldReturnToReserve = true;
            planche2.shouldReturnToReserve = true;
            planche3.shouldReturnToReserve = true;

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
            if ((planche1.getNumberBilles() == oiseauxToDisplayBranche1) && (value == planche1.getNumberBilles()))
            {
                validusAnimated.goodAnswerPlaySound(new NextQuestion(500));
                validusAnimated.isActive = false;
                addDiamonds(1);
                planche1.setAllBillesInactive();
                planche1.setActive(false);
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche1);

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

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

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
            int numOiseauxBranche1 = randOiseauxArray.get(questionCourante)[0];

            uneSouris.setVisible(false);
            nbInput = String.valueOf(numOiseauxBranche1);
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

            isAllActive = true;

            timer.schedule(new EtapeInstructionPlanche2(500, 0), 0);


            nbInput = null;
        }
    }

    private class EtapeInstructionPlanche2 extends MyTimer.TaskEtape
    {
        private EtapeInstructionPlanche2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new DisplayOiseauxBranche2(2_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_PlaceSurLaDeuxiemePlancheAutantDebIlles.mp3", nextEtape);
            activiteView.addTextActivite("Place sur la deuxième planche autant de billes que d'oiseaux que tu vois sur la seconde branche, tape leur nombre au clavier puis valide");
        }
    }


    private class DisplayOiseauxBranche2 extends MyTimer.TaskEtape
    {
        private DisplayOiseauxBranche2(long durMillis, long delay)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            DisplayOiseauxBranche2 nextEtape = new DisplayOiseauxBranche2(0, 0);

            oiseauxToDisplayBranche2 = randOiseauxArray.get(brancheRenewal)[1];

            planche2.setVisible(true);

            if ((cptOiseau2 < oiseauxToDisplayBranche2)/* && ((oiseauxToDisplayBranche1 + oiseauxToDisplayBranche2) <= 9)*/)
            {
                if (cptOiseau2 + cptOiseau1 < 9)
                {
                    UnOiseau oiseau = oiseauxList.get(cptOiseau1 + cptOiseau2);
                    posX = (MyConstants.SCREENWIDTH / 7f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * (/*cptOiseau -*/ cptOiseau2);

                    posY = 5 * MyConstants.SCREENHEIGHT / 11f;

                    oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                    timer.schedule(nextEtape, 100);
                    cptOiseau2++;
                }
            }
            else
            {
                timer.schedule(new InputClavier2(500), 0);
            }
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
            sacDeBilles.setActive(true);
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
            if ((planche2.getNumberBilles() == oiseauxToDisplayBranche2) && (value == planche2.getNumberBilles()))
            {
                validusAnimated.goodAnswerPlaySound(new NextQuestion2(1_000));

                validusAnimated.isActive = false;
                addDiamonds(1);
                planche2.setAllBillesInactive();
                planche2.setActive(false);
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
                planche2.addBilleAndOrganize(billeRectification);
                timer.schedule(new EtapeRectification2(500), 500);
            }
            else if (planche2.getNumberBilles() > oiseauxToDisplayBranche2)
            {
                billeRectification = planche2.getLastBille();
                planche2.removeBille(billeRectification);
                sacDeBilles.addBilleToReserve(billeRectification);

                timer.schedule(new EtapeRectification2(500), 500);
            }
            else if (planche2.getNumberBilles() == oiseauxToDisplayBranche2)
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(oiseauxToDisplayBranche2);

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
            switch (oiseauxToDisplayBranche2)
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

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

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
            int numOiseauxBranche2 = randOiseauxArray.get(questionCourante)[1];
            uneSouris.setVisible(false);
            nbInput = String.valueOf(numOiseauxBranche2);
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

            timer.schedule(new OiseauxOut(500, 0), 0);

            nbInput = null;

            cptOiseauTotal = cptOiseau1 + cptOiseau2;

        }
    }


    private class OiseauxOut extends MyTimer.TaskEtape
    {
        private OiseauxOut(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            sacDeBilles.setActive(false);
            planche1.shouldReturnToReserve = false;
            planche2.shouldReturnToReserve = false;
            planche3.shouldReturnToReserve = false;
            if (cptOiseauTotal != 0)
            {
                if (cptOiseauTotal == cptOiseau1)
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_LesOiseauxSenvolent.mp3");
                }

                UnOiseau oiseau = oiseauxList.get(cptOiseauTotal - 1);

                int posX = MyConstants.SCREENWIDTH * 2;
                int posY = MyConstants.SCREENHEIGHT * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(new OiseauxOut(250, 0), 750);
                cptOiseauTotal--;
            }
            else
            {
                planche3.setVisible(true);

                metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_DeplaceToutesLesBillesSurLaPlancheTotal.mp3", new InputClavier3(/*(250),*/ 1_000));
                activiteView.addTextActivite("Déplace toutes les billes sur la planche Total. \nTape ensuite sur le clavier le nombre de billes qui s'y trouvent puis valide.");

                brancheRenewal++;
            }
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
//            sacDeBilles.setActive(true);
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
                validusAnimated.goodAnswerPlaySound(new ResetScreen(1_000));

                validusAnimated.isActive = false;
                addDiamonds(1);
                planche2.setAllBillesInactive();
                uneArdoise2.fillLabel(3, Integer.toString(value));
                questionCourante++;
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
                    planche1.removeBille(billeRectification);
                }
                else if (planche2.getNumberBilles() != 0)
                {
                    billeRectification = planche2.getLastBille();
                    planche2.removeBille(billeRectification);


                }
                planche3.addBilleAndOrganize(billeRectification);
                timer.schedule(new AddBilles3(500), 500);
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition((cptOiseau1 + cptOiseau2));

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

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

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

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_500);

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
            uneSouris.setVisible(false);
            nbInput = String.valueOf((cptOiseau1 + cptOiseau2));
            afterCorrection = true;
            timer.schedule(new ResetScreen(1_000), 500);
            styleTest.up = drawableAux;
//            planche3.setAllBillesActive();
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
            planche2.setVisible(false);
            planche3.setVisible(false);

            solutionView.addTextActivite(cptOiseau1 + " + " + cptOiseau2 + " = " + (cptOiseau1 + cptOiseau2));
            metrologue.metrologuePlaySound("Sounds/Onglet2_2/chap2_onglet2_JAnnonceLAddition.mp3", new Annonce1(1_000));

            planche3.removeAllBilles(sacDeBilles);

            sacDeBilles.setActive(true);
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
            switch (cptOiseau1)
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
            switch (cptOiseau2)
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
            switch (cptOiseau1 + cptOiseau2)
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

            cptOiseau1 = 0;
            cptOiseau2 = 0;
            cptOiseauTotal = 0;

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
            if (questionCourante == 15)
            {
                timer.schedule(new Fin(2_000, 0), 0);
            }
            else
            {
                timer.schedule(new EtapeInstructionPlanche1(1_500, 0), 0);

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


    public ArrayList<UneBille> autoFillPlanche()
    {
        float firstPositionBilleX = (sacDeBilles.getPosition().x + sacDeBilles.largeurBille / 4);
        float firstPositionBilleY = (sacDeBilles.getPosition().y + sacDeBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size(); i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, largeurBilleMultiple);
            sacDeBilles.addBilleToReserve(billeAdded);
            allDrawables.add(billeAdded);
            objectTouchedList.add(billeAdded);
            billeAdded.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(billeAdded);

        }
        return billesList;
    }


    public int randOiseau2(int nbOiseau1)
    {
        return MathUtils.random(1, Math.min(1, 9 - nbOiseau1));
    }

    private void getRandOiseauxArray()
    {
        for (int i = 0; i < 5; i++)
        {
            int[] oiseauxNumUpTo9 = {0, 0};
            int nbOiseau1 = MathUtils.random(1, 8);

            oiseauxNumUpTo9[0] = nbOiseau1;
            oiseauxNumUpTo9[1] = randOiseau2(nbOiseau1);
            randOiseauxArray.add(oiseauxNumUpTo9);

        }
    }


    private ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;
        float oiseauWidth = MyConstants.SCREENWIDTH / 18f;
        float oiseauHeight = oiseauWidth * (500f / 396f);

        for (int i = 0; i < 9; i++)
        {
            float firstPositionOiseauXNew = firstPositionOiseauX + (i * MyConstants.SCREENWIDTH / 70f);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, oiseauWidth, oiseauHeight);
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
            myCorrectionAndPauseGeneral.addElements(unOiseau);
        }
        return oiseauxList;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

//        boolean isReserveActif = sacDeBilles.isActive();
        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
        {
//            if (billesList.size() <= 9)
            if ((planche1.getNumberBilles() + planche2.getNumberBilles() + planche3.getNumberBilles() <= 9) || (billesList.size() <= 9))
            {
                UneBille billeAdded = sacDeBilles.getBilleAndRemove();
                if (billeAdded != null)
                {
                    billeAdded.setVisible(true);
                    objectTouched = billeAdded;
                    billeAdded.setActive(true);
                }
            }
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