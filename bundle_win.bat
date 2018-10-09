set VERSION=%1
set LAUNCH4JC="C:\Program Files (x86)\Launch4j\launch4jc.exe"
set NSIS="C:\NSIS\Bin\makensis.exe"

rem "C:\Program Files\Java\jdk-10.0.2\bin\javapackager" -deploy -native exe -appclass com.github.OpenICP_BR.ktApp.MyAppKt -srcdir target -outdir target -outfile Hi123.exe
copy target\ktApp-%VERSION%.jar target\ktApp.jar
%LAUNCH4JC% other_res\win\Launch4j.xml
%NSIS% /V4 other_res\win\installer.nsi
