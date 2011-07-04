rem update versions
call mvn clean install -Psamples
call mvn clean install -Ptests
call mvn clean install -Ptemplates
call mvn clean install -Ptutorials
rem mvn scm:tag -Dtag=%1
rem mvn -Psamples -Ptests -DperformRelease -Psonatype-oss-release clean deploy
rem update versions