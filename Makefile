.PHONY: build test

build:
	./gradlew clean build installDist

run-web:
	./railways/build/install/railways/bin/railways web
