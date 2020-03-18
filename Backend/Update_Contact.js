var responseObj;

var updateFun = function (sqlConn, data, res) {
    responseObj = res;
    console.log("========= Update Data =========\n"+JSON.stringify(data));
    
    // Update the contact primary details
    var nameObj = data.nameData;
    sqlQuery = "update dbd_class.contact set fname = ?, mname = ?, lname = ? where contact_id = ?;";
    sqlConn.query(sqlQuery, [nameObj.fname, nameObj.mname, nameObj.lname, nameObj._id], (err, result) => {
        if (err) throw err;
        // Updated the contact primary details successfully
        // Rewrite the secondary details now
        responseObj.write("1");
        responseObj.end();
    });
}

module.exports = updateFun;