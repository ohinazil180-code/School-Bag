package com.example.data.repository

import com.example.data.local.SchoolDao
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SchoolRepository(
    private val schoolDao: SchoolDao,
    private val externalScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    init {
        externalScope.launch {
            seedDatabaseIfEmpty()
        }
    }

    suspend fun seedDatabaseIfEmpty() {
        val existingStudents = schoolDao.getAllStudents().firstOrNull()
        if (existingStudents.isNullOrEmpty()) {
            schoolDao.insertUserAccounts(SampleData.sampleUserAccounts)
            schoolDao.insertStudents(SampleData.sampleStudents)
            schoolDao.insertClasses(SampleData.sampleClasses)
            schoolDao.insertSubjects(SampleData.sampleSubjects)
            schoolDao.insertHomeworkList(SampleData.sampleHomework)
            schoolDao.insertNotes(SampleData.sampleNotes)
            schoolDao.insertNotices(SampleData.sampleNotices)
            schoolDao.insertTimetableEntries(SampleData.sampleTimetable)
            schoolDao.insertExams(SampleData.sampleExams)
            schoolDao.insertResults(SampleData.sampleResults)
            schoolDao.insertFeeRecords(SampleData.sampleFees)
            schoolDao.insertAttendanceRecords(SampleData.sampleAttendance)
            schoolDao.insertStudyMaterials(SampleData.sampleStudyMaterials)
            schoolDao.insertVideoLectures(SampleData.sampleVideoLectures)
            schoolDao.insertNotifications(SampleData.sampleNotifications)
        } else {
            // Also verify user accounts exist
            val existingUsers = schoolDao.getAllUserAccounts().firstOrNull()
            if (existingUsers.isNullOrEmpty()) {
                schoolDao.insertUserAccounts(SampleData.sampleUserAccounts)
            }
        }
    }

    // --- User Accounts ---
    val allUserAccounts: Flow<List<UserAccount>> = schoolDao.getAllUserAccounts()
    suspend fun getUserByUsernameOrEmail(username: String): UserAccount? = schoolDao.getUserByUsernameOrEmail(username)
    suspend fun getUserByStudentId(studentId: String): UserAccount? = schoolDao.getUserByStudentId(studentId)
    suspend fun insertUserAccount(userAccount: UserAccount) = schoolDao.insertUserAccount(userAccount)

    // --- Students ---
    val allStudents: Flow<List<Student>> = schoolDao.getAllStudents()
    fun getStudentById(id: String): Flow<Student?> = schoolDao.getStudentById(id)
    fun getStudentsByClass(className: String): Flow<List<Student>> = schoolDao.getStudentsByClass(className)
    suspend fun insertStudent(student: Student) = schoolDao.insertStudent(student)
    suspend fun updateStudent(student: Student) = schoolDao.updateStudent(student)
    suspend fun deleteStudent(student: Student) = schoolDao.deleteStudent(student)

    // --- Classes & Subjects ---
    val allClasses: Flow<List<ClassItem>> = schoolDao.getAllClasses()
    val allSubjects: Flow<List<Subject>> = schoolDao.getAllSubjects()
    suspend fun insertClass(classItem: ClassItem) = schoolDao.insertClass(classItem)
    suspend fun deleteClass(classItem: ClassItem) = schoolDao.deleteClass(classItem)
    suspend fun insertSubject(subject: Subject) = schoolDao.insertSubject(subject)
    suspend fun deleteSubject(subject: Subject) = schoolDao.deleteSubject(subject)

    // --- Homework ---
    val allHomework: Flow<List<Homework>> = schoolDao.getAllHomework()
    fun getHomeworkByClass(className: String): Flow<List<Homework>> = schoolDao.getHomeworkByClass(className)
    suspend fun insertHomework(homework: Homework) = schoolDao.insertHomework(homework)
    suspend fun updateHomework(homework: Homework) = schoolDao.updateHomework(homework)
    suspend fun deleteHomework(homework: Homework) = schoolDao.deleteHomework(homework)

    // --- Notes & Study Material ---
    val allNotes: Flow<List<NoteItem>> = schoolDao.getAllNotes()
    fun getNotesByClass(className: String): Flow<List<NoteItem>> = schoolDao.getNotesByClass(className)
    suspend fun insertNote(note: NoteItem) = schoolDao.insertNote(note)
    suspend fun deleteNote(note: NoteItem) = schoolDao.deleteNote(note)

    val allStudyMaterials: Flow<List<StudyMaterial>> = schoolDao.getAllStudyMaterials()
    fun getStudyMaterialsByClass(className: String): Flow<List<StudyMaterial>> = schoolDao.getStudyMaterialsByClass(className)
    suspend fun insertStudyMaterial(material: StudyMaterial) = schoolDao.insertStudyMaterial(material)
    suspend fun deleteStudyMaterial(material: StudyMaterial) = schoolDao.deleteStudyMaterial(material)

    // --- Video Lectures ---
    val allVideoLectures: Flow<List<VideoLecture>> = schoolDao.getAllVideoLectures()
    fun getVideoLecturesByClass(className: String): Flow<List<VideoLecture>> = schoolDao.getVideoLecturesByClass(className)
    suspend fun insertVideoLecture(video: VideoLecture) = schoolDao.insertVideoLecture(video)
    suspend fun deleteVideoLecture(video: VideoLecture) = schoolDao.deleteVideoLecture(video)

    // --- Notices ---
    val allNotices: Flow<List<Notice>> = schoolDao.getAllNotices()
    fun getNoticesForClass(className: String): Flow<List<Notice>> = schoolDao.getNoticesForClass(className)
    suspend fun insertNotice(notice: Notice) = schoolDao.insertNotice(notice)
    suspend fun deleteNotice(notice: Notice) = schoolDao.deleteNotice(notice)

    // --- Timetable ---
    fun getTimetableForDay(className: String, day: String): Flow<List<TimetableEntry>> = schoolDao.getTimetableForDay(className, day)
    fun getTimetableByClass(className: String): Flow<List<TimetableEntry>> = schoolDao.getTimetableByClass(className)
    suspend fun insertTimetableEntry(entry: TimetableEntry) = schoolDao.insertTimetableEntry(entry)
    suspend fun deleteTimetableEntry(entry: TimetableEntry) = schoolDao.deleteTimetableEntry(entry)

    // --- Exams ---
    val allExams: Flow<List<ExamItem>> = schoolDao.getAllExams()
    fun getExamsByClass(className: String): Flow<List<ExamItem>> = schoolDao.getExamsByClass(className)
    suspend fun insertExam(exam: ExamItem) = schoolDao.insertExam(exam)
    suspend fun deleteExam(exam: ExamItem) = schoolDao.deleteExam(exam)

    // --- Results ---
    fun getResultsByStudent(studentId: String): Flow<List<ResultItem>> = schoolDao.getResultsByStudent(studentId)
    fun getResultsByClass(className: String): Flow<List<ResultItem>> = schoolDao.getResultsByClass(className)
    suspend fun insertResult(result: ResultItem) = schoolDao.insertResult(result)
    suspend fun deleteResult(result: ResultItem) = schoolDao.deleteResult(result)

    // --- Fees ---
    fun getFeesByStudent(studentId: String): Flow<List<FeeRecord>> = schoolDao.getFeesByStudent(studentId)
    val allFees: Flow<List<FeeRecord>> = schoolDao.getAllFees()
    suspend fun insertFeeRecord(fee: FeeRecord) = schoolDao.insertFeeRecord(fee)
    suspend fun updateFeeRecord(fee: FeeRecord) = schoolDao.updateFeeRecord(fee)

    // --- Attendance ---
    fun getAttendanceByStudent(studentId: String): Flow<List<AttendanceRecord>> = schoolDao.getAttendanceByStudent(studentId)
    fun getAttendanceByClassAndDate(className: String, date: String): Flow<List<AttendanceRecord>> = schoolDao.getAttendanceByClassAndDate(className, date)
    suspend fun insertAttendanceRecord(record: AttendanceRecord) = schoolDao.insertAttendanceRecord(record)
    suspend fun insertAttendanceRecords(records: List<AttendanceRecord>) = schoolDao.insertAttendanceRecords(records)

    // --- Notifications ---
    val allNotifications: Flow<List<PushNotificationLog>> = schoolDao.getAllNotifications()
    suspend fun insertNotification(notification: PushNotificationLog) = schoolDao.insertNotification(notification)
    suspend fun markNotificationAsRead(id: String) = schoolDao.markNotificationAsRead(id)
    suspend fun markAllNotificationsAsRead() = schoolDao.markAllNotificationsAsRead()
}
