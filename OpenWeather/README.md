# OpenWeather

This is a simple weather application for android learnner, who wants to learn basic android technologies. 

# Scenario
- Network is not connected : When user opens the weather application and network is not connected, this screen will be displayed

 ![rsz_11splash](https://user-images.githubusercontent.com/64088589/141047533-c587f809-ddd1-4e63-ada5-46c5d060435d.png)
 
   user need to check current status of network connection and press "Try Again" button.
 - If the application is run correctly, it will show main screen
 
 ![rsz_main](https://user-images.githubusercontent.com/64088589/141047547-db527427-533d-4146-ab4e-16e8f161e2be.png)
 
   at the top of this screen is current weather information, the bottom of it is weather hourly and weather daily information.
 - When user opens the application or make a refresh action, it will get weather data using OpenWeather api and update it to main screen.
 - User presses the search location icon at the top screen, The search location screen will be displayed, this location information will be used for updating
   weather data.
 - User presses the location icon at the top screen, this application will update weather data using user's current location. (it uses GPS data)
 - On the right of top screen is Setting menu, user can change some features of this application, example :  the temperature unit (Celsius or Fahrenheit),
   run forcegound service or enable auto update weather after a specific time
   
   ![rsz_menu](https://user-images.githubusercontent.com/64088589/141047619-e0d13fe1-b70f-4351-88ce-94c1d2bf5e72.png)
   
# Technologies and Libraries
  - MVVM architecture
  - Livedata
  - SQLite database
  - Retrofit
  - Dagger Hilt
  - View Pager2
  - Work Manager
  - Broadcast Receiver
  - Share Preference
  - ThreadPool
  - RecycleView
  - Foreground Service
  - Glide

# Supported Android version
Android Oreo (Android 8) or higher

# Video demo
https://www.youtube.com/watch?v=zADCqrJpuOw
