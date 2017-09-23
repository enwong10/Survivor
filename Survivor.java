/*--------------------------------------------------------------------------------------*/
/*  Survivor.java  -  A program that similuates the TV show Survivor, this program   */
/*                       uses linked lists to accept an undetermined amount of names    */
/*                       to be stored in a circular linked list. A backup of these      */
/*                       names is then created. One by one, these contestants are       */
/*                       eliminated, determined by the user, until one is left. Using   */
/*                       the backup list previously created, the names of the original  */
/*                       contestants are displayed, followed by the last man standing.  */
/*--------------------------------------------------------------------------------------*/
/*  Author: Enoch Wong                                                                  */
/*  Date: January 13, 2016                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: The names of the contestants, followed by the elimination order              */
/*  Output: The full list of contestants, and the winner after the elimination round    */
/*--------------------------------------------------------------------------------------*/

import java.io.*;
import java.util.*;
import java.text.*;

public class Survivor
{

    static Node initialize () throws IOException
    {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));

	//INITIALIZATION
	//declare variables
	Node head = null, name = null, tail = null;
	String contestantname;

	System.out.println ("***WELCOME TO SURVIVOR!*** \nPLEASE ENTER THE NAME OF THE CONTESTANTS. ENTER 'fin' WHEN COMPLETE.");

	//input contestants as long as "fin" is not entered
	do
	{
	    //read the input
	    contestantname = stdin.readLine ();

	    //set up linked list
	    if (!contestantname.equals ("fin"))
	    {
		name = new Node ();
		name.data = contestantname;
		name.next = head;
		head = name;
	    }

	    //set tail
	    if (name.next == null)
	    {
		tail = name;
	    }

	}
	while (!contestantname.equals ("fin"));

	//make the linked list circular
	tail.next = head;

	//return tail to main methods
	return tail;
    }


    //BACKUP METHOD
    //Node name and tail from the original node, backupname, backuphead and backuptail as pass by references to create a backup list
    static Node backup (Node tail)
    {
	//set the new nodes to null
	Node backupname = null, backuphead = null, backuptail = null;

	//reset name to tail
	Node name = tail;

	//do while name doesn't return to tail (full loop)
	do
	{
	    //create node
	    backupname = new Node ();
	    backupname.data = name.data;
	    backupname.next = backuphead;
	    backuphead = backupname;

	    //set tail
	    if (backupname.next == null)
	    {
		backuptail = backupname;
	    }
	    name = name.next;
	}
	while (name != tail);

	//make node circular by connecting head and tail
	backuptail.next = backuphead;

	//return backuptail to main method
	return backuptail;
    }


    //display the backup for user
    static void displaybackup (Node backuptail)
    {
	System.out.println ("The backup has been completed. \nThe contestants entered are: ");

	//show user the backup
	Node backupname = backuptail;
	do
	{
	    System.out.println (backupname.data);
	    backupname = backupname.next;
	}
	while (backupname != backuptail);
    }


    //ELIMINATE METHOD
    static String eliminate (Node tail) throws IOException
    {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));

	//declare variables
	String eliminate;
	Node delete = null, name = tail;
	boolean found;

	//do while there are more than 1 competitors in competition
	do
	{
	    //ask for person to be eliminated
	    System.out.println ("Who has been eliminated?");
	    eliminate = stdin.readLine ();
	    //reset linked list (name to tail)
	    name = tail;
	    //reset found to false after each loop
	    found = false;

	    //search for the name
	    do
	    {
		//if it's found
		if (name.next.data.equals (eliminate))
		{
		    //temporary node to the one being eliminated
		    delete = name.next;
		    //deletes the node by passing it over
		    name.next = delete.next;
		    //has been deleted
		    found = true;
		    System.out.println (eliminate + " has been eliminated. Better luck next time!");

		    //if tail gets deleted, then the tail gets shifted
		    if (delete == tail)
		    {
			tail = tail.next;
		    }
		    //reset the delete node
		    delete = null;
		}
		//moves the original node to the next node
		name = name.next;
	    }
	    while (name != tail && found == false);

	    //if unfound, tell user
	    if (found == false)
	    {
		System.out.println (eliminate + " was not found in the list. Please try again.");
	    }

	    System.out.println ();
	}
	while (!name.next.equals (name));

	//return winner
	return name.data;
    }


    //SUMMARY METHOD
    //backup name and backup tail to call the backup list
    static void summary (Node backupname, Node backuptail)
    {
	//output data
	System.out.println (backupname.data);
	//move node along
	backupname = backupname.next;

	//as long as node doesn't return to tail (full loop)
	if (backupname != backuptail)
	{
	    //recursion
	    summary (backupname, backuptail);
	}
    }


    public static void main (String str[]) throws IOException
    {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));
	DecimalFormat df = new DecimalFormat ("#");

	//declare variables
	Node tail = null;

	//INITIALIZATION
	//call initialize method
	tail = initialize ();

	//BACKUP
	//declare backuphead node
	Node backuptail = null;

	//output message
	System.out.println ();
	System.out.println ("***BACKUP STAGE***");

	//call backup method
	backuptail = backup (tail);

	//display backup
	displaybackup (backuptail);

	//ELIMINATION
	//declare variable
	String winner;

	//output message
	System.out.println ();
	System.out.println ("***ELIMINATION STAGE***");

	//call method and return the winner
	winner = eliminate (tail);

	//SUMMARY
	//set backupname to backuptail
	Node backupname = backuptail;
	//output
	System.out.println ("The game is now complete.");
	System.out.println ();
	System.out.println ("***RESULTS***");
	System.out.println ("The original contestants were:");
	//call method
	summary (backupname, backuptail);

	//CONGRATULATORY MESSAGE
	System.out.println ();
	System.out.println ("CONGRATULATIONS TO " + winner + " FOR WINNING THIS EDITION OF SURVIVOR!");
	System.out.println ("THAT'S IT FOR NOW, SEE YOU NEXT TIME!");

    }
}

