# Changelog

## 1.0.0
- Обробка записів про доставлення посилок (варіант 15)
- Maven Wrapper, JUnit 5, SpotBugs, виконуваний JAR
- GitHub Actions на Ubuntu, Windows, macOS
- Відхилення NaN та Infinity у weightKg

## 2.0.0

- Реалізовано незмінну модель Parcel.
- Додано централізовану валідацію даних.
- Реалізовано метод Parcel.fromCsv().
- Додано record WeightDelivery.
- Реалізовано Parcel.Builder.
- Проведено рефакторинг зі збереженням результатів Lab 1.
- Розширено набір автоматизованих тестів до 50.
- SpotBugs не виявив дефектів.
- Успішно перевірено збірку на Ubuntu, Windows та macOS.
- Оновлено README.md та REPORT.md.

## 3.0.0

- Реалізовано успадкування та поліморфізм класів Parcel, RegularParcel та ExpressParcel.
- Додано enum DeliveryKind.
- Реалізовано методи обчислення вартості та терміну доставлення.
- Реалізовано equals() та hashCode().
- Додано sealed-ієрархію та вичерпний switch.
- Реалізовано клас ParcelTypeResolver.
- Розширено набір автоматизованих тестів до 79.
- Успішно перевірено збірку на Ubuntu, Windows та macOS.
- Оновлено README.md та REPORT.md.

