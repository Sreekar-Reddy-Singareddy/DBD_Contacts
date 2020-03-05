var http = require("http")
var urlParser = require("url")
var mysql = require("mysql")
var axios = require("axios")
var addC = require("./Add_Contact.js")

// Create a MySQL Connection here
var sqlConn = mysql.createConnection({host: "127.0.0.1", user: "sreekar", password: "sreekar2019"});
sqlConn.connect(function (error) {
    if (error) throw error;
    console.log("MySQL Connection Successful!\n");
});

// Create the HTTP Server on port 8000
http.createServer(function (req, res) {
    var data = [];
    req.on("data", (body) => {
        data.push(body);
    }).on('end',()=>{
        data = Buffer.concat(data).toString();
    });

    var parsedURL = urlParser.parse(req.url, true);
    var action = parsedURL.pathname;

    if (action == "/add") {
        res.write("Add Contact\n");
        console.log(data.contact)
        addC(sqlConn, data);
    } else if (action == "/modify") {
        res.write("Modify Contact\n");
    } else if (action == "/remove") {
        res.write("Remove Contact\n");
    } else if (action == "/retrieve") {
        res.write("Retrieve Contact\n");
    }
    res.end();
}).
    listen(8000);

// Making some duplicate requests
    var contact = {contact: {fname: "Sreekar", mname: "Reddy", lname: "Singareddy"}}
    axios.post("http://localhost:8000/add", contact)
