package com.evalutel.primval_desktop.onglets.chapitre1;


import com.badlogic.gdx.Game;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.EcrinDiamantView;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyCorrectionAndPauseGeneral;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;

import java.util.ArrayList;
import java.util.TimerTask;


public class ScreenEx1_2 extends ScreenOnglet
{
    ScreeenBackgroundImage bgScreenEx1_2;
    int posX, posY;
    int failedAttempts;
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;
    private UneBille billeRectification;
    private UnePlancheNew planche1;
    private int randNumOiseau;
    private int cptOiseau;

    DatabaseDesktop dataBase;


    String consigneExercice;


    boolean isInCorrection = false;


    public ScreenEx1_2(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 2, true);

        this.dataBase = dataBase;

        bgScreenEx1_2 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_2);

        reserveBilles = new ReserveBilles(10 * MyConstants.SCREENWIDTH / 11, 9 * MyConstants.SCREENHEIGHT / 11, largeurBille, largeurBille);
        reserveBilles.largeurBille = largeurBille;
        allDrawables.add(reserveBilles);
        myCorrectionAndPauseGeneral.addElements(reserveBilles);


        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
        allCorrigibles.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);


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
        consigneExercice = "Faire correspondre des billes à des oiseaux, de 1 à 9";

        resultatExercice = new UnResultat("Primval", 1, 2, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);

        int noteMax = db.getHighestNote(1, 2);

        String noteMaxObtenue = noteMax + "/9";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, noteMaxObtenue, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);


        getNumberOiseauxArList();

        timer.schedule(new PresentationExercice(2000), 100);
    }


    public ArrayList getNumberOiseauxArList()
    {
        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;
        oiseauxList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (float) ((MyConstants.SCREENWIDTH / 12) * (396.0f / 500.0f)), (float) (MyConstants.SCREENWIDTH / 12) * (500.0f / 396.0f));
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

    private class PresentationExercice extends MyTimer.TaskEtape
    {
        private PresentationExercice(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Place autant de billes que d'oiseaux que tu vois et demande à Mademoiselle Validus si c'est juste pour avoir un diamant.");

            MyTimer.TaskEtape nextEtape = new EtapeInstruction(2000, 0);

            metrologue.metrologuePlaySound("Sounds/Metrologue/Place autant de billes.mp3", nextEtape);

//            //timer.schedule(new EtapeInstruction(2000), 1000);
            reserveBilles.setActive(false);
        }
    }

    private class EtapeInstruction extends MyTimer.TaskEtape
    {
        private EtapeInstruction(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            int[] numOiseauArray = MyMath.genereTabAleatoire(9);

            MyMath.melangeTab(numOiseauArray);

            randNumOiseau = numOiseauArray[questionCourante];

            timer.schedule(new DisplayOiseaux(1000), 0);
        }
    }

    private class DisplayOiseaux extends MyTimer.TaskEtape
    {
        private DisplayOiseaux(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            DisplayOiseaux nextEtape = new DisplayOiseaux(0);

            reserveBilles.setActive(true);


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
                timer.schedule(new EtapeAttendreValidus(1000), 100);
            }
        }
    }


    private class EtapeAttendreValidus extends MyTimer.TaskEtape
    {
        private EtapeAttendreValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            failedAttempts = 0;
            validusAnimated.isActive = true;
            validusAnimated.etapeCorrection = new CheckValidus(0);
        }
    }

    private class CheckValidus extends MyTimer.TaskEtape
    {
        private CheckValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (planche1.getNumberBilles() == randNumOiseau)
            {
//                validusAnimated.isActif = false;
//                activiteView.addTextActivite("C'est bien continue " /*+ questionCourante*/);
//                timer.schedule(new EtapeNextQuestion(1000), 500);
//                MyTimer.TaskEtape nextEtape = new EtapeInstruction(2000, 500);
                MyTimer.TaskEtape nextEtape = new EtapeNextQuestion(1000, 500);

                if (questionCourante != 9)
                {

                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - C'est bien continue.mp3", nextEtape);
                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                planche1.SetAllBillesActive();
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    isInCorrection = !isInCorrection;

                    validusAnimated.isActive = false;
                    planche1.SetAllBillesInactive();
                    reserveBilles.setActive(false);
                    failedAttempts = 0;
//                    activiteView.addTextActivite("Voici la correction");

                    MyTimer.TaskEtape nextEtape = new EtapeRectification1(1000, 500);

                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", nextEtape);

                    addPierres(1);

//                    timer.schedule(new EtapeRectification1(1000), 500);
                }
                else
                {
//                    activiteView.addTextActivite("Tu t'es trompé essaie encore.");
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }

    private class EtapeRectification1 extends MyTimer.TaskEtape
    {
        private EtapeRectification1(long durMillis)
        {
            super(durMillis);
        }

        private EtapeRectification1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (planche1.getNumberBilles() < randNumOiseau)
            {
                uneMain.setVisible(true);
                timer.schedule(new MoveMainToReserve1(500), 500);
            }
            else if (planche1.getNumberBilles() > randNumOiseau)
            {
                uneMain.setVisible(true);
                timer.schedule(new MoveMainBackToPlanche(500), 500);
            }
            else
            {
                isInCorrection = !isInCorrection;
                myCorrectionAndPauseGeneral.correctionStop();

                reserveBilles.setActive(true);

                timer.schedule(new EtapeNextQuestion(500), 500);
                uneMain.setVisible(false);
            }
        }
    }

    private class MoveMainToReserve1 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
            int posY = (int) posYf;

            MyTimer.TaskEtape nextEtape = new DisplayBilleReserve(500);
            uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 500);
        }
    }

    private class DisplayBilleReserve extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            billeRectification = reserveBilles.getBilleAndRemove();
            billeRectification.setPositionCenter(posX, posY);
            billeRectification.setVisible(true);

            MyTimer.TaskEtape nextEtape = new EtapeDragBille(500);
            timer.schedule(nextEtape, 500);
            uneMain.imageDown();
        }
    }

    private class EtapeDragBille extends MyTimer.TaskEtape
    {
        private EtapeDragBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddBille(500);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 1000, 1f / 6f);
            uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
        }
    }

    private class EtapeAddBille extends MyTimer.TaskEtape
    {
        private EtapeAddBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            planche1.addBilleAndOrganize(billeRectification);

            uneMain.imageUp();

            timer.schedule(new EtapeRectification1(1000), 500);
        }
    }

    private class MoveMainBackToPlanche extends MyTimer.TaskEtape
    {
        private MoveMainBackToPlanche(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            billeRectification = planche1.getLastBille();
            planche1.removeBille(billeRectification);
            uneMain.setVisible(true);

            float posX = billeRectification.getPosition().x + (int) (billeRectification.animationWidth / 2);
            float posY = billeRectification.getPosition().y + (int) (billeRectification.animationWidth / 2);

            MyTimer.TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);
        }
    }

    private class MoveBilleOutOfPlanche extends MyTimer.TaskEtape
    {
        private MoveBilleOutOfPlanche(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = MyConstants.SCREENHEIGHT / 5;
            int posY = MyConstants.SCREENWIDTH / 8;

            //uneMain.moveTo(durationMillis, posX, posY, null, 1000);
            uneMain.cliqueTo(durationMillis, posX, posY, null, 500);

            MyTimer.TaskEtape nextEtape = new LastOne(500);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 1000, 1f / 6f);
        }
    }

    private class LastOne extends MyTimer.TaskEtape
    {
        private LastOne(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = MyConstants.SCREENHEIGHT / 5;
            int posY = MyConstants.SCREENWIDTH / 8;

            reserveBilles.addBilleToReserve(billeRectification);

            MyTimer.TaskEtape nextEtape = new EtapeRectification1(500);
            uneMain.moveTo(50, posX, posY, nextEtape, 500);

            if (planche1.getNumberBilles() == randNumOiseau)
            {
                //uneMain.setVisible(false);
            }
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
            if (questionCourante == 9)
            {

                new FinOnglet(1000, 500);

                // fin exercice
                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1000L;

                ecrinDiamantView.getDiamantCount();


                timer.cancel();
            }
            else
            {
//                MyTimer.TaskEtape nextEtape =  new EtapeInstruction(2000, 500);
                timer.schedule(new EtapeInstruction(1000, 500), 500);
            }
        }
    }
}