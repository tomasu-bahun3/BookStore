/*
 *              Revision History
 * ***************************************************************
 * 10/17/19 - Worked on lab - Thomas Bahun
 * 2019 - revised for use in a lab.
 */
package bookstorewitharraylist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author aapplin
 */
public class BookStoreWithArrayList {

    private ArrayList<Book> inventory = new ArrayList<>();
    /**
     * readInventory reads the entire inventory file into an ArrayList,
     * and then sorts it using Collections.sort()
     * @param fileName the first command line argument
     */
    public void readInventory(String fileName) {
        try {
            Scanner inFile = new Scanner(new FileReader(fileName));

            if (!inFile.hasNext()) {
                System.out.println("The file is empty.");
                System.exit(1);
            }
            // if there is stuff in the file... continue
            String isbn;
            int numOnShelf;
            while (inFile.hasNext()) {
                isbn = inFile.next();
                numOnShelf = inFile.nextInt();
                if (isbn.length() == 13 && numOnShelf >= 0) {
                    inventory.add(new Book(isbn, numOnShelf));
                }
            }
            inFile.close();
            Collections.sort(inventory);
        } catch (FileNotFoundException ex) {
            System.err.println("The file " + fileName + " does not exist");
            System.exit(1);
        } catch (InputMismatchException ex) {
            System.err.println("Attempt to read the wrong data type.");
            System.exit(1);
        }

    }
    
    /**
     * Binary search is used on sorted data only! It effectively cuts the
     * search area in half at each iteration. The number of comparisons for
     * a search is (log2 n) which is very fast. The search will only take
     * 13 iterations for a list of size 10,000.
     * @param keyToFind the String we are looking for
     * @return the index where the item was found or the place where it would
     * be if it were in the list.
     */
    public int binarySearch(String keyToFind) {
        int low = 0,
            high = inventory.size()-1,
            mid = 0,
            iterations = 0; // counter for the number of searches
        boolean found = false; // we havent started looking yet
        while(low <= high && !found) {
            mid = low + (high-low) / 2;
            if(inventory.get(mid).getIsbn().compareTo(keyToFind) == 0) {
                found = true;
            } else if (inventory.get(mid).getIsbn().compareTo(keyToFind) > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
            
            iterations++;
        }
        System.out.println("number of iterations: " + iterations);
        if(!found) {
            mid = -mid-1; // where it would be if it was here
        }
        return mid;
    }
   
    /**
     * Update inventory reads a file of new deliveries to the book store.
     * if the book is already in inventory it updates the number on shelf
     * by the number delivered.  If it is not in inventory it is added.
     * @param fileName a command line argument 
     */
    public void updateInventory(String fileName) {
        try {
            Scanner inFile = new Scanner(new FileReader(fileName));

            if (!inFile.hasNext()) { // nothing in the file?? 
                System.out.println("The file is empty.");
                System.exit(1);
            }
            // if there is stuff in the file... continue
            String isbn;
            int numInShipment;
            while (inFile.hasNext()) {
                isbn = inFile.next();
                numInShipment = inFile.nextInt();
                if (isbn.length() == 13 && numInShipment >= 0) { // valid
                    int index = binarySearch(isbn);
                    if (index >= 0 ){ // we already carry this book
                        inventory.get(index).addToNumberOnShelf(numInShipment);
                    }else{ // this is a new book
                        inventory.add(new Book(isbn, numInShipment));
                    }
                }
            }
            inFile.close();
            Collections.sort(inventory); //Sort it again
        } catch (FileNotFoundException ex) {
            System.err.println("The file " + fileName + " does not exist");
            System.exit(1);
        } catch (InputMismatchException ex) {
            System.err.println("Attempt to read the wrong data type.");
            System.exit(1);
        }
    }
    /**
     * writeNewInventory writes the current inventory to a file.
     * @param fileName  a command line parameter
     */
    public void writeNewInventory(String fileName) {
        try {
            PrintStream out = new PrintStream(new File(fileName));
            for (Book b : inventory){
                out.println(b);
            }
            out.close();
        }catch(FileNotFoundException ex){
            System.out.println("The file or directory is write protected");
            System.exit(1);
        }
    }

    /**
     * the driver for the program
     * @param filenames the command line arguments (3 are required for this
     * application)
     */
    public void run(String[] filenames) {
        readInventory(filenames[0]);
        System.out.println("Number of books in inventory " + inventory.size());
        updateInventory(filenames[1]);
        System.out.println("Number of books in inventory after update "
                + inventory.size());
        writeNewInventory(filenames[2]);
    }

    /**
     * This method checks for the correct number of command line arguments and
     * exits if they are not there. This application needs 3 filenames
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("usage: inFile updateFile outFile");
            System.exit(1);
        }
        BookStoreWithArrayList driver = new BookStoreWithArrayList();
        driver.run(args);
    }
}
