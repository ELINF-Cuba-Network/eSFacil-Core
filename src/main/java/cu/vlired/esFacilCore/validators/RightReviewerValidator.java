package cu.vlired.esFacilCore.validators;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.constants.Condition;
import cu.vlired.esFacilCore.exception.ResourceNotFoundException;
import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import cu.vlired.esFacilCore.repository.UserRepository;
import cu.vlired.esFacilCore.services.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class RightReviewerValidator
    implements ConstraintValidator<RightReviewer, UUID> {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private I18n i18n;

    @Override
    public void initialize(RightReviewer constraintAnnotation) {

    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext constraintValidatorContext) {

        Document document = documentRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                i18n.t("app.document.id.not.found", ArrayUtils.toArray(id))
            ));

        User currentUser = userService.getCurrent();
        return document.getPerson().getId().equals(currentUser.getId());
    }
}
