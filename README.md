# SWEN30006 Project 2: Pacman Game Editor and Autoplayer

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [How to Run](#how-to-run)
- [Design](#design)
  - [Editor](#editor)
  - [Autoplayer](#autoplayer)
- [Contributing](#contributing)
- [Acknowledgments](#acknowledgments)
- [License](#license)

## Overview

This project involves the creation of a Pacman game editor and an autoplayer. The editor allows users to load, edit, and save game maps. The autoplayer applies strategic algorithms to navigate Pacman through the game map automatically.

## Prerequisites

- Java JDK (version 11 or later recommended)
- Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)

## Installation

1. Clone the repository:
```
git clone https://github.com/your-username/swen30006_project2.git
```

2. Navigate to the project directory:
```
cd swen30006_project2
```

3. Compile the Java files:
```
javac *.java
```

## How to Run

1. To run the game editor:
```
java Driver [filename/foldername]
```
*If no argument is provided, an empty editor initializes. If a `.xml` file is passed, the editor will load the map from the file.*

2. To run the autoplayer:
```
java Autoplayer
```

## Design

### Editor

The game editor's primary objective is to manage the game map. It processes arguments passed from the Driver class. Based on the argument type, the editor either initializes a fresh map or loads an existing one. If a folder name is passed, it validates the folder contents before initializing the game.

**Key Features:**

- Folder and File Detection
- Dynamic Map Creation from XML files
- Game Execution with Autoplay Capability

### Autoplayer

The autoplayer uses the Strategy Pattern, allowing easy future extensions. The `NextMove` interface defines movement methods, with the `AutoMove` class implementing this interface. New strategies can be added without altering existing classes.

**Key Features:**

- Strategy Pattern for Future Extensions
- Dynamic Path Finding for Pacman

## Contributing

Feel free to fork the project, make changes, and submit pull requests. For significant changes, open an issue first to discuss the proposed modifications.

## Acknowledgments

- Project contributors and team members.
- SWEN30006 course staff for guidance.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
