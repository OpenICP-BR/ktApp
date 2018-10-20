# DEPENDENCY: https://github.com/sindresorhus/create-dmg
# DEPENDENCY: https://github.com/Jorl17/jar2app

# ANSI escape codes
GREEN='\033[32m'
BLUE='\033[34m'
RED='\033[31m'
BLACK='\033[30m'
BOLD='\033[1m'
RESET='\033[0m'

# Set default commands
JAR2APP=jar2app
CDMG=create-dmg
MVN=mvn
ECHO=@echo -e

# Set default file locations and config
HERE:=$(shell pwd)
SRC=../src/
SRCs:=$(shell find $(SRC) -type f)
VERSION:=$(shell cd .. && ./fix_version.sh > /dev/null && ./get_version.sh)
NAME=OpenICP-BR
APP=$(NAME).app
DMG=$(NAME)-$(VERSION).dmg
JAR:=ktApp-${VERSION}.jar
JRE=jdk-10.jdk
JRE_PATH=/Library/Java/JavaVirtualMachines/${JRE}/Contents
JAR2APP_OPTS=--name "${NAME}" --icon ../other_res/osx/logo.icns --short-version "${VERSION}" --version "${VERSION}" --runtime "${JRE}" --low-res-mode --copyright="G Queiroz"

.PHONY: app dmg all clean

all: app dmg

$(JAR): ../pom.xml $(SRCs)
	cd .. && $(MVN) clean package
	$(ECHO) $(GREEN)$(BOLD)Got: $(RESET)$(BOLD)$(JAR)$(RESET)

$(APP): $(JAR)
	# Prepare JRE for bundling
	$(ECHO) $(BLUE)Preparing JRE on: $(RESET)$(BOLD)$(JRE_PATH)$(RESET)
	ls -al
	pwd
	rm -vrf ${JRE}
	mkdir -p ${JRE}/Contents/Home/jre/
	ln -s ${JRE_PATH}/MacOS ${JRE}/Contents
	ln -s ${JRE_PATH}/Home/bin ${JRE}/Contents/Home/jre/
	ln -s ${JRE_PATH}/Home/conf ${JRE}/Contents/Home/jre/
	ln -s ${JRE_PATH}/Home/jmods ${JRE}/Contents/Home/jre/
	ln -s ${JRE_PATH}/Home/lib ${JRE}/Contents/Home/jre/
	cp -Hva ${JRE_PATH}/Info.plist ${JRE}/Contents/
	$(ECHO) $(GREEN)$(BOLD)Prepared JRE on: $(RESET)$(BOLD)$(JRE_PATH)$(RESET)

	# Clean stuff
	$(ECHO) $(BLUE)Cleaning previous app if any on: $(RESET)$(BOLD)$(APP)$(RESET)
	-rm -rf ${APP}

	# Package app
	$(ECHO) $(BLUE)Packaging App Bundle on: $(RESET)$(BOLD)$(APP)$(RESET)
	cp "${JAR}" ktApp.jar
	jar2app ktApp.jar ${JAR2APP_OPTS}
	$(ECHO) $(GREEN)$(BOLD)Got App Bundle on: $(RESET)$(BOLD)$(APP)$(RESET)

	# Copy libraries
	$(ECHO) $(BLUE)Copying libraries$(RESET)
	mkdir -pv ${APP}/Contents/Java/lib
	cp -arv lib/* ${APP}/Contents/Java/lib
	$(ECHO) $(GREEN)$(BOLD)Copied libraries$(RESET)

$(DMG): $(APP)
	$(ECHO) $(BLUE)Creating dmg file on: $(RESET)$(BOLD)$(DMG)$(RESET)
	$(CDMG) "${APP}" --overwrite
	mv "$(NAME) $(VERSION).dmg" "$(DMG)"
	$(ECHO) $(GREEN)$(BOLD)Got dmg file on: $(RESET)$(BOLD)$(DMG)$(RESET)

app: $(APP)

dmg: $(DMG)

clean:
	rm -rf $(APP) $(DMG)
