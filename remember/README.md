# Remember
_Rust_ CLI command to _fast-save_ little notes, to be able to remember them down the road.  

## Why  
I always have _split-of-a-second_ ideas which could be expanded into projects in the future, but I forget them because I don't want to write them down _physically_ in a post-it note or _E-Mail_ it to me. This is my attempt to solve this problem.  

I also kinda wanted to do something with _Rust_.

## Usage
### Warning  
This project is still WIP and its API may change without previous warning.

#### Add new note
To use it, is as simple as

~~~ bash
rem add "This is my note"
~~~  

#### List notes
Access all your notes with `rem list` or `rem show`. This will print something like:  

~~~ text
To Remember:
	1. this is my first note
	2. this is my second note
~~~

#### Delete notes
You may delete notes by index such as

~~~ bash
rem del 1
rem delete 1
~~~

Or you may clean the whole list with any of the two options, they do exactly the same

~~~ bash
rem clean
rem empty
~~~

In this case, a prompt will appear asking you for confirmation `y / yes`.
