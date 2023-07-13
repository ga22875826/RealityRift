
const month_names = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];

isLeapYear = (year) => {
    return (year % 4 === 0 && year % 100 !== 0 && year !== 0) || (year % 100 === 0 && year % 400 === 0)
}

getFebDays = (year) => {
    return isLeapYear(year) ? 29 : 28
}

let calendar = document.querySelector('.calendar');

generateCalendar = (month, year) => {
    yourChoice = new Date(year, month);
    let calendar_days = calendar.querySelector('.calendar-days');
    let calendar_header_year = calendar.querySelector('#year');

    let days_of_month = [31, getFebDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    calendar_days.innerHTML = '';

    let currDate = new Date();
    if (month < 0 || month > 11) month = currDate.getMonth();
    if (!year) year = currDate.getFullYear();

    let curr_month = `${month_names[month]}`;
    month_picker.innerHTML = curr_month;
    calendar_header_year.innerHTML = year;

    let first_day = new Date(year, month, 1);

    for (let i = 0; i <= days_of_month[month] + first_day.getDay() - 1; i++) {
        let day = document.createElement('div');
        if (i >= first_day.getDay()) {
            day.classList.add('calendar-day-hover');
            day.innerHTML = i - first_day.getDay() + 1;
            day.innerHTML += `<span></span>
                              <span></span>
                              <span></span>
                              <span></span>`;
            if (i - first_day.getDay() + 1 === currDate.getDate() && year === currDate.getFullYear() && month === currDate.getMonth()) {
                day.classList.add('curr-date');
            }
        }
        calendar_days.appendChild(day);
    }
}

let month_list = calendar.querySelector('.month-list');

month_names.forEach((e, index) => {

    let month = document.createElement('div');
    month.innerHTML = `<div data-month="${index}">${e}<div>`;
    month.querySelector('div').onclick = () => {
        month_list.classList.remove('show');
        curr_month.value = index;
        generateCalendar(index, curr_year.value);
        setupCalendarDayClickHandlers();
    }
    month_list.appendChild(month);
})

let month_picker = calendar.querySelector('#month-picker');

month_picker.onclick = () => {
    month_list.classList.add('show');
}

let currDate = new Date();
let curr_month = { value: currDate.getMonth() };
let curr_year = { value: currDate.getFullYear() };


//選擇日期時間
var yourChoice = new Date();
document.querySelector('.yourchoice').innerHTML = yourChoice.toISOString();
generateCalendar(curr_month.value, curr_year.value);
setupCalendarDayClickHandlers();
setSelectedTime();

document.querySelector('#prev-year').onclick = () => {
    --curr_year.value;
    generateCalendar(curr_month.value, curr_year.value);
    setupCalendarDayClickHandlers();
}

document.querySelector('#next-year').onclick = () => {
    ++curr_year.value;
    generateCalendar(curr_month.value, curr_year.value);
    setupCalendarDayClickHandlers();
}

function setSelectedTime() {
    const gameTimes = document.querySelectorAll('.game-time');
    gameTimes.forEach((gameTime) => {
        gameTime.onclick = () => {
            const timeString = gameTime.innerHTML;
            const [hours, minutes] = timeString.split(":");
            yourChoice.setHours(hours);
            yourChoice.setMinutes(minutes);
            let choice = document.querySelector('.yourchoice');
            choice.innerHTML = yourChoice;
            gameTime.classList.add('selectedTime');
            // 设置请求方法和URL
            confirmBooking(yourChoice, gameTime);

        }
    })
}

function setupCalendarDayClickHandlers() {
    const dayList = document.querySelectorAll('.calendar-day-hover');
    dayList.forEach((day) => {
        day.onclick = () => {
            let currDate = document.querySelector('.curr-date');
            if (currDate !== null) currDate.classList.remove('curr-date');
            day.classList.add('curr-date');
            yourChoice.setDate(parseInt(day.innerHTML));
            let choice = document.querySelector('.yourchoice');
            choice.innerHTML = yourChoice;
        }
    })
}

function formatDate(date) {
    return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}

function formatTime(time) {
    let hours = time.getHours();
    let minutes = time.getMinutes();
    if (hours < 10 && minutes < 10) return "0" + time.getHours() + ":0" + time.getMinutes();
    if (hours < 10) return "0" + time.getHours() + ":" + time.getMinutes();
    if (minutes < 10) return time.getHours() + ":0" + time.getMinutes();
    return time.getHours() + ":" + time.getMinutes();
}

function toServlet(yourChoice) {
    let date = formatDate(yourChoice);
    let time = formatTime(yourChoice);
    // window.location.href = "http://127.0.0.1:5500/booking.html"
    let ddd = document.querySelector("#date");
    let ttt = document.querySelector("#time");
    ddd.value = date;
    ttt.value = time;

    document.getElementById("myForm").submit();
}
//-------------------------------------------------------------------------------
function confirmBooking(yourChoice, gameTime) {
    let date = formatDate(yourChoice);
    let time = formatTime(yourChoice);
    Swal.fire({
        title: "日期: " + date + "<br>時間: " + time,
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: '確定',
        denyButtonText: `取消`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            Swal.fire('預約成功', '', 'success').then((result) => {
                if (result.isConfirmed)
                    toServlet(yourChoice);

            })
        } else if (result.isDenied) {
            gameTime.classList.remove('selectedTime');
        }
    })
}
