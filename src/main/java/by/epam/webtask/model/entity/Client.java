package by.epam.webtask.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Client extends User {

    private LocalDateTime registerDate;
    private BigDecimal money;

    public Client() {
    }

    public Client(LocalDateTime registerDate, BigDecimal money) {
        this.registerDate = registerDate;
        this.money = money;
    }

    public Client(long userId, String firstName, String lastName, String login, String password, String email, int phoneNumber, UserRole role, UserState state, LocalDateTime registerDate, BigDecimal money) {
        super(userId, firstName, lastName, login, password, email, phoneNumber, role, state);
        this.registerDate = registerDate;
        this.money = money;
    }

    public Client(String firstName, String lastName, String login, String password, String email, int phoneNumber, UserRole role, UserState state, LocalDateTime registerDate, BigDecimal money) {
        super(firstName, lastName, login, password, email, phoneNumber, role, state);
        this.registerDate = registerDate;
        this.money = money;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (registerDate != client.registerDate) return false;
        return money != null ? money.equals(client.money) : client.money == null;
    }

    @Override
    public int hashCode() {
        int result = registerDate.hashCode();
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("registerDate=").append(registerDate);
        sb.append(", money='").append(money).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
