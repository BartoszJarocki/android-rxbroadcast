# RxBroadcast

## Setup
Here are the basic rules to set up RxBroadcast for Android

####Step 1: Repository
Add it in your root build.gradle at the end of repositories:

```groovy  
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
####Step 2: Dependencies
Add the dependency

```groovy  
dependencies {
	compile 'com.github.BartoszJarocki:RxBroadcast:1.0.0'
}
```
