package ru.innopolis.service.sec.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.innopolis.dao.OrganizationDAO;
import ru.innopolis.dao.UserDao;
import ru.innopolis.dao.UserInfoDAO;
import ru.innopolis.model.Organization;
import ru.innopolis.model.OrganizationStatus;
import ru.innopolis.model.Role;
import ru.innopolis.model.User;
import ru.innopolis.model.UserInfo;
import ru.innopolis.model.UserStatus;
import ru.innopolis.service.sec.exception.RegistrationServiceException;
import ru.innopolis.service.sec.service.dto.UserReqForm;
import ru.innopolis.service.sec.service.interfaces.RegistrationService;

/**
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger logger = LogManager.getLogger(RegistrationServiceImpl.class.getName());

    private PasswordEncoder passwordEncoder;
    private OrganizationDAO organizationDAO;
    private UserInfoDAO userInfoDAO;
    private UserDao userDAO;

    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, OrganizationDAO organizationDAO, UserInfoDAO userInfoDAO,
                                   UserDao userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.organizationDAO = organizationDAO;
        this.userInfoDAO = userInfoDAO;
        this.userDAO = userDAO;
    }

    /**
     * Регистрирует новый аккаунт парнера
     *
     * @param userReqForm
     *
     * @param userSession
     * @return
     *
     * @throws RegistrationServiceException
     */
    @Override
    public Mono<ResponseEntity<UserReqForm>> registerNewOperatorAccount(UserReqForm userReqForm, User userSession) {
        logger.trace("Register user account with information: " + userReqForm);

        Organization organization = organizationDAO.getByName(userReqForm.getOrganizationName());
        if (organization == null) {
            throw new RegistrationServiceException("Организации не найдена");
        }

        if (!organizationDAO.getByUser(userSession).equals(organization)){
            throw new RegistrationServiceException("Организации не найдена");
        }

        User user = regUser(userReqForm, Role.ROLE_OPERATOR, organization);
        logger.debug("Registered user account: " + user.getId());
        return Mono.just(new ResponseEntity<>(userReqForm, HttpStatus.CREATED));
    }

    /**
     * Регистрирует новый аккаунт администратора
     *
     * @param userReqForm
     *
     * @return
     *
     * @throws RegistrationServiceException
     */
    @Override
    public Mono<ResponseEntity<UserReqForm>> registerNewAdminAccount(UserReqForm userReqForm) {
        logger.debug("Register admin user account with information: " + userReqForm);
        Organization organization = regOrganization(userReqForm.getOrganizationName());
        User user = regUser(userReqForm, Role.ROLE_ADMIN, organization);
        logger.debug("Registered user account: " + user.getId());
        return Mono.just(new ResponseEntity<>(userReqForm, HttpStatus.CREATED));
    }

    @Override
    public Organization regOrganization(String organizationName) {
        Organization organization = organizationDAO.getByName(organizationName);
        if (organization == null) {
            logger.warn("organization not found - " + organizationName);
            organization = new Organization();
            organization.setOrganizationName(organizationName);

//            organization.setOrganizationStatus(OrganizationStatus.STATUS_CREATED);
            organization.setOrganizationStatus(OrganizationStatus.STATUS_ACTIVED);//TODO нет процедуры активации организации
            logger.debug("add  organization - " + organization);
            organizationDAO.save(organization);
        }
        return organization;
    }

    /**
     * Зарегистрировать пользователя с формы
     *
     * @param userReqForm
     * @param role
     *
     * @return
     */
    public User regUser(UserReqForm userReqForm, Role role, Organization organization) {
        logger.debug("regUser: " + userReqForm);
        User user = new User();
        user.setEmail(userReqForm.getEmail());
        user.setUsername(userReqForm.getUsername());
        user.setPassword(passwordEncoder.encode(userReqForm.getPassword()));
        user.setUserStatus(UserStatus.STATUS_ACTIVED); //TODO not activation
        //user.setUserStatus(User.UserStatus.STATUS_CREATED);
        user.setRole(role);
        logger.debug("add  user - " + user);

        UserInfo userInfo = new UserInfo();
        userInfo.setLastName(userReqForm.getLastName());
        userInfo.setFirstName(userReqForm.getFirstName());
        userInfo.setSecondName(userReqForm.getSecondName());
        user.setUserInfo(userInfo);
        logger.debug("add  userInfo - " + userInfo);

        organization.getUsers().add(user);
        userInfoDAO.save(userInfo);
        userDAO.save(user);
        organizationDAO.update(organization);
        return user;
    }
}
