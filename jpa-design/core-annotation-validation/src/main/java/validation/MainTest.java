package validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class MainTest {

    public static void main(String[] args) {
        JavaValidationsDemo validationsDemo = new JavaValidationsDemo();
        System.out.println(validationsDemo.getOperation());

        // 需要通过验证器对Validations的注解进行验证
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<JavaValidationsDemo>> errors = validator.validate(validationsDemo);
        if(!errors.isEmpty()){
            System.out.println("Validation failed");
        }
    }
}
