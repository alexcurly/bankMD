// /**
//  * Открывает диалог подтверждения удаления записи
//  * @param {number} id - ID записи для удаления
//  */
// function openDeleteModal(id) {
//     // Подтверждаем удаление у пользователя
//     if (confirm('Вы уверены, что хотите удалить эту запись?')) {
//         deleteHistory(id);
//     }
// }
//
// /**
//  * Удаляет запись истории по ID
//  * @param {number} id - ID записи для удаления
//  */
// async function deleteHistory(id) {
//     try {
//         // Отправляем DELETE запрос
//         const response = await fetch(`${API_BASE_URL}/delete/${id}`, {
//             method: 'DELETE'
//         });
//
//         if (!response.ok) {
//             throw new Error(`HTTP error! status: ${response.status}`);
//         }
//
//         // Показываем сообщение об успехе
//         alert('Запись успешно удалена!');
//
//         // Обновляем таблицу
//         refreshHistoryTable();
//
//     } catch (error) {
//         console.error('Ошибка при удалении записи:', error);
//         alert('Ошибка при удалении записи: ' + error.message);
//     }
// }