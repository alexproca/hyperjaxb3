setlocal
set JAVA_HOME=%JAVA6_HOME%
call mvn -X -P samples,templates,tests,fullTests clean install >std 2>err
endlocal