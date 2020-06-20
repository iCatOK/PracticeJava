//package course_work;

import java.util.Random;
import java.util.Scanner;

public class course_work {
    // 1. Создаем двумерный массив
    static char[][] field;

    // 2. Обозначаем кто будет ходить какими фишками
    static final char PLAYER_DOT = 'X';
    static final char AI_DOT = '0';
    static final char EMPTY_DOT = '.';

    // 8. Создаем сканер
    static Scanner scanner = new Scanner(System.in);
    // 12. Создаем рандом
    static final Random rand = new Random();

    // 4. Заполняем на массив
    private static void initField(int SIZE_X, int SIZE_Y) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // 5. Выводим на массив на печать
    private static void printField(int SIZE_X, int SIZE_Y) {
        //6. украшаем картинку
        System.out.println("-------");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        //6. украшаем картинку
        System.out.println("-------");
    }

    // 7. Метод который устанавливает символ
    private static void setSym(int y, int x, char sym) {
        field[y][x] = sym;
    }

    // 9. Ход игрока
    private static void playerStep(int SIZE_X, int SIZE_Y) {
        int x, y;

        do {
            System.out.println("Введите координаты: X (1-" + SIZE_X + ")");
            x = scanner.nextInt() - 1;
            System.out.println("Введите координаты: Y (1-" + SIZE_Y + ")");
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x, SIZE_X, SIZE_Y)); // 11. Проверка
        setSym(y, x, PLAYER_DOT);
    }

    // 13-1. Победа на следующем хогу ПК или игрока
    private static int[] aiStepFINAL(int SIZE_X, int SIZE_Y, int win, char sym) {
        int end, h, g, size;
        int[] coordinates = new int[2];
        int[] emptyX = new int[SIZE_X * SIZE_X];
        int[] emptyY = new int[SIZE_Y * SIZE_Y];


        for (int j = 0; j < SIZE_Y; j++) {
            for (int i = 0; i < SIZE_X; i++) {
                // Вертикаль
                end = 0;
                g = 0;
                size = 0;
                for (int k = j; k < j + win; k++) {
                    if ((k == SIZE_Y)) {
                        break;
                    } // Поле кончилось
                    if (field[k][i] == EMPTY_DOT) {
                        emptyX[g] = i;
                        emptyY[g] = k;
                        g++;
                        size++;
                    }
                    if (field[k][i] == sym) {
                        end++;
                    }
                }
                if (end == win - 1) // Можно выиграть
                {
                    // System.out.println("Можно победит 1");
                    for (int f = 0; f < size; f++) {
                        if (emptyY[f] != 0 && emptyY[f] != SIZE_Y - 1) {
                            // System.out.println("Можно победит 1-1");
                            if (field[emptyY[f] + 1][emptyX[f]] == sym
                                    || field[emptyY[f] - 1][emptyX[f]] == sym
                                    || (field[emptyY[f] - 1][emptyX[f]] == sym && field[emptyY[f] + 1][emptyX[f]] == sym)) {
                                coordinates[0] = emptyX[f];
                                coordinates[1] = emptyY[f];
                                return coordinates;
                            }
                        } else {
                            if (emptyY[f] == 0) {
                                //   System.out.println("Можно победит 1-2");
                                if (field[emptyY[f] + 1][emptyX[f]] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                            if (emptyY[f] == SIZE_Y - 1) {
                                //  System.out.println("Можно победит 1-3");
                                if (field[emptyY[f] - 1][emptyX[f]] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                        }
                    }
                }

                // Вниз и вправо
                end = 0;
                h = i;
                g = 0;
                size = 0;
                for (int k = j; k < j + win; k++) {
                    if ((k == SIZE_Y) || (h == SIZE_X)) {
                        break;
                    } // Поле кончилось
                    if (field[k][h] == EMPTY_DOT) {
                        emptyX[g] = h;
                        emptyY[g] = k;
                        g++;
                        size++;
                    }
                    if (field[k][h] == sym) {
                        end++;
                    }
                    h++;
                }
                if (end == win - 1) // Можно выиграть
                {
                    // System.out.println("Можно победит 2");
                    for (int f = 0; f < size; f++) {
                        if (emptyX[f] != 0 && emptyX[f] != SIZE_X - 1 && emptyY[f] != 0 && emptyY[f] != SIZE_Y - 1) {
                            //  System.out.println("Можно победит 2-1");
                            if (field[emptyY[f] + 1][emptyX[f] + 1] == sym
                                    || field[emptyY[f] - 1][emptyX[f] - 1] == sym
                                    || (field[emptyY[f] - 1][emptyX[f] - 1] == sym && field[emptyY[f] + 1][emptyX[f] + 1] == sym)) {
                                coordinates[0] = emptyX[f];
                                coordinates[1] = emptyY[f];
                                return coordinates;
                            }
                        } else {
                            if (emptyX[f] == 0 || emptyY[f] == 0) {
                                //System.out.println("Можно победит 2-2");
                                if (field[emptyY[f] + 1][emptyX[f] + 1] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }

                            if (emptyX[f] == SIZE_X - 1 || emptyY[f] == SIZE_Y - 1) {
                                //  System.out.println("Можно победит 2-3");
                                if (field[emptyY[f] - 1][emptyX[f] - 1] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                        }
                    }
                }

                // Вниз и влево
                end = 0;
                h = i;
                g = 0;
                size = 0;
                for (int k = j; k > j - win; k--) {
                    if ((k == -1) || (h == SIZE_X)) {
                        break;
                    } // Поле кончилось
                    if (field[k][h] == EMPTY_DOT) {
                        emptyX[g] = h;
                        emptyY[g] = k;
                        g++;
                        size++;
                    }
                    if (field[k][h] == sym) {
                        end++;
                    }
                    h++;
                }
                if (end == win - 1) // Можно выиграть
                {
                    //System.out.println("Можно победит 3");
                    for (int f = 0; f < size; f++) {
                        if (emptyX[f] != 0 && emptyX[f] != SIZE_X - 1 && emptyY[f] != 0 && emptyY[f] != SIZE_Y - 1) {
                            //System.out.println("Можно победит 3-1");
                            if (field[emptyY[f] + 1][emptyX[f] - 1] == sym
                                    || field[emptyY[f] - 1][emptyX[f] + 1] == sym
                                    || (field[emptyY[f] - 1][emptyX[f] + 1] == sym && field[emptyY[f] + 1][emptyX[f] - 1] == sym)) {
                                coordinates[0] = emptyX[f];
                                coordinates[1] = emptyY[f];
                                return coordinates;
                            }
                        } else {
                            if (emptyX[f] == 0 || emptyY[f] == 0) {
                                // System.out.println("Можно победит 3-2");
                                if (field[emptyY[f] + 1][emptyX[f] - 1] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                            if (emptyX[f] == SIZE_X - 1 || emptyY[f] == SIZE_Y - 1) {
                                // System.out.println("Можно победит 3-3");
                                if (field[emptyY[f] - 1][emptyX[f] + 1] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                        }
                    }
                }

                // Горизонталь
                end = 0;
                g = 0;
                size = 0;
                for (int k = i; k < i + win; k++) {
                    if (k == SIZE_X) {
                        break;
                    } // Поле кончилось
                    if (field[j][k] == EMPTY_DOT) {
                        emptyX[g] = k;
                        emptyY[g] = j;
                        g++;
                        size++;
                    }
                    if (field[j][k] == sym) {
                        end++;
                    }
                }
                if (end == win - 1) // Можно выиграть
                {
                    //System.out.println("Можно победит 4");
                    for (int f = 0; f < size; f++) {
                        if (emptyX[f] != 0 && emptyX[f] != SIZE_X - 1) {
                            // System.out.println("Можно победит 4-1");
                            if (field[emptyY[f]][emptyX[f] + 1] == sym
                                    || field[emptyY[f]][emptyX[f] - 1] == sym
                                    || (field[emptyY[f]][emptyX[f] - 1] == sym && field[emptyY[f]][emptyX[f] + 1] == sym)) {
                                coordinates[0] = emptyX[f];
                                coordinates[1] = emptyY[f];
                                return coordinates;
                            }
                        } else {
                            if (emptyX[f] == 0) {
                                // System.out.println("Можно победит 4-2");
                                if (field[emptyY[f]][emptyX[f] + 1] == sym) {
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                            if (emptyX[f] == SIZE_X - 1) {
                                if (field[emptyY[f]][emptyX[f] - 1] == sym) {
                                    // System.out.println("Можно победит 4-3");
                                    coordinates[0] = emptyX[f];
                                    coordinates[1] = emptyY[f];
                                    return coordinates;
                                }
                            }
                        }
                    }
                }
            }
        }

        coordinates[0] = -1;
        coordinates[1] = -1;
        return coordinates;
    }

    // 13-2. Ставим новый 0 на соседнюю клетку с текущим 0 или Х
    private static int[] aiStepNEXT(int SIZE_X, int SIZE_Y, char sym) {
        int x = -1, y = -1, position, count = 0;
        int[] coordinates = new int[2];

        for (int j = 0; j < SIZE_Y; j++) {
            for (int i = 0; i < SIZE_X; i++) {
                if (field[j][i] == sym) // Нашли 0
                {
                    x = i;
                    y = j;
                }
                if (field[j][i] == EMPTY_DOT) // Количество пустых клеток
                {
                    count++;
                }
            }
        }

        if (x == -1 && y == -1) // Не нашли 0
        {
            coordinates[0] = -1; // Не нашли 0
            coordinates[1] = -1;
        } else {
            if (count > 1) // Если пустых клеток, больше 1
            {
                do {
                    position = rand.nextInt(8);
                    // System.out.println("position " + position);

                    switch (position) {
                        case (0):
                            if (isCellValid(y - 1, x - 1, SIZE_X, SIZE_Y)) // левый верхний угол{
                            {
                                coordinates[0] = x - 1;
                                coordinates[1] = y - 1;
                                position = 9;
                            }
                            break;
                        case (1):
                            if (isCellValid(y - 1, x, SIZE_X, SIZE_Y)) // вверх
                            {
                                coordinates[0] = x;
                                coordinates[1] = y - 1;
                                position = 9;
                            }
                            break;
                        case (2):
                            if (isCellValid(y - 1, x + 1, SIZE_X, SIZE_Y)) // правый верхний угол
                            {
                                coordinates[0] = x + 1;
                                coordinates[1] = y - 1;
                                position = 9;
                            }
                            break;
                        case (3):
                            if (isCellValid(y, x + 1, SIZE_X, SIZE_Y)) // вправо
                            {
                                coordinates[0] = x + 1;
                                coordinates[1] = y;
                                position = 9;
                            }
                            break;
                        case (4):
                            if (isCellValid(y + 1, x + 1, SIZE_X, SIZE_Y)) // правый нижний угод
                            {
                                coordinates[0] = x + 1;
                                coordinates[1] = y + 1;
                                position = 9;
                            }
                            break;
                        case (5):
                            if (isCellValid(y + 1, x, SIZE_X, SIZE_Y)) // вниз
                            {
                                coordinates[0] = x;
                                coordinates[1] = y + 1;
                                position = 9;
                            }
                            break;
                        case (6):
                            if (isCellValid(y + 1, x - 1, SIZE_X, SIZE_Y)) // левый нижний угол
                            {
                                coordinates[0] = x - 1;
                                coordinates[1] = y + 1;
                                position = 9;
                            }
                            break;
                        case (7):
                            if (isCellValid(y, x - 1, SIZE_X, SIZE_Y)) // влево
                            {
                                coordinates[0] = x - 1;
                                coordinates[1] = y;
                                position = 9;
                            }
                            break;
                        default:
                            break;
                    }
                } while (position != 9);
            } else // Занять единственную свободную клетку
              {
                for (int j = 0; j < SIZE_Y; j++) {
                    for (int i = 0; i < SIZE_X; i++) {
                        if (field[j][i] == EMPTY_DOT) // Нашли 0
                        {
                            coordinates[0] = i;
                            coordinates[1] = j;
                        }
                    }
                }
            }
        }

        return coordinates;
    }

    // 13. Ход ПК
    private static void aiStep(int SIZE_X, int SIZE_Y, int win) {
        int[] coordinates;

        coordinates = aiStepFINAL(SIZE_X, SIZE_Y, win, AI_DOT); // Победа на следующем шагу

        if (coordinates[0] == -1 && coordinates[1] == -1) // Если победа не на следующем шагу
        {
            coordinates = aiStepFINAL(SIZE_X, SIZE_Y, win, PLAYER_DOT); // Блокируем игрока, если у него шаг до победы

            if (coordinates[0] == -1 && coordinates[1] == -1) // Если победа не на следующем шагу игрока
            {
                coordinates = aiStepNEXT(SIZE_X, SIZE_Y, AI_DOT); // Ставим новый 0 на соседнюю клетку с текущим 0
            }

            if (coordinates[0] == -1 && coordinates[1] == -1) // Иначе
            {
                coordinates = aiStepNEXT(SIZE_X, SIZE_Y, PLAYER_DOT); // Ставим новый 0 на соседнюю клетку с текущим X
            }
        }
        setSym(coordinates[1], coordinates[0], AI_DOT);
    }

    // 14. Проверка победы
    private static boolean checkWin(int win, int SIZE_X, int SIZE_Y) {
        char current; // Значение текущей клетки
        int end; // Текущая длина ряда
        int h;

        for (int j = 0; j < SIZE_Y; j++) {
            for (int i = 0; i < SIZE_X; i++) {
                if (field[j][i] == EMPTY_DOT) {
                    continue;
                }
                current = field[j][i];

                // Смотрим вниз от текущей клетки
                end = 0;
                for (int k = j; k < j + win; k++) {
                    if ((k == SIZE_Y) || (field[k][i] != current)) {
                        break;
                    }  // Нет ряда
                    end++;
                }
                if (end == win) {
                    return true;
                } // Есть ряд - конец игры

                // Смотрим вниз и вправо от текущей клетки
                end = 0;
                h = i;
                for (int k = j; k < j + win; k++) {
                    if ((k == SIZE_Y) || (h == SIZE_X) || (field[k][h] != current)) {
                        break;
                    } // Нет ряда
                    end++;
                    h++;
                }
                if (end == win) {
                    return true;
                } // Есть ряд - конец игры

                // Смотрим вниз и влево от текущей клетки
                end = 0;
                h = i;
                for (int k = j; k > j - win; k--) {
                    if ((k == -1) || (h == SIZE_X) || (field[k][h] != current)) {
                        break;
                    } // Нет ряда
                    end++;
                    h++;
                }
                if (end == win) {
                    return true;
                } // Есть ряд - конец игры

                // Смотрим вправо от текущей клетки
                end = 0;
                for (int k = i; k < i + win; k++) {
                    if ((k == SIZE_X) || (field[j][k] != current)) {
                        break;
                    } // Нет ряда
                    end++;
                }
                if (end == win) {
                    return true;
                } // Есть ряд - конец игры
            }
        }
        return false; // Игра не окончена
    }

    // 16. Проверка полное ли поле? возможно ли ходить?
    private static boolean isFieldFull(int SIZE_X, int SIZE_Y) {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    // 10. Проверяем возможен ли ход
    private static boolean isCellValid(int y, int x, int SIZE_X, int SIZE_Y) {
        // если вываливаемся за пределы возвращаем false
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return false;
        }

        // если не путое поле тоже false
        return (field[y][x] == EMPTY_DOT);
    }

    // 3-1. Определяем количество клеток для победы
    private static int max(int first, int second) {
        return first > second ? first : second;
    }

    public static void main(String[] args) {
        int SIZE_X, SIZE_Y, win;

        // 3. Определяем размеры массива
        System.out.print("Введите количество клеток по Х (столбцов): ");
        SIZE_X = scanner.nextInt();
        System.out.print("Введите количество клеток по Y (строк): ");
        SIZE_Y = scanner.nextInt();
        while (SIZE_Y < 2 || SIZE_X < 2) {
            System.out.println("Ошибка!");
            System.out.print("Введите количество клеток по Х (столбцов): ");
            SIZE_X = scanner.nextInt();
            System.out.print("Введите количество клеток по Y (строк): ");
            SIZE_Y = scanner.nextInt();
        }

        field = new char[SIZE_Y][SIZE_X];

        // 3-1. Определяем количество клеток для победы
        System.out.print("Введите количество клеток для победы: ");
        win = scanner.nextInt();
        while (win > max(SIZE_X, SIZE_Y) || win < 2) {
            System.out.println("Ошибка!");
            System.out.print("Введите количество клеток для победы: ");
            win = scanner.nextInt();
        }

        // 1 - 1 иницируем и выводим на печать
        initField(SIZE_X, SIZE_Y);
        printField(SIZE_X, SIZE_Y);

        // 15 Основной ход программы
        while (true) {
            playerStep(SIZE_X, SIZE_Y); // Ход игрока
            printField(SIZE_X, SIZE_Y);

            if (checkWin(win, SIZE_X, SIZE_Y)) {
                System.out.println("Player WIN!");
                break;
            }
            if (isFieldFull(SIZE_X, SIZE_Y)) {
                System.out.println("DRAW");
                break;
            }

            aiStep(SIZE_X, SIZE_Y, win); // Ход ПК
            printField(SIZE_X, SIZE_Y);

            if (checkWin(win, SIZE_X, SIZE_Y)) {
                System.out.println("Win SkyNet!");
                break;
            }
            if (isFieldFull(SIZE_X, SIZE_Y)) {
                System.out.println("DRAW!");
                break;
            }
        }
    }
}
