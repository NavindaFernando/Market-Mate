package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("code"), rst.getString("description"),  rst.getInt("qtyOnHand"),rst.getBigDecimal("unitPrice")));
        }
        return allItems;
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT code FROM Item WHERE code=?", code).next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }

    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)", dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
        rst.next();
        return new Item(code, rst.getString("description"), rst.getInt("qtyOnHand"), rst.getBigDecimal("unitPrice"));
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?", dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getCode());
    }
}
