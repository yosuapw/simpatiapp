$(document).ready(function(){
    mainTop = $('.main').position().top ;
    scrollAmount = mainTop - 150;
    
    $("html, body").animate({scrollTop: scrollAmount}, 500);
});
