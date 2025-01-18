db.getSiblingDB("admin").createUser({
    user: "signoz",
    pwd: "signoz",
    roles: [{ role: 'clusterMonitor', db: 'admin' }]
})

db.getSiblingDB('webapi_db').createUser({
    user: "appuser",
    pwd: "apppassword",
    roles: [{ role: "readWrite", db: "webapi_db" }]
});

db.getSiblingDB('webapi_db').createCollection('users');
