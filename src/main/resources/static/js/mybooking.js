$(function() {
	$(".change").click(function() {
		let gameID = $(this).closest(".booking-content").find(".game-info").html();
		console.log(gameID);
		window.location.href = "http://localhost:8080/rr/booking?gameID=" + gameID;
	});
	$(".cancel").click(function() {
		Swal.fire({
			title: '確定要取消預約?',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: '是',
			denyButtonText: `否，再考慮一下`,
		}).then((result) => {
			/* Read more about isConfirmed, isDenied below */
			if (result.isConfirmed) {
				let booking = $(this);
				let reservationID = $(".booking-info").attr('name');
				console.log(reservationID);
				$.ajax({
					type: "POST",
					url: "deleteReservation.jsp",
					data: { reservationID: reservationID },
					success: function(response) {
						// 刪除成功時執行的代碼
						booking.closest(".booking").remove();
						if ($(".container-booking").find($(".booking")).length == 0) {
							var html = '<div class="no-booking">' +
								'<div id="no-booking">' +
								'<image src="images\\jigsaw-icon.png"></image>' +
								'<div>你還沒有預約</div>' +
								'</div>' +
								'<div class="go-booking">' +
								'<div class="go-booking-btns">' +
								'<div class="go-booking-btn">前往遊戲選單</div>' +
								'</div>' +
								'</div>' +
								'</div>';
							$(".container-booking").append(html);
						console.log("123");
						}

					},
					error: function(xhr, status, error) {
						// 刪除失敗時執行的代碼
						console.log(error);
					}
				});
				Swal.fire('取消預約成功', '', 'success')
			} else if (result.isDenied) {

			}
		})
	});

	$(".go-booking-btn").click(function() {
		console.log("123");
		window.location.href = "http://localhost:8080/Project2/main.jsp";
	});
})