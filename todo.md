# Todo

## Meta

- [ ] Readme needs to contain coherent explanation / diagrams once PoC is done

## Functionality

### Config file parsing

- [x] Define config file schema
- [x] Be able to handle arbitrary rules
- [ ] Have example JSON
- [ ] Be able to convert JSON into data classes
- [ ] Rename "config" into "recipe"

### UI

- [ ] Research UI on Kt apps
- [ ] Basic GUI for picking config, and picking image, displaying output
- [ ] Coherent debug & info log outputting system

### Resources

- [ ] Support defining custom resources in JSON
- [ ] Support tiles generating / using these resources

### Generation

- [ ] Support 2nd layer of generation (structures), making sure to reuse most code

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