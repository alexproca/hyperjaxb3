setlocal
set JAVA_HOME=C:\Programme\Java\jdk1.5.0_06\
call mvn -DperformRelease=true clean deploy
endlocal

