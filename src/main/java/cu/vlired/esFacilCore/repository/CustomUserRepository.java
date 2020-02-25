package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.util.Page;

import java.util.List;

public interface CustomUserRepository {
    List<User> list(Page page);
}
