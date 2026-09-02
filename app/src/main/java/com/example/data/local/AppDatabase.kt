package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.AttendanceRecord
import com.example.data.model.ClassItem
import com.example.data.model.ExamItem
import com.example.data.model.FeeRecord
import com.example.data.model.Homework
import com.example.data.model.NoteItem
import com.example.data.model.Notice
import com.example.data.model.PushNotificationLog
import com.example.data.model.ResultItem
import com.example.data.model.Student
import com.example.data.model.StudyMaterial
import com.example.data.model.Subject
import com.example.data.model.TimetableEntry
import com.example.data.model.UserAccount
import com.example.data.model.VideoLecture

@Database(
    entities = [
        UserAccount::class,
        Student::class,
        ClassItem::class,
        Subject::class,
        Homework::class,
        NoteItem::class,
        Notice::class,
        TimetableEntry::class,
        ExamItem::class,
        ResultItem::class,
        FeeRecord::class,
        AttendanceRecord::class,
        StudyMaterial::class,
        VideoLecture::class,
        PushNotificationLog::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun schoolDao(): SchoolDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "digital_schoolbag_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
