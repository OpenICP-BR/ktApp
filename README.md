# OpenICP-BR

## Compiling and Packaging

Compile: `mvn compile`

Run: `mvn exec:java`

Generate final `.jar` and copy dependencies: `mvn package`

Generate `.dmg` file for macOS: `./bundle_osx.sh`
 * Needs: <https://github.com/sindresorhus/create-dmg>, <https://github.com/Jorl17/jar2app>

Generate `.exe` file for Windows: `./bundle_win.bat`
  * Needs: <http://launch4j.sourceforge.net/>, <http://nsis.sourceforge.net/Main_Page>
  
Generate `.deb` file for Ubuntu: `./bundle_deb.sh` (unfinished)

Generate `.rpm` file for Fedora: `./bundle_deb.sh` (unfinished)

Generate `.appImage` file for Linux: `./bundle_app_image.sh` (unfinished)
