# Todo

## Meta

- [ ] Readme needs to contain coherent explanation / diagrams once PoC is done

## Functionality

### Resources

- [x] Support defining custom resources in JSON
- [x] Add to validator
- [ ] Support tiles generating / using these resources
  - [ ] Extract input & output OUT of rules, and into something hardcoded
  - [ ] Make width / height / filename / image info provided variables, and output a required output
- [ ] Support output of these resources, maybe in img metadata
- 
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

## Technical

### Image handling

- [ ] Handle bmps / jpgs etc
- [ ] Scale all input images to XxY (depending on config, 100x100 default)
- [ ] Support SVG definitions for tiles
- [ ] Output as SVG & PNG (10x render)

### Code quality

- [ ] RuleValidator should have infinite loop detection
- [ ] Hardcoded filename needs to copy input
- [ ] Review input / output exceptions (e.g. skipping check in RuleValidator)