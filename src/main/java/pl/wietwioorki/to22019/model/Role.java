package pl.wietwioorki.to22019.model;

public enum Role {
    L("Librarian"),
    U("User"),
    G("Guest");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public static Role of(String value) {
        for (Role role : Role.values()) {
            if (role.name.equals(value)) {
                return role;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
