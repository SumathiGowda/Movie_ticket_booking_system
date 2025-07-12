package trial1;





import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
// Other specific imports you need

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import trial1.TicketSummaryGUI;
// Other specific AWT classes used


public class SeatBookingGUI extends JFrame {

    private int movieId;
    private String username;
    private int numberOfSeats;

    private final int rows = 11;
    private final int cols = 10;
    private final JToggleButton[][] seatButtons = new JToggleButton[rows][cols];
    private final Set<JToggleButton> selectedSeats = new HashSet<>();
    private final JLabel statusLabel = new JLabel();

    public SeatBookingGUI(int movieId, String username, int numberOfSeats) {
        this.movieId = movieId;
        this.username = username;
        this.numberOfSeats = numberOfSeats;

        setTitle("Seat Booking - Movie ID: " + movieId + ", User: " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createSeatLayout();
    }

private void createSeatLayout() {
    JPanel seatPanel = new JPanel(new GridLayout(rows, cols + 1, 5, 5)); // +1 for aisle

    // Get booked seats from DB
    Set<String> bookedSeats = getBookedSeatsFromDatabase(movieId);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j <= cols; j++) {
            if (j == 5) {
                seatPanel.add(new JLabel()); // aisle gap
                continue;
            }

            int actualCol = j < 5 ? j : j - 1;
            String seatCode = (char) ('A' + i) + String.valueOf(actualCol + 1);
            JToggleButton seat = new JToggleButton(seatCode);
            seatButtons[i][actualCol] = seat;

            if (bookedSeats.contains(seatCode)) {
                seat.setEnabled(false);
                seat.setBackground(Color.RED);
            } else {
                seat.addActionListener(e -> handleSeatSelection(seat));
            }
            
            seatPanel.add(seat);
        }
    }
JButton cancelButton = new JButton("Cancel");
cancelButton.addActionListener(e -> {
    System.out.println("Cancel button clicked");
    UserDashboard dashboard = new UserDashboard(username);
    dashboard.setVisible(true);
    this.dispose();
});



JButton confirmButton = new JButton("Confirm Booking");
confirmButton.addActionListener(e -> showSelectedSeats());

JPanel buttonPanel = new JPanel(); // default FlowLayout
buttonPanel.add(cancelButton);
buttonPanel.add(confirmButton);

JPanel bottomPanel = new JPanel(new BorderLayout());
statusLabel.setText("Select up to " + numberOfSeats + " seats.");
bottomPanel.add(statusLabel, BorderLayout.CENTER);
bottomPanel.add(buttonPanel, BorderLayout.EAST);

add(new JScrollPane(seatPanel), BorderLayout.CENTER);
add(bottomPanel, BorderLayout.SOUTH);

}


    private void handleSeatSelection(JToggleButton seat) {
        if (seat.isSelected()) {
            if (selectedSeats.size() < numberOfSeats) {
                selectedSeats.add(seat);
                seat.setBackground(Color.GREEN);
            } else {
                seat.setSelected(false);
                JOptionPane.showMessageDialog(this, "You can only select " + numberOfSeats + " seats.");
            }
        } else {
            selectedSeats.remove(seat);
            seat.setBackground(null);
        }
        statusLabel.setText("Selected: " + selectedSeats.size() + "/" + numberOfSeats);
    }

    private void showSelectedSeats() {
        if (selectedSeats.size() != numberOfSeats) {
            JOptionPane.showMessageDialog(this, "Please select exactly " + numberOfSeats + " seats.");
            return;
        }

        StringBuilder seatList = new StringBuilder("You selected: ");
        boolean success = true;

        for (JToggleButton seat : selectedSeats) {
            seatList.append(seat.getText()).append(" ");
            if (!insertBookingIntoDatabase(movieId, username, seat.getText())) {
                success = false;
            }
        }

      if (success) {
    List<String> seats = new ArrayList<>();
    for (JToggleButton seat : selectedSeats) {
        seats.add(seat.getText());
    }
    new TicketSummaryGUI(movieId, username, seats).setVisible(true);
    this.dispose();
} else {
    JOptionPane.showMessageDialog(this, "Booking failed for one or more seats.");
}

    }

    private boolean insertBookingIntoDatabase(int movieId, String username, String seatNumber) {
        String url = "jdbc:mysql://localhost:3300/movie_db"; // Correct MySQL port
        String dbUser = "root"; // Change if needed
        String dbPassword = ""; // Change if needed

        String sql = "INSERT INTO tickets_table (movie_id, username, seat_number) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movieId);
            pstmt.setString(2, username);
            pstmt.setString(3, seatNumber);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted seat: " + seatNumber + ", Rows affected: " + rowsAffected);
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to insert seat: " + seatNumber);
            e.printStackTrace();
            return false;
        }
    }
    
    private Set<String> getBookedSeatsFromDatabase(int movieId) {
    Set<String> bookedSeats = new HashSet<>();
    String url = "jdbc:mysql://localhost:3300/movie_db"; // Your DB url
    String dbUser = "root";
    String dbPassword = "";

    String sql = "SELECT seat_number FROM tickets_table WHERE movie_id = ?";

    try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, movieId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            bookedSeats.add(rs.getString("seat_number"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bookedSeats;
}
}
