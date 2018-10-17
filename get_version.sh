#!/bin/bash

xmlstarlet sel -N x="http://maven.apache.org/POM/4.0.0" -t -m "//x:project/x:version" -v . -n pom.xml
