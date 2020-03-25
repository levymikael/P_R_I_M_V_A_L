package com.evalutel.primval_desktop.Database;


import com.evalutel.primval_desktop.Database.DataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDesktop extends DataBase
{
    protected Connection db_connection;
    protected Statement stmt;
    protected boolean nodatabase = false;


    public DatabaseDesktop()
    {
        loadDatabase();
        if (isNewDatabase())
        {
            onCreate();
            upgradeVersion();
        }
        else if (isVersionDifferent())
        {
            onUpgrade();
            upgradeVersion();
        }

    }

    public void execute(String sql)
    {
        try
        {
             stmt.execute(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql)
    {
        try
        {
            return stmt.executeUpdate(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public Result query(String sql)
    {
        try
        {
            return new ResultDesktop(stmt.executeQuery(sql));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void loadDatabase()
    {
        File file = new File(database_name + ".db");
        if (!file.exists())
            nodatabase = true;
        try
        {
            Class.forName("org.sqlite.JDBC");
            db_connection = DriverManager.getConnection("jdbc:sqlite:" + database_name + ".db");
            stmt = db_connection.createStatement();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void upgradeVersion()
    {
        execute("PRAGMA user_version=" + version);
    }

    private boolean isNewDatabase()
    {
        return nodatabase;
    }

    private boolean isVersionDifferent()
    {
        Result q = query("PRAGMA user_version");
        if (!q.isEmpty())
            return (q.getInt(1) != version);
        else
            return true;
    }

    public class ResultDesktop implements Result
    {
        ResultSet res;
        boolean called_is_empty = false;

        public ResultDesktop(ResultSet res)
        {
            this.res = res;
        }

        public boolean isEmpty()
        {
            try
            {
                if (res.getRow() == 0)
                {
                    called_is_empty = true;
                    return !res.next();
                }
                return res.getRow() == 0;
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return false;
        }

        public boolean moveToNext()
        {
            try
            {
                if (called_is_empty)
                {
                    called_is_empty = false;
                    return true;
                }
                else
                    return res.next();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return false;
        }

        public int getColumnIndex(String name)
        {
            try
            {
                return res.findColumn(name);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public int getInt(int columnIndex)
        {
            try
            {
                return res.getInt(columnIndex);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }

        public float getFloat(int columnIndex)
        {
            try
            {
                return res.getFloat(columnIndex);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        public String getString(int columnIndex)
        {
            try
            {
                return res.getString(columnIndex);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
