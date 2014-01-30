Sudoku
======
Projects in this repo are a simple sudoku applications (web app & desktop app).
They are all maven projects.
For the main logic:

sudoku-common is the main project which executes the sudoku generation and guesses checking;

sudoku-events handles the events occured in the sudoku - game over, cell guess, updating etc.;

sudoku is the implementation of sudoku-common, it is a sudoku with numbers (using sudoku-numbers-generator);

sudoku-basic-value-generator generates basic values, it was used in the beginning, now sudoku-numbers-generator is used for sudoku cell data generation;

sudoku-numbers-generator is used for generating the data inside the sudoku cells (in this case - numbers);

sudoku-runner holds the server (Apache Tomcat) and db for the webapp. It uses hibernate & hsqldb, also java servlets for requests processing. It uses jsp pages for the frontend and js and jquery for the requests;

(1) sudoku-app is the desktop app for the sudoku, depending on the OS you're running you'll need to use different dependencies (defined in the pom.xml in sudoku-app)
org.eclipse.swt.gtk.linux.x86_64 - for linux x64
org.eclipse.swt.win32.win32.x86 - for windows x32
org.eclipse.swt.win32.win32.x86_64 - for windows x32

To run the desktop app make sure you have the right dependency in sudoku-app pom.xml depending on your OS (1), then run side.sudoku.app.SudokuApp. 

To run the web app deploy sudoku-runner to the Apache Tomcat server, start it and go the http://localhost:whatever-port-you-set/sudoku-runner/hello

The difficulty setting is still not working do great, but other than that enjoy playing it ;)
