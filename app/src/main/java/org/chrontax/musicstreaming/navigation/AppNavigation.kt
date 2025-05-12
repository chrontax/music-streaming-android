package org.chrontax.musicstreaming.navigation

object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val SETTINGS_ROUTE = "settings"
    const val SEARCH_ROUTE = "search"

    // Define the base route for the release screen
    const val RELEASE_BASE_ROUTE = "release"

    // Define the argument key for the release ID
    const val RELEASE_ID_ARG = "releaseId"

    // Define the full route with the ID parameter
    const val RELEASE_ROUTE = "$RELEASE_BASE_ROUTE/{$RELEASE_ID_ARG}"
}