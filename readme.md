# PixelMetre

## What is this?

PixelMetre is a (very unfinished) engine & ecosystem for creating simulations that act based on input images.

For example, there could be a "Anthill" recipe that defines the rules for an ant colony and the life within. An image would then provide the "seed" for this colony, which would be converted by the sim rules into a full simulation.

## What can it do currently?

Nothing much. It can load an image from `/input/` and perform very basic logic on it:
1. Fetch the edge pixels of the image.
2. Work out the most common colour.
3. Replace all those pixels with "water", and all others with "land".›

## How can I use it?

Run `Main.kt` with an image file in `/input/`.

## What is the roadmap?

* [todo.md](todo.md) is the constantly updated plan for the project, with far more ideas than can ever be implemented!
* [pixels.md](pixels.md) is a definition of the rules included in the default sim ruleset.

## How can I help?

* Let me know what you think of the planned features / roadmap.

## What are key terms for the project?

* Simrecipe: The config file that defines how a simulation should run.
* Resources / Tile / Structure / Life: The entities defined in a simrecipe.
* Step: 1 "turn" in the sim, where all life will act according to their rules.