package com.example.ui.screens.user

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.data.model.Homework
import com.example.ui.components.AttachmentLinkButton
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorSuccess
import com.example.ui.theme.SunshinePrimary
import com.example.ui.viewmodel.SchoolViewModel

@Composable
fun HomeworkScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allHomework by viewModel.allHomework.collectAsState()
    val subjects by viewModel.allSubjects.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Pending, 1: Completed, 2: All
    var selectedSubjectFilter by remember { mutableStateOf("All") }

    val studentClass = activeStudent?.className ?: "Class 8"
    val classHomework = allHomework.filter { it.className == studentClass }

    val filteredHomework = classHomework.filter { hw ->
        val matchesTab = when (selectedTab) {
            0 -> !hw.isCompleted
            1 -> hw.isCompleted
            else -> true
        }
        val matchesSubject = if (selectedSubjectFilter == "All") true else hw.subject == selectedSubjectFilter
        matchesTab && matchesSubject
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Tab row for Pending vs Completed vs All
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = SunshinePrimary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = SunshinePrimary
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Pending (${classHomework.count { !it.isCompleted }})", fontWeight = FontWeight.SemiBold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Completed (${classHomework.count { it.isCompleted }})", fontWeight = FontWeight.SemiBold) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("All (${classHomework.size})", fontWeight = FontWeight.SemiBold) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Subject filter chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedSubjectFilter == "All",
                    onClick = { selectedSubjectFilter = "All" },
                    label = { Text("All Subjects") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunshinePrimary.copy(alpha = 0.2f),
                        selectedLabelColor = SunshinePrimary
                    )
                )
            }
            items(subjects) { subject ->
                FilterChip(
                    selected = selectedSubjectFilter == subject.name,
                    onClick = { selectedSubjectFilter = subject.name },
                    label = { Text(subject.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunshinePrimary.copy(alpha = 0.2f),
                        selectedLabelColor = SunshinePrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Homework list
        if (filteredHomework.isEmpty()) {
            EmptyStateCard(
                title = if (selectedTab == 0) "No Pending Homework!" else "No Assignments Found",
                message = if (selectedTab == 0) "Great job! All your assignments are submitted and up to date." else "No homework entries matching this filter.",
                icon = Icons.Default.CheckCircle
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredHomework, key = { it.id }) { hw ->
                    HomeworkCard(
                        homework = hw,
                        onToggle = { viewModel.toggleHomeworkCompletion(hw) },
                        onOpenAttachment = { viewModel.openExternalLink(hw.attachmentUrl) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun HomeworkCard(
    homework: Homework,
    onToggle: () -> Unit,
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
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SunshinePrimary.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = homework.subject,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SunshinePrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Assigned: ${homework.assignedDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (homework.isCompleted) ColorSuccess.copy(alpha = 0.15f) else ColorError.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = if (homework.isCompleted) "Completed" else "Due: ${homework.dueDate}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (homework.isCompleted) ColorSuccess else ColorError,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Top) {
                Checkbox(
                    checked = homework.isCompleted,
                    onCheckedChange = { onToggle() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ColorSuccess,
                        uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = homework.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = homework.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (homework.attachmentUrl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        AttachmentLinkButton(
                            url = homework.attachmentUrl,
                            label = "Open Assignment File / Worksheet",
                            onOpenLink = { onOpenAttachment() }
                        )
                    }
                }
            }
        }
    }
}
