package com.evalutel.primval_desktop.onglets.chapitre2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneArdoise2;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx2_1 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;

    private UnePlancheNew planche1, planche2, planche3;

    private int cptOiseau, cptBille = 0;


    private UneArdoise2 uneArdoise2;
    private CalculetteView calculetteView;
    private float posY;

    private TextButton.TextButtonStyle styleTest;
    private Drawable drawableAux;

    private int cpt;

    private Label currentLabel;

    ScreenEx2_1(final Game game, String ongletTitre)
    {
        super(game, 2, 1, false, 0);


        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_1);

        oiseauxList = getNumberOiseauxArList();

        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60f, 9 * MyConstants.SCREENHEIGHT / 11f, (largeurBilleUnique * 1.5f), (largeurBilleUnique * 1.5f));
        sacDeBilles.largeurBille = largeurBilleUnique;
        sacDeBilles.setActive(false);
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);
//        allCorrigibles.add(sacDeBilles);

        float allPlanchesStartPositionX = 1.8f * MyConstants.SCREENWIDTH / 3 - largeurBilleMultiple / 2;

        planche1 = new UnePlancheNew(allPlanchesStartPositionX, 1.9f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche2 = new UnePlancheNew(allPlanchesStartPositionX, 1.1f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        planche3 = new UnePlancheNew(allPlanchesStartPositionX, 0.3f * MyConstants.SCREENHEIGHT / 3, largeurPlancheMultiple, largeurBilleMultiple);
        allPlanches.add(planche1);
        allPlanches.add(planche2);
        allPlanches.add(planche3);

        for (int i = 0; i < allPlanches.size(); i++)
        {
            UnePlancheNew unePlanche = allPlanches.get(i);
            allDrawables.add(unePlanche);
            myCorrectionAndPauseGeneral.addElements(unePlanche);
//            allCorrigibles.add(unePlanche);
        }

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        activiteView = new ActiviteView(stage, xTableTitre, activiteWidth * 42 / 1626, activiteWidth, "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        billesList = autoFillPlanche();

        resultatExercice = new UnResultat("Primval", 2, 1, 0, ongletTitre, 0, 0, dateTest, 0, 0, 0, 123);


        float widthCalculette = MyConstants.SCREENWIDTH / 6.5f;
        float hauteurCalculette = (widthCalculette * 362f / 355f) * 1.2f;
        float positionCalculetteX = MyConstants.SCREENWIDTH - widthCalculette - (MyConstants.SCREENWIDTH / 200f);
        float positionCalculetteY = MyConstants.SCREENWIDTH / 200f;

        calculetteView = new CalculetteView(stage, validusAnimated, positionCalculetteX, positionCalculetteY, widthCalculette, hauteurCalculette);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        float ardoise2Size = MyConstants.SCREENWIDTH / 6.5f;
        float posYArdoise2 = MyConstants.SCREENHEIGHT / 2f - ((ardoise2Size * 1.2f) / 2);

        uneArdoise2 = new UneArdoise2(stage, "", calculetteView.positionX, posYArdoise2, ardoise2Size);
        allDrawables.add(uneArdoise2);
        uneArdoise2.setActive(false);
        myCorrectionAndPauseGeneral.addElements(uneArdoise2);


        validusAnimated.setVisible(false);

        timer.schedule(new PresentationMetrologue(3_000), 1_000);

        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre2(game));
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
            MyTimer.TaskEtape nextEtape = new VoiciLaRegleDuJeu(3_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/Metrologue - Instructions onglet 2_1.mp3", nextEtape);
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
            MyTimer.TaskEtape nextEtape = new DisplayOiseaux(2_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet2_MaintenantOnVaCompteravecBadix.mp3", nextEtape);
        }
    }


    private class DisplayOiseaux extends MyTimer.TaskEtape
    {
        private DisplayOiseaux(long durMillis, long delay)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            DisplayOiseaux nextEtape = new DisplayOiseaux(0, 0);

            if (cptOiseau < 7)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);

                float posX;
                if (cptOiseau < 4)
                {
                    posY = 7 * MyConstants.SCREENHEIGHT / 10f;
                    posX = (MyConstants.SCREENWIDTH / 6f) + ((oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau));
                }
                else
                {
                    posY = 5 * MyConstants.SCREENHEIGHT / 11f;
                    posX = (MyConstants.SCREENWIDTH / 6f) +  (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 4);
                }

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau++;
            }
            else
            {
                timer.schedule(new MoveMainToReserve1(1_500, 1_500), 1_000);
            }
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
            if (cptBille < 4)
            {
                uneSouris.setVisible(true);

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve1(500);

                if (cptBille == 0)
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeVois4OiseauxSurLaPremiere.mp3", nextEtape);
                    uneSouris.moveTo(durationMillis, posXmain, posYMain, null, 500);
                }
                else
                {
                    uneSouris.moveTo(durationMillis, posXmain, posYMain, nextEtape, 500);
                }
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
            if (cptBille < 4)
            {
                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                UneBille bille = billesList.get(cptBille);
                bille.setPositionCenter(posXMain, posYMain);
                bille.setVisible(true);

                MyTimer.TaskEtape nextEtape = new EtapeDragFirstBille(500);
                if (cptBille == 0)
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeDeplace1a1.mp3");
                    timer.schedule(nextEtape, 0);
                }
                else
                {
                    timer.schedule(nextEtape, 0);
                }
                uneSouris.imageDown();
            }
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
            float posX = planche1.getPosition().x + (planche1.getWidth() / 2);
            float posY = planche1.getPosition().y + (planche1.getHeight() / 2);

            MyTimer.TaskEtape nextEtape = new EtapeAddFirstBille(1_500, 500);

            bille.animateImage(durationMillis, true, (posX - bille.getWidth() / 2), (posY - bille.getWidth() / 2), nextEtape, 1_000, 1f / 6f);

            uneSouris.cliqueTo(durationMillis, posX, posY, null, 500);
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
            if (cptBille < 3)
            {
                UneBille bille = billesList.get(cptBille);

                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                planche1.addBilleAndOrganize(bille);

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve1(500);

                uneSouris.moveTo(500, posXMain, posYMain, nextEtape, 500);
            }
            else
            {
                uneSouris.setVisible(true);

                UneBille bille = billesList.get(cptBille);

                planche1.addBilleAndOrganize(bille);

                MyPoint buttonPosition = calculetteView.buttonPosition(4);

                float posX = buttonPosition.x;
                float posY = buttonPosition.y;

                MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

                metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTape4AuClavier.mp3", nextEtape);

                uneSouris.moveTo(durationMillis, posX, posY, null, 500);
            }
            cptBille++;
        }
    }

    private class ClickMainToCalculette extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            styleTest = calculetteView.quatre_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition(4);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 500);

            calculetteView.textDisplay(4);
        }
    }

    private class MoveMainToValidate extends MyTimer.TaskEtape
    {
        private MoveMainToValidate(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_PuisJeValide.mp3", nextEtape);

            uneSouris.moveTo(durationMillis, posX, posY, null, 1_000);

            styleTest.up = drawableAux;
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
            uneSouris.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToReserve2(500, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeVois3OiseauxSurLaSeconde.mp3");

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textRemove();
            uneArdoise2.fillLabel(1, "4");

            currentLabel = activiteView.addTextActivite("4 +");
        }
    }


    private class MoveMainToReserve2 extends MyTimer.TaskEtape
    {
        private MoveMainToReserve2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cptBille >= 3 && cptBille < 5)
            {
                styleTest.up = drawableAux;

                float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve2(500);

                uneSouris.moveTo(durationMillis, posXmain, posYMain, nextEtape, 500);
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
            if (cptBille <= 6)
            {
                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                UneBille bille = billesList.get(cptBille);
                bille.setPositionCenter(posXMain, posYMain);

                MyTimer.TaskEtape nextEtape = new EtapeDragSecondBille(500);
                timer.schedule(nextEtape, 500);
                uneSouris.imageDown();
            }
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
            float posX = planche2.getPosition().x + (planche2.getWidth() / 2);
            float posY = planche2.getPosition().y + (planche2.getHeight() / 2);

            MyTimer.TaskEtape nextEtape = new EtapeAddSecondBille(1_500, 500);

            bille.animateImage(durationMillis, true, (posX - bille.getWidth() / 2), (posY - bille.getWidth() / 2), nextEtape, 1_000, 1f / 6f);

            uneSouris.cliqueTo(durationMillis, posX, posY, null, 500);
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
            if (cptBille <= 5)
            {
                UneBille bille = billesList.get(cptBille);

                float posXMain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
                float posYMain = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

                planche2.addBilleAndOrganize(bille);
                cptBille++;

                MyTimer.TaskEtape nextEtape = new DisplayBilleReserve2(500);

                uneSouris.moveTo(500, posXMain, posYMain, nextEtape, 500);
            }
            else if (cptBille == 6)
            {
                UneBille bille = billesList.get(cptBille);
                planche2.addBilleAndOrganize(bille);

                cptBille++;

                MyPoint buttonPosition = calculetteView.buttonPosition(3);

                float posX = buttonPosition.x;
                float posY = buttonPosition.y;

                MyTimer.TaskEtape nextEtape = new ClickMainToCalculette2(1_500, 1_000);

                uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);
            }
        }
    }


    private class ClickMainToCalculette2 extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            styleTest = calculetteView.trois_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition(3);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate2(500);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTape3AuClavier.mp3");

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textDisplay(3);
        }
    }

    private class MoveMainToValidate2 extends MyTimer.TaskEtape
    {
        private MoveMainToValidate2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate2(1_000, 1_000);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);

            styleTest.up = drawableAux;
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
            uneSouris.setVisible(true);

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new OiseauxOut(500, 2_500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textRemove();
            uneArdoise2.fillLabel(2, "3");

            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + " 3 =  ");

            currentLabel.setText(newStrBuilder);

        }
    }

    private class OiseauxOut extends MyTimer.TaskEtape
    {
        private OiseauxOut(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cptOiseau != 0)
            {
                if (cptOiseau == 2)
                {
                    metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_TiensLesOiseauxSontPartisConbienYenAvaitIl.mp3");
                }
                UnOiseau oiseau = oiseauxList.get(cptOiseau - 1);
                int posX = MyConstants.SCREENWIDTH * 2;
                int posY = MyConstants.SCREENHEIGHT * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(new OiseauxOut(250, 0), 100);
                cptOiseau--;
            }
            else
            {
                timer.schedule(new MovingBillestoBoard3(3_000, 2_500), 3_500);
            }
        }
    }

    private class MovingBillestoBoard3 extends MyTimer.TaskEtape
    {
        private MovingBillestoBoard3(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new MoveMainToPlanche1and2(1_500, 1_500);
            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeDeplaceToutesLesBilles.mp3", nextEtape);
        }
    }

    private class MoveMainToPlanche1and2 extends MyTimer.TaskEtape
    {
        private MoveMainToPlanche1and2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (cpt < 7)
            {
                styleTest.up = drawableAux;

                float Xbille1 = billesList.get(cpt).currentPositionX + (largeurBilleMultiple / 2);
                float Ybille1 = billesList.get(cpt).currentPositionY + (largeurBilleMultiple / 2);

                MyTimer.TaskEtape nextEtape = new ClickOnBille1(500);

                uneSouris.moveTo(durationMillis, Xbille1, Ybille1, nextEtape, 500);

            }
            else
            {
                timer.schedule(new MoveMaintoButton7(1_000, 2_000), 0);
            }
        }
    }


    private class ClickOnBille1 extends MyTimer.TaskEtape
    {
        private ClickOnBille1(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cpt);
            float posXMilieuPlanche = planche3.getPosition().x + (planche3.getWidth() / 2);
            float posYMilieuPlanche = planche3.getPosition().y + (planche3.getHeight() / 2);
            float posXMilieuPlancheBille = planche3.getPosition().x + (planche3.getWidth() / 2) - (largeurBilleMultiple / 2);
            float posYMilieuPlancheBille = planche3.getPosition().y + (planche3.getHeight() / 2) - (largeurBilleMultiple / 2);

            MyTimer.TaskEtape nextEtape = new EtapeDragBille1(500, 0);

            bille.animateImage(durationMillis, true, posXMilieuPlancheBille, posYMilieuPlancheBille, nextEtape, 1_000, 1f / 6f);

            uneSouris.cliqueTo(durationMillis, posXMilieuPlanche, posYMilieuPlanche, null, 500);
        }
    }


    private class EtapeDragBille1 extends MyTimer.TaskEtape
    {
        private EtapeDragBille1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            UneBille bille = billesList.get(cpt);
            MyTimer.TaskEtape nextEtape = new MoveMainToPlanche1and2(1_000, 0);
            planche3.addBilleAndOrganize(bille);

//            uneSouris.imageUp();

            timer.schedule(nextEtape, 1_000);

            cpt++;
        }
    }

    private class MoveMaintoButton7 extends MyTimer.TaskEtape
    {
        private MoveMaintoButton7(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyPoint buttonPosition = calculetteView.buttonPosition(7);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette3(1_000, 1_500);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 0);

            metrologue.metrologuePlaySound("Sounds/Onglet_2_1/chap2_onglet1_JeTapeLeNombreDeBilles.mp3");
        }
    }

    private class ClickMainToCalculette3 extends MyTimer.TaskEtape
    {
        private ClickMainToCalculette3(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
//            MyPoint buttonPosition = calculetteView.buttonPosition(7);

            timer.schedule(new MoveMainToValidate3(1_000), 2_500);

            uneSouris.imageDown();
            calculetteView.textDisplay(7);

            styleTest = calculetteView.sept_bouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;
        }
    }

    private class MoveMainToValidate3 extends MyTimer.TaskEtape
    {
        private MoveMainToValidate3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {


            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate3(1_000, 2_000);

            uneSouris.moveTo(durationMillis, posX, posY, nextEtape, 1_000);

            styleTest.up = drawableAux;

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

            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new AnnonceResultat(1_500, 0);


            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textRemove();
            uneArdoise2.fillLabel(3, "7");

            StringBuilder ex = currentLabel.getText();
            StringBuilder newStrBuilder = new StringBuilder(ex.toString() + "7 ");

            currentLabel.setText(newStrBuilder);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;
        }
    }

    private class AnnonceResultat extends MyTimer.TaskEtape
    {
        private AnnonceResultat(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new Fin(500, 0);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_JAnnonceleresultat.mp3", nextEtape);
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


    private ArrayList<UneBille> autoFillPlanche()
    {
        float firstPositionBilleX = (sacDeBilles.getPosition().x + sacDeBilles.largeurBille / 4);
        float firstPositionBilleY = (sacDeBilles.getPosition().y + sacDeBilles.largeurBille);

        billesList = new ArrayList<>();

        for (int i = 0; i < oiseauxList.size(); i++)
        {
            UneBille billeAdded = new UneBille(firstPositionBilleX, firstPositionBilleY, largeurBilleMultiple);
            billesList.add(billeAdded);

            allDrawables.add(billeAdded);
            billeAdded.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(billeAdded);

        }
        return billesList;
    }


    private ArrayList getNumberOiseauxArList()
    {
        oiseauxList = new ArrayList<>();

        float firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        float firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;

        float oiseauHeight = MyConstants.SCREENWIDTH / 12f;
        float oiseauWidth = oiseauHeight * (396f / 500f);

        for (int i = 0; i < 7; i++)
        {
            float firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, oiseauWidth, oiseauHeight);
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

        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = new UneBille(10_000, 10_000, sacDeBilles.largeurBille);
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
                    billeAux.touchUp(allPlanches);
                }
            }
        }
        objectTouched = null;
        return false;
    }
}