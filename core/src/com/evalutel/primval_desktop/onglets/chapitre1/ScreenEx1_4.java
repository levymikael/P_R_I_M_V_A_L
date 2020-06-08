package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
import com.evalutel.primval_desktop.UneArdoise;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.ui_tools.MyPoint;
import com.evalutel.primval_desktop.ui_tools.MyTextButton;

import java.util.ArrayList;


public class ScreenEx1_4 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    ArrayList<UneArdoise> ardoiseList = new ArrayList<>();
    private ArrayList<UnePlancheNew> allPlanches;

    protected CalculetteViewTest calculetteViewTest;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_1;

    int failedAttempts, currrentBillesNumber = 0;

    DatabaseDesktop dataBase;

    String consigneExercice;

    Drawable drawableAux;

    TextButton.TextButtonStyle styleTest;

    boolean alreadyClicked = false;
    boolean isAllActive = false;

    public ScreenEx1_4(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 4, true);

        this.dataBase = dataBase;

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/FondsSequence/fond03.jpg");
        allDrawables.add(bgScreenEx1_1);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlanche / 2, MyConstants.SCREENHEIGHT / 10, largeurPlanche, largeurBille);
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
        allCorrigibles.add(planche1);

//        validusAnimated.setVisible(tr);

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Prononciation des chiffres 1 à 9";

        resultatExercice = new UnResultat("Primval", 1, 4, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);

        int noteMax = db.getHighestNote(1, 4);

        String noteMaxObtenue = noteMax + "/9";

        activiteView = new ActiviteView(stage, activiteWidth, numExercice, consigneExercice, noteMaxObtenue, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        calculetteViewTest = new CalculetteViewTest(stage);
        allDrawables.add(calculetteViewTest);
//        calculetteViewTest.setActive(false);

//        billesList = autoFillPlanche();

        displayArdoise();

        resultatExercice = new UnResultat("Primval", 1, 4, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);

        timer.schedule(new PresentationOnglet(3000), 1000);

        billesList = new ArrayList<>();


        calculetteViewTest.validerBouton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.log("", "button validate pressed");
                pressValidate();
            }
        });

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
            MyTimer.TaskEtape nextEtape = new InstructionPart2(3000, 2000);

            metrologue.metrologuePlaySound("Sounds/Onglet1_4/Prononciation des chifres de 1 a 9.mp3", nextEtape);
        }
    }

    private class InstructionPart2 extends MyTimer.TaskEtape
    {
        private InstructionPart2(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private InstructionPart2(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            isAllActive = true;
            metrologue.metrologuePlaySound("Sounds/Onglet1_4/Touche un chiffre pour entendre son nom.mp3");
            activiteView.addTextActivite("1. Touche un chiffre pour entendre son nom");

        }
    }

    private class ArdoiseTouched extends MyTimer.TaskEtape
    {
        private ArdoiseTouched(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            MyTimer.TaskEtape nextEtape = new InstructionPart3(2000, 1000);

            String audioPath = "";
            switch (currrentBillesNumber)
            {
                case 1:
                    audioPath = "Sounds/Onglet1_4/un.mp3";

                    break;
                case 2:
                    audioPath = "Sounds/Onglet1_4/deux.mp3";

                    break;
                case 3:
                    audioPath = "Sounds/Onglet1_4/trois.mp3";

                    break;
                case 4:
                    audioPath = "Sounds/Onglet1_4/quatre.mp3";

                    break;
                case 5:
                    audioPath = "Sounds/Onglet1_4/cinq.mp3";

                    break;
                case 6:
                    audioPath = "Sounds/Onglet1_4/six.mp3";

                    break;
                case 7:
                    audioPath = "Sounds/Onglet1_4/sept.mp3";

                    break;
                case 8:
                    audioPath = "Sounds/Onglet1_4/huit.mp3";

                    break;
                case 9:
                    audioPath = "Sounds/Onglet1_4/neuf.mp3";

                    break;
            }

            metrologue.metrologuePlaySound(audioPath, nextEtape);

        }
    }

    private class InstructionPart3 extends MyTimer.TaskEtape
    {
        private InstructionPart3(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private InstructionPart3(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
//            MyTimer.TaskEtape nextEtape = new PressValidate(2000, 1000);

            metrologue.metrologuePlaySound("Sounds/Onglet1_4/Tape le chiffre au clavier et valide.mp3", null);
            activiteView.addTextActivite("2. Tape le chiffre au clavier et valide");

        }
    }


    public void pressValidate()
    {
        String nbInput = calculetteViewTest.getInput();

        if (nbInput != null)
        {
            if (Integer.parseInt(nbInput) == currrentBillesNumber)
            {
                calculetteViewTest.textRemove();

                UneArdoise uneArdoise = ardoiseList.get(currrentBillesNumber - 1);
                uneArdoise.setActive(false);

                if (questionCourante != 8)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - C'est bien continue.mp3");
                }
                else
                {
                    activiteView.addTextActivite("Youpi ! Tu as gagné un diamant.");
                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
                }
                validusAnimated.isActive = false;
                addDiamonds(1);

                Texture ardoiseBgInactive = new Texture("Images/Ardoise/ardoise_fond.png");
                ardoiseBgInactive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                uneArdoise.setBackground(new TextureRegionDrawable(new TextureRegion(ardoiseBgInactive)));

                cleanPlanche();
                isAllActive = true;
                activiteView.emptyActivite();

                timer.schedule(new InstructionPart2(500), 1500);

            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

//                isInCorrection = !isInCorrection;

                    validusAnimated.isActive = false;
                    failedAttempts = 0;


                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3");

                    addPierres(1);

                    timer.schedule(new EtapeRectification(1000), 500);
                }
                else
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                failedAttempts++;
            }
        }
    }


    private class EtapeRectification extends MyTimer.TaskEtape
    {
        private EtapeRectification(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);
            timer.schedule(new MoveMainToCalculette(500), 500);

            uneMain.setVisible(false);
        }
    }


    private class MoveMainToCalculette extends MyTimer.TaskEtape
    {
        private MoveMainToCalculette(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(currrentBillesNumber);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1500, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);
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
            switch (currrentBillesNumber)
            {
                case 1:
                    styleTest = calculetteViewTest.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteViewTest.deux_bouton.getStyle();
                    break;

                case 3:
                    styleTest = calculetteViewTest.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteViewTest.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteViewTest.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteViewTest.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteViewTest.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteViewTest.huit_bouton.getStyle();
                    break;

                case 9:
                    styleTest = calculetteViewTest.neuf_bouton.getStyle();
                    break;

                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(currrentBillesNumber);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

//            MyTimer.TaskEtape nextEtape = new MoveMainTo1Validate(1500, 1000);

            uneMain.cliqueTo(500, (int) posX, (int) posY, null, 1000);

            calculetteViewTest.textDisplay(currrentBillesNumber);

            timer.schedule(new MainDisappear(500), 500);
        }
    }

    private class MainDisappear extends MyTimer.TaskEtape
    {
        private MainDisappear(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            uneMain.setVisible(false);
            styleTest.up = styleTest.down;
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


    public void displayArdoise()
    {
        int firstPositionArdoiseX = MyConstants.SCREENWIDTH / 4;
        int firstPositionArdoiseY = 2 * MyConstants.SCREENHEIGHT / 3;

        int buttonSize = MyConstants.SCREENWIDTH / 15;


        for (int i = 0; i < 9; i++)
        {
            int firstPositionArdoiseXNew = firstPositionArdoiseX + (i * (MyConstants.SCREENWIDTH / 9));

            if (i > 4)
            {
                firstPositionArdoiseY = 5 * MyConstants.SCREENHEIGHT / 9;
                firstPositionArdoiseXNew = (firstPositionArdoiseX + MyConstants.SCREENWIDTH / 20) + ((i - 5) * (MyConstants.SCREENWIDTH / 9));
            }
            final UneArdoise uneArdoise = new UneArdoise(stage, String.valueOf(i + 1), firstPositionArdoiseXNew, firstPositionArdoiseY, buttonSize);
            uneArdoise.setPosition(firstPositionArdoiseXNew, firstPositionArdoiseY);
            ardoiseList.add(uneArdoise);
            uneArdoise.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    super.clicked(event, x, y);

                    if (isAllActive)
                    {
                        if (uneArdoise.isActive())
                        {
                            String numberTouched = uneArdoise.number;
                            currrentBillesNumber = Integer.parseInt(numberTouched);
                            displayBille(currrentBillesNumber);

                            isAllActive = false;

                            timer.schedule(new ArdoiseTouched(500), 500);

                        }
                    }
                }
            });
            myCorrectionAndPauseGeneral.addElements(uneArdoise);
        }
    }

//    public void getMp3(int i)
//    {
//        String audioPath = "";
//        switch (i)
//        {
//            case 1:
//                audioPath = "Sounds/Onglet1_4/un.mp3";
//
//                break;
//            case 2:
//                audioPath = "Sounds/Onglet1_4/deux.mp3";
//
//                break;
//            case 3:
//                audioPath = "Sounds/Onglet1_4/trois.mp3";
//
//                break;
//            case 4:
//                audioPath = "Sounds/Onglet1_4/quatre.mp3";
//
//                break;
//            case 5:
//                audioPath = "Sounds/Onglet1_4/cinq.mp3";
//
//                break;
//            case 6:
//                audioPath = "Sounds/Onglet1_4/six.mp3";
//
//                break;
//            case 7:
//                audioPath = "Sounds/Onglet1_4/sept.mp3";
//
//                break;
//            case 8:
//                audioPath = "Sounds/Onglet1_4/huit.mp3";
//
//                break;
//            case 9:
//                audioPath = "Sounds/Onglet1_4/neuf.mp3";
//
//                break;
//        }
//
//        metrologue.metrologuePlaySound(audioPath);
//    }

    public void displayBille(int nbBillesToDisplay)
    {
        for (int i = 0; i < nbBillesToDisplay; i++)
        {
            UneBille bille = new UneBille(planche1.currentPositionX, planche1.currentPositionY, largeurBille);

            billesList.add(bille);
            allDrawables.add(bille);
            planche1.addBilleAndOrganize(bille);
            bille.setVisible(true);
            myCorrectionAndPauseGeneral.addElements(bille);
            allCorrigibles.add(bille);
        }
    }


    public void cleanPlanche()
    {
        for (int i = 0; i < billesList.size(); i++)
        {
            UneBille bille = billesList.get(i);
            planche1.removeBille(bille);
            allDrawables.remove(bille);
        }

        billesList.clear();
    }
}