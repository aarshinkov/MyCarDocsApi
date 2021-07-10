package bg.forcar.api.utils;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum Roles {

    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    private Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role.toUpperCase();
    }
}
