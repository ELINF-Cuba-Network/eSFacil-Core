package cu.vlired.esFacilCore.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RightReviewerValidator.class)
public @interface RightReviewer {
    String message() default "{app.document.this.user.can.not.reject}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
