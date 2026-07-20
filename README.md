# Cloud-Native Java Spring Boot REST API Backend with GitLab CI/CD

Промышленный бэкенд-микросервис на базе фреймворка Spring Boot (v3.2.4), функционирующий в распределенном облачном кластере Kubernetes под управлением автоматического конвейера сборки.

## Архитектура и функционал приложения
Приложение реализует REST API управления сущностями пользователей и построено по слоям (Controller -> Repository -> Entity):
* `POST /users` — Прием JSON-данных, валидация и запись через Hibernate ORM в удаленную СУБД PostgreSQL (настраивается через [deploy_postgres.yml](https://github.com/EvgenKli/my-ansible-automation/blob/main/deploy_postgres.yml)).
* `GET /users` — Высокоскоростное чтение списка пользователей из базы данных.
* `GET /` — Кастомная страница верификации статуса работоспособности инфраструктуры.
* `/error` — Кастомный обработчик ошибок 404/500, возвращающий структурированную JSON-шпаргалку для пользователя.

## Контейнеризация (Dockerfile)
Спроектирован оптимизированный многоэтапный Multi-stage [Dockerfile](http://github.com/EvgenKli/my-spring-backend/blob/main/Dockerfile):
* **Этап 1 (Builder):** Компиляция кода утилитой Maven на базе образа `maven:alpine`.
* **Этап 2 (Final JRE):** Копирование готового бинарного `.jar` файла в ультра-легкий дистрибутив `eclipse-temurin:21-jre-alpine`.
* **Результат:** Размер продакшен-образа уменьшен в 10 раз (до 50 МБ), что минимизирует зону атаки (Attack Surface).

## Автоматический конвейер (GitLab CI/CD)
В корне проекта настроен файл [.gitlab-ci.yml](https://github.com/EvgenKli/my-spring-backend/blob/main/.gitlab-ci.yml), реализующий концепцию GitOps и деплоя без простоя (Zero Downtime Deployment):
1. **Stage: build-and-pack** — Локальный `gitlab-runner` ловит пуш кода, собирает Docker-образ с флагом `--no-cache` и пушит его в закрытый безопасный склад GitLab Container Registry.
2. **Stage: deploy-to-k8s** — Раннер динамически создает сессию безопасности SSH (права 600), копирует актуальные манифесты из папки `k8s/` на Мастер-ноду, применяет их через `kubectl apply -R -f` и запускает плавное обновление (Rolling Update) подов.

## Связанные компоненты экосистемы
* Развертывание облачной инфраструктуры (IaC): [my-infrastructure-iac-project](https://github.com/EvgenKli/my-infrastructure-iac-project)
* Настройка ОС, СУБД и Helm: [my-ansible-automation](https://github.com/EvgenKli/my-ansible-automation)

## Инструкция для ручного тестирования API

### Создание записи пользователя (POST)
```bash
curl -X POST http://5.35.28.230:8085/users \
  -H "Content-Type: application/json" \
  -d '{"name": "YourName", "email": "your@email.com"}'
```

### Получение списка пользователей (GET)
```bash
curl http://5.35.28.230:8085/users
```

### Проверка главной страницы (GET)
```bash
curl http://5.35.28.230:8085
```

## Безопасность и управление секретами (Security Disclaimer)
В рамках данной лабораторной инфраструктуры учетные данные к базе данных (`spring.datasource.password`) намеренно оставлены в открытом виде в демонстрационных целях для обеспечения автономного развертывания проекта «из коробки» проверяющими специалистами. В промышленной эксплуатации данные секреты инжектируются в среду выполнения динамически через маскированные переменные окружения CI/CD или Kubernetes Secrets.
