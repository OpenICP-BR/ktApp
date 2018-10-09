@echo OFF

set VERSION=%1
set LAUNCH4JC="C:\Program Files (x86)\Launch4j\launch4jc.exe"
set NSIS="C:\NSIS\Bin\makensis.exe"

rem "C:\Program Files\Java\jdk-10.0.2\bin\javapackager" -deploy -native exe -appclass com.github.OpenICP_BR.ktApp.MyAppKt -srcdir target -outdir target -outfile Hi123.exe
@echo Copying jar
copy target\ktApp-%VERSION%.jar target\ktApp.jar
if %errorlevel% neq 0 exit /b %errorlevel%
@echo Running launch4j
%LAUNCH4JC% other_res\win\Launch4j.xml
if %errorlevel% neq 0 exit /b %errorlevel%
@echo Running NSIS
%NSIS% /V4 other_res\win\installer.nsi
if %errorlevel% neq 0 exit /b %errorlevel%
