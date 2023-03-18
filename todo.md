# Todo

## Meta

- [ ] Readme needs to contain coherent explanation / diagram

## Functionality

### Resources

- [x] Support defining custom resources in JSON
- [x] Add to validator
- [x] Support tiles generating / using these resources
- [ ] Support output of these resources, maybe in img metadata

### Generation

- [ ] Support 2nd layer of generation (structures), making sure to reuse most code

### UI

- [ ] Research UI on Kt apps
  - "Swing" is [natively supported](https://www.jetbrains.com/help/idea/creating-and-disposing-of-a-form-runtime-frame.html), but Java
  - Would Compose work?
- [ ] Basic GUI for picking config, and picking image, displaying output
- [ ] Coherent debug & info log outputting system

### Simulation

- [ ] Support the concept of a "tick", and perform 1
- [ ] Simulate tile effects on resources
- [ ] Simulate tile effects on each other(!)

### Rules

- [ ] Make more example rules
- [ ] Ability to define collections of rules for reuse would be v powerful
- [ ] If we have sub-packages, e.g. analyser -> singleoutput, can enforce any signature
  - Although the whole interface structure is arbitrary...

## Technical

### Image handling

- [x] Handle bmps / jpgs etc
- [ ] Scale all input images to XxY (depending on config, 100x100 default)
- [ ] Support SVG definitions for tiles
- [ ] Output as SVG & PNG (10x render)

### Code quality

- [ ] RuleValidator should have infinite loop detection
- [ ] Actually handle exceptions properly
- [ ] Improve architecture