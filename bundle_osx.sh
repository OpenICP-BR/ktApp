#!/bin/bash

# DEPENDENCY: https://github.com/sindresorhus/create-dmg

GREEN='\033[0;32m'
NC='\033[0m' # No Color

VERSION=$1

cd target
rm -rf "OpenICP-BR.app"
jar2app "ktApp-${VERSION}.jar" -n "OpenICP-BR" -i ../src/main/resources/logo.icns -v "${VERSION}"
echo -e "${GREEN}Generated .app bunlde ${NC}"

mkdir OpenICP-BR.app/Contents/Java/lib
cp -arv lib/* OpenICP-BR.app/Contents/Java/lib
echo -e "${GREEN}Copied java libraries to .app bunlde ${NC}"

rm -rf dmg
mkdir dmg
ln -s /Applications ./dmg/Aplicativos
cp -r OpenICP-BR.app ./dmg/
create-dmg OpenICP-BR.app --overwrite
rm ./dmg/Aplicativos
echo -e "${GREEN}Created DMG file${NC}"
