package com.david.backupcentral.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class Database(context:Context): SQLiteOpenHelper(context, DATABASE_NAME ,null, DATABASE_VERSION) {
    private var mInstance: Database? = null
    private val context = context
    private val TAG = Database::class.java.name
    @Synchronized
    fun getInstance(ctx: Context): Database? {
        if (mInstance == null) {
            mInstance = Database(ctx.applicationContext)
        }
        return mInstance
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction();
        try {
                db.isDatabaseIntegrityOk;
                db.setTransactionSuccessful();
            } catch (e:Exception) {
        } finally {
            db.endTransaction();
            //db.setForeignKeyConstraintsEnabled(true);
        }
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e(TAG, "Updating table from $oldVersion to $newVersion")
        try {
            for (i in oldVersion until newVersion) {
                val migrationName = String.format("from_%d_to_%d.sql", i, i + 1)
                Log.d(TAG, "Looking for migration file: $migrationName")
                readAndExecuteSQLScript(db, context, migrationName)
            }
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "Exception running upgrade script:", exception)
        }
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "BackupCentral.db"
    }


    private fun readAndExecuteSQLScript(db: SQLiteDatabase, ctx: Context, fileName: String) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d(TAG, "SQL script file name is empty")
            return
        }
        Log.d(TAG, "Script found. Executing...")
        val assetManager = ctx.assets
        var reader: BufferedReader? = null
        try {
            val `is` = assetManager.open(fileName)
            val isr = InputStreamReader(`is`)
            reader = BufferedReader(isr)
            executeSQLScript(db, reader)
        } catch (e: IOException) {
            Log.e(TAG, "IOException:", e)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e(TAG, "IOException:", e)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun executeSQLScript(db: SQLiteDatabase, reader: BufferedReader) {
        var line: String
        var statement = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            statement.append(line)
            statement.append("\n")
            if (line.endsWith(";")) {
                db.execSQL(statement.toString())
                statement = StringBuilder()
            }
        }
    }
}