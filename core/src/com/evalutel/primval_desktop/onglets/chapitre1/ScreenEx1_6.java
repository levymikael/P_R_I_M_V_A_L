package com.evalutel.primval_desktop.onglets.chapitre1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteViewTest;
import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.General.MyMath;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.SacDeBougies;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UnGateauAnniversaire;
import com.evalutel.primval_desktop.UneBougie;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.ui_tools.MyPoint;

import java.util.ArrayList;


public class ScreenEx1_6 extends ScreenOnglet implements InputProcessor
{
    private ArrayList<UneBougie> bougiesList;
    private ArrayList<Texture> pastilleList;
    private ArrayList<UnGateauAnniversaire> allGateaux;

    private UneBougie bougieRectification;

    protected CalculetteViewTest calculetteViewTest;

    private UnGateauAnniversaire gateauAnniversaire;
    ScreeenBackgroundImage bgScreenEx1_6;

    TextureRegion textureRegion;

    int cptBougie = 0;
    private int randNumPastille;

    TextButton.TextButtonStyle styleTest;

//    int posX, posY;

    int[] numPastilleArray;

    int failedAttempts, currrentBougiesNumber = 0;

    boolean isInCorrection = false;

    boolean afterCorrection, isAllActive, displayPastille = false;

    DatabaseDesktop dataBase;

    String consigneExercice, nbInput;

    Label currentLabel;
    Drawable drawableAux;


    public ScreenEx1_6(Game game, DatabaseDesktop dataBase)
    {
        super(game, dataBase, 1, 6, true);

        this.dataBase = dataBase;

        bgScreenEx1_6 = new ScreeenBackgroundImage("Images/Onglet_1_6/anniversaire.jpg");
        allDrawables.add(bgScreenEx1_6);

        sacDeBougies = new SacDeBougies(5.3f * MyConstants.SCREENWIDTH / 6f, 9 * MyConstants.SCREENHEIGHT / 11, (float) (largeurBougie * 1.5), (float) (largeurBougie * 1.5));
        sacDeBougies.largeurBille = largeurBille;
        sacDeBougies.isActive();
        sacDeBougies.setActive(false);
        allDrawables.add(sacDeBougies);
        myCorrectionAndPauseGeneral.addElements(sacDeBougies);
        allCorrigibles.add(sacDeBougies);

        int gateauWidth = MyConstants.SCREENWIDTH / 2;
        int gateauHeight = (int) (gateauWidth * (266f / 462f));

        gateauAnniversaire = new UnGateauAnniversaire(3 * MyConstants.SCREENWIDTH / 10 - largeurGateau / 10, MyConstants.SCREENHEIGHT / 20, gateauWidth, gateauHeight);
        gateauAnniversaire.shouldReturnToReserve = true;
        allDrawables.add(gateauAnniversaire);
        myCorrectionAndPauseGeneral.addElements(gateauAnniversaire);
        allCorrigibles.add(gateauAnniversaire);

        bougiesList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            UneBougie bougie = new UneBougie(sacDeBougies.currentPositionX, sacDeBougies.currentPositionY, sacDeBougies.largeurBille);

            sacDeBougies.addBougieToReserve(bougie);
            allDrawables.add(bougie);
            objectTouchedList.add(bougie);
            bougie.setVisible(false);
            myCorrectionAndPauseGeneral.addElements(bougie);
            allCorrigibles.add(bougie);
            bougiesList.add(bougie);
        }

        allGateaux = new ArrayList<>();
        allGateaux.add(gateauAnniversaire);


        String numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();
        consigneExercice = "Un gâteau pour plusieurs anniversaires";


        float posEnonceX = (MyConstants.SCREENWIDTH - activiteWidth) / 2f;
        float posSolutionX = posEnonceX + activiteWidth / 2f;

        activiteView = new ActiviteView(stage, posEnonceX, activiteWidth * 42 / 1626, activiteWidth / 2f, "activite");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);

        solutionView = new ActiviteView(stage, posSolutionX, activiteWidth * 42 / 1626, activiteWidth / 2f, "solution");
        allDrawables.add(solutionView);
        myCorrectionAndPauseGeneral.addElements(solutionView);


        int noteMax = db.getHighestNote(1, 6);

        String noteMaxObtenue = noteMax + "/9";

        exoConsigneLabel = new Label(consigneExercice, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46);


        tableTitre.add(exoNumLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 25).padLeft(MyConstants.SCREENWIDTH / 46);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22);

        stage.addActor(tableTitre);

        calculetteViewTest = new CalculetteViewTest(stage, validusAnimated);
        allDrawables.add(calculetteViewTest);
        calculetteViewTest.setActive(false);
        myCorrectionAndPauseGeneral.addElements(calculetteViewTest);

        pastilleList = pastilleToDisplay();

        numPastilleArray = MyMath.genereTabAleatoire(9);

        resultatExercice = new UnResultat("Primval", 1, 6, 0, consigneExercice, 9, 0, dateTest, 0, 0, 0, 123);


        uneMain.setPosition(MyConstants.SCREENWIDTH / 2, MyConstants.SCREENHEIGHT / 3);


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
                batch.draw(textureRegion, MyConstants.SCREENWIDTH / 6, 7 * MyConstants.SCREENHEIGHT / 15, MyConstants.SCREENHEIGHT / 4, MyConstants.SCREENHEIGHT / 4 * (268 / 234));
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
            uneMain.imageDown();

            MyTimer.TaskEtape nextEtape = new EtapeInstruction(2_000, 1_000);

            metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_Ungateaupourplusieursanniversaires.mp3", nextEtape);
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
                metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_lafamilleDubonheur.mp3", new BirthdayKiDPicDisplay(1_000));
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
        private BirthdayKiDPicDisplay(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

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

            MyTimer.TaskEtape nextEtape = new InputClavier(500);


            switch (randNumPastille)
            {
                case 1:
                    activiteView.setTextActivite("Lucas ne marche pas encore, et il accourt à 4 pattes pour souffler sa 1ère bougie. Lucas a 1 an");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_LucasNeMarchePasEncore.mp3", nextEtape);
                    break;

                case 2:
                    activiteView.setTextActivite(" Emma est fière car cette année elle va souffler ses 2 bougies comme une grande. L'année dernière elle avait eu du mal avec la 1ère. Emma a 2 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_Onglet6_EmmaEstFierecarcetteannee.mp3", nextEtape);
                    break;

                case 3:
                    activiteView.setTextActivite("Arthur est très ému, car tout le monde le regarde, mais il souffle vaillamment ses 3 bougies. Arthur a 3 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_Arthuresttresemu.mp3", nextEtape);
                    break;

                case 4:
                    activiteView.setTextActivite("Lina est turbulente, après avoir renversé la tasse de sa cousine, elle se dirige vers son gâteau et ses 4 bougies, Lina a 4 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_Onglet6_LinaestTurbulente.mp3", nextEtape);
                    break;

                case 5:
                    activiteView.setTextActivite("Héloïse dit à sa poupée qu'elle est maintenant une grande fille de 5 ans. Héloïse a 5 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_Heloiseditasapoupee.mp3", nextEtape);
                    break;

                case 6:
                    activiteView.setTextActivite("Théo est fier, car à 6 ans il va bientôt aller à la grande école. Théo a 6 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_Onglet6_Theoestfiercara6ans.mp3", nextEtape);
                    break;

                case 7:
                    activiteView.setTextActivite("Zoé pose son petit chat dans son panier afin de pouvoir souffler tranquillement ses 7 bougies. Zoé a 7 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_Onglet6_Zoeposesonpetitchat.mp3", nextEtape);
                    break;

                case 8:
                    activiteView.setTextActivite("Hugo est bagarreur, après un croche-pied à son petit frère, il avance pour souffler ses 8 bougies. Hugo a 8 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_Onglet6_Hugoestbagarreur.mp3", nextEtape);
                    break;

                case 9:
                    activiteView.setTextActivite("Chloé est très inquiète, elle se demande si elle va pouvoir souffler ses 9 bougies d'un seul coup. Chloé a 9 ans");
                    metrologue.metrologuePlaySound("Sounds/Onglet1_6/chap1_onglet6_ChloeEstTresInquiete.mp3", nextEtape);
                    break;

                default:
                    break;
            }


        }
    }


    private class InputClavier extends MyTimer.TaskEtape
    {
        private InputClavier(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        private InputClavier(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            calculetteViewTest.setActive(true);
            validusAnimated.setActive(true);

            activiteView.addTextActivite("Tape avec ton doigt sur la boite de bougies. Une bougie se posera sur le gâteau. Tape sur le clavier le nombre de bougies que tu as placé puis Valide. Tape sur une bougie sur le gâteau si tu veux la retirer");
            if (questionCourante == 0)
            {
                metrologue.metrologuePlaySound("Sounds/Onglet1_6/Chap1_onglet6_Tapeavectondoigt.mp3");
            }


            validusAnimated.etapeCorrection = new PressValidate(0);
            calculetteViewTest.etapeCorrection = new PressValidate(0);

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
            String txtTape = calculetteViewTest.getInput();

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
            }
            else
            {
                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    calculetteViewTest.setActive(false);
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
                    validusAnimated.validusPlaySound("Sounds/Onglet1_6/Chap1_ong6_Tutestrompecliquesurlaboitepourajouterdesbougies.mp3");
                }
                else if (gateauAnniversaire.getNumberBougies() > randNumPastille)
                {
                    validusAnimated.validusPlaySound("Sounds/Onglet1_6/Chap1_Onglet6_tutestrompe,cliquesurlegateaupourenleverdesbougies.mp3");
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
            uneMain.setVisible(true);

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(randNumPastille);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new ClickMainToCalculette(1_500, 1_000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);
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

            MyPoint buttonPosition = calculetteViewTest.buttonPosition(randNumPastille);

            float posX = buttonPosition.x;
            float posY = buttonPosition.y;

            MyTimer.TaskEtape nextEtape = new MoveMainToValidate(500);

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteViewTest.textDisplay(randNumPastille);
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
            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new ClickOnValidate(1_000, 1_000);

            uneMain.moveTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

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
            uneMain.setVisible(true);

            MyPoint buttonValidatePosition = calculetteViewTest.calculetteValidateAndDisplay();

            float posX = buttonValidatePosition.x;
            float posY = buttonValidatePosition.y;

            MyTimer.TaskEtape nextEtape = new MainDisappear(500);

            styleTest = calculetteViewTest.validerBouton.getStyle();

            drawableAux = styleTest.up;
            styleTest.up = styleTest.down;

            uneMain.cliqueTo(durationMillis, (int) posX, (int) posY, nextEtape, 1_000);

            calculetteViewTest.textRemove();
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
            calculetteViewTest.textRemove();

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
                calculetteViewTest.textRemove();

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
            Texture pastilleTxture = new Texture("Images/Onglet_1_6/pastille" + (i + 1) + ".png");
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
            if (bougie != null)
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
            switch (randNumPastille)
            {
                case 1:
                    solutionView.addTextActivite("Lucas a 1 an");
                    break;
                case 2:
                    solutionView.addTextActivite("Emma a 2 ans");
                    break;
                case 3:
                    solutionView.addTextActivite("Arthur a 3 ans");
                    break;
                case 4:
                    solutionView.addTextActivite("Lina a 4 ans");
                    break;
                case 5:
                    solutionView.addTextActivite("Héloise a 5 ans");
                    break;
                case 6:
                    solutionView.addTextActivite("Théo a 6 ans");
                    break;
                case 7:
                    solutionView.addTextActivite("Zoé a 7 ans");
                    break;
                case 8:
                    solutionView.addTextActivite("Hugo a 8 ans");
                    break;
                case 9:
                    solutionView.addTextActivite("Chloé a 9 ans");
                    break;
                default:
                    break;
            }

            timer.schedule(new EtapeInstruction(500), 0);
        }
    }
}