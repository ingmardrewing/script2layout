all:
	mvn clean package

run: all
	java -jar target/script2layout-1.0.1-SNAPSHOT.jar script.txt

test:
	mvn clean test

zip: all
	jar2app target/script2layout-1.0.1-SNAPSHOT.jar
	mv script2layout-1.0.1-SNAPSHOT.app script2layout.app
	zip -r script2layout script2layout.app
	rm -rf script2layout.app
	mvn clean

