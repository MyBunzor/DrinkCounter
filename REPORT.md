# Report

### Description
The DrinksDrunk applications helps users to register their consumption of alcohol. They can add different drinks to their own database, which is visiualized with a bar graph. Trophies can be achieved when users drink little or nothing over longer periods of time. Here's what the home activity looks like: 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20NewPlusActivity.png" width="20%" height="20%"/>

### Technical design
The functionality of this app is supported by two datatables, that are created and stored in the DrinksDatabase.java file: one to register the drinks, another to register trophies. The navigation works as illustrated below: 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20TechnicalDesign.png" width="100%" height="100%"/>

The user has several options in the home activity of the application, or 'PlusActivity'. The drink icons represent six drinks and add these drinks to the drinks table when they are tapped on. When the hand icon is clicked, the user is led to the InputActivity, where a drink can be added with a date of choice. The session switch in the lower-left corner is there to enable the user to register drinks in a chosen period of time, for example at a party. The red arrow functions as an undo-button, and finally, the bar graph icon in the lower-right corner leads the user to the TimeActivity.

Once more the user has multiple choices. Four timeperiods can be selected that show data in a bar graph within that timeperiod in the GraphActivity. This visualization (AnyChart) uses the SQLite drinks table. Touching the History-button in the TimeActivity directs us to the HistoryActivity, where in a listview, supported by the DrinkAdapter in the DrinkAdapter.java file, all added drinks with a timestamp can be seen. Finally, the Trophy-button in the TimeActivity guides the user to the TrophyActivity, where once again the SQLite drinks table is being used to determine what trophies are achieved, and what trophies aren't. The achieved-status of trophies is registerd in the SQLite trophies table, that on it's turn is being used by the TrophyAdapter to decide to color (achieved) trophies or not.

### Challenges
Changes
Why: 
Ideal world: 




