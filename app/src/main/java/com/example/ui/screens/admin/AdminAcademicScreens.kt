package com.example.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClassItem
import com.example.data.model.ExamItem
import com.example.data.model.NoteItem
import com.example.data.model.ResultItem
import com.example.data.model.Student
import com.example.data.model.Subject
import com.example.data.model.TimetableEntry
import com.example.data.model.VideoLecture
import com.example.ui.components.AttachmentLinkButton
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorInfo
import com.example.ui.theme.ColorPurple
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.ColorTeal
import com.example.ui.theme.SunshinePrimary
import com.example.ui.theme.SunshineSecondary
import com.example.ui.viewmodel.SchoolViewModel

// ----------------------------------------------------------------------
// 1. Manage Results & Marks Screen
// ----------------------------------------------------------------------
@Composable
fun ManageResultsScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }

    var selectedClass by remember { mutableStateOf("Class 8") }
    val classStudents = allStudents.filter { it.className == selectedClass }
    var selectedStudentId by remember(selectedClass) {
        mutableStateOf(classStudents.firstOrNull()?.id ?: "")
    }

    var selectedSubject by remember { mutableStateOf("Mathematics") }
    var examName by remember { mutableStateOf("Term 1 Examination") }
    var marksObtainedStr by remember { mutableStateOf("") }
    var maxMarksStr by remember { mutableStateOf("100") }
    var remarks by remember { mutableStateOf("Excellent concept clarity") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆 Enter Marks & Results",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showAddForm = !showAddForm },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(if (showAddForm) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showAddForm) "Cancel" else "Enter Marks")
                }
            }
        }

        if (showAddForm) {
            item {
                SunshineCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Record Student Assessment Score", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        Text("Select Class:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(allClasses) { cls ->
                                FilterChip(
                                    selected = selectedClass == cls.name,
                                    onClick = {
                                        selectedClass = cls.name
                                        selectedStudentId = allStudents.firstOrNull { it.className == cls.name }?.id ?: ""
                                    },
                                    label = { Text(cls.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Text("Select Student:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(classStudents) { std ->
                                FilterChip(
                                    selected = selectedStudentId == std.id,
                                    onClick = { selectedStudentId = std.id },
                                    label = { Text("${std.rollNo}. ${std.name}") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Text("Select Subject:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(allSubjects) { sub ->
                                FilterChip(
                                    selected = selectedSubject == sub.name,
                                    onClick = { selectedSubject = sub.name },
                                    label = { Text(sub.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        OutlinedTextField(
                            value = examName,
                            onValueChange = { examName = it },
                            label = { Text("Exam Name *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = marksObtainedStr,
                                onValueChange = { marksObtainedStr = it },
                                label = { Text("Marks Obtained *") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = maxMarksStr,
                                onValueChange = { maxMarksStr = it },
                                label = { Text("Max Marks *") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        OutlinedTextField(
                            value = remarks,
                            onValueChange = { remarks = it },
                            label = { Text("Teacher Remarks / Feedback") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                val std = classStudents.find { it.id == selectedStudentId }
                                val marks = marksObtainedStr.toDoubleOrNull() ?: 0.0
                                val max = maxMarksStr.toDoubleOrNull() ?: 100.0
                                if (std != null && marksObtainedStr.isNotBlank()) {
                                    viewModel.addResult(
                                        studentId = std.id,
                                        studentName = std.name,
                                        className = selectedClass,
                                        examName = examName,
                                        subject = selectedSubject,
                                        marksObtained = marks,
                                        maxMarks = max,
                                        remarks = remarks
                                    )
                                    marksObtainedStr = ""
                                    showAddForm = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Save Marksheet Entry", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Class $selectedClass Student Marksheet Logs",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(classStudents, key = { it.id }) { std ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SunshinePrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${std.rollNo}", fontWeight = FontWeight.Bold, color = SunshinePrimary)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = std.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "ID: ${std.studentCode} • Roll #${std.rollNo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ColorSuccess.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "A+ (94%)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = ColorSuccess,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 2. Manage Exams & Syllabus Screen
// ----------------------------------------------------------------------
@Composable
fun ManageExamsScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allExams by viewModel.allExams.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }

    var selectedClass by remember { mutableStateOf("Class 8") }
    var selectedSubject by remember { mutableStateOf("Science") }
    var examName by remember { mutableStateOf("Mid-Term Examination 2026") }
    var date by remember { mutableStateOf("2026-09-20") }
    var timeSlot by remember { mutableStateOf("09:00 AM - 12:00 PM") }
    var maxMarks by remember { mutableStateOf("80") }
    var passingMarks by remember { mutableStateOf("28") }
    var room by remember { mutableStateOf("Hall A - Desk 1-40") }
    var syllabus by remember { mutableStateOf("Chapter 1: Crop Production\nChapter 2: Microorganisms\nChapter 3: Synthetic Fibres\nChapter 4: Materials & Metals") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📅 Schedule Exams & Syllabus",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showAddForm = !showAddForm },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(if (showAddForm) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showAddForm) "Cancel" else "Add Exam")
                }
            }
        }

        if (showAddForm) {
            item {
                SunshineCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Create Exam Schedule", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        Text("Select Class:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(allClasses) { cls ->
                                FilterChip(
                                    selected = selectedClass == cls.name,
                                    onClick = { selectedClass = cls.name },
                                    label = { Text(cls.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Text("Select Subject:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(allSubjects) { sub ->
                                FilterChip(
                                    selected = selectedSubject == sub.name,
                                    onClick = { selectedSubject = sub.name },
                                    label = { Text(sub.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        OutlinedTextField(
                            value = examName,
                            onValueChange = { examName = it },
                            label = { Text("Exam Name *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = date,
                                onValueChange = { date = it },
                                label = { Text("Date (YYYY-MM-DD) *") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = timeSlot,
                                onValueChange = { timeSlot = it },
                                label = { Text("Time Slot *") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = maxMarks,
                                onValueChange = { maxMarks = it },
                                label = { Text("Max Marks") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = passingMarks,
                                onValueChange = { passingMarks = it },
                                label = { Text("Passing Marks") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        OutlinedTextField(
                            value = room,
                            onValueChange = { room = it },
                            label = { Text("Exam Hall / Room") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = syllabus,
                            onValueChange = { syllabus = it },
                            label = { Text("Complete Syllabus Topics *") },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            maxLines = 4
                        )

                        Button(
                            onClick = {
                                if (examName.isNotBlank() && date.isNotBlank()) {
                                    viewModel.addExam(
                                        className = selectedClass,
                                        examName = examName,
                                        subject = selectedSubject,
                                        date = date,
                                        timeSlot = timeSlot,
                                        maxMarks = maxMarks.toIntOrNull() ?: 100,
                                        passingMarks = passingMarks.toIntOrNull() ?: 33,
                                        syllabus = syllabus,
                                        room = room
                                    )
                                    showAddForm = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Save Exam Schedule & Dispatch Alerts", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Scheduled Exams (${allExams.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(allExams, key = { it.id }) { exam ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SunshinePrimary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${exam.className} • ${exam.subject}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SunshinePrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = exam.examName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Date: ${exam.date} • ${exam.timeSlot} • ${exam.room}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Max: ${exam.maxMarks} • Passing: ${exam.passingMarks}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { viewModel.deleteExam(exam) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 3. Manage Timetable Screen
// ----------------------------------------------------------------------
@Composable
fun ManageTimetableScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()

    var selectedClass by remember { mutableStateOf("Class 8") }
    var selectedDay by remember { mutableStateOf("Monday") }

    var periodNumber by remember { mutableIntStateOf(1) }
    var timeSlot by remember { mutableStateOf("08:30 AM - 09:15 AM") }
    var subject by remember { mutableStateOf("Mathematics") }
    var teacher by remember { mutableStateOf("Mr. Sharma") }
    var room by remember { mutableStateOf("Room 204") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🗓️ Manage Class Timetable Matrix",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Add Timetable Period Slot", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Text("Class:", style = MaterialTheme.typography.labelMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(allClasses) { cls ->
                            FilterChip(
                                selected = selectedClass == cls.name,
                                onClick = { selectedClass = cls.name },
                                label = { Text(cls.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text("Day of Week:", style = MaterialTheme.typography.labelMedium)
                    val daysList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(daysList) { d ->
                            FilterChip(
                                selected = selectedDay == d,
                                onClick = { selectedDay = d },
                                label = { Text(d.take(3)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = "$periodNumber",
                            onValueChange = { periodNumber = it.toIntOrNull() ?: 1 },
                            label = { Text("Period #") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = timeSlot,
                            onValueChange = { timeSlot = it },
                            label = { Text("Time Slot") },
                            modifier = Modifier.weight(2f)
                        )
                    }

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject *") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = teacher,
                            onValueChange = { teacher = it },
                            label = { Text("Teacher Name") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = room,
                            onValueChange = { room = it },
                            label = { Text("Room / Lab") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addTimetableEntry(
                                className = selectedClass,
                                dayOfWeek = selectedDay,
                                periodNumber = periodNumber,
                                timeSlot = timeSlot,
                                subject = subject,
                                teacher = teacher,
                                room = room
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add / Update Timetable Slot", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 4. Manage Students Screen
// ----------------------------------------------------------------------
@Composable
fun ManageStudentsScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }

    var studentName by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("Class 8") }
    var section by remember { mutableStateOf("A") }
    var rollNoStr by remember { mutableStateOf("${allStudents.size + 1}") }
    var parentName by remember { mutableStateOf("") }
    var parentPhone by remember { mutableStateOf("+91 98765 43210") }
    var parentEmail by remember { mutableStateOf("parent@example.com") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "👥 Student Directory",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showAddForm = !showAddForm },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(if (showAddForm) Icons.Default.Close else Icons.Default.PersonAdd, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showAddForm) "Cancel" else "Add Student")
                }
            }
        }

        if (showAddForm) {
            item {
                SunshineCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Register New Student", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = studentName,
                            onValueChange = { studentName = it },
                            label = { Text("Full Student Name *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("Select Class:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(allClasses) { cls ->
                                FilterChip(
                                    selected = selectedClass == cls.name,
                                    onClick = { selectedClass = cls.name },
                                    label = { Text(cls.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = section,
                                onValueChange = { section = it },
                                label = { Text("Section") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = rollNoStr,
                                onValueChange = { rollNoStr = it },
                                label = { Text("Roll Number") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        OutlinedTextField(
                            value = parentName,
                            onValueChange = { parentName = it },
                            label = { Text("Parent / Guardian Name *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = parentPhone,
                            onValueChange = { parentPhone = it },
                            label = { Text("Parent Phone Number *") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (studentName.isNotBlank()) {
                                    viewModel.addStudent(
                                        name = studentName,
                                        className = selectedClass,
                                        section = section,
                                        rollNo = rollNoStr.toIntOrNull() ?: 1,
                                        parentName = parentName.ifBlank { "Parent" },
                                        parentPhone = parentPhone,
                                        parentEmail = parentEmail
                                    )
                                    studentName = ""
                                    showAddForm = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Generate Student ID & Save", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Registered Students (${allStudents.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(allStudents, key = { it.id }) { std ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(SunshinePrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = std.name.take(1), fontWeight = FontWeight.Bold, color = SunshinePrimary, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = std.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "${std.className} - ${std.section} • Roll #${std.rollNo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "Code: ${std.studentCode} • Parent: ${std.parentPhone}", style = MaterialTheme.typography.labelSmall, color = SunshinePrimary)
                        }
                    }

                    IconButton(onClick = { viewModel.deleteStudent(std) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 5. Manage Classes & Subjects Screen
// ----------------------------------------------------------------------
@Composable
fun ManageClassesSubjectsScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()

    var className by remember { mutableStateOf("") }
    var classSection by remember { mutableStateOf("A") }
    var classTeacher by remember { mutableStateOf("") }

    var subjectName by remember { mutableStateOf("") }
    var subjectCode by remember { mutableStateOf("") }
    var subjectTeacher by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🏛️ Academic Classes & Subjects",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Add Class Card
        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Add Class Section", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = className,
                            onValueChange = { className = it },
                            label = { Text("Class (e.g. Class 11)") },
                            modifier = Modifier.weight(2f)
                        )
                        OutlinedTextField(
                            value = classSection,
                            onValueChange = { classSection = it },
                            label = { Text("Sec") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = classTeacher,
                        onValueChange = { classTeacher = it },
                        label = { Text("Class Teacher") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            if (className.isNotBlank()) {
                                viewModel.addClass(className, classSection, classTeacher, "Room 201")
                                className = ""
                                classTeacher = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Class Section")
                    }
                }
            }
        }

        // Add Subject Card
        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Add Subject", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = subjectName,
                            onValueChange = { subjectName = it },
                            label = { Text("Subject Name") },
                            modifier = Modifier.weight(2f)
                        )
                        OutlinedTextField(
                            value = subjectCode,
                            onValueChange = { subjectCode = it },
                            label = { Text("Code") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = subjectTeacher,
                        onValueChange = { subjectTeacher = it },
                        label = { Text("Subject Teacher") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            if (subjectName.isNotBlank()) {
                                viewModel.addSubject(subjectName, subjectCode.ifBlank { subjectName.take(3).uppercase() }, subjectTeacher, "#FF9F45")
                                subjectName = ""
                                subjectCode = ""
                                subjectTeacher = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineSecondary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Subject", color = Color.Black)
                    }
                }
            }
        }

        item {
            Text("Active Classes (${allClasses.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        items(allClasses, key = { it.id }) { cls ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "${cls.name} - ${cls.section}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Teacher: ${cls.classTeacher} • ${cls.roomNumber}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { viewModel.deleteClass(cls) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                    }
                }
            }
        }

        item {
            Text("Active Subjects (${allSubjects.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        items(allSubjects, key = { it.id }) { sub ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = sub.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Code: ${sub.code} • Teacher: ${sub.teacherName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { viewModel.deleteSubject(sub) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 6. Manage Notes & Study Materials Screen
// ----------------------------------------------------------------------
@Composable
fun ManageNotesMaterialsScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()
    val allNotes by viewModel.allNotes.collectAsState()
    val allVideos by viewModel.allVideoLectures.collectAsState()

    var selectedClass by remember { mutableStateOf("Class 8") }
    var selectedSubject by remember { mutableStateOf("Science") }
    var title by remember { mutableStateOf("") }
    var chapter by remember { mutableStateOf("Chapter 1") }
    var fileUrl by remember { mutableStateOf("") }
    var fileType by remember { mutableStateOf("PDF") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📚 Upload Notes & Material Links",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Add Digital Note / Textbook Link", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Text("Class:", style = MaterialTheme.typography.labelMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(allClasses) { cls ->
                            FilterChip(
                                selected = selectedClass == cls.name,
                                onClick = { selectedClass = cls.name },
                                label = { Text(cls.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text("Subject:", style = MaterialTheme.typography.labelMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(allSubjects) { sub ->
                            FilterChip(
                                selected = selectedSubject == sub.name,
                                onClick = { selectedSubject = sub.name },
                                label = { Text(sub.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title *") },
                        placeholder = { Text("e.g. Chapter 1 Notes & Formulas") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = chapter,
                        onValueChange = { chapter = it },
                        label = { Text("Chapter / Unit") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = fileUrl,
                        onValueChange = { fileUrl = it },
                        label = { Text("External PDF / Document Link *") },
                        placeholder = { Text("https://ncert.nic.in/... or https://drive.google.com/...") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (title.isNotBlank() && fileUrl.isNotBlank()) {
                                viewModel.addNote(
                                    className = selectedClass,
                                    subject = selectedSubject,
                                    title = title,
                                    chapter = chapter,
                                    description = "Study material for $selectedClass $selectedSubject",
                                    fileUrl = fileUrl,
                                    fileType = fileType
                                )
                                title = ""
                                fileUrl = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add to Digital School Bag", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text("Uploaded Digital Notes (${allNotes.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        items(allNotes, key = { it.id }) { note ->
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = note.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "${note.className} • ${note.subject} • ${note.chapter}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        AttachmentLinkButton(
                            url = note.fileUrl,
                            label = "Open Document",
                            onOpenLink = { viewModel.openExternalLink(note.fileUrl) }
                        )
                    }
                    IconButton(onClick = { viewModel.deleteNote(note) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
