package pl.wietwioorki.to22019.model;

public enum Role {
    L("librarian"),
    U("User"),
    G("Guest");

    private String name;

    Role(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
