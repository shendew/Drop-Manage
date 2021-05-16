package com.kingdew.pos3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    //db details
    private static final int VERSION=1;
    private static final String DB_NAME="OR_MAN";
    private static final String TABLE_NAME="ORDERS";

    private static final String PRO_TABLE_NAME="PRODUCTS";

    private static final String PNO=" no ";
    private static final String PRODUCT_NUMBER="order_number";
    private static final String PTITLE="title";
    private static final String PRODUCT_LINK="product_link";








    //column names
    private static final String NO=" no ";
    private static final String ORDER_NUMBER="order_number";
    private static final String TITLE="title";
    private static final String SHIPPED_DATE="shipped_date";
    private static final String TRACKING_NUMBER="tracking_number";
    private static final String CONTACT_DETAILS="contact_details";
    private static final String COMPLETED_DATE="completed_date";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //TABLE CREATE QUERY""

        String TABLE_CREATE_QUERY= "CREATE TABLE "+TABLE_NAME+"("
                +NO+"INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ORDER_NUMBER+" TEXT,"
                +TITLE+" TEXT,"
                +CONTACT_DETAILS+" TEXT,"
                +TRACKING_NUMBER+" TEXT,"
                +SHIPPED_DATE+" TEXT,"

                +COMPLETED_DATE+" TEXT"+
                ");";

        String PRODUCT_CREATE_QUERY= "CREATE TABLE "+PRO_TABLE_NAME+"("
                +PNO+"INTEGER PRIMARY KEY AUTOINCREMENT,"
                +PRODUCT_NUMBER+" TEXT,"
                +PTITLE+" TEXT,"
                +PRODUCT_LINK+" TEXT"+
                ");";

         sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);
         sqLiteDatabase.execSQL(PRODUCT_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_TABLE_QUERY= "DROP TABLE IF EXISTS "+TABLE_NAME;
        String DROP_PRO_TB_QUERY="DROP TABLE IF EXISTS "+PRO_TABLE_NAME;
        //DROP TABLE IF EXISTS
        sqLiteDatabase.execSQL(DROP_PRO_TB_QUERY);
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
        //CREATE TABLES AGAIN
        onCreate(sqLiteDatabase);
    }


    public void addOrder(ORDERS orders){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(ORDER_NUMBER,orders.getSorderID() );
        contentValues.put(TITLE,orders.getSorderTitle());
        contentValues.put(SHIPPED_DATE,orders.getSshippedDate());
        contentValues.put(TRACKING_NUMBER,orders.getStrackingNumber());
        contentValues.put(CONTACT_DETAILS,orders.getScontactDetails());
        contentValues.put(COMPLETED_DATE,orders.getScompletedDate());

        //save table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        //close database
        sqLiteDatabase.close();

    }


    public int countOrder(){
        SQLiteDatabase db =getReadableDatabase();
        String query="SELECT * FROM "+ TABLE_NAME+";";

        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();
    }


    public List<ORDERS> orderROWS(){
        List<ORDERS> orders=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+ TABLE_NAME+";";
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                //creat order obj
                ORDERS orders1 = new ORDERS();

                orders1.setNo(cursor.getInt(0));
                orders1.setSorderID(cursor.getString(1));
                orders1.setSorderTitle(cursor.getString(2));
                orders1.setSshippedDate(cursor.getString(3));
                orders1.setStrackingNumber(cursor.getString(4));
                orders1.setScontactDetails(cursor.getString(5));
                orders1.setScompletedDate(cursor.getString(6));

                orders.add(orders1);
            } while (cursor.moveToNext());

        }
         return orders;



    }


    public List<PRODUCTS>productRows(){
        List<PRODUCTS> products=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String q="SELECT * FROM "+PRO_TABLE_NAME+";";
        Cursor cursor=database.rawQuery(q,null);

        if (cursor.moveToFirst()){
            do {
                PRODUCTS pro=new PRODUCTS();
                pro.setProno(cursor.getInt(0));
                pro.setProductnum(cursor.getString(1));
                pro.setPro_title(cursor.getString(2));
                pro.setPro_link(cursor.getString(3));

                products.add(pro);
            }while (cursor.moveToNext());
        }
        return products;
    }


    public void addProduct(PRODUCTS products){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(PRODUCT_NUMBER,products.getProductnum());
        contentValues.put(PTITLE,products.getPro_title());
        contentValues.put(PRODUCT_LINK,products.getPro_link());

        sqLiteDatabase.insert(PRO_TABLE_NAME,null,contentValues);

        sqLiteDatabase.close();


    }


    public int countpro(){
        SQLiteDatabase database=getReadableDatabase();
        String query= "SELECT * FROM "+ PRO_TABLE_NAME+";";
        Cursor cursor=database.rawQuery(query,null);

        return cursor.getCount();
    }


    public ORDERS serach(String data){

        ORDERS orders;
        SQLiteDatabase database=getWritableDatabase();


        Cursor cursor=database.query(TABLE_NAME,new String[]{NO,ORDER_NUMBER,TITLE,CONTACT_DETAILS,TRACKING_NUMBER,SHIPPED_DATE,COMPLETED_DATE},
                ORDER_NUMBER+"= ?",new String[]{String.valueOf(data)},null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            orders=new ORDERS(

                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
            );

        return orders;

        }
        return null;
    }


    public PRODUCTS search(String data){
        PRODUCTS products;
        SQLiteDatabase database=getWritableDatabase();
        Cursor cursor=database.query(PRO_TABLE_NAME,new String[]{PNO,PRODUCT_NUMBER,PTITLE,PRODUCT_LINK},
                PRODUCT_NUMBER+"= ?",new String[]{String.valueOf(data)},null,null,null);
        if (cursor != null){

            cursor.moveToFirst();
            products=new PRODUCTS(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            return products;
        }
        return null;
    }


    public  void deleteordata(int id){
       SQLiteDatabase database=getReadableDatabase();
       database.delete(TABLE_NAME,"no =?",new String[]{String.valueOf(id)});
       database.close();
    }


    public void deleteprodata(int id){
        SQLiteDatabase database=getReadableDatabase();
        database.delete(PRO_TABLE_NAME,"no =?",new String[]{String.valueOf(id)});
        database.close();
    }


    public void deletesearchor(String no){
        SQLiteDatabase database=getReadableDatabase();
        database.delete(TABLE_NAME,"order_number =?",new String[]{String.valueOf(no)});
        database.close();
    }


    public void deletesearchpr(String no){
        SQLiteDatabase database=getReadableDatabase();
        database.delete(PRO_TABLE_NAME,"order_number =?",new String[]{String.valueOf(no)});
        database.close();
    }


    public  ORDERS viewdataor(int id){
        ORDERS orders;
        SQLiteDatabase database=getWritableDatabase();
        Cursor cursor=database.query(TABLE_NAME,new String[]{NO,ORDER_NUMBER,TITLE,CONTACT_DETAILS,TRACKING_NUMBER,SHIPPED_DATE,COMPLETED_DATE},
                NO+"= ?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            orders=new ORDERS(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            return orders;

        }
        return  null;
    }

    public int updateorder(ORDERS orders){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(ORDER_NUMBER,orders.getSorderID() );
        contentValues.put(TITLE,orders.getSorderTitle());
        contentValues.put(SHIPPED_DATE,orders.getSshippedDate());
        contentValues.put(TRACKING_NUMBER,orders.getStrackingNumber());
        contentValues.put(CONTACT_DETAILS,orders.getScontactDetails());
        contentValues.put(COMPLETED_DATE,orders.getScompletedDate());
    int stts=sqLiteDatabase.update(TABLE_NAME,contentValues,NO+" =?",new String[]{String.valueOf(orders.getNo())});
    sqLiteDatabase.close();
    return stts;
    }


    public PRODUCTS viewdatapro(int id){
        PRODUCTS products;
        SQLiteDatabase database=getWritableDatabase();
        Cursor cursor=database.query(PRO_TABLE_NAME,new String[]{PNO,PRODUCT_NUMBER,PTITLE,PRODUCT_LINK},
                PNO+"= ?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            products=new PRODUCTS(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            return products;
        }
        return  null;
    }

    public int updateproduct(PRODUCTS products){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(PRODUCT_NUMBER,products.getProductnum());
        contentValues.put(PTITLE,products.getPro_title());
        contentValues.put(PRODUCT_LINK,products.getPro_link());
        int stts=sqLiteDatabase.update(PRO_TABLE_NAME,contentValues,PNO+" =?",new String[]{String.valueOf(products.getProno())});
        sqLiteDatabase.close();
        return stts;
    }

}
