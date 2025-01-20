Description: Watchmode App is an Android application designed to provide users with a seamless platform to explore movies and TV shows. Powered by the Watchmode API, the app fetches real-time data to display list and details of movies and shows.

Features:

1. Titles Listing Screen:

->Displays a curated list of movies and TV shows fetched from the Watchmode API.
->Includes a toggle button or tabs to easily switch between Movies and TV Shows.
->Implements a shimmer effect for a visually appealing loading state.

2. Details Screen:

->Allows users to tap on any item to navigate to a detailed view.
->Displays comprehensive information, including the title, description, rating, release date, and poster image.
->Includes a shimmer effect during data loading for a smooth user experience.

3.API Integration:

->Leverages Retrofit for robust and efficient networking.
->Simultaneously fetches separate datasets for movies and TV shows using RxKotlin's Single.zip.

4.Error Handling:

->Gracefully handles errors like network issues and API failures, ensuring a smooth experience for users.

5.Modern Android Architecture:

->Built using the MVVM (Model-View-ViewModel) architecture for clean and maintainable code.
->Utilizes Dependency Injection with Hilt to simplify the management of dependencies.

Technology Stack:

->Frontend: Kotlin and Jetpack Compose for building intuitive and declarative UIs.
->Networking: Retrofit for API calls and RxKotlin for asynchronous programming.
->Image Loading: OkHTTP for downloading image and Coil library for loading the image to ui.
->Dependency Injection: Hilt.
->Architecture: MVVM.

This project showcases modern Android development practices and delivers a feature-rich, user-friendly app for discovering entertainment content.
