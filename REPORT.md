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
The first change I made with regards to my most complete idea at the start of the developing process is adding trophies to the application. The original idea was without achievements: however, I received critiques and feedback from technical assistants and other students as well implying DrinksDrunk didn't have enough userinteraction. That is, the app doesn't motivate the user enough to use. With the help of trophies and notifications this problem is tackled.

Other improvements in the form of additions to DrinksDrunk were creating the InputActivity, the extra SQLite table for trohpies and the HistoryActivity, where users can see at what time they added each drink. This came in handy for myself as well, because I now could see clearly how the drinks table was structured and if each drink was added properly. Creating a second table provideded more oversight and makes it easier to add extra features in the future, or changing attributes of trophies, instead of creating a seperate Trophy class and initialising extra objects. The InputActivity was added because otherwise users couldn't register drinks at the right date if their phone would run empty or was lost.

A third change, or maybe choice, I made to the applicatin was the bar graph. One of the biggest challenges of building DrinksDrunk was making the connection to the Database fully functional. Because this wasn't working smoothly (and proceeded to be tough to work with in the following weeks), I decided to lower the bar (pun) and replace my idea of a line graph with a bar graph. It was simply easier to implement and make fully functional this way. 

Most of the big changes mentioned above were additions to my original design. I made them to create more user-interaction, improve a dynamic connection between the application and it's datatable(s) and to give the user the extra option to add drinks 'in hindsight'. My struggle with the SQLite database however meant that the data visualization was going to be less spectacular. In my original design, I thought of a line graph because it wouldn't just show how much a user drank, but also show decline or ascent in consumption. Aside from that, it would when in a timeperiod the user consumed alcohol. This negative effect of the tradeoff, nonetheless, is in part taken care of by the HistoryActivity, where the user can see when drinks where drunk.  

In an ideal world, I would've had more time to spend on improving the interaction between the database and the application. Then maybe it would be possible to add extra features to the data visualization, such as a line graph or other types of charts. It also would have enabled the possibility of adding more trophies, better notifications and something funny like a gif when you click on an achieved trophy. Although that would have been nice, the product as it is now is what I had in mind in the beginning, plus some extra features such as a detailed history, trophies and retroactively inputting data.



