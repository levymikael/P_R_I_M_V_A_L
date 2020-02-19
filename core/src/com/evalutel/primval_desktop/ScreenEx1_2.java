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

    private ArrayList<Integer> numberOiseauxList;

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
    private int rand_int, randNumOiseau;
    private int cptOiseau, cptBille = 0;
    int posX, posY;
    int firstPositionOiseauX, firstPositionOiseauY;
    int failedAttempts;


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


        myButtonValidus.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                CheckValidus();
            }
        });

        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);

        strDiamantCount = diamantCount + "/9";

        ecrinDiamantView = new EcrinDiamantView(stage, 69 * 3, "0/0", strDiamantCount);
        allDrawables.add(ecrinDiamantView);


        getNumberOiseauxArList();

        timer.schedule(new PresentationExercice(2000), 100);

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
        }
    }


//    private class EtapeAddOiseau extends TaskEtape
//    {
//        private EtapeAddOiseau(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptOiseau < oiseauxList.size())
//            {
//                UnOiseau oiseau = oiseauxList.get(cptOiseau);
//
//                if (cptOiseau > 5)
//                {
//                    posY = 5 * screenHeight / 11;
//                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * (cptOiseau - 6);
//                }
//                else
//                {
//                    posY = 7 * screenHeight / 10;
//                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
//                }
//                oiseau.animateImage(1000, true, posX, posY, null, 500);
//                cptOiseau++;
//            }
//        }
//    }


//    private class EtapeDragBille extends TaskEtape
//    {
//        private EtapeDragBille(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cptBille);
//            bille.setVisible(true);
//            int posX = screenWidth / 2;
//            int posY = (int) planche1.getHeight() / 2;
//
////            TaskEtape nextEtape = new EtapeAddBille(1000);
//            TaskEtape nextEtape2 = new EtapeAddOiseau(1000);
//
//            if (cptBille == 0)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//
//                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 0);
//            }
//            else if (cptBille == 1)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 0);
//            }
//            else if (cptBille == 2)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
//            }
//            else if (cptBille == 3)
//            {
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
//            }
//        }
//    }

    public void CheckValidus()
    {
        if (planche1.getNumberBilles() == randNumOiseau)
        {
            enonceView.addTextEnonce("C'est bien continue " + questionCourante);

            //Remise diamant

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

                getCorrection();


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


    public void CorrectionRemoveBille(int diff2Correct)
    {

        System.out.println(billesList);
        System.out.println(diff2Correct);

        for (int i = 0; i < diff2Correct; i++)
        {

            UneBille bille2Remove = billesList.get(billesList.size() - i-1);

            bille2Remove.setPosition(screenWidth + 1000, screenHeight + 1000);
            planche1.removeBille(bille2Remove);

        }

        timer.schedule(new EtapeNextQuestion(1000), 500);
    }

    public void autoFillPlanche(int diff2Correct)
    {
        int firstPositionBilleX = (planche1.getPosition().x + planche1.getPosition().x / 2);
        int firstPositionBilleY = (planche1.getPosition().y + planche1.getPosition().y / 2);


        for (int i = 0; i < diff2Correct; i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, reserveBilles.largeurBille, reserveBilles.largeurBille);

            allDrawables.add(billeAdded);
            billeAdded.setVisible(true);
            planche1.addBilleAndOrganize(billeAdded);
        }
        timer.schedule(new EtapeNextQuestion(1000), 500);
    }


    public void getCorrection()

    {
        if (planche1.getNumberBilles() < randNumOiseau)
        {

            int diff2Correct = randNumOiseau - planche1.getNumberBilles();

            autoFillPlanche(diff2Correct);

        }
        else if (planche1.getNumberBilles() > randNumOiseau)
        {
            int diff2Correct = planche1.getNumberBilles() - randNumOiseau;

            CorrectionRemoveBille(diff2Correct);
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
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 200);
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
        }

        numberOiseauxList = new ArrayList<>();

        return oiseauxList;
    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button)
//    {
//        int reversedScreenY = screenHeight - screenY;
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
//            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (screenHeight - screenY - objectTouched.getHeight() / 2));
//        }
//        return true;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button)
//    {
//        if (objectTouched != null)
//        {
//            if (objectTouched instanceof UneMain)
//            {
//                UneMain mainAux = (UneMain) objectTouched;
//                mainAux.touchUp(planche1, screenX, screenHeight - screenY);
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
        int reversedScreenY = screenHeight - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            System.out.println("clickedOnReserve");
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille, reserveBilles.largeurBille);
            objectTouchedList.add(billeAdded);
            allDrawables.add(billeAdded);
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
                    firstPositionX = objectTouched.getPosition().x;
                    firstPositionY = objectTouched.getPosition().y;

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