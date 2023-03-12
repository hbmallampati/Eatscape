# Eatscape
The Android app helps users find restaurants based on their location and/or cuisine selection. Users can also fetch the top N restaurants based on their preferences. The app uses the Yelp database in the backend and follows the MVVM architecture pattern. SQLite is used for local storage, and Jetpack components are used for easy development and maintenance.

### Features
* Location-based restaurant search
* Cuisine-based restaurant search
* Top N restaurant search
* Display of restaurant details such as - restaurant contact number, restaurant website link, expected price range, address
* Filter search results based on various criteria

### Technology Stack
* MVVM architecture pattern
* Yelp API for restaurant data
* SQLite for local storage
* Jetpack components such as LiveData, ViewModel, and Room for easier development and maintenance

### Functionality
App loads with selection options for user. The user can set location, can pick any cuisine if he like, limit the search results to sone integer number n, if want to can sort the results in ascending or descending order of prices.
<p align="center">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943696-07c8d785-9824-46f2-a0dc-20102d7621b9.png">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943698-71de3b5b-8094-462f-9839-d4d524f83c9a.png">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943704-45989055-acab-4a4d-ab2e-8fb74b0941de.png">
 </p>

On hitting Go the app run SQL queries on SQLiteDatabase, fetches query results and displays them to the user.
<p align="center">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943708-8d3d0cae-9f57-4c45-8db1-988849a28f17.png">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943713-a97bdfa2-0dab-41ef-a6af-67298fe2d854.png">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943718-a193b3cd-656e-4b9e-af25-18d8c04f73af.png">
  <img width="250" height="450" alt="image" src="https://user-images.githubusercontent.com/98439391/213943720-e50fbf10-9771-4c2d-98ae-b414ff51ce76.png">
 </p>
