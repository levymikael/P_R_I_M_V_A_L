package com.evalutel.primval_desktop.ui_tools;

public class PauseSingleton
{
    private static volatile PauseSingleton instance = null;
    public boolean isPause = false;

    private PauseSingleton()
    {
    }

    public final static PauseSingleton getInstance()
    {
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
        //d'éviter un appel couteux à synchronized,
        //une fois que l'instanciation est faite.
        if (PauseSingleton.instance == null)
        {
            // Le mot-clé synchronized sur ce bloc empèche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized (PauseSingleton.class)
            {
                if (PauseSingleton.instance == null)
                {
                    PauseSingleton.instance = new PauseSingleton();
                }
            }
        }
        return PauseSingleton.instance;
    }
}
