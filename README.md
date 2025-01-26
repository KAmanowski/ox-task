# OX-Task

The app runs via Spring Boot with a Postgres DB.

To run:

1. `generate-db-image.bat` to spin up docker DB container.
2. Then run the app - liquibase will build the DB up for you.

Quick rundown of the app:

- We have 4 tables:
  - `retailer`
  - `manufacturer`
  - `transaction`
  - `user`
- All have endpoints.
- Token auth is done by first generating a user via endpoint, which will return a token. You then provide your username and the generated token to the DELETE endpoint.
- `TransactionController` has all of the endpoint required for the tech test.

For a full list of endpoints please open up: `http://localhost:8080/swagger-ui/index.html#/`

Thank you!
