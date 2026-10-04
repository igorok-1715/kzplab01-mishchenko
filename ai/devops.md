# Роль: DevOps

## Завдання
- налаштувати Maven, Maven Wrapper і виконуваний JAR;
- підключити SpotBugs у фазі `verify`;
- налаштувати GitHub Actions на Ubuntu, Windows і macOS.

## Що запропонував ШІ
- `pom.xml` із JUnit 5, SpotBugs і maven-shade-plugin;
- workflow `ci.yml` із матрицею з трьох ОС і публікацією JAR;
- команди для генерації Maven Wrapper.

## Що перевірив і прийняв автор
Збірка `mvnw clean verify` запущена локально та в GitHub Actions: усі перевірки зелені.