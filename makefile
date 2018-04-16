all:
	mvn clean package

run: all
	java -jar target/script2layout-1.0.1-SNAPSHOT.jar script.txt

test:
	mvn clean test

app:
	mvn package appbundle:bundle
