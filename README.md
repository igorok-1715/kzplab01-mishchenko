# kzplab01-mishchenko — доставлення посилок (варіант 15)

## Призначення
Консольна програма читає записи про посилки з `data/input.csv`, перевіряє їх,
обчислює чотири показники та виводить звіт у консоль і файл `out/report.txt`.
Хибні рядки пропускаються з повідомленням про номер рядка та причину.

## Формат входу
UTF-8, один запис у рядку, поля розділені `;`, без заголовка:

```text
id;weightKg;city;deliveryDays;express
```

| Поле | Тип | Приклад | Правила |
|------|-----|---------|---------|
| id | String | P001 | не порожнє |
| weightKg | double | 2.5 | число з крапкою, не від'ємне |
| city | String | Львів | не порожнє |
| deliveryDays | int | 3 | ціле, не від'ємне |
| express | boolean | true | лише `true` або `false` |

## Показники
1. Кількість коректних записів.
2. Середня вага (кг).
3. Найбільший термін доставлення (дн.).
4. Кількість експрес-доставлень.

## Запуск

Без Maven (рівень 1), з кореня проєкту:

```text
javac -encoding UTF-8 -d target/classes src/main/java/ua/lpnu/kzp/*.java
java -cp target/classes ua.lpnu.kzp.Main
```

З Maven Wrapper (рівень 2+). На Windows — `mvnw.cmd`, на macOS/Ubuntu — `./mvnw`:

```text
mvnw.cmd clean verify
java -jar target/lab01-1.0.0.jar
java -jar target/lab01-1.0.0.jar --help
java -jar target/lab01-1.0.0.jar --version
java -jar target/lab01-1.0.0.jar --input data/input.csv --output out/report.txt
```

## Приклад виводу

```text
Коректних записів: 3
Середня вага: 4.43 кг
Найбільший термін: 4 дн.
Експрес-доставлень: 2
Помилок: 5
Рядок 4: числове поле має помилковий формат
Рядок 5: від'ємне числове значення
Рядок 6: порожній id або місто
Рядок 7: очікується 5 полів, отримано 3
Рядок 8: express має бути true або false
```

<<<<<<< HEAD
## Рівень 2 — інженерний
- Maven-проєкт (`pom.xml`) з Maven Wrapper (`mvnw`, `mvnw.cmd`).
- Залежність JUnit 5 підтягується Maven; тести: `ParcelReportTest`, `MainTest`.
- Статичний аналізатор SpotBugs запускається у фазі `verify`.
- `mvnw clean verify` створює виконуваний JAR із залежностями: `target/lab01-1.0.0.jar`.
- Робота планується й здається через GitHub Issues та Pull Request;
  дефекти пов'язуються з виправленнями через `Closes #N`.
- CI: `.github/workflows/ci.yml` (Ubuntu, Windows, macOS).

=======
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
## Структура
- `Main` — розбір аргументів і запуск.
- `FileReport` — читання/запис файлів (UTF-8, `Path`).
- `ParcelReport` — перевірка записів, обчислення, текст звіту.
<<<<<<< HEAD
- `ParcelReportTest`, `MainTest` — тести JUnit 5 (`src/test/java`).
=======
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
