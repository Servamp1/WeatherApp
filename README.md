# WeatherApp

Курсовая работа «Разработка Android-приложения для просмотра погоды в выбранном городе».

Автор: Аничкин Даниил Русланович. Группа: ЗИСТ-3. Учебное заведение: ВГУВТ.

## Возможности

Три экрана: текущая погода, выбор из десяти городов и информация об авторе. Главный экран показывает температуру, ветер (направление, откуда он дует, и скорость), осадки за интервал API и местное время данных. Последний город сохраняется; по умолчанию выбрана Москва. Есть обновление и сообщения об ошибках.

## Технологии

Kotlin 2.2.21, XML Views, ConstraintLayout, AppCompatActivity, Intent, SharedPreferences, HttpsURLConnection, JSONObject и ExecutorService. AGP 8.13.2, Gradle 8.13, JDK 17, compile/target SDK 36, min SDK 23 (Android 6.0).

## Запуск

1. Распакуйте проект в C:\AndroidProjects\WeatherApp (путь без кириллицы).
2. В Android Studio откройте каталог проекта. Установите SDK Platform 36 и Build Tools 35.0.0; выберите Gradle JDK 17.
3. Дождитесь синхронизации и нажмите Run, выбрав телефон или эмулятор Android 6.0+ с интернетом.

Из PowerShell в каталоге проекта: .\gradlew.bat assembleDebug. Для проверок: .\gradlew.bat lintDebug testDebugUnitTest. Вне Android Studio задайте JAVA_HOME и создайте local.properties с sdk.dir, указывающим на ваш Android SDK. Эти локальные настройки не входят в архив.

## Данные

[Open-Meteo](https://open-meteo.com/) — бесплатный endpoint без ключа для некоммерческого учебного использования. [Документация](https://open-meteo.com/en/docs), [условия](https://open-meteo.com/en/terms), [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/). Текущая погода — модельная оценка, а не прямое измерение станции. Осадки относятся к предшествующему интервалу current.interval, а не к суткам. Координаты центров городов проверены через [Open-Meteo Geocoding / GeoNames](https://open-meteo.com/en/docs/geocoding-api).

GitHub-публикация не выполнялась. По просьбе пользователя Git-авторство и коммиты оставлены для самостоятельной настройки; создан отдельный пустой репозиторий .git.
