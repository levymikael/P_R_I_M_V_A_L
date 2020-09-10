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
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.SacDeBougies;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnGateauAnniversaire;
import com.evalutel.primval_desktop.UneBougie;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_6 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBougie> bougiesList;
    private ArrayList<Texture> pastilleList;
    private ArrayList<UnGateauAnniversaire> allGateaux;

    private UneBougie bougieRectification;

    protected CalculetteView calculetteView;

    private UnGateauAnniversaire gateauAnniversaire;
    ScreeenBackgroundImage bgScreenEx1_7;

    TextureRegion textureRegion;

    int cptBougie = 0;
    private int randNumPastille;

    TextButton.TextButtonStyle styleTest;

//    int posX, posY;

    int[] numPastilleArray;

    int failedAttempts, currrentBougiesNumber = 0;

    boolean isInCorrection = false;

    boolean afterCorrection, isAllActive, displayPastille = false;


    String nbInput;

    //    Label currentLabel;
    Drawable drawableAux;


    ScreenEx1_6(final Game game, String ongletTitre)
    {
        super(game, 1, 6, true, 9);


        float largeurBougie = MyConstants.SCREENWIDTH / 18f;
        float largeurGateau = largeurBougie * 9;
        float hauteurGateau = (largeurGateau * (266f / 462f));

        bgScreenEx1_7 = new ScreeenBackgroundImage("Images/Onglet_1_7/anniversaire.jpg");
        allDrawables.add(bgScreenEx1_7);

        sacDeBougies = new SacDeBougies(5.3f * MyConstants.SCREENWIDTH / 6f, 9 * MyConstants.SCREENHEIGHT / 11f, (largeurBougie * 1.5f), (largeurBougie * 1.5f));
        sacDeBougies.largeurBille = largeurBilleUnique;
        sacDeBougies.setActive(true);
        allDrawables.add(sacDeBougies);
        myCorrectionAndPauseGeneral.addElements(sacDeBougies);
//        allCorrigibles.add(sacDeBougies);


        gateauAnniversaire = new UnGateauAnniversaire(3 * MyConstants.SCREENWIDTH / 10f - largeurGateau / 10, MyConstants.SCREENHEIGHT / 17f, largeurGateau, hauteurGateau, largeurBougie);
        gateauAnniversaire.shouldReturnToReserve = true;
        allDrawables.add(gateauAnniversaire);
        myCorrectionAndPauseGeneral.addElements(gateauAnniversaire);
//        allCorrigibles.add(gateauAnniversaire);

        bougiesList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            UneBougie bougie = new UneBougie(sacDeBougies.currentPositionX, sacDeBougies.currentPositionY, sacDeBougies.largeurBille * 1.3f);

            sacDeBougies.addBougieToReserve(bougie);
            allDrawables.add(bougie);
            objectTouchedList.add(bougie);
            bougie.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bougie);
//            allCorrigibles.add(bougie);
            bougiesList.add(bougie);
        }

        allGateaux = new ArrayList<>();
        allGateaux.add(gateauAnniversaire);


        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        float posEnonceX = (MyConstants.SCREENWIDTH - activiteWidth) / 2f;
        float posSolutionX = posEnonceX + activiteWidth / 2f;

        activiteView = new ActiviteView(stage, posEnonceX, activiteWidth * 42f / 1626f, activiteWidth / 2f, "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        solutionView = new ActiviteView(stage, posSolutionX, activiteWidth * 42f / 1626f, activiteWidth / 2f, "solution");
        allDrawables.add(solutionView);
        myCorrectionAndPauseGeneral.addElements(solutionView);


        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(1, 7);

        String noteMaxObtenue = noteMax + "/9";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);


        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);


        float widthCalculette = MyConstants.SCREENWIDTH / 6.5f;
        float hauteurCalculette = (widthCalculette * 362f / 355f) * 1.2f;
        float positionCalculetteX = MyConstants.SCREENWIDTH - widthCalculette - (MyConstants.SCREENWIDTH / 200f);
        float positionCalculetteY = MyConstants.SCREENWIDTH / 200f;

        calculetteView = new CalculetteView(stage, validusAnimated, positionCalculetteX, positionCalculetteY, widthCalculette, hauteurCalculette);
        allDrawables.add(calculetteView);
        calculetteView.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteView);

        pastilleList = pastilleToDisplay();

        numPastilleArray = MyMath.genereTabAleatoire(9);

        resultatExercice = new UnResultat("Primval", 1, 7, 0, ongletTitre, MyConstants.noteMaxChap1[4], 0, dateTest, 0, 0, 0, 123);

        uneSouris.setPosition(MyConstants.SCREENWIDTH / 2f, MyConstants.SCREENHEIGHT / 3f);


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

        timer.schedule(new PresentationOnglet(3_000), 1_000);
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);

        if (displayPastille)
        {
            if (textureRegion != null)
            {
                batch.begin();
                batch.draw(textureRegion, MyConstants.SCREENWIDTH / 6f, 6 * MyConstants.SCREENHEIGHT / 15f, MyConstants.SCREENHEIGHT / 4f, MyConstants.SCREENHEIGHT / 4f * (234f / 268f));
                batch.end();
            }
        }
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
            uneSouris.imageDown();

            MyTimer.TaskEtape nextEtape = new EtapeInstruction(2_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_Ungateaupourplusieursanniversaires.mp3", nextEtape);
        }
    }

    private class EtapeInstruction extends MyTimer.TaskEtape
    {
        private EtapeInstruction(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private EtapeInstruction(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (questionCourante == 0)
            {
                activiteView.setTextActivite("La famille Dubonheur est réunie pour fêter l'anniversaire de tous les cousins et cousines");
                metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_lafamilleDubonheur.mp3", new BirthdayKiDPicDisplay(1_000));
            }
            else
            {
                timer.schedule(new BirthdayKiDPicDisplay(1_000), 0);
            }
        }
    }

    private void pastilleDraw(int randNumPastille)
    {
        Texture txture = pastilleList.get(randNumPastille - 1);
//        txture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegion = new TextureRegion(txture);
    }

    private class BirthdayKiDPicDisplay extends MyTimer.TaskEtape
    {
//        private BirthdayKiDPicDisplay(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

        private BirthdayKiDPicDisplay(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            randNumPastille = numPastilleArray[questionCourante];

            pastilleDraw(randNumPastille);

            sacDeBougies.setActive(true);
            if (questionCourante == 0)
            {
                displayPastille = true;
            }

            MyTimer.TaskEtape nextEtape = new InputClavier(1_000);


            switch (randNumPastille)
            {
                case 1:
                    activiteView.setTextActivite("Lucas ne marche pas encore, et il accourt à 4 pattes pour souffler sa 1ère bougie. Lucas a 1 an");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_LucasNeMarchePasEncore.mp3", nextEtape);
                    break;

                case 2:
                    activiteView.setTextActivite("Emma est fière car cette année elle va souffler ses 2 bougies comme une grande. L'année dernière elle avait eu du mal avec la 1ère. Emma a 2 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_Onglet6_EmmaEstFierecarcetteannee.mp3", nextEtape);
                    break;

                case 3:
                    activiteView.setTextActivite("Arthur est très ému, car tout le monde le regarde, mais il souffle vaillamment ses 3 bougies. Arthur a 3 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_Arthuresttresemu.mp3", nextEtape);
                    break;

                case 4:
                    activiteView.setTextActivite("Lina est turbulente, après avoir renversé la tasse de sa cousine, elle se dirige vers son gâteau et ses 4 bougies, Lina a 4 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_Onglet6_LinaestTurbulente.mp3", nextEtape);
                    break;

                case 5:
                    activiteView.setTextActivite("Héloïse dit à sa poupée qu'elle est maintenant une grande fille de 5 ans. Héloïse a 5 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_Heloiseditasapoupee.mp3", nextEtape);
                    break;

                case 6:
                    activiteView.setTextActivite("Théo est fier, car à 6 ans il va bientôt aller à la grande école. Théo a 6 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_Onglet6_Theoestfiercara6ans.mp3", nextEtape);
                    break;

                case 7:
                    activiteView.setTextActivite("Zoé pose son petit chat dans son panier afin de pouvoir souffler tranquillement ses 7 bougies. Zoé a 7 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_Onglet6_Zoeposesonpetitchat.mp3", nextEtape);
                    break;

                case 8:
                    activiteView.setTextActivite("Hugo est bagarreur, après un croche-pied à son petit frère, il avance pour souffler ses 8 bougies. Hugo a 8 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_Onglet6_Hugoestbagarreur.mp3", nextEtape);
                    break;

                case 9:
                    activiteView.setTextActivite("Chloé est très inquiète, elle se demande si elle va pouvoir souffler ses 9 bougies d'un seul coup. Chloé a 9 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet_1_6/chap1_onglet6_ChloeEstTresInquiete.mp3", nextEtape);
                    break;

                default:
                    break;
            }

//            sacDeBougies.setActive(false);
        }
    }


    private class InputClavier extends MyTimer.TaskEtape
    {
//        private InputClavier(long durMillis, long delay)
//        {
//            super(durMillis, delay);
//        }

        private InputClavier(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteView.setActive(true);
            validusAnimated.setActive(true);
            sacDeBougies.setActive(true);

            activiteView.addTextActivite("Tape avec ton doigt sur la boite de bougies. Une bougie se posera sur le gâteau. Tape sur le clavier le nombre de bougies que tu as placé puis Valide. Tape sur une bougie sur le gâteau si tu veux la retirer");
            if (questionCourante == 0)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet_1_6/Chap1_onglet6_Tapeavectondoigt.mp3");
            }

            validusAnimated.etapeCorrection = new PressValidate(0);
            calculetteView.etapeCorrection = new PressValidate(0);
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
            if ((gateauAnniversaire.getNumberBougies() == randNumPastille) && (value == gateauAnniversaire.getNumberBougies()))
            {
                if (questionCourante != 8)
                {
                    validusAnimated.goodAnswerPlaySound(new EtapeNextQuestion(500, 0));
                }
                else
                {
                    timer.schedule(new Fin(1_000, 0), 500);
                    validusAnimated.validusPlaySound("Sounds/Validus/Youpi tu as gagne.mp3");
                }
                validusAnimated.isActive = false;
                addDiamonds(1);
                gateauAnniversaire.setAllBougiesActive();
                failedAttempts = 0;
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteView.setActive(false);
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
                    failedAttempts = 0;

                    addPierres(1);
                }
                else if (gateauAnniversaire.getNumberBougies() == 0)
                {
                    validusAnimated.validusPlaySound("Sounds/Validus/Validus - tu t'es trompe.mp3");
                }
                else if (gateauAnniversaire.getNumberBougies() < randNumPastille)
                {
                    validusAnimated.validusPlaySound("Sounds/Onglet_1_6/Chap1_ong6_Tutestrompecliquesurlaboitepourajouterdesbougies.mp3");
                }
                else if (gateauAnniversaire.getNumberBougies() > randNumPastille)
                {
                    validusAnimated.validusPlaySound("Sounds/Onglet_1_6/Chap1_Onglet6_tutestrompe,cliquesurlegateaupourenleverdesbougies.mp3");
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
            timer.schedule(new AddBougies(1_000), 1_000);
        }
    }


    private class AddBougies extends MyTimer.TaskEtape
    {
        private AddBougies(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (gateauAnniversaire.getNumberBougies() < randNumPastille)
            {
                bougieRectification = sacDeBougies.getBougieAndRemove();
                bougieRectification.setVisible(true);
                gateauAnniversaire.addBougieAndOrganize(bougieRectification);
                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (gateauAnniversaire.getNumberBougies() > randNumPastille)
            {
                bougieRectification = gateauAnniversaire.getLastBougie();
                gateauAnniversaire.removeBougie(bougieRectification);
                sacDeBougies.addBougieToReserve(bougieRectification);

                timer.schedule(new EtapeRectification(500), 500);
            }
            else if (gateauAnniversaire.getNumberBougies() == randNumPastille)
            {
                timer.schedule(new MoveMainToCalculette(1_000), 1_000);
            }
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
            uneSouris.setVisible(true);

            MyPoint buttonPosition = calculetteView.buttonPosition(randNumPastille);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneSouris.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
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
            switch (randNumPastille)
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

            MyPoint buttonPosition = calculetteView.buttonPosition(randNumPastille);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneSouris.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteView.textDisplay(randNumPastille);
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

            uneSouris.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

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

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteView.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneSouris.cliqueTo(durationMillis, posX, posY, nextEtape, 1_000);

            calculetteView.textRemove();
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
            uneSouris.setVisible(false);
            nbInput = String.valueOf(randNumPastille);
            afterCorrection = true;
            timer.schedule(new NextQuestion(500), 500);
            styleTest.up = drawableAux;
            gateauAnniversaire.setAllBougiesActive();
            failedAttempts = 0;
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

            isAllActive = true;

            if (questionCourante == 8)
            {
                timer.schedule(new Fin(500, 0), 500);
            }
            else
            {
                timer.schedule(new SolutionDisplay(500, 0), 0);
            }
            questionCourante++;

            nbInput = null;
        }
    }

    private class EtapeNextQuestion extends MyTimer.TaskEtape
    {
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
                MyTimer.TaskEtape finOnglet = new FinOnglet(1_000, 500);
                finOnglet.run();

                endTime = System.currentTimeMillis();
                seconds = (endTime - startTime) / 1_000L;

                ecrinDiamantView.getDiamantCount();

                timer.cancel();
            }
            else
            {
                timer.schedule(new SolutionDisplay(1_000, 500), 500);
                calculetteView.textRemove();
            }
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
            MyTimer.TaskEtape finOnglet = new FinOnglet(1_000, 500);
            finOnglet.run();

            endTime = System.currentTimeMillis();
            seconds = (endTime - startTime) / 1_000L;

            timer.cancel();
        }
    }

    public ArrayList pastilleToDisplay()
    {
        pastilleList = new ArrayList<>();

        for (int i = 0; i < 9; i++)
        {
            Texture pastilleTxture = new Texture("Images/Onglet_1_7/pastille" + (i + 1) + ".png");
            pastilleTxture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            pastilleList.add(pastilleTxture);
        }

        return pastilleList;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (validusAnimated.contains(mousePointerX, mousePointerY) && validusAnimated.isActive() && (!validusAnimated.isPause()))
        {
            objectTouched = validusAnimated;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if ((objectTouched != null) && (objectTouched.isDragable()))
        {
            objectTouched.setPosition((screenX - objectTouched.getWidth() / 2), (MyConstants.SCREENHEIGHT - screenY - objectTouched.getHeight() / 2));
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
            if (objectTouched instanceof UneBougie)
            {
                UneBougie bougieAux = (UneBougie) objectTouched;
                bougieAux.touchUp(allGateaux);
                //bougiesList.add(bougieAux);
            }
            else if (objectTouched instanceof ValidusAnimated)
            {
                if (validusAnimated.contains(mousePointerX, mousePointerY))
                {
                    validusAnimated.touchUp(mousePointerX, mousePointerY);
                }
            }
        }
        else if (gateauAnniversaire.contains(mousePointerX, mousePointerY) && (!gateauAnniversaire.isPause()))
        {

            UneBougie bougie = gateauAnniversaire.getLastBougie();
            if (bougie != null && gateauAnniversaire.isActive())
            {
                gateauAnniversaire.removeBougie(bougie);
                gateauAnniversaire.reorganiseBougies();

                bougie.gateauNew = null;
                bougie.setPosition(100_000, 100_000);
                sacDeBougies.addBougieToReserve(bougie);
            }

        }
        else if (sacDeBougies.contains(mousePointerX, mousePointerY) && (sacDeBougies.isActive()) && (!sacDeBougies.isPause()))
        {
            UneBougie bougie = null;
            if (sacDeBougies.getBillesCount() > 0)
            {
                bougie = sacDeBougies.getBougieAndRemove();
            }
            if (bougie != null)
            {
//                sacDeBougies.removeBougie(bougie);
//                gateauAnniversaire.reorganiseBougies();

                gateauAnniversaire.addBougieAndOrganize(bougie);
//                bougie.setPosition(100_000, 100_000);
//               gateauAnniversaire.addBougie()
            }

        }
        objectTouched = null;
        return false;
    }


    private class SolutionDisplay extends MyTimer.TaskEtape
    {
        private SolutionDisplay(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
            String solutionNumber = Integer.toString(questionCourante ) + ". ";
            switch (randNumPastille)
            {
                case 1:
                    solutionView.addTextActivite(solutionNumber + "Lucas a 1 an");
                    break;
                case 2:
                    solutionView.addTextActivite(solutionNumber + "Emma a 2 ans");
                    break;
                case 3:
                    solutionView.addTextActivite(solutionNumber + "Arthur a 3 ans");
                    break;
                case 4:
                    solutionView.addTextActivite(solutionNumber + "Lina a 4 ans");
                    break;
                case 5:
                    solutionView.addTextActivite(solutionNumber + "Héloise a 5 ans");
                    break;
                case 6:
                    solutionView.addTextActivite(solutionNumber + "Théo a 6 ans");
                    break;
                case 7:
                    solutionView.addTextActivite(solutionNumber + "Zoé a 7 ans");
                    break;
                case 8:
                    solutionView.addTextActivite(solutionNumber + "Hugo a 8 ans");
                    break;
                case 9:
                    solutionView.addTextActivite(solutionNumber + "Chloé a 9 ans");
                    break;
                default:
                    break;
            }

            timer.schedule(new EtapeInstruction(500), 0);
        }
    }
}