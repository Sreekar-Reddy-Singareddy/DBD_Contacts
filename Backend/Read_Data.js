var FS = require("fs");
var MYSQL = require("mysql");
var RL = require("readline");

function someFunc (newLine, sqlConn) {
    var conData = newLine.split(",");

    var q = "insert into dbd_class.contact (contact_id,fname, mname, lname) values (?,?,?,?);";
    var insertedContactId = parseInt(conData[0],10);
    console.log(insertedContactId);
    sqlConn.query(q, [insertedContactId, conData[1], conData[2], conData[3]], (err, res) => {
        if (err) throw err;
        if (conData[6] != "" && conData[7] != "" && conData[8] != "" && conData[9] != "") {
            if (conData[9] != "") {
                var zipcode = parseInt(conData[9], 10);
                q = "insert into dbd_class.address (contact_id, address_type, address, city, state, zipcode) values (?,?,?,?,?,?);";
                sqlConn.query(q, [insertedContactId, "home", conData[6], conData[7], conData[8], zipcode], (err, res) => {
                    if (err) throw err;
                });
            }
            else {
                q = "insert into dbd_class.address (contact_id, address_type, address, city, state) values (?,?,?,?,?);";
                sqlConn.query(q, [insertedContactId, "home", conData[6], conData[7], conData[8]], (err, res) => {
                    if (err) throw err;
                });
            }
        }
        
        if (conData[11] != "" && conData[12] != "" && conData[13] != "" && conData[14] != "") {
            if (conData[14] != "") {
                q = "insert into dbd_class.address (contact_id, address_type, address, city, state, zipcode) values (?,?,?,?,?,?);";
                sqlConn.query(q, [insertedContactId, "work", conData[11], conData[12], conData[13], parseInt(conData[14],10)], (err, res) => {
                    if (err) throw err;
                });
            }
            else {
                q = "insert into dbd_class.address (contact_id, address_type, address, city, state) values (?,?,?,?,?);";
                sqlConn.query(q, [insertedContactId, "work", conData[11], conData[12], conData[13]], (err, res) => {
                    if (err) throw err;
                });
            }
        }


        if (conData[4] != "") {
        q = "insert into dbd_class.phone (contact_id, phone_type, area_code, number) values (?,?,?,?);"
        var numComps = conData[4].split("-");
        areaCode = parseInt(numComps[0], 10);
        number = parseInt(numComps[1] + numComps[2], 10);
        sqlConn.query(q, [insertedContactId, "home", areaCode, number], (err, res) => {
            if (err) throw err;
        });
    }
        if (conData[5] != "") {
        q = "insert into dbd_class.phone (contact_id, phone_type, area_code, number) values (?,?,?,?);"
        numComps = conData[5].split("-");
        areaCode = parseInt(numComps[0], 10);
        number = parseInt(numComps[1] + numComps[2], 10);
        sqlConn.query(q, [insertedContactId, "cell phone", areaCode, number], (err, res) => {
            if (err) throw err;
        });
    }
        if (conData[10] != "") {
        q = "insert into dbd_class.phone (contact_id, phone_type, area_code, number) values (?,?,?,?);"
        numComps = conData[10].split("-");
        areaCode = parseInt(numComps[0], 10);
        number = parseInt(numComps[1] + numComps[2], 10);
        sqlConn.query(q, [insertedContactId, "work", areaCode, number], (err, res) => {
            if (err) throw err;
        });
    }

    if (conData[15] != "") {
        q = "insert into dbd_class.date (contact_id, date_type, date) values (?,?,?);";
        var dateComps = conData[15].split("-");
        var date = new Date(dateComps[0], dateComps[1]-1, dateComps[2]);
        sqlConn.query(q, [insertedContactId, "Birthday", date], (err, res) => {
            if (err) throw err;
        });
    }
    });
}

// MySQL Connection
var sqlConn = MYSQL.createConnection({ host: "127.0.0.1", user: "sreekar", password: "sreekar2019" });
sqlConn.connect(function (error) {
    if (error) throw error;
    console.log("* MySQL Connection Successful!\n");
});

var linereader = RL.createInterface({
    input: FS.createReadStream("contacts.csv"),
    output: process.stdout,
    terminal: false
});

var header = true;
linereader.on("line", (newLine) => {
    if (header) {
        header = false;
        return;
    }
    someFunc(newLine, sqlConn);
});