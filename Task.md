

Create a simple spring boot app with the following conditions:


a. Create a variable(counter) that can be shared by all the clients, the initial value of the counter is 50.

b. There is an ENDPOINT that will receive two request parameter, the first one will increase the number of producer threads. The second parameter will increase the number of consumer threads.

     The response will be HTTP 201 Created success status.

c. Using MySQL, persist the request's information received by the app to the database.

d. The producer threads will increase the value of the counter while the consumer threads will decrease it. 

e. Print in the console the current value of the counter when it changes and print which producer/consumer is responsible for the change.

f. The threads will run in parallel and continue until the counter reaches 0 or 100. Persist in the database the timestamp when the counter reaches 0 or 100.

g. Create another ENDPOINT that will receive one parameter, the parameter will change the current value of the counter.

	The response will be HTTP 200 Ok success status.

h. Dockerizing the app is highly recommended and nice to have.