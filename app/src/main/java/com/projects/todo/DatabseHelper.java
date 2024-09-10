package com.projects.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_STATUS = "status";

    public DatabseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE "+TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK + " text," + COLUMN_STATUS + " INTEGER );";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTask(String task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_TASK,task);
        contentValue.put(COLUMN_STATUS,0);
        long result = db.insert(TABLE_NAME,null,contentValue);
        return result != -1;
    }
    public Cursor getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    public boolean updateTaskStatus(int id,int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_STATUS,status);
        int result = db.update(TABLE_NAME,contentValue,COLUMN_ID + " = ?",new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,COLUMN_ID+" = ?",new String[]{String.valueOf(id)});
        return result > 0;
    }
}
