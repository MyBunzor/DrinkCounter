# Design Document 
In the image blow, the homescreen (PlusActivity), TimeActivity and the GraphActivity are displayed. The core functionality is placed in the homescreen, where the user can plus a drink. When clicking on the data-button, the TimeActivity is loaded. Here, different timeperiods for the data to be shown in can be chosen. When one of the timeperiods in the second activity is clicked, the GraphActivity is loaded with the graph and specific time. The Trophies-button can be used to see which trophies the user has unlocked and which are still left to complete.

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20Design%20Prototype.png" width="65%" height="50%"/>

The cornerstone of this app will be a database of some sort, presumably a SQLite database. This database will be stored in a java file as well.  When the plus button next to a drink is clicked, this drink will get + 1 in the table - each drink having it's own row. With the plus, a timestamp is added as well. The timestamp enables the ability to show different timeperiods in which the alcohol is consumed (or isn't!). Each drink will have the attributes and functions presented in the class below: 

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20drink%20class.png" width="15%" height="15%"/>

The number of drinks already had will be displayed next to the drink. The set- and get function will be connected to the SQLite table. The get function will be used in the third activity, to show data for a certain period; the set function will be used when a user adds a drink (or substracts one, if a mistake is made). 
