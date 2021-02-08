# kubik-commerce

The project has been split into 2 different modules.

* users: It will manage all the operations of the users. Due to the logic it can contain it's been decided to create a single module for it. It has also 
been considered it might be needed for the use of some future modules, so it makes sense to keep it separate and therefore can interact with different 
modules
  
* store: It will take care of handling the different stores and products associated. Due to the dependency a product has with a store it makes sense to
create a single module for the management of both.
  
A postman collection has been added with all the endpoints and operations allowed by the different modules.

### Extra considerations

Since it has been requested to try to keep it simple I want to give some considerations or actions I would take in case of leading this to a real life
project.

1. Considering it has been requested a powerful search engine, I believe the best approach would be separating the writing operations for the reading
operations for the store and product. There should be different databases which should be synchronized.
2. More like an improvement, due to the need of having the owner receiving an email when a product is deleted and in a very likely situation of having
more notifications will be delivered, a notification service could be built.
3. Regarding transactions, an event broker should be implemented in order to check the user's balance, product availability and if the user performing the
transaction actually exists.
4. An API gateway should be implemented in order to make sure the clients have access through a single point and no need to the different services urls.
It would also help to get security and logging in place.