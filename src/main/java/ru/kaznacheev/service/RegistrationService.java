package ru.kaznacheev.service;

import ru.kaznacheev.entity.Client;

public interface RegistrationService {

    String registration(Client client);
    String activate(Client client);

}
