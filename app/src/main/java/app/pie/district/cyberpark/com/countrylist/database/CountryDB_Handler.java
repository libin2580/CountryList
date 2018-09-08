package app.pie.district.cyberpark.com.countrylist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.pie.district.cyberpark.com.countrylist.model.Countrydb_model;

/**
 * Created by libin on 22/12/2017.
 */
public class CountryDB_Handler extends SQLiteOpenHelper implements CountryDb_Listener {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "CountryDatabase.db";
    private static final String TABLE_NAME = "country_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
   // private static final String KEY_STATE = "_state";
   // private static final String KEY_DESCRIPTION = "_description";

    String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT )";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public CountryDB_Handler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void addCity(Countrydb_model country) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = 0;
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, country.getName());
           // values.put(KEY_STATE, city.getState());
           // values.put(KEY_DESCRIPTION,city.getDescription());

            db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }
    public int checkExistance(String mem_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int val=0;
        try {

            db.beginTransaction();
            String selectQuery = "SELECT "+KEY_ID+" FROM "+KEY_NAME+" WHERE "+KEY_NAME+"='"+mem_name+"'";
            System.out.println("selectQuery : "+selectQuery);
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    val=Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID)));
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("ID = " + c.getString(c.getColumnIndex(KEY_ID)));
                    System.out.println("---------------------------------------------------------------------------------");

                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return  val;

    }
    @Override
    public ArrayList<Countrydb_model> getAllCity() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Countrydb_model> cityList = null;
        try{
            cityList = new ArrayList<Countrydb_model>();
            String QUERY = "SELECT  * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {

                    Countrydb_model city = new Countrydb_model();
                    city.setId(cursor.getInt(0));
                    city.setName(cursor.getString(1));
                   // city.setState(cursor.getString(2));
                  //  city.setDescription(cursor.getString(3));
                    cityList.add(city);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return cityList;
    }
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        //db.delete(DatabaseHelper.DB_NAME, null, null);
        db.delete(TABLE_NAME, null, null);
    }
    @Override
    public int getCityCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Log.e("count",""+QUERY);
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}
