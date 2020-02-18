package com.evalutel.primval_desktop;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.evalutel.ui_tools.MyImageButton;

import java.util.ArrayList;
import java.util.Random;


public class ScreenEx1_2 extends ScreenOnglet
{

    private ArrayList<UneBille> billesList;
    private ArrayList<UnOiseau> oiseauxList;
    private ArrayList<UnePlancheNew> allPlanches;

    private ArrayList<Integer> numberOiseauxList;

    MyImageButton myButtonValidus;

    private UnePlancheNew planche1;
    ScreeenBackgroundImage bgScreenEx1_2;

    boolean isVisible = true;
    boolean isActive = false;

    //    Validus validus;
    Metronome metronome;

    boolean state = false;
    int rand_int, randNumOiseau;
    int cptOiseau, cptBille = 0;
    int firstPositionOiseauX, firstPositionOiseauY;
//    double mainDoigtX = 0.1 * uneMain.getWidth();
//    double mainDoigtY = 0.9 * uneMain.getHeight();

    EnonceView enonceView;


    public ScreenEx1_2()
    {
        super();

        int largeurBille = 200;
        int largeurPlanche = largeurBille * 4;

        bgScreenEx1_2 = new ScreeenBackgroundImage();
        bgScreenEx1_2.ScreeenBackgroundImage("Images/Chapitre1/mise_en_scene01.jpg");
        allDrawables.add(bgScreenEx1_2);

//        oiseauxList = getNumberOiseauxArList();

        reserveBilles = new ReserveBilles(screenWidth - 300, screenHeight - 300, 200, 200);
        reserveBilles.largeurBille = largeurBille;
        reserveBilles.setActive(true);
        allDrawables.add(reserveBilles);


        planche1 = new UnePlancheNew(screenWidth / 2 - largeurPlanche / 2, 0, largeurPlanche, largeurBille);
//        planche1.shouldReturnToReserve = true;
        allDrawables.add(planche1);

        allPlanches = new ArrayList<>();
        allPlanches.add(planche1);


        float enonceWidth = (screenWidth / 4) * 3;

        String numExercice = "1-2";
        String consigneExercice = "Faire correspondre des billes a des oiseaux, de 1 a 9";

        enonceView = new EnonceView(stage, 50, 2000, enonceWidth, numExercice, consigneExercice);
        allDrawables.add(enonceView);

//        validus = new Validus(0, screenHeight / 7, 300, 300);
//        allDrawables.add(validus);


        myButtonValidus = new MyImageButton(stage, "Images/vo00000.png", 300, 300);

        myButtonValidus.setPosition(0, screenHeight / 7);

        allDrawables.add(myButtonValidus);


        myButtonValidus.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                CheckValidus();
            }
        });


        metronome = new Metronome(0, 2 * screenHeight / 5, 300, 300);
        allDrawables.add(metronome);


        getNumberOiseauxArList();

        timer.schedule(new PresentationExercice(2000), 1000);

    }


    private class PresentationExercice extends TaskEtape
    {
        private PresentationExercice(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            enonceView.addTextEnonce("Place autant de billes que d'oiseaux que tu vois et demande a Mademoiselle Validus si c'est juste pour avoir un diamant.");
        }
    }


    private class EtapeAddOiseau extends TaskEtape
    {
        private EtapeAddOiseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptOiseau < oiseauxList.size())
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);
                int posY = 7 * screenHeight / 10;
                int posX = (2 * screenWidth / 10) + (int) (oiseau.animationWidth + oiseau.animationWidth / 15) * cptOiseau;

                oiseau.animateImage(1000, true, posX, posY, null, 500);
                cptOiseau++;
            }
        }
    }


    private class EtapeRemoveOiseau extends TaskEtape
    {
        private EtapeRemoveOiseau(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            if (cptOiseau < oiseauxList.size())
            {
                UnOiseau oiseau = oiseauxList.get(cptOiseau);
                int posX = screenWidth * 2;
                int posY = screenHeight * 2;

                oiseau.animateImage(1000, true, posX, posY, null, 500);
                cptOiseau--;
            }
        }
    }

    private class NewOiseauxNum extends TaskEtape
    {
        private NewOiseauxNum(long durMillis)
        {
            super(durMillis);
        }

        @Override
        public void run()
        {
            int lastOiseauNum = numberOiseauxList.get(numberOiseauxList.size() - 1);
            int diffOiseau = oiseauxList.size() - lastOiseauNum;
            System.out.println("etape new oiseau num");

            if (diffOiseau < 0)
            {
                for (int i = 0; i < diffOiseau; i++)
                {
                    int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
                    UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 200);
                    allDrawables.add(unOiseau);
                    oiseauxList.add(unOiseau);
                    timer.schedule(new EtapeAddOiseau(1000), 500);
                }
            }
            else
            {
                for (int i = 0; i < Math.abs(diffOiseau); i++)
                {

                    timer.schedule(new EtapeRemoveOiseau(1000), 500);

                    oiseauxList.remove(oiseauxList.size() - (Math.abs(diffOiseau)));
                }
            }
        }
    }


//    private class MoveMainToReserve1 extends TaskEtape
//    {
//        private MoveMainToReserve1(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            if (cptBille < billesList.size())
//            {
//                uneMain.setVisible(true);
//
//                float posXmain = reserveBilles.currentPositionX + reserveBilles.getWidth() / 2;
//                float posYf = reserveBilles.currentPositionY + reserveBilles.getHeight() / 2;
//                int posY = (int) posYf;
//
//                TaskEtape nextEtape = new DisplayBilleReserve(500);
//                uneMain.moveTo(durationMillis, (int) posXmain, posY, nextEtape, 1000);
//            }
//        }
//    }

//    private class EtapeDragBille extends TaskEtape
//    {
//        private EtapeDragBille(long durMillis)
//        {
//            super(durMillis);
//        }
//
//        @Override
//        public void run()
//        {
//            UneBille bille = billesList.get(cptBille);
//            bille.setVisible(true);
//            int posX = screenWidth / 2;
//            int posY = (int) planche1.getHeight() / 2;
//
////            TaskEtape nextEtape = new EtapeAddBille(1000);
//            TaskEtape nextEtape2 = new EtapeAddOiseau(1000);
//
//            if (cptBille == 0)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//
//                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 0);
//            }
//            else if (cptBille == 1)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, nextEtape2, 0);
//            }
//            else if (cptBille == 2)
//            {
//
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
//            }
//            else if (cptBille == 3)
//            {
//                bille.animateImage(durationMillis, true, (int) (posX - bille.getWidth() / 2), (int) (posY - bille.getWidth() / 2), nextEtape, 500);
//                uneMain.cliqueTo(durationMillis, posX, posY, null, 0);
//            }
//        }
//    }

    public void CheckValidus()
    {
        if (cptBille == oiseauxList.size())
        {
            enonceView.addTextEnonce("C'est bien continue ");

            //Remise diamant

            new NewOiseauxNum(1000);

        }
        else
        {
            enonceView.addTextEnonce("Tu t'es trompe essaie encore.");
        }
    }


    public int getRand_int()
    {
        Random rand = new Random();
        int oiseauMaxQuantity = 10;
        rand_int = rand.nextInt(oiseauMaxQuantity) + 1;

        return rand_int;
    }


    public ArrayList getNumberOiseauxArList()
    {
        randNumOiseau = getRand_int();

        oiseauxList = new ArrayList<>();

        numberOiseauxList = new ArrayList<>();


        if (numberOiseauxList.contains(randNumOiseau))
        {
            randNumOiseau = getRand_int();
        }
        else
        {
            numberOiseauxList.add(rand_int);

            firstPositionOiseauX = screenWidth + 200;
            firstPositionOiseauY = screenHeight + 200;
            System.out.println("rand_int :" + rand_int);

            for (int i = 0; i < rand_int; i++)
            {
                int firstPositionOiseauXNew = firstPositionOiseauX + (i * 250);
                UnOiseau unOiseau = new UnOiseau(firstPositionOiseauXNew, firstPositionOiseauY, 200, 200);
                allDrawables.add(unOiseau);
                oiseauxList.add(unOiseau);
                if (numberOiseauxList.size() > 1)
                {
                    timer.schedule(new NewOiseauxNum(1000), 500);
                }
                else
                {
                    timer.schedule(new EtapeAddOiseau(1000), 500);
                }
            }

            System.out.println(numberOiseauxList);
        }
        return oiseauxList;
    }


//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button)
//    {
//        int reversedScreenY = screenHeight - screenY;
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
//            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (screenHeight - screenY - objectTouched.getHeight() / 2));
//        }
//        return true;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button)
//    {
//        if (objectTouched != null)
//        {
//            if (objectTouched instanceof UneMain)
//            {
//                UneMain mainAux = (UneMain) objectTouched;
//                mainAux.touchUp(planche1, screenX, screenHeight - screenY);
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
        int reversedScreenY = screenHeight - screenY;
        mousePointerX = screenX;
        mousePointerY = reversedScreenY;

        if (reserveBilles.contains(screenX, reversedScreenY) && reserveBilles.isActive()) /*si bille part de la reserve*/
        {
            System.out.println("clickedOnContainer");
            UneBille billeAdded = new UneBille(reserveBilles.currentPositionX + (int) reserveBilles.animationWidth / 2, reserveBilles.currentPositionY + (int) reserveBilles.animationHeight / 2, reserveBilles.largeurBille, reserveBilles.largeurBille);
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
            objectTouched.setPosition((int) (screenX - objectTouched.getWidth() / 2), (int) (screenHeight - screenY - objectTouched.getHeight() / 2));
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (objectTouched != null)
        {
            //AnimationImageNew animationImageNewAux = (AnimationImageNew) objectTouched;
            if (objectTouched instanceof UneBille)
            {
                UneBille billeAux = (UneBille) objectTouched;
                billeAux.touchUp(allPlanches, screenX, screenHeight - screenY);


                if (planche1.contains(screenX, screenHeight - screenY))
                {
                    cptBille++;
                    System.out.println("cptbille :" + cptBille);
                }

                else /*si bille pas deposee dans planche*/
                {
                    objectTouched.setPosition(firstPositionX, firstPositionY);
                    if (billeAux.plancheNew != null)
                    {
                        if (billeAux.plancheNew.shouldReturnToReserve)
                        {
                            billeAux.setPosition(100000, 100000);
                            allDrawables.remove(billeAux);
                            billeAux.plancheNew = null;
                        }
                        else
                        {
                            planche1.addBilleAndOrganize(billeAux);

                        }
                    }
                    else
                    {
                        allDrawables.remove(billeAux);
                        billeAux.setPosition(100000, 100000);
                    }
                }
            }

        }
        objectTouched = null;
        return false;
    }
}