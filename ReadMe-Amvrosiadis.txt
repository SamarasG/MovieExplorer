1. Overview

We created an Android app named MovieExplorer where users are able to view top and popular movies fetched from The Movie Database (TMDB) API. Users are able to view more information of a movie like the poster, description, and release date by tapping on a movie. Users can mark movies as favorites, and the movies get stored locally using SharedPreferences. Users can even remove any movie from favorites whenever they want.

2. Application Structure and Architecture

The application is structured in the MVC (Model-View-Controller) pattern.

Model: The folder contains data classes such as Movie and MovieResponse that mirror the data structure of the downloaded data.

The composition includes:

XML files that describe the front-end user interface.

Activity classes in the ui package such as:

MainActivity: Displays the movie list through the use of a RecyclerView along with an open favorites button.

DetailsActivity: Uses ScrollView and LinearLayout to display movie details like poster, title, rating, and description. Has an interface to switch favorite state.

FavoritesActivity: Shows all the saved favorite movies and updates upon adding or deleting movies.

Controller / Repository:

MovieRepository: Handles data fetching from the TMDB API with Retrofit and delivers it to the UI.

FavoritesRepository: Stores and fetches favorite movies to/from SharedPreferences. Stores data in JSON and fetches data asynchronously using apply().

3. API Integration

A retrofit is used in network operations for conducting activities such as:

Issuing HTTP requests

Handling mistakes

Serialization of JSON into Java objects using Gson

The Retrofit interfaces use the @GET annotation for mapping the API endpoints and the network calls are asynchronous. The base URL is set in RetrofitClient.

Glide is utilized to fetch and store images (the movie posters) from the TMDB API and display a placeholder image while fetching the original image.

4. Application Functionality

4.1 App Launch

When the app is launched, MainActivity checks for network connectivity using NetworkUtil. 20 movies in batch are downloaded from TMDB through MovieRepository, and these are bound in RecyclerView using MovieAdapter. Scrolling loads further results. Placeholder images display during loading.

4.2 Viewing Details

A movie click invokes an Intent to start DetailActivity, passing movie details through putExtra(). The new activity displays all information about the movie.

4.3 Adding/Removing Favorites

Here, the button to add/remove the movie to/from favorites can be clicked by the user. The movie details are stored or deleted from SharedPreferences by FavoritesRepository, which is in asynchronous mode.

4.4 Viewing Favorites

The user may then transition between MainActivity and FavoritesActivity, where the saved movie listings appear in a RecyclerView. Clicking any movie returns the user back into DetailActivity, where the button reflects the movie's existing favorite state.

4.5 SharedPreferences

Because SharedPreferences only support primitive types, we serialize the list of movies into a JSON string with Gson. We store this in a key (for instance, "favorites"). We deserialize and use it to refetch the list of movies in a persistent and efficient way whenever we need it.

5. External Libraries

Retrofit2: For HTTP Networking

Gson: For JSON serialization/des

Glide: Load and cache images

AppCompat: For backward compatibility

ConstraintLayout: For maintainable and flexible UI layout

6. Conclusion MovieExplorer showcases the usage of the basic Android concepts like activities, intents, RecyclerView, API calls, and local storage. Although we used SharedPreferences, Room would enhance the handling of the data through support for enhanced threading and query operations. The application is an efficient, well-structured example for a basic Android application.