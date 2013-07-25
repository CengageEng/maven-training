Intro to Maven Course Notes
===========================

## Why Maven?
### Regular Structure
* Maven turns source code into artifacts
* Not a general automation tool
  * Makes it really hard to do some things that lead to hidden dependencies and pain later
  * Also makes it really hard to do some pretty useful things
* Approachable for new developers
* Tools can understand your build
* Supports collective ownership–rarely see builds that only one person can touch
* Codifies good practice
  * Separate code, tests, resources
  * Declarative dependency management
  * Test final built artifacts
  * Simple build steps
  * Intricate resource copying is hard
  * Share resources via dependencies

  
Most projects are, at some level, doing the same thing: turning source code into build artifacts. They do this by running a compiler and running tests to verify the output (and input). There are a lot of other things associated with this, such as managing dependencies, replacing variable data in files, time and/or version stamping, etc., but nearly all builds have some of the same components and structure.

In the pre-Maven days, every build was a special snowflake. Depending on the build system author's mood or inclination, the location of source files, the specific compiler commands invoked, and how dependencies are managed would all be unique to each build. Any new developer trying to contribute to a project would first have to learn some of the intricacies of that build system before being productive. Maven takes all the common elements of a typical build and applies a predictable structure to them. This results in some degree of reduced flexibility in how you manage and lay out your software components, but it has the advantage that anyone familiar with a Maven build in general can very quickly understand and work with a specific project built with Maven. Another great benefit is that, because the structure is regular and machine-readable, tools can understand your build as well. Maven projects can easily be imported into a wide range of IDEs, and build automation tools such as Jenkins can trivially be setup to build and report on a Maven project without having to configure all the details such as test output locations or module structure.

#### Why Not Maven?
For some cases, Maven may not be the right tool. For general automation tasks that don't fit inside the box of turning source code into tested build artifacts, you're probably going to end up fighting Maven. Maven does have a plugin system that allows for many of the things you might need to do along the way to producing a build artifact. Many of the common tasks are already available as plugins. Alternatively, you can use the Maven AntRun plugin to invoke Ant scripts or wrap your Maven build with a shell or other script that performs the general automation tasks you need.

## The Maven Way

Maven has its way of doing things. If you have a problem that's not obvious to solve, first try to fit it into Maven's lifecycle and existing plugins.

## Maven Basics

### Creating a New Project
Start off with the simplest POM. Just the coordinates. Add a defaultGoal.

### Maven Lifecycle
Default lifecycle:

* validate (basic project correctness)
* compile
* test (unit tests before packaging)
* package (build the output artifact e.g. jar or war)
* integration-test (more on this later)
* verify (quality checks)
* install (install artifact into local repository…most of the time you want this)
* deploy (not what you thing…deploys artifact to a Maven repository)

Each phase executes all the previous. Goals can be bound to phases. Packaging binds some initial goals to phases.

Jar:

Phase                 | Goal
----------------------|-------------------------
process-resources     | resources:resources
compile               | compiler:compile
process-test-resources| resources:testResources
test-compile          | compiler:testCompile
test                  | surefire:test
package               | jar:jar
install               | install:install
deploy                | deploy:deploy


We'll see more about binding goals to phases later. You can execute phases or goals directly on the command line.

### Basic Dependency Management
Maven manages transitive dependencies declaratively. This is one of the greatest benefits of Maven, and other tools (such as Ivy) have copied its model. A dependency consists minimally of a groupId, artifactId, and version (the coordinates). Optionally, a scope can be specified. Possible scopes are compile (the default), test, provided (added to classpath for compile, not runtime), runtime (not added to classpath at compile time), and system (don't use it…for specifying a local file).

### Release and Snapshot Versions
Maven has two types of dependency versions: release and snapshot. Snapshot dependencies are indicated by "```-SNAPSHOT```" in the version. Snapshot versions are treated differently by Maven's dependency management engine, in that they are treated as snapshots of a state in time in development. They are expected to change frequently, so they are re-fetched from upstream repositories frequently. Release versions, on the other hand, are expected to never change. Once you've downloaded a release version of an artifact, Maven will not attempt to download that artifact again.

### Directory Layout
Convention over configuration. Look at the Super POM. (mvn help:effective-pom)

### Super POM
All POMs inherit from it. Go there to see defaults. mvn help:effective-pom shows you your project with the Super POM values.

### Exercise: A Simple Library Project
Let's pretend we're building a Hello World library that prints Hello World out to the console. Write a HelloWorld class that outputs a String and a test that invokes it. Build and test your project with Maven. Solution in http://github.com/ostewart/maven-training

## More Basics
Now that we've seen how to create a very simple project built with defaults, let's look at something more complicated. Also note what we've gotten from just a few lines of XML as compared to what we'd have to do in Ant to handle compilation, test running, jar building, and version management.

### Plugins
Maven's main construct of modularity is the plugin. Many of the aspects of your project can be configured by configuring the plugins Maven uses to execute parts of the build. For example, the maven-compiler-plugin:

      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>2.0.2</version>
        <configuration>
          <source>1.6</source>
          <target>1.6</target>
        </configuration>
      </plugin>

### Common Plugins
Plugins you'll frequently encounter include the compile plugin and the SureFire plugin that runs unit tests. Behind the scenes and able to be customized are the maven-resources-plugin, the maven-dependency-plugin, and others.

Note on reading plugin documentation: look first at the Introduction, Usage, and Goals sections. Almost all plugins have these, and that's where the information you're looking for is.

### Packaging
The packaging for a module defines the lifecycle bindings for that module as well as how the build artifact is packaged. Most common is jar packaging, that packages up your class files into a jar. Also common are the pom packaging, for container modules, and war packaging for web artifacts.

### Exercise: Add TestNG Unit Tests
Configure the SureFire plugin to run TestNG instead of JUnit. Bonus: add a Cobertura report.

## Multi-Module Maven
Most projects become complex enough that they can be decomposed into multiple modules. Maven's straightforward module system and transitive dependency system make it really easy to split your system into multiple modules (assuming you haven't already created crazy circular dependency issues).

A multi-module project typically consists of a top-level container with pom packaging and a modules section that references each module. Each module corresponds to a subdirectory of the parent project/module.

Modules are useful for grouping related functionality and, perhaps more importantly, for isolating functionality that should not be grouped. Strive for loose coupling and high cohesion. Vertical module stacks (service-web/webservice).

Digression: why separate code into modules? To enforce code boundaries, dependency relationships. As a unit for sharing code (can depend on a library jar). As a way of organizing dependencies. (E.g. some code needs an HTTP client. Not all your code should talk directly via HTTP. Isolate the HTTP client code into a module, then other code can depend on that without depending on the HTTP client.) As a unit of packaging (to generate a WAR, for example).

### Hard Things
There are only two hard things in Computer Science: cache invalidation, naming things, and off-by-one errors.

http://martinfowler.com/bliki/TwoHardThings.html

GroupIds are usually named similarly to packages. Within big organizations, I often match groupIds to package hierarchies, for example: ```com.example.mymodule``` would be both the groupId and the Java package into which all the code in sub-modules are packaged. I find it helps to add a level of package hierarchy for the artifactId as well. So a ```utility``` module in the ```com.example.mymodule``` group would have packages prefixed with ```com.example.mymodule.utility```. For small organizations, it's often enough to use the organization's domain name as a groupId and name each module in the form ```{project}-{module}```.

For a simple multi-module Scala project published to Maven Central: https://github.com/cyrusinnovation/stubble

One thing to be aware of is changing module names in development. Once you install a module or project, a copy resides in your local repository. Modules without updated dependency information may still be able to pick up the old name, so the project will only break when someone without the old artifact in their repository tries to build. You can avoid this by manual removing the renamed artifact from ```~/.m2/repository```.


### Parent Child Relationships

Each maven project can specify an explicit parent, from which it will inherit configuration. If no parent is specified, it inherits from the Super POM. The parent project can be used to manage common configuration such as plugin settings and dependencyManagement information.

Project inheritance should be used for settings that should be shared across multiple projects, not as a way to express dependencies. An inheritance relationship is not required for but often coincides with containing and grouping of sub-projects.

A typical multi-module might be structured like this:

           my-project
          /     |    \
     common  service web

The web module might depend on common, but it does not inherit from it. Common, service, and web all inherit from my-project, which is used to establish common dependency versions and other settings for all the sub-projects/modules.

Reference: http://maven.apache.org/guides/mini/guide-multiple-modules.html

Cross module dependencies within a multi-module project are expressed as any other dependencies. Dependency resolution may be different between building the whole project with the reactor and building a single sub-module in isolation. Just run install all the time to avoid confusion.

### Finding Dependencies
http://mvnrepository.com/ or GitHub project information, or just use your IDE if it works.

### Exercise: Multi-Module Project

Now that we have a HelloWorld utility, we'd like to use it to greet people on our roster of users. For each name in the roster, we'd like to output the hello greeting with that name. While the HelloWorld utility may be generally useful in our future endeavors, this greeting service is specialized functionality, so we'd like to put it in its own module. There's also another library we can use (Google Guava) that will help us implement this functionality, but isn't needed for the simple HelloWorld utility.

### Building for the Web

Use ```war``` packaging and place ```WEB-INF``` resources (e.g. web.xml) in ```src/main/webapp/WEB-INF/```. Anything in ```src/main/webapp``` will be packaged directly into the war file.

### Exercise: Simple Web Project

We'd like to expose our HelloWorld utility as a web service. Write a simple servlet that extracts the name from the URL and serves the hello response as plain text.


### Integration Testing
Effective integration testing can be one of the trickier problems to solve in Maven. Because integration testing often involves deployment into a container and other associated tasks, it often doesn't fit directly into Maven's model of transforming source code into artifacts, and looks more like general automation. That said, there are ways to work effective integration testing into your build.

The main challenges in adding integration testing to a Maven build are fitting the testing into Maven's lifecycle and triggering the needed software actions to start and stop a container, deploy the software, and run tests against it, all in the correct order. Maven's main construct for ordering operations is its lifecycle. Let's look at the default lifecycle in more detail:

* validate (basic project correctness)
* generate-sources
* process-sources
* generate-resources
* process-resources
* compile
* process-classes
* generate-test-sources
* process-test-sources
* generate-test-resources
* process-test-resources
* test-compile
* test (unit tests before packaging)
* prepare-package
* package (build the output artifact e.g. jar or war)
* pre-integration-test
* integration-test (more on this later)
* post-integration-test
* verify (quality checks)
* install (install artifact into local repository…most of the time you want this)
* deploy (not what you thing…deploys artifact to a Maven repository)


Our goal here is to build all our library modules, build the web module code and run as much testing against it we can without deploying into a container, then to deploy into a container and run a suite of end-to-end tests that verify the final packaging, deployment, and wiring.

In the Java world, this normally means building a war file, starting Tomcat or some other servlet container, then running some JUnit/TestNG tests that invoke Selenium or some other HTTP client.

The main construct we have in Maven to manage a container effectively is the [Cargo Plugin](http://cargo.codehaus.org/Maven2+plugin). Cargo allows you to configure a container within your Maven project and then start and stop the container at the right phases during the build. The catch is that Cargo works by using a dependency on your war artifact in order to deploy. This means it can't run during the integration-test phase of your web module, but you can separate your integration tests out into a dedicated module that will build after the web module is complete.


### Exercise: Integration Testing the Web Module

Write a simple integration test for the hello servlet using HTTPClient or Selenium and Cargo.

