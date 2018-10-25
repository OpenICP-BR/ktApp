#!/usr/bin/env bash

# This script changes the version number in pom.xml to the current tag OR to the current date followed by the commit hash.

GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

DATE=`TZ='UTC' date '+%Y.%m.%d.%H.%M.%S.%Z'`
COMMIT=`git rev-parse --short HEAD`
VERSION=${DATE}-${COMMIT}

TAGS=`git tag -l --points-at HEAD | wc -l`
if [ "$TAGS" != "0" ]; then
    VERSION=`git tag -l --points-at HEAD | head -n 1`
else
    BRANCH=`./get_branch.sh`
    COMMIT=`git rev-parse --short HEAD`
    DATE=`git log -1 --date=short --pretty=format:%cd | tr '-' '.'`
    VERSION=${BRANCH}.${DATE}.${COMMIT}
fi

# Only change if necessary (it avoids problems with GNU Make)
if [ `./get_version.sh` != "${VERSION}" ]; then
    echo -e "${GREEN}Setting version to: ${BLUE}${VERSION}${NC}"
    xmlstarlet ed -N x="http://maven.apache.org/POM/4.0.0" -u "//x:project/x:version" -v "${VERSION}" pom.xml > pom2.xml
    mv pom2.xml pom.xml
else
    echo -e "${GREEN}Version already was: ${BLUE}${VERSION}${NC}"
fi