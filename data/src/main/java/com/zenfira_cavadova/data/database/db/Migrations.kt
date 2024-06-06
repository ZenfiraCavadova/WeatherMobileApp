package com.zenfira_cavadova.data.database.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE notes_database ADD COLUMN creationDate INTEGER NOT NULL DEFAULT 0")
        }
    }
}