# Prerequisites
* Postman: For input and output.
* MySQL: For database connectivity.
   That's it.

---

# To run the Application
* Set the URL, username, and password in src/main/resources/application.properties file.
* Run src/main/java/com.project.restapi/RestApiApplication.java

---

# Application's functionality:

## Authors

#### Functions | HTTP Methods | URLs | Response Codes

Create | POST | /authors | 201 / 404

READ(One) | GET | /authors/{id} | 200 / 404

READ(All) | GET | /authors | 200 / 200

UPDATE | PUT | /authors/{id} | 200 / 404

UPDATE(Partial) | PATCH | /authors/{id} | 200 / 404

DELETE | DELETE | /authors/{id} | 204 / 204

## Books

#### Functions | HTTP Methods | URLs | Response Codes

Create | PUT | books/{isbn} | 201 / 404

READ(One) | GET | /books/{isbn} | 200 / 404

READ(All) | GET | /books | 200 / 200

UPDATE | PUT | /books/{isbn} | 200 / 404

UPDATE(Partial) | PATCH | /books/{isbn} | 200 / 404

DELETE | DELETE | /books/{isbn} | 204 / 204

---

## Request Format

### Authors
    {
        "id": 1,
        "name": "Pietro Parkour",
        "age": 22
    }

### Books
    {
        "isbn": "1234567891234",
        "title": "The Formidable Foe",
        "author": {
            "id": 1,
            "name": "Pietro Parkour",
            "age": 22
        }
    }


#### NOTE: Field "id" is optional in both formats.