import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JColorChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CS180 - Lab 08
 *
 * The aim of the program is to check if a student is eligible for financial aid and if he is eligible, then
 * for how much aid.
 *
 * @author Tanuj Yadav, tyadav@purdue.edu,Lab Section: G06
 *
 * @version 10/21/16
 *
 */

public class FAFSAGUI
{
    public static void main(String[] args)
    {
        JFrame frame;
        frame = new JFrame();
        frame.setSize( 500,700 );
        frame.setTitle("Welcome");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container cPane = frame.getContentPane();
        cPane.setBackground(Color.BLUE);

        do{
            JOptionPane.showMessageDialog( null, "Welcome to the FAFSA!","Welcome", JOptionPane.INFORMATION_MESSAGE );
            int accept = JOptionPane.showConfirmDialog( null, "Have you been accepted into a degree or certificate program?", "Program Acceptance", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            boolean isAcceptedStudent = false;
            if(accept == JOptionPane.YES_OPTION)
                isAcceptedStudent = true;
            int register = JOptionPane.showConfirmDialog( null, "Are you registered for the selective service?", "Selective Service", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            boolean isSSregistered = false;
            if(register == JOptionPane.YES_OPTION )
                isSSregistered = true;

            int socialNum = JOptionPane.showConfirmDialog(null, "Do you have a social security number?", "Social Security Number", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            boolean hasssn;
            if(socialNum == JOptionPane.YES_OPTION);
                hasssn = true;

            int rezstatus = JOptionPane.showConfirmDialog( null, "Do you have valid residency status?","Residency Status", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
            boolean hasValidResidency = false;
            if(rezstatus == JOptionPane.YES_OPTION)
                hasValidResidency = true;

            String age = null;
            do{
                age = JOptionPane.showInputDialog(null, "How old are you?","Age", JOptionPane.QUESTION_MESSAGE);
                int a = Integer.parseInt(age);
                if(a < 0)
                    JOptionPane.showMessageDialog(null, "Age cannot be a negative number.", "Error: Age", JOptionPane.ERROR_MESSAGE);
            }while (Integer.parseInt(age) < 0);

            String creditHours = null;
            do{
                creditHours = JOptionPane.showInputDialog(null, "How many credit hours do you plan on taking?", "Credit Hours", JOptionPane.QUESTION_MESSAGE);
                int ch = Integer.parseInt(creditHours);
                if(ch < 1 || ch > 24)
                    JOptionPane.showMessageDialog(null, "Credit hours must be between 1 and 24, inclusive.", "Error: Credit Hours", JOptionPane.ERROR_MESSAGE);
            }while (Integer.parseInt( creditHours ) < 1 || Integer.parseInt( creditHours ) > 24);

            String income = JOptionPane.showInputDialog( null, "What is your total yearly income?", "Student Income" , JOptionPane.QUESTION_MESSAGE );
            if(Double.parseDouble( income ) < 0)
                JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Student Income", JOptionPane.ERROR_MESSAGE);


            String houseIncome = JOptionPane.showInputDialog( null, "What is your parent's total yearly income?", "Parent Income", JOptionPane.QUESTION_MESSAGE);
            if(Double.parseDouble( houseIncome ) < 0)
                JOptionPane.showMessageDialog( null, "Income cannot be a negative number.", "Error: Parent Income", JOptionPane.ERROR_MESSAGE);
            int dependent = JOptionPane.showConfirmDialog( null,"Are you a dependent?", "Dependency", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE  );

            boolean isDependent = false;
            if(dependent == JOptionPane.YES_OPTION)
                isDependent = true;
            String[] classes = {"Freshman", "Sophomore", "Junior","Senior","Graduate"};
            String studentClass = (String) JOptionPane.showInputDialog( null, "What is your current class standing?", "Class Standing", JOptionPane.PLAIN_MESSAGE, null, classes, classes[0]);
            if(studentClass == "Freshman" || studentClass == "Sophomore" || studentClass == "Junior" || studentClass == "Senior" )
                studentClass = "Undergraduate";
            else
                studentClass = "Graduate";

            FAFSA fafsa = new FAFSA(isAcceptedStudent, isSSregistered, hasssn, hasValidResidency, isDependent, Integer.parseInt( age ), Integer.parseInt( creditHours ), Double.parseDouble( income ), Double.parseDouble( houseIncome), studentClass);
            fafsa.calcEFC();
            fafsa.calcStaffordLoan();
            fafsa.calcFederalGrant();
            fafsa.calcWorkStudy();
            fafsa.calcFederalAidAmount();
            fafsa.isFederalAidEligible();


            JOptionPane.showMessageDialog( null, "Loans: " + fafsa.calcStaffordLoan() + "\nGrants: " + fafsa.calcFederalGrant() + "\nWork Study: " + fafsa.calcWorkStudy() + "\n--------------" + "\nTotal: " + fafsa.calcFederalAidAmount(), "FAFSA Results", JOptionPane.INFORMATION_MESSAGE);

            int lastQ = JOptionPane.showConfirmDialog( null, "Would you like to complete another Application", "Continue",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(lastQ == JOptionPane.NO_OPTION )
                break;
        }
        while (true);
    }
}
