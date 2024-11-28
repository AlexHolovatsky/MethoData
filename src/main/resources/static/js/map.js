document.addEventListener('DOMContentLoaded', function () {
  const map = L.map('map', {
    center: [48.3794, 31.1656],
    zoom: 6,
    minZoom: 5,
    maxZoom: 10,
    maxBounds: [[44.3, 22.1], [52.4, 40.1]],
    maxBoundsViscosity: 1.0,
    scrollWheelZoom: true
  });

  // Додаємо шар карти
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
  }).addTo(map);

  // Дані про заповідники
  const reserves = [
    {
      name: 'Карпатський національний природний парк',
      coords: [48.4022, 24.3598],
      description: 'Це парк з мальовничими горами і стародавніми лісами.'
    },
    {
      name: 'Шацький національний природний парк',
      coords: [51.5144, 23.9388],
      description: 'Відомий своїми озерами, серед яких Світязь.'
    }
  ];

  // Модальні вікна
  const overlay = document.getElementById('overlay');
  const modal1 = document.getElementById('modal-1');
  const modal2 = document.getElementById('modal-2');
  const modal1Title = document.getElementById('modal-1-title');
  const modal1Description = document.getElementById('modal-1-description');
  const closeModal1Button = document.getElementById('close-modal-1');
  const closeModal2Button = document.getElementById('close-modal-2');
  const openModal2Button = document.getElementById('open-modal-2');

  function showModal1(reserve) {
    modal1Title.textContent = reserve.name;
    modal1Description.textContent = reserve.description;
    modal1.style.display = 'block';
    overlay.style.display = 'block';
  }

  function showModal2() {
    modal2.style.display = 'block';
  }

  function closeModal() {
    modal1.style.display = 'none';
    modal2.style.display = 'none';
    overlay.style.display = 'none';
  }

  closeModal1Button.addEventListener('click', closeModal);
  closeModal2Button.addEventListener('click', closeModal);
  openModal2Button.addEventListener('click', showModal2);
  overlay.addEventListener('click', closeModal);

  // Додаємо маркери
  reserves.forEach(reserve => {
    L.marker(reserve.coords)
      .addTo(map)
      .bindPopup(`
        <b>${reserve.name}</b><br>
        ${reserve.description}<br>
        <button onclick="showModal1({name: '${reserve.name}', description: '${reserve.description}'})">
          Детальніше
        </button>
      `);
  });

  // Забезпечуємо доступність функції
  window.showModal1 = showModal1;
});