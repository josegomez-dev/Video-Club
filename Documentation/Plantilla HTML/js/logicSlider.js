var x = 0
$('.nav-side .nav-toggle').on('click', function(e) {
	e.preventDefault();//de esta forma no se carga la pantalla para prevenir la acción por defecto del elemento
	$(this).parent().toggleClass('nav-open');
    if(x%2==0){
        document.body.style.marginLeft="250px";
    }else{
        document.body.style.marginLeft="0px";
    }
    x++;
    /*var elementoNav = document.querySelector('#navBar');
	elementoNav.classList.add('nav-open');*/
});