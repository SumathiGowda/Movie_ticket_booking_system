/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package trial1;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JButton;
import java.math.BigDecimal;
import java.sql.Date;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author sumat
 */
public class UserDashboard extends javax.swing.JFrame {
  public String username;
  
    /**
     * Creates new form UserDashboard
     */
    public UserDashboard(String username) {
        initComponents();
   // Load and scale the logo image to fit JLabel



        this.username = username;
       // Create the JScrollPane and add jPanel5 to it
       movieListPanel.setLayout(new BoxLayout(movieListPanel, BoxLayout.Y_AXIS));
       loadDataFromDatabase(); 



        searchField.setText("Search Movie");
        searchField.addFocusListener(new java.awt.event.FocusAdapter()
     {
         @Override
         public void focusGained(java.awt.event.FocusEvent evt)
         {
             if(searchField.getText().equals("Search Movie"))
             {
                 searchField.setText("");
                 searchField.setForeground(Color.BLACK);
                 
             }
         }
         
         @Override
         public void focusLost(java.awt.event.FocusEvent evt)
         {
             if(searchField.getText().isEmpty())
             {
                 searchField.setText("Search Movie");
                 searchField.setForeground(Color.GRAY);
                 
             }
             
         }
     });
        
   
          
    }
   


    private UserDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void loadDataFromDatabase() {
    String query = "SELECT * FROM movies_table";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pst = conn.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {

        movieListPanel.removeAll(); // Clear previous entries

        while (rs.next()) {
            // Panel for one movie
            JPanel moviePanel = new JPanel(new BorderLayout());
            moviePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // === Poster Image ===
            String imagePath = rs.getString("poster");
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(400, 450, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setPreferredSize(new Dimension(400, 450));
            moviePanel.add(imageLabel, BorderLayout.WEST);
            
            
            int movie_id = rs.getInt("movie_id");


            // === Movie Details Panel ===
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            Font font = new Font("Arial", Font.BOLD, 20);

            JLabel titleLabel = new JLabel("Title: " + rs.getString("title"));
            titleLabel.setFont(font);

            JLabel genreLabel = new JLabel("Genre: " + rs.getString("genre"));
            genreLabel.setFont(font);

            JLabel durationLabel = new JLabel("Duration: " + rs.getString("duration"));
            durationLabel.setFont(font);

            JLabel locationLabel = new JLabel("Location: " + rs.getString("location"));
            locationLabel.setFont(font);

            JLabel dateLabel = new JLabel("Date: " + rs.getDate("date").toString());
            dateLabel.setFont(font);

            JLabel timeLabel = new JLabel("Time: " + rs.getString("time"));
            timeLabel.setFont(font);

            JLabel priceLabel = new JLabel("Price: ₹" + rs.getBigDecimal("price").toString());
            priceLabel.setFont(font);

            JLabel languageLabel = new JLabel("Language: " + rs.getString("language"));
            languageLabel.setFont(font);

            // === Book Now Button ===
            JButton bookButton = new JButton("Book Now");
            bookButton.setFont(new Font("Arial", Font.BOLD, 18));
            bookButton.setBackground(Color.GREEN);
            bookButton.setForeground(Color.WHITE);
            
            
bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NumberOfSeats seats = new NumberOfSeats(movie_id, username);
                    seats.setVisible(true);
                    
                }
            });

            
            
            // Add all details to panel
            detailsPanel.add(titleLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(genreLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(durationLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(locationLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(dateLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(timeLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(priceLabel);
            detailsPanel.add(Box.createVerticalStrut(5));
            detailsPanel.add(languageLabel);
            detailsPanel.add(Box.createVerticalStrut(10));
            detailsPanel.add(bookButton);

            moviePanel.add(detailsPanel, BorderLayout.CENTER);

            // Add movie panel to main list panel
            movieListPanel.add(moviePanel);
            movieListPanel.add(Box.createVerticalStrut(20));
        }

        movieListPanel.revalidate();
        movieListPanel.repaint();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
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

        jTextField1 = new javax.swing.JTextField();
        jScrollBar2 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        movieListPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        jScrollBar2.setPreferredSize(new java.awt.Dimension(15, 48));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));

        jLabel2.setBackground(new java.awt.Color(255, 102, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PictureDekho");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Adobe Express - file (2).png")); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 100));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 100));
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        searchField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        searchField.setText("jTextField2");
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout movieListPanelLayout = new javax.swing.GroupLayout(movieListPanel);
        movieListPanel.setLayout(movieListPanelLayout);
        movieListPanelLayout.setHorizontalGroup(
            movieListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movieListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1061, Short.MAX_VALUE))
        );
        movieListPanelLayout.setVerticalGroup(
            movieListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movieListPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(482, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(movieListPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(searchField)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

       
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new UserDashboard().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel movieListPanel;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
