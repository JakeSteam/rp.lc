# PixelMetre

## What is this?

PixelMetre is a (very unfinished) engine & ecosystem for creating simulations that act based on input images.

For example, there could be an "Anthill" recipe that defines the rules for an ant colony and the life within. An image would then provide the "seed" for this colony, which would be converted by the sim rules into a full simulation.

## What can it do currently?

It can load an image from `/input/` and perform a set of JSON-derived rules on it. These rules identify the most common border pixel, colour all matching pixels blue, and all other pixels green.

These rules are currently hardcoded in `Main.kt` (soon to be in JSON), [this is the logic flow](/docs/exampleFlow.png).

## How can I use it?

Run `Main.kt` with an image file in `/input/`.

## What is the roadmap?

* [todo.md](todo.md) is the constantly updated plan for the project, with far more ideas than can ever be implemented!
* [pixels.md](pixels.md) is a definition of the rules intended to be included in the default sim ruleset.

## What's the end goal?

Eventually this project will ideally end up as a collection of projects:

* `PixelMetre Recipe Creator`: A drag & drop recipe creator.
* `PixelMetre Recipe Store`: A listing of recipes, their metadata, samples, etc.
* `PixelMetre Engine`: **This repo**, a tool that uses recipes to convert input images into simulations.

The projects will have shared modules / functionality, e.g. JSON / rule parsing & validation

## How can I help?

* Let me know what you think of the planned features / roadmap.
