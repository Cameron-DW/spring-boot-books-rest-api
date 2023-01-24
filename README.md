# Spring Boot, Spring Security, JWT, JPA, MySQL Rest API
Restful CRUD API where you can create an account, create different Bookshelfs and add created Books to these Bookshelfs using Spring Boot, MySQl, JPA and Hibernate. Spring Security ensures that requests need to authorized with JSON Web Tokens that are generated for Users when initially signed up and later when signed in using the correct email and password. ADMIN accounts can access any endpoint and USER accounts can only access endpoints relating to their specific resources.

![ER Diagram](https://user-images.githubusercontent.com/89137794/214269079-aa6ee6a7-a0d5-4187-ba71-db9e96f678e1.png)


### Auth

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/signup | Sign Up | [JSON](#sign-up) |
| POST   | /api/auth/signin | Sign In | [JSON](#sign-in) |


### User

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| GET   | /api/users | Get all Users | |
| GET   | /api/users/{userId} | Get User by id |  |
| PUT   | /api/users/{userId} | Update User by id | [JSON](#update-user) |
| DELETE   | /api/users/{userId} | Delete User by id | |


### Bookshelf

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/users/{userId}/bookshelfs | Create new Bookshelf for a User | [JSON](#create-bookshelf) |
| GET   | /api/bookshelfs | Get all Bookshelfs |  |
| GET   | /api/users/{userId}/bookshelfs | Get all Bookshelfs of a User |  |
| GET   | /api/bookshelfs/{bookshelfId} | Get Bookshelf by id |  |
| PUT   | /api/bookshelfs/{bookshelfId} | Update Bookshelf by id | [JSON](#update-bookshelf) |
| DELETE   | /api/bookshelfs/{bookshelfId} | Delete Bookshelf by id |  |

### Book

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/bookshelfs/{bookshelfId}/books | Create new Book and add it to Bookshelf | [JSON](#create-book) |
| POST   | /api/bookshelfs/{bookshelfId}/books | Add Existing Book to Bookshelf | [JSON](#add-existing-book) |
| GET   | /api/books | Get all Books |  |
| GET   | /api/bookshelfs/{bookshelfId}/books | Get all Books of a Bookshelf |  |
| GET   | /api/books/{bookId} | Get Book by id |  |
| PUT   | /api/books/{bookId} | Update Book by id | [JSON](#update-book) |
| DELETE   | /api/books/{bookId} | Delete Book by id |  |
| DELETE   | /api/bookshelfs/{bookshelfId}/books/{bookId} | Delete Book from Bookshelf by id |  |



## Request Body Examples


##### <a id="sign-up">Sign Up -> /api/auth/signup</a>
```json
{
	"firstName": "Joe",
	"lastName": "Smith",
  	"email": "joesmith@gmail.com",
	"password": "password123",
	"role": "USER"
}
```

##### <a id="sign-in">Sign In -> /api/auth/signin</a>
```json
{
  	"email": "joesmith@gmail.com",
	"password": "password123"
}
```

##### <a id="update-user">Update User-> /api/user/{userId}</a>
```json
{
  	"firstName": "JoeUpdated",
	"lastName": "SmithUpdated",
  	"email": "joesmithupdated@gmail.com",
	"password": "password123updated",
	"role": "ADMIN"
}
```

##### <a id="create-bookshelf">Create Bookshelf-> /api/user/{userId}/bookshelfs</a>
```json
{
  	"name": "To Read",
	"description": "List of Books that I want to read."
}
```

##### <a id="update-bookshelf">Update Bookshelf-> /api/bookshelfs/{bookshelfId}</a>
```json
{
  	"name": "To Read Updated",
	"description": "List of Books that I want to read Updated."
}
```

##### <a id="create-book">Create Book and add it to Bookshelf-> /api/bookshelfs/{bookshelfId}/books</a>
```json
{
  	"name": "Romeo and Juliet",
	"rating": 8
	"review": "I gave this book a rating of 8 because..."
}
```

##### <a id="add-existing-book">Add Existing Book to Bookshelf-> /api/bookshelfs/{bookshelfId}/books</a>
```json
{
  	"id": 2
}
```

##### <a id="update-book">Update Book-> /api/books/{bookId}</a>
```json
{
  	"name": "Romeo and Juliet Updated",
	"rating": 10
	"review": "I gave this book a rating of 10 because..."
}
```
