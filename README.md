# Angry_Birds

#### A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff). This project was generated using `Box2D` Library.

### Game play Demonstration Video: [Angry Birds GamePlay](https://drive.google.com/file/d/1YxeqszZc0i_HjIp4MTNZI0gfDwXUux1E/view?usp=drive_link)

---

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper has been included. First navigate to the project's root directory `AP_project/`. Then you can run the project by using `./gradlew run` (for mac/unix) and `gradlew run` (for windows) commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

### Project Members:

    1) Gopal Krishna Tewari (2023223)
    2) Aniket Khandelwal (2023087)

### Contributions:

    1) Gopal Krishna Tewari:

    	# Made the settings page
    	# Made the home page
    	# Added the background images and buttons on the level screen
        # Made Readme and video

    2) Aniket Khandelwal:

        # Made the birds classes
    	# Made the pigs classes
    	# Made the material classes
    	# Made the pause screen
        # Made the level Screen

### How the code works:-

    There are in total 19 java classes


    Homescreen.java:-

    Class Structure and Initialization:

    The class implements Screen, an interface from LibGDX, allowing it to serve as a display screen within a Game context.

Essential components include:
OrthographicCamera for setting the view.
SpriteBatch for rendering textures.
Sprite objects for the background, title, and buttons (play, settings, exit).
Rectangle and Circle objects define clickable areas (play button as a rectangle, settings and exit as circles).
show() Method:

Configures the camera to match the window size.
Loads textures for all visual elements: background, title, play, settings, and exit icons.
Initializes hit areas (playButton, settingsbutton, and exitbutton) to detect mouse interactions over the buttons.
Sets a custom cursor using a Pixmap (assuming a file called cursor.png), giving the game a personalized look.
render(float delta) Method:

Clears the screen and sets a black background.
Begins a batch to draw each element on the screen: background, icons, and title.
Tracks mouse position in the camera’s coordinate system and checks if it overlaps with any button.
When hovering over a button, the cursor changes to a hand.
Detects if a button is clicked, which triggers a screen transition (e.g., the play button transitions to SelectLevel, the settings button to Settings, and the exit button closes the app).
resize(), pause(), resume(), hide(), and dispose():

dispose() clears resources from memory to avoid leaks, including textures and the custom cursor.
Other methods (pause, resume, etc.) are placeholders, ready for additional handling if needed.
This structure ensures a smooth, intuitive user experience in the main menu while preparing users for game navigation with visual cues and custom graphics. Let me know if you'd like more detail on specific interactions or class elements!

    Levelscreen.java:-

The `LevelScreen` class represents a playable level screen and ContactListener in your Angry Birds game, managing the game’s main gameplay elements, collision physics and user interface during a level. Here's how it works:

1. **Class Structure and Initialization**:

   - Implements the `Screen` interface from LibGDX, allowing it to be managed within the game's `Screen` context.
   - Essential components include:
     - `Level level` for the game’s logic and entities within a particular level.
     - `SpriteBatch` to handle rendering.
     - `OrthographicCamera` to view the game’s visual content.
     - UI elements such as `pauseicon` (to pause the game) and `score` (displaying the player’s score).
   - A `Circle pausebutton` represents the clickable area for the pause button.

2. **show() Method**:

   - Initializes the camera to match the screen dimensions.
   - Loads textures for the `pauseicon` and `score` sprites.
     - The `pauseicon` (positioned in the top-right corner) allows the player to pause the game.
     - `score` displays the player's score, positioned near the top-left corner.
   - `pausebutton` is defined as a circular hit area to detect clicks on the pause icon.

3. **render(float delta) Method**:

   - Sets the background color to white and clears the screen.
   - Begins the `batch` to draw elements on the screen:
     - Calls `level.render(batch)` to render the level’s game objects and elements.
     - Renders the `pauseicon` and `score`.
   - Tracks the mouse position in the game world and checks if it overlaps with the `pausebutton`.
     - When hovering over `pausebutton`, the cursor changes to a hand.
     - If clicked, the game switches to `PauseScreen`, pausing gameplay and allowing the player to resume or adjust settings.

4. **resize(), pause(), resume(), hide(), and dispose()**:

   - `dispose()` releases resources like `batch` and calls `level.dispose()` to free up any allocated memory within the level.
   - Other methods (`resize`, `pause`, etc.) are present but are placeholders, ready to handle additional functionalities if necessary.

### ContactListener Methods

The `ContactListener` interface in Box2D is used to handle collision events between bodies in the physics world. Below are the methods and their purposes:

#### 1. `beginContact(Contact contact)`

This method is called when two fixtures start to collide.

- **Use:** Detect when a collision begins.
- **Parameters:**
  - `contact`: Provides details about the contact point, such as the two fixtures involved.

---

#### 2. `endContact(Contact contact)`

This method is called when two fixtures stop colliding.

- **Use:** Detect when a collision ends.
- **Parameters:**
  - `contact`: Provides details about the contact point, such as the two fixtures that were involved.

---

#### 3. `preSolve(Contact contact, Manifold oldManifold)`

This method is called before the collision response is calculated.

- **Use:** Modify or disable the contact before the physics engine processes it.
- **Parameters:**
  - `contact`: Provides details about the contact point.
  - `oldManifold`: The collision manifold from the previous step, useful for comparison.

---

#### 4. `postSolve(Contact contact, ContactImpulse impulse)`

This method is called after the collision response has been calculated.

- **Use:** Analyze the results of the collision, such as the applied impulse, for game logic or effects.
- **Parameters:**
  - `contact`: Provides details about the contact point.
  - `impulse`: Contains the collision impulse, useful for determining collision strength.

---

### Example Use Cases

- **`beginContact`**: Trigger an event when a player touches an enemy or an item.
- **`endContact`**: Stop applying a force when an object moves out of a zone.
- **`preSolve`**: Prevent collisions under specific conditions, such as allowing certain objects to pass through each other.
- **`postSolve`**: Apply visual effects (e.g., sparks or damage) based on the collision force.

---

       loadingscreen.java

Here’s how it functions:

1. **Class Structure and Initialization**:

   - Implements the `Screen` interface from LibGDX, meaning it serves as one of the game screens.
   - Uses an `OrthographicCamera` for a straightforward, fixed camera view.
   - A `SpriteBatch` to handle rendering, and a `Sprite` to display the loading image.
   - A `loadingTime` variable to track the duration of the loading screen display, set to 0 initially.

2. **show() Method**:

   - Initializes the camera and aligns it with the screen size.
   - Loads the `Sprite` with the "loading.png" texture, which will act as the loading image.

3. **render(float delta) Method**:

   - Clears the screen to a black background for a clean loading display.
   - Begins the `batch` and renders the `sprite`, displaying the loading image.
   - Adds the `delta` time (the time since the last frame) to `loadingTime`, incrementing it to simulate a loading duration.
   - If `loadingTime` exceeds 3 seconds, it calls `game.setScreen(new HomeScreen(game))` to transition to the `HomeScreen`, thus completing the loading phase.

4. **resize(), pause(), resume(), hide(), and dispose()**:
   - `dispose()` releases resources like `batch` and the texture used by `sprite`.
   - The other methods (`resize`, `pause`, etc.) are placeholders, with potential for future use if more functionality is added.

This class effectively serves as a timed loading screen, providing players with a brief pause before reaching the main menu. It's a helpful addition for games that need a moment to load assets or prepare the game environment.

    Pausescreen.java

It provides options to resume, restart, or exit back to the level selection screen. Here’s a breakdown of how this class functions:

1. **Class Structure and Initialization**:

   - Implements the `Screen` interface from LibGDX to manage this state within the game.
   - Important elements:
     - `SpriteBatch` for rendering.
     - `Sprite pausedisplay` to hold the pause screen background image.
     - Three `Circle` objects (`playbutton`, `restartbutton`, `exitbutton`) define the clickable areas for resuming, restarting, and exiting the level.
     - `OrthographicCamera` for positioning and rendering the pause screen at the correct viewport.
     - `Game game` for handling screen transitions, and `LevelScreen lvlscreen` to allow returning to the current level if resuming.

2. **show() Method**:

   - Sets the system cursor to the default arrow cursor and initializes the camera to match screen dimensions.
   - Loads the `pausedisplay` sprite texture, which displays the pause screen graphic.
   - Initializes the three buttons as `Circle` objects with positions and sizes that make them easy to interact with, representing each clickable area for `restart`, `play`, and `exit`.

3. **render(float delta) Method**:

   - Begins the `SpriteBatch` to render the `pausedisplay` image.
   - Calculates the `mousePos` coordinates in game space to track the mouse’s position relative to the camera.
   - Checks if the mouse is hovering over any button:
     - **Restart Button**: If clicked, resets the level by creating a new `LevelScreen` instance with a new `Level1()` and switches the screen to restart.
     - **Play Button**: If clicked, resumes the game by setting the screen back to the current `lvlscreen`.
     - **Exit Button**: If clicked, switches to `SelectLevel`, allowing the player to choose another level or return to the main menu.
   - Updates the cursor appearance based on hover status, setting it to a hand icon when hovering over any clickable area.

4. **resize(), pause(), resume(), hide(), and dispose() Methods**:
   - `dispose()` releases resources used by the pause screen, including the `batch` and the texture for `pausedisplay`.
   - The other methods serve as placeholders, enabling expansion if additional features or specific handling for resizing, pausing, or resuming are needed.

The `PauseScreen` class is an interactive UI overlay that lets players control their game session by either continuing, restarting, or exiting, providing a smooth and intuitive pause experience.

    Settings screen (Settings.java):-

The `Settings` screen class allows players to adjust sound and music settings and and reset the game progress. Here’s a breakdown of its components:

### Key Class Elements

1. **Screen Interface Implementation**:

   - The `Settings` class implements the `Screen` interface, which allows it to handle rendering, input, and transitions in the game’s screen flow.

2. **Fields and Initialization**:

   - The `game` instance provides access to the main game class for screen transitions.
   - `batch` is used to render textures.
   - `stage` is used for handling screen viewport resizing.
   - Sprites for the background and buttons (sound, music, and back) manage the screen’s visual elements.
   - A `homescreen` instance is passed to navigate back to the previous screen.

3. **show() Method**:

   - Initializes `SpriteBatch` and `Stage`.
   - Loads the background and button textures (`b1.png`, `sound_button.png`, `music_button.png`, `back_button.png`).
   - Positions the buttons using the `centerX` variable for horizontal centering and stacks them vertically with defined gaps.

4. **render(float delta) Method**:

   - Clears the screen and sets a black background.
   - Begins the `SpriteBatch`, rendering the background and buttons.
   - Uses input detection to check if any button is pressed:
     - **Sound Button**: Logs a message for toggling sound settings (implementation pending).
     - **Music Button**: Logs a message for toggling music settings (implementation pending).
     - **Back Button**: Navigates to the previous screen (`homescreen`), allowing users to exit settings.

5. **resize(int width, int height)**:

   - Updates the viewport in `stage` to handle screen resizing dynamically.

6. **dispose() Method**:
   - Releases resources for the batch, stage, and button textures to prevent memory leaks.

### Suggestions for Additional Functionality

1. **Toggle Sound and Music States**:

   - Add fields to track the sound and music states and update them in response to button presses.
   - This could be integrated with a preference system to persist these settings.

2. **Button Feedback**:

   - Provide visual feedback on button clicks (e.g., changing button color or adding a clicked animation).

3. **Consistent Button Positioning**:
   - Make button positioning responsive to screen size adjustments.

This setup provides a clean and user-friendly settings screen, making it straightforward for users to adjust audio settings or exit back to the main screen.

## Implemented Design Patterns

### 1. Observer Pattern:

- The ContactListener interface observes when the collision between bird, blocks and pigs take place.

### 2. Factory Pattern:

- The Classes of Birds, Pigs and Blocks help create game objects with different colors, sizes, or abilities based on some parameters.

### 3. Singleton Pattern:

- The Main class creates a single instance of the Game for the entire game thus implementing singleton pattern.

---

### Sources:-

    1) https://fontmeme.com/angry-birds-font/


    2) https://angrybirds.fandom.com/wiki/Angry_Birds_(series)/Main_Menus


    3) https://lightningsamurai.wordpress.com/2016/11/29/research-into-the-buttons-and-assets-angry-birds/


    4) https://figma.com

#### Instructions to run this code:-

    - Install Gradle and libGDX. Open the project's root directory in any IDE (eg. IntelliJ, VSCode) and run this using gradle GUI or run using terminal commands mentioned above.
