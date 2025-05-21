1. Overview

We have made MovieExplorer , an Android application that  users can use to browse popular movies that are
    retrieved from The Movie Database API. The application user can also view detailed information about
    the movie by clicking on the movies icon.
    Furthermore the user can add a movie to their favourites list , that is stored locally using
    SharedPreferences, and ofcuurse  , the user can take a movie that is in their favourites list,
    out of it.

2. The Application Architecture & Structure

The app follows an MVC (model, viewer , controller) architecture:

The Model: It Holds the data classes (Movie, MovieResponse)

View: The view is broken in two distinct parts.
    The front-end part that is in the  XML layouts,
    that holds the frontend ui.
    The other part is the backend Activities files located in the ui package,
    these are :
    The MainActivity that Layout includes a RecyclerView for displaying a list of movies
    and a button to access Favorites.
    The DetailActivity that uses ScrollView and LinearLayout that is used to display the poster,
    the title and the release date of the movie , and also the movies rating,
    and a detailed description. In addition  inside this DetailActivity the user
    will find a button to manage the favorites list.
    The FavoritesActivity has a RecyclerView for displaying a list of saved favorite movies
    that is updated on reuse so that when a movie is taken out of the favourites list, it will
    immediately be taken out of the recyclerview.

Controller / Repository:

MovieRepository: This is the data manager file that is used for retrieving movies from the TMDB API.
It makes HTTP requests via Retrofit, and then it  processes the response, and returns the data to the UI
via callback functions.

FavoritesRepository: This file is responsible for storing and retrieving favorite movies locally
using SharedPreferences. It serializes the movie objects into Json and also manages the persistent
favorite list using th apply() function instead of the commit() function to make sure the actions are taken asynchronously
and so the application will not crash.

3. API integration:

Retrofit is an HTTP client for Android. we used it because without we would have to
    Manually write HTTP code.
    Manually handle errors.
    Manually convert JSON to objects.

    We use it  map API endpoints to interface methods using annotations like @GET,
    and also to handle network calls asynchronously.

    The TMDB API base URL is set in the RetrofitClient.
    From there JSON responses are automatically parsed using Gson
    into Java model classes as seen in the MovieResponse model.

Glide:
The Images are loaded using Glide:

    Glide is an image loading and caching library.
    It's used to download and cache the movie poster images from TMDB.
    Using Glide we have loaded Poster URLs with .load(url).into(imageView).
    The Placeholder image is displayed while loading the actual image. This placeholder image can be found
    in the res/drawable folder.


4. How It Works:
This following part is a Step-by-Step Guide of the functionality of the application.

4.1 On App Launch

The MainActivity starts running.
Before the onCreate function is activated to make the landing page,
the Network connection is verified using NetworkUtil.
From there the MovieRepository fetches 20 popular movies using the TMDB API.
The MovieAdapter binds this movie list to the RecyclerView and if the user scrolls down past
the initially fetched movie list, the next part is fetched in the same manner.
In the delay between scrolling further down and the next parts being fetched, the placeholder image
can bee seen, that is then replaced by the movie image.

4.2 When a movie icon is clicked

When the User clicks on a movie card.
Intent launches DetailActivity with Movie the data passed via the putExtra() function.
Then the screen changes to display DetailActivity.
There the new screen displays the movie info and the movies poster.

4.3 Add or Remove Favorites

Inside the DetailActivity layout exists the Button that can toggle favorites.
The FavoritesRepository is used to serialize the movie object and then saves/removes
it in SharedPreferences asynchronously so that the application will not crash. These movies that
are in the favourites list are stored locally.

4.4 Accessing the  Favorites list

From MainActivity the user can click on the favourites button in order to
open the FavoritesActivity, where all the movies tha have been marked as favourite in the Details Activity
can be seen.
To do this, the FavoritesRepository retrieves the saved Movie list from SharedPreferences.
In order to display the list of movies  RecyclerView is called to manage the list, so that it will
correctly be displayed .
If a user clicks on a favorite movie, this will take the user back to DetailActivity.
Since a movie is already in the favourites list, the button will now display
the message : remove from favourites. If clicked the movie is removed.

4.5 SharedPreferences
SharedPreferences is used in the android environment to save primatives like Strings and boolean values
and numbers. In order for us to make use of SharedPreferences we serialize the movie list object into
a Json string and we save this list under a key. in the case of favourites this is favourites.
Serialized Movie list saved as JSON string. When the favourites screen is opened this list is
called and is then deserialized back in to an object using Gson. Then we are able to
show that list in your RecyclerView.

This allows us to have fast and persistent local storage that is adequate for the
scale of the application.

5. External Libraries
5.1 Retrofit2
5.2 Gson
5.3 Glide
5.4 AppCompat
5.5 ConstraintLayout
This external library is used so that ui design is simplified.
It allows for a layout system to be used, so that elements of th ui can be
positioned  relative to each other. This made it easier to not have ui elements on top of
each other.

6. Conclusion

In our MovieExplorer application we have demonstrated some core Android concepts
such as activities, intents,
RecyclerView, REST API , and local data persistence, although this part was done using SharedPreferences
instead of Room, although this would allow for separation of concerns on the thread level and querying
and filtering of the database objects that we would have had to have created.
MovieExplorer offers a clean structure for a small sized Android applications.

