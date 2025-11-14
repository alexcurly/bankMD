// // Получаем форму создания из DOM
// const createHistoryForm = document.getElementById('createHistoryForm');
//
// /**
//  * Обработчик отправки формы создания новой записи
//  */
// createHistoryForm.addEventListener('submit', async function(event) {
//     // Предотвращаем стандартную отправку формы
//     event.preventDefault();
//
//     // Собираем данные из формы в объект
//     const formData = new FormData(event.target);
//     const historyData = {
//         transferAuditId: getNumberValue(formData.get('transferAuditId')),
//         profileAuditId: getNumberValue(formData.get('profileAuditId')),
//         accountAuditId: getNumberValue(formData.get('accountAuditId')),
//         antiFraudAuditId: getNumberValue(formData.get('antiFraudAuditId')),
//         publicBankInfoAuditId: getNumberValue(formData.get('publicBankInfoAuditId')),
//         authorizationAuditId: getNumberValue(formData.get('authorizationAuditId'))
//     };
//
//     try {
//         // Отправляем POST запрос для создания новой записи
//         const response = await fetch(`${API_BASE_URL}/save`, {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify(historyData)
//         });
//
//         // Проверяем успешность запроса
//         if (!response.ok) {
//             throw new Error(`HTTP error! status: ${response.status}`);
//         }
//
//         // Получаем созданную запись из ответа
//         const createdHistory = await response.json();
//
//         // Показываем сообщение об успехе
//         alert(`Запись успешно создана с ID: ${createdHistory.id}`);
//
//         // Закрываем модальное окно
//         const modal = bootstrap.Modal.getInstance(document.getElementById('createModal'));
//         modal.hide();
//
//         // Очищаем форму
//         createHistoryForm.reset();
//
//         // Обновляем таблицу с данными
//         refreshHistoryTable();
//
//     } catch (error) {
//         // Обрабатываем ошибки при создании
//         console.error('Ошибка при создании записи:', error);
//         alert('Ошибка при создании записи: ' + error.message);
//     }
// });
//
// /**
//  * Преобразует строковое значение в число, если оно не пустое
//  * @param {string} value - Значение из формы
//  * @returns {number|null} Число или null
//  */
// function getNumberValue(value) {
//     return value ? parseInt(value) : null;
// }