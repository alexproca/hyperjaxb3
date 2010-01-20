rd /q/s target
mkdir target
jar cvf0 bundles/hibernate-annotations-3.3.0.ga-bundle.jar -C repository/org/hibernate/hibernate-annotations/3.3.0.ga pom.xml -C repository/org/hibernate/hibernate-annotations/3.3.0.ga hibernate-annotations-3.3.0.ga.jar
jar cvf0 bundles/hibernate-entitymanager-3.3.1.ga-bundle.jar -C repository/org/hibernate/hibernate-entitymanager/3.3.1.ga hibernate-entitymanager-3.3.1.ga.jar -C repository/org/hibernate/hibernate-entitymanager/3.3.1.ga pom.xml
jar cvf0 bundles/hibernate-commons-annotations-3.0.0.ga-bundle.jar -C repository/org/hibernate/hibernate-commons-annotations/3.0.0.ga pom.xml -C repository/org/hibernate/hibernate-commons-annotations/3.0.0.ga hibernate-commons-annotations-3.0.0.ga.jar
jar cvf0 bundles/hibernate-validator-3.0.0.ga-bundle.jar -C repository/org/hibernate/hibernate-validator/3.0.0.ga pom.xml -C repository/org/hibernate/hibernate-validator/3.0.0.ga hibernate-validator-3.0.0.ga.jar
jar cvf0 bundles/hibernate-search-3.0.0.cr1-bundle.jar -C repository/org/hibernate/hibernate-search/3.0.0.cr1 pom.xml -C repository/org/hibernate/hibernate-search/3.0.0.cr1 hibernate-search-3.0.0.cr1.jar