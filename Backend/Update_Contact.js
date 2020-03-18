var ADD_CONTACT = require("./Add_Contact.js")

function deleteExistingSecondaryDetails(sqlConn, data, id, res) {
    sqlQuery = "delete from dbd_class.address where contact_id = ?;";
    sqlConn.query(sqlQuery, [id,id,id], (err, result) => {
        if (err) throw err;
        sqlQuery = "delete from dbd_class.phone where contact_id = ?;";
        sqlConn.query(sqlQuery, id, (err, result) => {
            if (err) throw err;
            sqlQuery = "delete from dbd_class.date where contact_id = ?;";
            sqlConn.query(sqlQuery, id, (err, result)=> {
                if (err) throw err;
                // Rewrite the secondary details now
                ADD_CONTACT.setId(id);
                ADD_CONTACT.setResponse(res);
                ADD_CONTACT.setFullContact(data);
                ADD_CONTACT.secDetails(sqlConn);
            })
        })
    });
}

var updateFun = function (sqlConn, data, res) {
    console.log("========= Update Data =========\n"+JSON.stringify(data));
    
    // Update the contact primary details
    var nameObj = data.nameData;
    sqlQuery = "update dbd_class.contact set fname = ?, mname = ?, lname = ? where contact_id = ?;";
    sqlConn.query(sqlQuery, [nameObj.fname, nameObj.mname, nameObj.lname, nameObj._id], (err, result) => {
        if (err) throw err;
        // Updated the contact primary details successfully
        // Delete all secondary details of this contact now
        deleteExistingSecondaryDetails(sqlConn, data, nameObj._id, res);
    });
}

module.exports = updateFun;