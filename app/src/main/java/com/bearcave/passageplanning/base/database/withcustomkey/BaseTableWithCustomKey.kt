package com.bearcave.passageplanning.base.database.withcustomkey


import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bearcave.passageplanning.data.database.ManagerListener
import java.util.*

/**
 * @param <DAO> - Data Access Object
 * *
 * @param <Id> - Column id in database.
</Id></DAO> */
abstract class BaseTableWithCustomKey<DAO : DatabaseElementWithCustomKey<Id>, Id>(val manager: ManagerListener) : ManagerListener, CRUDWithCustomKey<DAO, Id> {

    abstract val tableName: String

    protected abstract fun createKeyToValueTypeHolder(): LinkedHashMap<String, String>

    protected abstract fun getContentValue(element: DAO): ContentValues

    protected abstract val idKey: String

    protected abstract fun loadFrom(cursor: Cursor): DAO

    fun tableCreator(): String {
        val table = StringBuilder()
        table.append("CREATE TABLE ")
        table.append(tableName)
        table.append("(")

        for ((key, value) in createKeyToValueTypeHolder()) {
            table.append("$key $value,")
        }

        for (reference in createReferences()) {
            table.append(referenceTemplate(reference[0], reference[1], reference[2]))
        }

        table.delete(table.length - 1, table.length)

        table.append(");")

        return table.toString()
    }

    /**
     * @return ArrayList, which contains 3-dimensional arrays [key id, foreign table humanCode, foreign key id]
     */
    protected open fun createReferences(): ArrayList<Array<String>> {
        return ArrayList()
    }

    private fun referenceTemplate(keyId: String, table: String, foreignKeyId: String): String {
        return " $FOREIGN_KEY ($keyId) $REFERENCE $table($foreignKeyId),"
    }


    private val tableColumns: Array<String?>
        get() {
            val typeHolder = createKeyToValueTypeHolder()

            val result = arrayOfNulls<String>(typeHolder.size)

            var i = 0
            for (key in typeHolder.keys) {
                result[i++] = key
            }

            return result
        }

    override val writableDatabase: SQLiteDatabase
        get() = manager.writableDatabase


    override val readableDatabase: SQLiteDatabase
        get() = manager.readableDatabase


    override fun insert(element: DAO): Long {
        return writableDatabase
                .insert(
                        tableName, null,
                        getContentValue(element)
                )
    }

    override fun read(id: Id): DAO {
        val cursor = readableDatabase.query(
                tableName,
                tableColumns,
                idKey + ANY,
                arrayOf(id.toString()), null, null, null, null
        )

        cursor?.moveToFirst()

        return loadFrom(cursor)
    }

    override fun readAll(): List<DAO> {
        val requestedList = ArrayList<DAO>()
        val selectQuery = "SELECT  * FROM " + tableName

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                requestedList.add(loadFrom(cursor))
            } while (cursor.moveToNext())
        }

        return requestedList
    }

    override fun update(element: DAO): Int {
        return writableDatabase.update(
                tableName,
                getContentValue(element),
                idKey + ANY,
                arrayOf(element.id.toString())
        )
    }

    override fun delete(element: DAO): Int {
        return writableDatabase.delete(
                tableName,
                idKey + ANY,
                arrayOf(element.id.toString())
        )
    }

    companion object {

        val KEY_ID = " id "

        val ANY = " = ?"

        val INTEGER = " INTEGER "
        val TEXT = " TEXT "
        val FLOAT = " FLOAT"
        val DOUBLE = " DOUBLE "
        val DATETIME = " DATETIME "

        val NOT_NULL = " NOT NULL "
        val AUTOINCREMENT = " AUTOINCREMENT "
        val PRIMARY_KEY = " PRIMARY KEY "
        val UNIQUE = " UNIQUE "
        val REFERENCE = " REFERENCES "
        val FOREIGN_KEY = " FOREIGN KEY "
    }
}
