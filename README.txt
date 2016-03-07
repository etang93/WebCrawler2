WebCrawler:

Folder Structure: located on the same level as bin. The root folder has a -0 ending.
We were never told how to create the directory structure so I used the last element of the url:
ex: http://www.pages.drexel.edu/~et354/Fish/Brainstorm.html will have a directory called Brainstorm.html-(LayerNumber)

Issues with the above can occur if the url contains invalid characters, I tried to parse them out to no avail.
If the upper layer's folder was not created, then subsequent layers would not as well. 

Layers crawled for printing and structure forming: first layer is the root layer, 2nd layer is the layer under root and so on.

The user can Pause, Resume and Stop the applicationonly once the user has started from the menu at any time.

options are:
1: start, if it has already started then nothing will happen if the user tries it again
2: pause, only when the program has started by the user using (1)
3: resume, can be done as long as the user has started by the user using (1)
4: stop, can be done as long as the user has started by the user using (1)

When all the pipes close, the user might need to stop the program by pressing 4, depends on the thread.

There is a menu thread that cannot be stopped since it is used to resume, stop, and pause the active threads.

Requirements:
Jsoup jar