package com.evalutel.primval_desktop;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.ui_tools.MyImageButton;

import java.util.ArrayList;


public class ScreenEx1_2 extends ScreenOnglet
{

    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    MyImageButton myButtonValidus;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_2;

    boolean isVisible = true;
    boolean isActive = false;

    Metronome metronome;
    EcrinDiamantView ecrinDiamantView;

    String strDiamantCount;
    int diamantCount;

    boolean state = false;
    private int randNumOiseau;
    private int cptOiseau, cptBille = 0;
    int posX, posY;
    int firstPositionOiseauX, firstPositionOiseauY;
    int failedAttempts;
    UneBille billeMovedOut;

    EnonceView enonceView;


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
        reserveBilles.setActive(true);
        allDrawables.add(reserveBilles);


        planche1 = new UnePlancheNew(screenWidth / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);

        for (int i = 0; i < 9; i++)
        {
            UneBille bille = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille);

            reserveBilles.addBilleToReserve(bille);
            allDrawables.add(bille);
            objectTouchedList.add(bille);
            bille.setVisible(false);

        }

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        billesList = new ArrayList<>();

        float enonceWidth = (screenWidth / 4) * 3;

        String numExercice = "1-2";
        String consigneExercice = "Faire correspondre des billes a des oiseaux, de 1 a 9";

        enonceView = new EnonceView(stage, 50, 2000, enonceWidth, numExercice, consigneExercice);
        allDrawables.add(enonceView);

        myButtonValidus = new MyImageButton(stage, "Images/vo00000.png", 300, 300);

        myButtonValidus.setPosition(0, screenHeight / 7);

        allDrawables.add(myButtonValidus);



        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);

        strDiamantCount = diamantCount + "/9";

        ecrinDiamantView = new EcrinDiamantView(stage, 69 * 3, "0/0", strDiamantCount);
        allDrawables.add(ecrinDiamantView);


        timer.schedule(new PresentationExercice(2000), 100);

        getNumberOiseauxArList();


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
            enonceView.addTextEnonce("Place autant de billes que d'oiseaux que tu vois et demande a Mademoiselle Validus si c'est juste pour avoir un diamant.");
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

                //TODO: n'a pas l'air de fonctionner - turn touchables object inactives

                myButtonValidus.addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y)
                    {
                        CheckValidus();
                    }
                });

                reserveBilles.setActive(false);
                planche1.SetAllBillesInactive();
                ClickListener myButtonValidusListener = myButtonValidus.getClickListener();
                myButtonValidus.removeListener(myButtonValidusListener);

                UnOiseau oiseau = oiseauxList.get(cptOiseau);

                if (cptOiseau > 5)
                {
                    posY = 5 * screenHeight / 11;
                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * (cptOiseau - 6);
                }
                else
                {
                    posY = 7 * screenHeight / 10;
                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
                }
                oiseau.animateImage(500, true, posX, posY, nextEtape, 20);
                cptOiseau++;
            }
            else if (cptOiseau > randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau - 1);
                int posX = screenWidth * 2;
                int posY = screenHeight * 2;

                oiseau.animateImage(500, true, posX, posY, nextEtape, 20);
                cptOiseau--;
            }
            else
            {

            }

            //TODO: n'a pas l'air de fonctionner - turn touchables object active

            reserveBilles.setActive(true);
            planche1.SetAllBillesActive();

            myButtonValidus.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    CheckValidus();
                }
            });
        }
    }


    public void CheckValidus()
    {
        if (planche1.getNumberBilles() == randNumOiseau)
        {
            enonceView.addTextEnonce("C'est bien continue " + questionCourante);

            timer.schedule(new EtapeNextQuestion(1000), 500);

            failedAttempts = 0;
            diamantCount++;
            ecrinDiamantView.addTextEcrin(diamantCount + "/9");

        }
        else
        {
            enonceView.addTextEnonce("Tu t'es trompe essaie encore.");
            failedAttempts++;

            if (failedAttempts == 2)

            {
                failedAttempts = 0;
                enonceView.addTextEnonce("voici la correction");

                timer.schedule(new EtapeRectification1(1000), 500);

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

                timer.schedule(new MoveMainToReserve1(1000), 500);
            }
            else if (planche1.getNumberBilles() > randNumOiseau)
            {

                uneMain.setVisible(true);

                UneBille bille = planche1.getLastBille();
                planche1.removeBilleToReserve(bille);

                timer.schedule(new MoveMainBackToPlanche(1000), 500);
            }
            else
            {
                timer.schedule(new EtapeNextQuestion(1000), 500);
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
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
                int posY = (int) posYf;

                TaskEtape nextEtape = new DisplayBilleReserve(500);
                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1000);
            }
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
            float posXmain = reserveBilles.currentPositionX /*+ reserveBilles.getWidth() / 2*/;
            float posYf = reserveBilles.currentPositionY /*+ reserveBilles.getHeight() / 2*/;
            int posX = (int) posXmain;
            int posY = (int) posYf;

//            UneBille bille = reserveBilles.getBilleAndRemove();
//            bille.setPositionCenter(posX, posY);
//            bille.setVisible(true);

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
            UneBille bille = reserveBilles.getBilleAndRemove();
            bille.setVisible(true);
            bille.setPosition(reserveBilles.currentPositionX, reserveBilles.currentPositionY);
            int posX = screenWidth / 2;
            int posY = (int) planche1.getHeight() / 2;

            TaskEtape nextEtape = new EtapeAddBille(1000);

            bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
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
            UneBille bille = reserveBilles.getBilleAndRemove();
            int posX = screenWidth / 2;
            int posY = (int) planche1.getHeight() / 2;

            planche1.addBilleAndOrganize(bille);
            cptBille++;

            uneMain.moveTo(50, posX, posY, null, 1000);
            uneMain.setVisible(false);

            timer.schedule(new EtapeRectification1(1000), 500);

//            TaskEtape nextEtape = new MoveMainToReserve1(1000);
//            uneMain.moveTo(50, posX, posY, nextEtape, 1000);

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
            UneBille bille = planche1.getLastBille();

            uneMain.setVisible(true);

            float posX = bille.getPosition().x + (int) (bille.animationWidth / 2);
            float posY = bille.getPosition().y + (int) (bille.animationWidth / 2);

            TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
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
            UneBille bille = planche1.getLastBille();

            billeMovedOut = planche1.getLastBille();

            int posX = 600;
            int posY = 400;

            uneMain.cliqueTo(durationMillis, posX, posY, null, 500);

            uneMain.moveTo(durationMillis, posX, posY, null, 1000);

            TaskEtape nextEtape = new LastOne(500);

            bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
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

            TaskEtape nextEtape = new EtapeRectification1(1000);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);

            billeMovedOut.setVisible(false);
            uneMain.setVisible(false);
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

                /*
                if (planche1.contains(screenX, screenHeight - screenY))
                {
                    cptBille++;
                    System.out.println("cptbille :" + cptBille);
                }
                else
                {
                    objectTouched.setPosition(firstPositionX, firstPositionY);
                    if (billeAux.plancheNew != null)
                    {
                        if (billeAux.plancheNew.shouldReturnToReserve)
                        {
                            billeAux.setPosition(100000, 100000);
                            allDrawables.remove(billeAux);
                            billeAux.plancheNew = null;
                        }
                        else
                        {
                            planche1.addBilleAndOrganize(billeAux);
                        }
                    }
                    else
                    {
                        allDrawables.remove(billeAux);
                        billeAux.setPosition(100000, 100000);
                    }
                }*/
            }
        }
        objectTouched = null;
        return false;
    }
}