import java.lang.management.PlatformLoggingMXBean;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // 3. Определяем размеры массива и выигрышную серию
    static int SIZE_X;
    static int SIZE_Y;
    static int WIN_SERIES;
    static int SIZE_D = SIZE_X;

    // 1. Создаем двумерный массив
    static char[][] field;
    static int SHAPE;

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
        do {
            System.out.println("Введите размеры поля: X Y (1-10)");
            SIZE_X = scanner.nextInt();
            SIZE_Y = scanner.nextInt();
        } while (!(SIZE_X > 0 && SIZE_X < 11 && SIZE_Y > 0 && SIZE_Y < 11));

        SIZE_D = Math.min(SIZE_X,SIZE_Y);

        do {
            System.out.println("Введите выигрышную серию: W (1-max(X,Y))");
            WIN_SERIES = scanner.nextInt();
        } while (!(WIN_SERIES <= Math.max(SIZE_X, SIZE_Y)));

        if(Math.min(SIZE_X,SIZE_Y)==SIZE_X) SHAPE = 1; //вертикально
        else if(Math.min(SIZE_X,SIZE_Y)==SIZE_Y) SHAPE = 2; //горизонтальнo

        field = new char[SIZE_Y][SIZE_X];

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
            System.out.println(String.format("Введите координаты: X Y (1-%d)(1-%d)", SIZE_X, SIZE_Y));
            coords[0] = scanner.nextInt() - 1;
            coords[1] = scanner.nextInt() - 1;
        } while (!isCellValid(coords[1], coords[0]));
        setSym(coords[1], coords[0], PLAYER_DOT);
        return coords;
    }

    // 13. Ход ПК
    private static int[] aiStep() {
        //int[] coords = {0,0};
//        do{
//            coords[0] = rand.nextInt(SIZE_X);
//            coords[1] = rand.nextInt(SIZE_Y);
//        } while(!isCellValid(coords[1], coords[0]));
        int[] coords = calculateStep();
        setSym(coords[1], coords[0], AI_DOT);
        return coords;
    }

    private static int[] calculateStep(){
        //boolean isStepTaken = false;
        int win_streak = 0, current_ws = 0;
        int[] coords = {0,0}, best_coords = {0,0};

        //горизонтали
        for(int i = 0; i < SIZE_Y; i++){
            for(int j = 0; j < SIZE_X; j++) {
//                if (field[i][j] == AI_DOT) {
//                    isStepTaken = true;
//                    break;
//                }
                if (field[i][j] == PLAYER_DOT)
                    current_ws++;
                if(field[i][j]==EMPTY_DOT)
                    coords = new int[]{j,i};
            }
            if(current_ws>win_streak){
                win_streak = current_ws;
                best_coords = coords;
            }
            current_ws = 0;
        }
        //вертикали
        for(int i = 0; i < SIZE_X; i++){
            for(int j = 0; j < SIZE_Y; j++) {
                if (field[j][i] == PLAYER_DOT)
                    current_ws++;
                if(field[j][i]==EMPTY_DOT)
                    coords = new int[]{i,j};
            }
            if(current_ws>win_streak){
                win_streak = current_ws;
                best_coords = coords;
            }
            current_ws = 0;
        }
        //диагонали
        int[] diagonal_best_coords = diagonalStreakCalc();
        if(diagonal_best_coords[2] > win_streak && diagonal_best_coords[0] > -1 && diagonal_best_coords[1] > -1) {
            //win_streak = diagonal_best_coords[2];
            best_coords[0] = diagonal_best_coords[0];
            best_coords[1] = diagonal_best_coords[1];
        }


        return best_coords;
    }

    //вычисление лучшего диагонального хода
    private static int[] diagonalStreakCalc(){
        int end_x = SIZE_X, end_y = SIZE_Y;
        int current_ws_d1 = 0, current_ws_d2 = 0;
        int[] coords_d1 = {-1,-1}, best_coords = {0,-1,-1}, coords_d2 = {-1,-1};

        switch (SHAPE){
            case 1:{
                end_y = SIZE_Y - WIN_SERIES + 1;
            } break;
            case 2:{
                end_x = SIZE_X - WIN_SERIES + 1;
            } break;
        }

        //диагонали со сдвигом вправо-влево
        for(int i = 0; i <end_x; i++){
            for(int j = 0; j < SIZE_D; j++){
                try{
                    if(field[j][j+i] == PLAYER_DOT)
                        current_ws_d1++;
                    if(field[j][j+i]==EMPTY_DOT)
                        coords_d1 = new int[]{j,i};
                    if(field[j][SIZE_X-1-j-i] == PLAYER_DOT)
                        current_ws_d2++;
                    if(field[j][SIZE_X-1-j-i]==EMPTY_DOT)
                        coords_d2 = new int[]{j,i};
                } catch (ArrayIndexOutOfBoundsException e){
                    break;
                }
            }
            if(current_ws_d1 < current_ws_d2 && !Arrays.equals(coords_d2, new int[]{-1, -1})) {
                current_ws_d1 = current_ws_d2;
                coords_d1 = coords_d2;
            }
            if(best_coords[2] < current_ws_d1 && !Arrays.equals(coords_d1, new int[]{-1, -1})){
                best_coords[2] = current_ws_d1;
                best_coords[0] = coords_d1[0];
                best_coords[1] = coords_d1[1];
            }
            current_ws_d1 = 0;
            current_ws_d2 = 0;
            coords_d1 = new int[]{-1,-1}; coords_d2 = new int[]{-1,-1};
        }
        //диагонали со сдвигом вверх-вниз
        for(int i = 1; i < end_y; i++){
            for(int j = 0; j < SIZE_D; j++){
                try{
                    if(field[i+j][j] == PLAYER_DOT)
                        current_ws_d1++;
                    if(field[i+j][j]==EMPTY_DOT)
                        coords_d1 = new int[]{j,i};
                    if(field[i+j][SIZE_X-1-j] == PLAYER_DOT)
                        current_ws_d2++;
                    if(field[i+j][SIZE_X-1-j]==EMPTY_DOT)
                        coords_d2 = new int[]{j,i};
                } catch (ArrayIndexOutOfBoundsException e){
                    break;
                }
                if(current_ws_d1 < current_ws_d2 && !Arrays.equals(coords_d2, new int[]{-1, -1})) {
                    current_ws_d1 = current_ws_d2;
                    coords_d1 = coords_d2;
                }
                if(best_coords[2] < current_ws_d1 && !Arrays.equals(coords_d1, new int[]{-1, -1})){
                    best_coords[2] = current_ws_d1;
                    best_coords[0] = coords_d1[0];
                    best_coords[1] = coords_d1[1];
                }
                current_ws_d1 = 0;
                current_ws_d2 = 0;
                coords_d1 = new int[]{-1,-1}; coords_d2 = new int[]{-1,-1};
            }
        }

        return best_coords;

    }

    // 14. Проверка победы
    private static boolean checkWin(char sym, int[] coords) {
        //счетчики
        int i = 0, j = 0;
        //выигрыш-серии по верт,горизонт, и диагоналям
        int win_x = 0, win_y = 0, win_d1 = 0, win_d2 = 0;
        //расчёт выигрыша по веритикали и горизонтали
        while (i < SIZE_X || j < SIZE_Y){
            win_x = i < SIZE_X && field[coords[1]][i] == sym ? win_x+1 : 0;
            win_y = j < SIZE_Y && field[j][coords[0]] == sym ? win_y+1 : 0;
            //win_d1 = k < SIZE_D && field[k][k] == sym ? win_d1+1 : 0;
            //win_d2 = k < SIZE_D && field[k][SIZE_D-1-k] == sym ? win_d2+1 : 0;

            if(win_x == WIN_SERIES || win_y == WIN_SERIES)
                return true;
            i++;j++;
        }
        //стартовая позиция для диагоналей
        int[] pos_d1 = {coords[0]-Math.min(coords[0],coords[1]), coords[1]-Math.min(coords[0],coords[1])};
        int[] pos_d2 = {coords[0]+Math.min(coords[0],coords[1]), coords[1]-Math.min(coords[0],coords[1])};
        //расчет выигрыша по главной диагонали
        while (pos_d1[0] < SIZE_X && pos_d1[1] < SIZE_Y){
            win_d1 = field[pos_d1[1]][pos_d1[0]] == sym ?  win_d1+1 : 0;
            if(win_d1 == WIN_SERIES)
                return true;
            pos_d1[0]++;
            pos_d1[1]++;
        }
        //расчет выигрыша по побочной диагонали
        while (pos_d1[0] < SIZE_X && pos_d1[1] < SIZE_Y){
            win_d2 = field[pos_d2[1]][pos_d2[0]] == sym ?  win_d1+1 : 0;
            if(win_d2 == WIN_SERIES)
                return true;
            pos_d2[0]--;
            pos_d2[1]++;
        }
        return false;
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