package com.evalutel.primval_desktop.onglets.chapitre2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneArdoise2;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;
import java.util.Random;


public class ScreenEx2_2 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;

    private UnePlancheNew planche1, planche2, planche3;
    private UneBille billeRectification;

    ScreeenBackgroundImage bgScreenEx1_1;


    DatabaseDesktop dataBase;
    int[] numOiseauArray;

    ArrayList<int[]> randOiseauxArray = new ArrayList<>();

    UneArdoise2 uneArdoise2;
    protected CalculetteView calculetteView;
    float posX, posY;

    TextButton.TextButtonStyle styleTest;
    Drawable drawableAux;

    int cptOiseau1, cptOiseau2, cptBille, oiseauxToDisplayBranche1, oiseauxToDisplayBranche2;
    int cpt, failedAttempts;
    int numOiseauxBranche1, numOiseauxBranche2;


    Label currentLabel;

    String nbInput;
    boolean afterCorrection, isAllActive, touchValidate = false;


    public ScreenEx2_2(Game game, DatabaseDesktop dataBase, String ongletTitre)
    {
        super(game, dataBase, 2, 2, true, 15);

        this.dataBase = dataBase;

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60, 9 * MyConstants.SCREENHEIGHT / 11, (float) (largeurBilleUnique * 1.5), (float) (largeurBilleUnique * 1.5));
        sacDeBilles.largeurBille = largeurBilleMultiple;
        sacDeBilles.isActive();
        sacDeBilles.setActive(false);
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
//        allCorrigibles.add(sacDeBilles);

        planche1 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 1.9f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche2 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 1.2f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche3 = new UnePlancheNew(1.9f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2, 0.5f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
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

        numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        float posEnonceX = (MyConstants.SCREENWIDTH - activiteWidth) / 2f;
        float posSolutionX = posEnonceX + activiteWidth / 2f;

        activiteView = new ActiviteView(stage, posEnonceX, activiteWidth * 42 / 1626, activiteWidth / 2f, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        solutionView = new ActiviteView(stage, posSolutionX, activiteWidth * 42 / 1626, activiteWidth / 2f, "solution");
        allDrawables.add(solutionView);
        myCorrectionAndPauseGeneral.addElements(solutionView);

        int noteMax = db.getHighestNote(2, 2);

        String noteMaxObtenue = noteMax + "/15";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25).padLeft(MyConstants.SCREENWIDTH / 46);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22);

        stage.addActor(tableTitre);

        billesList = autoFillPlanche();

        oiseauxList = getNumberOiseauxArList();


//        numOiseauArray = MyMath.genereTabAleatoire(9);

        getRandOiseauxArray();

        resultatExercice = new UnResultat("Primval", 2, 2, 0, ongletTitre, 0, 0, dateTest, 0, 0, 0, 123);

        calculetteView = new CalculetteView(stage, validusAnimated);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        float buttonSize = (4 * MyConstants.SCREENWIDTH / 24) + (2 * MyConstants.SCREENWIDTH / 100) + (3 * MyConstants.SCREENWIDTH / 200);
        float posYArdoise2 = 0.3f * MyConstants.SCREENWIDTH;

        uneArdoise2 = new UneArdoise2(stage, "", 3.95f * MyConstants.SCREENWIDTH / 5, posYArdoise2, buttonSize);
        allDrawables.add(uneArdoise2);
        uneArdoise2.setActive(false);
        myCorrectionAndPauseGeneral.addElements(uneArdoise2);

        metrologue.setVisible(false);

        timer.schedule(new PresentationMetrologue(3000), 1000);
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
            MyTimer.TaskEtape nextEtape = new EtapeInstructionPlanche1(3000, 0);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/Metrologue - Instructions onglet 2_1.mp3", nextEtape);
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

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet2_MaintenantOnVaCompteravecBadix.mp3", nextEtape);
            activiteView.setTextActivite("Place sur la première planche autant de billes que d'oiseaux que tu vois sur la première branche, tape leur nombre au clavier puis valide");
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
            DisplayOiseauxBranche1 nextEtape = new DisplayOiseauxBranche1(0, 0);

            oiseauxToDisplayBranche1 = randOiseauxArray.get(questionCourante)[0];

            if (cptOiseau1 < oiseauxToDisplayBranche1)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau1);

//                if (cptOiseau < 4)
//                {
                posY = 7 * MyConstants.SCREENHEIGHT / 10;
                posX = (MyConstants.SCREENWIDTH / 7) + (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau1);
//                }
//                else
//                {
//                    posY = 5 * MyConstants.SCREENHEIGHT / 11;
//                    posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 4);
//                }

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
                planche1.setAllBillesInactive();
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

            if (questionCourante == 15)
            {
                timer.schedule(new Fin(500, 0), 500);
            }
            else
            {
                timer.schedule(new EtapeInstructionPlanche2(500, 0), 0);
            }
//            questionCourante++;

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

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet2_MaintenantOnVaCompteravecBadix.mp3", nextEtape);
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

            oiseauxToDisplayBranche2 = randOiseauxArray.get(questionCourante)[1];

            planche2.setVisible(true);

            if ((cptOiseau2 < oiseauxToDisplayBranche2)/* && ((oiseauxToDisplayBranche1 + oiseauxToDisplayBranche2) <= 9)*/)
            {

                UnOiseau oiseau = oiseauxList.get(cptOiseau1 + cptOiseau2);

//                if (cptOiseau < 4)
//                {
//                posY = 7 * MyConstants.SCREENHEIGHT / 10;
                posX = (MyConstants.SCREENWIDTH / 7) + (oiseau.animationWidth + oiseau.animationWidth / 8) * (/*cptOiseau -*/ cptOiseau2);
//                }
//                else
//                {
                posY = 5 * MyConstants.SCREENHEIGHT / 11;
//                    posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 4);
//                }

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau2++;
            }
            else
            {
                timer.schedule(new InputClavier2(500), 0);
            }
        }
    }


    private class InputClavier2 extends MyTimer.TaskEtape
    {
//        private InputClavier(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

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
//                if (questionCourante != 8)
//                {
                validusAnimated.goodAnswerPlaySound(new NextQuestion2(500));
//                }
//                else
//                {
//                    timer.schedule(new Fin(1_000, 0), 500);
//                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
//                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                planche2.setAllBillesInactive();
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
            nbInput = String.valueOf(numOiseauxBranche1);
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
            timer.schedule(new EtapeInstructionPlanche2(500, 0), 0);
//            }
            questionCourante++;

            nbInput = null;
        }
    }

//    private class EtapeInstructionPlanche2 extends MyTimer.TaskEtape
//    {
//        private EtapeInstructionPlanche2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            MyTimer.TaskEtape nextEtape = new DisplayOiseauxBranche2(2_000, 1_000);
//
//            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet2_MaintenantOnVaCompteravecBadix.mp3", nextEtape);
//            activiteView.addTextActivite("Place sur la deuxième planche autant de billes que d'oiseaux que tu vois sur la seconde branche, tape leur nombre au clavier puis valide");
//        }
//    }


//    private class MoveMainToReserve1 extends MyTimer.TaskEtape
//    {
//        private MoveMainToReserve1(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille < 4)
//            {
//                uneMain.setVisible(true);
//
//                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//
//                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve1(500);
//
//                if (cptBille == 0)
//                {
//                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeVois4OiseauxSurLaPremiere.mp3", nextEtape);
//                    uneMain.moveTo(durationMillis, posXmain, posYMain, null, 500);
//                }
//                else
//                {
//                    uneMain.moveTo(durationMillis, posXmain, posYMain, nextEtape, 500);
//                }
//            }
//        }
//    }
//
//    private class DisplayBilleReserve1 extends MyTimer.TaskEtape
//    {
//        private DisplayBilleReserve1(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille < 4)
//            {
//                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//
//                UneBille bille = billesList.get(cptBille);
//                bille.setPositionCenter(posXMain, posYMain);
//                bille.setVisible(true);
////                bille.setActive(false);
//
//                MyTimer.TaskEtape nextEtape = new EtapeDragFirstBille(500);
//                if (cptBille == 0)
//                {
//                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeDeplace1a1.mp3");
//                    timer.schedule(nextEtape, 0);
//
//                }
//                else
//                {
//                    timer.schedule(nextEtape, 0);
//                }
//                uneMain.imageDown();
//            }
//        }
//    }
//
//    private class EtapeDragFirstBille extends MyTimer.TaskEtape
//    {
//        private EtapeDragFirstBille(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cptBille);
//            bille.setVisible(true);
//            float posX = planche1.getPosition().x + (planche1.getWidth() / 2);
//            float posY = planche1.getPosition().y + (planche1.getHeight() / 2);
//
//            MyTimer.TaskEtape nextEtape = new EtapeAddFirstBille(1_500, 500);
//
//
//            bille.animateImage(durationMillis, true, (posX - bille.getWidth() / 2), (posY - bille.getWidth() / 2), nextEtape, 1_000, 1f / 6f);
//
//            uneMain.cliqueTo(durationMillis, posX, posY, null, 500);
//        }
//    }
//
//
//    private class EtapeAddFirstBille extends MyTimer.TaskEtape
//    {
//        private EtapeAddFirstBille(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille < 3)
//            {
//                UneBille bille = billesList.get(cptBille);
//
//                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//
//                planche1.addBilleAndOrganize(bille);
//
//                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve1(500);
//
//                uneMain.moveTo(500, posXMain, posYMain, nextEtape, 500);
//            }
//            else
//            {
//                uneMain.setVisible(true);
//
//                UneBille bille = billesList.get(cptBille);
//
//                planche1.addBilleAndOrganize(bille);
//
//                MyPoint buttonPosition = calculetteView.buttonPosition(4);
//
//                float posX = buttonPosition.x;
//                float posY = buttonPosition.y;
//
//                MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);
//
//                metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTape4AuClavier.mp3", nextEtape);
//
//                uneMain.moveTo(durationMillis, posX, posY, null, 500);
//            }
//            cptBille++;
//        }
//    }
//
//    private class ClickMainToCalculette extends MyTimer.TaskEtape
//    {
//        private ClickMainToCalculette(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            styleTest = calculetteView.quatre_bouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            MyPoint buttonPosition = calculetteView.buttonPosition(4);
//
//            float posX = buttonPosition.x;
//            float posY = buttonPosition.y;
//
//            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);
//
//            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 500);
//
//            calculetteView.textDisplay(4);
//        }
//    }
//
//    private class MoveMainToValidate extends MyTimer.TaskEtape
//    {
//        private MoveMainToValidate(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1_000, 1_000);
//
//            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_PuisJeValide.mp3", nextEtape);
//
//            uneMain.moveTo(durationMillis, posX, posY, null, 1_000);
//
//            styleTest.up = drawableAux;
//        }
//    }
//
//    private class ClickOnValidate extends MyTimer.TaskEtape
//    {
//        private ClickOnValidate(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            uneMain.setVisible(true);
//
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new MoveMainToReserve2(500, 0);
//
//            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeVois3OiseauxSurLaSeconde.mp3");
//
//            styleTest = calculetteView.validerBouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);
//
//            calculetteView.textRemove();
//            uneArdoise2.fillLabel(1, "4");
//
//            currentLabel = activiteView.addTextActivite("4 +");
//        }
//    }
//
//
//    private class MoveMainToReserve2 extends MyTimer.TaskEtape
//    {
//        private MoveMainToReserve2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille >= 3 && cptBille < 5)
//            {
//                styleTest.up = drawableAux;
//
//                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//
//                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve2(500);
//
//                uneMain.moveTo(durationMillis, posXmain, posYMain, nextEtape, 500);
//            }
//        }
//    }
//
//    private class DisplayBilleReserve2 extends MyTimer.TaskEtape
//    {
//        private DisplayBilleReserve2(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille <= 6)
//            {
//                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//                float posX = posXMain;
//                float posY = posYMain;
//
//                UneBille bille = billesList.get(cptBille);
//                bille.setPositionCenter(posX, posY);
//
//                MyTimer.TaskEtape nextEtape = new EtapeDragSecondBille(500);
//                timer.schedule(nextEtape, 500);
//                uneMain.imageDown();
//            }
//        }
//    }
//
//    private class EtapeDragSecondBille extends MyTimer.TaskEtape
//    {
//        private EtapeDragSecondBille(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cptBille);
//            bille.setVisible(true);
//            float posX = planche2.getPosition().x + (planche2.getWidth() / 2);
//            float posY = planche2.getPosition().y + (planche2.getHeight() / 2);
//
//            MyTimer.TaskEtape nextEtape = new EtapeAddSecondBille(1_500, 500);
//
//            bille.animateImage(durationMillis, true, (posX - bille.getWidth() / 2), (posY - bille.getWidth() / 2), nextEtape, 1_000, 1f / 6f);
//
//            uneMain.cliqueTo(durationMillis, posX, posY, null, 500);
//        }
//    }
//
//
//    private class EtapeAddSecondBille extends MyTimer.TaskEtape
//    {
//        private EtapeAddSecondBille(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille <= 5)
//            {
//                UneBille bille = billesList.get(cptBille);
//
//                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
//                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
//
//                planche2.addBilleAndOrganize(bille);
//                cptBille++;
//
//                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve2(500);
//
//                uneMain.moveTo(500, posXMain, posYMain, nextEtape, 500);
//            }
//            else if (cptBille == 6)
//            {
//                UneBille bille = billesList.get(cptBille);
//                planche2.addBilleAndOrganize(bille);
//
//                cptBille++;
//
//                MyPoint buttonPosition = calculetteView.buttonPosition(3);
//
//                float posX = buttonPosition.x;
//                float posY = buttonPosition.y;
//
//                MyTimer.TaskEtape nextEtape = new ClickMainToCalculette2(1_500, 1_000);
//
//                uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
//            }
//        }
//    }
//
//
//    private class ClickMainToCalculette2 extends MyTimer.TaskEtape
//    {
//        private ClickMainToCalculette2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            styleTest = calculetteView.trois_bouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            MyPoint buttonPosition = calculetteView.buttonPosition(3);
//
//            float posX = buttonPosition.x;
//            float posY = buttonPosition.y;
//
//            MyTimer.TaskEtape nextEtape = new MoveMainToValidate2(500);
//
//            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTape3AuClavier.mp3");
//
//            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);
//
//            calculetteView.textDisplay(3);
//        }
//    }
//
//    private class MoveMainToValidate2 extends MyTimer.TaskEtape
//    {
//        private MoveMainToValidate2(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new ClickOnValidate2(1_000, 1_000);
//
//            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
//
//            styleTest.up = drawableAux;
//        }
//    }
//
//    private class ClickOnValidate2 extends MyTimer.TaskEtape
//    {
//        private ClickOnValidate2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            uneMain.setVisible(true);
//
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new OiseauxOut(500, 0);
//
//            styleTest = calculetteView.validerBouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
//
//            calculetteView.textRemove();
//            uneArdoise2.fillLabel(2, "3");
//
//            StringBuilder ex = currentLabel.getText();
//            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "3 =  ");
//
//            currentLabel.setText(newStrBuilder);
//
//        }
//    }

    private class OiseauxOut extends MyTimer.TaskEtape
    {
        private OiseauxOut(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cptOiseau1 != 0)
            {
                if (cptOiseau1 == 2)
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_TiensLesOiseauxSontPartisConbienYenAvaitIl.mp3");
                }
                UnOiseau oiseau = oiseauxList.get(cptOiseau1 - 1);
                int posX = MyConstants.SCREENWIDTH * 2;
                int posY = MyConstants.SCREENHEIGHT * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(new OiseauxOut(250, 0), 100);
                cptOiseau1--;
            }
            else
            {
//                timer.schedule(new MoveMainToPlanche1and2(1_000, 500), 0);
            }
        }
    }


//    private class MoveMainToPlanche1and2 extends MyTimer.TaskEtape
//    {
//        private MoveMainToPlanche1and2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cpt < 7)
//            {
//                styleTest.up = drawableAux;
//
//                float Xbille1 = billesList.get(cpt).currentPositionX + largeurBilleMultiple / 2;
//                float Ybille1 = billesList.get(cpt).currentPositionY /*+ largeurBilleMultiple / 2*/;
//
//                MyTimer.TaskEtape nextEtape = new ClickOnBille1(500);
//
//                uneMain.moveTo(durationMillis, Xbille1, Ybille1, nextEtape, 500);
//                if (cpt == 1)
//                {
//                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeDeplaceToutesLesBilles.mp3");
//                }
//            }
//            else
//            {
//                timer.schedule(new ClickMainToCalculette3(1_000, 500), 0);
//            }
//        }
//    }
//
//
//    private class ClickOnBille1 extends MyTimer.TaskEtape
//    {
//        private ClickOnBille1(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cpt);
//            float posX = planche3.getPosition().x + (planche3.getWidth() / 2);
//            float posY = planche3.getPosition().y + (planche3.getHeight() / 2);
//
//            MyTimer.TaskEtape nextEtape = new EtapeDragBille1(500, 0);
//            uneMain.imageDown();
//
//            bille.animateImage(durationMillis, true, posX, posY, nextEtape, 1_000, 1f / 6f);
//
//            uneMain.moveTo(durationMillis, posX, posY, null, 500);
//        }
//    }
//
//
//    private class EtapeDragBille1 extends MyTimer.TaskEtape
//    {
//        private EtapeDragBille1(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cpt);
//            MyTimer.TaskEtape nextEtape = new MoveMainToPlanche1and2(1_000, 0);
//            planche3.addBilleAndOrganize(bille);
//
//            uneMain.imageUp();
//
//            timer.schedule(nextEtape, 1_000);
//
//            cpt++;
//
//        }
//    }
//
//    private class ClickMainToCalculette3 extends MyTimer.TaskEtape
//    {
//        private ClickMainToCalculette3(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            MyPoint buttonPosition = calculetteView.buttonPosition(7);
//
//            float posX = buttonPosition.x;
//            float posY = buttonPosition.y;
//
//            MyTimer.TaskEtape nextEtape = new MoveMainToValidate3(500);
//
//            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 0);
//
//            calculetteView.textDisplay(7);
//            styleTest = calculetteView.sept_bouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTapeLeNombreDeBilles.mp3");
//        }
//    }

//    private class MoveMainToValidate3 extends MyTimer.TaskEtape
//    {
//        private MoveMainToValidate3(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new ClickOnValidate3(1_000, 1_000);
//
//            uneMain.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
//
//            styleTest.up = drawableAux;
//        }
//    }

//    private class ClickOnValidate3 extends MyTimer.TaskEtape
//    {
//        private ClickOnValidate3(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            uneMain.setVisible(true);
//
//            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();
//
//            float posX = buttonValidatePosition.x;
//            float posY = buttonValidatePosition.y;
//
//            MyTimer.TaskEtape nextEtape = new Fin(500, 0);
//
//            styleTest = calculetteView.validerBouton.getStyle();
//
//            drawableAux = styleTest.up;
//            styleTest.up = styleTest.down;
//
//            uneMain.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);
//
//            calculetteView.textRemove();
//            uneArdoise2.fillLabel(3, "7");
//
//            StringBuilder ex = currentLabel.getText();
//            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "7 ");
//
//            currentLabel.setText(newStrBuilder);
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


//            billesList.add(billeAdded);


        }
        return billesList;
    }

    public int randOiseau1()
    {
        Random rand = new Random();
        numOiseauxBranche1 = rand.nextInt(8) + 1;
        return numOiseauxBranche1;
    }


    public int randOiseau2()
    {
        Random rand = new Random();
        int numOiseauxBranche2 = rand.nextInt(9 - numOiseauxBranche1) + 1;

        return numOiseauxBranche2;
    }

    public void getRandOiseauxArray()
    {
        for (int i = 0; i < 5; i++)
        {
            int[] oiseauxNumUpTo9 = {0, 0};
            oiseauxNumUpTo9[0] = randOiseau1();
            oiseauxNumUpTo9[1] = randOiseau2();
            randOiseauxArray.add(oiseauxNumUpTo9);
        }
    }


    public ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;

        for (int i = 0; i < 9; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (MyConstants.SCREENWIDTH / 15) * (396.0f / 500.0f), (float) (MyConstants.SCREENWIDTH / 15) * (500.0f / 396.0f));
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
            myCorrectionAndPauseGeneral.addElements(unOiseau);
        }
        return oiseauxList;
    }


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

        boolean isReserveActif = sacDeBilles.isActive();
        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = sacDeBilles.getBilleAndRemove();
            billeAdded.setVisible(true);
            objectTouched = billeAdded;
            billeAdded.setActive(true);

//
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
                        //reserveBilles.isActive();
                        //reserveBilles.setActive(true);
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