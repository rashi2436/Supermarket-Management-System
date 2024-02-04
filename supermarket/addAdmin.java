/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.awt.HeadlessException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import project.ConnectionProvider;

/**
 *
 * @author hp
 */
public class addAdmin extends javax.swing.JFrame {

    /**
     * Creates new form addAdmin
     */
    public addAdmin() {
        initComponents();
        FetchDetailsToTable();
    }
  public boolean InsertSeller() throws NoSuchAlgorithmException {
    String fullname = txt_fullname.getText();
    String address = txt_address.getText();
    String email = txt_email.getText();
    String password = txt_password.getText();
    String contact = txt_contact.getText();
    
    try {
        Connection con = ConnectionProvider.getCon();
        String sql = "Insert into admin(fullname, contact, address, email, password) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, fullname);
        ps.setString(2, contact);
        ps.setString(3, address);
        ps.setString(4, email);

        // Hash the password using SHA-512
         MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            ps.setString(5, hashtext);
            
        
        int updatedRowCount = ps.executeUpdate();

        if (updatedRowCount > 0) {
            JOptionPane.showMessageDialog(this, "Added Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// Hash the password using SHA-512
//private String hashPassword(String password) {
//    try {
//        MessageDigest md = MessageDigest.getInstance("SHA-512");
//        byte[] messageDigest = md.digest(password.getBytes());
//        BigInteger no = new BigInteger(1, messageDigest);
//        String hashtext = no.toString(16);
//
//        while (hashtext.length() < 32) {
//            hashtext = "0" + hashtext;
//        }
//        return hashtext;
//    } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//        return null; // handle the exception appropriately in your application
//    }
//}
  //store database result into ArrayList Method
    public ArrayList<adminArraylist> getList() {
        ArrayList<adminArraylist> getList = new ArrayList<>();

        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT fullname,contact,address,email,password from admin");

            while (rs.next()) {
                adminArraylist bd = new adminArraylist();
                bd.setName(rs.getString("fullname"));
                bd.setMno(rs.getString("contact"));
                bd.setAddress(rs.getString("address"));
                bd.setEmail(rs.getString("email"));
                bd.setPassword(rs.getString("password"));

                //bd.setDescription(rs.getString("description"));
                getList.add(bd);

            }

        } catch (SQLException e) {

        }
        return getList;

    }
    //validation

    /**
     *
     * @return
     */
    public boolean validateSignup() {
        String fullname = txt_fullname.getText();
        String address = txt_address.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();
        String contact = txt_contact.getText();

        if (fullname.equals("") || !fullname.matches("^[a-zA-Z ]{0,30}$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid Name !");
            return false;
        }
        if (address.equals("") || !address.matches("^[a-zA-Z0-9 ]{0,30}$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid Address !");
            return false;
        }

        if (email.equals("") || !email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid Email !");
            return false;
        }

        if (contact.equals("") || !contact.matches("^[0-9]{0,15}$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid Contact !");
            return false;
        } else if (!(contact.length() == 10)) {
            JOptionPane.showMessageDialog(this, "Please enter valid Contact !");
            return false;
        }
        if (password.equals("") || password.contains("*")) {
            JOptionPane.showMessageDialog(this, "Please enter Password !");
            return false;
        }

        return true;

    }
public boolean CheckDuplicateUser() {
        String fullname = txt_fullname.getText();
        boolean isExist = false;
        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement("Select * from admin where name=?");
            ps.setString(1, fullname);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;

            } else {
                isExist = false;
            }

        } catch (SQLException e) {
        }
        return isExist;
    }
    //Populate Jtable with data from database
    public void FetchDetailsToTable() {

        ArrayList<adminArraylist> datArrayList = getList();
        DefaultTableModel model = (DefaultTableModel) admin.getModel();
        model.setRowCount(0);

        Object[] rows = new Object[5];
        //loop through the arraylist to populate jtable

        for (int i = 0; i < datArrayList.size(); i++) {
            rows[0] = datArrayList.get(i).getName();
            rows[1] = datArrayList.get(i).getMno();
            rows[2] = datArrayList.get(i).getAddress();
            rows[3] = datArrayList.get(i).getEmail();
            rows[4] = datArrayList.get(i).getPassword();
            model.addRow(rows);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_fullname = new javax.swing.JTextField();
        txt_contact = new javax.swing.JTextField();
        txt_address = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        anew = new javax.swing.JButton();
        update = new javax.swing.JButton();
        del = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        admin = new javax.swing.JTable();
        txt_password = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 153, 0));
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Add Admin Details");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setText("Mobile Number");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 153, 0));
        jLabel4.setText("Admin Name");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 0));
        jLabel5.setText("Address");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 0));
        jLabel7.setText("Email-Id");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 0));
        jLabel8.setText("Password");

        txt_fullname.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txt_contact.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txt_address.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        txt_email.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        anew.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        anew.setForeground(new java.awt.Color(255, 153, 0));
        anew.setText("Add New");
        anew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anewActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        update.setForeground(new java.awt.Color(255, 153, 0));
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        del.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        del.setForeground(new java.awt.Color(255, 153, 0));
        del.setText("Delete");
        del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delActionPerformed(evt);
            }
        });

        admin.setForeground(new java.awt.Color(255, 153, 0));
        admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Mno", "Address", "Email", "Password"
            }
        ));
        admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(admin);

        txt_password.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Desktop\\Supermarket\\image\\admin_person_user_man_2839.png")); // NOI18N
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(anew)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(update)
                                .addGap(39, 39, 39)
                                .addComponent(del))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(111, 111, 111)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(anew)
                    .addComponent(update)
                    .addComponent(del))
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\Desktop\\Supermarket\\image\\cancel_190406.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anewActionPerformed
        if (validateSignup()==true) {
            try {
                InsertSeller();
            } catch (Exception e) {
            }
            
        }
    }//GEN-LAST:event_anewActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
                                       
    int selectedRow = admin.getSelectedRow();
    if (selectedRow != -1) {
        String selectedName = admin.getValueAt(selectedRow, 0).toString();
        boolean duplicateUser = CheckDuplicateUser(selectedName);

        if (duplicateUser) {
            JOptionPane.showMessageDialog(this, "User already exists! Update not allowed.");
        } else {
            String updatedFullname = txt_fullname.getText();
            String updatedContact = txt_contact.getText();
            String updatedAddress = txt_address.getText();
            String updatedEmail = txt_email.getText();
            String updatedPassword = txt_password.getText();

            try {
                Connection con = ConnectionProvider.getCon();
                String sql = "UPDATE admin SET name=?, mno=?, address=?, email=?, password=? WHERE name=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, updatedFullname);
                ps.setString(2, updatedContact);
                ps.setString(3, updatedAddress);
                ps.setString(4, updatedEmail);
                ps.setString(5, updatedPassword);
                ps.setString(6, selectedName);

                int updatedRowCount = ps.executeUpdate();

                if (updatedRowCount > 0) {
                    JOptionPane.showMessageDialog(this, "Updated Successfully!");
                    FetchDetailsToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating user!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to update!");
    }


    }//GEN-LAST:event_updateActionPerformed

    private void delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delActionPerformed
                                   
    int selectedRow = admin.getSelectedRow();
    if (selectedRow != -1) {
        String selectedName = admin.getValueAt(selectedRow, 0).toString();

        try {
            Connection con = ConnectionProvider.getCon();
            String sql = "DELETE FROM admin WHERE name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, selectedName);

            int deletedRowCount = ps.executeUpdate();

            if (deletedRowCount > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully!");
                FetchDetailsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting user!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to delete!");
    }


    }//GEN-LAST:event_delActionPerformed

    private void adminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminMouseClicked
        //        int i=jTable1.getSelectedRow();        // TODO add your handling code here:
        //        TableModel model=jTable1.getModel();
        //        jTextField1.setText(model.getValueAt(i, 0).toString());
        //        jTextField2.setText(model.getValueAt(i, 1).toString());
        //        jTextField3.setText(model.getValueAt(i, 2).toString());
        //        jTextField4.setText(model.getValueAt(i, 3).toString());
        //        jTextField5.setText(model.getValueAt(i, 4).toString());
        //        jTextField6.setText(model.getValueAt(i, 5).toString());
    }//GEN-LAST:event_adminMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(addAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable admin;
    private javax.swing.JButton anew;
    private javax.swing.JButton del;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_contact;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fullname;
    private javax.swing.JTextField txt_password;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    private boolean CheckDuplicateUser(String selectedName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
