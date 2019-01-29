# day 1
On the first day of the project, I created a GitHub repository, in which I made a README.md to write down the basics of my app idea. The idea is to create an app that registers how much a user has drunk in a certain period of time. In the app, different periods of time can be chosen to visualize the data in that period of time. 

# day 2
On day 2, I met up with my feedback team. We explained our ideas to eachother and gave feedback. The most important criticism of my app sofar is the dependance of the app on user-interaction: so, I came up with a solution to this, which is visualized in the design picture below. 

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20DesignedPrototype.png" width="100%" height="100%"/>

I have decided to add a fourth activity, named the 'TrophyActivity', which enables users to unlock drinking challenges. Or, actually 'non-drinking' challenges: a user can unlock a trophy if he or she hasn't drank alcohol for a period of time (session, day, week, month, etc). It also opens up the possibility of extending the app to non-alcoholic drinks as well, so actual drinking challenges or challenges of another kind could be added. I'm not sure about this yet though, and will primarily focus on completing the datastructure with a nice library that provides visualizing the data.

# day 3
The third day I mainly focussed on creating the SQLite database and connecting it with the PlusActivity (so that when a user plusses a beer, it's also registered in the database). It wasn't long before I discovered I hadn't put much thinking in my data structure: instead of creating different model classes for drinks (each drink 1 model class), as I was planning on doing, I'll now create 1 model class 'Drink' with two attributes: the kind of drink (f.e. beer/wine) and the timestamp. 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20drink%20class.png" width="20%" height="20%"/> <img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20class%20new%20blueprint.png" width="20%" height="20%"/>  

The left image shows my old idea. The attribute 'number of drinks', will be removed. It won't be necessary to register this in the Drink object, because each time a drink is plussed, a new object will be added to the database. More importantly, it's impossible to register timestamps if one drink is registered multiple times in one tablerow. The Drink-class now looks like the image on the right. 

Furthermore, I created layouts for the activities. These will be updated, because they're quite sober and boring now, but, my first priority overall is making the basic app idea function properly, before adding better layouts. 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20Homeactivity%20firstlayout.png" width="20%" height="20%"/> <img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20TimeActivity%20firstlayout.png" width="20%" height="20%"/>

# day 4
Today I decided to add another activity: the HistoryActivity. It's an activity that the user can access via a button on the homescreen. The new PlusActivity now looks like this: 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20Homeactivity%20secondlayout.png" width="20%" height="20%"/>

In the HistoryActivity all the inputted drinks are visible, with the moment they were added as well. It's not only practical for the user, but also useful to gain more insight in the way the SQLite database behaves. Wheter a drink is added or deleted, what the timestamp looks like or how many drinks should be visualized in a certain period: it's now visible in this activity.

# day 5
The database is finished, more or less. The user can add drinks, view them in the HistoryActivity that I made yesterday and undo the last input, in case of a mistake. Adding and deleting a drink activates a toast message, so the user's ensured that his action actually took place: 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20Homeactivity%20toastmessage.png" width="20%" height="20%"/>

At the moment of adding a drink, other add buttons are disabled (as can be seen) for 5 seconds, to ensure the user doesn't add more drinks at once by accident. It's unnecessary to do so, and therefor only mistakes could be made if they were enabled all the time. One last flaw concerning the input, is that users can't add drinks afterwards, in case their phone wasn't with them or empty. That's why it might be a good idea to implement a function / activity where users can add drinks an manually input time. This has to be given more thought before it'll be added to the app.

Now that the basic navigation and the database are in place, a library with a visualization needs to be added. I´m not sure yet in what way the data can be visualized best: 
- by a bar graph;
- by a line graph.

A line graph is probably more attractive, certainly for visualizations of long periods of time, but it's not suited for data of only one evening (f.e. if only 3 drinks were drunk by user). If, however, a visualization of a longer period is made, then the distribution of the consumption within that period wouldn't be quite clear. For now I'll focus on getting the bar chart first, because it's probably easier to implement.

# day 6
Today started out with a stand-up in our group, where each of us explained our progress. Feedback and critic was given by Marijn, who brought up the idea of creating an extra database, to put drinks in that were drunk in a session. It made me think, but first I wanted to try to keep all data in one database and separate drinks based on their timestamp. I made progress:
- Users are now able to record how much they drank in a session, as well as to see how many they've already had mid-session;
- The application remembers important data, even when it's shut down by the user (SharedPreferences);
- The BETWEEN function in SQL works properly.

The next few days are therefor needed to implement the ability to show how many drinks were drunk in a week / month / and year. If that's finished, the basic functionality of the app is ready. Only the visualization then is left.

Because a screenshot of the HistoryActivity wasn't displayed yet, here it is:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20HistoryActivity.png" width="20%" height="20%"/>

# day 7
Not much progress was made this day. The SQLite Database isn't fully functioning to its design yet.

Because the process was stuck there, I decided to test out the library for charts I found. That, however, wasn't much of a success either.

That's why I updated the design figure:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20Design%20Prototype%20update.png" width="100%" height="100%"/>

# day 8
The 8th day was more productive than the 7th, luckily. The application is now able to register how many alcohol consumptions and what kind were drunk in a session, week, month, and year. A library to visualize this information was added as well. A somewhat dynamic connection between the database and the library has already been made: the amount of beers in a session is already visible, as can be seen below:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20GraphActivity%20beers.png" width="20%" height="20%"/>

It's still partially hardcoded (the Query to get the amount of beers in a session) and ideally should be more dynamic. That's a task for tomorrow.

# day 9
Each timeperiod can be visualized and has a title with it as well. The index of the y-axis has been improved, now only showing integers. For the yearly and monthly visualizations, however, the index ideally should be relative to size of the consumption. Right now, it's hardcoded. Here's how the weekly consumption now looks:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20weekly%20visualization.png" width="20%" height="20%"/>

The database and the library are now functioning sufficiently. Next up is the TrophyActivity: a gridview has been added and the constraintlayout has been changed to a linear layout.

# day 10
Today the basic trophies where set up. A trophy class and adapter were made, images were downloaded and objects instantiated. Right now, four trophies are added to the gridview of the TrophyActivity:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20TrophyActivity.png" width="20%" height="20%"/>

The user can, as can be seen above, already achieve the first trophy. The weekly, monthly and yearly achievements still need to be implemented with the database. When a trophy is achieved, colour is added.

# day 11
A second database is made where all trophies are stored. Its columns are the attributes of the Trophy class. Each row represens another trophy. It didn't seem possible to permanently save the 'achieved field' of instances of the Trophy class: now, if a trophy is being achieved, it's registered in the database. This change remains. The Trophy class now seems irrelevant: it won't be deleted yet, however. Besides permanently saving whether a trophy was achieved or not, it can now also be registered how many times a trophy is achieved.

# day 12
The new trophy database isn't functioning flawless yet. It's still a little buggy (sometimes crashes for no reason) and (it seems randomly) sometimes shows trophies that aren't achieved yet. Here's what the Trophy Activity looks like when two trophies are achieved:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20TrophyActivity2.png" width="20%" height="20%"/>

When the database is functioning properly (that'll be one task for day 13), these to do's still need to be implemented this week:
- A way the user can manually input drinks (in case a phonebattery is empty or a phone is forgotten;
- Push notifications (based on the amount of drinks that are drunk / trophies that are almost achieved).

# day 13
Today an activity where the user add a drink and manually input a date and time as well was added to the application. Here's how it looks:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20InputActivity.png" width="20%" height="20%"/>

The background isn't definite yet. The layout still has to be improved: I'm only experimenting with colours at this point.

An improvement for this activity would be to show what datetime the user has chosen. Otherwise, it can easily be forgotten, or the user has to check in the HistoryActivity, which isn't user-friendly.

# day 14
Push notifications have been added to the application. When a user adds a drink, he's being reminded not to drink and drive:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20PushNotification.png" width="20%" height="20%"/>

More push notifications, however, should be added:
- When a user has achieved a trophy, a notification should pop up that is also clickable and will lead user to TrophyActivity;
- When a trophy is almost achieved (week, month, three days?);

The database is improved as well. Instead of an update statement with SQL, a ContentValues variable is now being used to update the table.

# day 15
The InputActivity has been altered: the user can now see what date and time he chose.

The PlusActivity needs some changes as well. The add buttons for drinks there where quite small, so I've come up with a new design. This isn't final yet though.

# day 16
A new layout has finally been implemented. Different colors are added, as wel as other buttons. Here's how it looks now:

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20NewPlusActivity.png" width="20%" height="20%"/> <img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20NewTimeActivity.png" width="20%" height="20%"/> <img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20NewInputActivity.png" width="20%" height="20%"/>

The PlusActivity was to crowded with different buttons, so the user has to click the Bar Graph image to get to the data page. Here, the user can choose to view data, history or their trophies.





