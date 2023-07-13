
//  ========== ========== ===== <!-- 返頂/底紐 --> ===== ========== ==========
var toTopBtn = document.getElementById('toTop');
var toButton = document.getElementById('toButton');

// 滾動事件
window.addEventListener('scroll', function () {
  if (window.pageYOffset > 10) {
    toTopBtn.classList.add('show');
    toButton.classList.add('show');
  } else {
    toTopBtn.classList.remove('show');
    toButton.classList.remove('show');
  }
});

// 返頂
toTopBtn.addEventListener('click', function () {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
});

// 到頁底
toButton.addEventListener('click', function () {
  var footer = document.getElementById('footer');
  console.log(footer)
  footer.scrollIntoView({ behavior: 'smooth' });
});

// ========== ========== ===== ===== ========== ==========
