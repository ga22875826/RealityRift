(function ($) {
  "use strict";

  $(window).on("load", function () {
    // PreLoader Init
    function preLoader() {
      $("body").addClass("loaded");
      $(".preloader-wrapper").addClass("loaded");
    }
    preLoader();
  });

  $(document).ready(function () {
    // navbarToggle Init
    function navbarToggle() {
      $(".nav-toggle").on("click", function () {
        $(".sidenav").toggleClass("show");
        $(".nav-toggle span").toggleClass("fa-times fa-bars");
        $(".nav-toggle-overlay").toggleClass("show");
      });
      $(".nav-toggle-overlay").on("click", function () {
        $(".sidenav").removeClass("show");
        $(".nav-toggle span").toggleClass("fa-bars fa-times");
        $(".nav-toggle-overlay").removeClass("show");
      });
    }
    navbarToggle();

    // form validation (only for this page)
    window.addEventListener(
      "load",
      function () {
        var forms = document.getElementsByClassName("needs-validation");
        var validation = Array.prototype.filter.call(forms, function (form) {
          form.addEventListener(
            "submit",
            function (event) {
              if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
              }
              form.classList.add("was-validated");
            },
            false
          );
        });
      },
      false
    );
  });
})(jQuery);

//  ========== ========== ===== <!-- 返頂/底紐 --> ===== ========== ==========
var toTopBtn = document.getElementById('toTop');
var toButton = document.getElementById('toButton');

// 滾動事件
window.addEventListener('scroll', function () {
  if (window.pageYOffset > 50) {
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

// ========== ========== ===== typewriter ===== ========== ========== 

    //header打字機
    document.addEventListener('DOMContentLoaded', function () {
      var typewriterElement = document.querySelector('.typewriter');
      var text = typewriterElement.textContent.trim();

      // 清空
      typewriterElement.textContent = '';

      function typeWriter(text, i) {
        if (i < text.length) {
          typewriterElement.textContent += text.charAt(i);
          setTimeout(function () {
            typeWriter(text, i + 1);
          }, 300);
        }
      }
      // 呼叫typeWriter
      typeWriter(text, 0);
    });



