# Design Document 
In the image blow, the homescreen and the choosescreen are displayed. The core functionality is placed in the homescreen, where the user
can plus a drink. When clicking on the data-button, the choose-screen is loaded. Here, differend timeperiods for the data to be shown in
can be chosen.

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20DesignDoc.png" width="40%" height="40%"/>

The cornerstone of this app will be a database of some sort, presumably a SQLite database. When the plus button next to a drink is clicked, 
this drink will get + 1 in the table - each drink having it's own row. With the plus, a timestamp is added as well. The timestamp enables the ability to show different timeperiods in which the alcohol is consumed (or isn't!). Each drink will have the attributes and functions presented in the class below: 

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20drink%20class.png" width="15%" height="15%"/>

The number of drinks already had will be displayed next to the drink. The set- and get function will be connected with the SQLite table. The get function will be used in the third activity, to show data for a certain period; the set function will be used when a user adds a drink (or substracts one, if a mistake is made). 
