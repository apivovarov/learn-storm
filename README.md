# Learn Storm

---

Table of Contents

* <a href="#getting-started">Getting started</a>
* <a href="#maven">Using learn-storm with Maven</a>

---


<a name="getting-started"></a>

# Getting started

## Prerequisites

First, you need `java` and `git` installed and in your user's `PATH`.

Next, make sure you have the learn-storm code available on your machine.  Git/GitHub beginners may want to use the
following command to download the latest leanr-storm code and change to the new directory that contains the downloaded
code.

    $ git clone git@github.com:apivovarov/learn-storm.git && cd learn-storm
    
## learn-storm overview


learn-storm contains a variety of examples of using Storm.  If this is your first time working with Storm, check out
these topologies first:

1. [SimpleTopology](src/main/java/org/x4444/storm/SimpleTopology.java):  Basic topology written in all Java

If you want to learn more about how Storm works, please head over to the
[Storm project page](http://storm.apache.org).


<a name="maven"></a>

# Using learn-storm with Maven

## Install Maven

Install [Maven](http://maven.apache.org/) (preferably version 3.x) by following
the [Maven installation instructions](http://maven.apache.org/download.cgi).


## Build and install Storm jars locally

If you are using the latest development version of Storm, e.g. by having cloned the Storm git repository,
then you must first perform a local build of Storm itself.  Otherwise you will run into Maven errors such as
"Could not resolve dependencies for project".

    # Must be run from the top-level directory of the Storm code repository
    $ mvn clean install -DskipTests

This command will build Storm locally and install its jar files to your user's `$HOME/.m2/repository/`.  When you run
the Maven command to build and run learn-storm (see below), Maven will then be able to find the corresponding version
of Storm in this local Maven repository at `$HOME/.m2/repository`.


## Running topologies with Maven

> Note: All following examples require that you run `cd learn-storm` beforehand.

learn-storm topologies can be run with the maven-exec-plugin. For example, to
compile and run `SimpleTopology` in local mode, use the command:

    $ mvn compile exec:java -Dstorm.topology=org.x4444.storm.SimpleTopology