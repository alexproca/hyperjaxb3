setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn -X -Dmaven.test.skip=true clean install >std 2>err
endlocal