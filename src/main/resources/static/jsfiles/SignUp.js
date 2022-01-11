    function registrationFormSubmitted() {

            let object = {};
            $('#form-registration [name]').each(function () {
                object[this.name] = this.value;
            });
            /*
            Second way to retrieve object from the form
             let object = {
                firstName : $("#floatingFistName").val(),
                lastName : $("#floatingSurname").val(),
                email : $("#floatingInput").val(),
                password : $("#floatingPassword").val()
            }
             */
            let registrationRequest = JSON.stringify(object);
            sendRegistrationRequest(registrationRequest);
    }


    function sendRegistrationRequest(registrationRequest) {

        //$.post('http://localhost:8080/registration', registrationRequest);


        $.ajax('http://localhost:8080/registration',//request url
            { //options
                type: 'POST',
                contentType: 'application/json',
                data: registrationRequest,
                dataType: 'json',
                //complete: compelete is a callback function to be executed when request finishes
                error: function (e) {
                    console.log(e)
                    alert("Error");
                },
                success: function (result) { //TODO: does not work, change or try it with vanilla Ajax
                    if (result.status === "success") {
                        let testObject = JSON.parse(result.data);
                        alert("Registration complete" + testObject);
                    }
                    //alert("Registration complete" + testObject);
                    console.log(result.data);
                }
            });
    }





