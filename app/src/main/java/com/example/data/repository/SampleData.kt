package com.example.data.repository

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

object SampleData {

    val sampleUserAccounts = listOf(
        UserAccount(
            id = "acc_admin",
            username = "admin",
            password = "admin123",
            role = "ADMIN",
            fullName = "School Principal / Administrator",
            email = "admin@digitalschoolbag.edu",
            phone = "+91 98765 00000"
        ),
        UserAccount(
            id = "acc_std_01",
            username = "aarav",
            password = "student123",
            role = "STUDENT",
            studentId = "std_01",
            fullName = "Aarav Sharma",
            email = "aarav@school.com",
            phone = "+91 98765 43210",
            className = "Class 8",
            section = "A",
            rollNo = 1,
            parentName = "Rajesh Sharma"
        ),
        UserAccount(
            id = "acc_std_02",
            username = "ananya",
            password = "student123",
            role = "STUDENT",
            studentId = "std_02",
            fullName = "Ananya Sharma",
            email = "ananya@school.com",
            phone = "+91 98765 43210",
            className = "Class 5",
            section = "B",
            rollNo = 12,
            parentName = "Rajesh Sharma"
        )
    )

    val sampleStudents = listOf(
        Student(
            id = "std_01",
            studentCode = "DSB-2026-8A01",
            name = "Aarav Sharma",
            className = "Class 8",
            section = "A",
            rollNo = 1,
            parentName = "Rajesh Sharma",
            parentPhone = "+91 98765 43210",
            parentEmail = "rajesh.sharma@example.com",
            avatarUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200",
            feeStatus = "Paid",
            pendingAmount = 0.0,
            totalAttendanceDays = 120,
            presentDays = 114
        ),
        Student(
            id = "std_02",
            studentCode = "DSB-2026-5B12",
            name = "Ananya Sharma",
            className = "Class 5",
            section = "B",
            rollNo = 12,
            parentName = "Rajesh Sharma",
            parentPhone = "+91 98765 43210",
            parentEmail = "rajesh.sharma@example.com",
            avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200",
            feeStatus = "Pending",
            pendingAmount = 4500.0,
            totalAttendanceDays = 120,
            presentDays = 110
        ),
        Student(
            id = "std_03",
            studentCode = "DSB-2026-8A02",
            name = "Vihaan Verma",
            className = "Class 8",
            section = "A",
            rollNo = 2,
            parentName = "Sunil Verma",
            parentPhone = "+91 98111 22334",
            parentEmail = "sunil.v@example.com",
            avatarUrl = "",
            feeStatus = "Paid",
            pendingAmount = 0.0,
            totalAttendanceDays = 120,
            presentDays = 108
        ),
        Student(
            id = "std_04",
            studentCode = "DSB-2026-8A03",
            name = "Riya Sengupta",
            className = "Class 8",
            section = "A",
            rollNo = 3,
            parentName = "Manoj Sengupta",
            parentPhone = "+91 98222 33445",
            parentEmail = "manoj.s@example.com",
            avatarUrl = "",
            feeStatus = "Paid",
            pendingAmount = 0.0,
            totalAttendanceDays = 120,
            presentDays = 118
        ),
        Student(
            id = "std_05",
            studentCode = "DSB-2026-8A04",
            name = "Kabir Patel",
            className = "Class 8",
            section = "A",
            rollNo = 4,
            parentName = "Vikram Patel",
            parentPhone = "+91 98333 44556",
            parentEmail = "vikram.p@example.com",
            avatarUrl = "",
            feeStatus = "Pending",
            pendingAmount = 6200.0,
            totalAttendanceDays = 120,
            presentDays = 105
        )
    )

    val sampleClasses = listOf(
        ClassItem(id = "cls_8a", name = "Class 8", section = "A", classTeacher = "Mrs. Sunita Rao", roomNumber = "Room 204"),
        ClassItem(id = "cls_8b", name = "Class 8", section = "B", classTeacher = "Mr. Amit Kapoor", roomNumber = "Room 205"),
        ClassItem(id = "cls_5b", name = "Class 5", section = "B", classTeacher = "Ms. Priya Nair", roomNumber = "Room 102"),
        ClassItem(id = "cls_9a", name = "Class 9", section = "A", classTeacher = "Dr. R. K. Mishra", roomNumber = "Room 301"),
        ClassItem(id = "cls_10a", name = "Class 10", section = "A", classTeacher = "Mrs. Vandana Joshi", roomNumber = "Room 305")
    )

    val sampleSubjects = listOf(
        Subject(id = "sub_math", name = "Mathematics", code = "MATH-08", teacherName = "Mr. Amit Kapoor", iconName = "calculate", colorHex = "#FF9F45"),
        Subject(id = "sub_sci", name = "Science", code = "SCI-08", teacherName = "Dr. R. K. Mishra", iconName = "science", colorHex = "#06D6A0"),
        Subject(id = "sub_eng", name = "English Literature", code = "ENG-08", teacherName = "Mrs. Sunita Rao", iconName = "menu_book", colorHex = "#3498DB"),
        Subject(id = "sub_sst", name = "Social Studies", code = "SST-08", teacherName = "Mrs. Vandana Joshi", iconName = "public", colorHex = "#9B59B6"),
        Subject(id = "sub_hin", name = "Hindi", code = "HIN-08", teacherName = "Mr. Devendra Shastri", iconName = "translate", colorHex = "#E74C3C"),
        Subject(id = "sub_cs", name = "Computer Science", code = "CS-08", teacherName = "Ms. Ananya Roy", iconName = "computer", colorHex = "#1ABC9C")
    )

    val sampleHomework = listOf(
        Homework(
            id = "hw_01",
            className = "Class 8",
            subject = "Mathematics",
            title = "Linear Equations Exercise 4.2",
            description = "Solve Questions 1 to 15 from NCERT Chapter 4 on graph sheet. Show complete step-by-step verification.",
            assignedDate = "2026-09-01",
            dueDate = "2026-09-03",
            attachmentUrl = "https://drive.google.com/file/d/sample-math-ch4-worksheet/view",
            isCompleted = false
        ),
        Homework(
            id = "hw_02",
            className = "Class 8",
            subject = "Science",
            title = "Cell Structure & Diagram",
            description = "Draw and label neat diagrams of Plant and Animal cells in the lab journal with key differences.",
            assignedDate = "2026-09-01",
            dueDate = "2026-09-04",
            attachmentUrl = "https://i.ibb.co/sample-cell-diagram.jpg",
            isCompleted = true
        ),
        Homework(
            id = "hw_03",
            className = "Class 8",
            subject = "English Literature",
            title = "The Tsunami - Summary & Q/A",
            description = "Write a 200-word critical analysis of Ignesious's story from Chapter 2.",
            assignedDate = "2026-08-31",
            dueDate = "2026-09-02",
            attachmentUrl = "",
            isCompleted = false
        ),
        Homework(
            id = "hw_04",
            className = "Class 8",
            subject = "Social Studies",
            title = "Resources & Sustainable Development",
            description = "Prepare a mind-map on Renewable vs Non-Renewable energy sources in India.",
            assignedDate = "2026-08-30",
            dueDate = "2026-09-05",
            attachmentUrl = "https://drive.google.com/file/d/sample-sst-mindmap/view",
            isCompleted = true
        )
    )

    val sampleNotes = listOf(
        NoteItem(
            id = "note_01",
            className = "Class 8",
            subject = "Science",
            title = "Force and Pressure - Complete Revision Notes",
            chapter = "Chapter 11",
            description = "Comprehensive summary covering contact forces, non-contact forces, atmospheric pressure and solved numericals.",
            fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            fileType = "PDF",
            dateAdded = "2026-08-28"
        ),
        NoteItem(
            id = "note_02",
            className = "Class 8",
            subject = "Mathematics",
            title = "Algebraic Expressions & Identities Formulas",
            chapter = "Chapter 9",
            description = "Key formulas, factorisation techniques, and common algebraic identity shortcuts.",
            fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            fileType = "PDF",
            dateAdded = "2026-08-25"
        ),
        NoteItem(
            id = "note_03",
            className = "Class 8",
            subject = "English Literature",
            title = "Glimpses of the Past - Character Sketches",
            chapter = "Unit 3",
            description = "Themes of India's freedom struggle, British East India company rule, and martyrs.",
            fileUrl = "https://drive.google.com/file/d/sample-english-notes/view",
            fileType = "Doc",
            dateAdded = "2026-08-22"
        ),
        NoteItem(
            id = "note_04",
            className = "Class 8",
            subject = "Computer Science",
            title = "Python Basics & Loops Handbook",
            chapter = "Module 2",
            description = "For loop, while loop syntax, condition statements with sample code snippets.",
            fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            fileType = "PDF",
            dateAdded = "2026-08-20"
        )
    )

    val sampleNotices = listOf(
        Notice(
            id = "not_01",
            title = "Annual Science Exhibition & Robot Showcase 2026",
            content = "Students are invited to submit working science project proposals by September 10th. Winning projects will represent the school at the State Science Fair.",
            date = "2026-09-01",
            targetClass = "All Classes",
            isPinned = true,
            priority = "High",
            attachmentUrl = "https://i.ibb.co/science-exhibition-poster.jpg"
        ),
        Notice(
            id = "not_02",
            title = "Mid-Term Examination Datesheet Released",
            content = "The Mid-Term Examination for Classes 6 to 10 will commence from September 18th, 2026. Hall tickets and seating arrangements will be available next week.",
            date = "2026-08-30",
            targetClass = "Class 8",
            isPinned = true,
            priority = "Urgent",
            attachmentUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
        ),
        Notice(
            id = "not_03",
            title = "Parent-Teacher Meeting (PTM) Schedule",
            content = "PTM for Term 1 progress discussion is scheduled for Saturday, September 12th between 08:30 AM to 01:00 PM. Roll number slots will be shared by class teachers.",
            date = "2026-08-28",
            targetClass = "All Classes",
            isPinned = false,
            priority = "Normal"
        ),
        Notice(
            id = "not_04",
            title = "Inter-School Swimming Championship Selections",
            content = "Trials for the Under-14 and Under-17 swim team will take place at the school Olympic pool on Friday after 3 PM.",
            date = "2026-08-26",
            targetClass = "Class 8",
            isPinned = false,
            priority = "Normal"
        )
    )

    val sampleTimetable = listOf(
        // Monday
        TimetableEntry(id = "tt_m1", className = "Class 8", dayOfWeek = "Monday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "Mathematics", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_m2", className = "Class 8", dayOfWeek = "Monday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "Science", teacher = "Dr. R. K. Mishra", room = "Sci Lab 1"),
        TimetableEntry(id = "tt_m3", className = "Class 8", dayOfWeek = "Monday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "English Literature", teacher = "Mrs. Sunita Rao", room = "Room 204"),
        TimetableEntry(id = "tt_m4", className = "Class 8", dayOfWeek = "Monday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "Social Studies", teacher = "Mrs. Vandana Joshi", room = "Room 204"),
        TimetableEntry(id = "tt_m5", className = "Class 8", dayOfWeek = "Monday", periodNumber = 5, timeSlot = "12:15 - 01:00 PM", subject = "Computer Science", teacher = "Ms. Ananya Roy", room = "Computer Lab"),
        TimetableEntry(id = "tt_m6", className = "Class 8", dayOfWeek = "Monday", periodNumber = 6, timeSlot = "01:00 - 01:45 PM", subject = "Physical Education", teacher = "Coach Rana", room = "Sports Ground"),

        // Tuesday
        TimetableEntry(id = "tt_t1", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "Science", teacher = "Dr. R. K. Mishra", room = "Sci Lab 1"),
        TimetableEntry(id = "tt_t2", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "Mathematics", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_t3", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "Hindi", teacher = "Mr. Devendra Shastri", room = "Room 204"),
        TimetableEntry(id = "tt_t4", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "English Literature", teacher = "Mrs. Sunita Rao", room = "Room 204"),
        TimetableEntry(id = "tt_t5", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 5, timeSlot = "12:15 - 01:00 PM", subject = "Library / Reading", teacher = "Mrs. Sunita Rao", room = "Central Library"),
        TimetableEntry(id = "tt_t6", className = "Class 8", dayOfWeek = "Tuesday", periodNumber = 6, timeSlot = "01:00 - 01:45 PM", subject = "Art & Craft", teacher = "Ms. Ritu Roy", room = "Art Studio"),

        // Wednesday
        TimetableEntry(id = "tt_w1", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "Mathematics", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_w2", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "Social Studies", teacher = "Mrs. Vandana Joshi", room = "Room 204"),
        TimetableEntry(id = "tt_w3", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "Science", teacher = "Dr. R. K. Mishra", room = "Sci Lab 1"),
        TimetableEntry(id = "tt_w4", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "Hindi", teacher = "Mr. Devendra Shastri", room = "Room 204"),
        TimetableEntry(id = "tt_w5", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 5, timeSlot = "12:15 - 01:00 PM", subject = "Computer Science", teacher = "Ms. Ananya Roy", room = "Computer Lab"),
        TimetableEntry(id = "tt_w6", className = "Class 8", dayOfWeek = "Wednesday", periodNumber = 6, timeSlot = "01:00 - 01:45 PM", subject = "Music & Drama", teacher = "Mr. David Sen", room = "Auditorium"),

        // Thursday
        TimetableEntry(id = "tt_th1", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "English Literature", teacher = "Mrs. Sunita Rao", room = "Room 204"),
        TimetableEntry(id = "tt_th2", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "Mathematics", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_th3", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "Science", teacher = "Dr. R. K. Mishra", room = "Sci Lab 1"),
        TimetableEntry(id = "tt_th4", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "Social Studies", teacher = "Mrs. Vandana Joshi", room = "Room 204"),
        TimetableEntry(id = "tt_th5", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 5, timeSlot = "12:15 - 01:00 PM", subject = "Moral Science", teacher = "Mrs. Sunita Rao", room = "Room 204"),
        TimetableEntry(id = "tt_th6", className = "Class 8", dayOfWeek = "Thursday", periodNumber = 6, timeSlot = "01:00 - 01:45 PM", subject = "Physical Education", teacher = "Coach Rana", room = "Sports Ground"),

        // Friday
        TimetableEntry(id = "tt_f1", className = "Class 8", dayOfWeek = "Friday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "Science", teacher = "Dr. R. K. Mishra", room = "Sci Lab 1"),
        TimetableEntry(id = "tt_f2", className = "Class 8", dayOfWeek = "Friday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "Mathematics", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_f3", className = "Class 8", dayOfWeek = "Friday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "English Literature", teacher = "Mrs. Sunita Rao", room = "Room 204"),
        TimetableEntry(id = "tt_f4", className = "Class 8", dayOfWeek = "Friday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "Hindi", teacher = "Mr. Devendra Shastri", room = "Room 204"),
        TimetableEntry(id = "tt_f5", className = "Class 8", dayOfWeek = "Friday", periodNumber = 5, timeSlot = "12:15 - 01:00 PM", subject = "Robotics & Coding", teacher = "Ms. Ananya Roy", room = "Robotics Lab"),
        TimetableEntry(id = "tt_f6", className = "Class 8", dayOfWeek = "Friday", periodNumber = 6, timeSlot = "01:00 - 01:45 PM", subject = "Club Activity", teacher = "Staff", room = "Activity Hall"),

        // Saturday
        TimetableEntry(id = "tt_s1", className = "Class 8", dayOfWeek = "Saturday", periodNumber = 1, timeSlot = "08:30 - 09:15 AM", subject = "Mathematics Quiz", teacher = "Mr. Amit Kapoor", room = "Room 204"),
        TimetableEntry(id = "tt_s2", className = "Class 8", dayOfWeek = "Saturday", periodNumber = 2, timeSlot = "09:15 - 10:00 AM", subject = "General Knowledge", teacher = "Mrs. Vandana Joshi", room = "Room 204"),
        TimetableEntry(id = "tt_s3", className = "Class 8", dayOfWeek = "Saturday", periodNumber = 3, timeSlot = "10:15 - 11:00 AM", subject = "Yoga & Mindfulness", teacher = "Guru Shanti", room = "Auditorium"),
        TimetableEntry(id = "tt_s4", className = "Class 8", dayOfWeek = "Saturday", periodNumber = 4, timeSlot = "11:00 - 11:45 AM", subject = "Class Meeting", teacher = "Mrs. Sunita Rao", room = "Room 204")
    )

    val sampleExams = listOf(
        ExamItem(
            id = "ex_01",
            className = "Class 8",
            examName = "Mid-Term Examination 2026",
            subject = "Mathematics",
            date = "2026-09-18",
            timeSlot = "09:00 AM - 12:00 PM",
            maxMarks = 80,
            passingMarks = 27,
            syllabus = "• Ch 1: Rational Numbers\n• Ch 2: Linear Equations in One Variable\n• Ch 3: Understanding Quadrilaterals\n• Ch 4: Practical Geometry\n• Ch 5: Data Handling & Histograms",
            room = "Hall A (Desk 1-30)"
        ),
        ExamItem(
            id = "ex_02",
            className = "Class 8",
            examName = "Mid-Term Examination 2026",
            subject = "Science",
            date = "2026-09-21",
            timeSlot = "09:00 AM - 12:00 PM",
            maxMarks = 80,
            passingMarks = 27,
            syllabus = "• Ch 1: Crop Production & Management\n• Ch 2: Microorganisms: Friend & Foe\n• Ch 3: Synthetic Fibres & Plastics\n• Ch 4: Materials: Metals & Non-Metals\n• Ch 11: Force and Pressure",
            room = "Hall A (Desk 1-30)"
        ),
        ExamItem(
            id = "ex_03",
            className = "Class 8",
            examName = "Mid-Term Examination 2026",
            subject = "English Literature",
            date = "2026-09-23",
            timeSlot = "09:00 AM - 12:00 PM",
            maxMarks = 80,
            passingMarks = 27,
            syllabus = "• Section A: Reading Comprehension (Unseen Passages)\n• Section B: Letter Writing, Diary Entry, Story Writing\n• Section C (Grammar): Active/Passive Voice, Tenses, Modals\n• Section D: Honeydew Ch 1-4, It So Happened Ch 1-3",
            room = "Hall B"
        ),
        ExamItem(
            id = "ex_04",
            className = "Class 8",
            examName = "Mid-Term Examination 2026",
            subject = "Social Studies",
            date = "2026-09-25",
            timeSlot = "09:00 AM - 12:00 PM",
            maxMarks = 80,
            passingMarks = 27,
            syllabus = "• History: How, When and Where; From Trade to Territory\n• Civics: The Indian Constitution; Understanding Secularism\n• Geography: Resources; Land, Soil, Water, Natural Vegetation",
            room = "Hall B"
        ),
        ExamItem(
            id = "ex_05",
            className = "Class 8",
            examName = "Mid-Term Examination 2026",
            subject = "Computer Science",
            date = "2026-09-28",
            timeSlot = "09:00 AM - 11:00 AM",
            maxMarks = 50,
            passingMarks = 18,
            syllabus = "• Unit 1: Networking Concepts & Topologies\n• Unit 2: HTML5 Forms and Tables\n• Unit 3: Python Fundamentals (Variables, Conditionals, Loops)",
            room = "Computer Lab"
        )
    )

    val sampleResults = listOf(
        ResultItem(
            id = "res_01",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "Mathematics",
            marksObtained = 48.0,
            maxMarks = 50.0,
            grade = "A+",
            remarks = "Exceptional analytical and problem-solving skills."
        ),
        ResultItem(
            id = "res_02",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "Science",
            marksObtained = 46.5,
            maxMarks = 50.0,
            grade = "A+",
            remarks = "Excellent understanding of scientific concepts."
        ),
        ResultItem(
            id = "res_03",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "English Literature",
            marksObtained = 44.0,
            maxMarks = 50.0,
            grade = "A",
            remarks = "Very expressive writing and rich vocabulary."
        ),
        ResultItem(
            id = "res_04",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "Social Studies",
            marksObtained = 45.0,
            maxMarks = 50.0,
            grade = "A",
            remarks = "Accurate map work and historical insights."
        ),
        ResultItem(
            id = "res_05",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "Computer Science",
            marksObtained = 49.0,
            maxMarks = 50.0,
            grade = "A+",
            remarks = "Flawless code logic in Python exercises."
        ),
        ResultItem(
            id = "res_06",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            examName = "Periodic Assessment 1 (July 2026)",
            subject = "Hindi",
            marksObtained = 42.0,
            maxMarks = 50.0,
            grade = "B+",
            remarks = "Good grammar, practice handwriting."
        )
    )

    val sampleFees = listOf(
        FeeRecord(
            id = "fee_01",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            term = "Quarter 2 (Jul - Sep 2026)",
            tuitionFee = 18000.0,
            busFee = 4500.0,
            libraryFee = 800.0,
            labFee = 1200.0,
            totalFee = 24500.0,
            paidAmount = 24500.0,
            status = "Paid",
            dueDate = "2026-07-15",
            receiptNo = "DSB-2026-Q2-0812"
        ),
        FeeRecord(
            id = "fee_02",
            studentId = "std_01",
            studentName = "Aarav Sharma",
            className = "Class 8",
            term = "Quarter 3 (Oct - Dec 2026)",
            tuitionFee = 18000.0,
            busFee = 4500.0,
            libraryFee = 800.0,
            labFee = 1200.0,
            totalFee = 24500.0,
            paidAmount = 0.0,
            status = "Pending",
            dueDate = "2026-10-10",
            receiptNo = "DSB-2026-Q3-PENDING"
        ),
        FeeRecord(
            id = "fee_03",
            studentId = "std_02",
            studentName = "Ananya Sharma",
            className = "Class 5",
            term = "Quarter 2 (Jul - Sep 2026)",
            tuitionFee = 15000.0,
            busFee = 4500.0,
            libraryFee = 600.0,
            labFee = 800.0,
            totalFee = 20900.0,
            paidAmount = 16400.0,
            status = "Partially Paid",
            dueDate = "2026-07-15",
            receiptNo = "DSB-2026-Q2-0544"
        )
    )

    val sampleAttendance = listOf(
        AttendanceRecord(id = "att_01", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-09-01", status = "PRESENT"),
        AttendanceRecord(id = "att_02", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-31", status = "PRESENT"),
        AttendanceRecord(id = "att_03", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-29", status = "PRESENT"),
        AttendanceRecord(id = "att_04", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-28", status = "PRESENT"),
        AttendanceRecord(id = "att_05", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-27", status = "PRESENT"),
        AttendanceRecord(id = "att_06", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-26", status = "ABSENT", remarks = "Medical Leave"),
        AttendanceRecord(id = "att_07", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-25", status = "PRESENT"),
        AttendanceRecord(id = "att_08", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-24", status = "PRESENT"),
        AttendanceRecord(id = "att_09", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-22", status = "PRESENT"),
        AttendanceRecord(id = "att_10", studentId = "std_01", studentName = "Aarav Sharma", className = "Class 8", date = "2026-08-21", status = "PRESENT")
    )

    val sampleStudyMaterials = listOf(
        StudyMaterial(
            id = "sm_01",
            className = "Class 8",
            subject = "Science",
            title = "NCERT Exemplar Solved Problems - Physics & Chemistry",
            topic = "Light & Chemical Effects of Electric Current",
            type = "PDF",
            fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            sizeString = "3.8 MB"
        ),
        StudyMaterial(
            id = "sm_02",
            className = "Class 8",
            subject = "Mathematics",
            title = "Class 8 Math Olympiad Practice Workbook 2026",
            topic = "Geometry & Mensuration Mastery",
            type = "WORKSHEET",
            fileUrl = "https://drive.google.com/file/d/sample-math-olympiad/view",
            sizeString = "5.2 MB"
        ),
        StudyMaterial(
            id = "sm_03",
            className = "Class 8",
            subject = "Social Studies",
            title = "CBSE Past 5 Years Solved Sample Papers",
            topic = "Indian Freedom Movement & Map Marking",
            type = "QUESTION_PAPER",
            fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            sizeString = "4.1 MB"
        ),
        StudyMaterial(
            id = "sm_04",
            className = "Class 8",
            subject = "English Literature",
            title = "Grammar Handbook: Direct & Indirect Speech",
            topic = "Grammar & Reported Speech Rules",
            type = "REFERENCE",
            fileUrl = "https://drive.google.com/file/d/sample-english-handbook/view",
            sizeString = "1.9 MB"
        )
    )

    val sampleVideoLectures = listOf(
        VideoLecture(
            id = "vid_01",
            className = "Class 8",
            subject = "Mathematics",
            title = "Understanding Linear Equations in One Variable (Full Concept)",
            chapter = "Chapter 2",
            duration = "28 mins",
            videoUrl = "https://www.youtube.com/watch?v=sample1",
            thumbnailUrl = "https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=400",
            teacherName = "Mr. Amit Kapoor"
        ),
        VideoLecture(
            id = "vid_02",
            className = "Class 8",
            subject = "Science",
            title = "Atmospheric Pressure & Barometer Experiment",
            chapter = "Chapter 11 - Physics",
            duration = "34 mins",
            videoUrl = "https://www.youtube.com/watch?v=sample2",
            thumbnailUrl = "https://images.unsplash.com/photo-1532094349884-543bc11b234d?w=400",
            teacherName = "Dr. R. K. Mishra"
        ),
        VideoLecture(
            id = "vid_03",
            className = "Class 8",
            subject = "English Literature",
            title = "Poem Analysis: The Ant and the Cricket",
            chapter = "Honeydew Poem 1",
            duration = "19 mins",
            videoUrl = "https://www.youtube.com/watch?v=sample3",
            thumbnailUrl = "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?w=400",
            teacherName = "Mrs. Sunita Rao"
        ),
        VideoLecture(
            id = "vid_04",
            className = "Class 8",
            subject = "Computer Science",
            title = "Python Coding Workshop: Lists & Loops with Turtle Graphics",
            chapter = "Module 3",
            duration = "42 mins",
            videoUrl = "https://www.youtube.com/watch?v=sample4",
            thumbnailUrl = "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?w=400",
            teacherName = "Ms. Ananya Roy"
        )
    )

    val sampleNotifications = listOf(
        PushNotificationLog(
            id = "notif_01",
            title = "📚 New Mathematics Homework Assigned",
            message = "Linear Equations Ex 4.2 has been posted by Mr. Amit Kapoor. Due on Sep 3, 2026.",
            timestamp = "10 mins ago",
            targetClass = "Class 8",
            isRead = false,
            type = "HOMEWORK"
        ),
        PushNotificationLog(
            id = "notif_02",
            title = "📢 Mid-Term Exam Datesheet Released",
            message = "Mid-Term Examination datesheet and syllabus are now live in the Exam section.",
            timestamp = "2 hours ago",
            targetClass = "All Classes",
            isRead = false,
            type = "EXAM"
        ),
        PushNotificationLog(
            id = "notif_03",
            title = "🏆 Science Exhibition Registration Open",
            message = "Submit your project models before September 10th to participate in the State Science Fair.",
            timestamp = "Yesterday",
            targetClass = "All Classes",
            isRead = true,
            type = "NOTICE"
        ),
        PushNotificationLog(
            id = "notif_04",
            title = "✅ Today's Attendance Marked",
            message = "Aarav Sharma marked PRESENT for Tuesday, Sep 1, 2026.",
            timestamp = "08:45 AM",
            targetClass = "Class 8",
            isRead = true,
            type = "ATTENDANCE"
        )
    )
}
