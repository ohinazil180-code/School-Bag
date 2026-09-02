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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.preferences.UserRole
import com.example.ui.components.StatMetricCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorInfo
import com.example.ui.theme.ColorPurple
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.ColorTeal
import com.example.ui.theme.ColorWarning
import com.example.ui.theme.SunshinePrimary
import com.example.ui.theme.SunshineSecondary
import com.example.ui.viewmodel.AdminSection
import com.example.ui.viewmodel.SchoolViewModel

data class AdminQuickAction(
    val section: AdminSection,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun AdminDashboardScreen(
    viewModel: SchoolViewModel,
    onSelectSection: (AdminSection) -> Unit
) {
    val students by viewModel.allStudents.collectAsState()
    val classes by viewModel.allClasses.collectAsState()
    val homework by viewModel.allHomework.collectAsState()
    val notices by viewModel.allNotices.collectAsState()

    val adminActions = listOf(
        AdminQuickAction(AdminSection.ATTENDANCE, "Mark Attendance", "Daily class-wise attendance", Icons.Default.HowToReg, ColorSuccess),
        AdminQuickAction(AdminSection.HOMEWORK, "Post Homework", "Upload tasks & external links", Icons.Default.Assignment, SunshinePrimary),
        AdminQuickAction(AdminSection.NOTICES, "Post Notice", "Broadcast school notices", Icons.Default.Campaign, ColorError),
        AdminQuickAction(AdminSection.RESULTS, "Upload Marks", "Enter term marks & grades", Icons.Default.Grade, ColorPurple),
        AdminQuickAction(AdminSection.NOTES_MATERIALS, "Study Notes", "Add textbook & PDF links", Icons.Default.MenuBook, ColorTeal),
        AdminQuickAction(AdminSection.EXAMS, "Exam Schedule", "Manage timetable & syllabus", Icons.Default.Quiz, ColorInfo),
        AdminQuickAction(AdminSection.TIMETABLE, "Timetable Matrix", "Edit weekly class periods", Icons.Default.CalendarMonth, SunshineSecondary),
        AdminQuickAction(AdminSection.STUDENTS, "Student Roster", "Add & manage student IDs", Icons.Default.People, ColorWarning),
        AdminQuickAction(AdminSection.CLASSES_SUBJECTS, "Classes & Subjects", "Manage academic structure", Icons.Default.Category, ColorPurple),
        AdminQuickAction(AdminSection.SEND_NOTIF, "Push Alerts (FCM)", "Send alerts to classes", Icons.Default.Send, SunshinePrimary)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Admin Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_hero_banner"),
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
                                    text = "School Administration",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Text(
                                    text = "Teacher & Admin Portal",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AdminPanelSettings,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Online DB",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Manage classes, homework, attendance, syllabus, and results in real-time.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Stats Overview Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatMetricCard(
                    title = "Students",
                    value = "${students.size}",
                    subtitle = "Registered",
                    icon = Icons.Default.People,
                    iconColor = SunshinePrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelectSection(AdminSection.STUDENTS) }
                )
                StatMetricCard(
                    title = "Classes",
                    value = "${classes.size}",
                    subtitle = "Active sections",
                    icon = Icons.Default.Category,
                    iconColor = ColorInfo,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelectSection(AdminSection.CLASSES_SUBJECTS) }
                )
                StatMetricCard(
                    title = "Homework",
                    value = "${homework.size}",
                    subtitle = "Active tasks",
                    icon = Icons.Default.Assignment,
                    iconColor = ColorSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelectSection(AdminSection.HOMEWORK) }
                )
            }
        }

        // Switch to Student App Button
        item {
            SunshineCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Student & Parent Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Preview how students and parents view the Digital School Bag",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { viewModel.switchRole(UserRole.STUDENT_PARENT) },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshinePrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Switch Mode")
                    }
                }
            }
        }

        // Management Grid
        item {
            Text(
                text = "🛠️ School Control Center",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                adminActions.chunked(2).forEach { rowActions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowActions.forEach { action ->
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(115.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { onSelectSection(action.section) }
                                    .testTag("admin_tile_${action.section.name.lowercase()}"),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = CardDefaults.outlinedCardBorder()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(action.color.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = action.icon,
                                            contentDescription = null,
                                            tint = action.color,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = action.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = action.description,
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
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
