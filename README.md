# Лабораторна робота №1

**Розгортання Java-проєкту та базова обробка даних**

**Курс:** Кросплатформні засоби програмування | **Робота:** №1 | **Варіант:** 15 | **Мова:** Java 21 | **Система збірки:** Maven

## Опис

Програма призначена для читання, перевірки та обробки списку посилок.
Для кожної посилки використовуються такі поля:
`id;weightKg;city;deliveryDays;express`

де:
* `id` — ідентифікатор посилки;
* `weightKg` — вага посилки в кілограмах;
* `city` — місто призначення;
* `deliveryDays` — термін доставлення у днях;
* `express` — ознака експрес-доставлення (`true`/`false`).

## Метрики варіанта 15

Програма обчислює:
1. кількість валідних записів (посилок);
2. середню вагу посилок;
3. найбільший термін доставлення;
4. кількість експрес-доставлень.

## Формат вхідних даних

Вхідний файл містить один запис на рядок.  
Роздільник полів: `;`  
Кодування: `UTF-8`

**Приклад (`data/input.csv`):**
```text
TRK-001;1.5;Kyiv;3;true
TRK-002;3.2;Lviv;5;false
TRK-003;0.8;Odesa;2;true
TRK-004;помилка;Kharkiv;4;false
TRK-005;-2.5;Dnipro;1;true

Невалідні рядки не враховуються у розрахунках. Для них програма виводить номер рядка та причину помилки.
Приклад вхідного файлу містить як валідні, так і невалідні записи.

Запуск
Через Maven Wrapper
На macOS/Linux:

Bash
./mvnw -B clean verify
На Windows:

DOS
mvnw.cmd -B clean verify
Запуск програми
Після компіляції:

Bash
java -cp target/classes ua.lpnu.kzp.Main
Запуск із власним вхідним файлом
Bash
java -cp target/classes ua.lpnu.kzp.Main --input data/input.csv
Запис звіту у файл
Bash
java -cp target/classes ua.lpnu.kzp.Main --input data/input.csv --output report.txt
Довідка
Bash
java -cp target/classes ua.lpnu.kzp.Main --help
Версія
Bash
java -cp target/classes ua.lpnu.kzp.Main --version
Запуск executable JAR
Після виконання:

Bash
./mvnw -B clean package
програму можна запустити командою:

Bash
java -jar target/lab01-1.0.0.jar --input data/input.csv
Довідка:

Bash
java -jar target/lab01-1.0.0.jar --help
Версія:

Bash
java -jar target/lab01-1.0.0.jar --version
Тестування
Для запуску JUnit-тестів:

Bash
./mvnw -B test
Тести перевіряють:

правильний розрахунок середньої ваги, максимального терміну та експрес-доставлень;

від'ємну вагу або термін доставлення;

нечислове значення ваги чи днів;

порожній рядок;

неправильну кількість полів;

ситуацію, коли всі записи невалідні.

Поточна тестова конфігурація містить набір автоматичних тестів.

Статичний аналіз
SpotBugs запускається автоматично під час:

Bash
./mvnw -B verify
На етапі verify виконуються тести, створюється JAR та запускається статичний аналіз SpotBugs.

GitHub Actions
CI налаштований для трьох операційних систем:

Ubuntu;

Windows;

macOS.

Workflow використовує Java 21 та Maven Wrapper. Для кожної операційної системи виконується clean verify.

У межах CI перевіряються:

компіляція проєкту;

JUnit-тести;

SpotBugs;

створення JAR.

Готовий JAR завантажується як GitHub Actions artifact.

Структура проєкту

.
├── .github/
│   └── workflows/
│       └── ci.yml
├── data/
│   └── input.csv
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ua/
│   │           └── lpnu/
│   │               └── kzp/
│   │                   ├── Main.java
│   │                   ├── Parcel.java
│   │                   ├── ParcelAnalyzer.java
│   │                   ├── ParcelParser.java
│   │                   └── ReportFormatter.java
│   └── test/
│       └── java/
│           └── ua/
│               └── lpnu/
│                   └── kzp/
│                       ├── ParcelAnalyzerTest.java
│                       └── ParcelParserTest.java
├── .gitignore
├── pom.xml
├── README.md
├── mvnw
└── mvnw.cmd

Параметри командного рядкаПрограма підтримує такі параметри:ПараметрПризначення--helpпоказати довідку--versionпоказати версію програми--input FILEвказати шлях до вхідного CSV-файлу--output FILEвказати шлях до файлу звітуЗа замовчуванням вхідні дані читаються з: data/input.csvПриклад результатуДля поточного data/input.csv програма отримує:PlaintextРядок 4: вага посилки має бути числом
Рядок 5: вага посилки не може бути від'ємною

Лабораторна робота №1
Варіант 15

Кількість валідних записів: 3
Середня вага: 1.83 кг
Найбільший термін: 5 днів
Кількість експрес-доставлень: 2
ВерсіяПоточна версія програми:1.0.0
