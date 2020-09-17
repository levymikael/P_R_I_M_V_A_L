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
import com.evalutel.primval_desktop.ActiviteView;
import com.evalutel.primval_desktop.CalculetteView;
import com.evalutel.primval_desktop.Database.MyDataBase;
import com.evalutel.primval_desktop.Database.UnResultat;
import com.evalutel.primval_desktop.Dices;
import com.evalutel.primval_desktop.General.MyConstants;
import com.evalutel.primval_desktop.MyTimer;
import com.evalutel.primval_desktop.ScreeenBackgroundImage;
import com.evalutel.primval_desktop.UneCase;
import com.evalutel.primval_desktop.UnePastelle;
import com.evalutel.primval_desktop.UneTrace;
import com.evalutel.primval_desktop.ValidusAnimated;
import com.evalutel.primval_desktop.onglets.ScreenOnglet;
import com.evalutel.primval_desktop.ui_tools.AppSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class ScreenEx2_4 extends ScreenOnglet implements InputProcessor
{
    private CalculetteView calculetteView;
    //    private float posX, posY;
    ArrayList<UneCase> caseList = new ArrayList<>();
    ArrayList<UnePastelle> pastelleList = new ArrayList<>();
    ArrayList<UnePastelle> freePastelleList = new ArrayList<>();
    ArrayList<UneTrace> traceList = new ArrayList<>();

    TextButton.TextButtonStyle styleTest;
    private Drawable drawableAux;

    private int caseCorrected, failedAttempts, diceRenewal;

    UnePastelle currentPastelle = null;
    UneCase currentCase = null;

    String currentColor;

    float caseWidth, caseHeight;
    ArrayList<String> contentArray = new ArrayList<>();
    ArrayList<Integer> valueArray = new ArrayList<>();

    private String[] nbCombinaisonArrayString = {"3", "1 + 2", "2 + 1", "4", "2 + 2", "3 + 1", "5", "2 + 3", "1 + 4", "6", "1 + 5", "2 + 4", "3 + 3", "7", "1 + 6", "2 + 5", "3 + 4", "8", "1 + 7", "2 + 6", "3 + 5", "4 + 4", "9", "1 + 8", "2 + 7", "3 + 6", "4 + 5"};

    private int[] nbCombinaisonArrayValues = {3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9};

    boolean isAllActive, isCaseClicked = false;
    Map<String, Integer> valueMap;


    private Dices dice1;

    private String nbInput;
    //    //    private boolean isAllActive;
    private boolean touchValidate = false;


    ScreenEx2_4(final Game game, String ongletTitre)
    {
        super(game, 2, 4, true, MyConstants.noteMaxChap2[2]);

        ScreeenBackgroundImage bgScreenEx1_1 = new ScreeenBackgroundImage("Images/onglet2_3/des_fond.jpg");
        allDrawables.add(bgScreenEx1_1);

        numExercice = super.resultatExercice.getChapitre() + "-" + resultatExercice.getOnglet();

        float posEnonceX = (MyConstants.SCREENWIDTH - activiteWidth) / 2f;
        float posSolutionX = posEnonceX + activiteWidth / 2f;

        activiteView = new ActiviteView(stage, posEnonceX, activiteWidth * 42 / 1626, activiteWidth /*/ 2f*/, "enonce");
        allDrawables.add(activiteView);
        myCorrectionAndPauseGeneral.addElements(activiteView);
//
//        solutionView = new ActiviteView(stage, posSolutionX, activiteWidth * 42 / 1626, activiteWidth / 2f, "solution");
//        allDrawables.add(solutionView);
//        myCorrectionAndPauseGeneral.addElements(solutionView);

        AppSingleton appSingleton = AppSingleton.getInstance();
        MyDataBase db = appSingleton.myDataBase;

        int noteMax = db.getHighestNote(2, 4);

        String noteMaxObtenue = noteMax + "/27";

        exoConsigneLabel = new Label(ongletTitre, labelStyleComic);
        exoNumLabel = new Label(numExercice, labelStyleArial);
        highestMarkObtainedLabel = new Label(noteMaxObtenue, labelStyle3);
        highestMarkObtainedLabel.setWidth(MyConstants.SCREENWIDTH / 46f);

        tableTitre.add(exoNumLabel).width(MyConstants.SCREENWIDTH / 25f).padLeft(MyConstants.SCREENWIDTH / 46f);
        tableTitre.add(exoConsigneLabel).width(activiteWidth - MyConstants.SCREENWIDTH / 9f);
        tableTitre.add(highestMarkObtainedLabel).align(Align.center).width(MyConstants.SCREENWIDTH / 22f);

        stage.addActor(tableTitre);

        resultatExercice = new UnResultat("Primval", 2, 4, 0, ongletTitre, 18, 0, dateTest, 0, 0, 0, 123);

        caseWidth = MyConstants.SCREENWIDTH / 20f;
        caseHeight = caseWidth * 1.5f;

        createCase();
        positionPastelPens();


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


        timer.schedule(new PresentationMetrologue(3_000), 1_000);
    }

    private void createCase()
    {
        for (int i = 0; i < 27; i++)
        {
            final UneCase uneCase = new UneCase(stage, nbCombinaisonArrayString[i], caseWidth, caseHeight, nbCombinaisonArrayValues[i]);
            caseList.add(uneCase);
            objectTouchedList.add(uneCase);

            uneCase.setActive(false);
            uneCase.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    super.clicked(event, x, y);
                    System.out.println(uneCase.getCaseValue() + "" + uneCase.isActive());
                    System.out.println(uneCase.getCasePositionList());
                    if (uneCase.isActive())
                    {
                        isCaseClicked = true;

                        if (isCaseClicked)
                        {
                            currentCase = uneCase;

                            if (!currentCase.getAlreadyColored())
                            {
                                checkCaseContent(uneCase.getCaseContent(), uneCase.getCaseValue(), uneCase.getCasePositionList());
                            }
                            else
                            {
                                validusAnimated.validusPlaySound("Sounds/Onglet2_4/Ong2_4_Validus_CetteCaseEstDejaColoree.mp3");
                                currentCase = null;
                            }

                        }


                    }
                }

            });
            myCorrectionAndPauseGeneral.addElements(uneCase);
        }

        float firstPositioncaseX = MyConstants.SCREENWIDTH / 5f;
        float firstPositioncaseY = 2 * MyConstants.SCREENHEIGHT / 3f;

        float positionYdeuxiemeLigne = firstPositioncaseY - (caseHeight * 1.3f);
        float positionYtroisiemeLigne = positionYdeuxiemeLigne - (caseHeight * 1.3f);

        Collections.shuffle(caseList);

        for (
                int j = 0; j < caseList.size(); j++)

        {
            UneCase caseAux = caseList.get(j);

            float interSpaceBetweenEachCaseX = caseAux.getWidth() / 3f;

            float firstPositioncaseXNew = firstPositioncaseX + (j * (caseAux.getWidth() + interSpaceBetweenEachCaseX));

            if ((j > 8) && (j <= 17))
            {
                firstPositioncaseY = positionYdeuxiemeLigne;
                firstPositioncaseXNew = firstPositioncaseX + ((j - 9) * (caseAux.getWidth() + interSpaceBetweenEachCaseX));
            }
            else if (j > 17)
            {
                firstPositioncaseY = positionYtroisiemeLigne;
                firstPositioncaseXNew = firstPositioncaseX + ((j - 18) * (caseAux.getWidth() + interSpaceBetweenEachCaseX));
            }
            caseAux.setPosition(firstPositioncaseXNew, firstPositioncaseY);
            caseAux.setCasePositon(j);
        }
    }

    public void positionPastelPens()
    {
        String[] pastelColorName = {"bleu", "gris", "jaune", "marron", "rouge", "vert", "violet_clair"};

        float pastelleWidth = MyConstants.SCREENWIDTH / 4f;
        float pastelleHeight = pastelleWidth * (117f / 606f);

        for (int i = 0; i < pastelColorName.length; i++)
        {
            float pastelleAuxPositionY = .7f * MyConstants.SCREENHEIGHT - (i * (pastelleHeight * 1.2f));

            final UnePastelle pastelleAux = new UnePastelle(stage, pastelleWidth, pastelleHeight, pastelColorName[i], pastelleAuxPositionY);
            pastelleList.add(pastelleAux);
            freePastelleList.add(pastelleAux);
            objectTouchedList.add(pastelleAux);

            allDrawables.add(pastelleAux);
            pastelleAux.setActive(true);

            pastelleAux.addListener(
                    new ClickListener()
                    {
                        @Override
                        public void clicked(InputEvent event, float x, float y)
                        {
                            super.clicked(event, x, y);

                            if (isAllActive)
                            {
                                if (pastelleAux.isActive())
                                {
                                    currentColor = pastelleAux.getPastelleCouleur();

                                    currentPastelle = pastelleAux;


                                    isAllActive = false;
                                    setAllCaseActive(true);
                                }
                            }
                        }
                    });

        }

        positionTrace();
    }


    public void checkCaseContent(String content, int value, int casePositionList)
    {
        int ok = 5;
        ok++;

//        if (contentArray.size() >= 1 && valueArray.size() >= 1)
//        {
//        if (currentPastelle.getPastelleValue() == value)
//        {
//
//            int firstValue = valueArray.get(0);
//
//
//            if (firstValue == value && !caseList.get(casePositionList).getAlreadyColored())
//            {
//                addDiamonds(1);
//                caseList.get(casePositionList).setCaseCouleurFond(currentColor);
//                contentArray.add(content);
//                valueArray.add(value);
//                caseList.get(casePositionList).setAlreadyColored();
//                currentPastelle.setValuePastelle(value);
//
//            }
//
//            else if (firstValue != value && !caseList.get(casePositionList).getAlreadyColored())
//            {
//                System.out.println("pas mme valeur");
//
//                if (failedAttempts == 1)
//                {
//                    myCorrectionAndPauseGeneral.correctionStart();
//
//                    validusAnimated.isActive = false;
//                    caseCorrected = casePositionList;
//                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
//                    failedAttempts = 0;
//                    currentCase = caseList.get(casePositionList);
//
//                    addPierres(1);
//                }
//                failedAttempts++;
//
//            }
//        }

//        else
//        {
        if (currentPastelle.getPastelleValue() == 0 && !currentCase.getAlreadyColored())
        {
            if (!isValuePastelleAlreadyAllocated(value))
            {
                contentArray.add(content);
                valueArray.add(value);
                addDiamonds(1);
                currentCase.setCaseCouleurFond(currentColor);
                currentCase.setAlreadyColored();
                addDiamonds(1);
                currentPastelle.setValuePastelle(currentCase.getCaseValue());
                for (int i = 0; i < traceList.size(); i++)
                {
                    UneTrace traceAux = traceList.get(i);
                    if (currentColor == traceAux.getTraceCouleur())
                    {
                        traceAux.setTraceValue(value);
                    }
                }
                freePastelleList.remove(currentPastelle);

                currentCase = null;
            }
            else
            {
                System.out.println("pas mme valeur");

                if (failedAttempts == 1)
                {
                    myCorrectionAndPauseGeneral.correctionStart();

                    validusAnimated.isActive = false;
                    caseCorrected = casePositionList;
                    validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
                    failedAttempts = 0;
//                    currentCase = caseList.get(casePositionList);

                    addPierres(1);
                }
                failedAttempts++;
            }

//            }
        }
        else if (currentPastelle.getPastelleValue() == currentCase.getCaseValue() && !currentCase.getAlreadyColored())
        {
            contentArray.add(content);
            valueArray.add(value);
            addDiamonds(1);
            caseList.get(casePositionList).setCaseCouleurFond(currentColor);
            caseList.get(casePositionList).setAlreadyColored();
            addDiamonds(1);
            currentPastelle.setValuePastelle(value);
            for (int i = 0; i < traceList.size(); i++)
            {
                UneTrace traceAux = traceList.get(i);
                if (currentColor == traceAux.getTraceCouleur())
                {
                    traceAux.setTraceValue(value);
                }
            }
            currentCase = null;
        }

        else if (!caseList.get(casePositionList).getAlreadyColored() && currentPastelle.getPastelleValue() != value)
        {
            if (failedAttempts == 1)
            {
                myCorrectionAndPauseGeneral.correctionStart();

                validusAnimated.isActive = false;
                caseCorrected = casePositionList;
                validusAnimated.validusPlaySound("Sounds/Validus/Voici la correction.mp3", new EtapeRectification(2_000));
                failedAttempts = 0;

                addPierres(1);
            }
            failedAttempts++;
        }

    }

    public boolean isValuePastelleAlreadyAllocated(int value)
    {
        boolean isValue = false;

        for (int i = 0; i < traceList.size(); i++)
        {
            if (traceList.get(i).getTraceValue() == value)
            {
                isValue = true;
            }
        }
        return isValue;
    }

    public void setValueToPastelle(String couleur, int value)
    {
        for (int i = 0; i < pastelleList.size(); i++)
        {
            UnePastelle pastelleAux = pastelleList.get(i);
            if (pastelleAux.getPastelleCouleur().equals(couleur))

            {
                pastelleAux.setValuePastelle(value);
            }
        }
    }


    public void setColorForCaseCorrection(int value)
    {
        boolean noPastelleForThisValue = false;
        for (int i = 0; i < pastelleList.size(); i++)
        {
            UnePastelle pastelleAux = pastelleList.get(i);
            if (pastelleAux.getPastelleValue() == value)
            {
                currentPastelle = pastelleAux;
                noPastelleForThisValue = true;
            }

        }
        if (!noPastelleForThisValue)
        {
            currentPastelle = freePastelleList.get(0);
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
            timer.schedule(new NewCaseColor(1_000), 1_000);
        }
    }


    private class NewCaseColor extends MyTimer.TaskEtape
    {
        private NewCaseColor(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float casePositionX = caseList.get(caseCorrected).getCasePositionXY().x;
            float casePositionY = caseList.get(caseCorrected).getCasePositionXY().y;

            uneSouris.setVisible(true);

            MyTimer.TaskEtape nextEtape = new ClickOnCaseToColor(2_500);

            uneSouris.moveTo(durationMillis, casePositionX, casePositionY, nextEtape, 1_000);


        }
    }

    private class ClickOnCaseToColor extends MyTimer.TaskEtape
    {
        private ClickOnCaseToColor(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            float casePositionX = caseList.get(caseCorrected).getCasePositionXY().x;
            float casePositionY = caseList.get(caseCorrected).getCasePositionXY().y;


            uneSouris.cliqueTo(durationMillis, casePositionX, casePositionY, null, 1_000);

            currentPastelle.pastelleInAndOut(false);
            currentPastelle = null;

            setColorForCaseCorrection(currentCase.getCaseValue());
            /*Selection de la nouvelle pastelle*/
            currentPastelle.pastelleInAndOut(true);

         String newColor =    currentPastelle.getPastelleCouleur();

         currentCase.setCaseCouleurFond(newColor);


        }
    }

    public void positionTrace()
    {
        float traceWidth = MyConstants.SCREENWIDTH / 4f;
        float traceHeight = traceWidth * (117f / 606f);

        String[] traceColorName = {"bleufonce", "gris", "jaune", "marron", "rouge", "vert", "violet"};

        for (int i = 0; i < traceColorName.length; i++)
        {
            float traceAuxPositionY = pastelleList.get(i).positionY;

            final UneTrace traceAux = new UneTrace(stage, traceWidth, traceHeight, traceColorName[i], traceAuxPositionY);
            traceList.add(traceAux);
            objectTouchedList.add(traceAux);
            allDrawables.add(traceAux);
            traceAux.setActive(true);
            traceAux.setVisible(false);
        }


    }


    public void setAllCaseActive(boolean active)
    {
        for (int i = 0; i < caseList.size(); i++)
        {
            UneCase caseAux = caseList.get(i);
            caseAux.setActive(active);
        }
    }

    public void setAllPastelleActive(boolean active)
    {
        for (int i = 0; i < pastelleList.size(); i++)
        {
            UnePastelle pastelleAux = pastelleList.get(i);
            pastelleAux.setActive(active);
        }
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
            MyTimer.TaskEtape nextEtape = new EtapeIntermediaire(3_000, 1_500);

            metrologue.metrologuePlaySound("Sounds/Onglet2_3/Chap2_Onglet3 - Total dun lancer de 2 des.mp3", nextEtape);
            isAllActive = true;
        }
    }

    private class EtapeIntermediaire extends MyTimer.TaskEtape
    {
        private EtapeIntermediaire(long durMillis, long delay)
        {
            super(durMillis, delay);
        }

        @Override
        public void run()
        {
//            setAllCaseActive(true);
            setAllPastelleActive(true);
            isAllActive = true;


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


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        int reversedScreenY = MyConstants.SCREENHEIGHT - screenY;
        mousePointerX = -screenX;
        mousePointerY = reversedScreenY;

        if (objectTouched instanceof UnePastelle)
        {
            int ok = 5;
            ok++;
            for (int i = 0; i < pastelleList.size(); i++)
            {

                UnePastelle pastelleAux = pastelleList.get(i);
                if (pastelleAux.isTouched(mousePointerX, mousePointerY))
                {
                    currentColor = pastelleAux.getPastelleCouleur();

                    System.out.println(currentColor);
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

        if ((objectTouched != null) && (objectTouched.isActive()))
        {
            if (objectTouched instanceof ValidusAnimated)
            {
                if (validusAnimated.contains(mousePointerX, mousePointerY))
                {
                    validusAnimated.touchUp(mousePointerX, mousePointerY);
                }
            }
            else if (objectTouched instanceof UnePastelle)
            {

                int ok = 5;
                ok++;
                for (int i = 0; i < pastelleList.size(); i++)
                {

                    UnePastelle pastelleAux = pastelleList.get(i);
                    if (pastelleAux.isTouched(mousePointerX, mousePointerY))
                    {
                        currentColor = pastelleAux.getPastelleCouleur();

                        System.out.println(currentColor);
                    }
                }
            }

        }
        objectTouched = null;
        return false;
    }
}