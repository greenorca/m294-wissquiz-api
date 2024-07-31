# m294-wissquiz-api
This is the Docker-Compose file for Wiss-Quiz (M294).

The Springboot-based API that provides:

1. GET http://localhost:8080/questions/1 # a bunch of questions and ansers for category with ID 1
2. POST http://localhost:8080/questions/1 # adds a new question for cat ID 1. Follow this data structure:

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


Start the stack with docker compose up. Required docker images are available in public.
