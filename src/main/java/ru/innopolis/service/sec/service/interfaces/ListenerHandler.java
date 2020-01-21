package ru.innopolis.service.sec.service.interfaces;


import ru.innopolis.model.User;

public interface ListenerHandler {

    void updateAuthentication(User user);
}
