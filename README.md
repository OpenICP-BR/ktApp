# OpenICP-BR ktApp

[![Build Status](https://travis-ci.com/OpenICP-BR/ktApp.svg?branch=master)](https://travis-ci.com/OpenICP-BR/ktApp)
[![Download](https://api.bintray.com/packages/gjvnq/mvn/ktApp/images/download.svg)](https://bintray.com/gjvnq/mvn/ktApp/_latestVersion)
![JRE Version](https://img.shields.io/badge/jre-10-lightgrey.svg)
[![LGPL License](https://img.shields.io/badge/license-LGPL-green.svg)](https://www.gnu.org/licenses/lgpl-3.0.en.html)
[![Doar](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M5A72UW7FF87W)

A simple desktop app for [CAdES](https://en.wikipedia.org/wiki/CAdES_(computing)) digital signatures according to [ICP-Brasil](https://www.iti.gov.br) (Brazil's PKI) [rules](https://www.iti.gov.br/legislacao/61-legislacao/504-documentos-principais).

## Compiling and Packaging

Compile: `mvn compile`

Run: `mvn exec:java`

Generate final `.jar` and copy dependencies: `mvn package`

Generate `.dmg` file for macOS: `./bundle_osx.sh`
 * Needs: <https://github.com/sindresorhus/create-dmg>, <https://github.com/Jorl17/jar2app>

Generate `.exe` file for Windows: `./bundle_win.bat`
  * Needs: <http://launch4j.sourceforge.net/>, <http://nsis.sourceforge.net/Main_Page>
  
Generate `.deb` file for Ubuntu: `./bundle_deb.sh`

Generate `.rpm` file for Fedora: `./bundle_rpm.sh` (unfinished)

Generate `.appImage` file for Linux: `./bundle_app_image.sh` (unfinished)

### Debian/Ubuntu repository

Run: `echo "deb https://dl.bintray.com/gjvnq/deb /" | sudo tee -a /etc/apt/sources.list`

Or add the following to `/etc/apt/sources.list`:

    deb https://dl.bintray.com/gjvnq/deb /