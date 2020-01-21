package ru.innopolis.service.sec.service.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;
import ru.innopolis.model.Organization;
import ru.innopolis.model.User;
import ru.innopolis.service.sec.exception.RegistrationServiceException;
import ru.innopolis.service.sec.service.dto.UserReqForm;

/**
 * Интерфейс для работы с регистрацией пользователей в системе
 */
public interface RegistrationService {

    /**
     * Регистрирует новый аккаунт оператора
     *
     * @param userReqForm
     * @param user
     * @return
     * @throws RegistrationServiceException
     */
    @PreAuthorize("hasAnyRole(T(ru.iteco.blockchain.server.model.enums.Role).ROLE_ADMIN)")
    Mono<ResponseEntity<UserReqForm>> registerNewOperatorAccount(UserReqForm userReqForm, User user)
            throws RegistrationServiceException;


    /**
     * Регистрирует новый аккаунт администратора
     *
     * @param userReqForm
     *
     * @return
     *
     * @throws RegistrationServiceException
     */
    Mono<ResponseEntity<UserReqForm>> registerNewAdminAccount(UserReqForm userReqForm);

    Organization regOrganization(String organizationName);

}
