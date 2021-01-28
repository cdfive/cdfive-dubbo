$(function () {
	
    //BEGIN PORTLET
    $(".portlet").each(function(index, element) {
        var me = $(this);
        $(">.portlet-header>.tools>i", me).click(function(e){
            if($(this).hasClass('fa-chevron-up')){
                $(">.portlet-body", me).slideUp('fast');
                $(this).removeClass('fa-chevron-up').addClass('fa-chevron-down');
            }
            else if($(this).hasClass('fa-chevron-down')){
                $(">.portlet-body", me).slideDown('fast');
                $(this).removeClass('fa-chevron-down').addClass('fa-chevron-up');
            }
            else if($(this).hasClass('fa-cog')){
                //Show modal
            }
            else if($(this).hasClass('fa-refresh')){
                //$(">.portlet-body", me).hide();
                $(">.portlet-body", me).addClass('wait');

                setTimeout(function(){
                    //$(">.portlet-body>div", me).show();
                    $(">.portlet-body", me).removeClass('wait');
                }, 1000);
            }
            else if($(this).hasClass('fa-times')){
                me.remove();
            }
        });
    });
    //END PORTLET
	
    //BEGIN PLUGINS DATE RANGE PICKER
    $('input[name="daterangepicker-default"]').daterangepicker();
    $('input[name="daterangepicker-date-time"]').daterangepicker({ timePicker: true, timePickerIncrement: 30, format: 'YYYY-MM-DD HH:mm' });
    $('.reportrange').daterangepicker(
        {
            ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                '最近7天': [moment().subtract('days', 6), moment()],
                '最近30天': [moment().subtract('days', 29), moment()],
                '本月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
            },
            startDate: moment().subtract('days', 29),
            endDate: moment(),
            opens: 'left'
        },
        function(start, end) {
            $('.reportrange span').html(start.format('YYYY年MM月DD日') + ' - ' + end.format('YYYY年MM月DD日'));
            $('input[name="datestart"]').val(start.format("YYYY-MM-DD"));
            $('input[name="endstart"]').val(end.format("YYYY-MM-DD"));
        }
    );
    $('.reportrange span').html(moment().subtract('days', 29).format('YYYY年MM月DD日') + ' - ' + moment().format('YYYY年MM月DD日'));
    //END PLUGINS DATE RANGE PICKER

    //BEGIN PLUGINS DATE PICKER
    $('.datepicker-default').datepicker();
    $('.datepicker-years').datepicker({
        startView: 1,
        minViewMode: 2
    });
    $('.input-daterange').datepicker({
        format: "yyyy-mm-dd"
    });
    $('.datepicker-inline').datepicker({
        format: "yyyy-mm-dd",
        startView: 2,
        minViewMode: 3
    }).on('changeDate',function(ev){
    	alert(moment(ev.date.valueOf()).format("YYYY-MM-DD"));
	});
    //END PLUGINS DATE PICKER

    //BEGIN PLUGINS DATETIME PICKER
    $('.datetimepicker-default').datetimepicker({
    	format:"YYYY-MM-DD HH:mm"
    });
    $('.datetimepicker-disable-date').datetimepicker({
        pickDate: false,
        format:"HH:mm"
    });
    $('.datetimepicker-disable-time').datetimepicker({
        pickTime: false,
        format:"YYYY-MM-DD"
    });
    $('.datetimepicker-start').datetimepicker({
    	format:"YYYY-MM-DD HH:mm"
    });
    $('.datetimepicker-end').datetimepicker({
    	format:"YYYY-MM-DD HH:mm"
    });
    $('.datetimepicker-start').on("change.dp",function (e) {
    	alert(e.date);
        $('.datetimepicker-end').data("DateTimePicker").setStartDate(e.date);
    });
    $('.datetimepicker-end').on("change.dp",function (e) {
    	alert(e.date);
        $('.datetimepicker-start').data("DateTimePicker").setEndDate(e.date);
    });
    //END PLUGINS DATETIME PICKER

    //BEGIN PLUGINS TIME PICKER
    $('.timepicker-default').timepicker();
    $('.timepicker-24hr').timepicker({
        autoclose: true,
        minuteStep: 1,
        showSeconds: true,
        showMeridian: false
    });
    //END PLUGINS TIME PICKER

    //BEGIN PLUGINS CLOCKFACE TIME PICKER
    $('.clockface-default').clockface();
    $('.clockface-component').clockface({
        format: 'HH:mm',
        trigger: 'manual'
    });

    $('#btn-clockface-component').click(function(e){
        e.stopPropagation();
        $('.clockface-component').clockface('toggle');
    });

    $('.clockface-inline').clockface({
        format: 'H:mm'
    }).clockface('show', '14:30');
    //END PLUGINS CLOCKFACE TIME PICKER

    //BEGIN PLUGINS COLOR PICKER
    $('.colorpicker-default').colorpicker();
    $('.colorpicker-rgba').colorpicker();
    $('.colorpicker-component').colorpicker({
        format: 'hex'
    }).on('changeColor', function(ev) {
            $('.colorpicker-component span i').css('color',ev.color.toHex());
            $('.colorpicker-component input').val(ev.color.toHex());
        });
    //END PLUGINS COLOR PICKER

    // BEGIN PLUGIN MASK INPUT
    $("#date").mask("9999-99-99");
    $("#phone").mask("(999) 999-9999? x99999");
    $("#product-key").mask("(aa) 99-999")
    // END PLUGIN MASK INPUT

    //BEGIN CHARACTER COUNT
    $("#message1, #message2").charCount();
    $("#message3").charCount({
        allowed: 50,
        warning: 20,
        counterText: '还可以输入字数: '
    });
    //END CHARACTER COUNT

});
