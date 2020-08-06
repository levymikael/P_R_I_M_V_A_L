package com.evalutel.primval_desktop.ui_tools;

import com.evalutel.primval_desktop.Database.DatabaseDesktop;
import com.evalutel.primval_desktop.Database.MyDataBase;

public class AppSingleton
{
    private static volatile AppSingleton instance = null;
    public MyDataBase myDataBase;

    private AppSingleton()
    {

        DatabaseDesktop databaseDesktop = new DatabaseDesktop();
        myDataBase = new MyDataBase(databaseDesktop);

    }

    public final static AppSingleton getInstance()
    {
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
        //d'éviter un appel couteux à synchronized,
        //une fois que l'instanciation est faite.
        if (AppSingleton.instance == null)
        {
            // Le mot-clé synchronized sur ce bloc empèche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized (AppSingleton.class)
            {
                if (AppSingleton.instance == null)
                {
                    AppSingleton.instance = new AppSingleton();
                }
            }
        }
        return AppSingleton.instance;
    }
}
