package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_5 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    private UneBille billeRectification;

    protected CalculetteViewTest calculetteViewTest;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_1;

    int cptOiseau, cptBille = 0;
    private int randNumOiseau;

    TextButton.TextButtonStyle styleTest;

    int posX, posY;

    int[] numOiseauArray;

    int failedAttempts, currrentBillesNumber = 0;

    boolean isInCorrection = false;

    boolean afterCorrection, isAllActive, touchValidate = false;

    DatabaseDesktop dataBase;

    String consigneExercice, nbInput;

    Label currentLabel;
    Drawable drawableAux;


    public ScreenEx1_5(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 5, true);

        this.dataBase = dataBase;

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        reserveBilles = new ReserveBilles(10 * MyConstants.SCREENWIDTH / 11, 9 * MyConstants.SCREENHEIGHT / 11, largeurBille, largeurBille);
        reserveBilles.largeurBille = largeurBille;
        reserveBilles.isActive();
        reserveBilles.setActive(false);
        allDrawables.add(reserveBilles);
        myCorrectionAndPauseGeneral.addElements(reserveBilles);
        allCorrigibles.add(reserveBilles);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
        allCorrigibles.add(planche1);

        for (int i = 0; i < 9; i++)
        {
            UneBille bille = new UneBille(reserveBilles.currentPositionX, reserveBilles.currentPositionY, reserveBilles.largeurBille);

            reserveBilles.addBilleToReserve(bille);
            allDrawables.add(bille);
            objectTouchedList.add(bille);
            bille.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bille);
            allCorrigibles.add(bille);
        }

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        billesList = new ArrayList<>();

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Compter des oiseaux et taper leur nombre";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, "", "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        calculetteViewTest = new CalculetteViewTest(stage);
        allDrawables.add(calculetteViewTest);
        calculetteViewTest.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteViewTest);


        oiseauxList = getNumberOiseauxArList();

        numOiseauArray = MyMath.genereTabAleatoire(9);

        resultatExercice = new UnResultat("Primval", 1, 5, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);

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

            MyTimer.TaskEtape nextEtape = new EtapeInstruction(3000, 2000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.0 - Ecriture des chiffres de 1 a 9.mp3", nextEtape);
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

            activiteView.setTextActivite("1. Place autant de billes que d'oiseaux que tu vois tape ce nombre au clavier puis valide");

            timer.schedule(new DisplayOiseaux(1_000, 0), 0);
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
                    posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 6);
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
                timer.schedule(new InputClavier(1000), 100);
            }
            reserveBilles.setActive(true);
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
//                MyTimer.TaskEtape nextEtape = new EtapeNextQuestion(1000, 500);
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
            if (planche1.getNumberBilles() == randNumOiseau)
            {
                int ok = 5;
                ok++;
                if (value == planche1.getNumberBilles())
                {
                    if (questionCourante != 8)
                    {
                        validusAnimated.validusPlaySound("Sounds/Validus/Validus - C'est bien continue.mp3", new EtapeNextQuestion(500, 0));
                    }
                    else
                    {
                        timer.schedule(new Fin(1_000, 0), 500);
                        validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
                    }
                    validusAnimated.isActive = false;
                    addDiamonds(1);
                    planche1.setAllBillesActive();
                }
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteViewTest.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2000));
                    failedAttempts = 0;

                    addPierres(1);
                }
                else if (planche1.getNumberBilles() == 0)
                {
                    activiteView.setTextActivite("Tu n'as pas mis de bille sur la planche");
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");

                }
                else if (planche1.getNumberBilles() != value)
                {
                    activiteView.setTextActivite("Il n'y a pas autant de billes sur la planche que d'oiseaux");
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");

                }
                else
                {
                    activiteView.setTextActivite("");

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
            timer.schedule(new AddBilles(1000), 1000);
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
            if (planche1.getNumberBilles() < randNumOiseau)
            {
                billeRectification = reserveBilles.getBilleAndRemove();
                billeRectification.setVisible(true);
                planche1.addBilleAndOrganize(billeRectification);
                timer.schedule(new EtapeRectification(500), 500);


            }
            else if (planche1.getNumberBilles() > randNumOiseau)
            {
                billeRectification = planche1.getLastBille();
                planche1.removeBille(billeRectification);
                reserveBilles.addBilleToReserve(billeRectification);

                timer.schedule(new EtapeRectification(500), 500);

            }
            else if (planche1.getNumberBilles() == randNumOiseau)
            {
                timer.schedule(new MoveMainToCalculette(1000), 1000);
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

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
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

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

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

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

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

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

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
            planche1.setAllBillesActive();
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
                MyTimer.TaskEtape finOnglet = new FinOnglet(1000, 500);
                finOnglet.run();

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1000L;

                ecrinDiamantView.getDiamantCount();

                timer.cancel();
            }
            else
            {
                timer.schedule(new EtapeInstruction(1000, 500), 500);
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
            MyTimer.TaskEtape finOnglet = new FinOnglet(1000, 500);
            finOnglet.run();

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1000L;

            timer.cancel();
        }
    }

    private class EtapeAddBille extends MyTimer.TaskEtape
    {
        private EtapeAddBille(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            planche1.addBilleAndOrganize(bille);
            cptBille++;

            uneMain.moveTo(50, posX, posY, null, 1000);


            MyPoint buttonPosition = calculetteViewTest.buttonPosition(currrentBillesNumber);

            posX = buttonPosition.x;
            posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);


        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = reserveBilles.getBilleAndRemove();
            billeAdded.setVisible(true);
            objectTouched = billeAdded;
            billeAdded.setActive(true);

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


    public ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;

        for (int i = 0; i < 9; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (float) ((MyConstants.SCREENWIDTH / 12) * (396.0f / 500.0f)), (float) (MyConstants.SCREENWIDTH / 12) * (500.0f / 396.0f));
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
        }
        return oiseauxList;
    }

}