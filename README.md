# 🔧 Code Audit Checklist

Проект содержит несколько ключевых классов, каждый из которых требует улучшений для соответствия принципам Clean Architecture, тестируемости и модульности. 
Ниже представлены основные проблемы и рекомендации по их устранению.

---

## 📂 LocationRepository

### ❌ Проблемы

1 Зависимость от Android API (`Context`, `Geocoder`, `Looper`, `Log`)
2 Использование callback API вместо suspend/Flow
3 Нарушение SRP: получение локации, геокодинг и трекинг объединены
4 Отсутствие освобождения `LocationCallback`
5 Отсутствует проверка разрешений

### ✅ Рекомендации

* Использовать `suspend`/`callbackFlow`
* `Geocoder` вызывать через `Dispatchers.IO`
* Разделить получение локации и трекинг
* Хранить `LocationCallback` как поле, реализовать метод `stop()`
* Добавить проверку разрешений перед вызовами FusedLocationClient
* Переименовать конфликтующий класс `Location` в `AppLocation`

---

## 📂 WeatherRepository

### ❌ Проблемы

1 Использование блокирующего `call.execute()`
2 Старый подход с callback вместо `suspend`
3 Маппинг и парсинг JSON в репозитории
4 Жесткая зависимость от `RetrofitInstance`
5 Некачественное кеширование (`cachedWeatherData`)
6 Нарушение архитектурных слоёв

### ✅ Рекомендации

* Использовать `suspend fun` с `Dispatchers.IO`
* Вынести маппинг в отдельный слой (DTO → Domain)
* Внедрять `WeatherApi` через конструктор
* Реализовать кеш с учётом срока жизни и потоко-безопасности
* Разделить слои: API, маппинг, domain

---

## 📂 WeatherApiService

### ❌ Проблемы

1 Использование `Call<JsonObject>` вместо типизированных моделей
2 Императивный стиль (enqueue/execute)
3 Захардкоженный API ключ
4 `BASE_URL` в интерфейсе
5 Повторение параметров (`appid`, `units`)

### ✅ Рекомендации

* Использовать `suspend fun` в Retrofit-интерфейсе
* Использовать DTO-классы вместо `JsonObject`
* Вынести ключи и `BASE_URL` в настройки или Interceptor
* Удалить дублирование параметров через `@QueryMap`

---

## 📂 WeatherViewModel

### ❌ Проблемы

1 Нарушение DI: ручное создание репозитория
2 Хранение `Context` как `lateinit var`
3 Использование `Handler`, `Timer`
4 Неинкапсулированные `LiveData`
5 Создание собственного `CoroutineScope`
6 Изменение `data class` напрямую

### ✅ Рекомендации

* Использовать DI (Hilt, ViewModelFactory)
* Передавать `Context` в методы, использовать `applicationContext`
* Использовать `viewModelScope` для всех корутин
* Заменить `Timer` на `while (isActive) { delay(...) }`
* Сделать `LiveData` публичными через `val`, `MutableLiveData` — приватными
* Изменять `data class` через `copy()`

---

## 🔧 Дополнительные замечания

### ⚠️ var в data class (например, `isFavorite`, `isSelected`)

* Может вызвать проблемы с `copy()`, `equals()`, `hashCode()`

### ✅ Решение

* Сделать поля `val`
* Изменять значения только через `copy()`

---

## 📅 Рекомендуемые действия

* [ ] Переписать `LocationRepository` с использованием Flow
* [ ] Вынести маппинг JSON → Domain в отдельные DTO
* [ ] Интегрировать Hilt или ViewModelFactory
* [ ] Переписать API-интерфейсы на `suspend`
* [ ] Вынести API ключи из кода
* [ ] Улучшить кеширование с использованием Room/DataStore

