# Recipes2Pantry

Application that helps you manage your recipes and products.

## How to run the API

1) Download the application folder from repository.
2) Open **recipes2pantry** root folder in console.
3) Use the **gradlew bootRun** command (Linux/macOS):
```
$ ./gradlew bootRun
```
or (Windows):
```
> gradlew bootRun
```

## How to test the API & check documentation

After launching the application, all endpoints with documentation can be found at address below:
```
http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs
```
To facilitate testing of the application, some data was added by **DbInitializer.class** on **dev profile** (by default).
