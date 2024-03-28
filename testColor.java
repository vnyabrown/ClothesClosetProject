import java.util.Scanner;
import java.util.Vector;

import model.Color;
import model.ColorCollection;

import java.util.InputMismatchException;
import java.util.Vector;
import java.util.Scanner;
import java.util.Properties;
import database.*;

//java -cp mariadb-java-client-3.0.3.jar;classes;. ATM
//To run Java FX
public class testColor
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int userInput;

        System.out.print("1 Add, 2 modify, 3 delete: ");
        userInput = input.nextInt();

        switch(userInput){
            case 1:
                addColor();
                break;
            case 2:
                modifyColor();
                break;
            case 3:
                deleteColor();
                break;
        }


    } // end of main

    public static void addColor()
    {
        // Init Scanner obj
        Scanner input = new Scanner(System.in);

        System.out.println("\nYou have selected 'addColor'");


        Properties newColorInfo = new Properties();

        System.out.print("Please enter a new color description: ");
        newColorInfo.setProperty("Description", input.nextLine());

        System.out.print("Please enter a new color Barcode Prefix: ");
        newColorInfo.setProperty("BarcodePrefix", input.nextLine());

        System.out.print("Please enter a new color Alpha code: ");
        newColorInfo.setProperty("AlphaCode", input.nextLine());


        Color newColor = new Color(newColorInfo);
        newColor.save();
    } // end of addColor

    public static void modifyColor()
    {
        // Requires getting a Color Collection
        String bcpfx;
        String userInput;
        Vector<Color> colorList = null;
        ColorCollection colorCollection = new ColorCollection();

        // Init Scanner obj
        Scanner input = new Scanner(System.in);

        System.out.println("\nYou have selected 'modifyColor'");
        System.out.print("enter the barcode Prefix of the color you wish to modify: ");
        bcpfx = input.nextLine();
        colorList = colorCollection.findColorBarcodePfx(bcpfx);
        System.out.println(colorList);

        System.out.print("Enter a new Description(leave blank if unchanged): ");
        userInput = input.nextLine();
        if (userInput != ""){
            //finish modify
        }
        System.out.print("Enter a new BarcodePrefix(leave blank if unchanged): ");

        System.out.print("Enter a new AlphaCode(leave blank if unchanged): ");



        
    } // end of modifyColor

    public static void deleteColor()
    {
        // Init Scanner obj
        Scanner input = new Scanner(System.in);
        String bcpfx;
        String userInput;
        Vector<Color> colorList = null;
        ColorCollection colorCollection = new ColorCollection();

        System.out.println("\nYou have selected 'deleteColor'");
        System.out.print("enter the barcode Prefix of the color you wish to delete: ");
        bcpfx = input.nextLine();
        colorList = colorCollection.findColorBarcodePfx(bcpfx);

        System.out.println(colorList);
        System.out.print("Are you sure you want to delete this color?(Y/N): ");
        userInput = input.nextLine();

        if (userInput.equals("Y") || userInput.equals("y")) {
            colorList.elementAt(0).stateChangeRequest("markInactive", "");
            System.out.println("Color deleted");
        } else {
            System.out.println("unrecognized " + userInput);
        }


    } // end of deleteColor
} // end of class