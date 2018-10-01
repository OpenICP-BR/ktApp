## Developer Commands

### Dirty/cached versions

Compile: `mvn compile`

Run: `mvn exec:java`

Generate final JAR with dependencies: `mvn assembly:single`

### Clean/no-cache versions

Compile: `mvn clean compile`

Run: `mvn clean compile exec:java`

Generate final JAR with dependencies: `mvn clean compile assembly:single`