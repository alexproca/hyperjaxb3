setlocal
set JAVA_HOME=C:\Programme\Java\jdk1.6.0_02\
call mvn -X -Dmaven.test.skip=true clean install >std 2>err
endlocal