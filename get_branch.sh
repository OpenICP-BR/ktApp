#!/bin/bash

git branch --contains HEAD | grep -v "(HEAD" | head -n 1 | cut -c 3-
