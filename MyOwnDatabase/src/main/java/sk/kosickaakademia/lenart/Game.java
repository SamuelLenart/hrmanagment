package sk.kosickaakademia.lenart;

public class Game {
    private int id;
    private String name;
    private int created;
    private String genre;
    private String players;

    public Game(int id, String name, int created, String genre, String players){
        this(name, created, genre, players);
        this.id = id;
    }

    public Game(String name, int created, String genre, String players) {
        this.name=name;
        this.created=created;
        this.genre=genre;
        this.players=players;
    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getCreated(){
        return created;
    }

    public String getGenre(){
        return genre;
    }

    public String getPlayers(){
        return players;
    }
}
