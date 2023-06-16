# C23-PS048 Android App
Capstone project Bangkit 2023


## About Our App


A personal gardening assistant which can help people with common knowledge to start gardening like a professional

## How To Make This Android App Project

### How To Build This Project

If you build this application an error will occur. Because this application requires an API Key to display the Google Map. Follow this tutorial to generate Google map api key
>[Set up in Cloud Console](https://developers.google.com/maps/documentation/android-sdk/start#set_up_in_cloud_console) (Note: ignore the other steps from this link)

Once you have the api key, follow these steps:
* In your Project Folder, Open local.properties file
* Inside your local properties, set MAPS_KEY variable
```
MAPS_KEY = your-api-key
```


### Libraries We Use

| Library name  | Usages        | Dependency    |
| ------------- | ------------- | ------------- |
| [Retrofit2](https://square.github.io/retrofit/) | Request API and convert json response into an object | implementation "com.squareup.retrofit2:retrofit:2.9.0" <br> implementation "com.squareup.retrofit2:converter-gson:2.9.0" |
| [OkHttp](https://square.github.io/okhttp/) |Data Request to API server|  implementation "com.squareup.okhttp3:logging-interceptor:4.9.3"|
| [CameraX](https://developer.android.com/training/camerax) |Camera Provider For the Application|implementation "androidx.camera:camera-camera2:1.2.3"<br> implementation "androidx.camera:camera-lifecycle:1.2.3"<br> implementation "androidx.camera:camera-view:1.3.0-alpha07"|
| [Coil](https://coil-kt.github.io/coil/) |Image Loader | implementation "io.coil-kt:coil-compose:2.4.0"|
|[DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAjwnZaVBhA6EiwAVVyv9JJDrHZ0zpyjRp2mCoKIKH2ijLF49ZQpVqUuvUv9E7FziCj7pSo6jRoCkfAQAvD_BwE&gclsrc=aw.ds)| Local Storage|implementation "androidx.datastore:datastore-preferences:1.0.0"|
|[Tflite](https://www.tensorflow.org/lite)| Machine Learning Processing|implementation 'org.tensorflow:tensorflow-lite:2.7.0'|
| [Maps](https://developers.google.com/maps) | Google maps service | implementation 'com.google.android.gms:play-services-maps:18.1.0'|

