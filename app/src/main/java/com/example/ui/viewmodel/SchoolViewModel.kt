package com.example.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
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
import com.example.data.preferences.PreferencesManager
import com.example.data.preferences.UserRole
import com.example.data.repository.SchoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class UserNavTab(val title: String, val icon: String) {
    HOME("Home", "home"),
    HOMEWORK("Homework", "assignment"),
    TIMETABLE("Timetable", "schedule"),
    NOTICES("Notices", "campaign"),
    MORE("More", "grid_view")
}

enum class AdminSection(val title: String, val icon: String) {
    DASHBOARD("Dashboard", "dashboard"),
    CLASSES_SUBJECTS("Classes & Subjects", "category"),
    HOMEWORK("Homework", "edit_note"),
    NOTES_MATERIALS("Notes & Materials", "menu_book"),
    NOTICES("Notices", "campaign"),
    TIMETABLE("Timetable", "calendar_month"),
    EXAMS("Exams & Syllabus", "quiz"),
    RESULTS("Marks & Results", "grade"),
    ATTENDANCE("Mark Attendance", "how_to_reg"),
    STUDENTS("Student Directory", "people"),
    SEND_NOTIF("Send Alerts", "send")
}

class SchoolViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = SchoolRepository(db.schoolDao())
    val preferencesManager = PreferencesManager(application)

    // User preferences & Session state
    val isDarkMode: StateFlow<Boolean> = preferencesManager.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val currentRole: StateFlow<UserRole> = preferencesManager.userRole
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserRole.STUDENT_PARENT)

    val activeStudentId: StateFlow<String> = preferencesManager.activeStudentId
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "std_01")

    val isLoggedIn: StateFlow<Boolean> = preferencesManager.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val loggedInUsername: StateFlow<String?> = preferencesManager.loggedInUsername
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val loggedInUserName: StateFlow<String?> = preferencesManager.loggedInUserName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Navigation state
    private val _currentUserTab = MutableStateFlow(UserNavTab.HOME)
    val currentUserTab: StateFlow<UserNavTab> = _currentUserTab.asStateFlow()

    private val _currentAdminSection = MutableStateFlow(AdminSection.DASHBOARD)
    val currentAdminSection: StateFlow<AdminSection> = _currentAdminSection.asStateFlow()

    private val _userMoreSubscreen = MutableStateFlow<String?>(null)
    val userMoreSubscreen: StateFlow<String?> = _userMoreSubscreen.asStateFlow()

    // Data streams from Room
    val allStudents: StateFlow<List<Student>> = repository.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeStudent: StateFlow<Student?> = combine(allStudents, activeStudentId) { students, id ->
        students.find { it.id == id } ?: students.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allClasses: StateFlow<List<ClassItem>> = repository.allClasses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSubjects: StateFlow<List<Subject>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allHomework: StateFlow<List<Homework>> = repository.allHomework
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotes: StateFlow<List<NoteItem>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudyMaterials: StateFlow<List<StudyMaterial>> = repository.allStudyMaterials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allVideoLectures: StateFlow<List<VideoLecture>> = repository.allVideoLectures
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotices: StateFlow<List<Notice>> = repository.allNotices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allExams: StateFlow<List<ExamItem>> = repository.allExams
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotifications: StateFlow<List<PushNotificationLog>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Student Scoped Flows
    val currentStudentClass: StateFlow<String> = activeStudent.combine(activeStudent) { _, student ->
        student?.className ?: "Class 8"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Class 8")

    val studentResults: StateFlow<List<ResultItem>> = activeStudentId.combine(allStudents) { id, _ ->
        repository.getResultsByStudent(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getResultsByStudent("std_01"))
        .combine(repository.getResultsByStudent("std_01")) { _, res -> res }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studentFees: StateFlow<List<FeeRecord>> = activeStudentId.combine(allStudents) { id, _ ->
        repository.getFeesByStudent(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getFeesByStudent("std_01"))
        .combine(repository.getFeesByStudent("std_01")) { _, fees -> fees }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studentAttendance: StateFlow<List<AttendanceRecord>> = activeStudentId.combine(allStudents) { id, _ ->
        repository.getAttendanceByStudent(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getAttendanceByStudent("std_01"))
        .combine(repository.getAttendanceByStudent("std_01")) { _, att -> att }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI state feedback
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    // Navigation setters
    fun setUserTab(tab: UserNavTab) {
        _currentUserTab.value = tab
        _userMoreSubscreen.value = null
    }

    fun setAdminSection(section: AdminSection) {
        _currentAdminSection.value = section
    }

    fun navigateUserSubscreen(screenId: String?) {
        _userMoreSubscreen.value = screenId
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            preferencesManager.setDarkMode(!isDarkMode.value)
        }
    }

    fun switchRole(newRole: UserRole) {
        viewModelScope.launch {
            preferencesManager.setUserRole(newRole)
            _statusMessage.value = "Switched to ${if (newRole == UserRole.ADMIN_TEACHER) "School Admin Mode" else "Student & Parent Mode"}"
        }
    }

    fun switchActiveChild(studentId: String) {
        viewModelScope.launch {
            preferencesManager.setActiveStudentId(studentId)
            val student = allStudents.value.find { it.id == studentId }
            _statusMessage.value = "Active profile: ${student?.name ?: "Student"}"
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    // --- Authentication & Account Management ---

    fun loginAdmin(
        usernameInput: String,
        passwordInput: String,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        val u = usernameInput.trim().lowercase()
        val p = passwordInput.trim()

        if (u.isBlank()) {
            onError("Please enter Admin username.")
            return
        }
        if (p.isBlank()) {
            onError("Please enter Admin password.")
            return
        }

        viewModelScope.launch {
            // Check fixed credentials or user accounts table
            val isFixedAdmin = (u == "admin" || u == "admin@school.com" || u == "admin@digitalschoolbag.edu") && p == "admin123"
            val dbUser = repository.getUserByUsernameOrEmail(u)
            val isDbAdmin = dbUser != null && dbUser.role == "ADMIN" && dbUser.password == p

            if (isFixedAdmin || isDbAdmin) {
                preferencesManager.setLoggedIn(
                    loggedIn = true,
                    role = UserRole.ADMIN_TEACHER,
                    username = "admin",
                    fullName = "School Administrator"
                )
                _currentAdminSection.value = AdminSection.DASHBOARD
                _statusMessage.value = "Welcome, School Administrator!"
                onSuccess()
            } else {
                onError("Invalid Admin credentials! Fixed credentials: Username 'admin', Password 'admin123'")
            }
        }
    }

    fun loginStudent(
        usernameOrEmailOrCode: String,
        passwordInput: String,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        val input = usernameOrEmailOrCode.trim()
        val p = passwordInput.trim()

        if (input.isBlank()) {
            onError("Please enter Student username, Student ID, or Email.")
            return
        }
        if (p.isBlank()) {
            onError("Please enter your password.")
            return
        }

        viewModelScope.launch {
            // First check user_accounts table
            val dbUser = repository.getUserByUsernameOrEmail(input.lowercase())
            if (dbUser != null && dbUser.role == "STUDENT") {
                if (dbUser.password == p) {
                    val sId = dbUser.studentId ?: "std_01"
                    preferencesManager.setLoggedIn(
                        loggedIn = true,
                        role = UserRole.STUDENT_PARENT,
                        username = dbUser.username,
                        fullName = dbUser.fullName,
                        studentId = sId
                    )
                    _currentUserTab.value = UserNavTab.HOME
                    _userMoreSubscreen.value = null
                    _statusMessage.value = "Welcome back, ${dbUser.fullName}!"
                    onSuccess()
                    return@launch
                } else {
                    onError("Incorrect password. Please try again.")
                    return@launch
                }
            }

            // Also check if matches sample student code or student record
            val matchedStudent = allStudents.value.find {
                it.studentCode.equals(input, ignoreCase = true) ||
                it.name.equals(input, ignoreCase = true) ||
                it.parentEmail.equals(input, ignoreCase = true)
            }

            if (matchedStudent != null) {
                if (p == "student123" || p == "123456" || p == matchedStudent.studentCode) {
                    preferencesManager.setLoggedIn(
                        loggedIn = true,
                        role = UserRole.STUDENT_PARENT,
                        username = matchedStudent.name.lowercase().replace(" ", "."),
                        fullName = matchedStudent.name,
                        studentId = matchedStudent.id
                    )
                    _currentUserTab.value = UserNavTab.HOME
                    _userMoreSubscreen.value = null
                    _statusMessage.value = "Welcome back, ${matchedStudent.name}!"
                    onSuccess()
                    return@launch
                } else {
                    onError("Incorrect password. Default sample password: student123")
                    return@launch
                }
            }

            onError("Student account not found. Please verify your username or register a new student account below.")
        }
    }

    fun registerStudent(
        fullName: String,
        username: String,
        passwordInput: String,
        confirmPasswordInput: String,
        className: String,
        section: String,
        rollNo: Int,
        parentName: String,
        parentPhone: String,
        parentEmail: String,
        onError: (String) -> Unit,
        onSuccess: (Student) -> Unit
    ) {
        val fName = fullName.trim()
        val uName = username.trim().lowercase()
        val pwd = passwordInput.trim()
        val cPwd = confirmPasswordInput.trim()
        val pName = parentName.trim()
        val pPhone = parentPhone.trim()
        val pEmail = parentEmail.trim()

        if (fName.isBlank()) {
            onError("Please enter student's full name.")
            return
        }
        if (uName.length < 3) {
            onError("Username must be at least 3 characters long.")
            return
        }
        if (pwd.length < 4) {
            onError("Password must be at least 4 characters long.")
            return
        }
        if (pwd != cPwd) {
            onError("Passwords do not match!")
            return
        }
        if (pName.isBlank()) {
            onError("Please enter parent / guardian name.")
            return
        }
        if (pPhone.isBlank()) {
            onError("Please enter parent phone number.")
            return
        }

        viewModelScope.launch {
            // Check if username already exists
            val existing = repository.getUserByUsernameOrEmail(uName)
            if (existing != null) {
                onError("Username '$uName' is already registered. Please choose another username or login.")
                return@launch
            }

            val newStudentId = "std_" + UUID.randomUUID().toString().take(8)
            val numClass = className.replace(Regex("[^0-9]"), "").ifBlank { "8" }
            val rollFormatted = rollNo.toString().padStart(2, '0')
            val studentCode = "DSB-2026-${numClass}${section}${rollFormatted}"

            val newStudent = Student(
                id = newStudentId,
                studentCode = studentCode,
                name = fName,
                className = className,
                section = section,
                rollNo = rollNo,
                parentName = pName,
                parentPhone = pPhone,
                parentEmail = if (pEmail.isNotBlank()) pEmail else "$uName@student.digitalschoolbag.edu",
                avatarUrl = "",
                feeStatus = "Paid",
                pendingAmount = 0.0,
                totalAttendanceDays = 100,
                presentDays = 96
            )
            repository.insertStudent(newStudent)

            val newAccount = UserAccount(
                id = "acc_" + UUID.randomUUID().toString().take(8),
                username = uName,
                password = pwd,
                role = "STUDENT",
                studentId = newStudentId,
                fullName = fName,
                email = if (pEmail.isNotBlank()) pEmail else "$uName@student.digitalschoolbag.edu",
                phone = pPhone,
                className = className,
                section = section,
                rollNo = rollNo,
                parentName = pName
            )
            repository.insertUserAccount(newAccount)

            // Seed student-specific fee & attendance records
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val fee = FeeRecord(
                id = "fee_" + UUID.randomUUID().toString().take(8),
                studentId = newStudentId,
                studentName = fName,
                className = className,
                term = "Term 1 (2026-27)",
                tuitionFee = 18000.0,
                busFee = 4500.0,
                libraryFee = 1200.0,
                labFee = 1500.0,
                totalFee = 25200.0,
                paidAmount = 25200.0,
                status = "Paid",
                dueDate = "2026-09-30",
                receiptNo = "DSB-REC-${UUID.randomUUID().toString().take(6).uppercase()}"
            )
            repository.insertFeeRecord(fee)

            val att = AttendanceRecord(
                id = "att_" + UUID.randomUUID().toString().take(8),
                studentId = newStudentId,
                studentName = fName,
                className = className,
                date = today,
                status = "PRESENT",
                remarks = "Registered & Active"
            )
            repository.insertAttendanceRecord(att)

            // Auto-login newly registered student
            preferencesManager.setLoggedIn(
                loggedIn = true,
                role = UserRole.STUDENT_PARENT,
                username = uName,
                fullName = fName,
                studentId = newStudentId
            )
            _currentUserTab.value = UserNavTab.HOME
            _userMoreSubscreen.value = null
            _statusMessage.value = "Account created successfully! Welcome to Digital School Bag, $fName 🎉"
            onSuccess(newStudent)
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesManager.logout()
            _currentUserTab.value = UserNavTab.HOME
            _userMoreSubscreen.value = null
            _currentAdminSection.value = AdminSection.DASHBOARD
            _statusMessage.value = "You have been logged out safely."
        }
    }

    // Homework actions
    fun toggleHomeworkCompletion(homework: Homework) {
        viewModelScope.launch {
            val updated = homework.copy(isCompleted = !homework.isCompleted)
            repository.updateHomework(updated)
            _statusMessage.value = if (updated.isCompleted) "Marked homework as completed! 🎉" else "Homework marked as pending."
        }
    }

    fun addHomework(
        className: String,
        subject: String,
        title: String,
        description: String,
        dueDate: String,
        attachmentUrl: String
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val newHw = Homework(
                id = "hw_" + UUID.randomUUID().toString().take(8),
                className = className,
                subject = subject,
                title = title,
                description = description,
                assignedDate = today,
                dueDate = dueDate,
                attachmentUrl = attachmentUrl,
                isCompleted = false
            )
            repository.insertHomework(newHw)
            
            // Dispatch notification alert
            val notif = PushNotificationLog(
                id = "notif_" + UUID.randomUUID().toString().take(8),
                title = "📝 New Homework: $title",
                message = "New assignment in $subject for $className. Due: $dueDate",
                timestamp = "Just now",
                targetClass = className,
                type = "HOMEWORK"
            )
            repository.insertNotification(notif)
            _statusMessage.value = "Homework added & alert dispatched successfully!"
        }
    }

    fun deleteHomework(homework: Homework) {
        viewModelScope.launch {
            repository.deleteHomework(homework)
            _statusMessage.value = "Homework deleted."
        }
    }

    // Notes & Study Materials actions
    fun addNote(
        className: String,
        subject: String,
        title: String,
        chapter: String,
        description: String,
        fileUrl: String,
        fileType: String
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val note = NoteItem(
                id = "note_" + UUID.randomUUID().toString().take(8),
                className = className,
                subject = subject,
                title = title,
                chapter = chapter,
                description = description,
                fileUrl = fileUrl,
                fileType = fileType,
                dateAdded = today
            )
            repository.insertNote(note)
            _statusMessage.value = "Note added successfully!"
        }
    }

    fun deleteNote(note: NoteItem) {
        viewModelScope.launch {
            repository.deleteNote(note)
            _statusMessage.value = "Note removed."
        }
    }

    fun addStudyMaterial(
        className: String,
        subject: String,
        title: String,
        topic: String,
        type: String,
        fileUrl: String,
        size: String
    ) {
        viewModelScope.launch {
            val material = StudyMaterial(
                id = "sm_" + UUID.randomUUID().toString().take(8),
                className = className,
                subject = subject,
                title = title,
                topic = topic,
                type = type,
                fileUrl = fileUrl,
                sizeString = size.ifBlank { "2.5 MB" }
            )
            repository.insertStudyMaterial(material)
            _statusMessage.value = "Study material added!"
        }
    }

    fun deleteStudyMaterial(material: StudyMaterial) {
        viewModelScope.launch {
            repository.deleteStudyMaterial(material)
            _statusMessage.value = "Study material removed."
        }
    }

    // Video Lecture actions
    fun addVideoLecture(
        className: String,
        subject: String,
        title: String,
        chapter: String,
        duration: String,
        videoUrl: String,
        teacherName: String
    ) {
        viewModelScope.launch {
            val video = VideoLecture(
                id = "vid_" + UUID.randomUUID().toString().take(8),
                className = className,
                subject = subject,
                title = title,
                chapter = chapter,
                duration = duration.ifBlank { "30 mins" },
                videoUrl = videoUrl,
                thumbnailUrl = "",
                teacherName = teacherName
            )
            repository.insertVideoLecture(video)
            _statusMessage.value = "Video lecture added!"
        }
    }

    fun deleteVideoLecture(video: VideoLecture) {
        viewModelScope.launch {
            repository.deleteVideoLecture(video)
            _statusMessage.value = "Video lecture removed."
        }
    }

    // Notice actions
    fun addNotice(
        title: String,
        content: String,
        targetClass: String,
        priority: String,
        isPinned: Boolean,
        attachmentUrl: String
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val notice = Notice(
                id = "not_" + UUID.randomUUID().toString().take(8),
                title = title,
                content = content,
                date = today,
                targetClass = targetClass,
                isPinned = isPinned,
                priority = priority,
                attachmentUrl = attachmentUrl
            )
            repository.insertNotice(notice)

            val notif = PushNotificationLog(
                id = "notif_" + UUID.randomUUID().toString().take(8),
                title = "📢 School Notice: $title",
                message = content.take(80) + if (content.length > 80) "..." else "",
                timestamp = "Just now",
                targetClass = targetClass,
                type = "NOTICE"
            )
            repository.insertNotification(notif)
            _statusMessage.value = "Notice published & broadcasted!"
        }
    }

    fun deleteNotice(notice: Notice) {
        viewModelScope.launch {
            repository.deleteNotice(notice)
            _statusMessage.value = "Notice removed."
        }
    }

    // Timetable actions
    fun addTimetableEntry(
        className: String,
        dayOfWeek: String,
        periodNumber: Int,
        timeSlot: String,
        subject: String,
        teacher: String,
        room: String
    ) {
        viewModelScope.launch {
            val entry = TimetableEntry(
                id = "tt_" + UUID.randomUUID().toString().take(8),
                className = className,
                dayOfWeek = dayOfWeek,
                periodNumber = periodNumber,
                timeSlot = timeSlot,
                subject = subject,
                teacher = teacher,
                room = room
            )
            repository.insertTimetableEntry(entry)
            _statusMessage.value = "Timetable slot updated!"
        }
    }

    fun deleteTimetableEntry(entry: TimetableEntry) {
        viewModelScope.launch {
            repository.deleteTimetableEntry(entry)
            _statusMessage.value = "Timetable entry deleted."
        }
    }

    // Exam & Syllabus actions
    fun addExam(
        className: String,
        examName: String,
        subject: String,
        date: String,
        timeSlot: String,
        maxMarks: Int,
        passingMarks: Int,
        syllabus: String,
        room: String
    ) {
        viewModelScope.launch {
            val exam = ExamItem(
                id = "ex_" + UUID.randomUUID().toString().take(8),
                className = className,
                examName = examName,
                subject = subject,
                date = date,
                timeSlot = timeSlot,
                maxMarks = maxMarks,
                passingMarks = passingMarks,
                syllabus = syllabus,
                room = room
            )
            repository.insertExam(exam)

            val notif = PushNotificationLog(
                id = "notif_" + UUID.randomUUID().toString().take(8),
                title = "📅 Exam Schedule Added: $subject",
                message = "$examName scheduled for $date ($timeSlot)",
                timestamp = "Just now",
                targetClass = className,
                type = "EXAM"
            )
            repository.insertNotification(notif)
            _statusMessage.value = "Exam schedule & syllabus saved!"
        }
    }

    fun deleteExam(exam: ExamItem) {
        viewModelScope.launch {
            repository.deleteExam(exam)
            _statusMessage.value = "Exam removed."
        }
    }

    // Results / Marksheet actions
    fun addResult(
        studentId: String,
        studentName: String,
        className: String,
        examName: String,
        subject: String,
        marksObtained: Double,
        maxMarks: Double,
        remarks: String
    ) {
        viewModelScope.launch {
            val percentage = (marksObtained / maxMarks) * 100.0
            val grade = when {
                percentage >= 90 -> "A+"
                percentage >= 80 -> "A"
                percentage >= 70 -> "B+"
                percentage >= 60 -> "B"
                percentage >= 50 -> "C"
                percentage >= 40 -> "D"
                else -> "F"
            }
            val result = ResultItem(
                id = "res_" + UUID.randomUUID().toString().take(8),
                studentId = studentId,
                studentName = studentName,
                className = className,
                examName = examName,
                subject = subject,
                marksObtained = marksObtained,
                maxMarks = maxMarks,
                grade = grade,
                remarks = remarks.ifBlank { "Evaluated" }
            )
            repository.insertResult(result)
            _statusMessage.value = "Marks recorded for $studentName ($grade)!"
        }
    }

    fun deleteResult(result: ResultItem) {
        viewModelScope.launch {
            repository.deleteResult(result)
            _statusMessage.value = "Result entry removed."
        }
    }

    // Attendance marking actions
    fun markAttendance(
        studentId: String,
        studentName: String,
        className: String,
        date: String,
        status: String,
        remarks: String = ""
    ) {
        viewModelScope.launch {
            val record = AttendanceRecord(
                id = "att_${studentId}_$date",
                studentId = studentId,
                studentName = studentName,
                className = className,
                date = date,
                status = status,
                remarks = remarks
            )
            repository.insertAttendanceRecord(record)
            _statusMessage.value = "Attendance marked: $studentName -> $status"
        }
    }

    fun markAllClassPresent(className: String, date: String) {
        viewModelScope.launch {
            val classStudents = allStudents.value.filter { it.className == className }
            val records = classStudents.map { student ->
                AttendanceRecord(
                    id = "att_${student.id}_$date",
                    studentId = student.id,
                    studentName = student.name,
                    className = className,
                    date = date,
                    status = "PRESENT"
                )
            }
            repository.insertAttendanceRecords(records)
            _statusMessage.value = "Marked all ${classStudents.size} students as PRESENT for $date!"
        }
    }

    // Class & Subject actions
    fun addClass(name: String, section: String, teacher: String, room: String) {
        viewModelScope.launch {
            val item = ClassItem(
                id = "cls_" + UUID.randomUUID().toString().take(6),
                name = name,
                section = section,
                classTeacher = teacher.ifBlank { "Unassigned" },
                roomNumber = room.ifBlank { "Room 101" }
            )
            repository.insertClass(item)
            _statusMessage.value = "Class $name-$section added!"
        }
    }

    fun deleteClass(classItem: ClassItem) {
        viewModelScope.launch {
            repository.deleteClass(classItem)
            _statusMessage.value = "Class ${classItem.name} removed."
        }
    }

    fun addSubject(name: String, code: String, teacher: String, colorHex: String) {
        viewModelScope.launch {
            val subject = Subject(
                id = "sub_" + UUID.randomUUID().toString().take(6),
                name = name,
                code = code,
                teacherName = teacher,
                colorHex = colorHex.ifBlank { "#FF9F45" }
            )
            repository.insertSubject(subject)
            _statusMessage.value = "Subject $name added!"
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            repository.deleteSubject(subject)
            _statusMessage.value = "Subject ${subject.name} removed."
        }
    }

    // Student Management actions
    fun addStudent(
        name: String,
        className: String,
        section: String,
        rollNo: Int,
        parentName: String,
        parentPhone: String,
        parentEmail: String
    ) {
        viewModelScope.launch {
            val studentCode = "DSB-2026-${className.replace("Class ", "")}$section${String.format(Locale.getDefault(), "%02d", rollNo)}"
            val student = Student(
                id = "std_" + UUID.randomUUID().toString().take(8),
                studentCode = studentCode,
                name = name,
                className = className,
                section = section,
                rollNo = rollNo,
                parentName = parentName,
                parentPhone = parentPhone,
                parentEmail = parentEmail,
                feeStatus = "Paid",
                pendingAmount = 0.0,
                totalAttendanceDays = 120,
                presentDays = 120
            )
            repository.insertStudent(student)
            _statusMessage.value = "Student registered with ID: $studentCode"
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            repository.deleteStudent(student)
            _statusMessage.value = "Student ${student.name} removed."
        }
    }

    // Push notifications broadcast
    fun sendBroadcastNotification(
        title: String,
        message: String,
        targetClass: String,
        type: String
    ) {
        viewModelScope.launch {
            val notif = PushNotificationLog(
                id = "notif_" + UUID.randomUUID().toString().take(8),
                title = title,
                message = message,
                timestamp = "Just now",
                targetClass = targetClass,
                isRead = false,
                type = type
            )
            repository.insertNotification(notif)
            _statusMessage.value = "Push alert broadcasted to $targetClass via FCM pipeline!"
        }
    }

    fun markNotificationRead(id: String) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
            _statusMessage.value = "All notifications marked as read."
        }
    }

    // External link launcher helper
    fun openExternalLink(url: String) {
        if (url.isBlank()) {
            Toast.makeText(getApplication(), "No attachment URL available", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            getApplication<Application>().startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(getApplication(), "Opening in browser: $url", Toast.LENGTH_SHORT).show()
        }
    }
}
