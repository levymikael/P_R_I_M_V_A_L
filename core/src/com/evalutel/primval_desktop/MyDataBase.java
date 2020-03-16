package com.evalutel.primval_desktop;


import de.bitbrain.jpersis.JPersis;
import de.bitbrain.jpersis.drivers.Driver;
import de.bitbrain.jpersis.drivers.sqllite.SQLiteDriver;

public class MyDataBase
{

    Driver driver = new SQLiteDriver("database.sql");
    JPersis jpersis = new JPersis(driver);

    User user;

    UserMapper mapper = jpersis.map(UserMapper.class);

    int maxNotePageForIdProfil = 0;
    int maxDureePageForIdProfil = 0;
    int totalDureePageForIdProfil = 0;


    public void MyDataBase()
    {
        mapper.insert(user);
        mapper.count();
        mapper.findByUserName("userTest");

    }


    public int getMaxNotePageForIdProfil(Profil idProfil, int chapitre, int onglet, int page)
    {


        return maxNotePageForIdProfil;

    }

    public int getMaxDureePageForIdProfil(User idProfil, int chapitre, int onglet, int page)
    {


        return maxDureePageForIdProfil;
    }

    public float getTotalDureePageForIdProfil(User idProfil, int chapitre, int onglet, int page)
    {

        return totalDureePageForIdProfil;
    }

    public void removeOnglet(int onglet)
    {

    }


}
