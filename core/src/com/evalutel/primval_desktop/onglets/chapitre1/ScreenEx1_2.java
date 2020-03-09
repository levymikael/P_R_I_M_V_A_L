package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.EcrinDiamantView;
import com.evalutel.primval_desktop.EnonceView;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.Metronome;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;

import java.io.*;
//import com.sun.speech.freetts;

import java.util.ArrayList;


public class ScreenEx1_2 extends ScreenOnglet
{
    ScreeenBackgroundImage bgScreenEx1_2;
    Metronome metronome;
    EcrinDiamantView ecrinDiamantView;
    boolean state = false;
    int posX, posY;
    int firstPositionOiseauX, firstPositionOiseauY;
    int failedAttempts;
    ActiviteView activiteView;
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;
    private UneBille billeRectification;
    private UnePlancheNew planche1;
    private int randNumOiseau;
    private int cptOiseau;


    public ScreenEx1_2()
    {
        super();

        int largeurBille = 200;
        int largeurPlanche = largeurBille * 4;

        bgScreenEx1_2 = new ScreeenBackgroundImage();
        bgScreenEx1_2.ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_2);

        reserveBilles = new ReserveBilles(screenWidth - 300, screenHeight - 300, 200, 200);
        reserveBilles.largeurBille = largeurBille;
        allDrawables.add(reserveBilles);

        planche1 = new UnePlancheNew(screenWidth / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);

        for (int i = 0; i < 9; i++)
        {
            UneBille bille = new UneBille(reserveBilles.currentPositionX, reserveBilles.currentPositionY, reserveBilles.largeurBille);

            reserveBilles.addBilleToReserve(bille);
            allDrawables.add(bille);
            objectTouchedList.add(bille);
            bille.setVisible(false);
        }

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        billesList = new ArrayList<>();

        float activiteWidth = (screenWidth / 4) * 3;

        String numExercice = "1-2";
        String consigneExercice = "Faire correspondre des billes a des oiseaux, de 1 a 9";
        String exDansChapitre = "3/9";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, exDansChapitre);
        allDrawables.add(activiteView);

        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);

        ecrinDiamantView = new EcrinDiamantView(stage, myButtonValidus.getWidth(), 9);
        ecrinDiamantView.updateText();
        allDrawables.add(ecrinDiamantView);

        timer.schedule(new PresentationExercice(2000), 100);

        getNumberOiseauxArList();


    }


    public ArrayList getNumberOiseauxArList()
    {
        firstPositionOiseauX = screenWidth + 200;
        firstPositionOiseauY = screenHeight + 200;
        oiseauxList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 300);
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
        }

        return oiseauxList;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = screenHeight - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;


        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = reserveBilles.getBilleAndRemove();
            billeAdded.setVisible(true);
            objectTouched = billeAdded;
        }
        else /*si bille part de la planche*/
        {
            for (int i = 0; i < objectTouchedList.size(); i++)
            {
                MyTouchInterface objetAux = objectTouchedList.get(i);

                if (objetAux.isTouched(screenX, reversedScreenY))
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
        if (objectTouched != null)
        {
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (screenHeight - screenY - objectTouched.getHeight() / 2));
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (objectTouched != null)
        {
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;
                billeAux.touchUp(allPlanches, screenX, screenHeight - screenY);

                billesList.add(billeAux);

            }
        }
        objectTouched = null;
        return false;
    }

    private class PresentationExercice extends TaskEtape
    {
        private PresentationExercice(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Place autant de billes que d'oiseaux que tu vois et demande a Mademoiselle Validus si c'est juste pour avoir un diamant.");
            timer.schedule(new EtapeInstruction(2000), 1000);
        }
    }

    private class EtapeInstruction extends TaskEtape
    {
        private EtapeInstruction(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int[] numOiseauArray = MyMath.genereTabAleatoire(9);

            MyMath.melangeTab(numOiseauArray);

            randNumOiseau = numOiseauArray[questionCourante];

            timer.schedule(new Displayoiseaux(1000), 500);
        }
    }

    private class Displayoiseaux extends TaskEtape
    {
        private Displayoiseaux(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            Displayoiseaux nextEtape = new Displayoiseaux(0);

            if (cptOiseau < randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);

                if (cptOiseau > 5)
                {
                    posY = 5 * screenHeight / 11;
                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * (cptOiseau - 6);
                }
                else
                {
                    posY = 7 * screenHeight / 10;

                    if (cptOiseau < 3)
                    {
                        posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
                    }
                    else
                    {
                        posX = (3 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
                    }
                }
                oiseau.animateImage(500, true, posX, posY, null, 20);
                timer.schedule(nextEtape, 100);
                cptOiseau++;
            }
            else if (cptOiseau > randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau - 1);
                int posX = screenWidth * 2;
                int posY = screenHeight * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20);
                timer.schedule(nextEtape, 100);
                cptOiseau--;
            }
            else
            {
                myButtonValidus.etapeCorrection = new CheckValidus(0);
                myButtonValidus.setActive(true);
            }
        }
    }

    private class CheckValidus extends TaskEtape
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
                myButtonValidus.setActive(false);
                activiteView.addTextActivite("C'est bien continue " + questionCourante);

                timer.schedule(new EtapeNextQuestion(1000), 500);

                failedAttempts = 0;
                ecrinDiamantView.addDiamond(1);
            }
            else
            {
                activiteView.addTextActivite("Tu t'es trompe essaie encore.");
                failedAttempts++;

                if (failedAttempts == 2)
                {
                    myButtonValidus.setActive(false);
                    failedAttempts = 0;
                    ecrinDiamantView.addPierre(1);
                    activiteView.addTextActivite("Voici la correction");

                    timer.schedule(new EtapeRectification1(1000), 500);
                }
            }
        }
    }


    private class EtapeRectification1 extends TaskEtape
    {
        private EtapeRectification1(long durMillis)
        {
            super(durMillis);
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
                timer.schedule(new EtapeNextQuestion(500), 500);
                uneMain.setVisible(false);
            }
        }
    }

    private class MoveMainToReserve1 extends TaskEtape
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

            TaskEtape nextEtape = new DisplayBilleReserve(500);
            uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1000);
        }
    }

    private class DisplayBilleReserve extends TaskEtape
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

            TaskEtape nextEtape = new EtapeDragBille(1000);
            timer.schedule(nextEtape, 500);
            uneMain.imageDown();
        }
    }

    private class EtapeDragBille extends TaskEtape
    {
        private EtapeDragBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = screenWidth / 2;
            int posY = (int) planche1.getHeight() / 2;

            TaskEtape nextEtape = new EtapeAddBille(1000);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 500);
            uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
        }
    }

    private class EtapeAddBille extends TaskEtape
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

    private class MoveMainBackToPlanche extends TaskEtape
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

            TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);
            uneMain.imageDown();
        }
    }

    private class MoveBilleOutOfPlanche extends TaskEtape
    {
        private MoveBilleOutOfPlanche(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = 600;
            int posY = 400;

            uneMain.moveTo(durationMillis, posX, posY, null, 500);
            uneMain.cliqueTo(durationMillis, posX, posY, null, 500);

            TaskEtape nextEtape = new LastOne(500);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 500);
        }
    }

    private class LastOne extends TaskEtape
    {
        private LastOne(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = 600;
            int posY = 400;

            reserveBilles.addBilleToReserve(billeRectification);

            TaskEtape nextEtape = new EtapeRectification1(1000);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);

            if (planche1.getNumberBilles() == randNumOiseau)
            {
                uneMain.setVisible(false);
            }
        }
    }

    private class EtapeNextQuestion extends TaskEtape
    {
        private EtapeNextQuestion(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            questionCourante++;
            if (questionCourante == 9)
            {
                // fin exercice
            }
            else
            {
                timer.schedule(new EtapeInstruction(1000), 500);
            }
        }
    }
}