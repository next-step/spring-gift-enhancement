package gift.member;

public enum Role {
    ADMIN,
    USER;

    public static boolean containsIgnoreCase(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
