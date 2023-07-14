/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

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

// ========== ========== ===== 重設 ===== ========== ==========
$(document).ready(function () {
  $("#resetButton").click(function () {
    resetGameIndex();
    resetCitySelect();
    resetLevelSelect();
    resetPlayerSelect();
    resetPriceSelect();
    resetGamesCount();

  });
});

function resetCitySelect() {
  var selectCityInputs = document.querySelectorAll('.selectCity');
  selectCityInputs.forEach(function (input) {
    input.checked = false;
    input.disabled = false;
  });
}

function resetLevelSelect() {
  var radioButtons = document.getElementsByName("levelFilter");
  radioButtons.forEach(function (button) {
    button.checked = false;
    button.disabled = false;
  });
}

function resetPlayerSelect() {
  $('#playerSelect').text("遊 戲 人 數");
}

function resetPriceSelect() {
  $('#priceSelect').text("每 人 預 算");
}

//資料筆數
function resetGamesCount() {
  $.ajax({
    url: "/rr/Index",
    type: "GET",
    success: function (response) {
      var gamesCount = $(response).find("#gamesCount").html();
      $("#gamesCount").html(gamesCount);
    },
    error: function (error) {
      console.log("Ajax request failed: ", error);
    }
  });
}

//gamecard
function resetGameIndex() {
  $.ajax({
    url: "/rr/Index",
    type: "GET",
    success: function (response) {

      var updatedContent = $(response).find("#gameContainer").html();
      $("#gameContainer").html(updatedContent);

    },
    error: function (error) {
      console.log("Ajax request failed: ", error);
    }
  });
}

// ========== ========== ===== 取得：城市、難度、人數、預算 ===== ========== ========== 

//取城市
function getCity() {
  let selectCity = $(".selectCity:checked");
  let cities = ["", "", ""]
  for (i = 0; i < selectCity.length; i++) {
    cities[i] += (selectCity[i].value);
  }
  return cities;
}

//取難度
function getLevelText() {
  var selectedLevel = document.querySelector('input[name="levelFilter"]:checked');
  if (selectedLevel) {
    if (selectedLevel.id === "beginner") {
      return "新手入門";
    } else if (selectedLevel.id === "intermediate") {
      return "中度玩家";
    } else if (selectedLevel.id === "advanced") {
      return "重度解謎";
    }
  }
  return "";
}

//取人數
function getPlayer() {
  var playerSelect = $('#playerSelect').text();
  if (playerSelect === "不 限") {
    return "";
  } else if (playerSelect === "1 - 4 人") {
    return "1-4人";
  } else if (playerSelect === "4 - 6 人") {
    return "4-6人";
  } else if (playerSelect === "6 - 8 人") {
    return "6-8人";
  } else if (playerSelect === "8 人 以 上") {
    return "8人以上";
  }
  return "";
}

//取預算
function getPrice() {
  var priceSelect = $('#priceSelect').text();
  switch (priceSelect) {
    case "不 限":
      return "";
    case "$400 以內":
      let price1 = [0, 350];
      return price1;
    case "$400 ~ $600":
      let price2 = [400, 600];
      return price2;
    case "$600 ~ $800":
      let price3 = [600, 800];
      return price3;
    case "$800 ~ $1000":
      let price4 = [800, 1000];
      return price4;
    case "$1000 以上":
      let price5 = [1000, 1200];
      return price5;
    default:
      return "";
  }
}

// ========== ========== ===== 城市選框 ===== ========== ========== 
$(document).ready(function () {
  var maxSelection = 3;

  $('#playCityCollapse input[type="checkbox"]').change(function () {
    var selectedCount = $('#playCityCollapse input[type="checkbox"]:checked').length;

    // 超過3 其他設disabled
    $('#playCityCollapse input[type="checkbox"]').each(function () {
      if (selectedCount >= maxSelection && !this.checked) {
        $(this).prop('disabled', true);
      } else {
        $(this).prop('disabled', false);
      }
    });

    $('#playCityCollapse input[type="checkbox"]:checked').map(function () {
      return $(this).val();
    }).get();

    // Ajax請求
    getGamesByFilter(getLevelText(), getPlayer(), getPrice(), getCity());
  });
});

// ========== ========== ===== 難度選框 ===== ========== ========== 
var getLevel = null;

function levelClick(element) {
  var radioButtons = document.getElementsByName("levelFilter");

  // 再點一次反選
  if (element === getLevel) {
    element.checked = false;
    getLevel = null;

    // 解除其他disabled
    radioButtons.forEach(function (button) {
      button.disabled = false;
    });
  } else {
    getLevel = element;

    radioButtons.forEach(function (button) {
      if (button !== element) {
        button.disabled = true;
      }
    });
  }

  // Ajax請求
  getGamesByFilter(getLevelText(), getPlayer(), getPrice(), getCity());
}

// ========== ========== ===== 人數選框 ===== ========== ==========    
$(document).ready(function () {
  $('#playerSelect + .dropdown-menu .dropdown-item').click(function () {
    var playerSelect = $(this).text();
    $('#playerSelect').text(playerSelect);

    // Ajax請求
    getGamesByFilter(getLevelText(), getPlayer(), getPrice(), getCity());
  });
});

// ========== ========== ===== 預算選框 ===== ========== ==========    
$(document).ready(function () {
  $('#priceSelect + .dropdown-menu .dropdown-item').click(function () {
    var priceSelect = $(this).text();
    $('#priceSelect').text(priceSelect);

    // Ajax請求
    getGamesByFilter(getLevelText(), getPlayer(), getPrice(), getCity());
  });
});

// ========== ========== ===== AJax ===== ========== ========== 

function getGamesByFilter(level, player, price, cities) {

  // 發起 Ajax
  $.ajax({
    url: "/rr/Index/filter",
    type: "GET",
    data: {
      level: level,
      player: player,
      minPrice: price[0],
      maxPrice: price[1],
      city1: cities[0],
      city2: cities[1],
      city3: cities[2]
    },

    success: function (response) {
      // 清空現有
      $("#gameContainer").empty();

      // htmlmaker
      response.forEach(function (game, index) {
        var gameCard = `
            
            <div class="col mb-5">
              <div class="card h-100">
                <img class="card-img-top" src="/rr/${game.gameimg}" id="img${game.gameid}" style="width: 100%; height:250px">
                <div class="card-body p-4">
                  <div class="text-center">
                    <h5 class="fw-bolder">${game.gname}</h5><hr>
                    <div class="row">
	                  <div class="col-6" style="text-align: center;"><span><i class="fa-solid fa-user-group" style="color: rgb(1, 1, 40);"></i>	<span>${game.player}</span>	</span></div>
	                  <div class="col-6" style="text-align: center;"><span><i class="fa-solid fa-clock"  style="color: rgb(1, 1, 40);"></i>	<span>${game.time}</span></span></div>
	                </div>
                  </div>
                </div>
              <div class="row card-footer p-4 pt-0 border-top-0 bg-transparent">
				<div class="btn-group " role="group" aria-label="Basic outlined example">
                  <button id="like-${index}" type="button" class="btn btn-outline-dark" style="border: none;"> <i class="fa-regular fa-heart"></i> 收 藏 </button>
                  <button id="savedlike-${index}" type="button" class="btn btn-outline-dark" style="border: none; display: none"> <i class="fa-solid fa-heart"></i> 已收藏 </button>

				  <a class="btn btn-outline-dark mt-auto" style="border: none;border-left: 1px solid black;" href="/rr/game/view?gameid=${game.gameid}"> 介 紹 </a>
				</div>
              </div>
            </div>
          </div>
	    `;
        $("#gameContainer").append(gameCard);
      });

      // 更新結果筆數
      var totalGames = response.length;
      $("#gamesCount").text(totalGames);
      
	  //收藏紐
      $("[id^='like-']").click(likeBtn);
      $("[id^='savedlike-']").click(savedlikeBtn);
    },
    error: function (error) {
      console.log("Ajax request failed: ", error);
    }
  });
}

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
      }, 180);
    }
  }
  // 呼叫typeWriter
  typeWriter(text, 0);
});

// ========== ========== ===== savedlike ===== ========== ========== 

function likeBtn() {
  var index = $(this).attr("id").split("-")[1];
  $("#like-" + index).css("display", "none");
  $("#savedlike-" + index).css("display", "inline-block");
}

function savedlikeBtn() {
  var index = $(this).attr("id").split("-")[1];
  $("#like-" + index).css("display", "inline-block");
  $("#savedlike-" + index).css("display", "none");
}

$(document).ready(function() {
  $("[id^='like-']").click(likeBtn);
  $("[id^='savedlike-']").click(savedlikeBtn);
});

