// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class BankAccountDLL {
   BankAccount head = null;
   BankAccount tail = null;

   public BankAccountDLL() {
   }

   public void addAccount(BankAccount var1) {
      if (this.head == null) {
         this.head = this.tail = var1;
      } else {
         this.tail.next = var1;
         var1.prev = this.tail;
         this.tail = var1;
      }

   }

   public void displayRecords() {
      BankAccount var1 = this.head;
      if (var1 == null) {
         System.out.println("No records to display.");
      } else {
         while(var1 != null) {
            System.out.println(var1);
            var1 = var1.next;
         }

      }
   }

   public BankAccount findAccountById(String var1) {
      for(BankAccount var2 = this.head; var2 != null; var2 = var2.next) {
         if (var2.accountId.equals(var1)) {
            return var2;
         }
      }

      return null;
   }

   public void findAccountsByType(String var1) {
      BankAccount var2 = this.head;

      boolean var3;
      for(var3 = false; var2 != null; var2 = var2.next) {
         if (var2.accountType.equalsIgnoreCase(var1)) {
            System.out.println(var2);
            var3 = true;
         }
      }

      if (!var3) {
         System.out.println("No records found for account type: " + var1);
      }

   }

   public void deleteAccountById(String var1) {
      for(BankAccount var2 = this.head; var2 != null; var2 = var2.next) {
         if (var2.accountId.equals(var1)) {
            if (var2 == this.head && var2 == this.tail) {
               this.head = this.tail = null;
            } else if (var2 == this.head) {
               this.head = this.head.next;
               this.head.prev = null;
            } else if (var2 == this.tail) {
               this.tail = this.tail.prev;
               this.tail.next = null;
            } else {
               var2.prev.next = var2.next;
               var2.next.prev = var2.prev;
            }

            System.out.println("Customer record with ID " + var1 + " has been deleted.");
            return;
         }
      }

      System.out.println("No customer record found with ID: " + var1);
   }

   public void readFromFile(String var1) throws IOException {
      BufferedReader var2 = new BufferedReader(new FileReader(var1));

      String var3;
      while((var3 = var2.readLine()) != null) {
         String[] var4 = var3.split(",");
         String var5 = var4[0];
         String var6 = var4[1];
         String var7 = var4[2];
         String var8 = var4[3];
         String var9 = var4[4];
         double var10 = Double.parseDouble(var4[5]);
         String var12 = var4[6];
         double var13 = var12.equalsIgnoreCase("Fixed") ? Double.parseDouble(var4[7]) : 0.0;
         BankAccount var15 = new BankAccount(var5, var6, var7, var8, var9, var10, var12, var13);
         this.addAccount(var15);
      }

      var2.close();
   }

   public void writeToFile(String var1) throws IOException {
      BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));

      for(BankAccount var3 = this.head; var3 != null; var3 = var3.next) {
         var2.write(var3.toString());
         var2.newLine();
      }

      var2.close();
   }
}
