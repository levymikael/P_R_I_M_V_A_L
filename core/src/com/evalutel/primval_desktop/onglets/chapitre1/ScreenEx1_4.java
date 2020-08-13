package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UneArdoise;
import com.evalutel.primval_desktop.UneBille;
import com.evalutel.primval_desktop.UnePlancheNew;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;
import java.util.Random;


public class ScreenEx1_4 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBille> billesList;
    ArrayList<UneArdoise> ardoiseList = new ArrayList<>();
    //private ArrayList<UnePlancheNew> allPlanches;

    protected CalculetteView calculetteView;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_1;

    int failedAttempts, currrentBillesNumber = 0;

    DatabaseDesktop dataBase;

    String nbInput;

    Drawable drawableAux;

    TextButton.TextButtonStyle styleTest;

    boolean alreadyClicked = false;
    boolean isAllActive = false;
    boolean afterCorrection = false;
    Texture ardoiseBgInactive;

    public ScreenEx1_4(final Game game, String ongletTitre)
    {
        super(game, 1, 4, true, 9);

        this.dataBase = dataBase;

        ardoiseBgInactive = new Texture("Images/Ardoise/ardoise_fond_inactive.jpg");
        ardoiseBgInactive.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        bgScreenEx1_1 = new ScreeenBackgroundImage("Images/FondsSequence/" + getBgImageRandom());
        allDrawables.add(bgScreenEx1_1);

        planche1 = new UnePlancheNew(MyConstants.SCREENWIDTH / 2 - largeurPlancheUnique / 2, MyConstants.SCREENHEIGHT / 10, largeurPlancheUnique, largeurBilleUnique);
        allDrawables.add(planche1);
        myCorrectionAndPauseGeneral.addElements(planche1);
//        allCorrigibles.add(planche1);
        allPlanches.add(planche1);

        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        resultatExercice = new UnResultat("Primval", 1, 4, 0, ongletTitre, 9, 0, dateTest, 0, 0, 0, 123);


        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(1, 4);

        String noteMaxObtenue = noteMax + "/9";

        activiteView = new ActiviteView(stage, xTableTitre, activiteWidth * 42 / 1626, activiteWidth, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        exoNumLabel = new Label(numExercice, labelStyleArial);
        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);

        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        calculetteView = new CalculetteView(stage, validusAnimated);
        calculetteView.setActive(false);
        allDrawables.add(calculetteView);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        displayArdoise();

        billesList = new ArrayList<>();

        uneMain.setPosition(MyConstants.SCREENWIDTH / 2f, MyConstants.SCREENHEIGHT / 3f);

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
            failedAttempts = 0;

            metrologue.metrologuePlaySound("Sounds/Onglet1_4/Touche un chiffre pour entendre son nom.mp3");
            activiteView.setTextActivite("1. Touche un chiffre pour entendre son nom");
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

//        private InstructionPart3(long durMillis)
//        {
//            super(durMillis);
//        }

        @Override
        public void run()
        {
            metrologue.metrologuePlaySound("Sounds/Onglet1_4/Tape le chiffre au clavier et valide.mp3");
            activiteView.addTextActivite("2. Tape le chiffre au clavier et valide");
            calculetteView.setActive(true);

            calculetteView.etapeCorrection = new PressValidate(500);
        }
    }


    private class PressValidate extends MyTimer.TaskEtape
    {
        private PressValidate(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            String txtTape = calculetteView.getInput();

            int value = -1;

            try
            {
                value = Integer.parseInt(txtTape);
            } catch (Exception e)
            {

            }

            if (value == currrentBillesNumber)
            {
                addDiamonds(1);

                validusAnimated.isActive = false;
                calculetteView.setActive(false);

//                if (questionCourante != 8)
//                {
                    validusAnimated.goodAnswerPlaySound(new NextQuestion(500));
//                }
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteView.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2000));

                    addPierres(1);
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
            timer.schedule(new MoveMainToCalculette(1000), 1000);
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

            MyPoint buttonPosition = calculetteView.buttonPosition(currrentBillesNumber);

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
                    styleTest = calculetteView.un_bouton.getStyle();
                    break;
                case 2:
                    styleTest = calculetteView.deux_bouton.getStyle();
                    break;
                case 3:
                    styleTest = calculetteView.trois_bouton.getStyle();
                    break;
                case 4:
                    styleTest = calculetteView.quatre_bouton.getStyle();
                    break;
                case 5:
                    styleTest = calculetteView.cinq_bouton.getStyle();
                    break;
                case 6:
                    styleTest = calculetteView.six_bouton.getStyle();
                    break;
                case 7:
                    styleTest = calculetteView.sept_bouton.getStyle();
                    break;
                case 8:
                    styleTest = calculetteView.huit_bouton.getStyle();
                    break;
                case 9:
                    styleTest = calculetteView.neuf_bouton.getStyle();
                    break;
                default:
                    break;
            }

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            MyPoint buttonPosition = calculetteView.buttonPosition(currrentBillesNumber);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteView.textDisplay(currrentBillesNumber);
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

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1000, 1000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

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
            MyPoint buttonValidatePosition = calculetteView.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1000);

            calculetteView.textRemove();

            ardoiseAppear();
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
            nbInput = String.valueOf(currrentBillesNumber);
            afterCorrection = true;
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
        }
    }

    private class NextQuestion extends MyTimer.TaskEtape
    {

        private NextQuestion(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteView.textRemove();

            ardoiseAppear();

            cleanPlanche();
            isAllActive = true;

            UneArdoise uneArdoise = ardoiseList.get(currrentBillesNumber - 1);
            uneArdoise.setActive(false);
            uneArdoise.setBackground(new TextureRegionDrawable(new TextureRegion(ardoiseBgInactive)));

            if (questionCourante == 8)
            {
                timer.schedule(new Fin(500), 500);
            }
            else
            {
                timer.schedule(new InstructionPart2(500), 500);
            }
            questionCourante++;

            nbInput = null;
        }
    }

    private class Fin extends MyTimer.TaskEtape
    {
//        private Fin(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

        private Fin(long durMillis)
        {
            super(durMillis);
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

                            ardoiseDisappear();
                        }
                    }
                }
            });
            myCorrectionAndPauseGeneral.addElements(uneArdoise);
        }
    }

    public void displayBille(int nbBillesToDisplay)
    {
        for (int i = 0; i < nbBillesToDisplay; i++)
        {
            UneBille bille = new UneBille(planche1.currentPositionX, planche1.currentPositionY, largeurBilleUnique);

            billesList.add(bille);
            allDrawables.add(bille);
            planche1.addBilleAndOrganize(bille);
            bille.setVisible(true);
            myCorrectionAndPauseGeneral.addElements(bille);
//            allCorrigibles.add(bille);
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

    public void ardoiseDisappear()
    {
        for (int i = 0; i < ardoiseList.size(); i++)
        {
            UneArdoise ardoise = ardoiseList.get(i);
            ardoise.setVisible(false);
        }
    }

    public void ardoiseAppear()
    {
        for (int i = 0; i < ardoiseList.size(); i++)
        {
            UneArdoise ardoise = ardoiseList.get(i);
            ardoise.setVisible(true);
        }
    }

    public String getBgImageRandom()
    {
        String bgImgRandom = "";

        Random rand = new Random();

        int fondSequenceFolderSize = 14;
        int rand_int = rand.nextInt(fondSequenceFolderSize) + 1;
        if (rand_int < 10)
        {
            bgImgRandom = "fond0" + rand_int + ".jpg";
        }
        else
        {
            bgImgRandom = "fond" + rand_int + ".jpg";

        }
        return bgImgRandom;
    }
}