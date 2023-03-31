public class Song {
	
	String name;
	String artist;
	String id;
	String genre;
	String year;
	
	public Song(String name, String artist, String id, String genre, String year) {
		this.name = name;
		this.artist = artist;
		this.id = id;
		this.genre = genre;
		this.year = year;
	}
	
	public Song() {
		
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ", Artist: " + artist + ", ID: " + id
				+ ", Genre: " + genre + ", Year: " + year; 
	}
	
}
