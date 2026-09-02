package com.example.ui.screens.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Grading
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AttendanceRecord
import com.example.data.model.ExamItem
import com.example.data.model.FeeRecord
import com.example.data.model.NoteItem
import com.example.data.model.PushNotificationLog
import com.example.data.model.ResultItem
import com.example.data.model.StudyMaterial
import com.example.data.model.VideoLecture
import com.example.ui.components.AttachmentLinkButton
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorInfo
import com.example.ui.theme.ColorPurple
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.ColorTeal
import com.example.ui.theme.ColorWarning
import com.example.ui.theme.SunshinePrimary
import com.example.ui.theme.SunshineSecondary
import com.example.ui.viewmodel.SchoolViewModel

// ----------------------------------------------------
// 1. More Menu Grid Screen
// ----------------------------------------------------
data class UserMoreMenuItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun UserMoreMenuScreen(
    onNavigateSubscreen: (String) -> Unit
) {
    val menuItems = listOf(
        UserMoreMenuItem("notes", "Notes & E-Books", "Read chapter summaries & books", Icons.Default.MenuBook, SunshinePrimary),
        UserMoreMenuItem("exams", "Exams & Syllabus", "View mid-term dates & syllabus", Icons.Default.CalendarMonth, ColorInfo),
        UserMoreMenuItem("results", "Report Card", "Subject marks & GPA breakdown", Icons.Default.Grading, ColorPurple),
        UserMoreMenuItem("attendance", "Attendance Log", "Monthly present/absent stats", Icons.Default.CheckCircle, ColorSuccess),
        UserMoreMenuItem("fees", "Fee Status", "Tuition receipts & dues breakdown", Icons.Default.Payments, SunshineSecondary),
        UserMoreMenuItem("videos", "Video Lectures", "Recorded classroom classes", Icons.Default.VideoLibrary, ColorError),
        UserMoreMenuItem("study_materials", "Study Materials", "NCERT exemplars & past papers", Icons.Default.Download, ColorTeal),
        UserMoreMenuItem("notifications", "Alert Center", "Push notifications history", Icons.Default.Notifications, SunshinePrimary)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🎒 All Digital Bag Sections",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(14.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(menuItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateSubscreen(item.id) }
                        .testTag("more_tile_${item.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(item.color.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                tint = item.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// 2. Notes & E-Books Screen
// ----------------------------------------------------
@Composable
fun NotesAndBooksScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allNotes by viewModel.allNotes.collectAsState()
    val subjects by viewModel.allSubjects.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"

    var selectedSubject by remember { mutableStateOf("All") }

    val classNotes = allNotes.filter { it.className == studentClass }
    val filteredNotes = if (selectedSubject == "All") classNotes else classNotes.filter { it.subject == selectedSubject }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedSubject == "All",
                    onClick = { selectedSubject = "All" },
                    label = { Text("All Subjects") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunshinePrimary.copy(alpha = 0.2f),
                        selectedLabelColor = SunshinePrimary
                    )
                )
            }
            items(subjects) { sub ->
                FilterChip(
                    selected = selectedSubject == sub.name,
                    onClick = { selectedSubject = sub.name },
                    label = { Text(sub.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunshinePrimary.copy(alpha = 0.2f),
                        selectedLabelColor = SunshinePrimary
                    )
                )
            }
        }

        if (filteredNotes.isEmpty()) {
            EmptyStateCard(
                title = "No Digital Notes Found",
                message = "No textbook PDFs or notes uploaded for this subject yet.",
                icon = Icons.Default.MenuBook
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredNotes, key = { it.id }) { note ->
                    SunshineCard {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SunshinePrimary.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = note.subject,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SunshinePrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = note.fileType.uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${note.chapter} • Added: ${note.dateAdded}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            AttachmentLinkButton(
                                url = note.fileUrl,
                                label = "Open ${note.fileType} Document",
                                onOpenLink = { viewModel.openExternalLink(note.fileUrl) }
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

// ----------------------------------------------------
// 3. Exams & Syllabus Screen
// ----------------------------------------------------
@Composable
fun ExamsSyllabusScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allExams by viewModel.allExams.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"

    val classExams = allExams.filter { it.className == studentClass }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "📅 Upcoming Exams & Syllabus ($studentClass)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (classExams.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Exams Scheduled",
                    message = "There are no exams scheduled for $studentClass right now.",
                    icon = Icons.Default.CalendarMonth
                )
            }
        } else {
            items(classExams, key = { it.id }) { exam ->
                var expanded by remember { mutableStateOf(false) }

                SunshineCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SunshinePrimary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = exam.subject,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SunshinePrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = exam.date,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = ColorError
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = exam.examName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Time: ${exam.timeSlot} • Room: ${exam.room}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Max Marks: ${exam.maxMarks} • Passing: ${exam.passingMarks}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { expanded = !expanded }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Exam Syllabus",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = SunshinePrimary
                            )
                        }

                        AnimatedVisibility(visible = expanded) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = exam.syllabus,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------
// 4. Report Card / Marksheet Screen
// ----------------------------------------------------
@Composable
fun ResultMarksheetScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val studentResults by viewModel.studentResults.collectAsState()

    val totalMarksObtained = studentResults.sumOf { it.marksObtained }
    val totalMaxMarks = studentResults.sumOf { it.maxMarks }
    val overallPercentage = if (totalMaxMarks > 0) (totalMarksObtained / totalMaxMarks) * 100.0 else 92.4

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary Performance Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(ColorPurple, SunshinePrimary)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Term 1 Report Card",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${activeStudent?.name} • ${activeStudent?.className}-${activeStudent?.section}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Grade: A+ (Outstanding)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SunshineSecondary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = String.format("%.1f%%", overallPercentage),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${totalMarksObtained.toInt()} / ${totalMaxMarks.toInt()} Marks",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Subject-wise Marks Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (studentResults.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Marksheets Found",
                    message = "Marks for the recent assessments have not been recorded yet.",
                    icon = Icons.Default.Grading
                )
            }
        } else {
            items(studentResults, key = { it.id }) { res ->
                SunshineCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = res.subject,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = res.examName,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (res.remarks.isNotBlank()) {
                                Text(
                                    text = "Remarks: ${res.remarks}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SunshinePrimary
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${res.marksObtained.toInt()} / ${res.maxMarks.toInt()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = String.format("%.0f%%", (res.marksObtained / res.maxMarks) * 100),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ColorSuccess.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = res.grade,
                                    fontWeight = FontWeight.Bold,
                                    color = ColorSuccess,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------
// 5. Attendance Screen
// ----------------------------------------------------
@Composable
fun AttendanceScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val attendanceRecords by viewModel.studentAttendance.collectAsState()

    val totalDays = activeStudent?.totalAttendanceDays ?: 120
    val presentDays = activeStudent?.presentDays ?: 114
    val absentDays = totalDays - presentDays
    val attendancePct = (presentDays * 100) / totalDays

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Attendance Overview Card
        item {
            SunshineCard {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Overall Attendance",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Academic Year 2026-27",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "$attendancePct%",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = ColorSuccess
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AttendanceStatItem(label = "Total Days", value = "$totalDays", color = MaterialTheme.colorScheme.onSurface)
                        AttendanceStatItem(label = "Present", value = "$presentDays", color = ColorSuccess)
                        AttendanceStatItem(label = "Absent", value = "$absentDays", color = ColorError)
                        AttendanceStatItem(label = "Status", value = "Good", color = SunshinePrimary)
                    }
                }
            }
        }

        item {
            Text(
                text = "Recent Daily Log",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (attendanceRecords.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Attendance Logs",
                    message = "Daily attendance records for this month will appear here.",
                    icon = Icons.Default.CheckCircle
                )
            }
        } else {
            items(attendanceRecords, key = { it.id }) { record ->
                SunshineCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = record.date,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (record.remarks.isNotBlank()) {
                                Text(
                                    text = record.remarks,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (record.status == "PRESENT") ColorSuccess.copy(alpha = 0.15f) else ColorError.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = record.status,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (record.status == "PRESENT") ColorSuccess else ColorError,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun AttendanceStatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ----------------------------------------------------
// 6. Fees Status Screen (Read-Only)
// ----------------------------------------------------
@Composable
fun FeesScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val feeRecords by viewModel.studentFees.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SunshineCard {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Fee Account Status",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${activeStudent?.name} (${activeStudent?.className})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (activeStudent?.feeStatus == "Paid") ColorSuccess.copy(alpha = 0.15f) else ColorError.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = activeStudent?.feeStatus ?: "Paid",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (activeStudent?.feeStatus == "Paid") ColorSuccess else ColorError,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Term-wise Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (feeRecords.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Fee Invoices Found",
                    message = "Fee structures and receipts will appear here.",
                    icon = Icons.Default.Payments
                )
            }
        } else {
            items(feeRecords, key = { it.id }) { fee ->
                SunshineCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fee.term,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Due: ${fee.dueDate}",
                                style = MaterialTheme.typography.bodySmall,
                                color = ColorError,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        FeeRow(label = "Tuition Fee", amount = "₹${fee.tuitionFee.toInt()}")
                        FeeRow(label = "Transport / Bus Fee", amount = "₹${fee.busFee.toInt()}")
                        FeeRow(label = "Library & Lab Fee", amount = "₹${(fee.libraryFee + fee.labFee).toInt()}")
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        FeeRow(label = "Total Payable", amount = "₹${fee.totalFee.toInt()}", isBold = true)
                        FeeRow(label = "Amount Paid", amount = "₹${fee.paidAmount.toInt()}", color = ColorSuccess)
                        val balance = fee.totalFee - fee.paidAmount
                        FeeRow(label = "Balance Due", amount = "₹${balance.toInt()}", color = if (balance > 0) ColorError else ColorSuccess, isBold = true)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Receipt No: ${fee.receiptNo}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun FeeRow(label: String, amount: String, isBold: Boolean = false, color: Color = Color.Unspecified) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = if (color != Color.Unspecified) color else MaterialTheme.colorScheme.onSurface
        )
    }
}

// ----------------------------------------------------
// 7. Video Lectures Screen
// ----------------------------------------------------
@Composable
fun VideoLecturesScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allVideos by viewModel.allVideoLectures.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"

    val classVideos = allVideos.filter { it.className == studentClass }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📺 Video Lectures ($studentClass)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (classVideos.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Video Lectures",
                    message = "Video classes for $studentClass will be posted here.",
                    icon = Icons.Default.VideoLibrary
                )
            }
        } else {
            items(classVideos, key = { it.id }) { video ->
                SunshineCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SunshinePrimary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = video.subject,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SunshinePrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = "Duration: ${video.duration}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = video.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${video.chapter} • Teacher: ${video.teacherName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.openExternalLink(video.videoUrl) },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Watch Video Lecture")
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------
// 8. Study Materials Screen
// ----------------------------------------------------
@Composable
fun StudyMaterialScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allMaterials by viewModel.allStudyMaterials.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"

    val classMaterials = allMaterials.filter { it.className == studentClass }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📚 Study Materials & Question Papers",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (classMaterials.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Materials Found",
                    message = "Study resources and previous question papers will be posted here.",
                    icon = Icons.Default.Download
                )
            }
        } else {
            items(classMaterials, key = { it.id }) { item ->
                SunshineCard {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = ColorTeal.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = item.subject,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = ColorTeal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = item.type.replace("_", " "),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Topic: ${item.topic} • Size: ${item.sizeString}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AttachmentLinkButton(
                            url = item.fileUrl,
                            label = "Download / Open Document",
                            onOpenLink = { viewModel.openExternalLink(item.fileUrl) }
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------
// 9. Alert Center / Push Notifications Screen
// ----------------------------------------------------
@Composable
fun NotificationsScreen(viewModel: SchoolViewModel) {
    val notifications by viewModel.allNotifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔔 Notifications Alert Center",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(onClick = { viewModel.markAllNotificationsRead() }) {
                Text("Mark All Read", color = SunshinePrimary)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (notifications.isEmpty()) {
            EmptyStateCard(
                title = "No Notifications",
                message = "You are all caught up! New homework, notices, and exam alerts will appear here.",
                icon = Icons.Default.Notifications
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications, key = { it.id }) { notif ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.markNotificationRead(notif.id) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (notif.isRead) MaterialTheme.colorScheme.surface else SunshinePrimary.copy(alpha = 0.08f)
                        ),
                        border = if (!notif.isRead) CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(SunshinePrimary, SunshineSecondary))) else null
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = notif.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = notif.timestamp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = notif.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}
