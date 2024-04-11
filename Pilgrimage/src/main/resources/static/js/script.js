const toggleSidebar = () => {

	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");

	}
	else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");

	}
};

function deleteHotel(hId) {
	swal({
		title: "Are you sure?",
		text: "You want to delete this hotel..!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/admin/deletehotel/" + hId;
			} else {
				swal("Your Hotel  is safe!");
			}
		});

}
