import java.util.*;
/**
 * CS180 - Lab 05
 *
 * The aim of the program is to recommend a list of restaurants based on the
 * user's preference.
 *
 * @author Tanuj Yadav, tyadav@purdue.edu,Lab Section: G06
 *
 * @version 09/23/16
 *
 */
public class Restaurants {
    // On campus
    public static final String ON_CAMPUS_VEGAN = "Purdue Dining Courts\nFlatbreads";
    public static final String ON_CAMPUS_VEGETARIAN = ON_CAMPUS_VEGAN + "\nOasis Cafe\nAh*Z\nFreshens";
    public static final String ON_CAMPUS_GLUTEN_FREE = "Purdue Dining Courts\nFlatbreads\nOasis Cafe\nPappy's " +
            "Sweet Shop";
    public static final String ON_CAMPUS_BURGERS = "Pappy's Sweet Shop\nCary Knight Spot";
    public static final String ON_CAMPUS_SANDWICHES = "Flatbreads\nOasis Cafe\nErbert & Gerbert's";
    public static final String ON_CAMPUS_OTHERS = "Purdue Dining Courts\nAh*Z\nFreshens\nLemongrass";
    public static final String ON_CAMPUS_ALL = ON_CAMPUS_BURGERS + "\n" + ON_CAMPUS_SANDWICHES + "\n" +
            ON_CAMPUS_OTHERS;

    // Off campus
    public static final String OFF_CAMPUS_VEGAN = "Chipotle\nQdoba\nNine Irish Brothers\nFive Guys\n Puccini's " +
            "Smiling Teeth\nPanera Bread";
    public static final String OFF_CAMPUS_VEGETARIAN = OFF_CAMPUS_VEGAN + "\nWendy's\nBruno's Pizza\nJimmy " +
            "John's\nPotbelly Sandwich Shop\nBasil Thai\nIndia Mahal";
    public static final String OFF_CAMPUS_GLUTEN_FREE = "Chipotle\nQdoba\nNine Irish Brothers\nPuccini's Smiling " +
            "Teeth\nWendy's\nScotty's Brewhouse\nPanera Bread\nBasil Thai";
    public static final String OFF_CAMPUS_BURGERS = "Five Guys\nWendy's\nTriple XXX\nScotty's Brewhouse";
    public static final String OFF_CAMPUS_SANDWICHES ="Panera Bread\nJimmy John's\nPotbelly Sandwich Shop";
    public static final String OFF_CAMPUS_PIZZAS = "Puccini's Smiling Teeth\nMad Mushroom Pizza\nBruno's Pizza\n";
    public static final String OFF_CAMPUS_OTHERS = "Chipotle\nQdoba\nNine Irish Brothers\nFamous Frank's\n Von's " +
            "Dough Shack\nBuffalo Wild Wings\nBasil Thai\nMaru Sushi\nIndia Mahal\nHappy China\nYori";
    public static final String offCampusAll = OFF_CAMPUS_BURGERS + "\n" + OFF_CAMPUS_SANDWICHES + "\n" +
            OFF_CAMPUS_PIZZAS + OFF_CAMPUS_OTHERS;


public static void main(String[] args){
Scanner myScanner=new Scanner(System.in);
int inputCampus,inputRestriction,enterPreference;
String dietRestriction, inputPreference=null;

System.out.println("Enter 1 for On Campus \n Enter 2 for Off Campus");

     inputCampus=myScanner.nextInt();
     if(inputCampus==1){
     System.out.println("Do you have a dietary restriction?(Y/N)");
     dietRestriction=myScanner.next();
     if(dietRestriction.equals("Y")){
        System.out.println("Press 1 for vegan,2 for Vegetarian,3 for Gluten Free");
        inputRestriction=myScanner.nextInt();
        if(inputRestriction==1)
{
            System.out.println(ON_CAMPUS_VEGAN);
        }
        else if(inputRestriction==2)
        {
            System.out.println(ON_CAMPUS_VEGETARIAN);
        }
        else if(inputRestriction==3)
        {
            System.out.println(ON_CAMPUS_GLUTEN_FREE);
        }
        else System.out.println("DOES NOT EXIST");
        }
        else
        {
        System.out.println("Do you have a food preference?(Y/N)");
        inputPreference=myScanner.next();
        };
        if(inputPreference.equals("Y")){
        System.out.println("Enter 1 for burgers, 2 for sandwiches, 3 for others");
        enterPreference=myScanner.nextInt();
        if(enterPreference==1)
        {
            System.out.println(ON_CAMPUS_BURGERS);
        }
        else if(enterPreference==2)
        {
             System.out.println(ON_CAMPUS_SANDWICHES);
        }
        else if(enterPreference==3)
        {
          System.out.println(ON_CAMPUS_OTHERS);
        }
        else
        {
        System.out.println("DOES NOT EXIST");
        }
       else System.out.println(ON_CAMPUS_ALL);
       }
       else
        {
            if(inputCampus==2){
            System.out.println("Do you have a dietary restriction?(Y/N)");
            dietRestriction=myScanner.next();
            if(dietRestriction.equals("Y")){
            System.out.println("Press 1 for vegan,2 for Vegetarian,3 for Gluten Free");
            inputRestriction=myScanner.nextInt();
            if(inputRestriction==1)
            {
            System.out.println(OFF_CAMPUS_VEGAN);
            }
else if(inputRestriction==2)
            {
            System.out.println(OFF_CAMPUS_VEGETARIAN);
            }
            else if(inputRestriction==3)
           {
            System.out.println(OFF_CAMPUS_GLUTEN_FREE);
           }
           else System.out.println("DOES NOT EXIST");

          }
          else
          {
            System.out.println("Do you have a food preference?(Y/N)");
            inputPreference=myScanner.next();
          }
         if(inputPreference.equals("Y")){
         System.out.println("Enter 1 for burgers, 2 for sandwiches, 3 for pizzas, 4 for others");
         enterPreference=myScanner.nextInt();
         if(enterPreference==1)
        {
            System.out.println(OFF_CAMPUS_BURGERS);
        }
        else if(enterPreference==2)
        {
             System.out.println(OFF_CAMPUS_SANDWICHES);
        }
        else if(enterPreference==3)
        {
             System.out.println(OFF_CAMPUS_PIZZAS);
        }
        else if(enterPreference==4)
        {
        System.out.println(OFF_CAMPUS_OTHERS);
        }
        else System.out.println("DOES NOT EXIST");
        }
        else System.out.println(offCampusAll);
       }
     }
   }
}
                                                                                                                                                                   148,1         Bot
