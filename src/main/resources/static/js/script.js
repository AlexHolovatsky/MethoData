document.addEventListener('DOMContentLoaded', function () {
    const map = L.map('map', {
        center: [46.4511, 33.8739],
        zoom: 6,
        maxZoom: 100
    });

    L.tileLayer('https://{s}.tile.stamen.com/toner/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://stamen.com/">Stamen Design</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        subdomains: ['a', 'b', 'c', 'd']
    }).addTo(map);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: ''
    }).addTo(map);

    // Додати прослуховування події на натискання на карту
    map.on('click', function (e) {
        const coords = e.latlng; // Отримуємо координати місця натискання

        // Копіюємо координати в буфер обміну
        copyToClipboard(`${coords.lat}, ${coords.lng}`);

        // Показуємо повідомлення
        alert('Координати скопійовано: ' + `${coords.lat}, ${coords.lng}`);
    });

    // Функція для копіювання в буфер обміну
    function copyToClipboard(text) {
        const textarea = document.createElement('textarea');
        textarea.value = text;
        document.body.appendChild(textarea);
        textarea.select();
        document.execCommand('copy');
        document.body.removeChild(textarea);
    }


    fetch('/api/weather/reserves')
        .then(response => response.json())
        .then(reserves => {
            const customIcon = L.icon({
                iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34]
            });
            // Вихідний масив reserves
            const groupedData = reserves.reduce((acc, reserve) => {
                const areaId = reserve.protectedArea.id;

                if (!acc[areaId]) {
                    // Якщо такої території ще немає, додаємо її
                    acc[areaId] = {
                        protectedArea: reserve.protectedArea,
                        weatherData: []
                    };
                }

                // Додаємо інформацію про погоду до масиву weatherData
                acc[areaId].weatherData.push({
                    date: reserve.date,
                    temperature: reserve.temperature,
                    precipitation: reserve.precipitation,
                    windSpeed: reserve.windSpeed,
                    notes: reserve.notes
                });

                return acc;
            }, {});


            Object.values(groupedData).forEach(reserve => {
                const coords = reserve.protectedArea.location.split(',').map(coord => parseFloat(coord.trim()));
                L.marker(coords, { icon: customIcon })
                    .addTo(map)
                    .bindPopup(`
                    <b>${reserve.protectedArea.name}</b><br>
                    ${reserve.protectedArea.description}<br>
                    <button onclick='showModal1(${JSON.stringify(reserve)})'>
                        Детальніше
                    </button>
                `);
            });
        })
        .catch(error => console.error('Error fetching reserves:', error));

    map.on('zoom', function () {
      if (map.getZoom() > 10) {
          map.setZoom(10);
      }
      if (map.getZoom() < 5) {
          map.setZoom(5);
      }
  });

    // Функція для відкриття першого модального вікна
  window.showModal1 = async function(reserve) {
      console.log('reserve', JSON.stringify(reserve))
      const response = await fetch("/api/evaluate", {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify({weatherData: reserve.weatherData})
      });
      if (response.ok) {
          const data = await response.json();
          console.log("Fuzzy Evaluation Results:", data);
          // Відобразіть дані на сторінці
          data.forEach(item => {
              console.log(`Дата: ${item.date}, Стан: ${item.status}`);
          });
          document.getElementById("modal-1-fuzzy-logic").innerHTML = data.map(item => `<p>Дата: ${item.date}, Статус: ${item.status}</p>`).join('');
      } else {
          console.error("Error:", response.status);
      }

      console.log(reserve)
      document.getElementById('modal-1-title').textContent = reserve.protectedArea.name;
      document.getElementById('modal-1-description').textContent = reserve.protectedArea.description;
      document.getElementById('open-modal-2').onclick = function (){showModal2(reserve.weatherData);}
      document.getElementById('overlay').style.display = 'block';
      document.getElementById('modal-1').style.display = 'block';
  };

  // Закриття першого модального вікна
  document.getElementById('close-modal-1').addEventListener('click', function() {
      document.getElementById('overlay').style.display = 'none';
      document.getElementById('modal-1').style.display = 'none';
  });

  // Відкриття другого модального вікна

    window.showModal2 = function(filteredData) {
        document.getElementById('modal-1').style.display = 'none';
        document.getElementById('modal-2').style.display = 'block';

        // Логування вхідних даних
        console.log('Filtered Data:', filteredData);

        // Форматування даних
        const labels = filteredData.map(item => item.date); // Масив дат
        const temperatures = filteredData.map(item => parseFloat(item.temperature) || 0); // Температури
        const precipitations = filteredData.map(item => parseFloat(item.precipitation) || 0); // Опади
        const windSpeeds = filteredData.map(item => parseFloat(item.windSpeed) || 0); // Швидкість вітру

        // Очистка старих графіків, якщо вони є
        if (window.temperatureChart) window.temperatureChart.destroy();
        if (window.precipitationChart) window.precipitationChart.destroy();
        if (window.windSpeedChart) window.windSpeedChart.destroy();

        // 1. Графік для температури
        const ctxTemp = document.getElementById('temperature-chart').getContext('2d');
        window.temperatureChart = new Chart(ctxTemp, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Temperature (°C)',
                    data: temperatures,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderWidth: 2,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' },
                    tooltip: { mode: 'index', intersect: false }
                },
                scales: {
                    x: { title: { display: true, text: 'Date' } },
                    y: { title: { display: true, text: 'Temperature (°C)' } }
                }
            }
        });

        // 2. Графік для опадів
        const ctxPrecip = document.getElementById('precipitation-chart').getContext('2d');
        window.precipitationChart = new Chart(ctxPrecip, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Precipitation (mm)',
                    data: precipitations,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderWidth: 2,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' },
                    tooltip: { mode: 'index', intersect: false }
                },
                scales: {
                    x: { title: { display: true, text: 'Date' } },
                    y: { title: { display: true, text: 'Precipitation (mm)' } }
                }
            }
        });

        // 3. Графік для швидкості вітру
        const ctxWind = document.getElementById('wind-speed-chart').getContext('2d');
        window.windSpeedChart = new Chart(ctxWind, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Wind Speed (km/h)',
                    data: windSpeeds,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderWidth: 2,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' },
                    tooltip: { mode: 'index', intersect: false }
                },
                scales: {
                    x: { title: { display: true, text: 'Date' } },
                    y: { title: { display: true, text: 'Wind Speed (km/h)' } }
                }
            }
        });
    };



    // Закриття другого модального вікна
  document.getElementById('close-modal-2').addEventListener('click', function() {
      document.getElementById('modal-2').style.display = 'none';
      document.getElementById('overlay').style.display = 'none';
  });
});
