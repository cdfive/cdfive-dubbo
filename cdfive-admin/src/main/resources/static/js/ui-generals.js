$(function() {
//    setTimeout(function(){
//        var msg = '<p class="fa fa-lightbulb-o" style="font-size: 35px; float:left;margin-top: 10px;margin-right: 10px;"></p> Resize your web browser to see mobile & tablet tabs version';
//        $.notific8(msg);
//    }, 3000);
    
    fakewaffle.responsiveTabs(['xs', 'sm']);
    
    //BEGIN TOOTLIP
    $("[data-toggle='tooltip'], [data-hover='tooltip']").tooltip();
    //END TOOLTIP

    //BEGIN POPOVER
    $("[data-toggle='popover'], [data-hover='popover']").popover();
    //END POPOVER
});