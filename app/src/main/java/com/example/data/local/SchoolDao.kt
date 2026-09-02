package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

    // --- User Accounts ---
    @Query("SELECT * FROM user_accounts WHERE LOWER(username) = LOWER(:username) OR LOWER(email) = LOWER(:username) LIMIT 1")
    suspend fun getUserByUsernameOrEmail(username: String): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE studentId = :studentId LIMIT 1")
    suspend fun getUserByStudentId(studentId: String): UserAccount?

    @Query("SELECT * FROM user_accounts")
    fun getAllUserAccounts(): Flow<List<UserAccount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(userAccount: UserAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccounts(userAccounts: List<UserAccount>)

    // --- Students ---
    @Query("SELECT * FROM students ORDER BY rollNo ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    fun getStudentById(id: String): Flow<Student?>

    @Query("SELECT * FROM students WHERE className = :className ORDER BY rollNo ASC")
    fun getStudentsByClass(className: String): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<Student>)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    // --- Classes & Subjects ---
    @Query("SELECT * FROM classes ORDER BY name ASC")
    fun getAllClasses(): Flow<List<ClassItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classItem: ClassItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<ClassItem>)

    @Delete
    suspend fun deleteClass(classItem: ClassItem)

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<Subject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    // --- Homework ---
    @Query("SELECT * FROM homework ORDER BY dueDate DESC")
    fun getAllHomework(): Flow<List<Homework>>

    @Query("SELECT * FROM homework WHERE className = :className ORDER BY dueDate DESC")
    fun getHomeworkByClass(className: String): Flow<List<Homework>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomework(homework: Homework)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeworkList(homeworkList: List<Homework>)

    @Update
    suspend fun updateHomework(homework: Homework)

    @Delete
    suspend fun deleteHomework(homework: Homework)

    // --- Notes & Study Materials ---
    @Query("SELECT * FROM notes ORDER BY dateAdded DESC")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM notes WHERE className = :className ORDER BY dateAdded DESC")
    fun getNotesByClass(className: String): Flow<List<NoteItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteItem>)

    @Delete
    suspend fun deleteNote(note: NoteItem)

    @Query("SELECT * FROM study_materials ORDER BY title ASC")
    fun getAllStudyMaterials(): Flow<List<StudyMaterial>>

    @Query("SELECT * FROM study_materials WHERE className = :className ORDER BY title ASC")
    fun getStudyMaterialsByClass(className: String): Flow<List<StudyMaterial>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyMaterial(material: StudyMaterial)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyMaterials(materials: List<StudyMaterial>)

    @Delete
    suspend fun deleteStudyMaterial(material: StudyMaterial)

    // --- Video Lectures ---
    @Query("SELECT * FROM video_lectures ORDER BY chapter ASC")
    fun getAllVideoLectures(): Flow<List<VideoLecture>>

    @Query("SELECT * FROM video_lectures WHERE className = :className ORDER BY chapter ASC")
    fun getVideoLecturesByClass(className: String): Flow<List<VideoLecture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoLecture(video: VideoLecture)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoLectures(videos: List<VideoLecture>)

    @Delete
    suspend fun deleteVideoLecture(video: VideoLecture)

    // --- Notices ---
    @Query("SELECT * FROM notices ORDER BY isPinned DESC, date DESC")
    fun getAllNotices(): Flow<List<Notice>>

    @Query("SELECT * FROM notices WHERE targetClass = 'All Classes' OR targetClass = :className ORDER BY isPinned DESC, date DESC")
    fun getNoticesForClass(className: String): Flow<List<Notice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: Notice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<Notice>)

    @Delete
    suspend fun deleteNotice(notice: Notice)

    // --- Timetable ---
    @Query("SELECT * FROM timetable WHERE className = :className AND dayOfWeek = :day ORDER BY periodNumber ASC")
    fun getTimetableForDay(className: String, day: String): Flow<List<TimetableEntry>>

    @Query("SELECT * FROM timetable WHERE className = :className ORDER BY periodNumber ASC")
    fun getTimetableByClass(className: String): Flow<List<TimetableEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableEntry(entry: TimetableEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableEntries(entries: List<TimetableEntry>)

    @Delete
    suspend fun deleteTimetableEntry(entry: TimetableEntry)

    // --- Exams & Syllabus ---
    @Query("SELECT * FROM exams WHERE className = :className ORDER BY date ASC")
    fun getExamsByClass(className: String): Flow<List<ExamItem>>

    @Query("SELECT * FROM exams ORDER BY date ASC")
    fun getAllExams(): Flow<List<ExamItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: ExamItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExams(exams: List<ExamItem>)

    @Delete
    suspend fun deleteExam(exam: ExamItem)

    // --- Results / Marksheets ---
    @Query("SELECT * FROM results WHERE studentId = :studentId ORDER BY subject ASC")
    fun getResultsByStudent(studentId: String): Flow<List<ResultItem>>

    @Query("SELECT * FROM results WHERE className = :className ORDER BY studentName ASC, subject ASC")
    fun getResultsByClass(className: String): Flow<List<ResultItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ResultItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<ResultItem>)

    @Delete
    suspend fun deleteResult(result: ResultItem)

    // --- Fees ---
    @Query("SELECT * FROM fees WHERE studentId = :studentId ORDER BY dueDate DESC")
    fun getFeesByStudent(studentId: String): Flow<List<FeeRecord>>

    @Query("SELECT * FROM fees ORDER BY studentName ASC")
    fun getAllFees(): Flow<List<FeeRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeRecord(fee: FeeRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeRecords(fees: List<FeeRecord>)

    @Update
    suspend fun updateFeeRecord(fee: FeeRecord)

    // --- Attendance ---
    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceByStudent(studentId: String): Flow<List<AttendanceRecord>>

    @Query("SELECT * FROM attendance WHERE className = :className AND date = :date")
    fun getAttendanceByClassAndDate(className: String, date: String): Flow<List<AttendanceRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceRecord(record: AttendanceRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceRecords(records: List<AttendanceRecord>)

    // --- Notifications ---
    @Query("SELECT * FROM push_notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<PushNotificationLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: PushNotificationLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<PushNotificationLog>)

    @Query("UPDATE push_notifications SET isRead = 1 WHERE id = :id")
    suspend fun markNotificationAsRead(id: String)

    @Query("UPDATE push_notifications SET isRead = 1")
    suspend fun markAllNotificationsAsRead()
}
