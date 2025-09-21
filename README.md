# Dicoding Story App

This application is a project submission for the **"Belajar Aplikasi Android Intermediate"** course at Dicoding Academy. It is a story-sharing app where users can register, log in, view a feed of stories from other users, and upload their own stories with images and descriptions.

The project is built using modern Android development practices, including custom views, session management, and integration with a RESTful API.

## 📱 App Preview

Here is a glimpse of the application's user interface, based on the provided wireframes.

---

## ✨ Features

This application implements all the mandatory features from the submission criteria, along with several suggested features to achieve a higher score.

### Core Features

* **User Authentication**
    * **Register:** New users can sign up with a name, email, and password.
    * **Login:** Existing users can log in using their email and password.
    * **Session Management:** The app saves user sessions and auth tokens. Users who are already logged in are taken directly to the main screen.
    * **Logout:** Users can log out, which clears their session data and token.

* **Custom View with Validation**
    * A custom `EditText` component is used for the password field.
    * It provides an immediate error message if the password is less than 8 characters long.

* **Story Feed**
    * Displays a list of stories fetched from the API.
    * Each item in the list shows the user's name and the story's photo.
    * The latest story appears at the top of the list after being added.

* **Story Details**
    * Tapping on a story opens a detail screen showing the user's name, photo, and full description.

* **Add New Story**
    * Users can create a new story by uploading an image and adding a description.
    * Images can be selected from the device **Gallery**.
    * Upon successful upload, the user is returned to the main story feed, which is refreshed to show the new story.

* **Animations**
    * The app incorporates animations to enhance user experience, such as Property Animation, Motion Animation, or Shared Element transitions.

### Implemented Suggestions (For Higher Grade)

* **Enhanced Validation:** The custom `EditText` also validates email format in real-time.
* **Camera Support:** Users can choose to take a new picture with the **Camera** when adding a story.
* **Robust API Interaction:** The app provides user feedback for all network operations, including **loading indicators** and **error messages**.
* **Proper App Flow:** Implemented correct back stack management to prevent users from navigating back to login/register pages after being authenticated.
* **Clean Code & Architecture:** The project follows clean code principles and properly implements Android Architecture Components like `ViewModel` and `LiveData`.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Kotlin
* **Architecture:** Model-View-ViewModel (MVVM) & Repository Pattern
* **Asynchronous:** Kotlin Coroutines
* **Dependency Injection:** Using Hilt or a manual Injection class.
* **Networking:** Retrofit 2 & OkHttp3 (for auth interceptor)
* **Data Persistence:** Jetpack DataStore for session management.
* **UI Components:** RecyclerView, Custom Views.
* **API:** Dicoding Story API

---

## 🚀 How to Run

1.  Clone this repository:
    ```bash
    git clone [https://github.com/reisyaprtma/StoryApp-Mobile.git](https://github.com/reisyaprtma/StoryApp-Mobile.git)
    ```
2.  Open the project in Android Studio.
3.  Build the project and run it on an emulator or a physical device.
