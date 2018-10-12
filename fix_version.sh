#!/usr/bin/env bash

# This script changes the version number in pom.xml to the current tag OR to the current date followed by the commit hash.

DATE=`TZ='UTC' date '+%Y.%m.%d.%H.%M.%S.%Z'`
COMMIT=`git rev-parse --short HEAD`
VERSION=${DATE}-${COMMIT}

TAGS=`git tag -l --points-at HEAD | wc -l`
if [ "$TAGS" != "0" ]; then
    VERSION=`git tag -l --points-at HEAD | head -n 1`
fi

echo "Setting version to: ${VERSION}"

xmlstarlet ed -N x="http://maven.apache.org/POM/4.0.0" -u "//x:project/x:version" -v "${VERSION}" pom.xml > pom2.xml
mv pom2.xml pom.xml