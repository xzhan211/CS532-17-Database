import java.sql.*;
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;
import java.util.Formatter;
import java.io.IOException;
import java.io.File;


public class proj2 {
   public static void main (String args []) throws SQLException {
    try
    {
        
        String keybuffer;
        String userbuffer;
        Formatter formatter = new Formatter(System.out);
        BufferedReader  readKeyBoard;
        boolean exit = false;

        /*input user name and keyword*/
        readKeyBoard = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter user name >>> ");
        userbuffer = readKeyBoard.readLine();
        System.out.print("Please enter keyword >>> ");
        keybuffer = readKeyBoard.readLine();

        //Connecting to Oracle server. Need to replace username and
        //password by your username and your password. For security
        //consideration, it's better to read them in from keyboard.
        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
        Connection conn = ds.getConnection(userbuffer, keybuffer);

        System.out.println("******************************************************");
        System.out.println("|Database Project 2                      Version: 1.0|");
        System.out.println("|By: Xiaoyang Zhang                     BN: B00708854|");
        System.out.println("******************************************************\n");

      while(exit == false){


        String showTable = "KKKKKKKK";
        String commandName = null;
        String showPur = "KKKKKKKK";
        String inputEID = "KKKKKKKK";
        String inputCustomersCID = "KKKKKKKK";
        String inputCustomersName = "KKKKKKKK";
        String inputCustomersTel = "KKKKKKKK";
        String inputPurchasesEID = "KKKKKKKK";
        String inputPurchasesPID = "KKKKKKKK";
        String inputPurchasesCID = "KKKKKKKK";
        String inputPurchasesQTY = "KKKKKKKK";
        boolean ouputPurchasesValid = true;
        String inputPurchasesPur = "KKKKKKKK";

        System.out.println("******************************************************");
        System.out.println("|command       :                                     |");
        System.out.println("|1. [display]  : show the tuples in each table.      |");
        System.out.println("|2. [save]     : show purchase saving.               |");
        System.out.println("|3. [activity] : show monthly sale activity.         |");
        System.out.println("|4. [+customer]: add customer tuple.                 |");
        System.out.println("|5. [+purchase]: add purchase tuple.                 |");
        System.out.println("|6. [-purchase]: delete purchase tuple.              |");
        System.out.println("|7. [exit]     : exit JDBC                           |");
        System.out.println("|****************************************************|");
        System.out.println("|table name:                                         |");
        System.out.println("|1. employees                                        |");
        System.out.println("|2. customers                                        |");
        System.out.println("|3. products                                         |");
        System.out.println("|4. discounts                                        |");
        System.out.println("|5. suppliers                                        |");
        System.out.println("|6. supplies                                         |");
        System.out.println("|7. purchases                                        |");
        System.out.println("|8. logs                                             |");
        System.out.println("******************************************************\n");

        while(true){
          System.out.print("Please enter command: ");
          commandName = readKeyBoard.readLine();
          if(commandName.equals("display")){
              break;
          }else if(commandName.equals("save")){
              break;
          }else if(commandName.equals("activity")){
              break;
          }else if(commandName.equals("+customer")){
              break;
          }else if(commandName.equals("+purchase")){
              break;
          }else if(commandName.equals("-purchase")){
              break;
          }else if(commandName.equals("exit")){
              break;
          }
          System.out.println("Enter Command ERROR.\n");
        }        

        if(commandName.equals("display")){
          while(true){
            System.out.print("Please enter table name: ");
            showTable = readKeyBoard.readLine();
            if(showTable.equals("employees")){
                break;
            }else if(showTable.equals("customers")){
                break;
            }else if(showTable.equals("products")){
                break;
            }else if(showTable.equals("discounts")){
                break;
            }else if(showTable.equals("suppliers")){
                break;
            }else if(showTable.equals("supplies")){
                break;
            }else if(showTable.equals("purchases")){
                break;
            }else if(showTable.equals("logs")){
                break;
            }
            System.out.println("Enter Table Name ERROR.\n");
          }        
        }
        else if(commandName.equals("save")){
          System.out.print("Please enter pur# number: ");
          showPur = readKeyBoard.readLine();
        }else if(commandName.equals("activity")){
          System.out.print("Please enter employee_ID: ");
          inputEID = readKeyBoard.readLine();
        }else if(commandName.equals("+customer")){
          System.out.print("Please enter customer cid: ");
          inputCustomersCID = readKeyBoard.readLine();
          System.out.print("Please enter customer name: ");
          inputCustomersName = readKeyBoard.readLine();
          System.out.print("Please enter customer tel: ");
          inputCustomersTel = readKeyBoard.readLine();
        }else if(commandName.equals("+purchase")){
          System.out.print("Please enter purchase eid: ");
          inputPurchasesEID = readKeyBoard.readLine();
          System.out.print("Please enter purchase pid: ");
          inputPurchasesPID = readKeyBoard.readLine();
          System.out.print("Please enter purchase cid: ");
          inputPurchasesCID = readKeyBoard.readLine();
          System.out.print("Please enter purchase qty: ");
          inputPurchasesQTY = readKeyBoard.readLine();
        }else if(commandName.equals("-purchase")){
          System.out.print("Please enter delete purchase pur#: ");
          inputPurchasesPur = readKeyBoard.readLine();
        }else if(commandName.equals("exit")){
          System.out.print("Exit JDBC");
          exit = true;
        }
        
        int buffer = 0;
        String bufferString = null;
        int bufferNumber = 0;
        
        if(showTable.equals("employees"))
        {
          buffer = 1;
          bufferString = "begin ? := proj2.show_employees(); end;";
          showTable = null;
        }
        else if(showTable.equals("customers")){
          buffer = 2;
          bufferString = "begin ? := proj2.show_customers(); end;";
          showTable = null;
        }
        else if(showTable.equals("discounts")){
          buffer = 3;
          bufferString = "begin ? := proj2.show_discounts(); end;";
          showTable = null;
        }
        else if(showTable.equals("products")){
          buffer = 4;
          bufferString = "begin ? := proj2.show_products(); end;";
          showTable = null;
        }
        else if(showTable.equals("purchases")){
          buffer = 5;
          bufferString = "begin ? := proj2.show_purchases(); end;";
          showTable = null;
        }
        else if(showTable.equals("suppliers")){
          buffer = 6;
          bufferString = "begin ? := proj2.show_suppliers(); end;";
          showTable = null;
        }
        else if(showTable.equals("supplies")){
          buffer = 7;
          bufferString = "begin ? := proj2.show_supplies(); end;";
          showTable = null;
        }
        else if(showTable.equals("logs")){
          buffer = 8;
          bufferString = "begin ? := proj2.show_logs(); end;";
          showTable = null;
        }

        if(commandName.equals("save")){
          buffer = 9;
          bufferString = "begin ? := proj2.purchase_saving("+ showPur +"); end;";
          commandName = null;
        }
        else if(commandName.equals("activity")){
          buffer = 10;
          bufferString = "begin proj2.monthly_sale_activities('"+ inputEID +"',:1); end;";
          commandName = null;
        } 
        else if(commandName.equals("+customer")){
          buffer = 11;
          bufferString = "begin proj2.add_customer('"+ inputCustomersCID +"','"+ inputCustomersName +"','"+ inputCustomersTel +"'); end;";
          commandName = null;
        } 
        else if(commandName.equals("+purchase")){
          buffer = 12;
          bufferString = "begin proj2.add_purchase('"+ inputPurchasesEID +"','"+ inputPurchasesPID +"','"+ inputPurchasesCID +"','"+ inputPurchasesQTY +"', :1, :2); end;";
          commandName = null;
        }
        else if(commandName.equals("-purchase")){
          buffer = 13;
          bufferString = "begin proj2.delete_purchase('"+ inputPurchasesPur +"'); end;";
          commandName = null;
        }
        else if(commandName.equals("exit")){
          buffer = 14;
        }

        //Prepare to call stored procedure:
        CallableStatement cs = conn.prepareCall(bufferString);
        //register the out parameter (the first parameter)
        if (buffer != 11 && buffer != 12 && buffer != 13 && buffer != 9 && buffer != 14){
          cs.registerOutParameter(1, OracleTypes.CURSOR);
        }else if (buffer == 12){
          cs.registerOutParameter(1, OracleTypes.INTEGER);
          cs.registerOutParameter(2, OracleTypes.INTEGER);
        }else if (buffer == 9){
          cs.registerOutParameter(1, OracleTypes.INTEGER);
        }
        // execute and retrieve the result set
        cs.execute();
        if (buffer != 11 && buffer != 12 && buffer != 13 && buffer != 9){
            ResultSet rs = (ResultSet)cs.getObject(1);
          switch(buffer){
                case 1:
                      System.out.print("\n");
                      formatter.format("%-3s %-15s %-12s %-20s\n", "EID", "NAME", "TELEPHONE#", "EMAIL");
                      while (rs.next()) {
                      formatter.format("----------------------------------------------\n");
                      formatter.format("%-3s %-15s %-12s %-20s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                      }
                      System.out.print("\n");
                break;

                case 2:
                      System.out.print("\n");
                      formatter.format("%-4s %-15s %-12s %-4s  %-12s\n", "CID", "NAME", "TELEPHONE#", "VISITS_MADE", "LAST_VISIT_DATE");
                      while (rs.next()) {
                      formatter.format("-------------------------------------------------------------------\n");
                      formatter.format("%-4s %-15s %-12s %-12s %-12s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                      }
                      System.out.print("\n");
                break;

                case 3:
                      System.out.print("\n");
                      formatter.format("%-1s %-3s\n", "DISCNT_CATEGORY", "DISCNT_RATE");
                      while (rs.next()) {
                      formatter.format("-------------------------------\n");
                      formatter.format("%-15s %-3s\n", rs.getString(1), rs.getString(2));
                      }
                      System.out.print("\n");
                break;

                case 4:
                      System.out.print("\n");
                      formatter.format("%-4s %-15s %-5s %-4s %-6s %-1s\n", "PID", "NAME", "QOH", "QOH_THRESHOLD", "ORIGINAL_PRICE", "DISCNT_CATEGORY");
                      while (rs.next()) {
                      formatter.format("-------------------------------------------------------------\n");
                      formatter.format("%-4s %-15s %-5s %-13s %-14s %-1s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                      }
                      System.out.print("\n");
                break;

                case 5:
                      System.out.print("\n");
                      formatter.format("%-6s %-3s %-4s %-4s %-5s %-23s %-20s\n", "PUR#", "EID", "PID", "CID", "QTY", "PTIME", "TOTAL_PRICE");
                      while (rs.next()) {
                      formatter.format("-----------------------------------------------------------\n");
                      formatter.format("%-6s %-3s %-4s %-4s %-5s %-23s %-20s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                      }
                      System.out.print("\n");
                break;

                case 6:
                      System.out.print("\n");
                      formatter.format("%-2s %-15s %-15s %-23s %-20s\n", "SID", "NAME", "CITY", "TELEPHONE#", "EMAIL");
                      while (rs.next()) {
                      formatter.format("-------------------------------------------------------------\n");
                      formatter.format("%-2s %-15s %-16s %-12s %-20s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                      }
                      System.out.print("\n");
                break;

                case 7:
                      System.out.print("\n");
                      formatter.format("%-4s %-4s %-2s %-12s %-5s\n", "SUP#", "PID", "SID", "SDATE", "QUANTITY");
                      while (rs.next()) {
                      formatter.format("-----------------------------------------------\n");
                      formatter.format("%-4s %-4s %-3s %-12s %-5s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                      }
                      System.out.print("\n");
                break;

                case 8:
                      System.out.print("\n");
                      formatter.format("%-5s %-12s %-6s %-21s %-20s %-6s\n", "LOG#", "USER_NAME", "OPERATION", "OP_TIME", "TABLE_NAME", "TUPLE_PKEY");
                      while (rs.next()) {
                      formatter.format("----------------------------------------------------------------------------------\n");
                      formatter.format("%-5s %-12s %-9s %-12s %-20s %-6s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                      }
                      System.out.print("\n");
                break;

                case 10:
                      System.out.print("\n");
                      formatter.format("%-3s %-15s %-3s %-5s %-10s %-15s\n", "EID", "NAME", "TIMES", "QUANTITY", "TOTAL_DOLLAR", "MONTH/YEAR");
                      while (rs.next()) {
                      formatter.format("-------------------------------------------------------------\n");
                      formatter.format("%-3s %-15s %-5s %-8s %-12s %-15s\n", inputEID, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                      }
                      System.out.print("\n");
                break;   

                default:
                break;
          }
          
        }

        if(buffer == 9){
          System.out.printf("The total saving for pur#"+ showPur +" is $%.2f\n", cs.getFloat(1));

        }
        if(buffer == 12 && cs.getInt(1) == 0){
          System.out.println("Insufficient quantity in stock.\n"); 
        }else if(buffer == 12 && cs.getInt(1) == 2){
          System.out.println("Current qoh of the product is below the required threshold.");
          System.out.println("New supply is reguired, make new order.");
          System.out.println("Product ID >> " + inputPurchasesPID + "   update quantity of hold >> " + cs.getInt(2)); 
        }
        
        //close the result set, statement, and the connection
        if(exit == true){
          cs.close();
          conn.close();
        }
      }
   }
   catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());}
   catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
   
  }//main
}//class
