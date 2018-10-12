#!/usr/bin/env bash

VERSION=`./get_version.sh`

# Deploy jar
mvn deploy -DskipTests

# Generate .deb file
curl -T target/openicp-br-${VERSION}.deb -ugjvnq:${BINTRAY_PASSWORD}\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb