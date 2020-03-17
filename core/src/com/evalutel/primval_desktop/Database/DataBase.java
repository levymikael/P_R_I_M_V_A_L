package com.evalutel.primval_desktop.Database;


//General class that needs to be implemented on Android and Desktop Applications
public abstract class DataBase
{

    protected static String database_name = "database";
    protected static DataBase instance = null;
    protected static int version = 1;

    //Runs a sql query like "create".
    public abstract void execute(String sql);

    //Identical to execute but returns the number of rows affected (useful for updates)
    public abstract int executeUpdate(String sql);

    //Runs a query and returns an Object with all the results of the query. [Result Interface is defined below]
    public abstract Result query(String sql);

    public void onCreate()
    {
        //Example of Highscore table code (You should change this for your own DB code creation)
        execute("CREATE TABLE RESULTAT (id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL UNIQUE , id_profil INTEGER, duree REAL, date_resultat VARCHAR, mnemonique VARCHAR, chapitre INTEGER, onglet INTEGER, page INTEGER, intitule VARCHAR, points_obtenus INTEGER, points_possibles INTEGER, points_max INTEGER, id_resultat INTEGER UNIQUE)");
        execute("CREATE TABLE IF NOT EXISTS PROFILS " +
                "(id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , id_profil INTEGER UNIQUE, id_compte INTEGER, nom VARCHAR, prenom VARCHAR, age INTEGER, classe VARCHAR, avatar INTEGER, photo VARCHAR)");
//        execute("INSERT INTO 'Duration'(name,duration) values ('Cris',1234)");
        //Example of query to get DB data of Highscore table
////        Result q = query("SELECT * FROM 'Duration'");
//        if (!q.isEmpty())
//        {
//            q.moveToNext();
//            System.out.println("Duration of " + q.getString(q.getColumnIndex("name")) + ": " + q.getString(q.getColumnIndex("duration")));
//        }
    }

    public void onUpgrade()
    {
        //Example code (You should change this for your own DB code)
        execute("DROP TABLE IF EXISTS 'RESULTAT';");
        execute("DROP TABLE IF EXISTS 'PROFILS';");
        onCreate();
        System.out.println("DB Upgrade made because I changed DataBase.version on code");
    }

    //Interface to be implemented on both Android and Desktop Applications
    public interface Result
    {
        public boolean isEmpty();

        public boolean moveToNext();

        public int getColumnIndex(String name);

        public float getFloat(int columnIndex);

        public String getString(int columnIndex);

        public int getInt(int columnIndex);

    }
}
