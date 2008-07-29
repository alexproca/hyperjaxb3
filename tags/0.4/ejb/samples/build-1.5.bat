setlocal
set JAVA_HOME=C:\Programme\Java\jdk1.5.0_06\
call mvn -X -Dmaven.test.skip=true clean install >std 2>err
endlocal