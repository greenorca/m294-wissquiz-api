# m294-wissquiz-api
This is the Docker-Compose file for Wiss-Quiz (M294).

The Springboot-based API that provides:

1. GET http://localhost:8080/questions/quiz/1 # delivers 3 random questions and answers for category with ID 1
2. GET http://localhost:8080/questions/ # delivers all questions and answers for category with ID 1
3. POST http://localhost:8080/questions/1 # adds a new question for cat ID 1. Follow this data structure:

    ```json
    {
        "question": "What is the name of the biggest city?",
        "answers": [ {
            "answer": "Tokyo",
            "correct": true
          }, {
            "answer": "Shanghai",
            "correct": false
          },{
            "answer": "Sao Paolo",
            "correct": false
            }
        ]
    }
    ```

4. GET http://localhost:8080/category/ # fetch available categories
5. POST http://localhost:8080/category?name=Databases # adds another category

## Usage

1. Start the stack with docker compose up. Required docker images are available in public space.
2. for demonstration purpose, the database is writable and the API is not password protected
