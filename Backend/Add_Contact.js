var addContact = function (sqlConn, contactData) {
    // Contact data - simple JSON object
    var contact = contactData.contact;

    //Address data - array of multiple addresses
    var address = contactData.address;

    // Phone data - array of multiple phones
    var phone = contactData.phone;

    // Date data - simple JSON object
    var date = contactData.date;

    console.log("Inside the add contact module.\n");
    var sql = "insert into dbd_class.contact (fname, mname, lname) values (?,?,?);";
    sqlConn.query(sql, [contact.fname, contact.mname, contact.lname], function (error, result) {
        if (error) throw error;
        console.log("Inserted\n");
    })
}

module.exports = addContact;
