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

        //retrieve user given properties
        System.out.print("Please enter a new color description: ");
        newColorInfo.setProperty("Description", input.nextLine());

        System.out.print("Please enter a new color Barcode Prefix: ");
        newColorInfo.setProperty("BarcodePrefix", input.nextLine());

        System.out.print("Please enter a new color Alpha code: ");
        newColorInfo.setProperty("AlphaCode", input.nextLine());

        //create a new color object and save to database
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

        //retrieve color by barcode
        System.out.println("\nYou have selected 'modifyColor'");
        System.out.print("enter the barcode Prefix of the color you wish to modify: ");
        bcpfx = input.nextLine();
        colorList = colorCollection.findColorBarcodePfx(bcpfx);
        //If finding color by description or alphaCode the teller will also have to
        //ask the user to select the correct color from given colors

        //print colors retrieved
        System.out.println(colorList);

        System.out.print("Enter a new Description(leave blank if unchanged): ");
        userInput = input.nextLine();
        if (userInput != ""){
            //select the chosen color from list and modify the description
            colorList.elementAt(0).modifyDescription(userInput);
        }

        System.out.print("Enter a new BarcodePrefix(leave blank if unchanged): ");
        userInput = input.nextLine();
        if (userInput != ""){
            //select the chosen color from list and modify the BarcodePrefix
            colorList.elementAt(0).modifyBarcodePrefix(userInput);
        }

        System.out.print("Enter a new AlphaCode(leave blank if unchanged): ");
        userInput = input.nextLine();
        if (userInput != ""){
            //select the chosen color from list and modify the AlphaCode
            colorList.elementAt(0).modifyAlphaCode(userInput);
        }

        //Update the chosen color in the database with the new information
        colorList.elementAt(0).updateStateInDatabase();

        //print the updated color
        System.out.println("Here is the modified color;");
        System.out.println(colorList.elementAt(0));


        
    } // end of modifyColor

    public static void deleteColor()
    {
        // Init Scanner obj
        Scanner input = new Scanner(System.in);
        String bcpfx;
        String userInput;
        Vector<Color> colorList = null;
        ColorCollection colorCollection = new ColorCollection();

        //retrieve color by barcode
        System.out.println("\nYou have selected 'deleteColor'");
        System.out.print("enter the barcode Prefix of the color you wish to delete: ");
        bcpfx = input.nextLine();
        colorList = colorCollection.findColorBarcodePfx(bcpfx);
        //If finding color by description or alphaCode the teller will also have to
        //ask the user to select the correct color from given colors

        //print color and confirm
        System.out.println(colorList);
        System.out.print("Are you sure you want to delete this color?(Y/N): ");
        userInput = input.nextLine();

        if (userInput.equals("Y") || userInput.equals("y")) {
            //mark color inactive to delete
            colorList.elementAt(0).stateChangeRequest("markInactive", "");
            System.out.println("Color deleted");
        } else {
            System.out.println("unrecognized " + userInput);
        }


    } // end of deleteColor
} // end of class