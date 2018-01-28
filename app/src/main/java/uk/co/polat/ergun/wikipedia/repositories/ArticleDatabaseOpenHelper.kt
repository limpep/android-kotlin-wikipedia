package uk.co.polat.ergun.wikipedia.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class ArticleDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, "ArticlesDatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("Favorites", true,
                "id" to INTEGER + PRIMARY_KEY,
                "title" to TEXT,
                "url" to TEXT,
                "thumbnailJson" to TEXT)

        db?.createTable("History", true,
                "id" to INTEGER + PRIMARY_KEY,
                "title" to TEXT,
                "url" to TEXT,
                "thumbnailJson" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}