package com.evalutel.primval_desktop.Database;


import com.evalutel.primval_desktop.Ex.User;
import com.evalutel.primval_desktop.Profil;

import static java.lang.String.valueOf;

public class MyDataBase
{

    public DataBase database;

    int maxNotePageForIdProfil = 0;
    int maxDureePageForIdProfil = 0;
    int totalDureePageForIdProfil = 0;


    public MyDataBase(DatabaseDesktop database)
    {
        this.database = database;

    }


    public void insertProfil(int idprofil, String nom, String prenom, int age, String classe, int avatar, String photoPath)
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }


    public void updateprofil()
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }


    public void insertResultat(UnResultat resultat)
//    public void insertResultat(long duration, long dateResultat, String chapitre, String onglet, String page, String intitule, String points_obtenus, String points_possibles, String points_max)
    {
        long date = resultat.getDateResultat();
        long duree = resultat.getDuree();
        int chapitre = resultat.getChapitre();
        int onglet = resultat.getOnglet();
        int page = resultat.getPage();
        String intitule = resultat.getIntitule();
        int pointsObtenus = resultat.getPointsObtenus();
        int pointsPossibles = resultat.getPointsPossibles();
        int pointsMaxi = resultat.getPointsMaxi();
        int idProfil = resultat.getIdProfil();


        String intituleNew = intitule.replace("'", "''");
        String sqlQuery = "INSERT INTO RESULTAT " +
                "(id_profil, duree, date_resultat, chapitre, onglet, page, intitule, points_obtenus, points_possibles, points_max) " +
                "VALUES (" + idProfil + "," + duree + "," + date + "," + valueOf(chapitre) + "," + valueOf(onglet) + "," + valueOf(page) + "," + "'" + intituleNew + "'" + "," + "'" + valueOf(pointsObtenus) + "'" + "," + "'" + valueOf(pointsPossibles) + "'" + "," + "'" + valueOf(pointsMaxi) + "')";


        database.execute(sqlQuery);
    }

    public int getHighestNote(int chapitre, int onglet)
    {
        int highestNote = 0;

        String sqlQuery = "SELECT max(points_obtenus) from RESULTAT where chapitre = " + chapitre + " AND onglet = " + onglet;
        //String sqlQuery = "SELECT id_profil, id, points_obtenus from RESULTAT where chapitre = " + chapitre + " AND onglet = " + onglet;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            /*while (test.moveToNext())
            {
                int idProfil = test.getInt(1);
                int id = test.getInt(2);
                int pointObtenu = test.getInt(1);


                int ok = 5;
                ok++;
            }*/

            test.moveToNext();
            highestNote = test.getInt(1);

        }
        return highestNote;
    }


    public void updateResultat(int idResultatServer, int localResultat)
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }

    public int getMaxNotePageForIdProfil(Profil idProfil, int chapitre, int onglet, int page)
    {


        return maxNotePageForIdProfil;

    }

    public int getMaxDureePageForIdProfil(/*User idProfil,*/ int chapitre, int onglet/*, int page*/)
    {

        int maxDureePageForIdProfil = 0;

        String sqlQuery = "SELECT sum(duree) FROM RESULTAT WHERE  chapitre =" + chapitre + " AND onglet= " + onglet;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            /*while (test.moveToNext())
            {
                int idProfil = test.getInt(1);
                int id = test.getInt(2);
                int pointObtenu = test.getInt(1);


                int ok = 5;
                ok++;
            }*/

            test.moveToNext();
            maxDureePageForIdProfil = test.getInt(1);

            int ok = 5;
            ok++;

        }

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
