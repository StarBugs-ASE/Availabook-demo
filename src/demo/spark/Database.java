package demo.spark;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.internal.table.SqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetBusyHandler;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.Calendar;


import static com.sun.javafx.tools.resource.DeployResource.Type.data;
import static org.eclipse.jetty.http.DateGenerator.formatDate;

/**
 * Created by xx on 10/19/16.
 */
public class Database {
    public boolean createDatabase(String dbName) throws SqlJetException {
        File dbFile = new File(dbName);
        dbFile.delete();

        boolean hasCreated = false;

        SqlJetDb db = SqlJetDb.open(dbFile, true);
        db.getOptions().setAutovacuum(true);
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            db.getOptions().setUserVersion(1);
            hasCreated = true;
        } finally {
            db.commit();
        }

        return  hasCreated;
    }

    public boolean createTable(String tableName, SqlJetDb db) throws SqlJetException{
        boolean hasCreated = false;
        db.beginTransaction(SqlJetTransactionMode.WRITE);

        try {
            db.createTable("create table User(name TEXT NOT NULL PRIMARY KEY, email TEXT NOT NULL, password TEXT NOT NULL)");
        } finally {
            db.commit();
        }

        return hasCreated;
    }
    public boolean insertTable(ISqlJetTable table, SqlJetDb db) throws SqlJetException{
        boolean hasCreated = false;
      //  Calendar calendar = Calendar.getInstance();
       // calendar.clear();

        db.beginTransaction(SqlJetTransactionMode.WRITE);


        {

            try{
               // calendar.set(2005, 4, 19);
                table.insert("xx", "1234@columbia.edu", "12345");
                //calendar.set(2001, 3, 12);
                table.insert("mlz", "12s@djj.edu", "2ws");
            } finally {
                db.commit();
                hasCreated = true;
            }

        }
        return hasCreated;

    }

    private void printRecords(ISqlJetCursor cursor) throws SqlJetException{
        try {
            if (!cursor.eof()) {
                do {
                    System.out.println(cursor.getRowId() + " : " +
                            cursor.getString("name") + " " +
                            cursor.getString("email") + " " +
                            cursor.getString("availatime"));
                } while(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    public void queryTable(ISqlJetTable table, SqlJetDb db) throws SqlJetException{
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        //try {
          //  printRecords(table.order(table.getPrimaryKeyIndexName()));
        //} finally {
          //  db.commit();
        //}
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        try {
            printRecords(table.order("name"));
        } finally {
            db.commit();
        }
    }
/*
    public void updateTable(ISqlJetTable table, SqlJetDb db) throws SqlJetException{
        try{
            ISqlJetCursor updateCursor = table.open();
            do {
                updateCursor.update(
                        updateCursor.getValue(SECOND_NAME_FIELD),
                        updateCursor.getValue(FIRST_NAME_FIELD),
                        calendar.getTimeInMillis());
            } while(updateCursor.next());
            updateCursor.close();
        } finally {
            db.commit();
        }
    }*/



}
