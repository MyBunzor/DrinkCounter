# day 1
On the first day of the project, I created a GitHub repository, in which I made a README.md to write down the basics of my app idea. The idea is to create an app that registers how much a user has drunk in a certain period of time. In the app, different periods of time can be chosen to visualize the data in that period of time. 

# day 2
On day 2, I met up with my feedback team. We explained our ideas to eachother and gave feedback. The most important criticism of my app sofar is the dependance of the app on user-interaction: so, I came up with a solution to this, which is visualized in the design picture below. 

<img src="https://github.com/MyBunzor/DrinkCounter/blob/master/docs/DrinksDrunk%20DesignedPrototype.png" width="100%" height="100%"/>

I have decided to add a fourth activity, named the 'TrophyActivity', which enables users to unlock drinking challenges. Or, actually 'non-drinking' challenges: a user can unlock a trophy if he or she hasn't drank alcohol for a period of time (session, day, week, month, etc). It also opens up the possibility of extending the app to non-alcoholic drinks as well, so actual drinking challenges or challenges of another kind could be added. I'm not sure about this yet though, and will primarily focus on completing the datastructure with a nice library that provides visualizing the data.

# day 3
The third day I mainly focussed on creating the SQLite database and connecting it with the PlusActivity (so that when a user plusses a beer, it's also registered in the database). It wasn't long before I discovered I hadn't put much thinking in my data structure: instead of creating different model classes for drinks (each drink 1 model class), as I was planning on doing, I'll now create 1 model class 'Drink' with two attributes: the kind of drink (f.e. beer/wine) and the timestamp. 

<img src="https://github.com/MyBunzor/DrinksDrunk/blob/master/docs/DrinksDrunk%20drink%20class.png" width="10%" height="10%"/>

The left image shows my old idea. The attribute 'number of drinks', will be removed. It won't be necessary to register this in the Drink object, because each time a drink is plussed, a new object will be added to the database. More importantly, it's impossible to register timestamps if one drink is registered multiple times in one tablerow. The Drink-class now looks like the image on the right. 




