package by.epam.webtask.model.dao.impl;

import by.epam.webtask.exception.DaoException;

public class Main {
    public static void main(String[] args) throws DaoException {
        UserDaoImpl userDao = new UserDaoImpl();
        System.out.println(userDao.findAll());
    }
}
