package com.example.pc1.tuvung_everyday.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pc1.tuvung_everyday.Model.TuVung;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 7/5/2017.
 */

public class DBTuVung_Everyday  extends SQLiteOpenHelper{

    private static final String TAG = "SQLite";
    private static final int DATABASE_VESION = 1;
    private static  final String DATABASE_NAME ="DBTuVung";
    private static final String TABLE_NAME_TV = "TU_VUNG";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TA = "TIENG_ANH";

    private static final String COLUMN_TV = "TIENG_VIET";
    private static final String COLUMN_TIME_START = "TIME_START";
    private static final String COLUMN_TIME_END = "TIME_END";
    private static final String COLUMN_RATE = "RATTING";



    public DBTuVung_Everyday(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            Log.i(TAG,"Create table...");
        String sql = " CREATE TABLE "+TABLE_NAME_TV+"( " + COLUMN_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT , "

                + COLUMN_TA + " VARCHAR(50) , "+ COLUMN_TV + " NVARCHAR(50), "+COLUMN_TIME_START + " NVARCHAR(50), "
                + COLUMN_TIME_END + " NVARCHAR(50),"+COLUMN_RATE + " NVARCHAR(10))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS + "+ TABLE_NAME_TV);
        onCreate(db);
    }
    // them 1 dòng
    public void addTuVung(TuVung tuVung)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TV,tuVung.getTiengViet());
        values.put(COLUMN_TA,tuVung.getTiengAnh());

        values.put(COLUMN_TIME_START,tuVung.getNgayBatDau());
        values.put(COLUMN_TIME_END,tuVung.getNgayKetThuc());
        values.put(COLUMN_RATE,tuVung.getRating());
        db.insert(TABLE_NAME_TV,null,values);
        db.close();

    }
    // lấy 1 dòng của bảng
    public  TuVung ShowTu(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_TV,new String[]{COLUMN_ID,COLUMN_TV,COLUMN_TA,COLUMN_TIME_START,COLUMN_TIME_END,COLUMN_RATE}
        ,COLUMN_ID + " =? "+ new String[]{String.valueOf(ID)},null,null,null,null);
        TuVung tuVung = null;
        if (cursor !=null){
            cursor.moveToFirst();
             tuVung = new TuVung(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5));

        }
        return tuVung;
    }
    //Lấy tất cả các dòng của bảng đưa vào 1 list
    public ArrayList<TuVung> getListTu()
    {
        ArrayList<TuVung> arrTu = new ArrayList<TuVung>();
        String sql = " SELECT * FROM "+ TABLE_NAME_TV;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(sql,null);
        if(cursor.moveToFirst())
        {
            do{
                TuVung tuVung = new TuVung();
                tuVung.setID(Integer.parseInt(cursor.getString(0)));
                tuVung.setTiengAnh(cursor.getString(1));
                tuVung.setTiengViet(cursor.getString(2));

                tuVung.setNgayBatDau(cursor.getString(3));
                tuVung.setNgayKetThuc(cursor.getString(4));
                tuVung.setRating(cursor.getString(5));

                arrTu.add(tuVung);

            }while (cursor.moveToNext());
        }
        return arrTu;
    }
    // lấy số dòng của bảng
    public int getSize()
    {
        String sql = "SELECT * FROM "+TABLE_NAME_TV;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql,null);
        //cursor.close();
        return cursor.getCount();
    }
    // update TuVung truyền vào với tham số là ID của TuVung truyền vào
    public int updateTu(TuVung tuVung)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TA,tuVung.getTiengAnh());
        values.put(COLUMN_TV,tuVung.getTiengViet());
        values.put(COLUMN_TIME_START,tuVung.getNgayBatDau());
        values.put(COLUMN_TIME_END,tuVung.getNgayKetThuc());
        values.put(COLUMN_RATE,tuVung.getRating());

        return db.update(TABLE_NAME_TV,values,COLUMN_ID+ " =? ",new String[]{String.valueOf(tuVung.getID())});
    }
// Xóa TuVung truyền vào
    public void deleteTu(TuVung tuVung)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_TV, COLUMN_ID + " = ?",
                new String[] { String.valueOf(tuVung.getID()) });
        db.close();
    }
}
