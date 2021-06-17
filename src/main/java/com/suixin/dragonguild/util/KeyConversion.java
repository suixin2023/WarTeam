package com.suixin.dragonguild.util;

public class KeyConversion {
    public static int conversion(String letter){
        String toLowerCase = letter.toLowerCase();
        int key;
        switch (toLowerCase){
            case "g":
                key = 34;
                break;
            case "q":
                key = 16;
                break;
            case "e":
                key = 18;
                break;
            case "r":
                key = 19;
                break;
            case "t":
                key = 20;
                break;
            case "y":
                key = 21;
                break;
            case "u":
                key = 22;
                break;
            case "i":
                key = 23;
                break;
            case "o":
                key = 24;
                break;
            case "p":
                key = 25;
                break;
            case "h":
                key = 35;
                break;
            case "j":
                key = 36;
                break;
            case "k":
                key = 37;
                break;
            case "l":
                key = 38;
                break;
            case "z":
                key = 44;
                break;
            case "x":
                key = 45;
                break;
            case "c":
                key = 46;
                break;
            case "v":
                key = 47;
                break;
            case "b":
                key = 48;
                break;
            case "n":
                key = 49;
                break;
            case "m":
                key = 50;
                break;
            default:
                key = 0;
        }
        return key;
    }
}
