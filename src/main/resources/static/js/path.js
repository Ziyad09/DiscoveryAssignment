function selectPath() {
    //alert('a');
    var destination = document.getElementById('selectedDestination').value;
    alert(destination);

    $.get('/selectPath/' + destination,
        function (data, status) {
            var responseModal = $("#responseModal");
            responseModal.modal({backdrop: "static"})
                .find(".modal-body")
                .html(
                    '<div class="row">' +
                    '<h4>' + data + '</h4>' +
                    '</div>'
                );

            responseModal.modal({backdrop: "static"}).find(".modal-title").text('path is');
            responseModal.modal({backdrop: "static"}).find(".modal-footer").html('<a href="/" type="button" class="btn btn-default">Next</a>')
            //data.val('');
            data = "";
            //data.$.clear.valueOf();
            //$('#modal-body').val('')


        });

}
