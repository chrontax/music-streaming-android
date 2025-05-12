package org.chrontax.musicstreaming.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.chrontax.musicstreaming.data.Artist
import org.chrontax.musicstreaming.data.Release
import org.chrontax.musicstreaming.data.Track
import org.chrontax.musicstreaming.network.MusicApi
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

enum class SearchTab {
    ARTISTS, RELEASES, TRACKS
}

data class SearchUiState(
    val searchQuery: String = "",
    val selectedTab: SearchTab = SearchTab.ARTISTS,
    val artistResults: List<Artist> = emptyList(),
    val releaseResults: List<Release> = emptyList(),
    val trackResults: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val musicApi: MusicApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300.milliseconds) // Debounce the search query to avoid excessive API calls
                .distinctUntilChanged() // Only trigger search if the query has changed
                .collect { query ->
                    performSearch(query, _uiState.value.selectedTab)
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun onTabSelected(tab: SearchTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
        // Trigger a new search for the selected tab if the query is not empty
        if (_uiState.value.searchQuery.isNotEmpty()) {
            performSearch(_uiState.value.searchQuery, tab)
        }
    }

    private fun performSearch(query: String, tab: SearchTab) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                artistResults = emptyList(),
                releaseResults = emptyList(),
                trackResults = emptyList(),
                isLoading = false,
                error = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                when (tab) {
                    SearchTab.ARTISTS -> {
                        val artists = musicApi.searchArtists(
                            q = query,
                            limit = 20,
                            offset = 0
                        ) // Adjust limit/offset as needed
                        _uiState.value = _uiState.value.copy(artistResults = artists)
                    }

                    SearchTab.RELEASES -> {
                        val releases = musicApi.searchReleases(
                            q = query,
                            limit = 20,
                            offset = 0
                        ) // Adjust limit/offset as needed
                        _uiState.value = _uiState.value.copy(releaseResults = releases)
                    }

                    SearchTab.TRACKS -> {
                        val tracks = musicApi.searchTracks(
                            q = query,
                            limit = 20,
                            offset = 0
                        ) // Adjust limit/offset as needed
                        _uiState.value = _uiState.value.copy(trackResults = tracks)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    // You might want to add methods to handle item clicks if you want to navigate
    // fun onArtistClick(artistId: UUID) { /* ... */ }
    // fun onReleaseClick(releaseId: UUID) { /* ... */ }
    // fun onTrackClick(trackId: UUID) { /* ... */ }
}