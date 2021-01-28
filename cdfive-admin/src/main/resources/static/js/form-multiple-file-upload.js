$(function () {
    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        disableImageResize: false,
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: 'imgupload.do'
    });

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    // Demo settings:
    $('#fileupload').fileupload('option', {
        url: '../imgupload.do',
        // Enable image resizing, except for Android and Opera,
        // which actually support image resizing, but fail to
        // send Blob objects via XHR requests:
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator.userAgent),
        maxFileSize: 5000000,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        paramName:"image",
    });
    // Upload server status check for browsers with CORS support:
    if ($.support.cors) {
        $.ajax({
            url: '../imgupload.do',
            type: 'GET'
        }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('上传服务器当前不可用 - ' +
                        new Date())
                    .appendTo('#fileupload');
            });
    }

});