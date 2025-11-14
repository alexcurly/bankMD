// // Базовый URL API для работы с историей
// const API_BASE_URL = 'http://localhost:8088/api/history/history.html';
//
// // DOM элемент таблицы для отображения данных
// const historyTableBody = document.getElementById('historyTableBody');
//
// /**
//  * Загружает все записи истории с сервера и отображает их в таблице
//  */
// async function loadAllHistory() {
//     try {
//         // Отправляем GET запрос для получения всех записей
//         const response = await fetch(`${API_BASE_URL}/getall`);
//
//         // Проверяем успешность запроса
//         if (!response.ok) {
//             throw new Error(`HTTP error! status: ${response.status}`);
//         }
//
//         // Парсим JSON ответ от сервера
//         const historyList = await response.json();
//
//         // Очищаем таблицу перед добавлением новых данных
//         historyTableBody.innerHTML = '';
//
//         // Проверяем есть ли данные для отображения
//         if (historyList.length === 0) {
//             historyTableBody.innerHTML = `
//                 <tr>
//                     <td colspan="8" class="text-center text-muted">
//                         Нет данных для отображения
//                     </td>
//                 </tr>
//             `;
//             return;
//         }
//
//         // Для каждой записи истории создаем строку таблицы
//         historyList.forEach(history => {
//             const row = createHistoryTableRow(history);
//             historyTableBody.appendChild(row);
//         });
//
//     } catch (error) {
//         // Обрабатываем ошибки при загрузке данных
//         console.error('Ошибка при загрузке истории:', error);
//         historyTableBody.innerHTML = `
//             <tr>
//                 <td colspan="8" class="text-center text-danger">
//                     Ошибка при загрузке данных: ${error.message}
//                 </td>
//             </tr>
//         `;
//     }
// }
//
// /**
//  * Создает HTML строку таблицы для одной записи истории
//  * @param {Object} history - Объект записи истории
//  * @returns {HTMLElement} Строка таблицы
//  */
// function createHistoryTableRow(history) {
//     const row = document.createElement('tr');
//
//     // Форматируем значения для отображения (заменяем null на '-')
//     const transferId = history.transferAuditId || '-';
//     const profileId = history.profileAuditId || '-';
//     const accountId = history.accountAuditId || '-';
//     const antiFraudId = history.antiFraudAuditId || '-';
//     const bankInfoId = history.publicBankInfoAuditId || '-';
//     const authId = history.authorizationAuditId || '-';
//
//     // Создаем HTML содержимое строки
//     row.innerHTML = `
//         <td>${history.id}</td>
//         <td>${transferId}</td>
//         <td>${profileId}</td>
//         <td>${accountId}</td>
//         <td>${antiFraudId}</td>
//         <td>${bankInfoId}</td>
//         <td>${authId}</td>
//         <td>
//             <button class="btn btn-sm btn-warning me-1" onclick="openEditModal(${history.id})">
//                 ✏️ Редактировать
//             </button>
//             <button class="btn btn-sm btn-danger" onclick="openDeleteModal(${history.id})">
//                 🗑️ Удалить
//             </button>
//         </td>
//     `;
//
//     return row;
// }
//
// /**
//  * Обновляет данные в таблице
//  */
// function refreshHistoryTable() {
//     loadAllHistory();
// }
//
// // Загружаем данные при загрузке страницы
// document.addEventListener('DOMContentLoaded', function() {
//     loadAllHistory();
// });