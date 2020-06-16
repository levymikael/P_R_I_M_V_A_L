package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    Drawable drawableAux;


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

        calculetteViewTest = new CalculetteViewTest(stage, validusAnimated);
        allDrawables.add(calculetteViewTest);
        calculetteViewTest.setActive(false);

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

            MyTimer.TaskEtape nextEtape = new OnVaContinuer(3000, 2000);

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
            MyTimer.TaskEtape nextEtape = new MoveMainToReserve(2000, 500);

            metrologue.metrologuePlaySound("Sounds/Metrologue/je vois un oiseau.mp3", nextEtape);
        }
    }

    private class MoveMainToReserve extends MyTimer.TaskEtape
    {
        private MoveMainToReserve(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private MoveMainToReserve(long durMillis)
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

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve(1000);

                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1000);

                switch (cptOiseau)
                {
                    case 2:

                        metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.9 - Maintenant ils sont 3 oiseaux.mp3");
                        break;
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
            timer.schedule(nextEtape, 1000);
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
            int posX = MyConstants.SCREENWIDTH / 2;
            int posY = (int) planche1.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new EtapeAddBille(2000, 1000);

            bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 2500, 1f / 6f);
            uneMain.cliqueTo(durationMillis, posX, posY, null, 2000);

            switch (cptBille)
            {
                case 0:
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.3 - Je saisis une bille du sac.mp3");

                    break;

                case 1:

                    metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.6 - je depose encore une bille.mp3", nextEtape);
                    break;
            }
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo1Calculette(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.4 - Je tape un.mp3");
        }
    }

    private class ClickMainTo1Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo1Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            TextButton.TextButtonStyle styleTest = calculetteViewTest.un_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            calculetteViewTest.un_bouton.setStyle(styleTest);
            uneMain.setVisible(true);

            MyPoint button1Position = calculetteViewTest.buttonPosition(1);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo1Validate(1500, 1000);

            uneMain.cliqueTo(500, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textDisplay(1);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.un_bouton.getStyle();

            styleTest.up = drawableAux;

            calculetteViewTest.un_bouton.setStyle(styleTest);

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSecondOiseau(1500, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1500, true, posX, posY, new MoveMainToReserve(1500), 0, 1f / 6f);
                metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.5 - je vois maintenant 2 oiseaux.mp3");
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo2Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.7 - je tape 2.mp3");
        }
    }

    private class ClickMainTo2Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo2Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.deux_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button2Position = calculetteViewTest.buttonPosition(2);

            float posX = button2Position.x;
            float posY = button2Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo2Validate(1500, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);
            calculetteViewTest.textDisplay(2);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.deux_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate2(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate2 extends MyTimer.TaskEtape
    {
        private ClickOnValidate2(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddThirdOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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

                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(1500), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo3Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.10 - je tape 3.mp3");
        }
    }

    private class ClickMainTo3Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo3Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.trois_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(3);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo3Validate(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);
            calculetteViewTest.textDisplay(3);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.trois_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate3(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate3 extends MyTimer.TaskEtape
    {
        private ClickOnValidate3(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddFourthOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo4Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.12 - je tape 4.mp3");
        }
    }

    private class ClickMainTo4Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo4Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.quatre_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(4);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo4Validate(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            calculetteViewTest.textDisplay(4);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.quatre_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate4(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate4 extends MyTimer.TaskEtape
    {
        private ClickOnValidate4(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddFifthOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo5Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickMainTo5Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo5Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.cinq_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(5);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo5Validate(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            calculetteViewTest.textDisplay(5);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.cinq_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate5(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate5 extends MyTimer.TaskEtape
    {
        private ClickOnValidate5(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSixthOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;

            metrologue.metrologuePlaySound("Sounds/Onglet_1_3/Chap1Onglet3.14 - Maintenant ils occupennt toute la branche ils sont 6.mp3");
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo6Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickMainTo6Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo6Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.six_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(6);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo6Validate(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            calculetteViewTest.textDisplay(6);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.six_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate6(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate6 extends MyTimer.TaskEtape
    {
        private ClickOnValidate6(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddSeventhOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo7Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickMainTo7Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo7Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.sept_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(7);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo7Validate(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            calculetteViewTest.textDisplay(7);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.sept_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate7(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate7 extends MyTimer.TaskEtape
    {
        private ClickOnValidate7(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddEighthOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo8Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickMainTo8Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo8Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.huit_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(8);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo8Validate(1500, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
            calculetteViewTest.textDisplay(8);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.huit_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate8(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickOnValidate8 extends MyTimer.TaskEtape
    {
        private ClickOnValidate8(long durMillis, long delay)
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

            MyTimer.TaskEtape nextEtape = new EtapeAddNinthOiseau(1000, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteViewTest.textRemove();

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
                oiseau.animateImage(1000, true, posX, posY, new MoveMainToReserve(2000, 300), 1500, 1f / 6f);
            }
            cptOiseau++;
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

            MyTimer.TaskEtape nextEtape = new ClickMainTo9Calculette(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
        }
    }

    private class ClickMainTo9Calculette extends MyTimer.TaskEtape
    {
        private ClickMainTo9Calculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            TextButton.TextButtonStyle styleTest = calculetteViewTest.neuf_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint button1Position = calculetteViewTest.buttonPosition(9);

            float posX = button1Position.x;
            float posY = button1Position.y;

            MyTimer.TaskEtape nextEtape = new MoveMainTo9Validate(1500, 1000);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);
            calculetteViewTest.textDisplay(9);
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
            TextButton.TextButtonStyle styleTest = calculetteViewTest.neuf_bouton.getStyle();

            styleTest.up = drawableAux;

            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate9(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);
        }
    }

    private class ClickOnValidate9 extends MyTimer.TaskEtape
    {
        private ClickOnValidate9(long durMillis, long delay)
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

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1500);

            calculetteViewTest.textRemove();

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

            switch (cptBille)
            {
                case 1:
                    timer.schedule(new MoveMainTo1Calculette(2000, 1000), 2000);

                    break;

                case 2:

                    timer.schedule(new MoveMainTo2Calculette(2000, 1000), 2000);

                    break;

                case 3:

                    timer.schedule(new MoveMainTo3Calculette(2000, 1000), 2000);

                    break;

                case 4:

                    timer.schedule(new MoveMainTo4Calculette(2000, 1000), 2000);

                    break;

                case 5:

                    timer.schedule(new MoveMainTo5Calculette(2000, 1000), 2000);

                    break;

                case 6:

                    timer.schedule(new MoveMainTo6Calculette(2000, 1000), 2000);

                    break;

                case 7:

                    timer.schedule(new MoveMainTo7Calculette(2000, 1000), 2000);

                    break;

                case 8:

                    timer.schedule(new MoveMainTo8Calculette(2000, 1000), 2000);

                    break;

                case 9:

                    timer.schedule(new MoveMainTo9Calculette(2000, 1000), 2000);

                    break;
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
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, reserveBilles.largeurBille);
            billesList.add(billeAdded);
            myCorrectionAndPauseGeneral.addElements(billeAdded);

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