package ru.innopolis.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.model.Organization;
import ru.innopolis.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OrganizationDAOImpl.
 *
 * @author Ilya_Sukhachev
 */
@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

    Map<UUID, Organization> organizations = new ConcurrentHashMap();

    @Override
    public Organization getByUser(User user) {
        return organizations.values().stream()
                .filter(organization -> organization.getUsers().contains(user))
                .findFirst().orElse(null);
    }

    @Override
    public Organization getByName(String organizationName) {
        return organizations.values().stream()
                .filter(organization -> organization.getOrganizationName().equals(organizationName))
                .findFirst().orElse(null);
    }

    @Override
    public void save(Organization organization) {
        organization.setId(UUID.randomUUID());
        organizations.put(organization.getId(), organization);
    }

    @Override
    public void update(Organization organization) {
        organization.setId(UUID.randomUUID());
        organizations.put(organization.getId(), organization);
    }

    @Override
    public Collection<Organization> getAll() {
        return organizations.values();
    }
}
