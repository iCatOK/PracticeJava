import java.util.Random;
import java.util.Scanner;

public class Main {

    // 3. Определяем размеры массива и выигрышную серию
    static int SIZE_X = 3;
    static int SIZE_Y = 3;
    static final int WIN_SERIES = 2;
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

        System.out.println("Введите размеры поля: X Y (1-10)");
        SIZE_X = scanner.nextInt();
        SIZE_Y = scanner.nextInt();
        SIZE_D = Math.min(SIZE_X, SIZE_Y);

        for(int i = 0; i < SIZE_Y; i++) {
            for(int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // 5. Выводим на массив на печать
    private static void printField() {
        //6. украшаем картинку
        System.out.println("-------");
        for(int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for(int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        //6. украшаем картинку
        System.out.println("-------");
    }

    // 7. Метод который устанавливает символ
    private static void setSym(int y, int x, char sym){
        field[y][x] = sym;
    }

    // 9. Ход игрока
    private static int[] playerStep() {
        // 11. с проверкой
        int[] coords = {0,0};
        do {
            System.out.println("Введите координаты: X Y (1-3)");
            coords[0] = scanner.nextInt() - 1;
            coords[1] = scanner.nextInt() - 1;
        } while (!isCellValid(coords[1], coords[0]));
        setSym(coords[1], coords[0], PLAYER_DOT);
        return coords;
    }

    // 13. Ход ПК
    private static int[] aiStep() {
        int[] coords = {0,0};
        do{
            coords[0] = rand.nextInt(SIZE_X);
            coords[1] = rand.nextInt(SIZE_Y);
        } while(!isCellValid(coords[1], coords[0]));
        setSym(coords[1], coords[0], AI_DOT);
        return coords;
    }

    // 14. Проверка победы
    private static boolean checkWin(char sym, int[] coords) {
        //счетчики
        int i = 0, j = 0, k = 0;
        //выигрыш-серии по верт,горизонт, и диагоналям
        int win_x = 0, win_y = 0, win_d1 = 0, win_d2 = 0;
        while (i < SIZE_X || j < SIZE_Y || k < SIZE_D){
            win_x = i < SIZE_X && field[coords[1]][i] == sym ? win_x+1 : 0;
            win_y = j < SIZE_Y && field[j][coords[0]] == sym ? win_y+1 : 0;
            win_d1 = k < SIZE_D && field[k][k] == sym ? win_d1+1 : 0;
            win_d2 = k < SIZE_D && field[k][SIZE_D-1-k] == sym ? win_d2+1 : 0;

            if(win_x == WIN_SERIES || win_y == WIN_SERIES || win_d1 == WIN_SERIES || win_d2 == WIN_SERIES)
                return true;
            i++;j++;k++;
        }
        return false;

//        if (field[0][0] == sym && field[0][1] == sym && field[0][2] == sym) {
//            return true;
//        }
//        if (field[1][0] == sym && field[1][1] == sym && field[1][2] == sym) {
//            return true;
//        }
//        if (field[2][0] == sym && field[2][1] == sym && field[2][2] == sym) {
//            return true;
//        }
//
//        if (field[0][0] == sym && field[1][0] == sym && field[2][0] == sym) {
//            return true;
//        }
//        if (field[0][1] == sym && field[1][1] == sym && field[2][1] == sym) {
//            return true;
//        }
//        if (field[0][2] == sym && field[1][2] == sym && field[2][2] == sym) {
//            return true;
//        }
//
//
//        if (field[0][0] == sym && field[1][1] == sym && field[2][2] == sym) {
//            return true;
//        }
//        return field[2][0] == sym && field[1][1] == sym && field[0][2] == sym;
    }

    // 16. Проверка полное ли поле? возможно ли ходить?
    private static boolean isFieldFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for(int j = 0; j < SIZE_X; j++) {
                if(field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    // 10. Проверяем возможен ли ход
    private static boolean isCellValid(int y, int x) {
        // если вываливаемся за пределы возвращаем false
        if(x < 0 || y < 0 || x > SIZE_X -1 || y > SIZE_Y - 1) {
            return false;
        }
        // если не путое поле тоже false
        return (field[y][x] == EMPTY_DOT);
    }

    public static void main(String[] args) {
        // 1 - 1 иницируем и выводим на печать
        initField();
        printField();
        int[] playerCoords;
        int[] aiCoords;
        // 1 - 1 иницируем и выводим на печать

        // 15 Основной ход программы

        while (true) {
            playerCoords = playerStep();
            printField();
            if(checkWin(PLAYER_DOT, playerCoords)) {
                System.out.println("Player WIN!");
                break;
            }
            if(isFieldFull()) {
                System.out.println("DRAW");
                break;
            }

            aiCoords = aiStep();
            printField();
            if(checkWin(AI_DOT, aiCoords)) {
                System.out.println("Win SkyNet!");
                break;
            }
            if(isFieldFull()) {
                System.out.println("DRAW!");
                break;
            }
        }

    }
}