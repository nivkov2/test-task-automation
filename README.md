# Platform API functional tests #

## Prerequisites ##

git clone https://github.com/nivkov2/test-task-automation.git
cd test-task-automation

Ensure you have the following installed:
-	Java 21
- 	Gradle (latest version)

## Running tests ##

Run all tests (localhost):

```bash
./gradlew clean test
```

## Reporting ##

To generate and host reports locally run:

```bash
./gradlew allureReport allureServe
```

