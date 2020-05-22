ТicTacToe
===
Игра крестики нолики с выбором размера поля (1-10) и выигрышной серии в зависимости от размеров поля.

Для данного проекта были поставлены следующие задачи:

1) Игрок имеет возможность выбирать размеры поля и выигрышную серию.
2) Игра может проверять выигрыш для любого размера поля и выигрышной серии.
3) ИИ мешает игроку победить.
---
CheckWin - победа для произвольного размера поля
---
Для того, чтобы сделать победу для произвольного размера поля, необходимо сделать проверку не каждого возможного исхода победы
по всей игре, а для каждого возможного исхода победы после определенного хода. Для этого для функции нужны данные о последнем ходе игрока
или ИИ, после чего проверяется горизонталь, вериткаль и диагонали относительно прошедшего хода.
