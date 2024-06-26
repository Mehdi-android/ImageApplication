# Images

The App uses a set of Android Jetpack libraries plus Retrofit to dispay data from REST API
. The App uses Kotlin.

### Prerequisites

The project has all required dependencies in the gradle files. Add the Project to Android Studio or
Intelij and build.All the required dependencies will be downloaded and installed.

## Architecture

The project uses MVVM architecture pattern.

## Libraries

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Manage UI
  related data in a lifecycle conscious way and act as a channel between use cases and ui
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) - support library that
  allows binding of UI components in layouts to data sources,binds character details and search
  results to UI
* [Dagger 2](https://dagger.dev/dev-guide/) - For Dependency Injection.
* [Retrofit](https://square.github.io/retrofit/) - To access the Rest Api
