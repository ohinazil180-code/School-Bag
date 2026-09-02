package com.example.ui.screens.user

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Notice
import com.example.ui.components.AttachmentLinkButton
import com.example.ui.components.EmptyStateCard
import com.example.ui.components.SunshineCard
import com.example.ui.theme.ColorError
import com.example.ui.theme.ColorWarning
import com.example.ui.theme.SunshinePrimary
import com.example.ui.viewmodel.SchoolViewModel

@Composable
fun NoticeBoardScreen(viewModel: SchoolViewModel) {
    val activeStudent by viewModel.activeStudent.collectAsState()
    val allNotices by viewModel.allNotices.collectAsState()
    val studentClass = activeStudent?.className ?: "Class 8"

    var selectedFilter by remember { mutableStateOf("All") } // "All", "Pinned", "Class Specific", "Urgent"

    val classNotices = allNotices.filter {
        it.targetClass == "All Classes" || it.targetClass == studentClass
    }

    val filteredNotices = classNotices.filter { notice ->
        when (selectedFilter) {
            "Pinned" -> notice.isPinned
            "Class Specific" -> notice.targetClass == studentClass
            "Urgent" -> notice.priority == "Urgent" || notice.priority == "High"
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Filter chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            val filters = listOf("All", "Pinned", "Class Specific", "Urgent")
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SunshinePrimary.copy(alpha = 0.2f),
                        selectedLabelColor = SunshinePrimary
                    )
                )
            }
        }

        if (filteredNotices.isEmpty()) {
            EmptyStateCard(
                title = "No Notices Posted",
                message = "There are currently no active announcements matching your filter.",
                icon = Icons.Default.Campaign
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredNotices, key = { it.id }) { notice ->
                    NoticeDetailCard(
                        notice = notice,
                        onOpenAttachment = { viewModel.openExternalLink(notice.attachmentUrl) }
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
fun NoticeDetailCard(
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
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PushPin,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "PINNED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = notice.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (notice.priority == "Urgent" || notice.priority == "High") {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = ColorError.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = notice.priority.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = ColorError,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = notice.targetClass,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = notice.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notice.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            if (notice.attachmentUrl.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                AttachmentLinkButton(
                    url = notice.attachmentUrl,
                    label = "View Official Document / Poster",
                    onOpenLink = { onOpenAttachment() }
                )
            }
        }
    }
}
