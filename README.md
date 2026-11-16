# Лабораторна робота №1

**Розгортання Java-проєкту та базова обробка даних**

**Курс:** Кросплатформні засоби програмування
**Робота:** №1
**Варіант:** 15
**Предметна область:** доставлення посилок
**Мова:** Java 21
**Система збірки:** Maven

## Опис

Програма призначена для читання, перевірки та обробки списку записів про доставлення посилок.

Для кожного запису використовуються такі поля:

```text
id;weightKg;city;deliveryDays;express
```

де:

- `id` — ідентифікатор посилки;
- `weightKg` — вага посилки в кілограмах;
- `city` — місто доставлення;
- `deliveryDays` — термін доставлення в днях;
- `express` — ознака експрес-доставлення (`true` або `false`).

## Показники варіанта 15

Програма обчислює:

- кількість коректних записів;
- середню вагу (кг);
- найбільший термін доставлення (дн.);
- кількість експрес-доставлень.

## Формат вхідних даних

Вхідний файл містить один запис на рядок, без заголовка.

Роздільник полів:

```text
;
```

Кодування:

```text
UTF-8
```

| Поле | Тип | Приклад | Правила перевірки |
|------|-----|---------|-------------------|
| id | String | P001 | не порожнє |
| weightKg | double | 2.5 | число з крапкою, не від'ємне |
| city | String | Львів | не порожнє |
| deliveryDays | int | 3 | ціле число, не від'ємне |
| express | boolean | true | лише `true` або `false` |

Приклад:

```text
P001;2.5;Львів;2;true
P002;10.0;Київ;4;false
P003;0.8;Одеса;3;true
```

Некоректні рядки не враховуються у розрахунках. Для них програма виводить номер рядка та причину помилки.

Приклад вхідного файлу `data/input.csv` містить як коректні, так і некоректні записи.

## Запуск

### Через Maven Wrapper

На macOS/Linux:

```bash
./mvnw -B clean verify
```

На Windows:

```text
mvnw.cmd -B clean verify
```

### Запуск програми

Після компіляції:

```bash
java -cp target/classes ua.lpnu.kzp.Main
```

### Запуск із власним вхідним файлом

```bash
java -cp target/classes ua.lpnu.kzp.Main --input data/input.csv
```

### Запис звіту у файл

```bash
java -cp target/classes ua.lpnu.kzp.Main --input data/input.csv --output out/report.txt
```

За замовчуванням звіт записується у `out/report.txt` і виводиться в консоль.

### Довідка

```bash
java -cp target/classes ua.lpnu.kzp.Main --help
```

### Версія

```bash
java -cp target/classes ua.lpnu.kzp.Main --version
```

Поза JAR-файлом версія виводиться як `dev`.

## Запуск executable JAR

Після виконання:

```bash
./mvnw -B clean package
```

програму можна запустити командою:

```bash
java -jar target/lab01-1.0.0.jar --input data/input.csv
```

Довідка:

```bash
java -jar target/lab01-1.0.0.jar --help
```

Версія:

```bash
java -jar target/lab01-1.0.0.jar --version
```

## Тестування

Для запуску JUnit-тестів:

```bash
./mvnw -B test
```

Тести перевіряють:

- коректний запис і правильний розрахунок показників;
- середню вагу для кількох записів;
- порожній рядок;
- неправильну кількість полів;
- нечислове значення;
- від'ємне значення;
- значення NaN та Infinity у вазі (відхиляються як помилковий формат);
- порожній `id`;
- хибне значення `express`;
- ситуацію, коли всі записи некоректні (без ділення на нуль);
- українські символи;
- параметри командного рядка (`--help`, `--version`, `--input`, `--output`);
- параметр без значення та невідомий параметр;
- відсутній вхідний файл;
- створення файлу звіту.

Поточна тестова конфігурація містить 19 автоматичних тестів
(`ParcelReportTest` — 12, `MainTest` — 7).

## Статичний аналіз

SpotBugs запускається автоматично під час:

```bash
./mvnw -B verify
```

На етапі `verify` виконуються тести, створюється JAR та запускається статичний аналіз SpotBugs.

## GitHub Actions

CI налаштований для трьох операційних систем:

- Ubuntu;
- Windows;
- macOS.

Workflow використовує Java 21 та Maven Wrapper.

Для кожної операційної системи виконується:

```text
clean verify
```

У межах CI перевіряються:

- компіляція проєкту;
- JUnit-тести;
- SpotBugs;
- створення JAR.

Готовий JAR завантажується як GitHub Actions artifact.

## Версія та випуск

Версія програми задана в `pom.xml` (`<version>1.0.0</version>`) і записується в маніфест JAR-файлу.
Перевірка:

```bash
java -jar target/lab01-1.0.0.jar --version
```

Вивід:

```text
lab01 1.0.0
```

Випуск позначається Git-тегом `v1.0.0`. CI запускається і на тегах `v*` та перевіряє,
що тег збігається з версією в `pom.xml`. Готовий JAR доступний у GitHub Release
та як артефакт workflow.

## Робота через GitHub

- Завдання плануються як GitHub Issues.
- Кожна зміна виконується в окремій гілці й вливається в `main` через Pull Request.
- Дефекти пов'язуються з виправленнями через `Closes #N` в описі Pull Request.

## Структура проєкту

```text
.
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   └── bug_report.md
│   ├── workflows/
│   │   └── ci.yml
│   └── pull_request_template.md
├── .mvn/
│   └── wrapper/
│── ai/
│   ├── devops.md
│   ├── manager.md   
├── data/
│   └── input.csv
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ua/lpnu/kzp/
│   │           ├── FileReport.java
│   │           ├── Main.java
│   │           └── ParcelReport.java
│   └── test/
│       └── java/
│           └── ua/lpnu/kzp/
│               ├── MainTest.java
│               └── ParcelReportTest.java
├── .editorconfig
├── .gitattributes
├── .gitignore
├── pom.xml
├── README.md
├── REPORT.md
├── mvnw
└── mvnw.cmd
```

Призначення класів:

- `Main` — розбір аргументів командного рядка і запуск;
- `FileReport` — читання та запис файлів (UTF-8, `Path`);
- `ParcelReport` — перевірка записів, обчислення показників, формування тексту звіту.

## Параметри командного рядка

Програма підтримує такі параметри:

| Параметр | Призначення |
|----------|-------------|
| `--help` | показати довідку |
| `--version` | показати версію програми |
| `--input FILE` | вказати шлях до вхідного CSV-файлу |
| `--output FILE` | вказати шлях до файлу звіту |

За замовчуванням вхідні дані читаються з:

```text
data/input.csv
```

Якщо параметр `--input` або `--output` указано без значення, або параметр невідомий, програма виводить повідомлення про помилку і завершується.

## Приклад результату

Для поточного `data/input.csv` програма отримує:

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

Перевірка середньої ваги: (2.5 + 10.0 + 0.8) / 3 ≈ 4.43.

## Версія

Поточна версія програми:

```text
1.0.0
```

## Лабораторна робота № 2

**Поточна версія:** 2.0.0.

**Варіант:** № 15 · **Предметна область:** доставлення посилок

У лабораторній роботі № 2 виконано перехід від рядкового представлення даних до об’єктної моделі. Формат вхідного CSV та текст звіту збігаються з лабораторною № 1.

### Основні класи

#### Parcel

Основна сутність:

```java
Parcel
```

Поля:

```java
private final String id;
private final double weightKg;
private final String city;
private final int deliveryDays;
private final boolean express;
```

Особливості:

- клас `final`;
- поля `private final`;
- setter-и відсутні;
- валідація виконується в конструкторі;
- `NaN`, `Infinity` і `-Infinity` не допускаються;
- для перевірки ваги використовується `Double.isFinite()`.

Правила коректності:

- `id` і `city` не `null` і не порожні;
- `weightKg` — скінченне число, не менше 0;
- `deliveryDays` не менше 0;
- `express` — лише `true` або `false` (перевіряє `fromCsv`).

Створення з CSV:

```java
Parcel.fromCsv(String line)
```

Формат CSV:

```text
id;weightKg;city;deliveryDays;express
```

#### WeightDelivery

Допоміжне незмінне значення:

```java
record WeightDelivery(double weightKg, int deliveryDays)
```

Record використовується під час обчислення середньої ваги та найбільшого терміну доставлення. Метод `Parcel.toWeightDelivery()` повертає це значення.

### Порівняння Lab 1 і Lab 2

| Характеристика | Lab 1 | Lab 2 |
| --- | --- | --- |
| Представлення запису | рядок та окремі змінні | `Parcel` |
| Розбір CSV | у `ParcelReport.buildReport()` | `Parcel.fromCsv()` |
| Валідація | у циклі обробки | у конструкторі |
| Дані | окремі значення | `List<Parcel>` |
| Допоміжні значення | окремі змінні | `WeightDelivery` |
| Перевірка NaN / Infinity | як помилковий числовий формат | `Double.isFinite()` |
| Створення об’єкта | — | конструктор + Builder |
| Кількість тестів | 19 | 50 |
| Версія | 1.0.0 | 2.0.0 |
| JAR | `lab01-1.0.0.jar` | `lab01-2.0.0.jar` |

### Результати

Для контрольного набору даних `data/input.csv`:

```text
Коректних записів: 3
Середня вага: 4.43 кг
Найбільший термін: 4 дн.
Експрес-доставлень: 2
Помилок: 5
```

Результати Lab 1 і Lab 2 однакові, тому зовнішня поведінка програми після рефакторингу збережена.

### Тестування

Запуск:

```text
.\mvnw.cmd test
```

Результат:

```text
Tests run: 50, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| Тестовий клас | Тестів |
| --- | ---: |
| `MainTest` | 7 |
| `ParcelReportTest` | 12 |
| `ParcelTest` | 15 |
| `ParcelEngineeringTest` | 8 |
| `ParcelBuilderTest` | 8 |
| **Разом** | **50** |

Тести `MainTest` і `ParcelReportTest` (разом 19) залишились із лабораторної № 1 і проходять разом із новими.

Перевіряються:

- коректні та граничні значення;
- `null` і blank;
- від’ємні значення;
- `NaN`;
- `Infinity`;
- `-Infinity`;
- `fromCsv()`;
- `WeightDelivery`;
- коди завершення програми;
- створення об’єкта через Builder;
- валідація під час використання Builder.

### Maven і SpotBugs

Повна перевірка:

```text
.\mvnw.cmd clean verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

### Незмінність і Builder 

`Parcel` є повністю незмінним:

- клас `final`;
- поля `private final`;
- setter-и відсутні.

Для зручного створення об’єктів додано `Parcel.Builder`.

Приклад:

```java
Parcel parcel = Parcel.builder()
        .id("P001")
        .weightKg(2.5)
        .city("Львів")
        .deliveryDays(2)
        .express(true)
        .build();
```

Метод `build()` викликає основний конструктор `Parcel`, тому Builder не обходить валідацію. `ParcelBuilderTest` містить 8 тестів: коректне створення, відхилення некоректних значень, відхилення відсутніх обов’язкових полів, збереження правил валідації.

### JAR

Поточний JAR:

```text
target/lab01-2.0.0.jar
```

Перевірка версії:

```text
java -jar target\lab01-2.0.0.jar --version
```

Результат:

```text
lab01 2.0.0
```

Запуск програми:

```text
java -jar target\lab01-2.0.0.jar --input data\input.csv --output out\report.txt
```

### GitHub Actions

CI запускається на трьох ОС:

| ОС | Результат |
| --- | --- |
| Ubuntu | Success |
| Windows | Success |
| macOS | Success |

У workflow:

- запускається Maven `verify`;
- виконуються автоматизовані тести;
- запускається SpotBugs;
- перевіряється версія;
- JAR публікується як artifact.

Успішний workflow:

https://github.com/igorok-1715/kzplab01-mishchenko/actions/runs/43726664989

Опубліковано 3 artifacts.

### GitHub Issues

| Issue | Зміст |
| --- | --- |
| #23 | `Parcel` і `fromCsv` |
| #24 | `WeightDelivery` |
| #25 | тести `Parcel` і `WeightDelivery` |
| #27 | README і REPORT |
| #30 | `Parcel.Builder` і реліз 2.0.0 |

### Pull Request

| Pull Request | Зміст |
| --- | --- |
| #28 | `Parcel`, `fromCsv`, `WeightDelivery`, тести, документація |
| #29 | інженерний рівень і додаткові тести |
| #31 | `Parcel.Builder` і реліз 2.0.0 |
| #32 | уточнення звіту |

Фінальний Pull Request:

https://github.com/igorok-1715/kzplab01-mishchenko/pull/31

CI на Pull Request успішно пройшов на Ubuntu, Windows і macOS.

### Структура Lab 2

```text
src/
├── main/java/ua/lpnu/kzp/
│   ├── Main.java
│   ├── FileReport.java
│   ├── ParcelReport.java
│   ├── Parcel.java
│   └── WeightDelivery.java
└── test/java/ua/lpnu/kzp/
    ├── MainTest.java
    ├── ParcelReportTest.java
    ├── ParcelTest.java
    ├── ParcelEngineeringTest.java
    └── ParcelBuilderTest.java
```

### Підсумок

У Lab 2:

- створено `Parcel`;
- додано `fromCsv()`;
- реалізовано `WeightDelivery`;
- валідацію перенесено до предметної моделі;
- додано перевірку скінченних чисел;
- `Parcel` залишено повністю незмінним;
- реалізовано `Parcel.Builder`;
- Builder не обходить валідацію конструктора;
- кількість тестів збільшено з 19 до 50;
- SpotBugs не знаходить дефектів;
- CI проходить на Ubuntu, Windows і macOS;
- версію оновлено до 2.0.0;
- JAR публікується як artifact.

Git-тег фінальної версії:

```text
v2.0.0
```

Посилання:

https://github.com/igorok-1715/kzplab01-mishchenko/tree/v2.0.0
