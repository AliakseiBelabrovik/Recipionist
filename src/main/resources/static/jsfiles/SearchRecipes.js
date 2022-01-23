import ContentCreation from './contentCreation.js';

document.addEventListener("DOMContentLoaded", function (event) {


    //import ContentCreation from "./contentCreation";

    /**
     * Class with functions to display and delete HTML elements (search results) on the page
     */
    class DisplayedResults {
        constructor() {
            this.listOfResults = [];
            this.mainRow = document.querySelector(".row-photo-section"); //row from bootstrap
            this.listOfDisplayedResults = [];
            this.contenCreator = new ContentCreation;
        }

        //display all the results on the screen
        displayResultsOnScreen() {
            //add the results to the page
            for (let i = 0; i < this.listOfResults.length; i++) {
                this.mainRow.appendChild(this.createContent(this.listOfResults[i]));
            }
        }

        removeResultsOnScreen() {
            //add the results to the page
            while (this.mainRow.firstChild) {
                this.mainRow.removeChild(this.mainRow.lastChild);
            }
            this.listOfDisplayedResults = [];
        }

        deleteAllElementsFromList() {
            this.listOfResults = [];
        }



        createContent(singleResult) {
            return this.createCardWithResult(singleResult);

        }

        createCardWithResult(singleResult) {

            //let listOfDisplayedResults = [];

            let colFirstElement = document.createElement("div");
            colFirstElement.classList.add("col");

            let cardFirstElement = document.createElement("div");
            cardFirstElement.classList.add("card");
            cardFirstElement.classList.add("shadow-sm");
            colFirstElement.appendChild(cardFirstElement);

            let imageFirstElement = document.createElement("img");
            imageFirstElement.classList.add("card-img-top");
            imageFirstElement.src=singleResult.image;
            imageFirstElement.alt="Photo not available";
            imageFirstElement.setAttribute("width", "100%");
            imageFirstElement.setAttribute("height", "225");
            /*
            imageFirstElement.width = "100%;"
            imageFirstElement.height="225";
            */

            imageFirstElement.setAttribute("focusable", "false");
            cardFirstElement.appendChild(imageFirstElement);
            //this.mainRow

            let cardBodyFirstElement = document.createElement("div");
            cardBodyFirstElement.classList.add("card-body");
            cardBodyFirstElement.classList.add("red-section");

            if(localStorage.getItem('theme') === 'dark') {
                cardBodyFirstElement.classList.add("dark-mode-red-section");
            };

            let paraFirstElement = document.createElement("p");
            paraFirstElement.classList.add("card-text");
            paraFirstElement.innerHTML = singleResult.name;
            cardBodyFirstElement.appendChild(paraFirstElement);

            let bottomRowInCard = document.createElement("div");
            bottomRowInCard.classList.add("d-flex");
            bottomRowInCard.classList.add("justify-content-between");
            bottomRowInCard.classList.add("align-items-center");

            let btnGroup = document.createElement("div");
            btnGroup.classList.add("btn-group");
            let btnView = document.createElement("button");
            btnView.type="button";
            btnView.classList.add("btn");
            btnView.classList.add("btn-sm");
            btnView.classList.add("btn-outline-light");
            btnView.id = singleResult.id; //give the button the same id as the id of the short meal recipe
            btnView.innerText = "View";

            //let thisObject = this;

            btnView.addEventListener("click", function (event) {
                event.preventDefault();
                //resultOfShortSearch.deleteAllElementsFromList();
                //resultOfShortSearch.removeResultsOnScreen();
                //thisObject.triggerDetailedMealSearch("id/" + btnView.id);
                triggerDetailedMealSearch("id/" + btnView.id);
            });


            /*
            let btnYoutube = document.createElement("button");
            btnYoutube.type="button";
            btnYoutube.classList.add("btn");
            btnYoutube.classList.add("btn-sm");
            btnYoutube.classList.add("btn-outline-light");
            btnYoutube.innerText = "Youtube";
            */
            btnGroup.appendChild(btnView);
            /*
            btnGroup.appendChild(btnYoutube);

             */

            let smallText = document.createElement("small");
            smallText.classList.add("text");
            smallText.innerHTML="Good appetit!";

            bottomRowInCard.appendChild(btnGroup);
            bottomRowInCard.appendChild(smallText);

            cardBodyFirstElement.appendChild(paraFirstElement);
            cardBodyFirstElement.appendChild(bottomRowInCard);
            cardFirstElement.appendChild(cardBodyFirstElement);

            colFirstElement.appendChild(cardFirstElement);
            this.listOfDisplayedResults.push(colFirstElement)
            //listOfDisplayedResults.push(colFirstElement);
            return colFirstElement;

        }






    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * CLASS TO DISPLAY/DELETE SINGLE DETAILED RESULT
     */
    class DisplayedDetailedResult {
        constructor() {
            this.actualDetailedResult = null;
            this.sectionDOM = document.getElementById("photoSection"); //section to add results
            this.mainContainer = null; //main container of the album
            this.mainRow = document.querySelector(".row-photo-section"); //row from bootstrap
            this.listOfDisplayedResults = [];
        }

        //display all the results on the screen
        displayResultsOnScreen() {
            //add the results to the page
            //alert("in MXLHTTP REquest");
            this.createHTMLContent(this.actualDetailedResult);
            this.listOfDisplayedResults.push(this.actualDetailedResult);
        }


        removeResultsOnScreen() {
            while (this.mainRow.firstChild) {
                this.mainRow.removeChild(this.mainRow.lastChild);
            }
            if (this.mainContainer == null) {
                //do nothing
            } else {
                /*
                for(let i = 0; i < this.sectionDOM.children.length; i++) {
                    if (this.sectionDOM.children[i].id === "mainContainerDetailed") {
                        this.sectionDOM.appendChild(this.mainContainer);
                    }
                }
                 */
                this.sectionDOM.appendChild(this.mainContainer);
                this.mainContainer.parentElement.removeChild(this.mainContainer);
            }



            if(document.querySelector(".detailed-recipe") != null) {
                document.querySelector(".detailed-recipe").parentElement.removeChild(document.querySelector(".detailed-recipe"));
            }
            this.listOfDisplayedResults = [];
        }


        //create for each result an bootstrap cards
        createHTMLContent(singleResult) {

            let headerForName = document.createElement("h1");
            headerForName.classList.add("headerPhotoSection");
            headerForName.innerText = singleResult.mealName;
            headerForName.classList.add("detailed-recipe");
            this.sectionDOM.appendChild(headerForName);


            let rowForDisplayingRecipe = document.createElement("div");
            //rowForDisplayingRecipe.classList.add("row");
            rowForDisplayingRecipe.classList.add("row-photo-section");

            let rowRowWOW = document.createElement("div");
            rowRowWOW.classList.add("row");

            let columnFist = document.createElement("div");
            columnFist.classList.add("col-md-6");
            columnFist.classList.add("col");

            let headerCategory = document.createElement("h1");
            headerCategory.id = "category";
            headerCategory.innerText = "Category: " + singleResult.category;
            let headerArea = document.createElement("h1");
            headerArea.id = "area";
            if (singleResult.area == null) {
                headerArea.innerText = "Area: no information available" ;
            } else {
                headerArea.innerText = "Area: " + singleResult.area;
            }

            let headerInstructions = document.createElement("h1");
            headerInstructions.id = "instructions";
            headerInstructions.innerText = "Instructions: ";
            let paraWithInstructions = document.createElement("p");
            paraWithInstructions.id = "para-for-instructions";
            paraWithInstructions.innerText = singleResult.instructions;

            let btnYoutube = document.createElement("a");
            if (singleResult.youtubeLink == null ) {
                btnYoutube.classList.add("disabled");
                btnYoutube.setAttribute('aria-disabled', 'true');
            } else {
                btnYoutube.href = singleResult.youtubeLink;
            }
            btnYoutube.target = "_blank";
            btnYoutube.classList.add("btn");
            btnYoutube.classList.add("btn-outline-dark");
            btnYoutube.classList.add("btn-lg");
            btnYoutube.classList.add("button-download");



            let youtubeLogo = document.createElement("i");
            youtubeLogo.classList.add("fab");
            youtubeLogo.classList.add("fa-youtube");
            btnYoutube.innerHTML = youtubeLogo;
            btnYoutube.innerText = "Watch on Youtube";


            columnFist.appendChild(headerCategory);
            columnFist.appendChild(headerArea);
            columnFist.appendChild(headerInstructions);
            columnFist.appendChild(paraWithInstructions);
            columnFist.appendChild(btnYoutube);

            let columnSecond = document.createElement("div");
            columnSecond.classList.add("col-md-6");
            columnSecond.classList.add("col");
            let imageOfResult = document.createElement("img");
            imageOfResult.classList.add("img-thumbnail");
            imageOfResult.src = singleResult.image;
            imageOfResult.alt = "Foto";

            columnSecond.appendChild(imageOfResult);


            let rowWithTable = document.createElement("div");
            rowWithTable.classList.add("row");
            let header3 = document.createElement("h3");
            header3.innerText = "This is how you cook it";
            rowWithTable.appendChild(header3);
            let para = document.createElement("p");
            para.innerText = "And don't forget to follow us in social media!";
            rowWithTable.appendChild(para);

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
            let i = 0;
            singleResult.ingredients.forEach(item => {
                let tr = document.createElement("tr");
                let td1 = document.createElement("td");
                td1.innerText = item;

                let td2 = document.createElement("td");
                td2.innerText = singleResult.measures[i];
                tr.appendChild(td1);
                tr.appendChild(td2);
                tBody.appendChild(tr);
                i++;
            })
            table.appendChild(tBody);

            let tFoot = document.createElement("tfoot");
            let trFoot = document.createElement("tr");
            let td1 = document.createElement("td");
            td1.innerText = "That's";
            let td2 = document.createElement("td");
            td2.innerText = "It";
            trFoot.appendChild(td1);
            trFoot.appendChild(td2);
            tFoot.appendChild(trFoot);
            table.appendChild(tFoot);
            rowWithTable.appendChild(table);


            let paraLast = document.createElement("p");
            paraLast.innerText = "We hope everything was clear and you enjoyed cooking! If you liked it, sing up to our newsletter or follow us in social media.";
            rowWithTable.appendChild(paraLast);


            //
            rowRowWOW.appendChild(columnFist);
            rowRowWOW.appendChild(columnSecond);

            let containerBootstrap = document.createElement("div");
            containerBootstrap.classList.add("container");
            containerBootstrap.appendChild(rowRowWOW);


            rowForDisplayingRecipe.appendChild(containerBootstrap);
            //rowForDisplayingRecipe.appendChild(columnFist);
            //rowForDisplayingRecipe.appendChild(columnSecond);
            rowForDisplayingRecipe.appendChild(rowWithTable);

            this.mainContainer = document.createElement("div");
            this.mainContainer.id = "mainContainerDetailed";
            this.mainContainer.classList.add("container");

            this.mainContainer.appendChild(rowForDisplayingRecipe);
            this.sectionDOM.appendChild(this.mainContainer);

        }
    }

    /**
     * class for short result
     */
    class SingleShortResult {
        constructor(id, name, image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }
    }
    /**
     * class for detailed result
     */
    class SingleDetailedResult {
        constructor(id, mealName, drinkAlternate, category, area, image, instructions, youtubeLink, ingredients, measures) {
            this.id = id;
            this.mealName = mealName;
            this.drinkAlternate = drinkAlternate;
            this.category = category;
            this.area = area;
            this.image = image;
            this.instructions = instructions;
            //this.tags = tags;
            this.youtubeLink = youtubeLink;
            this.ingredients = ingredients; //must be a list
            this.measures = measures;
        }
    }

    /**
     * class for category
     */
    //class for single category
    class SingleCategory {
        constructor(id, category, image, description) {
            this.id = id;
            this.categoryName = category;
            this.image = image;
            this.description = description;
        }
    }




    /**
     * search button to trigger the search (Main search)
     * @type {Element}
     */
    let searchButton = document.querySelector("#search-button");
    searchButton.addEventListener("click", function (event) {
        event.preventDefault();
        let selectedSearchOption = document.getElementById("select-search-option").value;
        let searchInput = document.getElementById("search-input").value;

        //Firstly, clean the page and empty the lists
        resultOfDetailedSearch.removeResultsOnScreen();
        resultOfShortSearch.deleteAllElementsFromList();
        resultOfShortSearch.removeResultsOnScreen();
        resultOfCategorySearch.removeResultsOnScreen();

        //depending on the search option selected, call function
        if(selectedSearchOption === "random") {
            triggerDetailedMealSearch(selectedSearchOption);
        } else if (selectedSearchOption === "category/" || selectedSearchOption === "area/") {
            if (searchBar.disabled) {
                triggerShortMealSearch(selectedSearchOption, document.getElementById("select-categories-area-option").value);
            } else {
                triggerShortMealSearch(selectedSearchOption, searchInput);
            }

        } else {
            triggerShortMealSearch(selectedSearchOption, searchInput);
        }
    });



    /**
     * after search button was clicked, send GET request with AJAX -> API responds with the shortMeal
     * @param searchOption - by name, ingredient, etc
     * @param searchInput - user input
     */
    function triggerShortMealSearch (searchOption, searchInput) {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {

            if (this.readyState == 4 && this.status == 200) {
                let responseObj = JSON.parse(this.responseText);

                //empty the page and the list
                resultOfShortSearch.removeResultsOnScreen();
                //Retrieve objects
                responseObj.forEach(item => {
                    let id = item.id;
                    let name = item.mealName;
                    let image = item.thumbnail;
                    let objectResult = new SingleShortResult(id, name, image);
                    resultOfShortSearch.listOfResults.push(objectResult);
                });

                resultOfShortSearch.displayResultsOnScreen();
            }
        }


        const ourAPIUrl = "http://localhost:8080/api/recipionist/meals/";
        const url = ourAPIUrl + searchOption + searchInput;
        //alert("The url we send to our API is " + url);
        xmlhttp.open("GET", url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //xmlhttp.setRequestHeader("Accept", "application/xml");
        xmlhttp.send();
    }


    /**
     * Serach for a detailed recipe by id
     * @param idToSearchFor
     */
    function triggerDetailedMealSearch (idToSearchFor) {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            console.log("ready state is" + this.readyState + " and status is " + this.status);
            if (this.readyState == 4 && this.status == 200) {
                let responseObj = JSON.parse(this.responseText);
                console.log(responseObj);

                    let id = responseObj.id;
                    let name = responseObj.mealName;
                    let drinkAlternate = responseObj.drinkAlternate;
                    let image = responseObj.thumbnail;
                    let category = responseObj.category;
                    let area = responseObj.area;
                    let instructions = responseObj.instructions;
                    let youtubeLink = responseObj.youtubeLink;
                    let ingredients = responseObj.ingredients; //array
                    let measures = responseObj.measures; //array


                //remove the previous search results from the page
                    resultOfShortSearch.removeResultsOnScreen();
                    //then create an object for the detailed recipe and display it on the page
                    let singleDetailedResult = new SingleDetailedResult(id, name, drinkAlternate, category, area, image, instructions, youtubeLink, ingredients, measures);
                    resultOfDetailedSearch.actualDetailedResult = singleDetailedResult;
                    resultOfDetailedSearch.displayResultsOnScreen();
            }
        }



        const ourAPIUrl = "http://localhost:8080/api/recipionist/meals/";
        let  url;

        url = ourAPIUrl + idToSearchFor;

        xmlhttp.open("GET", url, true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send();
    }


    /**
     * show the possible search values if choose area or category
     * @type {Element}
     */
    let selectItem = document.querySelector("#select-search-option");
    selectItem.oninput = showAvailableSelectValues;
    let searchBar = document.querySelector("#search-input");

    function showAvailableSelectValues(e) {
        searchBar.disabled = false;
        //delete previously created elements
        if (document.getElementById("header-for-option-category-area") != null) {
            let header = document.getElementById("header-for-option-category-area");
            let select = document.getElementById("select-categories-area-option");
            header.parentElement.removeChild(header);
            select.parentElement.removeChild(select);
        }
        //if the chosen value is category -> suggest the search options
        if (selectItem.value === "category/" || selectItem.value === "area/") {
            //show the
            let selectOptionsCategoryOrArea = document.createElement("select");
            selectOptionsCategoryOrArea.classList.add("form-select");
            selectOptionsCategoryOrArea.id = "select-categories-area-option";
            let headerOptionsCategoryOrArea = document.createElement("label");
            headerOptionsCategoryOrArea.for = "select-categories-area-option";
            headerOptionsCategoryOrArea.classList.add("form-label");
            let selectedValue = null;
            if (selectItem.value === "category/") {
                headerOptionsCategoryOrArea.innerText = "Please choose category or type your own choice in the search bar";
                selectedValue = "category";
            } else {
                headerOptionsCategoryOrArea.innerText = "Please choose area or type your own choice in the search bar";
                selectedValue = "area";
            }
            headerOptionsCategoryOrArea.id = "header-for-option-category-area";
            //make ajax request

            let ajaxRequest = new XMLHttpRequest();
            ajaxRequest.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    let responseList = JSON.parse(this.responseText);
                    let listOfOptions = [];
                    let option = document.createElement("option");
                    let textOption = document.createTextNode(("Choose " + selectedValue));
                    option.appendChild(textOption);
                    option.value = "no-choice";
                    option.id = "category-no-choice";
                    selectOptionsCategoryOrArea.appendChild(option);
                    listOfOptions.push(option);

                    if (selectedValue === "category") {
                        responseList.forEach(item => {
                            let option = document.createElement("option");
                            let textOption = document.createTextNode((item.category));
                            option.appendChild(textOption);
                            option.value = item.category;
                            option.id = "category-" + item.category;

                            listOfOptions.push(option);
                        });

                    } else {
                        responseList.forEach(item => {
                            if (item.area === "Unknown"){
                                //do nothing
                            } else {
                                let option = document.createElement("option");
                                let textOption = document.createTextNode((item.area));
                                option.appendChild(textOption);
                                option.value = item.area;
                                option.id = "area-" + item.area;
                                listOfOptions.push(option);
                            }
                        });
                    }
                    //sort the list
                    listOfOptions.sort(function (a,b) {
                        if (a.value < b.value) {
                            return -1;
                        }
                        if (a.value > b.value) {
                            return 1;
                        }
                        return 0;
                    });

                    listOfOptions.forEach(item => {
                        selectOptionsCategoryOrArea.appendChild(item);
                    });
                }

                let firstSelect = document.getElementById("select-search-option");
                firstSelect.insertAdjacentElement("afterend", headerOptionsCategoryOrArea);
                headerOptionsCategoryOrArea.insertAdjacentElement("afterend", selectOptionsCategoryOrArea);
            };

            const ourAPIUrl = "http://localhost:8080/api/recipionist/meals/";
            let url = null;
            if(selectedValue === "category") {
                url = ourAPIUrl + "categories";
            } else {
                url = ourAPIUrl + "areas";
            }

            ajaxRequest.open("GET", url, true);
            ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            ajaxRequest.send();

            selectOptionsCategoryOrArea.oninput = blockSearchBar;

            //if the choice is random meal -> disable the search bar
        } else if (selectItem.value === "random") {
            searchBar.disabled = true;

        } else  {

        }
    };

    function blockSearchBar (e) {
        searchBar.disabled = e.target.value !== "no-choice";
    }


    /**
     * class to display/remove categories on the page
     */

    class DisplayedCategories {
        constructor() {
            this.listOfResults = [];
            this.sectionDOM = document.getElementById("photoSection"); //section to add results
            this.albumInSection = document.querySelector(".album"); //album with results
            this.mainContainer = document.getElementById("mainContainer"); //main container of the album
            this.mainRow = document.querySelector(".row-photo-section"); //row from bootstrap
            this.listOfDisplayedResults = [];
        }

        //display all the results on the screen
        displayResultsOnScreen() {
            //add the results to the page
            for (let i = 0; i < this.listOfResults.length; i++) {
                this.mainRow.appendChild(this.createCardWithResult(this.listOfResults[i]));
            }
        }
        removeResultsOnScreen() {
            //add the results to the page
            while (this.mainRow.firstChild) {
                this.mainRow.removeChild(this.mainRow.lastChild);
            }
        }
        deleteAllElementsFromList() {
            this.listOfResults = [];
        }
        //create for each result an bootstrap cards
        createCardWithResult(singleResult) {

            let colFirstElement = document.createElement("div");
            colFirstElement.classList.add("col");

            let cardFirstElement = document.createElement("div");
            cardFirstElement.classList.add("card");
            cardFirstElement.classList.add("shadow-sm");
            colFirstElement.appendChild(cardFirstElement);

            let imageFirstElement = document.createElement("img");
            imageFirstElement.classList.add("card-img-top");
            imageFirstElement.src=singleResult.image;
            imageFirstElement.alt="Photo not available";
            imageFirstElement.setAttribute("width", "100%");
            imageFirstElement.setAttribute("height", "225");
            /*
            imageFirstElement.width = "100%;"
            imageFirstElement.height="225";
            */

            imageFirstElement.setAttribute("focusable", "false");
            cardFirstElement.appendChild(imageFirstElement);
            //this.mainRow

            let cardBodyFirstElement = document.createElement("div");
            cardBodyFirstElement.classList.add("card-body");
            cardBodyFirstElement.classList.add("red-section");

            if(localStorage.getItem('theme') === 'dark') {
                cardBodyFirstElement.classList.add("dark-mode-red-section");
            }


            let testText = document.createElement("small");
            testText.classList.add("text");
            testText.innerHTML=singleResult.categoryName;
            cardBodyFirstElement.appendChild(testText);

            let paraFirstElement = document.createElement("p");
            paraFirstElement.classList.add("card-text");
            paraFirstElement.innerHTML = singleResult.description;
            cardBodyFirstElement.appendChild(paraFirstElement);

            let bottomRowInCard = document.createElement("div");
            bottomRowInCard.classList.add("d-flex");
            bottomRowInCard.classList.add("justify-content-between");
            bottomRowInCard.classList.add("align-items-center");

            let btnGroup = document.createElement("div");
            btnGroup.classList.add("btn-group");

            let btnView = document.createElement("button");
            btnView.type="button";
            btnView.classList.add("btn");
            btnView.classList.add("btn-sm");
            btnView.classList.add("btn-outline-light");
            btnView.innerText = "Search";


            btnView.addEventListener("click", function () {
                triggerShortMealSearch("category/", singleResult.categoryName);
            })

            btnGroup.appendChild(btnView);


            let smallText = document.createElement("small");
            smallText.classList.add("text");
            smallText.innerHTML=singleResult.categoryName;

            bottomRowInCard.appendChild(btnGroup);
            bottomRowInCard.appendChild(smallText);

            cardBodyFirstElement.appendChild(paraFirstElement);
            cardBodyFirstElement.appendChild(bottomRowInCard);
            cardFirstElement.appendChild(cardBodyFirstElement);

            colFirstElement.appendChild(cardFirstElement);
            this.listOfDisplayedResults.push(colFirstElement);
            return colFirstElement;
        }
    }


    let btnShowAllCategories = document.getElementById("btn-show-categories");
    btnShowAllCategories.addEventListener("click", function (event) {
        resultOfDetailedSearch.removeResultsOnScreen();
        resultOfShortSearch.deleteAllElementsFromList();
        resultOfShortSearch.removeResultsOnScreen();
        resultOfCategorySearch.deleteAllElementsFromList();
        resultOfCategorySearch.removeResultsOnScreen();
        triggerSearchByCategory();
    });


    //trigger to show all categories
    function triggerSearchByCategory () {
        let xmlHttPRequest = new XMLHttpRequest();
        xmlHttPRequest.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                let responseObj = JSON.parse(this.responseText);

                responseObj.forEach(item => {
                    let id = item.id;
                    let categoryName = item.category;
                    let image = item.thumbnail;
                    let description = item.description;

                    let singleCategory = new SingleCategory(id, categoryName, image, description);
                    resultOfCategorySearch.listOfResults.push(singleCategory);
                });
                resultOfCategorySearch.listOfResults.sort(function (a,b) {
                    if (a.categoryName < b.categoryName) {
                        return -1;
                    }
                    if (a.categoryName > b.categoryName) {
                        return 1;
                    }
                    return 0;
                });
                resultOfCategorySearch.displayResultsOnScreen();
            }
        }
        const ourAPIUrl = "http://localhost:8080/api/recipionist/meals/";
        const url = ourAPIUrl + "categories";

        xmlHttPRequest.open("GET", url, true);
        xmlHttPRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttPRequest.send();
    }


    let resultOfShortSearch = new DisplayedResults();
    let resultOfDetailedSearch = new DisplayedDetailedResult();
    let resultOfCategorySearch = new DisplayedCategories();

});

