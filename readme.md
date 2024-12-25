# Fetch-Rewards-Coding-Exercise

The app fetches data from https://fetch-hiring.s3.amazonaws.com/hiring.json, filters and sorts it based on given criteria, and displays the result in a list.

## Requirements
- Fetch data from a remote API.
- Filter out items with blank or null names.
- Group items by `listId`. (Also added filtering by group, select groups to show)
- Sort items by `listId` and `name`.
- Display the final result in a scrollable list.

---

## Project Structure
The project follows the MVVM (Model-View-ViewModel) architecture and uses Jetpack Compose for UI development. Below is the folder structure:

```
src/
├── main/java/com/anbapps/fetchrewardscodingexercise/
   ├── data/
   │   ├── api/
   │   │   ├── ApiService.kt        // Retrofit API interface
   │   │   ├── ApiClient.kt         // Retrofit client configuration
   │   ├── model/
   │   │   ├── Item.kt              // Data model for API response
   │   ├── repository/
   │       ├── ItemRepository.kt    // Repository to manage data fetching and filtering
   │
   ├── di/
   │   ├── AppModule.kt             // Dependency injection setup with Hilt
   │
   ├── ui/
   │   ├── theme/
   │   │   ├── Theme.kt             // App-wide theme settings
   │   │   ├── Color.kt             // App colors
   │   │   ├── Typography.kt        // Typography settings
   │   ├── components/
   │   │   ├── ItemCard.kt          // Reusable composable to display an item
   │   ├── screens/
   │       ├── ItemListScreen.kt    // Screen to display the list of items
   │       ├── ItemListViewModel.kt // ViewModel for the ItemListScreen
   │
   │
   ├── MainActivity.kt              // Entry point of the app
   ├── FetchApplication.kt          // Custom Application class for Hilt
├──test/java/com/anbapps/fetchrewardscodingexercise
   ├──RepositoryTest.kt             //Unit test to verify data filtering logic is working as expected
```

---

## Libraries Used
- **Retrofit**: For making API requests.
- **Gson Converter**: For converting JSON responses into Kotlin data classes.
- **Hilt**: For dependency injection.
- **Jetpack Compose**: For building the user interface.
- **StateFlow**: For managing UI state.
- **Coroutines**: For asynchronous programming - fetching data asynchronously.

---


## How to Run the Project
1. Clone the repository:
    ```bash
    git clone https://github.com/ahmedbilal205/Fetch-Rewards-Coding-Exercise.git
    ```
2. Open the project in Android Studio.
3. Sync Gradle to install dependencies.
4. Run the app on an emulator or a physical device.

---
## License
This project is licensed under the MIT License. See the LICENSE file for more details.
