package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.MyTouchInterface;
import com.evalutel.primval_desktop.ReserveBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_3 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    protected CalculetteViewTest calculetteViewTest;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_1;

    int cptOiseau, cptBille = 0;

    DatabaseDesktop dataBase;

    String consigneExercice;

    Label currentLabel;


    public ScreenEx1_3(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 3, false);

        this.dataBase = dataBase;

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        reserveBilles = new ReserveBilles(10 * MyConstants.SCREENWIDTH / 11, 9 * MyConstants.SCREENHEIGHT / 11, largeurBille, largeurBille);
        reserveBilles.largeurBille = largeurBille;
        reserveBilles.isActive();
        reserveBilles.setActive(false);
        allDrawables.add(reserveBilles);
        myCorrectionAndPauseGeneral.addElements(reserveBilles);
        allCorrigibles.add(reserveBilles);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
        allCorrigibles.add(planche1);

        validusAnimated.setVisible(false);

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Écriture des chiffres de 1 à 9";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, "", "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        calculetteViewTest = new CalculetteViewTest(stage, MyConstants.SCREENWIDTH - (MyConstants.SCREENWIDTH / 6), 0);
        allDrawables.add(calculetteViewTest);

        billesList = autoFillPlanche();

        resultatExercice = new UnResultat("Primval", 1, 3, 0, consigneExercice, 0, 0, dateTest, 0, 0, 0, 123);

        timer.schedule(new PresentationOnglet(3000), 1000);
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

            MyTimer.TaskEtape nextEtape = new OnVaContinuer(3000, 2500);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.0 - Ecriture des chiffres de 1 a 9.mp3", nextEtape);
        }
    }

    private class OnVaContinuer extends MyTimer.TaskEtape
    {
        private OnVaContinuer(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new EtapeAddFirstOiseau(2000, 1000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.1 - On va continuer.mp3", nextEtape);
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

            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 0)
            {
                oiseau.animateImage(1000, true, posX, posY, new JeVoisUnOIseau(2000), 1000, 1f / 6f);
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

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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
            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            UneBille bille = billesList.get(cptBille);
            bille.setPositionCenter(posX, posY);
            bille.setVisible(true);
            bille.setActive(false);

            MyTimer.TaskEtape nextEtape = new EtapeDragFirstBille(1000);
            timer.schedule(nextEtape, 1000);
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
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.3 - Je saisis une bille du sac.mp3");

                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 2500, 1f / 6f);

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

            MyTimer.TaskEtape nextEtape = new MoveMainTo1Calculette(2000, 1000);

            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }

    private class MoveMainTo1Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo1Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button1Position = calculetteViewTest.buttonPosition(1);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo1Validate(3000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            uneMain.imageDown();

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.4 - Je tape un.mp3");
        }
    }

    private class MoveMainTo1Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo1Validate(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);
            uneMain.imageUp();

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new EtapeAddSecondOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            uneMain.imageDown();
            uneMain.imageUp();
            currentLabel = activiteView.addTextActivite("1 -  ");
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
            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 1)
            {
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve2(1000), 0, 1f / 6f);
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.5 - je vois maintenant 2 oiseaux.mp3");
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve2 extends MyTimer.TaskEtape
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

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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
            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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

                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.6 - je depose encore une bille.mp3", nextEtape);
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

            MyTimer.TaskEtape nextEtape = new MoveMainTo2Calculette(1000, 500);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }

    private class MoveMainTo2Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo2Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button2Position = calculetteViewTest.buttonPosition(2);

            float posX = button2Position.x;
            float posY = button2Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo2Validate(2000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.7 - je tape 2.mp3");
        }
    }

    private class MoveMainTo2Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo2Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddThirdOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "2 -  ");

            currentLabel.setText(newStrBuilder);
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
            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (MyConstants.SCREENWIDTH / 6) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 2)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.8 - Puis je valide.mp3");

                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve3(2000), 1500, 1f / 6f);
            }
            cptOiseau++;
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

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
                int posY = (int) posYf;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve3(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);

                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.9 - Maintenant ils sont 3 oiseaux.mp3");
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
            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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

            MyTimer.TaskEtape nextEtape = new MoveMainTo3Calculette(1000, 500);
            uneMain.moveTo(50, posX, posY, nextEtape, 1000);
        }
    }

    private class MoveMainTo3Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo3Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button3Position = calculetteViewTest.buttonPosition(3);

            float posX = button3Position.x;
            float posY = button3Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo3Validate(2000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.10 - je tape 3.mp3");
        }
    }

    private class MoveMainTo3Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo3Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddFourthOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "3 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddFourthOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddFourthOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;


            if (cptOiseau == 3)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.11 - Une de plus ils sont 4.mp3");
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve4(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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
            float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
            float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
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

                timer.schedule(new MoveMainTo4Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo4Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo4Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button4Position = calculetteViewTest.buttonPosition(4);

            float posX = button4Position.x;
            float posY = button4Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo4Validate(2000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.12 - je tape 4.mp3");
        }
    }

    private class MoveMainTo4Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo4Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddFifthOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "4 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddFifthOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddFifthOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 4)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.13 - Encore un ils sont 5.mp3");
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve5(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve5 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve5(long durMillis, long delay)
        {
            super(durMillis, delay);
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve5(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve5 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve5(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeDragFifthBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragFifthBille extends MyTimer.TaskEtape
    {
        private EtapeDragFifthBille(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddFifthBille(2000, 1000);

            if (cptBille == 4)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddFifthBille extends MyTimer.TaskEtape
    {
        private EtapeAddFifthBille(long durMillis, long delay)
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

            if (cptBille == 5)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainTo5Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo5Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo5Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button5Position = calculetteViewTest.buttonPosition(5);

            float posX = button5Position.x;
            float posY = button5Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo5Validate(2000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
        }
    }

    private class MoveMainTo5Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo5Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSixthOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "5 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddSixthOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddSixthOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 7 * MyConstants.SCREENHEIGHT / 10;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;

            if (cptOiseau == 5)
            {
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve6(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.14 - Maintenant ils occupennt toute la branche ils sont 6.mp3");
        }
    }

    private class MoveMainToReserve6 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve6(long durMillis, long delay)
        {
            super(durMillis, delay);
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve6(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve6 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve6(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeDragSixthBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragSixthBille extends MyTimer.TaskEtape
    {
        private EtapeDragSixthBille(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSixthBille(2000, 1000);

            if (cptBille == 5)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddSixthBille extends MyTimer.TaskEtape
    {
        private EtapeAddSixthBille(long durMillis, long delay)
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

            if (cptBille == 6)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainTo6Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo6Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo6Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button6Position = calculetteViewTest.buttonPosition(6);

            float posX = button6Position.x;
            float posY = button6Position.y;


            MyTimer.TaskEtape nextEtape = new MoveMainTo6Validate(2000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
        }
    }

    private class MoveMainTo6Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo6Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSeventhOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "6 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddSeventhOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddSeventhOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 3);

            if (cptOiseau == 6)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.15 - un oiseau tout seul sur la seconde.mp3");
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve7(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve7 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve7(long durMillis, long delay)
        {
            super(durMillis, delay);
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve7(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve7 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve7(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeDragSeventhBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragSeventhBille extends MyTimer.TaskEtape
    {
        private EtapeDragSeventhBille(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSeventhBille(2000, 1000);

            if (cptBille == 6)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddSeventhBille extends MyTimer.TaskEtape
    {
        private EtapeAddSeventhBille(long durMillis, long delay)
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

            if (cptBille == 7)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainTo7Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo7Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo7Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button7Position = calculetteViewTest.buttonPosition(7);

            float posX = button7Position.x;
            float posY = button7Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo7Validate(2500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
        }
    }

    private class MoveMainTo7Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo7Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddEighthOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "7 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddEighthOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddEighthOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 3);

            if (cptOiseau == 7)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.16 - L'oiseau a un compagnon ils sont 8.mp3");
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve8(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve8 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve8(long durMillis, long delay)
        {
            super(durMillis, delay);
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve8(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve8 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve8(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeDragEighthBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragEighthBille extends MyTimer.TaskEtape
    {
        private EtapeDragEighthBille(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddEighthBille(2000, 1000);

            if (cptBille == 7)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddEighthBille extends MyTimer.TaskEtape
    {
        private EtapeAddEighthBille(long durMillis, long delay)
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

            if (cptBille == 8)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainTo8Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo8Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo8Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button8Position = calculetteViewTest.buttonPosition(8);

            float posX = button8Position.x;
            float posY = button8Position.y;


            MyTimer.TaskEtape nextEtape = new MoveMainTo8Validate(2500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
        }
    }

    private class MoveMainTo8Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo8Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddNinthOiseau(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "8 -  ");

            currentLabel.setText(newStrBuilder);
        }
    }

    private class EtapeAddNinthOiseau extends MyTimer.TaskEtape
    {
        private EtapeAddNinthOiseau(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UnOiseau oiseau = oiseauxList.get(cptOiseau);
            myCorrectionAndPauseGeneral.addElements(oiseau);
            int posY = 5 * MyConstants.SCREENHEIGHT / 11;
            int posX = (2 * MyConstants.SCREENWIDTH / 9) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 3);

            if (cptOiseau == 8)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.17 - Il n'y a plus de place pour se poser.mp3");
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve9(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
        }
    }

    private class MoveMainToReserve9 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve9(long durMillis, long delay)
        {
            super(durMillis, delay);
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve9(500);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 2000);
            }
        }
    }

    private class DisplayBilleReserve9 extends MyTimer.TaskEtape
    {
        private DisplayBilleReserve9(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeDragNinthBille(1000);
            timer.schedule(nextEtape, 1000);
            uneMain.imageDown();
        }
    }

    private class EtapeDragNinthBille extends MyTimer.TaskEtape
    {
        private EtapeDragNinthBille(long durMillis)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddNinthBille(2000, 1000);

            if (cptBille == 8)
            {
                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 1500, 1f / 6f);
                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
            }
        }
    }

    private class EtapeAddNinthBille extends MyTimer.TaskEtape
    {
        private EtapeAddNinthBille(long durMillis, long delay)
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

            if (cptBille == 9)
            {
                uneMain.moveTo(50, posX, posY, null, 1000);

                timer.schedule(new MoveMainTo9Calculette(2000, 1000), 2000);
            }
        }
    }

    private class MoveMainTo9Calculette extends MyTimer.TaskEtape
    {
        private MoveMainTo9Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint button9Position = calculetteViewTest.buttonPosition(9);

            float posX = button9Position.x;
            float posY = button9Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo9Validate(2500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
        }
    }

    private class MoveMainTo9Validate extends MyTimer.TaskEtape
    {
        private MoveMainTo9Validate(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new Fin(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 2000);
            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "9 -  ");

            currentLabel.setText(newStrBuilder);
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        boolean isReserveActif = reserveBilles.isActive();
        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille);
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
            }
        }
        objectTouched = null;
        return false;
    }
}