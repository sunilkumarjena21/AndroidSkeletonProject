# AndroidSkeleton
**V1.0.0**

AndroidSkeleton is an Android application Skeleton that can be used for android native development.This sample project contains useful folders and files which are not dependent on any project.
These files contains methods and classes which are common in any project and are required for better project management.

## applications

 - val : Generally used to give testing build to customer.
 - beta : BETA release process
 - prod : All real prod enviornment.
 - Mock : Target contains mock data.

# User Stories
-	[Android]Folder structure creation & setups
-	[Android]Raw generic files put in place
-	[Android]Utility files creation
-	[Android]Network layer implementation with Rx-Network bound layer
-	[Android]Auto build version management implementation
-	[Android]Build Variant setup with targets(develop,test,prod)
-	[Android]ViewModel layer implementation	Task
-	[AndroidReactive Layout operation and managing observables
-	[Android]Repository(Local DB) implementation
-	[Android]Micro setups on Data flow handling Operation by mock data
-	[Android]Create login and settings screen to display language localisation

# Versioning process

 - Versioning: [MAJOR].[MINOR].[PATCH].[RC]
 |                |TAG                          |EXEMPLE                     |
 |----------------|-------------------------------|-----------------------------|
 |VAL  |vX.X.X.VRCX  |V1.2.3.VRC4         |
 |BETA      |vX.X.X.BRCX   |v1.2.3.BRC4|
 |PRODUCTION    |vX.X.X       |v1.2.3|


# Technical stack

 - Kotlin
 - Android SDK 23+
 - Gradle 5.4.1

build specificity required, ie: lib by hand
## GIT conventions

 - Commit messages :
 please start the message by one of the following categories, in [squared brackets]:

 | Header | Purpose |
 |---|---|
 | CHORE | For local build and library changes |
 | CI | For CI and automatization |
 | DOCS | For documentation changes only |
 | FEAT | Adding a new feature to the app. Please append the ticket number between [squared brackets]. |
 | FIX | Fixing an existing bug. Please append the ticket number between [squared brackets]. |
 | PERF | Performance-related enhancements |
 | REFAC | Refactoring code only. No new feature allowed |
 | STYLE | Linting, Code Styling |
 | TEST | Changes on Unit of E2E Tests |
 | REL | Reserved for releases on master. |

 - Branches naming : feature/[FEATURE NAME]


## Architecture
- Rx
- MVVM / Repository pattern
- Dependency injection with Koin
- Database with Room
- Network with Retrofit
- Android x
- Databinding

# Licenses
-play-services
-crashlytics
-okhttp3:logging-interceptor
-jetbrains.kotlin

# Logging
-timber
-Stetho

## Language Localisation
- English --> en
- French --> fr
- Spanish --> es

## Folder Structure
- dao
- db
- errors
- model
- network
- respository
- screens
- services
- utils

## Loosely coupled modules
- dataSourceModule
- debugServicesModule
- viewModelsModule
- servicesModule
- daoModule
- repositoryModule
- utilsModule

## Data flow
![Alt text]("Data flow")

	
## Auto configuration options
-  lintOptions {
        lintConfig file("lint.xml")
    }

-   dataBinding {
        enabled = true
    }
-   buildTypes {
        debug {
            debuggable true
            minifyEnabled false
            shrinkResources false
        }
        release {
            debuggable false
            minifyEnabled true
            shrinkResources true
            // proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
	
	