# Manual Setup Instructions

These instructions will guide you through the manual setup process for the Minecraft Home System.

## Prerequisites

Before starting the setup process, ensure that you have the following prerequisites:

- Java Development Kit (JDK) installed
- Git installed
- Docker installed

## Setup Steps

Follow these steps to manually build and run the Minecraft Home System:

1. Clone the Repository
     ```
    git clone https://github.com/ismaiiil/MincraftHomeSystem
     ```

2. Build the Project

Open a terminal/command prompt and navigate to the cloned repository:
     ```
    cd MincraftHomeSystem
     ```


3. Run the following command to build the project:

- For Windows:

  ```
  .\gradlew.bat clean build shadowJar
  ```

- For Unix-based systems:

  ```
  ./gradlew clean build shadowJar
  ```

4. Move Files to Docker Volume `or your minecraft server in the /plugins folder`

- Create the necessary directories:

  ```
  mkdir docker_files/data/plugins
  ```

- Copy the built JAR file and configuration file to the appropriate location:

      ```
      copy home-system-spigot/build/libs/HomeSystem.jar docker_files/data/plugins/
      copy home-system-api/configurations/config.json docker_files/data/plugins/
      ```


- Open the `config.json` file in a text editor and update the necessary configuration values if necessary

5. Run Docker Compose

- Change the working directory:

  ```
  cd docker_files
  ```

- Start the Docker container:

  ```
  docker-compose up -d
  ```
  
Note: The docker will always wait for the db instance to up before the minecraft server.