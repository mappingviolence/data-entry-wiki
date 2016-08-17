#!/bin/sh

echo "==Build Started=="

echo "Installing Mapping Violence Core to maven"

MAPPINGVIOLENCE_CORE_VERSION=0.9.1

mvn install:install-file \
-Dfile=$PWD/lib/mappingviolence-core-v$MAPPINGVIOLENCE_CORE_VERSION.jar \
-DgroupId=org.mappingviolence -DartifactId=core \
-Dversion=0.9.1 \
-Dpackaging=jar

echo "Finished installing Mapping Violence Core"

echo "Deleting previous builds"

mvn clean

echo "Finished deleting previous builds"

echo "Building new project"

mvn install

echo "Finished building new project"

echo "==Build Complete=="
