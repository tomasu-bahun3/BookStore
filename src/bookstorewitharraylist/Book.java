/*
 *              Revision History
 * ***************************************************************
 *
 * 12/30/2018   Implemented the class - aapplin
 */
package bookstorewitharraylist;

/**
 * A class to represent a Book. 
 * @author aapplin
 */
public class Book implements Comparable<Book>{
    /**
     * The isbn is unique identifier for a published work
     */
    private String isbn;
    /**
     * The number of books in the current inventory
     */
    private int    numberOnShelf;
    
    /**
     * Parameterized Constructor.
     * @param isbn the String representing the book's unique identifier
     * @param numberOnShelf the integer that represents the number in inventory
     */
    public Book(String isbn, int numberOnShelf) {
        this.isbn = isbn;
        this.numberOnShelf = numberOnShelf;
    }

    /**
     * Adds to the number of books in inventory
     * @param booksShipped the number that just came in.
     */
    public void addToNumberOnShelf(int booksShipped){
        if (booksShipped > 0){
            numberOnShelf += booksShipped;
        }
    }
    
    /**
     * Accessor for the property isbn
     * @return a string value of isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Accessor for the property numberOnShelf
     * @return the current value of numberOnShelf
     */
    public int getNumberOnShelf() {
        return numberOnShelf;
    }

    /**
     * A toString to print a book object in the form:
     * xxxxxxxxxxxxx  nnn
     * @return the string representation of this Book
     */
    @Override
    public String toString() {
        return String.format("%s%6d", isbn, numberOnShelf);
    }
    /**
     * compareTo (abstract method of the Comparable Interface) is implemented to
     * impose a natural ordering on a group of objects. compareTo is used by the
     * Collections.sort() method to allow us to sort the competitors belonging
     * to some Java collection.
     *
     * @param that is the Book object we are comparing this one to
     * @return a negative integer, zero, or a positive integer if this object
     * comes before, is equal to, or comes after the specified object.
     */
    @Override
    public int compareTo(Book that) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        // shouldn't be any null objects, but if there are 
        // put them at the end
        if (that == null) {
            return AFTER;
        }
        // this optimization is usually worthwhile, aand can always be
        // added - if the addresses are the same... they are equal
        if (this == that) {
            return EQUAL;
        }
        
        return this.isbn.compareTo(that.getIsbn());
    }
    /**
     * Unit test for Book testing the parameterized constructor 
     * and the toString() methods.
     * @param args the command line arguments
     */
    
    public static void main (String[] args){
        Book book = new Book("1234567890123", 5);
        System.out.println(book);
    }
    
}
