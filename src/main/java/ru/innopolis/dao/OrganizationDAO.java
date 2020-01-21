package ru.innopolis.dao;

import ru.innopolis.model.Organization;
import ru.innopolis.model.User;

import java.util.Collection;

/**
 * OrganizationDAO.
 *
 * @author Ilya_Sukhachev
 */
public interface OrganizationDAO {
    Organization getByUser(User user);

    Organization getByName(String organizationName);

    void save(Organization organization);

    void update(Organization organization);

    Collection<Organization> getAll();
}
