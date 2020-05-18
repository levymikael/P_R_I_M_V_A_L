package com.evalutel.primval_desktop.General;

import java.util.ArrayList;
import java.util.Random;


import com.evalutel.primval_desktop.ui_tools.MyPoint;

public class MyMath
{

    public static double distancePoints(double x1, double y1, double x2, double y2)
    {
        double dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return dist;
    }

    public static double distancePoints(MyPoint ptA, MyPoint ptB)
    {
        double dist = Math.sqrt(Math.pow(ptA.x - ptB.x, 2) + Math.pow(ptA.y - ptB.y, 2));
        return dist;
    }

//    public static double distancePoints(MyPoint ptA, PointF ptB)
//    {
//        double dist = Math.sqrt(Math.pow(ptA.x - ptB.x, 2) + Math.pow(ptA.y - ptB.y, 2));
//        return dist;
//    }

//    public static double distancePoints(PointF ptA, MyPoint ptB)
//    {
//        double dist = Math.sqrt(Math.pow(ptA.x - ptB.x, 2) + Math.pow(ptA.y - ptB.y, 2));
//        return dist;
//    }
//
//    public static double distancePoints(PointF ptA, PointF ptB)
//    {
//        double dist = Math.sqrt(Math.pow(ptA.x - ptB.x, 2) + Math.pow(ptA.y - ptB.y, 2));
//        return dist;
//    }

    public static double getAngle0_2PI(double x1, double y1, double x2, double y2)
    {
        double retour = 0;

        double x = x2 - x1;
        double y = y2 - y1;

        if ((x > 0) && (y >= 0))
        {
            retour = Math.atan(y / x);
        }
        else if ((x > 0) && (y < 0))
        {
            retour = Math.atan(y / x) + 2 * Math.PI;
        }
        else if (x < 0)
        {
            retour = Math.atan(y / x) + Math.PI;
        }
        else if ((x == 0) && (y > 0))
        {
            retour = Math.PI / 2.0;
        }
        else if ((x == 0) && (y < 0))
        {
            retour = 3 * Math.PI / 2.0;
        }

        return retour;
    }

    public static int approximation(double valeur)
    {
        int moins = (int) valeur;
        int plus = moins + 1;

        if (Math.abs(valeur - moins) < Math.abs(valeur - plus))
        {
            return moins;
        }
        else
        {
            return plus;
        }

    }

    public static boolean isIntegerPresentInTab(int tab[], int valeur)
    {
        boolean retour = false;
        for (int i = 0; i < tab.length; i++)
        {
            if (tab[i] == valeur)
            {
                retour = true;
                break;
            }
        }

        return retour;
    }

    public static boolean isIntegerPresentInTab(ArrayList<Integer> tab, int valeur)
    {
        boolean retour = false;
        for (int i = 0; i < tab.size(); i++)
        {
            if (tab.get(i) == valeur)
            {
                retour = true;
                break;
            }
        }

        return retour;
    }

    public static boolean isIntegerPresentInTab(int tab[], int size, int valeur)
    {
        boolean retour = false;
        for (int i = 0; i < size; i++)
        {
            if (tab[i] == valeur)
            {
                retour = true;
                break;
            }
        }

        return retour;
    }

    public static int[] genereTabAleatoire(int taille)
    {
        int tabRetour[];
        tabRetour = new int[taille];
        for (int i = 0; i < taille; i++)
        {
            tabRetour[i] = i + 1;
        }
        Random r = new Random();
        for (int i = 0; i < 100; i++)
        {
            int rand1 = r.nextInt(taille);
            int rand2 = r.nextInt(taille);
            int aux = tabRetour[rand1];
            tabRetour[rand1] = tabRetour[rand2];
            tabRetour[rand2] = aux;
        }
        return tabRetour;

    }

    public static int[] genereTabAleatoire(int taille, int min, int max)
    {
        int tabAux[];
        tabAux = new int[max];

        for (int i = 0; i < max; i++)
        {
            tabAux[i] = min + i;
        }


        Random r = new Random();
        for (int i = 0; i < 100; i++)
        {

            int rand1 = r.nextInt(max);
            int rand2 = r.nextInt(max);
            int aux = tabAux[rand1];
            tabAux[rand1] = tabAux[rand2];
            tabAux[rand2] = aux;
        }


        int tabRetour[];
        tabRetour = new int[taille];

        for (int i = 0; i < taille; i++)
        {
            tabRetour[i] = tabAux[i];
        }

        return tabRetour;

    }

    public static int[] genereTabAleatoire(int min, int max)
    {
        int tabRetour[];
        int taille = max - min;
        tabRetour = new int[taille];
        for (int i = 0; i < taille; i++)
        {
            tabRetour[i] = min + i + 1;
        }
        Random r = new Random();
        for (int i = 0; i < 100; i++)
        {
            int rand1 = r.nextInt(taille);
            int rand2 = r.nextInt(taille);
            int aux = tabRetour[rand1];
            tabRetour[rand1] = tabRetour[rand2];
            tabRetour[rand2] = aux;
        }
        return tabRetour;

    }

    public static int[] melangeTab(int[] tab)
    {
        int taille = tab.length;

        Random r = new Random();
        for (int i = 0; i < 100; i++)
        {

            int rand1 = r.nextInt(taille);
            int rand2 = r.nextInt(taille);
            int aux = tab[rand1];
            tab[rand1] = tab[rand2];
            tab[rand2] = aux;
        }
        return tab;

    }

    public static String[] melangeTab(String[] tab)
    {
        int taille = tab.length;

        Random r = new Random();
        for (int i = 0; i < 100; i++)
        {

            int rand1 = r.nextInt(taille);
            int rand2 = r.nextInt(taille);
            String aux = tab[rand1];
            tab[rand1] = tab[rand2];
            tab[rand2] = aux;
        }
        return tab;

    }

    public static int myRand(int min, int max)
    {
        Random rand = new Random();
        int retour = rand.nextInt(max - min + 1) + min;
        return retour;
    }

    public static float myRand(float min, float max)
    {
        Random rand = new Random();

        float retour = min + rand.nextFloat() * (max - min);

        return retour;
    }

    public static boolean isArrayContainsObject(Object obj, Object[] array)
    {
        boolean retour = false;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null)
            {
                if (array[i].equals(obj))
                {
                    retour = true;
                    break;
                }
            }

        }


        return retour;
    }

}
