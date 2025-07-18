package gift.exception.conflict;

public class DuplicateEmailException extends DataConflictException {

    public DuplicateEmailException(String email) {
        super("이미 존재하는 이메일입니다: " + email);
    }
}
