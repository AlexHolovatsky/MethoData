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


    fetch('/api/reserves')
        .then(response => response.json())
        .then(reserves => {
            const customIcon = L.icon({
                iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34]
            });

            reserves.forEach(reserve => {
                const coords = reserve.location.split(',').map(coord => parseFloat(coord.trim()));
                L.marker(coords, { icon: customIcon })
                    .addTo(map)
                    .bindPopup(`
                    <b>${reserve.name}</b><br>
                    ${reserve.description}<br>
                    <button onclick="showModal1('${reserve.name}', '${reserve.description}')">
                        Детальніше
                    </button>
                `);
            });
        })
        .catch(error => console.error('Error fetching reserves:', error));

    const customIcon = L.icon({
        iconUrl: './src/main/resources/static/location.png',
        iconSize:     [40, 43], // size of the icon
        shadowSize:   [50, 64], // size of the shadow
        iconAnchor:   [20, 43], // point of the icon which will correspond to marker's location
        shadowAnchor: [4, 62],  // the same for the shadow
        popupAnchor:  [0, -43]
    });

  map.on('zoom', function () {
      if (map.getZoom() > 10) {
          map.setZoom(10);
      }
      if (map.getZoom() < 5) {
          map.setZoom(5);
      }
  });

  // Функція для відкриття першого модального вікна
  window.showModal1 = function(name, description) {
      document.getElementById('modal-1-title').textContent = name;
      document.getElementById('modal-1-description').textContent = description;
      document.getElementById('overlay').style.display = 'block';
      document.getElementById('modal-1').style.display = 'block';
  };

  // Закриття першого модального вікна
  document.getElementById('close-modal-1').addEventListener('click', function() {
      document.getElementById('overlay').style.display = 'none';
      document.getElementById('modal-1').style.display = 'none';
  });

  // Відкриття другого модального вікна
  document.getElementById('open-modal-2').addEventListener('click', function() {
      document.getElementById('modal-1').style.display = 'none';
      document.getElementById('modal-2').style.display = 'block';
  });

  // Закриття другого модального вікна
  document.getElementById('close-modal-2').addEventListener('click', function() {
      document.getElementById('modal-2').style.display = 'none';
      document.getElementById('overlay').style.display = 'none';
  });
});
