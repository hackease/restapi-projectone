- Prerequisites:
1. Postman: For input and output.
2. MySQL: For database connectivity.
That's it.

- To run the Application:
1. Set the URL, username, and password in src/main/resources/application.properties file.
2. Run src/main/java/com.project.restapi/RestApiApplication.class


--------------------------------
Application's functionality:
--------------------------------


                            - Authors -
-----------------------------------------------------------------
    Functions      HTTP             URLs            Response
                   Methods                          Codes
-----------------------------------------------------------------
CREATE          |   POST    |   /authors        |   201 / 404   |
READ(1)         |   GET     |   /authors/{id}   |   200 / 404   |
READ(*)         |   GET     |   /authors        |   200 / 200   |
UPDATE          |   PUT     |   /authors/{id}   |   200 / 404   |
UPDATE(Partial) |   PATCH   |   /authors/{id}   |   200 / 404   |
DELETE          |   DELETE  |   /authors/{id}   |   204 / 204   |
-----------------------------------------------------------------


                            - Books -
-----------------------------------------------------------------
    Functions      HTTP             URLs            Response
                   Methods                          Codes
-----------------------------------------------------------------
CREATE          |   PUT     |   /books/{isbn}   |   201 / 404   |
READ(1)         |   GET     |   /books/{isbn}   |   200 / 404   |
READ(*)         |   GET     |   /books          |   200 / 200   |
UPDATE          |   PUT     |   /books/{isbn}   |   200 / 404   |
UPDATE(Partial) |   PATCH   |   /books/{isbn}   |   200 / 404   |
DELETE          |   DELETE  |   /books/{isbn}   |   204 / 204   |
-----------------------------------------------------------------


--------------------------------
Input format: (Postman -> body)
--------------------------------

          - Authors -
{
    "id": 1,
    "name": "Pietro Parkour",
    "age": 22
}


            - Books -
{
    "isbn": "1234567891234",
    "title": "The Formidable Foe",
    "author": {
        "id": 1,
        "name": "Pietro Parkour",
        "age": 22
    }
}


NOTE: Field "id" is optional in both formats.