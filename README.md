# AppMeteo

This is a weather forecast application. It is composed of two different parts : a GUI and a CLI. <br>
This application uses data from the API https://openweathermap.org/api <br>
<br>
Authors :
<ul>
    <li>Nicolas Luciani</li>
    <li>Yoan Bouniard</li>
    <li>Théo Martinet</li>
    <li>Cody Clop</li>
</ul>
Librairies :

Only GSON

## CLI

### How to use it

To run the CLI, use <b>gradlew runCLI</b> <br>


You will then enter a Weather Session, type 'help' to know what you can do ! <br>

Basically, you can type 'fav' to enter a Favourite Session or 'weather' to enter a Weather Session.

'quit' to exit the application

#### -- Weather Session

'Your_City dd/MM/yyyy' to ask for the weather at a specific date<br>
The date must be between the next seven days. If you don't specify it, current weather will be displayed.


If two cities have the same name but are in two different countries, you can distinguish them by using their country code. <br>
For example, if you want to know the weather in the United State's Paris, you can enter 'Paris US'. <br>
Here is a detailed list of country codes : https://www.countrycode.org/ <br>

If two cities have the same name and are in the same country, you can distinguish them by using their ZIP Code. <br>
For example, in France there are two cities called Canapville, you can type '61120 FR' to know the weather in Orne's
Canapville or '14800 FR' to know the weather in Calvados'.<br>

You can also specify various options like '-precise' to learn about humidity, pressure and such. Type 'help' to know all
the options.

'fav' to enter a Favourite Session

'quit' to return to the Main Session

<br>

#### -- Favourite Session

'list' to see your favourites list, each of your favourite city has an index that will be displayed<br>

'getw index' to know the weather of your selected favourite city, you can specify options just like in a WeatherSession<br>
If no index is specified, the weather from all your favourites will be displayed.<br>

'add Your_City' to add a city to your favourites list<br>

'del city_index' to delete a city from your favourites list

'weather' to enter a Weather Session

'quit' to return to the Main Session

<br>

### How it works

#### -- Session management

When launched, the AppMeteoCLI class will instantiate a WeatherSession object and launch it.
A session is built from a User Object that possess two attributes : <br>
- A FavouriteList attribute
- A UserQuery attribute (for commandLine fixing / modification)

While it is running, a session will wait for an user input, treat it, display information. Finally, it will wait for the next query (unless the query
was 'quit'). <br>
The main purpose of this class is to have a clear division of the main tasks that can be done through this app.
Though, any other session query can be treated by calling the command *'session_type' + 'command'*.

#### -- User input recognition

Basically, the user input is inspired by UNIX command prompt. Each word (command, options) is separated by spaces.
This input is stored in a UserQuery property to be split and each entry is read and formatted by fixCommandLine method
to prepare treatment. <br>

Since spaces in city names are replaced by underscores, those are formatted normally (with spaces).
If the input contains date, this date is verified (format, and validity). Also, it verifies presence and validity of
what we called Selectors : ZIP codes and Country codes. Finally, those data are stored in instance properties.

#### -- Data retrieval from API

The class APIQuery is used for this.

This class is composed of static methods that create a query for the OpenWeatherMap API. <br>

The arguments for these methods are either :
- city's name
- city's name + country code
- city's ID
- ZIP Code + country code
- coordinates

They all return a String containing weather Data in a JSON format.

<br>

#### -- Weather information initialisation

Once data is retrieved, the String returned is passed to City's constructor to initialise city's information as well as its weather.

City's constructor analyses the JSON with the Gson library and initialise its attributes with collected data. <br>
Three of these attributes are especially important as they contain weather forecast's information :

- weatherNow (HourWeather) contains current weather's information
- weatherPerHour (ArrayList\<HourWeather\>) contains weather forecast for the next 48 hours, hour per hour
- weatherPerDay (ArrayList\<DayWeather\>) contains weather forecast for the next seven days including today's weather, day per day

A HourWeather object contains weather prediction for one specific hour<br>
A DayWeather object contains weather prediction for the whole day

Use examples : <br>
*(City) city.getWeatherNow().getWindSpeed()* returns current wind speed in city's location<br>
*(City) city.getWeatherPerHour.get(5).getTemp()* returns temperature in city's location, 6 hours from now<br>
*(City) city.getWeatherPerDay.get(3).getTempFeelsLikeNight()* returns temperature in city's location, 3 days from now during night

<br>

#### -- Weather Information display

Once data was transmitted to the city and weather objects, the session treating the query will print all informations through
the processing of treatQuery function.


#### -- Favourites management

The favourites are stored into a .txt file that is named "favourite.txt". When launching a Session, it's User attribute
initialise his Favourite list by searching for this file and reading it (constructing it otherwise).
<br>While the app is launched, the user can add, delete, list or query the weather of his favourites.


## GUI


### How to use it

When launching the GUI app with the command 'gradlew run', you will directly enter into the main scene, where you will be able
to do your first query thanks to the search bar, in the top right corner.  The search bar offer the following queries combinations: <br>

- city's name (Example : 'Marseille')
- city's name + country code (Example : 'Marseille FR')
- ZIP Code + country code (Example : '13000 FR')

On query success, you will have access to the following weather information : <br>

- the current weather (wind speed and orientation, humidity, temperature), in the center of the app
- the weather per hour (temperature and related icon for the next 24 hours), in the bottom of the center pane
- the weather per day (date, temperature and related icon for the next seven days)

Hovering with the mouse on a particular day in the day weather section will show more weather information about it.

Also, you will be able to add or remove the current city to your favourite list by clicking on the favourite indicator,
situated in the top left corner of the center pane.
You can access your favourites by clicking on the favourite tab, on the left border of the app. It will drag the favourite pane.
Clicking on a favourite will then display its weather information after dragging back the favourite pane.

Finally, you can switch between light mode and dark mode by clicking on the theme icon, situated in the top right corner
of the app.


### How it works

#### -- initialization

Launching the app will start an init process.<br> It will first instantiate the attribute currentCity with the last research,
or with the default town (Paris, france) through the initCurrentCity function by looking into appState.txt, located
in the resources package. <br> Secondly, it will fill the three weather grids with an updateWeather function call. <br> Thirdly, 
the function initFavourite will instantiate the favouriteList attribute, fill the favourite Grid, create the drag animation
necessary for panel dragging and update the favouriteIndicator's image. <br> Finally, the theme will be instantiated by looking again
into appState.txt and updating the app theme.

#### -- Weather research

Pressing a key in the searchBar will call the GUIController's searchForCity method. If the key pressed is Enter, it will analyse the
input to determine the city's name, the zipcode and the country code.<br>
There are three possible combinations :

- city's name
- city's name + country code
- zipcode + country code

Then, The GUIUtilities.getQueryResponse() will be called to use the correct APIQuery's method. <br>
On success of the API query, the currentCity attribute will be initialised with it and weather information, the background, and
the favourite indicator will all be updated with the related update functions.
On failure, a not found animation will be played by the GUIAnimations.playSearchErrorAnimation function.


#### -- Favourites management

favourite management, once a city's weather is displayed, is only done by clicks. When the user successfully search for a city,
it will automatically detect if this city is contained if the favourite list and update the favourite indicator consequently.
When clicking on the favourite indicator, the updateFavouriteFromCurrentCityOnClick() function will be called and add or remove
the current city from the favourite list.
When clicking on the favourite tab, the panel will be dragged. Again, clicking onto a favourite will undrag the panel and
display this favourite weather with much more info. It is done thanks to adding event handlers when instantiating a row of
the favourite grid. On click, the event handlers will send back the city object that was used to instantiate the row (taking
the risk to display an outdated weather).

#### -- Event handling

Event handling is mostly used through the fxml file of the GUI application. It handles :

key detection for :
    - the search feature handling, when pressing any keys, the searchForCity function is called.
click detection for :
    - the theme button (function onThemeModeButtonClicked())
    - the favourite indicator (function updateFavouriteFromCurrentCityOnClick())
    - the favourite tab (function translateFavouritePanelOnClick())
    - the hour grid arrows (fucntion onHourWeatherPaneLeftArrowClicked())
mouse entering / exiting for :
    - the hour grid arrows (onHourWeatherPane###ArrowMouseEntered() functions)
    - the cursor modification when hovering a clickable place (showCursorHand() and showCursorDefault())

Event handling is also present in code for the favourites clickable names : on click, update the app just as in the searchForCity
function but with the city object that the favourite was instantiated with.
The same functionality is used when hovering over a particular day in the dayWeatherGrid.

#### -- Animations

Animations are handled into the GUIAnimations class. This class is a compound of static functions for each of the
application animations : 

- createFavouritePanelTranslation(), showFavouritePanel(), hideFavouritePanel() and is FavouritePanelAnimationOver().
- HorizontalScroll() for the hour grid scroll animation
- playSearchErrorAnimation()



