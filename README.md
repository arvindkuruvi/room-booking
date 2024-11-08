# room-booking


Just have a docker desktop and run the below cmds

docker-compose up --build -d

docker-compose down


##############################################################
User service:
##############################################################

Swagger url : 
http://localhost:8080/swagger-ui/index.html#/

save a new user

Method: Post
URL: http://localhost:8080/users/save



Delete an user

Method: Delete
URL: http://localhost:8080//users/{id}



retreive user details

Method: Get
URL: http://localhost:8080//users/{id}



##############################################################
booking-service
##############################################################

Swagger url : 
http://localhost:8082/swagger-ui/index.html#/

Creating a new event

Method: Post
URL: http://localhost:8082/booking/event



Approving an event

Method: Post
URL: http://localhost:8082/booking/approve



get all unqpproved events

Method: Get
URL: http://localhost:8082/booking/unapproved


get all events

Method: Get
URL: http://localhost:8082/booking/events


##############################################################
room-servic
##############################################################e

save a new room

Method: POST
URL: http://localhost:8081/rooms


Method: Delete
URL: http://localhost:8081/rooms/{id}


##############################################################
approval-service
##############################################################

Swagger url : 
http://localhost:8083/swagger-ui/index.html#/

Approve an event

Method: POST
URL: http://localhost:8083/approve/event


