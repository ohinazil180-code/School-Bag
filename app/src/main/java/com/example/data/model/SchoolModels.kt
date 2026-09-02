package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(
    @PrimaryKey val id: String,
    val username: String, // unique username or email
    val password: String,
    val role: String,     // "STUDENT" or "ADMIN"
    val studentId: String? = null,
    val fullName: String,
    val email: String,
    val phone: String,
    val className: String = "",
    val section: String = "",
    val rollNo: Int = 1,
    val parentName: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val id: String,
    val studentCode: String, // e.g. "DSB-2026-8A01"
    val name: String,
    val className: String, // e.g. "Class 8"
    val section: String,   // e.g. "A"
    val rollNo: Int,
    val parentName: String,
    val parentPhone: String,
    val parentEmail: String,
    val avatarUrl: String = "",
    val feeStatus: String = "Paid", // "Paid", "Pending", "Overdue"
    val pendingAmount: Double = 0.0,
    val totalAttendanceDays: Int = 120,
    val presentDays: Int = 112
)

@Entity(tableName = "classes")
data class ClassItem(
    @PrimaryKey val id: String,
    val name: String,     // e.g. "Class 8"
    val section: String,  // e.g. "A"
    val classTeacher: String = "Mrs. Sunita Rao",
    val roomNumber: String = "Room 204"
)

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey val id: String,
    val name: String,
    val code: String,
    val teacherName: String,
    val iconName: String = "book",
    val colorHex: String = "#FF9F45"
)

@Entity(tableName = "homework")
data class Homework(
    @PrimaryKey val id: String,
    val className: String,
    val subject: String,
    val title: String,
    val description: String,
    val assignedDate: String,
    val dueDate: String,
    val attachmentUrl: String = "",
    val isCompleted: Boolean = false
)

@Entity(tableName = "notes")
data class NoteItem(
    @PrimaryKey val id: String,
    val className: String,
    val subject: String,
    val title: String,
    val chapter: String,
    val description: String,
    val fileUrl: String,
    val fileType: String = "PDF", // "PDF", "Doc", "Link"
    val dateAdded: String
)

@Entity(tableName = "notices")
data class Notice(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val date: String,
    val targetClass: String = "All Classes",
    val isPinned: Boolean = false,
    val priority: String = "Normal", // "High", "Normal", "Urgent"
    val attachmentUrl: String = ""
)

@Entity(tableName = "timetable")
data class TimetableEntry(
    @PrimaryKey val id: String,
    val className: String,
    val dayOfWeek: String, // "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    val periodNumber: Int,
    val timeSlot: String,  // "08:30 AM - 09:15 AM"
    val subject: String,
    val teacher: String,
    val room: String = "Room 101"
)

@Entity(tableName = "exams")
data class ExamItem(
    @PrimaryKey val id: String,
    val className: String,
    val examName: String, // "Mid-Term Examination 2026"
    val subject: String,
    val date: String,
    val timeSlot: String,
    val maxMarks: Int = 100,
    val passingMarks: Int = 40,
    val syllabus: String,
    val room: String = "Hall A"
)

@Entity(tableName = "results")
data class ResultItem(
    @PrimaryKey val id: String,
    val studentId: String,
    val studentName: String,
    val className: String,
    val examName: String,
    val subject: String,
    val marksObtained: Double,
    val maxMarks: Double = 100.0,
    val grade: String,
    val remarks: String = "Good Performance"
)

@Entity(tableName = "fees")
data class FeeRecord(
    @PrimaryKey val id: String,
    val studentId: String,
    val studentName: String,
    val className: String,
    val term: String, // "Term 1 (2026-27)"
    val tuitionFee: Double,
    val busFee: Double,
    val libraryFee: Double,
    val labFee: Double,
    val totalFee: Double,
    val paidAmount: Double,
    val status: String, // "Paid", "Pending", "Partially Paid"
    val dueDate: String,
    val receiptNo: String = "DSB-REC-2026-09"
)

@Entity(tableName = "attendance")
data class AttendanceRecord(
    @PrimaryKey val id: String,
    val studentId: String,
    val studentName: String,
    val className: String,
    val date: String, // "YYYY-MM-DD"
    val status: String, // "PRESENT", "ABSENT", "LEAVE", "HOLIDAY"
    val remarks: String = ""
)

@Entity(tableName = "study_materials")
data class StudyMaterial(
    @PrimaryKey val id: String,
    val className: String,
    val subject: String,
    val title: String,
    val topic: String,
    val type: String, // "PDF", "WORKSHEET", "QUESTION_PAPER", "REFERENCE"
    val fileUrl: String,
    val sizeString: String = "2.4 MB"
)

@Entity(tableName = "video_lectures")
data class VideoLecture(
    @PrimaryKey val id: String,
    val className: String,
    val subject: String,
    val title: String,
    val chapter: String,
    val duration: String,
    val videoUrl: String,
    val thumbnailUrl: String = "",
    val teacherName: String
)

@Entity(tableName = "push_notifications")
data class PushNotificationLog(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val targetClass: String = "All Classes",
    val isRead: Boolean = false,
    val type: String = "ANNOUNCEMENT" // "HOMEWORK", "NOTICE", "EXAM", "ATTENDANCE", "ANNOUNCEMENT"
)
