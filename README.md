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

# Лабораторна робота №2

**Рефакторинг Java-проєкту та перехід до об'єктної моделі**

**Курс:** Кросплатформні засоби програмування  
**Робота:** №2  
**Варіант:** 15  
**Предметна область:** доставлення посилок  
**Мова:** Java 21  
**Система збірки:** Maven  
**Поточна версія:** `2.0.0`

## Опис

У лабораторній роботі №2 виконано перехід від рядкового представлення даних до об'єктної моделі.

Основна мета — покращити структуру програми, забезпечити інкапсуляцію даних, перенести валідацію до класу-сутності та додати автоматизовані тести.

Під час рефакторингу:

- створено клас-сутність `Parcel`;
- реалізовано фабричний метод `fromCsv()`;
- додано допоміжний `record WeightDelivery`;
- реалізовано незмінність об'єктів;
- додано шаблон проєктування Builder;
- розширено автоматизоване тестування.

**Формат вхідного CSV-файлу та результати роботи програми залишилися сумісними з лабораторною роботою №1.**

## Основні класи

### Parcel

`Parcel` — основний клас-сутність, який представляє одну посилку.

Клас містить такі поля:

```java
private final String id;
private final double weightKg;
private final String city;
private final int deliveryDays;
private final boolean express;
```

**Особливості реалізації:**

- клас оголошено `final`;
- усі поля мають модифікатори `private final`;
- setter-и відсутні;
- валідація виконується в конструкторі;
- значення `NaN`, `Infinity` і `-Infinity` не допускаються;
- для перевірки ваги використовується `Double.isFinite()`.

**Правила коректності даних:**

- `id` — ідентифікатор посилки, не може бути `null` або порожнім;
- `weightKg` — вага посилки, скінченне число не менше 0;
- `city` — місто доставлення, не може бути `null` або порожнім;
- `deliveryDays` — термін доставлення, не менше 0;
- `express` — ознака експрес-доставлення (`true` або `false`).

### Створення об'єкта з CSV

Для перетворення CSV-рядка на об'єкт використовується статичний фабричний метод:

```java
Parcel.fromCsv(String line)
```

**Формат CSV:**

```text
id;weightKg;city;deliveryDays;express
```

Метод `fromCsv()` перевіряє структуру рядка, перетворює текстові значення на відповідні типи та створює об'єкт `Parcel`.

Якщо дані некоректні, запис не додається до списку посилок.

### WeightDelivery

`WeightDelivery` — допоміжний незмінний тип даних, реалізований за допомогою `record`.

```java
record WeightDelivery(double weightKg, int deliveryDays)
```

Record містить:

- `weightKg` — вагу посилки;
- `deliveryDays` — термін доставлення.

Для отримання цих значень із класу `Parcel` використовується метод:

```java
Parcel.toWeightDelivery()
```

`WeightDelivery` використовується під час обчислення середньої ваги та найбільшого терміну доставлення.

## Порівняння Lab 1 і Lab 2

| Характеристика | Lab 1 | Lab 2 |
|---|---|---|
| Представлення запису | Рядок та окремі змінні | `Parcel` |
| Розбір CSV | `ParcelReport.buildReport()` | `Parcel.fromCsv()` |
| Валідація | У циклі обробки | У конструкторі |
| Зберігання даних | Окремі значення | `List<Parcel>` |
| Допоміжні значення | Окремі змінні | `WeightDelivery` |
| Перевірка NaN / Infinity | Обробка помилкового числового формату | `Double.isFinite()` |
| Створення об'єкта | Без окремого класу-сутності | Конструктор + Builder |
| Кількість тестів | 19 | 50 |
| Версія | `1.0.0` | `2.0.0` |
| JAR | `lab01-1.0.0.jar` | `lab01-2.0.0.jar` |

## Результати виконання

Для перевірки використано контрольний файл:

```text
data/input.csv
```

**Результат роботи програми:**

```text
Коректних записів: 3
Середня вага: 4.43 кг
Найбільший термін: 4 дн.
Експрес-доставлень: 2
Помилок: 5
```

**Показники варіанта №15:**

Програма обчислює:

- кількість коректних записів;
- середню вагу посилок (кг);
- найбільший термін доставлення (дні);
- кількість експрес-доставлень.

Результати Lab 1 і Lab 2 однакові. Отже, після рефакторингу зовнішня поведінка програми залишилася незмінною.

## Тестування

Для автоматизованого тестування використовується **JUnit 5**.

**Команда запуску тестів:**

```powershell
.\mvnw.cmd test
```

**Результат виконання:**

```text
Tests run: 50, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Розподіл тестів

| Тестовий клас | Кількість тестів |
|---|---:|
| `MainTest` | 7 |
| `ParcelReportTest` | 12 |
| `ParcelTest` | 15 |
| `ParcelEngineeringTest` | 8 |
| `ParcelBuilderTest` | 8 |
| **Разом** | **50** |

**Перевіряються такі випадки:**

- коректні та граничні значення;
- `null` і порожні рядки;
- від'ємна вага;
- від'ємний термін доставлення;
- `NaN`;
- `Infinity`;
- `-Infinity`;
- правильність роботи `fromCsv()`;
- неправильна кількість полів CSV;
- неправильний числовий формат;
- робота `WeightDelivery`;
- коди завершення програми;
- створення об'єктів через Builder;
- неможливість обходу валідації через Builder.

Попередні 19 тестів лабораторної роботи №1 продовжують успішно проходити.

## Maven і SpotBugs

Для керування залежностями та збіркою використовується Maven.

**Команда повної перевірки:**

```powershell
.\mvnw.cmd clean verify
```

**Результат:**

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

Статичний аналіз SpotBugs не виявив дефектів.

## Незмінність і Builder ⭐

Клас `Parcel` реалізовано як повністю незмінний.

**Основні характеристики:**

- клас `final`;
- поля `private final`;
- setter-и відсутні;
- перевірка значень виконується під час створення об'єкта.

Для зручного створення об'єктів реалізовано:

```java
Parcel.Builder
```

**Приклад використання Builder:**

```java
Parcel parcel = Parcel.builder()
        .id("P001")
        .weightKg(2.5)
        .city("Львів")
        .deliveryDays(2)
        .express(true)
        .build();
```

Метод `build()` викликає основний конструктор `Parcel`, тому Builder не дозволяє обійти перевірку інваріантів.

**Для Builder реалізовано 8 тестів.**

Перевіряються:

- коректне створення об'єкта;
- відхилення некоректної ваги;
- відхилення від'ємного терміну доставлення;
- відхилення відсутніх обов'язкових полів;
- збереження правил валідації конструктора.

## JAR

Після успішної збірки створюється виконуваний JAR-файл.

**Поточний JAR:**

```text
target/lab01-2.0.0.jar
```

**Перевірка версії:**

```powershell
java -jar target\lab01-2.0.0.jar --version
```

**Результат:**

```text
lab01 2.0.0
```

**Запуск програми:**

```powershell
java -jar target\lab01-2.0.0.jar --input data\input.csv --output out\report.txt
```

Програма читає CSV-файл, перевіряє записи, обчислює статистику та формує текстовий звіт.

## GitHub Actions

Для автоматизації перевірок використовується GitHub Actions.

**CI запускається на трьох операційних системах:**

| Операційна система | Результат |
|---|---|
| Ubuntu | Success |
| Windows | Success |
| macOS | Success |

**Під час виконання workflow:**

- запускається Maven `verify`;
- виконуються автоматизовані тести;
- запускається SpotBugs;
- перевіряється версія програми;
- JAR публікується як artifact.

**Успішний workflow:**

https://github.com/igorok-1715/kzplab01-mishchenko/actions/runs/43726664989

У результаті опубліковано **3 JAR artifacts**.

## GitHub Issues

Для організації роботи використано GitHub Issues.

| Issue | Виконана робота |
|---|---|
| `#23` | Створення `Parcel` і методу `fromCsv()` |
| `#24` | Реалізація `WeightDelivery` |
| `#25` | Тести `Parcel` і `WeightDelivery` |
| `#27` | Оновлення README і REPORT |
| `#30` | Реалізація `Parcel.Builder` і реліз `2.0.0` |

Усі зазначені Issues завершено та закрито.

## Pull Requests

Для внесення змін до проєкту використано Pull Requests.

| Pull Request | Виконана робота |
|---|---|
| `#28` | `Parcel`, `fromCsv()`, `WeightDelivery`, тести та документація |
| `#29` | Інженерний рівень і додаткові тести |
| `#31` | `Parcel.Builder` і реліз `2.0.0` |
| `#32` | Оновлення документації |

**Фінальний Pull Request основної реалізації:**

https://github.com/igorok-1715/kzplab01-mishchenko/pull/31

CI на Pull Request успішно пройшов на Ubuntu, Windows і macOS.

## Структура проєкту

**Основні файли лабораторної роботи №2:**

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

## Підсумок

У лабораторній роботі №2:

- створено клас-сутність `Parcel`;
- реалізовано метод `fromCsv()`;
- додано `record WeightDelivery`;
- валідацію перенесено до предметної моделі;
- реалізовано перевірку скінченних чисел;
- забезпечено незмінність `Parcel`;
- реалізовано `Parcel.Builder`;
- Builder не обходить валідацію конструктора;
- кількість тестів збільшено з 19 до 50;
- SpotBugs не виявив дефектів;
- CI успішно працює на Ubuntu, Windows і macOS;
- версію оновлено до `2.0.0`;
- JAR публікується як artifact;
- результати лабораторних робіт №1 і №2 залишилися однаковими.

**Git-тег фінальної версії:**

```text
v2.0.0
```

**Посилання на реліз:**

https://github.com/igorok-1715/kzplab01-mishchenko/tree/v2.0.0

**Репозиторій проєкту:**

https://github.com/igorok-1715/kzplab01-mishchenko

# Лабораторна робота №3

Поточна версія: 3.0.0.

Варіант: №15  
Предметна область: доставлення посилок.

У Lab 3 модель розширено за допомогою успадкування, enum і поліморфізму.

## Основні зміни

Створено ієрархію:

```text
Parcel
├── RegularParcel
└── ExpressParcel
```

Parcel став абстрактним базовим класом і містить спільні поля та валідацію.

RegularParcel і ExpressParcel є різновидами Parcel (is-a), тому для них використано успадкування.

Підтипи мають власні правила:

- RegularParcel — вартість доставлення обчислюється за формулою `50.0 + weightKg * 10.0`, термін залишається незмінним;
- ExpressParcel — вартість доставлення обчислюється за формулою `100.0 + weightKg * 20.0`, термін скорочується на один день, але не нижче нуля.

Додано enum:

```java
DeliveryKind
```

зі значеннями:

```text
REGULAR
EXPRESS
```

## Поліморфізм

У базовому класі оголошено:

```java
public abstract DeliveryKind getDeliveryKind();
public abstract int calculateDeliveryTime();
public abstract double calculateDeliveryCost();
```

Підтипи мають різні реалізації цих методів.

Об'єкти зберігаються у:

```java
List<Parcel>
```

і обробляються через:

```java
parcel.calculateDeliveryTime();
parcel.calculateDeliveryCost();
```

без if або switch за конкретним підтипом.

## equals() і hashCode()

Реалізовано логічну рівність об'єктів через:

```java
equals()
hashCode()
```

Рівність перевірено за допомогою:

```java
HashSet<Parcel>
```

Рівні об'єкти не створюють дублікати у HashSet.

## Порівняння Lab 2 і Lab 3

| Характеристика | Lab 2 | Lab 3 |
|---|---|---|
| Базова модель | Один Parcel | Абстрактний Parcel |
| Типи посилок | Один клас | RegularParcel, ExpressParcel |
| Категорія | boolean express | DeliveryKind |
| Розрахунок вартості | Окремий метод відсутній | Поліморфний calculateDeliveryCost() |
| Розрахунок терміну | deliveryDays | calculateDeliveryTime() |
| Обробка | List<Parcel> | List<Parcel> з різними підтипами |
| Рівність | Стандартна Object | equals() + hashCode() |
| Перевірка колекції | Не використовувалась | HashSet |
| Кількість тестів | 50 | 79 |
| Версія | 2.0.0 | 3.0.0 |
| JAR | lab01-2.0.0.jar | lab01-3.0.0.jar |

## Сумісність із Lab 2

Формат CSV не змінено:

```text
id;weightKg;city;deliveryDays;express
```

Контрольний результат також залишився без змін:

```text
Кількість коректних записів: 3
Кількість некоректних записів: 5
Середня вага посилок: 4.43 кг
Найбільший термін доставлення: 4 дні
Кількість експрес-доставлень: 2
```

## Тестування

Запуск:

```powershell
.\mvnw.cmd test
```

Результат:

```text
Tests run: 79, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Повна перевірка:

```powershell
.\mvnw.cmd verify
```

SpotBugs:

```text
BugInstance size is 0
Error size is 0
BUILD SUCCESS
```

## JAR

Створення:

```powershell
.\mvnw.cmd package
```

Поточний JAR:

```text
target/lab01-3.0.0.jar
```

Перевірка версії:

```powershell
java -jar target\lab01-3.0.0.jar --version
```

Результат:

```text
lab01 3.0.0
```

## Додаткове завдання ⭐ — sealed-ієрархія

Базовий тип оголошено як sealed:

```java
public abstract sealed class Parcel
        permits RegularParcel, ExpressParcel
```

Тому безпосередніми підтипами можуть бути лише:

```text
RegularParcel
ExpressParcel
```

Обидва підтипи оголошено як final.

Для демонстрації вичерпної перевірки використано pattern matching у switch:

```java
return switch (parcel) {
    case RegularParcel regular -> "Звичайне доставлення";
    case ExpressParcel express -> "Експрес-доставлення";
};
```

Гілка default не потрібна, оскільки компілятор знає повний перелік дозволених підтипів.

Switch не дублює логіку calculateDeliveryTime() та calculateDeliveryCost(): він використовується лише для визначення конкретного типу посилки та повернення його текстового опису.

Для sealed-ієрархії додано окремі тести. Загальна кількість тестів — 79.

## Підсумок

У Lab 3:

- створено абстрактний Parcel;
- додано RegularParcel і ExpressParcel;
- додано DeliveryKind;
- реалізовано поліморфні calculateDeliveryTime() і calculateDeliveryCost();
- реалізовано equals() і hashCode();
- рівність перевірено через HashSet;
- реалізовано sealed-ієрархію та pattern matching switch;
- кількість тестів збільшено до 79;
- збережено результати попередньої роботи;
- версію оновлено до 3.0.0.

---

# Лабораторна робота №4

**Дисципліна:** Кросплатформні засоби програмування  
**Варіант:** №15  
**Предметна область:** Доставлення посилок  
**Java:** 21  
**Версія:** 4.0.0  
**Гілка:** lab4-level3

## Мета роботи

Реалізувати основні операції обробки поліморфної колекції `List<Parcel>` із використанням Java Stream API зі збереженням попередніх контрольних показників програми.

Предметну модель розширено полями габаритів та статусу доставлення.

Збережено підтримку попереднього формату вхідного запису:

```text
id;weightKg;city;deliveryDays;express
```

Додано підтримку розширеного формату CSV:

```text
id;weightKg;city;deliveryDays;express;dimensions;status
```

## Реалізовані Stream API запити

Для потокової обробки створено окремий клас `ParcelQueries`.

Реалізовано п'ять основних запитів та окремий Optional-пошук:

| Метод | Призначення |
|---|---|
| `filterInTransit()` | Відбір посилок зі статусом «у дорозі» |
| `mapCities()` | Перетворення посилок у список міст доставлення |
| `countByCity()` | Групування посилок за містом із визначенням їх кількості |
| `weightStatistics()` | Статистика ваги через `DoubleSummaryStatistics` |
| `topByWeight()` | Top-N за вагою та номером посилки |
| `findById()` | Пошук посилки за номером через `Optional` |

Усі запити працюють через спільний тип:

```java
List<Parcel>
```

Запити не містять `if` або `switch` за конкретним підтипом посилки.

## Основні запити

### Filter

```java
parcels.stream()
        .filter(p -> p.getStatus() == ParcelStatus.IN_TRANSIT)
        .toList();
```

До результату входять лише посилки зі статусом `IN_TRANSIT`.

### Map

```java
parcels.stream()
        .map(Parcel::getCity)
        .toList();
```

Результат:

```java
List<String>
```

### Grouping

```java
parcels.stream()
        .collect(Collectors.groupingBy(
                Parcel::getCity,
                Collectors.counting()
        ));
```

Результат:

```java
Map<String, Long>
```

### Statistics

```java
parcels.stream()
        .mapToDouble(Parcel::getWeightKg)
        .summaryStatistics();
```

Результат:

```java
DoubleSummaryStatistics
```

Обчислюються:

- `count` — кількість посилок;
- `sum` — сумарна вага;
- `min` — мінімальна вага;
- `max` — максимальна вага;
- `average` — середня вага.

Для порожньої колекції:

```text
count = 0
sum = 0.0
average = 0.0
min = Infinity
max = -Infinity
```

### Top-N

Компаратор:

```java
Comparator.comparingDouble(Parcel::getWeightKg)
        .reversed()
        .thenComparing(Parcel::getId)
```

Порядок:

- вага — за спаданням;
- при однаковій вазі номер посилки — за зростанням;
- після сортування застосовується `limit(n)`.

Перевірено:

- `n = 0`;
- `n = 1`;
- `n > size`;
- `n < 0` → `IllegalArgumentException`;
- однакові значення ваги;
- порожню колекцію.

### Optional

```java
parcels.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst();
```

Результат:

```java
Optional<Parcel>
```

Якщо посилка відсутня:

```java
Optional.empty()
```

Якщо номер дублюється, `findFirst()` повертає перший запис у початковому порядку.

## Stream API у ParcelReport

У Lab 3 підсумкові значення обчислювалися вручну в циклі `for`.

У Lab 4 до класу `ParcelReport` інтегровано запити Stream API:

```java
List<Parcel> inTransit =
        ParcelQueries.filterInTransit(parcels);

List<String> cities =
        ParcelQueries.mapCities(parcels);

Map<String, Long> cityCounts =
        ParcelQueries.countByCity(parcels);

DoubleSummaryStatistics stats =
        ParcelQueries.weightStatistics(parcels);

List<Parcel> top5 =
        ParcelQueries.topByWeight(parcels, 5);

Optional<Parcel> foundParcel =
        ParcelQueries.findById(parcels, "P001");
```

Попередні контрольні показники залишено незмінними, а звіт доповнено результатами Stream API.

Цикл читання CSV залишено, оскільки він відповідає за нумерацію рядків та окрему обробку помилок.

## Контрольний набір Lab 4

Для перевірки Stream API використано:

```text
data/input.csv
```

Набір містить 8 записів, із яких 3 коректні та 5 помилкових.

Коректні записи:

| Номер | Вага, кг | Місто | Термін, дн. | Експрес | Статус |
|---|---:|---|---:|---|---|
| P001 | 2.5 | Львів | 2 | true | IN_TRANSIT |
| P002 | 10.0 | Київ | 4 | false | DELIVERED |
| P003 | 0.8 | Одеса | 3 | true | IN_TRANSIT |

Додатковими тестами перевірено:

- дублікати;
- порожні колекції;
- однакові значення ваги;
- граничні значення N;
- відсутній ключ для `Optional.empty()`.

**Filter:**

```text
Посилки у дорозі:
P001 — Львів
P003 — Одеса
```

**Map:**

```text
[Львів, Київ, Одеса]
```

**Grouping:**

```text
Одеса: 1
Львів: 1
Київ: 1
```

**Statistics:**

```text
count = 3
sum = 13.30
min = 0.80
max = 10.00
average = 4.43
```

**Top-5:**

```text
P002 — Київ — 10.00 кг
P001 — Львів — 2.50 кг
P003 — Одеса — 0.80 кг
```

Оскільки набір містить три коректні записи, top-5 повертає всі три посилки.

**Optional:**

```text
P001 -> Посилку знайдено: P001 — Львів
P999 -> Optional.empty()
```

## Додаткове завдання ⭐

Реалізовано дворівневе групування:

```java
Map<ParcelStatus, Map<String, Double>>
```

Через:

```java
Collectors.groupingBy(
        Parcel::getStatus,
        Collectors.groupingBy(
                Parcel::getCity,
                Collectors.summingDouble(Parcel::getWeightKg)
        )
);
```

Перший рівень — `ParcelStatus`, другий — місто доставлення.

Для кожної групи обчислюється сумарна вага посилок.

Результат:

```text
Дворівневе групування посилок:
Статус: IN_TRANSIT
  Одеса: 0.80 кг
  Львів: 2.50 кг
Статус: DELIVERED
  Київ: 10.00 кг
```

Також додано окремий тест, який перевіряє, що Stream-запити не змінюють вхідну колекцію.

## Еквівалентність Lab 3 і Lab 4

### Порівняння «Цикли → Stream API»

| Обчислення | Цикловий стиль | Stream API |
|---|---|---|
| Сумарна вага | Накопичення у циклі `for` | `DoubleSummaryStatistics.getSum()` |
| Середня вага | Сума / кількість | `DoubleSummaryStatistics.getAverage()` |
| Мінімальна вага | `Math.min()` | `DoubleSummaryStatistics.getMin()` |
| Максимальна вага | `Math.max()` | `DoubleSummaryStatistics.getMax()` |
| Фільтрація | `if` у циклі | `filter()` |
| Перетворення | Ручне формування списку | `map()` |
| Групування | Ручна робота з `Map` | `groupingBy()` |
| Top-N | Сортування й обрізання | `sorted()` + `limit()` |
| Пошук | Цикл / `null` | `findFirst()` + `Optional` |

Контрольні показники Lab 3 збережено:

| Показник | Lab 3 | Lab 4 |
|---|---:|---:|
| Кількість коректних записів | 3 | 3 |
| Середня вага | 4.43 кг | 4.43 кг |
| Найбільший термін | 4 дн. | 4 дн. |
| Експрес-доставлення | 2 | 2 |
| Помилки | 5 | 5 |

Результат:

```text
Коректних записів: 3
Середня вага: 4.43 кг
Найбільший термін: 4 дн.
Експрес-доставлень: 2
Помилок: 5
```

Попередні контрольні показники не змінилися. Звіт доповнено результатами Stream API.

## Тестування

До Lab 4:

```text
79 тестів
```

Після Lab 4:

```text
Tests run: 101, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

| Етап | Додано тестів | Загальна кількість |
|---|---:|---:|
| Lab 3 | — | 79 |
| Рівень 1 | 6 | 85 |
| Рівень 2 | 13 | 98 |
| Завдання із зірочкою | 3 | 101 |

Перевірено:

- усі 5 Stream API запитів;
- порожні колекції;
- фільтрацію за статусом;
- дублікати;
- статистику;
- top-N;
- сортування за номером при однаковій вазі;
- `n = 0`;
- `n = 1`;
- завелике N;
- від'ємне N;
- знайдений та відсутній Optional;
- відсутність мутації;
- дворівневе `groupingBy`;
- усі попередні тести Lab 1–3.

## Maven, SpotBugs і JAR

**Тести:**

```powershell
.\mvnw.cmd test
```

**Повна перевірка:**

```powershell
.\mvnw.cmd clean verify
```

**Пакування:**

```powershell
.\mvnw.cmd package
```

Результат:

```text
Tests run: 101, Failures: 0, Errors: 0, Skipped: 0
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

**JAR:**

```text
target/lab01-4.0.0.jar
```

**Запуск:**

```powershell
java -jar target\lab01-4.0.0.jar --input data\input.csv --output out\report-lab4.txt
```

Запуск із кодуванням UTF-8:

```powershell
java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -jar target/lab01-4.0.0.jar
```

**Версія:**

```powershell
java -jar target\lab01-4.0.0.jar --version
```

Результат:

```text
lab01 4.0.0
```

## Автоматичне версіонування

Єдине джерело версії:

```text
pom.xml
```

Версія проєкту:

```xml
<version>4.0.0</version>
```

Maven автоматично підставляє версію з `pom.xml` до маніфесту JAR:

```xml
<Implementation-Version>${project.version}</Implementation-Version>
```

У класі `Main` версія отримується через:

```java
Main.class.getPackage().getImplementationVersion()
```

Ім'я JAR формується автоматично:

```xml
<finalName>${project.artifactId}-${project.version}</finalName>
```

У CI версія визначається через:

```text
project.version
```

Отримане значення використовується для перевірки `--version`, відповідності Git-тегу та завантаження JAR artifact.

## GitHub Actions

CI налаштовано на трьох операційних системах:

- Ubuntu — `ubuntu-latest`;
- Windows — `windows-latest`;
- macOS — `macos-latest`.

На кожній операційній системі передбачено:

- встановлення Java 21;
- використання Maven Wrapper;
- виконання `clean verify`;
- запуск усіх модульних тестів;
- статичний аналіз коду через SpotBugs;
- автоматичне визначення версії проєкту;
- перевірку результату `--version`;
- перевірку відповідності Git-тегу версії проєкту;
- завантаження виконуваного JAR як GitHub Actions artifact.

Повний успішний matrix-run створює:

```text
3 JAR artifacts
```

Очікуваний результат перевірки версії:

```text
lab01 4.0.0
```

Віддалені перевірки рівнів 1 та 2 пройдено успішно.

Фінальна локальна перевірка версії 4.0.0 із 101 тестом
завершилася успішно. Фінальна віддалена перевірка
ще не виконана.

## GitHub Issues

Для Lab 4 створено такі Issues:

- **#47** — Lab 4 Level 1: Basic Stream API queries and tests;
- **#49** — Lab 4 Level 2: Advanced Stream API queries, Optional and engineering tests;
- **#51** — Lab 4 Level 3: Full CI cycle, documentation and release 4.0.0.

Фінальний Pull Request передбачає об'єднання гілок:

```text
lab4-level3 -> main
```

В описі фінального Pull Request:

```text
Closes #51
```

Після успішного проходження перевірок та merge
буде створено Git-тег:

```text
v4.0.0
```

## Підсумок

У Lab 4:

- реалізовано всі 5 обов'язкових запитів Stream API;
- реалізовано пошук посилки за номером через `Optional`;
- інтегровано запити Stream API до класу `ParcelReport`;
- розширено предметну модель габаритами та статусом доставлення;
- реалізовано групування посилок за містом із визначенням їх кількості;
- реалізовано обчислення статистичних показників за вагою посилок;
- реалізовано top-5 посилок за спаданням ваги;
- другий критерій top-N працює за номером посилки;
- додано тести для граничних значень N;
- реалізовано дворівневе `groupingBy` ⭐;
- перевірено відсутність мутації вхідної колекції ⭐;
- кількість тестів збільшено з 79 до 101;
- усі попередні та нові тести проходять успішно;
- SpotBugs не виявляє дефектів;
- контрольні показники Lab 3 збережено;  
- `pom.xml` є єдиним джерелом версії проєкту;
- CI налаштовано на Ubuntu, Windows і macOS;
- налаштовано публікацію 3 JAR artifacts.
- версію проєкту оновлено до 4.0.0;