// ID of newly inserted contact
var insertedContactId;
var contactJsonMain;
var responseObj;

/**
 * This method inserts the secondary details of the contact
 * into the database. Data is taken from the global JSON object.
 * @param {MYSQL Connection} sqlConn 
 */
function insertSecondaryDetails (sqlConn) {
    var sqlQuery;

    // Insert Addresses
    var addresses = contactJsonMain.addressData;
    var addressesListSize = addresses.length, addressesInsertedSize = 0;
    sqlQuery = "insert into dbd_class.address (contact_id, address_type, address, city, state, zipcode) values (?,?,?,?,?,?);";
    for (var i in addresses) {
        // Insert each address object into database
        var addressObj = addresses[i];
        var values = [addressObj.contactId, addressObj.addressType, 
            addressObj.address, addressObj.city, addressObj.state, addressObj.zipcode];
        console.log(values)
        sqlConn.query(sqlQuery, values, 
            (error, result) => {
                if (error) throw error;
                // After a successful insert, update the counter
                console.log("Inserted "+addressesInsertedSize+" addresses of "+addressesListSize);
                addressesInsertedSize++;
                // If all addresses are inserted, then end the response
                if (addressesInsertedSize == addressesListSize) {
                    responseObj.end();
                }
        });
    }

    // Insert Phones
    var phones = contactJsonMain.phoneData;
    var sizeOfPhones = phones.length, sizeOfPhonesInserted = 0;
    sqlQuery = "insert into dbd_class.phone (contact_id, phone_type, area_code, number) values (?,?,?,?);";
    for (var i in phones) {
        // Insert each phone into the database
        var phoneObj = phones[i];
        var values = [phoneObj.contactId, phoneObj.phoneType, phoneObj.areaCode, phoneObj.number];
        sqlConn.query(sqlQuery, values, (error, result) => {
            if (error) throw error;
            // After a successful insert, update the counter
            console.log("Inserted "+sizeOfPhonesInserted+" phones of "+sizeOfPhones);
            sizeOfPhonesInserted++;
            // If all phones are inserted, then end the response
            if (sizeOfPhonesInserted = sizeOfPhones) {
                responseObj.end();
            }
        });
    }

    // Insert Dates
    var dates = contactJsonMain.dateData;
    var sizeOfDates = dates.length, sizeOfInsertedDates = 0;
    sqlQuery = "insert into dbd_class.date (contact_id, date_type, date) values (?,?,?);";
    for (var i in dates) {
        // Insert each date into the database
        var dateObj = dates[i];
        var values = [dateObj.contactId, dateObj.dateType, new Date(dateObj.date)];
        sqlConn.query(sqlQuery, values, (error, result) => {
            if (error) throw error;
            // After a successful insert, update the counter
            console.log("Inserted "+sizeOfInsertedDates+" dates of "+sizeOfDates);
            sizeOfInsertedDates++;
            // If all dates are inserted, then end the response
            if (sizeOfInsertedDates == sizeOfDates) {
                responseObj.end();
            }
        });

    }
}

// Method to get the newly inserted contact's ID
function setInsertedContactId (sqlConn) {
    var sqlQuery = "select last_insert_id() as ID;"
    sqlConn.query(sqlQuery, (err, result) => {
        if (err) throw err;
        Object.keys(result).forEach(key => {
            insertedContactId = result[key].ID; // result[key] is the row from database
        })

        // Set this ID to all the other details of this contact

        for (var i in contactJsonMain.addressData) contactJsonMain.addressData[i].contactId = insertedContactId;
        for (var i in contactJsonMain.phoneData) contactJsonMain.phoneData[i].contactId = insertedContactId;
        for (var i in contactJsonMain.dateData) contactJsonMain.dateData[i].contactId = insertedContactId;

        // Insert the secondary data of this contact
        insertSecondaryDetails(sqlConn)
    })
}

// Method to insert new contact into database
var addContact = function (sqlConn, contactJsonObject, res) {
    console.log("Inside the add contact module.\n");
    responseObj = res;
    contactJsonMain = contactJsonObject;

    // Contact data - simple JSON object
    // Insert the contact primary data into database
    var contact = contactJsonObject.nameData;
    var sqlQuery = "insert into dbd_class.contact (fname, mname, lname) values (?,?,?);";
    sqlConn.query(sqlQuery, [contact.fname, contact.mname, contact.lname], function (error, result) {
        if (error) throw error;
        console.log("Inserted: "+result.toString());
        // Once the new contact primary details are inserted, get their ID
        setInsertedContactId(sqlConn);
    })
}

module.exports = addContact;
