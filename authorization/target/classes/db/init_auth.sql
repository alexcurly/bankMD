-- Создание схемы auth, если она не существует
CREATE SCHEMA IF NOT EXISTS auth;

-- Таблица ролей пользователей
CREATE TABLE IF NOT EXISTS auth.roles (
                                          id SERIAL PRIMARY KEY,           -- Автоинкрементный первичный ключ
                                          role_name TEXT UNIQUE NOT NULL,  -- Уникальное название роли
                                          description TEXT                 -- Описание роли
);

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS auth.users (
                                          id SERIAL PRIMARY KEY,           -- Автоинкрементный первичный ключ
                                          username TEXT UNIQUE NOT NULL,   -- Уникальное имя пользователя
                                          password_hash TEXT NOT NULL,     -- Хэш пароля (BCrypt)
                                          role_id INT NOT NULL REFERENCES auth.roles(id), -- Внешний ключ к роли
                                          profile_id INT,                  -- Ссылка на профиль в другом модуле
                                          is_enabled BOOLEAN DEFAULT TRUE, -- Флаг активности пользователя
                                          created_at TIMESTAMP DEFAULT NOW() -- Дата создания
);

-- Наполнение таблицы ролей начальными данными
INSERT INTO auth.roles (role_name, description) VALUES
                                                    ('client', 'Клиент банка'),           -- Роль клиента
                                                    ('operator', 'Оператор поддержки'),   -- Роль оператора
                                                    ('fraud_analyst', 'Аналитик антифрода'), -- Роль аналитика
                                                    ('admin', 'Администратор')            -- Роль администратора
ON CONFLICT (role_name) DO NOTHING;   -- Игнорировать если роли уже существуют

-- Создание тестовых пользователей
-- Пароли: password123 (в продакшене использовать реальные BCrypt хэши)
INSERT INTO auth.users (username, password_hash, role_id) VALUES
                                                              ('client', '$2a$12$ABC123...bcryptHash...', (SELECT id FROM auth.roles WHERE role_name = 'client')),
                                                              ('operator', '$2a$12$DEF456...bcryptHash...', (SELECT id FROM auth.roles WHERE role_name = 'operator')),
                                                              ('fraud', '$2a$12$GHI789...bcryptHash...', (SELECT id FROM auth.roles WHERE role_name = 'fraud_analyst')),
                                                              ('admin', '$2a$12$JKL012...bcryptHash...', (SELECT id FROM auth.roles WHERE role_name = 'admin'))
ON CONFLICT (username) DO NOTHING; -- Игнорировать если пользователи уже существуют

