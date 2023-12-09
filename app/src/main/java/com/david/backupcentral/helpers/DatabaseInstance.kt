package com.david.backupcentral.helpers

class DatabaseInstance {
    private lateinit var dbHelper: Database

    @Synchronized
    fun getDatabaseHelper(): Database? {
        return dbHelper
    }
    @Synchronized
    fun setDatabaseHelper(adbHelper: Database) {
        this.dbHelper = adbHelper
    }
}