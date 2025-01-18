db = db.getSiblingDB('webapi_db');

db.createUser({
    user: "root",
    pwd: "password",
    roles: [ "root" ]
})

db.createUser({
    user: "appuser",
    pwd: "apppassword",
    roles: [{ role: "readWrite", db: "webapi_db" }]
});

db.createCollection('users');
