# aircheck-server
Backend server for aircheck project. It is a fully Scala activator project

###Setup:

1. Install activator:

Find a way to install **Typsafe Activator** on your machine, and setup the proper environmental variables, specifically PATH variables.

2. Install MySQL

Install MySQL on your favorite platform. Use the default password `123456` for root user

3. Setup MySQL Schema:

Execute the two SQL query in create_schema.sql 

###Run the application

Simply: `activator ui` under this folder

###Android Insertion Convention:
```
POST /api/insert

{
  "density":25.0,
  "lng": 179.9999999,
  "lat":123.444444,
  "user_id":1,
  "time":"2016-09-06 20:46:00"
}

```




