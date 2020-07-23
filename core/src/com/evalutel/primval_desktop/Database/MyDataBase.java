package com.evalutel.primval_desktop.Database;


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
                "VALUES (" + idProfil + "," + duree + "," + date + "," + chapitre + "," + onglet + "," + page + "," + "'" + intituleNew + "'" + "," + "'" + pointsObtenus + "'" + "," + "'" + pointsPossibles + "'" + "," + "'" + pointsMaxi + "')";


        database.execute(sqlQuery);
    }

    public int getHighestNote(int chapitre, int onglet)
    {
        int highestNote = 0;

        String sqlQuery = "SELECT max(points_obtenus) from RESULTAT where chapitre = " + chapitre + " AND onglet = " + onglet;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            highestNote = test.getInt(1);
        }
        return highestNote;
    }

    public int getHighestNotePerChapter(int chapitre)
    {
        int highestNote = 0;

        String sqlQuery = "SELECT max(points_obtenus) from RESULTAT where chapitre = " + chapitre;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            highestNote = test.getInt(1);
        }
        return highestNote;
    }


    public void updateResultat(int idResultatServer, int localResultat)
    {
        database.execute("INSERT OR REPLACE INTO PROFILS (id_profil, id_compte, nom, prenom, age, classe, avatar, photo) VALUES ('%d', '%d', '%@', '%@', %ld, '%@', %ld, '%@')");
    }

    public int getMaxNotePerExercice(/*Profil idProfil,*/ int chapitre, int onglet, int page)
    {
        String sqlQuery = "SELECT max( points_max) from RESULTAT where chapitre = " + chapitre + " AND onglet = " + onglet;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            maxNotePageForIdProfil = test.getInt(1);
        }
        return maxNotePageForIdProfil;
    }

    public int getMaxNotePerChapter(/*Profil idProfil,*/ int chapitre, int page)
    {
        String sqlQuery = "SELECT max( points_max) from RESULTAT where chapitre = " + chapitre;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            maxNotePageForIdProfil = test.getInt(1);
        }
        return maxNotePageForIdProfil;
    }

    public int getMaxNotePossiblePerExercice(/*Profil idProfil,*/ int chapitre, int onglet, int page)
    {
        String sqlQuery = "SELECT max( points_possibles) from RESULTAT where chapitre = " + chapitre + " AND onglet =" + onglet;

        int maxNotePossiblePerExercice = 0;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            maxNotePossiblePerExercice = test.getInt(1);
        }
        return maxNotePossiblePerExercice;
    }

    public int getMaxNotePossiblePerChapter(/*Profil idProfil,*/ int chapitre, int page)
    {
        String sqlQuery = "SELECT max( points_possibles) from RESULTAT where chapitre = " + chapitre;

        int maxNotePossiblePerExercice = 0;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            maxNotePossiblePerExercice = test.getInt(1);
        }
        return maxNotePossiblePerExercice;
    }

    public int getMaxDureePageForIdProfil(/*User idProfil,*/ int chapitre, int onglet/*, int page*/)
    {
        String sqlQuery = "SELECT sum(duree) FROM RESULTAT WHERE  chapitre =" + chapitre + " AND onglet= " + onglet;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            maxDureePageForIdProfil = test.getInt(1);
        }
        return maxDureePageForIdProfil;
    }


    public String getTotalNotePageForIdProfil(/*User idProfil,*/ int chapitreNumber)
    {
        int totalObtainedNoteforIdProfil = 0;
        int totalMaxNoteforIdProfil = 0;
        int totalPossibleNoteforIdProfil = 0;

        String sqlQueryNoteObtained, sqlQueryNotePossible, sqlQueryNoteMax = "";

        if (chapitreNumber == 0)
        {
            sqlQueryNoteObtained = "SELECT sum(somme_points_obtenus) FROM(SELECT max(points_obtenus) as somme_points_obtenus from RESULTAT  GROUP by onglet)";
            sqlQueryNotePossible = "SELECT sum(somme_points_possibles) FROM(SELECT max(points_possibles) as somme_points_possibles from RESULTAT  GROUP by onglet)";
            sqlQueryNoteMax = "SELECT sum(somme_points_max) FROM(SELECT max(points_max) as somme_points_max from RESULTAT  GROUP by onglet)";
        }
        else
        {
            sqlQueryNoteObtained = "SELECT sum(somme_points_obtenus) FROM(SELECT max(points_obtenus) as somme_points_obtenus from RESULTAT where chapitre = " + chapitreNumber + " GROUP by onglet)";
            sqlQueryNotePossible = "SELECT sum(somme_points_possibles) FROM(SELECT max(points_possibles) as somme_points_possibles from RESULTAT where chapitre = " + chapitreNumber + " GROUP by onglet)";
            sqlQueryNoteMax = "SELECT sum(somme_points_max) FROM(SELECT max(points_max) as somme_points_max from RESULTAT where chapitre = " + chapitreNumber + " GROUP by onglet)";
        }


        DataBase.Result totalNoteObtained = database.query(sqlQueryNoteObtained);

        if (!totalNoteObtained.isEmpty())
        {
            totalNoteObtained.moveToNext();
            totalObtainedNoteforIdProfil = totalNoteObtained.getInt(1);

            int ok = 5;
            ok++;
        }


        DataBase.Result totalNotePossible = database.query(sqlQueryNotePossible);

        if (!totalNotePossible.isEmpty())
        {
            totalNotePossible.moveToNext();
            totalPossibleNoteforIdProfil = totalNotePossible.getInt(1);

            int ok = 5;
            ok++;
        }

        DataBase.Result totalNoteMax = database.query(sqlQueryNoteMax);

        if (!totalNoteMax.isEmpty())
        {
            totalNoteMax.moveToNext();
            totalMaxNoteforIdProfil = totalNoteMax.getInt(1);
        }
        String totalNotePageForIdProfil;

        if (chapitreNumber !=0)
        {
             totalNotePageForIdProfil = totalObtainedNoteforIdProfil + "/" + totalPossibleNoteforIdProfil + "/" + totalMaxNoteforIdProfil;
        }
        else
        {

             totalNotePageForIdProfil = totalObtainedNoteforIdProfil + "/" + totalPossibleNoteforIdProfil + "/3596" ;
        }

        return totalNotePageForIdProfil;
    }

    public long getTotalDureePageForIdProfilByChapter(/*User idProfil,*/ int chapitre)
    {
        long totalDureePageForIdProfil = 0;

        String sqlQuery = "SELECT sum(duree) from RESULTAT WHERE chapitre = " + chapitre;

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            totalDureePageForIdProfil = test.getInt(1);
        }
        return totalDureePageForIdProfil;
    }

    public long getTotalDureeAllForIdProfil()
    {
        long totalDureePageForIdProfil = 0;

        String sqlQuery = "SELECT sum(duree) from RESULTAT";

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            totalDureePageForIdProfil = test.getInt(1);

            int ok = 5;
            ok++;
        }
        return totalDureePageForIdProfil;
    }

    public long getTotalNotesAllForIdProfil()
    {
        long totalDureePageForIdProfil = 0;

        String sqlQuery = "SELECT sum(duree) from RESULTAT";

        DataBase.Result test = database.query(sqlQuery);

        if (!test.isEmpty())
        {
            test.moveToNext();
            totalDureePageForIdProfil = test.getInt(1);

            int ok = 5;
            ok++;
        }
        return totalDureePageForIdProfil;
    }

    public void removeOnglet(int onglet)
    {

    }


}
