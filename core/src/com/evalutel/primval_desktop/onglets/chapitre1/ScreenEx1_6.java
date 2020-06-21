package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.UnGateauAnniversaire;
import com.evalutel.primval_desktop.UneBougie;
import com.evalutel.primval_desktop.SacDeBougies;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_6 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBougie> bougiesList;
//    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnGateauAnniversaire> allGateaux;

    private UneBougie bougieRectification;

    protected CalculetteViewTest calculetteViewTest;

    private UnGateauAnniversaire gateauAnniversaire;
    ScreeenBackgroundImage bgScreenEx1_6;

    int cptOiseau, cptBille = 0;
    private int randNumOiseau;

    TextButton.TextButtonStyle styleTest;

    int posX, posY;

    int[] numPastilleArray;

    int failedAttempts, currrentBillesNumber = 0;

    boolean isInCorrection = false;

    boolean afterCorrection, isAllActive, touchValidate = false;

    DatabaseDesktop dataBase;

    String consigneExercice, nbInput;

    Label currentLabel;
    Drawable drawableAux;


    public ScreenEx1_6(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 5, true);

        this.dataBase = dataBase;

        bgScreenEx1_6 = new ScreeenBackgroundImage("Images/Onglet_1_6/anniversaire.jpg");
        allDrawables.add(bgScreenEx1_6);

        sacDeBougies = new sacDeBougies(53 * MyConstants.SCREENWIDTH / 60, 9 * MyConstants.SCREENHEIGHT / 11, (float) (largeurBille * 1.5), (float) (largeurBille * 1.5));
        sacDeBougies.largeurBille = largeurBille;
        sacDeBougies.isActive();
        sacDeBougies.setActive(false);
        allDrawables.add(sacDeBougies);
        myCorrectionAndPauseGeneral.addElements(sacDeBougies);
        allCorrigibles.add(sacDeBougies);

        gateauAnniversaire = new UnGateauAnniversaire(MyConstants.SCREENWIDTH / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        gateauAnniversaire.shouldReturnToReserve = true;
        allDrawables.add(gateauAnniversaire);
        myCorrectionAndPauseGeneral.addElements(gateauAnniversaire);
        allCorrigibles.add(gateauAnniversaire);

        for (int i = 0; i < 9; i++)
        {
            UneBougie bougie = new UneBille(sacDeBougies.currentPositionX, sacDeBougies.currentPositionY, sacDeBougies.largeurBille);

            sacDeBougies.addBougiesToReserves(bougie);
            allDrawables.add(bougie);
            objectTouchedList.add(bougie);
            bougie.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bougie);
            allCorrigibles.add(bougie);
        }

        allGateaux = new ArrayList<>();
        allGateaux.add(gateauAnniversaire);

        bougiesList = new ArrayList<>();

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Compter des oiseaux et taper leur nombre";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, "", "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        calculetteViewTest = new CalculetteViewTest(stage, validusAnimated);
        allDrawables.add(calculetteViewTest);
        calculetteViewTest.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteViewTest);


//        oiseauxList = getNumberOiseauxArList();

        numPastilleArray = MyMath.genereTabAleatoire(9);

        resultatExercice = new UnResultat("Primval", 1, 6, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);

//        calculetteViewTest.validerBouton.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                if (calculetteViewTest.isActive())
//                {
//                    Gdx.app.log("", "button validate pressed");
//                    timer.schedule(new PressValidate(0), 0);
//                }
//            }
//        });

        uneMain.setPosition(MyConstants.SCREENWIDTH / 2, MyConstants.SCREENHEIGHT / 3);


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
            uneMain.imageDown();

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
            randNumPastille = numOiseauArray[questionCourante];

            activiteView.setTextActivite("1. Place autant de billes que d'oiseaux que tu vois tape ce nombre au clavier puis valide");
            metrologue.metrologuePlaySound("Sounds/onglet_1_5/metrologue - Instructions onglet 1_5.mp3", new DisplayOiseaux(1_000, 0));
//            timer.schedule(new DisplayOiseaux(1_000, 0), 0);
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

                if (cptOiseau > 5)
                {
                    posY = 5 * MyConstants.SCREENHEIGHT / 11;
                    posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 3);
                }
                else
                {
                    posY = 7 * MyConstants.SCREENHEIGHT / 10;

                    if (cptOiseau < 3)
                    {
                        posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
                    }
                    else
                    {
                        posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
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
            sacDeBougies.setActive(true);
        }
    }

    private class InputClavier extends MyTimer.TaskEtape
    {
        private InputClavier(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private InputClavier(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteViewTest.setActive(true);
            validusAnimated.setActive(true);

            validusAnimated.etapeCorrection = new PressValidate(0);
            calculetteViewTest.etapeCorrection = new PressValidate(0);

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
            String txtTape = calculetteViewTest.getInput();

            int value = -1;

            try
            {
                value = Integer.parseInt(txtTape);
            } catch (Exception e)
            {

            }
            if ((gateauAnniversaire.getNumberBilles() == randNumOiseau) && (value == gateauAnniversaire.getNumberBilles()))
            {
                if (questionCourante != 8)
                {
                    validusAnimated.goodAnswerPlaySound(new EtapeNextQuestion(500, 0));
                }
                else
                {
                    timer.schedule(new Fin(1_000, 0), 500);
                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                gateauAnniversaire.setAllBillesActive();
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteViewTest.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
                    failedAttempts = 0;

                    addPierres(1);
                }
                else if (gateauAnniversaire.getNumberBilles() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                else if (gateauAnniversaire.getNumberBilles() < randNumOiseau)
                {
                    validusAnimated.validusPlaySound("Sounds/onglet_1_5/onglet_1_5 - Validus - Tu'es trompe manque des billes planche.mp3");
                }
                else if (gateauAnniversaire.getNumberBilles() > randNumOiseau)
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
            if (gateauAnniversaire.getNumberBilles() < randNumOiseau)
            {
                bougieRectification = sacDeBougies.getBilleAndRemove();
                bougieRectification.setVisible(true);
                gateauAnniversaire.addBilleAndOrganize(bougieRectification);
                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (gateauAnniversaire.getNumberBilles() > randNumOiseau)
            {
                bougieRectification = gateauAnniversaire.getLastBille();
                gateauAnniversaire.removeBille(bougieRectification);
                sacDeBougies.addBilleToReserve(bougieRectification);

                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (gateauAnniversaire.getNumberBilles() == randNumOiseau)
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

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(randNumOiseau);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
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
                    styleTest = calculetteViewTest.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteViewTest.deux_bouton.getStyle();
                    break;
                case 3:
                    styleTest = calculetteViewTest.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteViewTest.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteViewTest.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteViewTest.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteViewTest.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteViewTest.huit_bouton.getStyle();
                    break;

                case 9:
                    styleTest = calculetteViewTest.neuf_bouton.getStyle();
                    break;
                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(randNumOiseau);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteViewTest.textDisplay(randNumOiseau);
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
            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1_000, 1_000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

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

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteViewTest.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteViewTest.textRemove();

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
            nbInput = String.valueOf(randNumOiseau);
            afterCorrection = true;
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
            gateauAnniversaire.setAllBillesActive();
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
            calculetteViewTest.textRemove();

            isAllActive = true;

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
                calculetteViewTest.textRemove();

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

//    private class EtapeAddBille extends MyTimer.TaskEtape
//    {
//        private EtapeAddBille(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cptBille);
//            int posX = MyConstants.SCREENWIDTH / 2;
//            int posY = (int) planche1.getHeight() / 2;
//
//            planche1.addBilleAndOrganize(bille);
//            cptBille++;
//
//            uneMain.moveTo(50, posX, posY, null, 1_000);
//
//
//            MyPoint buttonPosition = calculetteViewTest.buttonPosition(currrentBillesNumber);
//
//            posX = buttonPosition.x;
//            posY = buttonPosition.y;
//
//            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);
//
//            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
//
//
//        }
//    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (sacDeBougies.contains(screenX, reversedScreenY) && sacDeBougies.isActive()) /*si bille part de la reserve*/
        {
            UneBougie bougieAdded = sacDeBougies.getBilleAndRemove();
            bougieAdded.setVisible(true);
            objectTouched = bougieAdded;
            bougieAdded.setActive(true);

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
                UneBougie bougieAux = (UneBougie) objectTouched;
                bougieAux.touchUp(allGateaux);
                bougiesList.add(bougieAux);
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

//    public ArrayList<UneBille> autoFillPlanche()
//    {
//        int firstPositionBilleX = (reserveBilles.getPosition().x + reserveBilles.largeurBille / 4);
//        int firstPositionBilleY = (reserveBilles.getPosition().y + reserveBilles.largeurBille);
//
////        billesList = new ArrayList<>();
//
//        for (int i = 0; i < oiseauxList.size() + 1; i++)
//        {
//            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, reserveBilles.largeurBille);
//            billesList.add(billeAdded);
//            myCorrectionAndPauseGeneral.addElements(billeAdded);
//
//            allDrawables.add(billeAdded);
//            billeAdded.setVisible(false);
//        }
//        return billesList;
//    }


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
//            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (float) ((MyConstants.SCREENWIDTH / 12) * (396.0f / 500.0f)), (float) (MyConstants.SCREENWIDTH / 12) * (500.0f / 396.0f));
//            allDrawables.add(unOiseau);
//            oiseauxList.add(unOiseau);
//        }
//        return oiseauxList;
//    }

}