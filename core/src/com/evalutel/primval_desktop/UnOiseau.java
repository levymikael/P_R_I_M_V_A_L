package com.evalutel.primval_desktop;

import java.util.ArrayList;

public class UnOiseau extends AnimationImageNew /*implements MyDrawInterface */
{


    public UnOiseau(int startPositionX, int startPositionY, float animationHeight, float animationWidth)
    {
        super(getAnimationOiseau(), startPositionX, startPositionY, animationHeight, animationWidth);

//        setColor(Color.GREEN);
//
//        setTouchable(Touchable.enabled);
//
//        setWidth(200);
//        setHeight(450);
//
//        addListener(new DragListener()
//        {
//            @Override
//            public void dragStart(InputEvent event, float x, float y, int pointer) {
//                super.dragStart(event, x, y, pointer);
//
//                firstX = x;
//                firstY = y;
//
//                currentX = getX();
//                currentY = getY();
//
//                posX =  currentX;
//                posY =  currentY;
//            }
//
//            @Override
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                super.drag(event, x, y, pointer);
//
//                //moveBy(x-firstX, y - firstY);
//
//                moveBy(x - getWidth() / 2, y - getHeight() / 2);
//
//                //posX =  x;
//                //posY =  y;
//
//                posX = getX();
//                posY = getY();
//
//            }
//
//            @Override
//            public void dragStop(InputEvent event, float x, float y, int pointer) {
//                super.dragStop(event, x, y, pointer);
//
//                //moveBy(x-firstX, y - firstY);
//
//                setX(posX);
//                setY(posY);
//            }
//        });

    }

    private static ArrayList<String> getAnimationOiseau()
    {
        ArrayList<String> imgOiseauPaths = new ArrayList<>();

        for (int i = 0; i < 6; i++)
        {
            String imgaux = "Images/oiseau/oiseau1_1_00000" + i + ".png";
            imgOiseauPaths.add(imgaux);
        }

        return imgOiseauPaths;

    }

}
