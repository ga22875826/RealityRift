document.querySelector(".jsFilter").addEventListener("click", function() {
	document.querySelector(".filter-menu").classList.toggle("active");
});

document.querySelector(".grid").addEventListener("click", function() {
	document.querySelector(".list").classList.remove("active");
	document.querySelector(".grid").classList.add("active");
	document.querySelector(".products-area-wrapper").classList.add("gridView");
	document
		.querySelector(".products-area-wrapper")
		.classList.remove("tableView");
});

document.querySelector(".list").addEventListener("click", function() {
	document.querySelector(".list").classList.add("active");
	document.querySelector(".grid").classList.remove("active");
	document.querySelector(".products-area-wrapper").classList.remove("gridView");
	document.querySelector(".products-area-wrapper").classList.add("tableView");
});

var modeSwitch = document.querySelector('.mode-switch');
modeSwitch.addEventListener('click', function() {
	document.documentElement.classList.toggle('light');
	modeSwitch.classList.toggle('active');
});

//Confirm whether delete 
function confirmDel(itemid) {
	document.body.style.overflow = 'hidden';
	Swal.fire({
		title: '是否確認刪除?',
		showDenyButton: true,
		showCancelButton: false,
		padding: '3em',
		confirmButtonText: '刪除',
		denyButtonText: `取消`,
	}).then((result) => {
		/* Read more about isConfirmed, isDenied below */
		if (result.isConfirmed) {
			window.location.href = 'http://localhost:8080/Project2Hibernate/ItemController?action=delete&itemid=' + itemid;
		} else if (result.isDenied) {
			Swal.fire('取消')
		}

		document.body.style.overflow = 'visible';
	})
}




