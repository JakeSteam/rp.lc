# Todo

## Meta

* Project name
* Project icon
* Readme needs to contain coherant explanation / diagrams once PoC is done

## Meta (technical)

### Config file

- [x] Define config file schema
- [x] Be able to handle arbitrary rules
- [ ] Have example JSON
- [ ] Be able to convert JSON into data classes

### UX

- [ ] Basic GUI for picking config, and picking image, displaying output
- [ ] Coherent debug & info log outputting system

### Image handling

- [ ] Handle bmps / jpgs etc
- [ ] Scale all input images to XxY (depending on config, 100x100 default)
- [ ] Support SVG definitions for tiles
- [ ] Output as SVG & PNG (10x render)

### Resources
- [ ] Support defining custom resources in JSON
- [ ] Support tiles generating / using these resources

### Generation
- [ ] Support 2nd layer of generation (structures), making sure to reuse most code

### Simulation
- [ ] Support the concept of a "tick", and perform 1
- [ ] Simulate tile effects on resources
- [ ] Simulate tile effects on each other(!)

### Code quality

- [ ] RuleValidator should have infinite loop detection
- [ ] Hardcoded filename needs to copy input
- [ ] Review input / output exceptions (e.g. skipping check in RuleValidator)