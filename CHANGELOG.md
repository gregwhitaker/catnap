Changelog
===
All notable changes to this project will be documented in this file.

##1.0.0
###Added

* Initial Release

##2.0.0
###Added

* More documentation
* Deployed artifacts to JCenter

###Changed

* Removed support for partial XML responses (It wasn't super useful and caused a bunch of problems)
* Moved the build process to Gradle

###Fixed

* List items not rendering when subsequent calls made to same endpoint using different filter criteria

##2.2.0
###Added

* Added support for auto-detecting ObjectMapper in SpringBoot applications.
* Added support for auto-detecting ObjectMapper in SpringMVC applications.

###Changed

* Updated to Gradle 4.1
