package com.evalutel.primval_desktop;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScreenEx1_1 extends ScreenOnglet
{


    /*


    str0 = "Bonjour,\n" +
                "Je suis le professeur Metrologue, on va faire un jeu amusant qui s’appelle Badix.";
        str1 = "Tu veux jouer ?";
        str2 = "Voici la règle du jeu: \n";
        str3 = " Je vois un oiseau\n";
        str4 = "Je saisis une bille du sac et je la depose sur le plateau\n";
        str5 = "Je vois maintenant 2 oiseaux \n";
        str6 = " Je depose encore une bille\n";
        str7 = "Tiens ! Encore un oiseau \n";
        str8 = "Mince,  je crois que je me suis trompe, je clique sur Mademoiselle Validus pour savoir si c’est juste.\n";
        str9 = "Validus: Non, non tu t’es trompée \n";
        str10 = "Metronome : Pour corriger mon erreur, je retire une bille de la planche puis je demande a Mademoiselle Validus./n";
        str11 = "Validus:  Youpi ! Tu as gagne un diamant\t.\n";

     */

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

    int i;
    EnonceView enonceView;

    boolean etapeOiseau = true;

    ScheduledExecutorService exec;


//    String str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11;


    public ScreenEx1_1()
    {
        super();

        int largeurBille = 200;
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


        enonceView = new EnonceView(stage, 50, 2000, enonceWidth, numExercice, consigneExercice);
        //enonceViewEx1_1 = new EnonceViewEx1_1(50, 2000, enonceWidth, numExercice, consigneExercice);
        allDrawables.add(enonceView);

        validus = new Validus(0, screenHeight / 7, 300, 300);
        allDrawables.add(validus);

        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);

        billesList = autoFillPlanche();




         exec = Executors.newSingleThreadScheduledExecutor();


        timer.schedule(new PresentationMetrologue(2000), 1000);
//        timer.schedule(new EtapeAddOiseau(2000), 1);
        //timer.schedule(new MoveMainToReserve(1500), 0);


    }

    private class PresentationMetrologue extends TaskEtape
    {
        private PresentationMetrologue(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String str0 = "Bonjour,\n" +
                    "Je suis le professeur Metrologue, on va faire un jeu amusant qui s’appelle Badix.\n" +
                    "Tu veux jouer ?";
            enonceView.addTextEnonce(str0);

            timer.schedule(new VoiciLaRegleDuJeu(2000), 2000);
        }
    }

    private class VoiciLaRegleDuJeu extends TaskEtape
    {
        private VoiciLaRegleDuJeu(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String str = "Voici la règle du jeu:";
            enonceView.addTextEnonce(str);

            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            int posY = 7 * screenHeight / 10;
            int posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;

//            TaskEtape nextEtape = new EtapeAddOiseau(1000);

            oiseau.animateImage(durationMillis, true, posX, posY, null, 1000);
            timer.schedule(new JeVoisUnOIseau(2000), 1000);
            cptOiseau++;
        }
    }

    private class EtapeAddOiseau extends TaskEtape
    {
        private EtapeAddOiseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
//            if (cptOiseau < oiseauxList.size())
//            {

//                UnOiseau oiseau = oiseauxList.get(cptOiseau);
//                int posY;
//                int posX;

//                if (cptOiseau < 5)
////                {
//                posY = 7 * screenHeight / 10;
//                posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
////                }
//                else
//                {
//                    cpt++;
//                    posY = 5 * screenHeight / 11;
//                    posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cpt;
////                }

//                TaskEtape nextEtape = new EtapeAddOiseau(1000);

            if (cptOiseau == 0)
            {
                timer.schedule(new JeVoisUnOIseau(2000), 1000);
            }
//                oiseau.animateImage(durationMillis, true, posX, posY, nextEtape, 500);
            cptOiseau++;
//            }
//            else
//            {
//                timer.schedule(new MoveMainToReserve(2000), 1);
//
//            }
        }
    }

    private class JeVoisUnOIseau extends TaskEtape
    {
        private JeVoisUnOIseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String str = " Je vois un oiseau";
            enonceView.addTextEnonce(str);

            timer.schedule(new MoveMainToReserve1(2000), 1);
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
                //UneBille bille = billesList.get(cptBille);

                int posX = reserveBilles.currentPositionX;
                float posYf = reserveBilles.currentPositionY;
                int posY = (int) posYf;

                TaskEtape nextEtape = new EtapeDragBille(500);
                uneMain.moveTo(durationMillis, posX, posY - (int) reserveBilles.getHeight(), nextEtape, 1000);

                UnOiseau oiseau = oiseauxList.get(cptOiseau);
                int posXOiseau = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
                int posYOiseau = 7 * screenHeight / 10;


                TaskEtape nextEtape2 = new EtapeAddOiseau(1000);
                oiseau.animateImage(durationMillis, true, posXOiseau, posYOiseau, nextEtape2, 1000);

            }

        }
    }

    private class MoveMainToReserve2 extends TaskEtape
    {
        private MoveMainToReserve2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                int posX = reserveBilles.currentPositionX;
                float posYf = reserveBilles.currentPositionY;
                int posY = (int) posYf;

                TaskEtape nextEtape = new EtapeDragBille(500);

                UnOiseau oiseau = oiseauxList.get(2);
                int posXOiseau = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;
                int posYOiseau = 7 * screenHeight / 10;


                    enonceView.addTextEnonce("Je depose encore une bille");

                    oiseau.animateImage(durationMillis, true, posXOiseau, posYOiseau, nextEtape, 1000);
                    uneMain.moveTo(durationMillis, posX, posY - (int) reserveBilles.getHeight(), nextEtape, 1000);
//                }
            }
        }
    }

    private class MoveMainToReserve3 extends TaskEtape
    {
        private MoveMainToReserve3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                int posX = reserveBilles.currentPositionX;
                float posYf = reserveBilles.currentPositionY;
                int posY = (int) posYf;

                TaskEtape nextEtape = new EtapeDragBille(500);

                uneMain.moveTo(durationMillis, posX, posY - (int) reserveBilles.getHeight(), nextEtape, 1000);
            }

        }
    }

    private class MoveMainToReserve4 extends TaskEtape
    {
        private MoveMainToReserve4(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                int posX = reserveBilles.currentPositionX;
                float posYf = reserveBilles.currentPositionY;
                int posY = (int) posYf;

                TaskEtape nextEtape = new EtapeDragBille(500);
                uneMain.moveTo(durationMillis, posX, posY - (int) reserveBilles.getHeight(), nextEtape, 1000);
            }

        }
    }


    private class MoveMainToValidus extends TaskEtape
    {
        private MoveMainToValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int posX = validus.currentPositionX;
            int posY = validus.currentPositionY;

            uneMain.moveTo(durationMillis, posX, posY, null, 1000);
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

                if (cptBille == 0)
                {
                    enonceView.addTextEnonce("Je saisis une bille du sac et je la depose sur le plateau ");
                    TaskEtape nextEtape = new EtapeAddBille(1000);

                    bille.animateImage(durationMillis, true, posX, posY, nextEtape, 0);
                    uneMain.cliqueTo(durationMillis, posX, (int) posYMain, null, 0);
                }
                else if (cptBille == 1)
                {
                    TaskEtape nextEtape2 = new EtapeAddBille(1000);
                    enonceView.addTextEnonce("Je vois maintenant 2 oiseaux ");

                    cptOiseau++;
                    bille.animateImage(durationMillis, true, posX, posY, nextEtape2, 0);
                    uneMain.cliqueTo(durationMillis, posX, (int) posYMain, null, 0);
                }
                else if (cptBille == 2)
                {
                    TaskEtape nextEtape = new EtapeAddBille(1000);
                    enonceView.addTextEnonce("Tiens ! Encore un oiseau");

                    bille.animateImage(durationMillis, true, posX, posY, nextEtape, 0);
                    uneMain.cliqueTo(durationMillis, posX, (int) posYMain, null, 0);
                }
                else if (cptBille == 3)
                {
                    TaskEtape nextEtape = new EtapeAddBille(1000);
                    bille.animateImage(durationMillis, true, posX, posY, nextEtape, 0);

                    enonceView.addTextEnonce("Mince,  je crois que je me suis trompe, je clique sur Mademoiselle Validus pour savoir si c’est juste.");

                    TaskEtape nextEtape2 = new MoveMainToValidus(1000);


                    exec.scheduleAtFixedRate(nextEtape2, 0, 1000, TimeUnit.MILLISECONDS);

//                    uneMain.cliqueTo(durationMillis, posX, (int) posYMain, nextEtape2, 0);
                }

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
            if (cptBille < billesList.size())
            {
                UneBille bille = billesList.get(cptBille);
                int posX = screenWidth / 2;
                int posY = (int) planche1.getHeight() / 2;

                TaskEtape nextEtape = null;

                if (cptBille < 1)
                {
                    nextEtape = new MoveMainToReserve1(1000);
                }
                else if (cptBille < 2)
                {
                    nextEtape = new MoveMainToReserve2(1000);
                }
                else if (cptBille < 3)
                {
                    nextEtape = new MoveMainToReserve3(1000);
                }
                else if (cptBille < 4)
                {
                    nextEtape = new MoveMainToReserve4(1000);
                }
                planche1.addBilleAndOrganize(bille);
                cptBille++;
                uneMain.moveTo(50, posX, posY, nextEtape, 1000);

            }
            else
            {
                UneBille bille = billesList.get(cptBille);
                float posX = reserveBilles.currentPositionX + bille.getWidth();
                float posY = reserveBilles.currentPositionY - (3 * bille.getWidth());

                planche1.addBilleAndOrganize(bille);
                uneMain.moveTo(durationMillis, (int) posX, (int) posY, null, 1000);
            }
        }
    }


    public ArrayList<UneBille> autoFillPlanche()
    {
        int firstPositionBilleX = (reserveBilles.getPosition().x + reserveBilles.largeurBille / 4);
        int firstPositionBilleY = (reserveBilles.getPosition().y + reserveBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size() + 1; i++)
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
//        Random rand = new Random();
//        int oiseauMaxQuantity = 10;
//        rand_int = rand.nextInt(oiseauMaxQuantity) + 1;

        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = screenWidth + 200;
        int firstPositionOiseauY = screenHeight + 200;

//        int secondPositionOiseauX = 200;
//        int secondPositionOiseauY = 700;
        for (int i = 0; i < 3; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 200);
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

