package com.yourapp.vocalize.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourapp.vocalize.Utils
import com.yourapp.vocalize.data.model.Memo
import com.yourapp.vocalize.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onRecordTap: () -> Unit,
    onMemoSelected: (Memo) -> Unit,
    onCalendarTap: () -> Unit,
    onSearchTap: () -> Unit,
    onSettingsTap: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tabs = listOf("Recents", "All Memos", "Playlists")
    val selectedTab = remember { mutableStateOf(0) }
    val memos = viewModel.memos.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Vocalize") },
                actions = {
                    IconButton(onClick = onSettingsTap) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    Text(
                        text = title,
                        color = if (selectedTab.value == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        fontWeight = if (selectedTab.value == index) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.clickable { selectedTab.value = index }
                    )
                }
            }

            LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
                items(memos.take(10)) { memo ->
                    MemoCard(memo, onClick = { onMemoSelected(memo) })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = onRecordTap,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 88.dp)
                .size(72.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Record",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        NavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavItem(Icons.Default.Home, "Home", selected = true, onClick = {})
            BottomNavItem(Icons.Default.CalendarToday, "Calendar", selected = false, onClick = onCalendarTap)
            BottomNavItem(Icons.Default.Search, "Search", selected = false, onClick = onSearchTap)
            BottomNavItem(Icons.Default.Person, "Profile", selected = false, onClick = onSettingsTap)
        }
    }
}

@Composable
private fun BottomNavItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}

@Composable
fun MemoCard(memo: Memo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = memo.title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = Utils.formatTimestamp(memo.dateCreated), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                Text(text = Utils.formatDuration(memo.duration), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
            }
        }
    }
}
