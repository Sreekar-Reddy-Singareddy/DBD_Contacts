var responseObj;

var deleteFun = function (id, sqlConn, res) {
    responseObj = res;
    var sqlQuery = "delete from dbd_class.contact where contact_id = ?;";
    console.log("**** Deleting **** \n"+id);
    sqlConn.query(sqlQuery, id, (err, result) => {
        if (err) throw err;
        responseObj.write("1");
        responseObj.end();
    });
}

module.exports = deleteFun;