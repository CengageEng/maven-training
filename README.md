Intro to Maven
==============


## Why Maven?
* Regular structure
* Dependency management
* Tool Support
* Codification of good practices
* Why not Maven?
  
## The Maven Way
Don't fight Maven. You'll lose.

Some general principles:

* Don't be platform dependent
* Don't depend on anything outside the build itself and repository it's in or published dependency artifacts (snapshot dependencies will cause your build to break eventually, for example)
* Interact with other modules only through their artifacts as a dependency


## Resources
The Sonatype Book: http://books.sonatype.com/mvnref-book/reference/index.html
POM Reference: http://maven.apache.org/pom.html
Standard directory layout: http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
Maven Getting Started Guide: http://maven.apache.org/guides/getting-started/index.html
M2Eclipse: http://eclipse.org/m2e/

  
## Maven Basics
* Creating a new project
* Maven Lifecycle
* Basic dependency management
* Snapshots/versions
* Directory layout
* Super POM
* Exercise: Simple library project
  
## More Basics
* Plugin configuration
* Common plugins (SureFire, compiler)
* War projects
* Exercise: add TestNG testing (assuming I can get it TestNG and SureFire to play nice)

## Multi-Module Maven
* Parent child relationships
* Intra-project dependencies
* Exercise: Simple multi-module project

## Web Projects
* WAR packaging
* Integrating integration testing with Cargo
* Exercise: Multi-module build with testing

## Clean/Advanced Maven
* Using properties to reduce duplication
* Profiles
* Advanced dependency management (exclusions, dependencyManagement)
* Resolving dependency problems
  * Viewing dependency information (command line, IntelliJ)

## Social Maven
* The release plugin and Maven's release model
* Using Nexus and other repositories



# Javascript testing
http://searls.github.io/jasmine-maven-plugin/

gruntfile.js in nb-ui
# ide integration

# reference info for each exercise
