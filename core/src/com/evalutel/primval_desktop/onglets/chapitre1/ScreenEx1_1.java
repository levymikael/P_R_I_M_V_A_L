package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

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

//    ActiviteView activiteView;


    public ScreenEx1_1(final Game game, String ongletTitre)
    {
        super(game, 1, 1, false, 0);

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60, 9 * MyConstants.SCREENHEIGHT / 11, (float) (largeurBilleUnique * 1.5), (float) (largeurBilleUnique * 1.5));
        sacDeBilles.largeurBille = largeurBilleUnique;
        sacDeBilles.isActive();
        sacDeBilles.setActive(false);
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
//        allCorrigibles.add(sacDeBilles);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlancheUnique / 2, 0, largeurPlancheUnique, largeurBilleUnique);
//        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
//        allCorrigibles.add(planche1);


        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        activiteView = new ActiviteView(stage, xTableTitre, activiteWidth * 42 / 1626, activiteWidth, /*numExercice, consigneExercice, "",*/ "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label("", labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46);

        tableTitre.add(exoNumLabel)/*.align(Align.center).*/.width(MyConstants.SCREENWIDTH / 25).padLeft(MyConstants.SCREENWIDTH / 46);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22);

        stage.addActor(tableTitre);

        billesList = autoFillPlanche();

        resultatExercice = new UnResultat("Primval", 1, 1, 0, ongletTitre, 0, 0, dateTest, 0, 0, 0, 123);

        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre1(game));

//                game.dispose();
                Gdx.app.log("button click", "click!");

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1_000L;

                resultatExercice.setDuree(seconds);
                resultatExercice.setDate(endTime);

                if ((metrologue.isSpeaking) && (metrologue != null))
                {
                    metrologue.stopMusic();
                }
                else if ((validusAnimated.isSpeaking) && (validusAnimated != null))
                {
                    validusAnimated.stopMusic();
                }

                timer.cancel();
                AppSingleton appSingleton = AppSingleton.getInstance();
                MyDataBase db = appSingleton.myDataBase;

                db.insertResultat(resultatExercice);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                return true;
            }
        });

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
            uneMain.imageDown();
//            activiteView.addTextActivite("Bonjour,\nJe suis le professeur Metrologue, on va faire un jeu amusant qui s'appelle Badix.");
//            activiteView.addTextActivite("Tu veux jouer ?");

            MyTimer.TaskEtape nextEtape = new VoiciLaRegleDuJeu(3000, 2500);

            metrologue.metrologuePlaySound("Sounds/Metrologue/Bonjour je suis le prof.mp3", nextEtape);
        }
    }

    private class VoiciLaRegleDuJeu extends MyTimer.TaskEtape
    {
        private VoiciLaRegleDuJeu(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
//            activiteView.addTextActivite("Voici la règle du jeu:");
            MyTimer.TaskEtape nextEtape = new EtapeAddFirstOiseau(2000, 1000);

            metrologue.metrologuePlaySound("Sounds/Metrologue/Voici la regle du jeu.mp3", nextEtape);
        }
    }

    private class EtapeAddFirstOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddFirstOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 0)
            {
                oiseau.animateImage(1000, true, posX, posY, new JeVoisUnOIseau(2000), 1500, 1f / 6f);
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
//            activiteView.addTextActivite("Je vois un oiseau");

            MyTimer.TaskEtape nextEtape = new MoveMainToReserve1(2000, 500);

            metrologue.metrologuePlaySound("Sounds/Metrologue/je vois un oiseau.mp3", nextEtape);
        }
    }

    private class MoveMainToReserve1 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve1(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1000);
            }
        }
    }

    private class DisplayBilleReserve1 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragFirstBille(1_000);
            timer.schedule(nextEtape, 1_000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragFirstBille extends MyTimer.TaskEtape
    {
        private EtapeDragFirstBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            bille.setVisible(true);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddFirstBille(3000, 1000);

            if (cptBille == 0)
            {
                metrologue.metrologuePlaySound("Sounds/Metrologue/Je saisis une bille du sac.mp3");

                bille.animateImage(durationMillis, true, (posX - bille.getWidth() / 2f), (posY - bille.getWidth() / 2f), nextEtape, 2500, 1f / 6f);

                uneMain.cliqueTo(durationMillis, posX, posY, null, 2000);
            }
        }
    }


    private class EtapeAddFirstBille extends MyTimer.TaskEtape
    {
        private EtapeAddFirstBille(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSecondOiseau(2000, 1000);

            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }


    private class EtapeAddSecondOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddSecondOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 1)
            {
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve2(1000), 0, 1f / 6f);
                activiteView.addTextActivite("Je vois maintenant 2 oiseaux");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Je vois maintenant.mp3");
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve2 extends MyTimer.TaskEtape
    {
//        private MoveMainToReserve2(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

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

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve2(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1500);
            }
        }
    }

    private class DisplayBilleReserve2 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragSecondBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragSecondBille extends MyTimer.TaskEtape
    {
        private EtapeDragSecondBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            bille.setVisible(true);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddSecondBille(3000, 1000);
            if (cptBille == 1)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), null, 2000, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 1000);

//                activiteView.addTextActivite("Je dépose encore une bille.");
                metrologue.metrologuePlaySound("Sounds/Metrologue/je depose encore une bille.mp3", nextEtape);
            }
        }
    }

    private class EtapeAddSecondBille extends MyTimer.TaskEtape
    {
        private EtapeAddSecondBille(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddThirdOiseau(1000, 500);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }

    private class EtapeAddThirdOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddThirdOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 2)
            {
//                activiteView.addTextActivite("Tiens ! Encore un oiseau");
                metrologue.metrologuePlaySound("Sounds/Metrologue/Tiens encore un oiseau.mp3");

                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve3(2000), 1500, 1f / 6f);
            }
        }
    }

    private class MoveMainToReserve3 extends MyTimer.TaskEtape
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

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve3(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve3 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragThirdBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragThirdBille extends MyTimer.TaskEtape
    {
        private EtapeDragThirdBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            bille.setVisible(true);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddThirdBille(2500, 1000);

            if (cptBille == 2)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 2500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddThirdBille extends MyTimer.TaskEtape
    {
        private EtapeAddThirdBille(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new MoveMainToReserve4(1000, 500);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }

    private class MoveMainToReserve4 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve4(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cptBille < billesList.size())
            {
                uneMain.setVisible(true);

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve4(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve4 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve4(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragFourthBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragFourthBille extends MyTimer.TaskEtape
    {
        private EtapeDragFourthBille(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            bille.setVisible(true);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddFourthBille(2000, 1000);

            if (cptBille == 3)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddFourthBille extends MyTimer.TaskEtape
    {
        private EtapeAddFourthBille(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

//     /   private EtapeAddFourthBille(long durMillis)
//        {
//            super(durMillis);
//        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cptBille);
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            planche1.addBilleAndOrganize(bille);
            cptBille++;

            if (cptBille == 4)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainToValidus(1000), 2000);
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

            MyTimer.TaskEtape nextEtape = new ClickToValidus1(3000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 3500);

//            activiteView.addTextActivite("Mince, je crois que je me suis trompé, je clique sur Mademoiselle Validus pour savoir si c'est juste.");
            metrologue.metrologuePlaySound("Sounds/Metrologue/Mince je crois que.mp3");
        }
    }

    private class ClickToValidus1 extends MyTimer.TaskEtape
    {
        private ClickToValidus1(long durMillis)
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
//                activiteView.addTextActivite("Validus: Non, non tu t'es trompé.");
                validusAnimated.validusPlaySound("Sounds/Validus/non non tu tes trompe.mp3");

                MyTimer.TaskEtape nextEtape = new MoveMainBackToPlanche(500);

                uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);
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
            float posY = bille.getPosition().y + (int) (bille.animationWidth);

            MyTimer.TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 3000);
            uneMain.imageDown();

//            activiteView.addTextActivite("Metrologue : Pour corriger mon erreur, je retire une bille de la planche puis je demande à Mademoiselle Validus.");
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

            MyTimer.TaskEtape nextEtape = new MoveMainToValidus2(1000);
            uneMain.moveTo(500, posX, posY, nextEtape, 1000);
        }
    }

    private class MoveMainToValidus2 extends MyTimer.TaskEtape
    {
        private MoveMainToValidus2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            float posX = validusAnimated.getPosition().x + validusAnimated.getWidth() / 2;
            float posY = validusAnimated.getPosition().y + validusAnimated.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new ClickToValidus2(1500);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);

        }
    }

    private class ClickToValidus2 extends MyTimer.TaskEtape
    {
        private ClickToValidus2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            float posX = validusAnimated.getPosition().x + validusAnimated.getWidth() / 2;
            float posY = validusAnimated.getPosition().y + validusAnimated.getHeight() / 2;

            if /*(billesList.size() == 4)
            {
                activiteView.addTextActivite("Validus: Non, non tu t'es trompé.");
                validusAnimated.validusPlaySound("Sounds/Validus/non non tu tes trompe.mp3");

                MyTimer.TaskEtape nextEtape = new MoveMainBackToPlanche(1000);

                uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            }
            else if*/ (billesList.size() == 3)
            {
                MyTimer.TaskEtape nextEtape = new FinOnglet(1000, 1500);
                uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, null, 1000);
//                activiteView.addTextActivite("Youpi ! Tu as gagné un diamant.");
                validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3", nextEtape);
            }
        }
    }


    public ArrayList<UneBille> autoFillPlanche()
    {
        float firstPositionBilleX = (sacDeBilles.getPosition().x + sacDeBilles.largeurBille / 4);
        float firstPositionBilleY = (sacDeBilles.getPosition().y + sacDeBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size() + 1; i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, sacDeBilles.largeurBille);
            billesList.add(billeAdded);

            allDrawables.add(billeAdded);
            billeAdded.setVisible(false);

            myCorrectionAndPauseGeneral.addElements(billeAdded);

        }
        return billesList;
    }


    public ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        int firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        int firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;

        for (int i = 0; i < 3; i++)
        {
            int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, (float) ((MyConstants.SCREENWIDTH / 12) * (396.0f / 500.0f)), (float) (MyConstants.SCREENWIDTH / 12) * (500.0f / 396.0f));
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
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
            UneBille billeAdded = new UneBille(sacDeBilles.currentPositionX + (int) sacDeBilles.animationWidth / 2, sacDeBilles.currentPositionY + (int) sacDeBilles.animationHeight / 2, sacDeBilles.largeurBille);
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
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
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
                    billeAux.touchUp(allPlanches/*, screenX, MyConstants.SCREENHEIGHT - screenY*/);
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