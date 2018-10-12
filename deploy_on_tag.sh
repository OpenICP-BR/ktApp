#!/usr/bin/env bash

VERSION=`cat pom.xml | grep version | head -n 1 | grep -oe "[0-9.]*"`

# Deploy jar
cp .travis.settings.xml $HOME/.m2/settings.xml
mvn deploy -DskipTests

# Generate .deb file
./bundle_deb.sh
curl -T target/openicp-br-${VERSION}.deb -ugjvnq:${BINTRAY_PASSWORD}\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb