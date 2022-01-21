 let selectMealOrCocktail = document.querySelector("#select-create-meal-or-cocktail");

    //as soon as any option has been chosen, create form
    selectMealOrCocktail.oninput = showMealOrCocktail;

    //function to create form and then manage the requests
    function showMealOrCocktail(e) {
        //searchBar.disabled = false;
        let recipeOuterSection = document.querySelector("#create-recipe-outer-section");

        if (recipeOuterSection != null && document.querySelector("#form-create-recipe") != null) {
            recipeOuterSection.removeChild(document.querySelector("#form-create-recipe"));
        }


        if (selectMealOrCocktail.value === "meals" || selectMealOrCocktail.value === "cocktail"
            || selectMealOrCocktail.value === "updateMeal" || selectMealOrCocktail.value === "updateCocktail") {

            let formToCreateRecipe = document.createElement("form");
            formToCreateRecipe.id = "form-create-recipe";

            //===================================================
            let createRecipeInnerSection = document.createElement("div");
            createRecipeInnerSection.id = "create-recipe-inner-section";
            createRecipeInnerSection.classList.add("container-fluid");
            createRecipeInnerSection.classList.add("inner-container");

            let idRow = null;
            if (selectMealOrCocktail.value === "updateMeal" || selectMealOrCocktail.value === "updateCocktail") {
                //Recipe id
                //Recipe id
                //=====================================================================
                idRow = document.createElement("div");
                idRow.id = "firstRowAfterIntro";
                idRow.classList.add("row");

                let colId = document.createElement("div");
                colId.classList.add("col-sm-6");


                let headerId = document.createElement("h3");
                headerId.id = "idHeader";
                headerId.classList.add("name-for-form-attribute");
                headerId.innerText = "Recipe id";

                let textInputRecipeId = document.createElement("div");
                textInputRecipeId.classList.add("form-floating");
                textInputRecipeId.id = "textRecipeId";

                let inputRecipeId= document.createElement("input");
                inputRecipeId.type = "text";
                inputRecipeId.classList.add("form-control");
                inputRecipeId.id = "id";
                inputRecipeId.name = "id";

                inputRecipeId.placeholder = "Recipe id";
                inputRecipeId.required;

                let labelForRecipeId = document.createElement("label");
                labelForRecipeId.htmlFor = "id";
                labelForRecipeId.innerText = "Recipe id";




                textInputRecipeId.appendChild(inputRecipeId);
                textInputRecipeId.appendChild(labelForRecipeId);
                colId.appendChild(headerId);
                colId.appendChild(textInputRecipeId);
                idRow.appendChild(colId);


            }

            //Recipe name
            //=====================================================================
            let nameRow = document.createElement("div");
            nameRow.id = "firstRowAfterIntro";
            nameRow.classList.add("row");

            let col1 = document.createElement("div");
            col1.classList.add("col-sm-6");


            let header3 = document.createElement("h3");
            header3.id = "recipename";
            header3.classList.add("name-for-form-attribute");
            header3.innerText = "Recipe name";

            let textInputRecipeName = document.createElement("div");
            textInputRecipeName.classList.add("form-floating");
            textInputRecipeName.id = "textRecipeName";

            let inputRecipeName = document.createElement("input");
            inputRecipeName.type = "text";
            inputRecipeName.classList.add("form-control");
            inputRecipeName.id = "mealName";
            if (selectMealOrCocktail.value === "meals" || selectMealOrCocktail.value === "updateMeal") {
                inputRecipeName.name = "mealName";
            } else {
                inputRecipeName.name = "name";
            }
            inputRecipeName.placeholder = "Recipe name";
            inputRecipeName.required;

            let labelForRecipeName = document.createElement("label");
            labelForRecipeName.htmlFor = "mealName";
            labelForRecipeName.innerText = "Recipe name";


            textInputRecipeName.appendChild(inputRecipeName);
            textInputRecipeName.appendChild(labelForRecipeName);

            col1.appendChild(header3);
            col1.appendChild(textInputRecipeName);

            nameRow.appendChild(col1);

            //Recipe instructions/description
            //============================================================
            let instructionsRow = document.createElement("div");
            instructionsRow.classList.add("row");

            let col2 = document.createElement("div");

            let header32 = document.createElement("h3");
            header32.classList.add("name-for-form-attribute");
            header32.innerText = "Recipe";

            let textInputRecipeInstructions = document.createElement("div");
            textInputRecipeInstructions.classList.add("form-floating");
            textInputRecipeInstructions.id = "textRecipe";

            let inputTextArea = document.createElement("textarea");
            inputTextArea.classList.add("form-control");
            inputTextArea.classList.add("rounded-1");
            inputTextArea.rows = 5;
            inputTextArea.id = "instructions";
            inputTextArea.name = "instructions";
            inputTextArea.placeholder = "Recipe Text";
            inputTextArea.required;

            let labelForRecipeText = document.createElement("label");
            labelForRecipeText.htmlFor = "instructions";
            labelForRecipeText.innerText = "Description of recipe";

            textInputRecipeInstructions.appendChild(inputTextArea);
            textInputRecipeInstructions.appendChild(labelForRecipeText);

            col2.appendChild(header32);
            col2.appendChild(textInputRecipeInstructions);

            instructionsRow.appendChild(col2);

            let areaRow = null;
            if(selectMealOrCocktail.value === "meals" || selectMealOrCocktail.value === "updateMeal") {
                //Area
                //=====================================================================
                areaRow = document.createElement("div");
                areaRow.id = "areaRow";
                areaRow.classList.add("row");

                let col7 = document.createElement("div");
                col7.classList.add("col-sm-6");


                let header7 = document.createElement("h3");
                header7.id = "areaHeader";
                header7.classList.add("name-for-form-attribute");
                header7.innerText = "Area";

                let textInputArea = document.createElement("div");
                textInputArea.classList.add("form-floating");
                textInputArea.id = "textArea";

                let inputArea = document.createElement("input");
                inputArea.type = "text";
                inputArea.classList.add("form-control");
                inputArea.id = "area";
                inputArea.name = "area";
                inputArea.placeholder = "Area";
                inputArea.required;

                let labelForArea = document.createElement("label");
                labelForArea.htmlFor = "area";
                labelForArea.innerText = "Area";


                textInputArea.appendChild(inputArea);
                textInputArea.appendChild(labelForArea);

                col7.appendChild(header7);
                col7.appendChild(textInputArea);

                areaRow.appendChild(col7);
            }

            let glassRow = null;
            if(selectMealOrCocktail.value === "cocktail" || selectMealOrCocktail.value === "updateCocktail") {
                //Glass
                //=====================================================================
                glassRow = document.createElement("div");
                glassRow.id = "glassRow";
                glassRow.classList.add("row");

                let col8 = document.createElement("div");
                col8.classList.add("col-sm-6");


                let header8 = document.createElement("h3");
                header8.id = "glassHeader";
                header8.classList.add("name-for-form-attribute");
                header8.innerText = "Glass";

                let textInputGlass = document.createElement("div");
                textInputGlass.classList.add("form-floating");
                textInputGlass.id = "textGlass";

                let inputGlass = document.createElement("input");
                inputGlass.type = "text";
                inputGlass.classList.add("form-control");
                inputGlass.id = "glass";
                inputGlass.name = "glass";
                inputGlass.placeholder = "Glass";
                inputGlass.required;

                let labelForGlass = document.createElement("label");
                labelForGlass.htmlFor = "glass";
                labelForGlass.innerText = "Glass";


                textInputGlass.appendChild(inputGlass);
                textInputGlass.appendChild(labelForGlass);

                col8.appendChild(header8);
                col8.appendChild(textInputGlass);

                glassRow.appendChild(col8);
            }



            let alcoholRow = null;
            if(selectMealOrCocktail.value === "cocktail" || selectMealOrCocktail.value === "updateCocktail") {
                //Alcoholic
                //===========================================================
                alcoholRow = document.createElement("div");
                alcoholRow.classList.add("row");
                alcoholRow.id = "alcoholRow";

                let col9 = document.createElement("div");
                col9.classList.add("col-sm-6");


                let header9 = document.createElement("h3");
                header9.classList.add("name-for-form-attribute");
                header9.innerText = "Alcoholic";
                header9.id = "alcoholicHeader";


                let textInputAlcoholic = document.createElement("div");
                textInputAlcoholic.classList.add("form-floating");
                textInputAlcoholic.id = "textAcloholic";

                let selectAlcoholic = document.createElement("select");
                selectAlcoholic.classList.add("form-select");
                //selectCategory.classList.add("form-control");
                selectAlcoholic.name = "alcohol";
                selectAlcoholic.id = "alcohol";
                selectAlcoholic.required;

                let option1 = document.createElement("option");
                option1.value = "Alcoholic";
                let textOption = document.createTextNode(("Alcoholic"));
                option1.appendChild(textOption);

                let option2 = document.createElement("option");
                option2.value = "Alcoholic";
                let textOption2 = document.createTextNode(("Non alcoholic"));
                option2.appendChild(textOption2);

                let option3 = document.createElement("option");
                option3.value = "Alcoholic";
                let textOption3 = document.createTextNode(("Optional alcohol"));
                option3.appendChild(textOption3);

                selectAlcoholic.appendChild(option1);
                selectAlcoholic.appendChild(option2);
                selectAlcoholic.appendChild(option3);

                textInputAlcoholic.appendChild(selectAlcoholic);
                //textInputCategory.appendChild(labelForCategory);
                col9.appendChild(header9);
                col9.appendChild(textInputAlcoholic);
                alcoholRow.appendChild(col9);
            }

            ////////////////////////////////////////////////////////
            //Category
            //===========================================================
            let categoryRow = document.createElement("div");
            categoryRow.classList.add("row");

            let col3 = document.createElement("div");
            col3.classList.add("col-sm-6");


            let header33 = document.createElement("h3");
            header33.classList.add("name-for-form-attribute");
            header33.innerText = "Category";
            header33.id = "category";


            let textInputCategory = document.createElement("div");
            textInputCategory.classList.add("form-floating");
            textInputCategory.id = "textCategory";

            let selectCategory = document.createElement("select");
            selectCategory.classList.add("form-select");
            //selectCategory.classList.add("form-control");
            selectCategory.name = "category";
            selectCategory.id = "selectCategory";
            selectCategory.required;

            let listOfOptions = [];

            //ajax request to get the actual list of categories from the meal or cocktail db
            //listOfOptions = ajaxRequestCategories(selectMealOrCocktail.value);

            let ajaxRequest = new XMLHttpRequest();
            ajaxRequest.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    let responseList = JSON.parse(this.responseText);

                    responseList.forEach(item => {
                        let option = document.createElement("option");
                        option.value = item.category;
                        let textOption = document.createTextNode((item.category));
                        option.appendChild(textOption);
                        listOfOptions.push(option);

                    });

                    //sort the list
                    listOfOptions.sort(function (a, b) {
                        if (a.value < b.value) {
                            return -1;
                        }
                        if (a.value > b.value) {
                            return 1;
                        }
                        return 0;
                    });
                    listOfOptions.forEach(item => {
                        selectCategory.appendChild(item);
                    });
                }
            };

            const ourAPIUrl = "http://localhost:8080/api/recipionist/";
            let url = null;
            if (selectMealOrCocktail.value === "meals" || selectMealOrCocktail.value === "updateMeal") {
                url = ourAPIUrl + "meals/categories";
            } else if (selectMealOrCocktail.value === "cocktail" || selectMealOrCocktail.value === "updateCocktail") {
                url = ourAPIUrl + "cocktail/categories";
            }

            ajaxRequest.open("GET", url, true);
            ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            ajaxRequest.send();


            textInputCategory.appendChild(selectCategory);
            //textInputCategory.appendChild(labelForCategory);
            col3.appendChild(header33);
            col3.appendChild(textInputCategory);
            categoryRow.appendChild(col3);


            //Thumbnail
            //==========================================================
            /*
            let imageRow = document.createElement("div");
            imageRow.classList.add("row");

            let col4 = document.createElement("div");
            col4.classList.add("col");

            let header34 = document.createElement("h3");
            header34.id = "image";
            header34.classList.add("name-for-form-attribute");
            header34.innerText = "Image";

            let imageInputArea = document.createElement("div");
            imageInputArea.classList.add("input-group");
            imageInputArea.id = "uploadImage";

            let labelImage = document.createElement("label");
            labelImage.classList.add("form-label");
            labelImage.htmlFor = "customFile";

            let inputImage = document.createElement("input");
            inputImage.type = "file";
            inputImage.name = "thumbnail";
            inputImage.classList.add("form-control");
            inputImage.id = "thumbnail";
            inputImage.formEnctype = "multipart/form-data";

            imageInputArea.appendChild(labelImage);
            imageInputArea.appendChild(inputImage);
            col4.appendChild(header34);
            col4.appendChild(imageInputArea);
            imageRow.appendChild(col4);
               */

            let imageRow = document.createElement("div");
            //imageRow.id = "firstRowAfterIntro";
            imageRow.classList.add("row");

            let col4 = document.createElement("div");
            col4.classList.add("col-lg-6");


            let header34 = document.createElement("h3");
            header34.id = "image";
            header34.classList.add("name-for-form-attribute");
            header34.innerText = "Image";

            let imageInputArea = document.createElement("div");
            imageInputArea.classList.add("form-floating");
            imageInputArea.id = "uploadImage";

            let inputImage = document.createElement("input");
            inputImage.type = "text";
            inputImage.classList.add("form-control");
            inputImage.id = "thumbnail";
            inputImage.name = "thumbnail";
            inputImage.placeholder = "Image";
            //inputImage.required;

            let labelImage = document.createElement("label");
            labelImage.htmlFor = "thumbnail";
            labelImage.innerText = "Image";


            imageInputArea.appendChild(inputImage);
            imageInputArea.appendChild(labelImage);

            col4.appendChild(header34);
            col4.appendChild(imageInputArea);

            imageRow.appendChild(col4);



            //Ingredients //Measures
            //===========================================================
            let rowWithTable = document.createElement("div");
            rowWithTable.classList.add("row");
            let col5 = document.createElement("div");
            col5.classList.add("col");

            let header35 = document.createElement("h3");
            header35.classList.add("name-for-form-attribute");
            header35.innerText = "Ingredients & Measures";


            let table = document.createElement("table");
            table.classList.add("table");


            let tHead = document.createElement("thead");
            let trHead = document.createElement("tr");
            let thHead1 = document.createElement("th");
            thHead1.innerText = "Ingredients";
            let thHead2 = document.createElement("th");
            thHead2.innerText = "Measures";
            trHead.appendChild(thHead1);
            trHead.appendChild(thHead2);
            tHead.appendChild(trHead);
            table.appendChild(tHead);


            let tBody = document.createElement("tbody");

            let i = 1;
            for (i; i <= 15; i++) {
                let tr = document.createElement("tr");
                let tdIngredients = document.createElement("td");
                let inputIngredients = document.createElement("input");
                inputIngredients.type = "text";
                inputIngredients.classList.add("form-control");
                inputIngredients.name = "strIngredient" + i;
                inputIngredients.id = "strIngredient" + i;;
                inputIngredients.placeholder = "Ingredient " + i;
                tdIngredients.appendChild(inputIngredients);
                tr.appendChild(tdIngredients);

                let tdMeasures = document.createElement("td");
                let inputMeasures = document.createElement("input");
                inputMeasures.type = "text";
                inputMeasures.classList.add("form-control");
                inputMeasures.name = "strMeasure" + i;
                inputMeasures.id = "strMeasure" + i;
                inputMeasures.placeholder = "Measure " + i;
                tdMeasures.appendChild(inputMeasures);
                tr.appendChild(tdMeasures);
                tBody.appendChild(tr);
            }
            table.appendChild(tBody);

            col5.appendChild(header35);
            col5.appendChild(table);
            rowWithTable.appendChild(col5);


            //BUTTON
            let rowWithButton = document.createElement("div");
            rowWithButton.classList.add("row");

            let col6 = document.createElement("div");
            col6.classList.add("col");

            let buttonDiv = document.createElement("div");
            buttonDiv.id = "button";

            let buttonCreateRecipe = document.createElement("button");
            buttonCreateRecipe.type = "submit";
            buttonCreateRecipe.classList.add("btn");
            buttonCreateRecipe.classList.add("btn-lg");
            buttonCreateRecipe.classList.add("btn-dark");
            buttonCreateRecipe.innerText = "Submit";

            buttonDiv.appendChild(buttonCreateRecipe);
            col6.appendChild(buttonDiv);
            rowWithButton.appendChild(col6);


            //in the end
            if (selectMealOrCocktail.value === "updateMeal" || selectMealOrCocktail.value === "updateCocktail") {
                createRecipeInnerSection.appendChild(idRow);
            }

            createRecipeInnerSection.appendChild(nameRow);
            createRecipeInnerSection.appendChild(instructionsRow);
            if (selectMealOrCocktail.value === "meals" || selectMealOrCocktail.value === "updateMeal") {
                createRecipeInnerSection.appendChild(areaRow);
            }
            if (selectMealOrCocktail.value === "cocktail" || selectMealOrCocktail.value === "updateCocktail") {
                createRecipeInnerSection.appendChild(glassRow);
            }
            if (selectMealOrCocktail.value === "cocktail" || selectMealOrCocktail.value === "updateCocktail") {
                createRecipeInnerSection.appendChild(alcoholRow);
            }
            createRecipeInnerSection.appendChild(categoryRow);
            createRecipeInnerSection.appendChild(imageRow);
            createRecipeInnerSection.appendChild(rowWithTable);
            createRecipeInnerSection.appendChild(rowWithButton);


            formToCreateRecipe.appendChild(createRecipeInnerSection);
            recipeOuterSection.appendChild(formToCreateRecipe);

            //after the html elements has been created, call the function to manage HTTP request of the form
            manageTheForm();

        }
        //if nothing chosen, do nothing
        //if delete is chosen, delete with ajax request
        if (selectMealOrCocktail.value === "deleteMeal" || selectMealOrCocktail.value === "deleteCocktail") {
            let formToCreateRecipe = document.createElement("form");
            formToCreateRecipe.id = "form-create-recipe";

            //===================================================
            let createRecipeInnerSection = document.createElement("div");
            createRecipeInnerSection.id = "create-recipe-inner-section";
            createRecipeInnerSection.classList.add("container-fluid");
            createRecipeInnerSection.classList.add("inner-container");

            //Recipe id
            //=====================================================================
            let idRow = document.createElement("div");
            idRow.id = "firstRowAfterIntro";
            idRow.classList.add("row");

            let col1 = document.createElement("div");
            col1.classList.add("col-sm-6");


            let header3 = document.createElement("h3");
            header3.id = "idHeader";
            header3.classList.add("name-for-form-attribute");
            header3.innerText = "Recipe id";

            let textInputRecipeName = document.createElement("div");
            textInputRecipeName.classList.add("form-floating");
            textInputRecipeName.id = "textRecipeId";

            let inputRecipeName = document.createElement("input");
            inputRecipeName.type = "text";
            inputRecipeName.classList.add("form-control");
            inputRecipeName.id = "id";
            inputRecipeName.name = "id";

            inputRecipeName.placeholder = "Recipe id";
            inputRecipeName.required;

            let labelForRecipeName = document.createElement("label");
            labelForRecipeName.htmlFor = "id";
            labelForRecipeName.innerText = "Recipe id";

            //BUTTON
            let rowWithButton = document.createElement("div");
            rowWithButton.classList.add("row");

            let col6 = document.createElement("div");
            col6.classList.add("col");

            let buttonDiv = document.createElement("div");
            buttonDiv.id = "button";

            let buttonCreateRecipe = document.createElement("button");
            buttonCreateRecipe.type = "submit";
            buttonCreateRecipe.classList.add("btn");
            buttonCreateRecipe.classList.add("btn-lg");
            buttonCreateRecipe.classList.add("btn-dark");
            buttonCreateRecipe.innerText = "Submit";

            buttonDiv.appendChild(buttonCreateRecipe);
            col6.appendChild(buttonDiv);
            rowWithButton.appendChild(col6);


            textInputRecipeName.appendChild(inputRecipeName);
            textInputRecipeName.appendChild(labelForRecipeName);

            col1.appendChild(header3);
            col1.appendChild(textInputRecipeName);

            idRow.appendChild(col1);

            createRecipeInnerSection.appendChild(idRow);
            createRecipeInnerSection.appendChild(rowWithButton);


            formToCreateRecipe.appendChild(createRecipeInnerSection);
            recipeOuterSection.appendChild(formToCreateRecipe);



            // /api/recipionist/meals/delete/{id}
            let form = document.querySelector("#form-create-recipe");
            form.addEventListener("submit", function (event) {
                event.preventDefault();
                let xhttp = new XMLHttpRequest();
                //Define what happens in case of error
                xhttp.addEventListener("error", function (event) {
                    alert("Oops! Something went wrong.");
                    location.href="/session/createrecipe";
                });

                xhttp.addEventListener("load", function (event) {
                    alert("Success! The recipe was deleted.");
                    location.href="/session/createrecipe";

                });


                //chooses the route depending on the selected value
                if (selectMealOrCocktail.value === "deleteCocktail") {
                    xhttp.open("DELETE","/api/recipionist/cocktails/delete/" + document.getElementById("id").value ,true);
                } else {
                    xhttp.open("DELETE","/api/recipionist/meals/delete/" + document.getElementById("id").value ,true);
                }
                xhttp.send();
            });
        }
    };


//depending on the chosen values creates HTTP requests with ajax to create or update the recipes
 function manageTheForm(qualifiedName) {

     //access the form element
     let form = document.querySelector("#form-create-recipe");
     form.enctype = "multipart/form-data";


     if (form != null) {
         form.addEventListener("submit", function (event) {
             event.preventDefault();

             //save the ingredients ans measures as array, because the backend expects this parameters as array lists
             let listOfIngredients = [];
             let listOfMeasures = [];
             for (let i = 1; i <= 15;i++ ) {
                 if (document.getElementById("strIngredient" + i).value != null && document.getElementById("strIngredient" + i).value !== "") {
                     listOfIngredients.push(document.getElementById("strIngredient" + i).value);
                     listOfMeasures.push(document.getElementById("strMeasure" + i).value);
                 }

             }



            //create FormData to change/adapt the body of the form requests
            const FD = new FormData(form);
            //FD.set("thumbnail", JSON.stringify(document.getElementById("thumbnail").value));

            FD.set("ingredients", listOfIngredients);
            FD.set("measures", listOfMeasures);


            //create ajax request
             const XHR = new XMLHttpRequest();


             //Define what happens in case of error
             XHR.addEventListener("error", function (event) {
                 alert("Oops! Something went wrong.");
                 location.href="/session/createrecipe";
             });

             XHR.addEventListener("load", function (event) {
                 alert("Success! The recipe was created/updated.");
                 location.href="/session/createrecipe";

             });

             //PUT REQUESTS
             /*
             if (selectMealOrCocktail.value === "updateMeal") {
                 XHR.open("PUT", "/api/recipionist/meals/update/" + document.getElementById("id").value);
             }
             if(selectMealOrCocktail.value === "updateCocktail") {
                 XHR.open("PUT", "/api/recipionist/cocktails/update/" + document.getElementById("id").value);
             }
*/

             //POST REQUESTS
             if (selectMealOrCocktail.value === "meals") {
                 XHR.open("POST", "/api/recipionist/meals/new");
             }
             if (selectMealOrCocktail.value === "cocktail") {
                 XHR.open("POST", "/api/recipionist/cocktails/new");
             }

             //PATCH REQUESTS

             if (selectMealOrCocktail.value === "updateMeal") {
                 XHR.open("PATCH", "/api/recipionist/meals/update/" + document.getElementById("id").value);
             }
             if(selectMealOrCocktail.value === "updateCocktail") {
                 XHR.open("PATCH", "/api/recipionist/cocktails/update/" + document.getElementById("id").value);
             }


             FD.delete("id");
             //send data but without id as body parameter, because it is provided in URL (if needed)
             XHR.send(FD);
         });
     }
 }

