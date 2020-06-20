
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Scanner;

public class elvina_lazy_girl {

    // 3. Определяем размеры массива
    static int SIZE_X ;
    static int SIZE_Y ;
    static  int count;
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
        do {
            System.out.println("Введите размеры поля: X Y (1-10)");
            SIZE_X = scanner.nextInt();
            SIZE_Y = scanner.nextInt();
        } while (!(SIZE_X > 0 && SIZE_X < 11 && SIZE_Y > 0 && SIZE_Y < 11));


        do {
            System.out.println("Введите выигрышную серию:");
            count = scanner.nextInt();
        } while (!(count <= Math.max(SIZE_X, SIZE_Y)));

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
        //изменила вывод на консоль верхней границы
        System.out.print(" ");
        for(int i=0;i<SIZE_X;i++)
            System.out.print("|"+ (i+1));
        System.out.print("|\n");
        for(int i = 0; i < SIZE_Y; i++) {
            System.out.print(i+1);
            System.out.print("|");
            for(int j = 0; j < SIZE_X; j++) {

                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        //6. украшаем картинку
        for(int i=0;i<SIZE_X;i++)
            System.out.print("--");
        System.out.print("-\n");
    }

    // 7. Метод который устанавливает символ
    private static void setSym(int y, int x, char sym){
        field[y][x] = sym;
    }
    static int player_x,ai_x=4;
    static int player_y,ai_y=4;
    // 9. Ход игрока
    private static void playerStep() {
        // 11. с проверкой
        int x;
        int y;
        do {
            System.out.println("Введите координаты: X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        setSym(y, x, PLAYER_DOT);
        player_x=x;
        player_y=y;
    }

    /*   // 13. Ход ПК
       private static void aiStep() {
           int x;
           int y;
           do{
               x = rand.nextInt(SIZE_X);
               y = rand.nextInt(SIZE_Y);
           } while(!isCellValid(y,x));
           setSym(y, x, AI_DOT);
       }*/
    // 13. Ход ПК
    private static void aiStep() {
        int[] pos_ai={-1,-1};

        do {
            pos_ai=findWinStep(AI_DOT,1);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findWinStep(PLAYER_DOT,1);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findWinStep(AI_DOT,2);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findWinStep(PLAYER_DOT,2);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findWinStep(AI_DOT,3);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findWinStep(PLAYER_DOT,3);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findBetweenPoint(PLAYER_DOT);
            if (pos_ai[0]==-1&&pos_ai[1]==-1)pos_ai=findBetweenPoint(AI_DOT);

            if (pos_ai[0]==-1&&pos_ai[1]==-1){
                System.out.println("player");
                pos_ai=AroundDot(player_y,player_x);}
            if (pos_ai[0]==-1&&pos_ai[1]==-1){
                //    System.out.println("ai");
                pos_ai=AroundDot(ai_y,ai_x);}
            if (pos_ai[0]==-1&&pos_ai[1]==-1){
                //   System.out.println("rand");
                pos_ai[0]=rand.nextInt(SIZE_Y);
                pos_ai[1]=rand.nextInt(SIZE_X);
            }
        } while (!isCellValid(pos_ai[0], pos_ai[1]));
        setSym(pos_ai[0], pos_ai[1], AI_DOT);
        ai_x=pos_ai[1];
        ai_y=pos_ai[0];
    }
    /*суть в том что мы в первую очередь
    проверяем нет ли победы у пк при count-1
    нет ли победы у игрока при count-1
     1 +пустое место и count-2 у пк//x.xxx
    1 +пустое место и count-2  у игрока
    нет ли свободной count-2 у пк
    нет ли свободной count-2 у игрока
   */
    public static int[] AroundDot(int j,int i){
        int[]best_step={-1,-1};
        if(j-1>-1&&i+1<SIZE_X&&field[j-1][i+1]==EMPTY_DOT)//3правовверх
        {best_step=new int[]{j-1,i+1};return best_step;}
        if(j+1<SIZE_Y&&i+1<SIZE_X&&field[j+1][i+1]==EMPTY_DOT)//4правониз
        {best_step=new int[]{j+1,i+1};return best_step;}
        if(j-1>-1&&i-1>-1&&field[j-1][i-1]==EMPTY_DOT)//1левовверх
        {best_step=new int[]{j-1,i-1};return best_step;}
        if(j+1<SIZE_Y&&i-1>-1&&field[j+1][i-1]==EMPTY_DOT)//2левониз
        {best_step=new int[]{j+1,i-1};return best_step;}
        if(i+1<SIZE_X&&field[j][i+1]==EMPTY_DOT)//6вправо
        {best_step=new int[]{j,i+1};return best_step;}
        if(j-1>-1&&field[j-1][i]==EMPTY_DOT)//7вверъ
        {best_step=new int[]{j-1,i};return best_step;}
        if(i-1>-1&&field[j][i-1]==EMPTY_DOT)//5влево
        {best_step=new int[]{j,i-1};return best_step;}


        if(j+1<SIZE_Y&&field[j+1][i]==EMPTY_DOT)//8вниз
        {best_step=new int[]{j+1,i};return best_step;}
        //диагонали


        //горизонтали и вертикали
        return best_step;
    }
    public static int[] findBetweenPoint(char sym){
        int[]best_step={-1,-1};
        System.out.println("between");
        //горизонталь
        for(int j=0; j<SIZE_Y;j++) {
            for (int i = 0; i < SIZE_X;i++ ) {
                if(field[j][i]==EMPTY_DOT&&i-1>-1&&field[j][i-1]==sym&&i+1<SIZE_X&&field[j][i+1]==sym)
                {best_step=new int[]{j,i};return best_step;}
            }
        }
        //вертикаль
        for(int i=0; i<SIZE_X;i++) {
            for (int j = 0; j < SIZE_Y;j++ ) {
                if(field[j][i]==EMPTY_DOT&&j-1>-1&&field[j-1][i]==sym&&j+1<SIZE_Y&&field[j+1][i]==sym)
                {best_step=new int[]{j,i};return best_step;}
            }
        }
        //диагональ главная
        for(int i=0; i<SIZE_X;i++) {
            for (int j = 0; j < SIZE_Y;j++ ) {
                if(field[j][i]==EMPTY_DOT&&j-1>-1&&i-1>-1&& field[j-1][i-1]==sym&&j+1<SIZE_Y&&i+1<SIZE_X&&field[j+1][i+1]==sym)
                {best_step=new int[]{j,i};return best_step;}
            }
        }
        //диагональ побочная
        for(int i=0; i<SIZE_X;i++) {
            for (int j = 0; j < SIZE_Y;j++ ) {
                if(field[j][i]==EMPTY_DOT&&j-1>-1&&i+1<SIZE_X&&field[j-1][i+1]==sym&&j+1<SIZE_Y&&i-1>-1&&field[j+1][i-1]==sym)
                {best_step=new int[]{j,i};return best_step;}
            }
        }
        return best_step;
    }
    public static int[] findWinStep(char sym,int before){
        int[]best_step={-1,-1};
        switch (before){
            case 1:{
                //System.out.println("v 1");
                System.out.println("case 1");
                //диагональ главная
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y;j++ ) {
                        int buff_i=i,buff_j=j;
                        while(buff_i<SIZE_X&&buff_j<SIZE_Y){
                            curr=field[buff_j][buff_i]==sym?curr+1:0;
                            if(curr==0)break;
                            if (curr == count-1&&buff_j-count+1>-1&&buff_i-count+1>-1&&
                                    field[buff_j-count+1][buff_i-count+1]==EMPTY_DOT)//.xxxxo
                            {best_step=new int[]{buff_j-count+1,buff_i-count+1};return best_step;};
                            if (curr == count-1&&buff_j+1<SIZE_Y&&buff_i+1<SIZE_X&&
                                    field[buff_j+1][buff_i+1]==EMPTY_DOT)
                            {best_step=new int[]{buff_j+1,buff_i+1};return best_step;};//xxxx.
                            if(curr==count-2&&buff_j-count+1>-1&&buff_i-count+1>-1&&
                                    field[buff_j-count+1][buff_i-count+1]==sym&&field[buff_j-count+2][buff_i-count+2]==EMPTY_DOT)
                            {best_step=new int[]{buff_j-count+2,buff_i-count+2};return best_step;}//вариант х.ххх
                            buff_i++;
                            buff_j++;
                        }
                    }
                }
                //диагональ побочная
                for(int i=SIZE_X-1;i>=0 ;i--) {
                    int curr=0;
                    for (int j = 0; j <SIZE_Y; j++) {
                        int buff_i=i,buff_j=j;
                        while(buff_i>-1&&buff_j<SIZE_Y){
                            if(field[buff_j][buff_i]==sym) {
                                curr = curr + 1;
                                if (curr == count - 1 && buff_j - count + 1 > -1 && buff_i +count- 1 < SIZE_X &&
                                        field[buff_j - count + 1][buff_i +count- 1] == EMPTY_DOT) {
                                    best_step = new int[]{buff_j - count + 1, buff_i +count-1};//.xxxxo
                                    return best_step;
                                }
                                if (curr == count - 1 && buff_j + 1 < SIZE_Y && buff_i - 1 > -1 &&
                                        field[buff_j + 1][buff_i - 1] == EMPTY_DOT) {
                                    best_step = new int[]{buff_j + 1, buff_i - 1};//xxxx.
                                    return best_step;
                                }
                                if(curr==count-2&&buff_j-count+1>-1&&buff_i+count-1<SIZE_X&&
                                        field[buff_j-count+1][buff_i+count-1]==sym&&field[buff_j-count+2][buff_i+count-2]==EMPTY_DOT)
                                {best_step=new int[]{buff_j-count+2,buff_i+count-2};return best_step;}//вариант х.ххх
                                buff_i--;
                                buff_j++;
                            }
                            else {curr=0;break;}
                        }
                    }
                }
                //горизонталь
                for(int j=0; j<SIZE_Y;j++) {
                    int curr =0;
                    for (int i = 0; i < SIZE_X;i++ ) {
                        curr = i < SIZE_X && field[j][i] == sym ? curr+1 : 0;
                        if(curr==count-1&&i-count+1>-1&&field[j][i-count+1]==EMPTY_DOT)
                        { best_step=new int[]{j,i-count+1};return best_step;}//.ххххо
                        if(curr==count-1&&i+1<SIZE_X&&field[j][i+1]==EMPTY_DOT)
                        {best_step=new int[]{j,i+1};return best_step;}//вариант хххх.
                        if(curr==count-2&&i-count+1>-1&&field[j][i-count+1]==sym&&field[j][i-count+2]==EMPTY_DOT)
                        {best_step=new int[]{j,i-count+2};return best_step;}//вариант х.ххх
                    }
                }
                //вертикаль
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y; j++) {
                        curr=j<SIZE_Y&&field[j][i]==sym?curr+1:0;
                        if(curr==count-1&&j-count+1>-1&&field[j-count+1][i]==EMPTY_DOT)
                        {best_step=new int[]{j-count+1,i};return best_step;}//.ххххо
                        if(curr==count-1&&j+1<SIZE_Y&&field[j+1][i]==EMPTY_DOT)
                        {best_step=new int[]{j+1,i};return best_step;}//вариант хххх.
                        if(curr==count-2&&j-count+1>-1&&field[j-count+1][i]==sym&&field[j-count+2][i]==EMPTY_DOT)
                        {best_step=new int[]{j-count+2,i};return best_step;}//вариант х.ххх
                    }}
                return best_step;
            }
            case 2:{
                System.out.println("case 2");
                //диагональ главная
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y;j++ ) {
                        int buff_i=i,buff_j=j;
                        while(buff_i<SIZE_X&&buff_j<SIZE_Y){
                            curr=field[buff_j][buff_i]==sym?curr+1:0;
                            if(curr==0)break;
                            if (curr == count-2&&buff_j-count+2>-1&&buff_i-count+2>-1&&
                                    field[buff_j-count+2][buff_i-count+2]==EMPTY_DOT&&
                                    buff_j+1<SIZE_Y&&buff_i+1<SIZE_X&&field[buff_j+1][buff_i+1]==EMPTY_DOT)
                            {best_step=new int[]{buff_j+1,buff_i+1};return best_step;};
                            if (curr == count-2&&buff_j-count+2>-1&&buff_i-count+2>-1&&
                                    field[buff_j-count+2][buff_i-count+2]==EMPTY_DOT&&
                                    buff_j+1<SIZE_Y&&buff_i+1<SIZE_X&&field[buff_j+1][buff_i+1]!=EMPTY_DOT)
                            {best_step=new int[]{buff_j-count+2,buff_i-count+2};return best_step;};
                            buff_i++;
                            buff_j++;
                        }
                    }
                }
                //диагональ побочная
                for(int i=SIZE_X-1;i>=0 ;i--) {
                    int curr=0;
                    for (int j = 0; j <SIZE_Y; j++) {
                        int buff_i=i,buff_j=j;
                        while(buff_i>-1&&buff_j<SIZE_Y){
                            if(field[buff_j][buff_i]==sym) {
                                curr = curr + 1;
                                if (curr == count - 2 && buff_j + 1 < SIZE_Y && buff_i - 1 > -1 &&
                                        field[buff_j + 1][buff_i - 1] == EMPTY_DOT&&
                                        buff_j-count+2>-1&&buff_i+count-2<SIZE_X&&
                                        field[buff_j-count+2][buff_i+count-2]==EMPTY_DOT) {
                                    best_step = new int[]{buff_j + 1, buff_i - 1};
                                    return best_step;
                                }
                                if (curr == count - 2 && buff_j + 1 < SIZE_Y && buff_i - 1 > -1 &&
                                        field[buff_j + 1][buff_i - 1] != EMPTY_DOT&&
                                        buff_j-count+2>-1&&buff_i+count-2<SIZE_X&&
                                        field[buff_j-count+2][buff_i+count-2]==EMPTY_DOT) {
                                    best_step = new int[]{buff_j-count+2, buff_i+count-2};
                                    return best_step;
                                }
                                buff_i--;
                                buff_j++;
                            }
                            else {curr=0;break;}
                        }
                    }
                }
                //горизонталь
                for(int j=0; j<SIZE_Y;j++) {
                    int curr =0;
                    for (int i = 0; i < SIZE_X;i++ ) {
                        curr = i < SIZE_X && field[j][i] == sym ? curr+1 : 0;
                        if(curr==count-2&&i-(count-2)>-1&&i+1<SIZE_X&&//.ххх.
                                field[j][i-count+2]==EMPTY_DOT&&field[j][i+1]==EMPTY_DOT)
                        { best_step=new int[]{j,i+1};return best_step;}
                        if(curr==count-2&&i-(count-2)>-1&&i+1<SIZE_X&&//.ххх.
                                field[j][i-count+2]==EMPTY_DOT&&field[j][i+1]!=EMPTY_DOT)
                        { best_step=new int[]{j,i-count+2};return best_step;}
                    }
                }
                //вертикаль
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y; j++) {
                        curr=j<SIZE_Y&&field[j][i]==sym?curr+1:0;//.ххх.
                        if(curr==count-2&&j-(count-2)>-1&&j+1<SIZE_Y&&
                                field[j-count+2][i]==EMPTY_DOT&&field[j+1][i]==EMPTY_DOT)
                        {  best_step=new int[]{j+1,i};return best_step;}
                        if(curr==count-2&&j-(count-2)>-1&&j+1<SIZE_Y&&
                                field[j-count+2][i]==EMPTY_DOT&&field[j+1][i]!=EMPTY_DOT)
                        {  best_step=new int[]{j-count+2,i};return best_step;}
                    }}
                return best_step;
            }
            case 3:{
                System.out.println("case 3");
                //диагональ главная
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y;j++ ) {
                        int buff_i=i,buff_j=j;
                        while(buff_i<SIZE_X&&buff_j<SIZE_Y){
                            curr=field[buff_j][buff_i]==sym?curr+1:0;
                            if(curr==0)break;
                            if (curr == count-3&&buff_j-count+2>-1&&buff_i-count+2>-1&&
                                    field[buff_j-count+2][buff_i-count+2]==EMPTY_DOT&&field[buff_j-count+3][buff_i-count+3]==EMPTY_DOT
                                    &&buff_j+1<SIZE_Y&&buff_i+1<SIZE_X&&field[buff_j+1][buff_i+1]==EMPTY_DOT)
                            {best_step=new int[]{buff_j-count+3,buff_i-count+3};return best_step;};
                            buff_i++;
                            buff_j++;
                        }
                    }
                }
                //диагональ побочная
                for(int i=SIZE_X-1;i>=0 ;i--) {
                    int curr=0;
                    for (int j = 0; j <SIZE_Y; j++) {
                        int buff_i=i,buff_j=j;
                        while(buff_i>-1&&buff_j<SIZE_Y){
                            if(field[buff_j][buff_i]==sym) {
                                curr = curr + 1;
                                if (curr == count - 3 && buff_j + 1 < SIZE_Y && buff_i - 1 > -1 &&
                                        field[buff_j + 1][buff_i - 1] == EMPTY_DOT&&
                                        buff_j-count+2>-1&&buff_i+count-2<SIZE_X&&
                                        field[buff_j-count+2][buff_i+count-2]==EMPTY_DOT&&
                                        field[buff_j-count+3][buff_i+count-3]==EMPTY_DOT) {
                                    best_step = new int[]{buff_j-count+3, buff_i+count-3};
                                    return best_step;
                                }
                                buff_i--;
                                buff_j++;
                            }
                            else {curr=0;break;}
                        }
                    }
                }
                //горизонталь
                for(int j=0; j<SIZE_Y;j++) {
                    int curr =0;
                    for (int i = 0; i < SIZE_X;i++ ) {
                        curr = i < SIZE_X && field[j][i] == sym ? curr+1 : 0;
                        if(curr==count-3&&i-(count-2)>-1&&i+1<SIZE_X&&//.ххх.
                                field[j][i-count+2]==EMPTY_DOT&&field[j][i+1]==EMPTY_DOT&&
                                field[j][i-count+3]==EMPTY_DOT)
                        { best_step=new int[]{j,i-count+3};return best_step;}
                    }
                }
                //вертикаль
                for(int i=0; i<SIZE_X;i++) {
                    int curr=0;
                    for (int j = 0; j < SIZE_Y; j++) {
                        curr=j<SIZE_Y&&field[j][i]==sym?curr+1:0;//.ххх.
                        if(curr==count-3&&j-(count-2)>-1&&j+1<SIZE_Y&&
                                field[j-count+2][i]==EMPTY_DOT&&field[j+1][i]==EMPTY_DOT
                                &&field[j-count+3][i]==EMPTY_DOT)
                        {  best_step=new int[]{j-count+3,i};return best_step;}

                    }}
                return best_step;
            }
        }
        return best_step;
    }
    public static int[] findPos(char sym)
    {
        int[] pos_ai={-1,-1};

        return pos_ai;
    }

    //метод проверки рядом стоящих фишек
    //по горизонтали
    public static boolean checkGorizont(char sym)
    {

        for(int j=0; j<SIZE_Y;j++) {
            int curr =0;
            for (int i = 0; i < SIZE_X;i++ ) {
                curr = i < SIZE_X && field[j][i] == sym ? curr+1 : 0;
                if(curr==count)return true;
            }
        }

        return false;
    }
    //метод проверки рядом стоящих фишек
    //по вертикали
    public static boolean checkVertical(char sym) {
        for(int i=0; i<SIZE_X;i++) {
            int curr=0;
            for (int j = 0; j < SIZE_Y; j++) {
                curr=j<SIZE_Y&&field[j][i]==sym?curr+1:0;
                if(curr==count)return true;
            }}
        return false;
    }
    //по диагонали главной
    public static boolean checkDiagonal_1(char sym) {

        for(int i=0; i<SIZE_X;i++) {

            for (int j = 0; j < SIZE_Y;j++ ) {
                int curr=0;
                int buff_i=i,buff_j=j;
                while(buff_i<SIZE_X&&buff_j<SIZE_Y){
                    if(field[buff_j][buff_i]==sym)
                    {curr=curr+1;
                        buff_i++;
                        buff_j++;
                        if (curr == count) return true;}
                    else {curr=0;break;}
                }
            }
        }
        return false;
    }
    //по диагонали побочной
    public static boolean checkDiagonal_2(char sym) {
        for(int i=SIZE_X-1;i>=0 ;i--) {
            for (int j = 0; j <SIZE_Y; j++) {
                int curr=0;
                int buff_i=i,buff_j=j;
                while(buff_i>-1&&buff_j<SIZE_Y){
                    if(field[buff_j][buff_i]==sym)
                    {curr=curr+1;
                        buff_i--;
                        buff_j++;
                        if (curr == count) return true;}
                    else {curr=0;break;}
                }
            }
        }
        return false;
    }

    // 14. Проверка победы
    private static boolean checkWin(char sym) {
        //по горизонтали
        if (checkGorizont(sym)&&count<=SIZE_X)return true;
        //по вертикали
        if (checkVertical(sym)&&count<=SIZE_Y)return true;
        //по диагоналям
        if (checkDiagonal_1(sym)&&count<=SIZE_Y&&count<=SIZE_X)return true;
        if (checkDiagonal_2(sym)&&count<=SIZE_Y&&count<=SIZE_X)return true;
        return  false;
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
        // 1 - 1 иницируем и выводим на печать

        // 15 Основной ход программы

        while (true) {
            playerStep();
            printField();
            if(checkWin(PLAYER_DOT)) {
                System.out.println("Player WIN!");
                break;
            }
            if(isFieldFull()) {
                System.out.println("DRAW");
                break;
            }

            aiStep();
            printField();
            if(checkWin(AI_DOT)) {
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
