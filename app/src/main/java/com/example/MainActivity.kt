package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.preferences.UserRole
import com.example.ui.components.SchoolTopHeader
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.admin.ManageClassesSubjectsScreen
import com.example.ui.screens.admin.ManageExamsScreen
import com.example.ui.screens.admin.ManageHomeworkScreen
import com.example.ui.screens.admin.ManageNotesMaterialsScreen
import com.example.ui.screens.admin.ManageNoticesScreen
import com.example.ui.screens.admin.ManageResultsScreen
import com.example.ui.screens.admin.ManageStudentsScreen
import com.example.ui.screens.admin.ManageTimetableScreen
import com.example.ui.screens.admin.MarkAttendanceScreen
import com.example.ui.screens.admin.SendNotificationScreen
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.user.AttendanceScreen
import com.example.ui.screens.user.ExamsSyllabusScreen
import com.example.ui.screens.user.FeesScreen
import com.example.ui.screens.user.HomeworkScreen
import com.example.ui.screens.user.NotesAndBooksScreen
import com.example.ui.screens.user.NoticeBoardScreen
import com.example.ui.screens.user.NotificationsScreen
import com.example.ui.screens.user.ResultMarksheetScreen
import com.example.ui.screens.user.StudyMaterialScreen
import com.example.ui.screens.user.TimetableScreen
import com.example.ui.screens.user.UserHomeScreen
import com.example.ui.screens.user.UserMoreMenuScreen
import com.example.ui.screens.user.VideoLecturesScreen
import com.example.ui.theme.DigitalSchoolBagTheme
import com.example.ui.theme.SunshinePrimary
import com.example.ui.viewmodel.AdminSection
import com.example.ui.viewmodel.SchoolViewModel
import com.example.ui.viewmodel.UserNavTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SchoolViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            DigitalSchoolBagTheme(darkTheme = isDarkMode) {
                DigitalSchoolBagApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun DigitalSchoolBagApp(viewModel: SchoolViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()
    val userTab by viewModel.currentUserTab.collectAsState()
    val userSubscreen by viewModel.userMoreSubscreen.collectAsState()
    val adminSection by viewModel.currentAdminSection.collectAsState()
    val notifications by viewModel.allNotifications.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val unreadNotifs = notifications.count { !it.isRead }

    LaunchedEffect(statusMessage) {
        statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    if (!isLoggedIn) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("auth_scaffold"),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AuthScreen(viewModel = viewModel)
            }
        }
        return
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_root_scaffold"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            // Header configuration depends on role & navigation depth
            val headerTitle: String
            val headerSubtitle: String?
            val onBack: (() -> Unit)?

            if (currentRole == UserRole.ADMIN_TEACHER) {
                if (adminSection == AdminSection.DASHBOARD) {
                    headerTitle = "Digital School Bag"
                    headerSubtitle = "Admin & Teacher Portal"
                    onBack = null
                } else {
                    headerTitle = adminSection.title
                    headerSubtitle = "School Administration"
                    onBack = { viewModel.setAdminSection(AdminSection.DASHBOARD) }
                }
            } else {
                if (userSubscreen != null) {
                    headerTitle = when (userSubscreen) {
                        "notes" -> "Notes & E-Books"
                        "exams" -> "Exams & Syllabus"
                        "results" -> "Report Card"
                        "attendance" -> "Attendance Record"
                        "fees" -> "Fee Invoices"
                        "videos" -> "Video Classes"
                        "study_materials" -> "Study Materials"
                        "notifications" -> "Notifications"
                        else -> "School Bag"
                    }
                    headerSubtitle = activeStudent?.className
                    onBack = { viewModel.navigateUserSubscreen(null) }
                } else {
                    headerTitle = when (userTab) {
                        UserNavTab.HOME -> "Digital School Bag"
                        UserNavTab.HOMEWORK -> "Daily Homework"
                        UserNavTab.TIMETABLE -> "Class Timetable"
                        UserNavTab.NOTICES -> "School Notices"
                        UserNavTab.MORE -> "More Features"
                    }
                    headerSubtitle = if (userTab == UserNavTab.HOME) activeStudent?.className else null
                    onBack = null
                }
            }

            SchoolTopHeader(
                title = headerTitle,
                subtitle = headerSubtitle,
                role = currentRole,
                activeStudent = activeStudent,
                allStudents = allStudents,
                unreadNotifCount = unreadNotifs,
                isDarkMode = isDarkMode,
                onToggleDarkMode = { viewModel.toggleDarkMode() },
                onLogout = { viewModel.logout() },
                onSelectStudent = { studentId -> viewModel.switchActiveChild(studentId) },
                onOpenNotifications = {
                    if (currentRole == UserRole.STUDENT_PARENT) {
                        viewModel.navigateUserSubscreen("notifications")
                    } else {
                        viewModel.setAdminSection(AdminSection.SEND_NOTIF)
                    }
                },
                onBackClick = onBack
            )
        },
        bottomBar = {
            // Show bottom navigation bar in Student / Parent mode when not in subscreen
            if (currentRole == UserRole.STUDENT_PARENT && userSubscreen == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = SunshinePrimary
                ) {
                    NavigationBarItem(
                        selected = userTab == UserNavTab.HOME,
                        onClick = { viewModel.setUserTab(UserNavTab.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SunshinePrimary,
                            selectedTextColor = SunshinePrimary,
                            indicatorColor = SunshinePrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_home")
                    )
                    NavigationBarItem(
                        selected = userTab == UserNavTab.HOMEWORK,
                        onClick = { viewModel.setUserTab(UserNavTab.HOMEWORK) },
                        icon = { Icon(Icons.Default.Assignment, contentDescription = "Homework") },
                        label = { Text("Homework", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SunshinePrimary,
                            selectedTextColor = SunshinePrimary,
                            indicatorColor = SunshinePrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_homework")
                    )
                    NavigationBarItem(
                        selected = userTab == UserNavTab.TIMETABLE,
                        onClick = { viewModel.setUserTab(UserNavTab.TIMETABLE) },
                        icon = { Icon(Icons.Default.Schedule, contentDescription = "Timetable") },
                        label = { Text("Timetable", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SunshinePrimary,
                            selectedTextColor = SunshinePrimary,
                            indicatorColor = SunshinePrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_timetable")
                    )
                    NavigationBarItem(
                        selected = userTab == UserNavTab.NOTICES,
                        onClick = { viewModel.setUserTab(UserNavTab.NOTICES) },
                        icon = { Icon(Icons.Default.Campaign, contentDescription = "Notices") },
                        label = { Text("Notices", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SunshinePrimary,
                            selectedTextColor = SunshinePrimary,
                            indicatorColor = SunshinePrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_notices")
                    )
                    NavigationBarItem(
                        selected = userTab == UserNavTab.MORE,
                        onClick = { viewModel.setUserTab(UserNavTab.MORE) },
                        icon = { Icon(Icons.Default.GridView, contentDescription = "More") },
                        label = { Text("More", fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SunshinePrimary,
                            selectedTextColor = SunshinePrimary,
                            indicatorColor = SunshinePrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("nav_tab_more")
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedContent(
                targetState = Pair(currentRole, if (currentRole == UserRole.ADMIN_TEACHER) adminSection.name else (userSubscreen ?: userTab.name)),
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "MainContentTransition"
            ) { (_, stateKey) ->
                if (currentRole == UserRole.ADMIN_TEACHER) {
                    when (adminSection) {
                        AdminSection.DASHBOARD -> AdminDashboardScreen(
                            viewModel = viewModel,
                            onSelectSection = { sec -> viewModel.setAdminSection(sec) }
                        )
                        AdminSection.HOMEWORK -> ManageHomeworkScreen(viewModel = viewModel)
                        AdminSection.NOTICES -> ManageNoticesScreen(viewModel = viewModel)
                        AdminSection.ATTENDANCE -> MarkAttendanceScreen(viewModel = viewModel)
                        AdminSection.RESULTS -> ManageResultsScreen(viewModel = viewModel)
                        AdminSection.EXAMS -> ManageExamsScreen(viewModel = viewModel)
                        AdminSection.TIMETABLE -> ManageTimetableScreen(viewModel = viewModel)
                        AdminSection.STUDENTS -> ManageStudentsScreen(viewModel = viewModel)
                        AdminSection.CLASSES_SUBJECTS -> ManageClassesSubjectsScreen(viewModel = viewModel)
                        AdminSection.NOTES_MATERIALS -> ManageNotesMaterialsScreen(viewModel = viewModel)
                        AdminSection.SEND_NOTIF -> SendNotificationScreen(viewModel = viewModel)
                    }
                } else {
                    if (userSubscreen != null) {
                        when (userSubscreen) {
                            "notes" -> NotesAndBooksScreen(viewModel = viewModel)
                            "exams" -> ExamsSyllabusScreen(viewModel = viewModel)
                            "results" -> ResultMarksheetScreen(viewModel = viewModel)
                            "attendance" -> AttendanceScreen(viewModel = viewModel)
                            "fees" -> FeesScreen(viewModel = viewModel)
                            "videos" -> VideoLecturesScreen(viewModel = viewModel)
                            "study_materials" -> StudyMaterialScreen(viewModel = viewModel)
                            "notifications" -> NotificationsScreen(viewModel = viewModel)
                            else -> UserHomeScreen(
                                viewModel = viewModel,
                                onNavigateTab = { tab -> viewModel.setUserTab(tab) },
                                onNavigateSubscreen = { sub -> viewModel.navigateUserSubscreen(sub) }
                            )
                        }
                    } else {
                        when (userTab) {
                            UserNavTab.HOME -> UserHomeScreen(
                                viewModel = viewModel,
                                onNavigateTab = { tab -> viewModel.setUserTab(tab) },
                                onNavigateSubscreen = { sub -> viewModel.navigateUserSubscreen(sub) }
                            )
                            UserNavTab.HOMEWORK -> HomeworkScreen(viewModel = viewModel)
                            UserNavTab.TIMETABLE -> TimetableScreen(viewModel = viewModel)
                            UserNavTab.NOTICES -> NoticeBoardScreen(viewModel = viewModel)
                            UserNavTab.MORE -> UserMoreMenuScreen(
                                onNavigateSubscreen = { sub -> viewModel.navigateUserSubscreen(sub) }
                            )
                        }
                    }
                }
            }
        }
    }
}
