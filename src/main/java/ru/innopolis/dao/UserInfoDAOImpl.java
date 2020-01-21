package ru.innopolis.dao;

import org.springframework.stereotype.Repository;
import ru.innopolis.model.UserInfo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserInfoDAOImpl.
 *
 * @author Ilya_Sukhachev
 */
@Repository
public class UserInfoDAOImpl implements UserInfoDAO {

    Map<UUID, UserInfo> userInfos = new ConcurrentHashMap();

    @Override
    public void save(UserInfo userInfo) {
        userInfo.setId(UUID.randomUUID());
        userInfos.put(userInfo.getId(), userInfo);
    }
}
