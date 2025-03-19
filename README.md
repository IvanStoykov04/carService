# Система за управление на заявки за къстамизация на автомобили

## Описание на проекта
Тази система представлява платформа за реализиране и манипулиране на клиентски заявки за къстамизация на автомобили. Проектът е насочен към частни предприятия в България и цели да улесни комуникацията между клиентите и администраторите, като осигури бърз и сигурен начин за управление на заявки и услуги.

## Основни функционалности

### Клиент
- **Добавяне на нов автомобил:** Клиентът може да добави съществуваща кола в системата, като въведе основна информация (марка, модел, година на производство, регистрационен номер и др.).
- **Създаване на заявка:** Клиентът може да създаде заявка за къстамизация, избирайки автомобил от списъка си и добавяйки една или повече услуги.
- **История на заявките:** Клиентът има възможност да преглежда историята на създадените заявки с детайли за състоянието, избраните услуги и датите.
- **Модификация на заявка:** При необходимост, клиентът може да изпрати молба към администратора за промяна или премахване на съществуваща заявка.
- **Лични данни:** Добавяне и актуализиране на лични данни за идентификация.

### Администратор
- **Управление на заявки:** Администраторът може да създава, модифицира или изтрива заявки от базата данни.
- **Управление на автомобили:** Добавяне на нови коли в системата, както и премахване на съществуващи.
- **Управление на потребители:** Добавяне и изтриване на потребители, за да се управлява достъпът до платформата.
- **Модифициране на услуги:** Администраторът има възможност да добавя, променя или премахва предлаганите услуги.

### Предлагани услуги (примерен списък)
- Слагане на фолио на стъкла
- Слагане на фолио на фарове
- Полиране на фарове
- Чипосване (стейдж 1, 2, 3)
- Генерация
- Пребоядисване
- Дино
- Подмяна на брони
- Сплойлер
- Джанти и гуми
- Газ

## Функционални изисквания
- **F1:** Системата предоставя възможност за създаване на заявки за къстамизация на клиентския автомобил.
- **F2:** Системата позволява манипулиране на заявките в базата данни:
    - **F2_1:** Администраторите могат да изтриват заявки.
    - **F2_2:** Администраторите могат да променят елементи от заявките.
    - **F2_3:** Администраторите могат да създават нови заявки.
    - **F2_4:** Клиентът може да преглежда детайлите на създадените заявки.
    - **F2_5:** Клиентът има възможност да заяви промяна на своята заявка.
    - **F2_6:** Клиентът има възможност да поиска премахване на заявката.

## Нефункционални изисквания
- **NF1:** Мащабируемост и производителност:
    - **NF1_1:** Време за реакция под 10 секунди.
    - **NF1_2:** Поддържа до 100 потребители на час.
- **NF2:** Сигурност:
    - **NF2_1:** Чувствителните данни трябва да бъдат криптирани при съхранение и трансфер.
- **NF3:** Надеждност:
    - **NF3_1:** Приложението трябва да гарантира 99% наличност през работното време.
- **NF4:** Удобство при работа:
    - **NF4_4:** Приложението трябва да има ясни и кратки команди, лесни за запомняне.
- **NF5:** Обучение и документация:
    - **NF5_1:** Предоставяне на кратка и ясна документация за основните функции и начин на използване.

## User Stories

### 1. Клиент добавя нова кола
- **Като клиент**, искам да добавя нов автомобил в системата, за да мога да създавам заявки за неговата къстамизация.
- **Критерии:** Въвеждане на информация за марка, модел, година, регистрационен номер и др. Автомобилът се появява в списъка на клиента след успешно добавяне.

### 2. Клиент създава заявка за къстамизация
- **Като клиент**, искам да създам заявка за къстамизация, за да направя промени в автомобила (напр. пребоядисване, смяна на джанти).
- **Критерии:** Избор на автомобил от списъка, възможност за избор на една или повече услуги и генериране на заявка, записана в базата данни.

### 3. Клиент преглежда история на заявките
- **Като клиент**, искам да преглеждам историята на заявките си, за да виждам извършените промени и текущото състояние.
- **Критерии:** Списък с всички заявки, показващ статус, дата на създаване и избраните услуги. При клик върху заявка се показват подробности.

### 4. Клиент изисква промяна на заявка
- **Като клиент**, искам да поискам промяна на моята заявка, за да мога да добавя или премахна услуги.
- **Критерии:** Избор на съществуваща заявка, попълване на поле с описание на промените и изпращане на молба към администратора.

### 5. Клиент изисква премахване на заявка
- **Като клиент**, искам да изтрия заявка, ако реша, че не искам да продължавам с къстамизацията.
- **Критерии:** Избор на заявка за премахване, след което тя изчезва от историята.

### 6. Администратор добавя нови коли към базата
- **Като администратор**, искам да добавям нови автомобили към системата, за да могат клиентите да ги използват при създаване на заявки.
- **Критерии:** Въвеждане на информация за нови коли, които стават видими за клиентите.

### 7. Администратор модифицира заявка на клиент
- **Като администратор**, искам да променям заявки на клиентите, за да удовлетворя техните промени и корекции.
- **Критерии:** Модифициране на избраните услуги или статуса на заявката и актуализиране в базата данни.

### 8. Администратор премахва заявка
- **Като администратор**, искам да премахвам заявки, ако клиентът ги е отменил или са невалидни.
- **Критерии:** Изтриване на заявка от системата, така че тя да не се появява в историята на клиента.

### 9. Администратор добавя и премахва потребители
- **Като администратор**, искам да управлявам потребителите чрез добавяне и изтриване на акаунти, за да се осигури правилен достъп.
- **Критерии:** Въвеждане на необходимата информация за нови потребители и премахване на съществуващи.

### 10. Администратор модифицира услуги
- **Като администратор**, искам да управлявам услугите за къстамизация (добавяне, промяна или премахване), за да актуализирам предлаганите услуги.
- **Критерии:** Модифициране на списъка с услуги в системата.

## Технологии
- **Backend:** Java (използвайки JDBC или ORM като JPA/Hibernate)
- **База данни:** MySQL (или друга релационна база данни)
- **Frontend:** (опционално – може да се разработи уеб интерфейс)
- **Други:** Spring Boot (за по-бързо разработване на уеб приложение, ако е необходимо)

## Инсталация и настройка
1. **Клониране на репозитория:**
   ```bash
   git clone https://github.com/yourusername/your-repo-name.git
   ```
2. **Настройване на базата данни:**
    - Създайте база данни в MySQL.
    - Изпълнете предоставените SQL скриптове за създаване на таблиците и настройване на връзките.
3. **Конфигурация:**
    - Актуализирайте връзката към базата данни във файла `application.properties` (или друг конфигурационен файл).
4. **Стартиране на приложението:**
    - Ако използвате Spring Boot:
      ```bash
      mvn spring-boot:run
      ```
    - Ако имате самостоятелно Java приложение:
      ```bash
      java -jar your-app.jar
      ```

## Използване
- **Клиентски интерфейс:**  
  Влезте в системата чрез клиентския портал, за да:
    - Добавяте нов автомобил.
    - Създавате и управлявате заявки за къстамизация.
    - Преглеждате историята на заявките.

- **Администраторски интерфейс:**  
  Използвайте администраторския портал за:
    - Управление на автомобили и потребители.
    - Модифициране и изтриване на заявки.
    - Управление на предлаганите услуги.

## Контакт
За въпроси, предложения или сътрудничество:
- **Email:** Ivan13.is@abv.bg / dimi.pelov@gmail.com / kaloyan.yordanov99@gmail.com / valentinkostadinov033@gmlai.com

---