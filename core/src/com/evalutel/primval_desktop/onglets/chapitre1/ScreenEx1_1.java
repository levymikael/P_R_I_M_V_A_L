package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;

import java.util.ArrayList;


public class ScreenEx1_1 extends ScreenOnglet implements InputProcessor
{

    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_1;

//    boolean isVisible = true;
//    boolean isActive = false;

    int cptOiseau, cptBille = 0;

    ActiviteView activiteView;

    DatabaseDesktop dataBase;

    String consigneExercice;


    public ScreenEx1_1(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 1, false);

        this.dataBase = dataBase;

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        reserveBilles = new ReserveBilles(screenWidth - 300, screenHeight - 300, largeurBille, largeurBille);
        reserveBilles.largeurBille = largeurBille;
        reserveBilles.isActive();
        reserveBilles.setActive(false);
        allDrawables.add(reserveBilles);
        myPauseGeneral.addElements(reserveBilles);

        planche1 = new UnePlancheNew(screenWidth / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
//        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
        myPauseGeneral.addElements(planche1);

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Les nombres de 1 à 9 Badix, Métrologue et Validus.";


        float activiteWidth = (screenWidth / 4) * 3;


        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, "", "enonce");
        allDrawables.add(activiteView);
        myPauseGeneral.addElements(activiteView);


        billesList = autoFillPlanche();

        resultatExercice = new UnResultat("Primval", 1, 1, 0, consigneExercice, 0, 0, dateTest, 0, 0, 0, 123);

        timer.schedule(new PresentationMetrologue(2000), 1000);

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
            uneMain.imageDown();
            activiteView.addTextActivite("Bonjour,");
            activiteView.addTextActivite("Je suis le professeur Metrologue, on va faire un jeu amusant qui s'appelle Badix.");
            activiteView.addTextActivite("Tu veux jouer ?");
            metrologue.metrologuePlaySound("Sounds/Metrologue/Bonjour je suis le prof.mp3");

            timer.schedule(new VoiciLaRegleDuJeu(2000), 7000);
        }
    }

    private class VoiciLaRegleDuJeu extends MyTimer.TaskEtape
    {
        private VoiciLaRegleDuJeu(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Voici la règle du jeu:");
            metrologue.metrologuePlaySound("Sounds/Metrologue/Voici la regle du jeu.mp3");

            timer.schedule(new EtapeAddOiseau(1000), 1500);
        }
    }

    private class EtapeAddOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddOiseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myPauseGeneral.addElements(oiseau);
            int posY = 5 * screenHeight / 11;
            int posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;

            if (cptOiseau == 0)
            {
                oiseau.animateImage(1000, true, posX, posY, new JeVoisUnOIseau(2000), 1000, 1f / 6f);
            }
            else if (cptOiseau == 2)
            {
                activiteView.addTextActivite("Tiens ! Encore un oiseau");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Tiens encore un oiseau.mp3");

                oiseau.animateImage(1000, true, posX, posY, null, 1500, 1f / 6f);
            }
            else
            {
                oiseau.animateImage(1000, true, posX, posY, null, 1500, 1f / 6f);
                activiteView.addTextActivite("Je vois maintenant 2 oiseaux");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Je vois maintenant.mp3");
            }
            cptOiseau++;

        }
    }

    private class JeVoisUnOIseau extends MyTimer.TaskEtape
    {
        private JeVoisUnOIseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            activiteView.addTextActivite("Je vois un oiseau");
            metrologue.metrologuePlaySound("Sounds/Metrologue/je vois un oiseau.mp3");

            timer.schedule(new MoveMainToReserve1(2000), 1500);
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
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve(500);
                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1500);

                if (cptOiseau == 2)
                {
                    activiteView.addTextActivite("Je dépose encore une bille.");
                    metrologue.metrologuePlaySound("Sounds/Metrologue/je depose encore une bille.mp3");
                }
            }
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

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragBille(1000);
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
            UneBille bille = billesList.get(cptBille);
            bille.setVisible(true);
            int posX = screenWidth / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddBille(1500);
            MyTimer.TaskEtape nextEtape2 = new EtapeAddOiseau(2000);

            if (cptBille == 0)
            {
                activiteView.addTextActivite("Je saisis une bille du sac et je la dépose sur le plateau ");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Je saisis une bille du sac.mp3");

                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 2500, 1f / 6f);

                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 2500);
            }
            else if (cptBille == 1)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 0);
            }
            else if (cptBille == 2)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
            else if (cptBille == 3)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
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
            UneBille bille = billesList.get(cptBille);
            int posX = screenWidth / 2;
            int posY = (int) planche1.getHeight() / 2;

            planche1.addBilleAndOrganize(bille);
            cptBille++;

            if (cptBille == 4)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                activiteView.addTextActivite("Mince, je crois que je me suis trompé, je clique sur Mademoiselle Validus pour savoir si c'est juste.");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Mince je crois que.mp3");
                timer.schedule(new MoveMainToValidus(1000), 6000);
            }
            else
            {
                MyTimer.TaskEtape nextEtape = new MoveMainToReserve1(1000);
                uneMain.moveTo(50, posX, posY, nextEtape, 1000);
            }
        }
    }


    private class MoveMainToValidus extends MyTimer.TaskEtape
    {
        private MoveMainToValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            float posX = validusAnimated.getPosition().x + validusAnimated.getWidth() / 2;
            float posY = validusAnimated.getPosition().y + validusAnimated.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new ClickToValidus(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);
        }
    }

    private class ClickToValidus extends MyTimer.TaskEtape
    {
        private ClickToValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            float posX = validusAnimated.getPosition().x + validusAnimated.getWidth() / 2;
            float posY = validusAnimated.getPosition().y + validusAnimated.getHeight() / 2;

            if (billesList.size() == 4)
            {
                activiteView.addTextActivite("Validus: Non, non tu t'es trompée ");
                validusAnimated.validusPlaySound("Sounds/Validus/non non tu tes trompe.mp3");

                MyTimer.TaskEtape nextEtape = new MoveMainBackToPlanche(1000);

                uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            }
            else if (billesList.size() == 3)
            {
                MyTimer.TaskEtape nextEtape = new FinOnglet(1000);
                uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
                activiteView.addTextActivite("Youpi ! Tu as gagné un diamant.");
                validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
            }
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
            UneBille bille = planche1.getBille(3);

            float posX = bille.getPosition().x + (int) (bille.animationWidth / 2);
            float posY = bille.getPosition().y + (int) (bille.animationWidth / 2);

            MyTimer.TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 6000);
            uneMain.imageDown();

            activiteView.addTextActivite("Metrologue : Pour corriger mon erreur, je retire une bille de la planche puis je demande à Mademoiselle Validus.");
            metrologue.metrologuePlaySound("Sounds/Metrologue/Pour corriger mon erreur.mp3");
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
            UneBille bille = billesList.get(3);

            int posX = 600;
            int posY = 400;

            uneMain.cliqueTo(1500, posX, posY, null, 500);


            MyTimer.TaskEtape nextEtape = new LastOne(500);

            bille.animateImage(1500, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500, 1f / 6f);
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
            UneBille bille = billesList.get(3);

            int posX = 600;
            int posY = 400;

            bille.setVisible(false);

            billesList.remove(bille);

            MyTimer.TaskEtape nextEtape = new MoveMainToValidus(1000);
            uneMain.moveTo(500, posX, posY, nextEtape, 1000);


        }
    }


    private class FinOnglet extends MyTimer.TaskEtape
    {
        private FinOnglet(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            timer.cancel();


        }
    }


    public ArrayList<UneBille> autoFillPlanche()
    {
        int firstPositionBilleX = (reserveBilles.getPosition().x + reserveBilles.largeurBille / 4);
        int firstPositionBilleY = (reserveBilles.getPosition().y + reserveBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size() + 1; i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, reserveBilles.largeurBille);
            billesList.add(billeAdded);

            allDrawables.add(billeAdded);
            billeAdded.setVisible(false);
        }
        return billesList;
    }


    public ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = screenWidth + 200;
        int firstPositionOiseauY = screenHeight + 200;

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
//    public boolean TouchUp(int screenX, int screenY, int pointer, int button)
//    {
//        if (objectTouched != null)
//        {
//            if (objectTouched instanceof UneMain)
//            {
//                UneMain mainAux = (UneMain) objectTouched;
//                mainAux.TouchUp(planche1, screenX, screenHeight - screenY);
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

        boolean isReserveActif = reserveBilles.isActive();
        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille);
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
            if ((objectTouched instanceof UneBille) /*&& (objectTouched != null)*/)
            {
                UneBille billeAux = (UneBille) objectTouched;

                if (billeAux != null)
                {
                    billeAux.touchUp(allPlanches/*, screenX, screenHeight - screenY*/);
                }
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