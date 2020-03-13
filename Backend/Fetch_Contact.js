var responseObj;
var mainContactObject;

function getAllDates (sqlConn, id) {
    var sqlQuery = "select * from dbd_class.date where contact_id = ?;";
    sqlConn.query(sqlQuery, id, (err, res)=>{
        if (err) throw err;
        var dates = []
        Object.keys(res).forEach(key => {
            var a = res[key];
            var obj = {_id: a.Date_Id, contactId: a.Contact_Id, dateType: a.Date_Type, date: a.Date};
            dates.push(obj);
        });
        mainContactObject.dateData = dates;
        responseObj.write(JSON.stringify(mainContactObject));
        console.log("========== JSON FINAL ===========\n"+JSON.stringify(mainContactObject));
        responseObj.end();
    })
}

function getAllPhones(sqlConn, id) {
    var sqlQuery = "select * from dbd_class.phone where contact_id = ?;";
    sqlConn.query(sqlQuery, id, (err, res)=>{
        if (err) throw err;
        var phones = []
        Object.keys(res).forEach(key => {
            var a = res[key];
            var obj = {_id: a.Phone_Id, contactId: a.Contact_Id, phoneType: a.Phone_Type, areaCode: a.Area_Code, number: a.Number};
            phones.push(obj);
        });
        mainContactObject.phoneData = phones;
        getAllDates(sqlConn, id);
    })
}

function getAddressesOfContact (sqlConn, id) {
    var sqlQuery = "select * from dbd_class.address where contact_id = ?;";
    sqlConn.query(sqlQuery, id, (err, res)=>{
        if (err) throw err;
        var addresses = []
        Object.keys(res).forEach(key => {
            var a = res[key];
            var obj = {_id: a.Address_Id, contactId: a.Contact_Id, addressType: a.Address_Type, address: a.Address, city: a.City, state: a.State, zipcode: a.Zipcode};
            console.log("*** Address ***\n"+JSON.stringify(obj))
            addresses.push(obj);
        });
        mainContactObject.addressData = addresses;
        getAllPhones(sqlConn, id);
    })
}

// Gets the contact with specific ID
function getContactWithID (sqlConn, id) {
    mainContactObject = {nameData:"", addressData: "", phoneData: "", dateData: ""}
    var sqlQuery = "select * from dbd_class.contact where contact_id = ?;";
    sqlConn.query(sqlQuery, id, (err, result) => {
        if (err) throw err;
        Object.keys(result).forEach(key => {
            var contact = result[key];
            mainContactObject.nameData = {fname: contact.Fname, mname: contact.Mname, lname: contact.Lname, _id:contact.contact_id};
            getAddressesOfContact(sqlConn, id)
        }) 
    })
}

// Gets all the contacts that match the search query
function getAllContacts (sqlConn, queryParams) {
    console.log(queryParams);
    var sqlQuery = "select * from dbd_class.contact c " +
    "join " +
    "(select distinct contact_id from dbd_class.address " +
    "where address like ? or city like ? or state like ? or zipcode like ? " +
    "union " +
    "select distinct contact_id from dbd_class.phone " +
    "where area_code like ? or `number` like ? " +
    "union " +
    "select contact_id from dbd_class.contact " +
    "where fname like ? or mname like ? or lname like ?) cl " +
    "on cl.contact_id = c.contact_id;";
    var criteria = "%"+queryParams.search_criteria+"%";
    sqlConn.query(sqlQuery, [criteria, criteria, criteria,criteria, criteria, criteria,criteria, criteria, criteria], 
        (err, result) => {
        if (err) throw err;
        var allContacts = []
        Object.keys(result).forEach(key => {
            var row = result[key];
            var name = {nameData: {_id: row.contact_id, fname: row.Fname, mname: row.Mname, lname: row.Lname}};
            allContacts.push(name);
        });
        console.log(JSON.stringify({contacts: allContacts}));
        responseObj.write(JSON.stringify(allContacts));
        responseObj.end();
    });
}

// Fetches the list of contacts that match criteria
var fetchContact = function (queryParams, res, sqlConn) {
    responseObj = res;
    if ('search_criteria' in queryParams) {
        getAllContacts(sqlConn, queryParams);
    }
    else if ('contact_id' in queryParams) {
        console.log(queryParams.contact_id)
        getContactWithID(sqlConn, queryParams.contact_id);
    }
}

module.exports = fetchContact;