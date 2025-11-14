// Текущий ID редактируемой записи
let currentEditId = null;

/**
 * Открывает модальное окно редактирования и загружает данные записи
 * @param {number} id - ID записи для редактирования
 */
async function openEditModal(id) {
    currentEditId = id;

    try {
        // Загружаем данные записи по ID
        const response = await fetch(`${API_BASE_URL}/get/${id}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const history = await response.json();

        // Заполняем форму данными записи
        document.getElementById('editId').value = history.id;
        document.getElementById('editTransferAuditId').value = history.transferAuditId || '';
        document.getElementById('editProfileAuditId').value = history.profileAuditId || '';
        document.getElementById('editAccountAuditId').value = history.accountAuditId || '';
        document.getElementById('editAntiFraudAuditId').value = history.antiFraudAuditId || '';
        document.getElementById('editPublicBankInfoAuditId').value = history.publicBankInfoAuditId || '';
        document.getElementById('editAuthorizationAuditId').value = history.authorizationAuditId || '';

        // Показываем модальное окно
        const editModal = new bootstrap.Modal(document.getElementById('editModal'));
        editModal.show();

    } catch (error) {
        console.error('Ошибка при загрузке данных для редактирования:', error);
        alert('Ошибка при загрузке данных: ' + error.message);
    }
}

// Обработчик отправки формы редактирования
document.getElementById('editHistoryForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const historyData = {
        id: currentEditId,
        transferAuditId: getNumberValue(formData.get('transferAuditId')),
        profileAuditId: getNumberValue(formData.get('profileAuditId')),
        accountAuditId: getNumberValue(formData.get('accountAuditId')),
        antiFraudAuditId: getNumberValue(formData.get('antiFraudAuditId')),
        publicBankInfoAuditId: getNumberValue(formData.get('publicBankInfoAuditId')),
        authorizationAuditId: getNumberValue(formData.get('authorizationAuditId'))
    };

    try {
        // Отправляем PUT запрос для обновления записи
        const response = await fetch(`${API_BASE_URL}/update`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(historyData)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        alert('Запись успешно обновлена!');

        // Закрываем модальное окно
        const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
        modal.hide();

        // Обновляем таблицу
        refreshHistoryTable();

    } catch (error) {
        console.error('Ошибка при обновлении записи:', error);
        alert('Ошибка при обновлении записи: ' + error.message);
    }
});