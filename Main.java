import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

public class Main {
	static Song[] songs;
	static AVLTree name;
	static AVLTree artist;
	static AVLTree ID;
	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("song.txt");
		Scanner reader = new Scanner(file);
		Scanner input = new Scanner(System.in);
		Scanner lineCounter = new Scanner(file);
		int size = 0;
		while(lineCounter.hasNextLine()) {
			lineCounter.nextLine();
			size++;
		}
		songs = new Song[size]; 
		String[] songInfo = new String[size*5];
		name = new AVLTree();
		artist = new AVLTree();
		ID = new AVLTree();
		int count = 0;
		while(reader.hasNextLine()) { //Song name;Artist;ID;Genre;Year
			songInfo = reader.nextLine().split(";");
			for(int i=0;i<songInfo.length;i = i + 5) {
				 songs[count] = new Song(songInfo[i],songInfo[i+1],songInfo[i+2],songInfo[i+3],songInfo[i+4]);
				 name.insert(songs[count].name, count);
				 artist.insert(songs[count].artist, count);
				 ID.insert(songs[count].id, count);
				 count++;
			}
		}
		mainMenu();
		int choice = input.nextInt();
		System.out.println();
		while(choice != 0) {
			switch(choice) {
				case 1:
					System.out.println("0-Return Main Menu\n"
							+ "1-First Search(Name,Artist or ID)\n"
							+ "2-Third Search(Between Two ID)\n");
					int searchType = input.nextInt();
					if(searchType == 1) {
						int searchType2 = 0;
						do {
							System.out.println("What do you want to search for? \n0-Finish \n1-Name \n2-Artist \n3-ID ");
							searchType2 = input.nextInt();
							switch(searchType2) {
								case 1:
									Scanner inputName = new Scanner(System.in);
									System.out.println("Enter the name of the song you want to found(Enter first letters capital): ");
									String searchName = inputName.nextLine();
									if(firstSearch(name.root,searchName) != null)
										System.out.println(printInfo(firstSearch(name.root,searchName).index));
									else
										System.out.println("Could not found.");
									break;
								case 2:
									Scanner inputArtist = new Scanner(System.in);
									System.out.println("Enter the artist of the song you want to found(Enter first letters capital): ");
									String searchArtist = inputArtist.nextLine();
									if(firstSearch(artist.root,searchArtist) != null)
										System.out.println(printInfo(firstSearch(artist.root,searchArtist).index));
									else
										System.out.println("Could not found.");
									break;
								case 3:
									Scanner inputId = new Scanner(System.in);
									System.out.println("Enter the id of the song you want to found(Enter first letters capital): ");
									String searchId = inputId.nextLine();
									if(firstSearch(ID.root,searchId) != null)
										System.out.println(printInfo(firstSearch(ID.root,searchId).index));
									else 
										System.out.println("Could not found.");
									break;
							}
						}while(searchType2 !=0);
					}
					else if(searchType == 2) {
						System.out.println("Enter the lower bound: ");
						int lowerBound = input.nextInt();
						System.out.println("Enter the higher bound: ");
						int higherBound = input.nextInt();
						thirdSearch(ID.root, lowerBound, higherBound);
					}else if(searchType == 0) {
						mainMenu();
						choice = input.nextInt();
					}
					break;
				case 2:	
					System.out.println("Enter the ID you want to delete: "
							+ "0- Return Main Menu\n");
					String deleteID = input.next();
					if(!deleteID.equals("0")) {
						AVLNode backup = firstSearch(ID.root, deleteID);
						if(backup != null) {
							String backupName = songs[backup.index].name;
							String backupArtist = songs[backup.index].artist;
							System.out.println("DELETED!\n" + printInfo(backup.index));
							ID.deleteNode(ID.root, deleteID);
							name.deleteNode(name.root, backupName);
							artist.deleteNode(artist.root, backupArtist);	
						}else System.out.println("Could not found or delete.");
					}
					else{
						mainMenu();
						choice = input.nextInt();
					}	
					break;
			}
		}
	}

	private static void mainMenu() {
		System.out.println("Select the action: \n"
				+ "0-Finish\n"
				+ "1-Search\n"
				+ "2-Delete\n");
	}
	
	public static AVLNode firstSearch(AVLNode focus, String data) {
		if(focus == null) return null;
		if(focus.data.equals(data)) return focus;
		if(data.compareTo(focus.data) > 0) return firstSearch(focus.right, data);
		else return firstSearch(focus.left, data);
	}
	
	public static void thirdSearch(AVLNode focus, int start, int finish){
		if(focus == null) return;
		int id = Integer.parseInt(focus.data);
		if(id >= start) thirdSearch(focus.left,start,finish);
		if(id >= start && id <= finish) {
			System.out.println(printInfo(focus.index));
		}
		if(id<= finish) thirdSearch(focus.right,start,finish);
	}	
	
	public static String printInfo(int index) { 
		return songs[index].toString();
	}
	
}