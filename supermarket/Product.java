/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import project.ConnectionProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * c
 *
 * @author hp
 */
public final class Product extends javax.swing.JFrame {

    /**
     * Creates new form Product
     */
    DefaultTableModel model;

    public Product() {
      initComponents();
        FetchDetailsToTable();
        //initializeCategoryComboBox();
    }

    public boolean InsertProduct() {
        String proid = jTextField1.getText();
        String proname = jTextField2.getText();
        String procat = pCat.getSelectedItem().toString();
        String proprice = price.getText();
        String proqty = qty.getText();

        try {
            Connection con = ConnectionProvider.getCon();
            String sql = "Insert into product_tbl(proId,proName,proCat,proPrice,proQty) values (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, proid);
            ps.setString(2, proname);
            ps.setString(3, procat);
            ps.setString(4, proprice);
            ps.setString(5, proqty);

            int updatedRowCount = ps.executeUpdate();

            if (updatedRowCount > 0) {
                JOptionPane.showMessageDialog(this, "Successfully Added !");

            } else {
                JOptionPane.showMessageDialog(this, "Error Can't Add !");

            }

        } catch (HeadlessException | SQLException e) {
            e.getMessage();

        }
        return false;

    }
//    public ArrayList<categoryArraylist> getCategoryList() {
//    ArrayList<categoryArraylist> getCategoryList = new ArrayList<>();
//
//    try (Connection con = ConnectionProvider.getCon();
//         Statement st = con.createStatement();
//         ResultSet rs = st.executeQuery("SELECT cName FROM category_tbl")) {
//
//        while (rs.next()) {
//            categoryArraylist bd = new categoryArraylist();
//            bd.setcName(rs.getString("cName"));
//            getCategoryList.add(bd);
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return getCategoryList; // Fix: return the variable, not a method call
//}
//private void initializeCategoryComboBox() {
//        Category category = new Category();
//    ArrayList<categoryArraylist> getCategoryList = category.getCategoryList();
//
//    // Clear existing items
//    pCat.removeAllItems(); // Assuming pCat is your JComboBox
//
//    // Add fetched categories to the JComboBox
//    for (categoryArraylist categoryItem : getCategoryList) {
//        pCat.addItem(categoryItem.getcName());
//    }
//}
    public ArrayList<productArraylist> getList() {
        ArrayList<productArraylist> getList = new ArrayList<>();

        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT proId,proName,proCat,proPrice,proQty from product_tbl");

            while (rs.next()) {
                productArraylist bd = new productArraylist();
                bd.setProId(rs.getString("proId"));
                bd.setProName(rs.getString("proName"));
                bd.setProCat(rs.getString("proCat"));
                bd.setProPrice(rs.getString("proPrice"));
                bd.setProQty(rs.getString("proQty"));

                getList.add(bd);

            }

        } catch (SQLException e) {

        }
        return getList;

    }

    public void FetchDetailsToTable() {

        ArrayList<productArraylist> datArrayList = getList();
        model = (DefaultTableModel) product_tbl.getModel();
        model.setRowCount(0);

        Object[] rows = new Object[5];
        //loop through the arraylist to populate jtable

        for (int i = 0; i < datArrayList.size(); i++) {
            rows[0] = datArrayList.get(i).getProId();
            rows[1] = datArrayList.get(i).getProName();
            rows[2] = datArrayList.get(i).getProCat();
            rows[3] = datArrayList.get(i).getProPrice();
            rows[4] = datArrayList.get(i).getProQty();

            model.addRow(rows);
        }
    }

    /**
     *
     */
    public void UpdateProductCount() {
        String proid = jTextField1.getText();
        String qtyy = qty.getText();
        try (Connection con = ConnectionProvider.getCon();
                PreparedStatement ps = con.prepareStatement("UPDATE product_tbl SET proQty = proQty + ? WHERE proId = ?")) {

            // Assuming qty is an integer, you may need to parse it based on your requirements
            ps.setInt(1, Integer.parseInt(qtyy));
            ps.setString(2, proid);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Product Count Updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Cannot Update Product Count. Product ID not found or count is already 0.");
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
public void UpdatePriceCount() {
        String proid = jTextField1.getText();
        String pric = price.getText();
        try (Connection con = ConnectionProvider.getCon();
                PreparedStatement ps = con.prepareStatement("UPDATE product_tbl SET proPrice = proPrice + ? WHERE proId = ?")) {

            // Assuming qty is an integer, you may need to parse it based on your requirements
            ps.setInt(1, Integer.parseInt(pric));
            ps.setString(2, proid);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                JOptionPane.showMessageDialog(this, "Product Count Updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Cannot Update Product Count. Product ID not found or count is already 0.");
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Delete the selected row from the table and database
    public void deleteSelectedRow() {
        // Get the selected row index
        int selectedRowIndex = product_tbl.getSelectedRow();

        // Check if any row is selected
        if (selectedRowIndex != -1) {
            // Get the product ID from the selected row
            String proId = model.getValueAt(selectedRowIndex, 0).toString();

            try {
                // Connect to the database
                Connection con = ConnectionProvider.getCon();

                // Prepare the SQL statement for deletion
                String sql = "DELETE FROM product_tbl WHERE proId=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, proId);

                // Execute the SQL statement
                int rowCount = ps.executeUpdate();

                if (rowCount > 0) {
                    // Remove the row from the table
                    model.removeRow(selectedRowIndex);
                    JOptionPane.showMessageDialog(this, "Row deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete row.");
                }

                // Close the PreparedStatement
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        proId = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        proName = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        proQty = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        proPrice = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        cat = new javax.swing.JLabel();
        pCat = new javax.swing.JComboBox<>();
        edit = new javax.swing.JButton();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        product_tbl = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        search = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 0));
        jLabel3.setText(" PRODUCT LIST");

        proId.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        proId.setForeground(new java.awt.Color(255, 102, 0));
        proId.setText("Product ID");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 102, 0));

        proName.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        proName.setForeground(new java.awt.Color(255, 102, 0));
        proName.setText("Name");

        jTextField2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 102, 0));

        proQty.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        proQty.setForeground(new java.awt.Color(255, 102, 0));
        proQty.setText("Quantity");

        qty.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        qty.setForeground(new java.awt.Color(255, 102, 0));

        proPrice.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        proPrice.setForeground(new java.awt.Color(255, 102, 0));
        proPrice.setText("Price");

        price.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        price.setForeground(new java.awt.Color(255, 102, 0));

        cat.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        cat.setForeground(new java.awt.Color(255, 102, 0));
        cat.setText("Category");

        pCat.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        pCat.setForeground(new java.awt.Color(255, 102, 0));
        pCat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grocery", "Beverage", "Cosmetic", "Electronics", "Utensils", "Decorations", "Clothing", "Baby point", "Accessories" }));

        edit.setBackground(new java.awt.Color(255, 102, 0));
        edit.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        edit.setForeground(new java.awt.Color(255, 255, 255));
        edit.setText("Update");
        edit.setBorder(null);
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        add.setBackground(new java.awt.Color(255, 102, 0));
        add.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        add.setForeground(new java.awt.Color(255, 255, 255));
        add.setText("Add");
        add.setBorder(null);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(255, 102, 0));
        delete.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Delete");
        delete.setBorder(null);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        clear.setBackground(new java.awt.Color(255, 102, 0));
        clear.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        clear.setForeground(new java.awt.Color(255, 255, 255));
        clear.setText("Clear");
        clear.setBorder(null);
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        product_tbl.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        product_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Price", "Quantity"
            }
        ));
        product_tbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        product_tbl.setRowHeight(25);
        product_tbl.setSelectionBackground(new java.awt.Color(255, 102, 0));
        jScrollPane1.setViewportView(product_tbl);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 102, 0));
        jLabel9.setText("MANAGE PRODUCT");

        search.setBackground(new java.awt.Color(255, 102, 0));
        search.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setText("Search");
        search.setBorder(null);
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proId)
                            .addComponent(proName)
                            .addComponent(cat))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proQty)
                                    .addComponent(proPrice)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pCat, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(309, 309, 309))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180))))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(jLabel9)
                .addGap(0, 272, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(proId)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(proName)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(proQty)
                            .addGap(18, 18, 18)
                            .addComponent(proPrice))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cat)
                    .addComponent(pCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Desktop\\Supermarket\\image\\cancel_190406.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        InsertProduct();
        setVisible(false);
        new Product().setVisible(true);
    }//GEN-LAST:event_addActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed

        UpdateProductCount();
        UpdatePriceCount();

    }//GEN-LAST:event_editActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        deleteSelectedRow();
    }//GEN-LAST:event_deleteActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        qty.setText("");
        price.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // Get the target product ID from the text field
        String targetProductId = jTextField5.getText().trim();

        // Perform binary search
        int foundIndex = binarySearchProduct(targetProductId);

        // Display the search result
        if (foundIndex != -1) {
            // Highlight the row in the table
            product_tbl.setRowSelectionInterval(foundIndex, foundIndex);

            // Scroll to the highlighted row
            product_tbl.scrollRectToVisible(product_tbl.getCellRect(foundIndex, 0, true));

            JOptionPane.showMessageDialog(this, "Product found!");
        } else {
            JOptionPane.showMessageDialog(this, "Product not found!");
        }
    }

    private int binarySearchProduct(String targetProductId) {
        ArrayList<productArraylist> productList = getList();

        int low = 0;
        int high = productList.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midProductId = productList.get(mid).getProId();

            if (midProductId.equals(targetProductId)) {
                return mid; // Found the product, return the index
            } else if (midProductId.compareTo(targetProductId) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1; // Product not found, return -1

    }//GEN-LAST:event_searchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Seller.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seller.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seller.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seller.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Product().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JLabel cat;
    private javax.swing.JButton clear;
    private javax.swing.JButton delete;
    private javax.swing.JButton edit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JComboBox<String> pCat;
    private javax.swing.JTextField price;
    private javax.swing.JLabel proId;
    private javax.swing.JLabel proName;
    private javax.swing.JLabel proPrice;
    private javax.swing.JLabel proQty;
    private javax.swing.JTable product_tbl;
    private javax.swing.JTextField qty;
    private javax.swing.JButton search;
    // End of variables declaration//GEN-END:variables

}
