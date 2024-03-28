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
        modifyColor();
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

        Vector<Color> colorList = null;
        ColorCollection colorCollection = new ColorCollection();

        // Init Scanner obj
        Scanner input = new Scanner(System.in);

        System.out.println("\nYou have selected 'modifyColor'");
        System.out.println("enter the barcode Prefix of the color you wish to modify: ");
        bcpfx = input.nextLine();
        colorList = colorCollection.findColorBarcodePfx(bcpfx);



        
    } // end of modifyColor

    public static void deleteColor()
    {
        // Init Scanner obj
        Scanner input = new Scanner(System.in);

        System.out.println("\nYou have selected 'deleteColor'");
        System.out.println();
    } // end of deleteColor
} // end of class