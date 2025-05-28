# <div align="center">Шахматный движок на Java</div> 

<div align="center">
     <img src="https://github.com/user-attachments/assets/f6abfaba-1d8b-46ff-bc86-f80c92f6b6f3" alt="Общий вид интерфейса" align="center" width="500">
</div>

## 🎮 Особенности
- Полная реализация правил шахмат
- Drag-and-drop перемещение фигур
- Визуализация доступных ходов
- Поддержка FEN-нотации для произвольных позиций
- Определение конца игры (мат/пат/недостаток материала)


## 🛠️ Требования
- JDK 11+
- Любая IDE с поддержкой Java (IntelliJ, Eclipse)
- Библиотеки Swing/AWT/ImageIO (включены в JDK)


## 🏗️ Структура проекта
```
src/
├── main/
│    ├── Board.java        # Логика доски и отрисовка
│    ├── CheckScanner.java # Проверка шаха и мата
│    ├── Input.java        # Обработка мыши
│    └── Main.java         # Точка входа
│
├── pieces/
│    ├── Bishop.java # Слон
│    ├── King.java   # Король
│    ├── Knight.java # Конь
│    ├── Pawn.java   # Пешка
│    ├── Queen.java  # Ферзь
│    ├── Rook.java   # Ладья
│    └── Piece.java  # Базовый класс фигуры
│
└── resources/
     └── pieces.png # Спрайты фигур
```


## 🔧 Технические детали
### 1. Система перемещений
- **Валидация ходов**. Каждый класс фигуры реализует проверку допустимости хода:
  ```java
  public boolean isValidMovement(int col, int row) {
    // Логика для конкретной фигуры
  }
  ```
- **Проверка коллизий**. Метод ``moveCollidesWithPiece()`` анализирует траекторию движения и столкновение с препятствиями
- **Особые правила**:
  - Рокировка. Проверка первого хода короля и ладьи:
    ```java
    private boolean canCastle(int col, int row) {
      // Проверка условий для рокировки
    }
    ```
  - Взятие на проходе. Учет поля enPassantTile
  - Превращение пешки. Автоматическая замена на ферзя

  **Пример геймплея с подсветкой ходов и выполнения рокировки:**
  <div align="center"> 
    <img src="https://github.com/user-attachments/assets/7eda0586-c26d-4cb5-a778-7b7fa498f1a3" alt="Подсветка ходов" width="300">
    <img src="https://github.com/user-attachments/assets/197da630-a58c-4f49-a602-4d319acfaa27" alt="Рокировка короля" width="300">
  </div>   

### 2. Графическая система
- **Спрайты фигур**. Единый спрайт-лист pieces.png с масштабированием:
  ```java
  this.sprite = sheet.getSubimage(...).getScaledInstance(...);
  ```
- **Drag-and-drop**. Реализация через MouseAdapter:
  ```java
  public void mouseDragged(MouseEvent e) {
  // Обновление позиции фигуры
  }
  ```

### 3. Система контроля игры
- FEN-нотация
  Поддержка загрузки позиций через FEN-строку:
  ```java
  public final String fenStartingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"; // Стартовая позиция
  public final String fenTest = "r3k2r/8/8/8/2Pp4/8/8/R3K2R b KQkq c3 0 1"; // Тестовая позиция
  public final String fen2Bishops = "4k3/8/8/8/8/8/8/2BBK3 w - - 0 1"; //  Позиция с двумя слонами у белых
  
  // Устанавливаем позицию по желанию
  loadPositionFromFEN(fenStartingPosition);
  ```
  FEN-строку можно получить через различные сайты установки начальной позиции. Примером может служить chess.com, по адресу https://www.chess.com/ru/analysis?tab=analysis

  **Стартовая позиция и кастомная позиция:**
  <div align="center">
    <img src="https://github.com/user-attachments/assets/991bf27d-83fd-4255-9c6d-651969aadf5a" alt="Классическая расстановка"" width="300">
    <img src="https://github.com/user-attachments/assets/f6e8ab38-525f-4333-803c-fe96e833eb85" alt="Тестовая расстановка" width="300">
  </div>

- **Проверка конца игры:**
  - Мат. ``checkScanner.isGameOver()``
  - Пат. Отсутствие легальных ходов без шаха
  - Ничья. Правило недостатка материала


## ⚙️ Установка и запуск
1. Клонируйте репозиторий:
```bash
git clone https://github.com/Omuny/chess_Engine.git
```
2. Скопилируйте программу:
```bash
javac -d out src/main/*.java src/pieces/*.java
```
3. Запустите программу:
```bash
java -cp out main.Main
```
4. Создание JAR-файла (опционально):
```bash
jar cfe Chess.jar main.Main -C out
```

<img src="https://github.com/user-attachments/assets/6b4a3203-821e-4736-adbc-7e47e0b346ba" alt="Пример работы приложения 1" align="center" width="400">
<img src="https://github.com/user-attachments/assets/c68f8735-4b42-43ba-9f45-0f6793f54306" alt="Пример работы приложения 2" align="center" width="400">
