setlocal
set JAVA_HOME=%JAVA5_HOME%
mvn -DcontinuousIntegrationServer clean install >std 2>err
endlocal