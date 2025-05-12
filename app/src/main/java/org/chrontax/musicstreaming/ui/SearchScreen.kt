package org.chrontax.musicstreaming.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.chrontax.musicstreaming.data.Artist
import org.chrontax.musicstreaming.data.Release
import org.chrontax.musicstreaming.data.Track
import org.chrontax.musicstreaming.navigation.AppDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Search") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Tabs
            SearchTabs(
                selectedTab = uiState.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )

            // Search Results (based on selected tab)
            SearchResultContent(uiState = uiState, navController = navController)
        }
    }
}

@Composable
fun SearchTabs(
    selectedTab: SearchTab,
    onTabSelected: (SearchTab) -> Unit
) {
    val tabs = remember { SearchTab.entries.toTypedArray() }
    TabRow(selectedTabIndex = tabs.indexOf(selectedTab)) {
        tabs.forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                text = { Text(tab.name) }
            )
        }
    }
}

@Composable
fun SearchResultContent(uiState: SearchUiState, navController: NavController) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${uiState.error.localizedMessage}")
            }
        }

        else -> {
            when (uiState.selectedTab) {
                SearchTab.ARTISTS -> ArtistSearchResults(artists = uiState.artistResults)
                SearchTab.RELEASES -> ReleaseSearchResults(
                    releases = uiState.releaseResults,
                    navController = navController
                )

                SearchTab.TRACKS -> TrackSearchResults(tracks = uiState.trackResults)
            }
        }
    }
}

@Composable
fun ArtistSearchResults(artists: List<Artist>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(artists) { artist ->
            Text("${artist.name} (${artist.original_name})", modifier = Modifier.padding(8.dp))
            HorizontalDivider() // Add a divider for visual separation
        }
    }
}

@Composable
fun ReleaseSearchResults(releases: List<Release>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(releases) { release ->
            Row {
                Text(release.name, modifier = Modifier.padding(8.dp))
                Button(onClick = {
                    navController.navigate(AppDestinations.RELEASE_BASE_ROUTE + "/" + release.id)
                }) {
                    Text("View")
                }
            }
            HorizontalDivider() // Add a divider for visual separation
        }
    }
}

@Composable
fun TrackSearchResults(tracks: List<Track>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tracks) { track ->
            Text(track.name, modifier = Modifier.padding(8.dp))
            HorizontalDivider() // Add a divider for visual separation
        }
    }
}