#!/bin/bash

echo mkdir target
mkdir target

echo cd target \&\& make -f ../other_res/osx/bundle-osx-dmg.make $@
cd target && make -f ../other_res/osx/bundle-osx-dmg.make $@
