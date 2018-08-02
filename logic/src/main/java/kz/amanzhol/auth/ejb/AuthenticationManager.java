package kz.amanzhol.auth.ejb;

import kz.amanzhol.auth.domain.Credentials;
import kz.amanzhol.auth.domain.ShopUser;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AuthenticationManager {
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public ShopUser.Role loginAsUser(String email, String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return null;
        }

        Credentials credentials = entityManager.find(Credentials.class, email);
        if (credentials == null) {
            return null;
        }

        if (!password.equals(credentials.getPassword())) {
            return null;
        }

        ShopUser shopUser = credentials.getShopUser();
        if (shopUser == null) {
            return null;
        }

        return shopUser.getRole();
    }
}
