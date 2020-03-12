var responseObj;

// Fetches the list of contacts that match criteria
var fetchContact = function (queryParams, res, sqlConn) {
    responseObj = res;
    console.log(queryParams);
    var sqlQuery = "select * from dbd_class.contact where fname like ? or mname like ? or lname like ?;";
    var criteria = "%"+queryParams.search_criteria+"%";
    sqlConn.query(sqlQuery, [criteria, criteria, criteria], 
        (err, result) => {
        if (err) throw err;
        var allContacts = []
        Object.keys(result).forEach(key => {
            var row = result[key];
            var name = {nameData: {fname: row.Fname, mname: row.Mname, lname: row.Lname}};
            allContacts.push(name);
        });
        console.log(JSON.stringify({contacts: allContacts}));
        responseObj.write(JSON.stringify(allContacts));
        responseObj.end();
    });
}

module.exports = fetchContact;