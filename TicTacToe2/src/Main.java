import java.util.Random;
import java.util.Scanner;

public class Main {

    // 3. Определяем размеры массива
    static final int SIZE_X = 3;
    static final int SIZE_Y = 3;
    static int WIN_SERIES;
    static int SIZE_D = SIZE_X;

    // 1. Создаем двумерный массив
    static char[][] field = new char[SIZE_Y][SIZE_X];

    // 2. Обозначаем кто будет ходить какими фишками
    static final char PLAYER_DOT = 'X';
    static final char AI_DOT = '0';
    static final char EMPTY_DOT = '.';

    // 8. Создаем сканер
    static Scanner scanner = new Scanner(System.in);
    // 12. Создаем рандом
    static final Random rand = new Random();

    // 4. Заполняем на массив
    private static void initField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // 5. Выводим на массив на печать
    private static void printField() {
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
    private static int[] playerStep() {
        // 11. с проверкой
        int x;
        int y;
        do {
            System.out.println("Введите координаты: X Y (1-3)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (isCellNotValid(y, x));
        setSym(y, x, PLAYER_DOT);
        return new int[]{x, y};

    }

    // 13. Ход ПК
    private static int[] aiStep() {
        int x;
        int y;
        do {
            x = rand.nextInt(SIZE_X);
            y = rand.nextInt(SIZE_Y);
        } while (isCellNotValid(y, x));
        setSym(y, x, AI_DOT);
        return new int[]{x, y};
    }

    private static boolean isWinHorizontal(char sym, int y) {
        int winStreak = 0;
        for (int i = 0; i < SIZE_X; i++) {
            winStreak = field[y][i] == sym ? winStreak + 1 : 0;
            if (winStreak == WIN_SERIES)
                return true;
        }
        return false;
    }

    private static boolean isWinVertical(char sym, int x) {
        int winStreak = 0;
        for (int i = 0; i < SIZE_Y; i++) {
            winStreak = field[i][x] == sym ? winStreak + 1 : 0;
            if (winStreak == WIN_SERIES)
                return true;
        }
        return false;
    }

    private static boolean isWinMainDiagonal(char sym, int x, int y) {
        int offset = Math.min(x, y);
        int startPositionX = x - offset;
        int startPositionY = y - offset;
        int winStreak = 0;

        while (startPositionX < SIZE_X && startPositionY < SIZE_Y) {
            winStreak = field[startPositionY][startPositionX] == sym ? winStreak + 1 : 0;
            if (winStreak == WIN_SERIES)
                return true;
            startPositionX++;
            startPositionY++;
        }

        return false;
    }

    private static boolean isWinSecondaryDiagonal(char sym, int x, int y) {
        int offset = Math.min(SIZE_X - x - 1, y); //смещение для нахождения стартовой позиции побочной диагонали
        int startPositionX = x + offset;
        int startPositionY = y - offset;
        int winStreak = 0;

        while (startPositionX > -1 && startPositionY < SIZE_Y) {
            winStreak = field[startPositionY][startPositionX] == sym ? winStreak + 1 : 0;
            if (winStreak == WIN_SERIES)
                return true;
            startPositionX--;
            startPositionY++;
        }

        return false;
    }


    // 14. Проверка победы
    private static boolean checkWin(char sym, int x, int y) {
        return isWinHorizontal(sym, y) ||
                isWinVertical(sym, x) ||
                isWinMainDiagonal(sym, x, y) ||
                isWinSecondaryDiagonal(sym, x, y);
    }

    // 16. Проверка полное ли поле? возможно ли ходить?
    private static boolean isFieldFull() {
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
    private static boolean isCellNotValid(int y, int x) {
        // если вываливаемся за пределы возвращаем false
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return true;
        }
        // если не путое поле тоже false
        return (field[y][x] != EMPTY_DOT);
    }

    public static void main(String[] args) {
        // 1 - 1 иницируем и выводим на печать
        initField();
        printField();
        // 1 - 1 иницируем и выводим на печать

        // 15 Основной ход программы

        while (true) {
            int[] coordinates = playerStep();
            int x = coordinates[0];
            int y = coordinates[1];
            printField();
            if (checkWin(PLAYER_DOT, x, y)) {
                System.out.println("Player WIN!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW");
                break;
            }

            coordinates = aiStep();
            x = coordinates[0];
            y = coordinates[1];
            printField();
            if (checkWin(AI_DOT, x, y)) {
                System.out.println("Win SkyNet!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW!");
                break;
            }
        }

    }
}
