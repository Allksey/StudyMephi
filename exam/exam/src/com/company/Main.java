package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        initGame();
    }

    private static void initGame() {

        scenario();

    }

    public static void scenario(){

        Scanner sc = new Scanner(System.in);
        String s1;
        System.out.println("Здравствуй, Друг");
        System.out.println("Ты находишься в комнате, из которой можно выбраться через несколько дверей");
        System.out.println("Выбери куда ты пойдешь, написав цифру двери");
        int door = 3;
        doors(door);
        s1 = sc.nextLine();
        boolean isNumeric = s1.chars().allMatch( Character::isDigit );
        if(!isNumeric){
            System.out.println("Некорректный ввод");
            return;
        }
        switch (s1) {
            case ("0"):
                System.out.println("Ты выбрал дверь под номером 0 и попал в другую комнату с двумя дверьми\n Выбери одну из них, написав ее цифру, чтобы зайти в нее");
                door = 2;
                doors(door);
                s1 = sc.nextLine();
                isNumeric = s1.chars().allMatch( Character::isDigit );

                if(!isNumeric){
                    System.out.println("Некорректный ввод");
                    return;
                }
                switch (s1) {
                    case ("0"):
                        System.out.println("Ты зашел в дверь с номером 0 и выбрался наружу, Победа!!!");
                        return;
                    case ("1"):
                        System.out.println("Ты зашел в дверь с номером 1 и упал со скалы, Проигрыш!!!");
                        return;
                }
                break;
            case ("1"):
                System.out.println("Ты зашел в дверь с номером 1 и проснулся, Победа!!!");
                return;
            case ("2"):
                System.out.println("Ты зашел в дверь с номером 2 и попал в лапы зверя, Проигрыш!!!");
                return;
        }
    }

    public static void doors(int countDoors){
        int height = 5;
        String leftAlignFormat = "| %-15s |";
        for(int i = 0; i < countDoors; i++ ) {
            System.out.format("+-----------------+");
        }
        System.out.format("%n");
        for(int i = 0; i < countDoors; i++ ) {
            System.out.format("|     Дверь %d     |", i);

        }
        System.out.format("%n");
        for(int i = 0; i < countDoors; i++ ) {
            System.out.format("+-----------------+");
        }
        System.out.format("%n");
        for (int j = 0; j < height; j++) {
        for(int i = 0; i < countDoors; i++ ) {

                if (j == height/2) {
                    System.out.format(leftAlignFormat, "--");
                }
                else{
                    System.out.format(leftAlignFormat, "");
                }

            }
            System.out.format("%n");
        }
        for(int i = 0; i < countDoors; i++ ) {
            System.out.format("+-----------------+");
        }
        System.out.format("%n");
    }

}
