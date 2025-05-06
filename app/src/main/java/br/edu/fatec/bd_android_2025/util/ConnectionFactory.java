package br.edu.fatec.bd_android_2025.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class ConnectionFactory extends SQLiteOpenHelper {
        public ConnectionFactory(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table aluno(id integer primary key " + "autoincrement, nome varchar(50), cpf varchar(20)," + "telefone varchar(2))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            String sql = "DROP TABLE IF EXISTS aluno";
            db.execSQL(sql);
            onCreate(db);
        }
    }

