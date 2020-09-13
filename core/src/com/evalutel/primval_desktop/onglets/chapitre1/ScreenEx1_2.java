package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.Interfaces.MyTouchInterface;
import com.evalutel.primval_desktop.SacDeBilles;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnOiseau;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

import java.util.ArrayList;


public class ScreenEx1_2 extends ScreenOnglet
{
    private float posX, posY;
    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    //    private ArrayList<UnePlancheNew> allPlanches;
    private UneBille billeRectification;
    private UnePlancheNew planche1;
    private int randNumOiseau;
    private int cptOiseau, failedAttempts;

    private int[] numOiseauArray;

    boolean isInCorrection = false;


    ScreenEx1_2(final Game game, String ongletTitre)
    {
        super(game, 1, 2, true, 9);

        ScreeenBackgroundImage bgScreenEx1_2 = new ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_2);


        sacDeBilles = new SacDeBilles(53 * MyConstants.SCREENWIDTH / 60f, 9 * MyConstants.SCREENHEIGHT / 11f, (largeurBilleUnique * 1.5f), (largeurBilleUnique * 1.5f));
        sacDeBilles.largeurBille = largeurBilleUnique;
        allDrawables.add(sacDeBilles);
        myCorrectionAndPauseGeneral.addElements(sacDeBilles);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2f - largeurPlancheUnique / 2, 0, largeurPlancheUnique, largeurBilleUnique);
        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);
//        allCorrigibles.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
        allPlanches.add(planche1);

        for (int i = 0; i < 9; i++)
        {
            UneBille bille = new UneBille(10_000, 10_000, sacDeBilles.largeurBille);

            sacDeBilles.addBilleToReserve(bille);
            allDrawables.add(bille);
            objectTouchedList.add(bille);
            bille.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bille);
//            allCorrigibles.add(bille);
        }


        billesList = new ArrayList<>();

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        resultatExercice = new UnResultat("Primval", 1, 2, 0, ongletTitre, 9, 0, dateTest, 0, 0, 0, 123);

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(1, 2);

        String noteMaxObtenue = noteMax + "/9";

        activiteView = new ActiviteView(stage, xTableTitre, activiteWidth * 42 / 1626, activiteWidth, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);


        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        getNumberOiseauxArList();

        numOiseauArray = MyMath.genereTabAleatoire(9);


        myButtonBackToPreviousMenu.addListener(new ClickListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen(new Screen_Chapitre1(game));

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

        timer.schedule(new PresentationExercice(2_000), 500);
    }


    private void getNumberOiseauxArList()
    {
        float firstPositionOiseauX = MyConstants.SCREENWIDTH + 200;
        float firstPositionOiseauY = MyConstants.SCREENHEIGHT + 200;
        oiseauxList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            float firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
            UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, ((MyConstants.SCREENWIDTH / 12f) * (396f / 500f)), (MyConstants.SCREENWIDTH / 12f) * (500f / 396f));
            allDrawables.add(unOiseau);
            oiseauxList.add(unOiseau);
            myCorrectionAndPauseGeneral.addElements(unOiseau);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (sacDeBilles.contains(screenX, reversedScreenY) && sacDeBilles.isActive()) /*si bille part de la reserve*/
        {
            UneBille billeAdded = sacDeBilles.getBilleAndRemove();
            if (billeAdded != null)
            {
                billeAdded.setVisible(true);
                objectTouched = billeAdded;
                billeAdded.setActive(true);
            }
        }
        else if (validusAnimated.contains(mousePointerX, mousePointerY) && validusAnimated.isActive() && (!validusAnimated.isPause()))
        {
            objectTouched = validusAnimated;
        }
        else /*si bille part de la planche*/
        {
            for (int i = 0; i < objectTouchedList.size(); i++)
            {
                MyTouchInterface objetAux = objectTouchedList.get(i);

                if (objetAux.isTouched(screenX, reversedScreenY) && objetAux.isActive())
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
        if ((objectTouched != null) && (objectTouched.isDragable()))
        {
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (objectTouched != null)
        {
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;
                billeAux.touchUp(allPlanches);
                billesList.add(billeAux);
            }
            else if (objectTouched instanceof ValidusAnimated)
            {
                if (validusAnimated.contains(mousePointerX, mousePointerY))
                {
                    validusAnimated.touchUp(mousePointerX, mousePointerY);
                }
            }
        }
        objectTouched = null;
        return false;
    }

    private class PresentationExercice extends MyTimer.TaskEtape
    {
        private PresentationExercice(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new AtonTourDeJouer(1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet1_2/Onglet1_2_TitreOnglet.mp3", nextEtape);
        }
    }


    private class AtonTourDeJouer extends MyTimer.TaskEtape
    {
        private AtonTourDeJouer(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new EtapeInstruction(1_000, 500);

            metrologue.metrologuePlaySound("Sounds/Onglet1_2/Onglet1_2_Atontourdejouer.mp3", nextEtape);
        }
    }

    private class EtapeInstruction extends MyTimer.TaskEtape
    {
        private EtapeInstruction(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new DisplayOiseaux(1_000);

            if (questionCourante == 0)
            {
                activiteView.addTextActivite("1. Place autant de billes que d'oiseaux que tu vois et demande à Mademoiselle Validus si c'est juste pour avoir un diamant.");
                metrologue.metrologuePlaySound("Sounds/Onglet1_2/Onglet1.2_PlaceAutantdeBilles.mp3", nextEtape);
            }
            else
            {
                timer.schedule(new DisplayOiseaux(1_000), 500);
            }
            sacDeBilles.setActive(false);

            randNumOiseau = numOiseauArray[questionCourante];
        }
    }

    private class DisplayOiseaux extends MyTimer.TaskEtape
    {
        private DisplayOiseaux(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            DisplayOiseaux nextEtape = new DisplayOiseaux(0);

            sacDeBilles.setActive(true);

            if (cptOiseau < randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);

                if (cptOiseau > 5)
                {
                    posY = 5 * MyConstants.SCREENHEIGHT / 11f;
                    posX = (MyConstants.SCREENWIDTH / 6f) + (int) (oiseau.animationWidth + oiseau.animationWidth / 8) * (cptOiseau - 6);
                }
                else
                {
                    posY = 7 * MyConstants.SCREENHEIGHT / 10f;

                    if (cptOiseau < 3)
                    {
                        posX = (MyConstants.SCREENWIDTH / 6f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
                    }
                    else
                    {
                        posX = (2 * MyConstants.SCREENWIDTH / 9f) + (oiseau.animationWidth + oiseau.animationWidth / 8) * cptOiseau;
                    }
                }
                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau++;
            }
            else if (cptOiseau > randNumOiseau)
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau - 1);
                float posX = MyConstants.SCREENWIDTH * 2;
                float posY = MyConstants.SCREENHEIGHT * 2;

                oiseau.animateImage(500, true, posX, posY, null, 20, 1f / 6f);
                timer.schedule(nextEtape, 100);
                cptOiseau--;
            }
            else
            {
                timer.schedule(new EtapeAttendreValidus(1000), 100);
            }
        }
    }


    private class EtapeAttendreValidus extends MyTimer.TaskEtape
    {
        private EtapeAttendreValidus(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            failedAttempts = 0;
            validusAnimated.isActive = true;
            validusAnimated.etapeCorrection = new CheckValidus(0);
        }
    }

    private class CheckValidus extends MyTimer.TaskEtape
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
                MyTimer.TaskEtape nextEtape = new EtapeNextQuestion(1000, 500);

                if (questionCourante != 8)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - C'est bien continue.mp3", nextEtape);
                }
                else
                {
                    activiteView.addTextActivite("Youpi ! Tu as gagné un diamant.");
                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3", nextEtape);
                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                planche1.setAllBillesActive();
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    isInCorrection = !isInCorrection;

                    validusAnimated.isActive = false;
                    planche1.setAllBillesInactive();
                    sacDeBilles.setActive(false);
                    failedAttempts = 0;
//                    activiteView.addTextActivite("Voici la correction");

                    MyTimer.TaskEtape nextEtape = new EtapeRectification1(1000, 500);

                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", nextEtape);

                    addPierres(1);
                }
                else
                {
//                    activiteView.addTextActivite("Tu t'es trompé essaie encore.");
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }

    private class EtapeRectification1 extends MyTimer.TaskEtape
    {
        private EtapeRectification1(long durMillis)
        {
            super(durMillis);
        }

        private EtapeRectification1(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            if (planche1.getNumberBilles() < randNumOiseau)
            {
                uneSouris.setVisible(true);
                timer.schedule(new MoveMainToReserve1(500), 500);
            }
            else if (planche1.getNumberBilles() > randNumOiseau)
            {
                uneSouris.setVisible(true);
                timer.schedule(new MoveMainBackToPlanche(500), 500);
            }
            else
            {
                isInCorrection = !isInCorrection;
                myCorrectionAndPauseGeneral.correctionStop();

                sacDeBilles.setActive(true);

                timer.schedule(new EtapeNextQuestion(500), 500);
                uneSouris.setVisible(false);
            }
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
            uneSouris.setVisible(true);

            float posXsouris = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYsouris = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;

            MyTimer.TaskEtape nextEtape = new DisplayBilleReserve(500);
            uneSouris.moveTo(durationMillis, posXsouris, posYsouris, nextEtape, 500);
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
            float posXmain = sacDeBilles.currentPositionX + sacDeBilles.getWidth() / 2;
            float posYf = sacDeBilles.currentPositionY + sacDeBilles.getHeight() / 2;
            int posX = (int) posXmain;
            int posY = (int) posYf;

            billeRectification = sacDeBilles.getBilleAndRemove();
            billeRectification.setPositionCenter(posX, posY);
            billeRectification.setVisible(true);

            MyTimer.TaskEtape nextEtape = new EtapeDragBille(500);
            timer.schedule(nextEtape, 500);
            uneSouris.imageDown();
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
            float posX = MyConstants.SCREENWIDTH / 2f;
            float posY = planche1.getHeight() / 2f;

            MyTimer.TaskEtape nextEtape = new EtapeAddBille(500);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 1000, 1f / 6f);
            uneSouris.cliqueTo(durationMillis, posX, posY, null, 0);
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
            planche1.addBilleAndOrganize(billeRectification);

            uneSouris.imageUp();

            timer.schedule(new EtapeRectification1(1000), 500);
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
            billeRectification = planche1.getLastBille();
            planche1.removeBille(billeRectification);
            uneSouris.setVisible(true);

            float posX = billeRectification.getPosition().x + (billeRectification.animationWidth / 2);
            float posY = billeRectification.getPosition().y + (billeRectification.animationWidth / 2);

            MyTimer.TaskEtape nextEtape = new MoveBilleOutOfPlanche(500);

            uneSouris.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 500);
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
            float posX = (planche1.currentPositionX + planche1.getWidth()) + MyConstants.SCREENWIDTH / 20f;
            float posY = (planche1.currentPositionY + (planche1.getHeight() / 2));

            uneSouris.cliqueTo(durationMillis, posX, posY, null, 500);

            MyTimer.TaskEtape nextEtape = new LastOne(500);
            billeRectification.animateImage(durationMillis, true, (int) (posX - billeRectification.getWidth() / 2), (int) (posY - billeRectification.getWidth() / 2), nextEtape, 1000, 1f / 6f);
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
            float posX = (planche1.currentPositionX + planche1.getWidth()) + MyConstants.SCREENWIDTH / 20f;
            float posY = (planche1.currentPositionY + (planche1.getHeight() / 2));

            sacDeBilles.addBilleToReserve(billeRectification);

            MyTimer.TaskEtape nextEtape = new EtapeRectification1(500);
            uneSouris.moveTo(50, posX, posY, nextEtape, 500);

        }
    }

    private class EtapeNextQuestion extends MyTimer.TaskEtape
    {
        private EtapeNextQuestion(long durMillis)
        {
            super(durMillis);
        }

        private EtapeNextQuestion(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            questionCourante++;
            if (questionCourante == 9)       // fin exercice
            {
                MyTimer.TaskEtape finOnglet = new FinOnglet(1000, 500);
                finOnglet.run();

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1000L;

                ecrinDiamantView.getDiamantCount();

                timer.cancel();
            }
            else
            {
                timer.schedule(new EtapeInstruction(1000, 500), 500);
            }
        }
    }
}