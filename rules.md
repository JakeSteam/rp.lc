## Rules

### Current: 
* Analyser: Pixel array -> int
* Creator: Width & height -> Pixel array
* Placer: Pixel array & mask & true & false -> Pixel array
* Transformer: Pixel array & int -> Pixel array

### Naive interface by type:

* IntArrayToInt
* IntAndIntToInt
* IntArrayAndBooleanArrayAndBooleanAndBooleanToIntArray
* IntArrayAndIntToIntArray 

### Better:

Just use BaseRule, since reflection is used to find the invoke function anyway