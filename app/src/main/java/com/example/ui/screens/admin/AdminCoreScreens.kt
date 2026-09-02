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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Homework
import com.example.data.model.Notice
import com.example.data.model.Student
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.ColorWarning
import com.example.ui.theme.SunshinePrimary
import com.example.ui.theme.SunshineSecondary
import com.example.ui.viewmodel.SchoolViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ----------------------------------------------------------------------
// 1. Manage Homework Screen (Upload with External Link & List)
// ----------------------------------------------------------------------
@Composable
fun ManageHomeworkScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allSubjects by viewModel.allSubjects.collectAsState()
    val allHomework by viewModel.allHomework.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }

    // Form inputs
    var selectedClass by remember { mutableStateOf("Class 8") }
    var selectedSubject by remember { mutableStateOf("Mathematics") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("2026-09-05") }
    var attachmentUrl by remember { mutableStateOf("") }

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
                    text = "📝 Manage Daily Homework",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { showAddForm = !showAddForm },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("admin_add_homework_button")
                ) {
                    Icon(if (showAddForm) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showAddForm) "Cancel" else "Add Homework")
                }
            }
        }

        if (showAddForm) {
            item {
                SunshineCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "New Homework Assignment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        // Class Selector
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

                        // Subject Selector
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
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Assignment Title *") },
                            placeholder = { Text("e.g. Exercise 4.2 Linear Equations") },
                            modifier = Modifier.fillMaxWidth().testTag("homework_title_input")
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Instructions & Problems *") },
                            placeholder = { Text("Solve Q1 to Q8 from textbook page 74.") },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            maxLines = 4
                        )

                        OutlinedTextField(
                            value = dueDate,
                            onValueChange = { dueDate = it },
                            label = { Text("Due Date (YYYY-MM-DD)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = attachmentUrl,
                            onValueChange = { attachmentUrl = it },
                            label = { Text("External Attachment URL (Drive / ImgBB / PDF Link)") },
                            placeholder = { Text("https://drive.google.com/... or https://i.ibb.co/...") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (title.isNotBlank()) {
                                    viewModel.addHomework(
                                        className = selectedClass,
                                        subject = selectedSubject,
                                        title = title,
                                        description = description,
                                        dueDate = dueDate,
                                        attachmentUrl = attachmentUrl
                                    )
                                    title = ""
                                    description = ""
                                    attachmentUrl = ""
                                    showAddForm = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Publish Homework & Alert Class", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Active Homework Roster (${allHomework.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (allHomework.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Homework Entries",
                    message = "Tap 'Add Homework' to publish assignments to classes.",
                    icon = Icons.Default.Assignment
                )
            }
        } else {
            items(allHomework, key = { it.id }) { hw ->
                SunshineCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SunshinePrimary.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "${hw.className} • ${hw.subject}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SunshinePrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Due: ${hw.dueDate}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = ColorError
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = hw.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = hw.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (hw.attachmentUrl.isNotBlank()) {
                                Text(
                                    text = "📎 ${hw.attachmentUrl}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SunshinePrimary,
                                    maxLines = 1
                                )
                            }
                        }

                        IconButton(onClick = { viewModel.deleteHomework(hw) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 2. Manage Notices Screen (Broadcast with priority & attachments)
// ----------------------------------------------------------------------
@Composable
fun ManageNoticesScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allNotices by viewModel.allNotices.collectAsState()

    var showAddForm by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedTargetClass by remember { mutableStateOf("All Classes") }
    var priority by remember { mutableStateOf("Normal") }
    var isPinned by remember { mutableStateOf(false) }
    var attachmentUrl by remember { mutableStateOf("") }

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
                    text = "📢 School Notice Board",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { showAddForm = !showAddForm },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(if (showAddForm) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showAddForm) "Cancel" else "Post Notice")
                }
            }
        }

        if (showAddForm) {
            item {
                SunshineCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Compose New Notice",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Notice Title *") },
                            placeholder = { Text("e.g. Science Exhibition Registration") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text("Announcement Details *") },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            maxLines = 4
                        )

                        Text("Target Audience:", style = MaterialTheme.typography.labelMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            item {
                                FilterChip(
                                    selected = selectedTargetClass == "All Classes",
                                    onClick = { selectedTargetClass = "All Classes" },
                                    label = { Text("All Classes") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SunshinePrimary,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                            items(allClasses) { cls ->
                                FilterChip(
                                    selected = selectedTargetClass == cls.name,
                                    onClick = { selectedTargetClass = cls.name },
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Priority:")
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("Normal", "High", "Urgent").forEach { p ->
                                    FilterChip(
                                        selected = priority == p,
                                        onClick = { priority = p },
                                        label = { Text(p) }
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Pin to Top of Notice Board:")
                            Switch(
                                checked = isPinned,
                                onCheckedChange = { isPinned = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = SunshinePrimary)
                            )
                        }

                        OutlinedTextField(
                            value = attachmentUrl,
                            onValueChange = { attachmentUrl = it },
                            label = { Text("Circular PDF / Image Link (Optional)") },
                            placeholder = { Text("https://drive.google.com/...") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (title.isNotBlank() && content.isNotBlank()) {
                                    viewModel.addNotice(
                                        title = title,
                                        content = content,
                                        targetClass = selectedTargetClass,
                                        priority = priority,
                                        isPinned = isPinned,
                                        attachmentUrl = attachmentUrl
                                    )
                                    title = ""
                                    content = ""
                                    attachmentUrl = ""
                                    showAddForm = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Publish Notice & Send Alerts", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Published Announcements (${allNotices.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (allNotices.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Notices",
                    message = "Tap 'Post Notice' to broadcast school circulars.",
                    icon = Icons.Default.Campaign
                )
            }
        } else {
            items(allNotices, key = { it.id }) { notice ->
                SunshineCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (notice.isPinned) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = SunshinePrimary
                                    ) {
                                        Text(
                                            text = "PINNED",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = "${notice.date} • ${notice.targetClass}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = notice.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = notice.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = { viewModel.deleteNotice(notice) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ColorError)
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ----------------------------------------------------------------------
// 3. Mark Attendance Screen (Class roster with 1-tap all present)
// ----------------------------------------------------------------------
@Composable
fun MarkAttendanceScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()

    var selectedClass by remember { mutableStateOf("Class 8") }
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    var attendanceDate by remember { mutableStateOf(today) }

    val classStudents = allStudents.filter { it.className == selectedClass }

    // Map of studentId -> status ("PRESENT", "ABSENT", "LEAVE")
    var attendanceStatusMap by remember(selectedClass) {
        mutableStateOf(classStudents.associate { it.id to "PRESENT" })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📋 Mark Daily Attendance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Class & Date selector Card
        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Select Class:", style = MaterialTheme.typography.labelMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(allClasses) { cls ->
                            FilterChip(
                                selected = selectedClass == cls.name,
                                onClick = {
                                    selectedClass = cls.name
                                },
                                label = { Text(cls.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = attendanceDate,
                        onValueChange = { attendanceDate = it },
                        label = { Text("Attendance Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.markAllClassPresent(selectedClass, attendanceDate)
                                attendanceStatusMap = classStudents.associate { it.id to "PRESENT" }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ColorSuccess),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Mark All Present")
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Students in $selectedClass (${classStudents.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (classStudents.isEmpty()) {
            item {
                EmptyStateCard(
                    title = "No Students Enrolled",
                    message = "No students are registered in $selectedClass. Add students in Student Directory.",
                    icon = Icons.Default.HowToReg
                )
            }
        } else {
            items(classStudents, key = { it.id }) { student ->
                val currentStatus = attendanceStatusMap[student.id] ?: "PRESENT"

                SunshineCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SunshinePrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${student.rollNo}",
                                    fontWeight = FontWeight.Bold,
                                    color = SunshinePrimary
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = student.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "ID: ${student.studentCode} • Sec: ${student.section}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Toggle Buttons (P / A / L)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // Present Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == "PRESENT") ColorSuccess else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clickable {
                                        attendanceStatusMap = attendanceStatusMap + (student.id to "PRESENT")
                                        viewModel.markAttendance(student.id, student.name, student.className, attendanceDate, "PRESENT")
                                    }
                            ) {
                                Text(
                                    text = "P",
                                    color = if (currentStatus == "PRESENT") Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }

                            // Absent Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == "ABSENT") ColorError else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clickable {
                                        attendanceStatusMap = attendanceStatusMap + (student.id to "ABSENT")
                                        viewModel.markAttendance(student.id, student.name, student.className, attendanceDate, "ABSENT")
                                    }
                            ) {
                                Text(
                                    text = "A",
                                    color = if (currentStatus == "ABSENT") Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }

                            // Leave Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentStatus == "LEAVE") ColorWarning else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .clickable {
                                        attendanceStatusMap = attendanceStatusMap + (student.id to "LEAVE")
                                        viewModel.markAttendance(student.id, student.name, student.className, attendanceDate, "LEAVE")
                                    }
                            ) {
                                Text(
                                    text = "L",
                                    color = if (currentStatus == "LEAVE") Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
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

// ----------------------------------------------------------------------
// 4. Send Notifications Screen (FCM Broadcast System)
// ----------------------------------------------------------------------
@Composable
fun SendNotificationScreen(viewModel: SchoolViewModel) {
    val allClasses by viewModel.allClasses.collectAsState()
    val allNotifs by viewModel.allNotifications.collectAsState()

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var targetClass by remember { mutableStateOf("All Classes") }
    var notifType by remember { mutableStateOf("GENERAL") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🔔 Broadcast Push Notification Alert",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        item {
            SunshineCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Instant Mobile Push Broadcast (FCM)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Notification Title *") },
                        placeholder = { Text("e.g. 📢 School Closed Tomorrow due to Rain") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Alert Message *") },
                        placeholder = { Text("All students and parents please note school will remain closed tomorrow.") },
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        maxLines = 4
                    )

                    Text("Target Audience:", style = MaterialTheme.typography.labelMedium)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        item {
                            FilterChip(
                                selected = targetClass == "All Classes",
                                onClick = { targetClass = "All Classes" },
                                label = { Text("All Classes") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                        items(allClasses) { cls ->
                            FilterChip(
                                selected = targetClass == cls.name,
                                onClick = { targetClass = cls.name },
                                label = { Text(cls.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SunshinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (title.isNotBlank() && message.isNotBlank()) {
                                viewModel.sendBroadcastNotification(title, message, targetClass, notifType)
                                title = ""
                                message = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Push Broadcast Immediately", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text(
                text = "Notification Dispatch History (${allNotifs.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(allNotifs, key = { it.id }) { notif ->
            SunshineCard {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = notif.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = notif.targetClass,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notif.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sent: ${notif.timestamp}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
