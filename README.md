# Лабораторная работа 1

REST API на Java 21, Spring Boot, Maven и PostgreSQL. Реализованы пункты 2 и 3 задания.

## Запуск

Создайте базу `info_sec_lab1` в PostgreSQL и задайте переменные окружения:

```sh
export DB_URL=jdbc:postgresql://localhost:5432/info_sec_lab1
export DB_USER=postgres
export DB_PASSWORD='пароль_от_базы'
export JWT_SECRET="$(openssl rand -hex 32)"
mvn spring-boot:run
```

`JWT_SECRET` должен содержать не менее 32 байт. Таблица пользователей создаётся автоматически.

## API

Регистрация пользователя:

```sh
curl -i -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"student","password":"password123"}'
```

Вход возвращает JWT в поле `token`:

```sh
curl -i -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"student","password":"password123"}'
```

Список пользователей доступен только с токеном:

```sh
curl -i http://localhost:8080/api/data \
  -H 'Authorization: Bearer ВАШ_ТОКЕН'
```

Без токена `GET /api/data` возвращает HTTP 401. В ответах списка нет паролей и их хэшей.

## Защита

- Доступ к PostgreSQL идёт через Spring Data JPA: значения параметризуются, SQL не собирается из пользовательских строк.
- Имена пользователей ограничены буквами, цифрами и `_`; перед выдачей в `GET /api/data` они дополнительно экранируются для HTML. API возвращает JSON.
- Пароли хранятся как BCrypt-хэши. При входе сервер выдаёт подписанный JWT на 1 час; Spring Security проверяет подпись и срок действия токена на защищённом маршруте.
