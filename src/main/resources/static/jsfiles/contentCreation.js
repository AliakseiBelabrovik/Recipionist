export default class ContentCreation {
    constructor() {

    }

    //for each result, create a bootstrap card
    createCardWithResult(singleResult) {

        let listOfDisplayedResults = [];

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
        listOfDisplayedResults.push(colFirstElement);
        return colFirstElement;

    }

}





