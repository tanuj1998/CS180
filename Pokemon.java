import java.util.*;
/**
 *Pokemon Go
 * 
 * @author Tanuj Yadav, tyadav@purdue.edu
 *
 * @version 10/07/16 
*/


public class Pokemon
 {
    private String name;
    private int ID;
    private String type;
    private int healthPower;
    private double baseAttackPower;
    private static int NUM_POKEMONS = 0;

    public Pokemon(String name, String type, int healthPower, double baseAttackPower) 
    {
        this.name = name;
        this.ID = NUM_POKEMONS++;

        if (type.equalsIgnoreCase("fire") || type.equalsIgnoreCase("grass") || type.equalsIgnoreCase("water") || type.equalsIgnoreCase("electric")) 
        {
            this.type = MyUtils.formatStr(type);
        } else 
        {
            this.type = "fire";
        }
        if (healthPower > 0) 
        {
            this.healthPower = healthPower;
        } 
        else 
        {
            this.healthPower = 0;
        }
        if (baseAttackPower > 0)
        {
            this.baseAttackPower = baseAttackPower;
        } 
        else 
        {
            this.baseAttackPower = 1;
        }
    }

    public String getName() 
    {
        return this.name;
    }

    public int getId() 
    {
        return this.ID;
    }

    public String getType() 
    {
        return this.type;
    }

    public int getHealthPower() 
    {
        return this.healthPower;
    }

    public double getBaseAttackPower() 
    {
        return this.baseAttackPower;
    }

    public boolean setType(String type) 
    {
        if (type.equalsIgnoreCase("Water") || type.equalsIgnoreCase("Fire") || type.equalsIgnoreCase("Grass") || type.equalsIgnoreCase("Electric")) 
        {
            this.type = MyUtils.formatStr(type);
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public boolean setHealthPower(int healthPower) 
    {
        if (healthPower >= 0) 
        {
            this.healthPower = healthPower;
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public boolean setBaseAttackPower(double baseAttackPower)
     {
        if (baseAttackPower > 0) 
        {
            this.baseAttackPower = baseAttackPower;
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public String toString()
     {
        return ("Name: " + this.getName() + "\n ID: " + this.getId() + "\n Type: " + this.getType() + "\n Health Power: " + this.getHealthPower() +  "\n Base attack power: " + this.getBaseAttackPower());
    }

    public boolean isDead() 
    {
        if (healthPower <= 0)
         {
            this.healthPower = 0;
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public void reduceHealthPower(int healthPower) 
    {
        if (this.getHealthPower() - healthPower <= 0) 
        {
            this.healthPower = 0;
        } 
        else 
        {
            this.healthPower -= healthPower;
        }
    }

    public void boostHealthPower(int healthPower) 
    {
        this.healthPower += healthPower;
    }

    public static int battle(Pokemon p1, Pokemon p2) 
    {
        int winner = 0;
        double pokemon1 = p1.getBaseAttackPower() * getAttackMultiplier(p1, p2);
        double pokemon2 = p2.getBaseAttackPower() * getAttackMultiplier(p2, p1);

        while (p1.getHealthPower() != 0 || p2.getHealthPower() != 0) 
        {
            p1.reduceHealthPower((int) pokemon2);
            p2.reduceHealthPower((int) pokemon1);

            if (p1.isDead() && p2.isDead())
             {
                System.out.println(p1.toString());
                System.out.println(p2.toString());
                winner = 1;
                break;
            } else if (p1.isDead()) 
            {
                System.out.println(p1.toString());
                System.out.println(p2.toString());
                winner = 2;
                break;
            } else if (p2.isDead()) 
            {
                System.out.println(p1.toString());
                System.out.println(p2.toString());
                winner = 1;
                break;
            }
        }
        return winner;
    }

    public static double getAttackMultiplier(Pokemon attacker, Pokemon defender) 
    {

        double i = 0;
        if (attacker.type.equalsIgnoreCase("Grass") && defender.type.equalsIgnoreCase("Grass"))
        {
            i = 0.5;
        }
        
        else if (attacker.type.equalsIgnoreCase("Grass") && defender.type.equalsIgnoreCase("water"))
        {
            i = 2.0;
        }
        else if (attacker.type.equalsIgnoreCase("Grass") && defender.type.equalsIgnoreCase("Electric"))
        {
            i = 1.0;
        }
        else if (attacker.type.equalsIgnoreCase("Grass") && defender.type.equalsIgnoreCase("fire"))
        {
            i = 0.5;
        }
        
        else if (attacker.type.equalsIgnoreCase("electric") && defender.type.equalsIgnoreCase("water"))
        {
            i = 2.0;
        }
        else if (attacker.type.equalsIgnoreCase("electric") && defender.type.equalsIgnoreCase("fire"))
        {
            i = 1.0;
        }
        else if (attacker.type.equalsIgnoreCase("electric") && defender.type.equalsIgnoreCase("electric"))
        {
            i = 0.5;
        }
        else if (attacker.type.equalsIgnoreCase("electric") && defender.type.equalsIgnoreCase("grass"))
        {
            i = 0.5;
        }
        
        else if (attacker.type.equalsIgnoreCase("water") && defender.type.equalsIgnoreCase("electric"))
        {
            i = 1.0;
        }
        else if (attacker.type.equalsIgnoreCase("water") && defender.type.equalsIgnoreCase("fire"))
        {
            i = 2.0;
        }
        else if (attacker.type.equalsIgnoreCase("water") && defender.type.equalsIgnoreCase("water"))
        {
            i = 0.5;
        }
        else if (attacker.type.equalsIgnoreCase("water") && defender.type.equalsIgnoreCase("grass"))
        {
            i = 0.5;
        }
        else if (attacker.type.equalsIgnoreCase("fire") && defender.type.equalsIgnoreCase("fire"))
        {
            i = 0.5;
        }
        
        else if (attacker.type.equalsIgnoreCase("fire") && defender.type.equalsIgnoreCase("water"))
        {
            i = 0.5;
        }
        else if (attacker.type.equalsIgnoreCase("fire") && defender.type.equalsIgnoreCase("electric"))
        {
            i = 1.0;
        }
        else if (attacker.type.equalsIgnoreCase("fire") && defender.type.equalsIgnoreCase("grass"))
        {
            i = 2.0;
        }

        return i;

    }

    public static int battleOracle(Pokemon p1, Pokemon p2) 
    {

        double pokemon1 = p1.getBaseAttackPower() * getAttackMultiplier(p1, p2);
        double pokemon2 = p2.getBaseAttackPower() * getAttackMultiplier(p2, p1);

        if(pokemon1 < pokemon2)
         {
            return 2;
        }
        else if(pokemon1 > pokemon2)
         {
            return 1;
        }
        else 
        {
            return 1;
        }

    }
}
