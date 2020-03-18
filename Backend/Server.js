var HTTP = require("http")
var URL_PARSER = require("url")
var MYSQL = require("mysql")
var AXIOS = require("axios")
var ADD_CONTACT = require("./Add_Contact.js")
var FETCH_CONTACT = require("./Fetch_Contact.js")
var DELETE_CONTACT = require("./Delete_Contact.js")
var UPDATE_CONTACT = require("./Update_Contact.js");

// Create a MySQL Connection here
var sqlConn = MYSQL.createConnection({ host: "127.0.0.1", user: "sreekar", password: "sreekar2019" });
sqlConn.connect(function (error) {
    if (error) throw error;
    console.log("MySQL Connection Successful!\n");
});

/**
 * Handles the HTTP request.
 * Takes the data in the request as JSON object.
 */
function processHttpRequest (data, url, res) {
    var parsedURL = URL_PARSER.parse(url, true);
    var action = parsedURL.pathname;

    if (action == "/add") {
        res.write("Add Contact");
        console.log("Insert the contact here.");
        ADD_CONTACT.addCon(sqlConn, data, res)
    } else if (action == "/update") {
        res.write("Modify Contact\n");
        console.log("Update the contact here.");
        UPDATE_CONTACT(sqlConn, data, res);
    }
}

function handleGetRequest (req, res) {
    var parsedURL = URL_PARSER.parse(req.url, true);
    FETCH_CONTACT(parsedURL.query, res, sqlConn);
}

// Handle the POST Http requests here
function handlePostRequest (req, res) {
    var data = [];
    req.on("data", (chunk) => {
        data.push(chunk);
    })
    .on('end', () => {
        data = JSON.parse(data);
        // console.log("*** Data ***\n" + JSON.stringify(data));
        processHttpRequest(data, req.url, res);
    });
}

// Handle the DELETE Http requests here
function handleDeleteRequest (req, res) {
    var parsedUrl = URL_PARSER.parse(req.url, true);
    DELETE_CONTACT(parsedUrl.query.contact_id, sqlConn, res);
}

// Create the HTTP Server on port 8000
HTTP.createServer(function (req, res) {
    // console.log("====== Request: "+req.id + " &&& Response: "+res.id+" ======");
    if (req.method == "POST") {
        console.log("HTTP Post Request Made.")
        handlePostRequest(req, res);
    }
    else if (req.method == "GET") {
        console.log("HTTP Get Request Made.");
        handleGetRequest(req, res);
    }
    else if (req.method == "DELETE") {
        console.log("HTTP Delete Request Made");
        handleDeleteRequest(req, res);
    }
}).listen(3000, "0.0.0.0");
