# Replace (rp.lc)

## What is this?

Replace is a (very unfinished) engine & ecosystem for creating simulations that act based on input images.

For example, there could be an "Anthill" recipe that defines the rules for an ant colony and the life within. An image would then provide the "seed" for this colony, which would be converted by the sim rules into a full simulation.

## What can it do currently?

It can load an image from `/input/` and perform a set of JSON-derived rules on it. These rules identify the most common border pixel, colour all matching pixels blue, and all other pixels green.

These rules are currently hardcoded in `Main.kt` (soon to be in JSON), [this is the logic flow](/docs/exampleFlow.png).

## How can I use it?

Run `Main.kt` with an image file in `/input/`.

## What's the end goal?

Eventually this project will ideally end up as a collection of projects:

* `Replace Recipe Creator`: A drag & drop recipe creator.
* `Replace Recipe Store`: A listing of recipes, their metadata, samples, etc.
* `Replace Engine`: **This repo**, a tool that uses recipes to convert input images into simulations.
* `Rp.lc`: Documentation site.

The projects will have shared modules / functionality, e.g. JSON / rule parsing & validation

## Roadmap

### ~~Milestone 1: Proof of concept (Completed March '23)~~

Goal: A Kotlin project that can use a JSON config to apply image manipulation rules to any input image, and output the result.

- [x] ~~Dynamic rules from JSON config~~
- [x] ~~Applying rules to input, and outputting~~
- [x] ~~Validator confirming config is valid~~

### Milestone 2: Functionality complete (ETA April '23)

Goal: A Kotlin project that can generate worlds with tiles, structures, and game ticks based on input configs & tiles.

- [ ] Support tile images
  - [ ] Handle definition of 10x10 pixel art tile
  - [ ] Scale to 10x size of output
- [ ] Support context-dependent rules
  - [ ] Organise current rules according to inputs & outputs
  - [ ] Create "average R/G/B of up / down / left / right / all tiles" rules
  - [ ] Create "if tile x is (min, exactly, max) distance y away" rules
  - [ ] Use these for beach / deep sea tiles
- [ ] Support structures
  - [ ] Add ability to define generation rules for structures
  - [ ] Support 2nd stage of generation, based on tile input
  - [ ] Ensure all rules can be applied to both
  - [ ] Use these for beach hut / boat structures
- [ ] Support logging
  - [ ] Create a flexible yet easy to use logging util
  - [ ] Implement levels of detail (debug, error, etc)
  - [ ] Add appropriate logging to all processes
- [ ] Support game ticks
  - [ ] Add support for ability to define tick rules for structures
  - [ ] Adjust resources every tick 
- [ ] Documentation
  - [ ] Create project icon / wordmark
  - [ ] Create visualisation of data flow between rules
  - [ ] Showcase examples (island, anthill, game of life)

### Milestone 3: Ready to share (ETA May '23)

Goal: A GUI program for generating worlds from configs & images, with a high quality codebase.

- [ ] Support rule blocks
  - [ ] Add ability to define a block of rules, with input and output
  - [ ] Add ability to validate these
- [ ] Support UI / deployment
  - [ ] Look into KMM webapps
  - [ ] Convert project, especially config / image input
  - [ ] Implement a deployment CI as necessary
- [ ] Implement code quality standards
  - [ ] Restructure entire codebase by feature
  - [ ] Aim to resolve all warnings, remove any hacks, etc
  - [ ] Unit tests for rules
  - [ ] Unit tests for validator
  - [ ] Unit tests for actioner

### Milestone 4: Ready for non-technical use (ETA July '23)

- [ ] Create config builder
  - [ ] Create standalone application that can visualise rules
  - [ ] Extract ability to save / load to JSON
  - [ ] Add viewing rule metadata (type, input, output)
  - [ ] Add UI for chaining rules together
  - [ ] Add UI for defining tiles
  - [ ] Add UI for defining game config
  - [ ] Add UI for defining config metadata
  - [ ] Extract and implement validator
- [ ] Create config repository
  - [ ] Identify how storage will work, likely just a GitHub repo
  - [ ] Work out how to secure this... Some kind of kotlin validation / sanitisation before saving?
  - [ ] Add ability to upload from config builder
  - [ ] Add ability to load from main rp.lc app (with search)
  - [ ] Add unique name validation
  - [ ] Add public URLs for configs, displaying summary and CTA to load in rp.lc
- [ ] Create detailed, autoupdating documentation along with visualisations etc