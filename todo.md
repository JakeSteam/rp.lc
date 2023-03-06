# Todo

## Stages

* Stage 1: Basic functionality. Conversion can be triggered command line, pixels change based on data, position, neighbours
* Stage 2: Usability. App has basic GUI, error handling, code is tidied up a bit. Repo has basic metadata.
* Stage 3: Functionality. App gains ability to dynamically load "recipes" from another repo, and perform life iterations
* Stage 4: Polish. App gets towards 1.0.0, time to investigate performance, recipe spec, documentation, etc
* Stage 5: Ongoing work. New functionality, spec expansions, begin promotion, etc.

## Meta

* Project icon
* Project name

## Meta (technical)

* Create "config" file (input / output dir, simulation settings) and read values from it
  * "Meta" (name, author, version, engine version) - just text
  * "Resources" (coins, happiness, dirt, anything) - name, graphic, desc, positive / neutral / bad
  * "Tile" (dirt, water, etc) - base layer of map - name, graphic, desc, resource generation, rules(!)
  * "Structure" (rock, hut, seed) - goes on top of tiles - name, graphic, desc, rules(!), resource generation(?)
  * "Life" (human, ant, alien) - dynamic life, acts each iteration - name, graphic, desc, rules(!) / behaviour
* Text based UI needed, or console OK?
* Split up into basic classes, check they can interact
* Basic unit testing, to confirm they actually work
* Image scaling
  * Downscale (or upscale) all images to 100x100, cropping at edges.
  * Run generation
  * Upscale to config controlled size (200x200?), for simulation

## Image loading

* Filter to only load image files
* How to load pixels as R/G/B not single int?

## Image converting

* Run a pass over whole image
* First logic should be working out most common outer edge pixel, and converting to water
* Benchmark bulk pixel scanning / writing, performance matters!
* Output pixels should be SVGs, hopefully performance is OK...

## Image saving

* Should also output a log file