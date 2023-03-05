# Todo

## Meta

* Project icon
* Project readme

## Meta (technical)

* Create "config" file (input / output dir) and read values from it
* Text based UI needed, or console OK?
* Split up into basic classes, check they can interact
* Basic unit testing, to confirm they actually work

## Image loading

* Filter to only load image files
* How to load pixels as R/G/B not single int?

## Image converting

* Run a pass over whole image
* First logic should be working out most common outer edge pixel, and converting to water
* Benchmark bulk pixel scanning / writing, performance matters!
* Is it possible to have the rules dynamically defined? Then would allow alternative gens (e.g. minesweeper, tic tac toe, alien world)

## Image saving

* Should also output a log file