package com.pdkj.carschool.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.pdkj.carschool.db.QuestionsMetaData.DATABASE_NAME;

public class QuestionsDataBase extends SQLiteOpenHelper {

    private static final String createTableName1 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_SUBJECT1+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYID +" text ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";

    private static final String createTableName7 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_SUBJECT2+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.CATEGORYID +" text  ,"
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";


    private static final String createTableName2 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_SUBJECT4+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.CATEGORYID +" text ,"
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";

    private static final String createTableName3 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_ERROR_SUBJECT1+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.TIME +" text not null , "
            +QuestionsMetaData.MetaData.MYANSWER +" text not null , "
            +QuestionsMetaData.MetaData.CATEGORYID +" text  ,"
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";

    private static final String createTableName4 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_ERROR_SUBJECT4+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.TIME +" text not null , "
            +QuestionsMetaData.MetaData.MYANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";



    private static final String createTableName5 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_COLLECTIONS_SUBJECT1+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.CATEGORYID +" text , "
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";

    private static final String createTableName6 ="CREATE TABLE IF NOT EXISTS "+QuestionsMetaData.MetaData.TABLE_NAME_COLLECTIONS_SUBJECT4+
            " ( "+QuestionsMetaData.MetaData.ID +" text not null , "
            +QuestionsMetaData.MetaData.ANSWER +" text not null , "
            +QuestionsMetaData.MetaData.EXPLAINS +" text not null , "
            +QuestionsMetaData.MetaData.ITEM1+" text not null , "
            +QuestionsMetaData.MetaData.ITEM2+" text not null , "
            +QuestionsMetaData.MetaData.ITEM3+" text not null , "
            +QuestionsMetaData.MetaData.ITEM4+" text not null , "
            +QuestionsMetaData.MetaData.QUESTION+" text not null , "
            +QuestionsMetaData.MetaData.TYPE +" text not null , "
            +QuestionsMetaData.MetaData.CATEGORYID +" text, "
            +QuestionsMetaData.MetaData.EXAMTYPEID +" text  ,"
            +QuestionsMetaData.MetaData.CATEGORYTYPE +" text ,"
            +QuestionsMetaData.MetaData.URL +")";

    public QuestionsDataBase(Context context){
        this(context, DATABASE_NAME,null,QuestionsMetaData.DATABASE_VERSION);
    }

    public QuestionsDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableName1);
        sqLiteDatabase.execSQL(createTableName2);
        sqLiteDatabase.execSQL(createTableName3);
        sqLiteDatabase.execSQL(createTableName4);
        sqLiteDatabase.execSQL(createTableName5);
        sqLiteDatabase.execSQL(createTableName6);
        sqLiteDatabase.execSQL(createTableName7);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs){
      return this.getWritableDatabase().rawQuery(sql,selectionArgs);
    }

    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DATABASE_NAME);
    }


}
