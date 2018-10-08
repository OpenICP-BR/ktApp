## Developer Commands

### Dirty/cached versions

Compile: `mvn compile`

Run: `mvn exec:java`

Generate final JAR with dependencies: `mvn package`

Generate .dmg file for macOS: `./bundle_osx.sh`

### Clean/no-cache versions

Compile: `mvn clean compile`

Run: `mvn clean compile exec:java`

Generate final JAR with dependencies: `mvn clean package`