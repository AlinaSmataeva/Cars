package model.dao;

import entity.Order;
import exception.DaoException;

public interface OrderRepository {
    boolean createOrder(Order order) throws DaoException;

}
