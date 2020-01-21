package ru.innopolis.service;

import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.service.dto.OrganizationDto;

public interface OrganizationService {

    /**
     *  Получить список организаций
     *
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyRole(T(ru.iteco.blockchain.server.model.enums.Role).ROLE_OPERATOR)")
    Flux<OrganizationDto> getOrganizationsExcludeUserOrganization(User user) ;

}
