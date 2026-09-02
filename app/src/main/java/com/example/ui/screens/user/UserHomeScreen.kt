package com.example.ui.screens.user

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Grading
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Homework
import com.example.data.model.Notice
import com.example.data.model.TimetableEntry
import com.example.ui.components.AttachmentLinkButton
import com.example.ui.components.StatMetricCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorInfo
import com.example.ui.theme.ColorPurple
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.ColorTeal
import com.example.ui.theme.SunshinePrimary
import com.example.ui.theme.SunshineSecondary
import com.example.ui.viewmodel.SchoolViewModel
import com.example.ui.viewmodel.UserNavTab
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserHomeScreen(
    viewModel: SchoolViewModel,
    onNavigateTab: (UserNavTab) -> Unit,
    onNavigateSubscreen: (String) -> Unit
) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val homeworkList by viewModel.allHomework.collectAsState()
    val noticesList by viewModel.allNotices.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"
    val studentFees by viewModel.studentFees.collectAsState()

    val todayDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
    // Sample fallback to Monday if today is Sunday
    val displayDay = if (todayDayName == "Sunday") "Monday" else todayDayName

    val pendingHomework = homeworkList.filter { !it.isCompleted && it.className == studentClass }
    val latestNotices = noticesList.take(3)

    val attendancePct = if (activeStudent != null && activeStudent!!.totalAttendanceDays > 0) {
        (activeStudent!!.presentDays * 100) / activeStudent!!.totalAttendanceDays
    } else 94

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Welcome Hero Banner
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("user_hero_banner"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SunshinePrimary, SunshineSecondary)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Welcome back,",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Text(
                                    text = activeStudent?.name ?: "Student",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = "${activeStudent?.className} - ${activeStudent?.section ?: "A"}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Student ID: ${activeStudent?.studentCode ?: "DSB-2026-8A01"} • Roll No: ${activeStudent?.rollNo ?: 1}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // 2. Quick Metrics Row (Attendance, Pending HW, Fee Status)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatMetricCard(
                    title = "Attendance",
                    value = "$attendancePct%",
                    subtitle = "${activeStudent?.presentDays ?: 114}/${activeStudent?.totalAttendanceDays ?: 120} days",
                    icon = Icons.Default.CheckCircle,
                    iconColor = ColorSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSubscreen("attendance") }
                )
                StatMetricCard(
                    title = "Homework",
                    value = "${pendingHomework.size}",
                    subtitle = "Pending tasks",
                    icon = Icons.Default.Assignment,
                    iconColor = SunshinePrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(UserNavTab.HOMEWORK) }
                )
                StatMetricCard(
                    title = "Fee Status",
                    value = activeStudent?.feeStatus ?: "Paid",
                    subtitle = if ((activeStudent?.pendingAmount ?: 0.0) > 0) "₹${activeStudent?.pendingAmount?.toInt()} due" else "All clear",
                    icon = Icons.Default.Payments,
                    iconColor = if (activeStudent?.feeStatus == "Paid") ColorSuccess else ColorError,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSubscreen("fees") }
                )
            }
        }

        // 3. Quick Modules Grid (Digital School Bag Features)
        item {
            Text(
                text = "🎒 Digital Bag Essentials",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        title = "Notes & Books",
                        subtitle = "Subject PDFs",
                        icon = Icons.Default.MenuBook,
                        color = SunshinePrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateSubscreen("notes") }
                    )
                    QuickActionTile(
                        title = "Exams & Syllabus",
                        subtitle = "Mid-Term Dates",
                        icon = Icons.Default.CalendarMonth,
                        color = ColorInfo,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateSubscreen("exams") }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        title = "Report Card",
                        subtitle = "Marksheets & GPA",
                        icon = Icons.Default.Grading,
                        color = ColorPurple,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateSubscreen("results") }
                    )
                    QuickActionTile(
                        title = "Video Classes",
                        subtitle = "Recorded Lectures",
                        icon = Icons.Default.VideoLibrary,
                        color = ColorError,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateSubscreen("videos") }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        title = "Study Materials",
                        subtitle = "Question Papers",
                        icon = Icons.Default.Download,
                        color = ColorTeal,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateSubscreen("study_materials") }
                    )
                    QuickActionTile(
                        title = "Class Schedule",
                        subtitle = "Weekly Timetable",
                        icon = Icons.Default.Schedule,
                        color = SunshineSecondary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(UserNavTab.TIMETABLE) }
                    )
                }
            }
        }

        // 4. Pending Homework Section with Checkbox
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📝 Daily Homework (${pendingHomework.size} Pending)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onNavigateTab(UserNavTab.HOMEWORK) }) {
                    Text("View All", color = SunshinePrimary)
                }
            }

            if (pendingHomework.isEmpty()) {
                SunshineCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = ColorSuccess,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "All homework assignments are completed!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    pendingHomework.take(3).forEach { hw ->
                        HomeworkQuickItem(
                            homework = hw,
                            onToggleComplete = { viewModel.toggleHomeworkCompletion(hw) },
                            onOpenAttachment = { viewModel.openExternalLink(hw.attachmentUrl) }
                        )
                    }
                }
            }
        }

        // 5. School Notice Board Carousel
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📢 School Notice Board",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onNavigateTab(UserNavTab.NOTICES) }) {
                    Text("View All", color = SunshinePrimary)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                latestNotices.forEach { notice ->
                    NoticeQuickItem(
                        notice = notice,
                        onOpenAttachment = { viewModel.openExternalLink(notice.attachmentUrl) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun QuickActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HomeworkQuickItem(
    homework: Homework,
    onToggleComplete: () -> Unit,
    onOpenAttachment: () -> Unit
) {
    SunshineCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onToggleComplete) {
                Icon(
                    imageVector = if (homework.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Toggle Complete",
                    tint = if (homework.isCompleted) ColorSuccess else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = SunshinePrimary.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = homework.subject,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SunshinePrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Due: ${homework.dueDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = ColorError,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = homework.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = homework.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (homework.attachmentUrl.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    AttachmentLinkButton(
                        url = homework.attachmentUrl,
                        label = "View Worksheet / Resource",
                        onOpenLink = { onOpenAttachment() }
                    )
                }
            }
        }
    }
}

@Composable
fun NoticeQuickItem(
    notice: Notice,
    onOpenAttachment: () -> Unit
) {
    SunshineCard {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        text = notice.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (notice.priority == "Urgent") ColorError.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = notice.targetClass,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (notice.priority == "Urgent") ColorError else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = notice.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notice.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (notice.attachmentUrl.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                AttachmentLinkButton(
                    url = notice.attachmentUrl,
                    label = "View Notice Attachment",
                    onOpenLink = { onOpenAttachment() }
                )
            }
        }
    }
}
