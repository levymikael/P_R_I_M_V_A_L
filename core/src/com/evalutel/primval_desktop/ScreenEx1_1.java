package com.evalutel.primval_desktop;

import java.util.ArrayList;
import java.util.Random;


public class ScreenEx1_1 extends ScreenOnglet
{

    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    private UnePlancheNew planche1, planche2, planche3, touchedPlanche;
    private EnonceViewEx1_1 enonceViewEx1_1;
    ScreeenBackgroundImage bgScreenEx1_1;

    boolean isVisible = true;
    boolean isActive = false;

    Validus validus;
    Metronome metronome;

    boolean state = false;
    int rand_int;
    int cptOiseau, cptBille = 0;
    int cpt = -1;


    public ScreenEx1_1()
    {
        super();

        int largeurBille = 100;
        int largeurPlanche = largeurBille * 4;

        bgScreenEx1_1 = new ScreeenBackgroundImage();
        bgScreenEx1_1.ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        reserveBilles = new ReserveBilles(screenWidth - 300, screenHeight - 300, 200, 200);
        reserveBilles.largeurBille = largeurBille;
        reserveBilles.setActive(false);
        allDrawables.add(reserveBilles);

        planche1 = new UnePlancheNew(screenWidth / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
//        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        float enonceWidth = (screenWidth / 4) * 3;

        String numExercice = "1-1";
        String consigneExercice = " Les nombres de 1 a 9. Badix, Metrologue et Validus";

        enonceViewEx1_1 = new EnonceViewEx1_1(50, 2000, enonceWidth, numExercice, consigneExercice);
        allDrawables.add(enonceViewEx1_1);

        validus = new Validus(0, screenHeight / 7, 300, 300);
        allDrawables.add(validus);

        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);

        billesList = autoFillPlanche();

        //  timer.schedule(new EtapeAddOiseau(2000), 1);
        timer.schedule(new EtapeDragBille(2000), 1);
    }

//    private class EtapeAddOiseau extends TaskEtape
//    {
//        private EtapeAddOiseau(long durMillis) { super(durMillis); }
//
//        @Override
//        public void run()
//        {
//            if (cptOiseau < oiseauxList.size())
//            {
//                UnOiseau oiseau = oiseauxList.get(cptOiseau);
//                int posY;
//                int posX;
//
//                if (cptOiseau < 5)
//                {
//                    posY = 7 * screenHeight / 10;
//                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
//                } else
//                {
//                    cpt++;
//                    posY = 5 * screenHeight / 11;
//                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cpt;
//                }
//
//                TaskEtape nextEtape = new EtapeAddOiseau(1000);
//                oiseau.animateImage(durationMillis, true, posX, posY, nextEtape, 500);
//                cptOiseau++;
//            } else
//            {
//                System.out.print("landing over");
//                timer.schedule(new EtapeDragBille(2000), 1);
//
//            }
//        }
//    }


    private class MoveMain extends TaskEtape
    {
        private MoveMain(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);
                UneBille bille = billesList.get(cptBille);
                float posX = bille.currentPositionX;
                float posY = bille.currentPositionY - (uneMain.getHeight() * 2);

                TaskEtape nextEtape = new EtapeDragBille(500);
                uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
                timer.schedule(new MoveMain(0), 1);
            }
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
            if (cptBille < billesList.size())
            {
                UneBille bille = billesList.get(cptBille);
                bille.setVisible(true);
                int posX = screenWidth / 2;
                int posY = (int) planche1.getHeight() / 2;
                uneMain.setVisible(true);

                float posYMain = planche1.getHeight() - uneMain.getHeight();

                TaskEtape nextEtape = new EtapeAddBille(1000);
                bille.animateImage(durationMillis, true, posX, posY, nextEtape, 500);
                uneMain.cliqueTo(durationMillis, posX, (int) posYMain, null, 1000);
            }
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
            UneBille bille = billesList.get(cptBille);
            float posX = reserveBilles.currentPositionX + bille.getWidth();
            float posY = reserveBilles.currentPositionY - (3 * bille.getWidth());

            TaskEtape nextEtape = new MoveMain(1000);

            planche1.addBilleAndOrganize(bille);
            cptBille++;

            if (cptBille < billesList.size())
            {
                uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            } else
            {
                uneMain.moveTo(durationMillis, (int) posX, (int) posY, null, 1000);
            }
        }
    }


    public ArrayList<UneBille> autoFillPlanche()
    {
        int firstPositionBilleX = (reserveBilles.getPosition().x + reserveBilles.largeurBille / 4);
        int firstPositionBilleY = (reserveBilles.getPosition().y + reserveBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size(); i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, reserveBilles.largeurBille, reserveBilles.largeurBille);
            billesList.add(billeAdded);

            allDrawables.add(billeAdded);
            billeAdded.setVisible(false);
        }

        return billesList;
    }


    public ArrayList getNumberOiseauxArList()
    {
        Random rand = new Random();

        int oiseauMaxQuantity = 10;
        rand_int = rand.nextInt(oiseauMaxQuantity) + 1;

        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = screenWidth + 200;
        int firstPositionOiseauY = screenHeight + 200;

//        int secondPositionOiseauX = 200;
//        int secondPositionOiseauY = 700;
        for (int i = 0; i < 8; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 200);
            //unOiseau.animateImage(4000, false, (300 / (1 + i)), (300 / (1 + i)));
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
        }

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
//                    firstPositionX = objectTouched.getPosition().x;
//                    firstPositionY = objectTouched.getPosition().y;
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
            System.out.println("clickedOnContainer");
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille, reserveBilles.largeurBille);
            objectTouchedList.add(billeAdded);
            allDrawables.add(billeAdded);
            objectTouched = billeAdded;
//
        } else /*si bille part de la planche*/
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
            //AnimationImageNew animationImageNewAux = (AnimationImageNew) objectTouched;
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;
                billeAux.touchUp(allPlanches, screenX, screenHeight - screenY);
//
//                else /*si bille pas deposee dans planche*/
//                    {
//                    objectTouched.setPosition(firstPositionX, firstPositionY);
//                    if (billeAux.plancheNew != null) {
//                        if (billeAux.plancheNew.shouldReturnToReserve)
//                        {
//                            billeAux.setPosition(100000, 100000);
//                            allDrawables.remove(billeAux);
//                            billeAux.plancheNew = null;
//                        }
//                        else {
//                            planche1.addBilleAndOrganize(billeAux);
//                            planche2.addBilleAndOrganize(billeAux);
//                            planche3.addBilleAndOrganize(billeAux);
//                        }
//                    } else {
//                        allDrawables.remove(billeAux);
//                        billeAux.setPosition(100000, 100000);
//                    }
//                }
            }

        }
        objectTouched = null;
        return false;
    }
}

