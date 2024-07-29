import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.to_doapp.Todo

class TodoDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo_app"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TODO = "todos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TEXT = "text"
        private const val COLUMN_IS_DONE = "is_done"

        private const val SQL_CREATE_TABLE_TODO = """
            CREATE TABLE $TABLE_TODO (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TEXT TEXT NOT NULL,
                $COLUMN_IS_DONE INTEGER NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }

    fun insertTodo(todo: Todo) {
        val db = writableDatabase
        val data = ContentValues().apply {
            put(COLUMN_TEXT, todo.text)
            put(COLUMN_IS_DONE, if (todo.isDone) 1 else 0)
        }
        db.insert(TABLE_TODO, null, data)
        db.close()
    }

    fun getAllTodo(): List<Todo> {
        val todoList = mutableListOf<Todo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TODO"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT))
                val isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_DONE)) == 1
                val todo = Todo(id, text, isDone)
                todoList.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return todoList
    }

    fun updateTodoIsDone(id: Int, isDone: Boolean) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_IS_DONE, if (isDone) 1 else 0)
        }
        db.update(TABLE_TODO, contentValues, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteTodoById(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
