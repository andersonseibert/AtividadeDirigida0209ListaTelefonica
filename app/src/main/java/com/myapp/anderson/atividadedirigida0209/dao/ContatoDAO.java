package com.myapp.anderson.atividadedirigida0209.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myapp.anderson.atividadedirigida0209.model.Contato;

/**
 * Created by Anderson on 12/10/2017.
 */

public class ContatoDAO {
    private SQLiteOpenHelper _openHelper;



    public long add(Contato c) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put("title", c.getNome());
        row.put("priority", c.getNumero());
        long id = db.insert("contato", null, row);
        db.close();
        return id;
    }

    public Cursor getAll() {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from contato", null);
    }

    public ContentValues get(long id) {
        SQLiteDatabase db = _openHelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        ContentValues row = new ContentValues();
        Cursor cur = db.rawQuery("select nome, numero from contato where id = ?", new String[] { String.valueOf(id) });
        if (cur.moveToNext()) {
            row.put("nome", cur.getString(0));
            row.put("numero", cur.getString(1));
        }
        cur.close();
        db.close();
        return row;
    }

    public void delete(long id) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete("contato", "id = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void update(Contato c) {
        SQLiteDatabase db = _openHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues row = new ContentValues();
        row.put("nome", c.getNome());
        row.put("contato", c.getNumero());
        db.update("contato", row, "_id = ?", new String[] { String.valueOf(c.getId()) } );
        db.close();
    }
}
