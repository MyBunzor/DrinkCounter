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

In the activity, all the inputted drinks are visible, with the moment they were added as well. It's not only practical for the user, but also useful to gain more insight in the way the SQLite database behaves. Wheter a drink is added or deleted, what the timestamp looks like or how many drinks should be visualized in a certain period: it's now visible in this activity. 

# day 5
The database is finished, more or less. The user can add drinks, view them in the HistoryActivity that I made yesterday and undo the last input, in case of a mistake. Adding and deleting a drink activates a toast message, so the user's ensured that his action actually took place: 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20Homeactivity%20toastmessage.png" width="20%" height="20%"/>

At the moment of adding a drink, other add buttons are disabled (as can be seen) for 5 seconds, to ensure the user doesn't add more drinks at once by accident. It's unnecessary to do so, and therefor only mistakes could be made if they were enabled all the time. One last flaw concerning the input, is that users can't add drinks afterwards, in case their phone wasn't with them or empty. That's why it might be a good idea to implement a function / activity where users can add drinks an manually input time. This has to be given more thought before it'll be added to the app.

Now that the basic navigation and the database are in place, a library with a visualization needs to be added. I´m not sure yet in what way the data can be visualized best: 
- by a bar graph;
- by a line graph.

A line graph is probably more attractive, certainly for visualizations of long periods of time, but it's not suited for data of only one evening (f.e. if only 3 drinks were drunk by user). If, however, a visualization of a longer period is made, then the distribution of the consumption within that period wouldn't be quite clear. For now I'll focus on getting the bar chart first, because it's probably easier to implement.








