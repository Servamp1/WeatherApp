# Самостоятельная публикация

1. Распакуйте архив: скрытый каталог .git уже входит в проект. Повторный git init не нужен. Репозиторий новый, истории KotlinQuizApp в нём нет.
2. В терминале каталога проекта настройте только свои данные:

~~~powershell
git config user.name "Аничкин Даниил Русланович"
git config user.email "ВАШ_EMAIL"
git status
git add .
git commit -m "Create WeatherApp coursework project"
~~~

Замените ВАШ_EMAIL своим настоящим или GitHub noreply адресом. Коммиты не создавались по вашей просьбе настроить Git самостоятельно.

3. На GitHub создайте пустой публичный репозиторий WeatherApp без README, лицензии и .gitignore. Затем:

~~~powershell
git branch -M main
git remote add origin https://github.com/ВАШ_ЛОГИН/WeatherApp.git
git push -u origin main
~~~

Замените ВАШ_ЛОГИН. Перед отправкой проверьте git status и git ls-files: local.properties, build, .gradle, ключи подписи и личные настройки не должны попасть в список. APK при необходимости прикрепите к GitHub Release отдельно. Публикация в ходе разработки не выполнялась.
