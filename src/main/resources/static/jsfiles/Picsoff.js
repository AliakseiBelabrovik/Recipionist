 function picsoff()
 {
  let elementList = document.querySelectorAll('img');
  for (let i=0; i<elementList.length; i++)
    {
      if(elementList[i].style.display === 'none') {
           elementList[i].style.display='';
      } else {
            elementList[i].style.display='none';
      }
    }
}