default: run

compile:
	./gradlew fatJar

run: build
	java -jar 'build/libs/resumeable-downloader-all-1.0.jar'
