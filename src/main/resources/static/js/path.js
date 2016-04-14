function selectPath() {
    //alert('a');
    var destination = document.getElementById('selectedDestination').value;
    //alert(destination);

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

            responseModal.modal({backdrop: "static"}).find(".modal-title").text('Calculating path ... \n Okay, here is what I found..');
            responseModal.modal({backdrop: "static"}).find(".modal-footer").html('<a href="/" type="button" class="btn btn-default">Next</a>')
        });
}

function selectPathWithDelay() {
    //alert('a');
    var destination = document.getElementById('selectedDestination').value;
    //alert(destination);

    $.get('/selectDelayedPath/' + destination,
        function (data, status) {
            var responseModal = $("#responseModal");
            responseModal.modal({backdrop: "static"})
                .find(".modal-body")
                .html(
                    '<div class="row">' +
                    '<h4>' + data + '</h4>' +
                    '</div>'
                );

            responseModal.modal({backdrop: "static"}).find(".modal-title").text('Calculating path ... \n Okay, here is what I found..');
            responseModal.modal({backdrop: "static"}).find(".modal-footer").html('<a href="/" type="button" class="btn btn-default">Next</a>')
        });
}
