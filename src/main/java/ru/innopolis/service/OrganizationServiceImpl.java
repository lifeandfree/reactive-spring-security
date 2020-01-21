package ru.innopolis.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.innopolis.dao.OrganizationDAO;
import ru.innopolis.model.Organization;
import ru.innopolis.model.User;
import ru.innopolis.service.exception.Exception404;
import ru.innopolis.service.sec.service.dto.OrganizationDto;

import java.util.Collection;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LogManager.getLogger(OrganizationServiceImpl.class.getName());

    private OrganizationDAO organizationDAO;

    public OrganizationServiceImpl(OrganizationDAO organizationDAO) {
        this.organizationDAO = organizationDAO;
    }

    /**
     *  Получить список организаций
     *
     * @param user
     * @return
     */
    @Override
    public Flux<OrganizationDto> getOrganizationsExcludeUserOrganization(User user)  {

        logger.debug("Get Organizations Exclude Current User - " + user.getId());

        Organization userOrganization = organizationDAO.getByUser(user);

        if (userOrganization == null) {
            throw new Exception404("Неизвестное название организации");
        }

        Collection<Organization> organizationList = organizationDAO.getAll();

        return Flux.fromStream(
                organizationList.stream()
                        .filter(organization ->
                                !organization.equals(userOrganization))
                        .map(orgInfo -> new OrganizationDto(orgInfo.getOrganizationName()))
        );
    }

}
