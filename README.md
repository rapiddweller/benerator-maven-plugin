# benerator-maven-plugin

[![CI](https://github.com/rapiddweller/benerator-maven-plugin/actions/workflows/ci.yml/badge.svg)](https://github.com/rapiddweller/benerator-maven-plugin/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.rapiddweller/benerator-maven-plugin.svg)](https://search.maven.org/artifact/com.rapiddweller/benerator-maven-plugin)

'benerator-maven-plugin' is an open source Java library forked from maven-benerator-plugin by Volker Bergmann.

It provides a Maven plugin for integrating rapiddweller-benerator in your build process. The goals are benerator:
generate, benerator:dbsnapshot, benerator:createxml and benerator:datamimic.

`benerator:datamimic` converts Benerator XML descriptors (`*.ben.xml`) below `sourceDirectory` (default
`src/test/benerator`) to [DATAMIMIC](https://www.datamimic.io) format, writing the result and a
`migration-summary.md` to `outputDirectory` (default `${project.build.directory}/datamimic`).

## Introduction

This library is optional for [rapiddweller 'Benerator'](https://www.benerator.de).

## Prerequisites

- Java 11 JDK (we recommend [adoptopenjdk](https://adoptopenjdk.net/))
- [Maven](https://maven.apache.org/)

## Docs

- Create your docs using the maven site plugin `mvn site:site`.
- Checkout the rapiddweller benerator projects website [www.benerator.de](https://www.benerator.de/)
  for additional support resources.
- Checkout the maintainers website [www.rapiddweller.com](https://www.rapiddweller.com/)
  for additional support resources.
- Or read about in our rapiddweller benerator [online manual](https://docs.benerator.de/latest/maven_benerator_plugin.html)

## Getting Involved

If you would like to reach out to the maintainers, contact us via our
[Contact-Form](https://www.benerator.de/contact-us) or email us at
[solution.benerator@rapiddweller.com](mailto:solution.benerator@rapiddweller.com).

## Contributing

Please see our [Contributing](CONTRIBUTING.md) guidelines.

Check out the maintainers [website!](https://rapiddweller.com)
