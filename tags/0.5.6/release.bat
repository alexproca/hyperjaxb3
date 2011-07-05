rem update versions
rem call mvn clean install
rem call mvn clean install -Psamples
rem call mvn clean install -Ptests
rem call mvn clean install -Ptemplates
rem call mvn clean install -Ptutorials
call mvn clean install -Ptests
call mvn clean install -Psamples
call mvn clean install -Ptemplates
call mvn clean install -Ptutorials
rem mvn scm:tag -Dtag=%1
rem mvn -Psamples -Ptests -DperformRelease -Psonatype-oss-release clean deploy
rem update versions