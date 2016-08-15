##data-entry-wiki

#Building:

Download the mappingviolence core library jar (version $VERSION) from github to $WORKING_DIR/lib

Run mvn install:install-file -Dfile=$WORKING_DIR/lib/mappingviolence-core.jar \
-DgroupId=org.mappingviolence -DartifactId=core -Dversion=$VERSION \
-Dpackaging=jar

Run mvn clean package
