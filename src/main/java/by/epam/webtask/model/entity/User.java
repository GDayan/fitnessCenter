package by.epam.webtask.model.entity;

public class User extends CustomEntity{

    public enum UserRole{
        ADMIN(1),
        CLIENT(2),
        TRAINER(3);

        private long roleId;

        UserRole(long id){
            roleId = id;
        }

        public long getRoleId(){
            return roleId;
        }
    }
    public enum UserState{
        ACTIVE(1),
        BLOCKED(2);

        private long stateId;

        UserState(long id){
            stateId = id;
        }

        public long getStateId(){
            return stateId;
        }
    }

    private long userId;
    private String login;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private UserState state;

    public User(){}

    public User(long userId, String firstName, String lastName, String login,
                String password, String email, int phoneNumber, UserRole role, UserState state) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.state = state;
    }

    public User(String firstName, String lastName, String login,
                String password, String email, int phoneNumber, UserRole role, UserState state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.state = state;
    }

    public static class Builder{
        private User user = new User();

        public Builder userId(long userId) {
            user.userId = userId;
            return this;
        }

        public Builder firstName(String firstName){
            user.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder login(String login) {
            user.login = login;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder phoneNumber(int phoneNumber) {
            user.phoneNumber = phoneNumber;
            return this;
        }

        public Builder role(UserRole role) {
            user.role = role;
            return this;
        }

        public Builder state(UserState state) {
            user.state = state;
            return this;
        }

        public User build() {
            return user;
        }
    }

    public long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public UserState getState() {
        return state;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setFirstName(String name) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != user.role) return false;//TODO
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (phoneNumber != user.phoneNumber) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return state == user.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + phoneNumber;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", role=").append(role);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
