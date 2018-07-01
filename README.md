# Journal-App
Brief description of the project:

This project was about to create an android app which can permit users to write theirs diaries. 
At my level I conceive it like a numeric intima journal where people can easily write their thoughts (stories, moto, etc.). And also to succeed to make a difference between my app and the other ones I add something called mood. Up to now, there is 6 different moods in the app (happy, angry, love, sad, tired, sick). So a user has the possibility to specify how he feels when writing his thought or what his new diary entry is related to at the level of feelings. And each mood produce a single colour, why? Just to make the app more colourful so that it can succeed to attract young people (14-22 years)

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
We wrote some instrumentation tests for the MainActivity UI especially. You can find it in MainActivityInstrumentedTest.java 

•	Android Junit Runner :
I write some unit tests especially for my utilities class methods. For instance, one for the date format, or my own string wrapper method, etc. You can find it in UtilsUnitTest.java
