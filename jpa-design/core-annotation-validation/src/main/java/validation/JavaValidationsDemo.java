package validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class JavaValidationsDemo {

    @NotNull(message = "operation must not be null")
    @NotBlank(message = "operation must not be empty")
    private String operation;

    @NotNull(message = "user must not be null")
    private List<String> users;

    public JavaValidationsDemo() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
