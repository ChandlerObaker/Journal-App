# Journal-App

Brief description of the project:

This project was to create an Android application that could allow users to write their thoughts. At my level, I designed it as a digital diary where people can easily write their thoughts (stories, mottos, words of encouragement, shopping reminders, etc.). And also to make the difference between this application and others, I added something called "mood". So far, there are 6 different moods in the application (happy, angry, love, sad, tired, sick). Thus, a user has the ability to specify how he feels when he writes his thought or what his new entry in the diary is related to the level of feelings. And every mood produces a single color, why? Just to make the application more colorful so that it can attract young people (14-22 years old)

Functionalities implemented:

1.	Login or register through a Google Account
2.	Sign out from the app
3.	Write and save a diary entry
4.	Consult and/or update a diary entry
5.	Delete a diary

Bonus implemented :

•	Splash screen :
I added a splash screen to make the app more convenient for users. Based on one they can find in famous social medias like Snapchat or Twitter

•	Firebase Realtime Database (plus Offline capabilities) :
In this architecture I related data by designing two branches (one for the users and one for the diaries). And in the diary branch, the first child is the user id (who own the diaries content in this child branch). Also enable offline capabilities for users to permit them to access data even when they go offline

•	Expresso UI tests :
I wrote some instrumentation tests for the MainActivity UI especially. You can find it in MainActivityInstrumentedTest.java 

•	Android Junit Runner :
I write some unit tests especially for my utilities class methods. For instance, one for the date format, or my own string wrapper method, etc. You can find it in UtilsUnitTest.java

I also used Lint Utility to ensure that the app code was well structured based on recommendations and good practices when it comes about android development
