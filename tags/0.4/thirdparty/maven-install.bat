rem call mvn install:install-file -DgroupId=javax.sql	-DartifactId=jdbc-stdext	-Dversion=2.0 -Dpackaging=jar -Dfile=repository/javax/sql/jdbc-stdext/2.0/jdbc-stdext-2.0.jar -DpomFile=repository/javax/sql/jdbc-stdext/2.0/pom.xml
rem call mvn install:install-file -DgroupId=javax.resource	-DartifactId=connector -Dversion=1.0 -Dpackaging=jar -Dfile=repository/javax/resource/connector/1.0/connector-1.0.jar -DpomFile=repository/javax/resource/connector/1.0/pom.xml
rem call mvn install:install-file -DgroupId=javax.transaction	-DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar -Dfile=repository/javax/transaction/jta/1.0.1B/jta-1.0.1B.jar -DpomFile=repository/javax/transaction/jta/1.0.1B/pom.xml


rem call mvn install:install-file -Dfile=repository/org/hibernate/hibernate-entitymanager/3.3.1.ga/hibernate-entitymanager-3.3.1.ga.jar -DpomFile=repository/org/hibernate/hibernate-entitymanager/3.3.1.ga/pom.xml -DgroupId=org.hibernate -DartifactId=hibernate-entitymanager -Dversion=3.3.1.ga -Dpackaging=jar
rem call mvn install:install-file -Dfile=repository/org/hibernate/hibernate-validator/3.0.0.ga/hibernate-validator-3.0.0.ga.jar -DpomFile=repository/org/hibernate/hibernate-validator/3.0.0.ga/pom.xml -DgroupId=org.hibernate -DartifactId=hibernate-validator -Dversion=3.0.0.ga -Dpackaging=jar

rem call mvn install:install-file -Dfile=repository/com/sun/xml/xsom/xsom/20070606/xsom-20070606.jar -DpomFile=repository/com/sun/xml/xsom/xsom/20070606/pom.xml -DgroupId=com.sun.xml.xsom -DartifactId=xsom -Dversion=20070606 -Dpackaging=jar
rem call mvn install:install-file -Dfile=repository/com/sun/xml/xsom/xsom/20070606/xsom-20070606-sources.jar -DgroupId=com.sun.xml.xsom -DartifactId=xsom -Dversion=20070606 -Dpackaging=jar -Dclassifier=sources